package cn.wonhigh.retail.fas.web.controller;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.StringUtils;
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

import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.HeadquarterPriceRule;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.CommonUtilManager;
import cn.wonhigh.retail.fas.manager.HeadquarterCostMaintainManager;
import cn.wonhigh.retail.fas.manager.HeadquarterPriceRuleManager;
import cn.wonhigh.retail.fas.manager.ItemManager;
import cn.wonhigh.retail.fas.web.utils.HQQuotationRuleMatchHandleThread;
import cn.wonhigh.retail.fas.web.utils.HQRuleMatchHandleThread;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-01 09:25:14
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
@RequestMapping("/headquarter_cost_maintain")
@ModuleVerify("30120015")
public class HeadquarterCostMaintainController extends BaseController<HeadquarterCostMaintain> {
	
    @Resource
    private HeadquarterCostMaintainManager headquarterCostMaintainManager;
    
    @Resource
    private HeadquarterPriceRuleManager headquarterPriceRuleManager;
    
    @Resource
    private ItemManager itemManager;

    @Resource
    private BrandManager brandManager;
    
    @Resource
    private CommonUtilManager commonUtilManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("headquarter_cost_maintain/",headquarterCostMaintainManager);
    }
    

    
    /**
     * 通过商品属性信息，查询匹配加价规则，生成总部价
     * 
     * @param itemNo 商品编码
     * @param request HttpServletRequest
     * @return 总部价格维护对象
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/generate_cost")
	@ResponseBody
	public HeadquarterCostMaintain genetareHeadquarterCost(String itemNo, 
			HttpServletRequest request) throws ManagerException {
    	if (StringUtils.isEmpty(itemNo)) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", itemNo);
		List<Item> itemList = this.itemManager.findByBiz(null, params);
		
		HeadquarterCostMaintain headCostMaintain = new HeadquarterCostMaintain();
		
		if (CollectionUtils.isEmpty(itemList)) {
			return null;
		}
		Item item = itemList.get(0);
		headCostMaintain.setTwoLevelCategoryNo(item.getCategoryNo().substring(0, 4));
		headCostMaintain.setItemNo(item.getItemNo());
		headCostMaintain.setItemCode(item.getCode());
		headCostMaintain.setItemName(item.getName());
		headCostMaintain.setBrandNo(item.getBrandNo());
		headCostMaintain.setBrandName(item.getBrandName());
		headCostMaintain.setSupplierNo(item.getSupplierNo());
		headCostMaintain.setSupplierName(item.getSupplierName());
//		headCostMaintain.setCategoryNo(item.getCategoryNo());
		headCostMaintain.setCategoryNo(item.getRootCategoryNo());
		headCostMaintain.setYear(item.getYears());
		headCostMaintain.setSeason(item.getSellSeason());
		headCostMaintain.setSuggestTagPrice(item.getSuggestTagPrice());
		
		this.headquarterCostMaintainManager.generateHeadquarterCost(headCostMaintain);
		
		return headCostMaintain;
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
	public ResponseEntity<Map<String, Object>> queryRegionPriceRuleList(String itemNo, String addRuleNo,
			HttpServletRequest request) throws ManagerException{
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	if (StringUtils.isEmpty(addRuleNo)) {
    		resultMap.put("rules", new ArrayList<HeadquarterPriceRule>());
    		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
    	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", itemNo);
		List<Item> itemList = this.itemManager.findByBiz(null, params);
		HeadquarterCostMaintain headCostMaintain = new HeadquarterCostMaintain();
		if (CollectionUtils.isEmpty(itemList)) {
			resultMap.put("rules", new ArrayList<HeadquarterPriceRule>());
    		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		Item item = itemList.get(0);
		headCostMaintain.setItemNo(item.getItemNo());
		headCostMaintain.setItemCode(item.getCode());
		headCostMaintain.setItemName(item.getName());
		headCostMaintain.setBrandNo(item.getBrandNo());
		headCostMaintain.setBrandName(item.getBrandName());
		headCostMaintain.setSupplierNo(item.getSupplierNo());
		headCostMaintain.setSupplierName(item.getSupplierName());
		headCostMaintain.setCategoryNo(item.getRootCategoryNo());
		headCostMaintain.setYear(item.getYears());
		headCostMaintain.setSeason(item.getSellSeason());
		headCostMaintain.setSuggestTagPrice(item.getSuggestTagPrice());

		List<HeadquarterPriceRule> rules = this.headquarterCostMaintainManager.findRegionAddRulesByBiz(headCostMaintain);
		if (CollectionUtils.isEmpty(rules)) {
			Map<String, Object> ruleParamMap = new HashMap<String, Object>();
			ruleParamMap.put("addRuleNo", addRuleNo);
			rules = this.headquarterPriceRuleManager.findByBiz(null, ruleParamMap);
		}
		resultMap.put("rules", rules);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    	
    /**
     * 批量生成总部价
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 总部价格维护对象
     * @throws ManagerException 异常
     * @throws ParseException 
     * @throws InterruptedException 
     */
    @RequestMapping(value = "/batch_generate_cost")
	@ResponseBody
	public Map<String, Boolean> batchGenetareHeadquarterCost(HttpServletRequest request, HeadquarterCostMaintain maintain) throws ManagerException, ParseException, InterruptedException {
    	SimpleDateFormat simdf=new SimpleDateFormat("yyyy-MM-dd");
		String firstNew=request.getParameter("firstNew1");
		String effectiveTime1=request.getParameter("effectiveTime1");
		String HQQuotationFlag=request.getParameter("HQQuotationFlag");
		
		Date effectiveTime=simdf.parse(effectiveTime1);
    	Map<String, Boolean> map = new HashMap<String, Boolean>();
    	SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
		
    	List<Brand> brands = null;
    	if (null != maintain.getBrandUnitNos() && maintain.getBrandUnitNos().length > 0) {
    		Map<String, Object> brandParams = new HashMap<String, Object>();
    		brandParams.put("brandUnitNos", Arrays.asList(maintain.getBrandUnitNos()));
    		
    		brands = this.brandManager.findByBiz(null, brandParams);
    	}
    	String multiCategoryNo = request.getParameter("multiCategoryNo");
    	ExecutorService exe = Executors.newFixedThreadPool(brands.size());  
    	//依据牌价批量生成
    	if ("2".equals(HQQuotationFlag)) {
			for (Brand brand : brands) {
				exe.execute(new Thread(new HQQuotationRuleMatchHandleThread(this.headquarterCostMaintainManager,this.commonUtilManager,
						loginUser.getUsername(), brand.getBrandNo(),firstNew,effectiveTime,multiCategoryNo)));
			}
		}else {
			//依据采购价批量生成
			for (Brand brand : brands) {
				exe.execute(new Thread(new HQRuleMatchHandleThread(this.headquarterCostMaintainManager,this.commonUtilManager,
						loginUser.getUsername(), brand.getBrandNo(),firstNew,effectiveTime,multiCategoryNo)));
			}
		}
        exe.shutdown();  
        exe.awaitTermination(12, TimeUnit.HOURS);
        map.put("success", true);
        return map;
    }

    /**
     * 批量生成总部价
     * @throws Exception 
     */
    @RequestMapping(value = "/batch_generate_cost_new")
	@ResponseBody
	public Map<String, Object>  batchGenetareCostNew(HttpServletRequest req,Model model) throws Exception {
    	Map<String, Object> params = this.builderParams(req, model);
    	int count = headquarterCostMaintainManager.batchGenetareCostNew(params);
    	Map<String, Object> resultMap = new HashMap<>();
    	resultMap.put("success", true);
    	resultMap.put("count", count);
        return resultMap;
    }
    
    /**
     * 批量生成总部价
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
        	headquarterCostMaintainManager.batchGenetareCostByRule(params);
        	return true;
    	}
        return false;
    }
    
    /**
     * 校验从总部价生效日起，该商品是否有地区价引用
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 总部价格维护对象
     * @throws ManagerException 异常
     */
    @RequestMapping(value = "/check_region_price.json")
	public ResponseEntity<Integer> getCheckRegionPrice(HttpServletRequest req,Model model) throws ManagerException{
		Map<String, Object> params = builderParams(req, model);
		int total= headquarterCostMaintainManager.findRegionPriceCount(params);
		return new ResponseEntity<Integer>(total, HttpStatus.OK);
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
	public ResponseEntity<Map<String, Object>> ruleChoosedEvent(HttpServletRequest req,Model model, HeadquarterCostMaintain headquarterCostMaintain) throws ManagerException{
		
    	Map<String, Object> returnMap = headquarterCostMaintainManager.getPriceRuleData(headquarterCostMaintain);
    	
		return new ResponseEntity<Map<String, Object>>(returnMap, HttpStatus.OK);
	}
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, HeadquarterCostMaintain modelType)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	try {
    		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
    		
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<HeadquarterCostMaintain> entityClass = (Class<HeadquarterCostMaintain>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<HeadquarterCostMaintain> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), HeadquarterCostMaintain.class,entityClass.getSimpleName());
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
	    		for (HeadquarterCostMaintain hqCostMaintain : datas) {
	    			paramMap.put("code", hqCostMaintain.getItemCode());
	    			paramMap.put("brandNo", hqCostMaintain.getBrandNo());
	    			itemList = itemManager.findByBiz(null, paramMap);
	    			item = itemList.get(0);
	    			hqCostMaintain.setItemNo(item.getItemNo());
	    			hqCostMaintain.setItemName(item.getName());
	    			hqCostMaintain.setSupplierNo(item.getSupplierNo());
	    			hqCostMaintain.setSupplierName(item.getSupplierName());
	    			hqCostMaintain.setBrandNo(item.getBrandNo());
	    			hqCostMaintain.setBrandName(item.getBrandName());
	    			hqCostMaintain.setCreateUser(loginUser.getUsername());
	    			hqCostMaintain.setCreateTime(new Date());
	    			
	    			this.add(hqCostMaintain);
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
	private List<Object> getDataValidators(List<HeadquarterCostMaintain> datas, HttpServletRequest request) throws ManagerException{
		try {
			List<Object> errors = new ArrayList<Object>();
			if(datas != null && datas.size() > 0) {
				Map<String, List<String>> duplicateMap = new HashMap<String, List<String>>();
				Map<String, Object> itemMap = new HashMap<String, Object>();
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
				
				for(HeadquarterCostMaintain costMaintain : datas) {
					//判断空数据
					if (StringUtils.isEmpty(costMaintain.getItemCode()) || null == costMaintain.getEffectiveTime() 
							|| null == costMaintain.getHeadquarterCost()) {
						errorMessage = "第" + lineNum + "行数据有空记录";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//判断总部成本
					if (costMaintain.getHeadquarterCost().compareTo(BigDecimal.ZERO) == -1) {
						errorMessage = "第" + lineNum + "行数据的总部成本小于0";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//判断品牌是否存在
					if (!brandNos.contains(costMaintain.getBrandNo())) {
						errorMessage = "第" + lineNum + "行数据的品牌不存在或有误";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//判断数据的有效性
					itemMap.put("code", costMaintain.getItemCode());
					itemMap.put("brandNo", costMaintain.getBrandNo());
					items = itemManager.findByBiz(null, itemMap);
					if (CollectionUtils.isEmpty(items)) {
						errorMessage = "第" + lineNum + "行数据的该品牌的商品不存在";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//数据唯一性校验
					uniqueCheckMap.put("itemNo", items.get(0).getItemNo());
					uniqueCheckMap.put("effectiveTime", costMaintain.getEffectiveTime());
					count = this.headquarterCostMaintainManager.findCount(uniqueCheckMap);
					
					if (count > 0) {
						errorMessage = "第" + lineNum + "行数据的商品的总部价生效日已经存在";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					
					if(duplicateMap.containsKey(items.get(0).getItemNo())) {
						if (duplicateMap.get(items.get(0).getItemNo()).contains(DateUtil.getDate(costMaintain.getEffectiveTime(), "yyyy-mm-dd"))) {
							errorMessage = "第" + lineNum + "行数据的商品的总部价生效日已经在导入的文件中存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}
						duplicateMap.get(items.get(0).getItemNo()).add(DateUtil.getDate(costMaintain.getEffectiveTime(), "yyyy-mm-dd"));
					} else {
						List<String> effectiveTime = new ArrayList<String>();
						effectiveTime.add(DateUtil.getDate(costMaintain.getEffectiveTime(), "yyyy-mm-dd"));
						duplicateMap.put(items.get(0).getItemNo(), effectiveTime);
					}
					lineNum ++;
				}
			}
			return errors;
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/*@RequestMapping(value = "/do_noFirstNew")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	public Map<String, Object> noFirstNewMethod(HttpServletRequest req, Model model)
			throws ManagerException {
			int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
			int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
			String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
			String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
	
			List list = new ArrayList();
			List<HeadquarterCostMaintain> hList;
			Map<String, Object> obj = new HashMap<String, Object>();
			List<Brand> brands = null;
			Map<String, Object> params = builderParams(req, model);
			String BrandUnitNo = (String) params.get("brandUnitNos");
			String[] BrandUnitNos=null;
			if(BrandUnitNo != null && !BrandUnitNo.equals("")){
				BrandUnitNos=BrandUnitNo.split(",");
			}
			if (null != BrandUnitNos && BrandUnitNos.length > 0) {
				
				Map<String, Object> brandParams = new HashMap<String, Object>();
				brandParams.put("brandUnitNos", Arrays.asList(BrandUnitNos));
				
				brands = this.brandManager.findByBiz(null, brandParams);
			}
			params.put("brands", brands);
			SimplePage page=null;
			int total=0;
			params.put("shopNo", null);
			if(params.get("firstNew1").equals("0")){
				total = this.commonUtilManager.countHQFirstNewNeedRuleMatchItems(params);
				page = new SimplePage(pageNo, (int) total, (int) total);
				
				hList = this.commonUtilManager.queryHQFirstNewNeedRuleMatchItems(page, sortColumn, sortOrder, params);
			}else{
				total = this.commonUtilManager.countHQNeedRuleMatchItems(params);
				page = new SimplePage(pageNo, (int) total, (int) total);
				
				hList = this.commonUtilManager.queryHQNeedRuleMatchItems(page, sortColumn, sortOrder, params);
			}
			for(HeadquarterCostMaintain headCostMaintain:hList){
				this.headquarterCostMaintainManager.batchGenerateHeadquarterCost(headCostMaintain);
				if (headCostMaintain.getMatchResult()) {
					list.add(headCostMaintain);
				}
			}
			obj.put("total", total);
			obj.put("rows", hList);
			return obj;
	}*/
	
	/*@RequestMapping(value = "/add")
	@ResponseBody
	public Boolean add(HeadquarterCostMaintain obj, HttpServletRequest req)throws Exception{
		String insertedRows = req.getParameter("insertedRows");
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
		List<Object> insertedList = convertListWithTypeReference(insertedRows, HeadquarterCostMaintain.class);
		List<HeadquarterCostMaintain> batchInserts = new ArrayList<HeadquarterCostMaintain>();
		for (int i = 0; i < insertedList.size(); i++) {
			HeadquarterCostMaintain headCostMaintain = (HeadquarterCostMaintain)insertedList.get(i);
			headCostMaintain.setCreateUser(loginUser.getUsername());
			headCostMaintain.setCreateTime(DateUtil.getCurrentDateTime());
			batchInserts.add(headCostMaintain);
		}
		try{
			new Thread(new HQRuleMatchHandleThread(this.headquarterCostMaintainManager,batchInserts)).start();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}*/
	
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
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Object> convertListWithTypeReference(String rows, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(rows, new TypeReference<List<Map>>() {});
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}*/
}