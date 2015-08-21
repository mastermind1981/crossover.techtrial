package org.pm.crossover.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UploadResponse {
	private final boolean success;
	private final String message;
	private final String id;
}
