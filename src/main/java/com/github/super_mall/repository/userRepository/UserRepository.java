package com.github.super_mall.repository.userRepository;

import com.github.super_mall.entity.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
