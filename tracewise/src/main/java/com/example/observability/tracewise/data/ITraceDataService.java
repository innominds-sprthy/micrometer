package com.example.observability.tracewise.data;

import java.util.Collection;

import com.example.observability.tracewise.model.dto.TestCaseResponse;
import com.example.observability.tracewise.model.dto.Trace;

public interface ITraceDataService {

	TestCaseResponse findTraces(String traceId);

	Collection fetchClasses(String className);

	void save(Trace trace);

}
