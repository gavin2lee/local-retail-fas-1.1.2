package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.BillBalanceInvoiceDto;
import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BalanceInvoiceApplyGenerator;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceDtl;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceSource;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceApplyManager;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceDtlManager;
import cn.wonhigh.retail.fas.manager.BillBalanceInvoiceSourceManager;
import cn.wonhigh.retail.fas.manager.BillSaleBalanceManager;
import cn.wonhigh.retail.fas.manager.BillShopBalanceManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.LookupEntryManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;
import cn.wonhigh.retail.fas.web.utils.InvoiceApplyExportUtils;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-30 11:19:51
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
@RequestMapping("/bill_balance_invoice_apply")
public class BillBalanceInvoiceApplyController extends BaseController<BillBalanceInvoiceApply> {
	@Resource
	private BillBalanceInvoiceApplyManager billBalanceInvoiceApplyManager;

	@Resource
	private BillBalanceInvoiceSourceManager billBalanceInvoiceSourceManager;

//	@Resource
//	private AreaAmongTransferManager areaAmongTransferManager;
//
//	@Resource
//	private AreaAmongBalanceManager areaAmongBalanceManager;

	@Resource
	private BillSaleBalanceManager billSaleBalanceManager;
	
    @Resource
    private BillBalanceInvoiceDtlManager billBalanceInvoiceDtlManager;

//	@Resource
//	private OrderMainApi orderMainApi;
	
	@Resource
	private OrderMainManager orderMainManager;
	
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Resource
	private BillShopBalanceManager billShopBalanceManager;
	
	@Resource
	private LookupEntryManager lookupEntryManager;
	
//	@Resource
//	private MemberOrderDtlService memberOrderDtlService;
	
//	private static final int BUSINESSTYPE = 3;
//	private static final int BUSINESSTYPES = 4;
//	private static final int STATUSSTR = 41;
//	private static final int STATUSSTRS = 30;
//	private static final int INVOICETYPE = 0;
//	private static final int INVOICETYPES = 1;
	
	@Override
	public CrudInfo init() {
		return new CrudInfo("bill_balance_invoice_apply/", billBalanceInvoiceApplyManager);
	}

	@RequestMapping("/list")
	public String list() {
		return "bill_balance_invoice_apply/invoice_apply";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public ModelAndView list(@RequestParam("balanceType") String balanceType, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		int iBalanceType = Integer.parseInt(balanceType);
		mav.setViewName("bill_balance_invoice_apply/invoice_apply");
		mav.addObject("balanceType", iBalanceType);
		return mav;
	}

	@RequestMapping("/bill_balance_invoice_apply")
	public ModelAndView bill_balanceInvoiceApply(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_invoice_apply/invoice_apply");
		String billNo = request.getParameter("billNo");
		if (StringUtils.isNotEmpty(billNo)) {
			mav.addObject("billNo", billNo);
		}
		String billNoMenu = request.getParameter("billNoMenu");
		if(StringUtils.isNotEmpty(billNoMenu)) {
			mav.addObject("billNoMenu", billNoMenu);
		}
		String warnPostUrl = request.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		String isHq = request.getParameter("isHQ");
    	if("true".equals(isHq)){
    		mav.addObject("isHQ", "true");
    	}
		return mav;
	}

	@RequestMapping("/bill_balance_invoice_info")
	public ModelAndView bill_balanceInvoiceInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_invoice_apply/invoice_info");
		String billNo = request.getParameter("billNo");
		String balanceNo = request.getParameter("balanceNo");
		if (StringUtils.isNotEmpty(billNo)) {
			mav.addObject("billNo", billNo);
		}
		if (StringUtils.isNotEmpty(balanceNo)) {
			mav.addObject("balanceNo", balanceNo);
		}
		return mav;
	}

	// 基本信息(明细页面)
	@RequestMapping("/bill_balance_invoice_base")
	public ModelAndView bill_balanceInvoiceBase(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_invoice_apply/invoice_base");
		String billNo = request.getParameter("billNo");
		if (StringUtils.isNotEmpty(billNo)) {
			mav.addObject("billNo", billNo);
		}
		return mav;
	}

	// 源单信息
	@RequestMapping("/bill_balance_invoice_source")
	public ModelAndView bill_balanceInvoiceSource(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_invoice_apply/invoice_source");
		String billNo = request.getParameter("billNo");
		if (StringUtils.isNotEmpty(billNo)) {
			mav.addObject("billNo", billNo);
		}
		return mav;
	}

