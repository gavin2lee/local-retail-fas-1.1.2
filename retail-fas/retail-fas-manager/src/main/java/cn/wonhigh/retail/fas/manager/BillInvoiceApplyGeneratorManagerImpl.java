package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BalanceInvoiceApplyGenerator;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceDtl;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceSource;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;
import cn.wonhigh.retail.fas.common.model.FinancialCategoryDtl;
import cn.wonhigh.retail.fas.common.model.InsidePurchaseBalanceDate;
import cn.wonhigh.retail.fas.common.model.InvoiceApplyConfirmDtl;
import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSetDtl;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.ShopGroup;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceApplyService;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceDtlService;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceSourceService;
import cn.wonhigh.retail.fas.service.BillInvoiceApplyService;
import cn.wonhigh.retail.fas.service.BillShopBalanceCateSumService;
import cn.wonhigh.retail.fas.service.BillShopBalanceService;
import cn.wonhigh.retail.fas.service.CategoryService;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.fas.service.CurrencyManagementService;
import cn.wonhigh.retail.fas.service.FinancialCategoryDtlService;
import cn.wonhigh.retail.fas.service.FinancialCategoryService;
import cn.wonhigh.retail.fas.service.InsidePurchaseBalanceDateService;
import cn.wonhigh.retail.fas.service.InvoiceApplyConfirmDtlService;
import cn.wonhigh.retail.fas.service.InvoiceInfoService;
import cn.wonhigh.retail.fas.service.InvoiceTemplateSetDtlService;
import cn.wonhigh.retail.fas.service.ShopBalanceDateService;
import cn.wonhigh.retail.fas.service.ShopGroupService;
import cn.wonhigh.retail.fas.service.ShopService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-16 14:05:12
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
@Service("billInvoiceApplyGeneratorManagerImpl")
class BillInvoiceApplyGeneratorManagerImpl extends BaseCrudManagerImpl implements BillInvoiceApplyGeneratorManager {

	@Resource
    private BillInvoiceApplyService billInvoiceApplyService;
	
	@Resource
    private InvoiceInfoService invoiceInfoService;
	
	@Resource
	private BillBalanceInvoiceSourceService billBalanceInvoiceSourceService;
	
	@Resource
    private BillBalanceInvoiceApplyService billBalanceInvoiceApplyService;
	
	@Resource
	private BillBalanceInvoiceDtlService billBalanceInvoiceDtlService;
	
	@Resource
    private ShopGroupService shopGroupService;

    @Resource
    private CategoryService categoryService;
    
	@Resource
	private FinancialCategoryService financialCategoryService;
	
	@Resource
    private BillShopBalanceService billShopBalanceService;
	
	@Resource
    private ShopBalanceDateService shopBalanceDateService;
	
    @Resource
    private CommonUtilService commonUtilService;
    
    @Resource
    private ShopService shopService;
    
	@Resource
	private BillShopBalanceCateSumService billShopBalanceCateSumService;

	@Resource
	private InvoiceTemplateSetDtlService invoiceTemplateSetDtlService;
	
	@Resource
	private InvoiceApplyConfirmDtlService invoiceApplyConfirmDtlService;
	
	@Resource
	private CurrencyManagementService currencyManagementService;
	
	@Resource
	private InsidePurchaseBalanceDateService insidePurchaseBalanceDateService;
	
	@Resource
	private FinancialCategoryDtlService financialCategoryDtlService;
	
	@Override
	protected BaseCrudService init() {
		return billInvoiceApplyService;
	}

	/**
	 * @author wangxy
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> generateBalanceInvoiceApply(
			List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerators) throws ManagerException {
		
		Map<String, Object> flag = null;
			try {
				if (CollectionUtils.isEmpty(balanceInvoiceApplyGenerators)) {
					flag = new HashMap<String,Object>();
					flag.put("error", "请选择源单据");
				}
				if (balanceInvoiceApplyGenerators.get(0).getBalanceType() == BalanceTypeEnums.AREA_MALL.getTypeNo()){
					//地区门店结算
					flag = areaMallBalanceInvoiceApplyGenerate(balanceInvoiceApplyGenerators);
				} else {
					//地区其他相关结算
					flag = otherBalanceInvoiceApplyGenerate(balanceInvoiceApplyGenerators);
				}
			} catch (ServiceException e) {
				flag = new HashMap<String,Object>();
				flag.put("error", "生成开票申请失败。");
				throw new ManagerException("生成开票申请失败",e);
			}
			return flag;
	}

	/**
	 * 地区门店结算单-开票申请
	 * @param balanceInvoiceApplyGenerators
	 * @throws ServiceException
	 * @author wangxy
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private Map<String, Object> areaMallBalanceInvoiceApplyGenerate(
			List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerators) throws ServiceException {
		Map<String, Object> flag = null;
		int count = 0;
		//<InvoiceName/shopNo,  List<module>>
		Map<String, List<BalanceInvoiceApplyGenerator>> applyListMap = new HashMap<String,List<BalanceInvoiceApplyGenerator>>();
		List<ShopGroup> shopGroups = null;
		Map<String, Object> groupMap = null;
		List<BalanceInvoiceApplyGenerator> shopApplyGenerators = null;
		Date date = new Date();
		//同一个开票名称下面的店铺合并
		for (BalanceInvoiceApplyGenerator applyGenerator : balanceInvoiceApplyGenerators) {
			if (StringUtils.isEmpty(applyGenerator.getShopNo())) {
				continue;
			}
			groupMap = new HashMap<String, Object>();
			groupMap.put("shopNo", applyGenerator.getShopNo());
			groupMap.put("companyNo", applyGenerator.getSalerNo());
			groupMap.put("validDate", date);
			shopGroups = shopGroupService.getShopGroupNoByShopNo(groupMap);
			
			if (CollectionUtils.isEmpty(shopGroups)) {//没有配置开票规则，就每个店铺生成一个开票申请
				if (applyListMap.containsKey(applyGenerator.getShopNo())) {
					applyListMap.get(applyGenerator.getShopNo()).add(applyGenerator);
				}else {
					shopApplyGenerators = new ArrayList<BalanceInvoiceApplyGenerator>();
					shopApplyGenerators.add(applyGenerator);
					applyListMap.put(applyGenerator.getShopNo(), shopApplyGenerators);
				}
			}else {//配置了开票规则,同一个开票名称下面的店铺合并
				if (applyListMap.containsKey(shopGroups.get(0).getInvoiceName())) {
					applyListMap.get(shopGroups.get(0).getInvoiceName()).add(applyGenerator);
				}else {
					shopApplyGenerators = new ArrayList<BalanceInvoiceApplyGenerator>();
					shopApplyGenerators.add(applyGenerator);
					applyListMap.put(shopGroups.get(0).getInvoiceName(), shopApplyGenerators);
				}
			}
		}
		
		Map<String, Object> shopGroupMap = null;
		List<ShopGroup> mallShopGroups = null;
		BillBalanceInvoiceApply invoiceApply = null;
		String invoiceNo =null;
		
		Iterator<Map.Entry<String,List<BalanceInvoiceApplyGenerator>>> shopIterator = applyListMap.entrySet().iterator();
		while (shopIterator.hasNext()) {
			Map.Entry<String,List<BalanceInvoiceApplyGenerator>> shopEntry = (Map.Entry<String,List<BalanceInvoiceApplyGenerator>>) shopIterator.next(); 
			List<BalanceInvoiceApplyGenerator> applyGenerators = shopEntry.getValue();
			
			invoiceApply = new BillBalanceInvoiceApply();
			invoiceApply.setSalerNo(applyGenerators.get(0).getSalerNo());
			invoiceApply.setSalerName(applyGenerators.get(0).getSalerName());

			invoiceNo = commonUtilService.getNewBillNoCompanyNo(invoiceApply.getSalerNo(),applyGenerators.get(0).getShopNo(),BillBalanceInvoiceApply.class.getSimpleName());
			invoiceApply.setBillNo(invoiceNo);
			invoiceApply.setInvoiceApplyDate(new Date());
			invoiceApply.setPostPayDate(new Date());
			invoiceApply.setCreateTime(new Date());
			
			invoiceApply.setPreInvoice((byte)1);//默认为否
			
			/*************************** wang.yj 增加管理城市及结算月 *********************/
			
