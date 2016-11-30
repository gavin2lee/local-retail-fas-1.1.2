package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.model.SalesSummary;
import cn.wonhigh.retail.fas.common.model.ShopNameReplace;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.manager.SalesSummaryManager;
import cn.wonhigh.retail.fas.manager.ShopNameReplaceManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 销售分类汇总
 * 
 * @author zhu.yl
 * @date 2014-08-25 15:58:32
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/sales_summary")
@ModuleVerify("30510410")
public class SalesSummaryController extends BaseController<SalesSummary> {
	@Resource
	private SalesSummaryManager salesSummaryManager;
	@Resource
	private FinancialAccountManager financialAccountManager;
	@Resource
	private ShopNameReplaceManager shopNameReplaceManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("sales_summary/", salesSummaryManager);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("fliterCompany", "groupLeadRole");
		mav.setViewName("sales_summary/list");
		return mav;
	}

	// 记录查询明细数，汇总数
	int iTotalCount = 0;

	// 查询方法
	@RequestMapping("/getSalesSummary")
	@ResponseBody
	public Map<String, Object> getSalesSummary(HttpServletRequest req,
			Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1
				: Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10
				: Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		params = setParams(params);
		List<SalesSummary> list = null;
		List<SalesSummary> listSum = new ArrayList<SalesSummary>();
		SalesSummary summary = null;
		// 总计
		summary = this.salesSummaryManager.selectSalesSummaryCount(params, null);
		Map<String, Object> obj = new HashMap<String, Object>();
		if(null != summary){
			summary.setCompanyName("总计：");
			listSum.add(summary);
	
	//		total = this.salesSummaryManager.selectCount(params);
			SimplePage page = new SimplePage(pageNo, pageSize, summary.getTotal());
			list = this.salesSummaryManager.selectSalesSummary(page, params);
			// 字段处理
			list = this.areaTailor(list);
			obj.put("total", summary.getTotal());
		}
		obj.put("rows", list);
		obj.put("footer", listSum);
		return obj;
	}

	private List<SalesSummary> areaTailor(List<SalesSummary> list)
			throws ManagerException {

		for (SalesSummary salesSummary : list) {

			// 门店、内购--添加品牌部编号至店铺名称后
			if ("门店".equals(salesSummary.getBizType())
					|| "内购".equals(salesSummary.getBizType())) {
				ShopNameReplace shopNameReplace = shopNameReplaceManager
						.selectReplaceName(salesSummary.getShopNo1(),
								salesSummary.getBrandUnitNo());
				if (shopNameReplace != null) {
					salesSummary.setShopName(shopNameReplace.getReplaceName());
					salesSummary.setShopNoReplace(shopNameReplace
							.getReplaceNo());
				}
			}
		}
		return list;
	}

	@RequestMapping(value = "/column_list.json")
	@ResponseBody
	public  Map<String, Object> queryColumnList(HttpServletRequest req, Model model) throws Exception {
        Map<String,Object> params = builderParams(req, model);
        Map<String, Object> obj = new HashMap<String, Object>();
        setCategoryParams(params);
        obj.put("CateGoryColumn", params.get("categoryNames"));
		return obj;
	}
	
	
	private void setCategoryParams(Map<String, Object> params) {
		List<String> categories = new ArrayList<>();
		List<String> categoryName = new ArrayList<String>();
		String organTypeNo = Authorization.getUser().getOrganTypeNo();
//		categories.add("00");
		if(null != params.get("categoryNo")){
			if(!params.get("categoryNo").toString().isEmpty()){
				String [] catgorys = params.get("categoryNo").toString().split(",");
				for (String catgoryNo : catgorys) {
					if ("U010101".equalsIgnoreCase(organTypeNo)) {
						if("01".equals(catgoryNo)){
							categories.add("'01'");
							categoryName.add("鞋");
						}else if("02".equals(catgoryNo)){
							categories.add("'02'");
							categoryName.add("童鞋");
						}else if("03".equals(catgoryNo)){
							categories.add("'03'");
							categoryName.add("服");
						}else if("04".equals(catgoryNo)){
							categories.add("'04'");
							categoryName.add("包饰");
						}else if("05".equals(catgoryNo)){
							categories.add("'05'");
							categoryName.add("护鞋用品");
						}else if("06".equals(catgoryNo)){
							categories.add("'06'");
							categoryName.add("物料");
						}else if("07".equals(catgoryNo)){
//							categories.add("07");
							categoryName.add("其他");
							params.put("notInNo", "'01','02','03','04','05','06'");
							params.put("otherCategoryNo", "07");
						}
					} else if ("U010102".equalsIgnoreCase(organTypeNo)) {
						if("41".equals(catgoryNo)){
							categories.add("'41'");
							categoryName.add("鞋");
						}else if("46".equals(catgoryNo)){
							categories.add("'46'");
							categoryName.add("服");
						}else if("47".equals(catgoryNo)){
							categories.add("'47'");
							categoryName.add("配");
						}else if("7".equals(catgoryNo)){
//							categories.add("07");
							categoryName.add("其他");
							params.put("notInNo", "'41','46','47'");
							params.put("otherCategoryNo", "07");
						}
					} else if ("U010105".equalsIgnoreCase(organTypeNo)) {
						if("1".equals(catgoryNo)){
							categories.add("'5B'");//鞋
							categoryName.add("鞋");
						}else if("2".equals(catgoryNo)){
							categories.add("'51','56','57','5D','52','53'");// 服
							categoryName.add("服");
						}else if("3".equals(catgoryNo)){
							categories.add("'5E'");//包
							categoryName.add("包");
						}else if("4".equals(catgoryNo)){
							categories.add("'58','59','5A','5F','5H','54','5k','5L'");// 配
							categoryName.add("配");
						}else if("5".equals(catgoryNo)){
							categories.add("'5G'");//物料
							categoryName.add("物料");
						}else if("6".equals(catgoryNo)){
//							categories.add("07");
							categoryName.add("其他");
							params.put("notInNo", "'51','56','57','58','59','5A','5B','5D','5E'," +
									"'5F','5H','5G','52','53','54','5k','5L'");
							params.put("otherCategoryNo", "07");
						}
					}
				}
				if(CollectionUtils.isNotEmpty(categories)){
					//查询条件
					params.put("categoryNos", categories);
				}
			}else{
				
				if ("U010101".equalsIgnoreCase(organTypeNo)) {
					categories.add("'01'");
					categoryName.add("鞋");
					categories.add("'02'");
					categoryName.add("童鞋");
					categories.add("'03'");
					categoryName.add("服");
					categories.add("'04'");
					categoryName.add("包饰");
					categories.add("'05'");
					categoryName.add("护鞋用品");
					categories.add("'06'");
					categoryName.add("物料");
//					categories.add("07");
					categoryName.add("其他");
					params.put("notInNo", "'01','02','03','04','05','06'");
					params.put("otherCategoryNo", "all");
				} else if ("U010102".equalsIgnoreCase(organTypeNo)) {
					categories.add("'41'");
					categoryName.add("鞋");
					categories.add("'46'");
					categoryName.add("服");
					categories.add("'47'");
					categoryName.add("配");
//					categories.add("''");
//					categories.add("''");
//					categories.add("''");
					categories.add("07");
					categoryName.add("其他");
					params.put("notInNo", "'41','46','47'");
					params.put("otherCategoryNo", "all");
				} else if ("U010105".equalsIgnoreCase(organTypeNo)) {
					categories.add("'5B'");//鞋
					categoryName.add("鞋");
					categories.add("'51','56','57','5D','52','53'");// 服
					categoryName.add("服");
					categories.add("'5E'");//包
					categoryName.add("包");
					categories.add("'58','59','5A','5F','5H','54','5k','5L'");// 配
					categoryName.add("配");
					categories.add("'5G'");//物料
					categoryName.add("物料");
//					categories.add("''");
					categories.add("07");
					categoryName.add("其他");
					params.put("notInNo", "'51','56','57','58','59','5A','5B','5D','5E'," +
							"'5F','5H','5G','52','53','54','5k','5L'");
					params.put("otherCategoryNo", "all");
				}
				//查询条件
				params.put("categoryNos", null);
			}
		}
		//页面显示的名称
		params.put("categoryNames", categoryName);
		//列查询的条件
		params.put("categories", categories);
	}

	/**
	 * 参数设置
	 * 
	 * @param req
	 * @param map
	 * @return
	 */
	public Map<String, Object> setParams(Map<String, Object> params) {
		boolean isPe = ShardingUtil.isPE();
		params.put("isPe", isPe);
		
		if(params.get("companyNo") != null && !"".equals(params.get("companyNo").toString())) {
			String[] temps = params.get("companyNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("companyNos", tempList);
			params.put("companyNo", null);
		}

		String hqCompanyNo = financialAccountManager.findLeadRoleCompanyNos();
		params.put("hqCompanyData", hqCompanyNo);

		String organNo = "";
		if (params.get("organNo") != null) {
			organNo = FasUtil.formatInQueryCondition(params.get("organNo")
					.toString());
		}
		String shopNos = "";
		if (params.get("shopNo") != null) {
			shopNos = FasUtil.formatInQueryCondition(params.get("shopNo")
					.toString());
		}
		String brandNos = "";
		if (params.get("brandNo") != null) {
			brandNos = FasUtil.formatInQueryCondition(params.get("brandNo")
					.toString());
		}
		// String itemNos = "";
		// if (map.get("itemNo") != null) {
		// itemNos =
		// FasUtil.formatInQueryCondition(map.get("itemNo").toString());
		// }
		params.put("organNo", organNo);
		params.put("brandNo", brandNos);
		params.put("shopNo", shopNos);
		// 客户
		if(params.get("customerNo") != null && !"".equals(params.get("customerNo").toString())) {
			String[] temps = params.get("customerNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("customerNos", tempList);
		}
		setCategoryParams(params);

		//批发
		String wholesaleSql = "";
		//其他
		String otherSql = "( bsb.bill_type = 1335 AND bsb.biz_type = 3) "
				+ "OR (bsb.bill_type = 1361 AND bsb.biz_type in (7,25)) OR (bsb.bill_type = 2005 AND bsb.biz_type = 35)" +
				"OR (bsb.bill_type = 1371 AND bsb.SALER_NO NOT IN ("+hqCompanyNo+") AND bsb.BUYER_NO IN ("+hqCompanyNo+"))" ;
		if(ShardingUtil.isPE()){//体育
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
		return params;
	}

	/**
	 * 导出汇总与明细数据源
	 */
	protected List<SalesSummary> queryExportData(Map<String, Object> params)
			throws ManagerException {
		List<SalesSummary> dataList = null;
		setParams(params);
		if (iTotalCount >= 0) {
			iTotalCount = salesSummaryManager.selectCount(params);
		}
		if (iTotalCount >= 0) {
			SimplePage page = new SimplePage(1, iTotalCount, (int) iTotalCount);
			dataList = salesSummaryManager.selectSalesSummary(page, params);
			dataList = this.areaTailor(dataList);
		}
		SalesSummary summary = null;
		// 总计
		summary = this.salesSummaryManager
				.selectSalesSummaryCount(params, null);
		summary.setCompanyName("总计：");
		// 添加合计至底部
		if (summary != null) {
			dataList.add(summary);
		}
		return dataList;
	}
}