package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import cn.wonhigh.retail.fas.common.dto.ShopGroupDto;
import cn.wonhigh.retail.fas.common.model.Bsgroups;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.Customer;
import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSet;
import cn.wonhigh.retail.fas.common.model.Mall;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopGroup;
import cn.wonhigh.retail.fas.common.model.ShopGroupDtl;
import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.BsgroupsManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.CustomerManager;
import cn.wonhigh.retail.fas.manager.InvoiceInfoManager;
import cn.wonhigh.retail.fas.manager.InvoiceTemplateSetManager;
import cn.wonhigh.retail.fas.manager.MallManager;
import cn.wonhigh.retail.fas.manager.ShopGroupDtlManager;
import cn.wonhigh.retail.fas.manager.ShopGroupManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.manager.SupplierManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-22 11:41:25
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
@RequestMapping("/shop_group")
@ModuleVerify("30100015")
public class ShopGroupController extends BaseCrudController<ShopGroup> {
    @Resource
    private ShopGroupManager shopGroupManager;
    
    @Resource
    private ShopGroupDtlManager shopGroupDtlManager;
    
    @Resource
    private ShopManager shopManager;
    
    @Resource
    private InvoiceInfoManager invoiceInfoManager;
    
    @Resource
    private CompanyManager companyManager;
    
    @Resource
    private CustomerManager customerManager;
    
    @Resource
    private MallManager mallManager;
    
    @Resource
    private BsgroupsManager bsgroupsManager;
    
    @Resource
    private SupplierManager supplierManager;
    
