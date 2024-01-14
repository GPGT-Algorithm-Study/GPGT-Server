package com.randps.randomdefence.domain.image.service;

import com.randps.randomdefence.domain.image.domain.Image;
import com.randps.randomdefence.domain.image.service.port.ImageRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    /*
     * 모든 이미지 조회
     */
    @Transactional
    public List<Image> findAllImage() {
        return imageRepository.findAll();
    }

    /*
     * 이미지 이름으로 이미지 조회
     */
    @Transactional
    public Image findByOriginalName(String originalName) {
        return imageRepository.findByOriginName(originalName);
    }

    /*
     * 이미지 이름 리스트로 모든 이미지 조회
     */
    @Transactional
    public List<Image> findAllByOriginalNameList(String[] originalNames) {
        return imageRepository.findAllByOriginNameIn(List.of(originalNames));
    }



    /*
     * 이미지 아이디 리스트로 전부 조회
     */
    @Transactional
    public List<Image> findAllByIdList(List<Long> imageIds) {
        return imageRepository.findAllById(imageIds);
    }

    /*
     * 이미지 서버에 저장
     */
    @Transactional
    public void save(String urlPath, String originName, String contentType) throws InvalidContentTypeException {
        String[] contentTypeArray = contentType.split("/");

        // 이미지 파일 확장자 확인
        if (!contentTypeArray[0].equals("image")) {
            throw new InvalidContentTypeException("이미지 파일이 아닙니다.");
        }

        Image image = Image.builder()
                .url(urlPath)
                .originName(originName)
                .contentType(contentTypeArray[1]) // 이미지 파일 확장자 저장
                .build();

        imageRepository.save(image);
    }

    /*
     * 이미지 UUID이름 리스트로 모두 state = true 설정
     */
    @Transactional
    public void setStateValidAll(List<String> imageUUIDs) {
        List<Image> images = imageRepository.findAllByOriginNameIn(imageUUIDs);

        for (Image image : images) {
            image.setStateOk();
        }

        imageRepository.saveAll(images);
    }

    /*
     * 이미지 서버에서 삭제
     */
    @Transactional
    public void delete(String originName) {
        imageRepository.deleteByOriginName(originName);
    }

    /*
     * 이미지 아이디 리스트로 전부 삭제
     */
    @Transactional
    public void deleteAllbyIdList(List<Long> imageIds) {
        imageRepository.deleteAllById(imageIds);
    }
}
