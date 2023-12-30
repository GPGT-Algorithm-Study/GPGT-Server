package com.randps.randomdefence.domain.user.service;

import com.randps.randomdefence.domain.user.domain.User;
import com.randps.randomdefence.domain.user.dto.authDto.PrincipalDetails;
import com.randps.randomdefence.domain.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String bojHandle) throws UsernameNotFoundException {
        User userEntity = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        return new PrincipalDetails(userEntity);
    }
}