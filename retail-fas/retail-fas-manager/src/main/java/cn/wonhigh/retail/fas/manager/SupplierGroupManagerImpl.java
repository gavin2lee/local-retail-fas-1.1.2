package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.model.SupplierGroup;
import cn.wonhigh.retail.fas.common.model.SupplierGroupRel;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.SupplierGroupRelService;
import cn.wonhigh.retail.fas.service.SupplierGroupService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-25 17:35:45
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
@Service("supplierGroupManager")
class SupplierGroupManagerImpl extends BaseCrudManagerImpl implements SupplierGroupManager {
    @Resource
    private SupplierGroupService supplierGroupService;

    @Resource
    private SupplierGroupRelService supplierGroupRelService;
    
    @Override
    public BaseCrudService init() {
        return supplierGroupService;
    }

	@Override
	public List<Map<String, Object>> getGroupNoAndName(
			SupplierGroup supplierGroup) throws ManagerException {
		try {
			return supplierGroupService.getGroupNoAndName(supplierGroup);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int save(SupplierGroup dto,Map<CommonOperatorEnum, List<SupplierGroupRel>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(dto.getId() != null) {
				count = supplierGroupService.modifyById(dto);
			} else {
				if(StringUtils.isNotBlank(dto.getGroupNo())){
					count = supplierGroupService.add(dto);
				}
			}
			if(count > 0) {
				for(Map.Entry<CommonOperatorEnum, List<SupplierGroupRel>> param : params.entrySet()) {
					if(param.getKey().equals(CommonOperatorEnum.DELETED)) {
						List<SupplierGroupRel> list = params.get(CommonOperatorEnum.DELETED);
						if(null != list && list.size() > 0) {
							for(SupplierGroupRel modelType : list) {
								count += this.supplierGroupRelService.deleteById(modelType);
							}
						}
					}
					if(param.getKey().equals(CommonOperatorEnum.UPDATED)) {
						List<SupplierGroupRel> list = params.get(CommonOperatorEnum.UPDATED);
						if(null != list && list.size() > 0) {
							for(SupplierGroupRel modelType : list) {
								modelType.setGroupNo(dto.getGroupNo());
								count += this.supplierGroupRelService.modifyById(modelType);
							}
						}
					}
					if (param.getKey().equals(CommonOperatorEnum.INSERTED)) {
						List<SupplierGroupRel> list = params.get(CommonOperatorEnum.INSERTED);
						if(null != list && list.size() > 0) {
							for(SupplierGroupRel modelType : list) {
								modelType.setGroupNo(dto.getGroupNo());
								this.supplierGroupRelService.add(modelType);
							}
						}
					}
				}
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void importNoGroup(String groupNo, List<Object> lstItem)
			throws ManagerException {
		try {
			SupplierGroupRel rel = new SupplierGroupRel();
			rel.setGroupNo(groupNo);
			rel.setCreateUser(CurrentUser.getCurrentUser().getUsername());
			rel.setCreateTime(new Date());
			for (Object object : lstItem) {
				Supplier supplier = (Supplier)object;
				rel.setId(null);
				rel.setSupplierNo(supplier.getSupplierNo());
				rel.setSupplierName(supplier.getFullName());
				this.supplierGroupRelService.add(rel);
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

}