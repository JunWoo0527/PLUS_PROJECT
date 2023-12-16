package com.study.plus.user.repository;

import com.study.plus.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByNickname(String nickname);

    boolean existsUserByEmail(String email);

    Optional<User> findByNickname(String nickname);
}
