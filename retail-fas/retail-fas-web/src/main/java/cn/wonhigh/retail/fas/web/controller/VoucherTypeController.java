package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.AccountingSubject;
import cn.wonhigh.retail.fas.common.model.VoucherType;
import cn.wonhigh.retail.fas.manager.VoucherTypeManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 凭证类型 
 * @author ouyang.zm
 * @date  2014-09-03 09:46:57
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
@RequestMapping("/base_setting/voucher_type")
@ModuleVerify("30100012")
public class VoucherTypeController extends BaseController<VoucherType> {
    @Resource
    private VoucherTypeManager voucherTypeManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/voucher_type/",voucherTypeManager);
    }
    
    /**
     * 校验是否包含重复数据
     * @param financialAccount
     * @return
     * @throws ManagerException 
     */
    @ResponseBody
    @RequestMapping(value="/check_Repect", method=RequestMethod.POST)
    public boolean checkrepeatData(@ModelAttribute("model") VoucherType model) throws ManagerException{
    	Map<String,Object> params=new HashMap<String, Object>();
		String queryCondition = "AND VOUCH_TYPE_CODE = '" + model.getVouchTypeCode() + "'";
		params.put("queryCondition", queryCondition);
    	List<VoucherType> list=voucherTypeManager.findByBiz(null, params);
    	boolean msg=false;
    	if(list.size()>0){
    		msg=true;
    	}
		return msg;
    }
}