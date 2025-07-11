package com.example.observability.tracewise.service;

import java.util.Collection;

import com.example.observability.tracewise.model.dto.TestCaseResponse;

public interface ITraceService {

	TestCaseResponse fetchTraces(String traceId);

	Collection fetchClasses(String className);

}
