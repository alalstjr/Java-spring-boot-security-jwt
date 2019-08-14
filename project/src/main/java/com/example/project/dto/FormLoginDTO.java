package com.example.project.dto;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormLoginDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String userId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String password;
}
