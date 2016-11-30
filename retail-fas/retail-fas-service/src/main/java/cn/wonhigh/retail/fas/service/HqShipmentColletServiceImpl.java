package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.HqShipmentCollet;
import cn.wonhigh.retail.fas.dal.database.HqSportReportMapper;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-13 15:20:45
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
@Service("hqShipmentColletServiceImpl")
class HqShipmentColletServiceImpl extends BaseCrudServiceImpl implements HqShipmentColletService {
	
    @Resource
    private HqSportReportMapper hqShipmentColletMapper;
    
    @Override
	public BaseCrudMapper init() {
		return hqShipmentColletMapper;
	}

	@Override
	public List<HqShipmentCollet> findHqShipmentColletByCondition(Map<String, Object> params, SimplePage page) throws ServiceException {
		try{
    		return hqShipmentColletMapper.findHqShipmentColletByCondition(params,page);
    	} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
     * 根据条件查询总部出货统计表的记录数
     * @param params
     * @return
     */
	@Override
    public HqShipmentCollet findHqShipmentColletCount(Map<String,Object> params)throws ServiceException {
    	try{
    		return hqShipmentColletMapper.findHqShipmentColletCount(params);
    	}catch(Exception e){
    		throw new ServiceException(e);
    	}
    }
    
    
    /**
	 * 根据条件查询退供应商统计表明细
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
	@Override
    public List<HqShipmentCollet> findRecallSupplierList(Map<String,Object> params,SimplePage page) throws ServiceException{
    	try{
    		return hqShipmentColletMapper.findRecallSupplierList(params,page);
    	}catch(Exception e){
    		throw new ServiceException(e);
    	}
    }
    
    /**
     * 根据条件查询退供应商统计表明细的记录数
     * @param params
     * @return
     */
	@Override
    public HqShipmentCollet findRecallSupplierCount(Map<String,Object> params)throws ServiceException{
    	try{
    		return hqShipmentColletMapper.findRecallSupplierCount(params);
    	}catch(Exception e){
    		throw new ServiceException(e);
    	}
    }
    
    
    /**
	 * 根据条件查询退供应商统计表汇总
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
	@Override
    public List<HqShipmentCollet> findReturnSupplierList(Map<String,Object> params,SimplePage page)throws ServiceException{
    	try{
    		return hqShipmentColletMapper.findReturnSupplierList(params,page);
    	}catch(Exception e){
    		throw new ServiceException(e);
    	}
    }
    
    /**
     * 根据条件查询退供应商统计表汇总的记录数
     * @param params
     * @return
     */
    public HqShipmentCollet findReturnSupplierCount(Map<String,Object> params) throws ServiceException{
    	try{
    		return hqShipmentColletMapper.findReturnSupplierCount(params);
    	}catch(Exception e){
    		throw new ServiceException(e);
    	}
    }

}