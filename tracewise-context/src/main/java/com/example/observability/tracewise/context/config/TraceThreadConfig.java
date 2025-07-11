package com.example.observability.tracewise.context.config;

import java.time.Duration;
import java.util.concurrent.Executor;

import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.core.task.support.ContextPropagatingTaskDecorator;

@Configuration
public class TraceThreadConfig {

	@Bean
	public TaskDecorator taskDecorator() {
		return new ContextPropagatingTaskDecorator();
	}

	@Bean
	public Executor taskExecutor(ThreadPoolTaskExecutorBuilder builder, TaskDecorator taskDecorator) {
		return builder.taskDecorator(taskDecorator).corePoolSize(10).maxPoolSize(50).queueCapacity(100)
				.threadNamePrefix("tracewise-").allowCoreThreadTimeOut(true).keepAlive(Duration.ofSeconds(120)).build();
	}

}
