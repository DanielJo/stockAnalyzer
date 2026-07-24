package io.github.danieljo.stockanalyzer.rest;

import io.github.danieljo.stockanalyzer.job.AnalysisJob;
import io.github.danieljo.stockanalyzer.job.JobStatus;

public record AnalysisJobResponse(String id, JobStatus status, String errorMessage) {

	public static AnalysisJobResponse from(AnalysisJob job) {
		return new AnalysisJobResponse(job.getId(), job.getStatus(), job.getErrorMessage());
	}
}
