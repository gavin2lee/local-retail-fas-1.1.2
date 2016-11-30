package cn.wonhigh.retail.fas.api.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiVo;
import cn.wonhigh.retail.fas.api.manager.BillBalanceApiManagerImpl;
import cn.wonhigh.retail.fas.api.manager.BillBuyBalanceApiManagerImpl;
import cn.wonhigh.retail.fas.api.model.ApiMessage;
import cn.wonhigh.retail.fas.api.model.BillSplitSettlePathDtlModel;
import cn.wonhigh.retail.fas.api.model.SettlePathDtlModel;
import cn.wonhigh.retail.fas.api.service.BillBuyBalanceApiService;
import cn.wonhigh.retail.fas.api.service.BillSaleBalanceApiService;
import cn.wonhigh.retail.fas.api.service.BrandApiService;
import cn.wonhigh.retail.fas.api.service.CompanyAccountingPeriodService;
import cn.wonhigh.retail.fas.api.service.FinancialAccountApiService;
import cn.wonhigh.retail.fas.api.service.OrganApiService;
import cn.wonhigh.retail.fas.api.service.PurchasePriceApiService;
import cn.wonhigh.retail.fas.api.utils.Validator;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillPriceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.pms.api.service.BillPurchaseOrderApi;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

public class BaroqueBillBuyBalanceHandlerImpl extends BillBalanceApiManagerImpl implements BillBuyBalanceHandler {

	public BaroqueBillBuyBalanceHandlerImpl() {
		logger = Logger.getLogger(BillBuyBalanceApiManagerImpl.class);
	}

	private Logger logger;

	@Resource
	private BillBuyBalanceApiService billBuyBalanceApiService;

	@Resource
	private BillSaleBalanceApiService billSaleBalanceApiService;

	@Resource
	private FinancialAccountApiService financialAccountApiService;

	@Resource
	private PurchasePriceApiService purchasePriceApiService;

	@Resource
	private BrandApiService brandApiService;

	@Resource
	private CompanyAccountingPeriodService companyAccountingPeriodService;

	@Resource
	private OrganApiService organApiService;

	@Resource
	private RegionCostAccountingStrategy baroqueRegionCostAccountingStrategy;

	@Resource
	private BillBuyBalanceAdditionalStrategy baroqueBillBuyBalanceAdditionalStrategy;

	@Resource
	private BillPurchaseOrderApi billPurchaseOrderApiManager;
	@Override
	public boolean process(List<BillBalanceApiDto> lstBill) throws RpcException {
		boolean result = false;
		if (!Validator.validateAndConvertModel(lstBill)) {
			throw new RpcException("fas", "数据校验不通过!");
		}
		try {
			// 收货单（入库单）获取原相关发货单（出库单）信息
			this.formatReceiveBill(lstBill);

			if (this.isNeedSplit(lstBill)) {// 巴洛克业务验收单才拆单
				this.decorateBillBalanceApiDto(lstBill);//特殊单据特殊处理
				ApiMessage message = this.splitBill(lstBill);
				if (message != null) {
					this.addFailureLog(lstBill.get(0), message.getErrorMsg());
					throw new RpcException(message.getProjectName(), message.getErrorMsg());
				}
				return result;
			}
			this.fullfillRemark(lstBill);
			result = this.billBuyBalanceApiService.batchInsert(lstBill) > 0;
			return result;

		} catch (ServiceException e) {
			logger.error("调用insert方法失败:" + e.getMessage(), e);
			this.addFailureLog(lstBill.get(0), "批量插入数据失败");
			throw new RpcException(e.getMessage(), e);
		}
	}
	/**
	 * 按不同单据类型装饰BillBalanceApiDto
	 * @param lstBill
	 */
	private void decorateBillBalanceApiDto(List<BillBalanceApiDto> lstBill) {
		if (null != lstBill && lstBill.size() > 0) {
			Integer billType = lstBill.get(0).getBillType();
			if (billType.intValue() == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
				this.decorateBillBalanceApiDtoForReturnOwn(lstBill);
			}
		}
		
	}
	
