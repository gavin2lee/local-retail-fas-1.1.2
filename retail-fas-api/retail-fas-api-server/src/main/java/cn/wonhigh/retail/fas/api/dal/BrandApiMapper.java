package cn.wonhigh.retail.fas.api.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.Brand;


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
public interface BrandApiMapper {

	List<Brand> findByBrandNo(@Param("brandNo")String brandNo);
	
}