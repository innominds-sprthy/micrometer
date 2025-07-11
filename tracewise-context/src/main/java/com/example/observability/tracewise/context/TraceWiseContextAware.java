package com.example.observability.tracewise.context;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ContextSnapshot.Scope;
import io.micrometer.context.ContextSnapshotFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TraceWiseContextAware {

	// Runnable object
	public static Runnable of(Runnable task) {
		ContextSnapshot snapshot = ContextSnapshotFactory.builder().build().captureAll();
		return () -> {
			try (Scope scope = snapshot.setThreadLocals()) {
				task.run();
			}
		};
	}

	// CompletableFuture object with only required action i.e. executable action
	public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
		ContextSnapshot snapshot = ContextSnapshotFactory.builder().build().captureAll();
		return CompletableFuture.supplyAsync(() -> {
			try (Scope scope = snapshot.setThreadLocals()) {
				return supplier.get();
			}
		});
	}

	// CompletableFuture object with executor
	public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, Executor executor) {
		ContextSnapshot snapshot = ContextSnapshotFactory.builder().build().captureAll();
		return CompletableFuture.supplyAsync(() -> {
			try (Scope scope = snapshot.setThreadLocals()) {
				return supplier.get();
			}
		}, executor);
	}

	// CompletableFuture object with runnable object and executor
	public static CompletableFuture<Void> runAsync(Runnable task, Executor executor) {
		ContextSnapshot snapshot = ContextSnapshotFactory.builder().build().captureAll();
		return CompletableFuture.runAsync(() -> {
			try (Scope scope = snapshot.setThreadLocals()) {
				task.run();
			}
		}, executor);
	}

	public static TraceWiseContext setTraceContext() {
		return new TraceWiseContext(ContextSnapshotFactory.builder().build().captureAll());
	}

	public static class TraceWiseContext {
		private ContextSnapshot snapshot = null;
		private Scope scope = null;

		private TraceWiseContext(ContextSnapshot snapshot) {
			this.snapshot = snapshot;
		}

		public void set() {
			scope = this.snapshot.setThreadLocals();
		}

		public void clear() {
			this.scope.close();
		}
	}

}
