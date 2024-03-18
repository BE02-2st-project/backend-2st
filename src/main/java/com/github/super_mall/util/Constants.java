package com.github.super_mall.util;

public final class Constants {

    private Constants() {}

    public static final String HEADER_ACCESSTOKEN_KEY = "Authorization";
    public static final String HEADER_REFRESHTOKEN_KEY = "refresh_token";
    public static final String BEARER = "Bearer ";
    public static final Long ACCESSTOKENVAILIDMILLISECOND = 1000L * 60 * 60 * 24 * 7;
    public static final Long REFRESHTOKENVAILIDMILLISECOND = 1000L * 60 * 60 * 24 + 15;


}
