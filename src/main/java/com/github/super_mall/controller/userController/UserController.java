package com.github.super_mall.controller.userController;

import com.github.super_mall.dto.userDto.UserDto;
import com.github.super_mall.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser (@PathVariable Long userId) {
        UserDto userDto = userService.getUser(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser (@RequestBody UserDto userDto, @PathVariable Long userId) {
        UserDto updateUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updateUser);
    }
}
