package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffExport;
import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffFooterDto;
import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffTrackDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;
import cn.wonhigh.retail.fas.service.BillShopBalanceDiffService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-15 10:52:13
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
@Service("billShopBalanceDiffManager")
class BillShopBalanceDiffManagerImpl extends BaseCrudManagerImpl implements BillShopBalanceDiffManager {
	
    @Resource
    private BillShopBalanceDiffService billShopBalanceDiffService;

    @Override
    public BaseCrudService init() {
        return billShopBalanceDiffService;
    }

	@Override
	public BigDecimal getDiffBalanceSum(BillShopBalanceDiff billShopBalanceDiff)
			throws ManagerException {
		return billShopBalanceDiffService.getDiffBalanceSum(billShopBalanceDiff);
	}

	@Override
	public BigDecimal getDiffBackAmountSum(BillShopBalanceDiff billShopBalanceDiff) 
			throws ManagerException {
		return billShopBalanceDiffService.getDiffBackAmountSum(billShopBalanceDiff);
	}

	@Override
	public BillShopBalanceDiff  getSumAmount(Map<String, Object> params)
			throws ManagerException {
		try {
			return (BillShopBalanceDiff) billShopBalanceDiffService.getSumAmount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findDiffTrackCount(Map<String, Object> params) throws ManagerException {
		try {
			return billShopBalanceDiffService.findDiffTrackCount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceDiffExport> findDiffTrackPage(SimplePage page,
			String orderByField,String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopBalanceDiffService.findDiffTrackPage(page,orderByField, orderBy,  params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 获取页脚汇总数据
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	@Override
	public BillShopBalanceDiffFooterDto getFooterDto(Map<String, Object> params) {
		return billShopBalanceDiffService.getFooterDto(params);
	}

	@Override
	public List<BillShopBalanceDiffExport> findDiffTrackExport(
			Map<String, Object> params) throws ManagerException {
		try {
			return billShopBalanceDiffService.findDiffTrackExport(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectlistSearchCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopBalanceDiffService.selectlistSearchCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceDiff> selectlistSearchByPage(SimplePage page,
			String orderByField,String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopBalanceDiffService.selectlistSearchByPage(page,orderByField, orderBy,  params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectlistDiffTrackCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopBalanceDiffService.selectlistDiffTrackCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceDiff> selectlistDiffTrackByPage(SimplePage page,
			String orderByField,String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopBalanceDiffService.selectlistDiffTrackByPage(page,orderByField, orderBy,  params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}