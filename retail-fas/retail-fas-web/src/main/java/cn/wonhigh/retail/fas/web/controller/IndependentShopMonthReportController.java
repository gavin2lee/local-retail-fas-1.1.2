package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.IndependentShopMonthReport;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.IndependentShopMonthReportManager;
import cn.wonhigh.retail.fas.manager.OrderMainManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/self_shop_month_report")
@ModuleVerify("30170020")
public class IndependentShopMonthReportController extends ExcelImportController<SaleOrderPayway> {

	@Resource
	private IndependentShopMonthReportManager independentShopMonthReportManager;

	@Resource
	private ShopManager shopManager;

	@Resource
	private OrderMainManager orderMainManager;

	@Override
	protected CrudInfo init() {
		// TODO Auto-generated method stub
		return new CrudInfo("IndepShop_management/self_shop_month_report/", independentShopMonthReportManager);
	}

	@Override
	public String list() {
		return "mallshop_balance/mall_saleMonthReport";
	}

	@Override
	protected String[] getImportProperties() {
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<SaleOrderPayway> list) {
		return false;
	}

	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("rows", new ArrayList<SaleOrderPayway>());
		obj.put("total", 0);

		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);

		String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
		String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		//是否小计
		//			String isSubTotal = params.get("isSubTotal") == null ? null : params.get("isSubTotal").toString();
		//页面选择了特定的店铺
		if (StringUtils.isNotEmpty(shopNo)) {
			//				params.put("shopNoLists",  Arrays.asList(shopNo.split(",")));
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		} else {
			//根据公司和管理城市查询店铺
			Map<String, Object> shopParamMap = new HashMap<String, Object>();
			shopParamMap.put("companyNo", companyNo);
			if (StringUtils.isNotEmpty(organNo)) {
				shopParamMap.put("parentOrganNos", FasUtil.formatInQueryCondition(organNo));
			}
			//需要查询的店铺列表
			List<String> shopNoList = new ArrayList<String>();
			//页面选择了公司和管理城市，根据提交来查询店铺
			List<Shop> shopList = shopManager.findByBiz(null, shopParamMap);
			if (!CollectionUtils.isEmpty(shopList)) {
				for (Shop shop : shopList) {
					shopNoList.add(shop.getShopNo());
				}
			}
			if (!CollectionUtils.isEmpty(shopNoList)) {
				params.put("shopNoList", shopNoList);
			} else {
				params.put("shopNo", "01");
			}
		}

		List<Integer> businessTypeList = new ArrayList<Integer>();
		//0-正常销售 1-跨店销售
		businessTypeList.add(Integer.valueOf(0));
		businessTypeList.add(Integer.valueOf(1));
		businessTypeList.add(Integer.valueOf(2));
		businessTypeList.add(Integer.valueOf(6));
		params.put("businessTypeList", businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货
		statusList.add(Integer.valueOf(30));
		statusList.add(Integer.valueOf(41));
		params.put("statusList", statusList);

		int total = this.orderMainManager.findSaleMonthReportCount(params);
		if (total < 1) {
			return obj;
		}

		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SaleOrderPayway> dailyReportList = orderMainManager.findSaleMonthReportByPage(page, sortColumn, sortOrder,
				params);

		obj.put("rows", dailyReportList);
		obj.put("total", total);

		//	    	OcOrderMainDto  ocOrderMainDto =  billShopSaleOrderManager.findList4SumOcOrderByParameter(params);
		//根据条件汇总销售金额、数量
		SaleOrderPayway saleOrderPayway = orderMainManager.getSumSaleMonthReport(params);

		if (null != saleOrderPayway) {
			// 组装footer
			SaleOrderPayway payWay = new SaleOrderPayway();
			payWay.setShopName("合计");
			payWay.setPayAmount(saleOrderPayway.getPayAmount());
			payWay.setReturnAmount(saleOrderPayway.getReturnAmount());
			payWay.setOrderAmount(saleOrderPayway.getOrderAmount());
			payWay.setP01(saleOrderPayway.getP01());
			payWay.setP04(saleOrderPayway.getP04());
			payWay.setP03(saleOrderPayway.getP03());
			payWay.setP05(saleOrderPayway.getP05());
			payWay.setP06(saleOrderPayway.getP06());

			payWay.setP08(saleOrderPayway.getP08());
			payWay.setP09(saleOrderPayway.getP09());
			payWay.setP10(saleOrderPayway.getP10());
			payWay.setP11(saleOrderPayway.getP11());
			payWay.setP12(saleOrderPayway.getP12());
			payWay.setP13(saleOrderPayway.getP13());
			payWay.setP14(saleOrderPayway.getP14());
			payWay.setP15(saleOrderPayway.getP15());
			payWay.setP16(saleOrderPayway.getP16());
			payWay.setP17(saleOrderPayway.getP17());
			payWay.setP18(saleOrderPayway.getP18());
			payWay.setP19(saleOrderPayway.getP19());
			payWay.setP20(saleOrderPayway.getP20());
			payWay.setP21(saleOrderPayway.getP21());
			payWay.setP22(saleOrderPayway.getP22());
			payWay.setP23(saleOrderPayway.getP23());
			payWay.setP24(saleOrderPayway.getP24());
			payWay.setP25(saleOrderPayway.getP25());
			payWay.setP26(saleOrderPayway.getP26());
			payWay.setP27(saleOrderPayway.getP27());
			payWay.setP28(saleOrderPayway.getP28());
			payWay.setP29(saleOrderPayway.getP29());
			payWay.setP30(saleOrderPayway.getP30());
			payWay.setP31(saleOrderPayway.getP31());
			payWay.setP32(saleOrderPayway.getP32());
			payWay.setP35(saleOrderPayway.getP35());
			payWay.setP999(saleOrderPayway.getP999());
			List<SaleOrderPayway> saleOrderPaywaySumTotal = new ArrayList<SaleOrderPayway>();
			saleOrderPaywaySumTotal.add(payWay);

			obj.put("footer", saleOrderPaywaySumTotal);
		}
		return obj;
	}

	/**
	 * 查询导出数据的方法
	 * @param params 查询参数
	 * @return List<ModelType>
	 * @throws ManagerException 异常
	 */
	@Override
	protected List<SaleOrderPayway> queryExportData(Map<String, Object> params) throws ManagerException {
		String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
		String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		//是否小计
		String isSubTotal = params.get("isSubTotal") == null ? null : params.get("isSubTotal").toString();
		//页面选择了特定的店铺
		if (StringUtils.isNotEmpty(shopNo)) {
			//				params.put("shopNoList",  Arrays.asList(shopNo.split(",")));
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		} else {
			//根据公司和管理城市查询店铺
			Map<String, Object> shopParamMap = new HashMap<String, Object>();
			shopParamMap.put("companyNo", companyNo);
			if (StringUtils.isNotEmpty(organNo)) {
				shopParamMap.put("parentOrganNos", FasUtil.formatInQueryCondition(organNo));
			}
			//需要查询的店铺列表
			List<String> shopNoList = new ArrayList<String>();
			//页面选择了公司和管理城市，根据提交来查询店铺
			List<Shop> shopList = shopManager.findByBiz(null, shopParamMap);
			if (!CollectionUtils.isEmpty(shopList)) {
				for (Shop shop : shopList) {
					shopNoList.add(shop.getShopNo());
				}
			}
			if (!CollectionUtils.isEmpty(shopNoList)) {
				params.put("shopNoList", shopNoList);
			}
		}

		List<Integer> businessTypeList = new ArrayList<Integer>();
		//0-正常销售 1-跨店销售
		businessTypeList.add(Integer.valueOf(0));
		businessTypeList.add(Integer.valueOf(1));
		businessTypeList.add(Integer.valueOf(2));
		businessTypeList.add(Integer.valueOf(6));
		params.put("businessTypeList", businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货
		statusList.add(Integer.valueOf(30));
		statusList.add(Integer.valueOf(41));
		params.put("statusList", statusList);

		//			int total = this.shopDayReportManager.findShopDailyReportCount(params);
		int total = this.orderMainManager.findSaleMonthReportCount(params);
		if (total < 1) {
			return null;
		}
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();

		SimplePage page = new SimplePage(1, total, (int) total);
		//			List<POSOcGroupOrderPayway> dailyReportList = shopDayReportManager.findShopDailyReportByPage(page, orderByField, orderBy, params);
		List<SaleOrderPayway> dailyReportList = orderMainManager.findSaleMonthReportByPage(page, orderByField, orderBy,
				params);

		SaleOrderPayway saleOrderPayway = orderMainManager.getSumSaleMonthReport(params);

		if (null != saleOrderPayway) {
			// 组装footer
			SaleOrderPayway payWay = new SaleOrderPayway();
			payWay.setShopName("合计");
			payWay.setPayAmount(saleOrderPayway.getPayAmount());
			payWay.setReturnAmount(saleOrderPayway.getReturnAmount());
			payWay.setOrderAmount(saleOrderPayway.getOrderAmount());
			payWay.setP01(saleOrderPayway.getP01());
			payWay.setP04(saleOrderPayway.getP04());
			payWay.setP03(saleOrderPayway.getP03());
			payWay.setP05(saleOrderPayway.getP05());
			payWay.setP06(saleOrderPayway.getP06());

			payWay.setP08(saleOrderPayway.getP08());
			payWay.setP09(saleOrderPayway.getP09());
			payWay.setP10(saleOrderPayway.getP10());
			payWay.setP11(saleOrderPayway.getP11());
			payWay.setP12(saleOrderPayway.getP12());
			payWay.setP13(saleOrderPayway.getP13());
			payWay.setP14(saleOrderPayway.getP14());
			payWay.setP15(saleOrderPayway.getP15());
			payWay.setP16(saleOrderPayway.getP16());
			payWay.setP17(saleOrderPayway.getP17());
			payWay.setP18(saleOrderPayway.getP18());
			payWay.setP19(saleOrderPayway.getP19());
			payWay.setP20(saleOrderPayway.getP20());
			payWay.setP21(saleOrderPayway.getP21());
			payWay.setP22(saleOrderPayway.getP22());
			payWay.setP23(saleOrderPayway.getP23());
			payWay.setP24(saleOrderPayway.getP24());
			payWay.setP25(saleOrderPayway.getP25());
			payWay.setP26(saleOrderPayway.getP26());
			payWay.setP27(saleOrderPayway.getP27());
			payWay.setP28(saleOrderPayway.getP28());
			payWay.setP29(saleOrderPayway.getP29());
			payWay.setP30(saleOrderPayway.getP30());
			payWay.setP31(saleOrderPayway.getP31());
			payWay.setP32(saleOrderPayway.getP32());
			payWay.setP35(saleOrderPayway.getP35());
			payWay.setP999(saleOrderPayway.getP999());
			dailyReportList.add(payWay);

		}
		//			return convertDailyReportList(dailyReportList, isSubTotal);
		return dailyReportList;
	}

	//	@Override
	public Map<String, Object> queryListOld(HttpServletRequest req, Model model) throws ManagerException {
		// TODO Auto-generated method stub

		Map<String, Object> params = builderParams(req, model);
		Map<String, Object> maps = null;
		List<IndependentShopMonthReport> independentShopMonthReportList = independentShopMonthReportManager
				.queryEachMonthShopReport(params);
		if (independentShopMonthReportList != null && independentShopMonthReportList.size() > 0) {
			maps = new HashMap<String, Object>();
			maps.put("total", independentShopMonthReportList.size());
			maps.put("rows", independentShopMonthReportList);
		}
		return maps;
	}

	//	@Override
	protected List<IndependentShopMonthReport> queryExportDataOld(Map<String, Object> params) throws ManagerException {
		// TODO Auto-generated method stub
		return independentShopMonthReportManager.queryEachMonthShopReport(params);
	}
}
