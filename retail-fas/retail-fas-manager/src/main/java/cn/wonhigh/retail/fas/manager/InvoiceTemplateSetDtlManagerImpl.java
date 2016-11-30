package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.InvoiceCategoryCommonDto;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSetDtl;
import cn.wonhigh.retail.fas.service.InvoiceTemplateSetDtlService;

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
@Service("invoiceTemplateSetDtlManager")
class InvoiceTemplateSetDtlManagerImpl extends BaseCrudManagerImpl implements InvoiceTemplateSetDtlManager {
    @Resource
    private InvoiceTemplateSetDtlService invoiceTemplateSetDtlService;

    @Override
    public BaseCrudService init() {
        return invoiceTemplateSetDtlService;
    }

	@Override
	public List<InvoiceCategoryCommonDto> selectCateInfo(Map<String, Object> params) {
		return invoiceTemplateSetDtlService.selectCateInfo(params);
	}
}