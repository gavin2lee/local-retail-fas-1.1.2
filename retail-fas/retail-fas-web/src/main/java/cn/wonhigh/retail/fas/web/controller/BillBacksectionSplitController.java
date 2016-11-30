package cn.wonhigh.retail.fas.web.controller;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplit;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.SpecialZoneInfo;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.BillBacksectionSplitDtlManager;
import cn.wonhigh.retail.fas.manager.BillBacksectionSplitManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.InvoiceInfoManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:36
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
@RequestMapping("/bill_backsection_split")
@ModuleVerify("30140013")
public class BillBacksectionSplitController extends ExcelImportController<BillBacksectionSplit> {
    @Resource
    private BillBacksectionSplitManager billBacksectionSplitManager;
    
    @Resource
    private BillBacksectionSplitDtlManager billBacksectionSplitDtlManager;
    
    @Resource
    private InvoiceInfoManager invoiceInfoManager;
    
    @Resource
    private CompanyManager companyManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_backsection_split/",billBacksectionSplitManager);
    }
    
//    @RequestMapping("/list")
//   	public String list() {
//   		return "mallshop_balance/bill_backsection_split";
//   	}
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/bill_backsection_split";
   	}
    
	@RequestMapping("/bill_backsection_split_dtl")
	public String bill_backsectionSplit() {
		return "mallshop_balance/bill_backsection_split_dtl";
	}
	
//	@RequestMapping("/post")
//	public ResponseEntity<BillBacksectionSplit> add(BillBacksectionSplit obj) throws ManagerException {
//		obj.setBacksectionNo("cccc");
//		billBacksectionSplitManager.add(obj);
//		String billNo = obj.getBacksectionNo();
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("backsectionNo", billNo);
//		List<BillBacksectionSplit> list = billBacksectionSplitManager.findByBiz(null, params);
//		return new ResponseEntity<BillBacksectionSplit>(list.get(0), HttpStatus.OK);
//	}
	
  	/**
   	 * 删除
   	 * @return
   	 * @throws Exception 
   	 */
   	@RequestMapping(value = "/deleteBacksectionSplit")
   	@ResponseBody
   	public Integer remove(@RequestParam("idList")String strIds) throws Exception{
   		String[] ids = strIds.split(";");
   		return  billBacksectionSplitManager.remove(ids);
   	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<BillBacksectionSplit> list) {
		// TODO Auto-generated method stub
		return false;
	} 
	
	
	
	/**
	 * 校验是否可被修改、删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/check_update")
    @ResponseBody
    public Map<String, Object> checkUpdate(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isUpdate", billBacksectionSplitManager.checkIsUpdate(request.getParameter("id")));
		return result;
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
	public ModelAndView doImport(HttpServletRequest request, BillBacksectionSplit modelType)throws Exception{
		ModelAndView mv = new ModelAndView("/print/import");
		try {
			SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<BillBacksectionSplit> entityClass = (Class<BillBacksectionSplit>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<BillBacksectionSplit> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), BillBacksectionSplit.class,entityClass.getSimpleName());
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
	    		for (BillBacksectionSplit billBacksectionSplit : datas) {
	    			
	    			billBacksectionSplit.setStatus(0);
	    			billBacksectionSplit.setCreateUser(loginUser.getUsername());
	    			billBacksectionSplit.setCreateTime(new Date());
	    			
	    			//新增回款单表头
	    			ResponseEntity<BillBacksectionSplit> resEntiy = this.add(billBacksectionSplit);
	    			
	    			//新增回款单明细
	    			Map<String, Object> params = new HashMap<String, Object>();
	    			params.put("id", resEntiy.getBody().getId());
	    			billBacksectionSplitDtlManager.selectAddInsertDtl(params);
	    			
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
			mv.addObject("error", "导入数据的回款金额不是数字格式！");
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
	private List<Object> getDataValidators(List<BillBacksectionSplit> datas, HttpServletRequest request) throws ManagerException{
		List<Object> errors = new ArrayList<Object>();
		if(datas != null && datas.size() > 0) {
			
			int lineNum = 2;
			String errorMessage = null;
			
			for(BillBacksectionSplit billBacksectionSplit : datas) {
				//判断空数据
				if (StringUtils.isEmpty(billBacksectionSplit.getCompanyNo()) || StringUtils.isEmpty(billBacksectionSplit.getBackNo()) 
						|| null == billBacksectionSplit.getBackDate() || null == billBacksectionSplit.getBackAmount()) {
					errorMessage = "第" + lineNum + "行数据有空记录";
					errors.add(errorMessage);
					lineNum ++;
					continue;
				}
				
				//判断公司是否存在，同时检查权限
				Map<String, Object> paramsCom = new HashMap<String, Object>();
				paramsCom.put("companyNo", billBacksectionSplit.getCompanyNo());
				List<Company> companyList = companyManager.findByBiz(null, paramsCom);
				if (CollectionUtils.isEmpty(companyList)) {
					errorMessage = "第" + lineNum + "行数据的公司编号不存在";
					errors.add(errorMessage);
					lineNum ++;
					continue;
				}
				
				//判断开票信息是否维护
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("companyNo", billBacksectionSplit.getCompanyNo());
				params.put("clientNo", billBacksectionSplit.getBackNo());
				List<InvoiceInfo> invoiceInfoList = invoiceInfoManager.findByBiz(null, params);
				if (CollectionUtils.isEmpty(invoiceInfoList)) {
					errorMessage = "第" + lineNum + "行数据对应的开票信息未维护";
					errors.add(errorMessage);
					lineNum ++;
					continue;
				}
				else {
					billBacksectionSplit.setCompanyName(invoiceInfoList.get(0).getCompanyName());
					billBacksectionSplit.setBackName(invoiceInfoList.get(0).getClientName());
				}
				
				lineNum ++;
			}
			
		}
		
		return errors;
	}
	
}