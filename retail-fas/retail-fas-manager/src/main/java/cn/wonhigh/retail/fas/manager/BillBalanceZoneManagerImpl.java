package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.backend.security.DataAccessEnum;
import cn.wonhigh.retail.fas.common.dto.WholesaleBalanceDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillBalanceZoneStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.CurrencyTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.GmsBillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.enums.WholesaleRemainingTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.CustomerOrderRemain;
import cn.wonhigh.retail.fas.common.model.CustomerReceRel;
import cn.wonhigh.retail.fas.common.model.CustomerReceRelDtl;
import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillSaleBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BrandUnitMapper;
import cn.wonhigh.retail.fas.dal.database.OtherDeductionMapper;
import cn.wonhigh.retail.fas.service.BillBalanceService;
import cn.wonhigh.retail.fas.service.BillSaleBalanceService;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.fas.service.CustomerOrderRemainService;
import cn.wonhigh.retail.fas.service.CustomerReceRelDtlService;
import cn.wonhigh.retail.fas.service.CustomerReceRelService;
import cn.wonhigh.retail.fas.service.OperateLogService;
import cn.wonhigh.retail.fas.service.OtherDeductionService;
import cn.wonhigh.retail.fas.service.WholesaleCustomerRemainingDtlService;
import cn.wonhigh.retail.fas.service.WholesaleCustomerRemainingSumService;
import cn.wonhigh.retail.fas.service.WholesalePrepayWarnService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.LongSequenceGenerator;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 地区批发销售出库单
 * 
 * @author yang.y
 */
