package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillMallDeductionCost;
import cn.wonhigh.retail.fas.dal.database.BillMallDeductionCostMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-08-28 11:47:41
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
@Service("billMallDeductionCostService")
class BillMallDeductionCostServiceImpl extends BaseCrudServiceImpl implements BillMallDeductionCostService {
    @Resource
    private BillMallDeductionCostMapper billMallDeductionCostMapper;

    @Resource
    private CodingRuleService codingRuleService;   
    
    @Override
    public BaseCrudMapper init() {
        return billMallDeductionCostMapper;
    }
    
    
    @Override
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		String billNo = "";
		try {
			BillMallDeductionCost obj =  (BillMallDeductionCost)modelType;
//			obj.setId(UUIDGenerator.getUUID());
			billNo = codingRuleService.getSerialNo(BillMallDeductionCost.class.getSimpleName());
			obj.setBillNo(billNo);
			int iCount = billMallDeductionCostMapper.insertSelective(obj);
			return iCount;
		} catch (Exception e) {
			codingRuleService. recycleSerialNo(BillMallDeductionCost.class.getSimpleName(),billNo);
			throw new ServiceException("",e);
		}
	}
}