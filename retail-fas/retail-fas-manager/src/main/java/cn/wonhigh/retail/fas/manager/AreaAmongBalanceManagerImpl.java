/**  
 *   
 * 项目名称：retail-fas-manager  
 * 类名称：AreaAmongBalanceManagerImpl  
 * 类描述：
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-24 下午12:09:56  
 * @version       
 */
package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.AreaAmongBalanceService;
import cn.wonhigh.retail.fas.service.BrandUnitService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("areaAmongBalanceManager")
public class AreaAmongBalanceManagerImpl extends BaseCrudManagerImpl implements
		AreaAmongBalanceManager {
	@Resource
	private AreaAmongBalanceService areaAmongBalanceService;
	@Resource
	private BrandUnitService brandUnitService;

	@Override
	public BaseCrudService init() {
		return areaAmongBalanceService;
	}

	/**
	 * 保存结算单(选单)
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public Map<String, Object> addBalanceBillBySelect(List<Object> lstItem,
			BillBalance billBalance) throws Exception {
		return areaAmongBalanceService.addBalanceBillBySelect(lstItem,billBalance);
	}

	/**
	 * 结算单金额验证
	 */
	@Override
	public BillBalance findOutList(BillBalance formData) {
		String brandUnitNo = formData.getBrandUnitNo();	// 获取品牌部
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
		// 查询结算信息
		List<BillBalance> lstBillBalance=areaAmongBalanceService.findBalanceBillList(formData);
		if(lstBillBalance != null && lstBillBalance.size() > 0){
			return lstBillBalance.get(0);
		}
		return null;
	}
	
	/**
	 * 单个保存结算单
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
		List<BillBalance> lstBillBalance = areaAmongBalanceService.findBalanceBillList(formData);
		// 保存结算信息
		if (lstBillBalance != null && lstBillBalance.size() > 0) {
			formData.setBrandUnitNo(brandUnitNo);
			formData.setBrandNo(brandNos);
			areaAmongBalanceService.addBalanceBill(lstBillBalance, formData);
			return lstBillBalance.get(0);
		}
		return null;
	}
	
	/**
	 * 批量生成结算单
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public Map<String,Object> addBalanceBillByBatch(BillBalance formData) throws Exception {
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
		List<BillBalance> lstItem =areaAmongBalanceService.findMatchedBalanceList(formData);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if(lstItem!=null){
			 resultMap=areaAmongBalanceService.addBalanceBillBatch(lstItem,formData);
		}else{
			resultMap.put("flag", false);
		}
		return resultMap;
	}

	@Override
	public int modifyBillStatus(String[] ids, Map<String,Object> params) {
		return areaAmongBalanceService.modifyBillStatus(ids, params);
	}

	@Override
	public int deleteBalanceBill(String[] ids) throws ManagerException {
		try {
			return areaAmongBalanceService.deleteBalanceBill(ids);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return areaAmongBalanceService.findTotalRow(params);
	}

	@Override
	public List<TotalDto> findReceivableAmountTotal(Map<String, Object> params) {
		return areaAmongBalanceService.findReceivableAmountTotal(params);
	}

	@Override
	public BillBalance modifyDeducation(List<Object> lstItem,
			BillBalance billBalance) {
		return areaAmongBalanceService.modifyDeducation(lstItem,billBalance);
	}
}
