package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.PayTerm;
import cn.wonhigh.retail.fas.common.model.PayTermSupplier;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.PayTermManager;
import cn.wonhigh.retail.fas.manager.PayTermSupplierManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-18 16:58:07
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
@RequestMapping("/pay_term_supplier")
@ModuleVerify("40001002")
public class PayTermSupplierController extends BaseCrudController<PayTermSupplier> {
    @Resource
    private PayTermSupplierManager payTermSupplierManager;
    
    @Resource
    private PayTermManager payTermManager;
    
    @Resource
    private CommonUtilController commonUtilController;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("pay_term_supplier/",payTermSupplierManager);
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
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dataList = payTermSupplierManager.findByPage(page, "", "", params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public Map<String, Object>  doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String[] fieldNames= new String[]{"companyNo","supplierNo","termNo","isPrepay","forwardProportion","spotProportion"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "companyNo", "companyName","公司编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "supplierNo", "supplierName","供应商编码",true));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), PayTermSupplier.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, PayTermSupplier.class);
		String termNo = "";
		PayTerm payTerm = null;
		Map<String, Object> params = new HashMap<String, Object>();
		SystemUser user = CurrentUser.getCurrentUser();
		for (ValidateResultVo resultVo : listValidate) {
			if(resultVo.getPass() == YesNoEnum.YES.getValue()){
				PayTermSupplier obj = (PayTermSupplier)resultVo.getValidateObj();
				termNo = obj.getTermNo();
				params.put("termNo", termNo);
				List<PayTerm> lstPayTerm = payTermManager.findByBiz(null, params);
				if(!CollectionUtils.isEmpty(lstPayTerm)){
					payTerm = lstPayTerm.get(0);
					obj.setTermName(payTerm.getTermName());
					obj.setTermType(String.valueOf(payTerm.getTermType()));
					obj.setFixedDay(payTerm.getFixedDay());
					obj.setDays(payTerm.getDays());
					this.validateIsPrePay(resultVo);
				}else{
					resultVo.setPass(YesNoEnum.NO.getValue());
					resultVo.setErrorInfo("条款编码有误!");
				}
				if(resultVo.getPass() == YesNoEnum.YES.getValue()){
					obj.setCreateTime(new Date());
					obj.setCreateUser(user.getUsername());
					payTermSupplierManager.add(obj);
				}
			}
		}
		map.put("success", true);
		map.put("rows", listValidate);
		return map;
	}

	private void validateIsPrePay(ValidateResultVo resultVo) {
		PayTermSupplier obj = (PayTermSupplier)resultVo.getValidateObj();
		try {
			Integer isPrepay = obj.getIsPrepay();
			BigDecimal forwardProportion = obj.getForwardProportion();
			BigDecimal spotProportion = obj.getSpotProportion();
			if(null != obj.getIsPrepay()){
				if(isPrepay.intValue() == YesNoEnum.YES.getValue()){
					if(null != forwardProportion && forwardProportion.doubleValue() >=0 && forwardProportion.doubleValue() <1
						&& null != spotProportion && spotProportion.doubleValue() >=0 && spotProportion.doubleValue() <1){
						return ;
					}else{
						resultVo.setPass(YesNoEnum.NO.getValue());
						resultVo.setErrorInfo("请检查预付比例是否设置正确，大小范围(0<&lt;X<&lt;1)");
					}
				} 
			}
			obj.setIsPrepay(0);
			obj.setForwardProportion(new BigDecimal(0));
			obj.setSpotProportion(new BigDecimal(0));
			return ;
		} catch (Exception e) {
			resultVo.setPass(YesNoEnum.NO.getValue());
			resultVo.setErrorInfo("导入数据不合法！");
		}
		
		
	}
	
}