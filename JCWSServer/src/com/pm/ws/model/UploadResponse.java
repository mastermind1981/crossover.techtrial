package com.pm.ws.model;

import java.io.Serializable;

public class UploadResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6074024465131133432L;
	private boolean success;
	private String message;
	private String fileId;

	public UploadResponse() {
		super();
	}

	public UploadResponse(boolean success, String message, String id) {
		super();
		this.success = success;
		this.message = message;
		this.fileId = id;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String id) {
		this.fileId = id;
	}
}
