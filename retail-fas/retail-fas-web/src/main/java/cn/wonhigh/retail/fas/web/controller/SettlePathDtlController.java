package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.SettleCategoryDtl;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;
import cn.wonhigh.retail.fas.common.model.SupplierGroup;
import cn.wonhigh.retail.fas.manager.SettlePathDtlManager;
import cn.wonhigh.retail.fas.manager.SupplierGroupManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 结算路径明细
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
@RequestMapping("/settle_path_dtl")
public class SettlePathDtlController extends BaseController<SettlePathDtl> {
	
    @Resource
    private SettlePathDtlManager settlePathDtlManager;

    @Resource
    private SupplierGroupManager supplierGroupManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_path_dtl/",settlePathDtlManager);
    }
    
    /**
     * 通过结算路径id，查询该结算路径的明细数据
     * 
     * @param pathNo 结算路径编码
     * @param request HttpServletRequest
     * @return 结算路径明细列表
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_settle_path")
	@ResponseBody
	public List<SettlePathDtl> querySettlePath(@RequestParam("pathNo")String pathNo, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pathNo", pathNo);
		List<SettlePathDtl> list = this.settlePathDtlManager.findByBiz(null, params);
		if(list != null && list.size() > 0) {
			SupplierGroup supplierGroup = new SupplierGroup();
			supplierGroup.setGroupNo(list.get(0).getCompanyNo());
			SupplierGroup result = supplierGroupManager.findById(supplierGroup);
			if(result != null) {
				list.get(0).setCompanyName(result.getGroupName());
			}
		}
		return list;
    }
    @RequestMapping(value = "/get_biz")
  	@ResponseBody
  	public List<SettlePathDtl> getBiz(SettlePathDtl modelType, HttpServletRequest req,Model model)
  			throws ManagerException {
  		String pathNo = req.getParameter("pathNo");
      	if(StringUtils.isNotEmpty(pathNo)) {
      		return super.getBiz(modelType, req, model);
      	}
      	return new ArrayList<SettlePathDtl>();
  	}
    @RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	String pathNo = req.getParameter("pathNo");
    	if(StringUtils.isEmpty(pathNo)) {
    		Map<String, Object> obj = new HashMap<String, Object>();
    		obj.put("total", 0);
    		obj.put("rows", new ArrayList<SettlePathDtl>(1));
    		return obj;
    	}
		return super.queryList(req, model);
	}
}