package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillSplitDtl;
import cn.wonhigh.retail.fas.common.model.SplitDataSource;
import cn.wonhigh.retail.fas.manager.BillSplitDtlManager;
import cn.wonhigh.retail.fas.manager.SplitDataSourceManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-09-11 10:09:58
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
@Controller
@RequestMapping("/split_data_source")
@ModuleVerify("30116001")
public class SplitDataSourceController extends BaseCrudController<SplitDataSource> {
    
	@Resource
    private SplitDataSourceManager splitDataSourceManager;
	
	@Resource
	private BillSplitDtlManager billSplitDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("split_data_source/", splitDataSourceManager);
    }
    
    @RequestMapping("/split_dtl")
    @ResponseBody
    public List<BillSplitDtl> splitDtl(@RequestParam("billNo")String billNo) 
    		throws ManagerException {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("refBillNo", billNo);
    	List<BillSplitDtl> list = billSplitDtlManager.findByBiz(null, params);
    	return list;
    }
}