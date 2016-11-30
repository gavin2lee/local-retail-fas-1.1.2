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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.BillInvCostAdjustDtlManager;
import cn.wonhigh.retail.fas.manager.BillInvCostAdjustManager;
import cn.wonhigh.retail.fas.manager.CompanyPeriodBalanceManager;
import cn.wonhigh.retail.fas.manager.ItemCostManager;
import cn.wonhigh.retail.fas.manager.ItemManager;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/bill_inv_cost_adjust_dtl_blk")
public class BillInvCostAdjustDtlBLKController extends ExcelImportController<BillInvCostAdjustDtl>  {
	
	@Resource
	private BillInvCostAdjustDtlManager billInvCostAdjustDtlManager;
	
	@Resource
	private BillInvCostAdjustManager billInvCostAdjustManager;
	
	@Resource
	private ItemManager itemManager;
	
	@Resource
	private ItemCostManager itemCostManager;
	
	@Resource
	private CompanyPeriodBalanceManager companyPeriodBalanceManager;
	
	@Override
	protected CrudInfo init() {
		return new CrudInfo("bill_inv_cost_adjust_dtl_blk/",billInvCostAdjustDtlManager);
	}
	
	@Override
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		
		int count = billInvCostAdjustDtlManager.findListBLKcount(params);
		List<BillInvCostAdjustDtl> list = new ArrayList<BillInvCostAdjustDtl>();
		if(count > 0){
			SimplePage page = new SimplePage(pageNo,pageSize,count);
			list = billInvCostAdjustDtlManager.findListBLK(page, null, null, params);
		}
		
		map.put("total", count);
		map.put("rows", list);