			invoiceApply.setOrganNo(applyGenerators.get(0).getOrganNo());
			invoiceApply.setOrganName(applyGenerators.get(0).getOrganName());
			invoiceApply.setMonth(applyGenerators.get(0).getBalanceMonth());
			invoiceApply.setBrandNo(applyGenerators.get(0).getBrandNo());
			invoiceApply.setBrandName(applyGenerators.get(0).getBrandName());
			
			/*************************** wang.yj end *********************/
			
			String buyerNo = invoiceApply.getBuyerNo();
			String bsgroupsNo = "";
			String invoiceName = "";
			//根据店铺找店铺开票规则， 找到开票信息维护
			shopGroupMap = new HashMap<String, Object>();
			shopGroupMap.put("shopNo", applyGenerators.get(0).getShopNo());
			shopGroupMap.put("companyNo", applyGenerators.get(0).getSalerNo());
			shopGroupMap.put("validDate", date);
			mallShopGroups = shopGroupService.getShopGroupNoByShopNo(shopGroupMap);
			
			if(!CollectionUtils.isEmpty(mallShopGroups)){
				if(StringUtils.isNotEmpty(mallShopGroups.get(0).getClientNo())){
					invoiceName = mallShopGroups.get(0).getInvoiceName();
					buyerNo = mallShopGroups.get(0).getClientNo();
					invoiceApply.setBuyerName(mallShopGroups.get(0).getClientName());
					invoiceApply.setBuyerNo(mallShopGroups.get(0).getClientNo());
				}
			}else{
				Shop shop = shopService.selectSubsiInfo(shopGroupMap);
				if(null != shop){
					bsgroupsNo = shop.getBsgroupsNo();
					if(StringUtils.isNotEmpty(shop.getMallNo())){
						buyerNo = shop.getMallNo();
						invoiceApply.setBuyerName(shop.getMallName());
						invoiceApply.setBuyerNo(shop.getMallNo());
					}else{
						buyerNo = shop.getBsgroupsNo();
						invoiceApply.setBuyerName(shop.getBsgroupsName());
						invoiceApply.setBuyerNo(shop.getBsgroupsNo());
					}
				}
			}
			
			
			if(StringUtils.isNotBlank(buyerNo)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("companyNo", invoiceApply.getSalerNo());
				map.put("clientNo", buyerNo);
//				map.put("status", 1);
				map.put("month", date);
				//根据公司编码、客户编码获取开票信息
				List<InvoiceInfo> invoiceInfos = invoiceInfoService.findByBiz(null, map);
				
				if(CollectionUtils.isEmpty(invoiceInfos) && StringUtils.isNotBlank(bsgroupsNo)){//如果根据商场编号查询开票信息，返回空，则根据商业集团编号查询
					map.put("clientNo", bsgroupsNo);
					invoiceInfos = invoiceInfoService.findByBiz(null, map);
				} 
				
				InvoiceInfo invoiceInfo = null;
				if(!CollectionUtils.isEmpty(invoiceInfos)){
					//返回多笔开票信息时，优先取首选的那笔记录
					for (InvoiceInfo invoiceInfo2 : invoiceInfos) {
						if(invoiceInfo2.getStatus() == 1){
							invoiceInfo = invoiceInfo2;
							break;
						}
					}
					//如果多笔中都没有首选的记录，则取第一笔
					if(null == invoiceInfo){
						invoiceInfo = invoiceInfos.get(0);
					}
					if(null != invoiceInfo){
						invoiceApply.setTaxRegistryNo(invoiceInfo.getTaxpayerNo());
						invoiceApply.setBankName(invoiceInfo.getBankName());
						invoiceApply.setBankAccount(invoiceInfo.getAccountNo());
						invoiceApply.setBankAccountName(invoiceInfo.getBankName());
						invoiceApply.setBuyerAddress(invoiceInfo.getAddress());
						invoiceApply.setBuyerTel(invoiceInfo.getContactNumber());
						invoiceApply.setMailingAddress(invoiceInfo.getPostAddress());
						invoiceApply.setContactName(invoiceInfo.getContactPerson());
						invoiceApply.setTel(invoiceInfo.getTelephoneNumber());
						if(StringUtils.isNotBlank(invoiceName)){
							invoiceApply.setInvoiceName(invoiceName);
						}else{
							invoiceApply.setInvoiceName(invoiceInfo.getInvoiceName());
						}
						invoiceApply.setInvoiceType(invoiceInfo.getInvoiceType());
						invoiceApply.setBuyerAddress(invoiceInfo.getAddress());
						invoiceApply.setMailingAddress(invoiceInfo.getPostAddress());
						invoiceApply.setBuyerAddress(invoiceInfo.getAddress());
						invoiceApply.setMailingAddress(invoiceInfo.getPostAddress());
						//设置收票方为店铺对应的商场
						invoiceApply.setBuyerNo(invoiceInfo.getClientNo());
						invoiceApply.setBuyerName(invoiceInfo.getClientName());
					}
				}else{
					flag = new HashMap<String,Object>();
					flag.put("error","生成失败，开票信息没做维护或不完善。");
					return flag;
				}
			}else{
				flag = new HashMap<String,Object>();
				flag.put("error","生成失败，开票客户方为空。");
				return flag;
			}
			
			
//			invoiceInfoMap = new HashMap<String, Object>();
//			if (!CollectionUtils.isEmpty(mallShopGroups)) {
//				//查询客户信息数据
//				invoiceInfoMap.put("companyNo", mallShopGroups.get(0).getCompanyNo());
//				invoiceInfoMap.put("clientNo", mallShopGroups.get(0).getClientNo());
//				invoiceInfoMap.put("status", 1);
//				invoiceInfos = invoiceInfoService.findByBiz(null, invoiceInfoMap);
//				if (!CollectionUtils.isEmpty(invoiceInfos)) {
//					invoiceApply.setInvoiceName(invoiceInfos.get(0).getInvoiceName());
//					invoiceApply.setInvoiceType(invoiceInfos.get(0).getInvoiceType());
//					invoiceApply.setBankName(invoiceInfos.get(0).getBankName());
//					invoiceApply.setBankAccountName(invoiceInfos.get(0).getBankName());
//					invoiceApply.setBankAccount(invoiceInfos.get(0).getAccountNo());
//					invoiceApply.setTaxRegistryNo(invoiceInfos.get(0).getTaxpayerNo());
//					invoiceApply.setTel(invoiceInfos.get(0).getTelephoneNumber());
//					invoiceApply.setContactName(invoiceInfos.get(0).getContactPerson());
//					invoiceApply.setBuyerTel(invoiceInfos.get(0).getContactNumber());
//					invoiceApply.setInvoiceType(invoiceInfos.get(0).getInvoiceType());
//				}
//				invoiceApply.setBuyerNo(mallShopGroups.get(0).getClientNo());
//				invoiceApply.setBuyerName(mallShopGroups.get(0).getClientName());
//			}else {
//				//设置收票方为店铺对应的商场
//				Shop shop = shopService.selectSubsiInfo(shopGroupMap);
//				if (null != shop) {
//					if (null != shop.getMallNo()) {
//						//查询客户信息数据
//						invoiceInfoMap.put("companyNo", invoiceApply.getSalerNo());
//						invoiceInfoMap.put("clientNo", shop.getMallNo());
//						invoiceInfoMap.put("status", 1);
//						invoiceInfos = invoiceInfoService.findByBiz(null, invoiceInfoMap);
//						
//						// 如是根据商场查询不到开票信息，则根据商业集团查询开票信息 wang.yj
//						if(CollectionUtils.isEmpty(invoiceInfos) && StringUtils.isNotBlank(shop.getBsgroupsNo())){
//							invoiceInfoMap.put("clientNo", shop.getBsgroupsNo());
//							invoiceInfos = invoiceInfoService.findByBiz(null, invoiceInfoMap);
//						}
//						/////////////////////wang.yj end/////////////////////////////
//						
//						if (!CollectionUtils.isEmpty(invoiceInfos)) {
//							invoiceApply.setTaxRegistryNo(invoiceInfos.get(0).getTaxpayerNo());
//							invoiceApply.setBankName(invoiceInfos.get(0).getBankName());
//							invoiceApply.setBankAccount(invoiceInfos.get(0).getAccountNo());
//							invoiceApply.setBankAccountName(invoiceInfos.get(0).getBankName());
//							invoiceApply.setBuyerAddress(invoiceInfos.get(0).getAddress());
//							invoiceApply.setBuyerTel(invoiceInfos.get(0).getContactNumber());
//							invoiceApply.setMailingAddress(invoiceInfos.get(0).getPostAddress());
//							invoiceApply.setContactName(invoiceInfos.get(0).getContactPerson());
//							invoiceApply.setTel(invoiceInfos.get(0).getTelephoneNumber());
//							invoiceApply.setInvoiceName(invoiceInfos.get(0).getInvoiceName());
//							invoiceApply.setInvoiceType(invoiceInfos.get(0).getInvoiceType());
//							invoiceApply.setBuyerAddress(invoiceInfos.get(0).getAddress());
//							invoiceApply.setMailingAddress(invoiceInfos.get(0).getPostAddress());
//							invoiceApply.setBuyerAddress(invoiceInfos.get(0).getAddress());
//							invoiceApply.setMailingAddress(invoiceInfos.get(0).getPostAddress());
//							//设置收票方为店铺对应的商场
//							invoiceApply.setBuyerNo(invoiceInfos.get(0).getClientNo());
//							invoiceApply.setBuyerName(invoiceInfos.get(0).getClientName());
//						}
//					}else {
//						//设置收票方为店铺
//						invoiceApply.setBuyerNo(shop.getShopNo());
//						invoiceApply.setBuyerName(shop.getShortName());
//					}
//				}
//			}
			
