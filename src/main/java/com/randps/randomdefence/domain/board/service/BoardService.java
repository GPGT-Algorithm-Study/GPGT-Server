package com.randps.randomdefence.domain.board.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.randps.randomdefence.domain.board.domain.Board;
import com.randps.randomdefence.domain.board.dto.BoardDetail;
import com.randps.randomdefence.domain.board.dto.BoardSimple;
import com.randps.randomdefence.domain.board.dto.SearchCondition;
import com.randps.randomdefence.domain.board.service.port.BoardRepository;
import com.randps.randomdefence.domain.comment.service.CommentService;
import com.randps.randomdefence.domain.image.domain.BoardImage;
import com.randps.randomdefence.domain.image.domain.Image;
import com.randps.randomdefence.domain.image.service.ImageService;
import com.randps.randomdefence.domain.image.service.port.BoardImageRepository;
import com.randps.randomdefence.domain.image.service.port.ImageRepository;
import com.randps.randomdefence.domain.notify.enums.NotifyType;
import com.randps.randomdefence.domain.notify.service.NotifyService;
import com.randps.randomdefence.global.aws.s3.service.S3Service;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class BoardService {

  private final BoardRepository boardRepository;

  private final CommentService commentService;

  private final ImageRepository imageRepository;

  private final ImageService imageService;

  private final S3Service s3Service;

  private final BoardImageRepository boardImageRepository;

  private final NotifyService notifyService;

  /*
   * 게시글 저장
   */
  @Transactional
  public Board save(String type, String bojHandle, String title, String content, Integer problemId,
      String images) {
    Board board = Board.builder()
        .type(type)
        .bojHandle(bojHandle)
        .title(title)
        .content(content)
        .problemId(problemId).build();

    boardRepository.save(board);

    // 만약 공지라면 전체 유저에게 알림을 발행한다.
    if (type.equals("notice")) {
      notifyService.systemPublishToAll("📣 새로운 공지가 등록되었습니다. 확인해 보세요! [" + board.getTitle() + "]",
          NotifyType.NOTICE, board.getId());
    }

    if (images.isBlank() || images.isEmpty()) {
      return board;
    }

    String[] imageUUIDs = images.split(","); // UUID가 ','를 기준으로 붙어서와야함

    // 모든 이미지 state = true로 설정
    imageService.setStateValidAll(List.of(imageUUIDs));

    // 모든 이미지 조회해서 board image 생성
    List<Image> imageList = imageService.findAllByOriginalNameList(imageUUIDs);
    List<BoardImage> boardImages = new ArrayList<>();
    for (Image image : imageList) {
      boardImages.add(BoardImage.builder()
          .boardId(board.getId())
          .imageId(image.getId())
          .build());
    }
    boardImageRepository.saveAll(boardImages);

    return board;
  }

  /*
   * 게시글 수정
   */
  @Transactional
  public Board update(Long boardId, String type, String bojHandle, String title, String content,
      Integer problemId, String images) {
    // 업데이트 할 게시글 조회
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

    // 내용 업데이트
    board.update(type, bojHandle, title, content, problemId);

    // 업데이트 저장
    boardRepository.save(board);

    // 만약 공지라면 전체 유저에게 알림을 발행한다.
    if (type.equals("notice")) {
      notifyService.systemPublishToAll("📣 공지에 변화가 있습니다. 확인해보세요! [" + board.getTitle() + "]",
          NotifyType.NOTICE, board.getId());
    }

    if (images.isBlank() || images.isEmpty()) {
      return board;
    }

    String[] imageUUIDs = images.split(","); // UUID가 ','를 기준으로 붙어서와야함

    // 모든 이미지 state = true로 설정
    imageService.setStateValidAll(List.of(imageUUIDs));

    // 모든 이미지 조회해서 board image 생성
    List<Image> imageList = imageService.findAllByOriginalNameList(imageUUIDs);
    List<BoardImage> boardImages = boardImageRepository.findAllByBoardId(boardId);
    for (Image image : imageList) {
      // 이미 있다면 패스
      Boolean isExist = false;
      for (BoardImage boardImage : boardImages) {
        if (boardImage.getImageId().equals(image.getId())) {
          isExist = true;
          break;
        }
      }
      if (isExist) {
        continue;
      }
//            System.out.println("added Image Id : " + image.getId());
      boardImages.add(BoardImage.builder()
          .boardId(board.getId())
          .imageId(image.getId())
          .build());
    }
    // 존재 했는데 삭제된 이미지는 삭제한다.
    List<Long> deletedImageIds = new ArrayList<>();
    List<BoardImage> addedBoardImages = new ArrayList<>();
    for (BoardImage boardImage : boardImages) {
      // 이미 있다면 패스
      Boolean isExist = false;
      for (Image image : imageList) {
        if (boardImage.getImageId().equals(image.getId())) {
          isExist = true;
          break;
        }
      }
      if (isExist) {
        addedBoardImages.add(boardImage);
        continue;
      }
      // 삭제할 이미지 리스트에 추가
      deletedImageIds.add(boardImage.getImageId());
//            System.out.println("delete Image Id : " + boardImage.getImageId());
      // 삭제
      boardImageRepository.delete(boardImage);
    }
    // 저장
    boardImageRepository.saveAll(addedBoardImages);
    // 이미지 삭제
    s3Service.deleteAllByIdListIncludeS3(deletedImageIds);

    return board;
  }

  /*
   * 게시글 삭제 (종속성 고려)
   */
  @Transactional
  public void delete(Long boardId) {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));
    List<BoardImage> boardImages = boardImageRepository.findAllByBoardId(boardId);
    List<Long> imageIds = new ArrayList<>();

    // comment 전부 삭제
    commentService.deleteAllByBoardId(boardId);

    for (BoardImage bi : boardImages) {
      imageIds.add(bi.getImageId());
    }

    List<Image> images = imageRepository.findAllById(imageIds);

    // S3에서 이미지 전부 삭제
    for (Image image : images) {
      s3Service.deleteImage(image.getOriginName());
    }

