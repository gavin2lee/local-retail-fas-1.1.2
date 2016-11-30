package cn.wonhigh.retail.fas.manager.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("tagPriceArgument")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TagPriceArgument implements ITaskArgs {
	public List<String> getItemNos() {
		return itemNos;
	}

	public void setItemNos(List<String> itemNos) {
		this.itemNos = itemNos;
	}

	private String company;
	private String supplier;
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Map<String, Object> toMap(){
		if(null == map){
			map = new HashMap<String, Object>();
		}
		map.put("multiCompanyNo", this.company);
		map.put("receiveDateStart", this.startDate);
		map.put("receiveDateEnd", this.endDate);
		
		return map;
	}

	private String startDate;
	private String endDate;
	private List<String> itemNos;
	private Map<String, Object> map;
}
