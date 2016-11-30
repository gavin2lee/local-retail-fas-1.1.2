package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.CashCheck;
import cn.wonhigh.retail.fas.common.model.CreditCardCheck;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
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
public interface CashCheckMapper extends BaseCrudMapper {
	
	CashCheck findShopSaleDetailCount(@Param("params")Map<String, Object> params);

	List<CashCheck> findShopSaleDetailList(@Param("page")SimplePage page, @Param("orderBy")String orderBy, @Param("orderByField")String orderByField, @Param("params")Map<String, Object> params);
	
	List<CashCheck> queryCashCheckDetail(@Param("params")Map<String, Object> params);
	
	BigDecimal queryActualIncomeAmount(@Param("params")Map<String, Object> params);
	
	BigDecimal querySystemIncomeAmount(@Param("params")Map<String, Object> params);
}