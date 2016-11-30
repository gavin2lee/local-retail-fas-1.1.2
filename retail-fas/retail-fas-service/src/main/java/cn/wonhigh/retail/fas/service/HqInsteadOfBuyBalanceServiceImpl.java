/**
 * title:HqInsteadOfBuyBalanceServiceImpl.java
 * package:cn.wonhigh.retail.fas.service
 * description:总部代采结算单业务处理
 * auther:user
 * date:2015-4-11 下午4:33:39
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
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BrandUnitMapper;
import cn.wonhigh.retail.fas.dal.database.FinancialAccountMapper;
import cn.wonhigh.retail.fas.dal.database.HqInsteadOfBuyBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.OtherDeductionMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("hqInsteadOfBuyBalanceService")
public class HqInsteadOfBuyBalanceServiceImpl extends BaseCrudServiceImpl
		implements HqInsteadOfBuyBalanceService {
	
	@Resource
	private HqInsteadOfBuyBalanceMapper hqInsteadOfBuyBalanceMapper;
	@Resource
	private BillBalanceMapper billBalanceMapper;
	@Resource
	private OtherDeductionMapper otherDeductionMapper;
	@Resource
	private CommonUtilService commonUtilService;
	@Resource
	private BrandUnitMapper brandUnitMapper;
	@Resource
	private FinancialAccountMapper financialAccountMapper;
	
	@Override
	public BaseCrudMapper init() {
		return hqInsteadOfBuyBalanceMapper;
	}

	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return hqInsteadOfBuyBalanceMapper.selectTotalRow(params);
	}
	
	@Override
	public List<BillBalance> findHqBalanceList(BillBalance billBalance) {
		List<BillBalance> list = hqInsteadOfBuyBalanceMapper
				.selectHqBuyBalanceBill(billBalance);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}
	
	@Override
	public void addHqBalanceBill(List<BillBalance> lstBillBalance,
			BillBalance formData) throws Exception {
		try {
			for (BillBalance fromSelect : lstBillBalance) {
				initHqBillInfo(fromSelect, formData); 
				validate(fromSelect); 
				hqInsteadOfBuyBalanceMapper.updateHqBuyBalanceNo(fromSelect); // 回写结算单号
				fromSelect.setBrandNo(null);
				billBalanceMapper.updateDeductionBalanceNo(fromSelect);	// 回写结算单号到其他扣项
				hqInsteadOfBuyBalanceMapper.insertSelective(fromSelect); 
				billBalanceMapper.updateBuyPurchasePrice(fromSelect);	// 反写单据采购价
			}
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);

		}
		
	}
	
	/**
	 * 单个保存结算单
	 * @param bill
	 * @param billBalance
	 * @throws Exception 
	 */
	private void initHqBillInfo(BillBalance fromSelect, BillBalance fromPage) throws Exception {
		fromSelect.setId(UUIDGenerator.getUUID());
		fromSelect.setBalanceType(BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo()); // 结算类型
		fromSelect.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getBuyerNo(),null,"GA"));// 生成结算单编码		
		fromSelect.setBillName(fromPage.getBillName());
		fromSelect.setStatus(new Integer(0)); 
		fromSelect.setBrandUnitNo(fromPage.getBrandUnitNo());
		fromSelect.setBrandUnitName(fromPage.getBrandUnitName());
		fromSelect.setBrandNo(fromPage.getBrandNo());
		fromSelect.setBrandName(fromPage.getBrandName());
		fromSelect.setBalanceStartDate(fromPage.getBalanceStartDate()); 
		fromSelect.setBalanceEndDate(fromPage.getBalanceEndDate()); 
		fromSelect.setBalanceDate(fromPage.getBalanceDate());
		fromSelect.setCurrency(fromPage.getCurrency());
		fromSelect.setCreateUser(fromPage.getCreateUser());
		fromSelect.setCreateTime(DateUtil.getCurrentDateTime());
		fromSelect.setRemark(fromPage.getRemark()); 
	}
	
	/**
	 * 生成总部代采结算单(选单操作)
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addHqBalanceBillBySelect(List<Object> lstItem,
			BillBalance fromPage) throws Exception {
		BillBalance bill = new BillBalance();
		BeanUtils.copyProperties(fromPage, bill);
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo()); 
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getBuyerNo(),null,"GA"));
		bill.setStatus(new Integer("0"));
		bill.setCreateTime(DateUtil.getCurrentDateTime());

		Integer entryQty = new Integer("0"); // 入库数量
		Integer returnQty = new Integer("0"); // 退残数量
		BigDecimal entryAmount = new BigDecimal(0); // 入库金额
		BigDecimal returnAmount = new BigDecimal(0); // 退残金额
		BigDecimal balanceAmount = new BigDecimal(0); // 应付金额
		for (Object object : lstItem) {
			BillBuyBalance buy = (BillBuyBalance) object;
			if(buy.getBillType().equals(BillTypeEnums.ASN.getRequestId())){
				entryAmount = entryAmount.add(buy.getCost().multiply(new BigDecimal(buy.getSendQty())));
				entryQty += buy.getSendQty();
			}
			if(buy.getBillType().equals(BillTypeEnums.RETURNOWN.getRequestId())){
				returnAmount = returnAmount.add(buy.getCost().multiply(new BigDecimal(buy.getSendQty())));
				returnQty += buy.getSendQty();
			}
		}
		
		Integer deductionQty = new Integer("0"); 		// 扣项数量
		BigDecimal deductionAmount = new BigDecimal(0); // 扣项金额
		// 查询其他扣项
		OtherDeduction otherDeduction = otherDeductionMapper.selectOtherDeductionByBillBalance(bill);
		if (otherDeduction != null) {
			deductionQty = otherDeduction.getDeductionQty();
			deductionAmount = otherDeduction.getDeductionAmount();
		}
		
		bill.setEntryQty(entryQty);
		bill.setReturnQty(returnQty);
		bill.setDeductionQty(deductionQty);
		bill.setBalanceQty(entryQty + returnQty); // 不用减去扣项数量
		bill.setEntryAmount(entryAmount);
		bill.setDeductionAmount(deductionAmount);
		bill.setReturnAmount(returnAmount);
		balanceAmount = entryAmount.add(returnAmount).subtract(deductionAmount);
		bill.setBalanceAmount(balanceAmount);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", bill.getBillNo());
		if(lstItem.size() > 0 && lstItem != null){
			for (Object object : lstItem) {
				BillBuyBalance buy = (BillBuyBalance) object;
				params.put("id", buy.getId());
				hqInsteadOfBuyBalanceMapper.updateBuyHqBalanceNoBySelect(params);
				if(buy.getBillType().equals(BillTypeEnums.ASN.getRequestId())){
					entryQty += buy.getSendQty();
					entryAmount = entryAmount.add(buy.getCost().multiply(new BigDecimal(buy.getSendQty())));
				}
				if(buy.getBillType().equals(BillTypeEnums.RETURNOWN.getRequestId())){
					returnQty += buy.getSendQty();
					returnAmount = returnAmount.add(buy.getCost().multiply(new BigDecimal(buy.getSendQty())));
				}
			}
			billBalanceMapper.updateDeductionBalanceNo(bill);// 回写结算单号到其他扣项
			billBalanceMapper.insertSelective(bill);
			billBalanceMapper.updateBuyPurchasePrice(bill);	// 反写单据采购价
			resultMap.put("bill", bill);
			resultMap.put("flag", true);
		}else{
			resultMap.put("flag", false);
		}
		return resultMap;
	}
	
	/**
	 * 总部代采结算单(扣项调整)
	 */
	@Override
	public BillBalance modifyHqDeducation(List<Object> lstItem,
			BillBalance fromPage) {
		BigDecimal paymentAmount = new BigDecimal(0);
		BigDecimal deductionAmount = new BigDecimal(0);
		BigDecimal entryAmount = fromPage.getEntryAmount();
		BigDecimal returnAmount = fromPage.getReturnAmount();
		Integer deducationQty = new Integer("0"); 
		Integer balanceQty = new Integer("0"); 
		Integer entryQty = fromPage.getEntryQty();
		Integer returnQty = fromPage.getReturnQty(); 
		if (StringUtils.isNotBlank(fromPage.getBillNo())) {
			billBalanceMapper.clearDeductionBalanceNo(fromPage.getBillNo());	// 清空没有选择的扣项
			for (Object object : lstItem) {
				OtherDeduction deduction = (OtherDeduction) object;
				deduction.setBalanceNo(fromPage.getBillNo());
				otherDeductionMapper.updateByPrimaryKeySelective(deduction);	// 回写结算单号到扣项
				deductionAmount = deductionAmount.add(deduction.getDeductionAmount());
				deducationQty += deduction.getDeductionQty();
			}
			paymentAmount = entryAmount.subtract(deductionAmount).add(returnAmount);
			balanceQty = entryQty + returnQty;
			fromPage.setDeductionQty(deducationQty);
			fromPage.setBalanceQty(balanceQty);
			fromPage.setBalanceAmount(paymentAmount);
			fromPage.setDeductionAmount(deductionAmount);
		}
		return fromPage;
	}
	
	@Override
	public List<BillBalance> findMatchedHqBalanceBill(BillBalance billBalance) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("salerNo", billBalance.getSalerNo());
		String areaCompnays=financialAccountMapper.selectNotLeadRoleCompanyNos(null);
		if(StringUtils.isBlank(billBalance.getBuyerNo())){
			params.put("buyerNo", FasUtil.splitToInStr(areaCompnays));
		}else{
			params.put("buyerNo", billBalance.getBuyerNo());
		}
		params.put("balanceStartDate", billBalance.getBalanceStartDate());
		params.put("balanceEndDate", billBalance.getBalanceEndDate());
		params.put("brandUnitNo", billBalance.getBrandUnitNo());
		List<BillBalance> list = hqInsteadOfBuyBalanceMapper.selectMatchedHqBalanceBill(params);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * 批量保存结算单
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addHqBalanceBillByBatch(List<BillBalance> billList,BillBalance formData) throws Exception {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		List<String> list=new ArrayList<String>();
		BillBalance bill =null;
		for(BillBalance fromSelect:billList){
			bill=new BillBalance();
			initCreateBalanceBill(bill, fromSelect, formData);
			validate(bill); 
			hqInsteadOfBuyBalanceMapper.insertSelective(bill); 
			String brandUnitNo = bill.getBrandUnitNo(); // 品牌部
			String brandNos = "";
			if (!brandUnitNo.equals("")) {
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("brandUnitNo", FasUtil.formatInQueryCondition(brandUnitNo));
				brandNos = brandUnitMapper.selectBrandNos(p); // 查询品牌部下的品牌
				bill.setBrandNo(brandNos);
			}
			hqInsteadOfBuyBalanceMapper.updateHqBuyBalanceNo(bill); // 回写结算单编号
			bill.setBrandNo(null);
			billBalanceMapper.updateDeductionBalanceNo(bill);// 回写结算单号到其他扣项
			billBalanceMapper.updateBuyPurchasePrice(bill);	// 反写单据采购价
			list.add(bill.getBillNo());
			resultMap.put("billNos", list);
			resultMap.put("bill", bill);// 默认返回第一个
			resultMap.put("flag", true);
		}
		return resultMap;
	}
	
	/**
	 * 保存总部代采结算单(批量操作)
	 * @throws Exception 
	 */
	private void initCreateBalanceBill(BillBalance bill,
			BillBalance fromSelect, BillBalance formData) throws Exception {
		BeanUtils.copyProperties(fromSelect, bill); //属性复制
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromSelect.getBuyerNo(),null,"GA"));// 生成结算单编码
		//保存前端传来的数据
		bill.setStatus(new Integer("0"));
		bill.setBalanceStartDate(formData.getBalanceStartDate());
		bill.setBalanceEndDate(formData.getBalanceEndDate());
		bill.setBalanceDate(formData.getBalanceDate());
		bill.setCreateUser(formData.getCreateUser());
		bill.setCreateTime(DateUtil.getCurrentDateTime());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int deleteBalanceBill(String[] ids) throws ServiceException {
		try {
			int iCount = 0;
			for (int i = 0; i < ids.length; i++) {
				// 回写结算单编号为空
				hqInsteadOfBuyBalanceMapper.updateBuyBalanceNoToNull(ids[i]);
				//清空扣项信息
				billBalanceMapper.clearDeductionBalanceNo(ids[i]);
				// 删除结算单
				iCount = hqInsteadOfBuyBalanceMapper.deleteBalanceBill(ids[i]);
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public int modifyBillStatus(String[] ids, Map<String, Object> params) {
		int n = 0;
		for (int i = 0; i < ids.length; i++) {
			params.put("billNo", ids[i]);
			n = hqInsteadOfBuyBalanceMapper.updateBillStatus(params);
		}
		return n;
	}

	@Override
	public int modifyBillCost(BillBalance formData) {
		int count=hqInsteadOfBuyBalanceMapper.updateBillCost(formData); //更新单价 
		return count;
	}
}
