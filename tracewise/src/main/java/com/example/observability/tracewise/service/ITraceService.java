package com.example.observability.tracewise.service;

import java.util.Collection;

public interface ITraceService {

	Collection fetchTraces(String traceId);

}
