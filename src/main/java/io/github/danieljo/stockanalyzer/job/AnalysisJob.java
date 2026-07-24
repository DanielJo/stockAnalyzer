package io.github.danieljo.stockanalyzer.job;

import java.time.Instant;
import java.util.UUID;

/**
 * State of one asynchronous analysis run, submitted via the REST API. Fields written from the
 * background executor thread and read from HTTP request threads are {@code volatile} so status
 * changes become visible without needing external locking.
 */
public class AnalysisJob {

	private final String id = UUID.randomUUID().toString();
	private final Instant createdAt = Instant.now();
	private volatile JobStatus status = JobStatus.PENDING;
	private volatile String result;
	private volatile String errorMessage;

	public String getId() {
		return id;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public JobStatus getStatus() {
		return status;
	}

	public String getResult() {
		return result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void markRunning() {
		this.status = JobStatus.RUNNING;
	}

	public void complete(String result) {
		this.result = result;
		this.status = JobStatus.DONE;
	}

	public void fail(String errorMessage) {
		this.errorMessage = errorMessage;
		this.status = JobStatus.FAILED;
	}
}
