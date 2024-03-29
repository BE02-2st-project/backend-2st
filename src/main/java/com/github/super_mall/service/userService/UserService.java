package com.github.super_mall.service.userService;

import com.github.super_mall.dto.userDto.UserDto;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.exceptions.LoginException;
import com.github.super_mall.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.System.in;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    public UserDto updateUser(UserDto userDto, Long userId) {
       User user = userRepository.findById(userId).orElseThrow(
               () -> new LoginException("유저가 없습니다")
       );

       log.info("gender = {}", userDto);

       User updateUser = user.updateUser(userDto);
       User findNewUser = userRepository.save(updateUser);

       return new UserDto(findNewUser);
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new LoginException("유저가 없습니다")
        );

        return new UserDto(user);
    }
}