    @Resource
    private InvoiceTemplateSetManager invoiceTemplateSetManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("shop_group/",shopGroupManager);
    }
    
    
    @Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		List list ;
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> params = builderParams(req, model);
		String shop=req.getParameter("shopNo");
		if(shop!=null && !shop.equals("")){
			String[] ShopNo = shop.split(",");
			params.put("shopNos", ShopNo);
			params.put("shopNo", null);
		}
		int total = this.shopGroupManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		list = this.shopGroupManager.findByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
    
	/**
	 * 过滤集合中重复值
	 * 
	 * @param items
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void filterList(List<ShopGroupDto> list) {
		List<ShopGroupDto> wis = new ArrayList<ShopGroupDto>();
		for (int i = 0; i < list.size(); i++){ //外循环是循环的次数
			ShopGroupDto shopGroupDto=list.get(i);
			for (int j = list.size() - 1 ; j > i; j--){ //内循环是 外循环一次比较的次数
				ShopGroupDto dto=list.get(j);
				if (shopGroupDto.getShopGroupNo().equals(dto.getShopGroupNo())){
					list.remove(i);
				}
			}
		}
	}
    
	@RequestMapping(value = "/add")
	@ResponseBody
	public ShopGroup add(ShopGroup obj, HttpServletRequest req)throws Exception{
		String insertedRows = req.getParameter("insertedRows");
		ShopGroup shopGroup = new ShopGroup();
		List<Object> insertedList = convertListWithTypeReference(insertedRows, ShopGroupDtl.class);
		Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
		String shopNos="";
		ShopGroupDtl shopGroupDtl = null;
		for (int i = 0; i < insertedList.size(); i++) {
			shopGroupDtl=(ShopGroupDtl)insertedList.get(i);
			uniqueCheckMap.put("companyNo", obj.getCompanyNo());
			uniqueCheckMap.put("shopNo",shopGroupDtl.getShopNo());
			int count = shopGroupManager.findByExportCount(uniqueCheckMap);
			if(count>0){
				shopNos+=shopGroupDtl.getShopName()+",";
			}
		}
		if(shopNos!=""){
			shopNos+="这些店铺已经存在开票规则";
			shopGroup.setShopName(shopNos);
		}else{
			shopGroup=shopGroupManager.add(obj, insertedList);
		}
		return shopGroup;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public ShopGroup update(ShopGroup obj, HttpServletRequest req)throws Exception{
		ShopGroup shopGroup = new ShopGroup();
		String insertedRows = req.getParameter("insertedRows");
		String updatedRows = req.getParameter("updatedRows");
		String deletedRows = req.getParameter("deletedRows");
		List<Object> insertedList = convertListWithTypeReference(insertedRows, ShopGroupDtl.class);
		List<Object> updatedList = convertListWithTypeReference(updatedRows, ShopGroupDtl.class);
		List<Object> deletedList = convertListWithTypeReference(deletedRows, ShopGroupDtl.class);
		shopGroup=shopGroupManager.update(obj, insertedList, updatedList, deletedList);
		return shopGroup;
	}
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Integer del(ShopGroup obj, HttpServletRequest req)throws Exception{
		String checkedRows = req.getParameter("checkedRows");
		if(StringUtils.isNotBlank(checkedRows)){
			List<Object> list = convertListWithTypeReference(checkedRows, ShopGroup.class);
			return shopGroupManager.delete(list);
		}
		return 0;
	}
	
	
	@RequestMapping(value = "/operate")
	@ResponseBody
	public Integer operate(ShopGroup obj, HttpServletRequest req)throws Exception{
		String checkedRows = req.getParameter("checkedRows");
		if(StringUtils.isNotBlank(checkedRows)){
			List<Object> list = convertListWithTypeReference(checkedRows, ShopGroup.class);
			return shopGroupManager.operate(list);
		}
		return 0;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	}
	
	/**
	 * 根据店铺编码获取店铺分组信息
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping(value = "/getShopGroupNo")
	@ResponseBody
	public ShopGroup getShopGroupNo(HttpServletRequest req)throws ManagerException{
		List<ShopGroup> list = new ArrayList<ShopGroup>();
		
		ShopGroup dto = new ShopGroup();
		String shopNo = req.getParameter("shopNo");
		
		if(StringUtils.isEmpty(shopNo)){
			return dto;
		}
		list  = shopGroupManager.getShopGroupNoByShopNo(shopNo);
		if(CollectionUtils.isEmpty(list)){
			return dto;
		}else{
			dto = list.get(0);
		}
		return dto;
	}
	
	@RequestMapping(value = "/toSelectTemplate")
	public String listTemplate() {
		return "shop_group/list_template";
	}
	
	@RequestMapping(value = "/toSelectClient")
	public String listClient() {
		return "shop_group/list_client";
	}
	
	@RequestMapping(value = "/list_shop_group_client.json")
	@ResponseBody
	public Map<String, Object> queryShopGroupClientList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = shopGroupManager.findClientQueryCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<ShopGroup> list = shopGroupManager.findClientQueryByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	@RequestMapping(value = "/get_invoice_name.json")
	@ResponseBody
	public InvoiceInfo queryInvoiceName(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> params = builderParams(req, model);
		params.put("status", 1);
		List<InvoiceInfo> infos = invoiceInfoManager.findByBiz(null, params);
		if (CollectionUtils.isEmpty(infos)) {
			params.put("companyNo", "");
			infos = invoiceInfoManager.findByBiz(null, params);
		}
		if (CollectionUtils.isEmpty(infos)) {
			return new InvoiceInfo();
		}
		return infos.get(0);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Object> convertListWithTypeReference(ObjectMapper mapper, List<Map> list, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		/*String needFilter ="";
		if(req.getParameter("needFilter")!=null){
			needFilter=req.getParameter("needFilter").toString();
		}*/
		Map<String, Object> params = builderParams(req, model);
		String shop=req.getParameter("shopNo");
		if(shop!=null && !shop.equals("")){
			String[] ShopNo = shop.split(",");
			params.put("shopNos", ShopNo);
			params.put("shopNo", null);
		}
		int total = this.shopGroupManager.findByExportCount(params);
		SimplePage page = new SimplePage(1, (int) total, (int) total);
		List<ShopGroupDto> list = this.shopGroupManager.findByExport(page,sortColumn, sortOrder, params);
		/*if(StringUtils.isNotEmpty(needFilter)){
			filterList(list);
		}*/
		ExportUtils.doExport(fileName, exportColumns, list, response);
	}
	
	public List<ShopGroupDto> getShopGroupDtoList(List shopGroupList) throws ManagerException{
		List<ShopGroupDto> returnList = new ArrayList<ShopGroupDto>();
		ShopGroup shopGroup = null;
		Map<String, Object> params = new HashMap<String, Object>();
		String shopGroupNos ="";
		for (int i = 0; i < shopGroupList.size(); i++) {
			shopGroup=(ShopGroup)shopGroupList.get(i);
			shopGroupNos+="'"+shopGroup.getShopGroupNo()+"'";
			if(i!=shopGroupList.size()-1){
				shopGroupNos=shopGroupNos+",";
			}
		}
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		params.put("shopGroupNos", shopGroupNos);
		returnList = shopGroupManager.findByPage(page, "", "", params);
		
		return returnList;
	}
	
	//取出导入数据中店铺编号对应的店铺名称	
	public Map<String, String> findShopName(List<ShopGroup> dataList){
		Map<String, String> dataMap = new HashMap<String, String>();
		for(ShopGroup shopGroup:dataList){
			dataMap.put(shopGroup.getShopGroupName(), shopGroup.getShopGroupNo());
			dataMap.put(shopGroup.getShopNo(), shopGroup.getShopName());
			dataMap.put(shopGroup.getClientNo(), shopGroup.getClientName());
			dataMap.put(shopGroup.getCompanyNo(), shopGroup.getCompanyName());
			dataMap.put(shopGroup.getTemplateNo(), shopGroup.getTemplateName());
			dataMap.put(shopGroup.getClientNo()+shopGroup.getCompanyNo(), shopGroup.getInvoiceName());
		}
		return dataMap;
	}
	
	/**
	 * 验证是否存在重复店铺
	 * 并拆分导入数据源
	 * @param dataList
	 * @param datas
	 * @return
	 */
	public String validateMultipleShop(List<ShopGroup> dataList,List<ShopGroup> datas){
		String errorStr="";
		boolean istrue=false;
		String ShopNos="";
		String ConpanyNo="";
		ShopGroup sg=null;
		//效验是否一个规则有多个门店
		for(int i=0;i<datas.size();i++){
			ShopGroup sp=datas.get(i);
			if(sp.getShopGroupName()==null){
				break;
			}
			if(sp.getShopNo().indexOf(",")>0){
				String[] str=sp.getShopNo().split(",");
				for(int j=0;j<str.length;j++){
					sg=new ShopGroup();
					sg.setClientNo(sp.getClientNo());
					sg.setCompanyNo(sp.getCompanyNo());
					sg.setShopGroupName(sp.getShopGroupName());
					sg.setTemplateNo(sp.getTemplateNo());
					sg.setClientTypeStr(sp.getClientTypeStr());
					sg.setShopNo(str[j]);
					dataList.add(sg);
				}
			}else{
				dataList.add(sp);
			}
		}
		//校验导入数据源中的重复店铺
		for(int i=0;i<dataList.size()-1;i++){
			for(int j=i+1;j<dataList.size();j++){
				if(dataList.get(i).getCompanyNo().equals(dataList.get(j).getCompanyNo()) && dataList.get(i).getShopNo().equals(dataList.get(j).getShopNo())){
					istrue=true;
					ConpanyNo=dataList.get(i).getCompanyNo();
					ShopNos=dataList.get(i).getShopNo();
					break;
				}
			}
			if(istrue){
				break;
			}
		}
		if(istrue){
			errorStr="导入数据源中公司编码为："+ConpanyNo+"有重复的"+ShopNos+"店铺,请检查导入数据源！";
		}
		return errorStr;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, ShopGroup modelType)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	try {
    		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<ShopGroup> entityClass = (Class<ShopGroup>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<ShopGroup> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), ShopGroup.class,entityClass.getSimpleName());
			List<ShopGroup> dataList=new ArrayList<ShopGroup>();
			//验证是否存在重复店铺
			String errorStr=validateMultipleShop(dataList,datas);
    		if(StringUtils.isNotEmpty(errorStr)){
    			mv.addObject("error", errorStr);
    		}else{
    			// 数据校验
        		List<Object> error = getDataValidators(dataList, request);
    			StringBuilder errorBuilder = new StringBuilder();
    			if (null != error && error.size() > 0) {
    				for (int i = 0; i < error.size(); i++) {
    					errorBuilder.append(error.get(i) + "<br/>");
    				}
    			}
    			Byte status = 0;
    			if (errorBuilder.length() == 0) {
    				Map<String, String> dataMap=findShopName(dataList);
    	    		int count = 0;
    	    		int shopcount = 0;
    	    		//一个结算公司规则对应多个店
     	    		for (ShopGroup hqCostMaintain : datas) {
    	    			if(hqCostMaintain.getShopGroupName()==null){
    	    				break;
    	    			}
						if (dataMap.get(hqCostMaintain.getShopGroupName())!=null && dataMap.get(hqCostMaintain.getShopGroupName())!="") {
							//检验店铺是否属于同一个店铺规则
		    				if(hqCostMaintain.getShopNo().indexOf(",")>0){
		    					String[] str=hqCostMaintain.getShopNo().split(",");
		    					for(int j=0;j<str.length;j++){
		    						ShopGroupDtl dtl =new ShopGroupDtl();
		        	    			dtl.setShopNo(str[j]);
		        	    			dtl.setShopName(dataMap.get(str[j]));
		        	    			dtl.setShopGroupNo(dataMap.get(hqCostMaintain.getShopGroupName()));
		        	    			shopGroupDtlManager.add(dtl);
		        	    			shopcount++;
		    					}
		    					continue;
		    				}else{
		    					ShopGroupDtl dtl =new ShopGroupDtl();
								dtl.setShopGroupNo(hqCostMaintain.getShopGroupNo());
	        	    			dtl.setShopNo(hqCostMaintain.getShopNo());
	        	    			dtl.setShopName(hqCostMaintain.getShopName());
	        	    			shopGroupDtlManager.add(dtl);
	        	    			shopcount++;
								continue;
		    				}
						}
    	    			hqCostMaintain.setCreateTime(new Date());
    	    			hqCostMaintain.setCreateUser(loginUser.getUsername());
    	    			hqCostMaintain.setStatus(status); //默认停用状态
    	    			hqCostMaintain.setValueDate(new Date()); //生效日
    	    			hqCostMaintain.setClientName(dataMap.get(hqCostMaintain.getClientNo()));
    	    			hqCostMaintain.setCompanyName(dataMap.get(hqCostMaintain.getCompanyNo()));
    	    			hqCostMaintain.setTemplateName(dataMap.get(hqCostMaintain.getTemplateNo()));
    	    			hqCostMaintain.setInvoiceName(dataMap.get(hqCostMaintain.getClientNo()+hqCostMaintain.getCompanyNo()));
    	    			List<Object> list=new ArrayList<Object>();
    	    			//检验店铺是否属于同一个店铺规则
	    				if(hqCostMaintain.getShopNo().indexOf(",")>0){
	    					String[] str=hqCostMaintain.getShopNo().split(",");
	    					for(int j=0;j<str.length;j++){
	    						ShopGroupDtl dtl =new ShopGroupDtl();
	        	    			dtl.setShopNo(str[j]);
	        	    			dtl.setShopName(dataMap.get(str[j]));
	        	    			list.add(dtl);
	    					}
	    				}else{
	    					ShopGroupDtl dtl =new ShopGroupDtl();
	    	    			dtl.setShopNo(hqCostMaintain.getShopNo());
	    	    			dtl.setShopName(dataMap.get(hqCostMaintain.getShopNo()));
	    	    			list.add(dtl);
	    				}
    					shopGroupManager.add(hqCostMaintain,list);
    	    			count ++;
    	    		}
    				if (count > 0) {
    					mv.addObject("success", "成功导入" + count + "条记录");
    				}else if(shopcount>0){
    					mv.addObject("success", "成功导入" + shopcount + "条记录");
    				} else{
    					mv.addObject("error", "没有要导入的行！");
    				}
    			} else {
    				mv.addObject("error", errorBuilder);
    			}
    		}
			return mv;
	    	
		}catch (NumberFormatException e) {
			mv.addObject("error", "导入数据的开票申请不是数字格式！");
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
	private List<Object> getDataValidators(List<ShopGroup> datas, HttpServletRequest request) throws ManagerException{
		List<Object> errors = new ArrayList<Object>();
			if(datas != null && datas.size() > 0) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
				int lineNum = 1;
				int count = 0;
				List<Company>  companyList = new ArrayList<Company>();
				List<Shop> shopList = null;
				List<InvoiceTemplateSet> invoiceTemplateSet = null;
				List <InvoiceInfo> infoList =null;
				String errorMessage = null;
				for(ShopGroup costMaintain : datas) {
					List<ShopGroup> shopGrpupList = new ArrayList<ShopGroup>();
					List<Company>  companyLists = new ArrayList<Company>();
					List<Customer> customerList = new ArrayList<Customer>();;
					List<Supplier> supplierList = new ArrayList<Supplier>();
					List<Mall> 	   mallList = new ArrayList<Mall>();
					List<Bsgroups> bsgroupList = new ArrayList<Bsgroups>();;
					//判断空数据
					if (StringUtils.isEmpty(costMaintain.getClientNo()) || null == costMaintain.getCompanyNo()) {
						errorMessage = "第" + lineNum + "行数据有空记录";
						errors.add(errorMessage);
						lineNum ++;
						break;
					}
					//判断数据的有效性
					//公司验证
					paramsMap.put("companyNo", costMaintain.getCompanyNo());
					companyList=companyManager.findByBiz(null, paramsMap);
					if (companyList.size()==0) {
						errorMessage = costMaintain.getCompanyNo()+"的公司编码不存在";
						errors.add(errorMessage);
						lineNum ++;
						break;
					}else{
						costMaintain.setCompanyName(companyList.get(0).getName());
					}//客户类型验证
					if(costMaintain.getClientTypeStr().startsWith("公司")){
						paramsMap.put("companyNo", costMaintain.getClientNo());
						paramsMap.put("dataAccess", "0");//客户是公司不加权限
						companyLists = companyManager.findByBiz(null, paramsMap);
						if (companyLists.size()!=0) {
							costMaintain.setClientName(companyLists.get(0).getName());
						}
					}else if(costMaintain.getClientTypeStr().startsWith("客户")){
						//客户验证
						paramsMap.put("customerNo", costMaintain.getClientNo());
						customerList = customerManager.findByBiz(null, paramsMap);
						if (customerList.size()!=0) {
							costMaintain.setClientName(customerList.get(0).getFullName());
						}
					}else if(costMaintain.getClientTypeStr().startsWith("商场")){
						//商场
						paramsMap.clear();
						paramsMap.put("mallNo", costMaintain.getClientNo());
						mallList = mallManager.findByBiz(null, paramsMap);
						if (mallList.size()!=0) {
							costMaintain.setClientName(mallList.get(0).getName());
						}
					}else if(costMaintain.getClientTypeStr().startsWith("商业集团")){
						//商业集团
						paramsMap.clear();
						paramsMap.put("bsgroupsNo", costMaintain.getClientNo());
						bsgroupList = bsgroupsManager.findByBiz(null, paramsMap);
						if (bsgroupList.size()!=0) {
							costMaintain.setClientName(bsgroupList.get(0).getName());
						}
					}else if(costMaintain.getClientTypeStr().startsWith("供应商")){
						//供应商
						paramsMap.clear();
						paramsMap.put("supplierNo", costMaintain.getClientNo());
						supplierList = supplierManager.findByBiz(null, paramsMap);
						if (supplierList.size()!=0) {
							costMaintain.setClientName(supplierList.get(0).getFullName());
						}
					}
					if(companyLists.size()<1 && supplierList.size()<1 &&	bsgroupList.size()<1 &&	customerList.size()<1 &&
							mallList.size()<1){
						errorMessage = "店铺分组名称:"+costMaintain.getShopGroupName()+"这行数据客户编码为:"+costMaintain.getClientNo()+"不存在";
						errors.add(errorMessage);
						lineNum ++;
						break;
					}
					//店铺规则名称
					paramsMap.clear();
					paramsMap.put("shopGroupNameUnique", costMaintain.getShopGroupName());
					paramsMap.put("templateNo", costMaintain.getTemplateNo());
					paramsMap.put("companyNo", costMaintain.getCompanyNo());
					paramsMap.put("clientNo", costMaintain.getClientNo());
					shopGrpupList=shopGroupManager.findByBiz(null, paramsMap);
					if(shopGrpupList.size()>0){
						costMaintain.setShopGroupNo(shopGrpupList.get(0).getShopGroupNo());
					}
					//根据客户与公司取开票名称
					paramsMap.clear();
					paramsMap.put("status", 1);
					paramsMap.put("companyNo", costMaintain.getCompanyNo());
					paramsMap.put("clientNo", costMaintain.getClientNo());
					infoList = invoiceInfoManager.findByBiz(null, paramsMap);
					if (infoList.size()==0) {
						errorMessage = "店铺分组名称:"+costMaintain.getShopGroupName()+" 这行数据公司编码为:"+costMaintain.getCompanyNo()+"、客户编码为:"+costMaintain.getClientNo()+"没有开票信息维护";
						errors.add(errorMessage);
						lineNum ++;
						break;
					}else{
						costMaintain.setInvoiceName(infoList.get(0).getInvoiceName());
					}
					//发票模板验证
					paramsMap.clear();
					paramsMap.put("companyNo", costMaintain.getCompanyNo());
					paramsMap.put("invoiceTempNo", costMaintain.getTemplateNo());
					invoiceTemplateSet = invoiceTemplateSetManager.findByBiz(null, paramsMap);
					if (invoiceTemplateSet.size()==0) {
						errorMessage = "店铺分组名称:"+costMaintain.getShopGroupName()+" 这行数据的发票模板编码为"+costMaintain.getTemplateNo()+"不存在";
						errors.add(errorMessage);
						lineNum ++;
						break;
					}else{
						costMaintain.setTemplateName(invoiceTemplateSet.get(0).getName());
					}
					//店铺验证
					paramsMap.clear();
					paramsMap.put("shopNo", costMaintain.getShopNo());
					shopList = shopManager.findByBiz(null, paramsMap);
					if (shopList.size()==0) {
						errorMessage = costMaintain.getShopNo()+"行数据的店铺编码不存在";
						errors.add(errorMessage);
						lineNum ++;
						break;
					}else{
						costMaintain.setShopName(shopList.get(0).getFullName());
					}
					//数据唯一性校验同一家公司同 一家店只对应一个开票规则
					uniqueCheckMap.put("shopNo", costMaintain.getShopNo());
					uniqueCheckMap.put("companyNo", costMaintain.getCompanyNo());
					//uniqueCheckMap.put("templateNo", costMaintain.getTemplateNo());
					//uniqueCheckMap.put("shopGroupNameUnique", costMaintain.getShopGroupName());
					count =	shopGroupManager.findByExportCount(uniqueCheckMap);
					if (count > 0) {
						errorMessage ="公司编码为："+costMaintain.getCompanyNo()+"的开票规则已经拥有店铺编码为: "+costMaintain.getShopNo() + "的店铺!";
						errors.add(errorMessage);
						lineNum ++;
						break;
					}
					lineNum ++;
				}
			}
			
		return errors;
	}
}