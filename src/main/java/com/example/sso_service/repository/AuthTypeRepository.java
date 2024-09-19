package com.example.sso_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sso_service.entities.AuthType;
import com.example.sso_service.entities.User;

@Repository
public interface AuthTypeRepository extends JpaRepository<AuthType, Long> {
}
