/**
 * title:InvoiceConfirmController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:到票确认
 * auther:user
 * date:2015-4-11 上午9:09:36
 */
package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.InvoiceConfirmEnums;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.InvoiceConfirm;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.InvoiceConfirmManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/invoice_confirm/")
public class InvoiceConfirmController extends BaseCrudController<BillCommonInvoiceRegister>{
	@Resource
	private InvoiceConfirmManager invoiceConfirmManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("invoice_confirm/",
				invoiceConfirmManager);
	}
	
	@RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String flag = req.getParameter("sign");
		if (StringUtils.isNotBlank(flag)) {
			if (flag.equals("hq")) {//总部发票
				mav.setViewName("/invoice_confirm/hq/list");
			} else if (flag.equals("area")) {//地区发票
				mav.setViewName("/invoice_confirm/area/list");
			}
			
		}
		return mav;
    }
	
	/**
	 * 结算类型
	 * @return
	 */
	@RequestMapping("/balance_type")
	@ResponseBody
	public List<Map<String,String>> initAreaAmongBalanceStatus() {
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String> map=null;
		InvoiceConfirmEnums[] enumData = InvoiceConfirmEnums.values();
		for (InvoiceConfirmEnums x : enumData) {
			map=new HashMap<String, String>();
			map.put("typeNo", x.getTypeNO().toString());
			map.put("typeName", x.getTypeName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 到票确认
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateInvoice")
	@ResponseBody
	public Integer invoiceConfirm(HttpServletRequest req,@RequestParam("idList")String[] idList,@RequestParam("confirmDate")String  confirmDate) throws Exception{
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
		InvoiceConfirm invoiceConfirm=new InvoiceConfirm();
		invoiceConfirm.setAuditor(loginUser.getUsername());
		invoiceConfirm.setAuditDate(DateUtil.getdate(confirmDate));
		int	n=invoiceConfirmManager.updateById(idList,invoiceConfirm,new Integer(1));
		return n;
	}
	
	/**
	 * 反确认
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/invoice_reConfirm")
	@ResponseBody
	public Integer invoiceReConfirm(HttpServletRequest req,@RequestParam("idList")String[] idList) throws Exception{
		InvoiceConfirm invoiceConfirm=new InvoiceConfirm();
		int	n=invoiceConfirmManager.updateById(idList,invoiceConfirm,new Integer(0));
		return n;
	}
	
	/**
	 * 生成采购发票
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/generate_bill_by_invoice")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public Map<String,Object> generateBill(HttpServletRequest req, Model model) throws Exception {
		String checkList = req.getParameter("checkedRows");
		String loginName = CurrentUser.getCurrentUser(req).getUsername();
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(checkList)) {
			List<Map> list = mapper.readValue(checkList, new TypeReference<List<Map>>(){});
			List<BillCommonInvoiceRegister> oList=(List<BillCommonInvoiceRegister>)convertListWithTypeReference(mapper,list);
			resultMap = invoiceConfirmManager.generateBill(oList, loginName);
		}
		return resultMap;
	}
	
	/**
	 * 转换成泛型列表
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private List<BillCommonInvoiceRegister> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		List<BillCommonInvoiceRegister> tl=new ArrayList<BillCommonInvoiceRegister>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillCommonInvoiceRegister type=mapper.readValue(mapper.writeValueAsString(list.get(i)),BillCommonInvoiceRegister.class);
			tl.add(type);
		}
		return tl;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1: Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "": String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "": String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.invoiceConfirmManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, total, total);
		List dataList = invoiceConfirmManager.findByPage(page, sortColumn, sortOrder, params);
		for(int i=0;i<dataList.size();i++){
			((BillCommonInvoiceRegister)dataList.get(i)).setUseFlagName(null);
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}

}
