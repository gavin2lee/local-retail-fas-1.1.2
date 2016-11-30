package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.PaidTypeEnums;
import cn.wonhigh.retail.fas.common.enums.WholesaleRemainingTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillPrePaymentNt;
import cn.wonhigh.retail.fas.common.model.CustomerOrderRemain;
import cn.wonhigh.retail.fas.common.model.CustomerReceRel;
import cn.wonhigh.retail.fas.common.model.GroupSaleApplyInvoiceRel;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceApplyService;
import cn.wonhigh.retail.fas.service.BillPrePaymentNtService;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.CustomerOrderRemainService;
import cn.wonhigh.retail.fas.service.CustomerReceRelService;
import cn.wonhigh.retail.fas.service.InvoiceInfoService;
import cn.wonhigh.retail.fas.service.WholesaleCustomerRemainingDtlService;
import cn.wonhigh.retail.fas.service.WholesaleCustomerRemainingSumService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.LongSequenceGenerator;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-22 12:14:38
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
@Service("billPrePaymentNtManager")
class BillPrePaymentNtManagerImpl extends BaseCrudManagerImpl implements BillPrePaymentNtManager,WholesaleRemaingManager {
	
	private Logger logger = Logger.getLogger(BillPrePaymentNtManagerImpl.class);
	
    @Resource
    private BillPrePaymentNtService billPrePaymentNtService;
    
    @Resource
    private BillBalanceInvoiceApplyService billBalanceInvoiceApplyService;
    
    @Resource
    private CommonUtilService commonUtilService; 
    
    @Resource
    private GroupSaleApplyInvoiceRelManager groupSaleApplyInvoiceRelManager;
    
    @Resource
    private CustomerReceRelService customerReceRelService;
    
    @Resource
    private WholesaleCustomerRemainingSumService customerRemainingSumService;
    
    @Resource
    private WholesaleCustomerRemainingDtlService customerRemainingDtlService;
    
    @Resource
    private InvoiceInfoService invoiceInfoService;
    
    @Resource
    private CompanyService companyService;
    
    @Resource
	private CustomerOrderRemainService customerOrderRemainService;
    
    @Override
    public BaseCrudService init() {
        return billPrePaymentNtService;
    }

