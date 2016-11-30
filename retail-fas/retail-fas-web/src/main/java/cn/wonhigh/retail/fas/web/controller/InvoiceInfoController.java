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

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.ClientTypeEnums;
import cn.wonhigh.retail.fas.common.model.Bsgroups;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.Customer;
import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.common.model.Mall;
import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.BsgroupsManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.CustomerManager;
import cn.wonhigh.retail.fas.manager.InvoiceInfoManager;
import cn.wonhigh.retail.fas.manager.MallManager;
import cn.wonhigh.retail.fas.manager.SupplierManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
/**
 * 开票信息表维护
 * @author ouyang.zm
 * @date  2015-02-03 11:06:43
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
@RequestMapping("/base_setting/invoice_info_set")
@ModuleVerify("30100013")
public class InvoiceInfoController extends BaseController<InvoiceInfo> {
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

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/invoice_info/",invoiceInfoManager);
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
	 * 保存
	 */
    @RequestMapping({"/save"})
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity<Map<String, Boolean>> save(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException, ManagerException {
    	  String deletedList = (StringUtils.isEmpty(req.getParameter("deleted"))) ? "" : req.getParameter("deleted");
    	  String upadtedList = (StringUtils.isEmpty(req.getParameter("updated"))) ? "" : req.getParameter("updated");
    	  String insertedList = (StringUtils.isEmpty(req.getParameter("inserted"))) ? "" : req.getParameter("inserted");
    	  SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
	      List oList;
	      Map flag = new HashMap();
	      Map params = new HashMap();
	      InvoiceInfo invoiceInfo=null;
		  String errorStr = "";
		  String clientNo = "";
		  String ero = "保存失败!<br/>";
	      Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
	      //删除之后备选的改为首选
	      if (StringUtils.isNotEmpty(deletedList)) {
	    	  oList = convertListWithTypeReference(deletedList, InvoiceInfo.class);
			  List<InvoiceInfo> invoiceInfoLists = oList;
	    	  for (int i = 0; i < invoiceInfoLists.size(); i++) {
	    		  	if(invoiceInfoLists.get(i).getStatus()==1){
	    		  		uniqueCheckMap.put("companyNo",invoiceInfoLists.get(i).getCompanyNo());
						uniqueCheckMap.put("clientNo",invoiceInfoLists.get(i).getClientNo());
						uniqueCheckMap.put("status","0");
						List iInfoList = invoiceInfoManager.findByBiz(InvoiceInfo.class, uniqueCheckMap);
						if (iInfoList.size() > 0) {
							InvoiceInfo invo = (InvoiceInfo) iInfoList.get(0);
							invo.setStatus(1);
							List uList = new ArrayList();
							uList.add(invo);
							params.put(CommonOperatorEnum.UPDATED, uList);
							invoiceInfoManager.save(params);
						}
	    		  	}
	    	  }
	    	  params.clear();
	    	  params.put(CommonOperatorEnum.DELETED, oList);
	      }
	      if (StringUtils.isNotEmpty(upadtedList) && upadtedList.length()>2) {
	    	  List<Object> invoiceInfoList = convertListWithTypeReference(upadtedList, InvoiceInfo.class);
	    	  errorStr = validateCompanyNoOrClientNo(invoiceInfoList);
			  if (StringUtils.isNotEmpty(errorStr)) {
				 clientNo += errorStr;
			  } else {
		    	  for (int i = 0; i < invoiceInfoList.size(); i++) {
					invoiceInfo = (InvoiceInfo) invoiceInfoList.get(i);
					((InvoiceInfo) invoiceInfoList.get(i)).setUpdateUser(loginUser.getUsername());
					((InvoiceInfo) invoiceInfoList.get(i)).setUpdateTime(new Date());
					uniqueCheckMap.put("companyNo", invoiceInfo.getCompanyNo());
					uniqueCheckMap.put("clientNo", invoiceInfo.getClientNo());
					int count1 = invoiceInfoManager.findCount(uniqueCheckMap);
					if(count1==1){
						if(invoiceInfo.getStatus()==0){
							clientNo+="公司编码："+invoiceInfo.getCompanyNo()+"与客户编码："+invoiceInfo.getClientNo()+"的数据只有一条不能改为备选。<br/>";
						}
					}else{
						if(invoiceInfo.getStatus()!=0){
				  			    //数据唯一性校验,首选状态的只允许有一条
								uniqueCheckMap.put("status","1");
								List<InvoiceInfo> invoiceCount =invoiceInfoManager.findByBiz(null,uniqueCheckMap);
								if (invoiceCount.size() > 0) {
									if(invoiceCount.get(0).getId().equals(invoiceInfo.getId())){
										
									}else{
										clientNo+="已存在公司编码："+invoiceInfo.getCompanyNo()+"与客户编码："+invoiceInfo.getClientNo()+"被首选的数据。<br/>";
										continue;
									}
								}
				  		}
					}
					
		    	  }
	        }
			if (clientNo != "") {
				clientNo = ero + clientNo;
				flag.put("message", clientNo);
			} else {
				oList = invoiceInfoList;
				params.put(CommonOperatorEnum.UPDATED, oList);
			}
	      }
	      if (StringUtils.isNotEmpty(insertedList) && insertedList.length()>2) {
			clientNo = "";
			List<Object> invoiceInfoList = convertListWithTypeReference(insertedList, InvoiceInfo.class);
			errorStr = validateCompanyNoOrClientNo(invoiceInfoList);
			if (StringUtils.isNotEmpty(errorStr)) {
				clientNo += errorStr;
			} else {
	    		  for (int i = 0; i < invoiceInfoList.size(); i++) {
					invoiceInfo = (InvoiceInfo) invoiceInfoList.get(i);
					((InvoiceInfo) invoiceInfoList.get(i)).setCreateUser(loginUser.getUsername());
					((InvoiceInfo) invoiceInfoList.get(i)).setCreateTime(new Date());
	  	  			//数据唯一性校验 首选状态的单只允许有一条
	  	  			if(invoiceInfo.getStatus()!=0){
	  	  				uniqueCheckMap.put("companyNo",invoiceInfo.getCompanyNo());
	  					uniqueCheckMap.put("clientNo",invoiceInfo.getClientNo());
	  					uniqueCheckMap.put("status","1");
	  					int count =	invoiceInfoManager.findCount(uniqueCheckMap);
	  					if (count > 0) {
	  						clientNo+="已存在公司编码："+invoiceInfo.getCompanyNo()+"与客户编码："+invoiceInfo.getClientNo()+"被首选的数据。<br/>";
	  						continue;
	  					}
	  	  			}
	  	    	  }
	    	  }
			if (clientNo != "") {
				clientNo = ero + clientNo;
				flag.put("message", clientNo);
			} else {
				oList = invoiceInfoList;
				params.put(CommonOperatorEnum.INSERTED, oList);
			}
	      }
		if (params.size() > 0) {
			invoiceInfoManager.save(params);
		}
		flag.put("success", Boolean.valueOf(true));
		return new ResponseEntity(flag, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/getInvoiceInfoByClientType")
	@ResponseBody
	public  Map<String, Object> getInvoiceInfoByClientType(HttpServletRequest req, Model model) throws ManagerException {
    	int total = 0;
		SimplePage page = null;
		List<InvoiceInfo> list = null;
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		total = invoiceInfoManager.selectDistinctClientCount(params);
		page = new SimplePage(pageNo, pageSize, total);
		list = invoiceInfoManager.selectDistinctClientByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("rows", list);
		obj.put("total", total);
		return obj;
    }
    
    /**
	 * 客户类型
	 * @return
	 */
	@RequestMapping(value = "/getClientType")
	@ResponseBody
	public List<Map<String, String>> getTransferOutBillStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		ClientTypeEnums[] enumData = ClientTypeEnums.values();
		for (ClientTypeEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("typeNo", x.getTypeNo().toString());
			map.put("typeName", x.getTypeName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 组装导入数据源开票状态
	 * @param datas
	 * @return
	 */
	public void validateMultipleShop(List<InvoiceInfo> datas){
		//校验导入数据源中的是否存在多条相同公司和客户的数据
		for(int i=0;i<datas.size()-1;i++){
			for(int j=i+1;j<datas.size();j++){
				if(datas.get(i).getCompanyNo()!=null && datas.get(j).getCompanyNo()!=null){
					if(datas.get(i).getCompanyNo().equals(datas.get(j).getCompanyNo())
							&& datas.get(i).getClientNo().equals(datas.get(j).getClientNo())){
						if(datas.get(i).getStatus()==null){
							datas.get(i).setStatus(1);
							datas.get(j).setStatus(0);
						}else{
							datas.get(j).setStatus(0);
							break;
						}
					}
				}
			}
		}
		for(int i=0;i<datas.size();i++){
			if(datas.get(i).getStatus()!=null&&datas.get(i).getStatus()==0){
				continue;
			}else{
				datas.get(i).setStatus(1);
			}
		}
	
	}
	
	/**
	 * 导入
	 * @param request
	 * @param modelType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, InvoiceInfo modelType)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	try {
    		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<InvoiceInfo> entityClass = (Class<InvoiceInfo>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<InvoiceInfo> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), InvoiceInfo.class,entityClass.getSimpleName());
			//验证导入数据源中是否存在多条相同公司和客户的数据
			validateMultipleShop(datas);
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
	    		for (InvoiceInfo hqCostMaintain : datas) {
	    			hqCostMaintain.setCreateTime(new Date());
	    			hqCostMaintain.setCreateUser(loginUser.getUsername());
	    			//hqCostMaintain.setStatus(1); //默认首选状态
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
	private List<Object> getDataValidators(List<InvoiceInfo> datas, HttpServletRequest request) throws ManagerException{
		List<Object> errors = new ArrayList<Object>();
		try {
				if(datas != null && datas.size() > 0) {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
					int lineNum = 2;
					int count = 0;
					List<Company>  companyList = null;
					List<Supplier> supplierList = null;
					List<Mall> 	   mallList = null;
					List<Bsgroups> bsgroupList = null;
					List<Customer> customerList = null;
					String errorMessage = null;
					for(InvoiceInfo costMaintain : datas) {
						//判断空数据
						if (StringUtils.isEmpty(costMaintain.getClientNo()) || null == costMaintain.getCompanyNo()
								|| null == costMaintain.getClientTypeStr()) {
							errorMessage = "第" + lineNum + "行数据有空记录";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}
						//判断数据的有效性
						//发票类型验证
						if(!costMaintain.getInvoiceTypeStr().startsWith("普通发票") && !costMaintain.getInvoiceTypeStr().startsWith("增值票") ){
							errorMessage = "第" + lineNum + "行数据的发票类型不存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}else if(costMaintain.getInvoiceTypeStr().startsWith("普通发票")){
							costMaintain.setInvoiceType(new Byte("0"));
						}else if(costMaintain.getInvoiceTypeStr().startsWith("增值票")){
							costMaintain.setInvoiceType(new Byte("1"));
						}
						paramsMap.put("companyNo", costMaintain.getCompanyNo());
						companyList=companyManager.findByBiz(null, paramsMap);
						if (companyList.size()==0) {
							errorMessage = "第" + lineNum + "行数据的公司编码不存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}else{
							costMaintain.setCompanyName(companyList.get(0).getName());
						}
						//客户类型验证
						if(costMaintain.getClientTypeStr().startsWith("公司")){
							paramsMap.put("companyNo", costMaintain.getClientNo());
							paramsMap.put("dataAccess", "0");//客户是公司不加权限
							companyList = companyManager.findByBiz(null, paramsMap);
							if (companyList.size()==0) {
								errorMessage = "第" + lineNum + "行数据的公司编码不存在";
								errors.add(errorMessage);
								lineNum ++;
								continue;
							}else{
								costMaintain.setClientName(companyList.get(0).getName());
							}
						}else if(costMaintain.getClientTypeStr().startsWith("客户")){
							paramsMap.put("customerNo", costMaintain.getClientNo());
							customerList = customerManager.findByBiz(null, paramsMap);
							if (customerList.size()==0) {
								errorMessage = "第" + lineNum + "行数据的客户编码不存在";
								errors.add(errorMessage);
								lineNum ++;
								continue;
							}else{
								costMaintain.setClientName(customerList.get(0).getFullName());
								costMaintain.setClientShortName(customerList.get(0).getShortName());
							}
						}else if(costMaintain.getClientTypeStr().startsWith("商场")){
							paramsMap.clear();
							paramsMap.put("mallNo", costMaintain.getClientNo());
							mallList = mallManager.findByBiz(null, paramsMap);
							if (mallList.size()==0) {
								errorMessage = "第" + lineNum + "行数据的商场编码不存在";
								errors.add(errorMessage);
								lineNum ++;
								continue;
							}else{
								costMaintain.setClientName(mallList.get(0).getName());
							}
						}else if(costMaintain.getClientTypeStr().startsWith("商业集团")){
							paramsMap.clear();
							paramsMap.put("bsgroupsNo", costMaintain.getClientNo());
							bsgroupList = bsgroupsManager.findByBiz(null, paramsMap);
							if (bsgroupList.size()==0) {
								errorMessage = "第" + lineNum + "行数据的商业集团编码不存在";
								errors.add(errorMessage);
								lineNum ++;
								continue;
							}else{
								costMaintain.setClientName(bsgroupList.get(0).getName());
							}
						}else if(costMaintain.getClientTypeStr().startsWith("供应商")){
							paramsMap.clear();
							paramsMap.put("supplierNo", costMaintain.getClientNo());
							supplierList = supplierManager.findByBiz(null, paramsMap);
							if (supplierList.size()==0) {
								errorMessage = "第" + lineNum + "行数据的供应商编码不存在";
								errors.add(errorMessage);
								lineNum ++;
								continue;
							}else{
								costMaintain.setClientName(supplierList.get(0).getFullName());
								costMaintain.setClientShortName(supplierList.get(0).getShortName());
							}
						}else{
							errorMessage = "第" + lineNum + "客户类型不存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}
						//判断数据是不是首选状态
						if(costMaintain.getStatus()==1){
							//数据唯一性校验 首选状态的单只允许有一条
							uniqueCheckMap.put("companyNo",costMaintain.getCompanyNo());
							uniqueCheckMap.put("clientNo",costMaintain.getClientNo());
							uniqueCheckMap.put("status","1");
							count =	invoiceInfoManager.findCount(uniqueCheckMap);
							if (count > 0) {
								costMaintain.setStatus(0);
							}
						}
						lineNum ++;
					}
				}
			} catch (ManagerException e) {
				logger.error(e.getMessage(), e);
				throw new ManagerException(e.getMessage(), e);
			}
			return errors;
		}
	
	//判断保存数据中是否存在相同数据
	public String validateCompanyNoOrClientNo(List<Object> invoiceInfoList){
		boolean istrue=false;
		String errorStr="";
		for(int i=0;i< invoiceInfoList.size()-1;i++){
			InvoiceInfo invoiceInfoOne=(InvoiceInfo) invoiceInfoList.get(i);
			for(int j=i+1;j<invoiceInfoList.size();j++){
				InvoiceInfo invoiceInfoTwo=(InvoiceInfo) invoiceInfoList.get(j);
				if(invoiceInfoOne.getCompanyNo().equals(invoiceInfoTwo.getCompanyNo())
						&&invoiceInfoOne.getClientNo().equals(invoiceInfoTwo.getClientNo())
						&&invoiceInfoOne.getStatus()==invoiceInfoTwo.getStatus()
						&&invoiceInfoOne.getStatus()==1){
						istrue=true;
						break;
				}
			}
			if(istrue){
				break;
			}
		}
		if(istrue){
			errorStr="保存数据中不能存在公司与客户相同的多条数据。";
		}
		return errorStr;
	}
	
	/**
	 * 首选
	 * @param request
	 * @return
	 * @throws ManagerException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@RequestMapping("/do_enableone")
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity doEnable1(HttpServletRequest request) throws ManagerException, JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" 
				: request.getParameter("deleted");
		Map flag = new HashMap();
		List<Object> invoiceInfoList = convertListWithTypeReference(idList, InvoiceInfo.class);
		Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
		boolean istrue=false;
		for(int i=0;i< invoiceInfoList.size()-1;i++){
			InvoiceInfo invoiceInfoOne=(InvoiceInfo) invoiceInfoList.get(i);
			for(int j=i+1;j<invoiceInfoList.size();j++){
				InvoiceInfo invoiceInfoTwo=(InvoiceInfo) invoiceInfoList.get(j);
				if(invoiceInfoOne.getCompanyNo().equals(invoiceInfoTwo.getCompanyNo())
						&&invoiceInfoOne.getClientNo().equals(invoiceInfoTwo.getClientNo())){
						istrue=true;
						break;
				}
			}
			if(istrue){
				break;
			}
		}
		if(istrue){
			flag.put("message", "首选数据中不能存在公司与客户相同的数据。");
		}else{
			String clientNo="";
			String ero="首选失败!<br/>";
			for(Object invoiceInfo:invoiceInfoList){
				uniqueCheckMap.put("companyNo",((InvoiceInfo) invoiceInfo).getCompanyNo());
				uniqueCheckMap.put("clientNo",((InvoiceInfo) invoiceInfo).getClientNo());
				uniqueCheckMap.put("status","1");
				int count =	invoiceInfoManager.findCount(uniqueCheckMap);
				if (count > 0) {
					clientNo+="已存在公司编码："+((InvoiceInfo) invoiceInfo).getCompanyNo()+"与客户编码："+((InvoiceInfo) invoiceInfo).getClientNo()+"被首选的数据。<br/>";
					continue;
				}
			}
			if(clientNo!=""){
				clientNo=ero+clientNo;
				flag.put("message", clientNo);
			}else{
				super.doEnable(request);
			}
		}
		flag.put("success", Boolean.valueOf(true));
	    return new ResponseEntity(flag, HttpStatus.OK);
	}

	/**
	 * 备选
	 * @param request
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 * @throws ManagerException
	 */
	@RequestMapping("/do_unableone")
	@ResponseBody
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity doUnable1(HttpServletRequest request) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException, ManagerException {
		String idList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" 
				: request.getParameter("deleted");
		Map flag = new HashMap();
		List<Object> invoiceInfoList = convertListWithTypeReference(idList, InvoiceInfo.class);
		Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
		boolean istrue=false;
		String clientNo="";
		String ero="备选失败!<br/>";
		 for (int i = 0; i < invoiceInfoList.size(); i++) {
	  			InvoiceInfo invoiceInfo=(InvoiceInfo)invoiceInfoList.get(i);
	  			uniqueCheckMap.put("companyNo",invoiceInfo.getCompanyNo());
				uniqueCheckMap.put("clientNo",invoiceInfo.getClientNo());
				int count =	invoiceInfoManager.findCount(uniqueCheckMap);
				if(count==1){
					clientNo+="公司编码："+invoiceInfo.getCompanyNo()+"与客户编码："+invoiceInfo.getClientNo()+"的数据只有一条不能改为备选。<br/>";
					istrue=true;
				}
		  }
		if(clientNo!=""){
			clientNo=ero+clientNo;
			flag.put("message", clientNo);
		}else{
			super.doUnable(request);
		}
		flag.put("success", Boolean.valueOf(true));
	    return new ResponseEntity(flag, HttpStatus.OK);
	}

}