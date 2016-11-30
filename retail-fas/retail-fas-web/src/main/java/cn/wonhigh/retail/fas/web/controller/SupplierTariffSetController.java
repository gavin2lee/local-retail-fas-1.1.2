package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.model.SupplierTariffSet;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.SupplierManager;
import cn.wonhigh.retail.fas.manager.SupplierTariffSetManager;
import cn.wonhigh.retail.mdm.api.util.CacheContext;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-08 15:43:31
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
@RequestMapping("/supplier_tariff_set")
@ModuleVerify("34000007")
public class SupplierTariffSetController extends BaseController<SupplierTariffSet> {
    @Resource
    private SupplierTariffSetManager supplierTariffSetManager;
    @Resource
    private SupplierManager supplierManager;
	
    @Override
    public CrudInfo init() {
        return new CrudInfo("supplier_tariff_set/",supplierTariffSetManager);
    }
    /**
     * 增删改方法
     * @param req
     * @return
     * @throws ManagerException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/validate_data")
	@ResponseBody
	public Map<String, Object> validateData(HttpServletRequest req) throws ManagerException, JsonParseException, JsonMappingException, IOException {

		Map<String, Object> result = new HashMap<String, Object>();
//		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		List<SupplierTariffSet> checkList = new ArrayList<SupplierTariffSet>();

		if(StringUtils.isNotEmpty(insertedList)){
			if(!insertedList.equals("[]")){
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
				checkList.addAll(convertSupplierTariffSet(mapper,list));
			}
		}
		if (!CollectionUtils.isEmpty(checkList)) {
			Map<String, List<String>> duplicateMap = new HashMap<String, List<String>>();
			
			Map<String, Object> checkParams = new HashMap<String, Object>();
			int count = 0;
			boolean duplicate = false;
			String duplicateSupplierNo = "";
			for (SupplierTariffSet settlePeriod : checkList) {
				checkParams.put("supplierNo", settlePeriod.getSupplierNo());
				checkParams.put("styleNo", settlePeriod.getStyleNo());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = formatter.format( settlePeriod.getEffectiveDate());
				checkParams.put("effectiveDate", dateString);
				count = this.supplierTariffSetManager.findCount(checkParams);
				if (count > 0) {
					duplicate = true;
					duplicateSupplierNo = settlePeriod.getSupplierName();
					break;
				}	
				if (duplicateMap.containsKey(settlePeriod.getSupplierNo())) {
					if (duplicateMap.get(settlePeriod.getSupplierNo()).contains(settlePeriod.getSupplierNo())) {
						duplicate = true;
						duplicateSupplierNo = settlePeriod.getSupplierName();
						break;
					}else {
						duplicateMap.get(settlePeriod.getSupplierNo());
					}
				}else {
					List<String> supplierNos = new ArrayList<String>();
					supplierNos.add(settlePeriod.getSupplierNo());
				}
			}
			if (duplicate) {
				result.put("success", false);
				result.put("message", "供应商[" + duplicateSupplierNo + "]已经维护了关税设置！");
				return result;
			}
		}
		result.put("success", true);
		return result;
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
   	@SuppressWarnings({ "unchecked", "rawtypes" })
   	private List<SupplierTariffSet> convertSupplierTariffSet(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
   		Class<SupplierTariffSet> entityClass = (Class<SupplierTariffSet>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
   		List<SupplierTariffSet> tl=new ArrayList<SupplierTariffSet>(list.size());
   		for (int i = 0; i < list.size(); i++) {
   			SupplierTariffSet type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
   			tl.add(type);
   		}
   		return tl;
   	}
    /**
     * 查询
     */
    @RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		params=setParams(params);
		int total = supplierTariffSetManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<SupplierTariffSet> list = supplierTariffSetManager.findByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
    
    /**
	 * 参数设置
	 * @param req
	 * @param map
	 * @return
	 */
	public Map<String, Object> setParams(Map<String, Object> params){
		String supplierNo = params.get("supplierNo") == null ? "" : params.get("supplierNo").toString();
		List<String> supplierNoList = new ArrayList<String>();
		if (StringUtils.isNotBlank(supplierNo)) {
			if (supplierNo.contains(",")) {
				String[] supplierNo1 = supplierNo.split(",");
				for (String supplierNoTemp : supplierNo1) {
					supplierNoList.add(supplierNoTemp);
				}
			} else {
				supplierNoList.add(supplierNo);
			}
			params.put("supplierNos", supplierNoList);
			params.put("supplierNo", null);
		}
		return params;
	}
	
