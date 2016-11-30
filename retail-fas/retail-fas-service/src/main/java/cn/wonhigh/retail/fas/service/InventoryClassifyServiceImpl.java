package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.dal.database.InventoryClassifyMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-06 19:05:06
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
@Service("inventoryClassifyColletService")
class InventoryClassifyServiceImpl extends BaseCrudServiceImpl implements InventoryClassifyService {
	
	@Resource
	private InventoryClassifyMapper inventoryClassifyMapper;

    @Override
    public BaseCrudMapper init() {
        return inventoryClassifyMapper;
    }

	@Override
	public int findInventoryClassInicationCount(Map<String, Object> params)
			throws ServiceException {
		try{
			return inventoryClassifyMapper.selectInventoryClassInicationCount(params);
		}catch(Exception e){
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<Map<String, Object>> findInventoryClassInicationByPage(SimplePage page,
			Map<String, Object> params) throws ServiceException {
		try{
			return inventoryClassifyMapper.selectInventoryClassInicationByPage(page, params);
		}catch(Exception e){
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<PeriodBalance> findCateGoryCloumn(String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		try{
			return inventoryClassifyMapper.selectCateGoryCloumn(orderByField, orderBy, params);
		}catch(Exception e){
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<Map<String, Object>> findInventoryClassInicationTotal(
			Map<String, Object> params) throws ServiceException {
		try{
			return inventoryClassifyMapper.selectInventoryClassInicationTotal(params);
		}catch(Exception e){
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findPeInventoryClassInicationCount(Map<String, Object> params)
			throws ServiceException {
		try{
			return inventoryClassifyMapper.findPeInventoryClassInicationCount(params);
		}catch(Exception e){
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<Map<String, Object>> findPeInventoryClassInicationByPage(
			SimplePage page, Map<String, Object> params)throws ServiceException {
		try{
			return inventoryClassifyMapper.findPeInventoryClassInicationByPage(page, params);
		}catch(Exception e){
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<Map<String, Object>> findPeInventoryClassInicationTotal(
			Map<String, Object> params) throws ServiceException {
		try{
			return inventoryClassifyMapper.findPeInventoryClassInicationTotal(params);
		}catch(Exception e){
			throw new ServiceException("",e);
		}
	}

}