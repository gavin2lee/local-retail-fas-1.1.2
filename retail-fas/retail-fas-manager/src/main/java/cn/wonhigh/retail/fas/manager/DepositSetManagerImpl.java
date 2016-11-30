package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.DepositSet;
import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.DepositSetService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-10 16:41:24
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
@Service("depositSetManager")
class DepositSetManagerImpl extends BaseCrudManagerImpl implements DepositSetManager {
    @Resource
    private DepositSetService depositSetService;

    @Override
    public BaseCrudService init() {
        return depositSetService;
    }

	@Override
	public boolean addListDepositSet(List<DepositSet> list) throws ManagerException {
		try {
			
			if(list!=null &&list.size()>0){
				for (DepositSet depositSet:list) {
					String shopNo = depositSet.getShopNo();
					if(null != shopNo){
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("queryCondition", " AND shop_no = '"+shopNo+"' ");
						List<DepositSet> l = depositSetService.findByBiz(null, params);
						if(l.size() > 0){
							continue;
						}
					}
					
					depositSet.setId(UUIDGenerator.getUUID());
					depositSetService.add(depositSet);
				}
			}
			return true;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public DepositSet getDepositSet(Map<String, Object> params) {
		return depositSetService.getDepositSet(params);
	}
}