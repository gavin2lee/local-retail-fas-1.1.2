package cn.wonhigh.retail.fas.web.controller;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.ExportTypeEnum;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.PayRelationshipManager;
import cn.wonhigh.retail.fas.manager.SupplierReturnProfitManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

@Controller
@RequestMapping("/return_profit")
@ModuleVerify("40001021")
public class ReturnProfitController extends BaseCrudController<PayRelationship> {
	private Logger logger = Logger.getLogger(ApplyForPaymentController.class);

	@Resource
	private PayRelationshipManager payRelationshipManager;
	
	@Resource
	private SupplierReturnProfitManager supplierReturnProfitManager;

	@Override
	protected CrudInfo init() {
		return new CrudInfo("return_profit/", null);
	}
	
	@Override
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		params.put("businessBillType", "1301");
		
		PayRelationship payRelationship = payRelationshipManager.findReturnProfitCount(params);
		List<PayRelationship> list = new ArrayList<PayRelationship>();
		if(payRelationship != null && payRelationship.getTotal() > 0){
			SimplePage page = new SimplePage(pageNo, pageSize, payRelationship.getTotal());
			list = payRelationshipManager.findReturnProfitList(page, null, null, params);
			payRelationship.setSupplierName("合计");
			list.add(payRelationship);
		}
		map.put("total", payRelationship.getTotal());
		map.put("rows", list);

