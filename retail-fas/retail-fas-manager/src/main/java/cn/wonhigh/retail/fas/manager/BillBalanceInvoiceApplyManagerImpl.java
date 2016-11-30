package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
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
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-30 11:19:51
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
@Service("billBalanceInvoiceApplyManager")
class BillBalanceInvoiceApplyManagerImpl extends BaseCrudManagerImpl implements BillBalanceInvoiceApplyManager {
    @Resource
    private BillBalanceInvoiceApplyService billBalanceInvoiceApplyService;
    
//	@Resource
//	private CodingRuleService codingRuleService;
    
    @Resource
    private CommonUtilService commonUtilService;
	
	@Resource
	private BillBalanceInvoiceSourceService billBalanceInvoiceSourceService;
	
	@Resource
	private BillShopBalanceService billShopBalanceService;
	
	@Resource
	private BillShopBalanceCateSumService billShopBalanceCateSumService;
	
	@Resource
	private InvoiceTemplateSetDtlService invoiceTemplateSetDtlService;
	
	@Resource
	private BillBalanceInvoiceDtlService billBalanceInvoiceDtlService;
	
	@Resource
	private FinancialCategoryService financialCategoryService;
	
	@Resource
	private FinancialCategoryDtlService financialCategoryDtlService;
	
	@Resource
	private InvoiceInfoService invoiceInfoService;
	
	@Resource
	private CurrencyManagementService currencyManagementService;
	
	@Resource
	private OrderMainManager orderMainManager;
	
	@Resource
	private ShopGroupService shopGroupService;
	
	@Resource
	private ShopBalanceDateService shopBalanceDateService;
	
	@Resource
	private ShopService shopService;
	
	@Resource
	private CategoryService categoryService;
	
	@Resource
	private BillCommonInvoiceRegisterManager billCommonInvoiceRegisterManager;
	
	@Resource
	private BillCommonRegisterDtlManager billCommonRegisterDtlManager;
	
	@Resource
	private InvoiceApplyConfirmDtlService invoiceApplyConfirmDtlService;
	
	@Resource
	private InsidePurchaseBalanceDateService insidePurchaseBalanceDateService;
	
	private Log log = LogFactory.getLog(BillBalanceInvoiceApplyManagerImpl.class);
	
	private static final int RULESTATUS = 1;
	
    @Override
    public BaseCrudService init() {
        return billBalanceInvoiceApplyService;
    }
	
