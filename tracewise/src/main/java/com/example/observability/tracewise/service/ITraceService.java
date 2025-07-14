package com.example.observability.tracewise.service;

import java.util.Collection;

import com.example.observability.tracewise.model.dto.TraceDTO;

public interface ITraceService {

	TraceDTO fetchTraces(String traceId);

	Collection fetchClasses(String className);

}
