package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjustDtl;
import cn.wonhigh.retail.fas.dal.database.BillPurchaseAdjustDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-13 12:09:02
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
@Service("billPurchaseAdjustDtlService")
class BillPurchaseAdjustDtlServiceImpl extends BaseCrudServiceImpl implements BillPurchaseAdjustDtlService {
    @Resource
    private BillPurchaseAdjustDtlMapper billPurchaseAdjustDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return billPurchaseAdjustDtlMapper;
    }
   
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			BillPurchaseAdjustDtl obj =  (BillPurchaseAdjustDtl)modelType;
			obj.setId(UUIDHexGenerator.generate());
			int iCount = super.add(obj);
			return iCount;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findDtlCount(Map<String, Object> params) throws ServiceException {
		try {
			return billPurchaseAdjustDtlMapper.findDtlCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<BillPurchaseAdjustDtl> findDtlList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return billPurchaseAdjustDtlMapper.findDtlList(page,sortColumn,sortOrder,params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<BillPurchaseAdjustDtl> findDtlFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return billPurchaseAdjustDtlMapper.findDtlFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
}