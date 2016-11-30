package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSet;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSetDtl;
import cn.wonhigh.retail.fas.service.InvoiceTemplateSetDtlService;
import cn.wonhigh.retail.fas.service.InvoiceTemplateSetService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
@Service("invoiceTemplateSetManager")
class InvoiceTemplateSetManagerImpl extends BaseCrudManagerImpl implements InvoiceTemplateSetManager {
    @Resource
    private InvoiceTemplateSetService invoiceTemplateSetService;

    @Resource
    private InvoiceTemplateSetDtlService invoiceTemplateSetDtlService;
    
    @Override
    public BaseCrudService init() {
        return invoiceTemplateSetService;
    }

    @Override
	public int remove(String[] ids) throws ManagerException {
		int iCount = 0;
		for (String str : ids) {
			String id = str.split(",")[0];
			String invoiceTempNo = str.split(",")[1];
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("invoiceTempNo", invoiceTempNo);
			List<InvoiceTemplateSet> invoiceTemplateSetList;
			try {
//				invoiceTemplateSetList = invoiceTemplateSetService.findByBiz(null, params);
				List<InvoiceTemplateSetDtl> invoiceTemplateSetDtlList= invoiceTemplateSetDtlService.findByBiz(null, params);
				if (!CollectionUtils.isEmpty(invoiceTemplateSetDtlList)) {
					for(InvoiceTemplateSetDtl invoiceTemplateSetDtl : invoiceTemplateSetDtlList){
						iCount = invoiceTemplateSetDtlService.deleteById(invoiceTemplateSetDtl.getId());
					}
				}
				InvoiceTemplateSet invoiceTemplateSet = new InvoiceTemplateSet();
				invoiceTemplateSet.setInvoiceTempNo(invoiceTempNo);
				iCount = invoiceTemplateSetService.deleteByModel(invoiceTemplateSet);//.deleteById(id);//invoiceTemplateSetList.get(0).getId()
			} catch (ServiceException e) {
				throw new ManagerException(e.getMessage(), e);

			}	
		}
		return iCount;
	}

    
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int save(InvoiceTemplateSet invoiceTemplateSet,
			Map<CommonOperatorEnum, List<InvoiceTemplateSetDtl>> params)
			throws ManagerException {
		try {
			int count = 0;
			if(StringUtils.isNotBlank(invoiceTemplateSet.getId())) {
				count = invoiceTemplateSetService.modifyById(invoiceTemplateSet);
			} else {
				invoiceTemplateSet.setId(null);
				count = invoiceTemplateSetService.add(invoiceTemplateSet);
			}
			for(Map.Entry<CommonOperatorEnum, List<InvoiceTemplateSetDtl>> param : params.entrySet()) {
				if(param.getKey().equals(CommonOperatorEnum.DELETED)) {
					List<InvoiceTemplateSetDtl> list = params.get(CommonOperatorEnum.DELETED);
					if(null != list && list.size() > 0) {
						for(InvoiceTemplateSetDtl modelType : list) {
							count += this.invoiceTemplateSetDtlService.deleteById(modelType);
						}
					}
				}
				if(param.getKey().equals(CommonOperatorEnum.UPDATED)) {
					List<InvoiceTemplateSetDtl> list = params.get(CommonOperatorEnum.UPDATED);
					if(null != list && list.size() > 0) {
						for(InvoiceTemplateSetDtl modelType : list) {
							modelType.setInvoiceTempNo(invoiceTemplateSet.getInvoiceTempNo());
							count += this.invoiceTemplateSetDtlService.modifyById(modelType);
						}
					}
				}
				if (param.getKey().equals(CommonOperatorEnum.INSERTED)) {
					List<InvoiceTemplateSetDtl> list = params.get(CommonOperatorEnum.INSERTED);
					if(null != list && list.size() > 0) {
						for(InvoiceTemplateSetDtl modelType : list) {
							modelType.setInvoiceTempNo(invoiceTemplateSet.getInvoiceTempNo());
							this.invoiceTemplateSetDtlService.add(modelType);
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
	public <InvoiceTemplateSet> int deleteByModel(
			InvoiceTemplateSet invoiceTemplateSet) {
		return invoiceTemplateSetService.deleteByModel(invoiceTemplateSet);
	}

	@Override
	public int checkIsUseData(String invoiceTempNo) {
		return invoiceTemplateSetService.checkIsUseData(invoiceTempNo);
	}
}