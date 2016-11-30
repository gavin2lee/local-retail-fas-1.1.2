package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.model.PaySaleCheck;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-17 18:00:37
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface PaySaleCheckManager extends BaseCrudManager {
	/**
	 * 根据查询条件，查询支付方式销售核对数据
	 * @param page
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public List<PaySaleCheck> findPaySaleCheckList(SimplePage page,String orderByField,String orderBy,Map<String, Object> params) throws ManagerException;
	
	/**
	 * 根据查询条件，查询支付方式销售核对总数
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public PaySaleCheck findPaySaleCheckCount(Map<String, Object> params) throws ManagerException;
	
	public void updateData(List<PaySaleCheck> paySaleCheckList,String type) throws ServiceException;
}