package com.tian.project.chabaike.entity;

import java.io.Serializable;

public class ContentInfo implements Serializable {
	private static final long serialVersionUID = -790879193654262735L;
	private ContentInfoData data;
	private String errorMessage;

	public ContentInfoData getData() {
		return data;
	}

	public void setData(ContentInfoData data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
