/**
 * title:PayableAccountController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:验收单列表
 * auther:user
 * date:2016-4-7 下午4:45:22
 */
package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BillBuyBalanceAdditionDto;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceAdditionalManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;


@Controller
@RequestMapping("/receipt")
@ModuleVerify("34000004")
public class ReceiptController extends BaseCrudController<BillBuyBalanceAdditionDto> {
	@Resource
	private BillBuyBalanceAdditionalManager billBuyBalanceAdditionalManager;
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SupplierRateSetController.class);

	@Override
	public CrudInfo init() {
		return new CrudInfo("receipt/", billBuyBalanceAdditionalManager);
	}
	
    @RequestMapping(method = RequestMethod.GET ,value ="/list")
    public String list(HttpServletRequest req) {
    	return "ba_receipt/list";
    }
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {

		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		// 选单操作
		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			int total = this.billBuyBalanceAdditionalManager.getCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillBuyBalanceAdditionDto> list = this.billBuyBalanceAdditionalManager.findBaroqueBillByPage(page,sortColumn, sortOrder, params);
			obj.put("total", total);
			obj.put("rows", list);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return obj;
	}
}
