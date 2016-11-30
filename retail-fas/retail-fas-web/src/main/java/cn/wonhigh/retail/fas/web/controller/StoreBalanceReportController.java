package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;
import cn.wonhigh.retail.fas.manager.OrderUnitManager;
import cn.wonhigh.retail.fas.manager.PeriodBalanceManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;
import cn.wonhigh.retail.mdm.api.util.CacheContext;
import cn.wonhigh.retail.mdm.common.model.Category;
import cn.wonhigh.retail.mdm.common.model.Company;
import cn.wonhigh.retail.mdm.common.model.Item;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-28 09:02:52
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
@RequestMapping("/store_balance_report")
@ModuleVerify("30120007")
public class StoreBalanceReportController extends BaseController<PeriodBalance> {
	
    @Resource
    private PeriodBalanceManager periodBalanceManager;
    
    @Resource
    private OrderUnitManager orderUnitManager;
    
    @Resource
    private BrandUnitManager brandUnitManager;
    
    Map<String, BrandUnit> brandUnits = new HashMap<String, BrandUnit>();

    @Override
    public CrudInfo init() {
        return new CrudInfo("store_balance_report/",periodBalanceManager);
    }
    
    @RequestMapping(value = "/store_balance_report.json")
   	@ResponseBody
   	public  Map<String, Object> queryStoreBalanceReportList(HttpServletRequest req, Model model) throws ManagerException {
    	Map<String, Object> obj = new HashMap<String,Object>();
		Map<String, Object> params = builderParams(req, model);
		int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	String startYearMonth = StringUtils.isBlank(req.getParameter("startYearMonth")) ? "" : req.getParameter("startYearMonth");
    	String endYearMonth = StringUtils.isBlank(req.getParameter("endYearMonth")) ? "" : req.getParameter("endYearMonth");
    	params = convertYearMoth(startYearMonth, endYearMonth, params);
    	
    	if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("multiBrands", Arrays.asList(req.getParameter("brandNos").split(",")));
		}
		if (StringUtils.isNotEmpty(req.getParameter("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(req.getParameter("categoryNos").split(",")));
		}
		if (StringUtils.isNotEmpty(req.getParameter("organNos"))) {
			params.put("multiOrganNo", Arrays.asList(req.getParameter("organNos").split(",")));
		}
		if (StringUtils.isNotEmpty(req.getParameter("orderUnitNos"))) {
			params.put("orderUnitNos", Arrays.asList(req.getParameter("orderUnitNos").split(",")));
		}else{
			params.put("orderUnitNos", this.getOrderUnitNos(params));
		}
		
		params.put("itemNos", periodBalanceManager.getAllItemNos(params));//从period_balance表中获取当前查询条件所有ItemNos
		
		String orderByField = null;
		String orderBy = null;
		
		//设置分组汇总的分组条件
		this.setCollectGroupByParam1(params);
		
		int total = periodBalanceManager.queryStoreBalanceCount(params);
		
		List<PeriodBalance> list = new ArrayList<PeriodBalance>();
		List<PeriodBalance> footer = new ArrayList<PeriodBalance>();
		if(total > 0){
			SimplePage page = new SimplePage(pageNo,pageSize,total);
			list = periodBalanceManager.queryStoreBalanceList(page,orderByField,orderBy,params);
			footer.add(periodBalanceManager.queryStoreBalanceAll(params));
		}
		
		obj.put("total", total);
		obj.put("rows", periodBalanceManager.setStoreBalanceReportProperties(list)); 
		obj.put("footer", footer);
		obj.put("footer", footer);
		
		String organTypeNo = Authorization.getUser().getOrganTypeNo();
		params.put("organTypeNo", organTypeNo);
		List<BrandUnit> ls = brandUnitManager.findByBiz(null, params);
		for (BrandUnit brandUnit : ls) {
			brandUnits.put(brandUnit.getBrandUnitNo(), brandUnit);
		}
		return obj;
    }
    
	private void setCollectGroupByParam1(Map<String, Object> params) {
		params.put("flag", "false");
    	StringBuilder groupBySql = new StringBuilder();
		if (params.get("isCheckShopNo").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("p.store_no,");
		}
		if (params.get("isCheckGender").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("i.gender,");
		}
		if (params.get("isCheckBrandUnit").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("b.sys_no,");
		}
		if (params.get("isCheckCategory").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("first_level_category_no,");
		}
		if (params.get("isCheckRootCategory").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("second_level_category_no,");
		}
		if (params.get("isCheckYear").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("i.years,");
		}
		if (params.get("isCheckSellSeason").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("i.sell_season,");
		}
		if(groupBySql.length()>0){
			params.put("groupBySql", groupBySql.deleteCharAt(groupBySql.length()-1));
		}
	}

	@RequestMapping(value = "/store_balance_report1.json")
   	@ResponseBody
   	public  Map<String, Object> queryGMSBalanceList(HttpServletRequest req, Model model) throws ManagerException {
    	int total = 0;
    	SimplePage page = null;
		List<PeriodBalance> list = null;
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String isSubTotal = StringUtils.isEmpty(req.getParameter("isSubTotal")) ? "" : req.getParameter("isSubTotal");
    	String startYearMonth = StringUtils.isBlank(req.getParameter("startYearMonth")) ? "" : req.getParameter("startYearMonth");
    	String endYearMonth = StringUtils.isBlank(req.getParameter("endYearMonth")) ? "" : req.getParameter("endYearMonth");
		Map<String, Object> params = builderParams(req, model);
		params = convertYearMoth(startYearMonth, endYearMonth, params);
		if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("multiBrands", Arrays.asList(req.getParameter("brandNos").split(",")));
		}
		if (StringUtils.isNotEmpty(req.getParameter("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(req.getParameter("categoryNos").split(",")));
		}
		if (StringUtils.isNotEmpty(req.getParameter("organNos"))) {
			params.put("multiOrganNo", Arrays.asList(req.getParameter("organNos").split(",")));
		}
		
		params.put("orderUnitNos", this.getOrderUnitNos(params));//订货单位获取
		
		//设置分组汇总的分组条件
		setCollectGroupByParam(params);
		//按照小计方式查询
		if(isSubTotal.equals("true")){
			total = periodBalanceManager.findCompanyPeriodBalanceSubTotalCount1(params);
			page = new SimplePage(pageNo, pageSize, (int) total);
			list = periodBalanceManager.findCompanyPeriodBalanceSubTotalPages1(page, sortColumn, sortOrder, params);
		}else {
			total = periodBalanceManager.findStoreBalanceCount(params);
			page = new SimplePage(pageNo, pageSize, (int) total);
			list = periodBalanceManager.findStoreBalanceList(page, sortColumn, sortOrder, params);
		}
		//计算牌价额
		getTagAoumtByCache(list);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
//		obj.put("rows", periodBalanceManager.setStoreBalanceReportProperties(list));
		obj.put("rows", list);
		return obj;
   	}
    
    private List<String> getOrderUnitNos(Map<String, Object> map) throws ManagerException {
    	String compangNo = (String) map.get("companyNo");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("companyNo", compangNo);
    	
    	List<OrderUnit> list=orderUnitManager.findByBiz(null, params);//查询公司下的所有订货单位
		List<String> orderUnitNos = new ArrayList<String>();
		for(OrderUnit orderUnit:list){
			orderUnitNos.add(orderUnit.getOrderUnitNo());
		}
		return orderUnitNos;
	}

	private void setCollectGroupByParam(Map<String, Object> params) {
		params.put("flag", "false");
    	StringBuilder groupBySql = new StringBuilder();
		if (params.get("isCheckShopNo").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("p.store_no,");
		}
		if (params.get("isCheckGender").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("le1.`name`,");
		}
		if (params.get("isCheckBrandUnit").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("bu.brand_unit_no,");
		}
		if (params.get("isCheckCategory").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("ca1.category_no,");
		}
		if (params.get("isCheckRootCategory").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("ca2.category_no,");
		}
		if (params.get("isCheckYear").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("le.name,");
		}
		if (params.get("isCheckSellSeason").equals("true")) {
			params.put("flag", "true");
			groupBySql.append("le2.name,");
		}
		if(groupBySql.length()>0){
			params.put("groupBySql", groupBySql.deleteCharAt(groupBySql.length()-1));
		}
		
	}
	

	
	@Override
    protected List<PeriodBalance> queryExportData(Map<String, Object> params) throws ManagerException {
    	String startYearMonth = StringUtils.isBlank(String.valueOf(params.get("startYearMonth"))) ? "" : String.valueOf(params.get("startYearMonth"));
    	String endYearMonth = StringUtils.isBlank(String.valueOf(params.get("endYearMonth"))) ? "" : String.valueOf(params.get("endYearMonth"));
    	params = convertYearMoth(startYearMonth, endYearMonth, params);
    	
    	if (null != params.get("brandNos") && StringUtils.isNotEmpty((String)params.get("brandNos"))) {
			params.put("multiBrands", Arrays.asList(((String)params.get("brandNos")).split(",")));
		}
		if (null != params.get("categoryNos") && StringUtils.isNotEmpty((String)params.get("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(((String)params.get("categoryNos")).split(",")));
		}
		if (null != params.get("organNos") && StringUtils.isNotEmpty((String)params.get("organNos"))) {
			params.put("multiOrganNo", Arrays.asList(((String)params.get("organNos")).split(",")));
		}
		if (null != params.get("orderUnitNos")) {
			params.put("orderUnitNos", Arrays.asList(((String)params.get("orderUnitNos")).split(",")));
		}else{
			params.put("orderUnitNos", this.getOrderUnitNos(params));
		}
		
		params.put("itemNos", periodBalanceManager.getAllItemNos(params));//从period_balance表中获取当前查询条件所有ItemNos
		
		//设置分组汇总的分组条件
		this.setCollectGroupByParam1(params);
		
		int total = periodBalanceManager.queryStoreBalanceCount(params);
		
		List<PeriodBalance> list = new ArrayList<PeriodBalance>();
		if(total > 0){
			SimplePage page = new SimplePage(1,total,total);
			list = periodBalanceManager.queryStoreBalanceList(page,null,null,params);
		}
		
		return periodBalanceManager.setStoreBalanceReportProperties(list);
	}
	
	/**
	 * 根据商品编号及品牌，查询商品缓存，用期末数量计算牌价额
	 * @param list
	 * @author wang.yj
	 */
	private void getTagAoumtByCache(List<PeriodBalance> list){
		CacheContext context = CacheContext.current();
		for (PeriodBalance periodBalance : list) {
			Item item = context.getItem(periodBalance.getItemCode(), periodBalance.getBrandNo());
			if(null != item && null != item.getTagPrice()){
				periodBalance.setTagPrice(new BigDecimal(periodBalance.getClosingQty()).multiply(item.getTagPrice()));
			}else{
				periodBalance.setTagPrice(new BigDecimal(0.00));
			}
		}
	}
    
    @RequestMapping(value = "/do_store_balance_export")
	public void storeBalanceExport(HttpServletRequest request, Model model,HttpServletResponse response)
			throws ManagerException {

		Map<String, Object> params = builderParams(request, model);
		//地区类型多选
		if (null != params.get("brandNos") && StringUtils.isNotEmpty((String)params.get("brandNos"))) {
			params.put("multiBrands", Arrays.asList(((String)params.get("brandNos")).split(",")));
		}
		if (null != params.get("categoryNos") && StringUtils.isNotEmpty((String)params.get("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(((String)params.get("categoryNos")).split(",")));
		}
		if (null != params.get("organNos") && StringUtils.isNotEmpty((String)params.get("organNos"))) {
			params.put("multiOrganNo", Arrays.asList(((String)params.get("organNos")).split(",")));
		}
		
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String fileName = (String) params.get("fileName");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		if (!StringUtils.isNotEmpty(exportColumns))
			return;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			ExportComplexVo exportVo = new ExportComplexVo();
			exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
			Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
			exportVo.setRowAccessWindowSize(rowAccessWindowSize);
			List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
			});
			
			// 对手动隐藏的列进行处理，把手动隐藏的列过滤掉，不导出来
			List<Map> tempColumnsList = new ArrayList<Map>();
			for (Map map : columnsList) {
				Object hid = map.get("hidden");
				if(null == hid || !hid.equals(true)){
					tempColumnsList.add(map);
				}
			}
			
			tempColumnsList = this.sortExportColumns(tempColumnsList);
			exportVo.setColumnsMapList(tempColumnsList);
			if (StringUtils.isNotEmpty(firstHeaderColumns)) {
				List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
				});
				exportVo.setHeaderColumnsMapList(headerColumnsList);
			}

			final HSSFExportComplex exportExcel = new HSSFExportComplex(exportVo);
			Function<Object, Boolean> handler = new Function<Object, Boolean>() {
				@SuppressWarnings({ "rawtypes" })
				@Override
				public Boolean apply(Object input) {
					Map vals = (Map) input;
					
					exportExcel.write(vals);
					return true;
				}
			};
			SimplePage page = new SimplePage();
			page.setPageSize(Integer.MAX_VALUE);
			periodBalanceManager.storeBalanceExport(page, params, handler);
		
			exportExcel.flush(response);
			exportExcel.close();
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void queryResultMap(Map vals) {
    	
    	//是否是小计行
    	boolean isCount = false;
    	if (null != vals.get("categoryNo") && vals.get("categoryNo").toString().indexOf("zzzzz") != -1) {
    		isCount = true;
    	}
    	
    	//从主数据获取静态信息
    	CacheContext context = CacheContext.current();
    	
    	if(null != vals.get("companyNo")) {
    		Company company = context.getCompany(vals.get("companyNo").toString());
        	if(null != company) {
        		vals.put("companyName", company.getName());
        	}
    	}
    	
    	if(null != vals.get("brandUnitNo")) {
    		BrandUnit brandUnit = brandUnits.get(vals.get("brandUnitNo").toString());
    		if(null != brandUnit){
    			vals.put("brandUnitName", brandUnit.getName());
    		}
    	}
    	
    	if(null != vals.get("orderUnitNo")) {
    		cn.wonhigh.retail.mdm.common.model.OrderUnit orderUnit = context.getOrderUnit(vals.get("orderUnitNo").toString());
	    	if(null != orderUnit) {
	    		vals.put("orderUnitName", orderUnit.getName());
	    	}
    	}
    	if(null != vals.get("storeNo") && !"All".equals(vals.get("storeNo").toString())){
    		String storeNo = vals.get("storeNo").toString();
    		if(storeNo.length()==6){
    			cn.wonhigh.retail.mdm.common.model.Shop shop = context.getShop(storeNo);
    			if(null != shop && null != shop.getStoreNo()){
    				vals.put("storeName",shop.getFullName());
    			}
    		}else{
    			cn.wonhigh.retail.mdm.common.model.Store store = context.getStore(storeNo);
    			if(null != store && null != store.getStoreNo()){
    				vals.put("storeName",store.getFullName());
    			}
    		}
    	}
		
    	if(null != vals.get("organNo")){
	    	cn.wonhigh.retail.mdm.common.model.Organ organ = context.getOrgan(vals.get("organNo").toString());
			if(null != organ && null != organ.getOrganNo()){
				vals.put("organName",organ.getName());
			}
    	}
    	
    	if(null != vals.get("itemCode") && null != vals.get("brandNo")) {
	    	Item item = context.getItem(vals.get("itemCode").toString(), vals.get("brandNo").toString());
	    	if(null != item) {
	    		Category category = context.getCategory(item.getCategoryNo());
		    	if(null != category) {
		    		vals.put("categoryName", category.getName());
		    		if (category.getCategoryNo().length() >= 2) {
						String rootCategoryNo = category.getCategoryNo().substring(0, 2);
						Category rootCategory = context.getCategory(rootCategoryNo);
						if (null != rootCategory) {
							vals.put("firstLevelCategoryNo", rootCategory.getCategoryNo());
				        	vals.put("firstLevelCategoryName", rootCategory.getName());
						}
					}
					if (category.getCategoryNo().length() >= 4 && !isCount) {
						String secondCategoryNo = category.getCategoryNo().substring(0, 4);
						Category secondCategory = context.getCategory(secondCategoryNo);
						if (null != secondCategory) {
							vals.put("secondLevelCategoryNo", secondCategory.getCategoryNo());
				        	vals.put("secondLevelCategoryName", secondCategory.getName());
						}
					}
		    	}
		    	
	    		vals.put("yearsName", context.getLookupName(item.getYears()));
	        	vals.put("seasonName", context.getLookupName(item.getSellSeason()));
	        	vals.put("gender", context.getLookupName(item.getGender()));
	        	vals.put("orderfrom", context.getLookupName(item.getOrderfrom()));
	    	}
    	}
    	
    	if(isCount) {
    		vals.put("companyName", "小计：");
    		vals.put("itemCode", "");
    		vals.put("itemName", "");
    		vals.put("categoryName", "");
    		vals.put("yearsName", "");
    		vals.put("seasonName", "");
    		vals.put("orderfrom", "");
    		vals.put("gender", "");
    	}
    	
    }
    
    @Override
	protected void selectManagerMenthod(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException {
    	
    	if(null == brandUnits || brandUnits.size() == 0){
    		String organTypeNo = Authorization.getUser().getOrganTypeNo();
    		params.put("organTypeNo", organTypeNo);
    		List<BrandUnit> ls = brandUnitManager.findByBiz(null, params);
    		for (BrandUnit brandUnit : ls) {
    			brandUnits.put(brandUnit.getBrandUnitNo(), brandUnit);
    		}
    	}
    	String startYearMonth = null;
    	String endYearMonth = null;
    	//地区类型多选
		if (null != params.get("brandNos") && StringUtils.isNotEmpty((String)params.get("brandNos"))) {
			params.put("multiBrands", Arrays.asList(((String)params.get("brandNos")).split(",")));
		}
		if (null != params.get("categoryNos") && StringUtils.isNotEmpty((String)params.get("categoryNos"))) {
			params.put("multiCategorys", Arrays.asList(((String)params.get("categoryNos")).split(",")));
		}
		if (null != params.get("organNos") && StringUtils.isNotEmpty((String)params.get("organNos"))) {
			params.put("multiOrganNo", Arrays.asList(((String)params.get("organNos")).split(",")));
		}
		if(null != params.get("startYearMonth") && StringUtils.isNotBlank((String)params.get("startYearMonth"))) {
    		startYearMonth = params.get("startYearMonth").toString();
    	}
    	if(null != params.get("endYearMonth") && StringUtils.isNotBlank((String)params.get("endYearMonth"))) {
    		endYearMonth = params.get("endYearMonth").toString();
    	}
    	params = convertYearMoth(startYearMonth, endYearMonth, params);
    	
		
		params.put("orderUnitNos", this.getOrderUnitNos(params));//订货单位获取
		params.put("itemNos", periodBalanceManager.getAllItemNos(params));//从period_balance表中获取当前查询条件所有ItemNos
		
		//设置分组汇总的分组条件
		this.setCollectGroupByParam1(params);
		
    	this.periodBalanceManager.queryStoreBalanceList(page, params, handler);
    }
    
  private Map<String, Object> convertYearMoth(String startYearMonth, String endYearMonth, Map<String,Object> params){
    	
    	List<PeriodBalance> list = new ArrayList<PeriodBalance>();
    	if(startYearMonth.equals("") || endYearMonth.equals("") || startYearMonth.length()<6 || endYearMonth.length()<6){
    		return params;
    	}
    	int startYear = Integer.parseInt(startYearMonth.substring(0,4));
    	int startMonth = Integer.parseInt(startYearMonth.substring(4,6));
    	int endYear = Integer.parseInt(endYearMonth.substring(0,4));
    	int endMonth = Integer.parseInt(endYearMonth.substring(4,6));
    	int tempYear = endYear - startYear;
    	PeriodBalance perBalance;
    	if( tempYear == 0){
    		for(int i = startMonth; i<= endMonth; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(startYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    	}else if(tempYear >= 1){
    		for(int i= startMonth; i<=12; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(startYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    		for(int i = 1; i< endYear-startYear; i++){
    			for (int j = 1; j <= 12; j++) {
    				perBalance = new PeriodBalance();
        			perBalance.setYear(startYear+i+"");
        			perBalance.setMonth(j+"");
        			list.add(perBalance);
				}
    		}
    		for(int i = 1; i<= endMonth; i++){
    			perBalance = new PeriodBalance();
    			perBalance.setYear(endYear+"");
    			perBalance.setMonth(i+"");
    			list.add(perBalance);
    		}
    	}
    	params.put("multiYearAndMonth", list);
    	return params;
    }
    
}