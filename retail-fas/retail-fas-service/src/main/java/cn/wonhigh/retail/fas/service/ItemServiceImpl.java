package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.dal.database.ItemMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("itemService")
class ItemServiceImpl extends BaseCrudServiceImpl implements ItemService {
    @Resource
    private ItemMapper itemMapper;

    @Override
    public BaseCrudMapper init() {
        return itemMapper;
    }
    
    @Override
	public String findItemNameByNo(String itemNo) throws ServiceException {
		return itemMapper.findItemNameByNo(itemNo);
	}
    
    @Override
	public List<Item> findComboGridDataByCondition(SimplePage page, String orderByField, String orderBy, Map<String, Object> params, AuthorityParams authorityParams)
			throws ServiceException {
		try {
			return itemMapper.findComboGridDataByCondition(page, orderByField, orderBy, params, authorityParams);
		} catch (Exception e) {
			throw new ServiceException("查询出错！", e);
		}
	}

	@Override
	public Integer findStyleNoCount(Map<String, Object> params) throws ServiceException {
		try {
			return itemMapper.findStyleNoCount(params);
		} catch (Exception e) {
			throw new ServiceException("查询出错！", e);
		}
	}

	@Override
	public List<Item> findStyleNo(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return itemMapper.findStyleNo(page,sortColumn,sortOrder,params);
		} catch (Exception e) {
			throw new ServiceException("查询出错！", e);
		}
	}

	@Override
	public int findStyleCount(Map<String, Object> params) throws ServiceException {
		try {
			return itemMapper.findStyleCount(params);
		} catch (Exception e) {
			throw new ServiceException("查询出错！", e);
		}
	}

	@Override
	public List<Item> findStyleList(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return itemMapper.findStyleList(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException("查询出错！", e);
		}
	}

	@Override
	public List<Item> findBLKItemInfo(ItemCostConditionDto dto) throws ServiceException {
		try {
			return itemMapper.findBLKItemInfo(dto);
		} catch (Exception e) {
			throw new ServiceException("巴洛克款号查询所有商品出错！", e);
		}
	}

}