package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.util.List;

public class PagingDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8761244085970824416L;

	
	private Integer totals;
	
	private List<T> results;

	public Integer getTotals() {
		return totals;
	}

	public List<T> getResults() {
		return results;
	}

	public void setTotals(Integer totals) {
		this.totals = totals;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}
	
}
