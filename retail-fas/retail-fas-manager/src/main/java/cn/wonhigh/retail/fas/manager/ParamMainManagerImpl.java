package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ParamMainDto;
import cn.wonhigh.retail.fas.common.model.ParamDtl;
import cn.wonhigh.retail.fas.common.model.ParamMain;
import cn.wonhigh.retail.fas.service.ParamDtlService;
import cn.wonhigh.retail.fas.service.ParamMainService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-21 10:32:05
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
@Service("paramMainManager")
class ParamMainManagerImpl extends BaseCrudManagerImpl implements ParamMainManager {
    @Resource
    private ParamMainService paramMainService;

    @Resource
    private ParamDtlService paramDtlService;
    
    @Override
    public BaseCrudService init() {
        return paramMainService;
    }

	@Override
	public int save(ParamMain dto,
			Map<CommonOperatorEnum, List<ParamDtl>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(dto.getId() != null) {
				count = paramMainService.modifyById(dto);
			} else {
				count = paramMainService.add(dto);
			}
			if(count > 0 && params != null) {
				List<ParamDtl> delList = params.get(CommonOperatorEnum.DELETED);
				if(null != delList && delList.size() > 0) {
					for(ParamDtl modelType : delList) {
						count += this.paramDtlService.deleteById(modelType);
					}
				}
				List<ParamDtl> updateList = params.get(CommonOperatorEnum.UPDATED);
				if(null != updateList && updateList.size() > 0) {
					for(ParamDtl modelType : updateList) {
						modelType.setParamCode(dto.getParamCode());
						count += this.paramDtlService.modifyById(modelType);
					}
				}
				List<ParamDtl> insertList = params.get(CommonOperatorEnum.INSERTED);
				if(null != insertList && insertList.size() > 0) {
					for(ParamDtl modelType : insertList) {
						modelType.setParamCode(dto.getParamCode());
						this.paramDtlService.add(modelType);
					}
				}
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findRelationCount(Map<String, Object> params) {
		return paramMainService.findRelationCount(params);
	}

	@Override
	public List<ParamMainDto> findRelationByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params) {
		return paramMainService.findRelationByPage(page,sortColumn,sortOrder,params);
	}

	@Override
	public void delete(List<ParamMainDto> list) throws ServiceException {
		Map<String,Object> map=null;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// 先删除明细
				map = new HashMap<String, Object>();
				map.put("paramCode", list.get(i).getParamCode());
				List<ParamDtl> dtlList = paramDtlService.findByBiz(null, map);
				if (dtlList.size() > 0) {
					for (int j = 0; j < dtlList.size(); j++) {
						paramDtlService.deleteById(dtlList.get(j).getId());
					}
				}
				// 再删除主表
				paramMainService.deleteById(list.get(i).getId());
			}
		}
	}
}