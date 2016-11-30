package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.BillCommonRegisterDtlService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-10 14:40:44
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
@Service("billCommonRegisterDtlManager")
class BillCommonRegisterDtlManagerImpl extends BaseCrudManagerImpl implements BillCommonRegisterDtlManager {
    @Resource
    private BillCommonRegisterDtlService billCommonRegisterDtlService;
    
    @Override
    public BaseCrudService init() {
        return billCommonRegisterDtlService;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int saveAll(Map<CommonOperatorEnum, List<BillCommonRegisterDtl>> params) throws ManagerException {
		try {
			int count = 0;
			for (Entry<CommonOperatorEnum, List<BillCommonRegisterDtl>> param : params.entrySet()) {
				if (param.getKey().equals(CommonOperatorEnum.DELETED)) {
					List<BillCommonRegisterDtl> list = params.get(CommonOperatorEnum.DELETED);
					if (null != list && list.size() > 0) {
						for (BillCommonRegisterDtl BillCommonRegisterDtl : list) {
							count += this.billCommonRegisterDtlService.deleteById(BillCommonRegisterDtl);
						}
					}
				}
				if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
					List<BillCommonRegisterDtl> list = params.get(CommonOperatorEnum.UPDATED);
					if (null != list && list.size() > 0) {
						for (BillCommonRegisterDtl BillCommonRegisterDtl : list) {
							count += this.billCommonRegisterDtlService.modifyById(BillCommonRegisterDtl);
						}
					}
				}
				if (param.getKey().equals(CommonOperatorEnum.INSERTED)) {
					//List<BillCommonRegisterDtl> list = (List<BillCommonRegisterDtl>)params.get(CommonOperatorEnum.INSERTED);
					List<BillCommonRegisterDtl> list = params.get(CommonOperatorEnum.INSERTED);
					if (null != list && list.size() > 0) {
						for (BillCommonRegisterDtl billCommonRegisterDtl : list) {	
//							String billNo = codingRuleService.getSerialNo(BillBalanceInvoiceRegister.class.getSimpleName());
//							int no = Integer.valueOf(billNo.substring(14, billNo.length()));//Integer.valueOf(billNo.substring(2, billNo.length()-2));
//							int fno= no-1;
//							String bno=String.valueOf(fno);
//							billCommonRegisterDtl.setBillNo(billNo.substring(0, billNo.length()-2)+bno);
//							BillCommonRegisterDtl billCommonRegisterDtl = new BillCommonRegisterDtl();
//							billCommonRegisterDtl.setBillNo(billNo);
							billCommonRegisterDtl.setId(UUIDGenerator.getUUID());
							this.billCommonRegisterDtlService.add(billCommonRegisterDtl);
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
	public BillCommonRegisterDtl getInvoiceAmount(String invoiceNo) throws ManagerException {
		try {
			return billCommonRegisterDtlService.getInvoiceAmount(invoiceNo);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}