package com.example;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ContextSnapshot.Scope;
import io.micrometer.context.ContextSnapshotFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TraceWiseContextAware {

	public static Runnable of(Runnable task) {
		ContextSnapshot snapshot = ContextSnapshotFactory.builder().build().captureAll();
		return () -> {
			try (Scope scope = snapshot.setThreadLocals()) {
				task.run();
			}
		};
	}

}
