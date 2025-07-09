package com.example.observability.tracewise.logprocessor.scheduler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.observability.tracewise.model.dto.TraceInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogProcessor {

	@Autowired
	private TraceInfo traceInfo;

	@Value("${tracewise.inmemory}")
	private String inMemory;

	@Value("${logs.location}")
	private String logsDir;

	@Value("${traceId.start}")
	private String traceIdStart;

	@Value("${traceId.end}")
	private String traceIdEnd;

	@Value("${traceId.delimiter}")
	private String traceIdDelimiter;

	@Scheduled(cron = "${trace.logprocessor.scheduler.cron}")
	public void runTask() {
		log.info("Log Processing Started");
		processLogs();
		log.info("Log Processing Done");
	}

	private Object processLogs() {
		List<String> fileNames = new LinkedList<>();
		File dir = new File(logsDir);
		findFiles(fileNames, dir);
		log.debug("File names :: {}", fileNames);
		fileNames.parallelStream().forEach(fileName -> processFile(fileName));
		return null;
	}

	private Object processFile(String fileName) {
		log.debug("Processing file :: {}", fileName);
		Map<String, String> processDone = new HashMap<>();
		processDone.put(fileName, fileName);
		List<String> lines = null;
		try {
			lines = Files.lines(Paths.get(fileName)).filter(line -> line.contains(traceIdStart))
					.collect(Collectors.toList());
		} catch (IOException e) {
			log.error("Exception while finding trace lines in logs {}:: {}", fileName, e);
		}
		if (lines != null) {
			lines.parallelStream().forEach(line -> {
				extractTraceInfo(line);
			});
		}
		processDone.remove(fileName);
		if (processDone.isEmpty()) {
			log.debug("Trace Map :: {}", this.traceInfo.getTraceMap());
		}
		return null;
	}

	private void extractTraceInfo(String line) {
		String tracedText = line.substring(line.indexOf(traceIdStart) + traceIdStart.length(),
				line.indexOf(traceIdEnd));
		log.debug("Traced Text :: {}", tracedText);
		String traceArr[] = tracedText.split(traceIdDelimiter);
		if (traceArr != null) {
			String serviceName = "";
			String className = "";
			String methodName = "";
			String traceId = "";
			String spanId = "";
			int length = traceArr.length;
			if (length > 0)
				serviceName = traceArr[0];
			if (length > 1)
				className = traceArr[1];
			if (length > 2)
				methodName = traceArr[2];
			if (length > 3)
				traceId = traceArr[3];
			if (length > 4)
				spanId = traceArr[4];

			if (traceId != null) {
				Set<String> traces = this.traceInfo.getTraceMap().get(traceId);
				if (traces == null) {
					traces = new LinkedHashSet<>();
					this.traceInfo.getTraceMap().put(traceId, traces);
				}
				StringBuilder sb = new StringBuilder();
				sb.append(serviceName).append("-").append(className).append("-").append(methodName);
				traces.add(sb.toString());
			}
		}
	}

	private void findFiles(List<String> fileNames, File dir) {
		if (dir.isDirectory()) {
			File files[] = dir.listFiles();
			Arrays.asList(files).stream().forEach(file -> {
				findFiles(fileNames, file);
			});
		} else {
			isLogFile(dir, fileNames);
		}
	}

	private void isLogFile(File file, List<String> fileNames) {
		String name = file.getName();
		String extension = name.substring(name.lastIndexOf('.') + 1);
		if ("log".equals(extension)) {
			fileNames.add(file.getAbsolutePath());
		}
	}
}