//        // 이미지 전부 삭제
//        imageService.deleteAllbyIdList(imageIds);

    // 게시글 이미지 중간 테이블 객체 삭제
    boardImageRepository.deleteAllByBoardId(boardId);

    // 게시글 삭제
    boardRepository.delete(board);
  }

  /*
   * 특정 유저의 모든 게시글 삭제 (종속성 고려)
   */
  @Transactional
  public void deleteAllByBojHandle(String bojHandle) {
    boardRepository.findAllByBojHandle(bojHandle).forEach(board -> {
      delete(board.getId());
    });
  }

  /**
   * 모든 게시글 조회
   */
  public List<Board> findAll() {
    return boardRepository.findAll();
  }

  /**
   * 모든 게시글 조회 (페이징)
   */
  public Page<BoardSimple> findAllSimple(Pageable pageable) {
    return boardRepository.findAllBoardSimplePaging(pageable);
  }

  /**
   * 타입별 게시글 조회 (페이징)
   */
  public Page<BoardSimple> findAllSimpleByType(String type, Pageable pageable) {
    return boardRepository.findAllBoardSimpleByTypePaging(type, pageable);
  }

  /**
   * boardId로 게시글 디테일 조회
   */
  public BoardDetail findDetailByBoardId(Long boardId) {
    return boardRepository.findBoardDetail(boardId);
  }

  /**
   * 특정 유저의 bojHandle로 게시글 조회 (페이징)
   */
  public Page<BoardSimple> findAllSimpleByUser(String bojHandle, Pageable pageable) {
    return boardRepository.findAllUserBoardSimplePaging(bojHandle, pageable);
  }

  /**
   * 특정 질의(Qeury)에 따라 제목에 질의를 포함하는 게시글 조회 (페이징)
   */
  public Page<BoardSimple> findAllSimpleByQuery(String query, Pageable pageable) {
    return boardRepository.findAllBoardSimpleByQueryPaging(query, pageable);
  }

  /**
   * SearchCondition에 따른 게시글 동적 조회 (페이징)
   */
  public Page<BoardSimple> findAllSimpleByCondition(SearchCondition condition, Pageable pageable) {
    return boardRepository.findAllBoardSimpleByConditionPaging(condition, pageable);
  }

}
