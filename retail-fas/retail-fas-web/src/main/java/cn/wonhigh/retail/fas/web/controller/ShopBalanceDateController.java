package cn.wonhigh.retail.fas.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.ShopBalanceDateManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-15 14:29:23
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
@RequestMapping("/shop_balance_date")
@ModuleVerify("3140000")
public class ShopBalanceDateController extends ExcelImportController<ShopBalanceDate> {
    @Resource
    private ShopBalanceDateManager shopBalanceDateManager;
    
    @Resource
    private ShopManager shopManager;

    private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShopBalanceDateController.class);
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("shop_balance_date/",shopBalanceDateManager);
    }
    
    @RequestMapping("/list")
	public String list() {
		return "mallshop_balance/shop_balance_date";
	}
    
    @RequestMapping("/warn_shop_show")
	public String warnShopShow() {
		return "mallshop_balance/warn_shop_show";
	}
    
    @RequestMapping(method = RequestMethod.GET ,value = "/warn_no_set_shopBalanceDate")
	public ModelAndView warnNoSetShopBalanceDate(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		mav.setViewName("mallshop_balance/warn_no_set_shopBalanceDate");
		return mav;
	}
    
    @RequestMapping(method = RequestMethod.GET ,value = "/warn_shopBalanceData_part_right")
	public ModelAndView warnShopBalanceDataPartRight(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		String warnPostUrl = req.getParameter("warnPostUrl");
		if(StringUtils.isNotBlank(warnPostUrl)){
			mav.addObject("warnPostUrl", warnPostUrl);
		}
		mav.setViewName("mallshop_balance/warn_shopBalanceData_part_right");
		return mav;
	}
    
    @RequestMapping(value = "/initSubInfo")
  	public ResponseEntity<ShopBalanceDate> initSubsiInfo(HttpServletRequest request) throws ManagerException{
      	String shopNo = request.getParameter("shopNo");
  		String month = request.getParameter("month");
  		String balanceType = request.getParameter("balanceType");
  		
  		Map<String, Object> params = new HashMap<String, Object>();
  		params.put("shopNo", shopNo);
  		params.put("month", month);
//		否生成结算单(0-已生成预估  1-未生成，2-已生成)
		if (balanceType.equals("1")) {  //正式    !2 否生成结算单
			params.put("notEqualBalanceFlag", 2);// 未结算    不等于2 已结算  不等于0 已生成预估 
		}else if(balanceType.equals("2")) {  //预估
			params.put("balanceFlag", 1);  // 1  2 
		}
		
		List<ShopBalanceDate> shopBalanceDateList=new ArrayList<ShopBalanceDate>();
		try {
			shopBalanceDateList = shopBalanceDateManager.findByBiz(null, params);
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		if(CollectionUtils.isEmpty(shopBalanceDateList)) {
			return null;
		}
		ShopBalanceDate shopBalanceDate = shopBalanceDateList.get(0);
//		shopBalanceDate.setBalanceStartDate(shopBalanceDateList.get(0).getBalanceStartDate());
//		shopBalanceDate.setBalanceEndDate(shopBalanceDateList.get(0).getBalanceEndDate());
		
  		return new ResponseEntity<ShopBalanceDate>(shopBalanceDate, HttpStatus.OK);
  	}
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		String onlyNewShop = req.getParameter("isNewShop");
		
//		String yearMonth = req.getParameter("month");
//		String yearMonthDate = yearMonth + "01";
//		Date monthFirstDay = DateUtil.parseToDate(yearMonthDate, "yyyyMMdd");
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(monthFirstDay);
//		cal.add(Calendar.MONTH, -1);
//		String lastYearMonth = DateUtil.format(cal.getTime(), "yyyyMM");
		
		Map<String, Object> params = builderParams(req, model);
		params.put("multiCompanyNo", FasUtil.formatInQueryCondition(req.getParameter("multiCompanyNo")));
		params.put("multiBsgroupsNo", FasUtil.formatInQueryCondition(req.getParameter("multiBsgroupsNo")));
		params.put("multiMallNo", FasUtil.formatInQueryCondition(req.getParameter("multiMallNo")));
		String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
		if (StringUtils.isNotEmpty(shopNos)) {
			params.put("shopNos",Arrays.asList(shopNos.split(",")) );
		}
		int total = 0;
		SimplePage page = null;
		List<ShopBalanceDate> list = new ArrayList<ShopBalanceDate>();
		
		if ("true".equals(onlyNewShop)) {
//			params.put("lastMonth",lastYearMonth);
			total = this.shopBalanceDateManager.findNewShopDateCount(params);
			page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.shopBalanceDateManager.findNewShopDateByPage(page, sortColumn, sortOrder, params);
		}else {
			total = this.shopBalanceDateManager.findCount(params);
			page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.shopBalanceDateManager.findByPage(page, sortColumn, sortOrder, params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
    
    @Override
    protected List<ShopBalanceDate> queryExportData(Map<String, Object> params) throws ManagerException {
    	int total = 0;
    	SimplePage page = null;
    	List<ShopBalanceDate> list = new ArrayList<ShopBalanceDate>();
    	
    	String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		String isNewShop = params.get("isNewShop").toString();
		
		String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
		String multiCompanyNo = params.get("multiCompanyNo") == null ? null : params.get("multiCompanyNo").toString();
		String multiBsgroupsNo = params.get("multiBsgroupsNo") == null ? null : params.get("multiBsgroupsNo").toString();
		String multiMallNo = params.get("multiMallNo") == null ? null : params.get("multiMallNo").toString();
		params.put("multiCompanyNo", FasUtil.formatInQueryCondition(multiCompanyNo));
		params.put("multiBsgroupsNo", FasUtil.formatInQueryCondition(multiBsgroupsNo));
		params.put("multiMallNo", FasUtil.formatInQueryCondition(multiMallNo)); 
		if (StringUtils.isNotEmpty(shopNos)) {
			params.put("shopNos",Arrays.asList(shopNos.split(",")) );
		}
		if ("true".equals(isNewShop)) {
			total = this.shopBalanceDateManager.findNewShopDateCount(params);
			page = new SimplePage(1, total, (int) total);
			list = this.shopBalanceDateManager.findNewShopDateByPage(page, orderByField, orderBy, params);
		}else {
			total = this.shopBalanceDateManager.findCount(params);
			page = new SimplePage(1, total, (int) total);
			list = this.shopBalanceDateManager.findByPage(page, orderByField, orderBy, params);
		}
		return list;
	}
    
	@RequestMapping("/validationRepeat")
	@ResponseBody
	public ResponseEntity<Map<String, String>> validationRepeat(HttpServletRequest req) throws Exception{
		
		String deletedList = StringUtils.isEmpty(req.getParameter("deleted")) ? "" : req.getParameter("deleted");
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		
		Map<String,String> respInfo = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			if(StringUtils.isNotEmpty(deletedList)) {
				List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>() {});
				for(Map map : list){
					ShopBalanceDate shopBalanceDate = mapper.readValue(mapper.writeValueAsString(map), ShopBalanceDate.class);
					shopBalanceDateManager.deleteById(shopBalanceDate);
				}
			}
			if(StringUtils.isNotEmpty(upadtedList)) {
				List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {});
				
				for(Map map : list){
					ShopBalanceDate shopBalanceDate = mapper.readValue(mapper.writeValueAsString(map), ShopBalanceDate.class);
					setDefaulValues(shopBalanceDate,req);
					Map<String,String> _errorInfo = shopBalanceDateManager.validationShopBalanceDate(shopBalanceDate);
					if(_errorInfo.get("error") != null){
						respInfo.put("error", _errorInfo.get("error") + "<br />" );
						continue;
					}
					shopBalanceDateManager.modifyById(shopBalanceDate);
				}
				
			}
			if(StringUtils.isNotEmpty(insertedList)) {
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {});
				for(Map map : list){
					ShopBalanceDate shopBalanceDate = mapper.readValue(mapper.writeValueAsString(map), ShopBalanceDate.class);
					setDefaulValues(shopBalanceDate,req);
					Map<String,String> _errorInfo = shopBalanceDateManager.validationShopBalanceDate(shopBalanceDate);
					if(_errorInfo.get("error") != null){
						respInfo.put("error", _errorInfo.get("error") + "<br />" );
						continue;
					}
					shopBalanceDateManager.add(shopBalanceDate);
				}
				
			}
		} 
