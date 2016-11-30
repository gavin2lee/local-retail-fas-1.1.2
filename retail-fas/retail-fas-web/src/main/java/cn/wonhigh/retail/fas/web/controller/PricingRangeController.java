package cn.wonhigh.retail.fas.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.PricingRange;
import cn.wonhigh.retail.fas.manager.PricingRangeManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-07-11 17:43:44
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
@RequestMapping("/pricing_range")
public class PricingRangeController extends BaseCrudController<PricingRange> {
    @Resource
    private PricingRangeManager pricingRangeManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("pricing_range/",pricingRangeManager);
    }

    @RequestMapping(value = "/get_pricing_item_count")
    @ResponseBody
	public Integer getPricingItemCount(HttpServletRequest req, Model model) throws ManagerException {
    	Map<String, Object> params = builderParams(req, model);
    	List<PricingRange> lstItem = pricingRangeManager.findByBiz(null, null);
    	if(!CollectionUtils.isEmpty(lstItem)){
    		if(lstItem.get(0).getIsStart() == 1){
    			return pricingRangeManager.getPricingItemCount(params);
    		}
     	}
		return 0;
	}
}