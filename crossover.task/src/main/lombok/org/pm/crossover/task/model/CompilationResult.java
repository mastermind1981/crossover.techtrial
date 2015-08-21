package org.pm.crossover.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompilationResult {
	private final int exitCode;
	private final String output;
}
