package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BillStatusEnums;
import cn.wonhigh.retail.fas.common.model.BillPurchaseAdjust;
import cn.wonhigh.retail.fas.dal.database.BillPurchaseAdjustDtlMapper;
import cn.wonhigh.retail.fas.dal.database.BillPurchaseAdjustMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-13 12:09:02
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
@Service("billPurchaseAdjustService")
class BillPurchaseAdjustServiceImpl extends BaseCrudServiceImpl implements BillPurchaseAdjustService {
	
    @Resource
    private BillPurchaseAdjustMapper billPurchaseAdjustMapper;
    
    @Resource
    private BillPurchaseAdjustDtlMapper billPurchaseAdjustDtlMapper;

    @Resource
    private CommonUtilService commonUtilService; 
    
    @Override
    public BaseCrudMapper init() {
        return billPurchaseAdjustMapper;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			BillPurchaseAdjust obj =  (BillPurchaseAdjust)modelType;
			obj.setId(UUIDHexGenerator.generate());
			obj.setStatus(BillStatusEnums.CREATE.getTypeNo());
			obj.setBillNo(commonUtilService.getNewBillNoCompanyNo(obj.getSalerNo(), BillPurchaseAdjust.class.getSimpleName()));
			int iCount = super.add(obj);
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
    		BillPurchaseAdjust billPurchaseAdjust = (BillPurchaseAdjust)obj;
			String idList = billPurchaseAdjust.getIdList();
			if(StringUtils.isNotBlank(idList)){
				String idArr[] = idList.split(";");
				for (String str : idArr) {
					billPurchaseAdjust = new BillPurchaseAdjust();
					String[] arr = str.split(",");
					billPurchaseAdjust.setId(arr[0]);
					super.deleteById(billPurchaseAdjust);
					billPurchaseAdjustDtlMapper.deleteByBillNo(arr[1]);
				}
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException();
		}
    }
	
	@Override
	public int verifyBill(BillPurchaseAdjust obj) throws ServiceException {
		int totalCount = 0;
		try {
			String billNo = obj.getBillNo();
			String[] arrNo = billNo.split(",");
			for (String str : arrNo) {
				obj.setBillNo(str);
				totalCount += billPurchaseAdjustMapper.verify(obj);
			}
			return totalCount;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

}