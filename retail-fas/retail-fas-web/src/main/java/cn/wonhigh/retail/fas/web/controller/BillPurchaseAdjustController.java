package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjust;
import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjustDtl;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BillPurchaseAdjustDtlManager;
import cn.wonhigh.retail.fas.manager.BillPurchaseAdjustManager;
import cn.wonhigh.retail.fas.manager.CommonUtilManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-13 12:09:02
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
@RequestMapping("/bill_purchase_adjust")
@ModuleVerify("40001022")
public class BillPurchaseAdjustController extends BaseCrudController<BillPurchaseAdjust> {
	
    @Resource
    private BillPurchaseAdjustManager billPurchaseAdjustManager;
   
    @Resource
    private BillPurchaseAdjustDtlManager billPurchaseAdjustDtlManager;
    
    @Resource
    private CommonUtilController commonUtilController;
   
    @Resource
    private CommonUtilManager commonUtilManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_purchase_adjust/",billPurchaseAdjustManager);
    }
    
    @RequestMapping("/list_tabMain")
	public String listTabMain() throws ManagerException {
		return "bill_purchase_adjust/list_tabMain";
	}

	@RequestMapping(value = "/do_import")
	@ResponseBody
	public Map<String, Object>  doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String billNo = req.getParameter("billNo");
		String[] fieldNames= new String[]{"id","brandNo","itemCode","supplierAmount","referAmount","amount","remark"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NULL.getTypeNo(), "itemCode", "","商品编码",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "supplierAmount", "","厂商额", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "referAmount", "","中间额", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "amount", "","地区额", true));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), BillPurchaseAdjustDtl.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, BillPurchaseAdjustDtl.class);
		String username = Authorization.getUser().getUsername();
		Date currDate = new Date();
		for (ValidateResultVo resultVo : listValidate) {
			if(resultVo.getPass() == YesNoEnum.YES.getValue()){
				if(this.itemValidate(resultVo)){
					BillPurchaseAdjustDtl dtl = (BillPurchaseAdjustDtl) resultVo.getValidateObj();
					dtl.setBillNo(billNo);
					dtl.setCreateTime(currDate);
					dtl.setUpdateTime(currDate);
					dtl.setCreateUser(username);
					dtl.setUpdateUser(username);
					billPurchaseAdjustDtlManager.add(dtl);
				}
			}
			
		}
		map.put("success", true);
		map.put("rows", listValidate);
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean itemValidate(ValidateResultVo resultVo) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		BillPurchaseAdjustDtl obj = (BillPurchaseAdjustDtl)resultVo.getValidateObj();
		params.put("itemCode", obj.getItemCode());
		params.put("brandNo", obj.getBrandNo());
		List list =  commonUtilManager.selectItemExtendsList(new SimplePage(), "", "", params);
		if(list.size() == 0){
			resultVo.setPass(YesNoEnum.NO.getValue());
			resultVo.setErrorInfo("无效的商品编码！");
			return false;
		}
		ItemDto item = (ItemDto) list.get(0);
		obj.setItemNo(item.getItemNo());
		obj.setItemName(item.getName());
		obj.setCategoryNo(item.getCategoryNo());
		obj.setCategoryName(item.getCategoryName());
		return true;
	}

	@RequestMapping(value = "/dtl_list.json")
	@ResponseBody
	public  Map<String, Object> queryDtlList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		List<BillPurchaseAdjustDtl> lstItem = new ArrayList<BillPurchaseAdjustDtl>();
		List<BillPurchaseAdjustDtl> lstFooter = new ArrayList<BillPurchaseAdjustDtl>();
		int total = billPurchaseAdjustDtlManager.findDtlCount(params);
		if(total >0){
			SimplePage page = new SimplePage(pageNo, pageSize, total);
			lstItem = billPurchaseAdjustDtlManager.findDtlList(page, sortColumn, sortOrder, params);
			lstFooter = billPurchaseAdjustDtlManager.findDtlFooter(params);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", lstItem);
		obj.put("footer", lstFooter);
		return obj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping(value = "/export_data")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List dataList = billPurchaseAdjustManager.findByPage(page, "", "", params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
    /**
     * 新增
     */
	@RequestMapping(value = "/post")
	public ResponseEntity<BillPurchaseAdjust> add(BillPurchaseAdjust obj) throws ManagerException {
		billPurchaseAdjustManager.add(obj);
		obj = billPurchaseAdjustManager.findById(obj);
		return new ResponseEntity<BillPurchaseAdjust>(obj, HttpStatus.OK);
	}
	
    /**
     * 修改
     */
	@RequestMapping(value = "/put")
	public ResponseEntity<BillPurchaseAdjust> moditfy(BillPurchaseAdjust obj) throws ManagerException {
		billPurchaseAdjustManager.modifyById(obj);
		obj = billPurchaseAdjustManager.findById(obj);
		return new ResponseEntity<BillPurchaseAdjust>(obj, HttpStatus.OK);
	}
	
	 /**
     * 确认/反确认
     */
	@ResponseBody
	@RequestMapping(value = "/verify_bill")
	public BillPurchaseAdjust verifyBill(HttpServletRequest req, BillPurchaseAdjust obj) throws ManagerException {
		if(StringUtils.isBlank(obj.getBillNo()) || obj.getStatus() == null){
			return null;
		}
		billPurchaseAdjustManager.verifyBill(obj);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", obj.getBillNo());
		List<BillPurchaseAdjust> lstItem = billPurchaseAdjustManager.findByBiz(null, params);
		if(!CollectionUtils.isEmpty(lstItem)){
			return lstItem.get(0);
		}
		return null;
	}
	
}