@Service("billBalanceZoneManager")
public class BillBalanceZoneManagerImpl extends BaseCrudManagerImpl implements BillBalanceZoneManager,
		WholesaleRemaingManager {

	private Logger logger = Logger.getLogger(BillBalanceZoneManagerImpl.class);

	@Resource
	private OtherDeductionMapper otherDeductionMapper;

	@Resource
	private BillBalanceService billBalanceService;

	@Resource
	private BillSaleBalanceService billSaleBalanceService;

	@Resource
	private WholesalePrepayWarnService wholesalePrepayWarnService;

	@Resource
	private CommonUtilService commonUtilService;

	@Resource
	private OtherDeductionService otherDeductionService;

	@Resource
	private BillBalanceMapper billBalanceMapper;

	@Resource
	private CustomerReceRelService customerReceRelService;

	@Resource
	private CustomerReceRelDtlService customerReceRelDtlService;

	@Resource
	private BillSaleBalanceMapper billSaleBalanceMapper;

	@Resource
	private OperateLogService operateLogService;

	@Resource
	private BrandUnitMapper brandUnitMapper;

	@Resource
	private CustomerOrderRemainService customerOrderRemainService;

	@Resource
	private WholesaleCustomerRemainingSumService customerRemainingSumService;

	@Resource
	private WholesaleCustomerRemainingDtlService customerRemainingDtlService;

	@Override
	public BaseCrudService init() {
		return billBalanceService;
	}

	/**
	 * 新增地区批发结算单
	 * 
	 * @param model
	 *            结算单
	 * @return 新增的对象
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance addBill(BillBalance bill) throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("buyerNo", bill.getBuyerNo());
			params.put("salerNo", bill.getSalerNo());
			params.put("balanceStartDate", DateUtil.format(bill.getBalanceStartDate(), "yyyy-MM-dd"));
			params.put("balanceEndDate", DateUtil.format(bill.getBalanceEndDate(), "yyyy-MM-dd"));
			params.put("billType", BillTypeEnums.SALEOUTS.getRequestId());
			String brandNos = Authorization.getAccessData(DataAccessEnum.BRAND).toString().replace("[", "").replace("]", "").replace(" ", "");
			// 品牌部多选
			String brandUnitNos = bill.getBrandUnitNo() == null ? null : bill.getBrandUnitNo();
			if (brandUnitNos != null && !"".equals(brandUnitNos)) {
				String brandUnitNoStr = FasUtil.formatInQueryCondition(brandUnitNos);
				bill.setMultiBrandUnitNo(brandUnitNoStr);
				params.put("brandUnitNo", brandUnitNoStr);
				params.put("multiBrandUnitNo", brandUnitNoStr);
			}
			// 管理城市多选
			String organNoFroms = bill.getBrandUnitNo() == null ? null : bill.getOrganNoFrom();
			if (organNoFroms != null && !"".equals(organNoFroms)) {
				String organNoFromStr = FasUtil.formatInQueryCondition(organNoFroms);
				params.put("multiOrganNoFrom", organNoFromStr);
			}
			// 批发销售
			String queryCondition = params.get("queryCondition") == null ? "" : String.valueOf(params
					.get("queryCondition"));
			queryCondition = queryCondition.concat(" AND (balance_no IS NULL OR balance_no = '') AND biz_type in ("
					+ BizTypeEnums.WHOLESALE.getStatus() + "," 
					+ BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus() + ","
					+ BizTypeEnums.WHOLESALE_RECALL.getStatus() + "," 
					+ BizTypeEnums.WHOLESALE_RETURN.getStatus() + "," 
					+ BizTypeEnums.CUSTOMER_RETURN.getStatus() + ")");
			params.put("queryCondition", queryCondition);
			// 汇总出库金额
			BillBalance totalBill = billSaleBalanceService.sumBillCostAndQty(params);
			bill = this.initBill(bill);
			params.put("queryCondition", "AND (balance_no IS NULL OR balance_no = '')");
			bill.setOutAmount(totalBill.getOutAmount());
			bill.setMultiBrandNo(FasUtil.formatInQueryCondition(brandNos));
			bill.setOutQty(totalBill.getOutQty());
			bill.setBalanceAmount(totalBill.getOutAmount());
			OtherDeduction deduction = otherDeductionService.findOtherDeductionBanlance(params);
			if (null == deduction && totalBill.getBillSaleBalanceCount().intValue() == 0) {
				 bill.setErrorInfo("当前没有发生任何业务");
				 return bill;
			}
			if (null != deduction) {
				bill.setSumRebateAmount(deduction.getRebateAmount());
				bill.setRebateAmount(deduction.getRebateAmount());
				bill.setOtherPrice(deduction.getOtherPrice());
				bill.setDeductionAmount(deduction.getDeductionAmount());
				billBalanceMapper.updateDeductionBalanceNo(bill);
				bill.setBalanceAmount(BigDecimalUtil.subtract(
						BigDecimalUtil.add(totalBill.getOutAmount(),deduction.getOtherPrice()),
						BigDecimalUtil.add(deduction.getDeductionAmount(), deduction.getRebateAmount()
								)));
			}

			// 新增结算单
			if (StringUtils.isBlank(bill.getBrandUnitNo())) {
				bill.setBrandUnitName("");
			}
			int count = billBalanceService.add(bill);
			bill.setBillType(BillTypeEnums.SALEOUTS.getRequestId());
			bill.setBizType(BizTypeEnums.WHOLESALE.getStatus());
			bill.setBizMultiType("(" + BizTypeEnums.WHOLESALE.getStatus() + ","
					+ BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus() + "," 
					+ BizTypeEnums.WHOLESALE_RETURN.getStatus() + "," 
					+ BizTypeEnums.WHOLESALE_RECALL.getStatus() + "," 
					+ BizTypeEnums.CUSTOMER_RETURN.getStatus() + ")");
			if (count > 0) {
				// 回写结算单号至bill_sale_balance表中
				bill.setBrandMultiNo(FasUtil.formatInQueryCondition(bill.getBrandUnitNo()));
				bill.setBrandUnitNo(null);
				billBalanceService.updateSaleBalanceNo(bill);
			}
			this.insertOperateLog(bill);
			return bill;
		} catch (ServiceException e) {
			logger.error("新增地区批发结算单出错", e);
			throw new ManagerException(e.getMessage(), e);
		}
	}

	private boolean checkRebate(BillBalance bill) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", bill.getSalerNo());
		params.put("customerNo", bill.getBuyerNo());
		params.put("status", 1);
		List<CustomerReceRel> lstItem = customerReceRelService.findByBiz(null, params);
		if (lstItem.size() > 0) {
			CustomerReceRel rel = lstItem.get(0);
			String refId = rel.getId();
			int iYear = Calendar.getInstance().get(Calendar.YEAR);
			params.put("refId", refId);
			params.put("year", iYear);
			List<CustomerReceRelDtl> lstDtl = customerReceRelDtlService.findByBiz(null, params);
			if (lstDtl.size() > 0) {
				CustomerReceRelDtl dtl = lstDtl.get(0);
				BigDecimal amount = dtl.getRebateAmount() == null ? new BigDecimal(0) : dtl.getRebateAmount();
				BigDecimal hasAmount = dtl.getHasRebateAmount() == null ? new BigDecimal(0) : dtl.getHasRebateAmount();
				BigDecimal rebateAmount = bill.getRebateAmount() == null ? new BigDecimal(0) : bill.getRebateAmount();
				BigDecimal invoiceRebateAmount = bill.getInvoiceRebateAmount() == null ? new BigDecimal(0) : bill
						.getInvoiceRebateAmount();
				BigDecimal allRebateAmount = rebateAmount.add(invoiceRebateAmount);// 票前返利
																					// 与票后返利
				BillBalance result = billBalanceService.findById(bill);
				if (result != null) {// 去掉已存在的返利金额
					BigDecimal oldRebateAmount = result.getRebateAmount() == null ? new BigDecimal(0) : result
							.getRebateAmount();
					BigDecimal oldInvoiceRebateAmount = result.getInvoiceRebateAmount() == null ? new BigDecimal(0)
							: result.getInvoiceRebateAmount();
					allRebateAmount = allRebateAmount.subtract(oldRebateAmount.add(oldInvoiceRebateAmount));
				}
				if (allRebateAmount.subtract(amount.subtract(hasAmount)).doubleValue() <= 0) {
					dtl.setHasRebateAmount(hasAmount.add(allRebateAmount));
					customerReceRelDtlService.modifyById(dtl);
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * 批量新增地区批发结算单
	 * 
	 * @param params
	 *            数据
	 * @return 新增的数量
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int batchAddBill(Map<String, Object> params) throws Exception {
		try {
			int count = 0;
			String buyerNo = params.get("buyerNo").toString();
			String loginName = params.get("loginName").toString();
			String organNoFrom = params.get("organNoFrom").toString();
			String organNameFrom = params.get("organNameFrom").toString();
			String brandUnitFlag = params.get("brandUnitFlag").toString();
			String balanceStartDate = String.valueOf(params.get("balanceStartDate"));
			String balanceEndDate = String.valueOf(params.get("balanceEndDate"));
			String tempBrandUnitNos = String.valueOf(params.get("tempBrandUnitNos"));
			String queryCondition = params.get("queryCondition") == null ? "" : String.valueOf(params
					.get("queryCondition"));
			queryCondition = queryCondition.concat(" AND biz_type in (" + BizTypeEnums.WHOLESALE.getStatus() + ","
					+ BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus() + "," + BizTypeEnums.WHOLESALE_RETURN.getStatus()
					+ "," + BizTypeEnums.CUSTOMER_RETURN.getStatus()+ "," + BizTypeEnums.WHOLESALE_RECALL.getStatus() + ")");
			params.put("bizType", null);
			params.put("queryCondition", queryCondition);
			if (StringUtils.isEmpty(buyerNo.replaceAll(",", ""))) {
				params.put("buyerNo", null);
			}
			if (StringUtils.isNotBlank(organNoFrom)) {
				params.put("multiOrganNoFrom", FasUtil.formatInQueryCondition(organNoFrom));
			}
			List<WholesaleBalanceDto> lstDto = billSaleBalanceService.findMulitZoneBalance(params);
			if (lstDto != null && lstDto.size() > 0) {
				Date currDate = DateUtil.getCurrentDateTime();
				for (int i = 0; i < lstDto.size(); i++) {
					WholesaleBalanceDto wholesaleBalanceDto = lstDto.get(i);
					if (wholesaleBalanceDto.getSaleOutAmount() != null) {
						BillBalance bill = new BillBalance();
						if ("0".equals(brandUnitFlag)) { // 品牌部合并
							params.clear();
							params.put("tempBrandUnitNos", tempBrandUnitNos);
							List<BrandUnit> list = brandUnitMapper.selectByParams(null, params);
							if (list != null && list.size() > 0) {
								String tempBrandUnitNo = "";
								String tempBrandUnitName = "";
								for (BrandUnit unit : list) {
									tempBrandUnitNo = tempBrandUnitNo + "," + unit.getBrandUnitNo();
									tempBrandUnitName = tempBrandUnitName + "," + unit.getName();
								}
								if (StringUtils.isNotEmpty(tempBrandUnitNos)) {
									wholesaleBalanceDto.setBrandUnitNo(tempBrandUnitNo.substring(1,
											tempBrandUnitNo.length()));
									wholesaleBalanceDto.setBrandUnitName(tempBrandUnitName.substring(1,
											tempBrandUnitName.length()));
								} else {
									wholesaleBalanceDto.setBrandUnitNo(null);
									wholesaleBalanceDto.setBrandUnitName(null);
								}
							}
						}
						bill.setBuyerNo(wholesaleBalanceDto.getBuyerNo());
						bill.setBuyerName(wholesaleBalanceDto.getBuyerName());
						bill.setSalerNo(wholesaleBalanceDto.getSalerNo());
						bill.setSalerName(wholesaleBalanceDto.getSalerName());
						bill.setBrandUnitNo(wholesaleBalanceDto.getBrandUnitNo());
						bill.setBrandUnitName(wholesaleBalanceDto.getBrandUnitName());
						bill.setOrganNoFrom(organNoFrom);
						bill.setOrganNameFrom(organNameFrom);
						bill.setBalanceAmount(wholesaleBalanceDto.getSaleOutAmount());
						bill.setCurrency(String.valueOf(CurrencyTypeEnums.RMB.getTypeNo()));
						// 设置默认的单据名称
						bill.setBillName(bill.getSalerName() + "-" + bill.getBuyerName());
						bill.setOutAmount(wholesaleBalanceDto.getSaleOutAmount());
						bill.setOutQty(wholesaleBalanceDto.getSendQty());
						bill.setBalanceStartDate(DateUtil.parseToDate(balanceStartDate, "yyyy-MM-dd"));
						bill.setBalanceEndDate(DateUtil.parseToDate(balanceEndDate, "yyyy-MM-dd"));
						bill.setCreateUser(loginName);
						bill.setBalanceDate(DateUtil.parseToDate(DateUtil.formatDateByFormat(currDate, "yyyy-MM-dd"),
								"yyyy-MM-dd"));
						bill.setCreateTime(new Date());
						bill = this.initBill(bill);
						bill.setBalanceAmount(wholesaleBalanceDto.getSaleOutAmount());
						
						params.clear();
						params.put("buyerNo", bill.getBuyerNo());
						params.put("salerNo", bill.getSalerNo());
						params.put("multiBrandUnitNo", FasUtil.formatInQueryCondition(bill.getBrandUnitNo()));
						params.put("balanceStartDate", DateUtil.format(bill.getBalanceStartDate(), "yyyy-MM-dd"));
						params.put("balanceEndDate", DateUtil.format(bill.getBalanceEndDate(), "yyyy-MM-dd"));
						params.put("queryCondition", "AND (balance_no IS NULL OR balance_no = '')");
						OtherDeduction deduction = otherDeductionService.findOtherDeductionBanlance(params);
						if (null != deduction) {
							bill.setDeductionAmount(deduction.getDeductionAmount());
							bill.setRebateAmount(deduction.getRebateAmount());
							bill.setOtherPrice(deduction.getOtherPrice());
							bill.setBalanceAmount(BigDecimalUtil.subtract(BigDecimalUtil.add(wholesaleBalanceDto.getSaleOutAmount(),deduction.getOtherPrice()),
									BigDecimalUtil.add(deduction.getDeductionAmount(), deduction.getRebateAmount())));
							if (bill.getBrandUnitNo() != null && bill.getBrandUnitNo().indexOf(",") != -1) {
								bill.setBrandUnitNo(FasUtil.formatInQueryCondition(bill.getBrandUnitNo()));
								billBalanceMapper.updateDeductionWithMultiBrandNo(bill);
							} else {
								billBalanceMapper.updateDeductionBalanceNo(bill);
							}
						}
						
						billBalanceService.add(bill);
						String billBrandNoStr = bill.getBrandUnitNo();
						
						// 回写结算单号至bill_sale_balance表中
						bill.setBillType(BillTypeEnums.SALEOUTS.getRequestId());
						bill.setBizType(BizTypeEnums.WHOLESALE.getStatus());
						bill.setBizMultiType("(" + BizTypeEnums.WHOLESALE.getStatus() + ","
								+ BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus() + ","
								+ BizTypeEnums.WHOLESALE_RETURN.getStatus() + ","
								+ BizTypeEnums.WHOLESALE_RECALL.getStatus() + ","
								+ BizTypeEnums.CUSTOMER_RETURN.getStatus() + ")");
						bill.setBrandMultiNo(FasUtil.formatInQueryCondition(billBrandNoStr));
						bill.setBrandUnitNo("");
						billBalanceService.updateSaleBalanceNo(bill);

						this.insertOperateLog(bill);
						count++;
					}
				}
			}
			return count;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 设置未结订单金额、未结预收款
	 * 
	 * @param billBalance
	 *            结算单
	 * @return BillBalance
	 * @throws ServiceException
	 *             异常
	 */
	private BillBalance initBill(BillBalance bill) throws ServiceException {
		bill.setId(UUIDGenerator.getUUID());
		String billNo = commonUtilService.getNewBillNoCompanyNo(bill.getSalerNo(), FasBillTypeEnums.RS.getRequestId());
		bill.setBillNo(billNo);
		bill.setBalanceType(BalanceTypeEnums.AREA_WHOLESALE.getTypeNo());
		bill.setStatus(BillBalanceZoneStatusEnums.NO_CONFIRM.getTypeNo());
		return bill;
	}

	/**
	 * 批量删除数据
	 * 
	 * @param lstBill
	 *            待删除的数据集合
	 * @return 删除的数量
	 * @throws ManagerException
	 *             异常
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int delete(List<BillBalance> lstBill) throws ManagerException {
		try {
			int count = 0;
			for (BillBalance bill : lstBill) {
				bill = billBalanceService.findById(bill);
				count += billBalanceService.deleteById(bill);
//				billBalanceService.resetSaleBalanceActualCost(bill.getBillNo());
				billBalanceService.clearSaleBalanceNo(bill.getBillNo());
				billBalanceMapper.clearDeductionBalanceNo(bill.getBillNo());
//				if ((bill.getRebateAmount() != null && bill.getRebateAmount().doubleValue() != 0)
//						|| (bill.getInvoiceRebateAmount() != null && bill.getInvoiceRebateAmount().doubleValue() != 0)) {
//					Map<String, Object> params = new HashMap<String, Object>();
//					params.put("companyNo", bill.getSalerNo());
//					params.put("customerNo", bill.getBuyerNo());
//					params.put("status", 1);
//					List<CustomerReceRel> lstItem = customerReceRelService.findByBiz(null, params);
//					if (lstItem.size() > 0) {
//						CustomerReceRel rel = lstItem.get(0);
//						String refId = rel.getId();
//						int iYear = Calendar.getInstance().get(Calendar.YEAR);
//						params.put("refId", refId);
//						params.put("year", iYear);
//						List<CustomerReceRelDtl> lstDtl = customerReceRelDtlService.findByBiz(null, params);
//						if (lstDtl.size() > 0) {
//							CustomerReceRelDtl dtl = lstDtl.get(0);
//							BigDecimal hasAmount = dtl.getHasRebateAmount() == null ? new BigDecimal(0) : dtl
//									.getHasRebateAmount();
//							BigDecimal rebateAmount = bill.getRebateAmount() == null ? new BigDecimal(0) : bill
//									.getRebateAmount();
//							BigDecimal invoiceRebateAmount = bill.getInvoiceRebateAmount() == null ? new BigDecimal(0)
//									: bill.getInvoiceRebateAmount();
//							dtl.setHasRebateAmount(hasAmount.subtract(rebateAmount.add(invoiceRebateAmount)));
//							customerReceRelDtlService.modifyById(dtl);
//						}
//					}
//				}
			}
			return count;
		} catch (ServiceException e) {
			logger.error("删除地区批发结算单出错", e);
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 业务确认操作
	 * 
	 * @param oList
	 *            待确认的数据集合(单个或批量确认)
	 * @return 单据对象(批量操作时，返回第一个对象)
	 * @throws ManagerException
	 *             异常
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance confirm(List<BillBalance> lstBill) throws ManagerException {
		try {
			int num = 1;
			for (BillBalance model : lstBill) {
				this.verifyOperateLog(model);
				BillBalance tempBill = billBalanceService.findById(model);
				if (model.getStatus().intValue() == BillBalanceZoneStatusEnums.INVALID.getTypeNo()) {
					// modify by wang.m 去掉批发结算单上的返利和其他扣项自动更新余额的功能
					/*
					 * if(tempBill!=null && tempBill.getStatus().intValue() >=
					 * BillBalanceZoneStatusEnums
					 * .RECEIVE_FINANCE_CONFIRM.getTypeNo()){ try { num =
					 * saveWholesaleCustomerRemainingInfo(model, num); } catch
					 * (Exception e) { // TODO Auto-generated catch block
					 * logger.error(e.getMessage(), e); } ++num; }
					 */
					//
					if (tempBill != null
							&& tempBill.getStatus().intValue() >= BillBalanceZoneStatusEnums.RECEIVE_BUSSINESS_CONFIRM
									.getTypeNo()) {
						billSaleBalanceService.resetOtherDeductionByBalanceNo(model.getBillNo());
						model.setIsApportionRebate(YesNoEnum.NO.getValue());
					}
					model.setAuditor(null);
					model.setAuditTime(null);
				}
				if (model.getStatus().intValue() == BillBalanceZoneStatusEnums.RECEIVE_BUSSINESS_CONFIRM.getTypeNo()) {
					// 扣项与返利分摊
					apportionDeduction(tempBill);
					model.setIsApportionRebate(YesNoEnum.YES.getValue());
				}
				// if(model.getStatus().intValue()==
				// BillBalanceZoneStatusEnums.RECEIVE_FINANCE_CONFIRM.getTypeNo()){
				// try {
				// num = saveWholesaleCustomerRemainingInfo(model, num);
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// logger.error(e.getMessage(), e);
				// }
				// ++num;
				// }
				billBalanceService.modifyById(model);
				// 修改结算单状态
				billBalanceService.updateSaleBalanceStatus(model);

			}
			return billBalanceService.findById(lstBill.get(0));
		} catch (ServiceException e) {
			logger.error("确认地区批发结算单出错", e);
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 查询预收款预警里的金额
	 * 
	 * @param params
	 *            查询条件
	 * @return Map<String,BigDecimal>
	 * @throws ManagerException
	 *             异常
	 */
	@Override
	public Map<String, BigDecimal> selectAmount(Map<String, Object> params) throws ManagerException {
		try {
			Map<String, BigDecimal> amountMap = wholesalePrepayWarnService.selectTotalAmount(params);
			return amountMap;
		} catch (ServiceException e) {
			logger.error("查询预收款预警里的金额出错", e);
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance createBalance(BillBalance obj, List<Object> lstItem) throws ManagerException {
		try {
			String billNo = commonUtilService.getNewBillNoCompanyNo(obj.getSalerNo(),
					FasBillTypeEnums.getRequestIdByBalanceType(obj.getBalanceType()));
			obj.setId(UUIDHexGenerator.generate());
			obj.setBillNo(billNo);
			StringBuffer billNos = new StringBuffer(",");//出库单号
			BigDecimal outAmount = BigDecimal.ZERO;
			BigDecimal entryAmount = BigDecimal.ZERO;
			BigDecimal returnAmount = BigDecimal.ZERO;
			BigDecimal deductionAmount = BigDecimal.ZERO;
			BigDecimal rebateAmount = BigDecimal.ZERO;
			BigDecimal otherPrice = BigDecimal.ZERO;
			int outQty = 0;
			int entryQty = 0;
			int returnQty = 0;
			int deductionQty = 0;
			for (Object item : lstItem) {
				BillSaleBalance bill = (BillSaleBalance) item;
				bill.setBalanceNo(obj.getBillNo());
				billNos.append(bill.getBillNo());
				billNos.append(",");
				bill.setBalanceType(BalanceTypeEnums.AREA_WHOLESALE.getTypeNo());
				bill.setBalanceStatus(BillBalanceZoneStatusEnums.NO_CONFIRM.getTypeNo());
				billSaleBalanceMapper.updateBalanceInfoById(bill);
				if (bill.getBillType().intValue() == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
					returnAmount = returnAmount.add(bill.getCost().multiply(new BigDecimal(bill.getSendQty())));
					returnQty += bill.getSendQty();
				} else {
					outAmount = outAmount.add(bill.getCost().multiply(new BigDecimal(bill.getSendQty())));
					entryAmount = entryAmount.add(bill.getCost().multiply(new BigDecimal(bill.getReceiveQty())));
					outQty += bill.getSendQty();
					entryQty += bill.getReceiveQty();
				}
			}
			if (StringUtils.isNotBlank(obj.getBrandUnitNo())) {
				obj.setBrandUnitNo("(" + obj.getBrandUnitNo() + ")");
			}
			// 计算扣项
			OtherDeduction deduction = otherDeductionMapper.selectOtherDeductionOfMultiBrand(obj);
			if (deduction != null) {
				deductionQty = deduction.getDeductionQty();
				deductionAmount = deduction.getDeductionAmount();
			}
			//计算返利和其他费用
			OtherDeduction rebateOther = otherDeductionMapper.findChoiceRebateOtherPrice(billNos.toString());
			if (null != rebateOther) {
				rebateAmount = rebateOther.getRebateAmount();
				otherPrice = rebateOther.getOtherPrice();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("billNos", billNos.toString());			
				params.put("balanceNo", obj.getBillNo());			
				
				//修改选单返利和其他费用结算单号
				otherDeductionMapper.updateBalanceNoByBillNos(params);
			}
			BigDecimal balanceAmount = BigDecimalUtil.subtract(BigDecimalUtil.add(outAmount,otherPrice),
					BigDecimalUtil.add(deductionAmount,rebateAmount,returnAmount));
			
			obj.setRebateAmount(rebateAmount);
			obj.setOtherPrice(otherPrice);
			obj.setOutAmount(outAmount);
			obj.setEntryAmount(entryAmount);
			obj.setReturnAmount(returnAmount);
			obj.setDeductionAmount(deductionAmount);
			obj.setOutQty(outQty);
			obj.setEntryQty(entryQty);
			obj.setReturnQty(returnQty);
			obj.setDeductionQty(deductionQty);
			obj.setBalanceQty(outQty - returnQty);
			obj.setBalanceAmount(balanceAmount);
			if (StringUtils.isBlank(obj.getBrandUnitNo())) {
				obj.setBrandUnitName("");
			}
			billBalanceMapper.updateChoiceDeductionWithMultiBrandNo(obj);
			obj.setBrandUnitNo(FasUtil.parseInQueryCondition(obj.getBrandUnitNo()));
			billBalanceMapper.insertSelective(obj);
			this.insertOperateLog(obj); // 记录审批日志
			obj=billBalanceMapper.selectByPrimaryKey(obj);
			obj.setSumRebateAmount(rebateAmount);
			return obj;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem) throws ManagerException {
		try {
			BigDecimal deductionAmount = BigDecimal.ZERO;
			int deductionQty = 0;
			String billNo = obj.getBillNo();
			billBalanceMapper.clearDeductionBalanceNo(billNo);
			obj = billBalanceMapper.selectByPrimaryKey(obj);
			for (Object object : lstItem) {
				OtherDeduction deduction = (OtherDeduction) object;
				deduction.setBalanceNo(billNo);
				deduction.setBalanceType(BalanceTypeEnums.AREA_WHOLESALE.getTypeNo());
				otherDeductionMapper.updateByPrimaryKeySelective(deduction);
				deductionAmount = deductionAmount.add(deduction.getDeductionAmount());
				deductionQty += deduction.getDeductionQty();
			}
			obj.setDeductionQty(deductionQty);
			obj.setDeductionAmount(deductionAmount);
			int outQty = obj.getOutQty();
			int returnQty = obj.getReturnQty();
			obj.setBalanceQty(outQty - returnQty - deductionQty);
			obj.setBalanceAmount(obj.getOutAmount().subtract(obj.getReturnAmount()).subtract(obj.getRebateAmount())
					.subtract(deductionAmount));
			billBalanceMapper.updateByPrimaryKeySelective(obj);
			return obj;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 新增日志对象
	 * 
	 * @param billBalance
	 * @throws ServiceException
	 */
	private void insertOperateLog(BillBalance obj) throws ServiceException {
		OperateLog operateLog = new OperateLog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setDataNo(obj.getBillNo());
		operateLog.setModuleNo(OperateLogEnums.OperateModule.JSD.getModuleNo());
		operateLog.setCreateUser(obj.getCreateUser());
		operateLog.setCreateTime(obj.getCreateTime());
		operateLog.setStatus(BillBalanceZoneStatusEnums.NO_CONFIRM.getTypeNo());
		operateLog.setStatusName(BillBalanceZoneStatusEnums.NO_CONFIRM.getTypeName());
		operateLog.setOperateStatusName(BillBalanceZoneStatusEnums.NO_CONFIRM.getTypeName());
		operateLogService.add(operateLog);
	}

	/**
	 * 审批日志对象
	 * 
	 * @param billBalance
	 * @throws ServiceException
	 */
	private void verifyOperateLog(BillBalance billBalance) throws ServiceException {
		OperateLog operateLog = new OperateLog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setDataNo(billBalance.getBillNo());
		operateLog.setModuleNo(OperateLogEnums.OperateModule.JSD.getModuleNo());
		operateLog.setStatus(billBalance.getStatus());
		operateLog.setStatusName(BillBalanceZoneStatusEnums.getTypeNameByNo(billBalance.getStatus()));
		operateLog.setCreateUser(billBalance.getAuditor());
		operateLog.setCreateTime(billBalance.getAuditTime());
		operateLog.setOperateStatusName(BillBalanceZoneStatusEnums.getOperateNameByNo(billBalance.getStatus()));
		operateLogService.add(operateLog);
	}

	@Override
	@Transactional
	public BillBalance updateBill(BillBalance bill) throws ManagerException {
		try {
			// if((bill.getRebateAmount() != null &&
			// bill.getRebateAmount().doubleValue() != 0) ||
			// (bill.getInvoiceRebateAmount() != null &&
			// bill.getInvoiceRebateAmount().doubleValue() != 0)) {// 不符合生成条件
			// 返利金额超额
			// if(!checkRebate(bill)){
			// bill.setErrorInfo("未设置返利额度或返利金额已超支,不允许保存!");
			// return bill;
			// }
			// }
			if (bill.getRebateAmount() == null) {
				bill.setRebateAmount(BigDecimal.ZERO);
			}
			if (bill.getInvoiceRebateAmount() == null) {
				bill.setInvoiceRebateAmount(BigDecimal.ZERO);
			}
			// 票前返利分摊
			// if(bill.getStatus().equals(0)){
			// bill = apportionRebateAmount(bill);
			// }
			billBalanceMapper.updateByPrimaryKeySelective(bill);
			return bill;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	// private BillBalance apportionRebateAmount(BillBalance bill){
	// BillBalance tempBill = billBalanceMapper.selectByPrimaryKey(bill);
	// if(tempBill != null){
	// if(YesNoEnum.YES.getValue()==tempBill.getIsApportionRebate()){
	// billBalanceMapper.updateApportionRebateAgain(bill);
	// }else {
	// billBalanceMapper.updateApportionRebate(bill);
	// }
	// bill.setIsApportionRebate(1);
	// }
	// return bill;
	// }

	public List<BillSaleBalance> setExtendsBillSaleBalanceProperties(List<Object> list) {
		List<BillSaleBalance> retList = new ArrayList<BillSaleBalance>();
		if (!CollectionUtils.isEmpty(list)) {
			for (Object object : list) {
				retList.add(setExtendsProperties((BillSaleBalance) object));
			}
		}
		return retList;
	}

	private BillSaleBalance setExtendsProperties(BillSaleBalance billSaleBalance) {
		if (null != billSaleBalance.getTagPrice() && billSaleBalance.getTagPrice().compareTo(BigDecimal.ZERO) != 0) {
			billSaleBalance.setDiscount(billSaleBalance.getCost().divide(billSaleBalance.getTagPrice(), 5,
					BigDecimal.ROUND_HALF_UP)); // 扣率 = cost/牌价
			billSaleBalance.setDiscountStr((billSaleBalance.getCost().multiply(new BigDecimal(100))).divide(
					billSaleBalance.getTagPrice(), 3, BigDecimal.ROUND_HALF_UP) + "%");
			if (null != billSaleBalance.getOtherDeductCost() && billSaleBalance.getOtherDeductCost().compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal rebatePrice = BigDecimalUtil.subtract(billSaleBalance.getCost(),billSaleBalance.getOtherDeductCost().divide(new BigDecimal(billSaleBalance.getSendQty()), 3 ,BigDecimal.ROUND_HALF_UP));
				billSaleBalance.setBillRebateDiscountStr((rebatePrice.multiply(new BigDecimal(100))).divide(
						billSaleBalance.getTagPrice(), 3, BigDecimal.ROUND_HALF_UP) + "%");
			}
		} else {
			billSaleBalance.setDiscount(BigDecimal.ZERO);
		}
		return billSaleBalance;
	}

	@Override
	public <ModelType> int saveWholesaleCustomerRemainingInfo(ModelType modelType, int num)
			throws NumberFormatException, Exception {
		try {
			BillBalance bill = null;
			BillBalance billModel = (BillBalance) modelType;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", billModel.getBillNo());
			List<BillBalance> billList = billBalanceService.findByBiz(null, params);
			if (billList != null && billList.size() > 0) {
				bill = billList.get(0);
				bill.setStatus(billModel.getStatus());
			} else {
				throw new ManagerException("FAS找不到结算单: " + billModel.getBillNo());
			}
			params.clear();
			params.put("companyNo", bill.getSalerNo());
			params.put("customerNo", bill.getBuyerNo());
			BigDecimal remainingAmount = BigDecimal.ZERO;
			WholesaleCustomerRemainingSum remainingSum = new WholesaleCustomerRemainingSum();
			// 其他扣项、返利 在客户余额中记为收款 （客户余额收款为正数）
			BigDecimal money = bill.getDeductionAmount().add(bill.getRebateAmount().add(bill.getInvoiceRebateAmount()));
			if (money == null || BigDecimal.ZERO.compareTo(money) == 0) {
				return num;
			}
			if (bill.getStatus() == BillBalanceZoneStatusEnums.INVALID.getTypeNo()) { // 打回
				money = BigDecimal.ZERO.subtract(money);
			}
			List<WholesaleCustomerRemainingSum> sumList = customerRemainingSumService.findByBiz(null, params);
			sumList = customerRemainingSumService.findByBiz(null, params);
			if (sumList != null && sumList.size() > 0) {
				remainingSum = sumList.get(0);
				bill.setMoney(money);
				remainingAmount = remainingSum.getRemainingAmount().add(money);
				remainingSum.setRemainingAmount(remainingAmount);
				remainingSum.setUpdateTime(new Date());
				customerRemainingSumService.modifyById(remainingSum);
				// 新增明细
				try {
					saveWholesaleCustomerDtl(remainingSum, bill, num);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
				}
			} else { // 初始化客户余额
				remainingSum.setCompanyNo(bill.getSalerNo());
				remainingSum.setCompanyName(bill.getSalerName());
				remainingSum.setCustomerNo(bill.getBuyerNo());
				remainingSum.setCustomerName(bill.getBuyerName());
				remainingSum.setCreateTime(new Date());
				params.clear();
				params.put("companyNo", bill.getSalerNo());
				params.put("buyerNo", bill.getBuyerNo());
				WholesaleCustomerRemainingSum paidRemaining = customerRemainingSumService
						.selectCalcPaidAmountByParams(params);
				WholesaleCustomerRemainingSum sendRemaining = customerRemainingSumService
						.selectCalcSendAmountByParams(params);
				remainingAmount = paidRemaining.getPaidAmount().subtract(sendRemaining.getSendAmount());
				remainingSum.setRemainingAmount(remainingAmount.add(money));
				customerRemainingSumService.add(remainingSum);

				String remark = bill.getRemark();
				// 初始化明细
				bill.setRemark("客户余额数据初始化!");
				bill.setMoney(BigDecimal.ZERO);
				remainingSum.setRemainingAmount(remainingAmount);
				try {
					saveWholesaleCustomerDtl(remainingSum, bill, num);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
				}
				// 新增明细
				bill.setMoney(money);
				bill.setRemark(remark);
				remainingAmount = remainingAmount.add(money);
				remainingSum.setRemainingAmount(remainingAmount);
				saveWholesaleCustomerDtl(remainingSum, bill, ++num);
			}
			return num;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public <ModelType> void saveWholesaleCustomerDtl(WholesaleCustomerRemainingSum remainingSum, ModelType modelType,
			int num) throws NumberFormatException, Exception {
		try {
			BillBalance bill = (BillBalance) modelType;
			StringBuffer remarkBuffer = new StringBuffer();
			WholesaleCustomerRemainingDtl remainingDtl = new WholesaleCustomerRemainingDtl();
			remainingDtl.setType(WholesaleRemainingTypeEnums.DEDUCTION_REBATE.getTypeNo());
			remainingDtl.setMainId(remainingSum.getId());
			remainingDtl.setPrePaymentId(0);
			remainingDtl.setMoney(bill.getMoney());
			remainingDtl.setRemainingAmount(remainingSum.getRemainingAmount());
			remainingDtl.setRefNo(bill.getBillNo());
			if (bill.getStatus() == BillBalanceZoneStatusEnums.INVALID.getTypeNo()) {
				if (StringUtils.isBlank(bill.getRemark())) {
					remarkBuffer.append(" 结算单打回!");
				}
			}
			if (StringUtils.isBlank(bill.getRemark())) {
				remarkBuffer.append(" 相关单号：").append(bill.getBillNo()).append(" ");
			}
			remainingDtl.setCreateTime(new Date());
			remarkBuffer.append(bill.getRemark() == null ? "" : bill.getRemark());
			remainingDtl.setRemark(remarkBuffer.toString());
			// remainingDtl =
			// customerRemainingDtlService.setDtlPosition(remainingDtl);
			DecimalFormat df = new DecimalFormat("0000");
			remainingDtl.setPosition(Long.parseLong(DateUtil.getCurrentDateTimeToStr() + df.format(num)));
			customerRemainingDtlService.add(remainingDtl);

		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	// 品牌扣项与票前返利分摊
	private void apportionDeduction(BillBalance billBalance) throws ManagerException {
		BigDecimal sendAmount = BigDecimal.ZERO; // 结算单出库金额
		BigDecimal otherAmount = BigDecimal.ZERO; // 全量分摊金额 （票前返利 +
													// 销售明细中不存在的品牌的扣项）
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("balanceNo", billBalance.getBillNo());
			SimplePage page = new SimplePage();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			List<BillSaleBalance> saleList = billSaleBalanceService.findByPage(page, null, null, params);
			// 获取明细中所包含的品牌
			List<BillSaleBalance> brandDeductList = billSaleBalanceService.selectSendAmountDeductByBrand(billBalance
					.getBillNo());
			if (brandDeductList != null && brandDeductList.size() > 0) {
				for (BillSaleBalance temp : brandDeductList) {
					if (temp.getSendAmount().compareTo(BigDecimal.ZERO) == 0) {
						params.put("brandNo", temp.getBrandNo());
						List<BillSaleBalance> templist = billSaleBalanceService.findByPage(page, null, null, params);
						if (templist != null && templist.size() > 0) {
							// 出库金额为0，销售明细分摊 (此品牌出库明细与退货相抵冲)
							apportionSendAmountIsZeroAndHaveDtl(saleList, templist, temp.getDeductionAmount());
						} else {
							// 无销售明细，扣项计入全量分摊金额
							otherAmount = otherAmount.add(temp.getDeductionAmount());
						}
					} else {
						// 品牌扣项分摊 (有销售明细，且品牌汇总出库金额不为0)
						apportionBrandDeduct(saleList, temp.getBrandNo(), temp.getSendAmount(),
								temp.getDeductionAmount());
					}
				}
			}

//			params.clear();
//			params.put("billNo", billBalance.getBillNo());
//			List<BillBalance> billList = billBalanceService.findByPage(page, null, null, params);
//			if (billList != null && billList.size() > 0) {
//				otherAmount = BigDecimalUtil.subtract(BigDecimalUtil.add(otherAmount, billList.get(0).getRebateAmount()), billList.get(0)
//						.getOtherPrice()); // 结算单票前返利和其他费用计入全量分摊金额
//			}
			BillSaleBalance bill = billSaleBalanceService.selectSendAmountByBalanceNo(billBalance.getBillNo());
			if (bill != null) {
				sendAmount = bill.getSendAmount();
			}
			// 全量金额分摊
			apportionOtherAmount(saleList, sendAmount, otherAmount);

			// 更新销售明细
			for (BillSaleBalance sale : saleList) {
				billSaleBalanceService.modifyById(sale);
			}

		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/***
	 * 品牌扣项分摊
	 * 
	 * @param saleList
	 *            销售明细
	 * @param brandNo
	 *            品牌编码
	 * @param outAmount
	 *            单品牌销售总额
	 * @param deductionAmount
	 *            单品牌扣项总额
	 */
	private void apportionBrandDeduct(List<BillSaleBalance> saleList, String brandNo, BigDecimal outAmount,
			BigDecimal deductionAmount) {

		String itemNo = null;
		BigDecimal maxRatio = null;
		BigDecimal tempRatio = null;
		BigDecimal actualDeduction = BigDecimal.ZERO;
		// 品牌扣项分摊至货品
		if (saleList != null && saleList.size() > 0) {
			for (BillSaleBalance sale : saleList) {
				if (sale.getBrandNo().equals(brandNo)) {
					tempRatio = sale.getCost().multiply(new BigDecimal(sale.getSendQty()))
							.divide(outAmount, 4, BigDecimal.ROUND_HALF_UP);
					;
					if (maxRatio == null || maxRatio.compareTo(tempRatio) < 0) {
						maxRatio = tempRatio;
						itemNo = sale.getItemNo();
					}
					BigDecimal deductCost = tempRatio.multiply(deductionAmount).setScale(4, BigDecimal.ROUND_HALF_UP);
					;
					actualDeduction = actualDeduction.add(deductCost);
					sale.setOtherDeductCost(sale.getOtherDeductCost().add(deductCost));
				}
			}
			// 如果存在分摊差异，则将尾差直接写入扣率最大的货品上
			if (deductionAmount.compareTo(actualDeduction) != 0) {
				for (BillSaleBalance tempSale : saleList) {
					if (tempSale.getItemNo().equals(itemNo)) {
						tempSale.setOtherDeductCost(tempSale.getOtherDeductCost().add(
								deductionAmount.subtract(actualDeduction)));
						break;
					}
				}
			}
		}
	}

	/***
	 * 全量分摊 (票前返利 + 销售明细中不存在的品牌的扣项)
	 * 
	 * @param saleList
	 *            销售明细
	 * @param sendAmount
	 *            结算单总额
	 * @param otherAmount
	 *            全量分摊金额
	 */
	private void apportionOtherAmount(List<BillSaleBalance> saleList, BigDecimal sendAmount, BigDecimal otherAmount) {
		String itemNo = null;
		BigDecimal maxRatio = null;
		BigDecimal tempRatio = null;
		BigDecimal actualDeduction = BigDecimal.ZERO;
		if (saleList != null && saleList.size() > 0) {
			if (sendAmount.compareTo(BigDecimal.ZERO) != 0) {
				for (BillSaleBalance sale : saleList) {
					tempRatio = sale.getCost().multiply(new BigDecimal(sale.getSendQty()))
							.divide(sendAmount, 4, BigDecimal.ROUND_HALF_UP);
					if (maxRatio == null || maxRatio.compareTo(tempRatio) < 0) {
						maxRatio = tempRatio;
						itemNo = sale.getItemNo();
					}
					BigDecimal deductCost = tempRatio.multiply(otherAmount).setScale(4, BigDecimal.ROUND_HALF_UP);
					actualDeduction = actualDeduction.add(deductCost);
					sale.setOtherDeductCost(sale.getOtherDeductCost().add(deductCost));
				}
				// 如果存在分摊差异，则将尾差直接写入扣率最大的货品上
				if (otherAmount.compareTo(actualDeduction) != 0) {
					for (BillSaleBalance tempSale : saleList) {
						if (tempSale.getItemNo().equals(itemNo)) {
							tempSale.setOtherDeductCost(tempSale.getOtherDeductCost().add(
									otherAmount.subtract(actualDeduction)));
							break;
						}
					}
				}
			} else {
				// 出库金额为0，销售明细分摊 (此结算单出库明细与退货相抵冲)
				apportionSendAmountIsZeroAndHaveDtl(saleList, saleList, otherAmount);
			}
		}
	}

	// 扣项销售为0的处理方式
	private void apportionSendAmountIsZeroAndHaveDtl(List<BillSaleBalance> saleList, List<BillSaleBalance> doSaleList,
			BigDecimal otherAmount) {
		int temp = otherAmount.compareTo(BigDecimal.ZERO);
		BigDecimal tempSendAmount = BigDecimal.ZERO;
		List<BillSaleBalance> tempList = new ArrayList<BillSaleBalance>();
		if (temp != 0) {
			if (temp > 0) {
				for (BillSaleBalance sale : doSaleList) {
					if (sale.getSendQty() > 0) {
						tempList.add(sale);
						tempSendAmount = tempSendAmount.add(sale.getCost().multiply(new BigDecimal(sale.getSendQty())));
					}
				}
			} else {
				for (BillSaleBalance sale : doSaleList) {
					if (sale.getSendQty() < 0) {
						tempList.add(sale);
						tempSendAmount = tempSendAmount.add(sale.getCost().multiply(new BigDecimal(sale.getSendQty())));
					}
				}
			}
			// 调用全量分摊的逻辑
			// apportionOtherAmount(tempList, tempSendAmount, otherAmount);
			String itemNo = null;
			String billNo = null;
			BigDecimal maxRatio = BigDecimal.ZERO;
			BigDecimal tempRatio = BigDecimal.ZERO;
			BigDecimal actualDeduction = BigDecimal.ZERO;
			if (saleList != null && saleList.size() > 0) {
				if (tempSendAmount.compareTo(BigDecimal.ZERO) != 0) {
					for (BillSaleBalance sale : saleList) {
						if (tempList.contains(sale)) {
							tempRatio = sale.getCost().multiply(new BigDecimal(sale.getSendQty()))
									.divide(tempSendAmount, 4, BigDecimal.ROUND_HALF_UP);
							if (maxRatio == null || maxRatio.compareTo(tempRatio) < 0) {
								maxRatio = tempRatio;
								itemNo = sale.getItemNo();
								billNo = sale.getBillNo();
							}
							BigDecimal deductCost = tempRatio.multiply(otherAmount).setScale(4,
									BigDecimal.ROUND_HALF_UP);
							actualDeduction = actualDeduction.add(deductCost);
							sale.setOtherDeductCost(sale.getOtherDeductCost().add(deductCost));
						}
					}
					// 如果存在分摊差异，则将尾差直接写入扣率最大的货品上
					if (otherAmount.compareTo(actualDeduction) != 0) {
						for (BillSaleBalance tempSale : saleList) {
							if (tempSale.getItemNo().equals(itemNo) && tempSale.getBillNo().equals(billNo)) {
								tempSale.setOtherDeductCost(tempSale.getOtherDeductCost().add(
										otherAmount.subtract(actualDeduction)));
								break;
							}
						}
					}
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public Boolean unfrozenAmount(List<BillSaleBalance> billSaleBalanceList) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, BigDecimal> customerMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> billMap = new HashMap<String, BigDecimal>();
		Map<String, Integer> mainIdMap = new HashMap<String, Integer>();
		List<WholesaleCustomerRemainingDtl> remainingDtlList = new ArrayList<WholesaleCustomerRemainingDtl>();
		List<String> customerNoArray = new ArrayList<String>();
		
		try {
			for (BillSaleBalance bill : billSaleBalanceList) {
				customerNoArray.add(bill.getBuyerNo());    
			}
			params.put("customerNoArray",customerNoArray);
			params.put("status", GmsBillStatusEnums.PARTRECEIPT.getStatus());
			List<WholesaleCustomerRemainingSum> remainingSumList = customerRemainingSumService.findByBiz(null, params);
			List<CustomerOrderRemain> orderRemainList = customerOrderRemainService.selectLatestOrdersByParams(params);
			
			for (WholesaleCustomerRemainingSum remainingSum : remainingSumList) {
				mainIdMap.put(remainingSum.getCustomerNo(),remainingSum.getId());
			}
			for (BillSaleBalance billSaleBalance : billSaleBalanceList) {
				billSaleBalance.setUnfrozenDate(DateUtil.getCurrentDateTime());				
				String customerNo = billSaleBalance.getBuyerNo();
				String billNo = billSaleBalance.getBillNo();
				Integer unfrozenQty = billSaleBalance.getSendQty().intValue() > 0 ? -billSaleBalance.getUnfrozenQty():billSaleBalance.getUnfrozenQty();
				BigDecimal unforzenAmount = BigDecimalUtil.multiInt(billSaleBalance.getCost(), unfrozenQty);
				// 按客户-订单统计解冻金额
				if (null == customerMap.get(customerNo)) {
					customerMap.put(customerNo, unforzenAmount);
				} else {
					customerMap.put(customerNo,BigDecimalUtil.add(customerMap.get(customerNo),unforzenAmount));
				}
				if (null == billMap.get(billNo)) {
					billMap.put(billNo, unforzenAmount);
					WholesaleCustomerRemainingDtl remainingDtl = new WholesaleCustomerRemainingDtl();
					remainingDtl.setBillNo(billNo);
					remainingDtl.setRefNo(billNo);
					remainingDtl.setMainId(mainIdMap.get(customerNo));
					remainingDtl.setPosition(LongSequenceGenerator.getTimestamp());
					remainingDtl.setBillDate(DateUtil.getCurrentDateTime());
					remainingDtl.setBillType(BillTypeEnums.SALEOUTS.getRequestId());
					remainingDtl.setBizType(WholesaleRemainingTypeEnums.UNFROZEN_RETURN.getTypeNo());
					remainingDtlList.add(remainingDtl);
				} else {
					billMap.put(billNo,BigDecimalUtil.add(billMap.get(billNo),unforzenAmount));
				}
			}

			for (CustomerOrderRemain customerOrderRemain : orderRemainList) {
				customerOrderRemain.setAmount(customerMap.get(customerOrderRemain.getCustomerNo()));
			}
			for (WholesaleCustomerRemainingSum remainingSum : remainingSumList) {
				remainingSum.setSendAmount(customerMap.get(remainingSum.getCustomerNo()));
			}
            for (WholesaleCustomerRemainingDtl remainingDtl : remainingDtlList) {
            	remainingDtl.setMoney(billMap.get(remainingDtl.getBillNo()));
			}

			//批量解冻批发退货单
			billSaleBalanceMapper.batchUpdateFrozenBill(billSaleBalanceList);
			//批量修改订单余额
			if (null != orderRemainList && orderRemainList.size()>0) {
				billSaleBalanceMapper.batchUpdateOrderRemaining(orderRemainList);
			}
			//批量修改客户余额
			billSaleBalanceMapper.batchUpdateRemainingSum(remainingSumList);
			// 批量客户余额增加明细
			customerRemainingDtlService.batchInsertRemainingDtl(remainingDtlList);
			return true;
		} catch (Exception e) {
			logger.error("解冻退货金额失败", e);
			throw new ManagerException(e.getMessage(), e);
		}
	}

}
