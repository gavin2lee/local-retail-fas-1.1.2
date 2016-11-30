package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.dto.BalanceGatherDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 10:33:45
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
public interface BillBalanceQueryManager {
	
	public int selectDetailCount(Map<String, Object> params)throws ManagerException;

	public List<BalanceDetailDto> selectDetailList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;

	public List<BalanceDetailDto> selectDetailFooter(Map<String, Object> params)throws ManagerException;

	public int selectGatherCount(Map<String, Object> params)throws ManagerException;

	public List<BalanceGatherDto> selectGatherList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;

	public List<BalanceGatherDto> selectGatherFooter(Map<String, Object> params)throws ManagerException;

	public int selectBalanceCount(Map<String, Object> params)throws ManagerException;

	public List<BillBalance> selectBalanceList(SimplePage page,String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;

	public List<BillBalance> selectBalanceFooter(Map<String, Object> params)throws ManagerException;

	public int selectItemGatherCount(Map<String, Object> params)throws ManagerException;

	public List<BalanceDetailDto> selectItemGatherList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;

	public List<BalanceDetailDto> selectItemGatherFooter(Map<String, Object> params)throws ManagerException;

	public int selectEntryGatherCount(Map<String, Object> params)throws ManagerException;

	public List<BalanceGatherDto> selectEntryGatherList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)throws ManagerException;

	public List<BalanceGatherDto> selectEntryGatherFooter(Map<String, Object> params)throws ManagerException;
	
}