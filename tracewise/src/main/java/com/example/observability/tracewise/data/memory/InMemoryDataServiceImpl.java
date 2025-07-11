package com.example.observability.tracewise.data.memory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.observability.tracewise.data.IDataService;
import com.example.observability.tracewise.model.dto.AppDTO;
import com.example.observability.tracewise.model.dto.ClassDTO;
import com.example.observability.tracewise.model.dto.MethodDTO;
import com.example.observability.tracewise.model.dto.TestCaseDTO;
import com.example.observability.tracewise.model.dto.TestCaseResponse;
import com.example.observability.tracewise.model.dto.Trace;
import com.example.observability.tracewise.model.dto.TraceInfo;
import com.example.observability.tracewise.repository.TraceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InMemoryDataServiceImpl implements IDataService {

	@Autowired
	private TraceInfo traceInfo;	

    private final TraceRepository traceRepo;
    
    InMemoryDataServiceImpl( TraceRepository traceRepo)
    {
    	this.traceRepo=traceRepo;
    }

	@Override
	public TestCaseResponse findTraces(String traceId) {
		// Get data from db		
		List<Trace> traceList=(List<Trace>) traceRepo.findByTraceId(traceId);
		
		return convertToJson(traceList);
			
		//return this.traceInfo.getTraceMap().get(traceId);
	}
   
	 public static TestCaseResponse convertToJson(List<Trace> results) {
	        // Group by testcase
	        Map<String, List<Trace>> testcaseGroup = results.stream()
	            .collect(Collectors.groupingBy(Trace::getTestCaseId));

	        List<TestCaseDTO> testCaseList = testcaseGroup.entrySet().stream().map(testcaseEntry -> {
	            String testcase = testcaseEntry.getKey();
	            List<Trace> testcaseResults = testcaseEntry.getValue();

	            // Group by app
	            Map<String, List<Trace>> appGroup = testcaseResults.stream()
	                .collect(Collectors.groupingBy(Trace::getAppName));

	            List<AppDTO> apps = appGroup.entrySet().stream().map(appEntry -> {
	                String app = appEntry.getKey();
	                List<Trace> appResults = appEntry.getValue();

	                // Group by classname
	                Map<String, List<Trace>> classGroup = appResults.stream()
	                    .collect(Collectors.groupingBy(Trace::getClassName));

	                List<ClassDTO> classes = classGroup.entrySet().stream().map(classEntry -> {
	                    String classname = classEntry.getKey();
	                    List<MethodDTO> methods = classEntry.getValue().stream()
	                        .map(r -> new MethodDTO(r.getMethodName()))
	                        .collect(Collectors.toList());
	                    return new ClassDTO(classname, methods);
	                }).collect(Collectors.toList());

	                return new AppDTO(app, classes);
	            }).collect(Collectors.toList());

	            return new TestCaseDTO(testcase, apps);
	        }).collect(Collectors.toList());

	        return new TestCaseResponse(testCaseList);
	    }
	
	@Override
	public Collection fetchClasses(String className) {
		return this.traceInfo.getClassMap().get(className);
	}

}
