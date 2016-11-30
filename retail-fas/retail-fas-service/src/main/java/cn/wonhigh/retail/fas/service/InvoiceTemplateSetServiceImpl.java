package cn.wonhigh.retail.fas.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSet;
import cn.wonhigh.retail.fas.common.model.SequenceStrId;
import cn.wonhigh.retail.fas.dal.database.InvoiceTemplateSetMapper;

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
@Service("invoiceTemplateSetService")
class InvoiceTemplateSetServiceImpl extends BaseServiceImpl implements InvoiceTemplateSetService {
    @Resource
    private InvoiceTemplateSetMapper invoiceTemplateSetMapper;

    @Resource
	private CommonUtilService commonUtilService;
    
    @Override
    public BaseCrudMapper init() {
        return invoiceTemplateSetMapper;
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
    	InvoiceTemplateSet invoiceTemplateSet = (InvoiceTemplateSet) model;
    	String requestId = InvoiceTemplateSet.class.getSimpleName();
		//调用单据编号拼接处理方法，返回最终的单据编号
		String invoiceTempNo = commonUtilService.getNewBillNoCompanyNo(invoiceTemplateSet.getCompanyNo(),requestId);
    	invoiceTemplateSet.setInvoiceTempNo(invoiceTempNo);//generateBillNo(invoiceTemplateSet));
    	if(model != null){
			 if(model instanceof SequenceStrId){
				SequenceStrId id = (SequenceStrId)model;
				if(null == id.getId()){
					invoiceTemplateSet.setId(String.valueOf(getId()));
				}
			}
		}
    	invoiceTemplateSetMapper.insertSelective(invoiceTemplateSet);
		return 0;
    }    

    /**
	 * 生成单据编号
	 */
	private String generateBillNo(InvoiceTemplateSet invoiceTemplateSet){
		String prefix = "ITS";//BalanceTypeEnums.getTypeCodeByNo(billBalance.getBalanceType());
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhh");
		String dateSeq = dateFormat.format(cal.getTime());
//		invoiceTemplateSet.setBacksectionNo(dateSeq);
		String maxBillNo = invoiceTemplateSetMapper.selectInvoiceTemplateMaxBillNo(invoiceTemplateSet);
		int nextBillNo = Integer.valueOf(maxBillNo.substring(maxBillNo.length()-2))+1;
		String suffix = nextBillNo >= 10 ? String.valueOf(nextBillNo) : "0000"+nextBillNo;
		return prefix + dateSeq +suffix;
	}

	@Override
	public <InvoiceTemplateSet> int deleteByModel(
			InvoiceTemplateSet invoiceTemplateSet) {
		return invoiceTemplateSetMapper.deleteByModel(invoiceTemplateSet);
	}

	@Override
	public int checkIsUseData(String invoiceTempNo) {
		return invoiceTemplateSetMapper.checkIsUseData(invoiceTempNo);
	}
}