package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;
import cn.wonhigh.retail.fas.manager.CompanyBrandSettlePeriodManager;
import cn.wonhigh.retail.fas.manager.HeadquarterPeriodManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-13 12:04:21
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
@RequestMapping("/company_brand_settle_period")
@ModuleVerify("30120008")
public class CompanyBrandSettlePeriodController extends BaseController<CompanyBrandSettlePeriod> {
	
    @Resource
    private CompanyBrandSettlePeriodManager companyBrandSettlePeriodManager;

    @Resource
    private BrandUnitManager brandUnitManager;
    
    @Resource
    private HeadquarterPeriodManager headquarterPeriodManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("company_brand_settle_period/",companyBrandSettlePeriodManager);
    }
    
    @ResponseBody
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException
    {
            int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
            int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
            String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
            String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
            Map<String, Object> params = builderParams(req, model);
            if (StringUtils.isNotEmpty(req.getParameter("brandUnitNos"))) {
    			params.put("brandNos",FasUtil.formatInQueryCondition(req.getParameter("brandUnitNos").toString()));
    		}
            if (StringUtils.isNotEmpty(req.getParameter("companyNos"))) {
    			params.put("companyNos",FasUtil.formatInQueryCondition(req.getParameter("companyNos").toString()));
    		}
            int total = companyBrandSettlePeriodManager.findCount(params);
            SimplePage page = new SimplePage(pageNo, pageSize, total);
            List<CompanyBrandSettlePeriod> list = companyBrandSettlePeriodManager.findByPage(page, sortColumn, sortOrder, params);
            Map<String, Object> obj = new HashMap<String, Object>();
            obj.put("total", Integer.valueOf(total));
            obj.put("rows", list);
            return obj;
    }
       
	@SuppressWarnings("rawtypes")
	@RequestMapping("/validate_data")
	@ResponseBody
	public Map<String, Object> validateData(HttpServletRequest req) throws ManagerException, JsonParseException, JsonMappingException, IOException {

		Map<String, Object> result = new HashMap<String, Object>();
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		List<CompanyBrandSettlePeriod> checkList = new ArrayList<CompanyBrandSettlePeriod>();
		if(StringUtils.isNotEmpty(insertedList)){
			if(!insertedList.equals("[]")){
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
				checkList.addAll(convertCompanySettlePeriod(mapper,list));
			}
			
		}
		if (!CollectionUtils.isEmpty(checkList)) {
			Map<String, List<String>> duplicateMap = new HashMap<String, List<String>>();
			
			Map<String, Object> checkParams = new HashMap<String, Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			int count = 0;
			boolean duplicate = false;
			boolean duplicates = false;
			String duplicateShopGroup = "";
			String duplicateBrand = "";
			for (CompanyBrandSettlePeriod settlePeriod : checkList) {
				checkParams.put("companyNo", settlePeriod.getCompanyNo());
				checkParams.put("brandNo", settlePeriod.getBrandNo());
				count = this.companyBrandSettlePeriodManager.findCount(checkParams);
				if (count > 0) {
					duplicate = true;
					duplicateShopGroup = settlePeriod.getCompanyName();
					duplicateBrand = settlePeriod.getBrandName();
					break;
				}	
				params.put("companyNo", settlePeriod.getCompanyNo());
				params.put("brandUnitNo", settlePeriod.getBrandNo());
				params.put("accountSettleTime1", settlePeriod.getAccountSettleTime());
				params.put("supplierSettleTimes1", settlePeriod.getSupplierSettleTime());
				params.put("saleSettleTime1", settlePeriod.getSaleSettleTime());
				params.put("transferSettleTime1", settlePeriod.getTransferSettleTime());
				count = this.headquarterPeriodManager.findCount(params);
				if (count > 1) {
					duplicates = true;
					duplicateShopGroup = settlePeriod.getCompanyName();
					break;
				}	
				if (duplicateMap.containsKey(settlePeriod.getCompanyNo())) {
					if (duplicateMap.get(settlePeriod.getCompanyNo()).contains(settlePeriod.getBrandNo())) {
						duplicate = true;
						duplicateShopGroup = settlePeriod.getCompanyName();
						duplicateBrand = settlePeriod.getBrandName();
						break;
					}else {
						duplicateMap.get(settlePeriod.getCompanyNo()).add(settlePeriod.getBrandNo());
					}
				}else {
					List<String> brandNos = new ArrayList<String>();
					brandNos.add(settlePeriod.getBrandNo());
					duplicateMap.put(settlePeriod.getCompanyNo(), brandNos);
				}
			}
			if(duplicates){
				result.put("success", false);
				result.put("message", "公司[" + duplicateShopGroup + "]的结账日不能小于总部结账日！");
				return result;
			}
			if (duplicate) {
					result.put("success", false);
					result.put("message", "公司[" + duplicateShopGroup + "]已经维护了结账日！");
					if(duplicateBrand!=null){
						result.put("message", "[" + duplicateShopGroup + "]的[" + duplicateBrand + "]品牌已经维护了结账日！");
					}
					return result;
			}
		}
		result.put("success", true);
		return result;
		}
    
	@RequestMapping("/check_generate_condition")
	@ResponseBody
	public Map<String, Object> companyBrand(HttpServletRequest request,CompanyBrandSettlePeriod companyBrandSettlePeriod) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		
		calendar.add(Calendar.MONTH, -1);
		nowDate = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DATE));
		nowDate = calendar.getTime();
		
		Map<String, BrandUnit> brandMap = new HashMap<String, BrandUnit>();
		List<BrandUnit> brands = brandUnitManager.findByBiz(null, null);
		for (BrandUnit brandUnit : brands) {
			brandMap.put(brandUnit.getBrandUnitNo(), brandUnit);
		}
		
		Map<String, Object> checkParams = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();

		checkParams.put("companyNo", companyBrandSettlePeriod.getCompanyNo());
		
		int count = 0;
		boolean duplicate = false;
		boolean duplicates = false;
		String duplicateShopGroup = "";
		String duplicateBrand = "";
		String[] brandNos = companyBrandSettlePeriod.getBrandNo().split(",");
