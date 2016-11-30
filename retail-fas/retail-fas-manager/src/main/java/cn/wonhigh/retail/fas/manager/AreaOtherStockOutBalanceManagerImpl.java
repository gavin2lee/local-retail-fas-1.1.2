package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.AreaOtherBalanceService;
import cn.wonhigh.retail.fas.service.BrandUnitService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("areaOtherStockOutBalanceManager")
class AreaOtherStockOutBalanceManagerImpl extends BaseCrudManagerImpl implements AreaOtherStockOutBalanceManager {
	@Resource
	private AreaOtherBalanceService areaOtherBalanceService;
	@Resource
	private  BrandUnitService brandUnitService;
	
	@Override
	protected BaseCrudService init() {
		return areaOtherBalanceService;
	}
	@Override
	public int modifyBillStatus(String[] ids, Map<String,Object> params) {
		return areaOtherBalanceService.modifyBalanceBillStatus(ids, params);
	}

	@Override
	public int deleteBalanceBill(String[] ids) throws ManagerException {
		try {
			return areaOtherBalanceService.deleteBalanceBill(ids);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 *  出库金额验证
	 */
	@Override
	public BillBalance findBalanceBill(BillBalance formData) {
		String brandNo = formData.getBrandNo();	// 品牌
		if (!brandNo.equals("")) {
			String brandNos = "'" + brandNo.replaceAll(",", "','") + "'";
			formData.setBrandNo(brandNos);
		}
		// 查询结算信息
		List<BillBalance> lstBillBalance = areaOtherBalanceService
				.findBalanceBillList(formData);
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			return lstBillBalance.get(0);
		}
		return null;
	}

	/**
	 *  保存结算单(单个操作)
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance addBalanceBill(BillBalance formData) throws Exception {
		String brandUnitNo = formData.getBrandUnitNo(); // 品牌部
		String brandNos = "";
		Map<String, Object> params=new HashMap<String, Object>();
		if (!brandUnitNo.equals("")) {
			params.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下有权限的品牌
			formData.setBrandNo(brandNos);
		}else{
			params.put("brandUnitNo", null);
			brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下的品牌
			formData.setBrandNo(brandNos);
		}
		formData.setBrandUnitNo(FasUtil.formatInQueryCondition(brandUnitNo));
		// 查询结算信息
		List<BillBalance> lstBillBalance = areaOtherBalanceService.findBalanceBillList(formData);
		// 保存结算信息
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			formData.setBrandUnitNo(brandUnitNo);
			formData.setBrandNo(brandNos);
			areaOtherBalanceService.addBalanceBill(lstBillBalance, formData);
			return lstBillBalance.get(0);
		}
		return null;
	}

	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return areaOtherBalanceService.findTotalRow(params);
	}

	/**
	 * 批量生成结算单
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addBalanceBillByBatch(BillBalance formData)
			throws Exception {
		formData.setSalerNo(FasUtil.formatInQueryCondition(formData.getSalerNo()));
		formData.setBuyerNo(FasUtil.formatInQueryCondition(formData.getBuyerNo()));
		formData.setBrandUnitNo(formData.getBrandUnitNo());
		// 通过判断品牌部是否为空，设置品牌部和品牌
		String brandUnitNo = formData.getBrandUnitNo(); 
		Map<String, Object> params=new HashMap<String, Object>();
		String brandNos = "";
		if (!brandUnitNo.equals("")) {
			params.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下有权限的品牌
			formData.setBrandNo(brandNos);
		}else{
			params.put("brandUnitNo", null);
			brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下的所有品牌
			formData.setBrandNo(brandNos);
		}
		List<BillBalance> lstItem =areaOtherBalanceService.findMatchedBalanceBill(formData);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if(lstItem!=null){
			resultMap = areaOtherBalanceService.addBalanceBillBatch(lstItem,formData);
		}else {
			resultMap.put("flag", false);
		}
		return resultMap;
	}

	@Override
	public Map<String,Object> addBalanceBillBySelect(List<Object> lstItem,
			BillBalance billBalance) throws Exception {
		return areaOtherBalanceService.addBalanceBillBySelect(lstItem,billBalance);
	}
}