package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillInvoiceApplyDtl;
import cn.wonhigh.retail.fas.service.BillInvoiceApplyDtlService;
import cn.wonhigh.retail.fas.service.BillInvoiceApplyService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-16 14:05:12
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
@Service("billInvoiceApplyManager")
class BillInvoiceApplyManagerImpl extends BaseCrudManagerImpl implements BillInvoiceApplyManager {
    
	@Resource
    private BillInvoiceApplyService billInvoiceApplyService;
	
	@Resource
	private BillInvoiceApplyDtlService billInvoiceApplyDtlService;

    @Override
    public BaseCrudService init() {
        return billInvoiceApplyService;
    }

	@Override
	public int save(BillInvoiceApply model, Map<CommonOperatorEnum, List<BillInvoiceApplyDtl>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(model.getId() != null) {
				count = billInvoiceApplyService.modifyById(model);
			} else {
				count = billInvoiceApplyService.add(model);
			}
			if(count > 0) {
				for(Map.Entry<CommonOperatorEnum, List<BillInvoiceApplyDtl>> param : params.entrySet()) {
					if(param.getKey().equals(CommonOperatorEnum.DELETED)) {
						List<BillInvoiceApplyDtl> list = params.get(CommonOperatorEnum.DELETED);
						if(null != list && list.size() > 0) {
							for(BillInvoiceApplyDtl modelType : list) {
								count += this.billInvoiceApplyDtlService.deleteById(modelType);
							}
						}
					}
					if(param.getKey().equals(CommonOperatorEnum.UPDATED)) {
						List<BillInvoiceApplyDtl> list = params.get(CommonOperatorEnum.UPDATED);
						if(null != list && list.size() > 0) {
							for(BillInvoiceApplyDtl modelType : list) {
								count += this.billInvoiceApplyDtlService.modifyById(modelType);
							}
						}
					}
					if (param.getKey().equals(CommonOperatorEnum.INSERTED)) {
						List<BillInvoiceApplyDtl> list = params.get(CommonOperatorEnum.INSERTED);
						if(null != list && list.size() > 0) {
							for(BillInvoiceApplyDtl modelType : list) {
								this.billInvoiceApplyDtlService.add(modelType);
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
}