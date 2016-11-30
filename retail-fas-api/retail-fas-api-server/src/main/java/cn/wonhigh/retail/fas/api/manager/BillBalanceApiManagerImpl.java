package cn.wonhigh.retail.fas.api.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiVo;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.api.model.ApiMessage;
import cn.wonhigh.retail.fas.api.model.BillSplitLogModel;
import cn.wonhigh.retail.fas.api.model.BillSplitSettlePathDtlModel;
import cn.wonhigh.retail.fas.api.model.SettlePathDtlModel;
import cn.wonhigh.retail.fas.api.service.BillBuyBalanceApiService;
import cn.wonhigh.retail.fas.api.service.BillSaleBalanceApiService;
import cn.wonhigh.retail.fas.api.service.BillSplitLogApiService;
import cn.wonhigh.retail.fas.api.service.BrandApiService;
import cn.wonhigh.retail.fas.api.service.CompanyAccountingPeriodService;
import cn.wonhigh.retail.fas.api.service.FinancialAccountApiService;
import cn.wonhigh.retail.fas.api.service.OrganApiService;
import cn.wonhigh.retail.fas.api.service.PayRelationshipApiService;
import cn.wonhigh.retail.fas.api.service.PurchasePriceApiService;
import cn.wonhigh.retail.fas.api.service.RegionCostAccountingService;
import cn.wonhigh.retail.fas.api.service.SettlePathApiService;
import cn.wonhigh.retail.fas.api.utils.CommonUtils;
import cn.wonhigh.retail.fas.common.enums.BillPriceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.SettlePathBillBasisEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.SettlePathDtlQueryVo;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("billBalanceApiManager")
public class BillBalanceApiManagerImpl {

	private Logger logger = Logger.getLogger(BillBalanceApiManagerImpl.class);

	@Resource
	private SettlePathApiService settlePathApiService;

	@Resource
	private BillSplitLogApiService billSplitLogApiService;

	@Resource
	private RegionCostAccountingService regionCostAccountingService;

	@Resource
	private BillBuyBalanceApiService billBuyBalanceApiService;

	@Resource
	private BillSaleBalanceApiService billSaleBalanceApiService;

	@Resource
	private PurchasePriceApiService purchasePriceApiService;

	@Resource
	private FinancialAccountApiService financialAccountApiService;

	@Resource
	private CompanyAccountingPeriodService companyAccountingPeriodService;

	@Resource
	private OrganApiService organApiService;

	@Resource
	private BrandApiService brandApiService;

	@Resource
	private PayRelationshipApiService payRelationshipApiService;

	/**
	 * 判断单据是否是判断是否是批发/团购订单
	 * 
	 * @param billBalanceApiDto
	 *            单据
	 * @return ture or false
	 */
	protected boolean isOrderBill(BillBalanceApiDto billBalanceApiDto) {
		if (BillTypeEnums.SALEORDER.getRequestId() == billBalanceApiDto.getBillType().intValue()
				&& billBalanceApiDto.getBizType() != null
				&& (BizTypeEnums.WHOLESALE.getStatus().intValue() == billBalanceApiDto.getBizType().intValue()
						|| BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().intValue() == billBalanceApiDto.getBizType()
								.intValue()
						|| BizTypeEnums.WHOLESALE_RETURN.getStatus().intValue() == billBalanceApiDto.getBizType()
								.intValue()
						|| BizTypeEnums.GROUPSALE.getStatus().intValue() == billBalanceApiDto.getBizType().intValue() || BizTypeEnums.GROUPSALE_RETURN
						.getStatus().intValue() == billBalanceApiDto.getBizType().intValue())) {
			return true;
		}
		return false;
	}

