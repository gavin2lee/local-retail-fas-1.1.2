package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 11:02:19
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
public interface WholesalePrepayWarnMapper extends BaseCrudMapper {
	
	Map<String, BigDecimal> selectTotalAmount(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询保证金额
	 * @param params 查询参数
	 * @return Map<String, BigDecimal>
	 */
	Map<String, BigDecimal> selectMarginAmount(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询可冲销的数据集合
	 * @param params 查询参数
	 * @return List<WholesalePrepayWarn>
	 */
	List<WholesalePrepayWarn> selectReversal(@Param("params")Map<String, Object> params);

	/**
	 * 修改指定公司和客户的保证金余额和保证金是否足额标志
	 * @param params 参数
	 * @return 影响的记录数
	 * @throws ServiceException 异常
	 */
	int updateMargin(@Param("params")Map<String, Object> params) throws Exception;
}