	/**
	 * 点击菜单打开主页面的跳转方法
	 * @param request
	 * @return
	 */
	@RequestMapping("/bill_balance_invoice_list")
	public ModelAndView bill_balanceInvoiceList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_invoice_apply/list_tabMain");
		String billNo = request.getParameter("billNo");
		if (StringUtils.isNotEmpty(billNo)) {
			mav.addObject("billNo", billNo);
		}
		return mav;
	}

	@RequestMapping("/bill_balance_invoice_cate")
	public ModelAndView bill_balanceInvoiceCate(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_invoice_apply/invoice_cateinfo");
		String billNo = request.getParameter("billNo");
		if (StringUtils.isNotEmpty(billNo)) {
			mav.addObject("billNo", billNo);
		}
		return mav;
	}

	@RequestMapping("/bill_balance_invoice_dtl")
	public ModelAndView bill_balanceInvoiceDtl(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bill_balance_invoice_apply/invoice_dtlinfo");
		String billNo = request.getParameter("billNo");
		if (StringUtils.isNotEmpty(billNo)) {
			mav.addObject("billNo", billNo);
		}
		return mav;
	}

	/**
	 * 删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/deleteInvoiceApply")
	@ResponseBody
	public Integer remove(@RequestParam("idList") String strIds) throws Exception {
		String[] ids = strIds.split(";");
		return billBalanceInvoiceApplyManager.remove(ids);
	}
	
	/**
	 * 删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/deleteOn")
	@ResponseBody
	public Integer deleteOn(@RequestParam("id") String id, @RequestParam("billNo") String billNo, @RequestParam("balanceType") String balanceType) throws Exception {
		return billBalanceInvoiceApplyManager.deleteOn(id, billNo, balanceType);
	}
	
	/**
	 * 根据单号获取团购/员购订单号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOrderBillNo")
	@ResponseBody
	public String getOrderBillNo(HttpServletRequest request){
		String balanceNoStr = "";
		String billNo = StringUtils.isEmpty(request.getParameter("billNo")) ? "" : String.valueOf(request
				.getParameter("billNo"));
		
		String balanceType = StringUtils.isEmpty(request.getParameter("balanceType")) ? "" : String.valueOf(request
				.getParameter("balanceType"));
		
		if(StringUtils.isNotEmpty(billNo) && StringUtils.isNotEmpty(balanceType)){
//			balanceNoStr = billBalanceInvoiceApplyManager.getOrderBillNo(billNo, balanceType, "");
		}
		return balanceNoStr;
	}

	/**
	 * 查询明细
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getBillSaleBalanceList")
	@ResponseBody
	public Map<String, Object> getBillSaleBalanceDtl(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
//		String balanceNo = StringUtils.isEmpty(req.getParameter("balanceNo")) ? "" : String.valueOf(req
//				.getParameter("balanceNo"));
		String billNo = StringUtils.isEmpty(req.getParameter("billNo")) ? "" : String.valueOf(req
				.getParameter("billNo"));
		Map<String, Object> obj = new HashMap<String, Object>();
		if (null != billNo && "" != billNo) {
			List<BillBalanceInvoiceSource> invoiceSource;
			Map<String, Object> buyerparams = new HashMap<String, Object>();
			buyerparams.put("billNo", billNo);
			invoiceSource = billBalanceInvoiceSourceManager.findByBiz(null, buyerparams);

			Map<String, Object> params = new HashMap<String, Object>();//builderParams(req, model);
			if (invoiceSource.size() > 0) {
				params.put("balanceNo", invoiceSource.get(0).getBalanceNo());//结算单编号
			}
			//		params.put("billNo", billNo);
			int total = this.billSaleBalanceManager.findCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillSaleBalance> list = this.billBalanceInvoiceApplyManager.getBillSaleBalanceDtl(page, sortColumn,
					sortOrder, params);
			//		List<TotalDto> totalList=areaAmongBalanceManager.findTransferDtlTotal(params);
			obj.put("total", total);
			obj.put("rows", list);
		} else {
			obj.put("total", 0);
			obj.put("rows", null);
		}
		return obj;
	}

	/**
	 * 按大类汇总
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getBillSaleBalanceSum")
	@ResponseBody
	public Map<String, Object> getBillSaleBalanceSum(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
//		String balanceNo = StringUtils.isEmpty(req.getParameter("balanceNo")) ? "" : String.valueOf(req
//				.getParameter("balanceNo"));
		String billNo = StringUtils.isEmpty(req.getParameter("billNo")) ? "" : String.valueOf(req
				.getParameter("billNo"));
		Map<String, Object> obj = new HashMap<String, Object>();
		if (null != billNo && "" != billNo) {
			List<BillBalanceInvoiceSource> invoiceSource;
			Map<String, Object> buyerparams = new HashMap<String, Object>();
			buyerparams.put("billNo", billNo);
			invoiceSource = billBalanceInvoiceSourceManager.findByBiz(null, buyerparams);

			Map<String, Object> params = new HashMap<String, Object>();// builderParams(req, model);
			if (invoiceSource.size() > 0) {
				params.put("balanceNo", invoiceSource.get(0).getBalanceNo());//结算单编号
			}
			//		params.put("billNo", billNo);
			int total = this.billBalanceInvoiceApplyManager.selectBillSaleBalanceCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<BillSaleBalance> list = this.billBalanceInvoiceApplyManager.getBillSaleBalanceSum(page, sortColumn,
					sortOrder, params);
			//		List<TotalDto> totalList=areaAmongBalanceManager.findTransferDtlTotal(params);
			obj.put("total", total);
			obj.put("rows", list);
		} else {
			obj.put("total", 0);
			obj.put("rows", null);
		}
		return obj;
	}

	/**
	 * 转到查询结算单信息
	 * @return
	 */
	@RequestMapping("/toSearchBillBalance")
	public ModelAndView toSearchBillBalance(HttpServletRequest req) {
		String invoiceType = StringUtils.isEmpty(req.getParameter("invoiceType")) ? "" : String.valueOf(req
				.getParameter("invoiceType"));
		ModelAndView view = new ModelAndView();
		view.setViewName("base_setting/bill_balance/searchBillBalance");
		view.addObject("invoiceType", invoiceType);
		return view;
	}

	/**
	 * 转到查询需要开票的内购订单信息
	 * @return
	 */
	@RequestMapping("/toSearchOrderBillBalance")
	public ModelAndView toSearchOrderBillBalance(HttpServletRequest req) {
		String businessTypeStr = StringUtils.isEmpty(req.getParameter("businessTypeStr")) ? "" : String.valueOf(req
				.getParameter("businessTypeStr"));
		ModelAndView view = new ModelAndView();
		view.setViewName("base_setting/bill_balance/searchOrderBillShopBalance");
		view.addObject("businessTypeStr", businessTypeStr);
		return view;
	}

	/**
	 * 转到查询商场结算单信息
	 * @return
	 */
	@RequestMapping("/toSearchBillShopBalance")
	public ModelAndView toSearchBillShopBalance(HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		view.setViewName("base_setting/bill_balance/searchBillShopBalance");
		return view;
	}
	
	/**
	 * 转到客户查询界面
	 * @return
	 */
	@RequestMapping("/toSearchInvoiceInfo")
	public ModelAndView toSearchInvoiceInfo(HttpServletRequest req) {
		String companyNo = StringUtils.isEmpty(req.getParameter("companyNo")) ? "" : String.valueOf(req
				.getParameter("companyNo"));
		ModelAndView view = new ModelAndView();
		view.setViewName("base_setting/invoiceInfo/searchInvoiceInfo");
		view.addObject("companyNo", companyNo);
		return view;
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
	public void export(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		List dataList = null;
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		String dataListTemp = req.getParameter("dataList");
		if(StringUtils.isNotEmpty(dataListTemp) && dataListTemp.length()>2){
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(dataListTemp, new TypeReference<List<Map>>(){});
			dataList = convertListWithTypeReference(mapper, list, BillBalanceInvoiceApply.class);
		}else{
			int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null ? "0" : req.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(req.getParameter("pageSize") == null ? "0" : req.getParameter("pageSize"));
			Map<String, Object> params = builderParams(req, model);
			params.remove("exportColumns");
			params.remove("dataList");
			SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
			page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
			dataList = billBalanceInvoiceApplyManager.getByBalanceNo(page, "", "", params);
		}
		List<BillBalanceInvoiceDto> thList=new ArrayList<BillBalanceInvoiceDto>();
		List<BillBalanceInvoiceDto> dataLists = getInvoiceApplyList(dataList,req,thList);
		fileName+="("+dataLists.get(0).getFullName()+")";
		InvoiceApplyExportUtils.doExport(fileName, exportColumns, dataLists, response,thList);
	}
	
	//按开票方分组
	public List<BillBalanceInvoiceDto> getInvoiceApplyList(List invoiceApplyList,HttpServletRequest req,List<BillBalanceInvoiceDto> thList){
		List<BillBalanceInvoiceDto> returnList = new ArrayList<BillBalanceInvoiceDto>();
		List<BillBalanceInvoiceDtl> invoiceApplyDtlList = null;
		BillBalanceInvoiceApply apply = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupBy", "brand_no,saler_name");
		for (int i = 0; i < invoiceApplyList.size(); i++) {
			apply=(BillBalanceInvoiceApply)invoiceApplyList.get(i);
			params.put("billNo", apply.getBillNo());
			invoiceApplyDtlList = billBalanceInvoiceDtlManager.selectByGroupByParamExport(params);
			constructInvoiceApplyInfo(returnList,apply,invoiceApplyDtlList);
		}
		ListSortUtil<BillBalanceInvoiceDto> sortList = new ListSortUtil<BillBalanceInvoiceDto>(); 
		sortList.sort(returnList,"salerNo","desc");
		return subTotal(returnList,req,thList);
	}
	
	/**
	 * 要先对集合进行排序
	 * 根据synsetId在集合中进行分组
	 * @return
	 */
	public List<BillBalanceInvoiceDto> subTotal(List<BillBalanceInvoiceDto> list,HttpServletRequest req,List<BillBalanceInvoiceDto> thList){
		  //返回的处理好的集合对象
		  List<BillBalanceInvoiceDto> newSynList = new ArrayList<BillBalanceInvoiceDto>();
		  //定义一个map集合用于分组
		  Map<String, List<BillBalanceInvoiceDto>> mapList = new HashMap<String, List<BillBalanceInvoiceDto>>();
		  //分组数据
		  List<BillBalanceInvoiceDto> groupList = null;
		  //遍历集合以Synset_id为键，以chinese为值保存到mapList中
		  for (Iterator<BillBalanceInvoiceDto> it = list.iterator(); it.hasNext();){
			   //将循环读取的结果放入对象中
			  BillBalanceInvoiceDto synsetcn = (BillBalanceInvoiceDto) it.next();
			   //按开票名称
			   if (mapList.containsKey(synsetcn.getBuyerNo())) {
				   groupList.add(synsetcn);
			   //如果没有包含相同的键，在创建一个集合保存数据
			   } else {
					groupList = new ArrayList<BillBalanceInvoiceDto>();
					groupList.add(synsetcn);
					mapList.put(synsetcn.getBuyerNo(), groupList);
			   }
		  }
		  //遍历map集合
		  if(mapList.size()<=1){
			  getSumTotal(list);
			  addTextInfo(list,list.get(0),thList);
			  addTextDtl(list,list.get(0),req); 	
			  return list;
		  }
		  for (Map.Entry<String, List<BillBalanceInvoiceDto>> m : mapList.entrySet()){
				//获取所有的值
				List<BillBalanceInvoiceDto> synList = m.getValue();
				getSumTotal(synList);
				addTextInfo(synList,synList.get(0),thList);
				addTextDtl(synList,synList.get(0),req);
				newSynList.addAll(synList);
		  }
		  return newSynList;
	} 
	
	//添加导出列表描述信息
	private void addTextDtl(List<BillBalanceInvoiceDto> dataLists,BillBalanceInvoiceDto apply,HttpServletRequest request){
		SystemUser user = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
		BillBalanceInvoiceDto dto1 = new BillBalanceInvoiceDto();
		dto1.setShortName("开票名称："); 
		dto1.setCategoryName(apply.getInvoiceName());
		dataLists.add(dto1);
		BillBalanceInvoiceDto dto2 = new BillBalanceInvoiceDto();
		/*dto2.setShortName("纳税人识别号：");*/
		dto2.setShortName("地址电话:");
		dto2.setRemark("地区财务:"+user.getUsername());
		String buyerAddress ="";
		String buyertel ="";
		if(StringUtils.isNotEmpty(apply.getBuyerAddress())){
			buyerAddress = apply.getBuyerAddress();
		}
		if(StringUtils.isNotEmpty(apply.getTel())){
			buyertel = apply.getTel();
		}
		/*if(StringUtils.isNotEmpty(apply.getTaxRegistryNo())){
			dto2.setCategoryName(apply.getTaxRegistryNo().toString()+"");
		}*/
		dto2.setCategoryName(buyerAddress+buyertel+"");
		dataLists.add(dto2);
		BillBalanceInvoiceDto dto3 = new BillBalanceInvoiceDto();
		dto3.setRemark("深圳财务:");
		dto3.setShortName("开户行及账号：");
		String bankName = "";
		String bankAccount ="";
		if(StringUtils.isNotEmpty(apply.getBankName())){
			bankName = apply.getBankName();
		}
		if(StringUtils.isNotEmpty(apply.getBankAccount())){
			bankAccount = apply.getBankAccount();
		}
		dto3.setCategoryName(bankName+bankAccount+"");
		/*dto3.setShortName("地址电话:");
		String buyerAddress ="";
		String buyertel ="";
		if(StringUtils.isNotEmpty(apply.getBuyerAddress())){
			buyerAddress = apply.getBuyerAddress();
		}
		if(StringUtils.isNotEmpty(apply.getBuyerTel())){
			buyertel = apply.getBuyerTel();
		}
		dto3.setCategoryName(buyerAddress+buyertel+"");*/
		dataLists.add(dto3);
		BillBalanceInvoiceDto dto4 = new BillBalanceInvoiceDto();
		/*dto4.setShortName("开户行及账号：");
		String bankName = "";
		String bankAccount ="";
		if(StringUtils.isNotEmpty(apply.getBankName())){
			bankName = apply.getBankName();
		}
		if(StringUtils.isNotEmpty(apply.getBankAccount())){
			bankAccount = apply.getBankAccount();
		}
		dto4.setCategoryName(bankName+bankAccount+"");
		dataLists.add(dto4);*/
		BillBalanceInvoiceDto dto5 = new BillBalanceInvoiceDto();
		dto5.setShortName("邮寄地址：");
		if(StringUtils.isNotEmpty(apply.getMailingAddress())){
			dto5.setCategoryName(apply.getMailingAddress());
		}
		dataLists.add(dto5);
		BillBalanceInvoiceDto dto6 = new BillBalanceInvoiceDto();
		dto6.setShortName("");
		dto6.setCategoryName("");
		dataLists.add(dto6);
	}
	
	//添加明细
	public void  addTextInfo(List<BillBalanceInvoiceDto> dataLists,BillBalanceInvoiceDto apply,List<BillBalanceInvoiceDto> thList){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		BillBalanceInvoiceDto thDate=new BillBalanceInvoiceDto();
		String invoiceDate ="";
		String postPayDate ="";
		/*BillBalanceInvoiceDto dto2 = new BillBalanceInvoiceDto();
		dto2.setShortName("申请开票日期：");
		dto2.setBuyerName("商场交票日期：");*/
		if(apply.getInvoiceApplyDate()!=null){
			invoiceDate=format.format(apply.getInvoiceApplyDate()).toString();
		}
		if(apply.getPostPayDate()!=null){
			postPayDate=format.format(apply.getPostPayDate()).toString();
		}
		/*dto2.setCategoryName(invoiceDate);
		dto2.setBrandName(postPayDate);
		dto2.setTaxRegistryNo(apply.getTaxRegistryNo()+"");*/
		thDate.setBuyerName(invoiceDate);
		thDate.setCategoryName(postPayDate);
		thDate.setTaxRegistryNo(apply.getTaxRegistryNo()+"");
		/*dataLists.add(dto2);*/
		/*BillBalanceInvoiceDto dto1 = new BillBalanceInvoiceDto();
		dto1.setShortName("发票类型：");
		dto1.setCategoryName(apply.getFullName());
		dataLists.add(dto1);*/
		thList.add(thDate);
	}
	
	//合计
	public void getSumTotal(List<BillBalanceInvoiceDto> synList){
		BillBalanceInvoiceDto dto=new BillBalanceInvoiceDto();
		BillBalanceInvoiceDto dto1=new BillBalanceInvoiceDto();
		int qty = 0;
		BigDecimal sendAmount =new BigDecimal(0);
		BigDecimal posEarningAmount =new BigDecimal(0);
		for (int i = 0; i < synList.size(); i++) {
			BillBalanceInvoiceDto dtoTemp=synList.get(i);
			if(dtoTemp.getQty()!=null){
				qty+=dtoTemp.getQty();
			}
			if(dtoTemp.getAmount()!=null){
				sendAmount=sendAmount.add(dtoTemp.getAmount());
			}
			if(dtoTemp.getPosEarningAmount()!=null){
				posEarningAmount=posEarningAmount.add(dtoTemp.getPosEarningAmount());
			}
		}
		Date start = null;
		Date end =  null;
		if(synList.size()>1){
			start = synList.get(0).getBalanceStartDate();
			end = synList.get(0).getBalanceEndDate();
		}
		dto.setShortName("合计:");
		dto.setQty(qty);
		dto.setBalanceStartDate(start);
		dto.setBalanceEndDate(end);
		dto.setPosEarningAmount(posEarningAmount);
		dto.setAmount(sendAmount);
		synList.add(dto);
		dto1.setShortName("备注:");
		synList.add(dto1);
	}
	
	/** 
	 * List按照指定字段排序工具类 
	 * 
	 * @param <T> 
	 */  
	public class ListSortUtil<T> {  
		  /** 
	     * @param targetList 目标排序List 
	     * @param sortField 排序字段(实体类属性名) 
	     * @param sortMode 排序方式（asc or  desc） 
	     */  
	    @SuppressWarnings({ "unchecked", "rawtypes" })  
	    public void sort(List<T> targetList, final String sortField, final String sortMode) {  
	        Collections.sort(targetList, new Comparator() {  
	            public int compare(Object obj1, Object obj2) {   
	                int retVal = 0;  
	                try {  
	                    //首字母转大写  
	                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
	                    String methodStr="get"+newStr;  
	                    Method method1 = ((T)obj1).getClass().getMethod(methodStr, null);  
	                    Method method2 = ((T)obj2).getClass().getMethod(methodStr, null);  
	                    if (sortMode != null && "desc".equals(sortMode)) {  
	                        retVal = method2.invoke(((T) obj2), null).toString().compareTo(method1.invoke(((T) obj1), null).toString()); // 倒序  
	                    } else {  
	                        retVal = method1.invoke(((T) obj1), null).toString().compareTo(method2.invoke(((T) obj2), null).toString()); // 正序  
	                    }  
	                } catch (Exception e) {  
	                    throw new RuntimeException();  
	                }  
	                return retVal;  
	            }  
	        });  
	    }  
	}
	public BillBalanceInvoiceDto getBillBalanceInvoiceInfo(BillBalanceInvoiceDto dto,BillBalanceInvoiceApply apply){
		dto = new BillBalanceInvoiceDto();
		dto.setInvoiceType(apply.getInvoiceType());
		String invoiceTypeStr="";
		if(apply.getInvoiceType()==1){
			invoiceTypeStr="增值票";
		}else if(apply.getInvoiceType()==0){
			invoiceTypeStr="普通发票";
		}
		dto.setMailingAddress(apply.getMailingAddress()); 
		dto.setBankAccount(apply.getBankAccount()); 
		dto.setBankAccountName(apply.getBankAccountName()); 
		dto.setContactName(apply.getContactName()); 
		dto.setTel(apply.getTel()); 
		dto.setBalanceType(apply.getBalanceType());    
		dto.setFullName(invoiceTypeStr);  //发票类型
		dto.setBillNo(apply.getBillNo());
		dto.setStatusName(apply.getStatusName());
		dto.setInvoiceApplyDate(apply.getInvoiceApplyDate());
		dto.setInvoiceName(apply.getInvoiceName());
		dto.setSalerName(apply.getSalerName());
		dto.setBuyerName(apply.getBuyerName());
		dto.setSalerNo(apply.getSalerNo());
		dto.setBuyerNo(apply.getBuyerNo());
		dto.setPostPayDate(apply.getPostPayDate());
		dto.setAmount(apply.getAmount());
		dto.setCurrencyName(apply.getCurrencyName());
		dto.setTaxRegistryNo(apply.getTaxRegistryNo());
		dto.setRemark(apply.getRemark());
		dto.setQty(0);
		dto.setPosEarningAmount(new BigDecimal(0));
		return dto;
	}
	
	/**
	 * @param returnList
	 * @param apply
	 * @param invoiceDtlList
	 */
	public void constructInvoiceApplyInfo(List<BillBalanceInvoiceDto> returnList,BillBalanceInvoiceApply apply,
			List<BillBalanceInvoiceDtl> invoiceDtlList){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		BillBalanceInvoiceDto dto = null;
		if(invoiceDtlList.size()<1){
			returnList.add(getBillBalanceInvoiceInfo(dto,apply));
		}
		for (int i = 0; i < invoiceDtlList.size(); i++) {
			dto=getBillBalanceInvoiceInfo(dto,apply);
			BillBalanceInvoiceDtl dtl=(BillBalanceInvoiceDtl)invoiceDtlList.get(i);
			String start= "";
			String end= "";
			if(dtl.getSendAmount()!=null){
				dto.setAmount(dtl.getSendAmount());
			}
			if(dtl.getBalanceStartDate()!=null){
				start =format.format(dtl.getBalanceStartDate()).toString();
			}
			if(dtl.getBalanceStartDate()!=null){
				end =format.format(dtl.getBalanceEndDate()).toString();
			}
			dto.setBuyerAddress(apply.getBuyerAddress());
			dto.setBuyerTel(apply.getBuyerTel());
			dto.setBankName(apply.getBankName());
			dto.setCategoryName(start+"-"+end);
			dto.setBalanceStartDate(dtl.getBalanceStartDate());
			dto.setBalanceEndDate(dtl.getBalanceEndDate());
			dto.setShopNo(dtl.getShopNo());
          	dto.setShortName(dtl.getShortName());
		  	//dto.setFullName(dtl.getFullName());
		  	dto.setRetailType(dtl.getRetailType());
		  	String brandName="";
		  	String salerName="";
		  	if(StringUtils.isNotEmpty(dtl.getBrandName())){
		  		brandName=dtl.getBrandName();
		  	}
			if(StringUtils.isNotEmpty(dtl.getCategoryName())){
				salerName=dtl.getSalerName();
		  	}
			dto.setBrandName(brandName+salerName);
			dto.setBrandNo(dtl.getBrandNo());
			//dto.setCategoryName(dtl.getCategoryName());
			dto.setCategoryNo(dtl.getCategoryNo());
          	dto.setOrganName(dtl.getOrganName());
          	dto.setOrganNo(dtl.getOrganNo());
          	int qty = 0;
          	if(dtl.getQty()!=null){
          		qty=dtl.getQty();
			}
			dto.setQty(qty);
			//dto.setSendAmount(dtl.getSendAmount());
			dto.setTaxRate(dtl.getTaxRate());
			dto.setTaxAmount(dtl.getTaxAmount());
			dto.setNoTaxAmount(dtl.getNoTaxAmount());
			BigDecimal estimatedAmount = new BigDecimal(0);
			BigDecimal postAmount = new BigDecimal(0);
			BigDecimal contractRate = new BigDecimal(0);
			BigDecimal actualRate = new BigDecimal(0);
			BigDecimal costTotal = new BigDecimal(0);
			if(dtl.getEstimatedAmount()!=null){
				estimatedAmount=dtl.getEstimatedAmount();
			}
			dto.setEstimatedAmount(estimatedAmount);
			if(dtl.getPosEarningAmount()!=null){
				postAmount=dtl.getPosEarningAmount();
			}
			dto.setPosEarningAmount(postAmount);
			if(dtl.getContractRate()!=null){
				contractRate=dtl.getContractRate();
			}
			dto.setContractRate(contractRate);
			if(dtl.getActualRate()!=null){
				actualRate=dtl.getActualRate();
			}
			dto.setActualRate(actualRate);
			if(dtl.getCostTotal()!=null){
				costTotal=dtl.getCostTotal();
			}
			dto.setCostTotal(costTotal);
			returnList.add(dto);
		}
	}
	
	/**
	 * 导出开票申请单明细信息
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportInvoicDdtl")
	public void exportInvoicDdtl(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		String billNo = req.getParameter("billNo");
		String showType = req.getParameter("showType");
		Map<String, Object> params = new HashMap<String, Object>();
		List<BillBalanceInvoiceDtl> dataList = new ArrayList<BillBalanceInvoiceDtl>();
		if(StringUtils.isEmpty(showType)){
			params.put("billNo", billNo);
			dataList = billBalanceInvoiceDtlManager.findByBiz(null, params);
		}else if(showType.equals("1")){
	   		params.put("billNo", billNo);
	   		params.put("groupBy", "brand_no");
	   		dataList = billBalanceInvoiceDtlManager.selectByGroupByParams(params);
		}else if(showType.equals("2")){
	   		params.put("billNo", billNo);
	   		params.put("groupBy", "category_no");
	   		dataList = billBalanceInvoiceDtlManager.selectByGroupByParams(params);
		}else{
	   		params.put("billNo", billNo);
	   		params.put("groupBy", "brand_no,category_no");
	   		dataList = billBalanceInvoiceDtlManager.selectByGroupByParams(params);
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}

	/**
	 * 结算单处直接生成开票申请操作；新增开票申请信息
	 * @param model 待新增的实体对象
	 * @param request HttpServletRequest
	 * @return Boolean
	 * @throws ManagerException 
	 */
	@RequestMapping(value = "/addBillBalanceInvoiceApply", method = RequestMethod.POST)
	@ResponseBody
	public List<BillBalanceInvoiceApply> addBillBalanceInvoiceApply(BillBalanceInvoiceApply model, HttpServletRequest request) throws ManagerException {
		SystemUser currentUser = CurrentUser.getCurrentUser(request);
		List<BillBalanceInvoiceApply> returnList = new ArrayList<BillBalanceInvoiceApply>();
		try{
			if(StringUtils.isNotBlank(request.getParameter("checkedRows")) && request.getParameter("checkedRows") != null){
				ObjectMapper mapper = new ObjectMapper();
				@SuppressWarnings("rawtypes")
				List<Map> list = mapper.readValue(request.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
				List<Object> lstItem = convertListWithTypeReference(mapper, list, BillBalanceInvoiceApply.class);
				for (Object object : lstItem) {
					BillBalanceInvoiceApply invoiceApply = null;
					BillBalanceInvoiceApply dto = (BillBalanceInvoiceApply)object;
					dto.setCreateUser(currentUser.getUsername());
					invoiceApply = billBalanceInvoiceApplyManager.addBalanceInvoiceApply(dto);
					
					returnList.add(invoiceApply);
				}
			}
		}catch(Exception e){
			BillBalanceInvoiceApply invoiceApply = new BillBalanceInvoiceApply();
			invoiceApply.setErrorInfo("系统异常，单据保存失败。");
			returnList.add(invoiceApply);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return returnList;
//		String msg = "";
//		BillBalanceInvoiceApply invoiceApply = null;
//		try {
//			model.setCreateUser(currentUser.getUsername());
//			invoiceApply = billBalanceInvoiceApplyManager.addBillBalanceInvoiceApply(model);
//			if(null != invoiceApply){
//				msg = invoiceApply.getBalanceNo() + "单据保存成功！开票申请号："+invoiceApply.getBillNo();
//			}else{
//				msg = model.getBalanceNo() + "单据保存失败，请完善开票信息。";
//			}
//		} catch (Exception e) {
//			msg = model.getBalanceNo() + "单据保存失败。";
////			invoiceApply = new BillBalanceInvoiceApply();
//		}
//		return msg;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Object> convertListWithTypeReference(ObjectMapper mapper, List<Map> list, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}

	/**
	 * 新增或修改
	 * @param req
	 * @param billReceipt
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/saveOrUpdate")
	public ResponseEntity<BillBalanceInvoiceApply> saveOrUpdate(HttpServletRequest req, BillBalanceInvoiceApply model) throws ManagerException {
		try{
			SystemUser currentUser = CurrentUser.getCurrentUser(req);
			//如果单据编号不为空，则修改，否则就新增
			if (StringUtils.isNotEmpty(model.getBillNo())) {
				model.setUpdateUser(currentUser.getUsername());
				model.setUpdateTime(new Date());
				int ret = billBalanceInvoiceApplyManager.modifyById(model);
				model = billBalanceInvoiceApplyManager.findById(model);
				if(ret == -1){
					model.setErrorInfo("保存失败，请检查开票方及收票方是否维护了开票信息。");
				}
			} else {
				model.setCreateUser(currentUser.getUsername());
				List<Object> lstItem = null;
				if(StringUtils.isNotBlank(req.getParameter("checkRows")) && req.getParameter("checkRows") != null){
					ObjectMapper mapper = new ObjectMapper();
					@SuppressWarnings("rawtypes")
					List<Map> list = mapper.readValue(req.getParameter("checkRows"), new TypeReference<List<Map>>() {});
					lstItem = convertListWithTypeReference(mapper, list, BalanceInvoiceApplyGenerator.class);
				}
				model = billBalanceInvoiceApplyManager.addInvoiceApply(model,lstItem);
				//			//绑定页面数据
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return new ResponseEntity<BillBalanceInvoiceApply>(model, HttpStatus.OK);
	}

	/**
	 * 针对发票登记查询开票申请信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getByPage")
	@ResponseBody
	public Map<String, Object> getByPage(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);

		String brandNo = params.get("brandNo") == null ? "" : params.get("brandNo").toString();
   		List<String> brandNoList = new ArrayList<String>();
   		if (StringUtils.isNotBlank(brandNo)) {
   			if (brandNo.contains(",")) {
   				String[] brandNos = brandNo.split(",");
   				for (String brandNoTemp : brandNos) {
   					brandNoList.add(brandNoTemp);
   				}
   			} else {
   				brandNoList.add(brandNo);
   			}

   			params.put("brandNoList", brandNoList);
   		}
		int total = this.billBalanceInvoiceApplyManager.getCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<BillBalanceInvoiceApply> list = this.billBalanceInvoiceApplyManager.getByPage(page, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

	/**
	 * 根据店铺信息获取订单信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getOrderBillBalance")
	@ResponseBody
	public Map<String, Object> getOrderBillBalance(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		
		Map<String, Object> params = builderParams(req, model);
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<BalanceInvoiceApplyGenerator> list = new ArrayList<BalanceInvoiceApplyGenerator>();
		
		params.put("queryType", 3);//表示GMS、POS 单据合并查询
		int total = orderMainManager.findApplyGeneratorDetailCount(params);
		if(total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = orderMainManager.findApplyGeneratorDetail(page, params);
		}
		obj.put("rows", list);
		obj.put("total", total);
		return obj;
	}
	
	/**
	 * 查看明细时，内购结算类型根据发票申请号获取订单明细信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getOrderBillBalanceByInvoiceNo")
	@ResponseBody
	public Map<String, Object> getOrderBillBalanceByInvoiceNo(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
			int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
			String billNo = req.getParameter("billNo") == null ? "" : req.getParameter("billNo");
	//		String businessTypeStr = StringUtils.isEmpty(req.getParameter("businessTypeStr")) ? "" : String.valueOf(req.getParameter("businessTypeStr"));
	//		String invoiceNo = req.getParameter("invoiceNo") == null ? "" : req.getParameter("invoiceNo");
			Map<String, Object> params = builderParams(req, model);
			int total = 0;
			
			
			List<ItemSaleDtlDto> dtlList = new ArrayList<ItemSaleDtlDto>();
			List<BalanceInvoiceApplyGenerator> list = new ArrayList<BalanceInvoiceApplyGenerator>();
			params.put("invoiceApplyNo", billNo);
			params.put("queryType", 3);//表示GMS、POS 单据合并查询
			total = orderMainManager.findApplyGeneratorDetailCount(params);
			if(total > 0) {
				SimplePage page = new SimplePage(pageNo, pageSize, total);
				list = orderMainManager.findApplyGeneratorDetail(page, params);
			}
			// 对象转换
			converDateToSaleItem(dtlList,list);
			obj.put("rows", dtlList);
			obj.put("total", total);
		}catch(ManagerException e){
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		} catch (RpcException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return obj;
	}
	
	/**
	 * 转化明细属性值
	 * @param dtlList
	 * @param list
	 * @throws RpcException 
	 */
	private void converDateToSaleItem(List<ItemSaleDtlDto> dtlList,List<BalanceInvoiceApplyGenerator> list) throws RpcException{
		if(CollectionUtils.isNotEmpty(list)){
			for (BalanceInvoiceApplyGenerator apply : list) {
				ItemSaleDtlDto itemSaleDtl = new ItemSaleDtlDto();
				itemSaleDtl.setTagPrice(apply.getTagPrice());
				itemSaleDtl.setShopNo(apply.getShopNo());
				itemSaleDtl.setShopName(apply.getShopName());
				itemSaleDtl.setBillNo(apply.getBillNo());
				itemSaleDtl.setOutDate(apply.getBalanceDate());
				itemSaleDtl.setItemCode(apply.getItemCode());
				itemSaleDtl.setItemName(apply.getItemName());
				itemSaleDtl.setTagPrice(apply.getTagPrice());
				itemSaleDtl.setSalePrice(apply.getSalePrice());
				itemSaleDtl.setQty(apply.getQty());
				itemSaleDtl.setSettlePrice(apply.getCost());
				itemSaleDtl.setAmount(apply.getAmount());
				itemSaleDtl.setProName(apply.getProName());
				itemSaleDtl.setDiscount(apply.getDiscount());
				itemSaleDtl.setHeadquarterCost(apply.getHeadquareCost());
				itemSaleDtl.setRegionCost(apply.getAreaCost());
				itemSaleDtl.setUnitCost(apply.getPrimeCost());
				dtlList.add(itemSaleDtl);
			}
		}
	}
	
	/**
	 * 根据结算单号获取开票申请列表信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getByBalanceNo.json")
	@ResponseBody
	public  Map<String, Object> getByBalanceNo(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billBalanceInvoiceApplyManager.getCountByBalanceNo(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBalanceInvoiceApply> list = this.billBalanceInvoiceApplyManager.getByBalanceNo(page, sortColumn, sortOrder, params);
		
		if(CollectionUtils.isNotEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("billNo", list.get(i).getBillNo());
				List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceManager.findByBiz(null, paramMap);
				
				String balanceNoStr = "";
				if(CollectionUtils.isNotEmpty(sourceList)){
					for (int j = 0; j < sourceList.size(); j++) {
						if(StringUtils.isNotEmpty(sourceList.get(j).getBalanceNo())){
							balanceNoStr += sourceList.get(j).getBalanceNo()+",";
						}
					}
					if(balanceNoStr.length() > 0){
						list.get(i).setBalanceNo(balanceNoStr.substring(0, balanceNoStr.length()-1));
					}
				}
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
//	/**
//	 * 根据开票申请号获取订单
//	 * @param req
//	 * @param model
//	 * @return
//	 * @throws ManagerException
//	 */
//	@RequestMapping(value = "/getOrderNoByApplyNo")
//	@ResponseBody
//	public String getOrderNoByApplyNo(HttpServletRequest req, Model model) throws ManagerException {
//		String billNo = StringUtils.isEmpty(req.getParameter("billNo")) ? "" : String.valueOf(req.getParameter("billNo"));
//		
//		String billNoStr = "";
//		
//		if(StringUtils.isNotEmpty(billNo)){
//			billNoStr = billBalanceInvoiceApplyManager.getOrderBillNo(billNo, String.valueOf(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()), "");
//		}
//		
//		return billNoStr;
//	}
	
	/**
	 * 获取结算单号
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getSaleOrderNoByApplyNo")
	@ResponseBody
	public String getSaleOrderNoByApplyNo(HttpServletRequest req, Model model) throws ManagerException {
		String billNo = StringUtils.isEmpty(req.getParameter("billNo")) ? "" : String.valueOf(req.getParameter("billNo"));
		
		String billNoStr = "";
		
		if(StringUtils.isNotEmpty(billNo)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("billNo", billNo);
			List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceManager.findByBiz(null, map);
			
			if(CollectionUtils.isNotEmpty(sourceList)){
				for (int i = 0; i < sourceList.size(); i++) {
					if(StringUtils.isNotEmpty(sourceList.get(i).getBalanceNo())){
						billNoStr +=sourceList.get(i).getBalanceNo()+",";
					}
				}
				if(billNoStr.length() > 0){
					billNoStr = billNoStr.substring(0, billNoStr.length()-1);
				}
			}
		}
		
		return billNoStr;
	}
	
	/**
     * 在团购预付款登记里，根据结算公司和客户查询，取得开票申请的信息
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/getBillInvoiceApplyNo")
	@ResponseBody
	public  Map<String, Object> getBillInvoiceApplyNo(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		params.put("balanceType", BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo());
		int total = this.billBalanceInvoiceApplyManager.findInvoiceApplyCountForPayment(params);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<BillCommonInvoiceRegister> list = billBalanceInvoiceApplyManager.findInvoiceApplyForPayment(page, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
    
//    /**
//	 * 导出
//	 * @param modelType 实体对象
//	 * @param req HttpServletRequest
//	 * @param model Model
//	 * @param response HttpServletResponse
//	 * @throws ManagerException 异常
//	 */
//	@RequestMapping(value = "/do_fas_export")
//	public void doFasExport(BillBalanceInvoiceApply modelType, HttpServletRequest req, Model model,
//			HttpServletResponse response) throws ManagerException {
//		String fileName = req.getParameter("fileName");
//		String exportColumns = req.getParameter("exportColumns");
////		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
////		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
//		Map<String, Object> params = builderParams(req, model);
////		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
////		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
//		int total = this.billBalanceInvoiceApplyManager.getCountByBalanceNo(params);
//		SimplePage page = new SimplePage(0, total,  total);
//		List<BillBalanceInvoiceApply> list = this.billBalanceInvoiceApplyManager.getByBalanceNo(page, null, null, params);
//		
//		if(CollectionUtils.isNotEmpty(list)){
//			for (int i = 0; i < list.size(); i++) {
//				if(StringUtils.isNotBlank(list.get(i).getCurrency())){
//					list.get(i).setCurrencyName(CurrencyTypeEnums.getNameByNo(Integer.parseInt(list.get(i).getCurrency())));
//				}
//				Map<String, Object> paramMap = new HashMap<String, Object>();
//				paramMap.put("billNo", list.get(i).getBillNo());
//				List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceManager.findByBiz(null, paramMap);
//				
//				String balanceNoStr = "";
//				if(CollectionUtils.isNotEmpty(sourceList)){
//					for (int j = 0; j < sourceList.size(); j++) {
//						if(StringUtils.isNotEmpty(sourceList.get(j).getBalanceNo())){
//							balanceNoStr += sourceList.get(j).getBalanceNo()+",";
//						}
//					}
//					if(balanceNoStr.length() > 0){
//						list.get(i).setBalanceNo(balanceNoStr.substring(0, balanceNoStr.length()-1));
//					}
//				}
//			}
//		}
////		List dataList = new ArrayList();
////		dataList.addAll(list);
//		try {
//			ExportUtils.doExport(fileName, exportColumns, list, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public 	Map<String, Object> doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
    	SystemUser user = CurrentUser.getCurrentUser(req);
    	String[] fieldNames= new String[]{"sourceNo","invoiceCode","invoiceNo","invoiceDate","express","deliveryNo","deliveryDate","confirmTime","remark"};
    	List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), BillCommonRegisterDtl.class, fieldNames);
    	List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
    	lstValidate.add(new ValidateVo(-1, "sourceNo", "","开票申请号", true));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "salerNo", "","公司编码", true));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "buyerNo", "","客户编码",true));
		lstValidate.add(new ValidateVo(-1, "invoiceCode", "","发票编码",true));
		lstValidate.add(new ValidateVo(-1, "invoiceNo", "","发票号",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "invoiceDate", "","发票日期",false));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "invoiceAmount", "","发票金额",true));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "taxRate", "","税率",false));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌名称",false));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.FINANCIAL_CATEGORY.getTypeNo(), "categoryNo", "categoryName","大类名称",false));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "qty", "","数量",true));