		return map;
	}
	
    @SuppressWarnings("rawtypes")
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
			List<BillInvCostAdjustDtl> deleteList = new ArrayList<BillInvCostAdjustDtl>();
			//设置id
			for(BillInvCostAdjustDtl dtl:oList){
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("billNo", billNo);
				temp.put("itemNo", dtl.getItemNo());
				temp.put("brandNo", dtl.getBrandNo());
				List<BillInvCostAdjustDtl> tempList = billInvCostAdjustDtlManager.findByBiz(null, temp);
				deleteList.addAll(tempList);
			}
			
			params.put(CommonOperatorEnum.DELETED, deleteList);
		}
		if (StringUtils.isNotEmpty(upadtedList)) {
			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {
			});
			List<BillInvCostAdjustDtl> oList = convertListWithTypeReference(mapper, list);
			params.put(CommonOperatorEnum.UPDATED, setProperties(oList, companyNo, year, month));
		}
		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
			});
			List<BillInvCostAdjustDtl> oList = convertListWithTypeReference(mapper, list);
			params.put(CommonOperatorEnum.INSERTED, setProperties(oList, companyNo, year, month));
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
    
    private List<BillInvCostAdjustDtl> setProperties(List<BillInvCostAdjustDtl> list,String companyNo,String year,String month){
    	Map<String, Object> params = new HashMap<String, Object>();
    	try {
			params.put("comapnyNo", companyNo);
			params.put("year", year);
			params.put("month", month);
			for(BillInvCostAdjustDtl dtl:list){
				params.put("itemNo", dtl.getItemNo());
				PeriodBalance periodBalance = billInvCostAdjustManager.findPeriodBalance(params);
				Integer closingQty = 0;
				if(null != periodBalance){
					closingQty = periodBalance.getOpeningQty()+periodBalance.getPurchaseInQty()+periodBalance.getOuterTransferInQty()+periodBalance.getPurchaseReturnQty();
				}
				dtl.setClosingQty(closingQty);
				params.remove("itemNo");
			}
		} catch (ManagerException e) {
			e.printStackTrace();
		}
    	
    	return list;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List<BillInvCostAdjustDtl> convertListWithTypeReference(ObjectMapper mapper, List<Map> list) {
    	List<BillInvCostAdjustDtl> tl = new ArrayList<BillInvCostAdjustDtl>();
		Class<BillInvCostAdjustDtl> entityClass = (Class<BillInvCostAdjustDtl>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		try {
			for (int i = 0; i < list.size(); i++) {
				String styleNo = (String) list.get(i).get("styleNo");
				Map<String, Object> params = new HashMap<String,Object>();
				params.put("queryCondition", " AND style_no = '" + styleNo + "'");
				List<Item> items = itemManager.findByBiz(null, params);
				for(Item item:items){
					Map<String, Object> map = list.get(i);
					map.remove("styleNo");
					map.put("itemNo", item.getItemNo());
					map.put("itemCode", item.getCode());
					map.put("itemName", item.getName());
					BillInvCostAdjustDtl type = mapper.readValue(mapper.writeValueAsString(map), entityClass);
					tl.add(type);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tl;
	}
	
	@RequestMapping(value = "/import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView("/print/import");
		List<UploadMessageModel> msgList = new ArrayList<UploadMessageModel>();
		List<BillInvCostAdjustDtl> list = new ArrayList<BillInvCostAdjustDtl>();
		Map<String, List<Item>> items = new HashMap<String, List<Item>>();
		try {
			InputStream inputStream = file.getInputStream();
			//创建Excel工作薄  
			Workbook wb = create(inputStream);  
			//得到第一个工作表  
			Sheet sheet = wb.getSheetAt(0); 
			BillInvCostAdjustDtl model = null;
			int rowCount = sheet.getPhysicalNumberOfRows();
			//先读取所有的style列表
			for(int i = 2; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				model = BillInvCostAdjustDtl.class.newInstance();
				for(int j = 0; j < row.getLastCellNum(); j++) {
					Object value = getCellValue(row.getCell(j));
					if(StringUtils.isBlank(String.valueOf(value))) {
						continue;
					}
					setValue(model, getImportProperties()[j], value);
				}
				UploadMessageModel msgModel = validateModel(model,i, items);
				if(msgModel == null || msgModel.isFlag()) {
					list.add(model);
				} else { //否则将其加入记录错误信息的集合中
					msgList.add(msgModel);
				}
			}
			
			if(msgList.size() <= 0){
				list = this.initDefaultInfo(list, request, items);
				doAdd(list);
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
	
	@Override
	protected List<BillInvCostAdjustDtl> queryExportData(Map<String, Object> params) throws ManagerException {
		int count = billInvCostAdjustDtlManager.findListBLKcount(params);
		List<BillInvCostAdjustDtl> list = new ArrayList<BillInvCostAdjustDtl>();
		if(count > 0){
			SimplePage page = new SimplePage(1,count,count);
			list = billInvCostAdjustDtlManager.findListBLK(page, null, null, params);
		}
		return list;
	}
	
	protected UploadMessageModel validateModel(BillInvCostAdjustDtl dtl, int rowIndex,Map<String, List<Item>> itemList){
		UploadMessageModel uploadMessageModel= null;
		try {
			if (null != dtl) {
				//校验必填项
				if(dtl.getStyleNo()==null || dtl.getBrandNo()==null || dtl.getAdjustCost()==null){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 信息不完整！");
					return uploadMessageModel;
				}
				if(!dtl.getBrandNo().startsWith("BA") && !dtl.getBrandNo().startsWith("RE")){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 请填写正确的品牌编码！");
					return uploadMessageModel;
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("queryCondition", " and style_no = '"+dtl.getStyleNo()+"' and brand_no = '"+dtl.getBrandNo()+"'");
				List<Item> items = itemManager.findByBiz(null, params);
				if(items.size() <= 0){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 款号与品牌编码不匹配！");
					return uploadMessageModel;
				}
				itemList.put(dtl.getStyleNo()+dtl.getBrandNo(), items);
				return uploadMessageModel;
			} else {
				return super.validateModel(dtl, rowIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return uploadMessageModel;
	}
	
	private boolean doAdd(List<BillInvCostAdjustDtl> list) throws ManagerException {
		try {
			if (list == null || list.size() < 1) {
				return false;
			}
			Map<CommonOperatorEnum, List<BillInvCostAdjustDtl>> params = new HashMap<CommonOperatorEnum, List<BillInvCostAdjustDtl>>();
			params.put(CommonOperatorEnum.INSERTED, list);
			billInvCostAdjustDtlManager.save(params);
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return true;
		
	}

	private List<BillInvCostAdjustDtl> initDefaultInfo(List<BillInvCostAdjustDtl> list, HttpServletRequest request,Map<String, List<Item>> temp) throws ManagerException {
		List<BillInvCostAdjustDtl> result = new ArrayList<BillInvCostAdjustDtl>();
		SystemUser currentUser = CurrentUser.getCurrentUser();
		String companyNo = request.getParameter("companyNo");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		
		List<String> itemNos = new ArrayList<String>();
		List<BillInvCostAdjustDtl> dtls = new ArrayList<BillInvCostAdjustDtl>();
		for(BillInvCostAdjustDtl dtl:list){
			List<Item> items = temp.get(dtl.getStyleNo()+dtl.getBrandNo());
			for(Item item:items){
				BillInvCostAdjustDtl obj = new BillInvCostAdjustDtl();
				obj.setItemNo(item.getItemNo());
				obj.setItemCode(item.getCode());
				obj.setItemName(item.getName());
				obj.setBrandNo(dtl.getBrandNo());
				obj.setSizeKind(item.getSizeKind());
				obj.setAdjustCost(dtl.getAdjustCost());
				obj.setUnitCost(dtl.getUnitCost());
				
				obj.setId(UUIDGenerator.getUUID());
				obj.setBillNo(request.getParameter("billNo"));
				obj.setShardingFlag(currentUser.getOrganTypeNo() + "_" + companyNo.substring(0, 1));
				if(!itemNos.contains(obj.getItemNo())){
					itemNos.add(obj.getItemNo());
				}
				dtls.add(obj);
			}
			
		}
		Map<String, Object> existMap = new HashMap<String, Object>();
		existMap.put("itemNos", itemNos);
		existMap.put("shardingFlag", currentUser.getOrganTypeNo() + "_" + request.getParameter("companyNo").substring(0, 1));
		existMap.put("billNo", request.getParameter("billNo"));
		List<BillInvCostAdjustDtl> existList = billInvCostAdjustDtlManager.findByBiz(null, existMap);
		
		List<BillInvCostAdjustDtl> effectList = new ArrayList<BillInvCostAdjustDtl>();
		if(existList.size() > 0){
			for(BillInvCostAdjustDtl dtl:dtls){
				for(BillInvCostAdjustDtl exist:existList){
					if(!dtl.getItemNo().equals(exist.getItemNo())){
						effectList.add(dtl);
					}
				}
			}
		}else{
			effectList = dtls;
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
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(key, value)
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
	protected String[] getImportProperties() {
		return new String[] { "styleNo","brandNo","adjustCost"};
	}

	@Override
	protected boolean doBatchAdd(List<BillInvCostAdjustDtl> list) throws ManagerException {
		return false;
	}


}
