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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.model.ToRegister;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.BsgroupsManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.CustomerManager;
import cn.wonhigh.retail.fas.manager.MallManager;
import cn.wonhigh.retail.fas.manager.SupplierManager;
import cn.wonhigh.retail.fas.manager.ToRegisterManager;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-03-31 14:45:27
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
@RequestMapping("/refuse_register")
@ModuleVerify("40001009")
public class ToRegisterController extends BaseController<ToRegister> {
    @Resource
    private ToRegisterManager toRegisterManager;
    @Resource
    private CompanyManager companyManager;
    @Resource
    private SupplierManager supplierManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("refuse_register/",toRegisterManager);
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, ToRegister modelType)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	try {
    		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<ToRegister> entityClass = (Class<ToRegister>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<ToRegister> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), ToRegister.class,entityClass.getSimpleName());
			//验证导入数据源中是否存在多条相同公司和客户的数据
			//validateMultipleShop(datas);
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
	    		for (ToRegister hqCostMaintain : datas) {
	    			hqCostMaintain.setCreateTime(new Date());
	    			hqCostMaintain.setCreateUser(loginUser.getUsername());
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
			mv.addObject("error", "导入数据的拒付登记不是数字格式！");
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
     * @throws IOException 
	 */
	private List<Object> getDataValidators(List<ToRegister> datas, HttpServletRequest request) throws ManagerException, IOException{
		List<Object> errors = new ArrayList<Object>();
		try {
				if(datas != null && datas.size() > 0) {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					int lineNum = 2;
					List<Company>  companyList = null;
					List<Supplier> supplierList = null;
					String errorMessage = null;
					for(ToRegister costMaintain : datas) {
						//判断空数据
						if (StringUtils.isEmpty(costMaintain.getSupplierNo()) || null == costMaintain.getCompanyNo()
								|| null == costMaintain.getPaymentDate()|| null == costMaintain.getPaymentAmount()) {
							errorMessage = "第" + lineNum + "行数据有空记录";
							errors.add(errorMessage);
							lineNum ++;
							continue;
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
						paramsMap.put("supplierNo", costMaintain.getSupplierNo());
						supplierList = supplierManager.findByBiz(null, paramsMap);
						if (supplierList.size()==0) {
							errorMessage = "第" + lineNum + "行数据的供应商编码不存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
						}else{
							costMaintain.setSupplierName(supplierList.get(0).getFullName());
						}
						lineNum ++;
					}
				}
			} catch (ManagerException e) {
				logger.error(e.getMessage(), e);
				throw new IOException(e.getMessage(), e);
			}
			return errors;
		}
}