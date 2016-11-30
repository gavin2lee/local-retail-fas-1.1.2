package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.HeadquarterCostAccountingMapper;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("headquarterCostAccountingService")
public class HeadquarterCostAccountingServiceImpl implements
		HeadquarterCostAccountingService {

	@Resource
	private HeadquarterCostAccountingMapper headquarterCostAccountingMapper;
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public BigDecimal findHeadquarterCost(String itemNo,Date effectiveDate)
			throws ServiceException {
		try{
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("itemNo", itemNo);
			params.put("effectiveDate", effectiveDate);
			return headquarterCostAccountingMapper.findHeadquarterCost(params);
		}catch(Exception e)
		{
			log.debug("系统错误-->获取商品总部成本价失败");
			throw new ServiceException("获取商品总部成本价失败",e);
		}
	}

	@Override
	public int qryHeadquarterCostExist(HeadquarterCostMaintain headqarterCostMaintain)
			throws ServiceException {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("itemNo", headqarterCostMaintain.getItemNo());
			params.put("supplierNo", headqarterCostMaintain.getSupplierNo());
			
			Integer retCountInteger = headquarterCostAccountingMapper.qryHeadquarterCostExist(params);
			
			if (null == retCountInteger) {
				return 0;
			}
			
			return retCountInteger;
			
		}catch(Exception e){
			log.debug("系统错误-->校验商品厂进价是否可以修改失败");
			throw new ServiceException("校验商品厂进价是否可以修改失败",e);
		}
	}
	
	@Override
	public int qryHeadquarterCostIsEffective(HeadquarterCostMaintain headqarterCostMaintain)
			throws ServiceException {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("itemNo", headqarterCostMaintain.getItemNo());
			params.put("supplierNo", headqarterCostMaintain.getSupplierNo());
			params.put("effectiveTime", headqarterCostMaintain.getEffectiveTime());
			
			Integer retCountInteger = headquarterCostAccountingMapper.qryHeadquarterCostIsEffective(params);
			
			if (null == retCountInteger) {
				return 0;
			}
			
			return retCountInteger;
			
		}catch(Exception e){
			log.debug("系统错误-->校验商品厂进价是否可以修改失败");
			throw new ServiceException("校验商品厂进价是否可以修改失败",e);
		}
	}

}
