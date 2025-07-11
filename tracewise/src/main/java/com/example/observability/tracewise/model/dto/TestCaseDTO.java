package com.example.observability.tracewise.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class TestCaseDTO {

	private String testCase;
	private List<AppDTO> apps;

	public TestCaseDTO(String testCase, List<AppDTO> apps) {
		this.testCase = testCase;
		this.apps = apps;
	}
}
