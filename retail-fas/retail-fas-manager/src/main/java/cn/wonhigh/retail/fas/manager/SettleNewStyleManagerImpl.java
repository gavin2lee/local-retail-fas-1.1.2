package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.SettleNewStyleDto;
import cn.wonhigh.retail.fas.common.model.SettleNewStyleDtl;
import cn.wonhigh.retail.fas.service.SettleNewStyleDtlService;
import cn.wonhigh.retail.fas.service.SettleNewStyleService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-26 15:42:01
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
@Service("settleNewStyleManager")
class SettleNewStyleManagerImpl extends BaseCrudManagerImpl implements SettleNewStyleManager {
    
	@Resource
    private SettleNewStyleService settleNewStyleService;
	
	@Resource
	private SettleNewStyleDtlService settleNewStyleDtlService;

    @Override
    public BaseCrudService init() {
        return settleNewStyleService;
    }
    
    @Override
	public int findRelationCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.settleNewStyleService.findRelationCount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<SettleNewStyleDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return this.settleNewStyleService.findRelationByPage(page, sortColumn, sortOrder, params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int save(SettleNewStyleDto dto, Map<CommonOperatorEnum, List<SettleNewStyleDtl>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(dto.getId() != null) {
				count = settleNewStyleService.modifyById(dto);
			} else {
				count = settleNewStyleService.add(dto);
			}
			if(count > 0 && params != null) {
				List<SettleNewStyleDtl> delList = params.get(CommonOperatorEnum.DELETED);
				if(null != delList && delList.size() > 0) {
					for(SettleNewStyleDtl modelType : delList) {
						count += this.settleNewStyleDtlService.deleteById(modelType);
					}
				}
				List<SettleNewStyleDtl> updateList = params.get(CommonOperatorEnum.UPDATED);
				if(null != updateList && updateList.size() > 0) {
					for(SettleNewStyleDtl modelType : updateList) {
						modelType.setStyleNo(dto.getStyleNo());
						count += this.settleNewStyleDtlService.modifyById(modelType);
					}
				}
				List<SettleNewStyleDtl> insertList = params.get(CommonOperatorEnum.INSERTED);
				if(null != insertList && insertList.size() > 0) {
					for(SettleNewStyleDtl modelType : insertList) {
						modelType.setStyleNo(dto.getStyleNo());
						this.settleNewStyleDtlService.add(modelType);
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
	public int delete(List<SettleNewStyleDto> list) throws ManagerException {
		int count = 0;
		try {
			if(list != null && list.size() > 0) {
				for(SettleNewStyleDto dto : list) {
					//删除表头数据
					count += settleNewStyleService.deleteById(dto);
					
					//删除表体数据
					settleNewStyleDtlService.deleteByStyleNo(dto.getStyleNo());
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}
}