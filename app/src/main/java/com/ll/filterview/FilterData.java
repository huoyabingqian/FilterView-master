package com.ll.filterview;

import java.io.Serializable;
import java.util.List;


public class FilterData implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> dateData;
    private List<String> typeData;
    private List<String> payData;
	public List<String> getDateData() {
		return dateData;
	}
	public void setDateData(List<String> dateData) {
		this.dateData = dateData;
	}
	public List<String> getTypeData() {
		return typeData;
	}
	public void setTypeData(List<String> typeData) {
		this.typeData = typeData;
	}
	public List<String> getPayData() {
		return payData;
	}
	public void setPayData(List<String> payData) {
		this.payData = payData;
	}

}
