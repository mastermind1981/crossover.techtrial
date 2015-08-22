package com.pm.ws.model;

import java.io.Serializable;

/**
 * Model class for Upload action response
 */
public class UploadResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6074024465131133432L;
	private boolean success;
	private String message;
	private String fileId;

	/**
	 * 
	 */
	public UploadResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param success
	 * @param message
	 * @param fileId
	 */
	public UploadResponse(boolean success, String message, String fileId) {
		super();
		this.success = success;
		this.message = message;
		this.fileId = fileId;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
