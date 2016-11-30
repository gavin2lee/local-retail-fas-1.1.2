package cn.wonhigh.retail.fas.web.controller;

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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.ShopExtensionDto;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillSalesOutstandingAnalysis;
import cn.wonhigh.retail.fas.common.model.ShopNameReplace;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.manager.BillSalesOutstandingAnalysisManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.manager.ShopNameReplaceManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;
import cn.wonhigh.retail.gms.common.utils.BeanUtilsCommon;
import cn.wonhigh.retail.mdm.api.util.CacheContext;
import cn.wonhigh.retail.mdm.common.model.Brand;
import cn.wonhigh.retail.mdm.common.model.Bsgroups;
import cn.wonhigh.retail.mdm.common.model.Category;
import cn.wonhigh.retail.mdm.common.model.Cmcdist;
import cn.wonhigh.retail.mdm.common.model.Item;
import cn.wonhigh.retail.mdm.common.model.Mall;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-13 15:20:45
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
@RequestMapping("/bill_sales_outstanding_analysis")
public class BillSalesOutstandingAnalysisController extends BaseController<BillSalesOutstandingAnalysis> {
	@Resource
	private BillSalesOutstandingAnalysisManager salesOutstandingAnalysisManager;
	
	@Resource
	private FinancialAccountManager financialAccountManager;
	
	@Resource
	private ShopNameReplaceManager shopNameReplaceManager;
	
	@Resource
	private ShopManager shopManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("bill_sales_outstanding_analysis/", salesOutstandingAnalysisManager);
	}

	@RequestMapping(method = RequestMethod.GET ,value = "/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String senda = req.getParameter("senda");
		if(StringUtils.isNotBlank(senda)){
			mav.addObject("senda", senda);
		}
		mav.setViewName("report/sales_outstanding_analysis");
		return mav;
	}

	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> obj = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));

		Map<String, Object> params = builderParams(req, model);

