package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceBack;
import cn.wonhigh.retail.fas.manager.BillShopBalanceBackManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-22 10:14:42
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
@RequestMapping("/bill_shop_balance_back")
public class BillShopBalanceBackController extends BaseController<BillShopBalanceBack> {
	
    @Resource
    private BillShopBalanceBackManager billShopBalanceBackManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_back/",billShopBalanceBackManager);
    }
    
    /**
     * 结算单中结算差异页签中,编辑行时,查询差异回款数据
     * @param request HttpServletRequest
     * @return List<BillShopBalanceBack>
     * @throws ManagerException 异常
     */
    @RequestMapping("/lstBalanceBack")
    @ResponseBody
    public List<BillShopBalanceBack> lstBalanceBack(HttpServletRequest request) 
    		throws ManagerException {
    	String balanceNo = request.getParameter("balanceNo");
		String diffBillNo = request.getParameter("diffBillNo");
		String billNo = request.getParameter("billNo");
    	if(StringUtils.isEmpty(balanceNo) || StringUtils.isEmpty(diffBillNo) || StringUtils.isEmpty(billNo)) {
    		return new ArrayList<BillShopBalanceBack>(1);
    	}
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("balanceNo", balanceNo);
    	params.put("diffBillNo", diffBillNo);
    	params.put("billNo", billNo);
    	return billShopBalanceBackManager.findByBiz(null, params);
    }
    
    /**
     * 保存数据并且返回按结算单号、结算差异编号汇总差异回款金额
     * @param request HttpServletRequest
     * @return Map<String, BigDecimal>
     * @throws ManagerException 
     */
    @RequestMapping("/saveAndReturnAmount")
    @ResponseBody
    public Map<String, BigDecimal> saveAndReturnAmount(HttpServletRequest request) throws ManagerException {
    	Map<String, BigDecimal> params = new HashMap<String, BigDecimal>();
    	try {
    		this.save(request);
    		String balanceNo = request.getParameter("balanceNo");
    		String diffBillNo = request.getParameter("diffBillNo");
    		String diffDtlId = request.getParameter("diffDtlId");
    		if(StringUtils.isEmpty(balanceNo) || StringUtils.isEmpty(diffBillNo) || StringUtils.isEmpty(diffDtlId)) {
    			params.put("amount", BigDecimal.ZERO);
    			return params;
        	}
    		BigDecimal amount = billShopBalanceBackManager.sumBackAmount(balanceNo, diffBillNo, diffDtlId);
			params.put("amount", amount);
    	} catch(Exception e) {
    		params.put("amount", BigDecimal.ZERO);
    		logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
    	}
    	return params;
    }
}