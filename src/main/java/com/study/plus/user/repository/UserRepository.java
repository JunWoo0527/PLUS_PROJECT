package com.study.plus.user.repository;

import com.study.plus.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsUserByNickname(String nickname);

  boolean existsUserByEmail(String email);

//    Optional<User> findById(Long id);

  Optional<User> findByNickname(String nickname);
}