//		// 设置多选的查询条件
//		setParams(params);
		List<BillSalesOutstandingAnalysis> list = new ArrayList<BillSalesOutstandingAnalysis>();

		BillSalesOutstandingAnalysis billSalesOutstandingAnalysis = null;
		List<BillSalesOutstandingAnalysis> footer = new ArrayList<BillSalesOutstandingAnalysis>();
		billSalesOutstandingAnalysis = this.salesOutstandingAnalysisManager.findSalesOutstandingAnalysisCount(params);
		if (null != billSalesOutstandingAnalysis) {
			SimplePage page = new SimplePage(pageNo, pageSize, billSalesOutstandingAnalysis.getTotal());
			list = this.salesOutstandingAnalysisManager.findSalesOutstandingAnalysis(page, sortColumn, sortOrder,
					params);
			for (BillSalesOutstandingAnalysis billSalesOutstandingAnalysis2 : list) {
				if("门店".equals(billSalesOutstandingAnalysis2.getBizType()) || "内购".equals(billSalesOutstandingAnalysis2.getBizType())) {
					processShopExtension(billSalesOutstandingAnalysis2);
				}
			}
			billSalesOutstandingAnalysis.setOrderNo("合计");
//			DecimalFormat format = new DecimalFormat("#.00");
//			String unitCost = format.format(billSalesOutstandingAnalysis.getUnitCost());
//			if(StringUtils.isNotBlank(unitCost)){
//				billSalesOutstandingAnalysis.setUnitCost(new BigDecimal(unitCost));
//			}
			footer.add(billSalesOutstandingAnalysis);
			obj.put("total", billSalesOutstandingAnalysis.getTotal());
		}
		
		obj.put("rows", list);
		obj.put("footer", footer);
		return obj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void processCategoryInfo(final CacheContext context, final Map vals) {
		Category category = context.getCategory(vals.get("categoryNo").toString());
		if( category != null){
			vals.put("subCategoruName", category.getName());		
			String[] ids = category.getNoPath().split(",");		
			if( ids.length <3)
				return;
			vals.put("categoryName", context.getCategoryName(ids[1]));
			vals.put("rootCategoryName", context.getCategoryName(ids[2]));
		}
		String organ = context.getOrganName(vals.get("organNo").toString());
		vals.put("organName1", organ);
		organ = context.getOrganName(vals.get("bizNo").toString());
		vals.put("organName2", organ);
		if(null != vals.get("mallNo")){
			// 商场
			Mall mall = context.getMall(vals.get("mallNo").toString());
			if(null != mall){
				vals.put("mallName", mall.getName());
				// 商圈
				Bsgroups bsgroup = context.get("Bsgroups",mall.getBsgroupsNo());
				vals.put("bsgroupsName", bsgroup.getName());
			}
		}
		if(null != vals.get("regionNo")){
			//片区
			String regionName = context.getRegionName(vals.get("regionNo").toString());
			vals.put("regionName", regionName);
		}
		if(null != vals.get("cmcdistNo")){
			Cmcdist cmcdist = context.get("Cmcdist",vals.get("cmcdistNo").toString());
			if(null != cmcdist){
				vals.put("cmcdistName", cmcdist.getName());
			}
		}
		if(null != vals.get("saleMode")){
			String saleMode = context.getLookupName(vals.get("saleMode").toString());
			vals.put("saleMode", saleMode);
		}
		if(null != vals.get("saleMode")){
			String retailType = context.getLookupName(vals.get("retailType").toString());
			vals.put("retailType", retailType);
		}
		if(null != vals.get("multi")){
			String multi = context.getLookupName(vals.get("multi").toString());
			vals.put("multi",multi);
		}
		if(null != vals.get("status")){
			String status = context.getLookupName(vals.get("status").toString());
			vals.put("status", status);
		}
		if(null != vals.get("shopLevel")){
			String shopLevel = context.getLookupName("shopLevel");
			vals.put("shopLevel", shopLevel);
		}
		
		////////////////////////// 最外层查询的基础信息 //////////////////////////////
		if (null == vals.get("brandNo"))
			return;
		if(null == context){
			System.out.print("context is null");
		}
		Brand brand = context.getBrand(vals.get("brandNo").toString());
		if (null == brand)
			return;

		String itemCode = vals.get("itemCode").toString();
		Item item = context.getItem(itemCode, brand.getBrandNo());
		if (item == null)
			return;
		String years = context.getLookupName(item.getYears());
		String sellSeason = context.getLookupName(item.getSellSeason());
		String gender = context.getLookupName(item.getGender());
		String orderFrom = context.getLookupName(item.getOrderfrom());
		String styleName = item.getStyleNo();//context.getLookupName(item.getStyleNo());
		String colorName = context.getColorName(item.getColorNo());

		vals.put("year", years);
		vals.put("sellSeason", sellSeason);
		vals.put("gender", gender);
		vals.put("orderfrom", orderFrom);
		vals.put("styleName", styleName);
		vals.put("colorName", colorName);
	}

	/**
	 * 根据店铺编号及品牌编号查询片区,管理城市,经营城市的缓存信息(明细导出调用)
	 * @param vals
	 */
	private void processShopExtension(Map<String, String> vals) {
//		CacheUntils cacheUntils = CacheUntils.current();
		
		String organName = "";
		String bizType = vals.get("bizType").toString();
		if(null != vals.get("brandNo") && null != vals.get("shopNo")){
			String brandNo = vals.get("brandNo").toString();
			String shopNo = vals.get("shopNo").toString();
//			ShopExtensionDto shopExtension = cacheUntils.getShopExtRegion(shopNo, brandNo);
//			if(null != shopExtension){
//				vals.put("regionName", shopExtension.getAttributeDetailName());
//			}
//			
//			ShopExtensionDto organ = cacheUntils.getShopExtOrgan(shopNo, brandNo);
//			if(null != organ){
//				String[] organs = organ.getAttributeDetailName().split(",");
//				vals.put("organName1", organs[0]);
//				vals.put("organName2", organs[1]);
//			}
			if(StringUtils.isNotBlank(shopNo) && StringUtils.isNotBlank(brandNo)){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("shopNo", shopNo);
				params.put("brandNo", brandNo);
				params.put("attributeType", "business_city_no");
				List<ShopExtensionDto> organList = shopManager.findShopExtentionByCondition(params);
				
				if(!CollectionUtils.isEmpty(organList)){
					String[] organs = organList.get(0).getAttributeDetailName().split(",");
					organName = organs[0];
					vals.put("organName1", organs[0]);
					vals.put("organName2", organs[1]);
				}
				params.put("attributeType", "part_area");
				List<ShopExtensionDto> regionList = shopManager.findShopExtentionByCondition(params);
				if(!CollectionUtils.isEmpty(regionList)){
					vals.put("regionName",regionList.get(0).getAttributeDetailName());
				}
				
				if(null != vals.get("brandUnitNo")){
					String brandUnitNo = vals.get("brandUnitNo").toString();
					try {
						//门店、内购--添加品牌部编号至店铺名称后
						if("门店".equals(bizType) || "内购".equals(bizType)) {
							if(StringUtils.isNotBlank(shopNo) && StringUtils.isNotBlank(brandUnitNo)){
								ShopNameReplace shopNameReplace = shopNameReplaceManager.selectReplaceName(shopNo, brandUnitNo);
								if(shopNameReplace !=null) {
									vals.put("shortName", shopNameReplace.getReplaceName());
									vals.put("shopNoReplace", shopNameReplace.getReplaceNo());
								}
							}
						}
						
					} catch (ManagerException e) {
						
					}
				}
			}
		}
		//销售回款汇总西北导出模板
		if(null != vals.get("brandUnitNo")){
			String brandUnitNo = vals.get("brandUnitNo").toString();
			if("内购".equals(bizType)) {
				vals.put("xb_shortName",organName+ "批发" + brandUnitNo + "员购店");
			} else if("批发".equals(bizType)) {
				vals.put("xb_shortName",organName + "批发" + brandUnitNo + "店");
			}else {
				vals.put("xb_shortName",vals.get("shortName").toString());
			}
		}
		
	}
	
	/**
	 *  根据店铺编号及品牌编号查询片区,管理城市,经营城市的缓存信息(查询调用)
	 * @param obj
	 * @throws ManagerException 
	 */
	private void processShopExtension(BillSalesOutstandingAnalysis obj) throws ManagerException {
//		CacheUntils cacheUntils = CacheUntils.current();
		
		if(null != obj.getBrandNo() && null != obj.getShopNo()){
//			ShopExtensionDto shopExtension = cacheUntils.getShopExtRegion(obj.getShopNo(), obj.getBrandNo());
//			if(null != shopExtension){
//				obj.setRegionName(shopExtension.getAttributeDetailName());
//			}
//			//part_area
//			ShopExtensionDto organ = cacheUntils.getShopExtOrgan(obj.getShopNo(), obj.getBrandNo());
//			if(null != organ){
//				String[] organs = organ.getAttributeDetailName().split(",");
//				obj.setOrganName1(organs[0]);
//				obj.setOrganName2(organs[1]);
//			}
			if(StringUtils.isNotBlank(obj.getShopNo()) && StringUtils.isNotBlank(obj.getBrandNo())){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("shopNo", obj.getShopNo());
				params.put("brandNo", obj.getBrandNo());
				params.put("attributeType", "business_city_no");
				List<ShopExtensionDto> organList = shopManager.findShopExtentionByCondition(params);
				if(!CollectionUtils.isEmpty(organList)){
					String[] organs = organList.get(0).getAttributeDetailName().split(",");
					obj.setOrganName1(organs[0]);
					obj.setOrganName2(organs[1]);
				}
	
				params.put("attributeType", "part_area");
				List<ShopExtensionDto> regionList = shopManager.findShopExtentionByCondition(params);
				if(!CollectionUtils.isEmpty(regionList)){
					obj.setRegionName(regionList.get(0).getAttributeDetailName());
				}
			}
			//门店、内购--添加品牌部编号至店铺名称后
			if("门店".equals(obj.getBizType()) || "内购".equals(obj.getBizType())) {
				if(StringUtils.isNotBlank(obj.getShopNo()) && StringUtils.isNotBlank(obj.getBrandUnitNo())){
					ShopNameReplace shopNameReplace = shopNameReplaceManager.selectReplaceName(obj.getShopNo(), obj.getBrandUnitNo());
					if(shopNameReplace !=null) {
						obj.setShortName(shopNameReplace.getReplaceName());
						obj.setShopNoReplace(shopNameReplace.getReplaceNo());
					}
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export")
	public void saleExport(BillSalesOutstandingAnalysis modelType, HttpServletRequest request, Model model,
			HttpServletResponse response) throws ManagerException {
	 
		Map<String, Object> params = builderParams(request, model);
//		// 设置多选的查询条件
//		setParams(params);
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String fileName = (String) params.get("fileName");
//		String exportType = (String) params.get("exportType");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		if (!StringUtils.isNotEmpty(exportColumns))
			return;
		ObjectMapper mapper = new ObjectMapper();

		try (final CacheContext context = new CacheContext()) {
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
				if (null == hid || !hid.equals(true)) {
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
				@Override
				public Boolean apply(Object input) {
					//long takes = System.currentTimeMillis();
					Map vals = (Map) input;
					queryResultMap(vals); 
					
					exportExcel.write(vals);
					
					//System.out.println("redis " + ( System.currentTimeMillis() - takes ) + " ms.");
					return true;
				}
			};

			SimplePage page = new SimplePage();

			//			BillSalesOutstandingAnalysis billSalesOutstandingAnalysis = null;
			//			if( "1".equals(exportType))
			//				billSalesOutstandingAnalysis = this.salesOutstandingAnalysisManager
			//					.findSalesOutstandingAnalysisCount(params);
			//			else
			//				billSalesOutstandingAnalysis = this.salesOutstandingAnalysisManager
			//					.findBillSaleCollectCount(params);
			//			
			//			int total = billSalesOutstandingAnalysis.getTotal();

			page.setPageSize(Integer.MAX_VALUE);

			selectManagerMenthod(page, params, handler);

			exportExcel.flush(response);
			exportExcel.close();
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void queryResultMap(Map vals) {
		vals.put("organName1", vals.get("organName"));
		vals.put("organName2", vals.get("bizName"));
//		CacheContext context = CacheContext.current();
//		processCategoryInfo(context, vals);
		processShopExtension(vals);
		vals.put("rateTypeStr", vals.get("discountTypeName"));
		vals.put("discPrice", vals.get("discAmount"));
		vals.put("cost", vals.get("headquarterCost"));
		vals.put("activityTypeStr", vals.get("activityTypeName"));

		vals.put("proStartDate", vals.get("proStartTime"));
		vals.put("proEndDate", vals.get("proEndTime"));
		vals.put("launchTypeStr", vals.get("launchTypeName"));
		vals.put("subCategoruName", vals.get("subCategoryName"));
		vals.put("year", vals.get("YEAR"));
		//String shop = context.getShopName(vals.get("shopNo").toString());
		//vals.put("shortName", shop);
	}

	@Override
	protected void selectManagerMenthod(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException {
		String exportType = (String) params.get("exportType");
		int type = "1".equals(exportType) ? 1 : 0;
		this.salesOutstandingAnalysisManager.findSalesOutstandingAnalysis(type, page, params, handler);

	}

	@Override
	 public Map<String, Object> builderParams(HttpServletRequest req, Model model){
		Map<String, Object> map = super.builderParams(req, model);
		setParams(map);
		return map;
	}
	/**
	 * 导出
	 * @param modelType 实体对象
	 * @param req HttpServletRequest
	 * @param model Model
	 * @param response HttpServletResponse
	 * @throws ManagerException 异常
	 */
	@RequestMapping(value = "/do_sale_export")
	public void doSaleExport(BillSalesOutstandingAnalysis modelType, HttpServletRequest request, Model model,
			HttpServletResponse response) throws ManagerException {
		Map<String, Object> params = builderParams(request, model);
//		// 设置多选的查询条件
//		setParams(params);
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String fileName = (String) params.get("fileName");
//		String exportType = (String) params.get("exportType");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(exportColumns)) {
			ExportComplexVo exportVo;
			List<Map> columnsList;
			try {
				columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
				});
				// 对手动隐藏的列进行处理，把手动隐藏的列过滤掉，不导出来
				List<Map> tempColumnsList = new ArrayList<Map>();
				for (Map map : columnsList) {
					Object hid = map.get("hidden");
					if (null == hid || !hid.equals(true)) {
						tempColumnsList.add(map);
					}
				}

				tempColumnsList = this.sortExportColumns(tempColumnsList);
				exportVo = new ExportComplexVo();
				exportVo.setColumnsMapList(tempColumnsList);
				if (StringUtils.isNotEmpty(firstHeaderColumns)) {
					List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
					});
					exportVo.setHeaderColumnsMapList(headerColumnsList);
				}
			} catch (Exception e) {
				throw new ManagerException(e);
			}
			//字段名列表

			exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
			Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
			exportVo.setRowAccessWindowSize(rowAccessWindowSize);
			try (final HSSFExportComplex exportExcel = new HSSFExportComplex(exportVo)) {
				List<BillSalesOutstandingAnalysis> list = null;
				BillSalesOutstandingAnalysis billSalesOutstandingAnalysis = null;

//				//设置分组汇总的分组条件
//				setCollectGroupByParam(params);
				billSalesOutstandingAnalysis = this.salesOutstandingAnalysisManager.findBillSaleCollectCount(params);
				if (null != billSalesOutstandingAnalysis && billSalesOutstandingAnalysis.getTotal() > 0) {
					int total = billSalesOutstandingAnalysis.getTotal();
					int pageNo = total % 5000 == 0 ? total / 5000 : total / 5000 + 1;
					if (pageNo > 1) {
						for (int i = 1; i <= pageNo; i++) {
							SimplePage page = new SimplePage(i, 5000, total);
							list = this.salesOutstandingAnalysisManager.findBillSaleCollectList(page, null, null,
									params);
							for (BillSalesOutstandingAnalysis billSalesOutstandingAnalysis2 : list) {
								if("门店".equals(billSalesOutstandingAnalysis2.getBizType()) || "内购".equals(billSalesOutstandingAnalysis2.getBizType())) {
									processShopExtension(billSalesOutstandingAnalysis2);
								}
								if("内购".equals(billSalesOutstandingAnalysis2.getBizType())) {
									billSalesOutstandingAnalysis2.setXb_shortName(billSalesOutstandingAnalysis2.getOrganName1() + "批发" + billSalesOutstandingAnalysis2.getBrandUnitNo() + "员购店");
								} else if("批发".equals(billSalesOutstandingAnalysis2.getBizType())) {
									billSalesOutstandingAnalysis2.setXb_shortName(billSalesOutstandingAnalysis2.getOrganName1() + "批发" + billSalesOutstandingAnalysis2.getBrandUnitNo() + "店");
								}else {
									billSalesOutstandingAnalysis2.setXb_shortName(billSalesOutstandingAnalysis2.getShortName());
								}
							}
							
							exportExcel.write(constructExportList(list, columnsList));
						}
					} else {
						list = this.salesOutstandingAnalysisManager.findBillSaleCollectList(null, null, null, params);
						for (BillSalesOutstandingAnalysis billSalesOutstandingAnalysis2 : list) {
							if("门店".equals(billSalesOutstandingAnalysis2.getBizType()) || "内购".equals(billSalesOutstandingAnalysis2.getBizType())) {
								processShopExtension(billSalesOutstandingAnalysis2);
							}
							if("内购".equals(billSalesOutstandingAnalysis2.getBizType())) {
								billSalesOutstandingAnalysis2.setXb_shortName(billSalesOutstandingAnalysis2.getOrganName1() + "批发" + billSalesOutstandingAnalysis2.getBrandUnitNo() + "员购店");
							} else if("批发".equals(billSalesOutstandingAnalysis2.getBizType())) {
								billSalesOutstandingAnalysis2.setXb_shortName(billSalesOutstandingAnalysis2.getOrganName1() + "批发" + billSalesOutstandingAnalysis2.getBrandUnitNo() + "店");
							} else {
								billSalesOutstandingAnalysis2.setXb_shortName(billSalesOutstandingAnalysis2.getShortName());
							}
						}
						exportExcel.write(constructExportList(list, columnsList));
					}
					exportExcel.flush(response);
					//exportExcel.close();
				}
			} catch (Exception e) {
				throw new ManagerException(e);
			}
		}
	}

	/**************************************** 以下为汇总处理 ******************************************/
	
	@RequestMapping(method = RequestMethod.GET ,value = "/collect_list")
	public ModelAndView toCollectList(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String senda = req.getParameter("sendas");
		if(StringUtils.isNotBlank(senda)){
			mav.addObject("sendas", senda);
		}
		mav.setViewName("report/sales_outstanding_collect");
		return mav;
	}

	@RequestMapping(value = "/findCollectList")
	@ResponseBody
	public Map<String, Object> findCollectList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> obj = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));

		Map<String, Object> params = builderParams(req, model);
