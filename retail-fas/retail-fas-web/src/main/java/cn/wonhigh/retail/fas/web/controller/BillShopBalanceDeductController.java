package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDeductFooterDto;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.FasLogoutStatusEnum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.MallDeductionSet;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.AnnotationReflectUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.manager.BillShopBalanceDeductManager;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.MallDeductionSetManager;
import cn.wonhigh.retail.fas.manager.ShopBalanceDateManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.service.ShopBalanceDateService;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-27 19:22:11
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
@RequestMapping("/bill_shop_balance_deduct")
@ModuleVerify("30140003")
public class BillShopBalanceDeductController extends ExcelImportController<BillShopBalanceDeduct> {
	
    @Resource
    private BillShopBalanceDeductManager billShopBalanceDeductManager;
    
    @Resource
    private MallDeductionSetManager mallDeductionSetManager;  
    
    @Resource
    private ShopService shopService;
    
    @Resource
    private ShopBalanceDateService shopBalanceDateService;

    @Resource
	private ShopManager shopManager ;
    
    @Resource
   	private BrandManager brandManager ;
    
    
    @Resource
   	private ShopBalanceDateManager shopBalanceDateManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_deduct/",billShopBalanceDeductManager);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/shopbalance_deduct";
   	}   
    
    @RequestMapping("/deduct_costbefore")
   	public String deduct_costbefore() {
   		return "mallshop_balance/shopbalance_deduct_costbefore";
   	}
    
    @RequestMapping("/deduct_costafter")
   	public String deduct_costafter() {
   		return "mallshop_balance/shopbalance_deduct_costafter";
   	}
    
    @RequestMapping("/shopdeduct_generate_results")
   	public String shopdeduct_generate_results() {
   		return "mallshop_balance/shopdeduct_generate_results";
   	}
    
    
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(BillShopBalanceDeduct.class);
    
    /**
   	 * 生成费用-执行方法
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping("/saveBalanceDeduct")
   	@ResponseBody
   	public List<Map<String, String>> saveBalanceDeduct(HttpServletRequest req, Model model) throws ManagerException{
   		SystemUser loginUser = CurrentUser.getCurrentUser(req);
   		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
   	
   		Map<String, Object> params = builderParams(req, model);
   		String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
		String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
		String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
		String month = params.get("month") == null ? null : params.get("month").toString();
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		String bsgroupsNo = params.get("bsgroupsNo") == null ? null : params.get("bsgroupsNo").toString();
		String mallNo = params.get("mallNo") == null ? null : params.get("mallNo").toString();
		String costDeductType = params.get("costDeductType") == null ? null : params.get("costDeductType").toString();
		List<String> shopNoGenerates = new ArrayList<String>();
		//如果选择了特定的店铺生成
		if(StringUtils.isNotEmpty(shopNos)){
			shopNoGenerates.addAll(Arrays.asList(shopNos.split(",")));
		}else {
			Map<String, Object> shopParamsMap = new HashMap<String, Object>();
			shopParamsMap.put("companyNo", companyNo);
			shopParamsMap.put("bsgroupsNo", bsgroupsNo);
			shopParamsMap.put("mallNo", mallNo);
			if(StringUtils.isNotEmpty(organNos)){
				shopParamsMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNos));
				shopParamsMap.put("organNos",  null);
			}
			if(StringUtils.isNotEmpty(brandNos)){
				shopParamsMap.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
			}
			
			List<Shop> shops = shopManager.findByBiz(null, shopParamsMap);
			if (!CollectionUtils.isEmpty(shops)) {
				for (Shop shop : shops) {
					shopNoGenerates.add(shop.getShopNo());
				}
			}
		}
   		
		if (CollectionUtils.isEmpty(shopNoGenerates)) {
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("shopNo", "");
			resultMap.put("errorInfo", "生成费用失败,没有找到需要生成费用的店铺");
			results.add(resultMap);
			return results;
		}
		int count = 0;
		for (String shopNo : shopNoGenerates) {
			//后台需要使用的必要参数
			BillShopBalanceDeduct deductParams = new BillShopBalanceDeduct();
			deductParams.setShopNo(shopNo);
			deductParams.setCompanyNo(companyNo);
			deductParams.setMonth(month);
			deductParams.setCreateUser(loginUser.getUsername());
			deductParams.setCostDeductTypeStr(costDeductType);
			try {
				deductParams.setCreateTime(DateUtil.getCurrentDateTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(),e);
			}
			count += billShopBalanceDeductManager.generateBalanceDeduct(deductParams);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("shopNo", shopNo);
			
			if (count > 0) {
				resultMap.put("errorInfo", "成功生成店铺编号 ： " + shopNo + " 的费用。");
			}else if (count < 0) {// -1
				resultMap.put("errorInfo", "生成费用失败,店铺编号 ： " + shopNo + " 的结算期设置错误,是否未结算状态请检查");
			} else {
				resultMap.put("errorInfo", "生成费用失败,MPS未设置费用或费用已经生成");
			}
			
			results.add(resultMap);
		}
		return results;
   	}
   	
   	/**
   	 * 更新
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping("/updateBalanceDeductBalanceNo")
   	@ResponseBody
   	public int updateBalanceDeductBalanceNo(HttpServletRequest req, Model model) throws Exception{
   		
   		Map<String, Object> params = builderParams(req, model);
   		return  billShopBalanceDeductManager.updateBalanceDeductBalanceNo(params);
   	}
   	
   	/**
   	 * 删除
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping(value = "/deleteBalanceDeduct")
   	@ResponseBody
   	public Integer remove(@RequestParam("idList")String strIds,HttpServletRequest request) throws Exception{
   		SystemUser loginUser = CurrentUser.getCurrentUser(request);
   		String createUser= loginUser.getUsername();
   		Date createTime = DateUtil.getCurrentDateTime();
   		String[] ids = strIds.split(";");
   		return  billShopBalanceDeductManager.remove(ids,createUser,createTime);
   	} 
   	
   	@RequestMapping(value = "/list.json")
	@ResponseBody
	public  Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
   		Map<String, Object> obj = new HashMap<String, Object>();
//   		String balaceDeduType = req.getParameter("balaceDeduType");
		Map<String, Object> params = builderParams(req, model);
//		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
//   		if(("1").equals(balaceDeduType) && StringUtils.isEmpty(shopNo)) {
//   			obj.put("total", 0);
//   			obj.put("rows", new ArrayList<BillShopBalanceDeduct>(0));
//   			return obj;
//   		}
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 20 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		
		String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
		String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
		String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
		
		//多选值格式转换
		if(StringUtils.isNotEmpty(organNos)){
			params.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNos));
			params.put("organNos",  null);
		}
		if(StringUtils.isNotEmpty(brandNos)){
			params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
		}
		if(StringUtils.isNotEmpty(shopNos)){
			params.put("shopNos",  Arrays.asList(shopNos.split(",")));
		}
		
		int total = billShopBalanceDeductManager.findCount(params);
		if(total == 0) {
			obj.put("total", 0);
   			obj.put("rows", new ArrayList<BillShopBalanceDeduct>(0));
   			return null;
		}
		
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<BillShopBalanceDeduct> list = billShopBalanceDeductManager.findByPage(page, sortColumn, sortOrder, params);
		
		obj.put("total", total);
		obj.put("rows", list);
		
		// 组装footer
		if(list.size() > 0) {
			BillShopBalanceDeductFooterDto footerDto = billShopBalanceDeductManager.getFooterDto(params);
			if(footerDto != null) {
				BillShopBalanceDeduct billShopBalanceDeduct = new BillShopBalanceDeduct();
				billShopBalanceDeduct.setMonth("合计");
				// 复制属性值
				try {
					AnnotationReflectUtil.copyProperties(billShopBalanceDeduct, footerDto);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(),e);

				}
				List<BillShopBalanceDeduct> BillShopBalanceDeductTotal = new ArrayList<BillShopBalanceDeduct>(1);
				BillShopBalanceDeductTotal.add(billShopBalanceDeduct);
				obj.put("footer", BillShopBalanceDeductTotal);
			}
		}
		return obj;
	}
   	
   	@Override
    public List<BillShopBalanceDeduct> queryExportData(Map<String, Object> params) throws ManagerException {
   		
   		String shopNos = params.get("shopNos") == null ? null : params.get("shopNos").toString();
		String organNos = params.get("organNos") == null ? null : params.get("organNos").toString();
		String brandNos = params.get("brandNos") == null ? null : params.get("brandNos").toString();
		
		//多选值格式转换
		if(StringUtils.isNotEmpty(organNos)){
			params.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNos));
			params.put("organNos",  null);
		}
		if(StringUtils.isNotEmpty(brandNos)){
			params.put("brandNos",  FasUtil.formatInQueryCondition(brandNos));
		}
		if(StringUtils.isNotEmpty(shopNos)){
			params.put("shopNos",  Arrays.asList(shopNos.split(",")));
		}
		
		int total = billShopBalanceDeductManager.findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		List<BillShopBalanceDeduct> list = billShopBalanceDeductManager.findByPage(page, orderByField, orderBy, params);
		return list;
	}

   	/**
	 * 给实体对象的字段设置默认的值
	 * 
	 * @param billShopBalanceDeduct 实体对象
	 * @param request HttpServletRequest
   	 * @return 
	 * @return 设置默认值之后的实体对象
	 */
	protected BillShopBalanceDeduct setDefaulValues(BillShopBalanceDeduct billShopBalanceDeduct, HttpServletRequest request) {
		SystemUser loginUser = CurrentUser.getCurrentUser(request);
		if(StringUtils.isEmpty(billShopBalanceDeduct.getId())) {
			billShopBalanceDeduct.setId(UUIDGenerator.getUUID());
			billShopBalanceDeduct.setCreateUser(loginUser.getUsername());
			try {
				billShopBalanceDeduct.setCreateTime(DateUtil.getCurrentDateTime());
				billShopBalanceDeduct.setUpdateTime(DateUtil.getCurrentDateTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(),e);
			}
			billShopBalanceDeduct.setUpdateUser(loginUser.getUsername());
			billShopBalanceDeduct.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
			billShopBalanceDeduct.setStatus(FasLogoutStatusEnum.ENABLE_STATUS.getValue());
		} else {
			billShopBalanceDeduct.setUpdateUser(loginUser.getUsername());
			try {
				billShopBalanceDeduct.setUpdateTime(DateUtil.getCurrentDateTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(),e);

			}
		}
//		    billShopBalanceDeduct.setId(UUIDGenerator.getUUID());
		return billShopBalanceDeduct;
	}
	
	@Override
	protected String[] getImportProperties() {// costCateCode  costCateName 
		return new String[]{"shortName","month","brandName","mallDeductionName","deductionName","deductAmount","actualAmount","diffReason",
				"costTypeStr","costDeductTypeStr","costPayTypeStr","processStatusStr"};
	}

	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	
    	List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), BillShopBalanceDeduct.class, request);
    	StringBuilder errorBuilder = new StringBuilder();
    	if(msgList != null && msgList.size() > 0){
	    	for(UploadMessageModel message : msgList){
	    		errorBuilder.append(message.getMessage() + "<br/>");
	    	}
	    	mv.addObject("error", errorBuilder);
    	}else{
    		mv.addObject("success","成功导入");
    	}
    	return mv;
	}

	@Override
	protected boolean doBatchAdd(List<BillShopBalanceDeduct> list)  {
		if(list != null && list.size() > 0){
			for(BillShopBalanceDeduct dtl : list){
			try {
				initializationInfo(list,dtl);
			} catch (Exception e) {
//				e1.printStackTrace();
				LOGGER.error(e.getMessage(),e);
				return false;
			}
				try {
					if(dtl.getCostTypeStr().equals("合同内")){
						dtl.setCostType((byte) 1);
					}else if(dtl.getCostTypeStr().equals("合同外")){
						dtl.setCostType((byte) 2 );
					}
				
					if(dtl.getCostPayTypeStr().equals("帐扣")){
						dtl.setCostPayType((byte) 1);
					}else if(dtl.getCostPayTypeStr().equals("现金")){
						dtl.setCostPayType((byte) 2 );
					}
					if(dtl.getCostDeductTypeStr().equals("票前")){
						dtl.setCostDeductType((byte) 1);
					}else if(dtl.getCostDeductTypeStr().equals("票后")){
						dtl.setCostDeductType((byte) 2 );
					}
					if(dtl.getProcessStatusStr().equals("未完成")){
						dtl.setProcessStatus((byte) 1);
					}else if(dtl.getProcessStatusStr().equals("已完成")){
						dtl.setProcessStatus((byte) 2 );
					}
					
//					dtl.setDeductAmount(dtl.getDeductAmount());
//					dtl.setActualAmount(dtl.getActualAmount());
					BigDecimal deductAmount=dtl.getDeductAmount();
					BigDecimal actualAmount=dtl.getActualAmount();
					if(null == deductAmount){
						deductAmount =  new BigDecimal("0");		
					}
					if(null == actualAmount){
						actualAmount =  new BigDecimal("0");		
					}
					dtl.setDeductAmount(deductAmount);
					dtl.setActualAmount(actualAmount);
//					扣费差异金额=实际金额-系统扣费金额
					dtl.setDiffAmount(actualAmount.subtract(deductAmount));
					
					dtl.setMonth(dtl.getMonth().substring(0, 6));
//					根据店铺   +  月份  获取开始  结束时间
//					dtl.setMallDeductionName(list.get(0).getMallDeductionName());

					Map<String, Object> shopBalanceDateparams = new HashMap<String, Object>();
					shopBalanceDateparams.put("shopNo", dtl.getShopNo());
					shopBalanceDateparams.put("month", dtl.getMonth().substring(0, 6));
					if(dtl.getCostDeductTypeStr().equals("票前")){
						shopBalanceDateparams.put("notEqualBalanceFlag", 2);//票后不控制
					}
					List<ShopBalanceDate> shopBalanceDateList;
					try {
						shopBalanceDateList = shopBalanceDateService.findByBiz(null, shopBalanceDateparams);
						if(!CollectionUtils.isEmpty(shopBalanceDateList)) {
							for(ShopBalanceDate shopBalanceDate : shopBalanceDateList){
								dtl.setBalanceStartDate(shopBalanceDate.getBalanceStartDate());
								dtl.setBalanceEndDate(shopBalanceDate.getBalanceEndDate());
							}
							
						}
					} catch (ServiceException e) {
						LOGGER.error(e.getMessage(),e);
						return false;
					}
					
					Map<String, Object> brandParams = new HashMap<String, Object>();
					brandParams.put("shopNo", dtl.getShopNo());
					brandParams.put("name", dtl.getBrandName());
					ShopBrand shopBrand = shopService.selectShopBrandInfo(brandParams);
					if(shopBrand != null){
						dtl.setBrandNo(shopBrand.getBrandNo());
						dtl.setBrandName(shopBrand.getName());
					}
					
					dtl.setDeductDate(new Date());
					dtl.setId(UUIDGenerator.getUUID());
					dtl.setGenerateType(1);
					billShopBalanceDeductManager.add(dtl);
				} catch (ManagerException e) {
					LOGGER.error(e.getMessage(),e);
					return false;
				}
			}
		}else{
			return false;
		}
		return true;
	}
	
	public void initializationInfo(List<BillShopBalanceDeduct> billShopBalanceDeductList,BillShopBalanceDeduct billShopBalanceDeduct) throws ServiceException{
		
		List<String> shopNoList = new ArrayList<String>();
//		for(BillShopBalanceDeduct billShopBalanceDeduct : billShopBalanceDeductList){
			Map<String, Object> shopParams = new HashMap<String, Object>();
			shopParams.put("shopShortName", billShopBalanceDeduct.getShortName());
			List<Shop> listShop = shopService.findByBiz(null, shopParams);
			if(listShop != null){
				if(!shopNoList.contains(listShop.get(0).getShopNo())){
					shopNoList.add(listShop.get(0).getShopNo());
				}
			}
//		}
		
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			StringBuffer sbf = new StringBuffer();
			
//			sbf.append(" AND s.shop_no in  ( ");
//			for(int i = 0; i < shopNoList.size();i++){
//				
//				if( i == (shopNoList.size() - 1)){
//					sbf.append("'").append(shopNoList.get(i)).append("'");
//				}else{
//					sbf.append("'").append(shopNoList.get(i)).append("'").append(",");
//				}
//			}
//			sbf.append(" ) ");
			sbf.append(" AND s.shop_no   =  ");
			sbf.append("'").append(shopNoList.get(0)).append("'");
			params.put("queryCondition", sbf.toString());
			
			List<Shop> shopList = shopService.selectSubsiInfoList(null, null, null, params);
			if(shopList != null){
				
//				for(BillShopBalanceDeduct billShopBalanceDeduct : billShopBalanceDeductList){
					for(Shop shop : shopList){						
						if(billShopBalanceDeduct.getShortName().equals(shop.getShortName())){
							billShopBalanceDeduct.setShopNo(shop.getShopNo());
							billShopBalanceDeduct.setShortName(shop.getShortName());
							billShopBalanceDeduct.setMallNo(shop.getMallNo());
							billShopBalanceDeduct.setMallName(shop.getMallName());
							billShopBalanceDeduct.setCompanyNo(shop.getCompanyNo());
							billShopBalanceDeduct.setCompanyName(shop.getCompanyName());							
					
							billShopBalanceDeduct.setOrganNo(shop.getOrganNo()); 
							billShopBalanceDeduct.setOrganName(shop.getOrganName());  
							billShopBalanceDeduct.setBsgroupsNo(shop.getBsgroupsNo()); 
							billShopBalanceDeduct.setBsgroupsName(shop.getBsgroupsName());  
								
//							ShopBrand  shopBrand = getShopBrandInfo(params);  
//							if(shopBrand != null && !"".equals(shopBrand)){
//								billShopBalanceDeduct.setBrandNo(shopBrand.getBrandNo());
//								billShopBalanceDeduct.setBrandName(shopBrand.getName());
//							}
//							List<CostCategorySetting> costCategorySettingList = costCategorySettingService.findByBiz(new CostCategorySetting(), params);
							
							Map<String, Object> mallDeductionSetParams = new HashMap<String, Object>();
							mallDeductionSetParams.put("name", billShopBalanceDeduct.getDeductionName());
							List<MallDeductionSet> mallDeductionSetList = mallDeductionSetManager.findByBiz(new MallDeductionSet(), mallDeductionSetParams);
							
//							Map<String,Object> bparams = new HashMap<String,Object>();
//							bparams.put("code", billShopBalanceDeduct.getDeductionCode());
//							List<MallDeductionSet> mallDeductionSetList = mallDeductionSetManager.findByBiz(new MallDeductionSet(), bparams);
							if(mallDeductionSetList != null){
								for(MallDeductionSet mallDeductionSet : mallDeductionSetList){
									billShopBalanceDeduct.setDeductionCode(mallDeductionSet.getCode());
									billShopBalanceDeduct.setCostCateCode(mallDeductionSet.getCostCode());  
									billShopBalanceDeduct.setCostCateName(mallDeductionSet.getCostName()); 
									billShopBalanceDeduct.setAccountsNo(mallDeductionSet.getAccountsNo());
								}
							}
							
//							Map<String, Object> brandParams = new HashMap<String, Object>();
//							brandParams.put("name", billShopBalanceDeduct.getBrandName());
//							List<Brand> brandList = brandManager.findByBiz(new Brand(), brandParams);
//							
//							if(brandList != null){
//								for(Brand brand : brandList){
//									billShopBalanceDeduct.setBrandNo(brand.getBrandNo()); 
//									billShopBalanceDeduct.setBrandName(brand.getName());
//								}
//							}
							break;
						}
					}
				}
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
//		}
		}
	}
	
	@Override
	protected UploadMessageModel validateModel(BillShopBalanceDeduct model, int rowIndex) throws ManagerException, IOException {
		UploadMessageModel messageModel = basicValidation(model,rowIndex);
		if(messageModel != null){
			return messageModel;
		}
		return super.validateModel(model, rowIndex);
	}
	
	public UploadMessageModel basicValidation(BillShopBalanceDeduct billShopBalanceDeduct,int rowIndex){
		
		UploadMessageModel uploadMessageModel = null;
		StringBuffer sbf = new StringBuffer();
		
		rowIndex = rowIndex + 1;
		String shopNo = billShopBalanceDeduct.getShopNo() != null ? billShopBalanceDeduct.getShopNo().trim() : null;
//		BigDecimal depositAmount = billShopBalanceDeduct.getDepositAmount();
//		Date depositTime = billShopBalanceDeduct.getDepositCashTime();
		
//		if(shopNo != null && !"".equals(shopNo)){
		
//			Pattern p = Pattern.compile("[0-9]{16,19}");
//	        //进行匹配，并将匹配结果放在Matcher对象中
//	        Matcher m = p.matcher(shopNo);
//	        if(!m.matches()){
//	        	sbf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//	        	sbf.append(" 第"+rowIndex+"行 账号不合法").append("<br />");
//	        }
//		}else{
//			sbf.append(" 第"+rowIndex+"行 账号不能为空").append("<br />");
//		}
		
//		if(depositAmount != null){
//			
//			if(depositAmount.compareTo(new BigDecimal(0)) <= 0){
//				sbf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//	        	sbf.append(" 第"+rowIndex+"行请正确录入存现金额").append("<br />");
//			}
//			
//		}else{
//			sbf.append(" 第"+rowIndex+"行 存现金额不能为空").append("<br />");
//		}
		
//		if(depositTime != null && !"".equals(depositTime)){
//			
//		}else{
//			sbf.append(" 第"+rowIndex+"行 存现日期不能为空").append("<br />");
//		}
		
		if(!"".equals(sbf.toString())){
			uploadMessageModel = new UploadMessageModel();
			uploadMessageModel.setFlag(false);
			uploadMessageModel.setMessage(sbf.toString());
		}
		return uploadMessageModel;
	}
}