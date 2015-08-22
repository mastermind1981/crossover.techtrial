package com.pm.ws.model;

import java.io.Serializable;

public class CompileResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7579229974223082193L;
	private int exitCode;
	private String output;

	public CompileResponse(int exitCode, String output) {
		super();
		this.exitCode = exitCode;
		this.output = output;
	}

	public CompileResponse() {
		super();
	}

	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
