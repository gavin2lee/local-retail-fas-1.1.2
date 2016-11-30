package cn.wonhigh.retail.fas.dal.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.OrderPayway;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-30 15:12:18
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
public interface OrderPaywayMapper extends BaseCrudMapper {
	/**
	 * 根据店铺编码查询销售订单
	 * @param shopNo
	 * @return
	 */
	public List<OrderPayway> findByShopNo(@Param("shopNo")String shopNo);

	/**
	 * 查询所有的支付方式
	 * @return
	 */
	public List<OrderPayway> queryAllPayWays();
}