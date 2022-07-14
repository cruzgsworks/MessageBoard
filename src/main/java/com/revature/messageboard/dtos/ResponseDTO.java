package com.revature.messageboard.dtos;

import java.util.ArrayList;

public class ResponseDTO {

	private int status;
	private String message;
	private boolean success;
	private ArrayList<?> results;

	public ResponseDTO(int status, String message, boolean success, ArrayList<?> results) {
		super();
		this.status = status;
		this.message = message;
		this.success = success;
		this.results = results;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ArrayList<?> getResults() {
		return results;
	}

	public void setResults(ArrayList<?> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + ", success=" + success + ", results=" + results
				+ ", getStatus()=" + getStatus() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess()
				+ ", getResults()=" + getResults() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
