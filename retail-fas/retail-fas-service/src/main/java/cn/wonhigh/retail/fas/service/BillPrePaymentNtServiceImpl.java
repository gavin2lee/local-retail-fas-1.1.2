package cn.wonhigh.retail.fas.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillPrePaymentNt;
import cn.wonhigh.retail.fas.dal.database.BillPrePaymentNtMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

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
@Service("billPrePaymentNtService")
class BillPrePaymentNtServiceImpl extends BaseServiceImpl implements BillPrePaymentNtService {
    @Resource
    private BillPrePaymentNtMapper billPrePaymentNtMapper;

    @Resource
    private CommonUtilService commonUtilService;
    
    @Override
    public BaseCrudMapper init() {
        return billPrePaymentNtMapper;
    }
    
    
	@Override
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			BillPrePaymentNt obj = (BillPrePaymentNt)modelType; 
			obj.setBalanceType(BalanceTypeEnums.AREA_WHOLESALE.getTypeNo());
			obj.setBillNo(commonUtilService.getNewBillNoCompanyNo(obj.getCompanyNo(), FasBillTypeEnums.PA.getRequestId()));
			validate(modelType);
			//billPrePaymentNtMapper.insertSelective(obj);
			super.add(obj);
			return 1;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
	@Override
	/**
	 * 保存团购预收款单
	 * @author wangyj
	 */
	public int addGroupPrePayment(BillPrePaymentNt billPrePaymentNt)throws ServiceException {
		//return billPrePaymentNtMapper.insertSelective(billPrePaymentNt);
		return super.add(billPrePaymentNt);
	}


	@Override
	public BillPrePaymentNt calcPrePaymentTotal(Map<String, Object> params)
			throws ServiceException {
		return billPrePaymentNtMapper.calcPrePaymentTotal(params);
	}
}