			BigDecimal totalInvoice = new BigDecimal(0);
			
			for (BalanceInvoiceApplyGenerator invoiceApplyGenerator : applyGenerators) {
				
				BillBalanceInvoiceSource invoiceSource = new BillBalanceInvoiceSource();
				invoiceSource.setBalanceNo(invoiceApplyGenerator.getBillNo());
				invoiceSource.setBillNo(invoiceNo);
				invoiceSource.setAmount(invoiceApplyGenerator.getAmount());
				invoiceSource.setBalanceType(invoiceApplyGenerator.getBalanceType());
				invoiceSource.setId(UUIDGenerator.getUUID());
				invoiceSource.setSalerNo(invoiceApplyGenerator.getSalerNo());
				//添加到关联表
				billBalanceInvoiceSourceService.add(invoiceSource);
				//更新结算单信息
				BillShopBalance shopBalance = new BillShopBalance();
				shopBalance.setBalanceNo(invoiceApplyGenerator.getBillNo());
				shopBalance.setInvoiceApplyDate(new Date());
				shopBalance.setInvoiceApplyNo(invoiceNo);
				shopBalance.setStatus(BalanceStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo());
				billShopBalanceService.updateInvoiceByBalanceNo(shopBalance);
				//更新结算期信息
				ShopBalanceDate shopBalancDate = new ShopBalanceDate();
				shopBalancDate.setShopNo(invoiceApplyGenerator.getShopNo());
				shopBalancDate.setMonth(invoiceApplyGenerator.getBalanceMonth());
				shopBalancDate.setBillalready((byte)2);
				shopBalancDate.setBalanceStartDate(invoiceApplyGenerator.getBalanceStartDate());
				shopBalancDate.setBalanceEndDate(invoiceApplyGenerator.getBalanceEndDate());
				shopBalanceDateService.updateBalanceDateSet(shopBalancDate);
					
				//结算单大类汇总信息
				this.addBalanceInvoiceApplyDtl(invoiceApplyGenerator, invoiceApply);
				
				totalInvoice = totalInvoice.add(invoiceApplyGenerator.getAmount());
			}
			invoiceApply.setAmount(totalInvoice);
			CurrencyManagement currency=currencyManagementService.findDefaultCurrency();
			invoiceApply.setCurrency(currency.getCurrencyCode());
			invoiceApply.setStatus(1);
			invoiceApply.setId(UUIDGenerator.getUUID());
			count += billBalanceInvoiceApplyService.add(invoiceApply);
		}
		if(count > 0){
			if(null == flag){
				flag = new HashMap<String,Object>();
			}
			flag.put("success", true);
		}
		return flag;
	}

	private void addBalanceInvoiceApplyDtl(BalanceInvoiceApplyGenerator applyGenerator, BillBalanceInvoiceApply invoiceApply) throws ServiceException {
		//获取结算单大类汇总信息
		Map<String, Object> balanceMap = new HashMap<String, Object>();
		balanceMap.put("balanceNo", applyGenerator.getBillNo());
		List<BillShopBalanceCateSum> sumList = billShopBalanceCateSumService.findShopCateSummary(balanceMap);
		//获取结算单头档信息
		List<BillShopBalance> billShopBalances = billShopBalanceService.findByBiz(null, balanceMap);
		//获取结算单大类汇总金额
//		BigDecimal allSendAmount = billShopBalanceCateSumService.getSumAmount(balanceMap);
		
		if (!CollectionUtils.isEmpty(sumList)) {
			//根据店铺找店铺开票规则， 找到开票信息维护
			Map<String, Object> shopNoMap = new HashMap<String, Object>();
			shopNoMap.put("shopNo", applyGenerator.getShopNo());
			List<Shop> invoiceShops = shopService.findByBiz(null, shopNoMap);
			shopNoMap.put("companyNo", applyGenerator.getSalerNo());
			shopNoMap.put("validDate", new Date());
			List<ShopGroup> invoiceShopGroups = shopGroupService.getShopGroupNoByShopNo(shopNoMap);
			
			for (int eachNo = 0 ; eachNo < sumList.size(); eachNo++) {
				BillShopBalanceCateSum billShopBalanceCateSum = sumList.get(eachNo);
				BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
				billBalanceInvoiceDtl.setQty(billShopBalanceCateSum.getSaleQty());
				
				if(StringUtils.isNotEmpty(billShopBalanceCateSum.getCategoryNo())){
					//通过大类获取大类的发票名称
					String categoryNo = billShopBalanceCateSum.getCategoryNo();
					Map<String, Object> categoryMap = new HashMap<String, Object>();
					categoryMap.put("categoryNo", categoryNo.substring(0, 2));
					List<Category> categoryList = categoryService.findByBiz(null, categoryMap);
					if (!CollectionUtils.isEmpty(categoryList)) {
						//插入一级大类编码和名称到汇总明细表
						billBalanceInvoiceDtl.setCateNo(categoryList.get(0).getCategoryNo());
						billBalanceInvoiceDtl.setCateName(categoryList.get(0).getName());
						//默认设置开票名称为一级大类的名称
						billBalanceInvoiceDtl.setSalerName(categoryList.get(0).getName());
					}
					if(!CollectionUtils.isEmpty(invoiceShopGroups)){
						if (StringUtils.isNotEmpty(invoiceShopGroups.get(0).getTemplateNo())) {
							//根据店铺找店铺开票规则， 找到开票信息维护
							Map<String, Object> invoiceTemplateSetMap = new HashMap<String, Object>();
							invoiceTemplateSetMap.put("invoiceTempNo", invoiceShopGroups.get(0).getTemplateNo());
							invoiceTemplateSetMap.put("categoryNo", categoryNo.substring(0, 2));
							List<InvoiceTemplateSetDtl> invoiceTemplateSetDtls = invoiceTemplateSetDtlService.findByBiz(null, invoiceTemplateSetMap);
							if(!CollectionUtils.isEmpty(invoiceTemplateSetDtls)){
								//设置开票名称为发票模板配置的名称
								billBalanceInvoiceDtl.setSalerName(invoiceTemplateSetDtls.get(0).getInvoiceName());
								//如果发票模板是否启用数量控制为：1 是，则数量为0
								if(null != invoiceTemplateSetDtls.get(0).getQtyControlFlag() 
										&& invoiceTemplateSetDtls.get(0).getQtyControlFlag() == 1){
									billBalanceInvoiceDtl.setQty(0);
								}
							}
						}
					}
				}
				
				if (!CollectionUtils.isEmpty(invoiceShops)) {
					billBalanceInvoiceDtl.setRetailType(invoiceShops.get(0).getRetailType());
				}
				billBalanceInvoiceDtl.setBalanceNo(billShopBalanceCateSum.getBalanceNo());
				billBalanceInvoiceDtl.setBillNo(invoiceApply.getBillNo());
				billBalanceInvoiceDtl.setBalanceStartDate(billShopBalanceCateSum.getBalanceStartDate());
				billBalanceInvoiceDtl.setBalanceEndDate(billShopBalanceCateSum.getBalanceEndDate());
				billBalanceInvoiceDtl.setBrandName(billShopBalanceCateSum.getBrandName());
				billBalanceInvoiceDtl.setBrandNo(billShopBalanceCateSum.getBrandNo());
				billBalanceInvoiceDtl.setCategoryName(billShopBalanceCateSum.getCategoryName());
				billBalanceInvoiceDtl.setCategoryNo(billShopBalanceCateSum.getCategoryNo());
				
//				BigDecimal billAmount = new BigDecimal(0);//商场开票金额
				//设置结算单信息到开票明细
				if(!CollectionUtils.isEmpty(billShopBalances)){
					billBalanceInvoiceDtl.setOrganNo(billShopBalances.get(0).getOrganNo());
					billBalanceInvoiceDtl.setOrganName(billShopBalances.get(0).getOrganName());
					billBalanceInvoiceDtl.setActualRate(billShopBalances.get(0).getActualRate());
					billBalanceInvoiceDtl.setContractRate(billShopBalances.get(0).getContractRate());
//					billAmount = billShopBalances.get(0).getMallBillingAmount() == null ? new BigDecimal(0) : billShopBalances.get(0).getMallBillingAmount();
				}
				//大类的终端收入金额 
				BigDecimal salesAmount = billShopBalanceCateSum.getSaleAmount() == null ? new BigDecimal(0) : billShopBalanceCateSum.getSaleAmount();
				billBalanceInvoiceDtl.setPosEarningAmount(salesAmount);
				//计算大类价格
				billBalanceInvoiceDtl.setSendAmount(billShopBalanceCateSum.getBillingAmount());
				
				billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
				billBalanceInvoiceDtl.setFullName(billShopBalanceCateSum.getFullName());
				if(null != invoiceApply.getInvoiceType() && invoiceApply.getInvoiceType() == 1){//发票类型 0、普通发票 1、增值票
					billBalanceInvoiceDtl.setNoTaxAmount(billBalanceInvoiceDtl.getSendAmount().divide(
							new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
					billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
							new BigDecimal(0.17)));
					billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
				}
				
				billBalanceInvoiceDtl.setShopNo(billShopBalanceCateSum.getShopNo());
				billBalanceInvoiceDtl.setShortName(billShopBalanceCateSum.getShortName());
				billBalanceInvoiceDtl.setId(UUIDGenerator.getUUID());
				billBalanceInvoiceDtl.setSalerNo(applyGenerator.getSalerNo());
				billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl);
			}
		}
		
	}

	/**
	 * 地区其他类型的结算单-开票申请
	 * @param balanceInvoiceApplyGenerators
	 * @throws ServiceException
	 * @author wangxy
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private Map<String,Object> otherBalanceInvoiceApplyGenerate(
			List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerators) throws ServiceException {
		//<saler,  <buyer,List<module>>>
		
		Map<String, Object> flag = null;
		
		int count = 0 ;
		
		Map<String, Map<String,List<BalanceInvoiceApplyGenerator>>> applyListMap = new HashMap<String, Map<String,List<BalanceInvoiceApplyGenerator>>>();
		
		Map<String, List<BalanceInvoiceApplyGenerator>> applyKeyMap = null;
		List<BalanceInvoiceApplyGenerator> buyerApplyGenerators = null;
		
		//按照调出公司+调入公司将结算单合并
		for (BalanceInvoiceApplyGenerator applyGenerator : balanceInvoiceApplyGenerators) {
			applyKeyMap = new HashMap<String, List<BalanceInvoiceApplyGenerator>>();
			
			if (applyListMap.containsKey(applyGenerator.getSalerNo())) {
				
				if (applyListMap.get(applyGenerator.getSalerNo()).containsKey(applyGenerator.getBuyerNo())) {
					applyListMap.get(applyGenerator.getSalerNo()).get(applyGenerator.getBuyerNo()).add(applyGenerator);
				}else {
					buyerApplyGenerators = new ArrayList<BalanceInvoiceApplyGenerator>();
					buyerApplyGenerators.add(applyGenerator);
					applyListMap.get(applyGenerator.getSalerNo()).put(applyGenerator.getBuyerNo(), buyerApplyGenerators);
				}
			}else {
				buyerApplyGenerators = new ArrayList<BalanceInvoiceApplyGenerator>();
				buyerApplyGenerators.add(applyGenerator);
				applyKeyMap.put(applyGenerator.getBuyerNo(),buyerApplyGenerators);
				applyListMap.put(applyGenerator.getSalerNo(), applyKeyMap);
			}
		}
		
		//将合并后的结算单生成开票申请
		Iterator<Map.Entry<String,Map<String,List<BalanceInvoiceApplyGenerator>>>> salerIterator = applyListMap.entrySet().iterator();
		while (salerIterator.hasNext()) {
			Map.Entry<String,Map<String,List<BalanceInvoiceApplyGenerator>>> entry = (Map.Entry<String,Map<String,List<BalanceInvoiceApplyGenerator>>>) salerIterator.next(); 
			String salerNo = entry.getKey();
			Map<String,List<BalanceInvoiceApplyGenerator>> buyerMap = entry.getValue();
			
			Map<String, Object> invoiceInfoMap = null;
			List<InvoiceInfo> invoiceInfos = null;
			BillBalanceInvoiceApply invoiceApply = null;
			String invoiceNo =null;
			List<String> balanceNos = null;
			
			Iterator<Map.Entry<String,List<BalanceInvoiceApplyGenerator>>> buyerIterator = buyerMap.entrySet().iterator();
			while (buyerIterator.hasNext()) {
				Map.Entry<String,List<BalanceInvoiceApplyGenerator>> buyerEntry = (Map.Entry<String,List<BalanceInvoiceApplyGenerator>>) buyerIterator.next(); 
				
				String buyerNo = buyerEntry.getKey();
				List<BalanceInvoiceApplyGenerator> applyGenerators = buyerEntry.getValue();
				
				invoiceApply = new BillBalanceInvoiceApply();
				
				invoiceApply.setBuyerNo(applyGenerators.get(0).getBuyerNo());
				invoiceApply.setBuyerName(applyGenerators.get(0).getBuyerName());
				invoiceApply.setSalerNo(applyGenerators.get(0).getSalerNo());
				invoiceApply.setSalerName(applyGenerators.get(0).getSalerName());
				
				/*************************** wang.yj 增加管理城市及结算月 *********************/
				
				invoiceApply.setOrganNo(applyGenerators.get(0).getOrganNo());
				invoiceApply.setOrganName(applyGenerators.get(0).getOrganName());
				invoiceApply.setMonth(applyGenerators.get(0).getBalanceMonth());
				
				/*************************** wang.yj end *********************/
				
				invoiceNo = commonUtilService.getNewBillNoCompanyNo(invoiceApply.getSalerNo(),null,BillBalanceInvoiceApply.class.getSimpleName());
				invoiceApply.setBillNo(invoiceNo);
				invoiceApply.setInvoiceApplyDate(new Date());
				invoiceApply.setPostPayDate(new Date());
				invoiceApply.setCreateTime(new Date());
				
				invoiceApply.setInvoiceType((byte)1);//默认为增值票
				invoiceApply.setPreInvoice((byte)1);//默认为否
				
				invoiceInfoMap = new HashMap<String, Object>();
				//查询客户信息数据
				invoiceInfoMap.put("companyNo", salerNo);
				invoiceInfoMap.put("clientNo", buyerNo);
