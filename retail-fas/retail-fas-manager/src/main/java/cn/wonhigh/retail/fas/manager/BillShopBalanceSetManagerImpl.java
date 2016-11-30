package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceSet;
import cn.wonhigh.retail.fas.service.BillShopBalanceSetService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 门店结算差异生成方式配置表
 * @author yang.y
 * @date  2015-01-05 14:11:37
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
@Service("billShopBalanceSetManager")
class BillShopBalanceSetManagerImpl extends BaseCrudManagerImpl implements BillShopBalanceSetManager {
	
    @Resource
    private BillShopBalanceSetService billShopBalanceSetService;

    @Override
    public BaseCrudService init() {
        return billShopBalanceSetService;
    }
    
    /**
     * 新增/修改操作
     * @param params Map<CommonOperatorEnum, List<BillShopBalanceSet>>
     * @return 影响的记录数
     * @throws ManagerException 异常
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int saveAll(Map<CommonOperatorEnum, List<BillShopBalanceSet>> dataMap)
			throws ManagerException {
		try {
			int count = 0;
			List<BillShopBalanceSet> delList = dataMap.get(CommonOperatorEnum.DELETED);
			if(null != delList && delList.size() > 0) {
				for(BillShopBalanceSet modelType : delList) {
					count += this.billShopBalanceSetService.deleteById(modelType);
				}
			}
			List<BillShopBalanceSet> updateList = dataMap.get(CommonOperatorEnum.UPDATED);
			if(null != updateList && updateList.size() > 0) {
				for(BillShopBalanceSet modelType : updateList) {
					count += this.billShopBalanceSetService.modifyById(modelType);
				}
			}
			List<BillShopBalanceSet> insertList = dataMap.get(CommonOperatorEnum.INSERTED);
			if(null != insertList && insertList.size() > 0) {
				for(BillShopBalanceSet modelType : insertList) {
					this.billShopBalanceSetService.add(modelType);
				}
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceSet> selectByParams(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopBalanceSetService.selectByParams(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}