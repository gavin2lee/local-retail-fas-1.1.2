package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.BillInvCostAdjustDtlService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:14
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
@Service("billInvCostAdjustDtlManager")
class BillInvCostAdjustDtlManagerImpl extends BaseCrudManagerImpl implements BillInvCostAdjustDtlManager {
    @Resource
    private BillInvCostAdjustDtlService billInvCostAdjustDtlService;

    @Override
    public BaseCrudService init() {
        return billInvCostAdjustDtlService;
    }
    
    @Override
  	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
  	public int save(Map<CommonOperatorEnum, List<BillInvCostAdjustDtl>> params, Map<String, String> paramTemp)
  			throws ManagerException {
  		try {
  			int count = 0;
  			for (Entry<CommonOperatorEnum, List<BillInvCostAdjustDtl>> param : params.entrySet()) {
  				if (param.getKey().equals(CommonOperatorEnum.DELETED)) {
  					List<BillInvCostAdjustDtl> list = params.get(CommonOperatorEnum.DELETED);
  					if (null != list && list.size() > 0) {
  						for (BillInvCostAdjustDtl modelType : list) {
  							count += this.billInvCostAdjustDtlService.deleteById(modelType);
  						}
  					}
//  					commonUtilService.updateTotal(list.get(0).getBillNo(),list.get(0).getShardingFlag(),BillTypeEnums.INVENTORY_COST_ADJUST);
  				}
  				if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
  					List<BillInvCostAdjustDtl> list = params.get(CommonOperatorEnum.UPDATED);
  					if (null != list && list.size() > 0) {
  						for (BillInvCostAdjustDtl modelType : list) {
  							count += modifyById(modelType);
  						}
  					}
//  					commonUtilService.updateTotal(list.get(0).getBillNo(),list.get(0).getShardingFlag(),BillTypeEnums.INVENTORY_COST_ADJUST);
  				}
  				if (param.getKey().equals(CommonOperatorEnum.INSERTED)) {
  					List<BillInvCostAdjustDtl> list = params.get(CommonOperatorEnum.INSERTED);
  					if (null != list && list.size() > 0) {
  						for (BillInvCostAdjustDtl model : list) {
  							model.setBillNo(paramTemp.get("billNo"));
  							model.setShardingFlag(paramTemp.get("shardingFlag"));
  							model.setId(UUIDGenerator.getUUID());
  							super.add(model);
  						}
  						count += list.size();
  					}
//  					commonUtilService.updateTotal(list.get(0).getBillNo(),list.get(0).getShardingFlag(),BillTypeEnums.INVENTORY_COST_ADJUST);
  				}
  			}
  			return count;
  		} catch (ServiceException e) {
  			throw new ManagerException(e.getMessage(), e);
  		}
  	}

	@Override
	public List<BillInvCostAdjustDtl> findListBLK(SimplePage page, String orderByField, String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return billInvCostAdjustDtlService.findListBLK(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findListBLKcount(Map<String, Object> params) throws ManagerException {
		try {
			return billInvCostAdjustDtlService.findListBLKcount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

}