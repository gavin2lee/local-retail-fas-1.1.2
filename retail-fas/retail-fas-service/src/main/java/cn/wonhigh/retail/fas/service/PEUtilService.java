package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.ContractDiscount;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-24 14:59:22
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface PEUtilService  {
	
	public BigDecimal getBuyBalancePrice(BillBuyBalance balance)throws ServiceException;
	
	public BigDecimal getSaleBalancePrice(BillSaleBalance balance)throws ServiceException;
	
	public Date getDueDate(String billNo, int billType)throws ServiceException;
	
	public BigDecimal selectTagPriceByItemNo(String itemNo)throws ServiceException;
	
	public ContractDiscount selectLastContractDiscount(Map<String, Object> params)throws ServiceException;
	
}