package com.randps.randomdefence.domain.image.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    void deleteByOriginName(String originName);

//    void deleteAllByIdIn()

    Image findByOriginName(String originName);

    List<Image> findAllByOriginNameIn(List<String> imageUUIDs);
}
