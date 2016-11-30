package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffExport;
import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffFooterDto;
import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDiffDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceDiffMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("billShopBalanceDiffService")
class BillShopBalanceDiffServiceImpl extends BaseCrudServiceImpl implements BillShopBalanceDiffService {
	
    @Resource
    private BillShopBalanceDiffMapper billShopBalanceDiffMapper;

    @Override
    public BaseCrudMapper init() {
        return billShopBalanceDiffMapper;
    }

	@Override
	public BigDecimal getDiffBalanceSum(BillShopBalanceDiff billShopBalanceDiff) {
		return billShopBalanceDiffMapper.getDiffBalanceSum(billShopBalanceDiff);
	}

	@Override
	public BigDecimal getDiffBackAmountSum(BillShopBalanceDiff billShopBalanceDiff) {
		return billShopBalanceDiffMapper.getDiffBackAmountSum(billShopBalanceDiff);
	}

	@Override
	public List<BillShopBalanceDiff>  getSumAmount(Map<String, Object> params)
			throws ServiceException {
		return billShopBalanceDiffMapper.getSumAmount(params);
	}

	@Override
	public int findDiffTrackCount(Map<String, Object> params) throws ServiceException {
		try {
			return billShopBalanceDiffMapper.findDiffTrackCount(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceDiffExport> findDiffTrackPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		try {
			return billShopBalanceDiffMapper.findDiffTrackPage(page, orderByField, orderBy, params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 汇总结算差异金额数据
	 * @param params 参数
	 * @return 结算差异数据汇总对象
	 */
	@Override
	public GatherBillShopBalanceDiffDto gatherBalanceDiff(Map<String, Object> params) {
		return billShopBalanceDiffMapper.gatherBalanceDiff(params);
	}

	/**
	 * 获取页脚汇总数据
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	@Override
	public BillShopBalanceDiffFooterDto getFooterDto(Map<String, Object> params) {
		List<BillShopBalanceDiffFooterDto> list = billShopBalanceDiffMapper.getFooterDto(params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 修改结算差异的状态
	 * @param params 限制参数
	 * @return 影响的行数
	 * @throws ServiceException 异常
	 */
	@Override
	public int modifyStatus(Map<String, Object> params) throws ServiceException {
		try {
			return billShopBalanceDiffMapper.modifyStatus(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceDiffExport> findDiffTrackExport(
			Map<String, Object> params) throws ServiceException {
		try {
			return billShopBalanceDiffMapper.findDiffTrackExport(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int selectlistSearchCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billShopBalanceDiffMapper.selectlistSearchCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceDiff> selectlistSearchByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billShopBalanceDiffMapper.selectlistSearchByPage(page, orderByField, orderBy, params);
	}

	@Override
	public int selectlistDiffTrackCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billShopBalanceDiffMapper.selectlistDiffTrackCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceDiff> selectlistDiffTrackByPage(SimplePage page,
			String orderByField, String orderBy,  Map<String, Object> params)
			throws ServiceException {
		return billShopBalanceDiffMapper.selectlistDiffTrackByPage(page, orderByField, orderBy, params);
	}
}