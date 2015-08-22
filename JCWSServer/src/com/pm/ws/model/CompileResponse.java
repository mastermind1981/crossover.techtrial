package com.pm.ws.model;

import java.io.Serializable;

/**
 * Model class for compilation action response
 *
 */
public class CompileResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7579229974223082193L;
	private int exitCode;
	private String output;

	/**
	 * 
	 */
	public CompileResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param exitCode
	 * @param output
	 */
	public CompileResponse(int exitCode, String output) {
		super();
		this.exitCode = exitCode;
		this.output = output;
	}

	/**
	 * @return the exitCode
	 */
	public int getExitCode() {
		return exitCode;
	}

	/**
	 * @param exitCode
	 *            the exitCode to set
	 */
	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * @param output
	 *            the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
	}

}
