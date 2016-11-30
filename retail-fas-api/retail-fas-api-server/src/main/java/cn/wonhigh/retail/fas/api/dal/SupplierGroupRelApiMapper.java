package cn.wonhigh.retail.fas.api.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.model.SupplierGroupRelModel;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-13 16:29:02
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
public interface SupplierGroupRelApiMapper {
	
	List<SupplierGroupRelModel> selectBySupplierNo(@Param("supplierNo")String supplierNo);
}