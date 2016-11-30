package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.dal.database.BillBalanceInvoiceRegisterMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-17 12:13:49
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
@Service("billBalanceInvoiceRegisterService")
class BillBalanceInvoiceRegisterServiceImpl extends BaseCrudServiceImpl implements BillBalanceInvoiceRegisterService {
    @Resource
    private BillBalanceInvoiceRegisterMapper billBalanceInvoiceRegisterMapper;

    @Resource
    private BillShopBalanceMapper billShopBalanceMapper;
    
    @Resource
    private BillBalanceMapper billBalanceMapper;
    
    @Resource
    private CodingRuleService codingRuleService;
    
    @Override
    public BaseCrudMapper init() {
        return billBalanceInvoiceRegisterMapper;
    }
    
    @Override
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		String billNo = "";
		String balanceNo="";
		try {
			BillBalanceInvoiceRegister obj =  (BillBalanceInvoiceRegister)modelType;
			billNo = codingRuleService.getSerialNo(BillBalanceInvoiceRegister.class.getSimpleName());
			obj.setBillNo(billNo);
			balanceNo = obj.getBalanceNo();
			int iCount = billBalanceInvoiceRegisterMapper.insertSelective(obj);
			
			
			//更新结算单上的开票申请信息反写
			if(10==obj.getBalanceType()){
				BillShopBalance	shopBalance  =   new  BillShopBalance();
				shopBalance.setBalanceNo(balanceNo);
//				shopBalance.setInvoiceFlag((byte) 2);
				shopBalance.setInvoiceApplyNo(billNo);
			    billShopBalanceMapper.updateInvoiceByBalanceNo(shopBalance);
			}else{
				BillBalance	balance  =   new  BillBalance();
				balance.setBillNo(balanceNo);
//				balance.setInvoiceApplyFlag(2);
				balance.setInvoiceApplyNo(billNo);
//				billBalanceMapper.updateInvoiceApplyNo(balance);
			}
			return iCount;
		} catch (Exception e) {
			codingRuleService. recycleSerialNo(BillBalanceInvoiceRegister.class.getSimpleName(),billNo);
			throw new ServiceException("",e);
		}
	}
}