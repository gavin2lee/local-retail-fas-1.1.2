package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.HqShipmentCollet;
import cn.wonhigh.retail.fas.service.HqShipmentColletService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author Administrator
 * @date 2015-04-13 15:20:45
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("hqShipmentColletManagerImpl")
class HqShipmentColletManagerImpl extends BaseCrudManagerImpl implements
		HqShipmentColletManager {
	@Resource
	private HqShipmentColletService hqShipmentColletService;

	@Override
	public BaseCrudService init() {
		return hqShipmentColletService;
	}

	@Override
	public List<HqShipmentCollet> findHqShipmentColletByCondition(
			Map<String, Object> params, SimplePage page)
			throws ManagerException {
		try {
			return hqShipmentColletService.findHqShipmentColletByCondition(
					params, page);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	/**
	 * 根据条件查询总部出货统计表的记录数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public HqShipmentCollet findHqShipmentColletCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return hqShipmentColletService.findHqShipmentColletCount(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	/**
	 * 根据条件查询退供应商统计表明细
	 * 
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public List<HqShipmentCollet> findRecallSupplierList(Map<String, Object> params, SimplePage page)throws ManagerException {
		try {
			return hqShipmentColletService.findRecallSupplierList(params,page);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	/**
	 * 根据条件查询退供应商统计表明细的记录数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public HqShipmentCollet findRecallSupplierCount(Map<String, Object> params) throws ManagerException {
		try {
			return hqShipmentColletService.findRecallSupplierCount(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	/**
	 * 根据条件查询退供应商统计表汇总
	 * 
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public List<HqShipmentCollet> findReturnSupplierList(Map<String, Object> params, SimplePage page)throws ManagerException {
		try {
			return hqShipmentColletService.findReturnSupplierList(params,
					page);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	/**
	 * 根据条件查询退供应商统计表汇总的记录数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public HqShipmentCollet findReturnSupplierCount(Map<String, Object> params) throws ManagerException {
		try {
			return hqShipmentColletService.findReturnSupplierCount(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

}