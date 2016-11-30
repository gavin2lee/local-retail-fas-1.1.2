package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.FinancialCategoryCommonDto;
import cn.wonhigh.retail.fas.common.dto.FinancialCategoryDto;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-12-23 10:38:39
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
public interface FinancialCategoryMapper extends BaseCrudMapper {
	
	int selectRelationCount(@Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams);

	List<FinancialCategoryDto> selectRelationByPage(@Param("page")SimplePage page, 
			@Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams);
	
	/**
	 * 通过商品大类获取分类编码和名称
	 * @param params
	 * @return
	 */
	List<FinancialCategory> selectCateInfoByCateNo(@Param("params")Map<String, Object> params);
	
	/**
	 * 获取财务大类和商品一级大类信息
	 * @param params
	 * @return
	 */
	List<FinancialCategoryCommonDto> getAllCateInfo(@Param("params")Map<String, Object> params);
}