	/**
	 * 从PMS获取remark字段
	 * @param lst
	 */
	private void fullfillRemark(List<BillBalanceApiDto> lst){
		if(null != lst && lst.size()>0){
			for(BillBalanceApiDto dto : lst){
				//获取PMS中的remark
				if(!StringUtils.isEmpty(dto.getOrderNo())){
					try{
						cn.wonhigh.retail.pms.api.vo.BillPurchaseOrder order = billPurchaseOrderApiManager.getBillPurchaseOrder(dto.getOrderNo());
						if(null!=order){
							dto.setRemark(order.getRemark());
						}
					}catch(Exception e){
						logger.error("获取PMS备注失败:" + e.getMessage(), e);
					}
				}
			}
		}
	}
	/**
	 * 原厂退残单BillBalanceApiDto
	 * @param lstBill
	 */
	private void decorateBillBalanceApiDtoForReturnOwn(List<BillBalanceApiDto> lstBill) {
		if (null != lstBill && lstBill.size() > 0) {
			for (BillBalanceApiDto dto : lstBill) {
				dto.setReceiveDate(dto.getSendDate());
				dto.setReceiveQty(dto.getSendQty());
			}
		}
	}

	/**
	 * 检验单据是否需拆单(巴洛克业务验收单拆单，到货单不拆单)
	 * 
	 * @param lstBill
	 *            List<BillBalanceApiDto>
	 * @return boolean
	 */
	protected boolean isNeedSplit(List<BillBalanceApiDto> lstBill) {
		// FIRST_ORDER(0, "订货"),
		// REPLENISH_ORDER(1, "补货")
		Integer billType = lstBill.get(0).getBillType();
		Integer bizType = lstBill.get(0).getBizType();
		// 巴洛克业务验收单和原残退厂发货单才拆单
		if (billType.intValue() == BillTypeEnums.RECEIPT.getRequestId().intValue()
				&& bizType != null
				&& (bizType.intValue() == BizTypeEnums.FIRST_ORDER.getStatus()
						|| bizType.intValue() == BizTypeEnums.REPLENISH_ORDER.getStatus()
						|| bizType.intValue() == BizTypeEnums.FIRST_ORDER_DIFF.getStatus() || bizType.intValue() == BizTypeEnums.REPLENISH_ORDER_DIFF
						.getStatus())) {
			return true;
		} else if (billType.intValue() == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
			return true;
		}
		return false;
	}

	/**
	 * 组装采购类的BillBalanceApiDto对象
	 * 
	 * @param dto
	 *            BillBalanceApiDto
	 * @param lstPathDtl
	 *            结算路径集合
	 * @param lstBuyBalance
	 *            内存中已保存的单据明细集合
	 * @param index
	 *            结算路径的序号
	 * @return BillBalanceApiDto
	 * @throws ServiceException
	 *             异常
	 */
	private BillBalanceApiVo generateBuyBalance(BillBalanceApiDto dto, List<SettlePathDtlModel> lstPathDtl,
			List<BillBalanceApiDto> lstBuyBalance, int index) throws ServiceException {
		BillBalanceApiVo newDto = super.createApiVo(dto);
		// 取拆单价格
		BigDecimal regionCost = BigDecimal.ZERO;
		PurchasePrice priceDto = purchasePriceApiService.findBillPurchasePrice(dto.getItemNo(), dto.getSupplierNo(),
				dto.getSendDate());

		if (BillPriceTypeEnums.PURCHASE_PRICE.getTypeNo().equals(lstPathDtl.get(index).getFinancialBasis())) {
			if (priceDto != null) {
				regionCost = priceDto.getPurchasePrice();
			}
		} else {
			try {
				regionCost = this.baroqueRegionCostAccountingStrategy.caculateRegionCost(dto.getBrandNo(),
						dto.getCategoryNo(), dto.getBuyerNo(), lstPathDtl.get(index - 1).getCompanyNo(),
						dto.getSupplierNo(), dto.getItemNo(), dto.getReceiveQty(), dto.getReceiveDate());
			} catch (Exception e) {
				logger.error(String.format("Baroque计算地区价错误,original_bill_no = %s", newDto.getOriginalBillNo()), e);
			}
		}
		newDto.setCost(regionCost);
		newDto.setExclusiveCost(BigDecimalUtil.multi(newDto.getCost(),
				BigDecimalUtil.subtract(new BigDecimal(1), newDto.getTaxRate())));
		// 如果结算路径设置了3级，则需要拆单
		if (lstPathDtl.size() > 2) {
			newDto.setBillNo("");
			newDto.setIsSplit(1);
		}
		if (index == 1) {
			newDto.setSalerNo(newDto.getSupplierNo());
			newDto.setSalerName(newDto.getSupplierName());
		} else {
			newDto.setSalerNo(lstPathDtl.get(index - 1).getCompanyNo());
			newDto.setSalerName(lstPathDtl.get(index - 1).getCompanyName());
		}// 总部和地区
		if (priceDto != null) {
			newDto.setPurchasePrice(priceDto.getPurchasePrice());
		}
		newDto.setBuyerNo(lstPathDtl.get(index).getCompanyNo()); // 中间结算公司
		newDto.setBuyerName(lstPathDtl.get(index).getCompanyName());
		newDto.setPathNo(lstPathDtl.get(index).getPathNo());
		newDto.setBalanceType(BalanceTypeEnums.BAROQUE_RECEIPT.getTypeNo());
		return newDto;
	}

