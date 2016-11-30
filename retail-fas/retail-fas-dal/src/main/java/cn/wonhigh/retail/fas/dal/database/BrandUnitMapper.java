package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BrandUnit;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2014-12-05 16:36:16
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
public interface BrandUnitMapper extends BaseCrudMapper {

	/**
	 * @param brandUnitNo
	 * @return
	 */
	String selectBrandNos(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据品牌编号查询其所属品牌部
	 * @param params
	 * @return
	 */
	BrandUnit getBrandUnitByBrand(@Param("params")Map<String, Object> params);
}