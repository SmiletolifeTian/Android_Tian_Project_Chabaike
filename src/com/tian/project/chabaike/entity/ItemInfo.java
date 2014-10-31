package com.tian.project.chabaike.entity;

import java.io.Serializable;
import java.util.List;

public class ItemInfo implements Serializable {
	private static final long serialVersionUID = -6277308745748191619L;
	private List<ItemInfoData> data;
	private String errorMessage;

	public List<ItemInfoData> getData() {
		return data;
	}

	public void setData(List<ItemInfoData> data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
