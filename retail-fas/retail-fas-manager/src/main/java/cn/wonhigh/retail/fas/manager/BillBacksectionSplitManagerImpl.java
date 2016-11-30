package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplit;
import cn.wonhigh.retail.fas.common.model.BillBacksectionSplitDtl;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;
import cn.wonhigh.retail.fas.common.model.RateExpenseOperate;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.service.BillBacksectionSplitDtlService;
import cn.wonhigh.retail.fas.service.BillBacksectionSplitService;
import cn.wonhigh.retail.mps.api.client.dto.finance.BalanceCallBackDto;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderParameterParentDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:36
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
@Service("billBacksectionSplitManager")
class BillBacksectionSplitManagerImpl extends BaseCrudManagerImpl implements BillBacksectionSplitManager {
    @Resource
    private BillBacksectionSplitService billBacksectionSplitService;
    
    @Resource
    private BillBacksectionSplitDtlService billBacksectionSplitDtlService;

    @Override
    public BaseCrudService init() {
        return billBacksectionSplitService;
    }

	@Override
	public int remove(String[] ids) throws ManagerException {
		int iCount = 0;
		for (String str : ids) {
			String id = str.split(",")[0];
			String backsectionNo = str.split(",")[1];
			
			try {
				//删除表头
				iCount = billBacksectionSplitService.deleteById(id);
				//删除明细
				billBacksectionSplitDtlService.deleteByBacksectionNo(backsectionNo);
				
			} catch (ServiceException e) {
				throw new ManagerException(e.getMessage(), e);
			}	
		}
		return iCount;
	}

	@Override
	public boolean checkIsUpdate(String id) {
		boolean isUpate = false;
		int count = billBacksectionSplitService.findAfterCount(id);
		if(count == 0){
			isUpate = true;
		}
		return isUpate;
	}
}