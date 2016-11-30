package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.FinancialCategoryCommonDto;
import cn.wonhigh.retail.fas.common.dto.FinancialCategoryDto;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;
import cn.wonhigh.retail.fas.common.model.FinancialCategoryDtl;
import cn.wonhigh.retail.fas.service.FinancialCategoryDtlService;
import cn.wonhigh.retail.fas.service.FinancialCategoryService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-12-23 10:38:39
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
@Service("financialCategoryManager")
class FinancialCategoryManagerImpl extends BaseCrudManagerImpl implements FinancialCategoryManager {
	
    @Resource
    private FinancialCategoryService financialCategoryService;
    
    @Resource
    private FinancialCategoryDtlService financialCategoryDtlService;

    @Override
    public BaseCrudService init() {
        return financialCategoryService;
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
   			return this.financialCategoryService.findRelationCount(params);
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
   	public List<FinancialCategoryDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
   			Map<String, Object> params) throws ManagerException {
   		try {
   			return this.financialCategoryService.findRelationByPage(page, sortColumn, sortOrder, params);
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
	public int save(FinancialCategoryDto dto, Map<CommonOperatorEnum, List<FinancialCategoryDtl>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(dto.getId() != null) {
				count = financialCategoryService.modifyById(dto);
			} else {
				count = financialCategoryService.add(dto);
			}
			if(count > 0 && params != null) {
				List<FinancialCategoryDtl> delList = params.get(CommonOperatorEnum.DELETED);
				if(null != delList && delList.size() > 0) {
					for(FinancialCategoryDtl modelType : delList) {
						count += this.financialCategoryDtlService.deleteById(modelType);
					}
				}
				List<FinancialCategoryDtl> updateList = params.get(CommonOperatorEnum.UPDATED);
				if(null != updateList && updateList.size() > 0) {
					for(FinancialCategoryDtl modelType : updateList) {
						modelType.setFinancialCategoryNo(dto.getFinancialCategoryNo());
						count += this.financialCategoryDtlService.modifyById(modelType);
					}
				}
				List<FinancialCategoryDtl> insertList = params.get(CommonOperatorEnum.INSERTED);
				if(null != insertList && insertList.size() > 0) {
					for(FinancialCategoryDtl modelType : insertList) {
						modelType.setFinancialCategoryNo(dto.getFinancialCategoryNo());
						this.financialCategoryDtlService.add(modelType);
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
	public int delete(List<FinancialCategoryDto> list) throws ManagerException {
		int count = 0;
		try {
			if(list != null && list.size() > 0) {
				for(FinancialCategoryDto dto : list) {
					//删除表头数据
					count += financialCategoryService.deleteById(dto);
					
					//删除表体数据
					financialCategoryDtlService.deleteByFinancialCategoryNo(dto.getFinancialCategoryNo());
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public List<FinancialCategoryCommonDto> getAllCateInfo(Map<String, Object> params) {
		return financialCategoryService.getAllCateInfo(params);
	}

	@Override
	public List<FinancialCategory> findCateInfoByCateNo(Map<String, Object> params) {
		return this.financialCategoryService.selectCateInfoByCateNo(params);
	}
	
	
}