//				invoiceInfoMap.put("status", 1);
				invoiceInfoMap.put("month", new Date());
				invoiceInfos = invoiceInfoService.findByBiz(null, invoiceInfoMap);
				InvoiceInfo invoiceInfo = null;
				if (!CollectionUtils.isEmpty(invoiceInfos)) {
					//返回多笔开票信息时，优先取首选的那笔记录
					for (InvoiceInfo invoiceInfo2 : invoiceInfos) {
						if(invoiceInfo2.getStatus() == 1){
							invoiceInfo = invoiceInfo2;
							break;
						}
					}
					//如果多笔中都没有首选的记录，则取第一笔
					if(null == invoiceInfo){
						invoiceInfo = invoiceInfos.get(0);
					}
					if(null != invoiceInfo){
						invoiceApply.setInvoiceName(invoiceInfo.getInvoiceName());
						invoiceApply.setInvoiceType(invoiceInfo.getInvoiceType());
						invoiceApply.setBankName(invoiceInfo.getBankName());
						invoiceApply.setBankAccountName(invoiceInfo.getBankName());
						invoiceApply.setBankAccount(invoiceInfo.getAccountNo());
						invoiceApply.setTaxRegistryNo(invoiceInfo.getTaxpayerNo());
						invoiceApply.setTel(invoiceInfo.getTelephoneNumber());
						invoiceApply.setContactName(invoiceInfo.getContactPerson());
						invoiceApply.setBuyerTel(invoiceInfo.getContactNumber());
					}
				}else{
					flag = new HashMap<String,Object>();
					flag.put("error", "没有维护开票信息，不能开票.");
					return flag;
				}
				
				BigDecimal totalInvoice = new BigDecimal(0);
				BillBalanceInvoiceSource invoiceSource = null;
				BillBalanceInvoiceApply updateCondition = null;
				//待大类数据汇总的结算单号
				balanceNos = new ArrayList<String>();
				for (BalanceInvoiceApplyGenerator invoiceApplyGenerator : applyGenerators) {
					
					balanceNos.add(invoiceApplyGenerator.getBillNo());
					
					invoiceSource = new BillBalanceInvoiceSource();
					invoiceSource.setBalanceNo(invoiceApplyGenerator.getBillNo());
					invoiceSource.setBillNo(invoiceNo);
					invoiceSource.setAmount(invoiceApplyGenerator.getAmount());
					invoiceSource.setBalanceType(invoiceApplyGenerator.getBalanceType());
					invoiceSource.setId(UUIDGenerator.getUUID());
					invoiceSource.setSalerNo(invoiceApplyGenerator.getSalerNo());
					//添加到关联表
					billBalanceInvoiceSourceService.add(invoiceSource);
					
					//更新结算单信息
					updateCondition = new BillBalanceInvoiceApply();
					updateCondition.setBillNo(invoiceApplyGenerator.getBillNo());
					updateCondition.setInvoiceApplyNo(invoiceNo);
					updateCondition.setStatus(BalanceStatusEnums.MAKE_INVOICE.getTypeNo());
					billBalanceInvoiceApplyService.updateInvoiceApplyNo(updateCondition);
					
					totalInvoice = totalInvoice.add(invoiceApplyGenerator.getAmount());
				}
				
				invoiceApply.setAmount(totalInvoice);
				CurrencyManagement currency=currencyManagementService.findDefaultCurrency();
				invoiceApply.setCurrency(currency.getCurrencyCode());
				invoiceApply.setStatus(1);
				invoiceApply.setId(UUIDGenerator.getUUID());
				count += billBalanceInvoiceApplyService.add(invoiceApply);
				
				//获取结算单大类汇总信息
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("balanceNos", balanceNos);
				List<BillSaleBalance> saleList = billBalanceInvoiceApplyService.getBillSaleBalanceSum(null,null,null,params);
				
				
