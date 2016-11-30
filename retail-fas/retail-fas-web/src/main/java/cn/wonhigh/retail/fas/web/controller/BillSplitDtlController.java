package cn.wonhigh.retail.fas.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.BillSplitDtl;
import cn.wonhigh.retail.fas.manager.BillSplitDtlManager;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途
 * 
 * @author yang.y
 * @date 2014-08-29 13:20:36
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/bill_split_dtl")
public class BillSplitDtlController extends BaseCrudController<BillSplitDtl> {
	@Resource
	private BillSplitDtlManager billSplitDtlManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("bill_split_dtl/", billSplitDtlManager);
	}

}