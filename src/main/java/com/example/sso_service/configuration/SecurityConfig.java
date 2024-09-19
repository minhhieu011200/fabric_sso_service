package com.example.sso_service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.sso_service.filter.JWTFilter;
import com.example.sso_service.service.AuthService;

import lombok.extern.log4j.Log4j2;

@Configuration
//@RequiredArgsConstructor
@EnableWebSecurity
@Log4j2
public class SecurityConfig {
	private String[] WHITE_LIST = { "/auth/**" };

	@Autowired
	@Lazy
	private AuthService authService;
	
	@Autowired
	@Lazy
	private JWTFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return webSecurity -> webSecurity.ignoring().requestMatchers("/actuator/**", "/v3/**", "/webjars/**",
				"/swagger-ui*/*swagger-initializer.js", "/swagger-ui*/**");
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(WHITE_LIST).permitAll()
						.anyRequest().authenticated())
				.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(provider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationProvider provider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(authService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
