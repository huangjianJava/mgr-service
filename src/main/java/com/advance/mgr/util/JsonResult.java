package com.advance.mgr.util;

import java.util.Date;

public class JsonResult {

	private int success;

	private int statusCode;

	private String message;

	private Object data;
    
	private Date timestamp;
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getSuccess() {
		return success;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}
 
}