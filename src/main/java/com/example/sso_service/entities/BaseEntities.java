package com.example.sso_service.entities;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntities {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	@CreationTimestamp
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date createDate;

	@UpdateTimestamp
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date updateDate;
	
}