//		catch (JsonParseException e) {
//			LOGGER.error(e.getMessage(),e);
//		} catch (JsonMappingException e) {
//			LOGGER.error(e.getMessage(),e);
//		} catch (IOException e) {
//			LOGGER.error(e.getMessage(),e);
//		} catch (ManagerException e) {
//			LOGGER.error(e.getMessage(),e);
//		} 
		catch (Exception e){
			LOGGER.error(e.getMessage(),e);
		}
		return new ResponseEntity<Map<String, String>>(respInfo, HttpStatus.OK);
	}
	
	/**
	 * 批量生成结算期
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/generateBalanceDate")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> batchGenerateBalanceDate(HttpServletRequest req,Model model) throws ManagerException{
		Map<String,Object> obj = new HashMap<String, Object>();
		obj.put("success", true);
		try {
			SystemUser loginUser = CurrentUser.getCurrentUser(req);
			Map<String,Object> params = builderParams(req, model);
			
			DateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
			DateFormat monthFormat = new SimpleDateFormat("yyyyMM");
			
			String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
			String companyName = params.get("companyName") == null ? null : params.get("companyName").toString();
			String currentMonth = params.get("month") == null ? null : params.get("month").toString();
			String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
			String cityNos = params.get("cityNos") == null ? null : params.get("cityNos").toString();
			String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
			String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
			
			List<Shop> shopList = null;
			if (StringUtils.isNotEmpty(shopNos)) {
				//选择了某些店铺，就只对指定店铺进行生成
				Map<String, Object> shopsMap = new HashMap<String, Object>();
				shopsMap.put("shopNos", Arrays.asList(shopNos.split(",")));
				shopList = shopManager.findByBiz(null, shopsMap);
			}else {
				//根据选择的条件，查询出符合条件的店铺进行生成结算期
				Map<String, Object> shopParamMap = new HashMap<String, Object>();
				shopParamMap.put("companyNo", companyNo);
//				shopParamMap.put("status", 1);
				shopParamMap.put("shopStatus", 1);  //生成正常店铺及当前日期起撤柜2个月的店铺
				
				if(StringUtils.isNotEmpty(cityNos)){  //经营城市
					shopParamMap.put("organNos",  FasUtil.formatInQueryCondition(cityNos));
				}
				if(StringUtils.isNotEmpty(organNos)){  //管理城市
					shopParamMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNos));
				}
				if(StringUtils.isNotEmpty(brandNos)){
					//过滤经营主品牌的店铺
					shopParamMap.put("mainBrandNos",  FasUtil.formatInQueryCondition(brandNos));	
				}
				shopList = shopManager.findByBiz(null, shopParamMap);
			}
			
			if (CollectionUtils.isEmpty(shopList)) {
				obj.put("success", false);
				obj.put("errorInfo", "生成失败,没有找到任何店铺");
				return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
			}
			
			//生成月份的结算期
			String dateFirst = currentMonth + "01";
			Date generateDate = dayFormat.parse(dateFirst);
			
			GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
			calendar.setTime(generateDate);
			calendar.add(Calendar.MONTH, -1);
			//上月份的结算期
			String lastMonth = monthFormat.format(calendar.getTime());
			//当前月份是否有结算期
			Map<String, Object> currBalanceDateMap = new HashMap<String, Object>();
			currBalanceDateMap.put("month", currentMonth);
			
			for(Shop shop : shopList) {
				currBalanceDateMap.put("shopNo", shop.getShopNo());
				int count = shopBalanceDateManager
						.findCount(currBalanceDateMap);
				//如果本月已有结算期，跳过本店铺的结算期生成
				if (count > 0) {
					continue;
				}
				//设置结算期基本信息
				ShopBalanceDate shopBalanceDate = new ShopBalanceDate();
				shopBalanceDate.setShopNo(shop.getShopNo());
				shopBalanceDate.setShortName(shop.getShortName());
				shopBalanceDate.setCompanyNo(companyNo);
				shopBalanceDate.setCompanyName(companyName);
				shopBalanceDate.setBalanceFlag(new Integer(1).byteValue()); // 1-未生成结算单
				shopBalanceDate.setBillalready(new Integer(1).byteValue()); // 1-未开票
				shopBalanceDate.setCreateUser(loginUser.getUsername());
				shopBalanceDate.setCreateTime(DateUtil.getCurrentDateTime());
				shopBalanceDateManager.generateBalanceDate(shopBalanceDate,currentMonth,lastMonth);
				
			}
				
			return new ResponseEntity<Map<String, Object>>(obj, HttpStatus.OK);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), ShopBalanceDate.class, request);
    	StringBuilder errorBuilder = new StringBuilder();
    	if(msgList != null && msgList.size() > 0){
	    	for(UploadMessageModel message : msgList){
	    		errorBuilder.append(message.getMessage() + "<br/>");
	    	}
	    	String errorInfo = " 以下错误行信息导入失败  <br />" + errorBuilder;
	    	mv.addObject("error", errorInfo);
    	}else{
    		mv.addObject("success","成功导入");
    	}
    	return mv;
    }
	
	@Override
	protected UploadMessageModel validateModel(ShopBalanceDate shopBalanceDate, int rowIndex) {
		UploadMessageModel uploadMessageModel = null;
		try{
			if(shopBalanceDate != null){
				if (StringUtils.isEmpty(shopBalanceDate.getShopNo()) || StringUtils.isEmpty(shopBalanceDate.getMonth())
						|| null == shopBalanceDate.getBalanceStartDate() || null == shopBalanceDate.getBalanceStartDate()) {
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 必填数据为空，店铺编码、结算月、结算起止时间为比必填！");
					return uploadMessageModel;
				}
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("shopNo", shopBalanceDate.getShopNo());
				int count = shopManager.findCount(params);
				if(count < 1){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 操作的店铺无效,请录入正确的店铺编码");
					return uploadMessageModel;
				}
				
				String month = shopBalanceDate.getMonth();
				Pattern p = Pattern.compile("[0-9]{6}");
				//进行匹配，并将匹配结果放在Matcher对象中
				Matcher m = p.matcher(month);
				if(!m.matches()){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 结算月格式无效,建议[201502]");
					return uploadMessageModel;
				}
				
				//终止日期
				if(shopBalanceDate.getBalanceEndDate().getTime() < shopBalanceDate.getBalanceStartDate().getTime()){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 终止日期不能小于起始日期");
					return uploadMessageModel;
				}
				
				//应开票日期
				if(shopBalanceDate.getShouldBillDate() != null){
					if(shopBalanceDate.getShouldBillDate().getTime() < shopBalanceDate.getBalanceEndDate().getTime()){
						uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 应开票日期不能小于终止日期");
						return uploadMessageModel;
					}
				}
				//应回款日期
				if(shopBalanceDate.getIncomePaymentsDate() != null){
					if(shopBalanceDate.getIncomePaymentsDate().getTime() < shopBalanceDate.getShouldBillDate().getTime()){
						uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 应回款日期不能小于于应开票日期");
						return uploadMessageModel;
					}
				}
				//发票应寄送日期
				if(shopBalanceDate.getInvoiceShouldSendDate() != null){
					if(shopBalanceDate.getInvoiceShouldSendDate().getTime() <  shopBalanceDate.getIncomePaymentsDate().getTime()){
						uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 发票应寄送日期不能小于应回款日期");
						return uploadMessageModel;
					}
				}
				//发票应送到日期
				if(shopBalanceDate.getInvoiceShouldArraiveDate() != null){
					if(shopBalanceDate.getInvoiceShouldArraiveDate().getTime() < shopBalanceDate.getInvoiceShouldSendDate().getTime()){
						uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 发票应到日期不能小于发票应寄送日");
						return uploadMessageModel;
					}
				}
				//备注
				if(shopBalanceDate.getRemark() != null){
					if(shopBalanceDate.getRemark().trim().length() > 200){
						uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 备注内容太长,请简写...");
						return uploadMessageModel;
					}
					shopBalanceDate.setRemark(shopBalanceDate.getRemark().trim());
				}
				
				Map<String,String> errorInfo = shopBalanceDateManager.validationShopBalanceDate(shopBalanceDate);
				if(errorInfo.get("error") != null){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 "+errorInfo.get("error").toString());
					return uploadMessageModel;
				}
				return uploadMessageModel;
			}else{
				return super.validateModel(shopBalanceDate, rowIndex);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			LOGGER.error(" validate the model values : "+shopBalanceDate.toString() + " , index : "+(rowIndex+1));
			uploadMessageModel = getErrorMessageObject(" validation ShopBalanceDate failed , "+shopBalanceDate.toString());
			return uploadMessageModel;
		}
	}
	
	public UploadMessageModel getErrorMessageObject(String message){
		UploadMessageModel uploadMessageModel = new UploadMessageModel();
		uploadMessageModel.setFlag(false);
		uploadMessageModel.setMessage(message);
		return uploadMessageModel;
	}
	

	@Override
	protected String[] getImportProperties() {
		return new String[]{"shopNo","month","balanceStartDate","balanceEndDate","shouldBillDate","incomePaymentsDate","invoiceShouldSendDate","invoiceShouldArraiveDate","remark"};
	}

	@Override
	protected boolean doBatchAdd(List<ShopBalanceDate> list) {
		try {
			if(list == null || list.size() < 1){
				return false;
			}
			
			addBatch(list);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addBatch(List<ShopBalanceDate> list) throws Exception{
		try{
			Date currentDate = new Date();
			Map<String,Object> params = new HashMap<String,Object>();
			
			for(ShopBalanceDate shopBalanceDate : list){
				params.put("shopNo", shopBalanceDate.getShopNo());
				Shop shop = shopManager.initSubsiInfo(params);
				
				shopBalanceDate.setCreateTime(currentDate);
				shopBalanceDate.setShortName(shop.getShortName());
				shopBalanceDate.setMallNo(shop.getMallNo());
				shopBalanceDate.setMallName(shop.getMallName());
				shopBalanceDate.setCompanyName(shop.getCompanyName());
				shopBalanceDate.setCompanyNo(shop.getCompanyNo());
				shopBalanceDate.setBsgroupsName(shop.getBsgroupsName());
				shopBalanceDate.setBsgroupsNo(shop.getBsgroupsNo());
				shopBalanceDate.setBillalready(new Integer(1).byteValue());
				shopBalanceDate.setBalanceFlag(new Integer(1).byteValue());
				shopBalanceDateManager.add(shopBalanceDate);
			}
			list.clear();
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 查询没有设置结算期的店铺信息
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/no_set_shopBalanceDate.json")
	@ResponseBody
	public Map<String, Object> selectNoSetShopBalanceDate(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<ShopBalanceDate> list = null;
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);

		total = shopBalanceDateManager.selectNoSetShopBalanceDateCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = shopBalanceDateManager.selectNoSetShopBalanceDateByPage(page, sortColumn, sortOrder, params);
		}
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 查询结算期设置交叉或不连续的店铺信息
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shopBalanceDate_part_right.json")
	@ResponseBody
	public Map<String, Object> selectShopBalanceDatePartOfRight(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<ShopBalanceDate> list = null;
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);

		total = shopBalanceDateManager.selectShopBalanceDatePartOfRightCount(params);
		if (total > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			list = shopBalanceDateManager.selectShopBalanceDatePartOfRightByPage(page, sortColumn, sortOrder, params);
		}
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
}