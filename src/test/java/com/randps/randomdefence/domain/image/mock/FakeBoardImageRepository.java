package com.randps.randomdefence.domain.image.mock;

import com.randps.randomdefence.domain.image.domain.BoardImage;
import com.randps.randomdefence.domain.image.service.port.BoardImageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeBoardImageRepository implements BoardImageRepository {

    private final List<BoardImage> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public List<BoardImage> findAllByBoardId(Long boardId) {
        return data.stream().filter(item -> item.getBoardId().equals(boardId)).collect(Collectors.toList());
    }

    @Override
    public void deleteAllByBoardId(Long boardId) {
        data.removeIf(item -> item.getBoardId().equals(boardId));
    }

    public BoardImage save(BoardImage boardImage) {
        if (boardImage.getId() == null || boardImage.getId() == 0L) {
            autoIncreasingCount++;
            BoardImage newBoardImage = BoardImage.builder()
                    .id(autoIncreasingCount)
                    .boardId(boardImage.getBoardId())
                    .imageId(boardImage.getImageId())
                    .build();
            data.add(newBoardImage);
            return newBoardImage;
        } else {
            data.removeIf(item -> item.getId().equals(boardImage.getId()));
            BoardImage newBoardImage = BoardImage.builder()
                    .id(boardImage.getId())
                    .boardId(boardImage.getBoardId())
                    .imageId(boardImage.getImageId())
                    .build();
            data.add(newBoardImage);
            return newBoardImage;
        }
    }

    @Override
    public List<BoardImage> saveAll(List<BoardImage> boardImages) {
        List<BoardImage> result = new ArrayList<>();
        for (BoardImage boardImage : boardImages) {
            result.add(save(boardImage));
        }
        return result;
    }

    @Override
    public void delete(BoardImage boardImage) {
        data.removeIf(item -> item.getId().equals(boardImage.getId()));
    }
}
