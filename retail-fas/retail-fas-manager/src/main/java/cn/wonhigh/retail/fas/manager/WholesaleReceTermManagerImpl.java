package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.WholesaleReceTermDtlDto;
import cn.wonhigh.retail.fas.common.model.WholesaleReceTerm;
import cn.wonhigh.retail.fas.service.WholesaleReceTermDtlService;
import cn.wonhigh.retail.fas.service.WholesaleReceTermService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-17 18:00:36
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
@Service("wholesaleReceTermManager")
class WholesaleReceTermManagerImpl extends BaseCrudManagerImpl implements WholesaleReceTermManager {
	
    @Resource
    private WholesaleReceTermService wholesaleReceTermService;
    
    @Resource
    private WholesaleReceTermDtlService wholesaleReceTermDtlService;

    @Override
    public BaseCrudService init() {
        return wholesaleReceTermService;
    }
    
    @Override
	public int save(WholesaleReceTerm model, Map<CommonOperatorEnum, List<WholesaleReceTermDtlDto>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(model.getId() != null) {
				count = wholesaleReceTermService.modifyById(model);
			} else {
				count = wholesaleReceTermService.add(model);
			}
			if(count > 0) {
				for(Map.Entry<CommonOperatorEnum, List<WholesaleReceTermDtlDto>> param : params.entrySet()) {
					if(param.getKey().equals(CommonOperatorEnum.DELETED)) {
						List<WholesaleReceTermDtlDto> list = params.get(CommonOperatorEnum.DELETED);
						if(null != list && list.size() > 0) {
							for(WholesaleReceTermDtlDto modelType : list) {
								count += this.wholesaleReceTermDtlService.deleteById(modelType);
							}
						}
					}
					if(param.getKey().equals(CommonOperatorEnum.UPDATED)) {
						List<WholesaleReceTermDtlDto> list = params.get(CommonOperatorEnum.UPDATED);
						if(null != list && list.size() > 0) {
							for(WholesaleReceTermDtlDto modelType : list) {
								modelType.setTermNo(model.getTermNo());
								count += this.wholesaleReceTermDtlService.modifyById(modelType);
							}
						}
					}
					if (param.getKey().equals(CommonOperatorEnum.INSERTED)) {
						List<WholesaleReceTermDtlDto> list = params.get(CommonOperatorEnum.INSERTED);
						if(null != list && list.size() > 0) {
							for(WholesaleReceTermDtlDto modelType : list) {
								modelType.setTermNo(model.getTermNo());
								this.wholesaleReceTermDtlService.add(modelType);
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
	public int checkOnlyByTermNoOrTermName(Map<String, Object> params)
			throws ManagerException {
		try {
			return wholesaleReceTermService.checkOnlyByTermNoOrTermName(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<WholesaleReceTerm> selectByCompanyNoAndCustomerNo(Map<String, Object> params) 
			throws ManagerException {
		try {
			return wholesaleReceTermService.selectByCompanyNoAndCustomerNo(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
}