    @Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int remove(String[] ids) throws ManagerException {
		try {
			int iCount = 0;
			for (String str : ids) {
				String id = str.split(",")[0];
				String billNo = str.split(",")[1];
				String balanceType = str.split(",")[2];
				List<BillBalanceInvoiceSource> invoiceSources = null;
				Map<String, Object> buyerparams = new HashMap<String, Object>();
				buyerparams.put("billNo", billNo);
				invoiceSources = billBalanceInvoiceSourceService.findByBiz(null, buyerparams);
				
				//清空源单的开票申请编号以及变更状态
				if(!CollectionUtils.isEmpty(invoiceSources)){
					if(balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_MALL.getTypeNo()))){
						for (BillBalanceInvoiceSource balanceInvoiceSource :  invoiceSources) {
							BillShopBalance shopBalance = new BillShopBalance();
							shopBalance.setBalanceNo(balanceInvoiceSource.getBalanceNo());
							shopBalance.setInvoiceApplyDate(null);
							shopBalance.setInvoiceApplyNo("");
							shopBalance.setStatus(BalanceStatusEnums.SEND_FINANCE_CONFIRM.getTypeNo());
							billShopBalanceService.updateInvoiceByBalanceNo(shopBalance);
							
							BillShopBalance shopBalances = new BillShopBalance();
							shopBalances.setBalanceNo(balanceInvoiceSource.getBalanceNo());
							shopBalances = billShopBalanceService.findById(shopBalance);
							
							if(null != shopBalances){
								ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
								shopBalanceDatePar.setShopNo(shopBalances.getShopNo());
								shopBalanceDatePar.setMonth(shopBalances.getMonth());
								shopBalanceDatePar.setBalanceStartDate(shopBalances.getBalanceStartDate());
								shopBalanceDatePar.setBalanceEndDate(shopBalances.getBalanceEndDate());
								shopBalanceDateService.updateBalanceBillAlready(shopBalanceDatePar);
							}
						}

					} else if(balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()))){
//						String billNoStr = getOrderBillNo(billNo, String.valueOf(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()), "");
//						if(StringUtils.isNotEmpty(billNoStr)){
//							orderMainApi.modifyOrderForInvoiceApply(Arrays.asList(billNoStr.split(",")), null, null);
//							//会写本库销售订单
//							orderMainManager.modifyOrderForInvoiceApply(Arrays.asList(billNoStr.split(",")), null, null);
//						}
//						BillBalanceInvoiceApply invoiceApply = new BillBalanceInvoiceApply();
//						invoiceApply.setId(id);
//						invoiceApply = billBalanceInvoiceApplyService.findById(invoiceApply);
						//回写内购结算期的是否已开票标识
		    			
		    			Map<String,Object> params = new HashMap<String,Object>();
		    			params.put("invoiceApplyNo", billNo);
		    			params.put("queryType", 3);
		    			params.put("searchCompanyNo", invoiceSources.get(0).getSalerNo());
		    			int total = orderMainManager.findApplyGeneratorDetailCount(params);
		    			SimplePage page = new SimplePage(1, total, total);
		    			// 根据开票申请号查询内购销售单据信息
		    			List<BalanceInvoiceApplyGenerator> list = orderMainManager.findApplyGeneratorDetail(page, params);
		    			for (BalanceInvoiceApplyGenerator balanceInvoiceApplyGenerator : list) {
		    				params.put("companyNo", balanceInvoiceApplyGenerator.getSalerNo());
		    				params.put("balanceDate", balanceInvoiceApplyGenerator.getBalanceDate());
		    				params.put("invoiceFlag", 1);
		    				// 根据结算公司及单据日期，查询内购结算期标识为已开票的记录是否存在，存在则修改为未开票
		    				List<InsidePurchaseBalanceDate> insidePurchaseList = insidePurchaseBalanceDateService.findByBiz(null, params);
		    				// 回写内购结算期 
		    				if(!CollectionUtils.isEmpty(insidePurchaseList)){
		    					InsidePurchaseBalanceDate insidePurchase = new InsidePurchaseBalanceDate();
		    					insidePurchase.setCompanyNo(balanceInvoiceApplyGenerator.getSalerNo());
		    					insidePurchase.setInvoiceFlag(0);
		    					insidePurchase.setBalanceStartDate(balanceInvoiceApplyGenerator.getBalanceDate());
		    					insidePurchaseBalanceDateService.updateInvoiceFlagByCondition(insidePurchase);
		    				}
						}
						
						// 根据开票申请号清空开票信息
		    			invoiceApplyConfirmDtlService.updateByInvoiceApplyNo(billNo);
					} else {
						for (BillBalanceInvoiceSource billBalanceInvoiceSource : invoiceSources) {
							BillBalanceInvoiceApply invoiceApply = new BillBalanceInvoiceApply();
							invoiceApply.setBillNo(billBalanceInvoiceSource.getBalanceNo());
							invoiceApply.setInvoiceApplyNo("");
							invoiceApply.setStatus(BalanceStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo());
							billBalanceInvoiceApplyService.updateInvoiceApplyNo(invoiceApply);
						}
					}
				}
				
				
				//删除源单信息表
				billBalanceInvoiceSourceService.deleteInvoiceSource(billNo);
				
				billBalanceInvoiceDtlService.deleteInvoiceDtl(billNo);
				
				//删除开票申请表
				BillBalanceInvoiceApply invoiceApplys = new BillBalanceInvoiceApply();
				invoiceApplys.setId(id);
				iCount = billBalanceInvoiceApplyService.deleteById(invoiceApplys);
				
			}
			return iCount;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSaleBalance> getBillSaleBalanceSum(
			SimplePage page,String orderByField,String orderBy,Map<String, Object> params) {
		return billBalanceInvoiceApplyService.getBillSaleBalanceSum(page,orderByField,orderBy,params);
	}

	@Override
	public List<BillSaleBalance> getBillSaleBalanceDtl(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billBalanceInvoiceApplyService.getBillSaleBalanceDtl(page, orderByField, orderBy, params);
	}

	@Override
	public int selectBillSaleBalanceCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceInvoiceApplyService.selectBillSaleBalanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public <ModelType> int modifyById(ModelType model) throws ManagerException {
		int count = 0;
		BillBalanceInvoiceApply invoiceApply = (BillBalanceInvoiceApply)model;
		try {
			BillBalanceInvoiceApply billBalanceInvoiceApply = new BillBalanceInvoiceApply();
			billBalanceInvoiceApply.setSalerNo(invoiceApply.getSalerNo());
			billBalanceInvoiceApply.setBuyerNo(invoiceApply.getBuyerNo());
			billBalanceInvoiceApply.setBalanceType(invoiceApply.getBalanceType());
			
			if (StringUtils.isNotEmpty(invoiceApply.getBalanceType())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("companyNo", invoiceApply.getSalerNo());
				map.put("clientNo", invoiceApply.getBuyerNo());
				if(null != invoiceApply.getInvoiceInfoStatus()){
					map.put("status", invoiceApply.getInvoiceInfoStatus()+"");
				}else{
					map.put("status", RULESTATUS);
				}
				//根据公司编码、客户编码获取开票信息
				List<InvoiceInfo> invoiceInfoList = invoiceInfoService.findByBiz(null, map);
				
				if(CollectionUtils.isEmpty(invoiceInfoList)){//如果根据商场编号查询开票信息，则返回
					return -1;
				} 
				// 根据开票方查询开票信息并设置相关信息
//				findInvoiceInfoByBuyerNo(billBalanceInvoiceApply);
//				
//				//如果错误信息不为空，则表示开票信息没做维护，直接返回
//				if(StringUtils.isNotBlank(billBalanceInvoiceApply.getErrorInfo())){
//					return -1;
//				}
				if(Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()) {
					BillBalanceInvoiceSource invoiceSource = new BillBalanceInvoiceSource();
					invoiceSource.setAmount(invoiceApply.getAmount());
					invoiceSource.setBillNo(invoiceApply.getBillNo());
					billBalanceInvoiceSourceService.updateAmountByBillNo(invoiceSource);
					
					InvoiceApplyConfirmDtl invoiceApplyConfirmDtl = new InvoiceApplyConfirmDtl();
	    			invoiceApplyConfirmDtl.setInvoiceApplyNo(invoiceApply.getBillNo());
	    			invoiceApplyConfirmDtl.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
	    			invoiceApplyConfirmDtl.setInvoiceAmount(invoiceApply.getAmount());
	    			invoiceApplyConfirmDtlService.updateConfirmDtlByApplyNo(invoiceApplyConfirmDtl);
				}else if(Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_MALL.getTypeNo()){
					// 修改时，更新结算单中的开票申请日期
					BillShopBalance shopBalance = new BillShopBalance();
					shopBalance.setInvoiceApplyNo(invoiceApply.getBillNo());
					shopBalance.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
					int ret = billShopBalanceService.updateInvoiceDateByApplyNo(shopBalance);
					log.info("回写结算单的开票日期:"+invoiceApply.getInvoiceApplyDate()+";开票申请号:"+invoiceApply.getBillNo()+";结果:"+ret);
				}
			}
			count = super.modifyById(model);
			log.info("更新开票申请信息开票申请号:"+invoiceApply.getBillNo()+";结果:"+count);
		} catch (ServiceException e) {
			log.info("更新开票申请信息失败,开票申请号:"+invoiceApply.getBillNo());
			throw new ManagerException(e);
		}
		return count;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalanceInvoiceApply addFetchId(BillBalanceInvoiceApply invoiceApply,List<Object> lstItem) throws ManagerException {
		String balanceNo = invoiceApply.getBalanceNo();
		String billNo = "";
		try {
			if(StringUtils.isNotBlank(balanceNo)){
				if(balanceNo.contains(",")){
					String[] balanceNos = balanceNo.split(",");
					for (String string : balanceNos) {
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("balanceNo", string);
						param.put("balanceType", invoiceApply.getBalanceType());
						List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceService.findByBiz(null, param);
						if(!CollectionUtils.isEmpty(sourceList)){// 如果结算单已经存在，则表示已经开过票，直接返回
							invoiceApply.setErrorInfo("保存失败，结算单号："+balanceNo+" 已做过开票申请。");
							return invoiceApply;
						}
					}
				}else{
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("balanceNo", balanceNo);
					param.put("balanceType", invoiceApply.getBalanceType());
					List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceService.findByBiz(null, param);
					if(!CollectionUtils.isEmpty(sourceList)){// 如果结算单已经存在，则表示已经开过票，直接返回
						invoiceApply.setErrorInfo("保存失败，结算单号："+balanceNo+" 已做过开票申请。");
						return invoiceApply;
					}
				}
			}
			
			String requestId = BillBalanceInvoiceApply.class.getSimpleName();
			//调用单据编号拼接处理方法，返回最终的单据编号
			billNo = commonUtilService.getNewBillNoCompanyNo(invoiceApply.getSalerNo(),null,requestId);
			invoiceApply.setBillNo(billNo);
			if (null == invoiceApply.getInvoiceApplyDate()) {
				invoiceApply.setInvoiceApplyDate(new Date());
			}
			if (null == invoiceApply.getPostPayDate()) {
				invoiceApply.setPostPayDate(new Date());
			}
			invoiceApply.setCreateTime(new Date());
			if(StringUtils.isNotEmpty(invoiceApply.getBalanceType())){
				
				BillBalanceInvoiceSource invoiceSource = new BillBalanceInvoiceSource();
				if(balanceNo.contains(",")){
					String[] billNos = balanceNo.split(",");
					for (String string : billNos) {
						invoiceSource.setBalanceNo(string);
						invoiceSource.setBillNo(billNo);
						invoiceSource.setAmount(invoiceApply.getBalanceAmount());
						invoiceSource.setBalanceType(Integer.parseInt(invoiceApply.getBalanceType()));
						invoiceSource.setId(UUIDGenerator.getUUID());
						invoiceSource.setSalerNo(invoiceApply.getSalerNo());
						billBalanceInvoiceSourceService.add(invoiceSource);
					}
				}else{
					invoiceSource.setBalanceNo(balanceNo);
					invoiceSource.setBillNo(billNo);
					if(null != invoiceApply.getBalanceAmount()){
						invoiceSource.setAmount(invoiceApply.getBalanceAmount());
					}else{
						invoiceSource.setAmount(invoiceApply.getAmount());
					}
					invoiceSource.setBalanceType(Integer.parseInt(invoiceApply.getBalanceType()));
					invoiceSource.setId(UUIDGenerator.getUUID());
					invoiceSource.setSalerNo(invoiceApply.getSalerNo());
					billBalanceInvoiceSourceService.add(invoiceSource);
				}
			}

			//用来存储根据商品小类汇总的数据集合
			List<BillBalanceInvoiceDtl> invoiceDtlList = new ArrayList<BillBalanceInvoiceDtl>();
			if (StringUtils.isNotEmpty(invoiceApply.getBalanceType())
					&& Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_MALL.getTypeNo()) {

				if (StringUtils.isNotEmpty(balanceNo)) {
					//获取商场结算单信息
					BillShopBalance billShopBalance = new BillShopBalance();
					billShopBalance.setBalanceNo(balanceNo);
					billShopBalance = billShopBalanceService.findById(billShopBalance);

					//获取结算单大类汇总信息
					Map<String, Object> balanceNoMap = new HashMap<String, Object>();
					balanceNoMap.put("balanceNo", balanceNo);
					List<BillShopBalanceCateSum> sumList = billShopBalanceCateSumService.findByBiz(null, balanceNoMap);
//					//获取结算单金额汇总
//					BigDecimal allSendAmount = billShopBalanceCateSumService.getSumAmount(balanceNoMap);

					if(null != billShopBalance){
						ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
						shopBalanceDatePar.setShopNo(billShopBalance.getShopNo());
						shopBalanceDatePar.setMonth(billShopBalance.getMonth());
						shopBalanceDatePar.setBillalready((byte)2);
						shopBalanceDatePar.setBalanceStartDate(billShopBalance.getBalanceStartDate());
						shopBalanceDatePar.setBalanceEndDate(billShopBalance.getBalanceEndDate());
						shopBalanceDateService.updateBalanceDateSet(shopBalanceDatePar);
					}
					
					//新增大类汇总信息
					if (null != billShopBalance && !CollectionUtils.isEmpty(sumList)) {
						//根据店铺找店铺开票规则， 找到开票信息维护
						Map<String, Object> shopNoMap = new HashMap<String, Object>();
						shopNoMap.put("shopNo", billShopBalance.getShopNo());
						List<Shop> invoiceShops = shopService.findByBiz(null, shopNoMap);
						
						List<ShopGroup> shopGroupList = shopGroupService.getShopGroupNoByShopNo(shopNoMap);
						
//						int lastIndex = sumList.size() - 1;
//						BigDecimal totalPortion = new BigDecimal(0);
						
						for (int eachNo = 0 ; eachNo < sumList.size(); eachNo++) {
							BillShopBalanceCateSum billShopBalanceCateSum = sumList.get(eachNo);
							BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
							byte qtyContorlFlag = 0; 
							if(StringUtils.isNotEmpty(billShopBalanceCateSum.getCategoryNo())){
								Category category = new Category();
								if(StringUtils.isNotBlank(billShopBalanceCateSum.getCategoryNo())){
									category.setCategoryNo(billShopBalanceCateSum.getCategoryNo().substring(0, 2));
								}
								category = categoryService.findById(category);
								
								if(null != category){
									//插入一级大类编码和名称到汇总明细表
									//billBalanceInvoiceDtl.setCateNo(category.getCategoryNo());
									billBalanceInvoiceDtl.setCategoryNo(category.getCategoryNo());
									billBalanceInvoiceDtl.setCategoryName(category.getName());
									//billBalanceInvoiceDtl.setCateName(category.getName());
									//默认设置开票名称为一级大类的名称
									billBalanceInvoiceDtl.setSalerName(category.getName());
								}
								
								if(!CollectionUtils.isEmpty(shopGroupList)){
									Map<String, Object> paramMap = new HashMap<String, Object>();
									if(StringUtils.isNotEmpty(shopGroupList.get(0).getTemplateNo())){
										paramMap.put("invoiceTempNo", shopGroupList.get(0).getTemplateNo());
										if(StringUtils.isNotBlank(billShopBalanceCateSum.getCategoryNo())){
											paramMap.put("categoryNo", billShopBalanceCateSum.getCategoryNo().substring(0, 2));
											List<InvoiceTemplateSetDtl> dtlList = invoiceTemplateSetDtlService.findByBiz(null, paramMap);
											
											if(!CollectionUtils.isEmpty(dtlList)){
												//设置开票名称为发票模板配置的名称
												billBalanceInvoiceDtl.setSalerName(dtlList.get(0).getInvoiceName());
												qtyContorlFlag = dtlList.get(0).getQtyControlFlag() == null ? 0 : dtlList.get(0).getQtyControlFlag();
											}
										}
									}
								}
							}
							if (!CollectionUtils.isEmpty(invoiceShops)) {
								billBalanceInvoiceDtl.setRetailType(invoiceShops.get(0).getRetailType());
							}
							billBalanceInvoiceDtl.setBalanceNo(balanceNo);
							billBalanceInvoiceDtl.setBillNo(billNo);
							billBalanceInvoiceDtl.setActualRate(billShopBalance.getActualRate());
							billBalanceInvoiceDtl.setContractRate(billShopBalance.getContractRate());
							billBalanceInvoiceDtl.setBalanceEndDate(billShopBalanceCateSum.getBalanceEndDate());
							billBalanceInvoiceDtl.setBalanceStartDate(billShopBalanceCateSum.getBalanceStartDate());
							billBalanceInvoiceDtl.setBrandName(billShopBalanceCateSum.getBrandName());
							billBalanceInvoiceDtl.setBrandNo(billShopBalanceCateSum.getBrandNo());
							billBalanceInvoiceDtl.setOrganNo(billShopBalance.getOrganNo());
							billBalanceInvoiceDtl.setOrganName(billShopBalance.getOrganName());
							//billBalanceInvoiceDtl.setCategoryNo(billShopBalanceCateSum.getCategoryNo());
							//billBalanceInvoiceDtl.setCategoryName(billShopBalanceCateSum.getCategoryName());
							//商场开票金额
//							BigDecimal billAmount = billShopBalance.getMallBillingAmount() == null ? new BigDecimal(0) : billShopBalance.getMallBillingAmount();
							//大类的终端收入金额 
							BigDecimal salesAmount = billShopBalanceCateSum.getSaleAmount() == null ? new BigDecimal(0) : billShopBalanceCateSum.getSaleAmount();
//							//计算大类价格
//							if(null != allSendAmount){
//								BigDecimal portions = null;
//								if (eachNo == lastIndex) {
//									portions = new BigDecimal(1).subtract(totalPortion);
//								}else {
//									portions = salesAmount.divide(allSendAmount, 10, RoundingMode.HALF_DOWN);
//									totalPortion = totalPortion.add(portions);
//								}
//								//大类的开票金额
////								billBalanceInvoiceDtl.setSendAmount(portions.multiply(billAmount));
//							}
							billBalanceInvoiceDtl.setSendAmount(billShopBalanceCateSum.getBillingAmount());
							billBalanceInvoiceDtl.setPosEarningAmount(salesAmount);
							billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
							billBalanceInvoiceDtl.setFullName(billShopBalanceCateSum.getFullName());
//							billBalanceInvoiceDtl.setCostTotal(costTotal);
							Byte invoiceTypes = 1;
							if(invoiceApply.getInvoiceType().equals(invoiceTypes)){
								billBalanceInvoiceDtl.setNoTaxAmount(billBalanceInvoiceDtl.getSendAmount().divide(
										new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
								billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
										new BigDecimal(0.17)));
								billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
							}
							
							//如果发票模板是否启用数量控制为：是，则数量为0
							if(qtyContorlFlag == 1){
								billBalanceInvoiceDtl.setQty(0);
							}else{
								billBalanceInvoiceDtl.setQty(billShopBalanceCateSum.getSaleQty());
							}
							billBalanceInvoiceDtl.setShopNo(billShopBalanceCateSum.getShopNo());
							billBalanceInvoiceDtl.setShortName(billShopBalanceCateSum.getShortName());
							billBalanceInvoiceDtl.setId(UUIDGenerator.getUUID());
							billBalanceInvoiceDtl.setSalerNo(invoiceApply.getSalerNo());
							billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl);

							//invoiceDtlList.add(billBalanceInvoiceDtl);
						}
					}
					
/*					List<BillBalanceInvoiceDtl> dtl = getIvvoiceDtlGroupByParams(invoiceDtlList, invoiceApply.getInvoiceType());
					
					if(!CollectionUtils.isEmpty(dtl)){
						for (BillBalanceInvoiceDtl billBalanceInvoiceDtl2 : dtl) {
							billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl2);
						}
					}*/
				}
			} else {

				if (StringUtils.isNotEmpty(balanceNo) && Integer.parseInt(invoiceApply.getBalanceType()) != BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()) {
					//获取结算单大类汇总信息
					Map<String, Object> params = new HashMap<String, Object>();
					
					List<BillSaleBalance> saleList = new ArrayList<BillSaleBalance>();
					
					if(Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_SALEORDER.getTypeNo()){
						if(invoiceApply.getBalanceNo().length() > 0){
							String[] billNos = invoiceApply.getBalanceNo().split(",");
							String orBillNo = "";
							for (int x = 0; x <billNos.length; x++) {
								orBillNo += ","+billNos[x]+",";
								/*if(x == 0){
									orBillNo =","+billNos[x]+",";
								}
								if(billNos.length -1 != x){
									orBillNo += orBillNo +",";
								}*/
							}
//							params.put("billType", BillTypeEnums.SALEORDER.getRequestId());
//							params.put("bizType", BizTypeEnums.AREA_SALEORDER.getTypeNo());
							params.put("billNo", orBillNo);
							saleList = billBalanceInvoiceApplyService.getBillSaleOrderSum(params);
						}
					}else{
						params.put("balanceNo", balanceNo);
						saleList = billBalanceInvoiceApplyService.getBillSaleBalanceSum(null, null,
								null, params);
					}

					if(!CollectionUtils.isEmpty(saleList)){
						for (BillSaleBalance billSaleBalance : saleList) {
							
//							String cateName = "";
//							String cateNo = "";
							String invoiceName = "";
							String cateNos = "";
							String cateNames = "";
							// 数量是否控制
							int qtyControlFlag = 0;
							//根据商品分类获取财务大类名称
							if(StringUtils.isNotEmpty(billSaleBalance.getCategoryNo())){
								
								Category category = new Category();
								if(StringUtils.isNotBlank(billSaleBalance.getCategoryNo())){
									category.setCategoryNo(billSaleBalance.getCategoryNo().substring(0, 2));
									category = categoryService.findById(category);
								}
								if(null != category){
									invoiceName = category.getName();
									if(StringUtils.isNotBlank(billSaleBalance.getCategoryNo())){
										cateNos = billSaleBalance.getCategoryNo().substring(0, 2);
									}
									cateNames = category.getName();
								}
								
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("companyNo", invoiceApply.getSalerNo());
								
								String categoryNo = billSaleBalance.getCategoryNo();
								List<FinancialCategory> financialCategoryList = null;
//								cateNo = categoryNo;
//								cateName = billSaleBalance.getCategoryName();
								String financialCategoryNo = "";
								while (categoryNo.length() >= 2) {
									map.put("categoryNo", categoryNo);
									financialCategoryList = financialCategoryService.selectCateInfoByCateNo(map);
									if(CollectionUtils.isEmpty(financialCategoryList)){
										if(StringUtils.isNotBlank(categoryNo)){
											categoryNo = categoryNo.substring(0, categoryNo.length()-2);
										}
										continue;
									}else {
										invoiceName = financialCategoryList.get(0).getName();
										financialCategoryNo = financialCategoryList.get(0).getFinancialCategoryNo();
										break;
									}
								}
								if(StringUtils.isNotBlank(categoryNo) && StringUtils.isNotBlank(financialCategoryNo)){
									// 根据大类及财务大类编号，查询明细，主要作数量控制
									map.put("categoryNo", categoryNo);
									map.put("financialCategoryNo", financialCategoryNo);
									List<FinancialCategoryDtl> fcDtlList = financialCategoryDtlService.findByBiz(null, map);
									
									if(!CollectionUtils.isEmpty(fcDtlList)){
										qtyControlFlag = fcDtlList.get(0).getQtyControlFlag() == null ? 0 : fcDtlList.get(0).getQtyControlFlag();
									}
								}
							}
							
							BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
							//billBalanceInvoiceDtl.setBalanceNo(balanceNo);
							billBalanceInvoiceDtl.setBillNo(billNo);
							billBalanceInvoiceDtl.setBalanceEndDate(billSaleBalance.getBalanceEndDate());
							billBalanceInvoiceDtl.setBalanceStartDate(billSaleBalance.getBalanceStartDate());
							billBalanceInvoiceDtl.setBrandName(billSaleBalance.getBrandName());
							billBalanceInvoiceDtl.setBrandNo(billSaleBalance.getBrandNo());
							billBalanceInvoiceDtl.setOrganNo(billSaleBalance.getOrganNoFrom());
							billBalanceInvoiceDtl.setOrganName(billSaleBalance.getOrganNameFrom());
							billBalanceInvoiceDtl.setCategoryName(cateNames);
							billBalanceInvoiceDtl.setCategoryNo(cateNos);
							//billBalanceInvoiceDtl.setCateNo(cateNos);
							//billBalanceInvoiceDtl.setCateName(cateNames);
							billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
							billBalanceInvoiceDtl.setSendAmount(billSaleBalance.getSendAmount());
							
							Byte invoiceTypes = 1;
							if(invoiceApply.getInvoiceType().equals(invoiceTypes)){
								billBalanceInvoiceDtl.setNoTaxAmount(billBalanceInvoiceDtl.getSendAmount().divide(
										new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
								billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
										new BigDecimal(0.17)));
								billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
							}
							billBalanceInvoiceDtl.setPosEarningAmount(new BigDecimal(0));
							if(1 == qtyControlFlag){//数量是否控制：是，则数量为0
								billBalanceInvoiceDtl.setQty(0);
							}else{
								billBalanceInvoiceDtl.setQty(billSaleBalance.getSendQty());
							}
							billBalanceInvoiceDtl.setSalerName(invoiceName);

							invoiceDtlList.add(billBalanceInvoiceDtl);
							//billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl);
						}
					}
					
					List<BillBalanceInvoiceDtl> dtl = getIvvoiceDtlGroupByParams(invoiceDtlList);
					
					if(!CollectionUtils.isEmpty(dtl)){
						for (BillBalanceInvoiceDtl billBalanceInvoiceDtl2 : dtl) {
							billBalanceInvoiceDtl2.setId(UUIDGenerator.getUUID());
							billBalanceInvoiceDtl2.setSalerNo(invoiceApply.getSalerNo());
							billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl2);
						}
					}
				}else{
					//内购明细汇总
//					getAreaGroupBuy(balanceNo, billNo, invoiceApply.getSalerNo());
				}
			}
			//更新结算单上的开票申请信息反写  add pos系统反写开票申请单号
			if (StringUtils.isNotEmpty(invoiceApply.getBalanceType())) {
				if (Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_MALL.getTypeNo()) {
					BillShopBalance shopBalance = new BillShopBalance();
					shopBalance.setBalanceNo(balanceNo);
					shopBalance.setInvoiceApplyNo(billNo);
					shopBalance.setStatus(BalanceStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo());
					shopBalance.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
					billShopBalanceService.updateInvoiceByBalanceNo(shopBalance);
				} else if(Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()){   //pos回写开票申请单号
					if(StringUtils.isNotEmpty(invoiceApply.getBalanceNo())){
						
						for (Object obj : lstItem) {
							BalanceInvoiceApplyGenerator apply = (BalanceInvoiceApplyGenerator)obj;
							// 更新单据明细及开票申请关联表的开票信息
							InvoiceApplyConfirmDtl invoiceApplyConfirmDtl = new InvoiceApplyConfirmDtl();
			    			invoiceApplyConfirmDtl.setInvoiceApplyNo(billNo);
			    			invoiceApplyConfirmDtl.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
			    			invoiceApplyConfirmDtl.setInvoiceAmount(invoiceApply.getAmount());
			    			invoiceApplyConfirmDtl.setOrderDtlId(apply.getDtlId());
			    			invoiceApplyConfirmDtl.setOrderNo(apply.getBillNo());
			    			invoiceApplyConfirmDtlService.updateByOrderNo(invoiceApplyConfirmDtl);
						}
//						orderMainApi.modifyOrderForInvoiceApply(balanceNoList, billNo, invoiceApply.getInvoiceApplyDate());
//						orderMainManager.modifyOrderForInvoiceApply(balanceNoList, billNo, invoiceApply.getInvoiceApplyDate());
					}
//				}else if(Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_SALEORDER.getTypeNo()){   //记录开票信息
//					if(StringUtils.isNotEmpty(invoiceApply.getBalanceNo())){
//						String[] billNoStr = invoiceApply.getBalanceNo().split(",");
//						Date createDate = new Date();
//						for (String str : billNoStr) {
//							GroupSaleApplyInvoiceRel dtl = new GroupSaleApplyInvoiceRel();
//							dtl.setCreateTime(createDate);
//							dtl.setCreateUser(invoiceApply.getCreateUser());
//							dtl.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
//							dtl.setInvoiceApplyNo(billNo);
//							dtl.setOrderNo(str);
//							dtl.setUpdateTime(createDate);
//							dtl.setUpdateUser(invoiceApply.getCreateUser());
//							groupSaleApplyInvoiceRelService.add(dtl);
//						}
//					}
				} else {
					invoiceApply.setBillNo(balanceNo);
					invoiceApply.setInvoiceApplyNo(billNo);
					invoiceApply.setStatus(BalanceStatusEnums.MAKE_INVOICE.getTypeNo());
					billBalanceInvoiceApplyService.updateInvoiceApplyNo(invoiceApply);
				}
			}
			invoiceApply.setBillNo(billNo);
			invoiceApply.setStatus(1);
			invoiceApply.setId(UUIDGenerator.getUUID());
			billBalanceInvoiceApplyService.add(invoiceApply);
		} catch (Exception e) {
//			codingRuleService.recycleSerialNo(BillBacksectionSplit.class.getSimpleName(), billNo);
			throw new ManagerException(e);
		}
		return invoiceApply;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalanceInvoiceApply addBillBalanceInvoiceApply(BillBalanceInvoiceApply model) throws ManagerException {
		BillBalanceInvoiceApply invoiceApply = (BillBalanceInvoiceApply) model;
//		List<BillBalanceInvoiceApply> list = new ArrayList<BillBalanceInvoiceApply>();
//		list.add(invoiceApply);
//		for (int i = 0; i < list.size(); i++) {
		
			String balanceNo = invoiceApply.getBalanceNo();
			String billNo = "";
			try {
				
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("balanceNo", balanceNo);
				List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceService.findByBiz(null, param);
				if(!CollectionUtils.isEmpty(sourceList)){// 如果结算单已经存在，则表示已经开过票，直接返回
					invoiceApply.setErrorInfo("保存失败，结算单号："+balanceNo+" 已做过开票申请。");
					return invoiceApply;
				}
				String requestId = BillBalanceInvoiceApply.class.getSimpleName();
				//调用单据编号拼接处理方法，返回最终的单据编号
				billNo = commonUtilService.getNewBillNoCompanyNo(invoiceApply.getSalerNo(),null,requestId);
				invoiceApply.setBillNo(billNo);
				if (null == invoiceApply.getInvoiceApplyDate()) {
					invoiceApply.setInvoiceApplyDate(new Date());
				}
				if (null == invoiceApply.getPostPayDate()) {
					invoiceApply.setPostPayDate(new Date());
				}
				invoiceApply.setCreateTime(new Date());
				
				List<BillBalanceInvoiceDtl> invoiceDtlList = new ArrayList<BillBalanceInvoiceDtl>();
				
				if(StringUtils.isNotEmpty(invoiceApply.getBalanceType())){
				
				//获取  纳税人识别号  开户行  帐号  收票方地址    收票方电话  邮寄地址
				//如果是商场门店
				//String  nameInvoice = "";
				//用来存储根据商品小类汇总的数据集合
				
//				if (StringUtils.isNotEmpty(invoiceApply.getBalanceType())) {
					int balanceType = Integer.parseInt(invoiceApply.getBalanceType());
					String buyerNo = ""; // 客户
					String bsgroupsNo = "" ; //商业集团
					if (balanceType == BalanceTypeEnums.AREA_MALL.getTypeNo()) { //商场从开票规则取数
						if(StringUtils.isNotEmpty(invoiceApply.getBuyerNo())){
							
							//店铺分组编码
							//String shopGroupNo = "";
							
							//通过店铺编码获取开票模块信息
/*							InvoiceRuleShopSet invoiceRuleShopSet = new InvoiceRuleShopSet();
							invoiceRuleShopSet.setShopNo(invoiceApply.getBuyerNo());
							invoiceRuleShopSet = invoiceRuleShopSetService.findById(invoiceRuleShopSet);*/
							
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("shopNo", invoiceApply.getBuyerNo());
							List<ShopGroup> shopList = shopGroupService.getShopGroupNoByShopNo(map);
							
							if(!CollectionUtils.isEmpty(shopList)){
								if(StringUtils.isNotEmpty(shopList.get(0).getClientNo())){
									buyerNo = shopList.get(0).getClientNo();
//									Map<String, Object> paramsMap = new HashMap<String, Object>();
//									paramsMap.put("companyNo", invoiceApply.getSalerNo());
//									paramsMap.put("clientNo", shopList.get(0).getClientNo());
//									paramsMap.put("status", RULESTATUS);
//									
//									
//									InvoiceInfo invoiceInfo = new InvoiceInfo();
//									//根据公司编码、客户编码获取开票信息
//									List<InvoiceInfo> invoiceInfoList = invoiceInfoService.findByBiz(null, paramsMap);
//									
//									if(!CollectionUtils.isEmpty(invoiceInfoList)){
//										invoiceInfo = invoiceInfoList.get(0);
//										if(null != invoiceInfo){
//											invoiceApply.setTaxRegistryNo(invoiceInfo.getTaxpayerNo());
//											invoiceApply.setBankName(invoiceInfo.getBankName());
//											invoiceApply.setBankAccount(invoiceInfo.getAccountNo());
//											invoiceApply.setBankAccountName(invoiceInfo.getBankName());
//											invoiceApply.setAmount(invoiceApply.getAmount());
//											invoiceApply.setBuyerAddress(invoiceInfo.getPostAddress());
//											invoiceApply.setBuyerTel(invoiceInfo.getContactNumber());
//											invoiceApply.setMailingAddress(invoiceInfo.getAddress());
//											invoiceApply.setContactName(invoiceInfo.getContactPerson());
//											invoiceApply.setTel(invoiceInfo.getTelephoneNumber());
//											invoiceApply.setBuyerName(shopList.get(0).getClientName());
//											invoiceApply.setBuyerNo(shopList.get(0).getClientNo());
//											invoiceApply.setName(invoiceInfo.getInvoiceName());
//										}
//									}
									invoiceApply.setBuyerName(shopList.get(0).getClientName());
									invoiceApply.setBuyerNo(shopList.get(0).getClientNo());
								}
							}else{
								Shop shop = shopService.selectSubsiInfo(map);
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
						}
					}else{
						buyerNo = invoiceApply.getBuyerNo();
					}
					if(StringUtils.isNotBlank(buyerNo)){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("companyNo", invoiceApply.getSalerNo());
						map.put("clientNo", buyerNo);
						map.put("status", RULESTATUS);
						InvoiceInfo invoiceInfo = new InvoiceInfo();
						//根据公司编码、客户编码获取开票信息
						List<InvoiceInfo> invoiceInfoList = invoiceInfoService.findByBiz(null, map);
						
						if(CollectionUtils.isEmpty(invoiceInfoList) && StringUtils.isNotBlank(bsgroupsNo)){//如果根据商场编号查询开票信息，返回空，则根据商业集团编号查询
							map.put("clientNo", bsgroupsNo);
							invoiceInfoList = invoiceInfoService.findByBiz(null, map);
						} 
						if(!CollectionUtils.isEmpty(invoiceInfoList)){
							invoiceInfo = invoiceInfoList.get(0);
							if(null != invoiceInfo){
								invoiceApply.setTaxRegistryNo(invoiceInfo.getTaxpayerNo());
								invoiceApply.setBankName(invoiceInfo.getBankName());
								invoiceApply.setBankAccount(invoiceInfo.getAccountNo());
								invoiceApply.setBankAccountName(invoiceInfo.getBankName());
								invoiceApply.setAmount(invoiceApply.getAmount());
								invoiceApply.setBuyerAddress(invoiceInfo.getAddress());
								invoiceApply.setBuyerTel(invoiceInfo.getContactNumber());
								invoiceApply.setMailingAddress(invoiceInfo.getPostAddress());
								invoiceApply.setContactName(invoiceInfo.getContactPerson());
								invoiceApply.setTel(invoiceInfo.getTelephoneNumber());
//								invoiceApply.setBuyerName(shopList.get(0).getClientName());
//								invoiceApply.setBuyerNo(shopList.get(0).getClientNo());
								invoiceApply.setInvoiceName(invoiceInfo.getInvoiceName());
								invoiceApply.setInvoiceType(invoiceInfo.getInvoiceType());
							}
						}else{
							invoiceApply.setBillNo("");
							invoiceApply.setErrorInfo("保存失败，开票信息没做维护或不完善。");
							return invoiceApply;//不能开票，返回信息提示
						}
					}else{
						invoiceApply.setBillNo("");
						invoiceApply.setErrorInfo("保存失败，商场为空。");
						return invoiceApply;//不能开票，返回信息提示
					}
					
					BillShopBalance shopBalance = new BillShopBalance();
					shopBalance.setBalanceNo(balanceNo);
					shopBalance = billShopBalanceService.findById(shopBalance);
					
					if(null != shopBalance){
						ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
						shopBalanceDatePar.setShopNo(shopBalance.getShopNo());
						shopBalanceDatePar.setMonth(shopBalance.getMonth());
						shopBalanceDatePar.setBillalready((byte)2);
						shopBalanceDatePar.setBalanceStartDate(shopBalance.getBalanceStartDate());
						shopBalanceDatePar.setBalanceEndDate(shopBalance.getBalanceEndDate());
						shopBalanceDateService.updateBalanceDateSet(shopBalanceDatePar);
					}
					
					BillBalanceInvoiceSource invoiceSource = new BillBalanceInvoiceSource();
					if(Integer.parseInt(invoiceApply.getBalanceType()) != BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()){
						invoiceSource.setBalanceNo(balanceNo);
					}
					invoiceSource.setBillNo(billNo);
					if (StringUtils.isNotEmpty(invoiceApply.getBalanceType())) {
						invoiceSource.setBalanceType(Integer.parseInt(invoiceApply.getBalanceType()));
					}
					invoiceSource.setAmount(invoiceApply.getAmount());
					invoiceSource.setId(UUIDGenerator.getUUID());
					invoiceSource.setSalerNo(invoiceApply.getSalerNo());
					billBalanceInvoiceSourceService.add(invoiceSource);
				}

				if (StringUtils.isNotEmpty(invoiceApply.getBalanceType())
						&& Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_MALL.getTypeNo()) {

					if (StringUtils.isNotEmpty(balanceNo)) {
						//获取商场结算单信息
						BillShopBalance billShopBalance = new BillShopBalance();
						billShopBalance.setBalanceNo(balanceNo);
						billShopBalance = billShopBalanceService.findById(billShopBalance);

						//获取结算单大类汇总信息
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("balanceNo", balanceNo);
						List<BillShopBalanceCateSum> sumList = billShopBalanceCateSumService.findByBiz(null, params);
						
//						BigDecimal allSendAmount = new BigDecimal(0);
						
						Map<String, Object> maps = new HashMap<String, Object>();
						maps.put("balanceNo", balanceNo);
//						allSendAmount = billShopBalanceCateSumService.getSumAmount(maps);

						//新增大类汇总信息
						if (null != billShopBalance && null != sumList && sumList.size() > 0) {
							for (BillShopBalanceCateSum billShopBalanceCateSum : sumList) {
								
								String invoiceName = "";
//								String cateName = "";
//								String cateNo = "";
								String cateNos = "";
								String cateNames = "";
//								cateNo = billShopBalanceCateSum.getCategoryNo();
//								cateName = billShopBalanceCateSum.getCategoryName();
								
								Category category = new Category();
								if(StringUtils.isNotBlank(billShopBalanceCateSum.getCategoryNo())){
									category.setCategoryNo(billShopBalanceCateSum.getCategoryNo().substring(0, 2));
									category = categoryService.findById(category);
								}
								
								if(null != category){
									invoiceName = category.getName();
									cateNos = category.getCategoryNo();
									cateNames = category.getName();
								}
								
								Map<String, Object> shopMap = new HashMap<String, Object>();
								shopMap.put("shopNo", billShopBalanceCateSum.getShopNo());
								List<ShopGroup> shopGroupList = shopGroupService.getShopGroupNoByShopNo(shopMap);
								byte qtyContorlFlag = 0;
								if(!CollectionUtils.isEmpty(shopGroupList)){
									Map<String, Object> paramMap = new HashMap<String, Object>();
									paramMap.put("invoiceTempNo", shopGroupList.get(0).getTemplateNo());
									if(StringUtils.isNotBlank(billShopBalanceCateSum.getCategoryNo())){
										paramMap.put("categoryNo", billShopBalanceCateSum.getCategoryNo().substring(0, 2));
										
										List<InvoiceTemplateSetDtl> dtlList = invoiceTemplateSetDtlService.findByBiz(null, paramMap);
										
										if(!CollectionUtils.isEmpty(dtlList)){
											invoiceName = dtlList.get(0).getInvoiceName();
											qtyContorlFlag = dtlList.get(0).getQtyControlFlag() == null ? 0 : dtlList.get(0).getQtyControlFlag();
										}
									}
									
								}
								
/*								if(StringUtils.isNotEmpty(billShopBalanceCateSum.getCategoryNo())){
									//通过大类获取大类的发票名称
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("categoryNo", billShopBalanceCateSum.getCategoryNo());
									List<InvoiceTemplateSetDtl> invoiceTemplateSetDtl = invoiceTemplateSetDtlService.findByBiz(null, map);
									if(null != invoiceTemplateSetDtl && invoiceTemplateSetDtl.size() > 0){
										invoiceName = invoiceTemplateSetDtl.get(0).getInvoiceName();
										cateNo = invoiceTemplateSetDtl.get(0).getCategoryNo();
										cateName = invoiceTemplateSetDtl.get(0).getCategoryName();
									}
								}*/
								
								BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
								//billBalanceInvoiceDtl.setBalanceNo(balanceNo);
								billBalanceInvoiceDtl.setBillNo(billNo);
								billBalanceInvoiceDtl.setActualRate(billShopBalance.getActualRate());
								billBalanceInvoiceDtl.setContractRate(billShopBalance.getContractRate());
								billBalanceInvoiceDtl.setBalanceEndDate(billShopBalanceCateSum.getBalanceEndDate());
								billBalanceInvoiceDtl.setBalanceStartDate(billShopBalanceCateSum.getBalanceStartDate());
								billBalanceInvoiceDtl.setBrandName(billShopBalanceCateSum.getBrandName());
								billBalanceInvoiceDtl.setBrandNo(billShopBalanceCateSum.getBrandNo());
								billBalanceInvoiceDtl.setRetailType(billShopBalance.getRetailType());
								billBalanceInvoiceDtl.setOrganNo(billShopBalance.getOrganNo());
								billBalanceInvoiceDtl.setOrganName(billShopBalance.getOrganName());
								billBalanceInvoiceDtl.setCategoryName(cateNames);
								billBalanceInvoiceDtl.setCategoryNo(cateNos);
								//billBalanceInvoiceDtl.setCateNo(cateNos);
								//billBalanceInvoiceDtl.setCateName(cateNames);
								
								//计算大类价格
//								if(null != invoiceApply.getAmount() && null != billShopBalanceCateSum.getSaleAmount()){
//									BigDecimal priceDifference = new BigDecimal(0);
//									priceDifference = invoiceApply.getAmount().subtract(invoiceApply.getSystemSalesAmount());
/*									billBalanceInvoiceDtl.setSendAmount(billShopBalanceCateSum.getSaleAmount().
									add(billShopBalanceCateSum.getSaleAmount().divide(allSendAmount)).multiply(priceDifference));*/
//									billBalanceInvoiceDtl.setSendAmount(BigDecimalUtil.add(billShopBalanceCateSum.getSaleAmount(), billShopBalanceCateSum.getSaleAmount().divide(allSendAmount, 2).multiply(priceDifference)));
//								}
								billBalanceInvoiceDtl.setSendAmount(billShopBalanceCateSum.getBillingAmount());
								billBalanceInvoiceDtl.setSendAmount(billShopBalanceCateSum.getBillingAmount());
								billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
								billBalanceInvoiceDtl.setFullName(billShopBalanceCateSum.getFullName());
								if(null != billBalanceInvoiceDtl.getSendAmount()){
									billBalanceInvoiceDtl.setNoTaxAmount(billBalanceInvoiceDtl.getSendAmount().divide(
										new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
								}
								billBalanceInvoiceDtl.setPosEarningAmount(new BigDecimal(0));
								//如果发票模板是否启用数量控制为：是，则数量为0
								if(qtyContorlFlag == 1){
									billBalanceInvoiceDtl.setQty(0);
								}else{
									billBalanceInvoiceDtl.setQty(billShopBalanceCateSum.getSaleQty());
								}
								billBalanceInvoiceDtl.setSalerName(invoiceName);
								billBalanceInvoiceDtl.setShopNo(billShopBalanceCateSum.getShopNo());
								billBalanceInvoiceDtl.setShortName(billShopBalanceCateSum.getShortName());
								if(null != billBalanceInvoiceDtl.getNoTaxAmount()){
									billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
										new BigDecimal(0.17)));
								}
								billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
								billBalanceInvoiceDtl.setId(UUIDGenerator.getUUID());
								billBalanceInvoiceDtl.setSalerNo(invoiceApply.getSalerNo());
								billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl);
							}
						}
					}
/*					List<BillBalanceInvoiceDtl> dtl = getIvvoiceDtlGroupByParams(invoiceDtlList, invoiceApply.getInvoiceType());
					
					if(!CollectionUtils.isEmpty(dtl)){
						for (BillBalanceInvoiceDtl billBalanceInvoiceDtl2 : dtl) {
							billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl2);
						}
					}*/
				} else {
					if (StringUtils.isNotEmpty(balanceNo) && Integer.parseInt(invoiceApply.getBalanceType()) != BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()){
						if (StringUtils.isNotEmpty(balanceNo)) {
							//获取结算单大类汇总信息
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("balanceNo", balanceNo);
							List<BillSaleBalance> saleList = billBalanceInvoiceApplyService.getBillSaleBalanceSum(null,
									null, null, params);
							
							String invoiceName = "";
							String cateNos = "";
							String cateNames = "";
							
							for (BillSaleBalance billSaleBalance : saleList) {
								
//								String cateName = "";
//								String cateNo = "";
								// 数量是否控制
								int qtyControlFlag = 0;
								//根据商品分类获取财务大类名称
								if(StringUtils.isNotEmpty(billSaleBalance.getCategoryNo())){
									
//									cateNo = billSaleBalance.getCategoryNo();
//									cateName = billSaleBalance.getCategoryName();
									
									Category category = new Category();
									if(StringUtils.isNotBlank(billSaleBalance.getCategoryNo())){
										category.setCategoryNo(billSaleBalance.getCategoryNo().substring(0, 2));
										category = categoryService.findById(category);
									}
									if(null != category){
										invoiceName = category.getName();
										if(StringUtils.isNotBlank(billSaleBalance.getCategoryNo())){
											cateNos = billSaleBalance.getCategoryNo().substring(0, 2);
										}
										cateNames = category.getName();
									}

									
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("companyNo", invoiceApply.getSalerNo());
									
									String categoryNo = billSaleBalance.getCategoryNo();
									List<FinancialCategory> financialCategoryList = null;
//									cateNo = categoryNo;
//									cateName = billSaleBalance.getCategoryName();
									// 财务大类编号
									String financialCategoryNo = "";
									while (categoryNo.length() >= 2) {
										map.put("categoryNo", categoryNo);
										financialCategoryList = financialCategoryService.selectCateInfoByCateNo(map);
										if(CollectionUtils.isEmpty(financialCategoryList)){
											categoryNo = categoryNo.substring(0, categoryNo.length()-2);
											continue;
										}else {
											invoiceName = financialCategoryList.get(0).getName();
											break;
										}
									}
									if(StringUtils.isNotBlank(categoryNo) && StringUtils.isNotBlank(financialCategoryNo)){
										// 根据大类及财务大类编号，查询明细，主要作数量控制
										map.put("categoryNo", categoryNo);
										map.put("financialCategoryNo", financialCategoryNo);
										List<FinancialCategoryDtl> fcDtlList = financialCategoryDtlService.findByBiz(null, map);
										
										if(!CollectionUtils.isEmpty(fcDtlList)){
											qtyControlFlag = fcDtlList.get(0).getQtyControlFlag() == null ? 0 : fcDtlList.get(0).getQtyControlFlag();
										}
									}
								}
								
								//退殘单，需在减去退殘数据及金额 到货单数
								if("".equals(billSaleBalance.getBillType()) && "".equals(billSaleBalance.getBizType())){
									
								}
								BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
								//billBalanceInvoiceDtl.setBalanceNo(balanceNo);
								billBalanceInvoiceDtl.setBillNo(billNo);
								billBalanceInvoiceDtl.setBalanceEndDate(billSaleBalance.getBalanceEndDate());
								billBalanceInvoiceDtl.setBalanceStartDate(billSaleBalance.getBalanceStartDate());
								billBalanceInvoiceDtl.setBrandName(billSaleBalance.getBrandName());
								billBalanceInvoiceDtl.setBrandNo(billSaleBalance.getBrandNo());
								billBalanceInvoiceDtl.setOrganNo(billSaleBalance.getOrganNoFrom());
								billBalanceInvoiceDtl.setOrganName(billSaleBalance.getOrganNameFrom());
								billBalanceInvoiceDtl.setCategoryName(cateNames);
								billBalanceInvoiceDtl.setCategoryNo(cateNos);
								//billBalanceInvoiceDtl.setCateNo(cateNos);
								//billBalanceInvoiceDtl.setCateName(cateNames);
								billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
								if(null != billSaleBalance.getSendAmount()){
									billBalanceInvoiceDtl.setNoTaxAmount(billSaleBalance.getSendAmount().divide(
										new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
								}
								billBalanceInvoiceDtl.setPosEarningAmount(new BigDecimal(0));
								
								if(1 == qtyControlFlag){//数量是否控制：是，则数量为0
									billBalanceInvoiceDtl.setQty(0);
								}else{
									billBalanceInvoiceDtl.setQty(billSaleBalance.getSendQty());
								}
								billBalanceInvoiceDtl.setSalerName(invoiceName);
								billBalanceInvoiceDtl.setSendAmount(billSaleBalance.getSendAmount());
								if(null != billBalanceInvoiceDtl.getNoTaxAmount()){
									billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
										new BigDecimal(0.17)));
								}
								billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
								
								invoiceDtlList.add(billBalanceInvoiceDtl);

								//billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl);
							}
						}
					}

					List<BillBalanceInvoiceDtl> dtl = getIvvoiceDtlGroupByParams(invoiceDtlList);
					
					if(!CollectionUtils.isEmpty(dtl)){
						for (BillBalanceInvoiceDtl billBalanceInvoiceDtl2 : dtl) {
							billBalanceInvoiceDtl2.setId(UUIDGenerator.getUUID());
							billBalanceInvoiceDtl2.setSalerNo(invoiceApply.getSalerNo());
							billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl2);
						}
					}
				}
				
				//更新结算单上的开票申请信息反写  add POS系统添加开票申请单号回写
				if (StringUtils.isNotEmpty(invoiceApply.getBalanceType())) {
					if (Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_MALL.getTypeNo()) {
						BillShopBalance shopBalance = new BillShopBalance();
						shopBalance.setBalanceNo(balanceNo);
						shopBalance.setInvoiceApplyNo(billNo);
						shopBalance.setStatus(BalanceStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo());
						shopBalance.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
						billShopBalanceService.updateInvoiceByBalanceNo(shopBalance);
//					} else if(Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()){   //pos回写开票申请单号
//						if(StringUtils.isNotEmpty(invoiceApply.getBalanceNo())){
//							List<String> balanceNoList = new ArrayList<String>();
//							balanceNoList.add(invoiceApply.getBalanceNo());
//							orderMainApi.modifyOrderForInvoiceApply(balanceNoList, billNo, invoiceApply.getInvoiceApplyDate());
//							//回写本库销售
//							orderMainManager.modifyOrderForInvoiceApply(balanceNoList, billNo, invoiceApply.getInvoiceApplyDate());
//						}
					} else {
						invoiceApply.setBillNo(balanceNo);
						invoiceApply.setInvoiceApplyNo(billNo);
						
						invoiceApply.setStatus(BalanceStatusEnums.MAKE_INVOICE.getTypeNo());
						billBalanceInvoiceApplyService.updateInvoiceApplyNo(invoiceApply);
					}
				}

				Byte invoiceType = 1;
				Byte preInvoice = 1;
				if (StringUtils.isEmpty(invoiceApply.getId())) {
					invoiceApply.setId(null);
				}
				CurrencyManagement currency=currencyManagementService.findDefaultCurrency();
				invoiceApply.setCurrency(currency.getCurrencyCode());//设置默认币种
				invoiceApply.setInvoiceType(invoiceType);
				invoiceApply.setPreInvoice(preInvoice);
				invoiceApply.setBillNo(billNo);
				invoiceApply.setStatus(1);
				invoiceApply.setId(UUIDGenerator.getUUID());
				billBalanceInvoiceApplyService.add(invoiceApply);
			} catch (Exception e) {
//				codingRuleService.recycleSerialNo(BillBacksectionSplit.class.getSimpleName(), billNo);
				throw new ManagerException(e);
			}
