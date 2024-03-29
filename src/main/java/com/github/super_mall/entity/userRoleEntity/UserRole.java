package com.github.super_mall.entity.userRoleEntity;

import com.github.super_mall.entity.roleEntity.Role;
import com.github.super_mall.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long userRoleId;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;
}
