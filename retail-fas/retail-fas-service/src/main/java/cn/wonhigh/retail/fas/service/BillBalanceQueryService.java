package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.dto.BalanceGatherDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.exception.ServiceException;
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
public interface BillBalanceQueryService{

	int selectDetailCount(Map<String, Object> params)throws ServiceException;

	List<BalanceDetailDto> selectDetailList(SimplePage page, String sortColumn,String sortOrder, Map<String, Object> params)throws ServiceException;

	List<BalanceDetailDto> selectDetailFooter(Map<String, Object> params)throws ServiceException;

	int selectGatherCount(Map<String, Object> params)throws ServiceException;

	List<BalanceGatherDto> selectGatherList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	List<BalanceGatherDto> selectGatherFooter(Map<String, Object> params)throws ServiceException;

	int selectBalanceCount(Map<String, Object> params)throws ServiceException;

	List<BillBalance> selectBalanceList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	List<BillBalance> selectBalanceFooter(Map<String, Object> params)throws ServiceException;

	int selectItemGatherCount(Map<String, Object> params)throws ServiceException;

	List<BalanceDetailDto> selectItemGatherList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	List<BalanceDetailDto> selectItemGatherFooter(Map<String, Object> params)throws ServiceException;

	int selectEntryGatherCount(Map<String, Object> params)throws ServiceException;

	List<BalanceGatherDto> selectEntryGatherList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)throws ServiceException;

	List<BalanceGatherDto> selectEntryGatherFooter(Map<String, Object> params)throws ServiceException;
	
}