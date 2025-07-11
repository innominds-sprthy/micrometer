package com.example.observability.tracewise.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Test_Case_Info")
public class Trace {

    @Column(name = "trace_id")
    private String traceId;
    
    @Column(name="span_id")
    private String spanId;
    
    @Column(name="class_name")
    private String className;
    
    @Column(name="app_name")
    private String appName;
    
    @Column(name="method_name")
    private String methodName;
    
    @Column(name="testcase_id")
    private String testCaseId;
    
    public Trace()
    {
    	
    }
    

	public Trace(String traceId, String spanId, String className, String appName, String methodName,
			String testCaseId) {
		super();
		this.traceId = traceId;
		this.spanId = spanId;
		this.className = className;
		this.appName = appName;
		this.methodName = methodName;
		this.testCaseId = testCaseId;
	}
}