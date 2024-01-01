package com.randps.randomdefence.domain.boolshit.mock;

import com.randps.randomdefence.domain.boolshit.domain.Boolshit;
import com.randps.randomdefence.domain.boolshit.dto.BoolshitLastResponse;
import com.randps.randomdefence.domain.boolshit.service.port.BoolshitRepository;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Builder;

public class FakeBoolshitRepository implements BoolshitRepository {

    private final UserRepository userRepository;

    private final List<Boolshit> data = new ArrayList<>();

    private Long autoIncreasingCount = 0L;

    @Builder
    public FakeBoolshitRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<BoolshitLastResponse> findLastBoolshit() {
        return data.stream().map(item -> BoolshitLastResponse.builder()
                .id(item.getId())
                .notionId(userRepository.findByBojHandle(item.getBojHandle()).orElseThrow().getNotionId())
                .emoji(userRepository.findByBojHandle(item.getBojHandle()).orElseThrow().getEmoji())
                .message(item.getMessage())
                .writtenDate(item.getCreatedDate())
                .build()).findAny();
    }

    @Override
    public List<Boolshit> findAll() {
        return data;
    }

    @Override
    public Boolshit save(Boolshit boolshit) {
        if (boolshit.getId() == null || boolshit.getId() == 0L) {
            autoIncreasingCount++;
            Boolshit newBoolshit = Boolshit.builder()
                    .id(autoIncreasingCount)
                    .bojHandle(boolshit.getBojHandle())
                    .message(boolshit.getMessage())
                    .build();
            data.add(newBoolshit);
            return newBoolshit;
        } else {
            data.removeIf(item -> item.getId().equals(boolshit.getId()));
            Boolshit newBoolshit = Boolshit.builder()
                    .id(boolshit.getId())
                    .bojHandle(boolshit.getBojHandle())
                    .message(boolshit.getMessage())
                    .build();
            data.add(newBoolshit);
            return newBoolshit;
        }
    }
}
