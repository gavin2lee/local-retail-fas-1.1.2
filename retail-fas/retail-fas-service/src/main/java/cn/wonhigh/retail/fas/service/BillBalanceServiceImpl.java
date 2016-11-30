package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
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
@Service("billBalanceService")
class BillBalanceServiceImpl extends BaseCrudServiceImpl implements BillBalanceService {
    @Resource
    private BillBalanceMapper billBalanceMapper;

    @Resource
    private OperateLogService operateLogService;
    
    @Override
    public BaseCrudMapper init() {
        return billBalanceMapper;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public int verify(BillBalance billBalance) throws ServiceException {
		int totalCount = 0;
		try {
			String billNo = billBalance.getBillNo();
			String[] arrNo = billNo.split(",");
			for (String str : arrNo) {
				billBalance.setBillNo(str);
				int iCount = billBalanceMapper.verify(billBalance);
				if(iCount > 0){
					this.insertOperateLog(billBalance);
				}
				totalCount += iCount;
			}
			return totalCount;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	/**
	 * 插入审批日志对象
	 * @param billBalance
	 * @throws ServiceException 
	 */
	private void insertOperateLog(BillBalance billBalance) throws ServiceException {
		OperateLog operateLog = new OperateLog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setDataNo(billBalance.getBillNo());
		operateLog.setModuleNo(OperateLogEnums.OperateModule.JSD.getModuleNo());
		operateLog.setStatus(billBalance.getStatus());
		operateLog.setStatusName(BalanceStatusEnums.getTypeNameByNo(billBalance.getStatus()));
		operateLog.setCreateUser(billBalance.getAuditor());
		operateLog.setCreateTime(billBalance.getAuditTime());
		operateLog.setOperateStatusName(BalanceStatusEnums.getOperateNameByNo(billBalance.getStatus()));
		operateLogService.add(operateLog);
	}
	
	@Override
	public void clearSaleBalanceNo(String balanceNo) throws ServiceException {
		try {
				billBalanceMapper.clearSaleBalanceNo(balanceNo);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public void updateSaleBalanceStatus(BillBalance billBalance)
			throws ServiceException {
		try {
				billBalanceMapper.updateSaleBalanceStatus(billBalance);
			} catch (Exception e) {
				throw new ServiceException("",e);
			}
	}

	@Override
	public void updateSaleBalanceNo(BillBalance billBalance)
			throws ServiceException {
		try {
				billBalanceMapper.updateSaleBalanceNo(billBalance);
			} catch (Exception e) {
				throw new ServiceException("",e);
			}
	}

	@Override
	public void clearBuyBalanceNo(String balanceNo) throws ServiceException {
		try {
				billBalanceMapper.clearBuyBalanceNo(balanceNo);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public void resetSaleBalanceActualCost(String balanceNo) throws ServiceException {
		try {
			billBalanceMapper.resetSaleBalanceActualCost(balanceNo);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

}