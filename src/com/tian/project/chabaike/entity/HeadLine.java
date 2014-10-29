package com.tian.project.chabaike.entity;

import java.io.Serializable;
import java.util.List;

public class HeadLine implements Serializable {
	private static final long serialVersionUID = -6277308745748191619L;
	private List<HeadLineData> data;
	private String errorMessage;

	public List<HeadLineData> getData() {
		return data;
	}

	public void setData(List<HeadLineData> data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