//		}
		return invoiceApply;
	}

	@Override
	public List<BillBalanceInvoiceApply> getByPage(SimplePage page, Map<String, Object> params) {
		return billBalanceInvoiceApplyService.getByPage(page, params);
	}

	@Override
	public int getCount(Map<String, Object> params) {
		return billBalanceInvoiceApplyService.getCount(params);
	}
	
	/**
	 * 根据管理城市、品牌、一级大类、开票名称进行数据分组处理
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceDtl> getIvvoiceDtlGroupByParams(List<BillBalanceInvoiceDtl> params){
		List<BillBalanceInvoiceDtl> dtlList = new ArrayList<BillBalanceInvoiceDtl>();
		
		Map<String, List<BillBalanceInvoiceDtl>> map = new HashMap<String, List<BillBalanceInvoiceDtl>>();
		for (BillBalanceInvoiceDtl billBalanceInvoiceDtl : params) {
			
			//拼接分组key
			String key = billBalanceInvoiceDtl.getBrandNo()+"|"+ billBalanceInvoiceDtl.getCategoryNo() 
					+ "|" + billBalanceInvoiceDtl.getOrganNo() + "|" + billBalanceInvoiceDtl.getSalerName();
			
			//根据大类汇总
			if(!map.containsKey(key)){
				List<BillBalanceInvoiceDtl> dtoList = new ArrayList<BillBalanceInvoiceDtl>();
				dtoList.add(billBalanceInvoiceDtl);
				
				map.put(key, dtoList);
			}else{
				List<BillBalanceInvoiceDtl> dtoList = map.get(key);
				dtoList.add(billBalanceInvoiceDtl);
				
				map.put(key, dtoList);
			}
		}
		
		Iterator<String> iterator = map.keySet().iterator();
		
		while(iterator.hasNext()){
			String key = iterator.next();
			
			List<BillBalanceInvoiceDtl> DetailList = map.get(key);
			
			int qty = 0;
			BigDecimal sendAmount = new BigDecimal(0);
			
			for(BillBalanceInvoiceDtl detail : DetailList){
				
				if(detail.getSendAmount() != null){
					sendAmount = sendAmount.add(detail.getSendAmount());
				}
				if(detail.getQty() != null){
					qty = qty + detail.getQty();
				}

			}
			
			BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
			billBalanceInvoiceDtl.setQty(qty);
			billBalanceInvoiceDtl.setBillNo(DetailList.get(0).getBillNo());
			billBalanceInvoiceDtl.setBrandName(DetailList.get(0).getBrandName());
			billBalanceInvoiceDtl.setBrandNo(DetailList.get(0).getBrandNo());
			billBalanceInvoiceDtl.setShopNo(DetailList.get(0).getShopNo());
			billBalanceInvoiceDtl.setShortName(DetailList.get(0).getShortName());
			billBalanceInvoiceDtl.setRetailType(DetailList.get(0).getRetailType());
			billBalanceInvoiceDtl.setOrganNo(DetailList.get(0).getOrganNo());
			billBalanceInvoiceDtl.setOrganName(DetailList.get(0).getOrganName());
			billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
			billBalanceInvoiceDtl.setSendAmount(sendAmount);
			billBalanceInvoiceDtl.setCategoryNo(DetailList.get(0).getCategoryNo());
			billBalanceInvoiceDtl.setCategoryName(DetailList.get(0).getCategoryName());
			
//			Byte invoiceTypes = 1;
//			if(invoiceTypes.equals(invoiceType)){
				billBalanceInvoiceDtl.setNoTaxAmount(billBalanceInvoiceDtl.getSendAmount().divide(
						new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
				billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
						new BigDecimal(0.17)));
				billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
//			}
			billBalanceInvoiceDtl.setPosEarningAmount(new BigDecimal(0));
			billBalanceInvoiceDtl.setSalerName(DetailList.get(0).getSalerName());
			
			dtlList.add(billBalanceInvoiceDtl);
		  }
		
		return dtlList;
}

//	/**
//	 * 根据品牌、店铺、大类分组获取地区团购信息
//	 * @param balanceNo
//	 * @return
//	 * @throws ServiceException 
//	 */
//	public void getAreaGroupBuy(String balanceNoStr, String billNo, String companyNo) throws ServiceException{
//		
//		List<OrderExMainDtlDto> dtlList = new ArrayList<OrderExMainDtlDto>();
//		
//		List<BillBalanceInvoiceDtl> invoiceDtl = new ArrayList<BillBalanceInvoiceDtl>();
//		
//		Map<String, List<OrderExMainDtlDto>> map = new HashMap<String, List<OrderExMainDtlDto>>();
//		
//		//单据类型集合
//		List<Integer> businessTypeList = new ArrayList<Integer>();
//		
//		//状态集合
//		List<Integer> statusList = new ArrayList<Integer>();
//		
//		businessTypeList.add(BUSINESSTYPE);
//		statusList.add(STATUSSTR);
//		statusList.add(STATUSSTRS);
//		
//		POSOcOrderParameterParentDto ocOrderParameterParentNoDto = new POSOcOrderParameterParentDto();
//		ocOrderParameterParentNoDto.setBusinessTypeList(businessTypeList);
//		ocOrderParameterParentNoDto.setStatusList(statusList);
//		
//		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDto = null;
//		
//		POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
//		pageDto.setPageNo(1);
//		pageDto.setPageSize(10000);
//		try {
//			orderAndReturnExMainDtlDto = orderMainManager.findList4OrderMainForCompany(pageDto, "", ocOrderParameterParentNoDto, INVOICETYPES, "", "",null);
//		} catch (ManagerException e) {
//			e.printStackTrace();
//		}
//		
//		if(null != orderAndReturnExMainDtlDto){
//			if(!CollectionUtils.isEmpty(orderAndReturnExMainDtlDto.getResult())){
//				String[] balanceNo = balanceNoStr.split(",");
//				
//				for (String no : balanceNo) {
//					for (int i = 0; i < orderAndReturnExMainDtlDto.getResult().size(); i++) {
//						if(no.equals(orderAndReturnExMainDtlDto.getResult().get(i).getOrderNo())){
//							OrderExMainDtlDto dto = new OrderExMainDtlDto();
//							//对象bean赋值
//							BeanUtils.copyProperties(orderAndReturnExMainDtlDto.getResult().get(i), dto);
//							dtlList.add(dto);
//						}
//					}
//				}
//			}
//			
//			for (OrderExMainDtlDto orderExMainDtlDto : dtlList) {
//				String key = orderExMainDtlDto.getBrandNo()+"|"+ orderExMainDtlDto.getCategoryNo() + "|" + orderExMainDtlDto.getShopNo();
//				
//				//根据大类汇总
//				if(!map.containsKey(key)){
//					List<OrderExMainDtlDto> dtoList = new ArrayList<OrderExMainDtlDto>();
//					dtoList.add(orderExMainDtlDto);
//					
//					map.put(key, dtoList);
//				}else{
//					List<OrderExMainDtlDto> dtoList = map.get(key);
//					dtoList.add(orderExMainDtlDto);
//					
//					map.put(key, dtoList);
//				}
//			}
//			
//			Iterator<String> iterator = map.keySet().iterator();
//			
//			while(iterator.hasNext()){
//				String key = iterator.next();
//				
//				List<OrderExMainDtlDto> orderExMainDtlDtoDetail = map.get(key);
//				
//				int qty = 0;
//				BigDecimal sendAmount = new BigDecimal(0);
//				
//				String brandNo = null ; String brandName = "" ;
//				String shopName = "" ; String shopNo = "" ;
//				
////				String cateName = "";
////				String cateNo = "";
//				String retailType = "";
//				String organNo = "";
//				String organName = "";
//				String invoiceName = "";
//				String cateNos = "";
//				String cateNames = "";
//				for(OrderExMainDtlDto orderExMainDtlDto : orderExMainDtlDtoDetail){
//					// 数量是否控制
//					int qtyControlFlag = 0;
//					//根据商品分类获取财务大类名称
//					if(StringUtils.isNotEmpty(orderExMainDtlDto.getCategoryNo())){
//						Map<String, Object> map2 = new HashMap<String, Object>();
//						map2.put("companyNo", companyNo);
//						
//						Category category = new Category();
//						category.setCategoryNo(orderExMainDtlDto.getCategoryNo().substring(0,2));
//						try {
//							category = categoryService.findById(category);
//						} catch (ServiceException e) {
//							e.printStackTrace();
//						}
//						if(null != category){
//							cateNos = category.getCategoryNo();
//							cateNames = category.getName();
//							invoiceName = category.getName();
//						}
//						
//						String categoryNo = orderExMainDtlDto.getCategoryNo();
//						List<FinancialCategory> financialCategoryList = null;
//						
//						
//						//获取商品大类编码对应的大类
////						cateNo = categoryNo;
//						
//						Category categoryDtl = new Category();
//						categoryDtl.setCategoryNo(categoryNo);
//						try {
//							categoryDtl = categoryService.findById(categoryDtl);
//						} catch (ServiceException e1) {
//							e1.printStackTrace();
//						}
////						if(null != categoryDtl){
////							cateName = categoryDtl.getName();
////						}
//						//cateName = orderExMainDtlDto.getCategoryName();
//						String financialCategoryNo = "";
//						while (categoryNo.length() >= 2) {
//							map2.put("categoryNo", categoryNo);
//							financialCategoryList = financialCategoryService.selectCateInfoByCateNo(map2);
//							if(CollectionUtils.isEmpty(financialCategoryList)){
//								categoryNo = categoryNo.substring(0, categoryNo.length()-2);
//								continue;
//							}else {
//								invoiceName = financialCategoryList.get(0).getName();
//								financialCategoryNo = financialCategoryList.get(0).getFinancialCategoryNo();
//								break;
//							}
//						}
//						
//						if(StringUtils.isNotBlank(categoryNo) && StringUtils.isNotBlank(financialCategoryNo)){
//							// 根据大类及财务大类编号，查询明细，主要作数量控制
//							map2.put("categoryNo", categoryNo);
//							map2.put("financialCategoryNo", financialCategoryNo);
//							List<FinancialCategoryDtl> fcDtlList = financialCategoryDtlService.findByBiz(null, map2);
//							
//							if(!CollectionUtils.isEmpty(fcDtlList)){
//								qtyControlFlag = fcDtlList.get(0).getQtyControlFlag() == null ? 0 : fcDtlList.get(0).getQtyControlFlag();
//							}
//						}
//					//根据店铺编码获取店铺小类和管理城市
//					if(StringUtils.isNotEmpty(orderExMainDtlDto.getShopNo())){
//						Shop shop = new Shop();
//						shop.setShopNo(orderExMainDtlDto.getShopNo());
//						try {
//							shop = shopService.findById(shop);
//						} catch (ServiceException e) {
//							e.printStackTrace();
//						}
//						
//						if(null != shop){
//							retailType = shop.getRetailType();
//						}
//						
//						Map<String, Object> map1 = new HashMap<String, Object>();
//						map1.put("shopNo", orderExMainDtlDto.getShopNo());
//						
//						Organ organ = organService.getOrganInfoByShopNo(map1);
//						
//						if(null != organ){
//							organNo = organ.getOrganNo();
//							organName = organ.getName();
//						}
//					}
//					
//					brandName = orderExMainDtlDto.getBrandName();
//					brandNo = orderExMainDtlDto.getBrandNo();
//					shopName = orderExMainDtlDto.getShopName();
//					shopNo = orderExMainDtlDto.getShopNo();
//					
//					if(orderExMainDtlDto.getAmount() != null){
//						sendAmount = sendAmount.add(orderExMainDtlDto.getAmount());
//					}
//					if(0 == qtyControlFlag){//数量是否控制：否，则计算数量
//						if(orderExMainDtlDto.getQty() != null){
//							qty = qty + orderExMainDtlDto.getDtlQty();
//						}
//					}
//				}
//			}
//				
//			BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
//			billBalanceInvoiceDtl.setQty(qty);
//			billBalanceInvoiceDtl.setBillNo(billNo);
//			billBalanceInvoiceDtl.setBrandName(brandName);
//			billBalanceInvoiceDtl.setBrandNo(brandNo);
//			billBalanceInvoiceDtl.setShopNo(shopNo);
//			billBalanceInvoiceDtl.setShortName(shopName);
//			billBalanceInvoiceDtl.setRetailType(retailType);
//			billBalanceInvoiceDtl.setOrganNo(organNo);
//			billBalanceInvoiceDtl.setOrganName(organName);
//			billBalanceInvoiceDtl.setCategoryName(cateNames);
//			billBalanceInvoiceDtl.setCategoryNo(cateNos);
//			billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
//			billBalanceInvoiceDtl.setSendAmount(sendAmount);
//			//billBalanceInvoiceDtl.setCateNo(cateNos);
//			//billBalanceInvoiceDtl.setCateName(cateNames);
//			
////			Byte invoiceTypes = 1;
////			if(invoiceType.equals(invoiceTypes)){
//				billBalanceInvoiceDtl.setNoTaxAmount(billBalanceInvoiceDtl.getSendAmount().divide(
//						new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
//				billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
//						new BigDecimal(0.17)));
//				billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
////			}
//			billBalanceInvoiceDtl.setPosEarningAmount(new BigDecimal(0));
//			billBalanceInvoiceDtl.setSalerName(invoiceName);
//			
//			invoiceDtl.add(billBalanceInvoiceDtl);	
//		   }
//	   }
//		
//		
//		List<BillBalanceInvoiceDtl> list = getIvvoiceDtlGroupByParams(invoiceDtl);
//		if(!CollectionUtils.isEmpty(list)){
//			for (BillBalanceInvoiceDtl billDtl : list) {
//				billDtl.setId(UUIDGenerator.getUUID());
//				billDtl.setSalerNo(companyNo);
//				billBalanceInvoiceDtlService.add(billDtl);
//			}
//		}
//   }
	
