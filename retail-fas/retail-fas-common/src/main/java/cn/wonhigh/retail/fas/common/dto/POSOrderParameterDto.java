package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  销售参数对象父类
 * @author zhang.lh
 * @date  2014-10-14 15:45:57
 * @version 1.0.0
 * @copyright (C) 2014 Wonhigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * 
 */
public class POSOrderParameterDto implements Serializable {

		private static final long serialVersionUID = -267711984809710701L;
  
		/**
	     * 销售门店编号
	     */
	    private String shopNo;
	    
  	    /**
	     * 开始日期
	     */
	    private String startDate;
	    
	    /**
	     * 结束日期
	     */
	    private String endDate;
  	    
	    /**
	     * 单据状态
	     */
	    private List<Integer> statusList; 
	    
	    private Map<String, Object> params;
  
 
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

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}
 
   		/**
		 * @return the shopNo
		 */
		public String getShopNo() {
			return shopNo;
		}

		/**
		 * @param shopNo the shopNo to set
		 */
		public void setShopNo(String shopNo) {
			this.shopNo = shopNo;
		}
        
		public List<Integer> getStatusList() {
			return statusList;
		}

		public void setStatusList(List<Integer> statusList) {
			this.statusList = statusList;
		}

		public Map<String, Object> getParams() {
  			params = new HashMap<String, Object>();
			params.put("shopNo", this.shopNo);
 			params.put("startDate", this.startDate);
			params.put("endDate", this.endDate);
			params.put("statusList", this.statusList);
    	    return params;
		}
	 
}