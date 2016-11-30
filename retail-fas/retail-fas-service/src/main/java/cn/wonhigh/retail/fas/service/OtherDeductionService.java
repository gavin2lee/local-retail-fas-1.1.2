package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-21 14:30:24
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
public interface OtherDeductionService extends BaseCrudService {

	List<OtherDeduction> findFooter(Map<String, Object> params)throws ServiceException;
	
	/**
	 * 回写结算单号
	 * @param bill
	 * @return
	 */
	void updateBalanceNo(BillBalance obj) throws ServiceException;
	

	/**
	 * 清除结算单号
	 * @param bill
	 * @return
	 */
	void clearBalanceNo(BillBalance obj) throws ServiceException;
	
	/**
	 * 结算查询扣项,返利,其他费用
	 * @param bill
	 * @return
	 */
	OtherDeduction findOtherDeductionBanlance(Map<String, Object> params) throws ServiceException;
}