//		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "price", "","价格",false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "deliveryDate", "","快递日期",false));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "confirmTime", "","确认日期",false));
		List<ValidateResultVo>  listValidate = doValidate(lstItem, lstValidate, BillCommonRegisterDtl.class);
		List<BillCommonRegisterDtl> dtlList = new ArrayList<BillCommonRegisterDtl>();
		boolean flag = true;
		for (Object object : listValidate) {
			ValidateResultVo vo = (ValidateResultVo)object;
			if(vo.getPass() == YesNoEnum.YES.getValue()){
				BillCommonRegisterDtl billCommonRegisterDtl = (BillCommonRegisterDtl)vo.getValidateObj();
				dtlList.add(billCommonRegisterDtl);
			}else{
				flag = false;
			}
		}
		//导入的数据只要有一笔不合法，则不执行保存操作
		if(flag && !CollectionUtils.isEmpty(dtlList)){
			billBalanceInvoiceApplyManager.doImportInvoiceRegsiter(dtlList,user.getUsername());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", flag);
		map.put("data", listValidate);
		return map;
	}
	
    
    /**
	 * 导入校验
	 * @param fileName
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	private List<ValidateResultVo> doValidate(List<Object> lstItem, List<ValidateVo> lstValidate, Class clazz) throws Exception {
		List<ValidateResultVo> resultList = new ArrayList<ValidateResultVo>();
		if(!CollectionUtils.isEmpty(lstItem) && !CollectionUtils.isEmpty(lstValidate) ){
			String sourceNo = "";//, salerNo = "", buyerNo = "";
			for (int i = 0,iSize = lstItem.size(); i<iSize; i++) {
				ValidateResultVo resultVo = new ValidateResultVo();
				Object obj = lstItem.get(i);
				for (ValidateVo validate : lstValidate) {
					this.validate(validate, obj, clazz, resultVo);
				}
				String errorInfo = "";
				BillCommonRegisterDtl billCommonRegisterDtl = (BillCommonRegisterDtl)obj;
				// 每笔记录都需要验证此申请是否可以登记发票（状态是否为确认；是否已经登记过发票）
				BillBalanceInvoiceApply billBalanceInoviceApply = new BillBalanceInvoiceApply();
				billBalanceInoviceApply.setBillNo(billCommonRegisterDtl.getSourceNo());
				BillBalanceInvoiceApply inoviceApply = billBalanceInvoiceApplyManager.findById(billBalanceInoviceApply);
				if(null != inoviceApply){
					if(1 == inoviceApply.getStatus()){ // 开票申请单据状态：1＝制单；2＝确认
						errorInfo = errorInfo.concat("必须为确认状态：").concat(billCommonRegisterDtl.getSourceNo()).concat(";");
					}
					if(StringUtils.isNotBlank(inoviceApply.getInvoiceRegisterNo())){
						errorInfo = errorInfo.concat("已登记过发票：").concat(billCommonRegisterDtl.getSourceNo()).concat(";");
					}
				} else {
					errorInfo = errorInfo.concat("开票申请号不存：").concat(billCommonRegisterDtl.getSourceNo()).concat(";");
				}
				// 如果开票申请号一样，表示此申请号导入的是多笔明细，需要验证多笔明细的公司及客户是否一致
				if(StringUtils.isNotBlank(billCommonRegisterDtl.getSourceNo()) && sourceNo.equals(billCommonRegisterDtl.getSourceNo())){
					errorInfo = errorInfo.concat("存在相同的开票申请号：").concat(billCommonRegisterDtl.getSourceNo()).concat(";");
//					if(StringUtils.isNotBlank(billCommonRegisterDtl.getSalerNo()) && !salerNo.equals(billCommonRegisterDtl.getSalerNo())){
//						errorInfo = errorInfo.concat("同一单开票申请公司必须一致：").concat(billCommonRegisterDtl.getSalerNo()).concat(";");
//					}
//					if(StringUtils.isNotBlank(billCommonRegisterDtl.getBuyerNo()) && !buyerNo.equals(billCommonRegisterDtl.getBuyerNo())){
//						errorInfo = errorInfo.concat("同一单开票申请客户必须一致：").concat(billCommonRegisterDtl.getBuyerNo()).concat(";");
//					}
				}else{
					sourceNo = billCommonRegisterDtl.getSourceNo();
//					salerNo = billCommonRegisterDtl.getSalerNo();
//					buyerNo = billCommonRegisterDtl.getBuyerNo();
//					if(null != inoviceApply){
//						if(!salerNo.equals(inoviceApply.getSalerNo())){
//							errorInfo = errorInfo.concat("公司编码与开票申请不一致：").concat(salerNo).concat(";");
//						}
//						if(!buyerNo.equals(inoviceApply.getBuyerNo())){
//							errorInfo = errorInfo.concat("客户编码与开票申请不一致：").concat(buyerNo).concat(";");
//						}
//					}
				}
				
				String errorCode = resultVo.getErrorCode();
				String emptyCode = resultVo.getEmptyCode();
				if(StringUtils.isNotBlank(errorCode) || StringUtils.isNotBlank(emptyCode) ){
					resultVo.setPass(YesNoEnum.NO.getValue());
					if(StringUtils.isNotBlank(errorCode)){
						errorInfo = errorInfo.concat("无效编码：").concat(errorCode.substring(0, errorCode.length()-1).concat(";"));
					}
					if(StringUtils.isNotBlank(emptyCode)){
						errorInfo = errorInfo.concat("必填字段：").concat(emptyCode.substring(0, emptyCode.length()-1));
					}
				}
				if(StringUtils.isBlank(errorInfo)){
					resultVo.setPass(YesNoEnum.YES.getValue());
				}else{
					resultVo.setErrorInfo(errorInfo);
				}
				resultVo.setIndex(i+3);
				resultVo.setValidateObj(obj);
				resultList.add(resultVo);
			}
		}
		return resultList;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void validate(ValidateVo validate, Object obj , Class clazz, ValidateResultVo result)throws Exception{
		if(StringUtils.isBlank(validate.getPropertyName())){
			return ;
		}
		String propertyName = validate.getPropertyName();
//		String cnPropertyName = validate.getCnPropertyName();
		Method getNoMethod = null;
//		Method setNameMethod = null;
		if(StringUtils.isNotBlank(propertyName)){
			getNoMethod =  clazz.getDeclaredMethod("get".concat(propertyName.substring(0,1).toUpperCase()).concat(propertyName.substring(1)));
		}
//		if(StringUtils.isNotBlank(cnPropertyName)){
//			 setNameMethod =  clazz.getDeclaredMethod("set".concat(cnPropertyName.substring(0,1).toUpperCase()).concat(cnPropertyName.substring(1)),String.class);
//		}
		String errorCode = result.getErrorCode();
		String emptyCode = result.getEmptyCode();
		Object validateObj = getNoMethod.invoke(obj);
		String validateNo = String.valueOf(validateObj);
		if(null!= validateObj && StringUtils.isNotBlank(validateNo)){
			if(validate.getValidateType() == ValidateTypeEnums.NUMBER.getTypeNo()){
				if(!(validateObj instanceof Number)){
					errorCode = errorCode.concat(String.valueOf(validateObj)).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.DATE.getTypeNo()){
				if(!(validateObj instanceof Date)){
					errorCode = errorCode.concat(String.valueOf(validateObj)).concat(",");
				}
			}
		}else{
			if(validate.isEmptyValidate()){
				emptyCode = emptyCode.concat(validate.getPropertyDesc()).concat(",");
			}
		}
		result.setErrorCode(errorCode);
		result.setEmptyCode(emptyCode);
	}
	
	
	@RequestMapping("/show_error_info")
	public String showErrorInfo() {
		return "bill_balance_invoice_apply/show_error_info";
	}
	
	
	/**
	 * 查询开票申请列表信息
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/apply_list.json")
	@ResponseBody
	public  Map<String, Object> getByApplyList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String isHq = params.get("isHQ") == null ? "" : params.get("isHQ").toString();
		// 管理城市
   		String organNo = params.get("organNo") == null ? "" : params.get("organNo").toString();
   		List<String> organNoList = new ArrayList<String>();
   		if (StringUtils.isNotBlank(organNo)) {
   			if (organNo.contains(",")) {
   				String[] organNos = organNo.split(",");
   				for (String organNoTemp : organNos) {
   					organNoList.add(organNoTemp);
   				}
   			} else {
   				organNoList.add(organNo);
   			}

   			params.put("organNos", organNoList);
   			params.put("organNo", null);
   		}
   		
   		//查询承担总部职能的结算公司
   		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isBlank(isHq) && StringUtils.isNotEmpty(companyNos)) {
   			params.put("queryCondition", "AND BALANCE_TYPE IN (5,7,8,10,11) AND T.SALER_NO NOT IN (" + companyNos + ")");
   		}else if(StringUtils.isNotEmpty(companyNos)){
   			params.put("queryCondition","AND BALANCE_TYPE IN (2,7,14) AND T.SALER_NO IN (" + companyNos + ")");
   		}
		
		int total = this.billBalanceInvoiceApplyManager.getCountByBalanceNo(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillBalanceInvoiceApply> list = this.billBalanceInvoiceApplyManager.getByBalanceNo(page, sortColumn, sortOrder, params);
		
		if(CollectionUtils.isNotEmpty(list)){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			for (BillBalanceInvoiceApply invoiceApply : list) {
				//查询门店名称
				paramMap.put("invoiceApplyNo", invoiceApply.getBillNo());
				List<BillShopBalance> billShopBalanceList = billShopBalanceManager.findByBiz(null, paramMap);
				if(CollectionUtils.isNotEmpty(billShopBalanceList)){
					String shopName = "";
					for (BillShopBalance billShopBalance : billShopBalanceList) {
						if(StringUtils.isNotBlank(billShopBalance.getShortName())){
							shopName +=billShopBalance.getShortName()+",";
						}
					}
					if(shopName.length() > 0){
						invoiceApply.setShopName(shopName.substring(0, shopName.length()-1));
					}
				}
				//此方法处理源单据号问题
				findBalanceNoByInvoiceNo(invoiceApply);
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	
	/**
	 * 修改开票申请
	 */
	@Override
	@RequestMapping(value = "/put")
	public ResponseEntity<BillBalanceInvoiceApply> moditfy(BillBalanceInvoiceApply type) throws ManagerException {
		super.moditfy(type);
		BillBalanceInvoiceApply invoiceApply = super.get(type).getBody();
		if(null != invoiceApply){
			//此方法处理源单据号问题
			findBalanceNoByInvoiceNo(invoiceApply);
		}
		return new ResponseEntity<BillBalanceInvoiceApply>(invoiceApply, HttpStatus.OK);
	}
	
	/**
	 * 查询开票申请明细
	 */
	@Override
	@RequestMapping(value = "/get")
	public ResponseEntity<BillBalanceInvoiceApply> get(BillBalanceInvoiceApply modelType) throws ManagerException {
		BillBalanceInvoiceApply invoiceApply = super.get(modelType).getBody();
		if(null != invoiceApply){
			//此方法处理源单据号问题
			findBalanceNoByInvoiceNo(invoiceApply);
		}
		return new ResponseEntity<BillBalanceInvoiceApply>(invoiceApply, HttpStatus.OK);
	}
	
	/**
	 * 根据发票编号查询开票申请号
	 * @param invoiceNo
	 * @return
	 * @throws ManagerException
	 */
	private void findBalanceNoByInvoiceNo(BillBalanceInvoiceApply invoiceApply) throws ManagerException{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("billNo", invoiceApply.getBillNo());
		List<BillBalanceInvoiceSource> list = billBalanceInvoiceSourceManager.findByBiz(null, params);
		String balanceNos = "";
		String balanceType = "";
		if(!CollectionUtils.isEmpty(list)){
			int index = 0;
			for (BillBalanceInvoiceSource billBalanceInvoiceSource : list) {
				balanceNos += billBalanceInvoiceSource.getBalanceNo();
				balanceType = billBalanceInvoiceSource.getBalanceType()+"";
				if(index < (list.size() -1)){
					balanceNos += ",";
				}
				index ++;
			}
		}
		invoiceApply.setBalanceNo(balanceNos);
		invoiceApply.setBalanceType(balanceType);
	}
	
	@Override
	protected List<BillBalanceInvoiceApply> queryExportData(Map<String, Object> params) throws ManagerException {
		String isHq = params.get("isHQ") == null ? "" : params.get("isHQ").toString();
		//查询承担总部职能的结算公司
   		String companyNos = financialAccountManager.findLeadRoleCompanyNos();
		if (StringUtils.isBlank(isHq) && StringUtils.isNotEmpty(companyNos)) {
   			params.put("queryCondition", "AND BALANCE_TYPE IN (5,7,8,10,11) AND T.SALER_NO NOT IN (" + companyNos + ")");
   		}else if(StringUtils.isNotEmpty(companyNos)){
   			params.put("queryCondition","AND BALANCE_TYPE IN (2,7,14) AND T.SALER_NO IN (" + companyNos + ")");
   		}
		List<BillBalanceInvoiceApply> applyList = null;
		String exportType = params.get("exportType") == null? "" :(String)params.get("exportType");
		if("1".equals(exportType)){//如果为华东导出,则需要查询店铺小类,及按店铺编号小计
			Map<String, BillBalanceInvoiceApply> map = new LinkedHashMap<String, BillBalanceInvoiceApply>();
			
			applyList = billBalanceInvoiceApplyManager.findHdTaxExportList(params);
			pressShopGroup(params, applyList, map);
			
			//按管理城市按品牌汇总到列表底部
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				Object key = it.next();
				BillBalanceInvoiceApply apply = map.get(key);
				String shopNo = apply.getBillNo();
				int index = 0;//某品牌最后一个下标
				for (BillBalanceInvoiceApply billBalanceInvoiceApply : applyList) {
					if (StringUtils.isNotBlank(billBalanceInvoiceApply.getShopNo()) && billBalanceInvoiceApply.getShopNo().equals(shopNo)){
						index = applyList.indexOf(billBalanceInvoiceApply);
						break;
					}
				}
				if (index > -1) {
					applyList.add(index, apply);
				}
			}
		}else{
			applyList = billBalanceInvoiceApplyManager.findTaxExportList(params);
		}
		
		return applyList;
	}

	/**
	 * 根据店铺名称分组
	 * @param params
	 * @param applyList
	 * @param map
	 * @return
	 * @throws ManagerException
	 */
	private void pressShopGroup(Map<String, Object> params, List<BillBalanceInvoiceApply> applyList,
			Map<String, BillBalanceInvoiceApply> map) throws ManagerException {
		int id = 0;
		if(!applyList.isEmpty()){
			params.put("lookupCode", "SHOP_CATEGORY_B");
			BillBalanceInvoiceApply temp = null;
			for (BillBalanceInvoiceApply billBalanceInvoiceApply : applyList) {
				if(StringUtils.isNotBlank(billBalanceInvoiceApply.getShopNo())){
					params.put("entryCode", billBalanceInvoiceApply.getRetailType());
					//查询店铺小类名称
					String name = lookupEntryManager.getEntryNameByLookupIdEntryCode(params);
					billBalanceInvoiceApply.setRetailType(name);
					if (null == map.get(billBalanceInvoiceApply.getShopNo())) {
						temp = new BillBalanceInvoiceApply();
						temp.setInvoiceName("小计");
						temp.setBillNo(billBalanceInvoiceApply.getShopNo());
						temp.setQty(0);
						temp.setNoTaxAmount(new BigDecimal(0));
						temp.setContainTaxAmount(new BigDecimal(0));
						temp.setTaxAmount(new BigDecimal(0));
						map.put(billBalanceInvoiceApply.getShopNo(), temp);
						id ++;
					}
					//根据店铺编号小计各项数量及金额
					sumToSubtotal(map.get(billBalanceInvoiceApply.getShopNo()),billBalanceInvoiceApply);
				}else{
					id ++;
				}
				billBalanceInvoiceApply.setId(id+"");
			}
		}
	}
	/**
	 * 将第二个对象的值累加至第一个对象中
	 * @param mainBillSalesSum
	 * @param addBillSalesSum
	 */
	private void sumToSubtotal(BillBalanceInvoiceApply mainApply, BillBalanceInvoiceApply addApply) {
		if(null != mainApply.getQty() && null != addApply.getQty()){//数量
			mainApply.setQty(mainApply.getQty() + addApply.getQty());
		}
		if(null != mainApply.getNoTaxAmount() && null != addApply.getNoTaxAmount()){//不含税金额
			mainApply.setNoTaxAmount(mainApply.getNoTaxAmount().add(addApply.getNoTaxAmount()));
		}
		if(null != mainApply.getContainTaxAmount() && null != addApply.getContainTaxAmount()){//含税税额
			mainApply.setContainTaxAmount(mainApply.getContainTaxAmount().add(addApply.getContainTaxAmount()));
		}
		if(null != mainApply.getTaxAmount() && null != addApply.getTaxAmount()){//税额
			mainApply.setTaxAmount(mainApply.getTaxAmount().add(addApply.getTaxAmount()));
		}
	}
	
}
