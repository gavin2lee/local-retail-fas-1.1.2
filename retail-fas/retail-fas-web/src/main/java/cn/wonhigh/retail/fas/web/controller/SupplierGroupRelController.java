package cn.wonhigh.retail.fas.web.controller;

import java.lang.reflect.ParameterizedType;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.model.SupplierGroupRel;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.SupplierGroupRelManager;
import cn.wonhigh.retail.fas.manager.SupplierManager;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-25 17:35:45
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
@RequestMapping("/supplier_group_rel")
public class SupplierGroupRelController extends BaseController<SupplierGroupRel> {
	
    @Resource
    private SupplierGroupRelManager supplierGroupRelManager;
    
    @Resource
    private SupplierManager supplierManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("supplier_group_rel/",supplierGroupRelManager);
    }
    
    @RequestMapping(value = "/query_supplier_group_dtl")
	@ResponseBody
	public List<SupplierGroupRel> querySettleCategoryDtl(@RequestParam("groupNo")String groupNo, 
			HttpServletRequest request) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupNo", groupNo);
		List<SupplierGroupRel> list = this.supplierGroupRelManager.findByBiz(null, params);
		return list;
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
	public ModelAndView doImport(HttpServletRequest request, SupplierGroupRel modelType)throws Exception{
   	ModelAndView mv = new ModelAndView("/print/import");
   	try {
   		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);

   		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<SupplierGroupRel> entityClass = (Class<SupplierGroupRel>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<SupplierGroupRel> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), SupplierGroupRel.class,entityClass.getSimpleName());
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
	    		for (SupplierGroupRel supplierGroupRel : datas) {
	    			supplierGroupRel.setCreateUser(loginUser.getUsername());
	    			supplierGroupRel.setCreateTime(new Date());
	    			this.supplierGroupRelManager.add(supplierGroupRel);
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
	private List<Object> getDataValidators(List<SupplierGroupRel> datas, HttpServletRequest request) throws ManagerException{
		try {
			List<Object> errors = new ArrayList<Object>();
			String groupNo = request.getParameter("groupNo");
			
			if(datas != null && datas.size() > 0) {
				Map<String, Object> supplierMap = new HashMap<String, Object>();
				Map<String, String> uniqueCheckMap = new HashMap<String, String>();
				int lineNum = 1;
				int count = 0;
				String errorMessage = null;
				List<Supplier> suppliers = null;
				
				for(SupplierGroupRel supplierGroupRel : datas) {
					//判断空数据
					if (StringUtils.isEmpty(supplierGroupRel.getGroupNo()) || StringUtils.isEmpty(supplierGroupRel.getSupplierNo()) 
							|| StringUtils.isEmpty(supplierGroupRel.getSupplierName())) {
						errorMessage = "第" + lineNum + "行数据有空记录";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					if (!(groupNo.equals(supplierGroupRel.getGroupNo()))) {
						errorMessage = "第" + lineNum + "行数据的供应商组编码错误";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//数据唯一性校验
					supplierMap.put("supplierNo", supplierGroupRel.getSupplierNo());
					suppliers = supplierManager.findByBiz(null, supplierMap);
					if (CollectionUtils.isEmpty(suppliers)) {
						errorMessage = "第" + lineNum + "行数据的供应商编码不存在";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					
					if (!(suppliers.get(0).getShortName().equals(supplierGroupRel.getSupplierName()))) {
						errorMessage = "第" + lineNum + "行数据的供应商名称不正确";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					
					count = this.supplierGroupRelManager.findCount(supplierMap);
					if (count > 0) {
						errorMessage = "第" + lineNum + "行数据的供应商编码已经存在于供应商组";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					
					if(uniqueCheckMap.containsKey(supplierGroupRel.getSupplierNo())) {
						errorMessage = "第" + lineNum + "行数据的供应商编码已经存在于导入列表";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					} else {
						uniqueCheckMap.put(supplierGroupRel.getSupplierNo(), supplierGroupRel.getSupplierNo());
					}
					lineNum ++;
				}
			}
			return errors;
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
}