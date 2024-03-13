package com.github.super_mall.repository.userRepository;

import com.github.super_mall.entity.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUserName(String id);

    Optional<User> findByEmail(String email);

}
