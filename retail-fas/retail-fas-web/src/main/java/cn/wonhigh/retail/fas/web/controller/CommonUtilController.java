package cn.wonhigh.retail.fas.web.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.enums.AskPaymentStatusEnums;
import cn.wonhigh.retail.fas.common.enums.AuditStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BusinessTypeEnums;
import cn.wonhigh.retail.fas.common.enums.CurrencyTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ExceptionTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ExcessStatusEnums;
import cn.wonhigh.retail.fas.common.enums.OrganizationTypeEnums;
import cn.wonhigh.retail.fas.common.enums.PayTermTypeEnums;
import cn.wonhigh.retail.fas.common.enums.PeBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.ReportBizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.SettleTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.Customer;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;
import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.common.model.Mall;
import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.common.model.PayTerm;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.model.SupplierGroup;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ShopVo;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;
import cn.wonhigh.retail.fas.manager.CategoryManager;
import cn.wonhigh.retail.fas.manager.CommonUtilManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.CurrencyManagementManager;
import cn.wonhigh.retail.fas.manager.CustomerManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.FinancialCategoryManager;
import cn.wonhigh.retail.fas.manager.InvoiceInfoManager;
import cn.wonhigh.retail.fas.manager.ItemManager;
import cn.wonhigh.retail.fas.manager.LookupEntryManager;
import cn.wonhigh.retail.fas.manager.MallManager;
import cn.wonhigh.retail.fas.manager.OrderUnitManager;
import cn.wonhigh.retail.fas.manager.OrganManager;
import cn.wonhigh.retail.fas.manager.PayTermManager;
import cn.wonhigh.retail.fas.manager.PurchasePriceManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.manager.SupplierManager;
import cn.wonhigh.retail.fas.manager.ZoneInfoManager;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/common_util")
public class CommonUtilController {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

	@Resource
	private CommonUtilManager commonUtilManager;
	
	@Resource
	private SupplierGroupController supplierGroupController;
	
	@Resource
	private SupplierManager supplierManager;
	
	@Resource
	private CompanyManager companyManager;
	
	@Resource
	private CustomerManager customerManager;
	
	@Resource
	private ItemManager itemManager;
	
	@Resource
	private BrandManager brandManager;

	@Resource
	private CategoryManager categoryManager;

	@Resource
	private FinancialCategoryManager financialCategoryManager;
	
	@Resource
	private BrandUnitManager brandUnitManager;
	
	@Resource
	private PayTermManager payTermManager;
	
	@Resource
	private MallManager mallManager;

	@Resource
	private ShopManager shopManager;
	
	@Resource
	private CustomerController customerController;
	
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Resource
	private OrganManager organManager;
	
	@Resource
	private OrderUnitManager orderUnitManager;
	
	@Resource
	private LookupEntryManager lookupEntryManager;
	
	@Resource
	private InvoiceInfoManager invoiceInfoManager;
	
	@Resource
	private PurchasePriceManager purchasePriceManager;
	
	@Resource
	private ZoneInfoManager zoneInfoManager;
	
	@Resource
	private CurrencyManagementManager currencyManagementManager;
	
	public static Map<String, List<LookupVo>> lookupMap = new HashMap<String, List<LookupVo>>();

	
	@RequestMapping("/toSelectExtendCategory")
	public String toSelectExtendCategory() {
		return "common_util/selectExtendCategory";
	}
	
