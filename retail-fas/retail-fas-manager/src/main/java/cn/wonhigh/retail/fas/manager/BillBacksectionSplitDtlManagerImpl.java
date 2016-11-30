package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplit;
import cn.wonhigh.retail.fas.common.model.BillBacksectionSplitDtl;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.BillBacksectionSplitDtlService;
import cn.wonhigh.retail.fas.service.BillBacksectionSplitService;
import cn.wonhigh.retail.fas.service.BillShopBalanceService;
import cn.wonhigh.retail.fas.service.CommonUtilService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import com.yougou.logistics.base.service.CodingRuleService;

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
@Service("billBacksectionSplitDtlManager")
class BillBacksectionSplitDtlManagerImpl extends BaseCrudManagerImpl implements BillBacksectionSplitDtlManager {
    @Resource
    private BillBacksectionSplitDtlService billBacksectionSplitDtlService;
    
    @Resource
    private BillBacksectionSplitService billBacksectionSplitService;
    
    @Resource
    private BillShopBalanceService billShopBalanceService;
    
	@Resource
	private CodingRuleService codingRuleService;
	
	  
    @Resource
	private CommonUtilService commonUtilService;

    @Override
    public BaseCrudService init() {
        return billBacksectionSplitDtlService;
    }

	@Override
	public int addBacksectionSplitDtlByBatch(
			BillShopBalance balance)
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", balance.getBalanceNo());//结算单编号
		params.put("mallNo", balance.getMallNo());
		params.put("month", balance.getMonth());
		List<BillShopBalance> billShopBalanceList =billShopBalanceService.findByBiz(balance, params);
		int n=0;
		BillBacksectionSplitDtl billBacksectionSplitDtl = new BillBacksectionSplitDtl();
		for (BillShopBalance billShopBalance : billShopBalanceList) {
			billBacksectionSplitDtl.setBalanceNo(billShopBalance.getBalanceNo());
			billBacksectionSplitDtl.setMallNo(billShopBalance.getMallNo());
			billBacksectionSplitDtl.setOrganNo(billShopBalance.getOrganNo());
			n = billBacksectionSplitDtlService.add(billBacksectionSplitDtl);
		}
		return n;
	}

	@Override
	public int selectShopBlanaceAccountCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBacksectionSplitDtlService.selectShopBlanaceAccountCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);	
		}
	}

	@Override
	public List<BillBacksectionSplitDtl> selectShopBlanaceAccountByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			// 设置收款差异金额
			String balanceNo = (String) params.get("balanceNo");
			BillShopBalance  billShopBalance = new BillShopBalance();
			billShopBalance.setBalanceNo(balanceNo);
			BillShopBalance  billShopBalanceSou = billShopBalanceService.findById(billShopBalance);
			BigDecimal thePaymentAmount = billBacksectionSplitDtlService.sumPaymentAmount(billShopBalance.getBalanceNo());
			 if(thePaymentAmount.compareTo(BigDecimal.ZERO) != 0){
				BigDecimal differenceAmount = BigDecimalUtil.subtract(billShopBalanceSou.getMallBillingAmount(), thePaymentAmount);
				billShopBalance.setDifferenceAmount(differenceAmount);
				
				int iCount = billShopBalanceService.modifyById(billShopBalance);
			 }
