package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.manager.BillBalanceManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@RequestMapping("/bill_balance")
public class BillBalanceController extends BaseCrudController<BillBalance> {
    @Resource
    private BillBalanceManager billBalanceManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_balance/",billBalanceManager);
    }
    
    /**
     * 审核
     * @param req
     * @param billBalance
     * @return
     * @throws ManagerException
     */
	@ResponseBody
	@RequestMapping(value = "/verify")
    public BillBalance  verify(HttpServletRequest req, BillBalance obj) throws ManagerException{
		if(obj.getStatus() == null || StringUtils.isBlank(obj.getBillNo())){
			return null;
		}
		SystemUser currUser = CurrentUser.getCurrentUser(req);
		obj.setAuditor(currUser.getUsername());
		obj.setAuditTime(new Date());
		billBalanceManager.verify(obj);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", obj.getBillNo());
		List<BillBalance> lstItem = billBalanceManager.findByBiz(null, params);
		if(!CollectionUtils.isEmpty(lstItem)){
			return lstItem.get(0);
		}
		return null;
    }
	
    /**
     * 导出
     * @param req
     * @param billBalance
     * @return
     * @throws ManagerException
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/export")
    public void export(HttpServletRequest req, HttpServletResponse response, Model model) throws Exception{
		List<Map> ColumnsList = ExportUtils.getColumnList(req.getParameter("exportColumns"));
		List<Map> dataMapList = ExportUtils.getDataList((List<Object>) this.queryList(req, model).get("rows"));;
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
    }
	

	/***
	 * 
	 * @param modelType
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/get_biz")
  	@ResponseBody
  	public List<BillBalance> getBiz(BillBalance modelType, HttpServletRequest req,Model model)
  			throws ManagerException {
  		String billNo = req.getParameter("billNo");
      	if(StringUtils.isNotEmpty(billNo)) {
      		return super.getBiz(modelType, req, model);
      	}
      	return new ArrayList<BillBalance>();
  	}
}