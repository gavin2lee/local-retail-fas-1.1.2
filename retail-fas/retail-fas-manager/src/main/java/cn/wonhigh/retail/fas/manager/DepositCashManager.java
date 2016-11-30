package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.DepositCheck;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 13:51:56
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
public interface DepositCashManager extends BaseCrudManager {
	/**
	 * 根据查询条件，查询pos 存现核对明细存入金额总数和期间销售金额总数
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public DepositCash findDetailCount(Map<String, Object> params);
	
	/**
	 * 根据查询条件，查询pos 存现核对明细
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public List<DepositCash> findDetail(SimplePage page,String orderByField,String orderBy,Map<String, Object> params);
	
	public void updateData(List<DepositCash> depositCashList) throws ServiceException;

	/**
	 * 获取销售金额
	 * @param depositCashList
	 * @return
	 */
	public List<DepositCash> getSaleAmountByOutDate(List<DepositCash> depositCashList);
	
	/**
	 * 根据店铺编码、销售日期获取实收金额
	 * @return
	 */
	@Cacheable(value = "paidinAmount", key = "#params")
	public Map<String, BigDecimal> getPaidinAmount(Map<String, Object> params);

	/**
	 * 根据参数查询各店铺最近一次存现时间
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	public List<DepositCash> findLastDepositDate(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 查询店铺超出最近一次存现的天数（现金销售为0不统计）
	 * @return
	 * @throws ManagerException 
	 */
	public DepositCheck findBeyondDates(Map<String, Object> params) throws ManagerException;
}