package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dal.BillBuyBalanceApiMapper;
import cn.wonhigh.retail.fas.api.dal.BillSaleBalanceApiMapper;
import cn.wonhigh.retail.fas.api.dal.BillSaleBalanceMapper;
import cn.wonhigh.retail.fas.api.dal.BrandApiMapper;
import cn.wonhigh.retail.fas.api.dal.CustomerOrderRemainMapper;
import cn.wonhigh.retail.fas.api.dal.FinancialAccountApiMapper;
import cn.wonhigh.retail.fas.api.dal.HeadquarterCostAccountingMapper;
import cn.wonhigh.retail.fas.api.dal.ItemApiMapper;
import cn.wonhigh.retail.fas.api.dal.ItemCostMapper;
import cn.wonhigh.retail.fas.api.dal.OrganApiMapper;
import cn.wonhigh.retail.fas.api.dal.PriceQuotationListMapper;
import cn.wonhigh.retail.fas.api.dal.RegionCostAccountingMapper;
import cn.wonhigh.retail.fas.api.dal.SystemParamSetMapper;
import cn.wonhigh.retail.fas.api.dal.WholesaleControlApiMapper;
import cn.wonhigh.retail.fas.api.dal.WholesaleCustomerRemainingDtlMapper;
import cn.wonhigh.retail.fas.api.dal.WholesaleCustomerRemainingSumMapper;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleControlApiDto;
import cn.wonhigh.retail.fas.api.utils.CommonUtils;
import cn.wonhigh.retail.fas.api.vo.CustomerOrderRemain;
import cn.wonhigh.retail.fas.api.vo.SystemParamSet;
import cn.wonhigh.retail.fas.api.vo.WholesaleCustomerRemainingDtl;
import cn.wonhigh.retail.fas.api.vo.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.dto.OrganDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillCodeEnums;
import cn.wonhigh.retail.fas.common.enums.GmsBillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.ReportBizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.SaleOutBizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.WholeSaleOrderTypeEnums;
import cn.wonhigh.retail.fas.common.enums.WholesaleRemainingTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalanceNet;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.model.PriceQuotationList;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.utils.BalanceTypeConvert;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasBillCodeConvert;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.mdm.common.model.Company;
import cn.wonhigh.retail.mps.api.client.dto.price.PriceVo;
import cn.wonhigh.retail.mps.api.client.service.price.SalePriceApi;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.LongSequenceGenerator;

@Service("billSaleBalanceApiService")
public class BillSaleBalanceApiServiceImpl implements BillSaleBalanceApiService {

	private Logger logger = Logger.getLogger(BillSaleBalanceApiServiceImpl.class);

	@Resource
	private BillSaleBalanceApiMapper billSaleBalanceApiMapper;
	
	@Resource
	private BillSaleBalanceMapper billSaleBalanceMapper;

	@Resource
	private OrganApiMapper organApiMapper;

	@Resource
	private ItemApiMapper itemApiMapper;

	@Resource
	private ItemCostMapper itemCostMapper;

	@Resource
	private RegionCostAccountingMapper regionCostAccountingMapper;

	@Resource
	private HeadquarterCostAccountingMapper headquarterCostAccountingMapper;

	@Resource
	private BrandApiMapper brandApiMapper;

	@Resource
	private PurchasePriceApiService purchasePriceApiService;

	@Resource
	private BillBuyBalanceApiMapper billBuyBalanceApiMapper;

	@Resource
	private FinancialAccountApiMapper financialAccountApiMapper;

	@Resource
	private WholesaleControlApiMapper wholesaleControlApiMapper;

	@Resource
	private WholesaleCustomerRemainingSumMapper wholesaleCustomerRemainingSumMapper;

	@Resource
	private WholesaleCustomerRemainingDtlMapper wholesaleCustomerRemainingDtlMapper;

	@Resource
	private SalePriceApi salePriceApi;

	@Resource
	private PriceQuotationListMapper priceQuotationListMapper;

	@Resource
	private CustomerOrderRemainMapper customerOrderRemainMapper;
	
	@Resource
	private WholesaleControlApiService wholesaleControlApiService;
	
	@Resource
	private SystemParamSetMapper systemParamSetMapper;

