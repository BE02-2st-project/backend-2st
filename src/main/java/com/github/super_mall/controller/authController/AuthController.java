package com.github.super_mall.controller.authController;

import com.github.super_mall.dto.loginDTO.LoginDTO;
import com.github.super_mall.dto.loginDTO.LoginResponseDto;
import com.github.super_mall.dto.refreshTokenDto.RefreshTokenDto;
import com.github.super_mall.dto.signupDto.SignupDTO;
import com.github.super_mall.dto.userDto.UserDto;
import com.github.super_mall.service.authService.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup (@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
       UserDto userDto = authService.signup(signupDTO);
       return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
       LoginResponseDto user = authService.login(loginDTO, response);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<String> refresh (@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse response) {
        String msg = authService.refresh(refreshTokenDto, response);
        return ResponseEntity.ok(msg);
    }

    @PutMapping("/deleted/{userId}")
    public ResponseEntity<UserDto> secession (@PathVariable Long userId) {
        UserDto userDto = authService.secession(userId);
        return ResponseEntity.ok(userDto);
    }

}
