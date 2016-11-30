package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.SettleCategoryDto;
import cn.wonhigh.retail.fas.common.model.SettleCategoryDtl;
import cn.wonhigh.retail.fas.service.SettleCategoryDtlService;
import cn.wonhigh.retail.fas.service.SettleCategoryService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-22 14:57:26
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
@Service("settleCategoryManager")
class SettleCategoryManagerImpl extends BaseCrudManagerImpl implements SettleCategoryManager {
    
	@Resource
    private SettleCategoryService settleCategoryService;
	
	@Resource
	private SettleCategoryDtlService settleCategoryDtlService;

    @Override
    public BaseCrudService init() {
        return settleCategoryService;
    }
    
    /**
     * 查询关联的数量
     * @param params 查询参数
     * @return 关联的数量
     * @throws ManagerException 异常
     */
    @Override
   	public int findRelationCount(Map<String, Object> params) throws ManagerException {
   		try {
   			return this.settleCategoryService.findRelationCount(params);
   		} catch(ServiceException e) {
   			throw new ManagerException(e.getMessage(), e);
   		}
   	}

    /**
     * 分页查询关联的数据
     * @param page 分页对象
     * @param sortColumn 排序的列
     * @param 降序/升序排列
     * @param params 查询参数
     * @return 关联的数据集合
     * @throws ManagerException 异常
     */
   	@Override
   	public List<SettleCategoryDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
   			Map<String, Object> params) throws ManagerException {
   		try {
   			return this.settleCategoryService.findRelationByPage(page, sortColumn, sortOrder, params);
   		} catch(ServiceException e) {
   			throw new ManagerException(e.getMessage(), e);
   		}
   	}

    /**
     * 新增/修改操作
     * @param dto SettleCategoryDto
     * @param params Map<CommonOperatorEnum, List<SettleCategoryDtl>>
     * @return 影响的记录数
     * @throws ManagerException 异常
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int save(SettleCategoryDto dto, Map<CommonOperatorEnum, List<SettleCategoryDtl>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(dto.getId() != null) {
				count = settleCategoryService.modifyById(dto);
			} else {
				count = settleCategoryService.add(dto);
			}
			if(count > 0 && params != null) {
				List<SettleCategoryDtl> delList = params.get(CommonOperatorEnum.DELETED);
				if(null != delList && delList.size() > 0) {
					for(SettleCategoryDtl modelType : delList) {
						count += this.settleCategoryDtlService.deleteById(modelType);
					}
				}
				List<SettleCategoryDtl> updateList = params.get(CommonOperatorEnum.UPDATED);
				if(null != updateList && updateList.size() > 0) {
					for(SettleCategoryDtl modelType : updateList) {
						modelType.setSettleCategoryNo(dto.getSettleCategoryNo());
						count += this.settleCategoryDtlService.modifyById(modelType);
					}
				}
				List<SettleCategoryDtl> insertList = params.get(CommonOperatorEnum.INSERTED);
				if(null != insertList && insertList.size() > 0) {
					for(SettleCategoryDtl modelType : insertList) {
						modelType.setSettleCategoryNo(dto.getSettleCategoryNo());
						this.settleCategoryDtlService.add(modelType);
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
	public int delete(List<SettleCategoryDto> list) throws ManagerException {
		int count = 0;
		try {
			if(list != null && list.size() > 0) {
				for(SettleCategoryDto dto : list) {
					//删除表头数据
					count += settleCategoryService.deleteById(dto);
					
					//删除表体数据
					settleCategoryDtlService.deleteBySettleCategoryNo(dto.getSettleCategoryNo());
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}
}