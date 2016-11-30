package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceInfo;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-30 11:19:51
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
public interface BillBalanceInvoiceInfoMapper extends BaseCrudMapper {
	/**
	 * @param string
	 */
	void deleteInvoiceInfo(String billNo);
	
	/**
	 * 根据结算公司和客户查询发票信息
	 * @param page
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceInfo> selectByCustomerNo(@Param("page") SimplePage page, @Param("params")Map<String,Object> params);
	
	/**
	 * 根据结算公司和客户查询发票数量
	 * @param params
	 * @return
	 */
	public int getCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 根据发票号获取发票信息
	 * @param invoiceNo
	 * @return
	 */
	public BillBalanceInvoiceInfo getInvoiceAmount(String invoiceNo);
}