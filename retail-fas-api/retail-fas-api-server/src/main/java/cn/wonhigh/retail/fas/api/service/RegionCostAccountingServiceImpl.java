package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.RegionCostAccountingMapper;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("regionCostAccountingService")
public class RegionCostAccountingServiceImpl implements
		RegionCostAccountingService {
	
	@Resource
	private RegionCostAccountingMapper regionCostAccountingMapper;
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public BigDecimal findRegionCost(String itemNo, String zoneNo,Date effectiveDate)
			throws ServiceException {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("itemNo", itemNo);
			params.put("zoneNo", zoneNo);
			params.put("effectiveDate", effectiveDate);
			RegionCostMaintain regionCostMaintain = regionCostAccountingMapper.findRegionCost(params);
			if (null == regionCostMaintain) {
				return BigDecimal.ZERO;
			}
			return regionCostMaintain.getRegionCost();
		}catch(Exception e)
		{
			log.debug("系统错误-->获取商品地区成本价失败");
    		throw new ServiceException("系统错误-->获取商品地区成本价失败",e);
		}
	}

	@Override
	public BigDecimal findRegionCostByOrderUnitNo(String itemNo,
			String orderUnitNo) throws ServiceException {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("itemNo", itemNo);
			params.put("orderUnitNo", orderUnitNo);
			
			RegionCostMaintain regionCostMaintain = regionCostAccountingMapper.findRegionCostByOrderUnitNo(params);
			if (null == regionCostMaintain) {
				return BigDecimal.ZERO;
			}
			return regionCostMaintain.getRegionCost();
			
		}catch(Exception e)
		{
			log.debug("系统错误-->获取商品地区成本价根据订货单位编号失败");
    		throw new ServiceException("系统错误-->获取商品地区成本价根据订货单位编号失败",e);
		}
	}

	@Override
	public List<RegionCostMaintain> findRegionCost(List<String> itemNos, String zoneNo,Date effectiveDate)
			throws ServiceException {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("array", itemNos);
			params.put("zoneNo", zoneNo);
			params.put("effectiveDate", effectiveDate);
			return regionCostAccountingMapper.findRegionCostList(params);
		}catch(Exception e)
		{
			log.debug("系统错误-->批量获取商品地区成本价失败");
    		throw new ServiceException("系统错误-->批量获取商品地区成本价失败",e);
		}
	}

	@Override
	public Company getCompanyInfoByOrderUnitNo(String orderUnitNo)
			throws ServiceException {
		try{
			return regionCostAccountingMapper.getCompanyInfoByOrderUnitNo(orderUnitNo);
		}catch(Exception e)
		{
    		throw new ServiceException("系统错误-->根据订货单位查询公司编码失败",e);
		}
	}

	@Override
	public FinancialAccount getFinancialAccountByCompanyNo(
			Map<String, Object> financialAccountmMap) throws ServiceException {
		try{
			return regionCostAccountingMapper.getFinancialAccountByCompanyNo(financialAccountmMap);
		}catch(Exception e)
		{
    		throw new ServiceException("系统错误-->根据订货单位查询公司编码失败",e);
		}
	}

	@Override
	public Company findCompanyModel(Map<String, Object> companyMap)
			throws ServiceException {
		try{
			return regionCostAccountingMapper.getCompanyModel(companyMap);
		}catch(Exception e)
		{
    		throw new ServiceException("系统错误-->根据订货单位查询公司编码失败",e);
		}
	}

}
