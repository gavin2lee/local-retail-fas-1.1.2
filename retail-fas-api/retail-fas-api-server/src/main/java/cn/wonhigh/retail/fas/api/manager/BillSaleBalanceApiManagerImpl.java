package cn.wonhigh.retail.fas.api.manager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dal.OtherDeductionMapper;
import cn.wonhigh.retail.fas.api.dal.WholesaleCustomerRemainingDtlMapper;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.api.model.ApiMessage;
import cn.wonhigh.retail.fas.api.service.BillBuyBalanceApi;
import cn.wonhigh.retail.fas.api.service.BillBuyBalanceApiService;
import cn.wonhigh.retail.fas.api.service.BillSaleBalanceApi;
import cn.wonhigh.retail.fas.api.service.BillSaleBalanceApiService;
import cn.wonhigh.retail.fas.api.utils.ReflectUtil;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.WholesaleRemainingTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("billSaleBalanceApiManager")
public class BillSaleBalanceApiManagerImpl extends BillBalanceApiManagerImpl implements BillSaleBalanceApi {

	private Logger logger = Logger.getLogger(BillSaleBalanceApiManagerImpl.class);

	@Resource
	private BillBuyBalanceApi billBuyBalanceApi;
	
	@Resource
	private BillSaleBalanceApiService billSaleBalanceApiService;

	@Resource
	private BillBuyBalanceApiService billBuyBalanceApiService;
	
	@Resource
	private OtherDeductionMapper otherDeductionMapper;
	
	@Resource
	private WholesaleCustomerRemainingDtlMapper remainingDtlMapper;

	/**
	 * 销售类单据插入单据池
	 * 
	 * @param balance
	 *            BillSaleBalance
	 * @return Boolean
	 * @throws RpcException
	 *             异常
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RpcException.class)
	public boolean insert(List<BillBalanceApiDto> lstBill) throws RpcException {
		try {
			if(!CollectionUtils.isEmpty(lstBill) && billBuyBalanceApi.isBalance(lstBill.get(0).getBillNo())){
				throw new RpcException("FAS", "单据已结算，不允许重传");
			}
			if (!this.validateAndConvertModel(lstBill)) {
				this.addFailureLog(lstBill.get(0), "传递的数据校验失败");
				return false;
			}
			if (isWholesale(lstBill.get(0).getOrderUnitNoFrom())) {
				logger.info("批发公司数据不同步：" + lstBill.get(0).getBillNo());
				return true;
			}
			if(this.handelCheckBill(lstBill.get(0))){
				logger.info("盘差处理单，公司参数已配置不处理：" + lstBill.get(0).getBillNo());
				return true;
			}
			if (this.isSplitType(lstBill)) {
				ApiMessage message = this.splitBill(lstBill);
				if (message != null) {
					this.addFailureLog(lstBill.get(0), message.getErrorMsg());
					throw new RpcException(message.getProjectName(), message.getErrorMsg());
				}
				return true;
			}
			int count = billSaleBalanceApiService.batchInsert(lstBill);
			if (count > 0) {
				this.addSuccessLog(lstBill.get(0));
			}
			return count > 0;
		} catch (ServiceException e) {
			logger.error("调用insert方法失败:" + e.getMessage(), e);
			this.addFailureLog(lstBill.get(0), "批量插入数据失败");
			throw new RpcException(e.getMessage(), e);
		}
	}

	// 批发判断
	private boolean isWholesale(String orderUnitNo) throws RpcException {
		if(!StringUtils.isBlank(orderUnitNo)){
			try {
				return billBuyBalanceApiService.selectIsWholesaleCount(orderUnitNo) > 0;
			} catch (Exception e) {
				logger.error("查询批发单据出错:" + e.getMessage(), e);
				throw new RpcException(e.getMessage(), e);
			}
		}
		return false;
	}
	
	/**
	 * 盘差处理单校验
	 * @param billBalanceApiDto
	 * @return
	 */
	private boolean handelCheckBill(BillBalanceApiDto billBalanceApiDto) {
		Integer billType = billBalanceApiDto.getBillType();
		Integer bizType = billBalanceApiDto.getBizType();
		if(null != billType && billType.intValue() == 1355
				&& null != bizType && bizType.intValue() == 10){
			String companyNo = billBalanceApiDto.getSalerNo();
			String paramCode = "NOT_RECEIVE_CHECK_BILL";
			String dtlValue = "1";
			return billSaleBalanceApiService.selectParamsCount(companyNo,paramCode,dtlValue) > 0;
		}
		return false;
	}

