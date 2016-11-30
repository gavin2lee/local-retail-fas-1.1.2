package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.OrderUnit;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 订货单位
 * @author huang.xb1
 * @date  2014-07-28 14:19:21
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
public interface OrderUnitMapper extends BaseCrudMapper {
	/**
	 * 获取店铺下的订货单位信息
	 * @param brand
	 * @param params
	 * @return
	 */
	public List<OrderUnit> selectByShopNo(@Param("model") OrderUnit orderUnit,
			@Param("params") Map<String, Object> params);

	public String findOrderUnitNameByNo(String orderUnitNo);
	
}