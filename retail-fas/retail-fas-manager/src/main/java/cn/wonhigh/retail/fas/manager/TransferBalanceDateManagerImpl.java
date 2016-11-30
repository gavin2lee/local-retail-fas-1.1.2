package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.TransferBalanceDate;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.TransferBalanceDateService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-07-05 14:55:50
 * @version 1.0.8
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("transferBalanceDateManager")
class TransferBalanceDateManagerImpl extends BaseCrudManagerImpl implements TransferBalanceDateManager {
    @Resource
    private TransferBalanceDateService transferBalanceDateService;
    @Resource
    private CompanyService companyService;

    @Override
    public BaseCrudService init() {
        return transferBalanceDateService;
    }

	@Override
	public Map<String, Object> addBalanceDateByBatch(TransferBalanceDate tbd) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		if (tbd != null) {
			TransferBalanceDate newTbd=new TransferBalanceDate();
			String salerName=companyService.findCompanyNameByNo(tbd.getSalerNo());
			int total = 0;
			String[] buyerNos = tbd.getBuyerNo().split(",");
			for (int i = 0; i < buyerNos.length; i++) {
				String buyerName=companyService.findCompanyNameByNo(buyerNos[i]);
				newTbd.setSalerNo(tbd.getSalerNo());
				newTbd.setSalerName(salerName);
				newTbd.setBuyerNo(buyerNos[i]);
				newTbd.setBuyerName(buyerName);
				newTbd.setBalanceStartDate(tbd.getBalanceStartDate());
				newTbd.setBalanceEndDate(tbd.getBalanceEndDate());
				newTbd.setBalanceMonth(tbd.getBalanceMonth());
				newTbd.setBalanceFlag(new Byte("0"));
				newTbd.setInvoiceFlag(new Byte("0"));
				newTbd.setCreateTime(DateUtil.getCurrentDateTime());
				newTbd.setCreateUser(tbd.getCreateUser());
				
				//查询是否已经存在
				params.put("salerNo", tbd.getSalerNo());
				params.put("buyerNo", buyerNos[i]);
				params.put("balanceMonth", tbd.getBalanceMonth());
				int tmpCount = transferBalanceDateService.findCount(params);
				if (tmpCount == 0) {
					int count = transferBalanceDateService.add(newTbd);
					total += count;
				}
				if (total > 0) {
					map.put("flag", true);
				} else {
					map.put("flag", false);
				}
			}
			
		}
		return map;
	}
}