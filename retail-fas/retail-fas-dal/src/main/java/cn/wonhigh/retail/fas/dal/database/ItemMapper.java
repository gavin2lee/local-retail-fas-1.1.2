package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.Item;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-10-22 15:09:15
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
public interface ItemMapper extends BaseCrudMapper {

	String findItemNameByNo(String itemNo);
	
	/**
	 * 商品选择控件（下拉网格）专用
	 * 
	 * @param params
	 * @param authorityParams
	 * @return
	 */
	public <ModelType> List<ModelType> findComboGridDataByCondition(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	Integer findStyleNoCount(@Param("params") Map<String, Object> params);

	List<Item> findStyleNo(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);

	/**
	 * 查询商品所有款号
	 * @param params
	 * @return
	 */
	public int findStyleCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询商品所有款号明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<Item> findStyleList(@Param("page")SimplePage page, @Param("orderByField")String orderByField, @Param("orderBy")String orderBy, @Param("params")Map<String, Object> params);
	
	
	/**
	 * 巴洛克-查询款号对应的所有商品信息
	 * @return
	 */
	public List<Item> findBLKItemInfo(@Param("params")ItemCostConditionDto dto);
}