	//  页面跳转
	@RequestMapping("/toImport")
	public ModelAndView toImport(HttpServletRequest req) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		obj.addObject("balanceType", req.getParameter("balanceType"));
		obj.setViewName("common_util/import");
		return obj;
	}
	
	@RequestMapping("/toSearchTemplate")
	public String toSearchTemplate() {
		return "base_setting/mall/searchTemplate";
	}
	
	@RequestMapping("/toSearchCompany")
	public String toSearchCompany() {
		return "base_setting/company/searchCompany";
	}

	@RequestMapping("/toSearchMall")
	public String toSearchMall() {
		return "base_setting/mall/searchMall";
	}
	
	@RequestMapping("/toSearchBrand")
	public String toSearchBrand() {
		return "base_setting/mall/searchBrand";
	}
	
	
	@RequestMapping("/toSearchCustomer")
	public String toSearchCustomer() {
		return "/base_setting/customer/searchCustomer";
	}
	
	@RequestMapping("/toSearchAllCustomer")
	public String toSearchAllCustomer() {
		return "/base_setting/customer/searchAllCustomer";
	}
	
	@RequestMapping("/toSearchShopBalanceBill")
	public ModelAndView toSearchShopBalanceBill(HttpServletRequest req) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		obj.addObject("month", req.getParameter("month"));
		
		obj.addObject("shopNo", req.getParameter("shopNo"));
		//use shopType to discriminate shop
		String shopType = req.getParameter("shopType");
		if(shopType != null && !"".equals(shopType)){
			obj.addObject("payType", shopType);
		}
		obj.setViewName("base_setting/mall/searchShopBalanceBill");
		return obj;
	}
		
	@RequestMapping("/toSearchShop")
	public ModelAndView toSearchShop(HttpServletRequest req) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		
		String companyNo = req.getParameter("companyNo");
		if(companyNo != null && !"".equals(companyNo)){
			obj.addObject("companyNo", companyNo);
		}
		
		//use shopType to discriminate shop
		String shopType = req.getParameter("shopType");
		if(shopType != null && !"".equals(shopType)){
			obj.addObject("payType", shopType);
		}
		obj.setViewName("base_setting/shop/searchShop");
		return obj;
	}
	
	@RequestMapping("/toSearchShopGroup")
	public String toSearchShopGroup() {
		return "base_setting/shop/manageShopGroup";
	}

	@RequestMapping("/toSearchBsgroups")
	public String toSearchBsgroups() {
		return "base_setting/mall/searchBsgroups";
	}

	@RequestMapping("/toSearchOrgan")
	public String toSearchOrgan() {
		return "base_setting/mall/searchOrgan";
	}

	@RequestMapping("/toSearchCostCate")
	public String toSearchCostCate() {
		return "base_setting/mall/searchCostCate";
	}

	@RequestMapping("/toSearchOrder")
	public ModelAndView toSearchOrder(HttpServletRequest req) {
		ModelAndView obj = new ModelAndView();
		if(StringUtils.isNotBlank(req.getParameter("queryCondition"))){
			obj.addObject("queryCondition", req.getParameter("queryCondition"));
		}
		obj.setViewName("common_util/singleSelectOrder");
		return obj;
	}
	
	@RequestMapping("/toSearchDiffType")
	public ModelAndView toSearchDiffType(HttpServletRequest req) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		obj.addObject("companyNo", req.getParameter("companyNo"));
		obj.setViewName("base_setting/mall/searchDiffType");
		return obj;
	}
	
	@RequestMapping("/toSearchDiffBack")
	public ModelAndView toSearchDiffBack(HttpServletRequest req) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		obj.addObject("diffBillNo", req.getParameter("billNo"));
		obj.addObject("balanceNo", req.getParameter("balanceNo"));
		obj.addObject("dtlId", req.getParameter("dtlId"));
		obj.setViewName("base_setting/mall/balanceDiffBack");
		return obj;
	}
	
	@RequestMapping("/toSearchPro")
	public ModelAndView toSearchPro(HttpServletRequest req) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		obj.addObject("shopNo", req.getParameter("shopNo"));
		obj.addObject("balanceNo", req.getParameter("balanceNo"));
		obj.setViewName("base_setting/mall/searchPro");
		return obj;
	}
	
	@RequestMapping("/toSearchDeduction")
	public String toSearchDeduction() {
		return "base_setting/mall/searchDeduction";
	}
	/**
	 * 单选/多选结算单查询页面跳转
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
    @RequestMapping("/toSearchBalance")
   	public ModelAndView toSearchBalance(HttpServletRequest req, Model model) {
    	ModelAndView obj = new ModelAndView();
    	obj.addObject("params", req.getQueryString());
    	obj.setViewName("common_util/singleSelectBalance");
    	if ("true".equals(req.getParameter("multiSelect"))) {
			obj.setViewName("common_util/multiSelectBalance");
		}
    	return obj;
   	}
    
	/**
	 * 单选/多选发票查询页面跳转
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
    @RequestMapping("/toSearchInvoice")
   	public ModelAndView toSearchInvoice(HttpServletRequest req, Model model) {
    	ModelAndView obj = new ModelAndView();
    	obj.addObject("params", req.getQueryString());
    	obj.setViewName("common_util/singleSelectInvoice");
    	if ("true".equals(req.getParameter("multiSelect"))) {
			obj.setViewName("common_util/multiSelectInvoice");
		}
    	return obj;
   	}
    
	/**
	 * 单选/多选发票查询页面跳转
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
    @RequestMapping("/toSearchOrganization")
   	public ModelAndView toSearchOrganization(HttpServletRequest req, Model model) {
    	ModelAndView obj = new ModelAndView();
    	obj.addObject("params", req.getQueryString());
    	obj.setViewName("common_util/singleSelectOrganization");
    	if ("true".equals(req.getParameter("multiSelect"))) {
			obj.setViewName("common_util/multiSelectOrganization");
		}
    	return obj;
   	}
    
	/**
	 * 单选/多选查询页面跳转
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/doSelect")
	public ModelAndView singleSelect(HttpServletRequest req) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		obj.addObject("selectUrl", req.getParameter("selectUrl"));
		obj.addObject("no", req.getParameter("no"));
		obj.addObject("name", req.getParameter("name"));
		obj.addObject("queryCondition", req.getParameter("queryCondition"));
		obj.addObject("params", req.getParameter("params"));
		obj.addObject("companyNo", req.getParameter("companyNo"));
		obj.setViewName("common_util/singleSelect");
		if ("true".equals(req.getParameter("multiSelect"))) {
			obj.setViewName("common_util/multiSelect");
		}
		return obj;
	}

	/**
	 * 单选/多选查询页面跳转
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/doSelectShop")
	public ModelAndView doSelectShop(HttpServletRequest req) throws ManagerException {
		ModelAndView obj = new ModelAndView();
		obj.addObject("queryCondition", req.getParameter("queryCondition"));
		obj.setViewName("common_util/singleSelectShop");
		return obj;
	}
	
	/**
	 * 查询单据状态
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBusinessType")
	@ResponseBody
	public List<LookupVo> getBusinessType(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("businessType");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new ArrayList<LookupVo>();
			for (BusinessTypeEnums type : BusinessTypeEnums.values()) {
				LookupVo entry = new LookupVo();
				entry.setCode(String.valueOf(type.getTypeNo()));
				entry.setName(String.valueOf(type.getTypeName()));
				lstVo.add(entry);
			}
			lookupMap.put("businessType", lstVo);
		}
		return lstVo;
	}
	
	/**
	 * 查询单据状态
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getStatus")
	@ResponseBody
	public List<LookupVo> getStatus(HttpServletRequest req) throws ManagerException {
		String fliterType =  req.getParameter("fliterType");
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		for (BillStatusEnums type : BillStatusEnums.values()) {
			boolean flag = true;
			if(StringUtils.isNotBlank(fliterType)){
				String[] arrFliter = fliterType.split(",");
				for (String str : arrFliter) {
					if(str.equals(type.name())){
						flag = false;
						break;
					}
				}
			}
			if(flag){
				LookupVo entry = new LookupVo();
				entry.setCode(String.valueOf(type.getTypeNo()));
				entry.setName(String.valueOf(type.getTypeName()));
				lstVo.add(entry);
			}
		}
		return lstVo;
	}
	
	/**
	 * 查询商品核价状态
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPricingStatus")
	@ResponseBody
	public List<LookupVo> getPricingStatus(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("pricingStatus");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new ArrayList<LookupVo>();
			for (ExcessStatusEnums type : ExcessStatusEnums.values()) {
				LookupVo entry = new LookupVo();
				entry.setCode(String.valueOf(type.getTypeNo()));
				entry.setName(String.valueOf(type.getTypeName()));
				lstVo.add(entry);
			}
			lookupMap.put("pricingStatus", lstVo);
		}
		return lstVo;
	}

	/**
	 * 查询单据状态
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPeBalanceStatus")
	@ResponseBody
	public List<LookupVo> getPeBalanceStatus(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("peBalanceStatus");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new ArrayList<LookupVo>();
			for (PeBalanceStatusEnums type : PeBalanceStatusEnums.values()) {
				LookupVo entry = new LookupVo();
				entry.setCode(String.valueOf(type.getTypeNo()));
				entry.setName(String.valueOf(type.getTypeName()));
				lstVo.add(entry);
			}
			lookupMap.put("peBalanceStatus", lstVo);
		}
		return lstVo;
	}
	
	/**
	 * 查询多级审批单据状态
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getAuditStatus")
	@ResponseBody
	public List<LookupVo> getAuditStatus(HttpServletRequest req) throws ManagerException {
		List<LookupVo>	lstVo = new ArrayList<LookupVo>();
		String fliterType =  req.getParameter("fliterType");
			for (AuditStatusEnums type : AuditStatusEnums.values()) {
				boolean flag = true;
				if(StringUtils.isNotBlank(fliterType)){
					String[] arrFliter = fliterType.split(",");
					for (String str : arrFliter) {
						if(str.equals(type.name())){
							flag = false;
							break;
						}
					}
				}
				if(flag){
					LookupVo entry = new LookupVo();
					entry.setCode(String.valueOf(type.getTypeNo()));
					entry.setName(String.valueOf(type.getTypeName()));
					lstVo.add(entry);
				}
			}
		return lstVo;
	}

	
	/**
	 * 查询请款单据状态
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getAskPaymentStatus")
	@ResponseBody
	public List<LookupVo> getAskpaymentStatus(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("askPaymentStatus");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new ArrayList<LookupVo>();
			for (AskPaymentStatusEnums type : AskPaymentStatusEnums.values()) {
				LookupVo entry = new LookupVo();
				entry.setCode(String.valueOf(type.getStatusNo()));
				entry.setName(String.valueOf(type.getStatusName()));
				lstVo.add(entry);
			}
			lookupMap.put("askPaymentStatus", lstVo);
		}
		return lstVo;
	}

	/**
	 * 查询请款单据状态
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getReportBizType")
	@ResponseBody
	public List<LookupVo> getReportBizType(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("reportBizType");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new ArrayList<LookupVo>();
			for (ReportBizTypeEnums type : ReportBizTypeEnums.values()) {
				LookupVo entry = new LookupVo();
				entry.setCode(String.valueOf(type.getTypeNo()));
				entry.setName(String.valueOf(type.getTypeName()));
				lstVo.add(entry);
			}
			lookupMap.put("reportBizType", lstVo);
		}
		return lstVo;
	}

	
	/**
	 * 查询结算单据状态
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBalanceStatus")
	@ResponseBody
	public List<LookupVo> getBalanceStatus(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		String fliterStatus = req.getParameter("fliterType");
		String[] arrFliter = null;
		boolean existFliter = false;
		if(StringUtils.isNotBlank(fliterStatus)){
			arrFliter = fliterStatus.split(",");
			existFliter = true;
		}
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new ArrayList<LookupVo>();
			for (BalanceStatusEnums type : BalanceStatusEnums.values()) {
				LookupVo entry = new LookupVo();
				boolean fliterFlag = false;
				if(existFliter){
					for (String str : arrFliter) {
						if(str.equals(type.name())){
							fliterFlag = true;
							break;
						}
					}
				}
				if(!fliterFlag){
					entry.setCode(String.valueOf(type.getTypeNo()));
					entry.setName(String.valueOf(type.getTypeName()));
					lstVo.add(entry);
				}
			}
		}
		return lstVo;
	}
	
	/**
	 * 查询结算类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBalanceType")
	@ResponseBody
	public List<LookupVo> getBalanceType(HttpServletRequest req) throws ManagerException {
		String filterType = req.getParameter("filterType");
		String showType = req.getParameter("showType");
		List<LookupVo> lstVo = new LinkedList<LookupVo>();
		if(StringUtils.isNotBlank(filterType)){
			String[] arrFilter = filterType.split(",");
			for (BalanceTypeEnums item : BalanceTypeEnums.values()) {
				boolean flag = true;
				for (String str : arrFilter) {
					if(str.equals(item.name())){
						flag = false;
						break;
					}
				}
				if(flag){
					LookupVo vo = new LookupVo();
					vo.setCode(String.valueOf(item.getTypeNo()));
					vo.setName(item.getTypeName());
					lstVo.add(vo);	
				}
			}
		}else if(StringUtils.isNotBlank(showType)){
			String[] arrShow = showType.split(",");
			for (String str : arrShow) {
				for (BalanceTypeEnums item : BalanceTypeEnums.values()) {
					if(str.equals(item.name())){
						LookupVo vo = new LookupVo();
						vo.setCode(String.valueOf(item.getTypeNo()));
						vo.setName(item.getTypeName());
						lstVo.add(vo);
						break;
					}
				}
			}
		}else{
			for (BalanceTypeEnums item : BalanceTypeEnums.values()) {
				LookupVo vo = new LookupVo();
				vo.setCode(String.valueOf(item.getTypeNo()));
				vo.setName(item.getTypeName());
				lstVo.add(vo);
			}
		}


		return lstVo;
	}
	
	/**
	 * 查询机构类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getOrganizationType")
	@ResponseBody
	public List<LookupVo> getOrganizationType(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("organizationType");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new LinkedList<LookupVo>();
			for (OrganizationTypeEnums item : OrganizationTypeEnums.values()) {
				LookupVo vo = new LookupVo();
				vo.setCode(String.valueOf(item.getTypeNo()));
				vo.setName(item.getTypeName());
				lstVo.add(vo);
			}
			lookupMap.put("organizationType", lstVo);
		}
		return lstVo;
	}
	
	/**
	 * 查询机构类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPayTermType")
	@ResponseBody
	public List<LookupVo> getPayTermType(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstVo = null;
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new LinkedList<LookupVo>();
			for (PayTermTypeEnums item : PayTermTypeEnums.values()) {
				LookupVo vo = new LookupVo();
				vo.setCode(String.valueOf(item.getTypeNo()));
				vo.setName(item.getTypeName());
				lstVo.add(vo);
			}
			lookupMap.put("payTermType", lstVo);
		}
		return lstVo;
	}
	
	/**
	 * 查询机构类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getOrganization")
	@ResponseBody
	public Map<String, Object>  getOrganization(HttpServletRequest req, Model model) throws ManagerException {
		String organizationType = req.getParameter("organizationType");
		if(Integer.parseInt(organizationType) == OrganizationTypeEnums.COMPANY.getTypeNo()){
			return this.getCompany(req, model);
		}else if(Integer.parseInt(organizationType) == OrganizationTypeEnums.SUPPLIER.getTypeNo()){
			return this.getSupplier(req, model);
		}else if(Integer.parseInt(organizationType) == OrganizationTypeEnums.CUSTOMER.getTypeNo()){
			return this.getCustomer(req, model);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", 0);
		map.put("rows", new ArrayList<Object>());
		return map;
	}
	
	/**
	 * 查询币别
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getGender")
	@ResponseBody
	public List<LookupVo> getGender() throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("gender");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new ArrayList<LookupVo>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("lookupId", 7);
			List<LookupEntry> lstItem = lookupEntryManager.findByBiz(null, params);
			for (LookupEntry lookupEntry : lstItem) {
				LookupVo vo = new LookupVo();
				vo.setCode(lookupEntry.getCode());
				vo.setName(lookupEntry.getName());
				lstVo.add(vo);
			}
			lookupMap.put("gender", lstVo);
		}
		return lstVo;
	}
	
	/**
	 * 查询币别
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCurrency")
	@ResponseBody
	public List<LookupVo> getCurrency() throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("currency");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new LinkedList<LookupVo>();
			for (CurrencyTypeEnums item : CurrencyTypeEnums.values()) {
				LookupVo vo = new LookupVo();
				vo.setCode(String.valueOf(item.getTypeNo()));
				vo.setName(item.getTypeName());
				lstVo.add(vo);
			}
			lookupMap.put("currency", lstVo);
		}
		return lstVo;
	}
	
	/**
	 * 查询体总币别
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getTSCurrency")
	@ResponseBody
	public List<LookupVo> getTSCurrency() throws ManagerException {
		List<CurrencyManagement> list = currencyManagementManager.findByBiz(null, null);
		List<LookupVo> retList = new LinkedList<LookupVo>();
		for(CurrencyManagement currencyManagement:list){
			LookupVo vo = new LookupVo();
			vo.setCode(currencyManagement.getCurrencyCode());
			vo.setName(currencyManagement.getCurrencyName());
			retList.add(vo);
		}
		return retList;
	}
	
	/**
	 * 查询结算方式
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getSettleMethod")
	@ResponseBody
	public List<LookupVo> getSettleMethod() throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("settleMethod");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new LinkedList<LookupVo>();
			for (SettleTypeEnums item : SettleTypeEnums.values()) {
				LookupVo vo = new LookupVo();
				vo.setCode(String.valueOf(item.getTypeNo()));
				vo.setName(item.getTypeName());
				lstVo.add(vo);
			}
			lookupMap.put("settleMethod", lstVo);
		}
		return lstVo;
	}

	
	/**
	 * 查询品牌
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBrand")
	@ResponseBody
	public List<LookupVo> getBrand() throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("brand");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new LinkedList<LookupVo>();
			List<Brand> lstItem = brandManager.findByBiz(null, null);
			for (Brand item : lstItem) {
				LookupVo vo = new LookupVo();
				vo.setCode(item.getBrandNo());
				vo.setName(item.getName());
				lstVo.add(vo);
			}
			lookupMap.put("brand", lstVo);
		}
		return lstVo;
	}
	
	/**
	 * 分页查询品牌
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPageBrand")
	@ResponseBody
	public Map<String, Object> getPageBrand(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = this.brandManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Brand> list = this.brandManager.findByPage(page, sortColumn, sortOrder, params);
		for (Brand item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getBrandNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}

	/**
	 * 分页查询管理城市
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPageOrgan")
	@ResponseBody
	public Map<String, Object> getPageOrgan(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = this.organManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Organ> list = this.organManager.findByPage(page, sortColumn, sortOrder, params);
		for (Organ item : list) {
			LookupVo vo = new LookupVo();
			vo.setCode(item.getOrganNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 分页查询订货单位
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPageOrderUnit")
	@ResponseBody
	public Map<String, Object> getPageOrderUnit(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = this.orderUnitManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<OrderUnit> list = this.orderUnitManager.findByPage(page, sortColumn, sortOrder, params);
		for (OrderUnit item : list) {
			LookupVo vo = new LookupVo();
			vo.setCode(item.getOrderUnitNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 查询大类
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCategory")
	@ResponseBody
	public List<LookupVo> getCategory() throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("category");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new LinkedList<LookupVo>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("levelid", 1);
			List<Category> lstItem = categoryManager.findByBiz(null, params);
			for (Category item : lstItem) {
				LookupVo vo = new LookupVo();
				vo.setCode(item.getCategoryNo());
				vo.setName(item.getName());
				lstVo.add(vo);
			}
			lookupMap.put("category", lstVo);
		}
		return lstVo;
	}

	/**
	 * 分页查询品牌
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPageCategory")
	@ResponseBody
	public Map<String, Object> getPageCategory(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = this.categoryManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Category> list = this.categoryManager.findByPage(page, sortColumn, sortOrder, params);
		for (Category item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getCategoryNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 分页查询财务大类
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPageFasCategory")
	@ResponseBody
	public Map<String, Object> getPageFasCategory(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = this.financialCategoryManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<FinancialCategory> list = this.financialCategoryManager.findByPage(page, sortColumn, sortOrder, params);
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		for (FinancialCategory item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getFinancialCategoryNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 分页查询品牌部
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPageBrandUnit")
	@ResponseBody
	public Map<String, Object> getPageBrandUnit(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = this.brandUnitManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BrandUnit> list = this.brandUnitManager.findByPage(page, sortColumn, sortOrder, params);
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		for (BrandUnit item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getBrandUnitNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 分页查询品牌部
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPayTerm")
	@ResponseBody
	public Map<String, Object> getPayTerm(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = this.payTermManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<PayTerm> list = this.payTermManager.findByPage(page, sortColumn, sortOrder, params);
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		for (PayTerm item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getTermNo());
			vo.setName(item.getTermName());
			vo.setExtendField1(String.valueOf(item.getTermType()));
			vo.setExtendField2(item.getFixedDay() != null ? String.valueOf(item.getFixedDay()) : null);
			vo.setExtendField3(item.getDays() != null ? String.valueOf(item.getDays()) : null);
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 查询单据类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBizType")
	@ResponseBody
	public List<LookupVo> getBizType(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstEntry = new ArrayList<LookupVo>();
		String showType = req.getParameter("showType");
		if (StringUtils.isNotBlank(showType)) {
			String type[] = showType.split(",");
			for (String str : type) {
				for (BizTypeEnums bizType : BizTypeEnums.values()) {
					if (str.equals(bizType.name())) {
						LookupVo entry = new LookupVo();
						entry.setCode(String.valueOf(bizType.getStatus()));
						entry.setName(String.valueOf(bizType.getText()));
						lstEntry.add(entry);
						break;
					}
				}
			}
			return lstEntry;
		}
		for (BillTypeEnums type : BillTypeEnums.values()) {
			LookupVo entry = new LookupVo();
			entry.setCode(String.valueOf(type.getRequestId()));
			entry.setName(String.valueOf(type.getDesc()));
			lstEntry.add(entry);
		}
		return lstEntry;
	}
	
	/**
	 * 查询单据类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBillType")
	@ResponseBody
	public List<LookupVo> getBillType(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstEntry = new ArrayList<LookupVo>();
		String showType = req.getParameter("showType");
		if (StringUtils.isNotBlank(showType)) {
			String type[] = showType.split(",");
			for (String str : type) {
				for (BillTypeEnums billType : BillTypeEnums.values()) {
					if (str.equals(billType.name())) {
						LookupVo entry = new LookupVo();
						entry.setCode(String.valueOf(billType.getRequestId()));
						entry.setName(String.valueOf(billType.getDesc()));
						lstEntry.add(entry);
						break;
					}
				}
			}
			return lstEntry;
		}
		for (BillTypeEnums type : BillTypeEnums.values()) {
			LookupVo entry = new LookupVo();
			entry.setCode(String.valueOf(type.getRequestId()));
			entry.setName(String.valueOf(type.getDesc()));
			lstEntry.add(entry);
		}
		return lstEntry;
	}

	/**
	 * 查询异常单据类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getExceptionType")
	@ResponseBody
	public List<LookupVo> getExceptionType(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstEntry = new ArrayList<LookupVo>();
		for (ExceptionTypeEnums type : ExceptionTypeEnums.values()) {
			LookupVo entry = new LookupVo();
			entry.setCode(String.valueOf(type.getTypeNo()));
			entry.setName(String.valueOf(type.getTypeName()));
			lstEntry.add(entry);
		}
		return lstEntry;
	}

	/**
	 * 查询供应商组
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getSupplierGroup")
	@ResponseBody
	public Map<String, Object> getSupplierGroup(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		Map<String, Object> map = supplierGroupController.queryList(req, model);
		List<SupplierGroup> lstItem = (List<SupplierGroup>) map.get("rows");
		for (SupplierGroup item : lstItem) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getGroupNo());
			vo.setName(item.getGroupName());
			lstVo.add(vo);
		}
		map.put("rows", lstVo);
		return map;
	}
	
	/**
	 * 查询供应商
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getSupplier")
	@ResponseBody
	public Map<String, Object> getSupplier(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = this.supplierManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Supplier> list = this.supplierManager.findByPage(page, sortColumn, sortOrder, params);
		for (Supplier item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getSupplierNo());
			vo.setName(item.getFullName());
			vo.setBankName(item.getBankName());
			vo.setBankAccount(item.getBankAccount());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}

	/**
	 * 查询公司
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCompany")
	@ResponseBody
	public Map<String, Object> getCompany(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req,model);
		this.setMulitParams(req, params);
		String strParams= req.getParameter("params");
		if(StringUtils.isNotBlank(strParams)){
			String companyNos=financialAccountManager.findLeadRoleCompanyNos();
			//过滤承担总部职能的结算公司
			if(("groupLeadRole").equals(strParams)){
				if(StringUtils.isNotBlank(companyNos)){
					params.put("queryCondition", "AND company_no NOT IN ("+companyNos +")");
				}
			}
			if(("notGroupLeadRole").equals(strParams)){
				if(StringUtils.isNotBlank(companyNos)){
					params.put("queryCondition", "AND company_no IN ("+companyNos +")");
				}
			}
		}
		int total = this.companyManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Company> list = this.companyManager.findByPage(page, sortColumn, sortOrder, params);
		for (Company item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getCompanyNo());
			vo.setName(item.getName());
			vo.setBankName(item.getBankName());
			vo.setBankAccount(item.getBankAccount());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}


	/**
	 * 查询客户
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCustomer")
	@ResponseBody
	public Map<String, Object> getCustomer(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		Map<String, Object> map = customerController.queryList(req, model);
		List<Customer> lstItem = (List<Customer>) map.get("rows");
		for (Customer item : lstItem) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getCustomerNo());
			vo.setName(item.getShortName());
			vo.setBankName(item.getBankName());
			vo.setBankAccount(item.getBankAccount());
			lstVo.add(vo);
		}
		map.put("rows", lstVo);
		return map;
	}
	
	/**
	 * 查询开票申请维护  中 Type为客户的 客户
	 */
	@RequestMapping("/getInvoiceInfoWholesaleZoneCustomer")
	@ResponseBody
	public Map<String, Object> getInvoiceInfoWholesaleZoneCustomer(HttpServletRequest req, Model model) throws ManagerException {
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req,model);
		Integer total = invoiceInfoManager.selectDistinctClientCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<InvoiceInfo> invoiceList = invoiceInfoManager.selectDistinctClientByPage(page, sortColumn, sortOrder, params);
		for (InvoiceInfo info : invoiceList) {
			LookupVo vo = new LookupVo();
			vo.setCode(info.getClientNo());
			vo.setName(info.getClientName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 查询商品
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getItem")
	@ResponseBody
	public Map<String, Object> getItem(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req,model);
		this.setMulitParams(req, params);
		int total = this.itemManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Item> list = this.itemManager.findByPage(page, sortColumn, sortOrder, params);
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		for (Item item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getItemNo()));
			vo.setCode(item.getCode());
			vo.setName(item.getName());
			vo.setExtendField1(item.getBrandNo());
			vo.setExtendField2(item.getRootCategoryNo());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}

	/**
	 * 查询商品
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getItemExtends")
	@ResponseBody
	public Map<String, Object> getItemExtends(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req,model);
		this.setMulitParams(req, params);
		int total = this.commonUtilManager.selectItemExtendsCount(params);
		List<ItemDto> list = new ArrayList<ItemDto>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.commonUtilManager.selectItemExtendsList(page, sortColumn, sortOrder, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}

	/**
	 * 查询地区
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getZone")
	@ResponseBody
	public List<LookupVo> getZone(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = this.builderParams(req,model);
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		List<ZoneInfo> lstItem = zoneInfoManager.findByBiz(null, params);
		for (ZoneInfo item : lstItem) {
			LookupVo vo = new LookupVo();
			vo.setCode(item.getZoneNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		return lstVo;
	}
	
	/**
	 * 查询地区
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPriceZone")
	@ResponseBody
	public List<LookupVo> getPriceZone(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = this.builderParams(req,model);
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		List<ZoneInfo> lstItem = commonUtilManager.findPriceZones(params);
		for (ZoneInfo item : lstItem) {
			LookupVo vo = new LookupVo();
			vo.setCode(item.getZoneNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		return lstVo;
	}
	
	/**
	 * 查询商场
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getMall")
	@ResponseBody
	public List<Mall> getMall(String zoneNo) throws ManagerException {
		List<Mall> lstVo = new ArrayList<Mall>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("zoneNo", zoneNo);
		List<Mall> lstItem = mallManager.findByBiz(null, params);
		for (Mall item : lstItem) {
			Mall vo = new Mall();
			vo.setMallNo(item.getMallNo());
			vo.setName(item.getName());
			lstVo.add(vo);
		}
		return lstVo;
	}

	/**
	 * 查询门店
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getShop")
	@ResponseBody
	public List<Shop> getShop() throws ManagerException {
		List<Shop> lstVo = new ArrayList<Shop>();
		List<Shop> lstItem = shopManager.findByBiz(null, null);
		for (Shop item : lstItem) {
			Shop vo = new Shop();
			vo.setCode(item.getShopNo());
			vo.setShortName(item.getShortName());
			vo.setFullName(item.getFullName());
			lstVo.add(vo);
		}
		return lstVo;
	}
	
	/**
	 * 查询店铺
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCompToShop")
	@ResponseBody
	public Map<String, Object> getCompToShop(HttpServletRequest req, Model model) throws ManagerException {	
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String multiName = StringUtils.isEmpty(req.getParameter("multiName")) ? "" : String.valueOf(req.getParameter("multiName"));
		String multiNo = StringUtils.isEmpty(req.getParameter("multiNo")) ? "" : String.valueOf(req.getParameter("multiNo"));
		Map<String, Object> params = this.builderParams(req, model);
		params.put("shortNameLike", multiName);
		params.put("shopNoLike", multiNo);
		int total = this.shopManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Shop> list = this.shopManager.findByPage(page, sortColumn, sortOrder, params);
		for (Shop item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getShopNo());
			vo.setName(item.getShortName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 查询店铺与管理城市
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCompToShopAndOrgan")
	@ResponseBody
	public Map<String, Object> getCompToShopAndOrgan(HttpServletRequest req, Model model) throws ManagerException {	
		List<LookupVo> lstVo = new ArrayList<LookupVo>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String multiName = StringUtils.isEmpty(req.getParameter("multiName")) ? "" : String.valueOf(req.getParameter("multiName"));
		String multiNo = StringUtils.isEmpty(req.getParameter("multiNo")) ? "" : String.valueOf(req.getParameter("multiNo"));
		Map<String, Object> params = this.builderParams(req, model);
		params.put("shortNameLike", multiName);
		params.put("shopNoLike", multiNo);
		int total = this.shopManager.findShopAndOrganCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Shop> list = this.shopManager.findShopAndOrganByPage(page, sortColumn, sortOrder, params);
		for (Shop item : list) {
			LookupVo vo = new LookupVo();
			vo.setId(String.valueOf(item.getId()));
			vo.setCode(item.getShopNo());
			vo.setName(item.getShortName());
			vo.setBankName(item.getOrganName());
			lstVo.add(vo);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstVo);
		return obj;
	}
	
	/**
	 * 分页查询店铺
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPageShop")
	@ResponseBody
	public Map<String, Object> getPageShop(HttpServletRequest req, Model model) throws ManagerException {	
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req, model);
		setMulitParams(req, params);
		int total = commonUtilManager.findShopCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<ShopVo> list = commonUtilManager.findShopByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 获取当前用户
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCurrentUser")
	@ResponseBody
	public SystemUser getCurentUser(HttpServletRequest req) throws ManagerException {
		SystemUser user = CurrentUser.getCurrentUser(req);
		return user;
	}
	
	/**
	 * Excel模板下载
	 * @param fileName
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/download",produces = "text/plain;charset=UTF-8")
	public ResponseEntity<byte[]> down(@RequestParam(required = true, value = "fileName") String fileName,
			HttpServletRequest request) throws Exception {
		String ctxPath = request.getSession().getServletContext().getRealPath(PublicConstans.DOWNLOAD_DIR);
		String path = ctxPath + File.separator + fileName;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		File file = new File(path);
		byte[] data = null;
		if (file.exists()) {
			headers.setContentLength(file.length());
			headers.setContentDispositionFormData("attachment", new String(fileName.getBytes(PublicConstans.ENCODE),
					"ISO-8859-1"));
			data = FileUtils.readFileToByteArray(file);
		} else {
			headers.setContentDispositionFormData("attachment", "error.txt");
			data = "模板下载错误,请稍后再试!".getBytes();
		}
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
	}
	
	private Map<String, Object> builderParams(HttpServletRequest req, Model model) throws ManagerException{

		Map<String, Object> retParams = new HashMap<String,Object>(req.getParameterMap().size());
		Map<String, String[]> params = req.getParameterMap();
		if (null != params && params.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Entry<String, String[]> p : params.entrySet()) {
				if (null == p.getValue() || StringUtils.isEmpty(p.getValue().toString()))
					continue;
				// 只转换一个参数，多个参数不转换
				String values[] = (String[]) p.getValue();
				String match = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
				if (values[0].matches(match)) {
					try {
						retParams.put(p.getKey(), sdf.parse(values[0]));
					} catch (ParseException e) {
						retParams.put(p.getKey(), values);
						logger.error(e.getMessage(), e);
						throw new ManagerException(e.getMessage(), e);
					}
				}else if(p.getKey().equals("queryCondition")&&model.asMap().containsKey("queryCondition")){
					retParams.put(p.getKey(), model.asMap().get("queryCondition"));
				} else {
					retParams.put(p.getKey(), values[0]);
				}
			}
		}
		return retParams;
	}
	
	private void setMulitParams(HttpServletRequest req, Map<String, Object> retParams){
		String multiq = req.getParameter("q");
		if(StringUtils.isNotBlank(multiq)){
			multiq = multiq.toUpperCase();
			if(multiq.indexOf(",")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,","));
			}else if(multiq.indexOf("，")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,"，"));
			}else if(multiq.indexOf(";")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,";"));
			}else if(multiq.indexOf("；")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,"；"));
			}else if(multiq.indexOf("/")!=-1){
				retParams.clear();
				retParams.put("multiq", FasUtil.formatInQueryConditionByChar(multiq,"/"));
			}
		}
	}
	
	/**
	 * 导入校验
	 * @param fileName
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	public List<ValidateResultVo> doValidate(List<Object> lstItem, List<ValidateVo> lstValidate, Class clazz) throws Exception {
		List<ValidateResultVo> resultList = new ArrayList<ValidateResultVo>();
		if(!CollectionUtils.isEmpty(lstItem) && !CollectionUtils.isEmpty(lstValidate) ){
			for (int i = 0,iSize = lstItem.size(); i<iSize; i++) {
				ValidateResultVo resultVo = new ValidateResultVo();
				Object obj = lstItem.get(i);
				for (ValidateVo validate : lstValidate) {
					this.validate(validate, obj, clazz, resultVo);
				}
				String errorCode = resultVo.getErrorCode();
				String emptyCode = resultVo.getEmptyCode();
				if(StringUtils.isNotBlank(errorCode) || StringUtils.isNotBlank(emptyCode) ){
					String errorInfo = "";
					resultVo.setPass(YesNoEnum.NO.getValue());
					if(StringUtils.isNotBlank(errorCode)){
						errorInfo = errorInfo.concat("无效编码：").concat(errorCode.substring(0, errorCode.length()-1).concat(";"));
					}
					if(StringUtils.isNotBlank(emptyCode)){
						errorInfo = errorInfo.concat("必填字段：").concat(emptyCode.substring(0, emptyCode.length()-1));
					}
					resultVo.setErrorInfo(errorInfo);
				}else{
					resultVo.setPass(YesNoEnum.YES.getValue());
				}
				resultVo.setIndex(i+3);
				resultVo.setValidateObj(obj);
				resultList.add(resultVo);
			}
		}else{
			for (int i = 0,iSize = lstItem.size(); i<iSize; i++) {
				ValidateResultVo resultVo = new ValidateResultVo();
				Object obj = lstItem.get(i);
				resultVo.setPass(YesNoEnum.YES.getValue());
				resultVo.setIndex(i+3);
				resultVo.setValidateObj(obj);
				resultList.add(resultVo);
			}
		}
		return resultList;
	}
	
	@SuppressWarnings({"rawtypes" })
	private void validate(ValidateVo validate, Object obj , Class clazz, ValidateResultVo result)throws Exception{
		if(StringUtils.isBlank(validate.getPropertyName())){
			return ;
		}
		String propertyName = validate.getPropertyName();
		String cnPropertyName = validate.getCnPropertyName();
		Method getNoMethod = null;
		Method setNameMethod = null;
		if(StringUtils.isNotBlank(propertyName)){
			getNoMethod =  clazz.getDeclaredMethod("get".concat(propertyName.substring(0,1).toUpperCase()).concat(propertyName.substring(1)));
		}
		if(StringUtils.isNotBlank(cnPropertyName)){
			 setNameMethod =  clazz.getDeclaredMethod("set".concat(cnPropertyName.substring(0,1).toUpperCase()).concat(cnPropertyName.substring(1)),String.class);
		}
		String errorCode = result.getErrorCode();
		String emptyCode = result.getEmptyCode();
		Object validateObj = getNoMethod.invoke(obj);
		String validateNo = String.valueOf(validateObj);
		if(null!= validateObj && StringUtils.isNotBlank(validateNo)){
			Map<String, Object> params = new HashMap<String, Object>();
			if(validate.getValidateType() == ValidateTypeEnums.COMPANY.getTypeNo()){
				params.put("companyNo", validateNo);
				List<Company> list =  companyManager.findByBiz(null, params);
				if(list.size() > 0){
					if(null != setNameMethod){
						setNameMethod.invoke(obj, list.get(0).getName());
					}
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.SUPPLIER.getTypeNo()){
				params.put("supplierNo", validateNo);
				List<Supplier> list =  supplierManager.findByBiz(null, params);
				if(list.size() > 0){
					if(null != setNameMethod){
						setNameMethod.invoke(obj, list.get(0).getFullName());
					}
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.CUSTOMER.getTypeNo()){
				params.put("customerNo", validateNo);
				List<Customer> list =  customerManager.findByBiz(null, params);
				if(list.size() > 0){
					if(null != setNameMethod){
						setNameMethod.invoke(obj, list.get(0).getShortName());
					}
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.BRAND.getTypeNo()){
				params.put("brandNo", validateNo);
				List<Brand> list =  brandManager.findByBiz(null, params);
				if(list.size() > 0){
					if(null != setNameMethod){
						setNameMethod.invoke(obj, list.get(0).getName());
					}
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.CATEGORY.getTypeNo()){
				params.put("categoryNo", validateNo);
				params.put("levelid", 1);
				List<Category> list =  categoryManager.findByBiz(null, params);
				if(list.size() > 0){
					if(null != setNameMethod){
						setNameMethod.invoke(obj, list.get(0).getName());
					}
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.ITEM.getTypeNo()){
				params.put("code", validateNo);
				List<Item> list =  itemManager.findByBiz(null, params);
				if(list.size() > 0){
					if(null != setNameMethod){
						setNameMethod.invoke(obj, list.get(0).getName());
					}
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.FINANCIAL_CATEGORY.getTypeNo()){
				params.put("financialCategoryNo", validateNo);
				List<FinancialCategory> list =  financialCategoryManager.findByBiz(null, params);
				if(list.size() > 0){
					if(null != setNameMethod){
						setNameMethod.invoke(obj, list.get(0).getName());
					}
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.BRAND_UNIT.getTypeNo()){
				params.put("brandUnitNo", validateNo);
				List<BrandUnit> list =  brandUnitManager.findByBiz(null, params);
				if(list.size() > 0){
					if(null != setNameMethod){
						setNameMethod.invoke(obj, list.get(0).getName());
					}
				}else{
					errorCode = errorCode.concat(validateNo).concat(",");
				}
			}else if(validate.getValidateType() == ValidateTypeEnums.NUMBER.getTypeNo()){
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
	
	/**
	 * 查询机构
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPageOrganization")
	@ResponseBody
	public Map<String, Object>  getPageOrganization(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = this.builderParams(req,model);
		this.setMulitParams(req, params);
		int iCount = commonUtilManager.selectPageOrganizationCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, iCount);
		List<LookupVo> lstItem = commonUtilManager.selectPageOrganizationList(page, sortColumn, sortOrder, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", iCount);
		map.put("rows", lstItem);
		return map;
	}
	
	/**
	 * 查询机构类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getAccountInfo")
	@ResponseBody
	public Map<String, Object> getAccountInfo(HttpServletRequest req) throws ManagerException {
		Map<String, Object> map = new HashMap<String, Object>();
		String code = req.getParameter("code");
		String bankName = "";
		String bankAccount = "";
		if(StringUtils.isNotBlank(code)){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", code);
			List<Company> lstCompany = companyManager.findByBiz(null, params);
			if(lstCompany.size() > 0){
				bankName = lstCompany.get(0).getBankAccountName();
				bankAccount = lstCompany.get(0).getBankAccount();
			}else{
				params.clear();
				params.put("supplierNo", code);
				List<Supplier> lstSupplier = supplierManager.findByBiz(null, params);
				if(lstSupplier.size() > 0){
					bankName = lstSupplier.get(0).getBankAccountName();
					bankAccount = lstSupplier.get(0).getBankAccount();
				}
			}
		}
		map.put("bankName", bankName);
		map.put("bankAccount", bankAccount);
		return map;
	}
	
	/**
	 * 查询数据字典
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getLookup")
	@ResponseBody
	public List<LookupEntry> getLookup(HttpServletRequest req) throws ManagerException {
		String lookupId = req.getParameter("lookupId");
		Map<String, Object> params = new HashMap<String, Object>();
		List<LookupEntry> lstItem = null;
		if(StringUtils.isNotBlank("lookupId")){
			params.put("lookupId", lookupId);
			lstItem = lookupEntryManager.findByBiz(null, params);
		}
		return lstItem;
	}
	
	/**
	 * 查询采购价
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPurchasePrice")
	@ResponseBody
	public PurchasePrice  getPurchasePrice(HttpServletRequest req) throws ManagerException {
		String itemNo = req.getParameter("itemNo");
		String supplierNo = req.getParameter("supplierNo");
		String billDate = req.getParameter("billDate");
		if(StringUtils.isNotBlank(itemNo) && StringUtils.isNotBlank(supplierNo) && StringUtils.isNotBlank(billDate)){
			return purchasePriceManager.findBalancePurchasePrice(itemNo, supplierNo, DateUtil.parseToDate(billDate, "yyyy-MM-dd"));
		}
		return null;
	}

	/**
	 * 查询采购价
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getPurchasePriceByItemNo")
	@ResponseBody
	public PurchasePrice  getPurchasePriceByItemNo(HttpServletRequest req) throws ManagerException {
		String itemNo = req.getParameter("itemNo");
		String billDate = req.getParameter("billDate");
		if(StringUtils.isNotBlank(itemNo)){
			return purchasePriceManager.findBalancePurchasePriceByItemNo(itemNo, DateUtil.parseToDate(billDate, "yyyy-MM-dd"));
		}
		return null;
	}
	
}
