package com.randps.randomdefence.global.aws.s3.service;

import com.randps.randomdefence.domain.image.service.port.ImageRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Service
@RequiredArgsConstructor
public class S3BatchService {

    private final S3Service s3Service;

    private final ImageRepository imageRepository;

    /**
     * 6시간이 지났고, 글에 연결되지 않은 모든 이미지를 삭제한다.
     */
    @Transactional
    public void deleteDetachedImages() {
        // 6시간 전 기준을 생성한다.
        LocalDateTime sixHoursBeforeNow = LocalDateTime.now().minusHours(6);

        // 기준보다 이전이면서 state가 false인 모든 이미지Id를 조회한다.
        List<Long> imageIds = imageRepository.findAllByCriterion(sixHoursBeforeNow);

        // S3를 포함해서 이미지를 모두 삭제한다.
        s3Service.deleteAllByIdListIncludeS3(imageIds);
    }
}
