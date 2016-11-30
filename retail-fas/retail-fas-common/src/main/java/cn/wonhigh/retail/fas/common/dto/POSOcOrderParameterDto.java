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
public class POSOcOrderParameterDto extends  POSOcOrderParameterParentDto implements Serializable {

		private static final long serialVersionUID = -267711984809710701L;
		
		private String queryCondition;//自定义查询语句
  
		/**
	     * 销售门店编号
	     */
	    private String shopNo;
	    
		/**
	     * 商品编码
	     */
	    private String itemNo;
	    
	    private String  orderNo;
	    
	    private  String proType;
	    
	    private Map<String, Object> params;
   

		public String getShopNo() {
			return shopNo;
		}

		public void setShopNo(String shopNo) {
			this.shopNo = shopNo;
		}

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}
		
 		public String getItemNo() {
			return itemNo;
		}

 		public void setItemNo(String itemNo) {
			this.itemNo = itemNo;
		}

		public Map<String, Object> getParams() {
  			params = new HashMap<String, Object>();
  			params.put("queryCondition", this.queryCondition);
			params.put("shopNo", this.shopNo);
 			params.put("startOutDate", this.getStartOutDate());
			params.put("endOutDate", this.getEndOutDate());
 			params.put("businessTypeList", this.getBusinessTypeList());
 			params.put("statusList", this.getStatusList());
 			params.put("orderNoListForFAS", this.getOrderNoList());
 			params.put("brandNoListForFAS", this.getBrandNoList());
 			params.put("itemNo", this.getItemNo());
 			params.put("orderNo", this.getOrderNo());
 			params.put("proType", this.getProType());
 			
 			if("discountCode".equals(this.getProType())){
 				params.put("discountCode", "discountCode");
 			}else if("proNo".equals(this.getProType())){
 				params.put("proNo", "proNo");
 			}else if("billingCode".equals(this.getProType())){
 				params.put("billingCode", "billingCode");
 			}else if("proDate".equals(this.getProType())){
 				params.put("proDate", "proDate");
 			}else{
 				params.put("discountCode", "discountCode");
 			}
    		return params;
		}

		@Override
		public String toString() {
			return "OcOrderParameterDto [shopNo=" + shopNo + ", itemNo=" + itemNo + ", params=" + params + "]";
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getProType() {
			return proType;
		}

		public void setProType(String proType) {
			this.proType = proType;
		}

		public String getQueryCondition() {
			return queryCondition;
		}

		public void setQueryCondition(String queryCondition) {
			this.queryCondition = queryCondition;
		}
	 
}