	/**
	 * 验证单据头数据是否正确
	 * 
	 * @param billHeaderApiDto
	 *            单据头
	 * @return Map<String, String>
	 */
	Map<String, String> validateBillHeader(BillHeaderApiDto billHeaderApiDto) {
		Map<String, String> msgMap = new HashMap<String, String>();
		if (billHeaderApiDto == null) {
			msgMap.put("errorMsg", "对象为空");
			return msgMap;
		}
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isEmpty(billHeaderApiDto.getBillNo())) {
			sb.append("单据编码为空,");
		}
		if (billHeaderApiDto.getBillType() == null) {
			sb.append("单据类型为空,");
		}
		// if(billHeaderApiDto.getBizType() == null) {
		// sb.append("业务类型为空,");
		// }
		if (sb.length() > 0) {
			msgMap.put("errorMsg", sb.deleteCharAt(sb.length() - 1).toString());
		}
		return msgMap;
	}

	/**
	 * 检验单据是否需拆单
	 * 
	 * @param lstBill
	 *            List<BillBalanceApiDto>
	 * @return boolean
	 */
	protected boolean isSplitType(List<BillBalanceApiDto> lstBill) {
		BillBalanceApiDto dto = lstBill.get(0);
		Integer billType = dto.getBillType();
		Integer bizType = dto.getBizType();
		return this.isSplitBillType(billType, bizType);
	}


	/**
	 * 检验单据是否需拆单
	 * 
	 * @param billType
	 *            单据类型
	 * @param bizType
	 *            业务类型
	 * @return boolean
	 */
	boolean isSplitBillType(Integer billType, Integer bizType) {
		// 到货单 | 验收单（订补货的才拆单）
		if ((billType.intValue() == BillTypeEnums.ASN.getRequestId().intValue() || billType.intValue() == BillTypeEnums.RECEIPT
				.getRequestId().intValue()) && bizType != null && (bizType.intValue() == 0 || bizType.intValue() == 1 || bizType.intValue() ==60)) {
			return true;
		}
		// 退厂单(原残退厂拆单)
		if (billType.intValue() == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
			return true;
		}
		return false;
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

	protected ApiMessage splitBill(List<BillBalanceApiDto> lstBill) throws ServiceException {

		List<BillBalanceApiDto> lstBuyBalance = new ArrayList<BillBalanceApiDto>();
		List<BillBalanceApiDto> lstSaleBalance = new ArrayList<BillBalanceApiDto>();
		for (int i = 0; i < lstBill.size(); i++) {
			BillBalanceApiDto dto = lstBill.get(i);

			// 查询结算路径
			BillSplitSettlePathDtlModel billSplitSettlePathDtl = this.findSettlePathDtls(dto);
			if (billSplitSettlePathDtl.getList() == null || billSplitSettlePathDtl.getList().size() < 2) {
				ApiMessage message = new ApiMessage();
				message.setErrorCode(billSplitSettlePathDtl.getErrorCode());
				message.setErrorMsg(billSplitSettlePathDtl.getErrorMsg());
				return message;
			}

			/*
			 * 判断结算公司关账日
			 */
			String settleCompanyNo = null;
			Date settleDate = null;
			String settleBrandNo = dto.getBrandNo();
			// 取结算公司，验收单且结算路径为三级时取第三级结算公司，其他取第二级
			if (BillTypeEnums.RECEIPT.getRequestId().equals(dto.getBillType())
					&& billSplitSettlePathDtl.getList().size() > 2) {
				settleCompanyNo = billSplitSettlePathDtl.getList().get(2).getCompanyNo();
			} else {
				settleCompanyNo = billSplitSettlePathDtl.getList().get(1).getCompanyNo();
			}
			// 取结算日期，验收单取收货日期，其他取发货日期
			if (BillTypeEnums.RECEIPT.getRequestId().equals(dto.getBillType())) {
				settleDate = dto.getReceiveDate();
			} else {
				settleDate = dto.getSendDate();
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", settleCompanyNo);
			Brand brand = brandApiService.findByBrandNo(settleBrandNo);
			if (null == brand) {
				ApiMessage message = new ApiMessage();
				message.setErrorCode("0011");
				message.setErrorMsg("未查询到品牌部信息");
				return message;
			}
			params.put("brandNo", brand.getSysNo());
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

			// 如果结算路径只设置了2级，则不拆单
			if (billSplitSettlePathDtl.getList().size() < 3) {
				BillBalanceApiVo buyBalance = this.buildBuyBalance(dto, billSplitSettlePathDtl.getList(),
						lstBuyBalance, 1);
				lstBuyBalance.add(buyBalance);
				continue;
			}
			// 判断第三级结算主体的父项公司编码是否等于第二级结算主体编码
			boolean splitFlag = true; // 是否需要拆出第三方的标志
			String thridCompanyNo = billSplitSettlePathDtl.getList().get(2).getCompanyNo();
			String secondCompanyNo = billSplitSettlePathDtl.getList().get(1).getCompanyNo();
			FinancialAccount financialAccount = financialAccountApiService.findByCompanyNo(thridCompanyNo);
			if (financialAccount != null && secondCompanyNo.equals(financialAccount.getParentCompany())) {
				splitFlag = false;
			}
			// 总部到货差异单据处理
			int buySplitIndex = 0, saleSplitIndex = 0;
			Integer billType = dto.getBillType();
			Integer bizType = dto.getBizType();
			// 到货单差异单据处理
			if((billType.intValue() == BillTypeEnums.ASN.getRequestId().intValue() 
					|| billType.intValue() == BillTypeEnums.RECEIPT.getRequestId().intValue())&&
					bizType != null && (bizType.intValue() == 3 || bizType.intValue() == 4 || bizType.intValue() == 5
							|| bizType.intValue() == 6 || bizType.intValue() == 7 || bizType.intValue() == 8 || bizType.intValue() == 9) ){
				Integer refBizType = billBuyBalanceApiService.selectRefBizType(dto.getRefBillNo());
				if (refBizType != null && refBizType.intValue() == 60) {
					dto.setBizType(60 + bizType);
				}
			}
			// 如果结算路径设置了3级，则需要拆单
			for (int j = 1; j < billSplitSettlePathDtl.getList().size(); j++) {
				// 供应商与总部间
				if (j == 1) {
					// 验收单 不需要拆出此类单据
					if (!BillTypeEnums.RECEIPT.getRequestId().equals(dto.getBillType())) {
						// 发方供应商，收方总部，总部向供应商采购
						BillBalanceApiVo buyBalance = this.buildBuyBalance(dto, billSplitSettlePathDtl.getList(),
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
					ZoneInfo zoneInfo = organApiService.getZoneInfoByCompanyNo(billSplitSettlePathDtl.getList()
							.get(j - 1).getCompanyNo());

					// 验收单 不需要拆出此类单据
					if (!BillTypeEnums.RECEIPT.getRequestId().equals(dto.getBillType())) {
						// 发方总部，收方地区，总部销售给地区
						BillBalanceApiVo saleBalance = this.buildSaleBalance(dto, billSplitSettlePathDtl.getList(),
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
					BillBalanceApiVo buyBalance = this.buildBuyBalance(dto, billSplitSettlePathDtl.getList(),
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

		int iCount = 0;
		// 批量插入拆弹明细汇总
		if (lstBuyBalance.size() > 0) {
			iCount = billBuyBalanceApiService.batchInsert(lstBuyBalance);
		}
		if (lstSaleBalance.size() > 0) {
			billSaleBalanceApiService.batchInsert(lstSaleBalance);
		}
		if (iCount > 0) {
			this.addSuccessLog(lstBill.get(0));
			// 体育总部公司的到货单 验收单配价处理
			BillBalanceApiDto dto = lstBuyBalance.get(lstBuyBalance.size() - 1);
			if (null != dto.getIsSplit() && dto.getIsSplit().intValue() == 1) {
				String hqCompanyNo = financialAccountApiService.selectLeadRoleCompanyNos();
				if (CommonUtils.isNeedHanderPEValence(dto.getSalerNo(), hqCompanyNo, dto)) {
					payRelationshipApiService.handerPEValence(dto);
				}
				// 体育单据需要调换双方公司处理
				if (CommonUtils.isNeedChangePE(dto)) {
					payRelationshipApiService.changePE(dto);
				}
			}
		}

		return null;
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
	private BillBalanceApiVo buildBuyBalance(BillBalanceApiDto dto, List<SettlePathDtlModel> lstPathDtl,
			List<BillBalanceApiDto> lstBuyBalance, int index) throws ServiceException {
		BillBalanceApiVo newDto = this.createApiVo(dto);
		// 取拆单价格
		BigDecimal regionCost = BigDecimal.ZERO;
		if (BillPriceTypeEnums.PURCHASE_PRICE.getTypeNo().equals(lstPathDtl.get(index).getFinancialBasis())) {
			PurchasePrice priceDto = purchasePriceApiService.findBillPurchasePrice(dto.getItemNo(),
					dto.getSupplierNo(), dto.getSendDate());
			if (priceDto != null) {
				regionCost = priceDto.getPurchasePrice();
			}
		} else {
			regionCost = regionCostAccountingService.findRegionCost(newDto.getItemNo(), lstPathDtl.get(index)
					.getFinancialBasis(), newDto.getSendDate());
		}
		newDto.setCost(regionCost);
		if(null != newDto.getTaxRate() && 0 != newDto.getTaxRate().doubleValue()){
			newDto.setExclusiveCost(BigDecimalUtil.multi(newDto.getCost(),
					BigDecimalUtil.subtract(new BigDecimal(1), newDto.getTaxRate())));
		}
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
		}
		newDto.setBuyerNo(lstPathDtl.get(index).getCompanyNo()); // 中间结算公司
		newDto.setBuyerName(lstPathDtl.get(index).getCompanyName());
		newDto.setPathNo(lstPathDtl.get(index).getPathNo());
		return newDto;
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
	private BillBalanceApiVo buildSaleBalance(BillBalanceApiDto dto, List<SettlePathDtlModel> lstPathDtl,
			List<BillBalanceApiDto> lstSaleBalance, int index) throws ServiceException {
		BillBalanceApiVo newDto = this.createApiVo(dto);
		newDto.setBillNo("");
		// 取拆单价格
		BigDecimal regionCost = BigDecimal.ZERO;
		if (BillPriceTypeEnums.PURCHASE_PRICE.getTypeNo().equals(lstPathDtl.get(index).getFinancialBasis())) {
			PurchasePrice priceDto = purchasePriceApiService.findBillPurchasePrice(dto.getItemNo(),
					dto.getSupplierNo(), dto.getSendDate());
			if (priceDto != null) {
				regionCost = priceDto.getPurchasePrice();
			}
		} else {
			regionCost = regionCostAccountingService.findRegionCost(newDto.getItemNo(), lstPathDtl.get(index)
					.getFinancialBasis(), newDto.getSendDate());
		}
		newDto.setCost(regionCost);
		if(null != newDto.getTaxRate() && 0 != newDto.getTaxRate().doubleValue()){
			newDto.setExclusiveCost(BigDecimalUtil.multi(newDto.getCost(),
					BigDecimalUtil.subtract(new BigDecimal(1), newDto.getTaxRate())));
		}
		// 如果结算路径设置了3级，则需要拆单
		if (lstPathDtl.size() > 2) {
			newDto.setIsSplit(1);
		}

		newDto.setSalerNo(lstPathDtl.get(index - 1).getCompanyNo());
		newDto.setSalerName(lstPathDtl.get(index - 1).getCompanyName());
		newDto.setBuyerNo(lstPathDtl.get(index).getCompanyNo()); // 中间结算公司
		newDto.setBuyerName(lstPathDtl.get(index).getCompanyName());
		newDto.setPathNo(lstPathDtl.get(index).getPathNo());
		return newDto;
	}

	/**
	 * 通过PropertyUtils工具类复制dto对象至新对象中
	 * 
	 * @param dto
	 *            待复制的对象
	 * @return 新对象
	 */
	protected BillBalanceApiVo createApiVo(BillBalanceApiDto dto) {
		BillBalanceApiVo newDto = new BillBalanceApiVo();
		try {
			PropertyUtils.copyProperties(newDto, dto);
		} catch (Exception e) {
			logger.error("调用createApiDto方法创建BillBalanceApiDto对象失败:" + e.getMessage(), e);
		}
		return newDto;
	}

	/**
	 * 根据单据明细，查询结算路径集合
	 * 
	 * @param dto
	 *            单据明细数据
	 * @param billBasis
	 *            单据依据
	 * @return BillSplitSettlePathDtlModel
	 * @throws ServiceException
	 *             异常
	 */
	protected BillSplitSettlePathDtlModel findSettlePathDtls(BillBalanceApiDto dto) throws ServiceException {

		int billType = dto.getBillType();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refBillNo", dto.getRefBillNo());
		Integer bizType = dto.getBizType();
		// 到货单差异单据处理
		if(billType == BillTypeEnums.ASN.getRequestId().intValue() &&
				bizType != null && (bizType.intValue() == 3 || bizType.intValue() == 4 || bizType.intValue() == 5
						|| bizType.intValue() == 6 || bizType.intValue() == 7 || bizType.intValue() == 8 || bizType.intValue() == 9) ){
			String pathNo = billBuyBalanceApiService.selectRefPathNo(params);
			if (StringUtils.isNotEmpty(pathNo)) {
				dto.setPathNo(pathNo);
			}
		}
		params.put("itemNo", dto.getItemNo());
		// 验收单拆单处理
		if (billType == BillTypeEnums.RECEIPT.getRequestId().intValue() && StringUtils.isEmpty(dto.getPathNo())) {
			String pathNo = billBuyBalanceApiService.selectRefPathNo(params);
			if (StringUtils.isNotEmpty(pathNo)) {
				dto.setPathNo(pathNo);
			}
		}
		SettlePathDtlQueryVo vo = new SettlePathDtlQueryVo();

		// 到货单的订补货是通过bizType区别的 // 验收单也采用到货单的拆单逻辑
		if (billType == BillTypeEnums.ASN.getRequestId().intValue()
				|| billType == BillTypeEnums.RECEIPT.getRequestId().intValue()) {
			vo.setBillType(dto.getBizType());
			vo.setBillBasis(Integer.parseInt(SettlePathBillBasisEnums.ORDERING.getValue()));
			if(billType == BillTypeEnums.RECEIPT.getRequestId().intValue() && StringUtils.isBlank(dto.getPathNo())){
				Integer refBizType = settlePathApiService.selectRefBizType(dto.getRefBillNo());
				if(null != refBizType){
					vo.setBillType(refBizType);
				}
			}
		} else if (billType == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
			vo.setReturnOwnFlag(1);
		}
		vo.setSupplierNo(dto.getSupplierNo());
		vo.setCompanyNo(dto.getBuyerNo());
		vo.setBrandNo(dto.getBrandNo());
		vo.setCategoryNo(dto.getCategoryNo());
		vo.setYears(dto.getYears());
		vo.setSeason(dto.getSeason());
		vo.setPathNo(dto.getPathNo());
		BillSplitSettlePathDtlModel billSplitSettlePathDtlModel = settlePathApiService.querySettlePathDtl(vo);
		return billSplitSettlePathDtlModel;
	}

	/**
	 * 插入失败日志
	 * 
	 * @param dto
	 *            单据对象
	 * @param failureReason
	 *            失败原因
	 * @return 插入的数量
	 * @throws ServiceException
	 *             异常
	 */
	protected int addFailureLog(BillBalanceApiDto dto, String failureReason) {
		try {
			BillSplitLogModel billSplitLog = null;

			billSplitLog = new BillSplitLogModel();
			billSplitLog.setRefBillNo(dto.getOriginalBillNo());
			billSplitLog.setBillType(dto.getBillType());
			billSplitLog.setSendOutDate(dto.getSendDate());
			billSplitLog.setProcessStatus(1);
			billSplitLog.setSplitTime(new Date());
			billSplitLog.setFailureReason(failureReason);

			return billSplitLogApiService.insert(billSplitLog);

		} catch (ServiceException e) {
			logger.error("插入接口日志失败:" + e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 插入成功日志
	 * 
	 * @param dto
	 *            单据对象
	 * @return 插入的数量
	 * @throws ServiceException
	 *             异常
	 */
	protected int addSuccessLog(BillBalanceApiDto dto) throws ServiceException {
		try {
			BillSplitLogModel billSplitLog = null;

			billSplitLog = new BillSplitLogModel();
			billSplitLog.setRefBillNo(dto.getOriginalBillNo());
			billSplitLog.setBillType(dto.getBillType());
			billSplitLog.setSendOutDate(dto.getSendDate());
			billSplitLog.setProcessStatus(0);
			billSplitLog.setSplitTime(new Date());
			billSplitLog.setFailureReason(null);

			return billSplitLogApiService.insert(billSplitLog);

		} catch (ServiceException e) {
			logger.error("插入接口日志失败:" + e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 收货单（入库单）获取原相关发货单（出库单）信息
	 * 
	 * @param lstBill
	 * @return
	 * @throws ServiceException
	 */
	protected List<BillBalanceApiDto> formatReceiveBill(List<BillBalanceApiDto> lstBill) throws ServiceException {
		for (BillBalanceApiDto dto : lstBill) {
			if (BillTypeEnums.RECEIPT.getRequestId().equals(dto.getBillType())) {
				List<BillBuyBalance> buyList = billBuyBalanceApiService.selectByRefBillNo(dto.getRefBillNo());
				if (!CollectionUtils.isEmpty(buyList)) {
					dto.setSendDate(buyList.get(0).getSendDate());
				}
			} else if (BillTypeEnums.TRANSFER_IN.getRequestId().equals(dto.getBillType())) {
				List<BillSaleBalance> saleList = billSaleBalanceApiService.selectByRefBillNo(dto.getRefBillNo());
				if (!CollectionUtils.isEmpty(saleList)) {
					dto.setSendDate(saleList.get(0).getSendDate());
				}
			}
		}
		return lstBill;
	}
}