	/**
	 * 销售类单据插入单据池
	 * 
	 * @param balance
	 *            BillSaleBalance
	 * @return int
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int insert(BillBalanceApiDto dto) throws ServiceException {
		try {
			BillSaleBalance billSaleBalance = new BillSaleBalance();
			PropertyUtils.copyProperties(billSaleBalance, dto);
			billSaleBalance.setId(UUIDGenerator.getUUID());
			billSaleBalance = this.convertBill(billSaleBalance, CommonUtils.isPEByShardingFlag(dto.getShardingFlag()));
			billSaleBalance.setActualCost(billSaleBalance.getCost());

			// 如果是批发客残退货单或者是召回退货设置状态为冻结9,发货数量改为负数
			if (null != billSaleBalance.getBizType()
					&& billSaleBalance.getBillType().intValue() == BillTypeEnums.SALEOUTS.getRequestId().intValue()
					&& (billSaleBalance.getBizType().intValue() == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue()
					|| billSaleBalance.getBizType().intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue())
					) {
				billSaleBalance.setStatus(GmsBillStatusEnums.FROZEN.getStatus());
				if (billSaleBalance.getBizType().intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus()) {
					billSaleBalance.setSendQty(-billSaleBalance.getSendQty());
				}
			}

			int count = billSaleBalanceApiMapper.insert(billSaleBalance);
			// 地区批发 批发出库 /客残退货/过季退货
			if (isWholesaleBill(billSaleBalance.getBillType(), billSaleBalance.getBizType())) {

				BigDecimal cost = billSaleBalance.getCost() == null ? BigDecimal.ZERO : billSaleBalance.getCost();
				BigDecimal sendAmount = cost.multiply(new BigDecimal(billSaleBalance.getSendQty()));
				BigDecimal rebatePrice = billSaleBalance.getBillRebateAmount() == null ? BigDecimal.ZERO : billSaleBalance
						.getBillRebateAmount();
				BigDecimal otherPrice = billSaleBalance.getOtherPrice() == null ? BigDecimal.ZERO : billSaleBalance
						.getOtherPrice();
				// 如果是出库单
				if (billSaleBalance.getBillType().intValue() == BillTypeEnums.SALEOUTS.getRequestId().intValue()) {
					updateWholesaleCustomerRemiaining(billSaleBalance,YesNoEnum.NO.getValue(), 1,  sendAmount, rebatePrice,otherPrice);
					//把返利和其他费用插入扣项表
					if (rebatePrice.compareTo(BigDecimal.ZERO) > 0 || otherPrice.compareTo(BigDecimal.ZERO) > 0) {
						OtherDeduction otherDedution = new OtherDeduction();
						PropertyUtils.copyProperties(otherDedution, billSaleBalance);
						otherDedution.setRebateAmount(rebatePrice);
						otherDedution.setDeductionDate(billSaleBalance.getSendDate());
						wholesaleControlApiService.addOtherDedution(otherDedution);
					}
				}
				List<BillSaleBalance> listBill = new ArrayList<BillSaleBalance>();
				listBill.add(billSaleBalance);
				updateCustomerOrderRemain(billSaleBalance, sendAmount, rebatePrice, otherPrice);
			}
			return count;
		} catch (Exception e) {
			logger.error("插入单据出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private void deleteBuyBill(List<BillBuyBalance> lstBuyBill) throws Exception{
		for (BillBuyBalance billBuyBalance : lstBuyBill) {
			billBuyBalanceApiMapper.deleteByBillNoAndItemNo(billBuyBalance.getOriginalBillNo(), billBuyBalance.getItemNo());
		}
	}
	
	private void deleteSaleBill(List<BillSaleBalance> lstSaleBill) throws Exception{
		for (BillSaleBalance billSaleBalance : lstSaleBill) {
			billSaleBalanceApiMapper.deleteByBillNoAndItemNo(billSaleBalance.getOriginalBillNo(), billSaleBalance.getItemNo());
		}
	}
	
	/**
	 * 批量插入数据
	 * 
	 * @param list
	 *            待插入的数据集合
	 * @return 插入的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int batchInsert(List<BillBalanceApiDto> list) throws ServiceException {
		try {
			int tempNum = 1;
			List<BillSaleBalance> lstBill = new ArrayList<BillSaleBalance>();
			List<BillBuyBalance> lstBuyBill = new ArrayList<BillBuyBalance>();
			// 查询总部职能公司编号集
			String leadRoleCompanyNos = financialAccountApiMapper.selectLeadRoleCompanyNos();
			if (null == leadRoleCompanyNos) {
				leadRoleCompanyNos = "";
			}
			for (int i = 0; i < list.size(); i++) {
				BillBalanceApiDto dto = list.get(i);

				// 索赔单 1355 & (索赔单 8 | 盘差差异 10) 发出数量为0，则不入库
				if ((BillTypeEnums.CLAIM.getRequestId().equals(dto.getBillType()) && (BizTypeEnums.CLAIM.getStatus()
						.equals(dto.getBizType()) || BizTypeEnums.FIRST_ORDER_DIFF.getStatus().equals(dto.getBizType())))) {
					if (null == dto.getSendQty() || dto.getSendQty().intValue() == 0)
						continue;
				}

				BillSaleBalance billSaleBalance = new BillSaleBalance();
				PropertyUtils.copyProperties(billSaleBalance, dto);
				billSaleBalance.setId(UUIDGenerator.getUUID());
				// 设置默认值
				if (billSaleBalance.getCost() == null) {
					billSaleBalance.setCost(new BigDecimal(0d));
				}
				if (billSaleBalance.getBillCost() == null) {
					billSaleBalance.setBillCost(new BigDecimal(0d));
				}
				if (billSaleBalance.getBuyerNo() == null) {
					billSaleBalance.setBuyerNo("9999");
					billSaleBalance.setBuyerName("9999");
				}
				if (billSaleBalance.getSalerNo() == null) {
					billSaleBalance.setSalerNo("9999");
					billSaleBalance.setSalerName("9999");
				}
				billSaleBalance.setActualCost(billSaleBalance.getCost());
				// 跨区调货：1320 1327 1322 1328 调货入库单
				if (dto.getBillType().intValue() == BillTypeEnums.REGION_SHOP_SHOP.getRequestId().intValue()
						|| dto.getBillType().intValue() == BillTypeEnums.REGION_STORE_STORE.getRequestId().intValue()
						|| dto.getBillType().intValue() == BillTypeEnums.REGION_SHOP_STORE.getRequestId().intValue()
						|| dto.getBillType().intValue() == BillTypeEnums.REGION_STORE_SHOP.getRequestId().intValue()) {
					billSaleBalance.setBillType(BillTypeEnums.TRANSFER_OUT.getRequestId());
				}
				if (StringUtils.isNotEmpty(dto.getSendStoreNo()) && StringUtils.isEmpty(dto.getReceiveStoreNo())) {
					billSaleBalance.setReceiveStoreNo(dto.getSendStoreNo());
				}
				if (StringUtils.isNotEmpty(dto.getSendStoreName()) && StringUtils.isEmpty(dto.getReceiveStoreName())) {
					billSaleBalance.setReceiveStoreName(dto.getSendStoreName());
				}

				// 卖方是否为总部
				boolean salerIsHq = false;
				if (null != billSaleBalance.getSalerNo()) {
					salerIsHq = leadRoleCompanyNos.contains(billSaleBalance.getSalerNo());
				}
				// 买方是否为总部
				boolean buyerIsHq = false;
				if (null != billSaleBalance.getBuyerNo()) {
					buyerIsHq = leadRoleCompanyNos.contains(billSaleBalance.getBuyerNo());
				}

				// 处理调货出库单 1371
				billSaleBalance = this.converTransferBill(billSaleBalance, salerIsHq, buyerIsHq, CommonUtils.isPEByShardingFlag(dto.getShardingFlag()));
				// 反冲单数量取反
				if (BalanceTypeConvert.isReturnBill(billSaleBalance.getBillType(), billSaleBalance.getBizType())) {
					billSaleBalance.setSendQty(-billSaleBalance.getSendQty());
					billSaleBalance.setReceiveQty(-billSaleBalance.getReceiveQty());
					billSaleBalance.setReceiveDate(billSaleBalance.getSendDate());
				}

				// 根据订货单位查询管理城市、大区信息
				billSaleBalance = this.convertBill(billSaleBalance,
						CommonUtils.isPEByShardingFlag(dto.getShardingFlag()));

				// 处理客残销售单
				BillBuyBalance billBuyBalance = this.convertToBuyBalance(billSaleBalance);
				if (billBuyBalance != null) {
					lstBuyBill.add(billBuyBalance);
				}

				// 卖方是否为总部
				if (null != billSaleBalance.getSalerNo()) {
					salerIsHq = leadRoleCompanyNos.contains(billSaleBalance.getSalerNo());
				}
				// 买方是否为总部
				if (null != billSaleBalance.getBuyerNo()) {
					buyerIsHq = leadRoleCompanyNos.contains(billSaleBalance.getBuyerNo());
				}

				// 设置结算类型
				billSaleBalance.setBalanceType(BalanceTypeConvert.chooseBalanceType(billSaleBalance, salerIsHq,
						buyerIsHq));

				// 设置FAS单据类型
				if (StringUtils.isEmpty(billSaleBalance.getFasBillCode())) {
					billSaleBalance.setFasBillCode(FasBillCodeConvert.chooseFasBillCode(billSaleBalance, salerIsHq,
							buyerIsHq));
				}

				// 如果是批发客残退货单或者是召回退货,则根据系统参数设置状态是否冻结
				if (null != billSaleBalance.getBizType()
						&& billSaleBalance.getBillType().intValue() == BillTypeEnums.SALEOUTS.getRequestId().intValue()
						&& (billSaleBalance.getBizType().intValue() == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue()
						|| billSaleBalance.getBizType().intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus())
						) {
					if (isFrozen(billSaleBalance.getSalerNo())) {
						billSaleBalance.setStatus(GmsBillStatusEnums.FROZEN.getStatus());
					}else {
						billSaleBalance.setStatus(GmsBillStatusEnums.UNFROZEN.getStatus());
						billSaleBalance.setReceiveQty(Math.abs(billSaleBalance.getSendQty()));
						billSaleBalance.setUnfrozenDate(DateUtil.getCurrentDateTime());
					}
					if (billSaleBalance.getBizType().intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus()) {
						billSaleBalance.setSendQty(-billSaleBalance.getSendQty());
					}
				}
				if(null != billSaleBalance.getIsSwap() && billSaleBalance.getIsSwap()){// 调换的调货出库单特殊处理
					BillBuyBalance newBuyBalance = new BillBuyBalance();
					PropertyUtils.copyProperties(newBuyBalance, billSaleBalance);
					lstBuyBill.add(newBuyBalance);
				}
				lstBill.add(billSaleBalance);
			}

			int count = 0;
			// 如果lstBill为空，则返回
			if (CollectionUtils.isEmpty(lstBill) && CollectionUtils.isEmpty(lstBuyBill))
				return count;
			Company c = new Company();
			c.setCompanyNo(lstBill.get(0).getSalerNo());
			Company company = companyApi.findById(c);
			if (company == null)
				throw new NullArgumentException("结算公司不能为空.");
			
			// 地区批发 批发订单/批发出库 /客残退货/过季退货
			BillSaleBalance billSaleBalance = lstBill.get(0);
			if (isWholesaleBill(billSaleBalance.getBillType(), billSaleBalance.getBizType())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("billNo", billSaleBalance.getBillNo());
				List<BillSaleBalance> billList = billSaleBalanceMapper.selectByParams(null, params);
				//如果批发单据不存在则更新客户余额和明细
				if (null == billList || billList.size() == 0) {
					BigDecimal sendAmount = BigDecimal.ZERO;//出库金额
					BigDecimal rebatePrice = billSaleBalance.getBillRebateAmount() == null ? BigDecimal.ZERO : billSaleBalance
							.getBillRebateAmount();
					BigDecimal otherPrice = billSaleBalance.getOtherPrice() == null ? BigDecimal.ZERO : billSaleBalance
							.getOtherPrice();
					for (BillSaleBalance bill : lstBill) {
						sendAmount = sendAmount.add(BigDecimalUtil.multiInt(bill.getCost(), bill.getSendQty()));
					}
					// 如果是出库单
					if (billSaleBalance.getBillType().intValue() == BillTypeEnums.SALEOUTS.getRequestId().intValue()) {
						tempNum = updateWholesaleCustomerRemiaining(billSaleBalance,YesNoEnum.NO.getValue(), tempNum,  sendAmount, rebatePrice,otherPrice);
						++tempNum;
						//把返利和其他费用插入扣项表
						if (rebatePrice.compareTo(BigDecimal.ZERO) > 0 || otherPrice.compareTo(BigDecimal.ZERO) > 0) {
							OtherDeduction otherDedution = new OtherDeduction();
							PropertyUtils.copyProperties(otherDedution, billSaleBalance);
							otherDedution.setRebateAmount(rebatePrice);
							otherDedution.setDeductionDate(billSaleBalance.getSendDate());
							wholesaleControlApiService.addOtherDedution(otherDedution);
						}
					}
					updateCustomerOrderRemain(billSaleBalance, sendAmount, rebatePrice, otherPrice);
				}				
			}									
			if(null != lstBill.get(0).getIsSwap() && lstBill.get(0).getIsSwap()){
				if (lstBuyBill != null && lstBuyBill.size() > 0) {
					this.deleteBuyBill(lstBuyBill);
					count = billBuyBalanceApiMapper.batchInsert(company.getOrganTypeNo(), lstBuyBill);
				}
			}else{
				this.deleteSaleBill(lstBill);
				count = billSaleBalanceApiMapper.batchInsert(company.getOrganTypeNo(), lstBill);
				if (lstBuyBill != null && lstBuyBill.size() > 0) {
					this.deleteBuyBill(lstBuyBill);
					count = billBuyBalanceApiMapper.batchInsert(company.getOrganTypeNo(), lstBuyBill);
				}
			}
			return count;
		} catch (Exception e) {
			logger.error("批量插入单据出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Resource
	private cn.wonhigh.retail.mdm.api.CompanyApi companyApi;

	/**
	 * 地区批发 批发出库/过季退货/客残退残 维护客户余额主表和明细
	 * 
	 * @param isInvalid
	 *            是否作废单据
	 * @throws
	 * @throws Exception
	 */
	@Transactional
	public int updateWholesaleCustomerRemiaining(BillSaleBalance billSaleBalance, int isInvalid, int tempNum,BigDecimal sendAmount, BigDecimal rebateAmount,BigDecimal otherPrice) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		Integer bizType = billSaleBalance.getBizType();
		String billNo = billSaleBalance.getBillNo();
		Date billDate = billSaleBalance.getSendDate();
		int count = 0;
		params.put("customerNo", billSaleBalance.getBuyerNo());
		List<WholesaleCustomerRemainingSum> remainingDtoList = wholesaleCustomerRemainingSumMapper.selectByParams(null,
				params);
		WholesaleCustomerRemainingSum remainingDto = null;
		if (remainingDtoList != null && remainingDtoList.size() > 0) {
			// 修改可用余额
			remainingDto = remainingDtoList.get(0);
			remainingDto.setUpdateTime(new Date());
			//如果是批发退货冻结,则增加冻结金额
			if ((bizType.intValue() == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() || bizType.intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue())
					&& isFrozen(billSaleBalance.getSalerNo())) {
				params.put("frozenCustomerAmount", BigDecimalUtil.subtract(BigDecimal.ZERO,sendAmount));
			} else {
				params.put("remainingAmount", BigDecimalUtil.subtract(rebateAmount,BigDecimalUtil.add(sendAmount,otherPrice)));
			}
			count = wholesaleCustomerRemainingSumMapper.updateByCustomerNo(params);
			
			remainingDtoList = wholesaleCustomerRemainingSumMapper.selectByParams(null,params);
			remainingDto = remainingDtoList.get(0);
			if (count > 0) {
				int num = 0;
				try {
					num = saveWholesaleCusotmerRemainingDtl(remainingDto, billNo, bizType, sendAmount, isInvalid,
							YesNoEnum.NO.getValue(), tempNum, rebateAmount,otherPrice, billDate);
					//如果是批发退货自动解冻,则新增一条解冻明细
					if ((bizType.intValue() == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() || bizType.intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue())
							&& !isFrozen(billSaleBalance.getSalerNo())) {
						num += saveWholesaleCusotmerRemainingDtl(remainingDto, billNo, WholesaleRemainingTypeEnums.UNFROZEN_RETURN.getTypeNo(), sendAmount, isInvalid,
								YesNoEnum.NO.getValue(), tempNum, rebateAmount,otherPrice, billDate);
					}
				} catch (ParseException e) {
					logger.debug("---- billSaleBalanceApi DTL 新增或修改 客户明余额细失败 !!!----");
					throw new ServiceException("billSaleBalanceApi DTL 新增或修改 客户余额明细失败 !!!");
				}
				if (num < 1) {
					logger.debug("---- billSaleBalanceApi DTL 新增或修改 客户明余额细失败 !!!----");
					throw new ServiceException("billSaleBalanceApi DTL 新增或修改 客户余额明细失败 !!!");
				}
			}
		} else {
			WholesaleControlApiDto paidDto = wholesaleControlApiMapper.selectCalcPaidAmountByParams(params);
			WholesaleControlApiDto sendDto = wholesaleControlApiMapper.selectCalcSendAmountByParams(params);
			BigDecimal remainingAmount = BigDecimalUtil.add(paidDto.getPaidAmount(),sendDto.getSendAmount());
			remainingDto = new WholesaleCustomerRemainingSum();
			remainingDto.setCompanyNo(billSaleBalance.getSalerNo());
			remainingDto.setCompanyName(billSaleBalance.getSalerName());
			remainingDto.setCustomerNo(billSaleBalance.getBuyerNo());
			remainingDto.setCustomerName(billSaleBalance.getBuyerName());
			if ((bizType.intValue() == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() || bizType.intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue())
					&& isFrozen(billSaleBalance.getSalerNo())) {
				remainingDto.setFrozenCustomerAmount(BigDecimalUtil.subtract(BigDecimal.ZERO,sendAmount));
				remainingDto.setRemainingAmount(remainingAmount);
			} else {
				remainingDto.setRemainingAmount(BigDecimalUtil.subtract(remainingAmount,BigDecimalUtil.subtract(BigDecimalUtil.add(sendAmount,otherPrice),rebateAmount)));
			}
			remainingDto.setCreateTime(new Date());
//			remainingDto.setId(sequenceService.getId());
			count = wholesaleCustomerRemainingSumMapper.insertSelective(remainingDto);
			remainingDtoList = wholesaleCustomerRemainingSumMapper.selectByParams(null,params);
			remainingDto = remainingDtoList.get(0);
			if (count > 0) {
				remainingDto.setRemainingAmount(remainingAmount);
				int num = 0;
				try {
					num = saveWholesaleCusotmerRemainingDtl(remainingDto, null, bizType, BigDecimal.ZERO, YesNoEnum.YES.getValue(),
							YesNoEnum.YES.getValue(), tempNum, BigDecimal.ZERO,BigDecimal.ZERO,billDate);
				} catch (ParseException e) {
					logger.debug("---- billSaleBalanceApi DTL 新增或修改 客户明余额细失败 !!!----");
					throw new ServiceException("billSaleBalanceApi DTL 新增或修改 客户余额明细失败 !!!");
				}
				remainingDtoList = wholesaleCustomerRemainingSumMapper.selectByParams(null,params);
				remainingDto = remainingDtoList.get(0);
				try {
					num += saveWholesaleCusotmerRemainingDtl(remainingDto, billNo, bizType, sendAmount, isInvalid,
							YesNoEnum.NO.getValue(), ++tempNum, rebateAmount,otherPrice,billDate);
					//如果是批发退货自动解冻,则新增一条解冻明细
					if ((bizType.intValue() == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() || bizType.intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue())
							&& !isFrozen(billSaleBalance.getSalerNo())) {
						num += saveWholesaleCusotmerRemainingDtl(remainingDto, billNo, WholesaleRemainingTypeEnums.UNFROZEN_RETURN.getTypeNo(), sendAmount, isInvalid,
								YesNoEnum.NO.getValue(), tempNum, rebateAmount,otherPrice, billDate);
					}
					
				} catch (ParseException e) {
					logger.debug("---- billSaleBalanceApi DTL 新增或修改 客户明余额细失败 !!!----");
					throw new ServiceException("billSaleBalanceApi DTL 新增或修改 客户余额明细失败 !!!");
				}

				if (num < 2) {
					logger.debug("---- billSaleBalanceApi DTL 新增或修改 客户明余额细失败 !!!----");
					throw new ServiceException("billSaleBalanceApi DTL 新增或修改 客户余额明细失败 !!!");
				}
			}
		}
		return tempNum;
	}

	/**
	 * 新增明细记录
	 * 
	 * @param remainingDto
	 * @param billNo
	 * @param bizType
	 * @param sendAmount
	 * @param isInvalid
	 *            0 : 普通写入 1：作废回写
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	private int saveWholesaleCusotmerRemainingDtl(WholesaleCustomerRemainingSum remainingDto, String billNo,
			Integer bizType, BigDecimal sendAmount, int isInvalid, int isFirst, int tempNum, BigDecimal rebateAmount,BigDecimal otherPrice, Date billDate)
			throws ServiceException, ParseException {
		WholesaleCustomerRemainingDtl remainingDtlDto = new WholesaleCustomerRemainingDtl();
		remainingDtlDto.setMainId(remainingDto.getId());
		remainingDtlDto.setBizType(bizType);
		remainingDtlDto.setBillType(BillTypeEnums.SALEOUTS.getRequestId());
//		remainingDtlDto.setId(sequenceService.getId());
		remainingDtlDto.setPrePaymentId(0);
		remainingDtlDto.setMoney(BigDecimalUtil.subtract(BigDecimal.ZERO,BigDecimalUtil.subtract(BigDecimalUtil.add(sendAmount,otherPrice),rebateAmount)));
		remainingDtlDto.setRemainingAmount(remainingDto.getRemainingAmount());
		remainingDtlDto.setRebateAmount(rebateAmount);
		remainingDtlDto.setOtherPrice(otherPrice);
		remainingDtlDto.setBillDate(billDate);
		remainingDtlDto.setFrozenAmount(remainingDto.getFrozenCustomerAmount());
		//如果是出库单,并且余额小于0,记信贷一次,设置type值为88
        if ((bizType.intValue() ==  BizTypeEnums.WHOLESALE.getStatus().intValue()|| bizType.intValue() == BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().intValue())
        	&&isInvalid != 1 && remainingDto.getRemainingAmount().compareTo(BigDecimal.ZERO) < 0){
        	remainingDtlDto.setType(WholesaleRemainingTypeEnums.CREDIT.getTypeNo());
		}
		if (StringUtils.isNotBlank(billNo)) {
			remainingDtlDto.setRefNo(billNo);
			remainingDtlDto.setBillNo(billNo);
			remainingDtlDto.setRemark("相关单号：" + billNo);
		}
		try {
			remainingDtlDto.setCreateTime(DateUtil.getCurrentDateTime());
			remainingDtlDto.setPosition(LongSequenceGenerator.getTimestamp());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (isInvalid == 1) {
			if (isFirst == 1) {
				remainingDtlDto.setRemark("客户余额初始化");
			} else {
				remainingDtlDto.setRemark(remainingDtlDto.getRemark() + "(单据重传-作废回写)");
			}
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
			remainingDtlDto.setCreateTime(calendar.getTime());
		}
		
		return wholesaleCustomerRemainingDtlMapper.insertSelective(remainingDtlDto);
	}

	/**
	 * 调货出库单处理
	 * 
	 * @param billBalance
	 * @return
	 */
	private BillSaleBalance converTransferBill(BillSaleBalance billBalance, boolean salerIsHq, boolean buyerIsHq, boolean isPE) {

		boolean flag = false;

		// 调货出库单 1371 地区至总部
		if (BillTypeEnums.TRANSFER_OUT.getRequestId().equals(billBalance.getBillType())
				&& (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType()) 
						|| BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType()) 
						|| BizTypeEnums.TRANSFERETURN.getStatus().equals(billBalance.getBizType()) 
						|| BizTypeEnums.TRANSFERETURNDIFF.getStatus().equals(billBalance.getBizType())
					) && !salerIsHq && buyerIsHq) {
			flag = true;

			if (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType()) 
					|| BizTypeEnums.TRANSFERETURN.getStatus().equals(billBalance.getBizType()) 
					|| BizTypeEnums.TRANSFERETURNDIFF.getStatus().equals(billBalance.getBizType())
				) {
				billBalance.setFasBillCode(FasBillCodeEnums.FG13710404.getCode());
			} else if (BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType())) {
				billBalance.setFasBillCode(FasBillCodeEnums.FG13710504.getCode());
			}
		}
		if (isPE) {
			if (BillTypeEnums.TRANSFER_OUT.getRequestId().equals(billBalance.getBillType())
					&& (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType()) || BizTypeEnums.DIFFERENCE
							.getStatus().equals(billBalance.getBizType())) && !salerIsHq && buyerIsHq) {
				if (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType())) {
					billBalance.setFasBillCode(FasBillCodeEnums.FG13710403.getCode());
				} else if (BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType())) {
					billBalance.setFasBillCode(FasBillCodeEnums.FG13710503.getCode());
				}
			}
			return billBalance;
		}
		if (flag) {
			String buyerNo = billBalance.getBuyerNo();
			String buyerName = billBalance.getBuyerName();
			String receiveStoreNo = billBalance.getReceiveStoreNo();
			String receiveStoreName = billBalance.getReceiveStoreName();
			String orderUnitNo = billBalance.getOrderUnitNo();
			String orderUnitName = billBalance.getOrderUnitName();

			// 设置买方
			billBalance.setBuyerNo(billBalance.getSalerNo());
			billBalance.setBuyerName(billBalance.getSalerName());
			billBalance.setReceiveStoreNo(billBalance.getSendStoreNo());
			billBalance.setReceiveStoreName(billBalance.getSendStoreName());
			billBalance.setOrderUnitNo(billBalance.getOrderUnitNoFrom());
			billBalance.setOrderUnitName(billBalance.getOrderUnitNameFrom());

			// 设置卖方
			billBalance.setSalerNo(buyerNo);
			billBalance.setSalerName(buyerName);
			billBalance.setSendStoreNo(receiveStoreNo);
			billBalance.setSendStoreName(receiveStoreName);
			billBalance.setOrderUnitNoFrom(orderUnitNo);
			billBalance.setOrderUnitNameFrom(orderUnitName);

			// 数量取反
			billBalance.setSendQty(-billBalance.getSendQty());
			billBalance.setReceiveQty(-billBalance.getReceiveQty());
			
			// 对调的调货出库单特殊处理
			Date sendDate = billBalance.getSendDate();
			Integer sendQty = billBalance.getSendQty();
			Integer receiveQty = billBalance.getReceiveQty();
			billBalance.setSendDate(null);
			billBalance.setReceiveDate(sendDate);
			billBalance.setSendQty(receiveQty);
			billBalance.setReceiveQty(sendQty);
			billBalance.setBillType(BillTypeEnums.TRANSFER_IN.getRequestId());
			billBalance.setIsSwap(true);
			if (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType())
					|| BizTypeEnums.TRANSFERETURN.getStatus().equals(billBalance.getBizType()) 
					|| BizTypeEnums.TRANSFERETURNDIFF.getStatus().equals(billBalance.getBizType())) {
				billBalance.setFasBillCode(FasBillCodeEnums.FG13720404.getCode());
			} else if (BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType())) {
				billBalance.setFasBillCode(FasBillCodeEnums.FG13720504.getCode());
			}
		}

		return billBalance;
	}

	/**
	 * 客残销售单如果是总部承担时，将数据复制到bill_buy_balance表中
	 * 
	 * @param billSaleBalance
	 *            单据对象
	 * @return BillBuyBalance对象
	 * @throws RpcException
	 * @throws Exception
	 *             异常
	 */
	private BillBuyBalance convertToBuyBalance(BillSaleBalance billSaleBalance) throws Exception {
		// 如果是客残销售单-总部承担，需要复制一条记录至bill_buy_balance表中，其中biz_type=99,
		// sale_no为item表中对应的供应商编码
		if (billSaleBalance.getBillType().intValue() == BillTypeEnums.SALEOUT.getRequestId().intValue()
				&& billSaleBalance.getBizType() != null
				&& billSaleBalance.getBizType().intValue() == SaleOutBizTypeEnums.HEADQUARTERSBEAR.getStatus()
						.intValue()) {
			BillBuyBalance billBuyBalance = new BillBuyBalance();
			billBuyBalance.setId(UUIDGenerator.getUUID());
			PropertyUtils.copyProperties(billBuyBalance, billSaleBalance);
			billBuyBalance.setBizType(ReportBizTypeEnums.CUSTOMRETURN.getTypeNo());
			billBuyBalance.setSalerNo(billSaleBalance.getSupplierNo());
			billBuyBalance.setSalerName(billSaleBalance.getSupplierName());
			// 客残退给供应商 更改商品单价为采购价
			// PurchasePriceDTO dto =
			// purchasePriceApi.getFactoryPrice(billBuyBalance.getItemNo(),
			// billBuyBalance.getSalerNo(), billBuyBalance.getSendDate());

			PurchasePrice dto = purchasePriceApiService.findBillPurchasePrice(billBuyBalance.getItemNo(),
					billBuyBalance.getSalerNo(), billBuyBalance.getSendDate());
			if (dto != null) {
				billBuyBalance.setCost(dto.getPurchasePrice());
			}
			return billBuyBalance;
		}
		return null;
	}

	/**
	 * 组装单据对象(批量插入)
	 * 
	 * @param billSaleBalance
	 *            BillSaleBalance对象
	 * @param firstBill
	 *            第一个单据对象
	 * @param isFirst
	 *            当前对象是否是第一个对象 （如果是，则查询，如果不是，则复制第一个对象查询出的结果）
	 * @return 组装后的单据对象
	 * @throws Exception
	 *             异常
	 */
	private BillSaleBalance convertBill(BillSaleBalance billSaleBalance, boolean isPE) throws Exception {

		// 冗余商品相关信息
		List<ItemDto> lstItem = itemApiMapper.findByItemNo(billSaleBalance.getItemNo());
		if (lstItem != null && lstItem.size() > 0) {
			ItemDto itemDto = lstItem.get(0);
			if (StringUtils.isEmpty(billSaleBalance.getSupplierNo())) {
				billSaleBalance.setSupplierNo(itemDto.getSupplierNo());
				billSaleBalance.setSupplierName(itemDto.getSupplierName());
			}
			billSaleBalance.setOrderfrom(itemDto.getOrderfrom());
			billSaleBalance.setGender(itemDto.getGender());
			billSaleBalance.setYears(itemDto.getYears());
			billSaleBalance.setSeason(itemDto.getSellSeason());
			//如果GMS传过来的牌价为空值或者为0,则给牌价赋值
			if (null == billSaleBalance.getTagPrice() || billSaleBalance.getTagPrice().compareTo(BigDecimal.ZERO)==0) {
				billSaleBalance.setTagPrice(itemDto.getTagPrice());
			}
			billSaleBalance.setBrandNo(itemDto.getBrandNo());
			billSaleBalance.setBrandName(itemDto.getBrandName());
			billSaleBalance.setCategoryNo(itemDto.getCategoryNo());
			billSaleBalance.setCategoryName(itemDto.getCategoryName());
		}

		// 设置品牌部
		List<Brand> lstBrand = brandApiMapper.findByBrandNo(billSaleBalance.getBrandNo());
		if (lstBrand != null && lstBrand.size() > 0) {
			billSaleBalance.setBrandUnitNo(lstBrand.get(0).getSysNo());
			billSaleBalance.setBrandUnitName(lstBrand.get(0).getBrandUnitName());
		}

		// 冗余管理城市、大区
		if (StringUtils.isNotEmpty(billSaleBalance.getOrderUnitNo())) {
			List<OrganDto> list = organApiMapper.selectByOrderUnitNo(billSaleBalance.getOrderUnitNo());
			if (list != null && list.size() > 0) {
				billSaleBalance.setOrganNo(list.get(0).getOrganNo());
				billSaleBalance.setOrganName(list.get(0).getName());
				billSaleBalance.setZoneNo(list.get(0).getZoneNo());
				billSaleBalance.setZoneName(list.get(0).getZoneName());
			}
		}
		if (StringUtils.isNotEmpty(billSaleBalance.getOrderUnitNoFrom())) {
			List<OrganDto> lstFrom = organApiMapper.selectByOrderUnitNo(billSaleBalance.getOrderUnitNoFrom());
			if (lstFrom != null && lstFrom.size() > 0) {
				billSaleBalance.setOrganNoFrom(lstFrom.get(0).getOrganNo());
				billSaleBalance.setOrganNameFrom(lstFrom.get(0).getName());
				billSaleBalance.setZoneNoFrom(lstFrom.get(0).getZoneNo());
				billSaleBalance.setZoneNameFrom(lstFrom.get(0).getZoneName());
			}
		}
		// 查询卖方单位成本
		Map<String, Object> buyerItemCostParams = new HashMap<String, Object>();
		buyerItemCostParams.put("companyNo", billSaleBalance.getSalerNo());
		buyerItemCostParams.put("itemNo", billSaleBalance.getItemNo());
		buyerItemCostParams.put("date", billSaleBalance.getSendDate());
		ItemCost buyerItemCost = itemCostMapper.getItemCost(buyerItemCostParams);
		if (buyerItemCost != null && buyerItemCost.getUnitCost() != null) {
			billSaleBalance.setUnitCost(buyerItemCost.getUnitCost());
		} else {
			// 未取到单位成本取地区价
			billSaleBalance.setUnitCost(billSaleBalance.getRegionCost());
		}

		// 查询买方单位成本
		Map<String, Object> salerItemCostParams = new HashMap<String, Object>();
		salerItemCostParams.put("companyNo", billSaleBalance.getBuyerNo());
		salerItemCostParams.put("itemNo", billSaleBalance.getItemNo());
		salerItemCostParams.put("date", billSaleBalance.getSendDate());
		ItemCost salerItemCost = itemCostMapper.getItemCost(salerItemCostParams);
		if (salerItemCost != null && salerItemCost.getUnitCost() != null) {
			billSaleBalance.setUnitCostSecond(salerItemCost.getUnitCost());
		} else {
			// 未取到单位成本取地区价
			billSaleBalance.setUnitCostSecond(billSaleBalance.getRegionCostSecond());
		}
		if (isPE) {
			return billSaleBalance;
		}
		// 查询采购价、物料价、厂进价
		PurchasePrice purchasePrice = purchasePriceApiService.findBillPurchasePrice(billSaleBalance.getItemNo(),
				billSaleBalance.getSupplierNo(), billSaleBalance.getSendDate());
		if (purchasePrice != null && purchasePrice.getPurchasePrice() != null) {
			billSaleBalance.setPurchasePrice(purchasePrice.getPurchasePrice());
		} else {
			billSaleBalance.setPurchasePrice(BigDecimal.ZERO);
		}
		if (purchasePrice != null && purchasePrice.getMaterialPrice() != null) {
			billSaleBalance.setMaterialPrice(purchasePrice.getMaterialPrice());
		} else {
			billSaleBalance.setMaterialPrice(BigDecimal.ZERO);
		}
		if (purchasePrice != null && purchasePrice.getFactoryPrice() != null) {
			billSaleBalance.setFactoryPrice(purchasePrice.getFactoryPrice());
		} else {
			billSaleBalance.setFactoryPrice(BigDecimal.ZERO);
		}
		// 查询牌价
		try {
			String organNoParam = billSaleBalance.getOrganNoFrom();
			// 如果发方管理城市为空,则取收方
			if (StringUtils.isEmpty(organNoParam)) {
				organNoParam = billSaleBalance.getOrganNo();
			}
			// PriceVo price = salePriceApi.getItemTagPriceByParam(2,
			// organNoParam, new
			// cn.wonhigh.retail.mps.api.client.dto.price.ItemDto(billSaleBalance.getItemNo()));
			PriceVo price = salePriceApi.getItemTagPriceByParam(organNoParam,
					new cn.wonhigh.retail.mps.api.client.dto.price.ItemDto(billSaleBalance.getItemNo()));
			if (price != null && null != price.getTagPrice()) {
				billSaleBalance.setTagPrice(price.getTagPrice());
			} else {
				billSaleBalance.setTagPrice(BigDecimal.ZERO);
			}
		} catch (Exception e) {
			logger.error("从MPS获取牌价出错:" + e.getMessage(), e);
			billSaleBalance.setTagPrice(BigDecimal.ZERO);
			throw new Exception(e.getMessage(), e);
		}
		// 查询全国统一牌价
		Map<String, Object> priceQuotationListParams = new HashMap<String, Object>();
		priceQuotationListParams.put("organId", "0000");
		priceQuotationListParams.put("itemNo", billSaleBalance.getItemNo());
		PriceQuotationList priceQuotationList = priceQuotationListMapper
				.getPriceQuotationList(priceQuotationListParams);
		if (priceQuotationList != null && priceQuotationList.getTagPrice() != null) {
			billSaleBalance.setTagPriceNation(priceQuotationList.getTagPrice());
		} else {
			billSaleBalance.setTagPriceNation(BigDecimal.ZERO);
		}

		// 查询卖方地区价
		if (billSaleBalance.getZoneNoFrom() != null) {
			String priceZoneNo = null;
			// 查询价格大区
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", billSaleBalance.getSalerNo());
			params.put("status", 1);
			List<FinancialAccount> financialAccounts = financialAccountApiMapper.selectByParams(params);
			if (!CollectionUtils.isEmpty(financialAccounts)) {
				priceZoneNo = financialAccounts.get(0).getPriceZone();
			}
			if (StringUtils.isEmpty(priceZoneNo)) {
				priceZoneNo = billSaleBalance.getZoneNoFrom();
			}

			Map<String, Object> salerRegionCostparams = new HashMap<String, Object>();
			salerRegionCostparams.put("zoneNo", billSaleBalance.getZoneNoFrom());
			salerRegionCostparams.put("itemNo", billSaleBalance.getItemNo());
			salerRegionCostparams.put("effectiveDate", billSaleBalance.getSendDate());
			RegionCostMaintain salerRegionCost = regionCostAccountingMapper.findRegionCost(salerRegionCostparams);
			if (salerRegionCost != null && salerRegionCost.getRegionCost() != null) {
				billSaleBalance.setRegionCost(salerRegionCost.getRegionCost());
			} else {
				billSaleBalance.setRegionCost(BigDecimal.ZERO);
			}
		} else {
			billSaleBalance.setRegionCost(BigDecimal.ZERO);
		}

		// 查询买方地区价
		if (billSaleBalance.getZoneNo() != null) {
			String priceZoneNo = null;
			// 查询价格大区
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", billSaleBalance.getBuyerNo());
			params.put("status", 1);
			List<FinancialAccount> financialAccounts = financialAccountApiMapper.selectByParams(params);
			if (!CollectionUtils.isEmpty(financialAccounts)) {
				priceZoneNo = financialAccounts.get(0).getPriceZone();
			}
			if (StringUtils.isEmpty(priceZoneNo)) {
				priceZoneNo = billSaleBalance.getZoneNo();
			}

			Map<String, Object> buyerRegionCostparams = new HashMap<String, Object>();
			buyerRegionCostparams.put("zoneNo", billSaleBalance.getZoneNo());
			buyerRegionCostparams.put("itemNo", billSaleBalance.getItemNo());
			buyerRegionCostparams.put("effectiveDate", billSaleBalance.getSendDate());
			RegionCostMaintain buyerRegionCost = regionCostAccountingMapper.findRegionCost(buyerRegionCostparams);
			if (buyerRegionCost != null && buyerRegionCost.getRegionCost() != null) {
				billSaleBalance.setRegionCostSecond(buyerRegionCost.getRegionCost());
			} else {
				billSaleBalance.setRegionCostSecond(BigDecimal.ZERO);
			}
		} else {
			billSaleBalance.setRegionCostSecond(BigDecimal.ZERO);
		}

		// 查询总部价
		Map<String, Object> headquarterCostParams = new HashMap<String, Object>();
		headquarterCostParams.put("itemNo", billSaleBalance.getItemNo());
		headquarterCostParams.put("effectiveDate", billSaleBalance.getSendDate());
		BigDecimal headquarterCost = headquarterCostAccountingMapper.findHeadquarterCost(headquarterCostParams);
		if (headquarterCost != null) {
			billSaleBalance.setHeadquarterCost(headquarterCost);
		} else {
			billSaleBalance.setHeadquarterCost(BigDecimal.ZERO);
		}

		return billSaleBalance;
	}

	/**
	 * 通过原单据编码查询单据池中相关单据的记录数
	 * 
	 * @param refBillNo
	 *            原单据编码
	 * @return 记录数
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int selectCountByRefBillNo(String refBillNo) throws ServiceException {
		try {
			int count = billSaleBalanceApiMapper.selectCountByRefBillNo(refBillNo);
			return count;
		} catch (Exception e) {
			logger.error("通过单据编码查询拆单的单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 通过单据编码删除未结算的数据
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 删除的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int deleteByBillNo(String billNo) throws ServiceException {
		try {
			int count = billSaleBalanceApiMapper.deleteByBillNo(billNo);
			return count;
		} catch (Exception e) {
			logger.error("通过单据编码删除单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 通过单据编码及货号删除未结算的数据
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 删除的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int deleteByBillNoAndItemNo(String billNo, String itemNo) throws ServiceException {
		try {
			int count = billSaleBalanceApiMapper.deleteByBillNoAndItemNo(billNo, itemNo);
			return count;
		} catch (Exception e) {
			logger.error("通过单据编码及货号删除单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 通过单据编码查询已结算单据的数量
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int selectCountByBillNo(String billNo) throws ServiceException {
		try {
			int count = billSaleBalanceApiMapper.selectCountByBillNo(billNo);
			return count;
		} catch (Exception e) {
			logger.error("通过单据编码查询单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 作废单据
	 * 
	 * @param billHeaderApiDto
	 *            参数条件
	 * @return 作废的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int invalid(BillHeaderApiDto billHeaderApiDto) throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", billHeaderApiDto.getBillNo());
			params.put("billType", billHeaderApiDto.getBillType());
			params.put("bizType", billHeaderApiDto.getBizType());
			int count = billSaleBalanceApiMapper.invalid(params);
			return count;
		} catch (Exception e) {
			logger.error("作废单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 通过map参数，查询数量
	 * 
	 * @param params
	 *            参训参数
	 * @return 查询到的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int selectCountByParams(Map<String, Object> params) throws ServiceException {
		try {
			int count = billSaleBalanceApiMapper.selectCountByParams(params);
			return count;
		} catch (Exception e) {
			logger.error("通过selectCountByParams方法查询单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BillSaleBalance selectSumBillSaleBalanceByBillNo(String billNo) throws ServiceException {
		try {
			BillSaleBalance billSaleBalance = billSaleBalanceApiMapper.selectSumBillSaleBalanceByBillNo(billNo);
			return billSaleBalance;
		} catch (Exception e) {
			logger.error("通过selectSumBillSaleBalanceByBillNo方法查询单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSaleBalance> selectByRefBillNo(String refBillNo) throws ServiceException {
		try {
			List<BillSaleBalance> list = billSaleBalanceApiMapper.selectByRefBillNo(refBillNo);
			return list;
		} catch (Exception e) {
			logger.error("根据源单编号查询单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

   
    /**
     * 维护批发客户订单余额表
     * @param listBill
     * @throws ServiceException
     */
	@Override
	public void updateCustomerOrderRemain(BillSaleBalance billSaleBalance, BigDecimal sendAmount, BigDecimal rebateAmount,BigDecimal otherPrice) throws ServiceException {
		if (null == billSaleBalance.getBizType() || null==billSaleBalance.getBillType()) {
			return;
		}
		Map<Integer,Integer> orderTypeMap= new HashMap<Integer, Integer>();
		orderTypeMap.put(0, WholeSaleOrderTypeEnums.SPOT.getTypeNo());
		orderTypeMap.put(1, WholeSaleOrderTypeEnums.RESTOCK.getTypeNo());
		orderTypeMap.put(2, WholeSaleOrderTypeEnums.ORDERING.getTypeNo());
		try {
			int bizType = billSaleBalance.getBizType();
	        int billType = billSaleBalance.getBillType();
			Map<String, Object> params = new HashMap<String, Object>();
			CustomerOrderRemain customerOrderRemain = new CustomerOrderRemain();
			List<CustomerOrderRemain> remainingDtoList = null;
			//过季退货
			if (bizType == BizTypeEnums.WHOLESALE_RETURN.getStatus().intValue()) {
				return;
			}
			if (billType == BillTypeEnums.SALEORDER.getRequestId().intValue()) {// 订单
				
				params.put("orderNo", billSaleBalance.getBillNo());
				remainingDtoList = customerOrderRemainMapper.selectByParams(null, params);
				if (null == remainingDtoList || remainingDtoList.size() == 0) {
					customerOrderRemain.setId(UUIDGenerator.getUUID());
					customerOrderRemain.setCompanyNo(billSaleBalance.getSalerNo());
					customerOrderRemain.setCompanyName(billSaleBalance.getSalerName());
					customerOrderRemain.setCustomerNo(billSaleBalance.getBuyerNo());
					customerOrderRemain.setCustomerName(billSaleBalance.getBuyerName());
					customerOrderRemain.setOrderNo(billSaleBalance.getBillNo());
					customerOrderRemain.setAmount(sendAmount);
					customerOrderRemain.setStatus(GmsBillStatusEnums.PARTRECEIPT.getStatus());
					customerOrderRemain.setOrderDate(billSaleBalance.getSendDate());
					customerOrderRemain.setWholesaleOrderType(orderTypeMap.get(billSaleBalance.getWholesaleOrderType()));
					customerOrderRemainMapper.insertSelective(customerOrderRemain);
				} else {
					customerOrderRemain = remainingDtoList.get(0);
					customerOrderRemain.setAmount(BigDecimalUtil.add(sendAmount,customerOrderRemain.getAmount()));
				}
			} 
	        
		    // 出库单
			if ((bizType == BizTypeEnums.WHOLESALE.getStatus().intValue()
					|| bizType == BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().intValue()) 
					&& billType == BillTypeEnums.SALEOUTS.getRequestId().intValue() ){
				if (StringUtils.isNotEmpty(billSaleBalance.getRefBillNo())) { 
					params.put("orderNo", billSaleBalance.getRefBillNo());
					remainingDtoList = customerOrderRemainMapper.selectByParams(null, params);
					if (null == remainingDtoList || remainingDtoList.size() == 0) {
						return;
					}
					customerOrderRemain = remainingDtoList.get(0);
					customerOrderRemain.setOutAmount(customerOrderRemain.getOutAmount().add(sendAmount));
					customerOrderRemain.setOutRebateAmount(BigDecimalUtil.add(customerOrderRemain.getOutRebateAmount(),rebateAmount));
					customerOrderRemain.setRemainingAmount(BigDecimalUtil.subtract(customerOrderRemain.getRemainingAmount(),BigDecimalUtil.subtract(BigDecimalUtil.add(sendAmount,otherPrice),rebateAmount)));
				}else {
					return;
				}
				
			} 
		    
			// 客残退货单或者召回退货单		
			if ((bizType == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() || bizType == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue())  
					&& billType == BillTypeEnums.SALEOUTS.getRequestId().intValue() ){
					params.put("companyNo", billSaleBalance.getSalerNo());
					params.put("customerNo", billSaleBalance.getBuyerNo());
					params.put("status", GmsBillStatusEnums.PARTRECEIPT.getStatus());
					// 查询最早一笔尚未完结的批发订单
					List<CustomerOrderRemain> remainingList = customerOrderRemainMapper.selectByParams(null, params);
					if (null == remainingList || remainingList.size() == 0) {
						return;
					}
					customerOrderRemain = remainingList.get(0);
					customerOrderRemain.setFrozenOrderAmount(BigDecimalUtil.add(customerOrderRemain.getFrozenOrderAmount(),
							BigDecimalUtil.subtract(BigDecimal.ZERO,sendAmount)));
					if (!isFrozen( billSaleBalance.getSalerNo())) {
						customerOrderRemain.setUnfreezeOrderAmount(BigDecimalUtil.add(customerOrderRemain.getUnfreezeOrderAmount(),
								BigDecimalUtil.subtract(BigDecimal.ZERO,sendAmount)));
						customerOrderRemain.setRemainingAmount(BigDecimalUtil.subtract(customerOrderRemain.getRemainingAmount(),sendAmount));
					}
			} 
			customerOrderRemainMapper.updateByPrimaryKeySelective(customerOrderRemain);
		} catch (Exception e) {
			logger.error("更新订单余额信息:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		
        

	}

	/**
	 * 是否是批发单
	 * 
	 * @param billType
	 * @param bizType
	 * @return
	 */
	private boolean isWholesaleBill(Integer billType1, Integer bizType1) {
		if (bizType1 == null)
			return false;
        int billType = billType1.intValue();
        int bizType = bizType1.intValue();
		if ((billType == BillTypeEnums.SALEOUTS.getRequestId().intValue() && (bizType == BizTypeEnums.WHOLESALE.getStatus().intValue()
						|| bizType == BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().intValue()
						|| bizType == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() 
						|| bizType == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue() 
						|| bizType == BizTypeEnums.WHOLESALE_RETURN.getStatus().intValue()))
				|| (billType == BillTypeEnums.SALEORDER.getRequestId().intValue() && (bizType == BizTypeEnums.FIRST_ORDER.getStatus().intValue()
						|| bizType == BizTypeEnums.REPLENISH_ORDER.getStatus().intValue()
						|| bizType == BizTypeEnums.WHOLESALE_RETURN.getStatus().intValue() 
						|| bizType == BizTypeEnums.DIRECT.getStatus().intValue() 
						|| bizType == BizTypeEnums.WHOLESALE.getStatus().intValue()))		
				) {
			return true;
		}
		return false;
	}

	@Override
	public int selectParamsCount(String salerNo, String paramCode, String dtlValue) {
		return billSaleBalanceApiMapper.selectParamsCount(salerNo,paramCode,dtlValue);
	}

	@Override
	public int batchInsertNet(List<BillBalanceApiDto> lstBill)
			throws ServiceException {
		try {
			int count = 0;
			for (BillBalanceApiDto dto : lstBill) {
				BillSaleBalance billSaleBalance = new BillSaleBalance();
				PropertyUtils.copyProperties(billSaleBalance, dto);
				billSaleBalance.setId(UUIDGenerator.getUUID());
				billSaleBalance = this.convertBill(billSaleBalance, CommonUtils.isPEByShardingFlag(dto.getShardingFlag()));
				billSaleBalance.setActualCost(billSaleBalance.getCost());
				BillSaleBalanceNet netBalance = new BillSaleBalanceNet();
				PropertyUtils.copyProperties(netBalance, billSaleBalance);
				netBalance.setShardingFlag(dto.getShardingFlag());
				count += billSaleBalanceApiMapper.insertNet(netBalance);
			}
			return count;
		} catch (Exception e) {
			logger.error("插入网销单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	/*
	 * 根据结算公司编号查询批发系统参数是否冻结
	 */
	private boolean isFrozen(String companyNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.clear();
		params.put("companyNo", companyNo);
		params.put("paramCode", "WH_Return_Frozen");
		params.put("status", "1");
		List<SystemParamSet> systemParamSetList = systemParamSetMapper.selectByParams(null, params);
		if (null != systemParamSetList && systemParamSetList.size() > 0 && "unfrozen".equals(systemParamSetList.get(0).getDtlValue())) {  
			return false;
		}
		return true;
	}

}
