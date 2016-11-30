package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.dto.BalanceGatherDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.service.BillBalanceQueryService;

import com.yougou.logistics.base.common.exception.ManagerException;
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
@Service("billBalanceQueryManager")
class BillBalanceQueryManagerImpl implements BillBalanceQueryManager {

    @Resource
    private BillBalanceQueryService billBalanceQueryService;
    
	@Override
	public int selectDetailCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectDetailCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BalanceDetailDto> selectDetailList(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectDetailList(page, sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BalanceDetailDto> selectDetailFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectDetailFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectGatherCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectGatherCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BalanceGatherDto> selectGatherList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectGatherList(page, sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BalanceGatherDto> selectGatherFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectGatherFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectBalanceCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectBalanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectBalanceList(page, sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectBalanceFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectItemGatherCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectItemGatherCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BalanceDetailDto> selectItemGatherList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectItemGatherList(page, sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BalanceDetailDto> selectItemGatherFooter(
			Map<String, Object> params) throws ManagerException {
		try {
			return billBalanceQueryService.selectItemGatherFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectEntryGatherCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectEntryGatherCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BalanceGatherDto> selectEntryGatherList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return billBalanceQueryService.selectEntryGatherList(page, sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BalanceGatherDto> selectEntryGatherFooter(
			Map<String, Object> params) throws ManagerException {
		try {
			return billBalanceQueryService.selectEntryGatherFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}