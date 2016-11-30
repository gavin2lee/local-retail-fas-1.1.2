package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.service.OrderUnitService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author huang.xb1
 * @date  2014-07-28 14:19:21
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
@Service("orderUnitManager")
class OrderUnitManagerImpl extends BaseCrudManagerImpl implements OrderUnitManager {
	@Resource
	private OrderUnitService orderUnitService;

//	@Resource
//	private OrderBrandStoreRelService orderBrandStoreRelService;

//	@Resource
//	private CodingRuleService codingRuleService;

	@Override
	public BaseCrudService init() {
		return orderUnitService;
	}

	@Override
	public OrderUnit getDetail(OrderUnit orderUnit) throws ManagerException {
		return null;
	}

//	@Override
//	public int saveAll(Map<CommonOperatorEnum, List<OrderBrandStoreRel>> params,OrderUnit orderUnit)
//			throws ManagerException {
//		try {
//			orderUnitService.modifyById(orderUnit);
////			orderBrandStoreRelService.save(params);
//		} catch (ServiceException e) {
//			throw new ManagerException(e);
//		}
//		return 0;
//	}

	@Override
	public int editOrderUnit(OrderUnit OrderUnit) throws ManagerException {
		return 0;
	}


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int delOrderUnit(Map<CommonOperatorEnum, List<OrderUnit>> params) throws ManagerException {
			try {
				int count = 0;
				for (Entry<CommonOperatorEnum, List<OrderUnit>> param : params.entrySet()) {
					if (param.getKey().equals(CommonOperatorEnum.DELETED)) {
						List<OrderUnit> list = params.get(CommonOperatorEnum.DELETED);
						if (null != list && list.size() > 0) {
							for (OrderUnit orderUnit : list) {
								//删除订货单位数据之前先删除与该记录相关的中间表中的数据
								OrderUnit orderUnitDel=orderUnitService.findById(orderUnit);
//								orderBrandStoreRelService.deleteByOrderUnitNo(
//										orderUnitDel.getOrderUnitNo());
								count += orderUnitService.deleteById(orderUnit);
							}
						}
				    }
				}
				return count;
			}catch (ServiceException e) {
				throw new ManagerException(e.getMessage(), e);
			}
			
		}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
//	public int add(List<OrderBrandStoreRel> list, OrderUnit orderUnit) throws ManagerException {
//		try {
//			orderUnit.setOrderUnitNo(codingRuleService.getSerialNo("ORDERUNIT_NO"));
//			orderUnitService.add(orderUnit);
//			
//			for(OrderBrandStoreRel rel:list){
//				
//				rel.setOrderUnitNo(orderUnit.getOrderUnitNo());
//				orderBrandStoreRelService.add(rel);
//			}
//		} catch (ServiceException e) {
//			throw new ManagerException();
//		} catch (Exception e) {
//			throw new ManagerException("添加订货单位出错！", e);
//		}
//		return 0;
//
//	}
 }