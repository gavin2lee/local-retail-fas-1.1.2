package cn.wonhigh.retail.fas.common.dto;


import java.io.Serializable;
import java.util.Date;
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
public class POSOcOrderParameterParentDto implements Serializable {

		private static final long serialVersionUID = -267711984809710701L;
  
   	    /**
	     * 销售开始日期
	     */
	    private Date startOutDate;
	    
	    /**
	     * 销售结束日期
	     */
	    private Date endOutDate;
  	    
 	    /**
	     * 订单业务类型,0-正常销售 1-跨店销售 2-商场团购 3-公司团购 4-员购 9-其他 默认为0
	     */
	    private List<Integer> businessTypeList;
	    
	    /**
	     * 单据状态
	     */
	    private List<Integer> statusList; 
	    
	    /**
	     * 订单编号
	     */
	    private List<String> orderNoList;
	    
	    /**
	     * 品牌编码
	     */
	    private List<String> brandNoList;
	    
	    private Map<String, Object> params;
  
	 

		public Date getStartOutDate() {
			return startOutDate;
		}

		public void setStartOutDate(Date startOutDate) {
			this.startOutDate = startOutDate;
		}

		public Date getEndOutDate() {
			return endOutDate;
		}

		public void setEndOutDate(Date endOutDate) {
			this.endOutDate = endOutDate;
		}

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}
   
		public List<Integer> getBusinessTypeList() {
			return businessTypeList;
		}

		public void setBusinessTypeList(List<Integer> businessTypeList) {
			this.businessTypeList = businessTypeList;
		}
          
	    	
		public List<Integer> getStatusList() {
			return statusList;
		}

		public void setStatusList(List<Integer> statusList) {
			this.statusList = statusList;
		}
  
		public List<String> getOrderNoList() {
			return orderNoList;
		}

 		public void setOrderNoList(List<String> orderNoList) {
			this.orderNoList = orderNoList;
		}


		public List<String> getBrandNoList() {
			return brandNoList;
		}

		public void setBrandNoList(List<String> brandNoList) {
			this.brandNoList = brandNoList;
		}
		
		public Map<String, Object> getParams() {
  			params = new HashMap<String, Object>();
  			params.put("startOutDate", this.startOutDate);
			params.put("endOutDate", this.endOutDate);
 			params.put("businessTypeList", this.businessTypeList);
 			params.put("statusList", this.statusList);
 			params.put("orderNoListForFAS", this.orderNoList);
 			params.put("brandNoListForFAS", this.brandNoList);
    		return params;
		}

		@Override
		public String toString() {
			return "OcOrderParameterParentDto [startOutDate=" + startOutDate + ", endOutDate=" + endOutDate
					+ ", businessTypeList=" + businessTypeList + ", statusList=" + statusList + ", orderNoList="
					+ orderNoList + ", brandNoList=" + brandNoList + ", params=" + params + "]";
		}
		
	 
}