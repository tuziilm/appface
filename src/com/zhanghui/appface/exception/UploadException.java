package com.zhanghui.appface.exception;

public class UploadException extends Exception {
	private static final long serialVersionUID = 705805289428243892L;

	public UploadException() {
		super();
	}

	public UploadException(String message, Throwable cause) {
		super(message, cause);
	}

	public UploadException(String message) {
		super(message);
	}

	public UploadException(Throwable cause) {
		super(cause);
	}
}
