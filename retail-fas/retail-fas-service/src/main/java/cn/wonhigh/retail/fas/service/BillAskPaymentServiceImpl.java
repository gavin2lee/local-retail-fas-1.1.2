package cn.wonhigh.retail.fas.service;

import java.util.Calendar;
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
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.enums.SettleTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillAskPayment;
import cn.wonhigh.retail.fas.common.model.BillAskPaymentDtl;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillAskPaymentDtlMapper;
import cn.wonhigh.retail.fas.dal.database.BillAskPaymentMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
@Service("billAskPaymentService")
class BillAskPaymentServiceImpl extends BaseCrudServiceImpl implements BillAskPaymentService {
    @Resource
    private BillAskPaymentMapper billAskPaymentMapper;

    @Resource
    private BillAskPaymentDtlService billAskPaymentDtlService;
    
    @Resource
    private BillAskPaymentDtlMapper billAskPaymentDtlMapper;
    
    @Resource
    private CommonUtilService commonUtilService;    
    
    @Resource
    private OperateLogService operateLogService; 
    
    @Resource
    private SupplierService supplierService; 
   
    @Resource
    private CompanyService companyService; 
    
    @Resource
    private CurrencyManagementService currencyManagementService; 
    
    @Override
    public BaseCrudMapper init() {
        return billAskPaymentMapper;
    }
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			BillAskPayment obj =  (BillAskPayment)modelType;
			obj.setId(UUIDHexGenerator.generate());
			obj.setBillNo(commonUtilService.getNewBillNoCompanyNo(obj.getBuyerNo(), FasBillTypeEnums.PR.getRequestId()));
			int iCount = super.add(obj);
			this.insertOperateLog(obj);// 插入日志对象
			if(StringUtils.isNotBlank(obj.getBalanceNo())){
				billAskPaymentMapper.updateBalanceAskPaymentNo(obj);
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
			BillAskPayment billAskPayment = (BillAskPayment)obj;
			String idList = billAskPayment.getIdList();
			if(StringUtils.isNotBlank(idList)){
				String idArr[] = idList.split(";");
				for (String str : idArr) {
					billAskPayment = new BillAskPayment();
					String[] arr = str.split(",");
					String id = arr[0];
					String billNo = arr[1];
					String balanceType = arr[2];
					billAskPayment.setId(id);
					billAskPayment.setBillNo(billNo);
					billAskPaymentMapper.clearBalanceAskPaymentNo(billNo, Integer.parseInt(balanceType));
					super.deleteById(billAskPayment);
					billAskPaymentDtlMapper.deleteByBillNo(billNo);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("moduleNo", OperateLogEnums.OperateModule.QKD.getModuleNo());
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
	public int generateBillBybalance(List<BillBalance> list, String loginName) throws ServiceException {
		try {
			int iCount = 0;
			for (BillBalance billBalance : list) {
				BillAskPayment billAskPayment = new BillAskPayment();
				BillAskPaymentDtl billAskPaymentDtl = new BillAskPaymentDtl();
				String billNo = commonUtilService.getNewBillNoCompanyNo(billBalance.getBuyerNo(), FasBillTypeEnums.PR.getRequestId());
				billAskPayment.setBillNo(billNo);
				billAskPayment.setBalanceType(billBalance.getBalanceType());
				billAskPayment.setCreateTime(new Date());
				billAskPayment.setCreateUser(loginName);
				billAskPayment.setStatus(BillStatusEnums.CREATE.getTypeNo());
				billAskPayment.setId(UUIDHexGenerator.generate());
				billAskPayment.setSalerNo(billBalance.getSalerNo());
				billAskPayment.setSalerName(billBalance.getSalerName());
				billAskPayment.setBuyerNo(billBalance.getBuyerNo());
				billAskPayment.setBuyerName(billBalance.getBuyerName());
				billAskPayment.setBalanceNo(billBalance.getBillNo());
				billAskPayment.setAllQty(billBalance.getBalanceQty());
				billAskPayment.setAllAmount(billBalance.getBalanceAmount());
				billAskPayment.setBillDate(new Date());
				CurrencyManagement currency = currencyManagementService.findDefaultCurrency();
				if(null != currency){
					billAskPayment.setCurrencyNo(currency.getCurrencyCode());
				}
				billAskPayment.setIsGenerate(YesNoEnum.YES.getValue());
				billAskPayment.setBalanceAmount(billBalance.getBalanceAmount());
				billAskPayment.setRemark(billBalance.getRemark());
				if(StringUtils.isBlank(billAskPayment.getRemark())){
					Calendar cal = Calendar.getInstance();
					cal.setTime(billBalance.getBalanceEndDate());
					int month = cal.get(Calendar.MONTH) + 1;
					billAskPayment.setRemark(month+"月货款");
				}
				billAskPaymentMapper.insertSelective(billAskPayment); // 插入请款单信息
				this.insertOperateLog(billAskPayment);// 插入日志对象
				if(StringUtils.isNotBlank(billAskPayment.getBalanceNo())){
					billAskPaymentMapper.updateBalanceAskPaymentNo(billAskPayment);
				}
				Map<String, Object> params = new HashMap<String, Object>();
				if(BalanceTypeEnums.AREA_AMONG.getTypeNo() == billBalance.getBalanceType() || BalanceTypeEnums.HQ_OTHER_STOCK_OUT.getTypeNo() == billBalance.getBalanceType()|| BalanceTypeEnums.AREA_WHOLESALE.getTypeNo() == billBalance.getBalanceType()){
					params.put("companyNo", billBalance.getSalerNo());
					List<Company> lstCompany = companyService.findByBiz(null, params);
					if(!CollectionUtils.isEmpty(lstCompany)){
						Company company = lstCompany.get(0);
						billAskPaymentDtl.setOtherBank(company.getBankName());
						billAskPaymentDtl.setOtherBankAccount(company.getBankAccount());
					}
				}else{
					params.put("supplierNo", billBalance.getSalerNo());
					List<Supplier> lstSupplier = supplierService.findByBiz(null, params);
					if(!CollectionUtils.isEmpty(lstSupplier)){
						Supplier supplier = lstSupplier.get(0);
						billAskPaymentDtl.setOtherBank(supplier.getBankName());
						billAskPaymentDtl.setOtherBankAccount(supplier.getBankAccount());
					}
				}
				billAskPaymentDtl.setBillNo(billNo);
				billAskPaymentDtl.setSettleMethodNo(String.valueOf(SettleTypeEnums.SettleType1.getTypeNo()));
				billAskPaymentDtl.setSettleMethodName(SettleTypeEnums.SettleType1.getTypeName());
				billAskPaymentDtl.setAmount(billBalance.getBalanceAmount());
				billAskPaymentDtl.setQty(billBalance.getBalanceQty());
				billAskPaymentDtl.setBrandNo(billBalance.getBrandUnitNo());
				billAskPaymentDtl.setBrandName(billBalance.getBrandUnitName());
				billAskPaymentDtlService.add(billAskPaymentDtl); // 插入请款单明细信息
				iCount++;
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException();
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public int verify(BillAskPayment obj) throws ServiceException {
		int totalCount = 0;
		try {
			String billNo = obj.getBillNo();
			String[] arrNo = billNo.split(",");
			for (String str : arrNo) {
				obj.setBillNo(str);
				totalCount += billAskPaymentMapper.verify(obj);
				this.insertOperateLog(obj);// 插入日志对象
			}
			return totalCount;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	/**
	 * 插入审批日志对象
	 * @param billBalance
	 * @throws ServiceException 
	 */
	private void insertOperateLog(BillAskPayment obj) throws ServiceException {
		OperateLog operateLog = new OperateLog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setDataNo(obj.getBillNo());
		operateLog.setModuleNo(OperateLogEnums.OperateModule.QKD.getModuleNo());
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
	public List<BillAskPayment> selectFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return billAskPaymentMapper.selectFooter(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public BillAskPayment addMainForm(BillAskPayment bill) throws ServiceException {
		bill.setId(UUIDHexGenerator.generate());
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(bill.getBuyerNo(), null,FasBillTypeEnums.PR.getRequestId()));
		bill.setStatus(new Integer(0));
		bill.setBalanceType(BalanceTypeEnums.PE_SUPPLIER.getTypeNo());
		int i=billAskPaymentMapper.insert(bill);
		BillAskPayment askPaymentBill=null;
		Map<String, Object> params=new HashMap<>();
		if(i>0){
			params.put("billNo", bill.getBillNo());
			askPaymentBill=(BillAskPayment) billAskPaymentMapper.selectByParams(null, params).get(0);
			billAskPaymentMapper.updateBalanceAskPaymentNo(bill);//回写请款单号到结算单
			
		}
		return askPaymentBill;
	}
	
	
}