package com.example.sso_service.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sso_service.entities.AccessToken;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    Optional<AccessToken> findByToken(String token);
}
