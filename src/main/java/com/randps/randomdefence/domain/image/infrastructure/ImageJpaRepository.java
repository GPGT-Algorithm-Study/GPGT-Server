package com.randps.randomdefence.domain.image.infrastructure;

import com.randps.randomdefence.domain.image.domain.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    void deleteByOriginName(String originName);

//    void deleteAllByIdIn()

    Image findByOriginName(String originName);

    List<Image> findAllByOriginNameIn(List<String> imageUUIDs);
}
