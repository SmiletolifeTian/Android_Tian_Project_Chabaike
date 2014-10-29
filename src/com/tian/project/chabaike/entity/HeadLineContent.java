package com.tian.project.chabaike.entity;

import java.io.Serializable;

public class HeadLineContent implements Serializable {
	private static final long serialVersionUID = -790879193654262735L;
	private HeadLineContentData data;
	private String errorMessage;

	public HeadLineContentData getData() {
		return data;
	}

	public void setData(HeadLineContentData data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
