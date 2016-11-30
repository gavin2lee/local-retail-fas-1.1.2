package cn.wonhigh.retail.fas.web.controller;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.RegionPriceRule;
import cn.wonhigh.retail.fas.common.model.SpecialZoneInfo;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.CommonUtilManager;
import cn.wonhigh.retail.fas.manager.ItemManager;
import cn.wonhigh.retail.fas.manager.RegionCostMaintainManager;
import cn.wonhigh.retail.fas.manager.RegionPriceRuleManager;
import cn.wonhigh.retail.fas.manager.SpecialZoneInfoManager;
import cn.wonhigh.retail.fas.manager.ZoneInfoManager;
import cn.wonhigh.retail.fas.web.utils.HSSFExportComplex;
import cn.wonhigh.retail.fas.web.utils.RegionRuleMatchHandleThread;
import cn.wonhigh.retail.fas.web.vo.ExportComplexVo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author wang.xy1
 * @date 2014-09-01 09:25:14
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
@RequestMapping("/region_cost_maintain")
@ModuleVerify("30120016")
public class RegionCostMaintainController extends
		BaseController<RegionCostMaintain> {

	private static final XLogger logger = XLoggerFactory.getXLogger(RegionCostMaintainController.class); 
	
	@Resource
	private RegionCostMaintainManager regionCostMaintainManager;

	@Resource
	private RegionPriceRuleManager regionPriceRuleManager;
	
	@Resource
	private CommonUtilManager commonUtilManager;

	@Resource
	private ZoneInfoManager zoneInfoManager;
	
	@Resource
	private SpecialZoneInfoManager specialZoneInfoManager;
	
	@Resource
	private ItemManager itemManager;

    @Resource
    private BrandManager brandManager;
    
	@Override
	public CrudInfo init() {
		return new CrudInfo("region_cost_maintain/", regionCostMaintainManager);
	}
	
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest request, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
        int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 10 : Integer.parseInt(request.getParameter("rows"));
        String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : String.valueOf(request.getParameter("sort"));
        String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : String.valueOf(request.getParameter("order"));
        Map<String, Object> params = builderParams(request, model);
        //地区类型多选
  		if(params.get("zoneNo") != null && !"".equals(params.get("zoneNo").toString())) {
  			String[] temps = params.get("zoneNo").toString().split(",");
  			List<String> tempList = Arrays.asList(temps);
  			params.put("zoneNos", tempList);
  			params.put("zoneNo", null);
  		}
        
        int total = regionCostMaintainManager.findCount(params);
        SimplePage page = new SimplePage(pageNo, pageSize, total);
        List<RegionCostMaintain> list = regionCostMaintainManager.findByPage(page, sortColumn, sortOrder, params);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", Integer.valueOf(total));
        obj.put("rows", list);
        return obj;
	}

	/**
	 * 通过商品属性信息，查询匹配加价规则，生成总部价
	 * 
	 * @param itemNo
	 *            商品编码
	 * @param request
	 *            HttpServletRequest
	 * @return 总部价格维护对象
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/generate_cost")
	@ResponseBody
	public RegionCostMaintain genetareHeadquarterCost(String itemNo, String zoneNo,
			HttpServletRequest request) throws ManagerException {
		if (StringUtils.isEmpty(itemNo)) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", itemNo);
		List<Item> itemList = this.itemManager.findByBiz(null, params);

		RegionCostMaintain regionCostMaintain = new RegionCostMaintain();

		if (CollectionUtils.isEmpty(itemList)) {
			return null;
		}
		
		Item item = itemList.get(0);
		regionCostMaintain.setTwoLevelCategoryNo(item.getCategoryNo().substring(0, 4));
		regionCostMaintain.setItemNo(item.getItemNo());
		regionCostMaintain.setItemCode(item.getCode());
		regionCostMaintain.setItemName(item.getName());
		regionCostMaintain.setBrandNo(item.getBrandNo());
		regionCostMaintain.setBrandName(item.getBrandName());
		regionCostMaintain.setSupplierNo(item.getSupplierNo());
		regionCostMaintain.setSupplierName(item.getSupplierName());
//		regionCostMaintain.setCategoryNo(item.getCategoryNo());
		regionCostMaintain.setCategoryNo(item.getRootCategoryNo());
		regionCostMaintain.setYear(item.getYears());
		regionCostMaintain.setSeason(item.getSellSeason());
		regionCostMaintain.setSuggestTagPrice(item.getSuggestTagPrice());
		regionCostMaintain.setZoneNo(zoneNo);
		this.regionCostMaintainManager.genetareRegionCost(regionCostMaintain);

		return regionCostMaintain;
	}

	/**
     * 选择规则时的事件处理函数
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 总部价格维护对象
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/rule_choosed.json")
	public ResponseEntity<Map<String, Object>> ruleChoosedEvent(HttpServletRequest req,Model model, RegionCostMaintain regionCostMaintain) throws ManagerException{
		
    	Map<String, Object> returnMap = regionCostMaintainManager.getPriceRuleData(regionCostMaintain);
    	
		return new ResponseEntity<Map<String, Object>>(returnMap, HttpStatus.OK);
	}
    
    /**
     * 查询规则列表
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 总部价格维护对象
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/query_priceRules.json")
	public ResponseEntity<Map<String, Object>> queryRegionPriceRuleList(String itemNo, String zoneNo,String addRuleNo,
			HttpServletRequest request) throws ManagerException{
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	if (StringUtils.isEmpty(addRuleNo)) {
    		resultMap.put("rules", new ArrayList<RegionPriceRule>());
    		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
    	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", itemNo);
		List<Item> itemList = this.itemManager.findByBiz(null, params);
		RegionCostMaintain regionCostMaintain = new RegionCostMaintain();
		if (CollectionUtils.isEmpty(itemList)) {
			resultMap.put("rules", new ArrayList<RegionPriceRule>());
    		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		Item item = itemList.get(0);
		regionCostMaintain.setItemNo(item.getItemNo());
		regionCostMaintain.setItemCode(item.getCode());
		regionCostMaintain.setItemName(item.getName());
		regionCostMaintain.setBrandNo(item.getBrandNo());
		regionCostMaintain.setBrandName(item.getBrandName());
		regionCostMaintain.setSupplierNo(item.getSupplierNo());
		regionCostMaintain.setSupplierName(item.getSupplierName());
		regionCostMaintain.setCategoryNo(item.getRootCategoryNo());
		regionCostMaintain.setYear(item.getYears());
		regionCostMaintain.setSeason(item.getSellSeason());
		regionCostMaintain.setSuggestTagPrice(item.getSuggestTagPrice());
		regionCostMaintain.setZoneNo(zoneNo);

		List<RegionPriceRule> rules = this.regionCostMaintainManager.findRegionAddRulesByBiz(regionCostMaintain);
		
		if (CollectionUtils.isEmpty(rules)) {
			Map<String, Object> ruleParamMap = new HashMap<String, Object>();
			ruleParamMap.put("addRuleNo", addRuleNo);
			rules = this.regionPriceRuleManager.findByBiz(null, ruleParamMap);
		}
		resultMap.put("rules", rules);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
    
	/**
	 * 批量生成地区价
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws ManagerException
	 *             异常
	 * @throws InterruptedException 
	 */
	@RequestMapping(value = "/batch_generate_cost")
	@ResponseBody
	public Map<String, Boolean> batchGenetareHeadquarterCost(
			HttpServletRequest request, RegionCostMaintain costMaintain)
			throws ManagerException, InterruptedException {

		// 返回值
		Map<String, Boolean> map = new HashMap<String, Boolean>();
    	SystemUser currUser = Authorization.getUser();
    	String organTypeNo = "";
		if(currUser != null){
			organTypeNo = currUser.getOrganTypeNo();
		}
		
    	List<Brand> brands = null;
    	List<String> zoneInfos = null;
    	
		if (null != costMaintain.getBrandUnitNos() && costMaintain.getBrandUnitNos().length > 0) {
			Map<String, Object> brandParams = new HashMap<String, Object>();
			brandParams.put("brandUnitNos", Arrays.asList(costMaintain.getBrandUnitNos()));
			
			brands = this.brandManager.findByBiz(null, brandParams);
		}
		String multiPriceZoneNo = FasUtil.parseInQueryCondition(request.getParameter("multiPriceZoneNo"));
		costMaintain.setZoneNos(multiPriceZoneNo.split(","));
		if (null != costMaintain.getZoneNos() && costMaintain.getZoneNos().length > 0) {
			zoneInfos = Arrays.asList(costMaintain.getZoneNos());
		}
		// add by wang.m
		String HQQuotationFlag=request.getParameter("HQQuotationFlag");
		ExecutorService exe = Executors.newFixedThreadPool(zoneInfos.size());  
        for (int i = 0; i < zoneInfos.size(); i++) {  
        	//优化 判断地区，品牌部是否存在规则
        	boolean flag = false;
        	String zoneNo = zoneInfos.get(i);
        	String[] brandUnitNos = costMaintain.getBrandUnitNos();
        	Map<String, Object> ruleParams = new HashMap<String, Object>();
        	for (String str : brandUnitNos) {
        		ruleParams.put("zoneNoAlial", zoneNo);
        		ruleParams.put("brandUnitNoAlial", str);
        		List<RegionPriceRule> ruleList = regionPriceRuleManager.findByBiz(null, ruleParams);
        		if(ruleList.size() > 0){
        			flag = true;
        			break;
        		}
			}
        	if(flag){
        		List<String> newZoneList = new ArrayList<String>();
            	newZoneList.add(zoneInfos.get(i));
        		// 查询所有地区需要维护的商品参数
        		Map<String, Object> params = new HashMap<String, Object>();
        		params.put("multiCategoryNo", request.getParameter("multiCategoryNo"));
        		params.put("brands", brands);
        		params.put("organTypeNo", organTypeNo);
        		params.put("userName", currUser.getUsername());
            	params.put("zoneNo", zoneInfos.get(i));
            	params.put("HQQuotationFlag", HQQuotationFlag);
                exe.execute(new Thread(new RegionRuleMatchHandleThread(this.regionCostMaintainManager,this.commonUtilManager,
        				this.zoneInfoManager,this.brandManager,newZoneList, params)));  
        	}
        }  
        exe.shutdown();  
        exe.awaitTermination(12, TimeUnit.HOURS);
        map.put("success", true);
        return map;
	}

    /**
     * 批量生成地区价
     * @throws Exception 
     */
    @RequestMapping(value = "/batch_generate_cost_new")
	@ResponseBody
	public Map<String, Object>  batchGenetareCostNew(HttpServletRequest req,Model model) throws Exception {
    	Map<String, Object> params = this.builderParams(req, model);
    	int count = regionCostMaintainManager.batchGenetareCostNew(params);
    	Map<String, Object> resultMap = new HashMap<>();
    	resultMap.put("success", true);
    	resultMap.put("count", count);
        return resultMap;
    }
    
    
    /**
     * 批量生成地区价
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 总部价格维护对象
     * @throws ManagerException 异常
     * @throws ParseException 
     * @throws InterruptedException 
     */
    @RequestMapping(value = "/generate_cost_by_rule")
	@ResponseBody
	public Boolean batchGenetareCostByRule(HttpServletRequest req,Model model) throws ManagerException {
    	if(StringUtils.isNotBlank(req.getParameter("effectiveTime"))
    			&& StringUtils.isNotBlank(req.getParameter("addRuleNo"))){
    		Map<String, Object> params = builderParams(req, model);
    		regionCostMaintainManager.batchGenetareCostByRule(params);
        	return true;
    	}
        return false;
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
	public ModelAndView doImport(HttpServletRequest request, RegionCostMaintain modelType)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	try {
    		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
    		
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<RegionCostMaintain> entityClass = (Class<RegionCostMaintain>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<RegionCostMaintain> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), RegionCostMaintain.class,entityClass.getSimpleName());
    		// 数据校验
    		List<Object> error = getDataValidators(datas, request);
			StringBuilder errorBuilder = new StringBuilder();
			if (null != error && error.size() > 0) {
				for (int i = 0; i < error.size(); i++) {
					errorBuilder.append(error.get(i) + "<br/>");
				}
			}
			if (errorBuilder.length() == 0) {
				Item item = null;
	    		List<Item> itemList = null;
	    		Map<String, Object> paramMap = new HashMap<String, Object>();
	    		int count = 0;
	    		for (RegionCostMaintain regionCostMaintain : datas) {
	    			paramMap.put("code", regionCostMaintain.getItemCode());
	    			paramMap.put("brandNo", regionCostMaintain.getBrandNo());
	    			itemList = itemManager.findByBiz(null, paramMap);
	    			item = itemList.get(0);
	    			regionCostMaintain.setItemNo(item.getItemNo());
	    			regionCostMaintain.setItemName(item.getName());
	    			regionCostMaintain.setSupplierNo(item.getSupplierNo());
	    			regionCostMaintain.setSupplierName(item.getSupplierName());
	    			regionCostMaintain.setBrandNo(item.getBrandNo());
	    			regionCostMaintain.setBrandName(item.getBrandName());
	    			
	    			regionCostMaintain.setCreateUser(loginUser.getUsername());
	    			regionCostMaintain.setCreateTime(new Date());
	    			
	    			this.add(regionCostMaintain);
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
			mv.addObject("error", "导入数据的总部成本价不是数字格式！");
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
	private List<Object> getDataValidators(List<RegionCostMaintain> datas, HttpServletRequest request) throws ManagerException{
		try {
			List<Object> errors = new ArrayList<Object>();
			if(datas != null && datas.size() > 0) {
				//Map<zoneNo, itemCode>, List<effectiveTime> 形式的数据
				Map<Map<String, String>, List<String>> duplicateMap = new HashMap<Map<String, String>, List<String>>();
				Map<String, String> regionKeyMap = new HashMap<String, String>();
				
				Map<String, Object> itemMap = new HashMap<String, Object>();
				Map<String, Object> zoneMap = new HashMap<String, Object>();
				Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
				
				//获取所有品牌列表
				List<Brand> listBrands = brandManager.findByBiz(null, null);
				List<String> brandNos = new ArrayList<String>();
				if (!CollectionUtils.isEmpty(listBrands)) {
					for (Brand brand : listBrands) {
						brandNos.add(brand.getBrandNo());
					}
				}
				
				int lineNum = 2;
				int count = 0;
				String errorMessage = null;
				List<Item> items = null;
				List<ZoneInfo> zoneInfos = null;
				List<SpecialZoneInfo> specialZoneInfos = null;
				
				for(RegionCostMaintain regionCostMaintain : datas) {
					//判断空数据
					if (StringUtils.isEmpty(regionCostMaintain.getItemCode()) || null == regionCostMaintain.getEffectiveTime() 
							|| null == regionCostMaintain.getRegionCost() || StringUtils.isEmpty(regionCostMaintain.getZoneNo())) {
						errorMessage = "第" + lineNum + "行数据有空记录";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//判断大区编码是否小写
					if (!regionCostMaintain.getZoneNo().toUpperCase().equals(regionCostMaintain.getZoneNo())) {
						errorMessage = "第" + lineNum + "行数据的大区编码是小写";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					if (regionCostMaintain.getRegionCost().compareTo(BigDecimal.ZERO) == -1) {
						errorMessage = "第" + lineNum + "行数据的地区成本小于0";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//判断品牌是否存在
					if (!brandNos.contains(regionCostMaintain.getBrandNo())) {
						errorMessage = "第" + lineNum + "行数据的品牌不存在或有误";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//判断数据的有效性
					itemMap.put("code", regionCostMaintain.getItemCode());
					itemMap.put("brandNo", regionCostMaintain.getBrandNo());
					items = itemManager.findByBiz(null, itemMap);
					if (CollectionUtils.isEmpty(items)) {
						errorMessage = "第" + lineNum + "行数据的该品牌的商品不存在";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//判断数据的有效性
					zoneMap.put("zoneNo", regionCostMaintain.getZoneNo());
					zoneInfos = zoneInfoManager.findByBiz(null, zoneMap);
					if (CollectionUtils.isEmpty(zoneInfos)) {
						specialZoneInfos = specialZoneInfoManager.findByBiz(null, zoneMap);
						if (CollectionUtils.isEmpty(specialZoneInfos)) {
							errorMessage = "第" + lineNum + "行数据的地区编码不存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}else {
							regionCostMaintain.setZoneName(specialZoneInfos.get(0).getName());
						}
					}else {
						regionCostMaintain.setZoneName(zoneInfos.get(0).getName());
					}
					
					//数据唯一性校验
					uniqueCheckMap.put("itemNo", items.get(0).getItemNo());
					uniqueCheckMap.put("zoneNo", regionCostMaintain.getZoneNo());
					uniqueCheckMap.put("effectiveTime", regionCostMaintain.getEffectiveTime());
					count = this.regionCostMaintainManager.findCount(uniqueCheckMap);
					
					if (count > 0) {
						errorMessage = "第" + lineNum + "行数据的商品的地区价生效日已经存在";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					regionKeyMap = new HashMap<String, String>();
					regionKeyMap.put("zoneNo", regionCostMaintain.getZoneNo());
					regionKeyMap.put("itemNo", items.get(0).getItemNo());
					
					if(duplicateMap.containsKey(regionKeyMap)) {
						if (duplicateMap.get(regionKeyMap).contains(DateUtil.getDate(regionCostMaintain.getEffectiveTime(), "yyyy-mm-dd"))) {
							errorMessage = "第" + lineNum + "行数据的商品的地区价生效日已经在导入的文件中存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}
						duplicateMap.get(regionKeyMap).add(DateUtil.getDate(regionCostMaintain.getEffectiveTime(), "yyyy-mm-dd"));
					} else {
						List<String> effectiveTime = new ArrayList<String>();
						effectiveTime.add(DateUtil.getDate(regionCostMaintain.getEffectiveTime(), "yyyy-mm-dd"));
						duplicateMap.put(regionKeyMap, effectiveTime);
					}
					
					lineNum ++;
				}
			}
			return errors;
		} catch (ManagerException e) {
			logger.error("地区价维护新增修改时的数据校验失败。");
			throw new ManagerException(e);
		}
	}
	
	/**
	 * 查询导出数据的方法
	 * @param params 查询参数
	 * @return List<ModelType>
	 * @throws ManagerException 异常
	 */
	protected List<RegionCostMaintain> queryExportData(Map<String, Object> params) throws ManagerException {
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		//地区类型多选
		if(params.get("zoneNo") != null && !"".equals(params.get("zoneNo").toString())) {
			String[] temps = params.get("zoneNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("zoneNos", tempList);
			params.put("zoneNo", null);
		}
		
		int total = regionCostMaintainManager.findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		List<RegionCostMaintain> list = regionCostMaintainManager.findByPage(page, orderByField, orderBy, params);
		return list;
	}
	
	@RequestMapping(value = "/do_region_cost_export")
	public void regionCostExport(HttpServletRequest request, Model model,HttpServletResponse response)
			throws ManagerException {

		Map<String, Object> params = builderParams(request, model);
		//地区类型多选
		if(params.get("zoneNo") != null && !"".equals(params.get("zoneNo").toString())) {
			String[] temps = params.get("zoneNo").toString().split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("zoneNos", tempList);
			params.put("zoneNo", null);
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
			this.regionCostMaintainManager.findAreaPriceExport(page, params, handler);
		
			exportExcel.flush(response);
			exportExcel.close();
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
	
	/**
	 * 查询商品在每个地区的地区价
	 * @param request
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/zoneRegionCostlist.json")
	@ResponseBody
	public Map<String, Object> queryZoneRegionCost(HttpServletRequest request, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(request, model);
		String headquarterCost=(String) params.get("headquarterCost");
		Map<String, Object> ChenckParams = new HashMap<String,Object>(); 
		ChenckParams.put("itemNo",params.get("itemNo"));
		ChenckParams.put("effectiveDate",params.get("effectiveDate"));
		List<RegionCostMaintain> list = regionCostMaintainManager.findZoneRegionCost(ChenckParams);
		for(RegionCostMaintain regionCostMaintain:list){
			if(regionCostMaintain.getRegionCost() != null){
				Double ss=Double.valueOf(regionCostMaintain.getRegionCost().toString())-Double.valueOf(headquarterCost);
				regionCostMaintain.setAddRuleNo(""+ss);
			}else{
				regionCostMaintain.setAddRuleNo("0");
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("total", list.size());
        obj.put("rows", list);
        return obj;
	}
	
}