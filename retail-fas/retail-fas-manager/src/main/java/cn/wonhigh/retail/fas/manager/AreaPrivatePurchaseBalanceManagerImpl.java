/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaPrivatePurchaseManagerImpl  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-9-29 下午4:21:15  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.AreaPrivatePurchaseBalanceService;
import cn.wonhigh.retail.fas.service.BrandUnitService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
@Service("areaPrivatePurchaseBalanceManager")
public class AreaPrivatePurchaseBalanceManagerImpl extends BaseCrudManagerImpl
		implements AreaPrivatePurchaseBalanceManager {
	@Resource
	private AreaPrivatePurchaseBalanceService areaPrivatePurchaseBalanceService;
	@Resource
	private BrandUnitService brandUnitService;
	
	@Override
	protected BaseCrudService init() {
		return areaPrivatePurchaseBalanceService;
	}

	@Override
	public int modifyBillStatus(String[] ids, Map<String,Object> params) {
		return areaPrivatePurchaseBalanceService.modifyBillStatus(ids,params);
	}

	@Override
	public int deleteBalanceBill(String[] ids) throws ManagerException {
		try {
			return areaPrivatePurchaseBalanceService.deleteBalanceBill(ids);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 结算单金额验证
	 */
	@Override
	public BillBalance findBalanceBill(BillBalance formData) {
		String brandUnitNo = formData.getBrandUnitNo();	// 品牌部
		Map<String, Object> params=new HashMap<String, Object>();
		if (!brandUnitNo.equals("")) {
			params.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
			String brandNosWith = brandUnitService.findBrands(params);	// 查询有权限的品牌部下有权限的品牌
			formData.setBrandNo(brandNosWith);
		}else{
			params.put("brandUnitNo", null);
			String brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下的品牌
			formData.setBrandNo(brandNos);
		}
		// 根据结算双方、结算期、品牌部查询结算单信息
		List<BillBalance> lstBillBalance = areaPrivatePurchaseBalanceService.findBalanceList(formData);
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			return lstBillBalance.get(0);
		}
		return null;
	}
	
	/**
	 *  保存自购结算单(单个操作)
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance addBalanceBill(BillBalance billBalance)
			throws Exception {
		String brandUnitNo = billBalance.getBrandUnitNo();	// 品牌部
		String brandNos = "";
		Map<String, Object> params=new HashMap<String, Object>();
		if (!brandUnitNo.equals("")) {
			params.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下有权限的品牌
			billBalance.setBrandNo(brandNos);
		}else{
			params.put("brandUnitNo", null);
			brandNos = brandUnitService.findBrands(params);	// 查询有权限的品牌部下的品牌
			billBalance.setBrandNo(brandNos);
		}
		List<BillBalance> lstBillBalance = areaPrivatePurchaseBalanceService.findBalanceList(billBalance);	// 查询结算信息
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			billBalance.setBrandUnitNo(brandUnitNo);
			billBalance.setBrandNo(brandNos);
			areaPrivatePurchaseBalanceService.addBalanceBill(lstBillBalance,billBalance);// 保存结算信息
			return lstBillBalance.get(0); 
		}
		return null;
	}

	/**
	 * 批量生成地区自购结算单
	 * buyerNo  多个   IN (a,b)
	 * salerNo  多个   IN (a,b)
	 * brandUnitNo 多个 IN (a,b)
	 * balanceStartDate 过滤开始时间  >= 
	 * balanceEndDate 过滤结束时间 <= 
	 * @throws Exception 
	 * 
	 */
	@Override
	public Map<String,Object> addBalanceBillByBatch(BillBalance formData) throws Exception {
		formData.setSalerNo(FasUtil.formatInQueryCondition(formData.getSalerNo()));
		formData.setBuyerNo(FasUtil.formatInQueryCondition(formData.getBuyerNo()));
		formData.setBrandUnitNo(FasUtil.formatInQueryCondition(formData.getBrandUnitNo()));
		List<BillBalance> lstItem =areaPrivatePurchaseBalanceService.findMatchedBalanceBill(formData);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if(lstItem!=null){
			resultMap= areaPrivatePurchaseBalanceService.addBalanceBillByBatch(lstItem,formData);
		}else{
			resultMap.put("flag", false);
		}
		return resultMap;
	}

	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return areaPrivatePurchaseBalanceService.findTotalRow(params);
	}

	@Override
	public Map<String,Object> addBalanceBillBySelect(List<Object> lstItem,
			BillBalance billBalance) throws Exception {
		return areaPrivatePurchaseBalanceService.addBalanceBillBySelect(lstItem,billBalance);
	}

	@Override
	public BillBalance modifyDeducation(List<Object> lstItem,
			BillBalance billBalance) {
		return areaPrivatePurchaseBalanceService.modifyDeducation(lstItem,billBalance);
	}
}
