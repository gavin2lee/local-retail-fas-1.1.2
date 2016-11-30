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

import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.CustomImperfect;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.CommonUtilManager;
import cn.wonhigh.retail.fas.manager.CustomImperfectManager;
import cn.wonhigh.retail.fas.manager.PurchasePriceManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-08-20 16:24:24
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
@RequestMapping("/custom_imperfect")
public class CustomImperfectController extends BaseCrudController<CustomImperfect> {
    @Resource
    private CustomImperfectManager customImperfectManager;

    @Resource
    private CommonUtilManager commonUtilManager;
    
    @Resource
    private PurchasePriceManager purchasePriceManager;
    
    @Resource
    private CommonUtilController commonUtilController;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("custom_imperfect/",customImperfectManager);
    }
    
	/**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/doExport")
	public void doExport(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List<OtherDeduction> dataList = customImperfectManager.findByPage(page, "", "", params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	/**
	 * 导入
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/doImport")
	@ResponseBody
	public Map<String, Object>  doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws Exception{
		String[] fieldNames= new String[]{"buyerNo","salerNo","returnDate","brandNo","itemCode","sizeNo","qty","reason","opinion"};
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "buyerNo", "buyerName","发票号", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "salerNo", "salerName","发票编码",true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "returnDate", "","日期", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.ITEM.getTypeNo(), "itemCode", "itemName","商品编码", true));
		lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "qty", "","数量", true));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), CustomImperfect.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, CustomImperfect.class);
		Map<String, Object> params = new HashMap<String, Object>();
		ItemDto item = null;
		for (ValidateResultVo resultVo : listValidate) {
			if(resultVo.getPass() == YesNoEnum.YES.getValue()){
				CustomImperfect obj = (CustomImperfect)resultVo.getValidateObj();
				params.put("itemCode", obj.getItemCode());
				params.put("brandNo", obj.getBrandNo());
				List list =  commonUtilManager.selectItemExtendsList(new SimplePage(), "", "", params);
				if(CollectionUtils.isEmpty(list)){
					continue;
				}
				item = (ItemDto) list.get(0);
				obj.setItemNo(item.getItemNo());
				obj.setItemName(item.getName());
				obj.setCategoryNo(item.getCategoryNo());
				obj.setCategoryName(item.getCategoryName());
				obj.setYears(item.getYears());
				obj.setSeason(item.getSellSeason());
				obj.setGender(item.getGender());
				obj.setYearsName(item.getYearsName());
				obj.setSeasonName(item.getSeasonName());
				obj.setGenderName(item.getGenderName());
				PurchasePrice purchasePrice = purchasePriceManager.findBalancePurchasePriceByItemNo(obj.getItemNo(), obj.getReturnDate());
				if(null != purchasePrice){
					obj.setPurchasePrice(purchasePrice.getPurchasePrice());
					obj.setAmount(purchasePrice.getPurchasePrice().multiply(new BigDecimal(obj.getQty())));
				}
				obj.setBalanceType(BalanceTypeEnums.HQ_VENDOR.getTypeNo());
				obj.setBalanceStatus(BalanceStatusEnums.NO_CONFIRM.getTypeNo());
				obj.setCreateTime(new Date());
				obj.setCreateUser(CurrentUser.getCurrentUser().getUsername());
				customImperfectManager.add(obj);
			}
		}
		map.put("success", true);
		map.put("rows", listValidate);
		return map;
	
	}
	
}