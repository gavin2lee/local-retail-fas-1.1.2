package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.BillShopBalanceBackMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-22 10:14:42
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
@Service("billShopBalanceBackService")
public class BillShopBalanceBackServiceImpl extends BaseCrudServiceImpl implements BillShopBalanceBackService {
	
    @Resource
    private BillShopBalanceBackMapper billShopBalanceBackMapper;

    @Override
    public BaseCrudMapper init() {
        return billShopBalanceBackMapper;
    }

    /**
     * 通过结算单号、结算差异编号汇总差异回款金额
     * @param balanceNo 结算单号
     * @param diffBillNo 结算差异编号
     * @param diffDtlId 结算差异ID
     * @return 汇总的金额
     * @throws ServiceException 异常
     */
	@Override
	public BigDecimal sumBackAmount(String balanceNo, String diffBillNo, String diffDtlId)
			throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("balanceNo", balanceNo);
			params.put("diffBillNo", diffBillNo);
			params.put("billNo", diffDtlId);
			return billShopBalanceBackMapper.sumBackAmount(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}