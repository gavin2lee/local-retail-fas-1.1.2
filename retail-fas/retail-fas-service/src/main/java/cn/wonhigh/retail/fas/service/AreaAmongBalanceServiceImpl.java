/**  
 *   
 * 项目名称：retail-fas-service  
 * 类名称：AreaAmongBalanceServiceImpl  
 * 类描述：地区间结算业务处理
 * 创建人：ouyang.zm  
 * 创建时间：2014-9-24 下午12:15:39  
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

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillAreaBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BrandUnitMapper;
import cn.wonhigh.retail.fas.dal.database.OtherDeductionMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("areaAmongBalanceService")
public class AreaAmongBalanceServiceImpl extends BaseCrudServiceImpl implements
		AreaAmongBalanceService {
	@Resource
	private BillAreaBalanceMapper billAreaBalanceMapper;
	@Resource
	private BrandUnitMapper brandUnitMapper;
	@Resource
	private CommonUtilService commonUtilService;
	@Resource
	private BillBalanceMapper billBalanceMapper;
	@Resource
	private OtherDeductionMapper otherDeductionMapper;
	
	@Override
	public BaseCrudMapper init() {
		return billAreaBalanceMapper;
	}

	/**
	 * 结算单合计
	 */
	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return billAreaBalanceMapper.selectTotalRow(params);
	}
	
	/**
	 * 收款金额合计
	 */
	@Override
	public List<TotalDto> findReceivableAmountTotal(Map<String, Object> params) {
		return billAreaBalanceMapper.selectBalancePaymentTotal(params);
	}

	/**
	 * 修改结算单状态
	 */
	@Override
	public int modifyBillStatus(String[] ids, Map<String,Object> params) {
		int n = 0;
		for (int i = 0; i < ids.length; i++) {
			params.put("billNo", ids[i]);
			n = billAreaBalanceMapper.updateBillStatus(params);
		}
		return n;
	}
	
	/**
	 * 查询符合条件的结算单
	 */
	@Override
	public List<BillBalance> findBalanceBillList(BillBalance billBalance) {
		List<BillBalance> list = billAreaBalanceMapper.selectBalanceBill(billBalance);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * 单个生成结算单
	 * @throws Exception 
	 */
	@Override
	public void addBalanceBill(List<BillBalance> lstBillBalance,
			BillBalance fromPage) throws Exception {
		try {
			for (BillBalance bill : lstBillBalance) {
				initBillInfo(bill, fromPage);// 设置结算单信息
				validate(bill); 
				bill.setBrandNo(FasUtil.formatInCondition(bill.getBrandNo()));
				bill.setBrandUnitNo(FasUtil.formatInCondition(bill.getBrandUnitNo()));
				billAreaBalanceMapper.updateSaleBalanceNo(bill);// 回写结算单编号
				billBalanceMapper.updateBalanceNoForMultiBrand(bill);// 回写结算单号到其他扣项
				bill.setBrandNo(null);
				bill.setBrandUnitNo(FasUtil.splitStr(bill.getBrandUnitNo()));
				billAreaBalanceMapper.insertSelective(bill); 
			}
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);

		}
	}

	/**
	 * 保存结算单(单个操作)
	 * @param bill
	 * @param billBalance
	 * @throws Exception 
	 */
	private void initBillInfo(BillBalance bill, BillBalance fromPage) throws Exception {
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.AREA_AMONG.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getSalerNo(),null,"RR"));// 生成结算单编码
		bill.setBillName(fromPage.getBillName());
		bill.setBuyerNo(fromPage.getBuyerNo());// 调入公司
		bill.setBuyerName(fromPage.getBuyerName());
		bill.setSalerNo(fromPage.getSalerNo());// 调出公司
		bill.setSalerName(fromPage.getSalerName());
		bill.setStatus(fromPage.getStatus());	//单据状态
		bill.setBrandNo(FasUtil.splitStr(fromPage.getBrandNo()));
		bill.setBrandName(fromPage.getBrandName());
		bill.setBrandUnitNo(fromPage.getBrandUnitNo());
		if(fromPage.getBrandUnitNo().equals("")){
			bill.setBrandUnitName("");
		}else{
			bill.setBrandUnitName(fromPage.getBrandUnitName()); 
		}
		bill.setBalanceStartDate(fromPage.getBalanceStartDate());	
		bill.setBalanceEndDate(fromPage.getBalanceEndDate());
		bill.setBalanceDate(fromPage.getBalanceDate());
		bill.setCurrency(fromPage.getCurrency());
		bill.setCreateUser(fromPage.getCreateUser());
		bill.setCreateTime(DateUtil.getCurrentDateTime());
		bill.setRemark(fromPage.getRemark());
	}
	
	/**
	 * 查询匹配的数据
	 */
	@Override
	public List<BillBalance> findMatchedBalanceList(BillBalance billBalance) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("salerNo", billBalance.getSalerNo());
		params.put("buyerNo", billBalance.getBuyerNo());
		params.put("balanceStartDate", billBalance.getBalanceStartDate());
		params.put("balanceEndDate", billBalance.getBalanceEndDate());
		params.put("brandUnitNo",FasUtil.formatInQueryCondition(billBalance.getBrandUnitNo()));
		params.put("brandNo", billBalance.getBrandNo());
		params.put("mergeFlag", billBalance.getMergeFlag());
		List<BillBalance> list = billAreaBalanceMapper.selectMatchedBalanceBill(params);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 批量生成结算单
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addBalanceBillBatch(List<BillBalance> billBalanceList,BillBalance formData) throws Exception {
		Map<String,Object> reslutMap=new HashMap<String, Object>(); //返回结果集
		ArrayList<String> list=new ArrayList<String>();  //保存生成的结算单号
		BillBalance bill =null;
		for(BillBalance fromselect : billBalanceList){
			bill = new BillBalance();
			initCreateBillInfo(bill, fromselect, formData);
			validate(bill);// 验证
			bill.setBrandUnitNo(FasUtil.formatInCondition(bill.getBrandUnitNo()));
			billAreaBalanceMapper.updateSaleBalanceNo(bill);// 回写结算单编号到bill_sale_balance
			if (bill.getMergeFlag() == 1) { // 回写结算单号到其他扣项
				billBalanceMapper.updateDeductionBalanceNo(bill);
			} else if (bill.getMergeFlag() == 0) {
				billBalanceMapper.updateBalanceNoForMultiBrand(bill);
			}
			bill.setBrandNo(null);
			bill.setBrandUnitNo(FasUtil.splitStr(bill.getBrandUnitNo()));
			billAreaBalanceMapper.insertSelective(bill);// 保存结算单
			list.add(bill.getBillNo());
			reslutMap.put("billNos", list);
			reslutMap.put("bill", bill);// 默认返回第一个
			reslutMap.put("flag", true);
		}
		return reslutMap;
	}

	/**
	 * 保存结算单(批量)
	 * @param bill
	 * @param billBalance
	 * @throws Exception 
	 */
	private void initCreateBillInfo(BillBalance bill, BillBalance fromSelect,BillBalance formData) throws Exception {
		BeanUtils.copyProperties(fromSelect, bill);//属性复制
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.AREA_AMONG.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromSelect.getSalerNo(),null,"RR"));// 生成结算单编码

		bill.setRemark(formData.getRemark());
		bill.setStatus(new Integer("0"));
		bill.setBillName(formData.getBillName());
		bill.setBalanceStartDate(formData.getBalanceStartDate());
		bill.setBalanceEndDate(formData.getBalanceEndDate());
		bill.setBalanceDate(formData.getBalanceDate());
		bill.setCreateUser(formData.getCreateUser());
		bill.setCreateTime(DateUtil.getCurrentDateTime());
		
		if (formData.getMergeFlag() == 0) {// 按品牌部合并生成
			String brandUnitNo = formData.getBrandUnitNo(); 
			String brandNos = "";
			Map<String, Object> params = new HashMap<String, Object>();
			if (!brandUnitNo.equals("")) {
				params.put("brandUnitNo",FasUtil.formatInQueryCondition(brandUnitNo));
				brandNos = brandUnitMapper.selectBrandNos(params); // 查询有权限品牌部下的有权限品牌
				bill.setBrandNo(brandNos);
				bill.setBrandUnitNo(formData.getBrandUnitNo());
				bill.setBrandUnitName(formData.getBrandUnitName());
				bill.setMergeFlag(new Integer(0));
			} else {
				params.put("brandUnitNo", null);
				brandNos = brandUnitMapper.selectBrandNos(params); // 查询有权限的品牌部下的品牌
				bill.setBrandNo(brandNos);
				bill.setBrandUnitName("");
				bill.setMergeFlag(new Integer(0));
			}
		}else if(formData.getMergeFlag() == 1){// 按品牌部分开生成
			String brandUnitNo = fromSelect.getBrandUnitNo(); // 品牌部
			String brandNos = "";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("brandUnitNo",FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitMapper.selectBrandNos(params); // 查询有权限品牌部下有权限的品牌
			bill.setBrandNo(brandNos);
			bill.setMergeFlag(new Integer(1));
		}
	}

	/**
	 * 保存结算单(选单操作)
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> addBalanceBillBySelect(List<Object> lstItem,
			BillBalance fromPage) throws Exception {
		BillBalance billBalance=new BillBalance();
		BeanUtils.copyProperties(fromPage, billBalance); //属性复制
		billBalance.setId(UUIDGenerator.getUUID());
		billBalance.setBalanceType(BalanceTypeEnums.AREA_AMONG.getTypeNo()); // 结算类型
		billBalance.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getSalerNo(),null,"RR")); // 生成结算单编码
		billBalance.setStatus(new Integer("0"));	//单据状态
		billBalance.setCreateTime(DateUtil.getCurrentDateTime());
		
		BigDecimal balanceAmount = new BigDecimal(0);//应收金额
		BigDecimal outAmount = new BigDecimal(0);	//出库金额
		Integer sendQty=new Integer("0");			//出库数量
		for (Object object : lstItem) {
			BillSaleBalance sale = (BillSaleBalance)object;
			balanceAmount = balanceAmount.add(sale.getCost().multiply(new BigDecimal(sale.getSendQty())));
			outAmount = outAmount.add(sale.getCost().multiply(new BigDecimal(sale.getSendQty())));
			sendQty += sale.getSendQty();
		}
	
		BigDecimal deductionAmount = new BigDecimal(0);	//扣项金额
		Integer deductionQty=new Integer("0");			//扣项数量
		/*判断和设置品牌部*/
		String brandUnitNo = billBalance.getBrandUnitNo(); 
		String brandNos = "";
		Map<String, Object> brandParams = new HashMap<String, Object>();
		if (!brandUnitNo.equals("")) {
			brandParams.put("brandUnitNo",FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitMapper.selectBrandNos(brandParams); // 查询有权限品牌部下的有权限品牌
			billBalance.setBrandNo(brandNos);
			billBalance.setBrandUnitNo(FasUtil.formatInQueryCondition(brandUnitNo));
			billBalance.setBrandUnitName(billBalance.getBrandUnitName());
		} else {
			brandParams.put("brandUnitNo", null);
			brandNos = brandUnitMapper.selectBrandNos(brandParams); // 查询有权限的品牌部下的品牌
			billBalance.setBrandNo(brandNos);
			billBalance.setBrandUnitName("");
		}
		// 查询其他扣项
		OtherDeduction otherDeduction = otherDeductionMapper.selectOtherDeductionOfMultiBrand(billBalance);
		if (otherDeduction != null) {
			deductionQty = otherDeduction.getDeductionQty();
			deductionAmount = otherDeduction.getDeductionAmount();
		}
		
		billBalance.setOutQty(sendQty);
		billBalance.setDeductionQty(deductionQty);
		billBalance.setBalanceQty(sendQty);//不用减去扣项数量
		billBalance.setOutAmount(outAmount);
		billBalance.setDeductionAmount(deductionAmount);
		billBalance.setBalanceAmount(balanceAmount.subtract(deductionAmount)); //应收金额减去扣项金额
		
		//判断结算金额是否小于等于0
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", billBalance.getBillNo());
		if (lstItem.size() > 0 && lstItem != null) {
			for (Object object : lstItem) {
				BillSaleBalance sale = (BillSaleBalance) object;
				sale.setBalanceNo(billBalance.getBillNo());
				params.put("id", sale.getId());
				billAreaBalanceMapper.updateSaleBalanceNoBySelect(params);// 回写结算单号到bill_sale_balance
			}
			billBalanceMapper.updateBalanceNoForMultiBrand(billBalance); // 回写结算单号到其他扣项
			//多品牌属性值设置
			billBalance.setBrandNo(null);
			billBalance.setBrandUnitNo(FasUtil.parseInQueryCondition(billBalance.getBrandUnitNo()));
			billAreaBalanceMapper.insertSelective(billBalance);      // 保存结算单
			resultMap.put("flag", true);
			resultMap.put("bill", billBalance);
		} else {
			resultMap.put("flag", false);
		}
		return resultMap;
	}
	
	/**
	 * 删除结算单
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int deleteBalanceBill(String[] ids) throws ServiceException {
		try {
			int iCount = 0;
			for (int i = 0; i < ids.length; i++) {
				// 回写bill_sale_balance结算单编号为null
				billAreaBalanceMapper.updateSaleBalanceNoToNull(ids[i]);
				//清空扣项信息
				billBalanceMapper.clearDeductionBalanceNo(ids[i]);
				// 删除结算单
				iCount = billAreaBalanceMapper.deleteBalanceBill(ids[i]);
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 扣项调整
	 */
	@Override
	public BillBalance modifyDeducation(List<Object> lstItem,
			BillBalance fromPage) {
		BigDecimal paymentAmount = new BigDecimal(0);// 应付金额
		BigDecimal outAmount = fromPage.getOutAmount();// 出库金额
		BigDecimal deductionAmount = new BigDecimal(0);// 扣项金额
		Integer deducationQty = new Integer("0"); // 扣项数量
		BillBalance bill=billBalanceMapper.selectByPrimaryKey(fromPage);
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
			paymentAmount = outAmount.subtract(deductionAmount);
			fromPage.setDeductionQty(deducationQty);
			fromPage.setBalanceAmount(paymentAmount);
			fromPage.setDeductionAmount(deductionAmount);
			fromPage.setBrandUnitName(bill.getBrandUnitName());
		}
		return fromPage;
	}
	
}