//	@Override
//	public String getOrderBillNo(String billNo, String balanceType, String billType) {
//		
//		String orderDtlBillNo = "";
//		
//		//单据类型集合
//		List<Integer> businessTypeList = new ArrayList<Integer>();
//		
//		//状态集合
//		List<Integer> statusList = new ArrayList<Integer>();
//		
//		businessTypeList.add(BUSINESSTYPE);
//		statusList.add(STATUSSTR);
//		statusList.add(STATUSSTRS);
//		
////		if(StringUtils.isNotEmpty(balanceType)){
////			if(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo() == Integer.parseInt(balanceType)){   //团购
////				businessTypeList.add(BUSINESSTYPE);
////				statusList.add(STATUSSTR);
////				statusList.add(STATUSSTRS);
////			}else if(BalanceTypeEnums.AREA_MEMBERS_PURCHASE.getTypeNo() == Integer.parseInt(balanceType)){   //员购
////				businessTypeList.add(BUSINESSTYPES);
////				statusList.add(STATUSSTR);
////				statusList.add(STATUSSTRS);
////			}
////		}
//		POSOcOrderParameterParentDto ocOrderParameterParentNoDto = new POSOcOrderParameterParentDto();
//		ocOrderParameterParentNoDto.setBusinessTypeList(businessTypeList);
//		ocOrderParameterParentNoDto.setStatusList(statusList);
//		
//		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDto = null;
//		if(StringUtils.isNotEmpty(balanceType) && BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo() == Integer.parseInt(balanceType)){
//			try {
//				POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
//				pageDto.setPageNo(1);
//				pageDto.setPageSize(10000);
//				//调用pos的接口,要按照店铺进行汇总，数据不能分页查询，拿到所有的订单
//				if(StringUtils.isNotEmpty(billType)){
//					orderAndReturnExMainDtlDto = orderMainManager.findList4OrderMainForCompany(pageDto, "", ocOrderParameterParentNoDto, INVOICETYPES, billNo, "",null);
//				}else{
//					orderAndReturnExMainDtlDto = orderMainManager.findList4OrderMainForCompany(pageDto, "", ocOrderParameterParentNoDto, INVOICETYPES, "", billNo,null);
//				}
//			} catch (ManagerException e) {
//				e.printStackTrace();
//			}
//		}else{
//			try {
//				POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
//				pageDto.setPageNo(1);
//				pageDto.setPageSize(10000);
//				//调用pos的接口,要按照店铺进行汇总，数据不能分页查询，拿到所有的订单
//				orderAndReturnExMainDtlDto = orderMainManager.findList4OrderMainForCompany(pageDto, "", ocOrderParameterParentNoDto, INVOICETYPES, billNo, "",null);
//			} catch (ManagerException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		if(null != orderAndReturnExMainDtlDto){
//			List<POSOrderAndReturnExMainDtlDto> dtoList= orderAndReturnExMainDtlDto.getResult();
//			
//			if(!CollectionUtils.isEmpty(dtoList)){
//				Map<String, POSOrderAndReturnExMainDtlDto> listMap = new HashMap<String, POSOrderAndReturnExMainDtlDto>();
//				for (POSOrderAndReturnExMainDtlDto dto : dtoList) {
//					listMap.put(dto.getOrderNo(), dto);
//				}
//				dtoList.clear();
//				dtoList.addAll(listMap.values());
//				
//				for (int i = 0; i < dtoList.size(); i++) {
//					orderDtlBillNo += dtoList.get(i).getOrderNo()+",";
//				}
//				if(orderDtlBillNo.length() > 0){
//					orderDtlBillNo = orderDtlBillNo.substring(0, orderDtlBillNo.length()-1);
//				}
//			}
//		}
//		
//		return orderDtlBillNo;
//	}
//
//	@Override
//	public List<POSOrderAndReturnExMainDtlDto> getOrderInfo(String balanceType,String invoiceNo,String applyNo,POSOcOrderParameterParentDto ocOrderParameterParentNoDto) {
//		
//		List<POSOrderAndReturnExMainDtlDto> list = new ArrayList<POSOrderAndReturnExMainDtlDto>();
//		
//		//单据类型集合
//		List<Integer> businessTypeList = new ArrayList<Integer>();
//		
//		//状态集合
//		List<Integer> statusList = new ArrayList<Integer>();
//		
//		businessTypeList.add(BUSINESSTYPE);
//		statusList.add(STATUSSTR);   
//		statusList.add(STATUSSTRS);
////		if(StringUtils.isNotEmpty(balanceType)){
////			if(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo() == Integer.parseInt(balanceType)){   //团购
////				businessTypeList.add(BUSINESSTYPE);
////				statusList.add(STATUSSTR);   
////				statusList.add(STATUSSTRS);
////			}else if(BalanceTypeEnums.AREA_MEMBERS_PURCHASE.getTypeNo() == Integer.parseInt(balanceType)){   //员购
////				businessTypeList.add(BUSINESSTYPES);
////				statusList.add(STATUSSTR);
////				statusList.add(STATUSSTRS);
////			}
////		}
////		OcOrderParameterParentDto ocOrderParameterParentNoDto = new OcOrderParameterParentDto();
//		if(null != ocOrderParameterParentNoDto){
//			ocOrderParameterParentNoDto.setBusinessTypeList(businessTypeList);
//			ocOrderParameterParentNoDto.setStatusList(statusList);
//		}else{
//			ocOrderParameterParentNoDto = new POSOcOrderParameterParentDto();
//			ocOrderParameterParentNoDto.setBusinessTypeList(businessTypeList);
//			ocOrderParameterParentNoDto.setStatusList(statusList);
//		}
//		
//		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDto = null;
//			try {
//				POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
//				pageDto.setPageNo(1);
//				pageDto.setPageSize(10000);
//				//调用pos的接口,要按照店铺进行汇总，数据不能分页查询，拿到所有的订单
//				orderAndReturnExMainDtlDto = orderMainManager.findList4OrderMainForCompany(pageDto, "", ocOrderParameterParentNoDto, INVOICETYPES, invoiceNo, applyNo,null);
//			} catch (ManagerException e) {
//				e.printStackTrace();
//			}
//		
//		if(null != orderAndReturnExMainDtlDto){
//			list = orderAndReturnExMainDtlDto.getResult();
//		}
//		
//		return list;
//	}

	@Override
	public int getCountByBalanceNo(Map<String, Object> params){
		return billBalanceInvoiceApplyService.getCountByBalanceNo(params);
	}

	@Override
	public List<BillBalanceInvoiceApply> getByBalanceNo(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billBalanceInvoiceApplyService.getByBalanceNo(page, orderByField, orderBy, params);
	}

	@Override
	public int deleteOn(String id, String billNo, String balanceType) throws ManagerException {
		int iCount = 0;
		try {
			List<BillBalanceInvoiceSource> invoiceSource = null;
			Map<String, Object> buyerparams = new HashMap<String, Object>();
			buyerparams.put("billNo", billNo);
			invoiceSource = billBalanceInvoiceSourceService.findByBiz(null, buyerparams);
			
			//清空源单的开票申请编号以及变更状态
			if(!CollectionUtils.isEmpty(invoiceSource)){
				if(balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_MALL.getTypeNo()))){
					for (BillBalanceInvoiceSource billBalanceInvoiceSource : invoiceSource) {
						BillShopBalance shopBalance = new BillShopBalance();
						shopBalance.setBalanceNo(billBalanceInvoiceSource.getBalanceNo());
						shopBalance.setInvoiceApplyDate(null);
						shopBalance.setInvoiceApplyNo("");
						shopBalance.setStatus(BalanceStatusEnums.SEND_FINANCE_CONFIRM.getTypeNo());
						billShopBalanceService.updateInvoiceByBalanceNo(shopBalance);
						
						BillShopBalance shopBalances = new BillShopBalance();
						shopBalances.setBalanceNo(billBalanceInvoiceSource.getBalanceNo());
						shopBalances = billShopBalanceService.findById(shopBalance);
						
						if(null != shopBalances){
							ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
							shopBalanceDatePar.setShopNo(shopBalances.getShopNo());
							shopBalanceDatePar.setMonth(shopBalances.getMonth());
							shopBalanceDatePar.setBalanceStartDate(shopBalances.getBalanceStartDate());
							shopBalanceDatePar.setBalanceEndDate(shopBalances.getBalanceEndDate());
							shopBalanceDateService.updateBalanceBillAlready(shopBalanceDatePar);
						}
					}

				} else if(balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()))){
//					String billNoStr = getOrderBillNo(billNo, String.valueOf(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()), "");
//					if(StringUtils.isNotEmpty(billNoStr)){
//						orderMainApi.modifyOrderForInvoiceApply(Arrays.asList(billNoStr.split(",")), null, null);
//						//回写本库销售数据
//						orderMainManager.modifyOrderForInvoiceApply(Arrays.asList(billNoStr.split(",")), null, null);
//					}
//					BillBalanceInvoiceApply invoiceApply = new BillBalanceInvoiceApply();
//					invoiceApply.setId(id);
//					invoiceApply = billBalanceInvoiceApplyService.findById(invoiceApply);
					//回写内购结算期的是否已开票标识
	    			
	    			Map<String,Object> params = new HashMap<String,Object>();
	    			params.put("invoiceApplyNo", billNo);
	    			params.put("queryType", 3);
	    			params.put("searchCompanyNo", invoiceSource.get(0).getSalerNo());
	    			int total = orderMainManager.findApplyGeneratorDetailCount(params);
	    			SimplePage page = new SimplePage(1, total, total);
	    			// 根据开票申请号查询内购销售单据信息
	    			List<BalanceInvoiceApplyGenerator> list = orderMainManager.findApplyGeneratorDetail(page, params);
	    			for (BalanceInvoiceApplyGenerator balanceInvoiceApplyGenerator : list) {
	    				params.put("companyNo", balanceInvoiceApplyGenerator.getSalerNo());
	    				params.put("balanceDate", balanceInvoiceApplyGenerator.getBalanceDate());
	    				params.put("invoiceFlag", 1);
	    				// 根据结算公司及单据日期，查询内购结算期标识为已开票的记录是否存在，存在则修改为未开票
	    				List<InsidePurchaseBalanceDate> insidePurchaseList = insidePurchaseBalanceDateService.findByBiz(null, params);
	    				// 回写内购结算期 
	    				if(!CollectionUtils.isEmpty(insidePurchaseList)){
	    					InsidePurchaseBalanceDate insidePurchase = new InsidePurchaseBalanceDate();
	    					insidePurchase.setCompanyNo(balanceInvoiceApplyGenerator.getSalerNo());
	    					insidePurchase.setInvoiceFlag(0);
	    					insidePurchase.setBalanceStartDate(balanceInvoiceApplyGenerator.getBalanceDate());
	    					insidePurchaseBalanceDateService.updateInvoiceFlagByCondition(insidePurchase);
	    				}
					}
					
					
					invoiceApplyConfirmDtlService.updateByInvoiceApplyNo(billNo);
				} else {
					for (BillBalanceInvoiceSource billBalanceInvoiceSource : invoiceSource) {
						BillBalanceInvoiceApply invoiceApply = new BillBalanceInvoiceApply();
						invoiceApply.setBillNo(billBalanceInvoiceSource.getBalanceNo());
						invoiceApply.setInvoiceApplyNo("");
						invoiceApply.setStatus(BalanceStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo());
						billBalanceInvoiceApplyService.updateInvoiceApplyNo(invoiceApply);
					}
				}
			}
			//删除开票信息表数据
			BillBalanceInvoiceApply invoiceApplys = new BillBalanceInvoiceApply();
			invoiceApplys.setId(id);
			iCount = billBalanceInvoiceApplyService.deleteById(invoiceApplys);
			
			//删除源单信息表数据
			billBalanceInvoiceSourceService.deleteInvoiceSource(billNo);
