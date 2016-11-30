package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.GmsBillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.WholesaleRemainingTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.CustomerOrderRemain;
import cn.wonhigh.retail.fas.common.model.ExchangeRateSetup;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.service.BillBalanceService;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;
import cn.wonhigh.retail.fas.service.CurrencyManagementService;
import cn.wonhigh.retail.fas.service.CustomerOrderRemainService;
import cn.wonhigh.retail.fas.service.ExchangeRateSetupService;
import cn.wonhigh.retail.fas.service.OtherDeductionService;
import cn.wonhigh.retail.fas.service.WholesaleCustomerRemainingDtlService;
import cn.wonhigh.retail.fas.service.WholesaleCustomerRemainingSumService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.LongSequenceGenerator;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-21 14:30:24
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
@Service("otherDeductionManager")
class OtherDeductionManagerImpl extends BaseCrudManagerImpl implements OtherDeductionManager {
	private Logger logger = Logger.getLogger(OtherDeductionManagerImpl.class);
    @Resource
    private OtherDeductionService otherDeductionService;

    @Resource
    private BillBuyBalanceService billBuyBalanceService;
    
    @Resource
    private BillBalanceService billBalanceService;
    
    @Resource
    private CurrencyManagementService currencyManagementService;
    
    @Resource
    private ExchangeRateSetupService exchangeRateSetupService;
    
    @Resource
	private WholesaleCustomerRemainingSumService remainingSumService;
    
    @Resource
	private WholesaleCustomerRemainingDtlService remainingDtlService;
    
    @Resource
	private CustomerOrderRemainService customerOrderRemainService;
    
    @Override
    public BaseCrudService init() {
        return otherDeductionService;
    }

	@Override
	public List<OtherDeduction> findFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return otherDeductionService.findFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void saveReturnList(String username, String parentId,
			List<Object> list) throws ManagerException {
		try {
			OtherDeduction obj = new OtherDeduction();
			obj.setId(parentId);
			obj = otherDeductionService.findById(obj);
			int qty = 0 ;
			BigDecimal amount = new BigDecimal(0);
			billBalanceService.clearBuyBalanceNo(parentId);
			for (Object object : list) {
				BillBuyBalance bill = (BillBuyBalance)object;
				bill.setBalanceNo(parentId);
				billBuyBalanceService.modifyById(bill);
				qty += bill.getSendQty();
				amount = amount.add(bill.getSendAmount());
			}
			obj.setDeductionQty(qty);
			obj.setReturnAmount(amount);
			obj.setDeductionAmount(amount.add(obj.getFineAmount()));
			otherDeductionService.modifyById(obj);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<OtherDeduction> setOtherDedutionProperies(List<OtherDeduction> list,Map<String, String> currencyInfo) throws ManagerException {
		boolean flag = ShardingUtil.isBLK();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "1");
			CurrencyManagement targetCurrency = currencyManagementService.findDefaultCurrency();
			for(OtherDeduction otherDedution:list){
				otherDedution.setCurrencyName(currencyInfo.get(otherDedution.getCurrencyNo()));
				if(flag && null != targetCurrency){
					StringBuffer queryCondition = new StringBuffer();
					otherDedution.setTargetCurrencyNo(targetCurrency.getCurrencyCode());//本位币
					otherDedution.setTargetCurrencyName(targetCurrency.getCurrencyName());
					queryCondition.append(" and source_currency = '" + otherDedution.getCurrencyNo() + "'");
					queryCondition.append(" and target_currency = '" + otherDedution.getTargetCurrencyNo() + "'");
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					queryCondition.append(" and effective_date <= '" + date + "'");
					queryCondition.append(" and company_no = '" + otherDedution.getBuyerNo() + "'");
					params.put("queryCondition", new String(queryCondition));
					BigDecimal conversionFactor = BigDecimal.valueOf(0d);
					if(otherDedution.getCurrencyNo() != null && otherDedution.getCurrencyNo().equals(targetCurrency.getCurrencyCode())){
						conversionFactor = BigDecimal.valueOf(1d);
					}else{
						List<ExchangeRateSetup> exchangeRateSetupList = exchangeRateSetupService.findByBiz(null, params);
						if(exchangeRateSetupList.size()>0){
							conversionFactor = exchangeRateSetupList.get(0).getConversionFactor();
						}
					}
					otherDedution.setConversionFactor(conversionFactor);//汇率
					otherDedution.setTargetCurrencyAmount(otherDedution.getFineAmount().multiply(conversionFactor));//本位币金额
				}
			}
		} catch (ServiceException e) {
			logger.error("设置本位币、汇率、本位币金额属性出错", e);
			throw new ManagerException(e.getMessage(), e);
		}
		return list;
	}

