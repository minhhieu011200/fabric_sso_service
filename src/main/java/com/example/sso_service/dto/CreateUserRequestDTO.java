package com.example.sso_service.dto;

import com.example.sso_service.entities.AuthType;
import com.example.sso_service.entities.EnumStatusUser;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
	private String email;
	private String password;
	private String name;
	private Long authType;
	private EnumStatusUser status;
}