//		String[] brandNames = companyBrandSettlePeriod.getBrandName().split(",");
		for (int i=0;i<brandNos.length;i++){
			checkParams.put("brandNo", brandNos[i]);
			count = this.companyBrandSettlePeriodManager.findCount(checkParams);
			if (count > 0) {
				duplicate = true;
				duplicateShopGroup = companyBrandSettlePeriod.getCompanyName();
				duplicateBrand = brandNos[i];
				break;
			}
			CompanyBrandSettlePeriod companyBrand=new CompanyBrandSettlePeriod();
			companyBrand.setCompanyNo(companyBrandSettlePeriod.getCompanyNo());
			companyBrand.setCompanyName(companyBrandSettlePeriod.getCompanyName());
			companyBrand.setBrandNo(brandNos[i]);
			companyBrand.setBrandName(brandMap.get(brandNos[i]).getName());
			companyBrand.setAccountSettleTime(nowDate);
			companyBrand.setSaleSettleTime(nowDate);
			companyBrand.setSupplierSettleTime(nowDate);
			companyBrand.setTransferSettleTime(nowDate);
			companyBrand.setCreateUser(loginUser.getUsername());
			companyBrand.setCreateTime(DateUtil.getCurrentDateTime());
			params.put("companyNo", companyBrandSettlePeriod.getCompanyNo());
			params.put("brandUnitNo", companyBrandSettlePeriod.getBrandNo());
			params.put("accountSettleTime", nowDate);
			params.put("supplierSettleTimes", nowDate);
			params.put("saleSettleTime",nowDate);
			params.put("transferSettleTime", nowDate);
			count = this.headquarterPeriodManager.findCount(params);
			if (count >= 1) {
				duplicates = true;
				duplicateShopGroup = companyBrandSettlePeriod.getCompanyName();
				break;
			}	
			companyBrandSettlePeriodManager.add(companyBrand);
		}
		if(duplicates){
			result.put("success", false);
			result.put("message", "公司[" + duplicateShopGroup + "]的结账日不能小于总部结账日！");
			return result;
		}
		if (duplicate) {
			result.put("success", false);
			result.put("message", "公司[" + duplicateShopGroup + "]已经维护了结账日！");
			if(duplicateBrand!=null){
				result.put("message", "[" + duplicateShopGroup + "]的[" + duplicateBrand + "]品牌已经维护了结账日！");
			}
			return result;
		}
		result.put("success", true);
		return result;
    }
    /**
	 * 参数设置
	 * @param req
	 * @param map
	 * @return
	 */
	/**
	 * 查询导出数据的方法
	 * @param params 查询参数
	 * @return List<ModelType>
	 * @throws ManagerException 异常
	 */
	protected List<CompanyBrandSettlePeriod> queryExportData(Map<String, Object> params) throws ManagerException {
		String orderByField = params.get("orderByField") == null ? "" : params.get("orderByField").toString();
		String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
		
		if (null != params.get("brandUnitNos")) {
			params.put("brandNos",FasUtil.formatInQueryCondition(params.get("brandUnitNos").toString()));
		}
		int total = companyBrandSettlePeriodManager.findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		List<CompanyBrandSettlePeriod> list = companyBrandSettlePeriodManager.findByPage(page, orderByField, orderBy, params);
		return list;
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
	private List<CompanyBrandSettlePeriod> convertCompanySettlePeriod(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		Class<CompanyBrandSettlePeriod> entityClass = (Class<CompanyBrandSettlePeriod>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<CompanyBrandSettlePeriod> tl=new ArrayList<CompanyBrandSettlePeriod>(list.size());
		for (int i = 0; i < list.size(); i++) {
			CompanyBrandSettlePeriod type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
			tl.add(type);
		}
		return tl;
	}
	

}