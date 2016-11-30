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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.model.SupplierRateSet;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.SupplierManager;
import cn.wonhigh.retail.fas.manager.SupplierRateSetManager;
import cn.wonhigh.retail.fas.web.utils.AreaAmongBalanceExportUtils;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;
import cn.wonhigh.retail.mdm.api.util.CacheContext;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author user
 * @date 2016-05-05 09:10:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the WonHigh technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@ModuleVerify("34000001")
@RequestMapping("/supplier_rate_set")
public class SupplierRateSetController extends BaseController<SupplierRateSet> {
	@Resource
	private SupplierRateSetManager supplierRateSetManager;

	@Resource
	private SupplierManager supplierManager;

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SupplierRateSetController.class);

	@Override
	public CrudInfo init() {
		return new CrudInfo("supplier_rate_set/", supplierRateSetManager);
	}

	// Excel导入功能
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(HttpServletRequest request, SupplierRateSet modelType)throws Exception{
		ModelAndView mv = new ModelAndView("/print/import");

	   	try {
	   		SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
	   		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile excelFile = multipartRequest.getFile("fileData");
				Class<SupplierRateSet> entityClass = (Class<SupplierRateSet>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
				// 忽略在excel有字段填写的值
				List<String> ignoreProperties = Lists.newArrayList();
				for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
					ignoreProperties.add(entry.getKey());
				}
				List<SupplierRateSet> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), SupplierRateSet.class,entityClass.getSimpleName());
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
		    		for (SupplierRateSet supplierTariffSet : datas) {
		    			CacheContext context = CacheContext.current();
		    			supplierTariffSet.setSupplierNo(supplierTariffSet.getSupplierNo());
		    			supplierTariffSet.setVatRate(supplierTariffSet.getVatRate());
		    			supplierTariffSet.setCurrencyCode(supplierTariffSet.getCurrencyCode());
		    			supplierTariffSet.setSupplierName(context.getSupplierName(supplierTariffSet.getSupplierNo()));
		    			supplierTariffSet.setCreateUser(loginUser.getUsername());
		    			supplierTariffSet.setCreateTime(new Date());
		    			
		    			this.add(supplierTariffSet);
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
	private List<Object> getDataValidators(List<SupplierRateSet> datas, HttpServletRequest request) throws ManagerException{
		try {
			List<String> duplicateMap = new ArrayList<String>();
			List<Object> errors = new ArrayList<Object>();
			if(datas != null && datas.size() > 0) {
				Map<String, Object> uniqueCheckMap = new HashMap<String, Object>();

				//获取所有供应商
				List<Supplier> listSupplier = supplierManager.findByBiz(null, null);
				List<String> supplierNos = new ArrayList<String>();
				if (!CollectionUtils.isEmpty(listSupplier)) {
					for (Supplier supplier : listSupplier) {
						supplierNos.add(supplier.getSupplierNo());
					}
				}
				int lineNum = 2;
				int count = 0;
				String errorMessage = null;
				for(SupplierRateSet supplierTariffSet : datas) {
					//判断空数据
					if (StringUtils.isEmpty(supplierTariffSet.getSupplierNo()) || null == supplierTariffSet.getCurrencyCode() 
							|| null == supplierTariffSet.getVatRate() ) {
						errorMessage = "第" + lineNum + "行数据有空记录";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//判断supplier是否存在
					String supplier=supplierTariffSet.getSupplierNo();
					if (!supplierNos.contains(supplier)) {
						errorMessage = "第" + lineNum + "行数据的供应商不存在或有误";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					//数据唯一性校验
					uniqueCheckMap.put("supplierNos", supplierTariffSet.getSupplierNo());
					count = this.supplierRateSetManager.findCount(uniqueCheckMap);
					if (count > 0) {
						errorMessage = "第" + lineNum + "行数据的供应商设置已经存在";
						errors.add(errorMessage);
						lineNum ++;
						continue;
					}
					if(duplicateMap.contains(supplierTariffSet.getSupplierNo())) {
							errorMessage = "第" + lineNum + "行数据的供应商已经在导入的文件中存在";
							errors.add(errorMessage);
							lineNum ++;
							continue;
					}else{
						duplicateMap.add(supplierTariffSet.getSupplierNo());
					}
					lineNum ++;
				}
			}
			return errors;
		} catch (ManagerException e) {
			logger.error("供应商设置校验数据失败。");
			throw new ManagerException(e);
		}
	}
	/**
	 * 新增/修改
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Boolean>
	 */
	@RequestMapping(value = "/save_all")
	@ResponseBody
	public Map<String, Object> saveAll(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JsonUtil<SupplierRateSet> util = new JsonUtil<SupplierRateSet>();
			Map<CommonOperatorEnum, List<SupplierRateSet>> dataMap = util.convertToMap(request, SupplierRateSet.class);
			// // 校验数据合法性
			// map = this.validateData(dataMap);
			// if(map != null && map.size() > 0) {
			// return map;
			// }
			supplierRateSetManager.saveAll(dataMap);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();// TODO
		}
		return map;
	}

	/**
	 * 
	 */
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {

		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		// 选单操作
		Map<String, Object> params = builderParams(req, model);
		setParams(req, params);

		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			int total = this.supplierRateSetManager.findCount();
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);

			List<SupplierRateSet> list = this.supplierRateSetManager.findSupplierByPage(page, sortColumn, sortOrder, params);
			obj.put("total", total);
			obj.put("rows", list);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return obj;
	}

	private void setParams(HttpServletRequest req, Map<String, Object> params) {
		// TODO Auto-generated method stub

	}


	/**
	 * 列表明细导出
	 * 
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req, Model model, HttpServletResponse response) throws Exception {
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.supplierRateSetManager.findCount();
		SimplePage page = new SimplePage(pageNo, total, (int) total);
		List<SupplierRateSet> list = this.supplierRateSetManager.findSupplierByPage(page, sortColumn, sortOrder, params);
		AreaAmongBalanceExportUtils.doExport(null, fileName, null, exportColumns, list, response, 1);
	}

}