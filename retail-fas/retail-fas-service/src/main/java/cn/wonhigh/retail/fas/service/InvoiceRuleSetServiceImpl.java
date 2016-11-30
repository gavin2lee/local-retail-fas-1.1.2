package cn.wonhigh.retail.fas.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.InvoiceRuleSet;
import cn.wonhigh.retail.fas.common.model.SequenceStrId;
import cn.wonhigh.retail.fas.dal.database.InvoiceRuleSetMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:37
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
@Service("invoiceRuleSetService")
class InvoiceRuleSetServiceImpl extends BaseServiceImpl implements InvoiceRuleSetService {
    @Resource
    private InvoiceRuleSetMapper invoiceRuleSetMapper;

    @Override
    public BaseCrudMapper init() {
        return invoiceRuleSetMapper;
    }
    
    protected int getId()throws ServiceException{
		try {
			String serialNo = codingRuleService.getSerialNo("public").substring(2);
			return Integer.parseInt(serialNo);
		} catch (ServiceException e) {
			throw new ServiceException("",e);
		}
		
	}
    
    @Override
	public <ModelType> int add(ModelType model) throws ServiceException {
    	InvoiceRuleSet invoiceRuleSet = (InvoiceRuleSet) model;
//    	invoiceRuleSet.setId(UUIDGenerator.getUUID());
		String invoiceRuleNo = generationBalanceNo(invoiceRuleSet);	
		invoiceRuleSet.setInvoiceRuleNo(invoiceRuleNo);
		if(model != null){
			 if(model instanceof SequenceStrId){
				SequenceStrId id = (SequenceStrId)model;
				if(null == id.getId()){
					invoiceRuleSet.setId(String.valueOf(getId()));
				}
			}
		}
		invoiceRuleSetMapper.insert(invoiceRuleSet);
		return 1;
	}
    
	/**
	 * 生成单据编号
	 */
	private String generationBalanceNo(InvoiceRuleSet invoiceRuleSet){
		String prefix = "IS";//BalanceTypeEnums.getTypeCodeByNo(billBalance.getBalanceType());
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhh");
		String dateSeq = dateFormat.format(cal.getTime());
		invoiceRuleSet.setInvoiceRuleNo(dateSeq);
		String maxBillNo = invoiceRuleSetMapper.selectInvoiceRuleMaxBillNo(invoiceRuleSet);
		int nextBillNo = Integer.valueOf(maxBillNo.substring(maxBillNo.length()-2))+1;
		String suffix = nextBillNo >= 10 ? String.valueOf(nextBillNo) : "0000"+nextBillNo;
		return prefix + dateSeq +suffix;
	}

	@Override
	public InvoiceRuleSet selectInvoiceNameByShopNo(Map<String, Object> params) {
		return invoiceRuleSetMapper.selectInvoiceNameByShopNo(params);
	}
	
	
}