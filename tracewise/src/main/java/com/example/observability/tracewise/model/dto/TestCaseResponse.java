package com.example.observability.tracewise.model.dto;

import java.util.List;

import lombok.Data;
@Data
public class TestCaseResponse {

	    private List<TestCaseDTO> testCase;
	    public TestCaseResponse(List<TestCaseDTO> testCase) {
	        this.testCase = testCase;
	    }
	
}