//				//其他扣项汇总金额
//				BigDecimal deductionAmount = billBalanceInvoiceApplyService.findDeductionAmountByBalanceNos(params);
				
				//汇总的明细
				Map<String,BillBalanceInvoiceDtl> dtlTotalMap = new HashMap<String,BillBalanceInvoiceDtl>();
				StringBuilder builderKey = null;
				if(!CollectionUtils.isEmpty(saleList)){
					for (BillSaleBalance billSaleBalance : saleList) {
						BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
						//根据商品分类获取财务大类名称
						if(StringUtils.isNotEmpty(billSaleBalance.getCategoryNo())){
							String categoryNo = billSaleBalance.getCategoryNo();
							Map<String, Object> categoryMap = new HashMap<String, Object>();
							categoryMap.put("categoryNo", categoryNo.substring(0, 2));
							List<Category> categoryList = categoryService.findByBiz(null, categoryMap);
							if (!CollectionUtils.isEmpty(categoryList)) {
								//插入一级大类编码和名称到汇总明细表
								billBalanceInvoiceDtl.setCateNo(categoryList.get(0).getCategoryNo());
								billBalanceInvoiceDtl.setCateName(categoryList.get(0).getName());
								//默认设置开票名称为一级大类名称
								billBalanceInvoiceDtl.setSalerName(categoryList.get(0).getName());
							}
							
							Map<String, Object> financialCategoryMap = new HashMap<String, Object>();
							financialCategoryMap.put("companyNo", invoiceApply.getSalerNo());
							
							List<FinancialCategory> financialCategoryList = null;
							//设置开票名称
							while (categoryNo.length() >= 2) {
								financialCategoryMap.put("categoryNo", categoryNo);
								financialCategoryList = financialCategoryService.selectCateInfoByCateNo(financialCategoryMap);
								if (CollectionUtils.isEmpty(financialCategoryList)) {
									categoryNo = categoryNo.substring(0, categoryNo.length()-2);
								}else {
									//设置开票名称
									billBalanceInvoiceDtl.setSalerName(financialCategoryList.get(0).getName());
//									// 根据大类及财务大类编号，查询明细，主要作数量控制
									financialCategoryMap.put("categoryNo", categoryNo);
									financialCategoryMap.put("financialCategoryNo", financialCategoryList.get(0).getFinancialCategoryNo());
									List<FinancialCategoryDtl> fcDtlList = financialCategoryDtlService.findByBiz(null, financialCategoryMap);
									//如果发票模板是否启用数量控制为：是，则数量为0
									if (null != fcDtlList && null != fcDtlList.get(0) && null != fcDtlList.get(0).getQtyControlFlag() 
											&& fcDtlList.get(0).getQtyControlFlag() == 1 ){
										billBalanceInvoiceDtl.setQty(0);
									}
									break;
								}
							}
						}
						billBalanceInvoiceDtl.setBillNo(invoiceNo);
						billBalanceInvoiceDtl.setOrganNo(billSaleBalance.getOrganNoFrom());
						billBalanceInvoiceDtl.setOrganName(billSaleBalance.getOrganNameFrom());
						billBalanceInvoiceDtl.setBalanceEndDate(billSaleBalance.getBalanceEndDate());
						billBalanceInvoiceDtl.setBalanceStartDate(billSaleBalance.getBalanceStartDate());
						billBalanceInvoiceDtl.setBrandName(billSaleBalance.getBrandName());
						billBalanceInvoiceDtl.setBrandNo(billSaleBalance.getBrandNo());
						billBalanceInvoiceDtl.setCategoryName(billSaleBalance.getCategoryName());
						billBalanceInvoiceDtl.setCategoryNo(billSaleBalance.getCategoryNo());
						billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
						billBalanceInvoiceDtl.setSendAmount(billSaleBalance.getSendAmount() == null ? new BigDecimal(0) : billSaleBalance.getSendAmount());
						
						billBalanceInvoiceDtl.setPosEarningAmount(new BigDecimal(0));
						billBalanceInvoiceDtl.setQty(billSaleBalance.getSendQty());
						billBalanceInvoiceDtl.setSalerNo(invoiceApply.getSalerNo());
	
						builderKey = new StringBuilder("");
						builderKey.append(billBalanceInvoiceDtl.getOrganNo()).append("|").
						append(billBalanceInvoiceDtl.getBrandNo()).append("|")
						.append(billBalanceInvoiceDtl.getCateNo()).append("|")
						.append(billBalanceInvoiceDtl.getSalerName());
						
						if (dtlTotalMap.containsKey(builderKey.toString())) {
							dtlTotalMap.get(builderKey.toString()).setQty(billBalanceInvoiceDtl.getQty());
							dtlTotalMap.get(builderKey.toString()).setSendAmount(billBalanceInvoiceDtl.getSendAmount());
						}else {
							dtlTotalMap.put(builderKey.toString(), billBalanceInvoiceDtl);
						}
					}
				}
				