	/**
	 * 校验数据是否正确
	 * 
	 * @param lstBill
	 *            List<BillBalanceApiDto>
	 * @return Boolean
	 */
	private boolean validateAndConvertModel(List<BillBalanceApiDto> lstBill) {
		if (lstBill == null || lstBill.size() == 0) {
			return false;
		}
		// 判断是否是批发/团购订单
		boolean isOrderBillFlag = this.isOrderBill(lstBill.get(0));
		boolean flag = true;
		for (BillBalanceApiDto dto : lstBill) {
			try {
				flag = ReflectUtil.validateRequired(dto, new String[] { "billNo", "billType" });
			} catch (ManagerException e) {
				logger.error(e.getMessage(), e);
			}
			if (!flag) {
				return false;
			}
			dto.setOriginalBillNo(dto.getBillNo());
			if (StringUtils.isEmpty(dto.getSalerNo())) {
				dto.setSalerNo(dto.getSupplierNo());
			}
			if (StringUtils.isEmpty(dto.getSalerName())) {
				dto.setSalerName(dto.getSupplierName());
			}
			// 如果不是批发/团购订单，批发/团购订单中billCost存订单金额，cost存销售价
			if (!isOrderBillFlag && dto.getCost() != null && dto.getBillCost() == null) {
				dto.setBillCost(dto.getCost());
			}
		}
		return true;
	}

