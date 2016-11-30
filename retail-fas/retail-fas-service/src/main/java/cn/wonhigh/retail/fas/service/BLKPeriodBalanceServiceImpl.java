package cn.wonhigh.retail.fas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;
import cn.wonhigh.retail.fas.common.model.OrgUnitBrandRel;
import cn.wonhigh.retail.fas.dal.database.BLKPeriodBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.OrgUnitBrandRelMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("blkPeriodBalanceService")
public class BLKPeriodBalanceServiceImpl extends BaseCrudServiceImpl implements BLKPeriodBalanceService {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BLKPeriodBalanceServiceImpl.class);
	
	@Resource
	private BLKPeriodBalanceMapper blkPeriodBalanceMapper;
	
	@Resource
	private OrgUnitBrandRelMapper orgUnitBrandRelMapper;
	
	@Override
	public BaseCrudMapper init() {
		return blkPeriodBalanceMapper;
	}

	@Override
	public BLKPeriodBalance findBLKPeriodBalanceSubTotalCount(Map<String, Object> params) throws ServiceException {
		try {
			return blkPeriodBalanceMapper.findBLKPeriodBalanceSubTotalCount(params);
		} catch (Exception e) {
			LOGGER.error("按小计查询巴洛克公司期间结存总计失败");
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BLKPeriodBalance> findBLKPeriodBalanceSubTotalPages(SimplePage page, String orderBy, String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return blkPeriodBalanceMapper.findBLKPeriodBalanceSubTotalPages(page, orderBy, orderByField, params);
		} catch (Exception e) {
			LOGGER.error("按小计查询巴洛克公司期间结存明细失败");
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BLKPeriodBalance findBLKPeriodBalanceCount(Map<String, Object> params) throws ServiceException {
		try {
			return blkPeriodBalanceMapper.findBLKPeriodBalanceCount(params);
		} catch (Exception e) {
			LOGGER.error("查询巴洛克公司期间结存总计失败");
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BLKPeriodBalance> findBLKPeriodBalancePages(SimplePage page, String orderBy, String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return blkPeriodBalanceMapper.findBLKPeriodBalancePages(page, orderBy, orderByField, params);
		} catch (Exception e) {
			LOGGER.error("查询巴洛克公司期间结存明细失败");
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int deleteCompanyMonthPeriodBalance(ItemCostConditionDto dto) throws ServiceException {
		try {
			return blkPeriodBalanceMapper.deleteCompanyMonthPeriodBalance(dto);
		} catch (Exception e) {
			LOGGER.error("################"+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部删除店铺当月结存失败", e);
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public void transferInventoryBookToPeriodBalance(ItemCostConditionDto itemCostConditionDto) throws ServiceException {
		try {
			LOGGER.info("################"+itemCostConditionDto.getCompanyNo()+"公司"+itemCostConditionDto.getCurrentYear()+"年"+itemCostConditionDto.getCurrentMonth()+"月"+itemCostConditionDto.getBrandUnitNo()+"品牌部开始执行店铺结存生产逻辑   ###################");
			
			HashMap<String,Object> orderUnitMap=new HashMap<String,Object>();
			orderUnitMap.put("companyNo", itemCostConditionDto.getCompanyNo());
			orderUnitMap.put("brandNos", itemCostConditionDto.getBrandNos());
			List<OrgUnitBrandRel> list=orgUnitBrandRelMapper.selectByOrderUnitNoParams(orderUnitMap);//查询公司下的所有订货单位
			List<String> orderUnitNos = new ArrayList<String>();
			for(OrgUnitBrandRel orgUnitBrandRel:list){
				orderUnitNos.add(orgUnitBrandRel.getOrderUnitNo());
			}
			
			itemCostConditionDto.setOrderUnitNos(orderUnitNos);
			blkPeriodBalanceMapper.transferInventoryBookToPeriodBalance(itemCostConditionDto);// 从流水表汇总出 本期发生的业务结存数据 到结存表
			// 结转公司上期存在的结存，而本期没有发生业务的数据到本月
			blkPeriodBalanceMapper.transferLastPeriodBalanceToCurrent(itemCostConditionDto);
			LOGGER.info("################"+itemCostConditionDto.getCompanyNo()+"公司"+itemCostConditionDto.getCurrentYear()+"年"+itemCostConditionDto.getCurrentMonth()+"月"+itemCostConditionDto.getBrandUnitNo()+"品牌部执行完成店铺结存生成逻辑   ###################");
		} catch (Exception e) {
			LOGGER.error(itemCostConditionDto.getCompanyNo()+"公司"+itemCostConditionDto.getCurrentYear()+"年"+itemCostConditionDto.getCurrentMonth()+"月"+itemCostConditionDto.getBrandUnitNo()+"品牌部按店铺汇总结存失败", e);
			throw new ServiceException(e.getMessage(),e);
		}		
		
	}

	@Override
	public int selectStoreBanalceCount(Map<String, Object> params) throws ServiceException {
		try {
			return blkPeriodBalanceMapper.selectStoreBanalceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<BLKPeriodBalance> selectStoreBalanceList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return blkPeriodBalanceMapper.selectStoreBalanceList(page, orderBy, orderByField, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

}