//		// 设置多选的查询条件
//		setParams(params);
//		//设置分组汇总的分组条件
//		setCollectGroupByParam(params);
		BillSalesOutstandingAnalysis billSalesOutstanding = null;
		List<BillSalesOutstandingAnalysis> list = new ArrayList<BillSalesOutstandingAnalysis>();
		billSalesOutstanding = this.salesOutstandingAnalysisManager.findBillSaleCollectCount(params);
		if (null != billSalesOutstanding) {
			SimplePage page = new SimplePage(pageNo, pageSize, billSalesOutstanding.getTotal());
			list = this.salesOutstandingAnalysisManager.findBillSaleCollectList(page, sortColumn, sortOrder, params);
			for (BillSalesOutstandingAnalysis billSalesOutstandingAnalysis : list) {
				if("门店".equals(billSalesOutstandingAnalysis.getBizType()) || "内购".equals(billSalesOutstandingAnalysis.getBizType())) {
					processShopExtension(billSalesOutstandingAnalysis);
				}
			}
		}

		List<BillSalesOutstandingAnalysis> footer = new ArrayList<BillSalesOutstandingAnalysis>();
		if (null != billSalesOutstanding) {
			billSalesOutstanding.setGender("合计");
//			DecimalFormat format = new DecimalFormat("#.00");
//			String unitCost = format.format(billSalesOutstanding.getUnitCost());
//			if(StringUtils.isNotBlank(unitCost)){
//				billSalesOutstanding.setUnitCost(new BigDecimal(unitCost));
//			}
			footer.add(billSalesOutstanding);
		}
		obj.put("total", billSalesOutstanding.getTotal());
		obj.put("rows", list);
		obj.put("footer", footer);

		return obj;
	}

	/**
	 * 设置多选择的查询条件
	 * @param params
	 * @return
	 */
	private void setParams(Map<String, Object> params) {
		
		String hqCompanyNo = financialAccountManager.findLeadRoleCompanyNos();
		params.put("hqCompanyData",hqCompanyNo);
		
		String shardingFlag = ShardingUtil.getShardingFlag();
		params.put("shardingFlag", shardingFlag);
		
		// 地区
		String zoneNo = params.get("zoneNo") == null ? "" : params.get("zoneNo").toString();
		List<String> zoneNoList = new ArrayList<String>();
		if (StringUtils.isNotBlank(zoneNo)) {
			if (zoneNo.contains(",")) {
				String[] zoneNos = zoneNo.split(",");
				for (String zoneNoTemp : zoneNos) {
					zoneNoList.add(zoneNoTemp);
				}
			} else {
				zoneNoList.add(zoneNo);
			}

			params.put("zoneNos", zoneNoList);
			params.put("zoneNo", null);
		}
		//管理城市多选
		if(params.get("organNo") != null && !"".equals(params.get("organNo").toString())) {
			String[] temps = params.get("organNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("organNos", tempList);
			params.put("organNo", null);
		}
		//大类多选
		if(params.get("categoryNo") != null && !"".equals(params.get("categoryNo").toString())) {
			String[] temps = params.get("categoryNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("categoryNos", tempList);
		}

		if(params.get("brandNo") != null && !"".equals(params.get("brandNo").toString())) {
			String[] temps = params.get("brandNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("brandNos", tempList);
		}

		// 公司
		if(params.get("companyNo") != null && !"".equals(params.get("companyNo").toString())) {
			String[] temps = params.get("companyNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("companyNos", tempList);
			params.put("companyNo", null);
		}
		// 客户
		if(params.get("customerNo") != null && !"".equals(params.get("customerNo").toString())) {
			String[] temps = params.get("customerNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("customerNos", tempList);
		}
		
		boolean isPe = ShardingUtil.isPE();
		params.put("isPe", isPe);
		//批发
		String wholesaleSql = "";
		//其他
		String otherSql = "( bsb.bill_type = 1335 AND bsb.biz_type = 3) "
				+ "OR (bsb.bill_type = 1361 AND bsb.biz_type in (7,25)) OR (bsb.bill_type = 2005 AND bsb.biz_type = 35)" +
				"OR (bsb.bill_type = 1371 AND bsb.SALER_NO NOT IN ("+hqCompanyNo+") AND bsb.BUYER_NO IN ("+hqCompanyNo+"))" ;
		if(isPe){//体育
			wholesaleSql = "(bsb.bill_type = 1335 AND bsb.biz_type IN (21,22,30,29,43))";
			otherSql = otherSql.concat(" OR (bsb.bill_type = 1333)"); // 采购退货单
		}else{//鞋，新业务
			wholesaleSql = "(bsb.bill_type = 1335 AND bsb.biz_type IN (21,22,30,29))";
		}
		//内购各种单据类型Sql
		String innerBuySql = "(bsb.bill_type = 1335 AND bsb.biz_type in (2,23,24,13)) OR bsb.bill_type = 1342 "
				+ "OR (bsb.bill_type = 1355 AND bsb.biz_type in (8,10,26)) ";
		//调货各种单据类型Sql
		String  transferSql = "(bsb.bill_type = 1371 AND bsb.SALER_NO NOT IN ("+hqCompanyNo+") " +
				"AND bsb.BUYER_NO NOT IN ("+hqCompanyNo+"))";
		
		params.put("wholesaleSql", wholesaleSql);
		params.put("otherSql", otherSql);
		params.put("innerBuySql", innerBuySql);
		params.put("transferSql", transferSql);
		String businessType = params.get("businessType") == null ? "" : params.get("businessType").toString();
		if (StringUtils.isNotBlank(businessType)) {
			if (businessType.contains(",")) {
				String[] businessTypes = businessType.split(",");
				String posSql = "";//pos 的业务类型条件
				String gmsSql = "";//gms 的单据类型条件
				for (String typeStr : businessTypes) {
					if ("1".equals(typeStr)) {//按店铺
						if (StringUtils.isNotBlank(posSql)) {
							posSql = posSql.concat(",");
						}
						posSql = posSql.concat("0,1,2,6");
					} else if ("2".equals(typeStr)) { //按批发
						if (StringUtils.isNotBlank(gmsSql)) {
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat(wholesaleSql);
					} else if ("3".equals(typeStr)) { //按调货
						if (StringUtils.isNotBlank(gmsSql)) {
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat(transferSql);
					} else if ("4".equals(typeStr)) { //内购
						if (StringUtils.isNotBlank(gmsSql)) {
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat(innerBuySql);
						if (StringUtils.isNotBlank(posSql)) {
							posSql = posSql.concat(",");
						}
						posSql = posSql.concat("3");
					} else {//其他
						if (StringUtils.isNotBlank(gmsSql)) {
							gmsSql = gmsSql.concat(" OR ");
						}
						gmsSql = gmsSql.concat(otherSql);
					}
				}

				if (StringUtils.isBlank(posSql) && StringUtils.isNotBlank(gmsSql)) { //如果pos 的查询条件为空，则表示没有选择门店及内购类型，那只需要查询gms单据即可
					params.put("queryCondition2", gmsSql);
					params.put("queryType", 1);//查询类型区分：1＝查询GMS单据，2＝只查询POS单据，3＝GMS、POS 单据连表查询
				} else { //否则则调用连表（pos 及 gms）的查询
					params.put("queryCondition2", gmsSql);
					params.put("queryCondition1", posSql);
					params.put("queryType", 3);//3＝GMS、POS 单据连表查询
				}

			} else {
				if ("1".equals(businessType)) {//按店铺
					String queryCondition1 = "0,1,2,6";
					params.put("queryCondition1", queryCondition1);
					params.put("queryType", 2);//查询类型区分：1＝查询GMS单据，2＝只查询POS单据，3＝GMS、POS 单据连表查询
				} else if ("2".equals(businessType)) { //按批发
					params.put("queryCondition2", wholesaleSql);
					params.put("queryType", 1);
				} else if ("3".equals(businessType)) { //按调货
					params.put("queryCondition2", transferSql);
					params.put("queryType", 1);

				} else if ("4".equals(businessType)) { //内购
					params.put("queryCondition2", innerBuySql);

					String queryCondition1 = " 3";
					params.put("queryCondition1", queryCondition1);
					params.put("queryType", 3);
				} else {//其他
					params.put("queryCondition2", otherSql);
					params.put("queryType", 1);
				}
			}
		} else {
			String posSql = "0,1,2,3,6";
			params.put("queryCondition1", posSql);
			String gmsSql = wholesaleSql//批发
			+" OR "+ transferSql//调货
			+" OR "+ innerBuySql//内购
			+" OR " +otherSql;//其他
			params.put("queryCondition2", gmsSql);
			params.put("queryType", 3);
		}
		
		String shopNo = params.get("shopNo") == null ? "" : params.get("shopNo").toString();
		List<String> shopNoList = new ArrayList<String>();
		if (StringUtils.isNotBlank(shopNo)) {
			if (shopNo.contains(",")) {
				String[] shopNos = shopNo.split(",");
				for (String shopNoTemp : shopNos) {
					shopNoList.add(shopNoTemp);
				}
			} else {
				shopNoList.add(shopNo);
			}
			params.put("queryType", 2);//如果选择了店铺条件，则只查询POS 单据连表查询
			params.put("shopNos", shopNoList);
			params.put("shopNo", null);
		}
		String exportType = (String) params.get("exportType");
		int type = "1".equals(exportType) ? 1 : 0;
		if(type == 0){
			//设置分组汇总的分组条件
			setCollectGroupByParam(params);
		}
	}

	///设置分组汇总的分组条件
	private void setCollectGroupByParam(Map<String, Object> params) {
		String groupBySql = "";
		String countGroupBySql = "";
		if (params.get("isCheckShopNo").equals("true")) {
			groupBySql += "tb1.shop_no,";
			countGroupBySql += "tb.shop_no,";
		}
		if (params.get("isCheckGender").equals("true")) {
			groupBySql += "tb1.gender,";
			countGroupBySql += "le2. NAME,";
		}
		if (params.get("isCheckBrandUnit").equals("true")) {
			groupBySql += "tb1.brand_unit_no,";
			countGroupBySql += "tb.brand_unit_no,";
		}
		if (params.get("isCheckCategory").equals("true")) {
			groupBySql += "tb1.category_no1,";
			countGroupBySql += "cty.category_no,";
		}
		if (params.get("isCheckRootCategory").equals("true")) {
			groupBySql += "tb1.category_no2,";
			countGroupBySql += "cty2.category_no,";
		}
		if (params.get("isCheckYear").equals("true")) {
			groupBySql += "tb1.`YEAR`,";
			countGroupBySql += "le.`name`,";
		}
		if (params.get("isCheckSellSeason").equals("true")) {
			groupBySql += "tb1.sell_season,";
			countGroupBySql += "le1.`name`,";
		}
		if (params.get("isCheckRate").equals("true")) {
			groupBySql += "tb1.rate,";
			countGroupBySql += "tb.rate,";
		}
		if (params.get("isCheckRateCode").equals("true")) {
			groupBySql += "tb1.rate_code,";
			countGroupBySql += "tb.rate_code,";
		}
		if(ShardingUtil.isBLK()){
			if (params.get("isStyleNo").equals("true")) {
				groupBySql += "tb1.style_no,";
				countGroupBySql += "it.style_no,";
			}
		}
		params.put("groupBySql", groupBySql);
		params.put("countGroupBySql", countGroupBySql);
	}

	private List<Map> constructExportList(List<BillSalesOutstandingAnalysis> list, List<Map> columnsList)
			throws Exception {
		List<Map> listArrayList = new ArrayList<Map>();
		if (list != null && list.size() > 0) {
			List<String> fields = new ArrayList<String>();
			for (Map map : columnsList) {
				fields.add(map.get("field").toString());
			}
			boolean flag = true;
			ExportFormat formatAnnotation = null;
			AbstractExportFormat<BillSalesOutstandingAnalysis> format = null;
			for (BillSalesOutstandingAnalysis vo : list) {
				Map map = null;
				if (flag) {
					formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
					flag = false;
				}
				if (formatAnnotation != null) {
					format = (AbstractExportFormat<BillSalesOutstandingAnalysis>) formatAnnotation.className()
							.newInstance();
					map = format.format(fields, vo);
				} else {
					map = new HashMap();
					BeanUtilsCommon.object2MapWithoutNull(vo, map);
				}
				listArrayList.add(map);
			}
		}
		return listArrayList;
	}
}
