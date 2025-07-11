package com.example.observability.tracewise.data;

import java.util.Collection;

import com.example.observability.tracewise.model.dto.TestCaseResponse;

public interface IDataService {

	TestCaseResponse findTraces(String traceId);

	Collection fetchClasses(String className);

}
