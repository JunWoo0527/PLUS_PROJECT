package com.study.plus.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional <RefreshToken> findByKeyNickname(String nickname);

    void deleteByKeyNickname(String nickname);



}