	/**
	 * 拆单
	 * 
	 * @param lstBill
	 *            待拆单的单据集合
	 * @return ApiMessage 拆单结果对象
	 * @throws ServiceException
	 *             异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	protected ApiMessage splitBill(List<BillBalanceApiDto> lstBill) throws ServiceException {
		String billNo = lstBill.get(0).getBillNo();
		String pathNo = lstBill.get(0).getPathNo();

		ApiMessage message = null;
		// 查询该单据已结算的记录数
		message = this.ValidateBillWhetherSettled(billNo, pathNo);
		if (message != null) {
			return message;
		}
		List<BillBalanceApiDto> lstBuyBalance = new ArrayList<BillBalanceApiDto>();
		List<BillBalanceApiDto> lstSaleBalance = new ArrayList<BillBalanceApiDto>();

		for (int i = 0; i < lstBill.size(); i++) {
			BillBalanceApiDto dto = lstBill.get(i);
			// 查询结算路径
			BillSplitSettlePathDtlModel billSplitSettlePathDtl = this.findSettlePathDtls(dto);
			if (billSplitSettlePathDtl.getList() == null || billSplitSettlePathDtl.getList().size() < 2) {
				message = new ApiMessage();
				message.setErrorCode(billSplitSettlePathDtl.getErrorCode());
				message.setErrorMsg(billSplitSettlePathDtl.getErrorMsg());
				return message;
			}
			/*
			 * 判断结算公司关账日
			 */
			String settleCompanyNo = this.setSettleCompanyNo(dto, billSplitSettlePathDtl);
			Date settleDate = this.setSettleDate(dto);
			String settleBrandNo = dto.getBrandNo();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", settleCompanyNo);

