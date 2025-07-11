package com.example.observability.tracewise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.observability.tracewise.model.dto.Trace;
public interface TraceRepository extends JpaRepository<Trace, Long> {
   List<Trace> findByTraceId(String traceId);
}
