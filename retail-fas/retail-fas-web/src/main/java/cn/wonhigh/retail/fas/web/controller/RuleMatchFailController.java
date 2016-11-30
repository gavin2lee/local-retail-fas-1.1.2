package cn.wonhigh.retail.fas.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.RuleMatchFail;
import cn.wonhigh.retail.fas.common.model.SettlementBodyChangeRecord;
import cn.wonhigh.retail.fas.manager.RuleMatchFailManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-15 17:42:50
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
@RequestMapping("/rule_match_fail")
@ModuleVerify("30120021")
public class RuleMatchFailController extends BaseCrudController<RuleMatchFail> {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

    @Resource
    private RuleMatchFailManager ruleMatchFailManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("rule_match_fail/",ruleMatchFailManager);
    }
    
    /**
   	 * 导出
   	 * @param modelType 实体对象
   	 * @param req HttpServletRequest
   	 * @param model Model
   	 * @param response HttpServletResponse
   	 * @throws ManagerException 异常
   	 */
   	@RequestMapping(value = "/do_exports")
   	public void doFasExport(BillBalanceInvoiceApply modelType, HttpServletRequest req, Model model,
   			HttpServletResponse response) throws ManagerException {
   	  String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
      String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
   	
   		String fileName = req.getParameter("fileName");
   		String exportColumns = req.getParameter("exportColumns");
   		
   		Map<String, Object> params = builderParams(req, model);
   		int total = ruleMatchFailManager.findCount(params);
   		SimplePage page = new SimplePage(0, total,  total);
   		List<RuleMatchFail> list = ruleMatchFailManager.findByPage(page, sortColumn, sortOrder, params);
   		try {
   			for(RuleMatchFail ruleMatchFail:list){
   				if(ruleMatchFail.getStatus()==0){
   					ruleMatchFail.setStatusName("失败");
   				}else if(ruleMatchFail.getStatus()==1){
   					ruleMatchFail.setStatusName("成功");
   				}
   				if(ruleMatchFail.getMatchType()==0){
   					ruleMatchFail.setMatchTypeName("总部匹配");
   				}else if(ruleMatchFail.getMatchType()==1){
   					ruleMatchFail.setMatchTypeName("地区匹配");
   				}
   				if(ruleMatchFail.getFailReason()==1){
   					ruleMatchFail.setFailReasonName("供应商组匹配失败");
   				}else if(ruleMatchFail.getFailReason()==2){
   					ruleMatchFail.setFailReasonName("结算大类匹配失败");
   				}else if(ruleMatchFail.getFailReason()==3){
   					ruleMatchFail.setFailReasonName("新旧款、年份季节、品牌部或生效日期匹配失败");
   				}else if(ruleMatchFail.getFailReason()==4){
   					ruleMatchFail.setFailReasonName("获取厂进价失败");
   				}else if(ruleMatchFail.getFailReason()==5){
   					ruleMatchFail.setFailReasonName("获取总部价失败");
   				}else if(ruleMatchFail.getFailReason()==6){
   					ruleMatchFail.setFailReasonName("匹配到多个加价规则");
   				}
   			}
	    	ExportUtils.doExport(fileName, exportColumns, list, response);
		} catch (RpcException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);   		}
  
   	}
}