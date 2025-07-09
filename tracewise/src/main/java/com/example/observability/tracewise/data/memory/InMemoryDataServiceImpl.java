package com.example.observability.tracewise.data.memory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.observability.tracewise.data.IDataService;
import com.example.observability.tracewise.model.dto.TraceInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InMemoryDataServiceImpl implements IDataService {

	@Autowired
	private TraceInfo traceInfo;

	@Override
	public Collection findTraces(String traceId) {
		return this.traceInfo.getTraceMap().get(traceId);
	}

}
