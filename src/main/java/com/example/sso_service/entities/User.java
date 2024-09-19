package com.example.sso_service.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "users")
public class User extends BaseEntities implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1340452102459251200L;
	@Column(unique = true)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String name;
	
	private EnumStatusUser status;
	@ManyToOne
	@JoinColumn(name = "auth_type_id",referencedColumnName = "id")
	private AuthType authType;
}
