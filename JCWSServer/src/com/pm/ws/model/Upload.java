package com.pm.ws.model;

import java.io.Serializable;

/**
 * Model class for Upload request
 *
 */
public class Upload implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8645185733081258145L;
	private byte[] file;

	/**
	 * 
	 */
	public Upload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param file
	 */
	public Upload(byte[] file) {
		super();
		this.file = file;
	}

	/**
	 * @return the file
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}

}
