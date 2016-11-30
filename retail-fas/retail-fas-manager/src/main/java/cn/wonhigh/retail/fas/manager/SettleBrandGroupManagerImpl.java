package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.SettleBrandGroupDto;
import cn.wonhigh.retail.fas.common.model.SettleBrandGroupRel;
import cn.wonhigh.retail.fas.service.SettleBrandGroupRelService;
import cn.wonhigh.retail.fas.service.SettleBrandGroupService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 品牌组
 * @author yang.y
 * @date  2014-08-26 10:36:46
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
@Service("settleBrandGroupManager")
class SettleBrandGroupManagerImpl extends BaseCrudManagerImpl implements SettleBrandGroupManager {
    
	@Resource
    private SettleBrandGroupService settleBrandGroupService;
	
	@Resource
	private SettleBrandGroupRelService settleBrandGroupRelService;

    @Override
    public BaseCrudService init() {
        return settleBrandGroupService;
    }
    
    @Override
   	public int findRelationCount(Map<String, Object> params) throws ManagerException {
   		try {
   			return this.settleBrandGroupService.findRelationCount(params);
   		} catch(ServiceException e) {
   			throw new ManagerException(e.getMessage(), e);
   		}
   	}

   	@Override
   	public List<SettleBrandGroupDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
   			Map<String, Object> params) throws ManagerException {
   		try {
   			return this.settleBrandGroupService.findRelationByPage(page, sortColumn, sortOrder, params);
   		} catch(ServiceException e) {
   			throw new ManagerException(e.getMessage(), e);
   		}
   	}
   	
   	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int save(SettleBrandGroupDto dto, Map<CommonOperatorEnum, List<SettleBrandGroupRel>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(dto.getId() != null) {
				count = settleBrandGroupService.modifyById(dto);
			} else {
				count = settleBrandGroupService.add(dto);
			}
			if(count > 0) {
				//先删
				this.settleBrandGroupRelService.deleteByGroupNo(dto.getGroupNo());
				List<SettleBrandGroupRel> list=
						(List<SettleBrandGroupRel>) params.get(CommonOperatorEnum.INSERTED);
				if(list != null && list.size()>0){
					//后增
					for(SettleBrandGroupRel modelType : list) {
						this.settleBrandGroupRelService.add(modelType);
					}
				}
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 删除操作
	 * @param list 待删除的数据集合
	 * @return 删除的记录数
	 * @throws ManagerException 异常
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int delete(List<SettleBrandGroupDto> list) throws ManagerException {
		int count = 0;
		try {
			if(list != null && list.size() > 0) {
				for(SettleBrandGroupDto dto : list) {
					//删除表头数据
					count += settleBrandGroupService.deleteById(dto);
					
					//删除表体数据
					settleBrandGroupRelService.deleteByGroupNo(dto.getGroupNo());
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}
}