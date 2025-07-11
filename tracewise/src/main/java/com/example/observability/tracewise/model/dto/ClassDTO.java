package com.example.observability.tracewise.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClassDTO {

	private String classname;
	private List<MethodDTO> methods;

	public ClassDTO(String classname, List<MethodDTO> methods) {
		this.classname = classname;
		this.methods = methods;
	}

}