	/**
	 * 作废单据
	 * 
	 * @param billHeaderApiDto
	 *            单据头对象
	 * @return 作废是否成功
	 * @throws RpcException
	 *             异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RpcException.class)
	@Override
	public boolean invalid(BillHeaderApiDto billHeaderApiDto) throws RpcException {
		try {
			Map<String, String> msgMap = this.validateBillHeader(billHeaderApiDto);
			if (msgMap != null && msgMap.size() > 0) {
				throw new RpcException("FAS", msgMap.get("errorMsg"));
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billType", billHeaderApiDto.getBillType());
			params.put("bizType", billHeaderApiDto.getBizType());
			params.put("billNo", billHeaderApiDto.getBillNo());
			params.put("balanceNoNotNull", "true");
			int count = 0;
			// 如果是拆单类型的单据，需要删除bill_buy_balance和bill_sale_balance两张表里的数据
			if (this.isSplitBillType(billHeaderApiDto.getBillType(), billHeaderApiDto.getBizType())) {
				// 查询单据是否已做结算(拆单的单据需查询两种表)
				int saleCount = billSaleBalanceApiService.selectCountByParams(params);
				if (saleCount > 0) {
					throw new RpcException("FAS", "单据已结算，不允许作废！");
				}
				count = billSaleBalanceApiService.invalid(billHeaderApiDto);
				int buyCount = billBuyBalanceApiService.selectCountByParams(params);
				if (buyCount > 0) {
					throw new RpcException("FAS", "单据已结算，不允许作废！");
				}
				count = billBuyBalanceApiService.invalid(billHeaderApiDto);
			} else {
				// 查询单据是否已做结算
				int selectCount = billSaleBalanceApiService.selectCountByParams(params);
				if (selectCount > 0) {
					throw new RpcException("FAS", "单据已结算，不允许作废！");
				}
				count = billSaleBalanceApiService.invalid(billHeaderApiDto);
			}
			return count > 0;
		} catch (ServiceException e) {
			logger.error("调用invalid作废方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	/**
	 * 批发单据作废
	 * 
	 * @param billHeaderApiDto
	 *            单据头对象
	 * @return 作废是否成功
	 * @throws RpcException
	 *             异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RpcException.class)                                              
	@Override
	public String invalidWholesaleOrders(BillHeaderApiDto billHeaderApiDto) throws RpcException {
		String message = null;
		try {
 			logger.info("invalidWholesaleOrders U88- billType:" + billHeaderApiDto.getBillType() + " bizType:"
					+ billHeaderApiDto.getBizType() + " billNo:" + billHeaderApiDto.getBillNo() + " ");
			// 验证参数
			Map<String, String> msgMap = this.validateBillHeader(billHeaderApiDto);
			if (msgMap != null && msgMap.size() > 0) {
				message = msgMap.get("errorMsg");
				return message;
			}
			// 业务处理
			if (billHeaderApiDto.getBillType().intValue() == BillTypeEnums.SALEOUTS.getRequestId().intValue()
					&& (billHeaderApiDto.getBizType().intValue() == BizTypeEnums.WHOLESALE.getStatus().intValue()
							|| billHeaderApiDto.getBizType().intValue() == BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().intValue()
							|| billHeaderApiDto.getBizType().intValue() == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() 
							|| billHeaderApiDto.getBizType().intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue() 
							|| billHeaderApiDto.getBizType().intValue() == BizTypeEnums.WHOLESALE_RETURN.getStatus().intValue())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("billType", billHeaderApiDto.getBillType());
				params.put("bizType", billHeaderApiDto.getBizType());
				params.put("billNo", billHeaderApiDto.getBillNo());
				params.put("balanceNoNotNull", "true");
				int selectCount = billSaleBalanceApiService.selectCountByParams(params);
				if (selectCount > 0) {
					message = "单据已结算或者已解冻，不允许重传！";
					return message;
				}
				// 获取作废单据的单据金额
				BillSaleBalance billSaleBalance = billSaleBalanceApiService
						.selectSumBillSaleBalanceByBillNo(billHeaderApiDto.getBillNo());
				OtherDeduction otherDeduction = otherDeductionMapper.findRebateOtherPrcie(billHeaderApiDto.getBillNo()); 
				if (null == otherDeduction) {
					otherDeduction = new OtherDeduction();
				}
				if (StringUtils.isNotEmpty(billSaleBalance.getBillNo())) {
					//扣除客户订单余额
					billSaleBalance.setSendAmount(BigDecimalUtil.subtract(BigDecimal.ZERO,billSaleBalance.getSendAmount()));
					otherDeduction.setRebateAmount(BigDecimalUtil.subtract(BigDecimal.ZERO,otherDeduction.getRebateAmount()));
					otherDeduction.setOtherPrice(BigDecimalUtil.subtract(BigDecimal.ZERO,otherDeduction.getOtherPrice()));
					billSaleBalanceApiService.updateCustomerOrderRemain(billSaleBalance, billSaleBalance.getSendAmount(), 
							otherDeduction.getRebateAmount(), otherDeduction.getOtherPrice());
					// 单据作废
					int count = billSaleBalanceApiService.invalid(billHeaderApiDto);
					if (count > 0) {
						//原有的客户余额明细的type改为99作废状态
						params.put("type", WholesaleRemainingTypeEnums.CANCEL.getTypeNo());
						remainingDtlMapper.updateTypeByBillNo(params);
						// 调整流水
						billSaleBalanceApiService.updateWholesaleCustomerRemiaining(billSaleBalance	,YesNoEnum.YES.getValue(),1
								, billSaleBalance.getSendAmount(),otherDeduction.getRebateAmount(),
								otherDeduction.getOtherPrice());
						
					} else {
						message = "FAS单据作废失败！";
					}
				} else {
					message = "FAS不存在该单据！";
				}
				//删除返利和其他费用
				otherDeductionMapper.deleteByBillNo(billHeaderApiDto.getBillNo());
			} else {
				message = "单据类型有误，不允许作废！";
			}
		} catch (Exception e) {
			logger.error("调用invalidWholesaleOrders作废方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
		return message;
	}

	/**
	 * 网销类单据插入单据池
	 * 
	 * @param balance
	 *        BillSaleBalance
	 * @return Boolean
	 * @throws RpcException
	 *             异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RpcException.class)
	@Override
	public boolean insertNet(List<BillBalanceApiDto> lstBill)
			throws RpcException {
		try {
			String validateMessage = this.validateNetBill(lstBill);
			if(StringUtils.isNotEmpty(validateMessage)){
				this.addFailureLog(lstBill.get(0), "传递的数据校验失败");
				return false;
			}
			int count = billSaleBalanceApiService.batchInsertNet(lstBill);
			if (count > 0) {
				this.addSuccessLog(lstBill.get(0));
			}
			return count > 0;
		} catch (ServiceException e) {
			logger.error("调用insertNet作废方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	private String validateNetBill(List<BillBalanceApiDto> lstBill) {
		if(CollectionUtils.isEmpty(lstBill)){
			return "数据校验失败!";
		}
		for (BillBalanceApiDto dto : lstBill) {
			if(StringUtils.isBlank(dto.getSalerNo()) ||
					StringUtils.isBlank(dto.getSalerNo()) || 
					StringUtils.isBlank(dto.getBillNo()) || 
					StringUtils.isBlank(dto.getItemNo()) ){
				return "数据校验失败!";
			}
		}
		return null;
	}
}
