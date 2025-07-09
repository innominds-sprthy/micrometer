package com.example.observability.tracewise.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.observability.tracewise.service.ITraceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/traces")
@Slf4j
public class TraceWiseController {

	private final ITraceService traceService;

	public TraceWiseController(ITraceService traceService) {
		this.traceService = traceService;
	}

	@GetMapping("/{traceId}")
	public ResponseEntity<Collection> placeOrder(@PathVariable("traceId") String traceId) {
		Collection traces = traceService.fetchTraces(traceId);
		return ResponseEntity.ok(traces);
	}

}
