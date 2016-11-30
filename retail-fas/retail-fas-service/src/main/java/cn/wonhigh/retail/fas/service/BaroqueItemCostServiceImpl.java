/**
 * 
 */
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.BaroqueItemCostDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.dal.database.BaroqueItemCostMapper;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * @author user
 * 
 */
@Service("baroqueItemCostServiceImpl")
public class BaroqueItemCostServiceImpl extends BaseCrudServiceImpl implements BaroqueItemCostService {

	@Resource
	private BaroqueItemCostMapper baroqueItemCostMapper;

	@Override
	public BaseCrudMapper init() {
		return this.baroqueItemCostMapper;
	}

	@Override
	public List<BaroqueItemCostDto> getBaroqueHeadquarterCostList(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		try {
			return this.baroqueItemCostMapper.getBaroqueHeadquarterCostList(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer getBaroqueHeadquarterCostListCount(Map<String, Object> params) throws ServiceException {
		try {
			return this.baroqueItemCostMapper.getBaroqueHeadquarterCostListCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BaroqueItemCostDto> generateBaroqueHeadquarterCost(Map<String, Object> params) throws ServiceException {
		try {
			return this.baroqueItemCostMapper.generateBaroqueHeadquarterCost(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCost(Map<String, Object> params)
			throws ServiceException {
		try {
			return this.baroqueItemCostMapper.generateBaroqueRegionHeadquarterCost(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BaroqueItemCostDto> generateBaroqueRegionHeadquarterCostForFF(Map<String, Object> params)
			throws ServiceException {
		try {
			return this.baroqueItemCostMapper.generateBaroqueRegionHeadquarterCostForFF(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BaroqueItemCostDto> getDiffHeadquarterCostForFF(Map<String, Object> params)
			throws ServiceException {
		try {
			return this.baroqueItemCostMapper.getDiffHeadquarterCostForFF(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void updateHeadquarterCost(BaroqueItemCostDto item) 
			throws ServiceException {
		try {
			 this.baroqueItemCostMapper.updateHeadquarterCost(item);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
