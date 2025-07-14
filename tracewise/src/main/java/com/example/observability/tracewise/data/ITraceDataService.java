package com.example.observability.tracewise.data;

import java.util.Collection;

import com.example.observability.tracewise.model.dto.Trace;
import com.example.observability.tracewise.model.dto.TraceDTO;

public interface ITraceDataService {

	TraceDTO findTraces(String traceId);

	Collection fetchClasses(String className);

	void save(Trace trace);

}
