package cn.wonhigh.retail.fas.dal.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.ShopNameReplace;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-01-06 17:24:59
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
public interface ShopNameReplaceMapper extends BaseCrudMapper {
	
	/**
	 * 查询店铺替换名称
	 * @param shopNo
	 * @param brandUnitNo 
	 * @return
	 */
	ShopNameReplace selectReplaceName(@Param("shopNo") String shopNo,@Param("brandUnitNo") String brandUnitNo);
	
	List<ShopNameReplace> selectReplaceNames();
}