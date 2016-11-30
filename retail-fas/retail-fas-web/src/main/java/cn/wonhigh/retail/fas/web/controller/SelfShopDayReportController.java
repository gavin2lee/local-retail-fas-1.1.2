package cn.wonhigh.retail.fas.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.MPSPaymentDto;
import cn.wonhigh.retail.fas.common.enums.ExportTypeEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.DepositCashManager;
import cn.wonhigh.retail.fas.manager.ShopDayReportManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.utils.SelfShopDayReportControllerHelper;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.BeanUtilsCommon;

@Controller
@RequestMapping("/self_shop_day_report")
@ModuleVerify("30170003")
public class SelfShopDayReportController extends ExcelImportController<SaleOrderPayway> {
	private static Field[] fields = SaleOrderPayway.class.getDeclaredFields();//声明静态变量，供对象共享
	@Resource
	private ShopDayReportManager shopDayReportManager;
	@Resource
	private ShopManager shopManager;
	@Resource
	private DepositCashManager depositCashManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("IndepShop_management/self_shop_day_report/", shopDayReportManager);
	}

	@Override
	protected String[] getImportProperties() {
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<SaleOrderPayway> list) {
		return false;
	}

	@RequestMapping("/self_shop_day_report_all")
	public String self_shop_day_report_all() {
		return "IndepShop_management/self_shop_day_report/self_shop_day_report_all";
	}

	@RequestMapping("/self_shop_day_report_brand")
	public String self_shop_day_report_brand() {
		return "IndepShop_management/self_shop_day_report/self_shop_day_report_brand";
	}
	
	/**
	 * 相同业务逻辑使用模板处理
	 * @return
	 * @author wangshimiao
	 * @throws ManagerException 
	 */
	public Map<String,Object> getParams(HttpServletRequest req, Model model) throws ManagerException{
		Map<String, Object> obj = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		params = getQueryParams(params);
		SaleOrderPayway show = this.getShow(req, model);
		obj.put("params", params);
		obj.put("pageNo", pageNo);
		obj.put("pageSize", pageSize);
		obj.put("sortColumn", sortColumn);
		obj.put("sortOrder", sortOrder);
		obj.put("show", show);

		return obj;
	}
	
	/**
	 * 相同业务逻辑使用模板处理
	 * @return
	 * @author wangshimiao
	 * @throws ManagerException 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getResult(Map<String, Object> param,SelfShopDayReportControllerHelper helper) throws ManagerException{
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> params = (Map<String, Object>) param.get("params");
		if (params == null) {
			obj.put("rows", new ArrayList<SaleOrderPayway>());
			obj.put("total", 0);
			return obj;
		}
		
		Map<String, Object> result = helper.getList(params);
		SaleOrderPayway all = (SaleOrderPayway) result.get("all");
		List<SaleOrderPayway> list = (java.util.List<SaleOrderPayway>) result.get("list");
		List<SaleOrderPayway> resultList = new ArrayList<SaleOrderPayway>();
		int pageNo = (int) param.get("pageNo");
		int pageSize = (int) param.get("pageSize");
		for (int i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
			if (i > (list.size() - 1))
				break;
			resultList.add(list.get(i));
		}
		
		List<SaleOrderPayway> footer = new ArrayList<SaleOrderPayway>();
		all.setShopNo("合计");
		footer.add(all);

		obj.put("rows", this.dailyReportListByShopNoAndOutdate(resultList));
		obj.put("total", all.getTotal());
		obj.put("all", param.get("show"));
		obj.put("footer", footer);
		obj.put("show", this.getShowColumns((SaleOrderPayway) param.get("show")));
		return obj;
	}
	
	/**
	 * 所有品牌查询
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@Override
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = getParams(req, model);
		SelfShopDayReportControllerHelper helper = new SelfShopDayReportControllerHelper() {
			@Override
			public Map<String, Object> getList(Map<String, Object> params) throws ManagerException {
				Map<String, Object> obj = new HashMap<String, Object>();
				List<SaleOrderPayway> list = this.deepCopy(shopDayReportManager.findShopDayReportByPage(null,null, params));
				Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(params);
				list = getPaidinAmountByOutDate(list, map);
				SaleOrderPayway all = this.getAllCount(list);
				obj.put("all", all);
				obj.put("list", list);
				return obj;
			}

			@Override
			public Map<String, Object> getExportList(
					Map<String, Object> params) throws ManagerException {
				return null;
			}
		};
		return getResult(params, helper);
	}

	/**
	 * 分品牌查询
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/list_brand")
	@ResponseBody
	public Map<String, Object> queryListForBrand(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = getParams(req, model);
		SelfShopDayReportControllerHelper helper = new SelfShopDayReportControllerHelper() {
			@Override
			public Map<String, Object> getList(Map<String, Object> params)
					throws ManagerException {
				Map<String, Object> obj = new HashMap<String, Object>();
				Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(params);
				
				List<SaleOrderPayway> list = this.deepCopy(shopDayReportManager.findSaleDayReportForBrandByPage(null, null, params));
				list = this.getPaidinAmountForBrandByOutDate(list, map);
				list = this.dailyReportListByShopNo(list);//实收误差校正
				list = this.dailyReportListByBrand(list);//分品牌计算实收，差异,并校正误差
				SaleOrderPayway all = this.getPaininAndDiff(list);//分品牌获取明细实收、差异、总账
				all = this.calculateError(all);//计算销售总计误差
				obj.put("all", all);
				obj.put("list", list);
				return obj;
			}

			@Override
			public Map<String, Object> getExportList(
					Map<String, Object> params) throws ManagerException {
				return null;
			}
		};
		return getResult(params, helper);
	}

	private Map<String, Object> getQueryParams(Map<String, Object> params) {
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		String mallNo = params.get("mallNo") == null ? null : params.get("mallNo").toString();
		String brandNo = params.get("brandNo") == null ? null : params.get("brandNo").toString();
		String retailType = params.get("retailType") == null ? null : params.get("retailType").toString();

		Map<String, Object> shopParamMap = new HashMap<String, Object>();
		shopParamMap.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		shopParamMap.put("mallNoLists", FasUtil.formatInQueryCondition(mallNo));
		shopParamMap.put("retailType", FasUtil.formatInQueryCondition(retailType));
		List<String> shopList = new ArrayList<String>();
		if (!(StringUtils.isBlank(shopNo) && StringUtils.isBlank(mallNo) && StringUtils.isBlank(retailType))) {
			shopList = shopManager.getShopBySelfCheckin(shopParamMap);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyNo", companyNo);
		if (shopList.size() > 0) {
			map.put("shopNoLists",
					FasUtil.formatInQueryCondition(shopList.toString().replace("[", "").replace("]", "")
							.replace(" ", "")));
		}
		map.put("brandNoLists", FasUtil.formatInQueryCondition(brandNo));
		map.put("startOutDate", params.get("startOutDate"));
		map.put("endOutDate", params.get("endOutDate"));

		List<Integer> businessTypeList = new ArrayList<Integer>();
		//0-正常销售 1-跨店销售
		businessTypeList.add(Integer.valueOf(0));
		businessTypeList.add(Integer.valueOf(1));
		businessTypeList.add(Integer.valueOf(2));
		businessTypeList.add(Integer.valueOf(6));
		map.put("businessTypeList", businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货
		statusList.add(Integer.valueOf(30));
		statusList.add(Integer.valueOf(41));
		map.put("statusList", statusList);
		return map;
	}

	private SaleOrderPayway getShow(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		List<SaleOrderPayway> list = null;
		params = getQueryParams(params);
		if (params == null) {
			return null;
		}
		
		list = this.deepCopy(shopDayReportManager.findShopDayReportByPage(null,null, params));
		list = getPaidinAmountByOutDate(list, params);

		SaleOrderPayway show = null;
		SaleOrderPayway temp = null;
		try {
			Class<SaleOrderPayway> cls = SaleOrderPayway.class;
			show = cls.newInstance();
			initSaleOrderPayway(show);
			int i = 0, len = list.size();
			while (i < len) {
				temp = list.get(i);
				for(Field field:fields){
					field.setAccessible(true);
					String name = field.getName();
					String type = field.getType().toString();
					if(type.endsWith("java.math.BigDecimal")){
						Method method = show.getClass().getMethod("set"+name.substring(0, 1).toUpperCase() + name.substring(1), BigDecimal.class);
						BigDecimal val = (BigDecimal) field.get(temp);
						if(val!=null&&val.compareTo(BigDecimal.ZERO)!=0){
							method.invoke(show, val);
						}
					}
				}
				i++;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		} 
		return show;
	}

	private List<SaleOrderPayway> getPaidinAmountByOutDate(
			List<SaleOrderPayway> list, Map<String, Object> params) {
		Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(params);
		for (SaleOrderPayway s : list) {
			BigDecimal bg = map.get(s.getShopNo() + s.getOutDate());
			if (bg != null) {
				s.setS01(bg);
				s.setD01(s.getS01().subtract(s.getP01()));
				s.setAmount(s.getAmount().add(s.getS01()));
				s.setDiffAmount(s.getDiffAmount().add(s.getS01()));
				s.setSum(s.getSum().add(s.getS01()));
			} else {
				s.setS01(new BigDecimal(0d));
				s.setD01(s.getP01().negate());
			}

		}
		return list;
	}

	private Map<String, Integer> getShowColumns(SaleOrderPayway saleOrderPayway) throws ManagerException {
		Integer[] cols = {0,0,0,0};//销售、实收、差异、总计
		try {
			for(Field f:fields){
				String name = f.getName();
				f.setAccessible(true);//设置属性可访问
				BigDecimal val = null;
				if(name.matches("p\\d+") || name.equals("totalAmount")){
					val = (BigDecimal) f.get(saleOrderPayway);
					if(val.compareTo(BigDecimal.ZERO)!=0){
						cols[0] = cols[0] + 1;
					}
				}else if(name.matches("s\\d+") || name.equals("amount")){
					val = (BigDecimal) f.get(saleOrderPayway);
					if(val.compareTo(BigDecimal.ZERO)!=0){
						cols[1] = cols[1] + 1;
					}
				}else if(name.matches("d\\d+") || name.equals("returnAmount") || name.equals("actualReturnAmount") || name.equals("diffAmount")){
					val = (BigDecimal) f.get(saleOrderPayway);
					if(val.compareTo(BigDecimal.ZERO)!=0){
						cols[2] = cols[2] + 1;
					}
				}else if(name.equals("poundage") || name.equals("sum")){
					val = (BigDecimal) f.get(saleOrderPayway);
					if(val.compareTo(BigDecimal.ZERO)!=0){
						cols[3] = cols[3] + 1;
					}
				}
			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		} 

		Map<String, Integer> maps = new HashMap<String, Integer>();
		maps.put("sales", cols[0]);
		maps.put("paidins", cols[1]);
		maps.put("diffs", cols[2]);
		maps.put("sum", cols[3]);

		return maps;
	}

	/**
	 * 计算小计
	 * @param dailyReportList
	 * @return
	 * @throws ManagerException 
	 */
	private List<SaleOrderPayway> dailyReportListByShopNoAndOutdate(List<SaleOrderPayway> dailyReportList) throws ManagerException {
		SaleOrderPayway temp = null;
		Map<String, SaleOrderPayway> map = new HashMap<String, SaleOrderPayway>();
		for (SaleOrderPayway saleOrderPayway : dailyReportList) {
			if (null == map.get(saleOrderPayway.getShopName())) {
				temp = new SaleOrderPayway();
				this.initSaleOrderPayway(temp);
				temp.setShopNo("小计");
				temp.setShopName(saleOrderPayway.getShopName());
				map.put(saleOrderPayway.getShopName(), temp);
			}
			this.sumToSaleOrderPayway(map.get(saleOrderPayway.getShopName()), saleOrderPayway);
		}
		//按店铺编号汇总到列表底部
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			SaleOrderPayway saleOrderPaywayObject = map.get(key);
			String shopName = saleOrderPaywayObject.getShopName();
			int index = -1;
			for (SaleOrderPayway s : dailyReportList) {
				if (null != s.getShopName() && null != shopName && shopName.equals(s.getShopName())) {
					index = dailyReportList.indexOf(s);
				}
			}
			if (index > -1) {
				dailyReportList.add(index + 1, saleOrderPaywayObject);
			}
		}
		return dailyReportList;
	}

	/**
	 * 获取所有导出列表
	 * @author wangshimiao
	 * @param helper
	 * @throws ManagerException 
	 */
	@SuppressWarnings("unchecked")
	private List<SaleOrderPayway> getExportList(Map<String, Object> param,SelfShopDayReportControllerHelper helper) throws ManagerException {
		Map<String, Object> result = helper.getExportList(param);
		List<SaleOrderPayway> list = (List<SaleOrderPayway>) result.get("list");
		SaleOrderPayway all = helper.getAllCount(list);
		list = this.dailyReportListByShopNoAndOutdate(list);//增加小计显示
		all.setShopNo("合计");//增加合计
		list.add(all);
		return list;
	}
	
	/**
	 * 所有品牌导出
	 */
	@Override
	protected List<SaleOrderPayway> queryExportData(Map<String, Object> params) throws ManagerException {
		params = getQueryParams(params);
		SelfShopDayReportControllerHelper helper = new SelfShopDayReportControllerHelper() {
			@Override
			public Map<String, Object> getList(Map<String, Object> params)
					throws ManagerException {
				return null;
			}
			@Override
			public Map<String, Object> getExportList(Map<String, Object> params)
					throws ManagerException {
				Map<String, Object> obj = new HashMap<String, Object>();
				List<SaleOrderPayway> list = this.deepCopy(shopDayReportManager.findShopDayReportByPage(null,null, params));
				Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(params);
				list = getPaidinAmountByOutDate(list, map);
				obj.put("list", list);
				SaleOrderPayway all = this.getAllCount(list);
				obj.put("all", all);
				return obj;
			}
		};
		return this.getExportList(params,helper);
	}

	/**
	 * 店铺日报表分品牌导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/brand_export")
	public void doSaleOrderPaywayForBrandExport(HttpServletRequest req, Model model, HttpServletResponse response)
			throws Exception {
		Map<String, Object> params = this.builderParams(req, model);
		Map<String, Object> param = this.getParams(req, model);
		SelfShopDayReportControllerHelper helper = new SelfShopDayReportControllerHelper() {
			@Override
			public Map<String, Object> getList(Map<String, Object> params)
					throws ManagerException {
				return null;
			}
			@Override
			public Map<String, Object> getExportList(Map<String, Object> params)
					throws ManagerException {
				Map<String, Object> obj = new HashMap<String, Object>();
				Map<String, Object> param = (Map<String, Object>) params.get("params");
				Map<String, BigDecimal> map = depositCashManager.getPaidinAmount(param);
				
				List<SaleOrderPayway> list = this.deepCopy(shopDayReportManager.findSaleDayReportForBrandByPage(null, null, param));//从缓存取值
				list = this.getPaidinAmountForBrandByOutDate(list, map);
				list = this.dailyReportListByShopNo(list);//实收误差校正
				list = this.dailyReportListByBrand(list);//分品牌计算实收，差异,并校正误差
				SaleOrderPayway all = this.getPaininAndDiff(list);//分品牌获取明细实收、差异、总账
				all = this.calculateError(all);//计算销售总计误差
				obj.put("all", all);
				obj.put("list", list);
				return obj;
			}
		};
		List<SaleOrderPayway> list = this.getExportList(param, helper);

		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String) params.get("firstHeaderColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String) params.get("exportType");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotEmpty(exportColumns)) {
			try {
				//字段名列表
				List<Map> columnsList = mapper.readValue(exportColumns, new TypeReference<List<Map>>() {
				});

				List<Map> subColumnsList = new ArrayList<Map>();
				if (StringUtils.isNotEmpty(subExportColumns)) {
					subColumnsList = mapper.readValue(subExportColumns, new TypeReference<List<Map>>() {
					});

					//如果是混合表头，则将subColumnsList加入columnsList集合
					if (ExportTypeEnum.FIX_HEADER.getType().equalsIgnoreCase(exportType)) {
						columnsList.addAll(subColumnsList);
						subColumnsList = new ArrayList<Map>(1);
					}
				}
				columnsList = this.sortExportColumns(columnsList);
				ExportComplexVo exportVo = new ExportComplexVo();
				exportVo.setColumnsMapList(columnsList);
				exportVo.setSubColumnsMapList(subColumnsList);

				if (StringUtils.isNotEmpty(firstHeaderColumns)) {
					List<Map> headerColumnsList = mapper.readValue(firstHeaderColumns, new TypeReference<List<Map>>() {
					});
					exportVo.setHeaderColumnsMapList(headerColumnsList);
				}

				List<Map> listArrayList = new ArrayList<Map>();
				if (list != null && list.size() > 0) {
					List<String> fields = new ArrayList<String>();
					for (Map map : columnsList) {
						fields.add(map.get("field").toString());
					}
					boolean flag = true;
					ExportFormat formatAnnotation = null;
					AbstractExportFormat<SaleOrderPayway> format = null;
					for (SaleOrderPayway vo : list) {
						Map<String, List> map = null;
						if (flag) {
							formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
							flag = false;
						}
						if (formatAnnotation != null) {
							format = (AbstractExportFormat<SaleOrderPayway>) formatAnnotation.className().newInstance();
							map = format.format(fields, vo);
						} else {
							map = new HashMap<String, List>();
							BeanUtilsCommon.object2MapWithoutNull(vo, map);
						}
						if (subColumnsList != null && subColumnsList.size() > 0) {
							List<Map> subExportData = this.findComplexHeaderData(vo);
							map.put("subExportData", subExportData);
						} else {
							map.put("subExportData", new ArrayList<Map>(1));
						}
						listArrayList.add(map);
					}
					Integer rowAccessWindowSize = getRowAccessWindowSizeValue(rowAccessWindowSizeStr);
					exportVo.setRowAccessWindowSize(rowAccessWindowSize);
					exportVo.setDataMapList(listArrayList);
					exportVo.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : "导出信息");
					HSSFExportComplex.commonExportData(exportVo, response);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
		} else {

		}

	}

	private void initSaleOrderPayway(SaleOrderPayway temp) throws ManagerException {
		for(Field field:fields){
			this.setField(field, temp, BigDecimal.ZERO);
		}
	}

	private void sumToSaleOrderPayway(SaleOrderPayway mainSaleOrderPayway, SaleOrderPayway addSaleOrderPayway) throws ManagerException {
		try {
			for(Field field:fields){
				field.setAccessible(true);
				if(field.getType().toString().endsWith("java.math.BigDecimal")){
					BigDecimal mainVal = (BigDecimal) field.get(mainSaleOrderPayway);
					BigDecimal addVal = (BigDecimal) field.get(addSaleOrderPayway);
					if(mainVal!=null && addVal !=null){
						this.setField(field, mainSaleOrderPayway, mainVal.add(addVal));
					}
				}
			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 给entity对象的field属性赋值val
	 * @param field
	 * @param entity
	 * @param val
	 * @throws ManagerException 
	 */
	private void setField(Field field,Object entity,Object val) throws ManagerException{
		String name = field.getName();
		if(name.matches("p\\d+")
				|| name.matches("s\\d+") || name.matches("d\\d+")
				|| name.equals("totalAmount") || name.equals("amount")
				|| name.equals("returnAmount") || name.equals("actualReturnAmount")
				|| name.equals("diffAmount") || name.equals("poundage") || name.equals("sum")){
			try {
				Method method = SaleOrderPayway.class.getMethod("set"+name.substring(0, 1).toUpperCase() + name.substring(1), BigDecimal.class);
				method.invoke(entity, val);
			} catch (Exception e) {
				throw new ManagerException(e.getMessage(), e);
			}
		}
	}

	@RequestMapping("/paymentList")
	@ResponseBody
	public Map<String, Object> queryPaymentType() throws ManagerException {

		List<MPSPaymentDto> paymentList = shopDayReportManager.queryPaymentsList();
		Map<String, Object> maps = null;
		if (paymentList != null) {
			maps = new HashMap<String, Object>();

			MPSPaymentDto paymentDto1 = new MPSPaymentDto();
			paymentDto1.setPayCode("shopNo");
			paymentDto1.setPayName("店铺编码");

			MPSPaymentDto paymentDto2 = new MPSPaymentDto();
			paymentDto2.setPayCode("shopName");
			paymentDto2.setPayName("店铺名称");

			MPSPaymentDto paymentDto3 = new MPSPaymentDto();
			paymentDto3.setPayCode("outDate");
			paymentDto3.setPayName("销售日期");

			paymentList.add(0, paymentDto1);
			paymentList.add(1, paymentDto2);
			paymentList.add(2, paymentDto3);

			maps.put("rows", paymentList);
			maps.put("total", paymentList.size());
		}
		return maps;
	}
	
	@SuppressWarnings("unchecked")
	protected List<SaleOrderPayway> deepCopy(List<SaleOrderPayway> srcList) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(srcList);

			ByteArrayInputStream byteIn = new ByteArrayInputStream(
					byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			List<SaleOrderPayway> destList = (List<SaleOrderPayway>) in
					.readObject();
			return destList;
		} catch (ClassNotFoundException e) {
			logger.error("未找到指定类", e);
		} catch (IOException e) {
			logger.error("list深拷贝IO流异常", e);
		}
		return srcList;
	}
}
