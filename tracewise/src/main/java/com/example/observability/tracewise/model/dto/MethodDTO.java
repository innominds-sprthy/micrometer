
package com.example.observability.tracewise.model.dto;

import lombok.Data;

@Data
public class MethodDTO {

	private String method;

	public MethodDTO(String method) {
		this.method = method;
	}

}
