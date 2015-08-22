package com.pm.ws.model;

/**
 * JCService Exception
 *
 */
public class JCException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1414895892517587554L;

	/**
	 * 
	 */
	public JCException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public JCException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public JCException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public JCException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public JCException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
