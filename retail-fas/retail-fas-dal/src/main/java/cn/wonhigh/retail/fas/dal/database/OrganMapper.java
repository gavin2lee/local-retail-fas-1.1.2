package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.Organ;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-30 10:22:59
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
public interface OrganMapper extends BaseCrudMapper {
	
	/**
	 * 根据店铺编码获取管理城市
	 * @param params
	 * @return
	 */
	Organ getOrganByShopNo(@Param("params")Map<String,Object> params);
	
	/**
	 * 根据店铺编码获取管理城市
	 * @param params
	 * @return
	 */
	Organ getOrganInfoByShopNo(@Param("params")Map<String,Object> params);
}