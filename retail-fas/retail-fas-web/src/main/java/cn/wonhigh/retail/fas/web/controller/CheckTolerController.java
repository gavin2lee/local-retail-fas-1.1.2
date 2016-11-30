package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.CheckToler;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.CheckTolerManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 对账容差设置
 * @author tan.y
 * @date  2016-04-12 15:44:38
 * @version 1.0.0
 * @copyright (C) 2016 Belle Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the Belle technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/check_toler")
@ModuleVerify("40001018")
public class CheckTolerController extends BaseController<CheckToler> {

	@Resource
	private CheckTolerManager checkTolerManager;

	@Resource
	private CommonUtilController commonUtilController;

	@Override
	public CrudInfo init() {
		return new CrudInfo("check_toler/", checkTolerManager);
	}

//	/**
//	 * 批量修改的方法
//	 * @param req HttpServletRequest
//	 * @return ResponseEntity<Map<String, Boolean>>
//	 * @throws JsonParseException 异常
//	 * @throws JsonMappingException 异常
//	 * @throws IOException 异常
//	 * @throws ManagerException 异常
//	 */
//	@RequestMapping(value = "/save")
//	@ResponseBody
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public ResponseEntity<Map<String, Boolean>> save(HttpServletRequest req) throws JsonParseException,
//			JsonMappingException, IOException, ManagerException {
//		String errorMsg = "";
//		Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
//		Map resultMap = new HashMap();
//		Map<CommonOperatorEnum, List<CheckToler>> params = null;
//		JsonUtil<CheckToler> util = new JsonUtil<CheckToler>();
//		try {
//			params = util.convertToMap(req, CheckToler.class);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			resultMap.put("false", Boolean.valueOf(false));
//			return new ResponseEntity(resultMap, HttpStatus.OK);
//		}
//
//		List<CheckToler> checkTolerList = params.get(CommonOperatorEnum.INSERTED);
//		if (null != checkTolerList) {
//			if (validateCompanyNoOrClientNo(checkTolerList)) {
//				errorMsg += "保存数据中不能存在公司与供应商相同的多条数据。";
//			} else {
//				for (int i = 0; i < checkTolerList.size(); i++) {
//					CheckToler checkToler = checkTolerList.get(i);
//					//数据唯一性校验 公司和供应商组合只允许有一条
//					uniqueCheckMap.put("companyNo", checkToler.getCompanyNo());
//					uniqueCheckMap.put("supplierNo", checkToler.getSupplierNo());
//					int count = checkTolerManager.findCount(uniqueCheckMap);
//					if (count > 0) {
//						errorMsg += "已存在公司：" + checkToler.getCompanyName() + "与供应商：" + checkToler.getSupplierName()
//								+ "组合的数据。<br/>";
//						checkTolerList.remove(checkToler);
//					}
//				}
//			}
//			if (errorMsg != "") {
//				errorMsg = "保存失败!<br/>" + errorMsg;
//				resultMap.put("message", errorMsg);
//			}
//			params.put(CommonOperatorEnum.INSERTED, checkTolerList);
//		}
//
//		if (params.size() > 0) {
//			checkTolerManager.save(params);
//		}
//		resultMap.put("success", Boolean.valueOf(true));
//
//		return new ResponseEntity(resultMap, HttpStatus.OK);
//	}

