package com.example.sso_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sso_service.dto.CreateUserRequestDTO;
import com.example.sso_service.dto.LoginRequestDTO;
import com.example.sso_service.dto.LoginResponseDTO;
import com.example.sso_service.entities.AccessToken;
import com.example.sso_service.entities.AuthType;
import com.example.sso_service.entities.CustomUserDetails;
import com.example.sso_service.entities.RefreshToken;
import com.example.sso_service.entities.User;
import com.example.sso_service.repository.AccessTokenRepository;
import com.example.sso_service.repository.AuthTypeRepository;
import com.example.sso_service.repository.RefreshTokenRepository;
import com.example.sso_service.repository.UserRepository;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import util.JsonUtil;

@Service
@Log4j2
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthTypeRepository authTypeRepository;

	@Autowired
	private AccessTokenRepository accessTokenRepository;
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	@Lazy
	private JWTService jwtUtil;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	public User createUser(CreateUserRequestDTO createUserRequestDTO) {
		String encodedPassword = passwordEncoder.encode(createUserRequestDTO.getPassword());

		User user = new User();
		AuthType authType = authTypeRepository.findById(createUserRequestDTO.getAuthType()).orElse(null);

		return userRepository.save(user.builder().email(createUserRequestDTO.getEmail()).password(encodedPassword)
				.name(createUserRequestDTO.getName()).status(createUserRequestDTO.getStatus()).authType(authType)
				.build());
	}

	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

		User user = userRepository.findByEmail(loginRequestDTO.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Username or Password is incorrect"));

		String userJsonString = JsonUtil.convertObjectToJson(user);
		String accessToken = jwtUtil.generateToken(userJsonString, 0);
		String refreshToken = jwtUtil.generateToken(userJsonString, 64000);

		accessTokenRepository
				.save(AccessToken.builder().userName(loginRequestDTO.getUsername()).token(accessToken).refreshToken(refreshToken).build());
		refreshTokenRepository.save(RefreshToken.builder().userName(loginRequestDTO.getUsername()).token(refreshToken)
				.accessToken(accessToken).build());

		return LoginResponseDTO.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

	public LoginResponseDTO refreshToken(HttpServletRequest httpServletRequest) throws Exception {
		String refreshToken = httpServletRequest.getHeader("x-refresh-token");


		if (StringUtil.isNullOrEmpty(refreshToken)) {
			throw new Exception("Invalid token");
		}

		if (jwtUtil.isTokenExpired(refreshToken)) {
			throw new Exception("Invalid token");
		}
		
		RefreshToken findRefreshToken = refreshTokenRepository.findByToken(refreshToken)
				.orElseThrow(() -> new Exception("Invalid token"));
	
		log.info("22222222222222222222222222test",findRefreshToken);

		String encodeTokenString = (String) jwtUtil.getEncodeToken(refreshToken);

		String accessToken = jwtUtil.generateToken(encodeTokenString, 0);

		accessTokenRepository
				.save(AccessToken.builder().userName(findRefreshToken.getUserName()).token(accessToken).build());
		refreshTokenRepository.save(RefreshToken.builder().userName(findRefreshToken.getUserName()).token(refreshToken)
				.accessToken(accessToken).build());
		
		return LoginResponseDTO.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}

}
