package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.dto.BalanceGatherDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.dal.database.BillBalanceQueryMapper;

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
@Service("billBalanceQueryService")
class BillBalanceQueryServiceImpl implements BillBalanceQueryService {

    @Resource
    private BillBalanceQueryMapper billBalanceQueryMapper;
    
	@Override
	public int selectDetailCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectDetailCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BalanceDetailDto> selectDetailList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectDetailList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BalanceDetailDto> selectDetailFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectDetailFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int selectGatherCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectGatherCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BalanceGatherDto> selectGatherList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectGatherList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BalanceGatherDto> selectGatherFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectGatherFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int selectEntryGatherCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectEntryGatherCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BalanceGatherDto> selectEntryGatherList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectEntryGatherList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BalanceGatherDto> selectEntryGatherFooter(
			Map<String, Object> params) throws ServiceException {
		try {
			return billBalanceQueryMapper.selectEntryGatherFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public int selectBalanceCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectBalanceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectBalanceList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectBalanceFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int selectItemGatherCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectItemGatherCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BalanceDetailDto> selectItemGatherList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return billBalanceQueryMapper.selectItemGatherList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BalanceDetailDto> selectItemGatherFooter(
			Map<String, Object> params) throws ServiceException {
		try {
			return billBalanceQueryMapper.selectItemGatherFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}