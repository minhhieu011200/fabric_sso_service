package com.example.sso_service.entities;

import org.springframework.data.redis.core.RedisHash;

import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Data;

@Data
@RedisHash("AccessToken")
@Builder
public class AccessToken {
	@Id
	private String token;
	private String refreshToken;
	
	private String userName;

}
