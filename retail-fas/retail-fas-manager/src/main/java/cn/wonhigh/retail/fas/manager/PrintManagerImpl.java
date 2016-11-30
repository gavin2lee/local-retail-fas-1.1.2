package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.service.PrintService;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-05-29 18:03:44
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("printManager")
class PrintManagerImpl implements PrintManager {
    @Resource
    private PrintService printService;
    
	@Override
	public Map<String, Object> getPrintListByBalanceNo(String balanceNo)
			throws ManagerException {
		try {
    		return printService.getPrintListByBalanceNo(balanceNo);
		} catch (Exception e) {
			throw new ManagerException("", e);
		}
	}

	@Override
	public Map<String, Object> getBalanceGatherListByBalanceNo(
			String balanceNo, Date balanceEndDate) throws ManagerException {
		try {
    		return printService.getBalanceGatherListByBalanceNo(balanceNo,balanceEndDate);
		} catch (Exception e) {
			throw new ManagerException("", e);
		}
	}

	@Override
	public Map<String, Map<String, Object>> getBalanceDiffData(String companyNo,
			String brandNo, String year, String month) throws ManagerException {
		try {
    		return printService.getBalanceDiffData(companyNo,brandNo,year,month);
		} catch (Exception e) {
			throw new ManagerException("", e);
		}
	}
}