package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ShopGroupDto;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.model.ShopGroup;
import cn.wonhigh.retail.fas.common.model.ShopGroupDtl;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.fas.service.ShopGroupDtlService;
import cn.wonhigh.retail.fas.service.ShopGroupService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-22 11:41:25
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
@Service("shopGroupManager")
class ShopGroupManagerImpl extends BaseCrudManagerImpl implements ShopGroupManager {
    @Resource
    private ShopGroupService shopGroupService;

    @Resource
    private ShopGroupDtlService shopGroupDtlService;
    
    @Resource
    private CommonUtilService commonUtilService;    
    
    @Override
    public BaseCrudService init() {
        return shopGroupService;
    }

	@Override
	public Integer delete(List<Object> dtlList) throws ManagerException {
		try {
			int iCount =0 ;
			for (Object object : dtlList) {
				ShopGroup shopGroup = (ShopGroup)object;
				shopGroupService.deleteById(shopGroup);
				shopGroupDtlService.deleteByNo(shopGroup.getShopGroupNo());
				iCount ++;
			}
			return iCount;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public ShopGroup add(ShopGroup obj, List<Object> insertedList)
			throws ManagerException {
		try {
			String shopGroupNo = commonUtilService.getNewBillNoCompanyNo(obj.getCompanyNo(), FasBillTypeEnums.SG.getRequestId());
			obj.setShopGroupNo(shopGroupNo);
			shopGroupService.add(obj);
			for (Object dtl : insertedList) {
				ShopGroupDtl shopGroupDtl = (ShopGroupDtl)dtl;
				shopGroupDtl.setShopGroupNo(shopGroupNo);
				shopGroupDtlService.add(shopGroupDtl);
			}
			return obj;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public ShopGroup update(ShopGroup obj, List<Object> insertedList,
			List<Object> updatedList, List<Object> deletedList)
			throws ManagerException {
		ShopGroup shopGroup =obj;
		try {
			String shopGroupNo = obj.getShopGroupNo();
			shopGroupService.modifyById(obj);
			String shopNos="";
			Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
			for (int i = 0; i < insertedList.size(); i++) {
				ShopGroupDtl dtl=(ShopGroupDtl)insertedList.get(i);
				uniqueCheckMap.put("shopNo",dtl.getShopNo());
				uniqueCheckMap.put("companyNo", obj.getCompanyNo());
				int count = shopGroupService.findByExportCount(uniqueCheckMap);
				if(count>0){
					shopNos+=dtl.getShopName()+",";
				}
			}
			if(shopNos!=""){
				shopNos+="这些店铺已经存在开票规则,不允许新增！";
				shopGroup.setShopName(shopNos);
			}else{
				for (Object dtl : insertedList) {
					ShopGroupDtl shopGroupDtl = (ShopGroupDtl)dtl;
					shopGroupDtl.setShopGroupNo(shopGroupNo);
					shopGroupDtlService.add(shopGroupDtl);
				}
			}
			for (Object dtl : updatedList) {
				shopGroupDtlService.modifyById(dtl);
			}
			for (Object dtl : deletedList) {
				shopGroupDtlService.deleteById(dtl);
			}
			return shopGroup;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Integer operate(List<Object> list) throws ManagerException {
		try {
			int iCount = 0;
			for (Object obj : list) {
				shopGroupService.modifyById(obj);
				iCount ++;
			}
			return iCount;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopGroup> getShopGroupNoByShopNo(String shopNo) throws ManagerException{
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", shopNo);
			return shopGroupService.getShopGroupNoByShopNo(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findClientQueryCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return shopGroupService.findClientQueryCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopGroup> findClientQueryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return shopGroupService.findClientQueryByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopGroupDto> findByExport(SimplePage page,String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		try{
			return shopGroupService.findByExport(page,orderByField, orderBy, params);
		}catch(ServiceException e){
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findByExportCount(Map<String, Object> params)throws ManagerException {
		try{
			return shopGroupService.findByExportCount(params);
		}catch(ServiceException e){
			throw new ManagerException(e.getMessage(), e);
		}
	}
}