		return map;
	}
	
	@RequestMapping(value = "/gennerateReturnProfit")
	@ResponseBody
	public Map<String, Object> generateReturnProfit(HttpServletRequest request,BigDecimal rate) throws Exception {
		Map<String, Object> params = new HashMap<String,Object>();
		String checkedRows = request.getParameter("checkedRows");
		ObjectMapper mapper = new ObjectMapper();
		List<PayRelationship> list = new ArrayList<PayRelationship>();
		if (StringUtils.isNotEmpty(checkedRows)) {
			@SuppressWarnings("rawtypes")
			List<Map> l = mapper.readValue(checkedRows, new TypeReference<List<Map>>(){});
			list = convertListWithTypeReference(mapper, l, request);
		}
		String type = request.getParameter("type");
		for(PayRelationship payRelationShip : list){
			//删除已生成的返利
			supplierReturnProfitManager.deleteGenerateReturnProfit(payRelationShip, null);
			rate = rate.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
			//重新生成返利
			supplierReturnProfitManager.addSupplierReturnProfit(payRelationShip, rate, type, 0);
		}
		
    	params.put("success", true);
		return params;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/do_fas_export")
	public void doFasExport(PayRelationship PayRelationship, HttpServletRequest request, Model model, HttpServletResponse response) throws ManagerException{
		Map<String, Object> params = builderParams(request, model);
		params.put("billType", "1301");
		PayRelationship payRelationship = payRelationshipManager.findReturnProfitCount(params);
		List<PayRelationship> list = new ArrayList<PayRelationship>();
		if(payRelationship != null && payRelationship.getTotal() > 0){
			SimplePage page = new SimplePage(1, payRelationship.getTotal(), payRelationship.getTotal());
			list = payRelationshipManager.findReturnProfitList(page, null, null, params);
			payRelationship.setSupplierName("合计");
			list.add(payRelationship);
		}
		String exportColumns = (String) params.get("exportColumns");
		String firstHeaderColumns = (String)params.get("firstHeaderColumns");
//		String lastHeaderColumns = (String)params.get("lastHeaderColumns");
		String subExportColumns = (String) params.get("exportSubColumns");
		String fileName = (String) params.get("fileName");
		String exportType = (String)params.get("exportType");
		//增加参数，该参数可以不指定，使用默认值
		String rowAccessWindowSizeStr = (String) params.get("rowAccessWindowSize");
		ObjectMapper mapper = new ObjectMapper();
		if(StringUtils.isNotEmpty(exportColumns)) {
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
					AbstractExportFormat<PayRelationship> format = null;
					for (PayRelationship vo : list) {
						Map<String, List> map = null;
						if (flag) {
							formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
							flag = false;
						}
						if (formatAnnotation != null) {
							format = (AbstractExportFormat<PayRelationship>) formatAnnotation.className().newInstance();
							map = format.format(fields, vo);
						} else {
							map = new HashMap<String, List>();
							String json = mapper.writeValueAsString(vo);
							map = mapper.readValue(json, Map.class);
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
			} catch(Exception e) {
				e.printStackTrace();
				throw new ManagerException(e.getMessage(), e);
			}
		} else {

		}
	}
	
	/**
	 * 对导出字段进行排序
	 * @param columns 原导出字段集合
	 * @return 排序后的导出字段
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> sortExportColumns(List<Map> columns) {
		List<Map> results = new ArrayList<Map>();
		for (int i = 0; i < columns.size(); i++) {
			Map map = null;
			if (columns.get(i).get("seq") != null && !"".equals(columns.get(i).get("seq").toString().trim())) {
				for (Map entry : columns) {
					Integer seq = Integer.parseInt(entry.get("seq").toString());
					if (seq.intValue() == i + 1) {
						map = entry;
						break;
					}
				}
			} else {
				map = columns.get(i);
			}
			results.add(map);
		}
		return results;
	}
	
	/**
	 * 查询符合表头的数据
	 * @param vo 查询参数
	 * @return List<Map>
	 */
	@SuppressWarnings("rawtypes")
	protected List<Map> findComplexHeaderData(PayRelationship vo) {
		return new ArrayList<Map>();
	}
	
	/**
	 * 转换成整型
	 * @param rowAccessWindowSizeStr String
	 * @return Integer
	 */
	public Integer getRowAccessWindowSizeValue(String rowAccessWindowSizeStr) {
		Integer rowAccessWindowSize = 1;
		if (!StringUtils.isEmpty(rowAccessWindowSizeStr)) {
			try {
				rowAccessWindowSize = Integer.parseInt(rowAccessWindowSizeStr);
			} catch (NumberFormatException e) {
				rowAccessWindowSize = 1;
			}
		}
		return rowAccessWindowSize;
	}

	
	protected List<PayRelationship> queryExportData(Map<String, Object> params) throws ManagerException {
		params.put("billType", "1301");
		PayRelationship payRelationship = payRelationshipManager.findReturnProfitCount(params);
		List<PayRelationship> list = new ArrayList<PayRelationship>();
		if(payRelationship != null && payRelationship.getTotal() > 0){
			SimplePage page = new SimplePage(1, payRelationship.getTotal(), payRelationship.getTotal());
			list = payRelationshipManager.findReturnProfitList(page, null, null, params);
			payRelationship.setSupplierName("合计");
			list.add(payRelationship);
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<PayRelationship> convertListWithTypeReference(ObjectMapper mapper, List<Map> list,
			HttpServletRequest request) throws Exception  {
		Class<PayRelationship> entityClass = (Class<PayRelationship>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		List<PayRelationship> tl = new ArrayList<PayRelationship>(list.size());
		for (int i = 0; i < list.size(); i++) {
			PayRelationship type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
			this.setDefaulValues(type, request);
			tl.add(type);
		}
		return tl;
	}
	
	protected PayRelationship setDefaulValues(PayRelationship model, HttpServletRequest request) {
		SystemUser loginUser = CurrentUser.getCurrentUser(request);
		if (StringUtils.isEmpty(model.getId())) {
			model.setId(null);
			model.setCreateUser(loginUser.getUsername());
			try {
				model.setCreateTime(DateUtil.getCurrentDateTime());
				model.setUpdateTime(DateUtil.getCurrentDateTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
		} else {
			model.setUpdateUser(loginUser.getUsername());
			try {
				model.setUpdateTime(DateUtil.getCurrentDateTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);

			}
		}
		return model;
	}

}
