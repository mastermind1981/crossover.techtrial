package com.pm.ws.model;

import java.io.Serializable;

public class Upload implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8645185733081258145L;
	private byte[] file;

	public Upload() {
		super();
	}

	public Upload(byte[] file) {
		super();
		this.file = file;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

}