	/**
	 * 判断保存数据中是否存在相同数据
	 * @param checkTolerList
	 * @return
	 */
	private boolean validateCompanyNoOrClientNo(List<CheckToler> checkTolerList) {
		for (int i = 0; i < checkTolerList.size() - 1; i++) {
			CheckToler checkTolerOne = (CheckToler) checkTolerList.get(i);
			for (int j = i + 1; j < checkTolerList.size(); j++) {
				CheckToler checkTolerTwo = (CheckToler) checkTolerList.get(j);
				if (checkTolerOne.getCompanyNo().equals(checkTolerTwo.getCompanyNo())
						&& checkTolerOne.getSupplierNo().equals(checkTolerTwo.getSupplierNo())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export_data")
	public void export(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dataList = checkTolerManager.findByPage(page, "", "", params);

		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}

	/**
	 * 导入
	 * @param file
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public Map<String, Object> doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)
			throws Exception {
		String[] fieldNames = null;
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		fieldNames = new String[] { "companyNo", "supplierNo", "pretaxTolerLow", "pretaxTolerUp", "aftertaxTolerLow",
				"aftertaxTolerUp","notaxTolerLow", "notaxTolerUp", "effectiveDate" };
		lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "supplierNo", "supplierName", "供应商编码",
				true));
		lstValidate
				.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "companyNo", "companyName", "公司编码", true));

		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), CheckToler.class, fieldNames);
		List<ValidateResultVo> listValidate = commonUtilController.doValidate(lstItem, lstValidate, CheckToler.class);
		SystemUser user = CurrentUser.getCurrentUser();
		for (ValidateResultVo resultVo : listValidate) {
			this.importValidate(resultVo, req);
			if (resultVo.getPass() == YesNoEnum.YES.getValue()) {
				CheckToler obj = (CheckToler) resultVo.getValidateObj();
				obj.setCreateUser(user.getUsername());
				obj.setCreateTime(new Date());
				checkTolerManager.add(obj);
			}
		}
		map.put("success", true);
		map.put("rows", listValidate);
		return map;
	}

	private void importValidate(ValidateResultVo resultVo, HttpServletRequest req) {
		if (resultVo.getPass() == YesNoEnum.NO.getValue()) {
			return;
		}
		try {

			CheckToler checkToler = (CheckToler) resultVo.getValidateObj();
//			Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();
			Map<String, String> amountNameMap = new HashMap<String, String>();
			
			amountNameMap.put("pretaxTolerLow","不含税容差金额>=");
			amountNameMap.put("pretaxTolerUp","不含税容差金额<=");
			amountNameMap.put("aftertaxTolerLow","含税容差金额>=");
			amountNameMap.put("aftertaxTolerUp ","含税容差金额<=");
			amountNameMap.put("notaxTolerLow","不含税*1.17与含税容差(厂商)>=");
			amountNameMap.put("notaxTolerUp","不含税*1.17与含税容差(厂商)<=");
			
//			uniqueCheckMap.put("companyNo", checkToler.getCompanyNo());
//			uniqueCheckMap.put("supplierNo", checkToler.getSupplierNo());
//			int count = checkTolerManager.findCount(uniqueCheckMap);
			StringBuffer error = new StringBuffer();
//			if (count > 0) {
//				error.append("&公司编号和供应商编号的组合已经存在");
//			}
			error.append(checkAmountLength(amountNameMap, checkToler));
			
			if (StringUtils.isNotEmpty(error.toString())) {
				resultVo.setPass(YesNoEnum.NO.getValue());
				resultVo.setErrorInfo(error.toString());
			}

		} catch (Exception e) {
			resultVo.setPass(YesNoEnum.NO.getValue());
			resultVo.setErrorInfo("请检查数据是否有效性)");
		}

	}

	/**
	 * 检查金额长度
	 * @param amountNameMap
	 * @return
	 */
	private StringBuffer checkAmountLength(Map<String, String> amountNameMap, CheckToler checkToler){
		StringBuffer result = new StringBuffer("");
		for (String key : amountNameMap.keySet()) {
			Object value = getFieldValueByName(key, checkToler);
			if(null != value){
				String amount = String.valueOf(value);
				if (amount.length() > 10) {
					result.append("&请检查");
					result.append(amountNameMap.get(key));
					result.append("是否设置正确(长度不超过十位的数字)");
				}
			}
			
		}
		return result;
	}
	
	/**
	 * 根据属性名称获取属性的值
	 * @param fieldName
	 * @param o
	 * @return
	 */
	private Object getFieldValueByName(String fieldName,CheckToler checkToler) {
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = checkToler.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(checkToler, new Object[] {});  
            return value;  
        } catch (Exception e) {  
            logger.error(e.getMessage(),e);  
            return null;  
        }  
    } 

}