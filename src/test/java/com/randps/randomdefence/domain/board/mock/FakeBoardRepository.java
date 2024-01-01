package com.randps.randomdefence.domain.board.mock;

import com.randps.randomdefence.domain.board.domain.Board;
import com.randps.randomdefence.domain.board.dto.BoardDetail;
import com.randps.randomdefence.domain.board.dto.BoardSimple;
import com.randps.randomdefence.domain.board.dto.SearchCondition;
import com.randps.randomdefence.domain.board.service.port.BoardRepository;
import com.randps.randomdefence.domain.comment.service.port.CommentRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeBoardRepository implements BoardRepository {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final List<Board> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    public FakeBoardRepository(UserRepository userRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public List<Board> findAll() {
        return data;
    }

    @Override
    public Board save(Board board) {
        if (board.getId() == null || board.getId() == 0L) {
            autoIncreasingCount++;
            Board newBoard = Board.builder().id(autoIncreasingCount).type(board.getType())
                    .bojHandle(board.getBojHandle()).title(board.getTitle()).content(board.getContent())
                    .problemId(board.getProblemId()).build();
            data.add(newBoard);
            return newBoard;
        } else {
            data.removeIf(item -> item.getId().equals(board.getId()));
            Board newBoard = Board.builder().id(board.getId()).type(board.getType()).bojHandle(board.getBojHandle())
                    .title(board.getTitle()).content(board.getContent()).problemId(board.getProblemId()).build();
            data.add(newBoard);
            return newBoard;
        }
    }

    @Override
    public void delete(Board board) {
        data.removeIf(item -> item.getId().equals(board.getId()));
    }

    @Override
    public List<Board> findAllByBojHandle(String bojHandle) {
        return data.stream().filter(item -> item.getBojHandle().equals(bojHandle)).collect(Collectors.toList());
    }

    @Override
    public Page<BoardSimple> findAllBoardSimplePaging(Pageable pageable) {
        List<BoardSimple> boardSimples;
        long total = data.size();

        if (pageable.getPageNumber() == 0) {
            boardSimples = data.stream().limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        } else {
            boardSimples = data.stream().skip((long) pageable.getPageSize() * pageable.getPageNumber())
                    .limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(boardSimples, pageable, total);
    }

    @Override
    public Page<BoardSimple> findAllBoardSimpleByTypePaging(String type, Pageable pageable) {
        List<BoardSimple> boardSimples;
        List<Board> filteredData = data.stream().filter(elem -> elem.getType().contains(type))
                .collect(Collectors.toList());
        long total = filteredData.size();

        if (pageable.getPageNumber() == 0) {
            boardSimples = filteredData.stream().limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        } else {
            boardSimples = filteredData.stream()
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber()).limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(boardSimples, pageable, total);
    }

    @Override
    public BoardDetail findBoardDetail(Long boardId) {
        return data.stream().filter(elem -> elem.getId().equals(boardId))
                .map(elem -> BoardDetail.builder()
                        .id(elem.getId())
                        .createdDate(elem.getCreatedDate())
                        .modifiedDate(elem.getModifiedDate())
                        .type(elem.getType())
                        .bojHandle(elem.getBojHandle())
                        .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                        .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                        .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                        .title(elem.getTitle())
                        .content(elem.getContent())
                        .problemId(elem.getProblemId())
                        .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                .findAny().orElseThrow();
    }

    @Override
    public Page<BoardSimple> findAllUserBoardSimplePaging(String bojHandle, Pageable pageable) {
        List<BoardSimple> boardSimples;
        List<Board> filteredData = data.stream().filter(elem -> elem.getBojHandle().contains(bojHandle))
                .collect(Collectors.toList());
        long total = filteredData.size();

        if (pageable.getPageNumber() == 0) {
            boardSimples = filteredData.stream().limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        } else {
            boardSimples = filteredData.stream()
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber()).limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(boardSimples, pageable, total);
    }

    @Override
    public Page<BoardSimple> findAllBoardSimpleByQueryPaging(String query, Pageable pageable) {
        List<BoardSimple> boardSimples;
        List<Board> filteredData = data.stream().filter(elem -> elem.getTitle().contains(query))
                .collect(Collectors.toList());
        long total = filteredData.size();

        if (pageable.getPageNumber() == 0) {
            boardSimples = filteredData.stream().limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        } else {
            boardSimples = filteredData.stream()
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber()).limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(boardSimples, pageable, total);
    }

    @Override
    public Page<BoardSimple> findAllBoardSimpleByConditionPaging(SearchCondition condition, Pageable pageable) {
        List<BoardSimple> boardSimples;
        List<Board> filteredData = data.stream()
                .filter(elem -> !condition.type.isEmpty() && !condition.type.isBlank() && elem.getType().equals(condition.type))
                .filter(elem -> !condition.bojHandle.isEmpty() && !condition.bojHandle.isBlank() && elem.getBojHandle().equals(condition.bojHandle))
                .filter(elem -> !condition.query.isEmpty() && !condition.query.isBlank() && elem.getTitle().contains(condition.query))
                .collect(Collectors.toList());
        long total = filteredData.size();

        if (pageable.getPageNumber() == 0) {
            boardSimples = filteredData.stream().limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        } else {
            boardSimples = filteredData.stream().skip((long) pageable.getPageSize() * pageable.getPageNumber())
                    .limit(pageable.getPageSize())
                    .map(elem -> BoardSimple.builder().id(elem.getId()).createdDate(elem.getCreatedDate())
                            .modifiedDate(elem.getModifiedDate()).type(elem.getType()).bojHandle(elem.getBojHandle())
                            .notionId(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getNotionId())
                            .emoji(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getEmoji())
                            .profileImg(userRepository.findByBojHandle(elem.getBojHandle()).orElseThrow().getProfileImg())
                            .title(elem.getTitle()).content(elem.getContent()).problemId(elem.getProblemId())
                            .commentCount((long) commentRepository.findAllByBoardId(elem.getId()).size()).build())
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(boardSimples, pageable, total);
    }
}