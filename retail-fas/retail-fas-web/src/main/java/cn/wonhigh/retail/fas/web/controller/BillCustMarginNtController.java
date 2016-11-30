package cn.wonhigh.retail.fas.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.model.BillCustMarginNt;
import cn.wonhigh.retail.fas.manager.BillCustMarginNtManager;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 客户保证金催缴通知单
 * @author admin
 * @date  2014-09-22 09:46:46
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
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/bill_cust_margin_nt")
public class BillCustMarginNtController extends BaseController<BillCustMarginNt> {
    
	@Resource
    private BillCustMarginNtManager billCustMarginNtManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_cust_margin_nt/",billCustMarginNtManager);
    }
    
    @RequestMapping("/bill_tab")
    public String billTab() {
    	return "bill_cust_margin_nt/bill_tab";
    }
    
    /**
     * 校验数据
     * @param request HttpServletRequest
     * @return  Map<String, Object>
     * @throws Exception  异常
     */
    @RequestMapping(value="/validate_data", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validateData(HttpServletRequest request)
    		throws Exception {
    	Map<String, Object> result = new HashMap<String, Object>();
    	String id = request.getParameter("id");
    	String billNo = request.getParameter("billNo");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", billNo);
		List<BillCustMarginNt> list = billCustMarginNtManager.findByBiz(null, params);
		if(list != null && list.size() != 0
				&& !list.get(0).getId().toString().equals(id)) {
			result.put("success", false);
			result.put("message", "单据编码不能重复");
			return result;
		}
		result.put("success", true);
		return result;
    }
    
    /**
	 * 审核
	 * @param request HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException 异常
	 */
	@RequestMapping("/do_audit")
	@ResponseBody
	public Boolean doAduit(HttpServletRequest request) throws ManagerException {
		try {
			String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request.getParameter("deleted");
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(idList, new TypeReference<List<Map>>() {});
			List<BillCustMarginNt> oList = convertListWithTypeReference(mapper, list, request);
			int auditVal = StringUtils.isEmpty(request.getParameter("auditVal")) 
					? FasAduitStatusEnum.ADUIT_STATUS.getValue() 
					: Integer.parseInt(request.getParameter("auditVal"));
			if(oList != null && oList.size() > 0) {
				SystemUser systemUser = CurrentUser.getCurrentUser(request);
				for(BillCustMarginNt model : oList) {
					model.setAuditStatus(auditVal);
					model.setAuditor(systemUser.getUsername());
					model.setAuditTime(new Date());
				}
				billCustMarginNtManager.doAudit(oList);
			}
			return true;
		} catch(Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}