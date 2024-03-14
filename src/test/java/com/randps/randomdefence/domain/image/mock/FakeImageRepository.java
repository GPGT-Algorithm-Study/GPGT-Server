package com.randps.randomdefence.domain.image.mock;

import com.randps.randomdefence.domain.image.domain.Image;
import com.randps.randomdefence.domain.image.service.port.ImageRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeImageRepository implements ImageRepository {

    private final List<Image> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Override
    public void deleteByOriginName(String originName) {
        data.removeIf(elem -> elem.getOriginName().equals(originName));
    }

    @Override
    public Image findByOriginName(String originName) {
        return data.stream().filter(elem -> elem.getOriginName().equals(originName)).findAny().orElse(null);
    }

    @Override
    public List<Image> findAllByOriginNameIn(List<String> imageUUIDs) {
        return data.stream().filter(elem -> imageUUIDs.contains(elem.getOriginName())).collect(Collectors.toList());
    }

    @Override
    public List<Long> findAllByCriterion(LocalDateTime criterion) {
        return data.stream()
                .filter(elem -> elem.getCreatedDate().isBefore(criterion) && !elem.getState())
                .map(Image::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Image> findAllById(List<Long> imageIds) {
        return data.stream().filter(elem -> imageIds.contains(elem.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Image> findAll() {
        return data;
    }

    @Override
    public Image save(Image image) {
        if (image.getId() == null || image.getId() == 0L) {
            autoIncreasingCount++;
            Image newImage = Image.builder()
                    .id(autoIncreasingCount)
                    .url(image.getUrl())
                    .originName(image.getOriginName())
                    .contentType(image.getContentType())
                    .build();
            autoIncreasingCount++;
            data.add(newImage);
            return newImage;
        } else {
            data.removeIf(elem -> elem.getId().equals(image.getId()));
            Image newImage = Image.builder()
                    .id(image.getId())
                    .url(image.getUrl())
                    .originName(image.getOriginName())
                    .contentType(image.getContentType())
                    .build();
            data.add(newImage);
            return newImage;
        }
    }

    @Override
    public List<Image> saveAll(List<Image> images) {
        List<Image> result = new ArrayList<>();
        for (Image image : images) {
            result.add(save(image));
        }
        return result;
    }

    @Override
    public void deleteAllById(List<Long> imageIds) {
        for (Long imageId : imageIds) {
            data.removeIf(elem -> elem.getId().equals(imageId));
        }
    }
}
