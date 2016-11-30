package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.DepositCashCumulativeDifference;
import cn.wonhigh.retail.fas.common.model.IndependShopDayReport;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
public interface DepositCashCumulativeDifferenceMapper extends BaseCrudMapper {
	
	public BigDecimal selectCumulativeDifferenceByShopNo(String shopNo);
	
	public List<IndependShopDayReport> findByPayName(@Param("page")SimplePage page,@Param("orderByField")String orderByField, @Param("orderBy")String orderBy, @Param("params")Map<String, Object> params);
	
	/**
	 * 其他支付方式
	 * @return
	 * @author wangsm
	 */
	public List<IndependShopDayReport> findByPayNameOther(@Param("page")SimplePage page,@Param("orderByField")String orderByField, @Param("orderBy")String orderBy, @Param("params")Map<String, Object> params);
	
	/**
	 * 现金支付方式
	 * @return
	 * @author wangsm
	 */
	public List<IndependShopDayReport> findByPayNameFromPOS(@Param("page")SimplePage page,@Param("orderByField")String orderByField, @Param("orderBy")String orderBy, @Param("params")Map<String, Object> params);
}