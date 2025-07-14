package com.example.observability.tracewise.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.example.observability.tracewise.data.ITraceDataService;
import com.example.observability.tracewise.model.dto.TestCaseResponse;
import com.example.observability.tracewise.service.ITraceService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraceServiceImpl implements ITraceService {

	private ITraceDataService dataService;

	public TraceServiceImpl(ITraceDataService dataService) {
		this.dataService = dataService;
	}

	@Override
	public TestCaseResponse fetchTraces(String traceId) {
		return this.dataService.findTraces(traceId);
	}

	@Override
	public Collection fetchClasses(String className) {
		return this.dataService.fetchClasses(className);
	}

}
