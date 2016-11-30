package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SystemParamSet;
import cn.wonhigh.retail.fas.service.SystemParamSetService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-22 10:32:22
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
@Service("systemParamSetManager")
class SystemParamSetManagerImpl extends BaseCrudManagerImpl implements SystemParamSetManager {
    @Resource
    private SystemParamSetService systemParamSetService;

    @Override
    public BaseCrudService init() {
        return systemParamSetService;
    }
    
    /**
	 * 根据系统参数key查询参数值集合
	 * @param systemParamSet
	 * @param params
	 * @return
     * @throws ServiceException 
	 */
	public List<SystemParamSet> findSystemParamSetByParma(SystemParamSet systemParamSet,Map<String, Object> params) throws ManagerException{
		try{
			return systemParamSetService.findByBiz(null,params);
		}catch(ServiceException e){
			throw new ManagerException(e);
		}
	}
	
	/**
	 * 根据系统参数key查询参数值
	 * @param systemParamSet
	 * @param params
	 * @return
	 */
	public String findSystemParamByParma(SystemParamSet systemParamSet,Map<String, Object> params)throws ManagerException{
		String value = "";
		try{
			List<SystemParamSet> systemParamSetList = systemParamSetService.findByBiz(null,params);
			if(systemParamSetList != null && systemParamSetList.size() > 0) {
				 value = systemParamSetList.get(0).getDtlValue();
			}
			return value;
		}catch(ServiceException e){
			throw new ManagerException(e);
		}
	}
}