package com.github.super_mall.dto.loginDTO;

import com.github.super_mall.entity.userEntity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginResponseDto {

    private Long userId;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String gender;
    private String socialName;
    private String socialUserId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime createAt;
    private LocalDateTime deletedAt;

    public LoginResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.socialName = user.getSocialName();
        this.socialUserId = user.getSocialUserId();
        this.createAt = user.getCreateAt();
        this.deletedAt = user.getDeletedAt();
    }
}
