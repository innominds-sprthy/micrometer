package com.example.observability.tracewise.data;

import com.example.observability.tracewise.model.dto.TestCaseResponse;

public interface IDataService {

	TestCaseResponse findTraces(String traceId);

}
