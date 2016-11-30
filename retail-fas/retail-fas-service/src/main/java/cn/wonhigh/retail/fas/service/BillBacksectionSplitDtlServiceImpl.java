package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplitDtl;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillBacksectionSplitDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("billBacksectionSplitDtlService")
class BillBacksectionSplitDtlServiceImpl extends BaseCrudServiceImpl implements BillBacksectionSplitDtlService {
	
    @Resource
    private BillBacksectionSplitDtlMapper billBacksectionSplitDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return billBacksectionSplitDtlMapper;
    }
    
    @Override
  	public <ModelType> int add(ModelType model) throws ServiceException {
    	BillBacksectionSplitDtl backsectionSplitDtl = (BillBacksectionSplitDtl) model;	
    	backsectionSplitDtl.setBacksectionDtlNo("BSD0001");  
    	setBillInfo(backsectionSplitDtl);
    	billBacksectionSplitDtlMapper.insertSelective(backsectionSplitDtl);
		return 0;
    }
    
    private void setBillInfo(BillBacksectionSplitDtl backsectionSplitDtl) {
    	backsectionSplitDtl.setId(UUIDGenerator.getUUID());
	}

    /**
	 * 通过结算单号，汇总本次回款金额
	 * @param balanceNo 结算单号
	 * @return 本次回款金额
	 */
	@Override
	public BigDecimal sumPaymentAmount(String balanceNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", balanceNo);
		BigDecimal amount = billBacksectionSplitDtlMapper.sumPaymentAmount(params);
		if(amount == null) {
			amount = BigDecimal.ZERO;
		}
		return amount;
	}

	@Override
	public int selectShopBlanaceAccountCount(Map<String, Object> params)
			throws ServiceException {
		return billBacksectionSplitDtlMapper.selectShopBlanaceAccountCount(params, null);
	}

	@Override
	public List<BillBacksectionSplitDtl> selectShopBlanaceAccountByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		return billBacksectionSplitDtlMapper.selectShopBlanaceAccountByPage(page, sortColumn, sortOrder, params, null);
	}

	@Override
	public List<BillBacksectionSplitDtl> selectAddInsertDtl(
			Map<String, Object> params) {
		return billBacksectionSplitDtlMapper.selectAddInsertDtl(params);
	}

	@Override
	public int deleteByBacksectionNo(String backsectionNo) {
		return billBacksectionSplitDtlMapper.deleteByBacksectionNo(backsectionNo);
	}

	@Override
	public BillBacksectionSplitDtl selectTotalSum(Map<String, Object> params)
			throws ServiceException {
		return billBacksectionSplitDtlMapper.selectTotalSum(params);
	}
	
	
}