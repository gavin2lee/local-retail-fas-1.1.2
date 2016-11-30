/**
 * title:PayableAccountController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:应付账款表
 * auther:user
 * date:2016-4-7 下午4:45:22
 */
package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.PayableAccountDto;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.PhysicalBrandManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;


@Controller
@RequestMapping("/payable_account")
@ModuleVerify("40001010")
public class PayableAccountController extends BaseCrudController<PayableAccountDto> {
	@Resource
	private PhysicalBrandManager physicalBrandManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("top_sports/payable_account/", physicalBrandManager);
	}
	
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String hqCompanyNos = financialAccountManager.findLeadRoleCompanyNos();
		String regionCompanyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(hqCompanyNos) && StringUtils.isNotEmpty(regionCompanyNos)) {
			params.put("queryCondition", "AND tmp.BUYER_NO IN ("+ hqCompanyNos + ") AND tmp.SALER_NO NOT IN ("+regionCompanyNos+")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.physicalBrandManager.findPayableAccountCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<PayableAccountDto> list = this.physicalBrandManager.findPayableAccountByPage(page, sortColumn, sortOrder, params);
//		List<PayableAccountDto> newlist =  setResults(list);
		obj.put("total", total);
		obj.put("rows", list);
//		obj.put("rows", newlist);
		return obj;
	}
	
	/**
	 * @param list
	 */
	private List<PayableAccountDto> setResults(List<PayableAccountDto> list) {
		List<PayableAccountDto> newList=new ArrayList<PayableAccountDto>();
		PayableAccountDto dto=null;
	    BigDecimal	LastBalance =new BigDecimal(0);//余额
	    BigDecimal  EarlyPayableAmount =new BigDecimal(0);//期初应付金额
		for (int i = 0; i <= list.size() - 1; i++) {
			if (i < list.size() - 1) {
				if (list.get(i).getSupplierNo().equals(list.get(i + 1).getSupplierNo())
						&& list.get(i).getBuyerNo().equals(list.get(i + 1).getBuyerNo())) {
				dto = new PayableAccountDto();
				if (i == 0) {
					dto.setEarlyPayableAmount(list.get(i).getEarlyPayableAmount());
					dto.setCurrentPayableAmount(list.get(i).getCurrentPayableAmount());
					dto.setCurrentPayment(list.get(i).getCurrentPayment());
					dto.setBalance(list.get(i).getEarlyPayableAmount().add(list.get(i).getCurrentPayableAmount()).subtract(list.get(i).getCurrentPayment()));
					LastBalance = dto.getBalance();//设置余额
				} else {
					EarlyPayableAmount=list.get(i).getEarlyPayableAmount().add(LastBalance);//设置期初应付金额
					dto.setEarlyPayableAmount(EarlyPayableAmount);
					dto.setCurrentPayableAmount(list.get(i).getCurrentPayableAmount());
					dto.setCurrentPayment(list.get(i).getCurrentPayment());
					dto.setBalance(dto.getEarlyPayableAmount().add(list.get(i).getCurrentPayableAmount()).subtract(list.get(i).getCurrentPayment()));
					LastBalance = dto.getBalance();//设置余额
				}
				dto.setBuyerNo(list.get(i).getBuyerNo());
				dto.setBuyerName(list.get(i).getBuyerName());
				dto.setSupplierNo(list.get(i).getSupplierNo());
				dto.setSupplierName(list.get(i).getSupplierName());
				dto.setBillDate(list.get(i).getBillDate());
				dto.setBillNo(list.get(i).getBillNo());
				dto.setBillTypeName(list.get(i).getBillTypeName());
				newList.add(dto);
				} else {
					dto = new PayableAccountDto();
					dto.setEarlyPayableAmount(list.get(i).getEarlyPayableAmount());
					dto.setCurrentPayableAmount(list.get(i).getCurrentPayableAmount());
					dto.setCurrentPayment(list.get(i).getCurrentPayment());
					dto.setBalance(dto.getEarlyPayableAmount().add(list.get(i).getCurrentPayableAmount()).subtract(list.get(i).getCurrentPayment()));
					dto.setBuyerNo(list.get(i).getBuyerNo());
					dto.setBuyerName(list.get(i).getBuyerName());
					dto.setSupplierNo(list.get(i).getSupplierNo());
					dto.setSupplierName(list.get(i).getSupplierName());
					dto.setBillDate(list.get(i).getBillDate());
					dto.setBillNo(list.get(i).getBillNo());
					dto.setBillTypeName(list.get(i).getBillTypeName());
					newList.add(dto);
				}
			} else {
				dto = new PayableAccountDto();
				EarlyPayableAmount=list.get(i).getEarlyPayableAmount().add(LastBalance);//设置期初应付金额
				dto.setEarlyPayableAmount(EarlyPayableAmount);
				dto.setCurrentPayableAmount(list.get(i).getCurrentPayableAmount());
				dto.setCurrentPayment(list.get(i).getCurrentPayment());
				dto.setBalance(dto.getEarlyPayableAmount().add(list.get(i).getCurrentPayableAmount()).subtract(list.get(i).getCurrentPayment()));
				dto.setBuyerNo(list.get(i).getBuyerNo());
				dto.setBuyerName(list.get(i).getBuyerName());
				dto.setSupplierNo(list.get(i).getSupplierNo());
				dto.setSupplierName(list.get(i).getSupplierName());
				dto.setBillDate(list.get(i).getBillDate());
				dto.setBillNo(list.get(i).getBillNo());
				dto.setBillTypeName(list.get(i).getBillTypeName());
				newList.add(dto);
			}
	    }
		return newList;
	}

	@RequestMapping(value = "/sum_list.json")
	@ResponseBody
	public Map<String, Object> querySumList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String hqCompanyNos = financialAccountManager.findLeadRoleCompanyNos();
		String regionCompanyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(hqCompanyNos) && StringUtils.isNotEmpty(regionCompanyNos)) {
			params.put("queryCondition", "AND tmp.BUYER_NO IN ("+ hqCompanyNos + ") AND tmp.SALER_NO NOT IN ("+regionCompanyNos+")");
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		int total = this.physicalBrandManager.findPayableAccountSumCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<PayableAccountDto> list = this.physicalBrandManager.findPayableAccountSumByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String hqCompanyNos = financialAccountManager.findLeadRoleCompanyNos();
		String regionCompanyNos = financialAccountManager.findNotLeadRoleCompanyNos();
		if (StringUtils.isNotEmpty(hqCompanyNos) && StringUtils.isNotEmpty(regionCompanyNos)) {
			params.put("queryCondition", "AND tmp.BUYER_NO IN ("+ hqCompanyNos + ") AND tmp.SALER_NO NOT IN ("+regionCompanyNos+")");
		}
		String flag = StringUtils.isEmpty(req.getParameter("flag")) ? "" : String.valueOf(req.getParameter("flag"));
		List<PayableAccountDto> dataList=new ArrayList<PayableAccountDto>();
		if(flag.equals("dtlList")){
			int total = this.physicalBrandManager.findPayableAccountCount(params);
			SimplePage page = new SimplePage(pageNo, total, (int) total);
			dataList = this.physicalBrandManager.findPayableAccountByPage(page, sortColumn, sortOrder, params);
		}else if(flag.equals("sumList")){
			int total = this.physicalBrandManager.findPayableAccountSumCount(params);
			SimplePage page = new SimplePage(pageNo, total, (int) total);
			dataList = this.physicalBrandManager.findPayableAccountSumByPage(page, sortColumn, sortOrder, params);
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
}
