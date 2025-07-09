package com.example.observability.tracewise.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.example.observability.tracewise.data.IDataService;
import com.example.observability.tracewise.service.ITraceService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraceServiceImpl implements ITraceService {

	private final IDataService dataService;

	public TraceServiceImpl(IDataService dataService) {
		this.dataService = dataService;
	}

	@Override
	public Collection fetchTraces(String traceId) {
		return this.dataService.findTraces(traceId);
	}

}
