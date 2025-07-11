package com.example.observability.tracewise.service;

import com.example.observability.tracewise.model.dto.TestCaseResponse;

public interface ITraceService {

	TestCaseResponse fetchTraces(String traceId);

}
