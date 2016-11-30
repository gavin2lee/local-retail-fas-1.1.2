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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.AlgorithmEnums;
import cn.wonhigh.retail.fas.common.enums.ContractDiscountTypeEnums;
import cn.wonhigh.retail.fas.common.enums.OrderTypeEnums;
import cn.wonhigh.retail.fas.common.enums.PriceBasisEnums;
import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.ContractDiscount;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceManager;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.CategoryManager;
import cn.wonhigh.retail.fas.manager.ContractDiscountManager;
import cn.wonhigh.retail.fas.manager.ItemManager;
import cn.wonhigh.retail.fas.manager.PayRelationshipManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-24 14:59:22
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
@RequestMapping("/contract_discount")
public class ContractDiscountController extends BaseCrudController<ContractDiscount> {
    @Resource
    private ContractDiscountManager contractDiscountManager;
    
    @Resource
    private PayRelationshipManager payRelationshipManager;
    
    @Resource
    private BillBuyBalanceManager billBuyBalanceManager;
    
    @Resource
    private ItemManager itemManager;
    
    @Resource
    private CategoryManager categoryManager;
    
    @Resource
    private BrandManager brandManager;
    @Resource
    private CommonUtilController commonUtilController;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("contract_discount/",contractDiscountManager);
    }
    
    @RequestMapping(method = RequestMethod.GET ,value ="/list")
    public ModelAndView list(HttpServletRequest req) {
    	String type = req.getParameter("type");
    	ModelAndView mav = new ModelAndView();
    	if(StringUtils.isNotBlank(type) && String.valueOf(ContractDiscountTypeEnums.SUPPLIER.getTypeNo()).equals(type)){
    		mav.setViewName("contract_discount/list_supplier");
    	}else if(StringUtils.isNotBlank(type) && String.valueOf(ContractDiscountTypeEnums.COMPANY.getTypeNo()).equals(type)){
    		mav.setViewName("contract_discount/list_company");
    	}else if(StringUtils.isNotBlank(type) && String.valueOf(ContractDiscountTypeEnums.ITEM.getTypeNo()).equals(type)){
    		mav.setViewName("contract_discount/list_item");
    	}
		return mav;
    }
    
	/**
	 * 检查是否只读
	 * @param req
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkIsReadonly")
	@ResponseBody
	public Boolean checkIsReadonly(HttpServletRequest req,Model model, HttpServletResponse response)throws Exception{
		String contractDiscountNo = req.getParameter("contractDiscountNo");
		String contractDiscountType = req.getParameter("contractDiscountType");
		Map<String, Object> params = new HashMap<String, Object>();
		if("1" .equals(contractDiscountType)){// 
			params.put("supplierContractDiscountNo", contractDiscountNo);
		}else if("2" .equals(contractDiscountType)){
			params.put("companyContractDiscountNo", contractDiscountNo);
		}else if("3" .equals(contractDiscountType)){
			params.put("itemNo", req.getParameter("itemNo"));
			params.put("salerNo", req.getParameter("salerNo"));
			params.put("buyerNo", req.getParameter("buyerNo"));
			params.put("cost", "(".concat(req.getParameter("purchasePrice")).concat(")"));
			return billBuyBalanceManager.findCount(params) >0;
		}else{
			return false;
		}
		return payRelationshipManager.findCount(params) > 0;
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
		List dataList = contractDiscountManager.findByPage(page, "", "", params);
		for (Object obj : dataList) {
			ContractDiscount contractDiscount = (ContractDiscount)obj;
			String orderType = contractDiscount.getOrderType();
			String priceBasis = contractDiscount.getPriceBasis();
			if(StringUtils.isNotBlank(orderType)){
				contractDiscount.setOrderType(OrderTypeEnums.getNameByNo(Integer.parseInt(orderType)));
			}
			if(StringUtils.isNotBlank(priceBasis)){
				contractDiscount.setPriceBasis(PriceBasisEnums.getNameByNo(Integer.parseInt(priceBasis)));
			}			
		}
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public Map<String, Object>  doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String type = req.getParameter("type");
		String[] fieldNames = null;
		List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
		if(type.equals(String.valueOf(ContractDiscountTypeEnums.SUPPLIER.getTypeNo()))){
			fieldNames= new String[]{"buyerNo","salerNo","orderType","brandNo","categoryNo","priceBasis","discount1","discount2","algorithm","notTaxAlgorithm","effectiveDate"};
			lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "salerNo", "salerName","供应商编码",true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "buyerNo", "buyerName","公司编码", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌编码", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.CATEGORY.getTypeNo(), "categoryNo", "categoryName","大类编码",true));
		}else if(type.equals(String.valueOf(ContractDiscountTypeEnums.COMPANY.getTypeNo()))){
			fieldNames= new String[]{"salerNo","buyerNo","orderType","brandNo","categoryNo","priceBasis","discount1","discount2","referDiscount1","referDiscount2","addPrice","algorithm","effectiveDate"};
			lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "salerNo", "salerName","发方公司编码",true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "buyerNo", "buyerName","收方公司编码", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.BRAND.getTypeNo(), "brandNo", "brandName","品牌编码", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.CATEGORY.getTypeNo(), "categoryNo", "categoryName","大类编码",true));
		}else if(type.equals(String.valueOf(ContractDiscountTypeEnums.ITEM.getTypeNo()))){
			fieldNames= new String[]{"buyerNo","salerNo","itemCode","purchasePrice","effectiveDate"};
			lstValidate.add(new ValidateVo(ValidateTypeEnums.SUPPLIER.getTypeNo(), "salerNo", "salerName","供应商编码",true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.COMPANY.getTypeNo(), "buyerNo", "buyerName","公司编码", true));
		}
		lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "effectiveDate", "","日期", true));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), ContractDiscount.class, fieldNames);
		List<ValidateResultVo>  listValidate = commonUtilController.doValidate(lstItem, lstValidate, ContractDiscount.class);
		SystemUser user = CurrentUser.getCurrentUser();
		for (ValidateResultVo resultVo : listValidate) {
			this.itemValidate(resultVo, type);
			this.importValidate(resultVo,type);
			if(resultVo.getPass() == YesNoEnum.YES.getValue()){
				ContractDiscount obj = (ContractDiscount)resultVo.getValidateObj();
				obj.setContractDiscountType(Integer.parseInt(type));
				obj.setCreateUser(user.getUsername());
				obj.setCreateTime(new Date());
				contractDiscountManager.add(obj);
			}
		}
		map.put("success", true);
		map.put("rows", listValidate);
		return map;
	}
	
	private void itemValidate(ValidateResultVo resultVo,String type) throws ManagerException{
		if(resultVo.getPass() == YesNoEnum.NO.getValue() || !type.equals(String.valueOf(ContractDiscountTypeEnums.ITEM.getTypeNo()))){
			return ;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		ContractDiscount obj = (ContractDiscount)resultVo.getValidateObj();
		String itemCode = obj.getItemCode();
		if(StringUtils.isBlank(itemCode)){
			resultVo.setPass(YesNoEnum.NO.getValue());
			resultVo.setErrorInfo("无效的商品编码！");
			return ;
		}
		params.put("code", itemCode);
		List<Item> list = itemManager.findByBiz(null, params);
		if(list.size() == 0){
			resultVo.setPass(YesNoEnum.NO.getValue());
			resultVo.setErrorInfo("无效的商品编码！");
			return ;
		}
		Item item = list.get(0);
		String brandNo = item.getBrandNo();
		String categoryNo = item.getRootCategoryNo();
		obj.setItemNo(item.getItemNo());
		obj.setItemName(item.getName());
		obj.setBrandNo(brandNo);
		obj.setCategoryNo(categoryNo);
		params.clear();
		params.put("brandNo", brandNo);
		List<Brand> listBrand = brandManager.findByBiz(null, params);
		if(listBrand.size() >0){
			obj.setBrandName(listBrand.get(0).getName());
		}
		params.clear();
		params.put("categoryNo", categoryNo);
		List<Category> listCategory = categoryManager.findByBiz(null, params);
		if(listBrand.size() >0){
			obj.setCategoryName(listCategory.get(0).getName());
		}
	}
	
	private void importValidate(ValidateResultVo resultVo,String type ){
		if(resultVo.getPass() == YesNoEnum.NO.getValue()){
			return ;
		}
		try {
			ContractDiscount obj = (ContractDiscount)resultVo.getValidateObj();
			String orderType = obj.getOrderType();
			String algorithm = obj.getAlgorithm();
			String notTaxAlgorithm = obj.getNotTaxAlgorithm();
			String priceBasis = obj.getPriceBasis();
			BigDecimal discount1 = obj.getDiscount1();
			BigDecimal discount2 = obj.getDiscount2();
			BigDecimal addPrice = obj.getAddPrice();
			BigDecimal purchasePrice = obj.getPurchasePrice();
			if(String.valueOf(ContractDiscountTypeEnums.SUPPLIER.getTypeNo()) .equals(type) 
					|| String.valueOf(ContractDiscountTypeEnums.COMPANY.getTypeNo()).equals(type)){
				if(!(StringUtils.isNotBlank(orderType) && (
						orderType.equals(String.valueOf(OrderTypeEnums.FORWARD.getTypeNo()))
						|| orderType.equals(String.valueOf(OrderTypeEnums.SPOT.getTypeNo()))
						|| orderType.equals(String.valueOf(OrderTypeEnums.FORWARD_SPOT.getTypeNo())) 
						) )){
					resultVo.setPass(YesNoEnum.NO.getValue());
					resultVo.setErrorInfo("请检查订货类型是否设置正确！(1:现货,2:期货,3:期货;现货)。");
				}
				if(!(StringUtils.isNotBlank(priceBasis) && (
						priceBasis.equals(String.valueOf(PriceBasisEnums.TAG_PRICE.getTypeNo()))
						|| priceBasis.equals(String.valueOf(PriceBasisEnums.SALES_AMOUNT.getTypeNo()))
						|| priceBasis.equals(String.valueOf(PriceBasisEnums.SUPPLIER_BALANCE.getTypeNo())) 
						) )){
					resultVo.setPass(YesNoEnum.NO.getValue());
					resultVo.setErrorInfo("请检查定价依据是否设置正确！(1:牌价,2:终端销售金额,3:供应商结算价)。");
				}
			}
			if(String.valueOf(ContractDiscountTypeEnums.SUPPLIER.getTypeNo()) .equals(type)){
				if(!(StringUtils.isNotBlank(algorithm) && (
						algorithm.equals(AlgorithmEnums.A.getTypeNo())
						|| algorithm.equals(AlgorithmEnums.B.getTypeNo())
						|| algorithm.equals(AlgorithmEnums.C.getTypeNo()) 
						))){
					resultVo.setPass(YesNoEnum.NO.getValue());
					resultVo.setErrorInfo(resultVo.getErrorInfo().concat("请检查算法是否设置正确！(A：1：牌价*批发折数四舍五入取2位小数保留到分，2：/1.17四舍五入取2位小数保留到分，3：*客户折扣*1.17  四舍五入取2位小数保留到分,B：1：牌价*批发折数四舍五入保留到元，2：/1.17四舍五入取2位小数保留到分，3：*客户折扣*1.17  四舍五入取2位小数保留到分,C：牌价*进货折扣)。"));
				}
				if(StringUtils.isNotBlank(notTaxAlgorithm)){
					if(!notTaxAlgorithm.equals(AlgorithmEnums.A.getTypeNo())
							&& !notTaxAlgorithm.equals(AlgorithmEnums.B.getTypeNo())){
						resultVo.setPass(YesNoEnum.NO.getValue());
						resultVo.setErrorInfo(resultVo.getErrorInfo().concat("请检查不含税算法是否设置正确！(A：1：牌价*折扣1四舍五入取2位小数保留到分，2：/1.17 四舍五入取2位小数保留到分，3：*折扣2四舍五入取2位小数保留到分，4： *数量四舍五入取2位小数保留到分,B:1：牌价*折扣1四舍五入保留到元 ，2：/1.17四舍五入取2位小数保留到分，3：*数量*折扣2  四舍五入取2位小数保留到分"));
					}
				}
				if(!(null != discount1 && discount1.doubleValue() >=0 && discount1.doubleValue() <=1
						&& null != discount2 && discount2.doubleValue() >=0 && discount2.doubleValue() <=1)){
					resultVo.setPass(YesNoEnum.NO.getValue());
					resultVo.setErrorInfo(resultVo.getErrorInfo().concat("请检查折扣是否设置正确，大小范围(0&lt;X&lt;1)"));
				}
			}else if(String.valueOf(ContractDiscountTypeEnums.COMPANY.getTypeNo()) .equals(type)){
				if(StringUtils.isNotBlank(priceBasis) && priceBasis.equals(String.valueOf(PriceBasisEnums.SUPPLIER_BALANCE.getTypeNo()))){
					if(null != discount2){
						resultVo.setPass(YesNoEnum.NO.getValue());
						resultVo.setErrorInfo(resultVo.getErrorInfo().concat("加价依据为牌价，不允许设置折扣2!"));
					}
					if(null != addPrice && null != discount1){
						resultVo.setPass(YesNoEnum.NO.getValue());
						resultVo.setErrorInfo(resultVo.getErrorInfo().concat("加价，折扣只允许设置一个！"));
					}else if(null == addPrice && null == discount1){
						resultVo.setPass(YesNoEnum.NO.getValue());
						resultVo.setErrorInfo(resultVo.getErrorInfo().concat("加价，折扣必须设置一个！"));
					}else if(null != addPrice && null == discount1){
						if(!(addPrice.doubleValue() >=-999999 && addPrice.doubleValue() <999999)){
							resultVo.setPass(YesNoEnum.NO.getValue());
							resultVo.setErrorInfo(resultVo.getErrorInfo().concat("请检查加价是否设置正确，大小范围(-999999&lt;X&lt;999999)"));
						}
					}else if(null != discount1 && null == addPrice){
						if(!(discount1.doubleValue() >=0 && discount1.doubleValue() <999999)){
							resultVo.setPass(YesNoEnum.NO.getValue());
							resultVo.setErrorInfo(resultVo.getErrorInfo().concat("请检查折扣是否设置正确，大小范围(0&lt;X&lt;999999)"));
						}
					}
				}else if(StringUtils.isNotBlank(priceBasis) && priceBasis.equals(String.valueOf(PriceBasisEnums.TAG_PRICE.getTypeNo()))){
					if(!(StringUtils.isNotBlank(algorithm) && (
							algorithm.equals(AlgorithmEnums.A.getTypeNo())
							|| algorithm.equals(AlgorithmEnums.B.getTypeNo())
							|| algorithm.equals(AlgorithmEnums.C.getTypeNo()) 
							))){
						resultVo.setPass(YesNoEnum.NO.getValue());
						resultVo.setErrorInfo(resultVo.getErrorInfo().concat("请检查算法是否设置正确！(A：1：牌价*批发折数四舍五入取2位小数保留到分，2：/1.17四舍五入取2位小数保留到分，3：*客户折扣*1.17  四舍五入取2位小数保留到分,B：1：牌价*批发折数四舍五入保留到元，2：/1.17四舍五入取2位小数保留到分，3：*客户折扣*1.17  四舍五入取2位小数保留到分,C：牌价*进货折扣)。"));
					}
					if(!(null != discount1 && discount1.doubleValue() >=0 && discount1.doubleValue() <=1
							&& null != discount2 && discount2.doubleValue() >=0 && discount2.doubleValue() <=1)){
						resultVo.setPass(YesNoEnum.NO.getValue());
						resultVo.setErrorInfo(resultVo.getErrorInfo().concat("请检查折扣是否设置正确，大小范围(0&lt;X&lt;1)"));
					}
					if(null != addPrice){
						resultVo.setPass(YesNoEnum.NO.getValue());
						resultVo.setErrorInfo(resultVo.getErrorInfo().concat("加价依据为牌价，不允许设置加价!"));
					}
				}
			}else if(String.valueOf(ContractDiscountTypeEnums.ITEM.getTypeNo()) .equals(type)){
				if(!(null != purchasePrice && purchasePrice.doubleValue() >=0 && purchasePrice.doubleValue() <999999)){
					resultVo.setPass(YesNoEnum.NO.getValue());
					resultVo.setErrorInfo(resultVo.getErrorInfo().concat("请检查采购价价是否设置正确，大小范围(0&lt;X&lt;999999)"));
				}
			}
		}catch (Exception e) {
			resultVo.setPass(YesNoEnum.NO.getValue());
			resultVo.setErrorInfo("请检查数据是否有效性)");
		}
		
	}
}