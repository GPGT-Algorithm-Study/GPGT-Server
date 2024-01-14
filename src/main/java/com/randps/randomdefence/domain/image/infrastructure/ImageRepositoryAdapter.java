package com.randps.randomdefence.domain.image.infrastructure;

import com.randps.randomdefence.domain.image.domain.Image;
import com.randps.randomdefence.domain.image.service.port.ImageRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryAdapter implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;

    private final ImageRepositoryImpl imageRepositoryImpl;

    @Override
    public void deleteByOriginName(String originName) {
        imageJpaRepository.deleteByOriginName(originName);
    }

    @Override
    public Image findByOriginName(String originName) {
        return imageJpaRepository.findByOriginName(originName);
    }

    @Override
    public List<Image> findAllByOriginNameIn(List<String> imageUUIDs) {
        return imageJpaRepository.findAllByOriginNameIn(imageUUIDs);
    }

    @Override
    public List<Long> findAllByCriterion(LocalDateTime criterion) {
        return imageRepositoryImpl.findAllByCriterion(criterion);
    }

    @Override
    public List<Image> findAllById(List<Long> imageIds) {
        return imageJpaRepository.findAllById(imageIds);
    }

    @Override
    public List<Image> findAll() {
        return imageJpaRepository.findAll();
    }

    @Override
    public Image save(Image image) {
        return imageJpaRepository.save(image);
    }

    @Override
    public List<Image> saveAll(List<Image> images) {
        return imageJpaRepository.saveAll(images);
    }

    @Override
    public void deleteAllById(List<Long> imageIds) {
        imageJpaRepository.deleteAllById(imageIds);
    }
}
