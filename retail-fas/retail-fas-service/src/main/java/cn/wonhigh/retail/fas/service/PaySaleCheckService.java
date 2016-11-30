package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PaySaleCheck;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2015-08-18 10:55:57
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface PaySaleCheckService extends BaseCrudService {
	/**
	 * 根据查询条件，查询支付方式销售核对总数
	 * 
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public PaySaleCheck findPaySaleCheckCount(Map<String, Object> params)
			throws ManagerException;

	/**
	 * 根据查询条件，查询支付方式销售核对明细
	 * 
	 * @param params
	 * @return
	 * @author wangsm
	 */
	List<PaySaleCheck> findPaySaleCheckList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException;
	
	/**
	 * 修改手续费
	 * @param id
	 * @param poundage
	 */
	public void updateData(String id,BigDecimal poundage, BigDecimal rate);
}