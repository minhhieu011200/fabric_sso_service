package com.example.sso_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.sso_service.dto.CreateUserRequestDTO;
import com.example.sso_service.dto.LoginRequestDTO;
import com.example.sso_service.dto.LoginResponseDTO;
import com.example.sso_service.entities.User;
import com.example.sso_service.repository.UserRepository;
import com.example.sso_service.service.AuthService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/createUser")
	public User createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
		return authService.createUser(createUserRequestDTO);
	}

	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return authService.login(loginRequestDTO);
	}
	
	@PostMapping("refreshToken")
	public LoginResponseDTO refreshToken(HttpServletRequest httpServletRequest) throws Exception {
		return authService.refreshToken(httpServletRequest);
	}

}
