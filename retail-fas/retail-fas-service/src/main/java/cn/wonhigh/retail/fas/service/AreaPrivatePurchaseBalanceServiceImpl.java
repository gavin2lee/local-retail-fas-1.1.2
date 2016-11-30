/**  
 *   
 * 项目名称：retail-fas-service  
 * 类名称：AreaPrivatePurchaseServiceImpl  
 * 类描述：地区自购结算业务处理
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-29 下午4:24:46  
 * @version       
 */
package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.AreaPrivatePurchaseBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BrandUnitMapper;
import cn.wonhigh.retail.fas.dal.database.OtherDeductionMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("areaPrivatePurchaseBalanceService")
public class AreaPrivatePurchaseBalanceServiceImpl extends BaseCrudServiceImpl
		implements AreaPrivatePurchaseBalanceService {
	@Resource
	private AreaPrivatePurchaseBalanceMapper areaPrivatePurchaseBalanceMapper;
	@Resource
	private BillBalanceMapper billBalanceMapper;
	@Resource
	private OtherDeductionMapper otherDeductionMapper;
	@Resource
	private CommonUtilService commonUtilService;
	@Resource
	private BrandUnitMapper brandUnitMapper;
	@Override
	public BaseCrudMapper init() {
		return areaPrivatePurchaseBalanceMapper;
	}

	@Override
	public int modifyBillStatus(String[] ids, Map<String, Object> params) {
		int n = 0;
		for (int i = 0; i < ids.length; i++) {
			params.put("billNo", ids[i]);
			n = areaPrivatePurchaseBalanceMapper.updateBillStatus(params);
		}
		return n;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int deleteBalanceBill(String[] ids) throws ServiceException {
		try {
			int iCount = 0;
			for (int i = 0; i < ids.length; i++) {
				// 回写结算单编号为空
				areaPrivatePurchaseBalanceMapper.updateBuyBalanceNoToNull(ids[i]);
				//清空扣项信息
				billBalanceMapper.clearDeductionBalanceNo(ids[i]);
				// 删除结算单
				iCount = areaPrivatePurchaseBalanceMapper.deleteBalanceBill(ids[i]);
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> findBalanceList(BillBalance billBalance) {
		List<BillBalance> list = areaPrivatePurchaseBalanceMapper
				.selectAreaPrivatePurchaseBalanceBill(billBalance);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 *  单个生成结算单
	 * @throws Exception 
	 */
	@Override
	public void addBalanceBill(List<BillBalance> lstBillBalance,
			BillBalance billBalance) throws Exception {
		try {
			for (BillBalance bill : lstBillBalance) {
				initBillInfo(bill, billBalance);
				validate(bill);
				areaPrivatePurchaseBalanceMapper.updateBuyBalanceNo(bill);	// 回写结算单编号到入库明细表
				bill.setBrandNo(null);
				billBalanceMapper.updateDeductionBalanceNo(bill);//回写结算单号到其他扣项
				areaPrivatePurchaseBalanceMapper.insertSelective(bill);	// 保存结算单
			}
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);

		}
	}

	/**
	 * 保存地区自购结算单(单个操作)
	 * @param bill
	 * @param billBalance
	 * @throws Exception 
	 */
	private void initBillInfo(BillBalance bill, BillBalance fromPage) throws Exception {
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.AREA_BUY.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getBuyerNo(),null,"RP"));// 生成结算单编码		
		bill.setBillName(fromPage.getBillName());
		bill.setBuyerNo(fromPage.getBuyerNo()); 
		bill.setBuyerName(fromPage.getBuyerName()); 
		bill.setSalerNo(fromPage.getSalerNo()); 
		bill.setSalerName(fromPage.getSalerName()); 
		bill.setStatus(fromPage.getStatus()); 
		bill.setBrandNo(fromPage.getBrandNo()); 
		bill.setBrandUnitNo(fromPage.getBrandUnitNo());
		bill.setBrandUnitName(fromPage.getBrandUnitName());
		bill.setBalanceStartDate(fromPage.getBalanceStartDate()); 
		bill.setBalanceEndDate(fromPage.getBalanceEndDate()); 
		bill.setBalanceDate(fromPage.getBalanceDate());
		bill.setCurrency(fromPage.getCurrency());
		bill.setCreateUser(fromPage.getCreateUser());
		bill.setCreateTime(DateUtil.getCurrentDateTime());
		bill.setRemark(fromPage.getRemark()); 
	}

	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return areaPrivatePurchaseBalanceMapper.selectTotalRow(params);
	}

	/**
	 * 查询匹配的结算单
	 */
	@Override
	public List<BillBalance> findMatchedBalanceBill(
			BillBalance billBalance) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("salerNo", billBalance.getSalerNo());
		params.put("buyerNo", billBalance.getBuyerNo());
		params.put("balanceStartDate", billBalance.getBalanceStartDate());
		params.put("balanceEndDate", billBalance.getBalanceEndDate());
		params.put("brandUnitNo", billBalance.getBrandUnitNo());
		List<BillBalance> list = areaPrivatePurchaseBalanceMapper
				.selectMatchedBalanceBill(params);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 批量生成地区自购结算单
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addBalanceBillByBatch(List<BillBalance> billList,BillBalance formData) throws Exception  {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		BillBalance bill = null;
		for (BillBalance fromSelect : billList) {
			bill=new BillBalance();
			initCreateBillInfo(bill, fromSelect, formData);
			validate(bill); 
			areaPrivatePurchaseBalanceMapper.insertSelective(bill);
			String brandUnitNo = bill.getBrandUnitNo(); // 品牌部
			String brandNos = "";
			if (!brandUnitNo.equals("")) {
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
				brandNos = brandUnitMapper.selectBrandNos(params); // 查询品牌部下的品牌
				bill.setBrandNo(brandNos);
			}
			areaPrivatePurchaseBalanceMapper.updateBuyBalanceNo(bill); // 回写结算单编号
			bill.setBrandNo(null);
			billBalanceMapper.updateDeductionBalanceNo(bill);	// 回写结算单号到其他扣项
			
			list.add(bill.getBillNo());
			resultMap.put("billNos",list);
			resultMap.put("bill", bill);//默认返回第一个
			resultMap.put("flag", true);
		}
		return resultMap;
	}

	/**
	 * 保存地区自购结算单(批量操作)
	 * @param bill
	 * @param billBalance
	 * @param b
	 * @throws Exception 
	 */
	private void initCreateBillInfo(BillBalance bill, BillBalance fromSelect,BillBalance formData
			) throws Exception {
		BeanUtils.copyProperties(fromSelect, bill);
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.AREA_BUY.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromSelect.getBuyerNo(),null,"RP"));// 生成结算单编码
		//保存前端传来的数据
		bill.setStatus(new Integer("0"));
		bill.setBalanceStartDate(formData.getBalanceStartDate());
		bill.setBalanceEndDate(formData.getBalanceEndDate());
		bill.setBalanceDate(formData.getBalanceDate());
		bill.setCreateUser(formData.getCreateUser());
		bill.setCreateTime(DateUtil.getCurrentDateTime());
	}

	/**
	 * 生成地区自购结算单(选单)
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addBalanceBillBySelect(List<Object> lstItem,
			BillBalance fromPage) throws Exception {
		BillBalance bill=new BillBalance();
		BeanUtils.copyProperties(fromPage, bill);
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.AREA_BUY.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getBuyerNo(),null,"RP"));// 生成结算单编码		
		bill.setStatus(new Integer("0")); 
		bill.setCreateTime(DateUtil.getCurrentDateTime());
	
		BigDecimal entryAmount = new BigDecimal(0);	 // 入库金额
		Integer entryQty = new Integer("0");		 // 入库数量
		for (Object object : lstItem) {
			BillBuyBalance buy = (BillBuyBalance) object;
			entryAmount = entryAmount.add(buy.getCost().multiply(new BigDecimal(buy.getReceiveQty())));
			entryQty+=buy.getReceiveQty();
		}
		
		Integer deductionQty = new Integer("0");       // 扣项数量
		BigDecimal deductionAmount = new BigDecimal(0);// 扣项金额
		// 查询其他扣项
		OtherDeduction otherDeduction = otherDeductionMapper.selectOtherDeductionByBillBalance(bill);
		if (otherDeduction != null) {
			deductionQty = otherDeduction.getDeductionQty();
			deductionAmount = otherDeduction.getDeductionAmount();
		}
		bill.setEntryQty(entryQty);
		bill.setDeductionQty(deductionQty);
		bill.setBalanceQty(entryQty); //不用减去扣项数量
		bill.setEntryAmount(entryAmount);
		bill.setDeductionAmount(deductionAmount);
		bill.setBalanceAmount(entryAmount.subtract(deductionAmount));
		
		//判断结算金额是否小于等于0
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", bill.getBillNo());
		String brandUnitNo = fromPage.getBrandUnitNo();  // 品牌部
		String brandNos = "";
		if (!brandUnitNo.equals("")) {
			Map<String, Object> p=new HashMap<String, Object>();
			p.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitMapper.selectBrandNos(p);	// 查询品牌部下的品牌
			params.put("brandNo", brandNos);
		}
		if (lstItem.size() > 0 && lstItem != null) {
			for (Object object : lstItem) {
				BillBuyBalance buy = (BillBuyBalance) object;
				params.put("id", buy.getId());
				areaPrivatePurchaseBalanceMapper.updateBuyBalanceNoBySelect(params); //回写结算单号
				entryAmount = entryAmount.add(buy.getCost().multiply(new BigDecimal(buy.getReceiveQty())));
				entryQty += buy.getReceiveQty();
			}
			billBalanceMapper.updateDeductionBalanceNo(bill);// 回写结算单号到其他扣项
			billBalanceMapper.insertSelective(bill);
			resultMap.put("flag", true);
			resultMap.put("bill", bill);
		}else{
			resultMap.put("flag", false);
		}
		return resultMap;
	}

	/**
	 * 地区自购结算单(扣项调整)
	 */
	@Override
	public BillBalance modifyDeducation(List<Object> lstItem,
			BillBalance fromPage) {
		BigDecimal paymentAmount = new BigDecimal(0); // 应付金额
		BigDecimal entryAmount = fromPage.getEntryAmount(); // 入库金额
		BigDecimal deductionAmount = new BigDecimal(0); // 扣项金额
		Integer deducationQty = new Integer("0"); // 扣项数量
		if (StringUtils.isNotBlank(fromPage.getBillNo())) {
			// 清空没有选择的扣项
			billBalanceMapper.clearDeductionBalanceNo(fromPage.getBillNo());
			for (Object object : lstItem) {
				OtherDeduction deduction = (OtherDeduction) object;
				deduction.setBalanceNo(fromPage.getBillNo());
				// 回写结算单号到扣项
				otherDeductionMapper.updateByPrimaryKeySelective(deduction);
				deductionAmount = deductionAmount.add(deduction
						.getDeductionAmount());
				deducationQty += deduction.getDeductionQty();
			}
			// 计算扣项金额和应付金额
			paymentAmount = entryAmount.subtract(deductionAmount);
			fromPage.setDeductionQty(deducationQty);
			fromPage.setBalanceAmount(paymentAmount);
			fromPage.setDeductionAmount(deductionAmount);
		}
		return fromPage;
	}
}
