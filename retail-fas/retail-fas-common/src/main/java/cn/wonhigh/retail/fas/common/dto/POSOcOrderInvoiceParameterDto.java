package cn.wonhigh.retail.fas.common.dto;

import java.io.Serializable;

/**
 *  发票号、日期写入销售订单对象
 * @author zhang.lh
 * @date  2014-10-14 15:45:57
 * @version 1.0.0
 * @copyright (C) 2014 Wonhigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * 
 */
public class POSOcOrderInvoiceParameterDto implements Serializable {

		private static final long serialVersionUID = -267711984809710701L;
		
 		//订单号
	    private String orderNo;
	    
	    //订单类型，0-正常销售 1-换货 2-退货
	    private int orderBillType;
	    
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public int getOrderBillType() {
			return orderBillType;
		}
		public void setOrderBillType(int orderBillType) {
			this.orderBillType = orderBillType;
		}
		
		@Override
		public String toString() {
			return "OcOrderInvoiceParameterDto [orderNo=" + orderNo + ", orderBillType=" + orderBillType + "]";
		}
	    
 	 
}