package cn.wonhigh.retail.fas.web.controller;

import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.model.SettlementBodyChangeRecord;
import cn.wonhigh.retail.fas.common.model.Store;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.OrderUnitManager;
import cn.wonhigh.retail.fas.manager.PeriodBalanceManager;
import cn.wonhigh.retail.fas.manager.SettlementBodyChangeRecordManager;
import cn.wonhigh.retail.fas.manager.StoreManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.mdm.api.util.CacheContext;
import cn.wonhigh.retail.mdm.common.model.Category;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-12-28 11:09:32
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
@RequestMapping("/settlement_body_change_record")
@ModuleVerify("30120009")
public class SettlementBodyChangeRecordController extends BaseCrudController<SettlementBodyChangeRecord> {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

	@Resource
    private SettlementBodyChangeRecordManager settlementBodyChangeRecordManager;
    
    @Resource
    private PeriodBalanceManager periodBalanceManager;
    
    @Resource
    private BrandManager brandManager;
    
    @Resource
    private BrandUnitManager brandUnitManager;
    
    @Resource
    private CompanyManager companyMananger;
    
    @Resource
    private OrderUnitManager orderUnitMananger;
    
    @Resource
    private StoreManager storeMananger;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("settlement_body_change_record/",settlementBodyChangeRecordManager);
    }
    
    @RequestMapping(value = "/list.html")
    public ModelAndView  selectAreaPrice(){
    	ModelAndView  mav=new ModelAndView();
    	mav.setViewName("settlement_body_change_record/list");
    	return mav;
    }
    
    @Override
  	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
              int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
              int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
              String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
              String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
              Map<String,Object> params = builderParams(req, model);
              if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
      			params.put("brandNo", Arrays.asList(req.getParameter("brandNo").split(",")));
      		  }
              if (StringUtils.isNotEmpty(req.getParameter("categoryNo"))) {
     				params.put("categoryNo", Arrays.asList(req.getParameter("categoryNo").split(",")));
              }
              int total = settlementBodyChangeRecordManager.findCount(params);
              SimplePage page = new SimplePage(pageNo, pageSize, total);
              List<SettlementBodyChangeRecord> list = settlementBodyChangeRecordManager.findByPage(page, sortColumn, sortOrder, params);
              CacheContext context = CacheContext.current();
              for(SettlementBodyChangeRecord settlementBody:list){
            	  if(settlementBody.getCategoryNo()!=null && settlementBody.getCategoryNo()!=""){
            		  Category category = context.getCategory(settlementBody.getCategoryNo());
            		  if(category!=null && category.getName()!=null){
            			  settlementBody.setCategoryName(category.getName());
            		  }
            		  Category category1 = context.getCategory(settlementBody.getCategoryNo().substring(0, 2));
            		  if(category1!=null && category1.getName()!=null){
            			  settlementBody.setFirstLevelCategoryName(category1.getName());
            		  }
            		  Category category2 = context.getCategory(settlementBody.getCategoryNo().substring(0, 4));
            		  if(category2!=null && category2.getName()!=null){
            			  settlementBody.setSecondLevelCategoryName(category2.getName());
            		  }
            	  }
              }
              SettlementBodyChangeRecord settlementBodyChangeRecord=settlementBodyChangeRecordManager.findByPageSum(params);
              List footerList=new ArrayList();
              footerList.add(settlementBodyChangeRecord);
              Map obj = new HashMap();
              obj.put("total", total);
              obj.put("rows", list);
              obj.put("footer",footerList);
              return obj;
          }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, SettlementBodyChangeRecord modelType)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	try {
    		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<SettlementBodyChangeRecord> entityClass = (Class<SettlementBodyChangeRecord>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<SettlementBodyChangeRecord> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), SettlementBodyChangeRecord.class,entityClass.getSimpleName());
			// 数据校验
    		List<Object> error = getDataValidators(datas, request);
			StringBuilder errorBuilder = new StringBuilder();
			if (null != error && error.size() > 0) {
				for (int i = 0; i < error.size(); i++) {
					errorBuilder.append(error.get(i) + "<br/>");
				}
			}
			if (errorBuilder.length() == 0) {
	    		int count = 0;
	    		for (SettlementBodyChangeRecord settlementBodyChangeRecord : datas) {
	    			settlementBodyChangeRecord.setCreateTime(new Date());
	    			settlementBodyChangeRecord.setCreateUser(loginUser.getUsername());
	    			settlementBodyChangeRecordManager.add(settlementBodyChangeRecord);
	    			count ++;
	    		}
				if (count > 0) {
					mv.addObject("success", "成功导入" + count + "条记录");
				} else{
					mv.addObject("error", "没有要导入的行！");
				}
			} else {
				mv.addObject("error", errorBuilder);
			}
			return mv;
	    	
		}catch (NumberFormatException e) {
			mv.addObject("error", "导入数据的不是数字格式！");
			return mv;
		}catch (ParseException e) {
			mv.addObject("error", "导入数据的变更日期不正确，格式需为yyyy-MM-dd或yyyy/MM/dd");
			return mv;
		}catch (Exception e) {
			mv.addObject("error", "导入数据发生其他异常，请联系管理员");
			return mv;
		}
	}

	/**
	 * 校验数据方法
	 * @param datas List<BillInvCostAdjustDtl>
	 * @param request HttpServletRequest
	 * @return 返回校验错误信息集合
	 */
	private List<Object> getDataValidators(List<SettlementBodyChangeRecord> datas, HttpServletRequest request) throws ManagerException{
		List<Object> errors = new ArrayList<Object>();
		try {
				if(datas != null && datas.size() > 0) {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					List<PeriodBalance> PeriodBalanceList=null;
					int lineNum = 2;
					String errorMessage = null;
					for(SettlementBodyChangeRecord costMaintain : datas) {
						//判断空数据
						if(StringUtils.isEmpty(costMaintain.getItemCode())||StringUtils.isEmpty(costMaintain.getBrandNo())
								||StringUtils.isEmpty(costMaintain.getOriginalCompanyNo())||StringUtils.isEmpty(costMaintain.getOriginalOrderUnitNo())
								||StringUtils.isEmpty(costMaintain.getOriginalStoreNo())||(costMaintain.getStockType()==null || StringUtils.isEmpty(costMaintain.getStockType().toString()))
								||StringUtils.isEmpty(costMaintain.getTargetCompanyNo())||StringUtils.isEmpty(costMaintain.getTargetOrderUnitNo())
								||StringUtils.isEmpty(costMaintain.getTargetStoreNo())){
							errorMessage = "第" + lineNum + "行必填数据有空记录";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}
						if(costMaintain.getStockType()<0 || costMaintain.getStockType()>3){
							errorMessage = "第" + lineNum + "行数据的存货属性值不正确";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}
						paramsMap.put("itemCode",costMaintain.getItemCode());
						paramsMap.put("multiBrands", Arrays.asList(costMaintain.getBrandNo().split(",")));
						paramsMap.put("companyNo", costMaintain.getOriginalCompanyNo());
						paramsMap.put("orderUnitNo",costMaintain.getOriginalOrderUnitNo());
						paramsMap.put("storeNo",costMaintain.getOriginalStoreNo());
						PeriodBalanceList=periodBalanceManager.findByBiz(null, paramsMap);
						if(PeriodBalanceList.size()==0){
							errorMessage = "第" + lineNum + "行数据的商品编码+品牌+原公司+原订货单位+原机构不存在结存数据中";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}else{
							costMaintain.setItemNo(PeriodBalanceList.get(0).getItemNo());
							costMaintain.setItemName(PeriodBalanceList.get(0).getItemName());
							if(costMaintain.getQty()==null || StringUtils.isEmpty(costMaintain.getQty().toString())){
								costMaintain.setQty(PeriodBalanceList.get(0).getClosingQty());
							}
							if(costMaintain.getStockCost()==null || StringUtils.isEmpty(costMaintain.getStockCost().toString())){
								costMaintain.setStockCost(PeriodBalanceList.get(0).getUnitCost());
							}
							if(costMaintain.getChangePrice()==null || StringUtils.isEmpty(costMaintain.getChangePrice().toString())){
								costMaintain.setChangePrice(PeriodBalanceList.get(0).getUnitCost());
							}
							if(costMaintain.getChangeDate()==null || StringUtils.isEmpty(costMaintain.getChangeDate().toString())){
								costMaintain.setChangeDate(new Date());
							}
						}
						paramsMap.clear();
						paramsMap.put("brandNo", costMaintain.getBrandNo());
						List<Brand> Brandlist =  brandManager.findByBiz(null, paramsMap);
						costMaintain.setBrandName(Brandlist.get(0).getName());
						costMaintain.setBrandUnitNo(Brandlist.get(0).getSysNo());
						paramsMap.clear();
						paramsMap.put("brandUnitNo",costMaintain.getBrandUnitNo());
						List<BrandUnit> brandUnitList =brandUnitManager.findByBiz(null, paramsMap);
						costMaintain.setBrandUnitName(brandUnitList.get(0).getName());
						paramsMap.clear();
						paramsMap.put("companyNo",costMaintain.getOriginalCompanyNo());
						List<Company> companyList =companyMananger.findByBiz(null, paramsMap);
						costMaintain.setOriginalCompanyName(companyList.get(0).getName());
						paramsMap.clear();
						paramsMap.put("orderUnitNo",costMaintain.getOriginalOrderUnitNo());
						List<OrderUnit> orderUnitList =orderUnitMananger.findByBiz(null, paramsMap);
						costMaintain.setOriginalOrderUnitName(orderUnitList.get(0).getName());
						paramsMap.clear();
						paramsMap.put("storeNo",costMaintain.getOriginalStoreNo());
						List<Store> storeList =storeMananger.findByBiz(null, paramsMap);
						costMaintain.setOriginalStoreName(storeList.get(0).getShortName());
						paramsMap.clear();
						paramsMap.put("companyNo",costMaintain.getTargetCompanyNo());
						List<Company> companyList1 =companyMananger.findByBiz(null, paramsMap);
						if(companyList1.size()==0){
							errorMessage = "第" + lineNum + "行数据中目的公司不存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}else{
							costMaintain.setTargetCompanyName(companyList1.get(0).getName());
						}
						paramsMap.clear();
						paramsMap.put("orderUnitNo",costMaintain.getTargetOrderUnitNo());
						List<OrderUnit> orderUnitList1 =orderUnitMananger.findByBiz(null, paramsMap);
						if(orderUnitList1.size()==0){
							errorMessage = "第" + lineNum + "行数据中目的订货单位不存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}else{
							costMaintain.setTargetOrderUnitName(orderUnitList1.get(0).getName());
						}
						paramsMap.clear();
						paramsMap.put("storeNo",costMaintain.getTargetStoreNo());
						List<Store> storeList1 =storeMananger.findByBiz(null, paramsMap);
						if(storeList1.size()==0){
							errorMessage = "第" + lineNum + "行数据中目的机构不存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}else{
							costMaintain.setTargetStoreName(storeList1.get(0).getShortName());
						}
						lineNum++;
					}
				}
			} catch (ManagerException e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
			return errors;
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
   		if (StringUtils.isNotEmpty(req.getParameter("brandNo"))) {
  			params.put("brandNo", Arrays.asList(req.getParameter("brandNo").split(",")));
  		  }
   		if (StringUtils.isNotEmpty(req.getParameter("categoryNo"))) {
				params.put("categoryNo", Arrays.asList(req.getParameter("categoryNo").split(",")));
   		}
   		int total = settlementBodyChangeRecordManager.findCount(params);
   		SimplePage page = new SimplePage(0, total,  total);
   		List<SettlementBodyChangeRecord> list = settlementBodyChangeRecordManager.findByPage(page, sortColumn, sortOrder, params);
   		SettlementBodyChangeRecord BodyChangeRecord=settlementBodyChangeRecordManager.findByPageSum(params);
   		list.add(BodyChangeRecord);
   		try {
   			CacheContext context = CacheContext.current();
   			for(SettlementBodyChangeRecord settlementBodyChangeRecord:list){
   				if(settlementBodyChangeRecord.getStockType()!=null && settlementBodyChangeRecord.getStockType()==0){
   					settlementBodyChangeRecord.setStockTypeName("正品");
   				}else if(settlementBodyChangeRecord.getStockType()!=null && settlementBodyChangeRecord.getStockType()==1){
   					settlementBodyChangeRecord.setStockTypeName("次品");
   				}else if(settlementBodyChangeRecord.getStockType()!=null && settlementBodyChangeRecord.getStockType()==2){
   					settlementBodyChangeRecord.setStockTypeName("原残");
   				}else if(settlementBodyChangeRecord.getStockType()!=null && settlementBodyChangeRecord.getStockType()==3){
   					settlementBodyChangeRecord.setStockTypeName("客残");
   				}
   				
	   			if(settlementBodyChangeRecord.getCategoryNo()!=null && settlementBodyChangeRecord.getCategoryNo()!=""){
	       		  Category category = context.getCategory(settlementBodyChangeRecord.getCategoryNo());
	       		  if(category!=null && category.getName()!=null){
	       			settlementBodyChangeRecord.setCategoryName(category.getName());
	       		  }
	       		  Category category1 = context.getCategory(settlementBodyChangeRecord.getCategoryNo().substring(0, 2));
	       		  if(category1!=null && category1.getName()!=null){
	       			settlementBodyChangeRecord.setFirstLevelCategoryName(category1.getName());
	       		  }
	       		  Category category2 = context.getCategory(settlementBodyChangeRecord.getCategoryNo().substring(0, 4));
	       		  if(category2!=null && category2.getName()!=null){
	       			settlementBodyChangeRecord.setSecondLevelCategoryName(category2.getName());
	       		  }
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