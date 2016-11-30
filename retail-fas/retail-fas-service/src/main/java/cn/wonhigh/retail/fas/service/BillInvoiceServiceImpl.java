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

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.PayTermTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillInvoice;
import cn.wonhigh.retail.fas.common.model.PayTermSupplier;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillInvoiceDtlMapper;
import cn.wonhigh.retail.fas.dal.database.BillInvoiceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("billInvoiceService")
class BillInvoiceServiceImpl extends BaseCrudServiceImpl implements BillInvoiceService {
    @Resource
    private BillInvoiceMapper billInvoiceMapper;

    @Resource
    private BillInvoiceDtlMapper billInvoiceDtlMapper;
    
    @Resource
    private CommonUtilService commonUtilService;  
    
    @Resource
    private PayTermSupplierService PayTermSupplierService;
    
    @Override
    public BaseCrudMapper init() {
        return billInvoiceMapper;
    }
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			BillInvoice obj =  (BillInvoice)modelType;
			obj.setId(UUIDGenerator.getUUID());
			obj.setBillNo(commonUtilService.getNewBillNoCompanyNo(obj.getBuyerNo(), FasBillTypeEnums.PI.getRequestId()));
			int iCount = super.add(obj);
			String refBillNo = obj.getRefBillNo();
			if(StringUtils.isNotBlank(refBillNo)){
				refBillNo = refBillNo.replaceAll(",", "','");
				obj.setRefBillNo("('".concat(refBillNo).concat("')"));
				billInvoiceMapper.updateBalanceInvoiceNo(obj);
				if(ShardingUtil.isPE()){
					this.updateDueDate(obj);
				}
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
    private void updateDueDate(BillInvoice obj) throws ServiceException {
		String salerNo = obj.getSalerNo();
		String buyerNo = obj.getBuyerNo();
		int termType = PayTermTypeEnums.INVOICE_DATE.getTypeNo();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierNo", salerNo);
		params.put("companyNo", buyerNo);
		params.put("termType", termType);
		List<PayTermSupplier> lstItem = PayTermSupplierService.findByBiz(null, params);
		if(!CollectionUtils.isEmpty(lstItem)){
			PayTermSupplier term = lstItem.get(0);
			int days = term.getDays();
			Calendar cal = Calendar.getInstance();
			cal.setTime(obj.getBillDate());
			cal.add(Calendar.DAY_OF_YEAR, days);
			Date dueDate = cal.getTime();
			params.clear();
			params.put("multiBalanceNo", FasUtil.formatInQueryCondition(obj.getRefBillNo()));
			params.put("dueDate", dueDate);
			// 更新其他扣项到期日
			billInvoiceMapper.updateDeductionDueDate(params);
			// 更新关系表到期日
			billInvoiceMapper.updatePayRelationDueDate(params);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
    public  int deleteById(Object obj)throws ServiceException{
    	int iCount =0;
    	try {
			BillInvoice billInvoice = (BillInvoice)obj;
			String idList = billInvoice.getIdList();
			if(StringUtils.isNotBlank(idList)){
				String idArr[] = idList.split(";");
				for (String str : idArr) {
					billInvoice = new BillInvoice();
					String id = str.split(",")[0];
					String billNo = str.split(",")[1];
					billInvoice.setId(id);
					billInvoice.setBillNo(billNo);
					super.deleteById(billInvoice);
  					//当删除到票确认生成的采购发票时，回写发票登记的标志
					String balanceType= str.split(",")[2];
					String hqArea=String.valueOf(BalanceTypeEnums.HQ_WHOLESALE.getTypeNo());
					String areaArea=String.valueOf(BalanceTypeEnums.AREA_AMONG.getTypeNo());
					String areaOther=String.valueOf(BalanceTypeEnums.AREA_OTHER.getTypeNo());
					if(balanceType.equals(hqArea)||balanceType.equals(areaArea)||balanceType.equals(areaOther)){
						billInvoiceMapper.clearInvoiceNoFlag(billNo);
					}
					billInvoiceMapper.clearBalanceInvoiceNo(billNo);
					billInvoiceDtlMapper.deleteByBillNo(billNo);
				}
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException();
		}
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public int verify(BillInvoice obj) throws ServiceException {
		int totalCount = 0;
		try {
			String billNo = obj.getBillNo();
			String[] arrNo = billNo.split(",");
			for (String str : arrNo) {
				obj.setBillNo(str);
				totalCount += billInvoiceMapper.verify(obj);
			}
			return totalCount;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public List<BillInvoice> selectFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return billInvoiceMapper.selectFooter(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

}