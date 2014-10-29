package com.tian.project.chabaike.entity;

import java.io.Serializable;
import java.util.List;

public class HomePage implements Serializable {
	private static final long serialVersionUID = 8792118163037439512L;
	private List<HomePageData> data;
	private String errorMessage;

	public List<HomePageData> getData() {
		return data;
	}

	public void setData(List<HomePageData> data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
