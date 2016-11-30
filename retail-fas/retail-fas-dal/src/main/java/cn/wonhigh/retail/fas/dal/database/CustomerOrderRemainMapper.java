package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerOrderRemainDto;
import cn.wonhigh.retail.fas.common.model.CustomerOrderRemain;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 批发订单余额 
 * @author user
 * @date  2016-06-06 18:10:49
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
public interface CustomerOrderRemainMapper extends BaseCrudMapper {
	
	/**
	 * 分页查询批发订单
	 * @param SimplePage 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return 订单数据集合
	 * @throws Exception 异常
	 */
	List<WholesaleCustomerOrderRemainDto> queryWholesaleCustomerOrderRemain(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,
			@Param("params")Map<String,Object> params);
	/**
	 * 查询最近未完结的批发订单
	 * @param params 查询参数
	 * @return 订单数据集合
	 * @throws Exception 异常
	 */
	List<CustomerOrderRemain> selectLatestOrdersByParams(@Param("params")Map<String,Object> params);
}