//			if(iCount < 1) {
//				return null;
//			}
			
			return billBacksectionSplitDtlService.selectShopBlanaceAccountByPage(page, sortColumn, sortOrder, params);
			
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);	
		}
	}

	@Override
	public int selectAddInsertDtl(
			Map<String, Object> params) throws ManagerException {
		
		try {
			//查询回款单
			BillBacksectionSplit selParam = new BillBacksectionSplit();
			selParam.setId(params.get("id").toString());
			BillBacksectionSplit billEntity =  billBacksectionSplitService.findById(selParam);
			
			//删除回款明细
			billBacksectionSplitDtlService.deleteByBacksectionNo(billEntity.getBacksectionNo());
			
			//查询需要回款的结算单
			Map<String, Object> dtlParams = new HashMap<String, Object>();
			dtlParams.put("companyNo", billEntity.getCompanyNo());
			dtlParams.put("backNo", billEntity.getBackNo());
			dtlParams.put("backDate", DateUtil.format1(billEntity.getBackDate()));
			List<BillBacksectionSplitDtl> dtlList = billBacksectionSplitDtlService.selectAddInsertDtl(dtlParams);
			
			BigDecimal backAmount =  billEntity.getBackAmount();
			
			//格式化明细列表
			for(BillBacksectionSplitDtl dtl : dtlList) {
				dtl.setId(UUIDGenerator.getUUID());
				dtl.setBacksectionNo(billEntity.getBacksectionNo());
				//该字段未使用，数据库为非空，给默认值
				dtl.setBacksectionDtlNo("BSD0001");
				
				/*
				 * 分摊回款金额
				 */
				//回款余额不足，本次回款金额=回款余额
				if(backAmount.compareTo(dtl.getNotReceiveAmount()) == -1) {
					dtl.setThePaymentAmount(backAmount);
					if(backAmount.doubleValue() != 0d) {
						backAmount = new BigDecimal(0d);
					}
				}
				//回款余额充足，本次回款金额=本次应回款金额
				else {
					dtl.setThePaymentAmount(dtl.getNotReceiveAmount());
					backAmount = backAmount.subtract(dtl.getNotReceiveAmount());
				}
				//应收款余额
				dtl.setDiffAmount(dtl.getNotReceiveAmount().subtract(dtl.getThePaymentAmount()));
				
			}
			
			//新增回款明细
			Map<CommonOperatorEnum,List<BillBacksectionSplitDtl>> operatrorMap = new HashMap<CommonOperatorEnum,List<BillBacksectionSplitDtl>>();
			operatrorMap.put(CommonOperatorEnum.INSERTED, dtlList);
			int count = billBacksectionSplitDtlService.save(operatrorMap);
			
			return count;
			
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);	
		}
	}
	
	@Override
	public List<BillBacksectionSplitDtl> selectAddDtl(
			Map<String, Object> params) throws ManagerException {
		return billBacksectionSplitDtlService.selectAddInsertDtl(params);
	}

	@Override
	public BillBacksectionSplitDtl selectTotalSum(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBacksectionSplitDtlService.selectTotalSum(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int batchAdd(List<BillBacksectionSplitDtl> list,BillShopBalance balance)
			throws ManagerException {
		int iCount = 0;
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("balanceNo", balance.getBalanceNo());//结算单编号
		params.put("companyNo",balance.getCompanyNo());
		params.put("mallNo", balance.getMallNo());
		params.put("month", balance.getMonth());
		params.put("organNo", balance.getOrganNo());
		params.put("bsgroupsNo", balance.getBsgroupsNo());
		params.put("shopNo", balance.getShopNo());
		List<BillShopBalance> billShopBalanceList = null;
		try {
			billShopBalanceList = billShopBalanceService.findByBiz(balance, params);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		if ((!CollectionUtils.isEmpty(billShopBalanceList)) && (billShopBalanceList.size())<=0){
			iCount = -1;
			return iCount;
		} 
		
		if ((!CollectionUtils.isEmpty(billShopBalanceList)) && (billShopBalanceList.size())>0){		
		BillBacksectionSplit billBacksectionSplit = new BillBacksectionSplit();
		billBacksectionSplit.setId(UUIDGenerator.getUUID());
		billBacksectionSplit.setCompanyNo(balance.getCompanyNo());
		billBacksectionSplit.setCompanyName(balance.getCompanyName());
		String billNo = null;
//		try {
//			billNo = codingRuleService.getSerialNo(BillBacksectionSplit.class.getSimpleName());
//		} catch (ServiceException e2) {
//			e2.printStackTrace();
//		}
//		billBacksectionSplit.setBacksectionNo(billNo);
		
		String requestId = BillBacksectionSplit.class.getSimpleName();
		//调用单据编号拼接处理方法，返回最终的单据编号s
    	try {
			billNo = commonUtilService.getNewBillNoCompanyNo(balance.getCompanyNo(),null,requestId);
		} catch (ServiceException e2) {
//			logger
		}
    	billBacksectionSplit.setBacksectionNo(billNo);
		try {
			billBacksectionSplitService.batchAdd(billBacksectionSplit);
		} catch (ServiceException e1) {
//			log
		}
		BillBacksectionSplitDtl billBacksectionSplitDtl = new BillBacksectionSplitDtl();
		   billBacksectionSplitDtl.setBacksectionNo(billNo);
		for (BillShopBalance billShopBalance : billShopBalanceList) {
			billBacksectionSplitDtl.setBalanceNo(billShopBalance.getBalanceNo());
			billBacksectionSplitDtl.setMallNo(billShopBalance.getMallNo());
			billBacksectionSplitDtl.setMallName(billShopBalance.getMallName());
			billBacksectionSplitDtl.setOrganNo(billShopBalance.getOrganNo());
			billBacksectionSplitDtl.setOrganName(billShopBalance.getOrganName());
			billBacksectionSplitDtl.setBsgroupsNo(billShopBalance.getBsgroupsNo());
			billBacksectionSplitDtl.setBsgroupsName(billShopBalance.getBsgroupsName());
			billBacksectionSplitDtl.setSystemSalesAmount(billShopBalance.getSystemSalesAmount());
			billBacksectionSplitDtl.setBillingAmount(billShopBalance.getBillingAmount());
			billBacksectionSplitDtl.setBalanceStartDate(billShopBalance.getBalanceStartDate());
			billBacksectionSplitDtl.setBalanceEndDate(billShopBalance.getBalanceEndDate());
			billBacksectionSplitDtl.setInvoiceApplyDate(billShopBalance.getInvoiceApplyDate());
			billBacksectionSplitDtl.setId(UUIDGenerator.getUUID());
			billBacksectionSplitDtl.setCreateUser(billShopBalance.getCreateUser());
			billBacksectionSplitDtl.setCreateTime(billShopBalance.getCreateTime());
			billBacksectionSplitDtl.setShardingFlag(billShopBalance.getShardingFlag());
			billBacksectionSplitDtl.setShopNo(billShopBalance.getShopNo());
			billBacksectionSplitDtl.setShortName(billShopBalance.getShortName());
			billBacksectionSplitDtl.setMonth(billShopBalance.getMonth());
			billBacksectionSplitDtl.setCompanyNo(billShopBalance.getCompanyNo());
			billBacksectionSplitDtl.setCompanyName(billShopBalance.getCompanyName());
			billBacksectionSplitDtl.setBrandNo(billShopBalance.getBrandNo());
			billBacksectionSplitDtl.setBrandName(billShopBalance.getBrandName());
			try {
				iCount = billBacksectionSplitDtlService.add(billBacksectionSplitDtl);
			} catch (ServiceException e) {
//				log
			}
		}
		return iCount;
		}
		return iCount;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int batchAdds(List<BillBacksectionSplitDtl> list,
			BillShopBalance balance) throws ManagerException {
		int iCount = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo",balance.getCompanyNo());
		params.put("mallNo", balance.getMallNo());
		params.put("month", balance.getMonth());
		params.put("organNo", balance.getOrganNo());
		params.put("bsgroupsNo", balance.getBsgroupsNo());
		params.put("shopNo", balance.getShopNo());
//		调整为做了结算单的从结算单读取(bill_shop_balance_cate_sum)品牌，没有做结算单的销售明细获取   
		List<BillShopBalance> billShopBalanceList = null;
		try {
			billShopBalanceList = billShopBalanceService.findShopBalanceSalesInfo(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((!CollectionUtils.isEmpty(billShopBalanceList)) && (billShopBalanceList.size())<=0){
			iCount = -1;
			return iCount;
		} 
		
		if ((!CollectionUtils.isEmpty(billShopBalanceList)) && (billShopBalanceList.size())>0){		
		BillBacksectionSplit billBacksectionSplit = new BillBacksectionSplit();
		billBacksectionSplit.setId(UUIDGenerator.getUUID());
		billBacksectionSplit.setCompanyNo(balance.getCompanyNo());
		billBacksectionSplit.setCompanyName(balance.getCompanyName());
		billBacksectionSplit.setCreateTime(balance.getCreateTime());
		billBacksectionSplit.setCreateUser(balance.getCreateUser());
		String billNo = null;
//		try {
//			billNo = codingRuleService.getSerialNo(BillBacksectionSplit.class.getSimpleName());
//		} catch (ServiceException e2) {
//			e2.printStackTrace();
//		}
//		billBacksectionSplit.setBacksectionNo(billNo);
		
		String requestId = BillBacksectionSplit.class.getSimpleName();
		//调用单据编号拼接处理方法，返回最终的单据编号s
    	try {
			billNo = commonUtilService.getNewBillNoCompanyNo(balance.getCompanyNo(),null,requestId);
		} catch (ServiceException e2) {
//			logger
		}
    	billBacksectionSplit.setBacksectionNo(billNo);
		try {
			billBacksectionSplitService.batchAdd(billBacksectionSplit);
		} catch (ServiceException e1) {
//			log
		}
		BillBacksectionSplitDtl billBacksectionSplitDtl = new BillBacksectionSplitDtl();
		   billBacksectionSplitDtl.setBacksectionNo(billNo);
		for (BillShopBalance billShopBalance : billShopBalanceList) {
			billBacksectionSplitDtl.setBalanceNo(billShopBalance.getBalanceNo());
			billBacksectionSplitDtl.setMallNo(billShopBalance.getMallNo());
			billBacksectionSplitDtl.setMallName(billShopBalance.getMallName());
			billBacksectionSplitDtl.setOrganNo(billShopBalance.getOrganNo());
			billBacksectionSplitDtl.setOrganName(billShopBalance.getOrganName());
			billBacksectionSplitDtl.setBsgroupsNo(billShopBalance.getBsgroupsNo());
			billBacksectionSplitDtl.setBsgroupsName(billShopBalance.getBsgroupsName());
			billBacksectionSplitDtl.setSystemSalesAmount(billShopBalance.getSystemSalesAmount());
			billBacksectionSplitDtl.setTicketDeductionAmount(billShopBalance.getTicketDeductionAmount());
			billBacksectionSplitDtl.setBillingAmount(billShopBalance.getBillingAmount());
			if (billShopBalance.getBalanceStartDate() != null) {
				billBacksectionSplitDtl.setBalanceStartDate(billShopBalance.getBalanceStartDate());
			}
			if (billShopBalance.getBalanceEndDate() != null) {
				billBacksectionSplitDtl.setBalanceEndDate(billShopBalance.getBalanceEndDate());
			}
			if (billShopBalance.getInvoiceApplyDate() != null) {
			      billBacksectionSplitDtl.setInvoiceApplyDate(billShopBalance.getInvoiceApplyDate());
			}
			billBacksectionSplitDtl.setId(UUIDGenerator.getUUID());
			billBacksectionSplitDtl.setCreateUser(billShopBalance.getCreateUser());
			billBacksectionSplitDtl.setCreateTime(billShopBalance.getCreateTime());
			billBacksectionSplitDtl.setShardingFlag(billShopBalance.getShardingFlag());
			billBacksectionSplitDtl.setShopNo(billShopBalance.getShopNo());
			billBacksectionSplitDtl.setShortName(billShopBalance.getShortName());
			billBacksectionSplitDtl.setMonth(billShopBalance.getMonth());
			billBacksectionSplitDtl.setCompanyNo(billShopBalance.getCompanyNo());
			billBacksectionSplitDtl.setCompanyName(billShopBalance.getCompanyName());
			billBacksectionSplitDtl.setBrandNo(billShopBalance.getBrandNo());
			billBacksectionSplitDtl.setBrandName(billShopBalance.getBrandName());
			try {
				billBacksectionSplitDtlService.add(billBacksectionSplitDtl);
				iCount++;  
			} catch (ServiceException e) {
			}		
		}
		}
		return iCount;
	}

//	@Override
//	public int batchAdd(List<BillBacksectionSplitDtl> list,BillShopBalance balance)
//			throws ManagerException {
//		int iCount = 0;
//		Map<String, Object> params = new HashMap<String, Object>();
////		params.put("balanceNo", balance.getBalanceNo());//结算单编号
//		params.put("companyNo",balance.getCompanyNo());
//		params.put("mallNo", balance.getMallNo());
//		params.put("month", balance.getMonth());
//		params.put("organNo", balance.getOrganNo());
//		params.put("bsgroupsNo", balance.getBsgroupsNo());
//		params.put("shopNo", balance.getShopNo());
//		List<BillShopBalance> billShopBalanceList = null;
//		try {
//			billShopBalanceList = billShopBalanceService.findByBiz(balance, params);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//		if ((!CollectionUtils.isEmpty(billShopBalanceList)) && (billShopBalanceList.size())<=0){
//			iCount = -1;
//			return iCount;
//		} 
//		
//		if ((!CollectionUtils.isEmpty(billShopBalanceList)) && (billShopBalanceList.size())>0){		
//		BillBacksectionSplit billBacksectionSplit = new BillBacksectionSplit();
//		billBacksectionSplit.setId(UUIDGenerator.getUUID());
//		billBacksectionSplit.setCompanyNo(balance.getCompanyNo());
//		billBacksectionSplit.setCompanyName(balance.getCompanyName());
//		String billNo = null;
////		try {
////			billNo = codingRuleService.getSerialNo(BillBacksectionSplit.class.getSimpleName());
////		} catch (ServiceException e2) {
////			e2.printStackTrace();
////		}
////		billBacksectionSplit.setBacksectionNo(billNo);
//		
//		String requestId = BillBacksectionSplit.class.getSimpleName();
//		//调用单据编号拼接处理方法，返回最终的单据编号s
//    	try {
//			billNo = commonUtilService.getNewBillNoCompanyNo(balance.getCompanyNo(),null,requestId);
//		} catch (ServiceException e2) {
////			logger
//		}
//    	billBacksectionSplit.setBacksectionNo(billNo);
//		try {
//			billBacksectionSplitService.batchAdd(billBacksectionSplit);
//		} catch (ServiceException e1) {
////			log
//		}
//		BillBacksectionSplitDtl billBacksectionSplitDtl = new BillBacksectionSplitDtl();
//		   billBacksectionSplitDtl.setBacksectionNo(billNo);
//		for (BillShopBalance billShopBalance : billShopBalanceList) {
//			billBacksectionSplitDtl.setBalanceNo(billShopBalance.getBalanceNo());
//			billBacksectionSplitDtl.setMallNo(billShopBalance.getMallNo());
//			billBacksectionSplitDtl.setMallName(billShopBalance.getMallName());
//			billBacksectionSplitDtl.setOrganNo(billShopBalance.getOrganNo());
//			billBacksectionSplitDtl.setOrganName(billShopBalance.getOrganName());
//			billBacksectionSplitDtl.setBsgroupsNo(billShopBalance.getBsgroupsNo());
//			billBacksectionSplitDtl.setBsgroupsName(billShopBalance.getBsgroupsName());
//			billBacksectionSplitDtl.setSystemSalesAmount(billShopBalance.getSystemSalesAmount());
//			billBacksectionSplitDtl.setBillingAmount(billShopBalance.getBillingAmount());
//			billBacksectionSplitDtl.setBalanceStartDate(billShopBalance.getBalanceStartDate());
//			billBacksectionSplitDtl.setBalanceEndDate(billShopBalance.getBalanceEndDate());
//			billBacksectionSplitDtl.setInvoiceApplyDate(billShopBalance.getInvoiceApplyDate());
//			billBacksectionSplitDtl.setId(UUIDGenerator.getUUID());
//			billBacksectionSplitDtl.setCreateUser(billShopBalance.getCreateUser());
//			billBacksectionSplitDtl.setCreateTime(billShopBalance.getCreateTime());
//			billBacksectionSplitDtl.setShardingFlag(billShopBalance.getShardingFlag());
//			billBacksectionSplitDtl.setShopNo(billShopBalance.getShopNo());
//			billBacksectionSplitDtl.setShortName(billShopBalance.getShortName());
//			billBacksectionSplitDtl.setMonth(billShopBalance.getMonth());
//			billBacksectionSplitDtl.setCompanyNo(billShopBalance.getCompanyNo());
//			billBacksectionSplitDtl.setCompanyName(billShopBalance.getCompanyName());
//			billBacksectionSplitDtl.setBrandNo(billShopBalance.getBrandNo());
//			billBacksectionSplitDtl.setBrandName(billShopBalance.getBrandName());
//			try {
//				iCount = billBacksectionSplitDtlService.add(billBacksectionSplitDtl);
//			} catch (ServiceException e) {
////				log
//			}
//		}
//		return iCount;
//		}
//		return iCount;
//	}
//
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
//	public int batchAdds(List<BillBacksectionSplitDtl> list,
//			BillShopBalance balance) throws ManagerException {
//		int iCount = 0;
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("companyNo",balance.getCompanyNo());
//		params.put("mallNo", balance.getMallNo());
//		params.put("month", balance.getMonth());
//		params.put("organNo", balance.getOrganNo());
//		params.put("bsgroupsNo", balance.getBsgroupsNo());
//		params.put("shopNo", balance.getShopNo());
////		调整为做了结算单的从结算单读取(bill_shop_balance_cate_sum)品牌，没有做结算单的销售明细获取   
//		List<BillShopBalance> billShopBalanceList = null;
//		try {
//			billShopBalanceList = billShopBalanceService.findShopBalanceSalesInfo(params);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if ((!CollectionUtils.isEmpty(billShopBalanceList)) && (billShopBalanceList.size())<=0){
//			iCount = -1;
//			return iCount;
//		} 
//		
//		if ((!CollectionUtils.isEmpty(billShopBalanceList)) && (billShopBalanceList.size())>0){		
//		BillBacksectionSplit billBacksectionSplit = new BillBacksectionSplit();
//		billBacksectionSplit.setId(UUIDGenerator.getUUID());
//		billBacksectionSplit.setCompanyNo(balance.getCompanyNo());
//		billBacksectionSplit.setCompanyName(balance.getCompanyName());
//		billBacksectionSplit.setCreateTime(balance.getCreateTime());
//		billBacksectionSplit.setCreateUser(balance.getCreateUser());
//		String billNo = null;
////		try {
////			billNo = codingRuleService.getSerialNo(BillBacksectionSplit.class.getSimpleName());
////		} catch (ServiceException e2) {
////			e2.printStackTrace();
////		}
////		billBacksectionSplit.setBacksectionNo(billNo);
//		
//		String requestId = BillBacksectionSplit.class.getSimpleName();
//		//调用单据编号拼接处理方法，返回最终的单据编号s
//    	try {
//			billNo = commonUtilService.getNewBillNoCompanyNo(balance.getCompanyNo(),null,requestId);
//		} catch (ServiceException e2) {
////			logger
//		}
//    	billBacksectionSplit.setBacksectionNo(billNo);
//		try {
//			billBacksectionSplitService.batchAdd(billBacksectionSplit);
//		} catch (ServiceException e1) {
////			log
//		}
//		BillBacksectionSplitDtl billBacksectionSplitDtl = new BillBacksectionSplitDtl();
//		   billBacksectionSplitDtl.setBacksectionNo(billNo);
//		for (BillShopBalance billShopBalance : billShopBalanceList) {
//			billBacksectionSplitDtl.setBalanceNo(billShopBalance.getBalanceNo());
//			billBacksectionSplitDtl.setMallNo(billShopBalance.getMallNo());
//			billBacksectionSplitDtl.setMallName(billShopBalance.getMallName());
//			billBacksectionSplitDtl.setOrganNo(billShopBalance.getOrganNo());
//			billBacksectionSplitDtl.setOrganName(billShopBalance.getOrganName());
//			billBacksectionSplitDtl.setBsgroupsNo(billShopBalance.getBsgroupsNo());
//			billBacksectionSplitDtl.setBsgroupsName(billShopBalance.getBsgroupsName());
//			billBacksectionSplitDtl.setSystemSalesAmount(billShopBalance.getSystemSalesAmount());
//			billBacksectionSplitDtl.setBillingAmount(billShopBalance.getBillingAmount());
//			if (billShopBalance.getBalanceStartDate() != null) {
//				billBacksectionSplitDtl.setBalanceStartDate(billShopBalance.getBalanceStartDate());
//			}
//			if (billShopBalance.getBalanceEndDate() != null) {
//				billBacksectionSplitDtl.setBalanceEndDate(billShopBalance.getBalanceEndDate());
//			}
//			if (billShopBalance.getInvoiceApplyDate() != null) {
//			      billBacksectionSplitDtl.setInvoiceApplyDate(billShopBalance.getInvoiceApplyDate());
//			}
//			billBacksectionSplitDtl.setId(UUIDGenerator.getUUID());
//			billBacksectionSplitDtl.setCreateUser(billShopBalance.getCreateUser());
//			billBacksectionSplitDtl.setCreateTime(billShopBalance.getCreateTime());
//			billBacksectionSplitDtl.setShardingFlag(billShopBalance.getShardingFlag());
//			billBacksectionSplitDtl.setShopNo(billShopBalance.getShopNo());
//			billBacksectionSplitDtl.setShortName(billShopBalance.getShortName());
//			billBacksectionSplitDtl.setMonth(billShopBalance.getMonth());
//			billBacksectionSplitDtl.setCompanyNo(billShopBalance.getCompanyNo());
//			billBacksectionSplitDtl.setCompanyName(billShopBalance.getCompanyName());
//			billBacksectionSplitDtl.setBrandNo(billShopBalance.getBrandNo());
//			billBacksectionSplitDtl.setBrandName(billShopBalance.getBrandName());
//			try {
//				iCount = billBacksectionSplitDtlService.add(billBacksectionSplitDtl);
//			} catch (ServiceException e) {
//			}
//		}
//		return iCount;
//		}
//		return iCount;
//}
}