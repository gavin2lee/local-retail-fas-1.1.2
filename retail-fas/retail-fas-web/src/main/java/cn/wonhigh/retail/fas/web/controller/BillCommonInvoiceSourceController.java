package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceSource;
import cn.wonhigh.retail.fas.manager.BillCommonInvoiceSourceManager;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-07 16:30:58
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
@Controller
@RequestMapping("/bill_common_invoice_source")
@Deprecated
public class BillCommonInvoiceSourceController extends BaseCrudController<BillCommonInvoiceSource> {
    @Resource
    private BillCommonInvoiceSourceManager billCommonInvoiceSourceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_common_invoice_source/",billCommonInvoiceSourceManager);
    }
}