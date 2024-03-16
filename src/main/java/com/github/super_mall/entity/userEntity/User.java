package com.github.super_mall.entity.userEntity;

import com.github.super_mall.dto.userDto.UserDto;
import com.github.super_mall.entity.userRoleEntity.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "gender")
    private String gender;
    @Column(name = "social_name")
    private String socialName;
    @Column(name = "social_user_id")
    private String socialUserId;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<UserRole> roles;

    public User (String socialUserId, String socialName, String email, String userName) {
        this.socialUserId = socialUserId;
        this.password = "12341234!";
        this.email = email;
        this.socialName = socialName;
        this.userName = userName;
    }

    public User update(String socialUserId, String socialName, String email, String userName) {
        this.socialUserId = socialUserId;
        this.password = "12341234!";
        this.email = email;
        this.socialName = socialName;
        this.userName = userName;
        return this;
    }


    public User updateUser(UserDto userDto) {
        this.userName = userDto.getUserName();
        this.address = userDto.getAddress();
        this.phoneNumber = userDto.getPhoneNumber();
        this.gender = userDto.getGender();
        return this;
    }
}