	@Override
	@Transactional
	public Boolean distributeDeduction(List<OtherDeduction> otherDeductionList) throws ManagerException {
		try {
			int tempNum = 1;
			for (OtherDeduction otherDeduction : otherDeductionList) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("customerNo", otherDeduction.getBuyerNo());
				WholesaleCustomerRemainingSum remainingSum = null;
				//更新客户余额
				List<WholesaleCustomerRemainingSum> remainingSumList = remainingSumService.findByBiz(null, params);
				if (null != remainingSumList && remainingSumList.size() > 0) {
					//查询扣项是否已经更新客户余额
					params.put("billNo", otherDeduction.getId().toString());
					List<WholesaleCustomerRemainingDtl> dtlList = remainingDtlService.findByBiz(null, params);
					if (null == dtlList || dtlList.size() < 1 ) {
						params.put("remainingAmount", otherDeduction.getDeductionAmount());
						remainingSumService.updateByCustomerNo(params);
						remainingSumList = remainingSumService.findByBiz(null, params);
						remainingSum = remainingSumList.get(0);
						updateRemainingDtl(tempNum,remainingSum, otherDeduction);
						tempNum++;
					}
				} else {
					remainingSum = new WholesaleCustomerRemainingSum();
					remainingSum.setCompanyNo(otherDeduction.getSalerNo());
					remainingSum.setCompanyName(otherDeduction.getSalerName());
					remainingSum.setCustomerNo(otherDeduction.getBuyerNo());
					remainingSum.setCustomerName(otherDeduction.getBuyerName());
					remainingSum.setRemainingAmount(otherDeduction.getDeductionAmount());
					remainingSumService.add(remainingSum);
					remainingSumList = remainingSumService.findByBiz(null, params);
					remainingSum = remainingSumList.get(0);
					updateRemainingDtl(tempNum,remainingSum, otherDeduction);
					tempNum++;
				}
				
				//更新最早一笔未完结的批发订单
				params.clear();
				params.put("customerNo", otherDeduction.getBuyerNo());
				params.put("status", GmsBillStatusEnums.PARTRECEIPT.getStatus());
				CustomerOrderRemain customerOrderRemain = null;
				List<CustomerOrderRemain> orderList = customerOrderRemainService.findByBiz(null, params);
				if (null != orderList && orderList.size() > 0) {
					customerOrderRemain = orderList.get(0);
					customerOrderRemain.setDeductionAmount(customerOrderRemain.getDeductionAmount().add(otherDeduction.getDeductionAmount()));
					customerOrderRemain.setRemainingAmount(customerOrderRemain.getRemainingAmount().add(otherDeduction.getDeductionAmount()));
					customerOrderRemainService.modifyById(customerOrderRemain);
					
					//更新扣项
					otherDeduction.setOrderNo(customerOrderRemain.getOrderNo());
				} 
				otherDeduction.setStatus(YesNoEnum.YES.getValue());
				otherDeductionService.modifyById(otherDeduction);
			}
			return true;
		} catch (ServiceException e) {
			logger.error("分配扣项失败", e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 更新客余额明细
	 */
	private void updateRemainingDtl(int tempNum,WholesaleCustomerRemainingSum remainingSum, OtherDeduction otherDeduction) throws ServiceException{
		WholesaleCustomerRemainingDtl remainingDtl = new WholesaleCustomerRemainingDtl();
		try {
			remainingDtl.setPosition(LongSequenceGenerator.getTimestamp());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		remainingDtl.setBillNo(otherDeduction.getId().toString());
		remainingDtl.setRefNo(otherDeduction.getId().toString());
		remainingDtl.setBillType(BillTypeEnums.DEDUCTION.getRequestId());
		remainingDtl.setBizType(WholesaleRemainingTypeEnums.DEDUCTION_REBATE.getTypeNo());
		remainingDtl.setMainId(remainingSum.getId());
		remainingDtl.setMoney(otherDeduction.getDeductionAmount());
		remainingDtl.setRemainingAmount(remainingSum.getRemainingAmount());
		remainingDtl.setFrozenAmount(remainingSum.getFrozenCustomerAmount());
		remainingDtl.setBillDate(otherDeduction.getDeductionDate());
		tempNum = remainingDtlService.add(remainingDtl);
		tempNum++;
	}
	
}