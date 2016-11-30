package cn.wonhigh.retail.fas.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.InvoiceCategoryCommonDto;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSetDtl;
import cn.wonhigh.retail.fas.common.model.SequenceStrId;
import cn.wonhigh.retail.fas.dal.database.InvoiceTemplateSetDtlMapper;

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
@Service("invoiceTemplateSetDtlService")
class InvoiceTemplateSetDtlServiceImpl extends BaseServiceImpl implements InvoiceTemplateSetDtlService {
    @Resource
    private InvoiceTemplateSetDtlMapper invoiceTemplateSetDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return invoiceTemplateSetDtlMapper;
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
    	InvoiceTemplateSetDtl invoiceTemplateSetDtl = (InvoiceTemplateSetDtl) model;
    	invoiceTemplateSetDtl.setInvoicetempDtlNo(generateBillNo(invoiceTemplateSetDtl));
    	if(model != null){
			 if(model instanceof SequenceStrId){
				SequenceStrId id = (SequenceStrId)model;
				if(null == id.getId()){
					invoiceTemplateSetDtl.setId(String.valueOf(getId()));
				}
			}
		}
    	invoiceTemplateSetDtlMapper.insert(invoiceTemplateSetDtl);
		return 0;
    }    
    
    /**
	 * 生成单据编号
	 */
	private String generateBillNo(InvoiceTemplateSetDtl invoiceTemplateSetDtl){
		String prefix = "ITSD";//BalanceTypeEnums.getTypeCodeByNo(billBalance.getBalanceType());
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhh");
		String dateSeq = dateFormat.format(cal.getTime());
		String maxBillNo = invoiceTemplateSetDtlMapper.selectInvoiceTemplateDtlMaxBillNo(invoiceTemplateSetDtl);
		int nextBillNo = Integer.valueOf(maxBillNo.substring(maxBillNo.length()-2))+1;
		String suffix = nextBillNo >= 10 ? String.valueOf(nextBillNo) : "000"+nextBillNo;
		return prefix + dateSeq +suffix;
	}

	@Override
	public List<InvoiceCategoryCommonDto> selectCateInfo(Map<String, Object> params) {
		return invoiceTemplateSetDtlMapper.selectCateInfo(params);
	}

	@Override
	public <InvoiceTemplateSetDtl> int deleteByModel(
			InvoiceTemplateSetDtl invoiceTemplateSetDtl) {
		// TODO Auto-generated method stub
		return 0;
	}
}