	 /**
	  * 导入
	  * @param file
	  * @param request
	  * @return
	  * @throws Exception
	  */
   @SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, SupplierTariffSet modelType)throws Exception{
   	ModelAndView mv = new ModelAndView("/print/import");
   	try {
   		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
   		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<SupplierTariffSet> entityClass = (Class<SupplierTariffSet>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<SupplierTariffSet> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), SupplierTariffSet.class,entityClass.getSimpleName());

			
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
	    		for (SupplierTariffSet supplierTariffSet : datas) {
	    			CacheContext context = CacheContext.current();
	    			supplierTariffSet.setSupplierNo(supplierTariffSet.getSupplierNo());
	    			supplierTariffSet.setTariffRate(supplierTariffSet.getTariffRate());
	    			supplierTariffSet.setStyleNo(supplierTariffSet.getStyleNo());
	    			supplierTariffSet.setEffectiveDate(supplierTariffSet.getEffectiveDate());
	    			supplierTariffSet.setSupplierName(context.getSupplierName(supplierTariffSet.getSupplierNo()));
	    			supplierTariffSet.setCreateUser(loginUser.getUsername());
	    			supplierTariffSet.setCreateTime(new Date());
	    			
	    			this.add(supplierTariffSet);
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
			mv.addObject("error", "导入数据的关税率不是数字格式！");
			return mv;
		}catch (ParseException e) {
			mv.addObject("error", "导入数据的生效时间不正确，格式需为yyyy-MM-dd或yyyy/MM/dd");
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
 	private List<Object> getDataValidators(List<SupplierTariffSet> datas, HttpServletRequest request) throws ManagerException{
 		try {
			Map<Map<String, String>, List<String>> duplicateMap = new HashMap<Map<String, String>, List<String>>();
			Map<String, String> regionKeyMap = new HashMap<String, String>();
 			List<Object> errors = new ArrayList<Object>();
 			if(datas != null && datas.size() > 0) {
 				Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();

 				//获取所有供应商
 				List<Supplier> listSupplier = supplierManager.findByBiz(null, null);
 				List<String> supplierNos = new ArrayList<String>();
 				if (!CollectionUtils.isEmpty(listSupplier)) {
 					for (Supplier supplier : listSupplier) {
 						supplierNos.add(supplier.getSupplierNo());
 					}
 				}
 				int lineNum = 2;
 				int count = 0;
 				String errorMessage = null;
 				for(SupplierTariffSet supplierTariffSet : datas) {
 					//判断空数据
 					if (StringUtils.isEmpty(supplierTariffSet.getSupplierNo()) || null == supplierTariffSet.getStyleNo() 
 							|| null == supplierTariffSet.getTariffRate() ||  null == supplierTariffSet.getEffectiveDate()) {
 						errorMessage = "第" + lineNum + "行数据有空记录";
 						errors.add(errorMessage);
 						lineNum ++;
 						continue;
 					}
 					//判断品牌是否存在
 					String supplier=supplierTariffSet.getSupplierNo();
 					if (!supplierNos.contains(supplier)) {
 						errorMessage = "第" + lineNum + "行数据的供应商不存在或有误";
 						errors.add(errorMessage);
 						lineNum ++;
 						continue;
 					}
 					//数据唯一性校验
 					uniqueCheckMap.put("supplierNo", supplierTariffSet.getSupplierNo());
 					uniqueCheckMap.put("styleNo", supplierTariffSet.getStyleNo());
 					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
 					String dateString = formatter.format( supplierTariffSet.getEffectiveDate());
 					uniqueCheckMap.put("effectiveDate", dateString);
 					count = this.supplierTariffSetManager.findCount(uniqueCheckMap);
 					if (count > 0) {
 						errorMessage = "第" + lineNum + "行数据的供应商款号关税生效日已经存在";
 						errors.add(errorMessage);
 						lineNum ++;
 						continue;
 					}
 					regionKeyMap = new HashMap<String, String>();
					regionKeyMap.put("supplierNo", supplierTariffSet.getSupplierNo());
					regionKeyMap.put("styleNo", supplierTariffSet.getStyleNo());
 					String dateStrings = formatter.format( supplierTariffSet.getEffectiveDate());
 					regionKeyMap.put("effectiveDate", dateStrings);
					if(duplicateMap.containsKey(regionKeyMap)) {
						if (duplicateMap.get(regionKeyMap).contains(DateUtil.getDate(supplierTariffSet.getEffectiveDate(), "yyyy-mm-dd"))) {
							errorMessage = "第" + lineNum + "行数据的供应商关税生效日已经在导入的文件中存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}
						duplicateMap.get(regionKeyMap).add(DateUtil.getDate(supplierTariffSet.getEffectiveDate(), "yyyy-mm-dd"));
					} else {
						List<String> effectiveTime = new ArrayList<String>();
						effectiveTime.add(DateUtil.getDate(supplierTariffSet.getEffectiveDate(), "yyyy-mm-dd"));
						duplicateMap.put(regionKeyMap, effectiveTime);
					}
 					lineNum ++;
 				}
 			}
 			return errors;
 		} catch (ManagerException e) {
 			logger.error("供应商款号关税校验数据失败。");
 			throw new ManagerException(e);
 		}
 	}
}