	/**
     * 保存方法团购预收款单，处理发票号使用标识回写问题
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
   	public int addGroupPrePayment(BillPrePaymentNt billPrePaymentNt) throws ManagerException {
    	int count = 0;
		try {
			String billNo = commonUtilService.getNewBillNoCompanyNo(billPrePaymentNt.getCompanyNo(),null,"PA");
			billPrePaymentNt.setBillNo(billNo);
			count = billPrePaymentNtService.addGroupPrePayment(billPrePaymentNt);
			if(count > 0 && StringUtils.isNotBlank(billPrePaymentNt.getInvoiceNo())){//回写发票号的使用标识
				Map<String,Object> params = new HashMap<String ,Object>();
				params.put("useFlag", 1);//使用标识：1= 已使用,0 = 未使用
				params.put("billNo", billPrePaymentNt.getInvoiceNo());
				billBalanceInvoiceApplyService.updateUseFlagByBillNo(params);
				
				//保存团购订单号及开票申请的关联信息
				GroupSaleApplyInvoiceRel dtl = new GroupSaleApplyInvoiceRel();
				dtl.setCreateTime(billPrePaymentNt.getCreateTime());
				dtl.setCreateUser(billPrePaymentNt.getCreateUser());
				dtl.setInvoiceApplyDate(billPrePaymentNt.getInvoiceDate());
				dtl.setInvoiceApplyNo(billPrePaymentNt.getInvoiceNo());
				dtl.setOrderNo(billPrePaymentNt.getSaleOrderNo());
				groupSaleApplyInvoiceRelManager.add(dtl);
			}
		} catch (ServiceException e) {
			logger.debug(e.getMessage(), e);
		}
    	return count;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public <ModelType> int save(Map<CommonOperatorEnum, List<ModelType>> params) throws ManagerException {
		try {
			int count = 0;
			for (Entry<CommonOperatorEnum, List<ModelType>> param : params.entrySet()) {
				if (param.getKey().equals(CommonOperatorEnum.DELETED)) {
					List<ModelType> list = params.get(CommonOperatorEnum.DELETED);
					if (null != list && list.size() > 0) {
						Map<String,Object> mapParam = null;
						for (ModelType modelType : list) {
							BillPrePaymentNt billPrePaymentNt = (BillPrePaymentNt) billPrePaymentNtService.findById(modelType);
							count += this.billPrePaymentNtService.deleteById(modelType);
							if(count > 0 && StringUtils.isNotBlank(billPrePaymentNt.getInvoiceNo())){//回写发票号的使用标识
								mapParam = new HashMap<String ,Object>();
								mapParam.put("useFlag", 0);//使用标识：1= 已使用,0 = 未使用
								mapParam.put("billNo", billPrePaymentNt.getInvoiceNo());
								billBalanceInvoiceApplyService.updateUseFlagByBillNo(mapParam);
								
								//删除团购订单号及开票申请的关联信息
								GroupSaleApplyInvoiceRel rel = new GroupSaleApplyInvoiceRel();
								rel.setInvoiceApplyNo(billPrePaymentNt.getInvoiceNo());
								groupSaleApplyInvoiceRelManager.deleteById(rel);
							}
						}
					}
				}
				if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
					List<ModelType> list = params.get(CommonOperatorEnum.UPDATED);
					if (null != list && list.size() > 0) {
						for (ModelType modelType : list) {
							count += this.billPrePaymentNtService.modifyById(modelType);
						}
					}
				}
				if (param.getKey().equals(CommonOperatorEnum.INSERTED)) {
					List<ModelType> list = params.get(CommonOperatorEnum.INSERTED);
					if (null != list && list.size() > 0) {
						for (ModelType modelType : list) {
							this.billPrePaymentNtService.add(modelType);
						}
						count += list.size();
					}
				}
			}
			return count;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int doAudit(List<BillPrePaymentNt> oList) throws ManagerException {
		try {
			int num = 1;
			for(BillPrePaymentNt model : oList) {
				billPrePaymentNtService.modifyById(model);
				updateMargin(model);
				//如果是保证金收款,客户余额没有初始化则初始化客户余额
				if(model.getPaidType() == PaidTypeEnums.MARGIN.getTypeNo()){					
					initCustomerRemaining(model);
				}
				//如果是订单收款和客户收款
				if(model.getPaidType() == PaidTypeEnums.ORDER.getTypeNo() || model.getPaidType() == PaidTypeEnums.OTHER.getTypeNo()){
					num = saveWholesaleCustomerRemainingInfo(model, num);
					++num;
				}
				if (StringUtils.isNotEmpty(model.getSaleOrderNo())) {
					updateOrderRemainAmount(model);
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return 1;
	}
    
	private void updateMargin(BillPrePaymentNt obj)throws ServiceException{
		if(obj.getPaidType() == PaidTypeEnums.MARGIN.getTypeNo()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("customerNo", obj.getCustomerNo());
			params.put("companyNo", obj.getCompanyNo());
			List<CustomerReceRel>  lstRel = customerReceRelService.findByBiz(null, params);
			if(lstRel.size() > 0){
				params.put("auditStatus", 1);
				params.put("paidType", 1);
				List<BillPrePaymentNt>  lstPay = billPrePaymentNtService.findByBiz(null, params);
				BigDecimal amount = new BigDecimal(0);
				for (BillPrePaymentNt nt : lstPay) {
					amount = amount.add(nt.getPaidAmount());
				}
				lstRel.get(0).setMarginRemainderAmount(amount);
				customerReceRelService.modifyById(lstRel.get(0));	
			}
		}
	}
	
//	@Override
//	public void saveWholesaleCustomerRemainingInfo(BillPrePaymentNt model)throws ManagerException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("companyNo", model.getCompanyNo());
//		params.put("customerNo", model.getCustomerNo());
//		BigDecimal remainingAmount = new BigDecimal(0);
//		WholesaleCustomerRemainingSum remainingSum = new WholesaleCustomerRemainingSum();
//		try{
//			List<WholesaleCustomerRemainingSum> sumList = customerRemainingSumService.findByBiz(null, params);
//			if(sumList != null && sumList.size() > 0) {
//				remainingSum = sumList.get(0);
//				if(model.getAuditStatus() == 1) { //审核
//					remainingAmount = remainingSum.getRemainingAmount().add(model.getPaidAmount());
//				}else {
//					remainingAmount = remainingSum.getRemainingAmount().subtract(model.getPaidAmount());
//				}
//				remainingSum.setRemainingAmount(remainingAmount);
//				remainingSum.setUpdateTime(new Date());
//				customerRemainingSumService.modifyById(remainingSum);
//				//新增明细
//				saveWholesaleCustomerDtl(remainingSum, model);
//			}else {	//初始化客户余额
//				remainingSum.setCompanyNo(model.getCompanyNo());
//				remainingSum.setCompanyName(model.getCompanyName());
//				remainingSum.setCustomerNo(model.getCustomerNo());
//				remainingSum.setCustomerName(model.getCustomerName());
//				remainingSum.setCreateTime(new Date());
//				params.clear();
//				params.put("companyNo", model.getCompanyNo());
//				params.put("buyerNo", model.getCustomerNo());
//				WholesaleCustomerRemainingSum paidRemaining = customerRemainingSumService.selectCalcPaidAmountByParams(params);
//				WholesaleCustomerRemainingSum sendRemaining = customerRemainingSumService.selectCalcSendAmountByParams(params);
//				remainingAmount = paidRemaining.getPaidAmount().subtract(sendRemaining.getSendAmount());
//				remainingSum.setRemainingAmount(remainingAmount);
//				customerRemainingSumService.add(remainingSum);
//				
//				String remark = model.getRemark();
//				BigDecimal paidAmount = model.getPaidAmount();
//				//初始化明细
//				model.setRemark("客户余额数据初始化!");
//				model.setPaidAmount(new BigDecimal(0));
//				if(model.getAuditStatus() == 1) { //审核
//					remainingSum.setRemainingAmount(remainingAmount.subtract(paidAmount));
//				}else {
//					remainingSum.setRemainingAmount(remainingAmount.add(paidAmount));
//				}
//				saveWholesaleCustomerDtl(remainingSum, model);
//				//新增明细
//				model.setRemark(remark);
//				model.setPaidAmount(paidAmount);
//				remainingSum.setRemainingAmount(remainingAmount);
//				saveWholesaleCustomerDtl(remainingSum, model);
//			}
//		}catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//	}
	
//	private void saveWholesaleCustomerDtl(WholesaleCustomerRemainingSum remainingSum, BillPrePaymentNt model)throws ServiceException {
//		try {
//			BigDecimal money = null;
//			StringBuffer remarkBuffer = new StringBuffer();
//			WholesaleCustomerRemainingDtl remainingDtl = new WholesaleCustomerRemainingDtl();
//			remainingDtl.setType(model.getPaidType());
//			remainingDtl.setMainId(remainingSum.getId());
//			remainingDtl.setPrePaymentId(Integer.valueOf(model.getId()));
//			remainingDtl.setRemainingAmount(remainingSum.getRemainingAmount());
//			remainingDtl.setRefNo(model.getBillNo());
//			if(model.getAuditStatus() == 1) { //审核
//				money = model.getPaidAmount();
//			}else {
//				money = new BigDecimal(0).subtract(model.getPaidAmount());
//				remarkBuffer.append(" 收款单反审核!");
//			}
//			if(model.getRemark() == null) {
//				remarkBuffer.append(" 相关单号：").append(model.getBillNo()).append(" ");
//			}
//			remainingDtl.setCreateTime(new Date());
//			remarkBuffer.append(model.getRemark());
//			remainingDtl.setRemark(remarkBuffer.toString());
//			remainingDtl.setMoney(money);
//			
//			//设置序号
//			remainingDtl = customerRemainingDtlService.setDtlPosition(remainingDtl);
//			customerRemainingDtlService.add(remainingDtl);
//		} catch (ServiceException e) {
//			throw new ServiceException(e.getMessage(), e);
//		}
//	}
	
	@Override
	public BillPrePaymentNt calcPrePaymentTotal(Map<String, Object> params) throws ManagerException {
		try {
			return billPrePaymentNtService.calcPrePaymentTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public <ModelType> int saveWholesaleCustomerRemainingInfo(ModelType modelType, int num)
			throws ManagerException, ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		modelType = billPrePaymentNtService.findById(modelType);
		BillPrePaymentNt model = (BillPrePaymentNt)modelType;
//		params.put("companyNo", model.getCompanyNo());
		params.put("customerNo", model.getCustomerNo());
		BigDecimal remainingAmount = new BigDecimal(0);
		WholesaleCustomerRemainingSum remainingSum = new WholesaleCustomerRemainingSum();
		try{
			List<WholesaleCustomerRemainingSum> sumList = customerRemainingSumService.findByBiz(null, params);
			if(sumList != null && sumList.size() > 0) {
				remainingSum = sumList.get(0);
				if(model.getAuditStatus() == 1) { //审核
					params.put("remainingAmount", model.getPaidAmount());
				}else {
					params.put("remainingAmount", BigDecimalUtil.subtract(BigDecimal.ZERO,model.getPaidAmount()));
				}
				customerRemainingSumService.updateByCustomerNo(params);
				sumList = customerRemainingSumService.findByBiz(null, params);
				remainingSum = sumList.get(0);
				//新增明细
				saveWholesaleCustomerDtl(remainingSum, model, num);
			}else {	//初始化客户余额
				remainingSum.setCompanyNo(model.getCompanyNo());
				remainingSum.setCompanyName(model.getCompanyName());
				remainingSum.setCustomerNo(model.getCustomerNo());
				remainingSum.setCustomerName(model.getCustomerName());
				remainingSum.setCreateTime(new Date());
				params.clear();
				params.put("companyNo", model.getCompanyNo());
				params.put("buyerNo", model.getCustomerNo());
				BigDecimal paidRemainingV = BigDecimal.ZERO;
				BigDecimal sendRemainingV = BigDecimal.ZERO;
				
				WholesaleCustomerRemainingSum paidRemaining = customerRemainingSumService.selectCalcPaidAmountByParams(params);
				if( paidRemaining != null && paidRemaining.getPaidAmount() != null )
					paidRemainingV = paidRemaining.getPaidAmount();
				
				WholesaleCustomerRemainingSum sendRemaining = customerRemainingSumService.selectCalcSendAmountByParams(params);
				if( sendRemaining != null && sendRemaining.getSendAmount() != null )
					sendRemainingV = paidRemaining.getPaidAmount();
				
				remainingAmount = paidRemainingV.subtract(sendRemainingV);
				BigDecimal paidAmount = model.getPaidAmount();
				if(model.getAuditStatus() == 1) { //审核
					remainingSum.setRemainingAmount(remainingAmount.add(paidAmount));
				}else {
					remainingSum.setRemainingAmount(remainingAmount.subtract(paidAmount));
				}
				customerRemainingSumService.add(remainingSum);
				//获取ID
				params.clear();
				params.put("companyNo", model.getCompanyNo());
				params.put("customerNo", model.getCustomerNo());
				List<WholesaleCustomerRemainingSum> remainList = customerRemainingSumService.findByBiz(null, params);
				WholesaleCustomerRemainingSum remainingSumNew = remainList.get(0);
				remainingSum.setId(remainingSumNew.getId());
				String remark = model.getRemark();
				
				//初始化明细
				model.setRemark("客户余额数据初始化!");
				model.setPaidAmount(new BigDecimal(0));
				remainingSum.setRemainingAmount(remainingAmount);
				saveWholesaleCustomerDtl(remainingSum, model, num);
				
				//新增明细 
				model.setRemark(remark);
				model.setPaidAmount(paidAmount);
				if(model.getAuditStatus() == 1) { //审核
					remainingSum.setRemainingAmount(remainingAmount.add(paidAmount));
				}else {
					remainingSum.setRemainingAmount(remainingAmount.subtract(paidAmount));
				}
				saveWholesaleCustomerDtl(remainingSum, model, ++num);
			}
			return num;
		}catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public <ModelType> void saveWholesaleCustomerDtl(
			WholesaleCustomerRemainingSum remainingSum, ModelType modelType, int num)
			throws ServiceException {
		BillPrePaymentNt model = (BillPrePaymentNt)modelType;
		try {
			BigDecimal money = null;
			StringBuffer remarkBuffer = new StringBuffer();
			WholesaleCustomerRemainingDtl remainingDtl = new WholesaleCustomerRemainingDtl();
			remainingDtl.setType(model.getPaidType());
			remainingDtl.setBillNo(model.getBillNo());
			remainingDtl.setBillType(BillTypeEnums.PRE_PAY_AMOUNT.getRequestId());
			remainingDtl.setBizType(WholesaleRemainingTypeEnums.OTHER_PAYMENT.getTypeNo());
			remainingDtl.setType(model.getPaidType());
			remainingDtl.setMainId(remainingSum.getId());
			remainingDtl.setPrePaymentId(Integer.valueOf(model.getId()));
			remainingDtl.setRemainingAmount(remainingSum.getRemainingAmount());
			remainingDtl.setRefNo(model.getBillNo());
			remainingDtl.setBillDate(model.getPaidDate());
			if(model.getAuditStatus() == 1) { //审核
				money = model.getPaidAmount();
			}else {
				money = new BigDecimal(0).subtract(model.getPaidAmount());
				remarkBuffer.append(" 收款单反审核!");
			}
			if(StringUtils.isBlank(model.getRemark())) {
				remarkBuffer.append(" 相关单号：").append(model.getBillNo()).append(" ");
			}
			remarkBuffer.append(model.getRemark()==null ? "" : model.getRemark());
			remainingDtl.setRemark(remarkBuffer.toString());
			remainingDtl.setMoney(money); 
			
			//设置序号
//			remainingDtl = customerRemainingDtlService.setDtlPosition(remainingDtl);
//			DecimalFormat df = new DecimalFormat("0000");
			try {
				remainingDtl.setPosition(LongSequenceGenerator.getTimestamp());
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
			customerRemainingDtlService.add(remainingDtl);
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 修改订单余额
	 * @param model
	 * @throws ManagerException
	 */
	private void updateOrderRemainAmount(BillPrePaymentNt model) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isEmpty(model.getSaleOrderNo())) {
			return;
		}
		params.put("orderNo", model.getSaleOrderNo());
		CustomerOrderRemain customerOrderRemain = null;
		try {
			List<CustomerOrderRemain> orderList = customerOrderRemainService.findByBiz(null, params);
			if (null != orderList && orderList.size() > 0) {
				customerOrderRemain = orderList.get(0);
				if(model.getAuditStatus() == 1) { //审核
					customerOrderRemain.setRemainingAmount(customerOrderRemain.getRemainingAmount().add(model.getPaidAmount()));
					customerOrderRemain.setReceiveAmount(customerOrderRemain.getReceiveAmount().add(model.getPaidAmount()));
				} else {
					customerOrderRemain.setRemainingAmount(customerOrderRemain.getRemainingAmount().subtract(model.getPaidAmount()));
					customerOrderRemain.setReceiveAmount(customerOrderRemain.getReceiveAmount().subtract(model.getPaidAmount()));
				}
				customerOrderRemainService.modifyById(customerOrderRemain);
			}
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		
	}
	
	/**
	 * 初始化客户余额
	 * @param modelType
	 * @throws ManagerException
	 * @throws ServiceException
	 */
	private void initCustomerRemaining(BillPrePaymentNt modelType) throws ManagerException, ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		BillPrePaymentNt model = billPrePaymentNtService.findById(modelType);
		params.put("customerNo", model.getCustomerNo());
		BigDecimal remainingAmount = BigDecimal.ZERO;
		WholesaleCustomerRemainingSum remainingSum = new WholesaleCustomerRemainingSum();
		try{
			List<WholesaleCustomerRemainingSum> sumList = customerRemainingSumService.findByBiz(null, params);
			if(null == sumList || sumList.size() <= 0) {            
				remainingSum.setCompanyNo(model.getCompanyNo());
				remainingSum.setCompanyName(model.getCompanyName());
				remainingSum.setCustomerNo(model.getCustomerNo());
				remainingSum.setCustomerName(model.getCustomerName());
				remainingSum.setCreateTime(new Date());
				params.clear();
				params.put("companyNo", model.getCompanyNo());
				params.put("buyerNo", model.getCustomerNo());
				BigDecimal paidRemainingV = BigDecimal.ZERO;
				BigDecimal sendRemainingV = BigDecimal.ZERO;
				
				WholesaleCustomerRemainingSum paidRemaining = customerRemainingSumService.selectCalcPaidAmountByParams(params);
				if( paidRemaining != null && paidRemaining.getPaidAmount() != null )
					paidRemainingV = paidRemaining.getPaidAmount();
				
				WholesaleCustomerRemainingSum sendRemaining = customerRemainingSumService.selectCalcSendAmountByParams(params);
				if( sendRemaining != null && sendRemaining.getSendAmount() != null )
					sendRemainingV = paidRemaining.getPaidAmount();
				
				remainingAmount = paidRemainingV.subtract(sendRemainingV);
				
				customerRemainingSumService.add(remainingSum);
				//获取ID
				params.clear();
				params.put("companyNo", model.getCompanyNo());
				params.put("customerNo", model.getCustomerNo());
				List<WholesaleCustomerRemainingSum> remainList = customerRemainingSumService.findByBiz(null, params);
				WholesaleCustomerRemainingSum remainingSumNew = remainList.get(0);
				remainingSum.setId(remainingSumNew.getId());
				
				//初始化明细
				model.setRemark("客户余额数据初始化!");
				model.setPaidAmount(new BigDecimal(0));
				remainingSum.setRemainingAmount(remainingAmount);
				saveWholesaleCustomerDtl(remainingSum, model, 0);
			}
		}catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}