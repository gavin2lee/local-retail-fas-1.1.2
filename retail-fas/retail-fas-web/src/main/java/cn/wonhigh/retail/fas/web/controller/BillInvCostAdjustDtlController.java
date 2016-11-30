package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.util.Hash;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.model.SelfShopDepositAccount;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.BillInvCostAdjustDtlManager;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.CompanyPeriodBalanceManager;
import cn.wonhigh.retail.fas.manager.ItemCostManager;
import cn.wonhigh.retail.fas.manager.ItemManager;
import cn.wonhigh.retail.fas.manager.PeriodBalanceManager;
import cn.wonhigh.retail.fas.service.ItemService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:14
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/bill_inv_cost_adjust_dtl")
public class BillInvCostAdjustDtlController extends ExcelImportController<BillInvCostAdjustDtl> {
    @Resource
    private BillInvCostAdjustDtlManager billInvCostAdjustDtlManager;
    
    @Resource
    private ItemManager itemManager;
    
    @Resource
    private ItemCostManager itemCostManager;
    
    @Resource
    private CompanyPeriodBalanceManager companyPeriodBalanceManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_inv_cost_adjust_dtl/",billInvCostAdjustDtlManager);
    }
    
    @RequestMapping(value = "/save")
	public ResponseEntity<Map<String, Boolean>> save(HttpServletRequest req) throws JsonParseException,
			JsonMappingException, IOException, ManagerException {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();

		String deletedList = StringUtils.isEmpty(req.getParameter("deleted")) ? "" : req.getParameter("deleted");
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		String billNo = req.getParameter("billNo");
		String shardingFlag = req.getParameter("shardingFlag");
		String companyNo = req.getParameter("companyNo");
		String companyName = req.getParameter("companyName");
		String year = req.getParameter("year");
		String month = req.getParameter("month");
		ObjectMapper mapper = new ObjectMapper();
		Map<CommonOperatorEnum, List<BillInvCostAdjustDtl>> params = new HashMap<CommonOperatorEnum, List<BillInvCostAdjustDtl>>();
		//用于存放billNo 和id号
		Map<String, String> paramsTemp = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(deletedList)) {
			List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>() {
			});
			List<BillInvCostAdjustDtl> oList = convertListWithTypeReference(mapper, list);
			params.put(CommonOperatorEnum.DELETED, oList);
		}
		if (StringUtils.isNotEmpty(upadtedList)) {
			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {
			});
			List<BillInvCostAdjustDtl> oList = convertListWithTypeReference(mapper, list);
			params.put(CommonOperatorEnum.UPDATED, oList);
		}
		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
			});
			List<BillInvCostAdjustDtl> oList = convertListWithTypeReference(mapper, list);
			params.put(CommonOperatorEnum.INSERTED, oList);
		}
		if (params.size() > 0) {
			paramsTemp.put("billNo", billNo);
			paramsTemp.put("shardingFlag", shardingFlag);
			paramsTemp.put("companyNo", companyNo);
			paramsTemp.put("companyName", companyName);
			paramsTemp.put("year", year);
			paramsTemp.put("month", month);
			billInvCostAdjustDtlManager.save(params, paramsTemp);
		}
		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
    
	@RequestMapping(value = "/import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView("/print/import");
		List<UploadMessageModel> msgList = new ArrayList<UploadMessageModel>();
		List<BillInvCostAdjustDtl> list = new ArrayList<BillInvCostAdjustDtl>();
		HashMap<String, Item> temp = new HashMap<String, Item>();
		List<BillInvCostAdjustDtl> list1 = new ArrayList<BillInvCostAdjustDtl>(1000);
		StringBuffer sb = new StringBuffer(" AND (");
		int effectiveCount = 0, totalCount = 0, count = 0;
		try {
			InputStream inputStream = file.getInputStream();
			//创建Excel工作薄  
			Workbook wb = create(inputStream);  
			//得到第一个工作表  
			Sheet sheet = wb.getSheetAt(0); 
			BillInvCostAdjustDtl model = null;
			int rowCount = sheet.getPhysicalNumberOfRows();
			for(int i = 2; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				model = BillInvCostAdjustDtl.class.newInstance();
				for(int j = 0; j < row.getLastCellNum(); j++) {
					Object value = getCellValue(row.getCell(j));
					if(value == null || "".equals(String.valueOf(value).trim())) {
						continue;
					}
					setValue(model, getImportProperties()[j], value);
					this.setDefaulValues(model, request);
				}
				list.add(model);
			}
			
			for(BillInvCostAdjustDtl dtl:list){
				sb.append(" (code = '"+dtl.getItemCode()+"' AND brand_no = '"+dtl.getBrandNo()+"') OR");
				if(count%1000==0 || count==list.size()-1){
					Map<String, Object> map = new HashMap<String,Object>();
					sb = sb.delete(sb.lastIndexOf(" OR"), sb.length());
					sb.append(")");
					map.put("queryCondition", sb);
					List<Item> items = itemManager.findByBiz(null, map);
					for(Item i:items){
						temp.put(i.getCode()+i.getBrandNo(), i);
					}
					sb.delete(6, sb.length());// 清空
				}
				count++;
			}
			
			for(int i = 2; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				model = BillInvCostAdjustDtl.class.newInstance();
				for(int j = 0; j < row.getLastCellNum(); j++) {
					Object value = getCellValue(row.getCell(j));
					if(value == null || "".equals(String.valueOf(value).trim())) {
						continue;
					}
					setValue(model, getImportProperties()[j], value);
					this.setDefaulValues(model, request);
				}
				
				//如果该行的数据已在list中存在，则忽略
				if(!this.validateRepeart(model, list1)) {
					UploadMessageModel msgModel = this.validateModel(model,i,temp);
					//如果数据满足要求，则将其加入待批量新增的集合中
					if(msgModel == null || msgModel.isFlag()) {
						effectiveCount++; // 有效记录数+1
						list1.add(model);
					} else { //否则将其加入记录错误信息的集合中
						msgList.add(msgModel);
					}
				}
				totalCount++;
				//批量 插入
				if(effectiveCount % 1000 == 0 || totalCount == (rowCount - 2)) {
					doAdd(list1, request,temp);
					list1 = new ArrayList<BillInvCostAdjustDtl>(1000);
				}
			}
		} catch(Exception e) {
			UploadMessageModel errorModel = new UploadMessageModel();
			errorModel.setFlag(false);
			errorModel.setMessage("导入失败,请联系管理员 ...");
			msgList.add(errorModel);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		StringBuilder errorBuilder = new StringBuilder();
		if (msgList != null && msgList.size() > 0) {
			for (UploadMessageModel message : msgList) {
				errorBuilder.append(message.getMessage() + "<br/>");
			}
			mv.addObject("error", " 以下错误行信息导入失败  <br />"+errorBuilder);
		} else {
			mv.addObject("success", "成功导入");
		}
		return mv;
	}
	
	private UploadMessageModel validateModel(BillInvCostAdjustDtl dtl, int rowIndex,HashMap<String, Item> temp) throws ManagerException, IOException {
		UploadMessageModel uploadMessageModel = null;
		try{
			if (null != dtl) {
				//校验必填项
				if(dtl.getItemCode()==null || dtl.getBrandNo()==null || dtl.getAdjustCost()==null){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 信息不完整！");
					return uploadMessageModel;
				}
				if(!temp.containsKey(dtl.getItemCode()+dtl.getBrandNo())){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 商品与品牌编码不匹配！");
					return uploadMessageModel;
				}
				return uploadMessageModel;
			} else {
				return super.validateModel(dtl, rowIndex);
			}
		} catch (Exception e) {
			uploadMessageModel = getErrorMessageObject(" validation BillInvCostAdjustDtl failed , "
					+ dtl.toString());
			return uploadMessageModel;
		}
	}

	private boolean doAdd(List<BillInvCostAdjustDtl> list, HttpServletRequest request,HashMap<String, Item> temp) throws ManagerException {
		try {
			if (list == null || list.size() < 1) {
				return false;
			}
			list = initDefaultInfo(list, request, temp);//库存成本明细初始化
			Map<CommonOperatorEnum, List<BillInvCostAdjustDtl>> params = new HashMap<CommonOperatorEnum, List<BillInvCostAdjustDtl>>();
			params.put(CommonOperatorEnum.INSERTED, list);
			billInvCostAdjustDtlManager.save(params);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return true;
		
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
	private List<BillInvCostAdjustDtl> convertListWithTypeReference(ObjectMapper mapper, List<Map> list)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		@SuppressWarnings("unchecked")
		Class<BillInvCostAdjustDtl> entityClass = (Class<BillInvCostAdjustDtl>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		List<BillInvCostAdjustDtl> tl = new ArrayList<BillInvCostAdjustDtl>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillInvCostAdjustDtl type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
			tl.add(type);
		}
		return tl;
	}

	@Override
	protected String[] getImportProperties() {
		return new String[] { "itemCode","brandNo","adjustCost"};
	}

	private List<BillInvCostAdjustDtl> initDefaultInfo(List<BillInvCostAdjustDtl> list, HttpServletRequest request,HashMap<String, Item> temp) throws ManagerException {
		List<BillInvCostAdjustDtl> result = new ArrayList<BillInvCostAdjustDtl>();
		SystemUser currentUser = CurrentUser.getCurrentUser();
		String companyNo = request.getParameter("companyNo");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		
		List<String> itemNos = new ArrayList<String>();
		for(BillInvCostAdjustDtl dtl:list){
			Item item = temp.get(dtl.getItemCode()+dtl.getBrandNo());
			dtl.setItemNo(item.getItemNo());
			dtl.setItemName(item.getName());
			dtl.setSizeKind(item.getSizeKind());
			
			dtl.setId(UUIDGenerator.getUUID());
			dtl.setBillNo(request.getParameter("billNo"));
			dtl.setShardingFlag(currentUser.getOrganTypeNo() + "_" + companyNo.substring(0, 1));
			if(!itemNos.contains(dtl.getItemNo())){
				itemNos.add(dtl.getItemNo());
			}
		}
		Map<String, Object> existMap = new HashMap<String, Object>();
		existMap.put("itemNos", itemNos);
		existMap.put("shardingFlag", currentUser.getOrganTypeNo() + "_" + request.getParameter("companyNo").substring(0, 1));
		existMap.put("billNo", request.getParameter("billNo"));
		List<BillInvCostAdjustDtl> existList = billInvCostAdjustDtlManager.findByBiz(null, existMap);
		
		List<BillInvCostAdjustDtl> effectList = new ArrayList<BillInvCostAdjustDtl>();
		if(existList.size() > 0){
			for(BillInvCostAdjustDtl dtl:list){
				for(BillInvCostAdjustDtl exist:existList){
					if(!dtl.getItemNo().equals(exist.getItemNo())){
						effectList.add(dtl);
					}
				}
			}
		}else{
			effectList = list;
		}
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNos", itemNos);
		params.put("companyNo", companyNo);
		params.put("year", year);
		params.put("month", month);
		List<ItemCost> itemCosts = itemCostManager.findByBiz(null, params);
		Map<String, ItemCost> itemCostInfo = new HashMap<String, ItemCost>();
		for(ItemCost itemCost:itemCosts){
			itemCostInfo.put(itemCost.getItemNo()+itemCost.getBrandNo(), itemCost);
		}
		List<CompanyPeriodBalance> companyPeriodBalances = companyPeriodBalanceManager.findByBiz(null, params);
		Map<String, CompanyPeriodBalance> companyPeriodBalanceInfo = new HashMap<String, CompanyPeriodBalance>();
		for(CompanyPeriodBalance cpb:companyPeriodBalances){
			companyPeriodBalanceInfo.put(cpb.getItemNo()+cpb.getBrandNo(), cpb);
		}
		
		for(BillInvCostAdjustDtl dtl:effectList){
			ItemCost ic = itemCostInfo.get(dtl.getItemNo()+dtl.getBrandNo());
			BigDecimal unitCost = ic==null?BigDecimal.ZERO:ic.getUnitCost();
			dtl.setUnitCost(unitCost);
			//根据公司+itemNo+年月汇总对应的期末库存
			CompanyPeriodBalance cpb = companyPeriodBalanceInfo.get(dtl.getItemNo()+dtl.getBrandNo());
			if(null != cpb) {
				dtl.setClosingQty(cpb.getOpeningQty() + cpb.getPurchaseInQty() + cpb.getPurchaseReturnQty() + cpb.getOuterTransferInQty());
			}else {
				dtl.setClosingQty(0);
			}
			
			result.add(dtl);
		}
		return result;
	}
	
	private Object getCellValue(Cell cell){  
        Object value = null; 
        if(cell==null){
        	  return "";  
		}
        //简单的查检列类型  
        switch(cell.getCellType()) {  
            case HSSFCell.CELL_TYPE_STRING://字符串  
                value = cell.getRichStringCellValue().getString();  
                break;  
            case HSSFCell.CELL_TYPE_NUMERIC://数字  
            	value = cell.getNumericCellValue();  
                break;  
            case HSSFCell.CELL_TYPE_BLANK:  
                value = "";  
                break;     
            case HSSFCell.CELL_TYPE_FORMULA:  
                value = String.valueOf(cell.getCellFormula());  
                break;  
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值  
                value = cell.getBooleanCellValue();  
                break;  
            case HSSFCell.CELL_TYPE_ERROR:  
                value = String.valueOf(cell.getErrorCellValue());  
                break;  
            default:  
                break;  
        }  
        return value;  
    }  
	
	private void setValue(BillInvCostAdjustDtl obj, String propertyName, Object value) throws Exception {
		Field field = obj.getClass().getDeclaredField(propertyName);
		value = this.convertValue(field, value);
		Method method = obj.getClass().getMethod("set" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1), field.getType());
		method.invoke(obj, value);
	}
	
	private Object convertValue(Field field, Object value) {
		String typeClassName = field.getType().getName();
		if(typeClassName.equals(Integer.class.getName())) {
			if(value instanceof Double) {
				value = ((Double)value).intValue(); 
			}else {
				value = Integer.parseInt(value.toString());
			}
		} else if(typeClassName.equals(Date.class.getName())) {
			JsonSerialize js = field.getAnnotation(JsonSerialize.class);
	    	if(js.using().getName().equals(JsonDateSerializer$10.class.getName())) {
	    		value = DateUtil.parseToDate(value.toString(), "yyyy-MM-dd");
	    	} else if(js.using().getName().equals(JsonDateSerializer$19.class.getName())) {
	    		value = DateUtil.parseToDate(value.toString(), "yyyy-MM-dd HH:mm:ss");
	    	}
		} else if(typeClassName.equals(Double.class.getName())) {
			value = Integer.parseInt(value.toString());
		} else if(typeClassName.equals(Float.class.getName())) {
			value = Float.parseFloat(value.toString());
		}else if(typeClassName.equals(BigDecimal.class.getName())){
			value = new BigDecimal(value.toString());
		} else {
			value = value.toString().trim();
		}
		return value;
	}

	@Override
	protected boolean doBatchAdd(List<BillInvCostAdjustDtl> list) {
		// TODO Auto-generated method stub
		return false;
	}

}