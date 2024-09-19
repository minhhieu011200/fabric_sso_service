package com.example.sso_service.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuthType extends BaseEntities implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4952818695338120879L;
	@Column(unique = true)
	private String name;

}
