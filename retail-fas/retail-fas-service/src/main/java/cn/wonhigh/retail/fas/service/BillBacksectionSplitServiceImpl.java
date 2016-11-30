package cn.wonhigh.retail.fas.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplit;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillBacksectionSplitMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:36
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
@Service("billBacksectionSplitService")
class BillBacksectionSplitServiceImpl extends BaseCrudServiceImpl implements BillBacksectionSplitService {
    @Resource
    private BillBacksectionSplitMapper billBacksectionSplitMapper;
    
    @Resource
    private CodingRuleService codingRuleService;   
    
    @Resource
	private CommonUtilService commonUtilService;

    @Override
    public BaseCrudMapper init() {
        return billBacksectionSplitMapper;
    }
    
    @Override
  	public <ModelType> int add(ModelType model) throws ServiceException {
    	String billNo = "";
		try {
    	BillBacksectionSplit backsectionSplit = (BillBacksectionSplit) model;
//    	billNo = codingRuleService.getSerialNo(BillBacksectionSplit.class.getSimpleName());
    	
    	String requestId = BillBacksectionSplit.class.getSimpleName();
		//调用单据编号拼接处理方法，返回最终的单据编号s
    	billNo = commonUtilService.getNewBillNoCompanyNo(backsectionSplit.getCompanyNo(),null,requestId);
    	
    	backsectionSplit.setBacksectionNo(billNo);//generateBillNo(backsectionSplit));
    	backsectionSplit.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(backsectionSplit.getCompanyNo()));
    	setBillInfo(backsectionSplit);
    	int iCount =billBacksectionSplitMapper.insertSelective(backsectionSplit);
		return iCount;
		} catch (Exception e) {
			codingRuleService. recycleSerialNo(BillBacksectionSplit.class.getSimpleName(),billNo);
			throw new ServiceException("",e);
		}
    } 
    
    private void setBillInfo(BillBacksectionSplit backsectionSplit) {
    	backsectionSplit.setId(UUIDGenerator.getUUID());
	}
    
    /**
	 * 生成单据编号
	 */
	private String generateBillNo(BillBacksectionSplit backsectionSplit){
		String prefix = "BS";//BalanceTypeEnums.getTypeCodeByNo(billBalance.getBalanceType());
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhh");
		String dateSeq = dateFormat.format(cal.getTime());
		backsectionSplit.setBacksectionNo(dateSeq);
		String maxBillNo = billBacksectionSplitMapper.selectBacksectionMaxBillNo(backsectionSplit);
		int nextBillNo = Integer.valueOf(maxBillNo.substring(maxBillNo.length()-2))+1;
		String suffix = nextBillNo >= 10 ? String.valueOf(nextBillNo) : "0000"+nextBillNo;
		return prefix + dateSeq +suffix;
	}

	@Override
	public int deleteById(int id) {
		return billBacksectionSplitMapper.deleteByPrimaryKey(id);
	}

	@Override
	public <ModelType> int deleteByPrimarayKeyForModel(ModelType record) {
		return billBacksectionSplitMapper.deleteByPrimarayKeyForModel(record);
	}

	@Override
	public int findAfterCount(String id) {
		return billBacksectionSplitMapper.selectAfterCount(id);
	}
	
	 @Override
	  	public <ModelType> int batchAdd(ModelType model) throws ServiceException {
			try {
	    	BillBacksectionSplit backsectionSplit = (BillBacksectionSplit) model;	    	
	    	int iCount =billBacksectionSplitMapper.insertSelective(backsectionSplit);
			return iCount;
			} catch (Exception e) {
//				codingRuleService. recycleSerialNo(BillBacksectionSplit.class.getSimpleName(),billNo);
				throw new ServiceException("",e);
			}
	    } 
}