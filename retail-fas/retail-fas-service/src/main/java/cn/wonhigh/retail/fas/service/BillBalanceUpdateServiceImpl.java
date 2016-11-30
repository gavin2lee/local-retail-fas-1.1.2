package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceQueryMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceUpdateMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
@Service("billBalanceUpdateService")
class BillBalanceUpdateServiceImpl extends BaseCrudServiceImpl implements BillBalanceUpdateService {

    @Resource
    private CommonUtilService commonUtilService;
    
    @Resource
    private OperateLogService operateLogService; 
    
    @Resource
    private BillBalanceMapper billBalanceMapper;
    
    @Resource
    private BillBalanceUpdateMapper billBalanceUpdateMapper;

    @Resource
    private BillBalanceQueryMapper billBalanceQueryMapper;
    
    @Override
    public BaseCrudMapper init() {
        return billBalanceMapper;
    }
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public BillBalance add(BillBalance obj, Map<String, Object> params)
			throws ServiceException {
		List<BillBalance> lstBalance = billBalanceQueryMapper.queryBalance(params);
		try {
			if(lstBalance.size() >0){
				for (BillBalance bill : lstBalance) {
					// 初始化结算单信息
					this.initBill(bill,obj);
					// 保存
					billBalanceMapper.insertSelective(bill);
					// 反写明细结算单号
					billBalanceUpdateMapper.updateDetailBalanceNo(bill);
					// 反写其他扣项明细结算单号
					billBalanceUpdateMapper.updateDeductionBalanceNo(bill);
					// 记录审批日志
					this.insertOperateLog(bill);
				}
				return billBalanceMapper.selectByPrimaryKey(lstBalance.get(0));
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public BillBalance update(BillBalance obj) throws ServiceException {
		try {
			billBalanceMapper.updateByPrimaryKeySelective(obj);
			return billBalanceMapper.selectByPrimaryKey(obj);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public Integer delete(BillBalance obj) throws ServiceException {
		try {
			int iCount = 0;
			String idList = obj.getId();
			if(StringUtils.isNotBlank(idList)){
				String idArr[] = idList.split(";");
				for (String str : idArr) {
					BillBalance billBalance = new BillBalance();
					String id = str.split(",")[0];
					String billNo = str.split(",")[1];
					billBalance.setId(id);
					billBalance.setBillNo(billNo);
					billBalance.setTableName(obj.getTableName());
					billBalanceMapper.deleteByPrimarayKeyForModel(billBalance);// 删除结算单
					billBalanceUpdateMapper.clearDetailBalanceNo(billBalance);// 清除明细结算单号
					billBalanceUpdateMapper.clearDeductionBalanceNo(billNo);// 清除其他扣项明细结算单号
					operateLogService.deleteByDataAndModuleNo(billNo, String.valueOf(OperateLogEnums.OperateModule.JSD.getModuleNo()));// 审批日志
					iCount++;
				}
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public BillBalance createBalance(BillBalance obj, List<Object> lstItem) throws ServiceException {
		try {
			Integer outQty = new Integer(0);
			Integer entryQty = new Integer(0);
			Integer returnQty = new Integer(0);
			BigDecimal outAmount = new BigDecimal(0);
			BigDecimal entryAmount = new BigDecimal(0);
			BigDecimal returnAmount = new BigDecimal(0);
			obj.setId(UUIDHexGenerator.generate());
			this.setBillNo(obj);
			for (Object item : lstItem) {
				BalanceDetailDto bill = (BalanceDetailDto) item;
				bill.setBalanceNo(obj.getBillNo());
				bill.setBalanceStatus(BalanceStatusEnums.NO_CONFIRM.getTypeNo());
				bill.setTableName(obj.getTableName());
				billBalanceUpdateMapper.updateDetailBalanceById(bill);
				if(bill.getBillType().intValue() == BillTypeEnums.RETURNOWN.getRequestId().intValue()){
					returnAmount = returnAmount.add(bill.getCost().multiply(new BigDecimal(bill.getSendQty())));
					returnQty += bill.getSendQty();
				}else{
					outAmount = outAmount.add(bill.getCost().multiply(new BigDecimal(bill.getSendQty())));
					entryAmount = entryAmount.add(bill.getCost().multiply(new BigDecimal(bill.getReceiveQty())));
					outQty += bill.getSendQty();
					entryQty += bill.getReceiveQty();
				}
			}
			obj.setOutAmount(outAmount);
			obj.setEntryAmount(entryAmount);
			obj.setReturnAmount(returnAmount);
			obj.setOutQty(outQty);
			obj.setEntryQty(entryQty);
			obj.setReturnQty(returnQty);
			obj.setBalanceQty(outQty + returnQty);
			obj.setBalanceAmount(outAmount.add(returnAmount));
			billBalanceMapper.insertSelective(obj);
			this.insertOperateLog(obj);	// 记录审批日志
			return billBalanceMapper.selectByPrimaryKey(obj);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem)
			throws ServiceException {
		try {
			try {
				Integer deductionQty = new Integer(0);
				BigDecimal deductionAmount = new BigDecimal(0);
				String billNo = obj.getBillNo();
				billBalanceUpdateMapper.clearDeductionBalanceNo(billNo);
				if(!CollectionUtils.isEmpty(lstItem)){
					for (Object object : lstItem) {
						OtherDeduction deduction = (OtherDeduction)object;
						deduction.setBalanceNo(billNo);
						deduction.setBalanceStatus(BalanceStatusEnums.NO_CONFIRM.getTypeNo());
						billBalanceUpdateMapper.updateDeductionBalanceById(deduction);
						deductionAmount = deductionAmount.add(deduction.getDeductionAmount());
						deductionQty += deduction.getDeductionQty();
					}
				}
				obj.setDeductionQty(deductionQty);
				obj.setDeductionAmount(deductionAmount);
				obj.setBalanceQty(obj.getOutQty() + obj.getReturnQty() - obj.getDeductionQty());
				obj.setBalanceAmount(obj.getOutAmount().add(obj.getReturnAmount()).subtract(deductionAmount));
				billBalanceMapper.updateByPrimaryKeySelective(obj);
				return obj;
			} catch (Exception e) {
				throw new ServiceException("",e);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	/**
	 * 初始化结算单信息
	 * @param bill
	 * @param obj
	 * @throws ServiceException
	 */
	private void initBill(BillBalance bill, BillBalance obj) throws ServiceException {
		bill.setId(UUIDHexGenerator.generate());
		bill.setBalanceType(obj.getBalanceType());
		this.setBillNo(bill);
		bill.setTableName(obj.getTableName());
		bill.setStatus(BalanceStatusEnums.NO_CONFIRM.getTypeNo());
		bill.setBalanceDate(obj.getBalanceDate());
		bill.setBalanceStartDate(obj.getBalanceStartDate());
		bill.setBalanceEndDate(obj.getBalanceEndDate());
		bill.setCreateUser(obj.getCreateUser());
		bill.setCreateTime(obj.getCreateTime());
		bill.setCurrency(obj.getCurrency());
		bill.setBillName(obj.getBillName());
		bill.setRemark(obj.getRemark());
	}

	/**
	 * 设置单据编号
	 * @param obj
	 * @throws ServiceException
	 */
	private void setBillNo(BillBalance obj) throws ServiceException{
		String billNo = "";
		if(obj.getBalanceType().intValue() == BalanceTypeEnums.HQ_VENDOR.getTypeNo()){
			billNo = commonUtilService.getNewBillNoCompanyNo(obj.getBuyerNo(), FasBillTypeEnums.getRequestIdByBalanceType(obj.getBalanceType()));
		}else if(obj.getBalanceType().intValue() == BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()){
			billNo = commonUtilService.getNewBillNoCompanyNo(obj.getSalerNo(), FasBillTypeEnums.getRequestIdByBalanceType(obj.getBalanceType()));
		}
		obj.setBillNo(billNo);
	}
	
	
	/**
	 * 插入审批日志对象
	 * @param billBalance
	 * @throws ServiceException 
	 */
	private void insertOperateLog(BillBalance obj) throws ServiceException {
		OperateLog operateLog = new OperateLog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setDataNo(obj.getBillNo());
		operateLog.setModuleNo(OperateLogEnums.OperateModule.JSD.getModuleNo());
		operateLog.setCreateUser(obj.getCreateUser());
		operateLog.setCreateTime(obj.getCreateTime());
		operateLog.setStatus(BalanceStatusEnums.NO_CONFIRM.getTypeNo());
		operateLog.setStatusName(BalanceStatusEnums.NO_CONFIRM.getTypeName());
		operateLog.setOperateStatusName(BalanceStatusEnums.NO_CONFIRM.getTypeName());
		operateLogService.add(operateLog);
	}

}