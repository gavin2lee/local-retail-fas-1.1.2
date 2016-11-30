package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopNameReplace;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.manager.ShopNameReplaceManager;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-01-06 17:24:59
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
@RequestMapping("/base_setting/shop_name_replace")
public class ShopNameReplaceController extends ExcelImportController<ShopNameReplace> {
	@Resource
	private ShopNameReplaceManager shopNameReplaceManager;

	@Resource
	private ShopManager shopManager;

	@Resource
	private BrandUnitManager brandUnitManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("shop_name_replace/", shopNameReplaceManager);
	}

	@RequestMapping("/list")
	public String list() {
		return "base_setting/shop_name_replace/list";
	}

	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 20 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));

		Map<String, Object> params = builderParams(req, model);
		// 格式化参数
		params = this.formatParams(params);
		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<ShopNameReplace> list = new ArrayList<ShopNameReplace>();
		total = shopNameReplaceManager.findCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = shopNameReplaceManager.findByPage(page, sortColumn, sortOrder, params);
		}

		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 格式化参数
	 * 
	 * @param params
	 * @return
	 */
	private Map<String, Object> formatParams(Map<String, Object> params) {

		if (params.get("companyNos") != null && !"".equals(params.get("companyNos").toString())) {
			params.put("companyNos", FasUtil.formatInQueryCondition(params.get("companyNos").toString()));
		}

		if (params.get("shopNos") != null && !"".equals(params.get("shopNos").toString())) {
			params.put("shopNos", FasUtil.formatInQueryCondition(params.get("shopNos").toString()));
		}

		if (params.get("brandUnitNos") != null && !"".equals(params.get("brandUnitNos").toString())) {
			params.put("brandUnitNos", FasUtil.formatInQueryCondition(params.get("brandUnitNos").toString()));
		}

		return params;
	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<ShopNameReplace> list) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 导入
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, ShopNameReplace modelType) throws Exception {
		ModelAndView mv = new ModelAndView("/print/import");
		try {
			SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<ShopNameReplace> entityClass = (Class<ShopNameReplace>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,
					entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<ShopNameReplace> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(),
					ShopNameReplace.class, entityClass.getSimpleName());
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
				for (ShopNameReplace shopNameReplace : datas) {

					shopNameReplace.setCreateUser(loginUser.getUsername());
					shopNameReplace.setCreateTime(new Date());

					// 新增数据
					ResponseEntity<ShopNameReplace> resEntiy = this.add(shopNameReplace);

					count++;
				}

				if (count > 0) {
					mv.addObject("success", "成功导入" + count + "条记录");
				} else {
					mv.addObject("error", "没有要导入的行！");
				}
			} else {
				mv.addObject("error", errorBuilder);
			}
			return mv;

		} catch (Exception e) {
			mv.addObject("error", "导入数据发生其他异常，请联系管理员");
			return mv;
		}
	}

	/**
	 * 校验数据方法
	 * 
	 * @param datas
	 *            List<ShopNameReplace>
	 * @param request
	 *            HttpServletRequest
	 * @return 返回校验错误信息集合
	 */
	private List<Object> getDataValidators(List<ShopNameReplace> datas, HttpServletRequest request)
			throws ManagerException {
		List<Object> errors = new ArrayList<Object>();
		if (datas != null && datas.size() > 0) {

			int lineNum = 2;
			String errorMessage = null;

			for (ShopNameReplace shopNameReplace : datas) {

				// 判断空数据
				if (StringUtils.isEmpty(shopNameReplace.getShopNo())
						|| StringUtils.isEmpty(shopNameReplace.getShopNo())
						|| StringUtils.isEmpty(shopNameReplace.getReplaceName())) {
					errorMessage = "第" + lineNum + "行数据有空记录";
					errors.add(errorMessage);
					lineNum++;
					continue;
				}

				// 判断店铺是否存在
				Map<String, Object> paramsShop = new HashMap<String, Object>();
				paramsShop.put("shopNo", shopNameReplace.getShopNo());
				List<Shop> shopList = shopManager.findByBiz(null, paramsShop);
				if (CollectionUtils.isEmpty(shopList)) {
					errorMessage = "第" + lineNum + "行数据的店铺编号不存在";
					errors.add(errorMessage);
					lineNum++;
					continue;
				} else {
					shopNameReplace.setShopName(shopList.get(0).getShortName());
				}

				// 判断品牌部是否存在
				Map<String, Object> paramsBrandUnit = new HashMap<String, Object>();
				paramsBrandUnit.put("brandUnitNo", shopNameReplace.getBrandUnitNo());
				List<BrandUnit> brandUnitList = brandUnitManager.findByBiz(null, paramsBrandUnit);
				if (CollectionUtils.isEmpty(brandUnitList)) {
					errorMessage = "第" + lineNum + "行数据的品牌部编号不存在";
					errors.add(errorMessage);
					lineNum++;
					continue;
				} else {
					shopNameReplace.setBrandUnitName(brandUnitList.get(0).getName());
				}

				lineNum++;
			}

		}

		return errors;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/validate_data")
	public Map<String, Object> validateData(HttpServletRequest req) throws ManagerException, JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		String insertedList = (StringUtils.isEmpty(req.getParameter("inserted"))) ? "" : req.getParameter("inserted");
		String updatedList = (StringUtils.isEmpty(req.getParameter("updated"))) ? "" : req.getParameter("updated");
		List<ShopNameReplace> checkList = new ArrayList<ShopNameReplace>();

		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
			});
			checkList.addAll(convertInvoiceInfo(mapper, list));
		}
		if (StringUtils.isNotEmpty(updatedList)) {
			List<Map> ulist = mapper.readValue(updatedList, new TypeReference<List<Map>>() {
			});
			checkList.addAll(convertInvoiceInfo(mapper, ulist));
		}
		if (!CollectionUtils.isEmpty(checkList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
			});
			checkList.addAll(convertInvoiceInfo(mapper, list));
			Map<String, List<String>> duplicateMap = new HashMap<String, List<String>>();

			Map<String, Object> checkParams = new HashMap<String, Object>();
			int count = 0;
			boolean duplicate = false;
			String duplicateShopGroup = "";
			String duplicateBrand = "";
			for (ShopNameReplace settlePeriod : checkList) {
				checkParams.put("shopNo", settlePeriod.getShopNo());
				checkParams.put("brandUnitNo", settlePeriod.getBrandUnitNo());
				count = this.shopNameReplaceManager.findCount(checkParams);
				if (count > 0) {
					duplicate = true;
					duplicateShopGroup = settlePeriod.getShopName();
					duplicateBrand = settlePeriod.getBrandUnitName();
					break;
				}
				if (duplicateMap.containsKey(settlePeriod.getShopNo())) {
					if (duplicateMap.get(settlePeriod.getShopNo()).contains(settlePeriod.getBrandUnitNo())) {
						duplicate = true;
						duplicateShopGroup = settlePeriod.getShopName();
						duplicateBrand = settlePeriod.getBrandUnitName();
						break;
					} else {
						duplicateMap.get(settlePeriod.getShopNo()).add(settlePeriod.getBrandUnitNo());
					}
				} else {
					List<String> brandNos = new ArrayList<String>();
					brandNos.add(settlePeriod.getBrandUnitNo());
					duplicateMap.put(settlePeriod.getShopNo(), brandNos);
					break;
				}
			}
			if (duplicate) {
				result.put("success", false);
				result.put("message", "[" + duplicateShopGroup + "]的[" + duplicateBrand + "]品牌部的店铺已替换！");
				return result;
			}
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 转换成泛型列表
	 * 
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<ShopNameReplace> convertInvoiceInfo(ObjectMapper mapper, List<Map> list) throws JsonParseException,
			JsonMappingException, JsonGenerationException, IOException {
		Class<ShopNameReplace> entityClass = (Class<ShopNameReplace>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		List<ShopNameReplace> tl = new ArrayList<ShopNameReplace>(list.size());
		for (int i = 0; i < list.size(); i++) {
			ShopNameReplace type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
			tl.add(type);
		}
		return tl;
	}
}