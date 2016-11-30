package cn.wonhigh.retail.fas.dal.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.ShopCheckSet;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-21 15:14:36
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
public interface ShopCheckSetMapper extends BaseCrudMapper {
	/**
	 * 获取店铺编码流水号
	 */
	public List<ShopCheckSet> getSerialNo();
	
	/**
	 * 获取店铺公司检查项编码列表
	 */
	public List<String> getCheckNo(@Param("shopNo")String shopNo);
}