			message = this.validateBrand(settleBrandNo, params);
			if (message != null) {
				return message;
			}
			message = this.ValidateCompanyBrandSettlePeriod(settleDate, params);
			if (message != null) {
				return message;
			}
			// 如果结算路径只设置了2级，则不拆单
			if (billSplitSettlePathDtl.getList().size() < 3) {
				BillBalanceApiVo buyBalance = this.generateBuyBalance(dto, billSplitSettlePathDtl.getList(),
						lstBuyBalance, 1);
				lstBuyBalance.add(buyBalance);
				continue;
			}
			// 判断第三级结算主体的父项公司编码是否等于第二级结算主体编码
			boolean splitFlag = this.setSplitFlag(billSplitSettlePathDtl); // 是否需要拆出第三方的标志
			this.handleSplitBill(dto, billSplitSettlePathDtl, lstBuyBalance, lstSaleBalance, splitFlag);
		}
		// 删除原数据
		for (BillBalanceApiDto dto : lstBill) {
			billBuyBalanceApiService.deleteByBillNoAndItemNo(dto.getBillNo(), dto.getItemNo());
			billSaleBalanceApiService.deleteByBillNoAndItemNo(dto.getBillNo(), dto.getItemNo());
		}
		int iCount = 0;
		// 批量插入拆单明细汇总
		if (lstBuyBalance.size() > 0) {
			this.fullfillRemark(lstBuyBalance);//从PMS获取备注
			iCount = billBuyBalanceApiService.batchInsert(lstBuyBalance);
		}
		if (lstSaleBalance.size() > 0) {
			billSaleBalanceApiService.batchInsert(lstSaleBalance);
			this.baroqueBillBuyBalanceAdditionalStrategy.process(lstSaleBalance);
		}
		if (iCount > 0) {
			this.addSuccessLog(lstBill.get(0));
			lstBill.add(lstBuyBalance.get(lstBuyBalance.size() - 1));
		}
		return null;
	}

	private void handleSplitBill(BillBalanceApiDto dto, BillSplitSettlePathDtlModel billSplitSettlePathDtl,
			List<BillBalanceApiDto> lstBuyBalance, List<BillBalanceApiDto> lstSaleBalance, boolean splitFlag)
			throws ServiceException {
		int buySplitIndex = 0, saleSplitIndex = 0;
		for (int j = 1; j < billSplitSettlePathDtl.getList().size(); j++) {
			// 供应商与总部间
			if (j == 1) {
				// 巴洛克业务验收单需要拆单，到货单 不需要拆出此类单据
				if (!BillTypeEnums.ASN.getRequestId().equals(dto.getBillType())) {
					// 发方供应商，收方总部，总部向供应商采购
					BillBalanceApiVo buyBalance = this.generateBuyBalance(dto, billSplitSettlePathDtl.getList(),
							lstBuyBalance, j);
					buyBalance.setBillNo(dto.getBillNo() + "_" + (buySplitIndex + 1));
					buySplitIndex++;
					lstBuyBalance.add(buyBalance);
				}
			}
			// 总部与地区间
			else {
				if (!splitFlag) {
					continue;
				}
				// 获取中间结算主体所属大区

				ZoneInfo zoneInfo = organApiService.getZoneInfoByCompanyNo(billSplitSettlePathDtl.getList().get(j - 1)
						.getCompanyNo());

				// 巴洛克业务验收单需要拆单，到货单 不需要拆出此类单据
				if (!BillTypeEnums.ASN.getRequestId().equals(dto.getBillType())) {
					// 发方总部，收方地区，总部销售给地区
					BillBalanceApiVo saleBalance = this.generateSaleBalance(dto, billSplitSettlePathDtl.getList(),
							lstSaleBalance, j);
					saleBalance.setBillNo(dto.getBillNo() + "_" + (saleSplitIndex + 1));
					saleSplitIndex++;
					if (zoneInfo != null) {
						saleBalance.setZoneNoFrom(zoneInfo.getZoneNo());
						saleBalance.setZoneNameFrom(zoneInfo.getName());
					}
					lstSaleBalance.add(saleBalance);
				}
				// 发方总部，收方地区，地区向总部采购
				BillBalanceApiVo buyBalance = this.generateBuyBalance(dto, billSplitSettlePathDtl.getList(),
						lstBuyBalance, j);
				buyBalance.setBillNo(dto.getBillNo() + "_" + (buySplitIndex + 1));
				buySplitIndex++;
				if (zoneInfo != null) {
					buyBalance.setZoneNoFrom(zoneInfo.getZoneNo());
					buyBalance.setZoneNameFrom(zoneInfo.getName());
				}
				lstBuyBalance.add(buyBalance);
			}
		}

	}

	private boolean setSplitFlag(BillSplitSettlePathDtlModel billSplitSettlePathDtl) throws ServiceException {
		String thridCompanyNo = billSplitSettlePathDtl.getList().get(2).getCompanyNo();
		String secondCompanyNo = billSplitSettlePathDtl.getList().get(1).getCompanyNo();
		FinancialAccount financialAccount = financialAccountApiService.findByCompanyNo(thridCompanyNo);
		if (financialAccount != null && secondCompanyNo.equals(financialAccount.getParentCompany())) {
			return false;
		}
		return true;
	}

	private Date setSettleDate(BillBalanceApiDto dto) {
		Date settleDate;
		// 取结算日期，验收单取收货日期，其他取发货日期
		if (BillTypeEnums.RECEIPT.getRequestId().equals(dto.getBillType())) {
			settleDate = dto.getReceiveDate();
		} else {
			settleDate = dto.getSendDate();
		}
		return settleDate;
	}

	private String setSettleCompanyNo(BillBalanceApiDto dto, BillSplitSettlePathDtlModel billSplitSettlePathDtl) {
		String settleCompanyNo;
		// 取结算公司，验收单且结算路径为三级时取第三级结算公司，其他取第二级
		if (BillTypeEnums.RECEIPT.getRequestId().equals(dto.getBillType())
				&& billSplitSettlePathDtl.getList().size() > 2) {
			settleCompanyNo = billSplitSettlePathDtl.getList().get(2).getCompanyNo();
		} else {
			settleCompanyNo = billSplitSettlePathDtl.getList().get(1).getCompanyNo();
		}
		return settleCompanyNo;
	}

	private ApiMessage validateBrand(String settleBrandNo, Map<String, Object> params) throws ServiceException {
		Brand brand = brandApiService.findByBrandNo(settleBrandNo);
		if (null == brand) {
			ApiMessage message = new ApiMessage();
			message.setErrorCode("0011");
			message.setErrorMsg("未查询到品牌部信息");
			return message;
		}
		params.put("brandNo", brand.getSysNo());
		return null;
	}

	private ApiMessage ValidateCompanyBrandSettlePeriod(Date settleDate, Map<String, Object> params)
			throws ServiceException {
		CompanyBrandSettlePeriod companyBrandSettlePeriod = companyAccountingPeriodService
				.getCompanyBrandBalanceDate(params);
		if (companyBrandSettlePeriod != null) {
			if (companyBrandSettlePeriod.getAccountSettleTime().after(settleDate)) {
				ApiMessage message = new ApiMessage();
				message.setErrorCode("0011");
				message.setErrorMsg("日期小于 " + companyBrandSettlePeriod.getCompanyName() + " "
						+ companyBrandSettlePeriod.getBrandName() + " 财务结账日"
						+ companyBrandSettlePeriod.getAccountSettleTime());
				return message;
			}
			if (companyBrandSettlePeriod.getSupplierSettleTime().after(settleDate)) {
				ApiMessage message = new ApiMessage();
				message.setErrorCode("0012");
				message.setErrorMsg("日期小于 " + companyBrandSettlePeriod.getCompanyName() + " "
						+ companyBrandSettlePeriod.getBrandName() + " 厂商结账日"
						+ companyBrandSettlePeriod.getSupplierSettleTime());
				return message;
			}
		}
		return null;
	}

	private ApiMessage ValidateBillWhetherSettled(String billNo, String pathNo) throws ServiceException {

		// pathNo为空则为GMS系统传过来的数据
		if (StringUtils.isEmpty(pathNo)) {
			int buyCount = billBuyBalanceApiService.selectCountByBillNo(billNo);
			if (buyCount > 0) {
				ApiMessage message = new ApiMessage();
				message.setErrorCode("0010");
				message.setErrorMsg("该单据已结算,不能重新拆单");
				return message;
			}
			int saleCount = billSaleBalanceApiService.selectCountByBillNo(billNo);
			if (saleCount > 0) {
				ApiMessage message = new ApiMessage();
				message.setErrorCode("0010");
				message.setErrorMsg("该单据已结算,不能重新拆单");
				return message;
			}
		}
		return null;
	}

	/**
	 * 组装销售类的BillBalanceApiDto对象
	 * 
	 * @param dto
	 *            BillBalanceApiDto
	 * @param lstPathDtl
	 *            结算路径集合
	 * @param lstSaleBalance
	 *            内存中已保存的单据明细集合
	 * @param index
	 *            结算路径的序号
	 * @return BillBalanceApiDto
	 * @throws ServiceException
	 *             异常
	 */
	private BillBalanceApiVo generateSaleBalance(BillBalanceApiDto dto, List<SettlePathDtlModel> lstPathDtl,
			List<BillBalanceApiDto> lstSaleBalance, int index) throws ServiceException {
		BillBalanceApiVo newDto = this.createApiVo(dto);
		newDto.setBillNo("");
		// 取拆单价格
		BigDecimal regionCost = BigDecimal.ZERO;
		PurchasePrice priceDto = purchasePriceApiService.findBillPurchasePrice(dto.getItemNo(), dto.getSupplierNo(),
				dto.getSendDate());

		if (BillPriceTypeEnums.PURCHASE_PRICE.getTypeNo().equals(lstPathDtl.get(index).getFinancialBasis())) {
			if (priceDto != null) {
				regionCost = priceDto.getPurchasePrice();
			}
		} else {
			try {
				regionCost = this.baroqueRegionCostAccountingStrategy.caculateRegionCost(dto.getBrandNo(),
						dto.getCategoryNo(), dto.getBuyerNo(), lstPathDtl.get(index - 1).getCompanyNo(),
						dto.getSupplierNo(), dto.getItemNo(), dto.getReceiveQty(), dto.getReceiveDate());
			} catch (ServiceException e) {
				logger.error(String.format("Baroque计算地区价错误,original_bill_no = %s", newDto.getOriginalBillNo()), e);
			}
		}
		newDto.setCost(regionCost);
		newDto.setExclusiveCost(BigDecimalUtil.multi(newDto.getCost(),
				BigDecimalUtil.subtract(new BigDecimal(1), newDto.getTaxRate())));
		// 如果结算路径设置了3级，则需要拆单
		if (lstPathDtl.size() > 2) {
			newDto.setIsSplit(1);
		}
		if (priceDto != null) {
			newDto.setPurchasePrice(priceDto.getPurchasePrice());
		}
		newDto.setSalerNo(lstPathDtl.get(index - 1).getCompanyNo());
		newDto.setSalerName(lstPathDtl.get(index - 1).getCompanyName());
		newDto.setBuyerNo(lstPathDtl.get(index).getCompanyNo()); // 中间结算公司
		newDto.setBuyerName(lstPathDtl.get(index).getCompanyName());
		newDto.setPathNo(lstPathDtl.get(index).getPathNo());
		newDto.setBalanceType(BalanceTypeEnums.BAROQUE_RECEIPT.getTypeNo());
		return newDto;
	}

}
