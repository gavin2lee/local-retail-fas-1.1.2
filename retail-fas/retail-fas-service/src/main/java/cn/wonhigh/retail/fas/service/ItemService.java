package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.Item;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-09-02 12:17:21
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
public interface ItemService extends BaseCrudService {

	String findItemNameByNo(String itemNo) throws ServiceException;
	
	/**
	 * 商品选择控件（下拉网格）专用
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @param authorityParams
	 * @return
	 * @throws ServiceException
	 */
	public List<Item> findComboGridDataByCondition(SimplePage page, String orderByField, String orderBy, Map<String, Object> params, AuthorityParams authorityParams)
			throws ServiceException;

	Integer findStyleNoCount(Map<String, Object> params) throws ServiceException;

	List<Item> findStyleNo(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params) throws ServiceException;

	/**
	 * 查询商品所有款号
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	public int findStyleCount(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 查询商品所有款号明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	public List<Item> findStyleList(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ServiceException;

	/**
	 * 巴洛克-查询款号对应的所有商品信息
	 * @return
	 * @throws ServiceException 
	 */
	public List<Item> findBLKItemInfo(ItemCostConditionDto dto) throws ServiceException;
	
}