package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.DepositCashCumulativeDifference;
import cn.wonhigh.retail.fas.common.model.IndependShopDayReport;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2015-04-22 09:29:45
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
public interface DepositCashCumulativeDifferenceService extends BaseCrudService {
	
	public BigDecimal selectCumulativeDifferenceByShopNo(String shopNo) throws ServiceException;
	
	/**
	 * 除现金外根据支付方式查询不同支付方式的金额
	 */
	public List<IndependShopDayReport> findByPayName(SimplePage page,String orderByField, String orderBy, Map<String, Object> params);
	
	/**
	 * 现金从pos存现核对中财务已确认取数据
	 */
	public List<IndependShopDayReport> findByPayNameFromPOS(SimplePage page,String orderByField, String orderBy, Map<String, Object> params);
	
	/**
	 * 其他支付方式的金额
	 */
	public List<IndependShopDayReport> findByPayNameOther(SimplePage page,String orderByField, String orderBy, Map<String, Object> params);
	
}