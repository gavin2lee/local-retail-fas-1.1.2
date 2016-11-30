package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.dto.SettlePathQueryExportDto;
import cn.wonhigh.retail.fas.common.model.SettlePathBrandRel;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;
import cn.wonhigh.retail.fas.service.SettlePathBrandRelService;
import cn.wonhigh.retail.fas.service.SettlePathDtlService;
import cn.wonhigh.retail.fas.service.SettlePathService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
@Service("settlePathManager")
class SettlePathManagerImpl extends BaseCrudManagerImpl implements SettlePathManager {
	
    @Resource
    private SettlePathService settlePathService;
    
    @Resource
    private SettlePathDtlService settlePathDtlService;
    
    @Resource
    private SettlePathBrandRelService settlePathBrandRelService;

    @Override
    public BaseCrudService init() {
        return settlePathService;
    }
    
    @Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int save(SettlePathDto dto, Map<CommonOperatorEnum, List<SettlePathDtl>> params,
			List<SettlePathBrandRel> brandList)
			throws ManagerException {
		try {
			int count = 0;
			if(dto.getId() != null) {
				count = settlePathService.modifyById(dto);
			} else {
				count = settlePathService.add(dto);
			}
			if(count > 0 && params != null) {
				//操作结算主体
				List<SettlePathDtl> lstDel = params.get(CommonOperatorEnum.DELETED);
				if(null != lstDel && lstDel.size() > 0) {
					for(SettlePathDtl modelType : lstDel) {
						count += this.settlePathDtlService.deleteById(modelType);
					}
				}
				List<SettlePathDtl> lstUpdate = params.get(CommonOperatorEnum.UPDATED);
				if(null != lstUpdate && lstUpdate.size() > 0) {
					for(SettlePathDtl modelType : lstUpdate) {
						modelType.setPathNo(dto.getPathNo());
						count += this.settlePathDtlService.modifyById(modelType);
					}
				}
				List<SettlePathDtl> lstInsert = params.get(CommonOperatorEnum.INSERTED);
				if(null != lstInsert && lstInsert.size() > 0) {
					for(SettlePathDtl modelType : lstInsert) {
						modelType.setPathNo(dto.getPathNo());
						this.settlePathDtlService.add(modelType);
					}
				}
			}
			//操作品牌
			if(brandList != null && brandList.size() > 0) {
				settlePathBrandRelService.save(dto.getPathNo(), brandList);
			} else if(StringUtils.isNotEmpty(dto.getId()) && StringUtils.isNotEmpty(dto.getPathNo())){ 
				// 删除原先关联的品牌数据
				settlePathBrandRelService.deleteByPathNo(dto.getPathNo());
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

    /**
	 * 定时修改结算路径的状态
	 * @param params 参数
	 * @return 修改的数量
	 * @throws ServiceException 异常
	 */
	@Override
	public int updateStatus(Map<String, Object> params) throws ManagerException {
		try {
			return settlePathService.updateStatus(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int doAudit(List<SettlePathDto> oList) throws ManagerException {
		try {
			int count = 0;
			if(oList != null && oList.size() > 0) {
				for(SettlePathDto model : oList) {
					count += settlePathService.doAudit(model);
				}
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int doStatus(List<SettlePathDto> oList) throws ManagerException {
		try {
			int count = 0;
			if(oList != null && oList.size() > 0) {
				for(SettlePathDto model : oList) {
					count += settlePathService.doStatus(model);
				}
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
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
   			return this.settlePathService.findRelationCount(params);
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
   	public List<SettlePathQueryExportDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
   			Map<String, Object> params) throws ManagerException {
   		try {
   			return this.settlePathService.findRelationByPage(page, sortColumn, sortOrder, params);
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
	public int delete(List<SettlePathDto> list) throws ManagerException {
		int count = 0;
		try {
			if(list != null && list.size() > 0) {
				for(SettlePathDto dto : list) {
					//删除表头数据
					count += settlePathService.deleteById(dto);
					//删除表体数据
					settlePathDtlService.deleteByPathNo(dto.getPathNo());
					//删除关联的品牌部
					settlePathBrandRelService.deleteByPathNo(dto.getPathNo());
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}
}