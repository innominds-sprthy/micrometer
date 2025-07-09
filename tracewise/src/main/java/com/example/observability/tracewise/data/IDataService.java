package com.example.observability.tracewise.data;

import java.util.Collection;

public interface IDataService {

	Collection findTraces(String traceId);

}
