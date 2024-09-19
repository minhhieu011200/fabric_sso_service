package com.example.sso_service.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.sso_service.service.AuthService;
import com.example.sso_service.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JWTFilter extends OncePerRequestFilter {
	@Autowired
	@Lazy
	private JWTService jwtService;

	@Autowired
	private AuthService authService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");
		log.info("22222222222222222222" + token);

		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7); // Loại bỏ "Bearer " từ token

			String username = (String) jwtService.getEncodeToken(token);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//				if (jwtUtil.validateToken(token, username)) {
				UserDetails userDetails = authService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				request.setAttribute("user", userDetails);
				SecurityContextHolder.getContext().setAuthentication(authentication);
//				}
			}
		}

		filterChain.doFilter(request, response);

	}

//    public JWTFilter(JWTUtil jwtUtil, AuthService authService) {
//        this.jwtUtil = jwtUtil;
//        this.authService = authService;
//    }
//    

}
