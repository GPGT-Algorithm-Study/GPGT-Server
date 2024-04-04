package com.randps.randomdefence.domain.item.infrastructure;

import com.randps.randomdefence.domain.item.domain.Item;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

  @NonNull
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Item> findById(@NonNull Long id);

}
