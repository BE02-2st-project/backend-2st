package com.github.super_mall.dto.refreshTokenDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenDto {

    private String email;
    private String refreshToken;

}
