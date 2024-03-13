package com.github.super_mall.entity.refreshTokenEntity;

import com.github.super_mall.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @Column(name = "refresh_token")
    private String refreshToken;
}
