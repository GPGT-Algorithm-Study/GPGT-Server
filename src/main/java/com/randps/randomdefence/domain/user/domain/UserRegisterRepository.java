package com.randps.randomdefence.domain.user.domain;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegisterRepository extends JpaRepository<UserRegister, Long> {

    Optional<UserRegister> findByBojHandle(String bojHandle);
}
