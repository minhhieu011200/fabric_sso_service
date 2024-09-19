package com.example.sso_service.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@RedisHash("RefreshToken")
public class RefreshToken{
	
	private String userName;
	@Id
	private String token;
	private String accessToken;
}
