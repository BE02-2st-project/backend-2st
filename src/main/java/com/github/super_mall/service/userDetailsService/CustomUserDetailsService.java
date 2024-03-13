package com.github.super_mall.service.userDetailsService;

import com.github.super_mall.entity.roleEntity.Role;
import com.github.super_mall.entity.userDetailEntity.CustomUserDetails;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.entity.userRoleEntity.UserRole;
import com.github.super_mall.exceptions.LoginException;
import com.github.super_mall.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(email).orElseThrow(
               () -> new LoginException("유저을 찾을 수 없습니다.")
       );

       CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .user_id(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(UserRole::getRole).map(Role::getRoleName).toList())
                .build();

       return  customUserDetails;
    }
}
