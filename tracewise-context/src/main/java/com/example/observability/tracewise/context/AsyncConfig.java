package com.example.observability.tracewise.context;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ContextSnapshot.Scope;
import io.micrometer.context.ContextSnapshotFactory;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setThreadNamePrefix("async-");
		executor.initialize();

		return task -> {
			ContextSnapshot snapshot = ContextSnapshotFactory.builder().build().captureAll();
			executor.execute(() -> {
				try (Scope scope = snapshot.setThreadLocals()) {
					task.run();
				}
			});
		};
	}
}