//				boolean firstflag = true;
				Iterator<Map.Entry<String,BillBalanceInvoiceDtl>> totalIterator = dtlTotalMap.entrySet().iterator();
				while (totalIterator.hasNext()) {
					Map.Entry<String,BillBalanceInvoiceDtl> totalEntry = (Map.Entry<String,BillBalanceInvoiceDtl>) totalIterator.next(); 
					BillBalanceInvoiceDtl total = totalEntry.getValue();
					
					//其他扣项减在第一个汇总大类里
//					if (firstflag && null != deductionAmount) {
//						total.setSendAmount(total.getSendAmount().subtract(deductionAmount));
//						firstflag = false;
//					}
					if(null != invoiceApply.getInvoiceType() && invoiceApply.getInvoiceType() == 1){//发票类型 0、普通发票 1、增值票
						total.setNoTaxAmount(total.getSendAmount().divide(
								new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
						total.setTaxAmount(total.getNoTaxAmount().multiply(
								new BigDecimal(0.17)));
						total.setTaxRate(new BigDecimal(0.17));
					}
					total.setId(UUIDGenerator.getUUID());
					billBalanceInvoiceDtlService.add(total);
				}
			}
		}
		
		if(count > 0){
			if(null == flag){
				flag = new HashMap<String,Object>();
			}
			flag.put("success", true);
		}
		return flag;
	}
	
	/**
	 * 生成开票申请－单据开票
	 * @param balanceInvoiceApplyTemp 选中的单据记录
	 * @param createUser 当前登记用户名称
	 * @param invoiceInfoStatus 收票方的开票信息状态
	 * @return
	 * @throws ManagerException
	 */	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> addInvoiceApplyMainInfo(List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyTemp,String createUser,String invoiceInfoStatus) throws ManagerException{
		
		Map<String, Object> flag = null;
		
		try{
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			BigDecimal totalAmount = new BigDecimal(0);
			String companyName = null; String companyNo = null;
			String buyerName = null; String buyerNo = null;
			String organName = null; String organNoTemp = null;
			String month = null;
			String brandNotemp = "";String brandName = "";
			
			Map<String,List<BalanceInvoiceApplyGenerator>> categoryMaps = new HashMap<String,List<BalanceInvoiceApplyGenerator>>();
			
			//以单据编号为Key的记录
			Map<String,BalanceInvoiceApplyGenerator> orderNoKeyMaps = new HashMap<String,BalanceInvoiceApplyGenerator>();
			
			String remark = "";
			String tempRek = "";
			for(BalanceInvoiceApplyGenerator balanceInvoiceApply : balanceInvoiceApplyTemp){
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("orderNo", balanceInvoiceApply.getBillNo());
				params.put("orderDtlId", balanceInvoiceApply.getDtlId());
				List<InvoiceApplyConfirmDtl> confirmDtlList = invoiceApplyConfirmDtlService.findByBiz(null, params);
				for (InvoiceApplyConfirmDtl invoiceApplyConfirmDtl : confirmDtlList) {
					if(StringUtils.isNotBlank(invoiceApplyConfirmDtl.getInvoiceApplyNo())){
						flag = new HashMap<String,Object>();
						flag.put("error", "保存失败，单据编号："+balanceInvoiceApply.getBillNo()+" 已做过开票申请。");
						return flag;
					}
				}
				companyName = balanceInvoiceApply.getSalerName();
				companyNo = balanceInvoiceApply.getSalerNo();
				buyerNo = balanceInvoiceApply.getBuyerNo();
				buyerName = balanceInvoiceApply.getBuyerName();
				organName = balanceInvoiceApply.getOrganName();
				organNoTemp = balanceInvoiceApply.getOrganNo();
//				brandNotemp = balanceInvoiceApply.getBrandNo();
//				brandName = balanceInvoiceApply.getBrandName();
				if(null != balanceInvoiceApply.getBalanceMonth()){
					month = balanceInvoiceApply.getBalanceMonth();
				}else if(null != balanceInvoiceApply.getBalanceDate()){
					month = df.format(balanceInvoiceApply.getBalanceDate());
				}
				
				if(balanceInvoiceApply.getAmount() != null){
					totalAmount = totalAmount.add(balanceInvoiceApply.getAmount());
				}
				
				String categoryNo = balanceInvoiceApply.getCategoryNo();
				String brandNo = balanceInvoiceApply.getBrandNo();
				String organNo = balanceInvoiceApply.getOrganNo();
				String key = categoryNo +"|"+ brandNo + "|" + organNo;
				
				//根据大类汇总
				if(!categoryMaps.containsKey(key)){
					
					List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerateList = new ArrayList<BalanceInvoiceApplyGenerator>();
					balanceInvoiceApplyGenerateList.add(balanceInvoiceApply);
					
					categoryMaps.put(key, balanceInvoiceApplyGenerateList);
				}else{
					List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerateList = categoryMaps.get(key);
					balanceInvoiceApplyGenerateList.add(balanceInvoiceApply);
					
					categoryMaps.put(key, balanceInvoiceApplyGenerateList);
				}
				
				//处理备注信息，取单据类型名称
				if(!tempRek.equals(balanceInvoiceApply.getBalanceTypeName())){
					if(StringUtils.isNotBlank(remark)){
						remark += ",";
					}
					remark += balanceInvoiceApply.getBalanceTypeName();
					tempRek = balanceInvoiceApply.getBalanceTypeName();
				}
				
				// 处理品牌信息
				if(!brandNotemp.contains(brandNo)){
					if(StringUtils.isNotBlank(brandNotemp)){
						brandNotemp += ",";
					}
					brandNotemp += balanceInvoiceApply.getBrandNo();
					if(StringUtils.isNotBlank(brandName)){
						brandName += ",";
					}
					brandName += balanceInvoiceApply.getBrandName();
				}
				String orderNoKey = balanceInvoiceApply.getBillNo();
				//根据单据号
				if(!orderNoKeyMaps.containsKey(orderNoKey)){
					orderNoKeyMaps.put(orderNoKey, balanceInvoiceApply);
				}
			}
			
			if(totalAmount.equals(new BigDecimal(0))){
				flag = new HashMap<String,Object>();
				flag.put("error", "开票金额为 0 ");
//				return flag;
			}
			
			BillBalanceInvoiceApply billBalanceInvoiceApply = new BillBalanceInvoiceApply();
			
			//调用单据编号拼接处理方法，返回最终的单据编号
			String serialNo = commonUtilService.getNewBillNoCompanyNo(companyNo,null,BillBalanceInvoiceApply.class.getSimpleName());
			billBalanceInvoiceApply.setBillNo(serialNo);
			billBalanceInvoiceApply.setAmount(totalAmount);
			billBalanceInvoiceApply.setSalerNo(companyNo);
			billBalanceInvoiceApply.setSalerName(companyName);
			billBalanceInvoiceApply.setBuyerNo(buyerNo);
			billBalanceInvoiceApply.setBuyerName(buyerName);
			Date currentDate = new Date();
			billBalanceInvoiceApply.setInvoiceApplyDate(currentDate);
			billBalanceInvoiceApply.setPostPayDate(currentDate);
			billBalanceInvoiceApply.setCreateTime(currentDate);
			
			CurrencyManagement currency=currencyManagementService.findDefaultCurrency();
			billBalanceInvoiceApply.setCurrency(currency.getCurrencyCode());
	
//			billBalanceInvoiceApply.setInvoiceType(new Integer(0).byteValue());
			billBalanceInvoiceApply.setPreInvoice(new Integer(1).byteValue());
			
			billBalanceInvoiceApply.setStatus(new Integer(1));
			billBalanceInvoiceApply.setRemark(remark);
			billBalanceInvoiceApply.setCreateUser(createUser);
			
			billBalanceInvoiceApply.setOrganName(organName);
			billBalanceInvoiceApply.setOrganNo(organNoTemp);
			billBalanceInvoiceApply.setMonth(month);
			billBalanceInvoiceApply.setBrandNo(brandNotemp);
			billBalanceInvoiceApply.setBrandName(brandName);
			//initialization invoice info 
			initInvoiceInfo(flag,billBalanceInvoiceApply,invoiceInfoStatus);
			
			//For bill_balance_invoice_dtl
			if(orderNoKeyMaps.size() > 0){
				Iterator<String> iterator = orderNoKeyMaps.keySet().iterator();
				while(iterator.hasNext()){
					String key = iterator.next();
					BillBalanceInvoiceSource billBalanceSource = new BillBalanceInvoiceSource();
					BalanceInvoiceApplyGenerator balanceInvoiceApplyGenerator = orderNoKeyMaps.get(key);
					billBalanceSource.setBillNo(serialNo);
					billBalanceSource.setBalanceType(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo());
					billBalanceSource.setAmount(totalAmount);
					billBalanceSource.setBalanceNo(balanceInvoiceApplyGenerator.getBillNo());
					billBalanceSource.setBillTypeName(balanceInvoiceApplyGenerator.getBalanceTypeName());
					billBalanceSource.setSalerNo(balanceInvoiceApplyGenerator.getSalerNo());
					billBalanceSource.setId(UUIDGenerator.getUUID());
					//源单TAB页
					billBalanceInvoiceSourceService.add(billBalanceSource);
				}
			}
			addInvoiceApplyByCategoryNo(categoryMaps,serialNo);
			
			//修改关联关系信息,回写开票申请号到关系表中
			updateInvoiceApplyConfirmDtl(balanceInvoiceApplyTemp,billBalanceInvoiceApply);
			//For bill_balance_invoice_apply
			billBalanceInvoiceApply.setId(UUIDGenerator.getUUID());
			int count = billBalanceInvoiceApplyService.add(billBalanceInvoiceApply);
			if(count > 0){
				if(null == flag){
					flag = new HashMap<String,Object>();
				}
				flag.put("success", true);
			}
			if(null != flag ){
				return flag;
			}
		}catch (Exception e) {
			throw new ManagerException();
		}
		return flag;
	}
	
	/**
	 * 查询开票信息
	 * @param billBalanceInvoiceApply
	 * @return
	 * @throws ServiceException
	 */
	private void initInvoiceInfo(Map<String, Object> flag,BillBalanceInvoiceApply billBalanceInvoiceApply,String invoiceInfoStatus) throws ManagerException {
//		Map<String, Object> flag = null;
		String salerNo = billBalanceInvoiceApply.getSalerNo();
		String buyerNo = billBalanceInvoiceApply.getBuyerNo();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("companyNo", salerNo);
		params.put("clientNo", buyerNo);
		if(StringUtils.isNotBlank(invoiceInfoStatus)){
			params.put("status", invoiceInfoStatus);
		}else{
			params.put("status", "1");
		}
		try {
			List<InvoiceInfo> invoiceInfoList = invoiceInfoService.findByBiz(null, params);
			
			if(invoiceInfoList != null && invoiceInfoList.size() > 0){
				
				InvoiceInfo invoiceInfo = invoiceInfoList.get(0);
				if(StringUtils.isNotBlank(invoiceInfo.getInvoiceName())){
					billBalanceInvoiceApply.setBankName(invoiceInfo.getBankName());
					billBalanceInvoiceApply.setBankAccount(invoiceInfo.getAccountNo());
					billBalanceInvoiceApply.setBankAccountName(invoiceInfo.getAccountNo());
					billBalanceInvoiceApply.setTel(invoiceInfo.getTelephoneNumber());
					billBalanceInvoiceApply.setContactName(invoiceInfo.getContactPerson());
					billBalanceInvoiceApply.setMailingAddress(invoiceInfo.getPostAddress());
					billBalanceInvoiceApply.setBuyerAddress(invoiceInfo.getAddress());
					billBalanceInvoiceApply.setTaxRegistryNo(invoiceInfo.getTaxpayerNo());
					billBalanceInvoiceApply.setInvoiceName(invoiceInfo.getInvoiceName());
					billBalanceInvoiceApply.setBuyerTel(invoiceInfo.getContactNumber());
					billBalanceInvoiceApply.setInvoiceType(invoiceInfo.getInvoiceType());
					billBalanceInvoiceApply.setPreInvoice((byte)1);
				}else{
					flag = new HashMap<String, Object>();
					flag.put("error", "开票名称为空，请完善开票信息.");
				}
			}else{
				flag = new HashMap<String, Object>();
				flag.put("error", "没有维护开票信息，不能开票.");
			}
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
	
	public void addInvoiceApplyByCategoryNo(Map<String,List<BalanceInvoiceApplyGenerator>> categoryMaps,String serialNo) throws ServiceException{
		
		Iterator<String> iterator = categoryMaps.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			
			List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerator = categoryMaps.get(key);
			
			BigDecimal totalAmount = new BigDecimal(0);
			int itemCount = 0;
			String salerNo = null;
			String brandNo = null ; String brandName = null ;
			String shopName = null ; String shopNo = null ;
			String categoryName = null ; String categoryNo = null ;
			String cateNo = null; String cateName = null;
			String organName = null; String organNo = null;
			Date balanceStartDate = null ; Date balanceEndDate = null;
			String invoiceName = null;
			
			BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
			for(BalanceInvoiceApplyGenerator balanceInvoiceApply : balanceInvoiceApplyGenerator){
				
				salerNo = balanceInvoiceApply.getSalerNo();
				brandName = balanceInvoiceApply.getBrandName();
				brandNo = balanceInvoiceApply.getBrandNo();
				shopName = balanceInvoiceApply.getShopName();
				shopNo = balanceInvoiceApply.getShopNo();
				categoryName = balanceInvoiceApply.getCategoryName();
				categoryNo = balanceInvoiceApply.getCategoryNo();
				
				if(categoryNo != null && !"".equals(categoryNo)){
					cateNo = categoryNo.substring(0, 2);
					
					Map<String, Object> categoryMap = new HashMap<String, Object>();
					categoryMap.put("categoryNo", cateNo);
					List<Category> categoryList = categoryService.findByBiz(null, categoryMap);
					if(categoryList != null && categoryList.size() > 0){
						cateName = categoryList.get(0).getName();
						invoiceName = cateName;
					}
					
					Map<String, Object> financialCategoryMap = new HashMap<String, Object>();
					financialCategoryMap.put("companyNo", balanceInvoiceApply.getSalerNo());
					financialCategoryMap.put("categoryNo", categoryNo);
					
					List<FinancialCategory> financialCategoryList = null;
					
					//设置开票名称
					while (categoryNo.length() >= 2) {
						financialCategoryMap.put("categoryNo", categoryNo);
						financialCategoryList = financialCategoryService.selectCateInfoByCateNo(financialCategoryMap);
						if (CollectionUtils.isEmpty(financialCategoryList)) {
							categoryNo = categoryNo.substring(0, categoryNo.length()-2);
						}else {
							//设置开票名称
							invoiceName = financialCategoryList.get(0).getName();
							break;
						}
					}
				}
				
				organName = balanceInvoiceApply.getOrganName();
				organNo = balanceInvoiceApply.getOrganNo();
				
				balanceStartDate = balanceInvoiceApply.getBalanceStartDate();
				balanceEndDate = balanceInvoiceApply.getBalanceEndDate();
				
				if(balanceInvoiceApply.getAmount() != null){
					totalAmount = totalAmount.add(balanceInvoiceApply.getAmount());
				}
				if(balanceInvoiceApply.getQty() != null){
					itemCount = itemCount + balanceInvoiceApply.getQty().intValue() ;
				}
			}
			
			billBalanceInvoiceDtl.setQty(itemCount);
			billBalanceInvoiceDtl.setBillNo(serialNo);
			billBalanceInvoiceDtl.setSendAmount(totalAmount);
			billBalanceInvoiceDtl.setBrandName(brandName);
			billBalanceInvoiceDtl.setBrandNo(brandNo);
			billBalanceInvoiceDtl.setShopNo(shopNo);
			billBalanceInvoiceDtl.setShortName(shopName);
			billBalanceInvoiceDtl.setCategoryName(categoryName);
			billBalanceInvoiceDtl.setCategoryNo(categoryNo);
			billBalanceInvoiceDtl.setCateName(cateName);
			billBalanceInvoiceDtl.setCateNo(cateNo);
			
			billBalanceInvoiceDtl.setBalanceEndDate(balanceEndDate);
			billBalanceInvoiceDtl.setBalanceStartDate(balanceStartDate);
			billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
			billBalanceInvoiceDtl.setOrganName(organName);
			billBalanceInvoiceDtl.setOrganNo(organNo);
			
			billBalanceInvoiceDtl.setNoTaxAmount(billBalanceInvoiceDtl.getSendAmount().divide(new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
			billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(new BigDecimal(0.17)));
			billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
			
			billBalanceInvoiceDtl.setSalerName(invoiceName);
			billBalanceInvoiceDtl.setId(UUIDGenerator.getUUID());
			billBalanceInvoiceDtl.setSalerNo(salerNo);
			billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl);
		}
	}
	
	/**
	 * 修改关联关系信息,回写开票申请号到关系表中
	 * @param billBalanceInvoiceApply
	 * @param billBalanceInvoiceSourceList
	 * @throws ServiceException
	 * @throws ManagerException
	 * @throws RpcException
	 */
	public void updateInvoiceApplyConfirmDtl(List<BalanceInvoiceApplyGenerator> list,BillBalanceInvoiceApply billBalanceInvoiceApply) throws ManagerException{
		try {
			if(list.size() > 0){
				for(BalanceInvoiceApplyGenerator obj : list){
					InvoiceApplyConfirmDtl invoiceApplyConfirmDtl = new InvoiceApplyConfirmDtl();
	    			invoiceApplyConfirmDtl.setInvoiceApplyNo(billBalanceInvoiceApply.getBillNo());
	    			invoiceApplyConfirmDtl.setInvoiceApplyDate(billBalanceInvoiceApply.getInvoiceApplyDate());
	    			invoiceApplyConfirmDtl.setInvoiceAmount(billBalanceInvoiceApply.getAmount());
	    			invoiceApplyConfirmDtl.setOrderDtlId(obj.getDtlId());
	    			invoiceApplyConfirmDtl.setOrderNo(obj.getBillNo());
	    			invoiceApplyConfirmDtlService.updateByOrderNo(invoiceApplyConfirmDtl);
	    			
	    			Map<String,Object> params = new HashMap<String,Object>();
					params.put("companyNo", obj.getSalerNo());
					params.put("balanceDate", obj.getBalanceDate());
					params.put("invoiceFlag", 1);
					List<InsidePurchaseBalanceDate> insidePurchaseList = insidePurchaseBalanceDateService.findByBiz(null, params);
					// 回写内购结算期 
					if(CollectionUtils.isEmpty(insidePurchaseList)){
						InsidePurchaseBalanceDate insidePurchase = new InsidePurchaseBalanceDate();
						insidePurchase.setCompanyNo(obj.getSalerNo());
						insidePurchase.setInvoiceFlag(1);
						insidePurchase.setBalanceStartDate(obj.getBalanceDate());
						insidePurchaseBalanceDateService.updateInvoiceFlagByCondition(insidePurchase);
					}
				}
			}
			
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
    
}