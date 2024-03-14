package com.randps.randomdefence.domain.image.service.port;

import com.randps.randomdefence.domain.image.domain.Image;
import java.time.LocalDateTime;
import java.util.List;

public interface ImageRepository {

    void deleteByOriginName(String originName);

    Image findByOriginName(String originName);

    List<Image> findAllByOriginNameIn(List<String> imageUUIDs);

    List<Long> findAllByCriterion(LocalDateTime criterion);

    List<Image> findAllById(List<Long> imageIds);

    List<Image> findAll();

    Image save(Image image);

    List<Image> saveAll(List<Image> images);

    void deleteAllById(List<Long> imageIds);
}
