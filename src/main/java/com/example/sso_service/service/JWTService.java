package com.example.sso_service.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2
@Service
@AllArgsConstructor
@Getter
public class JWTService {
	@Value("${jwt.key}")
	private String secretKey;

	@Value("${jwt.expire}")
	private long expirationTime; // 24 giờ

	public String generateToken(String data, Integer expirationTimeAdd) {
		return Jwts.builder().setSubject(data).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime + expirationTimeAdd))
				.signWith(getKey(secretKey), SignatureAlgorithm.HS256).compact();
	}

	private Key getKey(String secretKey) {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	public Claims getClaims(String token) {
		log.info("getClaims"+Jwts.parserBuilder().setSigningKey(getKey(secretKey)).build().parseClaimsJws(token));
		return Jwts.parserBuilder().setSigningKey(getKey(secretKey)).build().parseClaimsJws(token).getBody();

	}

	public Object getEncodeToken(String token) {
		return getClaims(token).getSubject();
	}

	public boolean isTokenExpired(String token) {
		log.info("getClaims"+getClaims(token).toString());
		return getClaims(token).getExpiration().before(new Date());
	}
	

    
}
