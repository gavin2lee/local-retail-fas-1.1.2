package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.AuditStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.enums.SettleTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillInvoice;
import cn.wonhigh.retail.fas.common.model.BillPayment;
import cn.wonhigh.retail.fas.common.model.BillPaymentDtl;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillInvoiceMapper;
import cn.wonhigh.retail.fas.dal.database.BillPaymentDtlMapper;
import cn.wonhigh.retail.fas.dal.database.BillPaymentMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-11-27 10:56:55
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
@Service("billPaymentService")
class BillPaymentServiceImpl extends BaseServiceImpl implements BillPaymentService {
    @Resource
    private BillPaymentMapper billPaymentMapper;

    @Resource
    private BillPaymentDtlService billPaymentDtlService;
    
    @Resource
    private BillPaymentDtlMapper billPaymentDtlMapper;
    
    @Resource
    private CommonUtilService commonUtilService;  
    
    @Resource
    private CompanyService companyService;  
    
    @Resource
    private CurrencyManagementService currencyManagementService;  
    
    @Resource
    private BillInvoiceMapper billInvoiceMapper;
    
    @Resource
    private OperateLogService operateLogService; 
    
    @Override
    public BaseCrudMapper init() {
        return billPaymentMapper;
    }
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			BillPayment obj =  (BillPayment)modelType;
			obj.setId(UUIDGenerator.getUUID());
			obj.setBillNo(commonUtilService.getNewBillNoCompanyNo(obj.getBuyerNo(), FasBillTypeEnums.PC.getRequestId()));
			int iCount = super.add(obj);
			this.updateInovicePaymentInfo(obj.getRefBillNo());
			if(ShardingUtil.isPE()){
				this.updatePayAmount(obj);
			}
			this.insertOperateLog(obj);// 插入日志对象
			return iCount;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int modifyById(ModelType modelType) throws ServiceException {
		try {
			BillPayment obj = (BillPayment) modelType;
			int iCount = super.modifyById(obj);
			obj = super.findById(obj);
			this.updateInovicePaymentInfo(obj.getRefBillNo());
			if(ShardingUtil.isPE()){
				this.updatePayAmount(obj);
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public  int deleteById(Object obj)throws ServiceException{
    	int iCount =0;
    	try {
			BillPayment billPayment = (BillPayment)obj;
			String idList = billPayment.getIdList();
			if(StringUtils.isNotBlank(idList)){
				String idArr[] = idList.split(";");
				for (String str : idArr) {
					billPayment = new BillPayment();
					String id = str.split(",")[0];
					String billNo = str.split(",")[1];
					billPayment.setId(id);
					billPayment = super.findById(billPayment);
					super.deleteById(billPayment);
					this.updateInovicePaymentInfo(billPayment.getRefBillNo());
					if(ShardingUtil.isPE()){
						this.updatePayAmount(billPayment);
					}
					billPaymentDtlMapper.deleteByBillNo(billNo);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("moduleNo", OperateLogEnums.OperateModule.FKD.getModuleNo());
					params.put("dataNo", billNo);
					operateLogService.deleteByDataAndModuleNo(params);
				}
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException();
		}
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public int generateByInvoice(List<BillInvoice> oList,
			String loginName) throws ServiceException {
		try {
			int iCount = 0;
			for (BillInvoice invoice : oList) {
				BillPayment bill = new BillPayment();
				BillPaymentDtl dtl = new BillPaymentDtl();
				String billNo = commonUtilService.getNewBillNoCompanyNo(invoice.getBuyerNo(), FasBillTypeEnums.PC.getRequestId());
				bill.setBillNo(billNo);
				bill.setCreateTime(new Date());
				bill.setCreateUser(loginName);
				bill.setId(UUIDHexGenerator.generate());
				bill.setSalerNo(invoice.getSalerNo());
				bill.setSalerName(invoice.getSalerName());
				bill.setBuyerNo(invoice.getBuyerNo());
				bill.setBuyerName(invoice.getBuyerName());
				bill.setRefBillNo(invoice.getBillNo());
				bill.setAmount(invoice.getAmount().subtract(invoice.getPaymentAmount()));
				bill.setQty(invoice.getQty() - invoice.getPaymentQty());
				bill.setRefQty(invoice.getQty());
				bill.setBillDate(new Date());
				bill.setRefAmount(invoice.getAmount());
				CurrencyManagement currency = currencyManagementService.findDefaultCurrency();
				if(null != currency){
					bill.setCurrency(currency.getCurrencyCode());
				}
				super.add(bill);
				this.updateInovicePaymentInfo(bill.getRefBillNo());
				if(ShardingUtil.isPE()){
					this.updatePayAmount(bill);
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("companyNo", invoice.getBuyerNo());
				List<Company> lstCompany = companyService.findByBiz(null, params);
				if(!CollectionUtils.isEmpty(lstCompany)){
					Company company = lstCompany.get(0);
					dtl.setBankAccount(company.getBankAccount());
				}
				this.insertOperateLog(bill);// 插入日志对象
				dtl.setBillNo(billNo);
				dtl.setPayAmount(bill.getAmount());
				dtl.setPayQty(bill.getQty());
				dtl.setBalanceAmount(bill.getAmount());
				dtl.setPaymentApplication("采购付款");
				dtl.setSettleMethodNo(String.valueOf(SettleTypeEnums.SettleType1.getTypeNo()));
				dtl.setSettleMethodName(SettleTypeEnums.SettleType1.getTypeName());
				billPaymentDtlService.add(dtl); // 插入付款单明细信息
				iCount++;
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public int verify(BillPayment obj) throws ServiceException {
		int totalCount = 0;
		try {
			String billNo = obj.getBillNo();
			String[] arrNo = billNo.split(",");
			for (String str : arrNo) {
				obj.setBillNo(str);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("billNo", str);
				List<BillPayment> lstPay = billPaymentMapper.selectByParams(null, params);
				if(lstPay.size() >0){
					totalCount += billPaymentMapper.verify(obj);
				}
				this.insertOperateLog(obj);// 插入日志对象
			}
			return totalCount;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 更新发票信息
	 * @param invoiceNo
	 */
    private void updateInovicePaymentInfo(String invoiceNo) {
    	if(StringUtils.isNotBlank(invoiceNo)){
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("refBillNo", invoiceNo);
    		List<BillPayment> lstPay =  billPaymentMapper.selectByParams(null, params);
    		if(!CollectionUtils.isEmpty(lstPay)){
        		BigDecimal payAmount = new BigDecimal(0);
        		int payQty = 0;
        		String paymentNo = "";
    			for (BillPayment billPayment : lstPay) {
        			payAmount = payAmount.add(billPayment.getAmount());
        			payQty += billPayment.getQty();
        			paymentNo += billPayment.getBillNo()+",";
        		}
        		if(paymentNo.length() > 0){
        			paymentNo = paymentNo.substring(0,paymentNo.length() - 1);
        		}
        		params.clear();
        		params.put("billNo", invoiceNo);
        		List<BillInvoice> lstInvoice =  billInvoiceMapper.selectByParams(null, params);
        		BigDecimal invoiceAmount =  lstInvoice.get(0).getAmount() == null ? new BigDecimal(0) : lstInvoice.get(0).getAmount();
        		BillInvoice invoice = new BillInvoice();
        		invoice.setBillNo(invoiceNo);
        		invoice.setPaymentAmount(payAmount);
        		invoice.setPaymentQty(payQty);
        		invoice.setPaymentNo(paymentNo);
        		invoice.setStatus(BillStatusEnums.ALL_PAYMENT.getTypeNo());
        		if(payAmount.subtract(invoiceAmount).doubleValue() < 0){
        			invoice.setStatus(BillStatusEnums.PART_PAYMENT.getTypeNo());
        		}
        		billPaymentMapper.updateInvoicePayment(invoice);
    		}else{
        		BillInvoice invoice = new BillInvoice();
        		invoice.setBillNo(invoiceNo);
        		invoice.setPaymentAmount(new BigDecimal(0));
        		invoice.setPaymentQty(0);
        		invoice.setPaymentNo("");
        		invoice.setStatus(BillStatusEnums.CONFIRM.getTypeNo());
        		billPaymentMapper.updateInvoicePayment(invoice);
    		}
    		
    	}
	}
    
	/**
	 * 付款分摊
	 * @param obj
	 */
	private void updatePayAmount(BillPayment obj) {
		String invoiceNo = obj.getRefBillNo();
		if(StringUtils.isBlank(invoiceNo)){
			return ;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", invoiceNo);
		List<BillInvoice> lstInvoice = billInvoiceMapper.selectByParams(null, params);
		if(lstInvoice.size() > 0){
			BillInvoice invoice = lstInvoice.get(0);
			String balanceNo = invoice.getRefBillNo();
			if(StringUtils.isNotBlank(balanceNo)){
				if(invoice.getStatus().intValue() == BillStatusEnums.CONFIRM.getTypeNo().intValue()){// 确认
					this.clearAllPaymentByBalanceNo(balanceNo);
				}else if(invoice.getStatus().intValue() == BillStatusEnums.PART_PAYMENT.getTypeNo().intValue()){// 部分付款
					this.updatePartPayment(invoice);
				}else{// 全部付款
					this.updateAllPaymentByBalanceNo(balanceNo);
				}
			}
			
		}
	}

	/**
	 * 部分付款的发票分摊
	 * @param invoice
	 */
	private void updatePartPayment(BillInvoice invoice) {
		String balanceNo = invoice.getRefBillNo();
		Map<String, Object> params = new HashMap<>();
		params.put("multiBalanceNo", FasUtil.formatInQueryCondition(balanceNo));
		List<PayRelationship> lstShip = billPaymentMapper.selectPayRealtionList(params);
		BigDecimal payAmount = invoice.getPaymentAmount();
		BigDecimal hasPayAmount = new BigDecimal(0);
		BigDecimal lastPayAmount = new BigDecimal(0);
		String lastPayNo = "";
		String multiRelationId = "";
		String multiDeductionId = "";
		String multiAdjustId = "";
		int lastPayType = 0;
		boolean updatePartPayFlag=false;
		int index = 0;
		for (PayRelationship ship : lstShip) {
			index ++;
			hasPayAmount = hasPayAmount.add(ship.getSupplierAmount());
			if(payAmount.subtract(hasPayAmount).doubleValue() <= 0){
				lastPayAmount = ship.getSupplierAmount().subtract(hasPayAmount.subtract(payAmount));
				lastPayType = ship.getBusinessBillType();
				lastPayNo = ship.getId();
				if(lastPayAmount.subtract(ship.getSupplierAmount()).doubleValue()<0){
					updatePartPayFlag = true;
				}
				break;
			}
			if(ship.getBusinessBillType() == 9999){//其他扣项
				multiDeductionId = multiDeductionId.concat(ship.getId()).concat(",");
			}else if(ship.getBusinessBillType() == 9998){// 采购入库调整单
				multiAdjustId = multiAdjustId.concat(ship.getId()).concat(",");
			}else{//到货单
				multiRelationId = multiRelationId.concat(ship.getId()).concat(",");
			}
		}
		if(index == lstShip.size()){// 所有单据都有分摊的情况
			this.updateAllPaymentByBalanceNo(balanceNo);
			if(updatePartPayFlag){
				this.updatePartPaymentById(balanceNo,lastPayType,lastPayNo,lastPayAmount);
			}
		}else{// 部分单据有分摊的情况
			this.updateAllPaymentByMultiId(balanceNo,multiDeductionId,multiRelationId,multiAdjustId);
			if(updatePartPayFlag){
				this.updatePartPaymentById(balanceNo,lastPayType,lastPayNo,lastPayAmount);
			}
		}
	}

	/**
	 * 根据结算单号清除付款
	 * @param params
	 */
	private void clearAllPaymentByBalanceNo(String balanceNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("multiBalanceNo", FasUtil.formatInQueryCondition(balanceNo));
		billPaymentMapper.clearRelationPaymentInfo(params);
		billPaymentMapper.clearDeductionPaymentInfo(params);
		billPaymentMapper.clearAdjustPaymentInfo(params);
		
	}

	/**
	 * 根据结算单号更新付款
	 * @param params
	 */
	private void updateAllPaymentByBalanceNo(String balanceNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("multiBalanceNo", FasUtil.formatInQueryCondition(balanceNo));
		params.put("payStatus", 2);//全部付款
		billPaymentMapper.updateRelationPaymentInfo(params);
		billPaymentMapper.updateDeductionPaymentInfo(params);
		billPaymentMapper.updateAdjustPaymentInfo(params);
	}

	/**
	 * 根据多个ID更新全部付款信息
	 * @param lastPayType
	 * @param lastPayNo
	 * @param lastPayAmount
	 */
	private void updateAllPaymentByMultiId(String balanceNo,String multiDeductionId,
			String multiRelationId, String multiAdjustId) {
		Map<String, Object> params = new HashMap<>();
		params.put("multiBalanceNo", FasUtil.formatInQueryCondition(balanceNo));
		params.put("payStatus", 2);//全部付款
		if(StringUtils.isNotBlank(multiDeductionId)){
			params.put("mulitDeductionId", FasUtil.formatInQueryCondition(multiDeductionId.substring(0,multiDeductionId.length()-1)));
			billPaymentMapper.updateDeductionPaymentInfo(params);
		}
		if(StringUtils.isNotBlank(multiRelationId)){
			params.put("multiRelationId", FasUtil.formatInQueryCondition(multiRelationId.substring(0,multiRelationId.length()-1)));
			billPaymentMapper.updateRelationPaymentInfo(params);
		}
		if(StringUtils.isNotBlank(multiAdjustId)){
			params.put("multiAdjustId", FasUtil.formatInQueryCondition(multiAdjustId.substring(0,multiAdjustId.length()-1)));
			billPaymentMapper.updateAdjustPaymentInfo(params);
		}
	}

	/**
	 * 根据ID更新部分付款信息
	 * @param lastPayType
	 * @param lastPayNo
	 * @param lastPayAmount
	 */
	private void updatePartPaymentById(String balanceNo,int lastPayType, String lastPayNo,
			BigDecimal lastPayAmount) {
		Map<String, Object> params = new HashMap<>();
		params.put("multiBalanceNo", FasUtil.formatInQueryCondition(balanceNo));
		params.put("payStatus", 1);//部分付款
		params.put("id", lastPayNo);
		params.put("payAmount", lastPayAmount);
		if(lastPayType == 9999){//其他扣项
			billPaymentMapper.updateDeductionPaymentInfo(params);
		}else if(lastPayType == 9998){// 采购入库调整单
			billPaymentMapper.updateAdjustPaymentInfo(params);
		}else{// 到货单
			billPaymentMapper.updateRelationPaymentInfo(params);
		}
	}

/*	private void updateStatus(BillPayment obj) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		String invoiceBillNo = obj.getRefBillNo();
		params.put("billNo", invoiceBillNo);
		List<BillInvoice> lstInvoice = billInvoiceMapper.selectByParams(null, params);
		if(lstInvoice.size() > 0){
			BillBalance bill = new BillBalance();
			BillInvoice invoice = lstInvoice.get(0);
			String balanceNo = invoice.getRefBillNo();
			String[] arrBalanceNo = balanceNo.split(",");
			for (String str : arrBalanceNo) {
				bill.setBillNo(str);
				bill.setStatus(obj.getStatus());
				billBalanceMapper.updateBuyBalanceStatus(bill);
				billBalanceMapper.updateSaleBalanceStatus(bill);
				billBalanceMapper.updateDeductionBalanceStatus(bill);
				billBalanceMapper.updateImperfectBalanceStatus(bill);
			}
		}
	}*/
	
	/**
	 * 插入审批日志对象
	 * @param billBalance
	 * @throws ServiceException 
	 */
	private void insertOperateLog(BillPayment obj) throws ServiceException {
		OperateLog operateLog = new OperateLog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setDataNo(obj.getBillNo());
		operateLog.setModuleNo(OperateLogEnums.OperateModule.FKD.getModuleNo());
		if(StringUtils.isNotBlank(obj.getCreateUser())){
			operateLog.setCreateUser(obj.getCreateUser());
			operateLog.setCreateTime(obj.getCreateTime());
			operateLog.setStatus(AuditStatusEnums.CREATE.getTypeNo());
			operateLog.setStatusName(AuditStatusEnums.CREATE.getTypeName());
			operateLog.setOperateStatusName(AuditStatusEnums.CREATE.getTypeName());
		}else{
			operateLog.setCreateUser(obj.getAuditor());
			operateLog.setCreateTime(obj.getAuditTime());
			operateLog.setStatus(obj.getStatus());
			operateLog.setStatusName(AuditStatusEnums.getNameByNo(obj.getStatus()));
			operateLog.setOperateStatusName(AuditStatusEnums.getOperateNameByNo(obj.getStatus()));
		}
		operateLogService.add(operateLog);
	}

	@Override
	public List<BillPayment> selectFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return billPaymentMapper.selectFooter(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}
}