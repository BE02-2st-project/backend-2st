package com.github.super_mall.entity.CustomoAuth2UserEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private String socialUserId;
    private String email;


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return this.socialUserId;
    }

    public String getEmail() {
        return this.email;
    }
}