//			if(!balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()))){
//				billBalanceInvoiceInfoService.deleteInvoiceInfo(billNo);
//			}
//			if(balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_SALEORDER.getTypeNo()))){
//				GroupSaleApplyInvoiceRel rel = new GroupSaleApplyInvoiceRel();
//				rel.setInvoiceApplyNo(billNo);
//				groupSaleApplyInvoiceRelService.deleteById(rel);
//			}
			billBalanceInvoiceDtlService.deleteInvoiceDtl(billNo);
			return iCount;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	public static void main(String[] args) {
		System.out.println("01".substring(0, "01".length()-2));
	}

	@Override
	public List<BillCommonInvoiceRegister> findInvoiceApplyForPayment(SimplePage page, Map<String, Object> params) throws ManagerException {
		try {
			return billBalanceInvoiceApplyService.findInvoiceApplyForPayment(page, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findInvoiceApplyCountForPayment(Map<String, Object> params) throws ManagerException {
		try {
			return billBalanceInvoiceApplyService.findInvoiceApplyCountForPayment(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 批量导入发票信息，根据开票申请号，获取发票表头信息及明细信息，导入的内容只包括发票编码、发票号、快递信息等，其他信息都由开票申请里取
	 * @param dtlList 开票申请号及发票信息，快递信息
	 * @param createUser 登陆用户名称
	 * @throws ManagerException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void doImportInvoiceRegsiter(List<BillCommonRegisterDtl> dtlList,String createUser) throws ManagerException{
		String billNo = "";
		try{
		if(!CollectionUtils.isEmpty(dtlList)){
			for (BillCommonRegisterDtl billCommonRegisterDtl : dtlList) {
				
				BillCommonInvoiceRegister billCommonInvoiceRegister = new BillCommonInvoiceRegister();
//				billCommonInvoiceRegister.setBillNo(billNo);
				billCommonInvoiceRegister.setSourceNo(billCommonRegisterDtl.getSourceNo());
//				billCommonInvoiceRegister.setSalerNo(billCommonRegisterDtl.getSalerNo());
//				billCommonInvoiceRegister.setBuyerNo(billCommonRegisterDtl.getBuyerNo());
				billCommonInvoiceRegister.setCreateUser(createUser);
				billNo = billCommonInvoiceRegisterManager.addInvoiceRegsiter(billCommonInvoiceRegister);
				//根据开票申请号查询按大类分明细
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("billNo", billCommonRegisterDtl.getSourceNo());
				List<BillBalanceInvoiceDtl> invoiceDtlList = billBalanceInvoiceDtlService.findByBiz(null, params);
				for (BillBalanceInvoiceDtl billBalanceInvoiceDtl : invoiceDtlList) {
					billCommonRegisterDtl.setBillNo(billNo);
					billCommonRegisterDtl.setBrandName(billBalanceInvoiceDtl.getBrandName());
					billCommonRegisterDtl.setBrandNo(billBalanceInvoiceDtl.getBrandNo());
					billCommonRegisterDtl.setCategoryName(billBalanceInvoiceDtl.getCategoryName());
					billCommonRegisterDtl.setCategoryNo(billBalanceInvoiceDtl.getCategoryNo());
					billCommonRegisterDtl.setNoTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount());
					billCommonRegisterDtl.setTaxAmount(billBalanceInvoiceDtl.getTaxAmount());
					billCommonRegisterDtl.setTaxRate(billBalanceInvoiceDtl.getTaxRate());
					billCommonRegisterDtl.setQty(billBalanceInvoiceDtl.getQty());
					billCommonRegisterDtl.setInvoiceAmount(billBalanceInvoiceDtl.getSendAmount());
					if(null != billBalanceInvoiceDtl.getQty() && null != billBalanceInvoiceDtl.getSendAmount() && billBalanceInvoiceDtl.getQty() != 0){
						billCommonRegisterDtl.setPrice(billBalanceInvoiceDtl.getSendAmount().divide(BigDecimal.valueOf(billBalanceInvoiceDtl.getQty()),10,RoundingMode.HALF_DOWN));
					}
					billCommonRegisterDtl.setEstimatedAmount(billBalanceInvoiceDtl.getEstimatedAmount());
					billCommonRegisterDtl.setId(UUIDGenerator.getUUID());
					billCommonRegisterDtl.setSalerNo(billCommonInvoiceRegister.getSalerNo());
					billCommonRegisterDtlManager.add(billCommonRegisterDtl);
				}
			}
		}
		}catch(ServiceException e){
			throw new ManagerException(e.getMessage(),e);
		}
	}


	
	/**
	 * 手动新增开票申请信息的保存处理
	 * @param invoiceApply
	 * @param lstItem 选中的内购结算单据集合
	 * @return
	 * @throws ManagerException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalanceInvoiceApply addInvoiceApply(BillBalanceInvoiceApply invoiceApply,List<Object> lstItem) throws ManagerException {
		try{
			String balanceNo = invoiceApply.getBalanceNo();
			if(StringUtils.isNotBlank(balanceNo)){
				if(Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()){
					for (Object obj : lstItem) {
						BalanceInvoiceApplyGenerator apply = (BalanceInvoiceApplyGenerator)obj;
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("orderNo", apply.getBillNo());
						params.put("orderDtlId", apply.getDtlId());
						List<InvoiceApplyConfirmDtl> confirmDtlList = invoiceApplyConfirmDtlService.findByBiz(null, params);
						for (InvoiceApplyConfirmDtl invoiceApplyConfirmDtl : confirmDtlList) {
							if(StringUtils.isNotBlank(invoiceApplyConfirmDtl.getInvoiceApplyNo())){
								invoiceApply.setErrorInfo("保存失败，单据编号："+apply.getBillNo()+" 已做过开票申请。");
								return invoiceApply;
							}
						}
					}
				}else{
//				if(balanceNo.contains(",")){
					String[] balanceNos = balanceNo.split(",");
					for (String string : balanceNos) {
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("balanceNo", string);
						param.put("balanceType", invoiceApply.getBalanceType());
						List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceService.findByBiz(null, param);
						if(!CollectionUtils.isEmpty(sourceList)){// 如果结算单已经存在，则表示已经开过票，直接返回
							invoiceApply.setErrorInfo("保存失败，结算单号："+string+" 已做过开票申请。");
							return invoiceApply;
						}
					}
//				}else{
//					Map<String,Object> param = new HashMap<String,Object>();
//					param.put("balanceNo", balanceNo);
//					param.put("balanceType", invoiceApply.getBalanceType());
//					List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceService.findByBiz(null, param);
//					if(!CollectionUtils.isEmpty(sourceList)){// 如果结算单已经存在，则表示已```````````````经开过票，直接返回
//						invoiceApply.setErrorInfo("保存失败，结算单号："+balanceNo+" 已做过开票申请。");
//						return invoiceApply;
//					}
//				}
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyNo", invoiceApply.getSalerNo());
			map.put("clientNo", invoiceApply.getBuyerNo());
			if(null != invoiceApply.getInvoiceInfoStatus()){
				map.put("status", invoiceApply.getInvoiceInfoStatus()+"");
			}else{
				map.put("status", RULESTATUS);
			}
//			map.put("month", new Date());
			//根据公司编码、客户编码获取开票信息
			List<InvoiceInfo> invoiceInfoList = invoiceInfoService.findByBiz(null, map);
			
			if(CollectionUtils.isEmpty(invoiceInfoList)){//如果根据商场编号查询开票信息，则返回
				invoiceApply.setErrorInfo("保存失败，开票信息没做维护或不完善");
				return invoiceApply;
			} 
			String requestId = BillBalanceInvoiceApply.class.getSimpleName();
			//调用单据编号拼接处理方法，返回最终的单据编号
			String billNo = commonUtilService.getNewBillNoCompanyNo(invoiceApply.getSalerNo(),null,requestId);
			invoiceApply.setBillNo(billNo);
			if (null == invoiceApply.getInvoiceApplyDate()) {
				invoiceApply.setInvoiceApplyDate(new Date());
			}
			if (null == invoiceApply.getPostPayDate()) {
				invoiceApply.setPostPayDate(new Date());
			}
			invoiceApply.setCreateTime(new Date());
			
			// 产生源单关系信息
			if(StringUtils.isNotEmpty(invoiceApply.getBalanceType())){
				
				BillBalanceInvoiceSource invoiceSource = new BillBalanceInvoiceSource();
//				if(balanceNo.contains(",")){
					String[] billNos = balanceNo.split(",");
					for (String string : billNos) {
						invoiceSource.setBalanceNo(string);
						invoiceSource.setBillNo(billNo);
						if(null != invoiceApply.getBalanceAmount()){
							invoiceSource.setAmount(invoiceApply.getBalanceAmount());
						}else{
							invoiceSource.setAmount(invoiceApply.getAmount());
						}
						invoiceSource.setBalanceType(Integer.parseInt(invoiceApply.getBalanceType()));
						invoiceSource.setId(UUIDGenerator.getUUID());
						invoiceSource.setSalerNo(invoiceApply.getSalerNo());
						billBalanceInvoiceSourceService.add(invoiceSource);
					}
//				}else{
//					invoiceSource.setBalanceNo(balanceNo);
//					invoiceSource.setBillNo(billNo);
//					if(null != invoiceApply.getBalanceAmount()){
//						invoiceSource.setAmount(invoiceApply.getBalanceAmount());
//					}else{
//						invoiceSource.setAmount(invoiceApply.getAmount());
//					}
//					invoiceSource.setBalanceType(Integer.parseInt(invoiceApply.getBalanceType()));
//					invoiceSource.setId(UUIDGenerator.getUUID());
//					invoiceSource.setSalerNo(invoiceApply.getSalerNo());
//					billBalanceInvoiceSourceService.add(invoiceSource);
//				}
			}
			//更新结算单上的开票申请信息反写  add pos系统反写开票申请单号
			if (StringUtils.isNotEmpty(invoiceApply.getBalanceType())) {
				if (Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_MALL.getTypeNo()) {
					//地区门店结算，按大类分产生明细
					addInvoiceApplyDtlByMallBalance(invoiceApply);
					
					BillShopBalance shopBalance = new BillShopBalance();
					shopBalance.setBalanceNo(balanceNo);
					shopBalance.setInvoiceApplyNo(billNo);
					shopBalance.setStatus(BalanceStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo());
					shopBalance.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
					int ret = billShopBalanceService.updateInvoiceByBalanceNo(shopBalance);
					log.info("addInvoiceApply回写结算单的信息,开票日期:"+invoiceApply.getInvoiceApplyDate()
							+";开票申请号:"+invoiceApply.getBillNo()+";结果:"+ret);
					
					// 回写结算期管理 
					BillShopBalance billShopBalance = billShopBalanceService.findById(shopBalance);
					if(null != billShopBalance){
						ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
						shopBalanceDatePar.setShopNo(billShopBalance.getShopNo());
						shopBalanceDatePar.setMonth(billShopBalance.getMonth());
						shopBalanceDatePar.setBillalready((byte)2);
						shopBalanceDatePar.setBalanceStartDate(billShopBalance.getBalanceStartDate());
						shopBalanceDatePar.setBalanceEndDate(billShopBalance.getBalanceEndDate());
						shopBalanceDateService.updateBalanceDateSet(shopBalanceDatePar);
					}
				} else if(Integer.parseInt(invoiceApply.getBalanceType()) == BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()){   //pos回写开票申请单号
					Map<String,List<BalanceInvoiceApplyGenerator>> categoryMaps = new HashMap<String,List<BalanceInvoiceApplyGenerator>>();
					if(StringUtils.isNotEmpty(invoiceApply.getBalanceNo())){
						
						for (Object obj : lstItem) {
							BalanceInvoiceApplyGenerator apply = (BalanceInvoiceApplyGenerator)obj;
							String categoryNo = apply.getCategoryNo();
							String brandNo = apply.getBrandNo();
							String organNo = apply.getOrganNo();
							String key = categoryNo +"|"+ brandNo + "|" + organNo;
							
							//根据大类汇总
							if(!categoryMaps.containsKey(key)){
								
								List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerateList = new ArrayList<BalanceInvoiceApplyGenerator>();
								balanceInvoiceApplyGenerateList.add(apply);
								
								categoryMaps.put(key, balanceInvoiceApplyGenerateList);
							}else{
								List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerateList = categoryMaps.get(key);
								balanceInvoiceApplyGenerateList.add(apply);
								
								categoryMaps.put(key, balanceInvoiceApplyGenerateList);
							}
							
							// 更新单据明细及开票申请关联表的开票信息
							InvoiceApplyConfirmDtl invoiceApplyConfirmDtl = new InvoiceApplyConfirmDtl();
			    			invoiceApplyConfirmDtl.setInvoiceApplyNo(billNo);
			    			invoiceApplyConfirmDtl.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
			    			invoiceApplyConfirmDtl.setInvoiceAmount(invoiceApply.getAmount());
			    			invoiceApplyConfirmDtl.setOrderDtlId(apply.getDtlId());
			    			invoiceApplyConfirmDtl.setOrderNo(apply.getBillNo());
			    			invoiceApplyConfirmDtlService.updateByOrderNo(invoiceApplyConfirmDtl);
						}
						
						//根据内购销售明细产生按大类分明细
						addInvoiceApplyDtlByOrderList(categoryMaps,billNo);
					}
				} else {
					//保存按大类分明细(其他结算的)
					addInvoiceApplyDtlByOtherBalance(invoiceApply);
					
					invoiceApply.setBillNo(balanceNo);
					invoiceApply.setInvoiceApplyNo(billNo);
					invoiceApply.setStatus(BalanceStatusEnums.MAKE_INVOICE.getTypeNo());
					billBalanceInvoiceApplyService.updateInvoiceApplyNo(invoiceApply);
				}
			}
			invoiceApply.setBillNo(billNo);
			invoiceApply.setStatus(1);
			invoiceApply.setId(UUIDGenerator.getUUID());
			billBalanceInvoiceApplyService.add(invoiceApply);
		}catch(ServiceException e){
			log.error("手动新增开票申请:", e);
			throw new ManagerException();
		}
		return invoiceApply; 
	}
	
	
	/**
	 * 结算单直接生成开票申请操作的业务处理
	 * @param balanceInvoiceApplyGenerators
	 * @throws ServiceException
	 * @author wangxj
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalanceInvoiceApply addBalanceInvoiceApply(BillBalanceInvoiceApply invoiceApply) throws ManagerException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("balanceNo", invoiceApply.getBalanceNo());
			invoiceApply.setErrorInfo("");
			List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceService.findByBiz(null, param);
			if (!CollectionUtils.isEmpty(sourceList)) {// 如果结算单已经存在，则表示已经开过票，直接返回
				invoiceApply.setErrorInfo("生成失败，结算单号：" + invoiceApply.getBalanceNo() + " 已做过开票申请。");
				return invoiceApply;
			}
			if(StringUtils.isEmpty(invoiceApply.getBalanceType())){
				invoiceApply.setErrorInfo("生成失败，结算类型为空");
				return invoiceApply;
			}
			// 根据开票方查询开票信息并设置相关信息
			findInvoiceInfoByBuyerNo(invoiceApply);
			//如果错误信息不为空，则表示开票信息没做维护，直接返回
			if(StringUtils.isNotBlank(invoiceApply.getErrorInfo())){
				return invoiceApply;
			}
			
			String requestId = BillBalanceInvoiceApply.class.getSimpleName();
			if (null == invoiceApply.getInvoiceApplyDate()) {
				invoiceApply.setInvoiceApplyDate(new Date());
			}
			if (null == invoiceApply.getPostPayDate()) {
				invoiceApply.setPostPayDate(new Date());
			}
	
			Date date = new Date();
			String applyNo = commonUtilService.getNewBillNoCompanyNo(invoiceApply.getSalerNo(), null, requestId);
			invoiceApply.setBillNo(applyNo);
			invoiceApply.setInvoiceApplyDate(date);
			invoiceApply.setPostPayDate(date);
			invoiceApply.setCreateTime(date);
			invoiceApply.setPreInvoice((byte) 1);// 默认为否
			//设置默认币别
			CurrencyManagement currency = currencyManagementService.findDefaultCurrency();
			invoiceApply.setCurrency(currency.getCurrencyCode());
			invoiceApply.setStatus(1);
			
			if(BalanceTypeEnums.AREA_MALL.getTypeNo() == Integer.parseInt(invoiceApply.getBalanceType())){
				addInvoiceApplyDtlByMallBalance(invoiceApply);
				
				//地区门店结算回写  
				BillShopBalance shopBalance = new BillShopBalance();
				shopBalance.setBalanceNo(invoiceApply.getBalanceNo());
				shopBalance.setInvoiceApplyNo(applyNo);
				shopBalance.setStatus(BalanceStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo());
				shopBalance.setInvoiceApplyDate(invoiceApply.getInvoiceApplyDate());
				int ret = billShopBalanceService.updateInvoiceByBalanceNo(shopBalance);
				log.info("addBalanceInvoiceApply回写结算单的信息,开票日期:"+invoiceApply.getInvoiceApplyDate()
						+";开票申请号:"+invoiceApply.getBillNo()+";结果:"+ret);
				// 回写结算期管理 
				BillShopBalance billShopBalance = billShopBalanceService.findById(shopBalance);
				if(null != billShopBalance){
					ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
					shopBalanceDatePar.setShopNo(billShopBalance.getShopNo());
					shopBalanceDatePar.setMonth(billShopBalance.getMonth());
					shopBalanceDatePar.setBillalready((byte)2);
					shopBalanceDatePar.setBalanceStartDate(billShopBalance.getBalanceStartDate());
					shopBalanceDatePar.setBalanceEndDate(billShopBalance.getBalanceEndDate());
					shopBalanceDateService.updateBalanceDateSet(shopBalanceDatePar);
				}
			}else{
				//保存按大类分明细
				addInvoiceApplyDtlByOtherBalance(invoiceApply);
				
				// （其他结算）更新结算单信息
				BillBalanceInvoiceApply updateCondition = new BillBalanceInvoiceApply();
				updateCondition.setBillNo(invoiceApply.getBalanceNo());
				updateCondition.setInvoiceApplyNo(applyNo);
				updateCondition.setStatus(BalanceStatusEnums.MAKE_INVOICE.getTypeNo());
				billBalanceInvoiceApplyService.updateInvoiceApplyNo(updateCondition);
			}
			//保存源单信息
			BillBalanceInvoiceSource invoiceSource = new BillBalanceInvoiceSource();
			invoiceSource.setBalanceNo(invoiceApply.getBalanceNo());
			invoiceSource.setBillNo(applyNo);
			invoiceSource.setAmount(invoiceApply.getAmount());
			invoiceSource.setBalanceType(Integer.parseInt(invoiceApply.getBalanceType()));
			invoiceSource.setId(UUIDGenerator.getUUID());
			invoiceSource.setSalerNo(invoiceApply.getSalerNo());
			// 添加到关联表
			billBalanceInvoiceSourceService.add(invoiceSource);
			
			//保存开票申请表头信息
			invoiceApply.setId(UUIDGenerator.getUUID());
			billBalanceInvoiceApplyService.add(invoiceApply);
		}catch(ServiceException e){
			log.error("结算单生成开票申请:", e);
			throw new ManagerException(e);
		}
		return invoiceApply;	
	}
	
	/**
	 * 新增按大类分明细信息（除地区门店结算外的其他结算单）
	 * @param invoiceApply
	 * @throws ServiceException
	 */
	private void addInvoiceApplyDtlByOtherBalance(BillBalanceInvoiceApply invoiceApply) throws ServiceException{
		if(StringUtils.isEmpty(invoiceApply.getBalanceNo())){
			return;
		}
		//获取结算单大类汇总信息 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", invoiceApply.getBalanceNo());
		List<BillSaleBalance> saleList = billBalanceInvoiceApplyService.getBillSaleBalanceSum(null,null, null, params);
		String invoiceName = "";
		String cateNos = "";
		String cateNames = "";
		for (BillSaleBalance billSaleBalance : saleList) {
			
			// 数量是否控制
			BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
			billBalanceInvoiceDtl.setQty(billSaleBalance.getSendQty());
			if(StringUtils.isNotBlank(billSaleBalance.getCategoryNo())){
				Category category = new Category();
				category.setCategoryNo(billSaleBalance.getCategoryNo().substring(0, 2));
				category = categoryService.findById(category);
				if(null != category){
					cateNos = category.getCategoryNo();
					cateNames = category.getName();
					invoiceName = category.getName();
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("companyNo", invoiceApply.getSalerNo());
				
				// 财务大类编号
				String financialCategoryNo = "";
				map.put("categoryNo", cateNos);
				//根据结算公司及大类编号获取财务大类信息
				List<FinancialCategory> financialCategoryList = financialCategoryService.selectCateInfoByCateNo(map);
				if(!CollectionUtils.isEmpty(financialCategoryList)){
					invoiceName = financialCategoryList.get(0).getName();
					financialCategoryNo = financialCategoryList.get(0).getFinancialCategoryNo();
				}
				if(StringUtils.isNotBlank(cateNos) && StringUtils.isNotBlank(financialCategoryNo)){
					// 根据大类及财务大类编号，查询明细，主要作数量控制
					map.put("categoryNo", cateNos);
					map.put("financialCategoryNo", financialCategoryNo);
					List<FinancialCategoryDtl> fcDtlList = financialCategoryDtlService.findByBiz(null, map);
					
					//如果发票模板是否启用数量控制为：是，则数量为0
					if (null != fcDtlList && null != fcDtlList.get(0) && null != fcDtlList.get(0).getQtyControlFlag() 
							&& fcDtlList.get(0).getQtyControlFlag() == 1 ){
						billBalanceInvoiceDtl.setQty(0);
					}
				}
			}
			
			billBalanceInvoiceDtl.setBillNo(invoiceApply.getBillNo());
			billBalanceInvoiceDtl.setBalanceEndDate(billSaleBalance.getBalanceEndDate());
			billBalanceInvoiceDtl.setBalanceStartDate(billSaleBalance.getBalanceStartDate());
			billBalanceInvoiceDtl.setBrandName(billSaleBalance.getBrandName());
			billBalanceInvoiceDtl.setBrandNo(billSaleBalance.getBrandNo());
			billBalanceInvoiceDtl.setOrganNo(billSaleBalance.getOrganNoFrom());
			billBalanceInvoiceDtl.setOrganName(billSaleBalance.getOrganNameFrom());
			billBalanceInvoiceDtl.setCategoryName(cateNames);
			billBalanceInvoiceDtl.setCategoryNo(cateNos);
			billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
			if(null != billSaleBalance.getSendAmount()){
				billBalanceInvoiceDtl.setNoTaxAmount(billSaleBalance.getSendAmount().divide(
					new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
			}
			billBalanceInvoiceDtl.setPosEarningAmount(new BigDecimal(0));
			
			billBalanceInvoiceDtl.setSalerName(invoiceName);
			billBalanceInvoiceDtl.setSendAmount(billSaleBalance.getSendAmount());
			if(null != billBalanceInvoiceDtl.getNoTaxAmount()){
				billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
					new BigDecimal(0.17)));
			}
			billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
			billBalanceInvoiceDtl.setId(UUIDGenerator.getUUID());
			billBalanceInvoiceDtl.setSalerNo(invoiceApply.getSalerNo());
			billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl);
		}
	}
	
	
	private void addInvoiceApplyDtlByMallBalance(BillBalanceInvoiceApply invoiceApply) throws ServiceException{
		if(StringUtils.isEmpty(invoiceApply.getBalanceNo())){
			return ;
		}
		//获取商场结算单信息
		BillShopBalance billShopBalance = new BillShopBalance();
		billShopBalance.setBalanceNo(invoiceApply.getBalanceNo());
		billShopBalance = billShopBalanceService.findById(billShopBalance);

		//获取结算单大类汇总信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", invoiceApply.getBalanceNo());
		List<BillShopBalanceCateSum> sumList = billShopBalanceCateSumService.findByBiz(null, params);

		//新增大类汇总信息
		if (null != billShopBalance && !CollectionUtils.isEmpty(sumList)) {
			for (BillShopBalanceCateSum billShopBalanceCateSum : sumList) {
				
				String invoiceName = "";
				String cateNos = "";
				String cateNames = "";
				Category category = new Category();
				
				BillBalanceInvoiceDtl billBalanceInvoiceDtl = new BillBalanceInvoiceDtl();
				billBalanceInvoiceDtl.setQty(billShopBalanceCateSum.getSaleQty());
				
				if(StringUtils.isNotBlank(billShopBalanceCateSum.getCategoryNo())){
					category.setCategoryNo(billShopBalanceCateSum.getCategoryNo().substring(0, 2));
					category = categoryService.findById(category);
				
					if(null != category){
						invoiceName = category.getName();
						cateNos = category.getCategoryNo();
						cateNames = category.getName();
					}
					Map<String, Object> shopMap = new HashMap<String, Object>();
					shopMap.put("shopNo", billShopBalanceCateSum.getShopNo());
					shopMap.put("companyNo", invoiceApply.getSalerNo());
					shopMap.put("validDate", new Date());
					List<ShopGroup> shopGroupList = shopGroupService.getShopGroupNoByShopNo(shopMap);
					
					if(!CollectionUtils.isEmpty(shopGroupList) && StringUtils.isNotBlank(shopGroupList.get(0).getTemplateNo())){
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("invoiceTempNo", shopGroupList.get(0).getTemplateNo());
						if(StringUtils.isNotBlank(billShopBalanceCateSum.getCategoryNo())){
							paramMap.put("categoryNo", billShopBalanceCateSum.getCategoryNo().substring(0, 2));
							
							List<InvoiceTemplateSetDtl> dtlList = invoiceTemplateSetDtlService.findByBiz(null, paramMap);
							
							if(!CollectionUtils.isEmpty(dtlList) && null != dtlList.get(0)){
								invoiceName = dtlList.get(0).getInvoiceName();
								//如果发票模板是否启用数量控制为：是，则数量为0
								if (null != dtlList.get(0).getQtyControlFlag() && dtlList.get(0).getQtyControlFlag() == 1 ){
									billBalanceInvoiceDtl.setQty(0);
								}
							}
						}
						
					}
				}
				
				
				//billBalanceInvoiceDtl.setBalanceNo(balanceNo);
				billBalanceInvoiceDtl.setBillNo(invoiceApply.getBillNo());
				billBalanceInvoiceDtl.setActualRate(billShopBalance.getActualRate());
				billBalanceInvoiceDtl.setContractRate(billShopBalance.getContractRate());
				billBalanceInvoiceDtl.setBalanceEndDate(billShopBalanceCateSum.getBalanceEndDate());
				billBalanceInvoiceDtl.setBalanceStartDate(billShopBalanceCateSum.getBalanceStartDate());
				billBalanceInvoiceDtl.setBrandName(billShopBalanceCateSum.getBrandName());
				billBalanceInvoiceDtl.setBrandNo(billShopBalanceCateSum.getBrandNo());
				billBalanceInvoiceDtl.setRetailType(billShopBalance.getRetailType());
				billBalanceInvoiceDtl.setOrganNo(billShopBalance.getOrganNo());
				billBalanceInvoiceDtl.setOrganName(billShopBalance.getOrganName());
				billBalanceInvoiceDtl.setCategoryName(cateNames);
				billBalanceInvoiceDtl.setCategoryNo(cateNos);
				billBalanceInvoiceDtl.setSendAmount(billShopBalanceCateSum.getBillingAmount());
				billBalanceInvoiceDtl.setEstimatedAmount(new BigDecimal(0));
				billBalanceInvoiceDtl.setFullName(billShopBalanceCateSum.getFullName());
				if(null != billBalanceInvoiceDtl.getSendAmount()){
					billBalanceInvoiceDtl.setNoTaxAmount(billBalanceInvoiceDtl.getSendAmount().divide(
						new BigDecimal(1.17), 10, RoundingMode.HALF_DOWN));
				}
				billBalanceInvoiceDtl.setPosEarningAmount(billShopBalanceCateSum.getSaleAmount());
				
				billBalanceInvoiceDtl.setSalerName(invoiceName);
				billBalanceInvoiceDtl.setShopNo(billShopBalanceCateSum.getShopNo());
				billBalanceInvoiceDtl.setShortName(billShopBalanceCateSum.getShortName());
				if(null != billBalanceInvoiceDtl.getNoTaxAmount()){
					billBalanceInvoiceDtl.setTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount().multiply(
						new BigDecimal(0.17)));
				}
				billBalanceInvoiceDtl.setTaxRate(new BigDecimal(0.17));
				billBalanceInvoiceDtl.setId(UUIDGenerator.getUUID());
				billBalanceInvoiceDtl.setSalerNo(invoiceApply.getSalerNo());
				billBalanceInvoiceDtlService.add(billBalanceInvoiceDtl);
			}
		}
	}
	
	/**
	 * 根据选择的内购明细，按大类、品牌、管理城市分组汇总成明细
	 * @param categoryMaps
	 * @param serialNo
	 * @throws ServiceException
	 */
	private void addInvoiceApplyDtlByOrderList(Map<String,List<BalanceInvoiceApplyGenerator>> categoryMaps,String billNo) throws ServiceException{
		
		Iterator<String> iterator = categoryMaps.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			
			List<BalanceInvoiceApplyGenerator> balanceInvoiceApplyGenerator = categoryMaps.get(key);
			
			BigDecimal totalAmount = new BigDecimal(0);
			int itemCount = 0;
			String salerNo = null;
			String brandNo = null ; String brandName = null ;
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
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("companyNo", balanceInvoiceApply.getSalerNo());
				params.put("balanceDate", balanceInvoiceApply.getBalanceDate());
				params.put("invoiceFlag", 1);
				List<InsidePurchaseBalanceDate> list = insidePurchaseBalanceDateService.findByBiz(null, params);
				// 回写内购结算期 
				if(CollectionUtils.isEmpty(list)){
					InsidePurchaseBalanceDate insidePurchase = new InsidePurchaseBalanceDate();
					insidePurchase.setCompanyNo(balanceInvoiceApply.getSalerNo());
					insidePurchase.setInvoiceFlag(1);
					insidePurchase.setBalanceStartDate(balanceInvoiceApply.getBalanceDate());
					insidePurchaseBalanceDateService.updateInvoiceFlagByCondition(insidePurchase);
				}
			}
			
			billBalanceInvoiceDtl.setQty(itemCount);
			billBalanceInvoiceDtl.setBillNo(billNo);
			billBalanceInvoiceDtl.setSendAmount(totalAmount);
			billBalanceInvoiceDtl.setBrandName(brandName);
			billBalanceInvoiceDtl.setBrandNo(brandNo);
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
	 * 根据收票方查询开票信息，并设置相关信息（如果是地区门店，开票方的取值：则根据店铺编号查询店铺开票规则的客户方，如果没有维护店铺开票规则，
	 * 则根据店铺编号获取对应的商场，如果没有商场，则取商业集团）
	 * @param invoiceApply
	 * @throws ServiceException
	 */
	private void findInvoiceInfoByBuyerNo(BillBalanceInvoiceApply invoiceApply) throws ServiceException{
		String buyerNo = invoiceApply.getBuyerNo();
		String bsgroupsNo = "";
		String invoiceName = "";
		Date date = new Date();
		if(BalanceTypeEnums.AREA_MALL.getTypeNo() == Integer.parseInt(invoiceApply.getBalanceType())){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shopNo", invoiceApply.getBuyerNo());
			map.put("companyNo", invoiceApply.getSalerNo());
			map.put("validDate", date);
			List<ShopGroup> shopList = shopGroupService.getShopGroupNoByShopNo(map);
			
			if(!CollectionUtils.isEmpty(shopList)){
				if(StringUtils.isNotEmpty(shopList.get(0).getClientNo())){
					invoiceName = shopList.get(0).getInvoiceName();
					buyerNo = shopList.get(0).getClientNo();
					invoiceApply.setBuyerName(shopList.get(0).getClientName());
					invoiceApply.setBuyerNo(shopList.get(0).getClientNo());
				}
			}else{
				Shop shop = shopService.selectSubsiInfo(map);
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
		}
		
		if(StringUtils.isNotBlank(buyerNo)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyNo", invoiceApply.getSalerNo());
			map.put("clientNo", buyerNo);
			map.put("month", date);
//			map.put("status", RULESTATUS);
			InvoiceInfo invoiceInfo = null;
			//根据公司编码、客户编码获取开票信息
			List<InvoiceInfo> invoiceInfoList = invoiceInfoService.findByBiz(null, map);
			
			if(CollectionUtils.isEmpty(invoiceInfoList) && StringUtils.isNotBlank(bsgroupsNo)){//如果根据商场编号查询开票信息，返回空，则根据商业集团编号查询
				map.put("clientNo", bsgroupsNo);
				invoiceInfoList = invoiceInfoService.findByBiz(null, map);
			} 
			if(!CollectionUtils.isEmpty(invoiceInfoList)){
				//返回多笔开票信息时，优先取首选的那笔记录
				for (InvoiceInfo invoiceInfo2 : invoiceInfoList) {
					if(invoiceInfo2.getStatus() == RULESTATUS){
						invoiceInfo = invoiceInfo2;
						break;
					}
				}
				//如果多笔中都没有首选的记录，则取第一笔
				if(null == invoiceInfo){
					invoiceInfo = invoiceInfoList.get(0);
				}
				if(null != invoiceInfo){
					invoiceApply.setTaxRegistryNo(invoiceInfo.getTaxpayerNo());
					invoiceApply.setBankName(invoiceInfo.getBankName());
					invoiceApply.setBankAccount(invoiceInfo.getAccountNo());
					invoiceApply.setBankAccountName(invoiceInfo.getBankName());
//					invoiceApply.setAmount(invoiceApply.getAmount());
					invoiceApply.setBuyerAddress(invoiceInfo.getAddress());
					invoiceApply.setBuyerTel(invoiceInfo.getContactNumber());
					invoiceApply.setMailingAddress(invoiceInfo.getPostAddress());
					invoiceApply.setContactName(invoiceInfo.getContactPerson());
					invoiceApply.setTel(invoiceInfo.getTelephoneNumber());
					invoiceApply.setBuyerName(invoiceInfo.getClientName());
					invoiceApply.setBuyerNo(invoiceInfo.getClientNo());
					if(StringUtils.isNotBlank(invoiceName)){
						invoiceApply.setInvoiceName(invoiceName);
					}else{
						invoiceApply.setInvoiceName(invoiceInfo.getInvoiceName());
					}
					invoiceApply.setInvoiceType(invoiceInfo.getInvoiceType());
				}
			}else{
				invoiceApply.setErrorInfo("生成失败，开票信息没做维护或不完善。");
			}
		}else{
			invoiceApply.setErrorInfo("生成失败，开票客户方为空。");
		}
	}
	
	/**
	 * 导出税控系统的模板
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	@Override
	public List<BillBalanceInvoiceApply> findTaxExportList(Map<String,Object> params) throws ManagerException{
		try {
			return billBalanceInvoiceApplyService.findTaxExportList(params);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}
	
	/**
	 * 查询华东税控模板信息
	 * @param params
	 * @return
	 */
	@Override
	public List<BillBalanceInvoiceApply> findHdTaxExportList(Map<String,Object> params) throws ManagerException{
		try {
			return billBalanceInvoiceApplyService.findHdTaxExportList(params);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(),e);
		}
	}
}