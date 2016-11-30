package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import cn.wonhigh.retail.fas.api.dal.CustomerOrderRemainMapper;
import cn.wonhigh.retail.fas.api.dal.CustomerReceRelMapper;
import cn.wonhigh.retail.fas.api.dal.OtherDeductionMapper;
import cn.wonhigh.retail.fas.api.dal.SystemParamSetMapper;
import cn.wonhigh.retail.fas.api.dal.WholesaleControlApiMapper;
import cn.wonhigh.retail.fas.api.dal.WholesaleCustomerRemainingDtlMapper;
import cn.wonhigh.retail.fas.api.dal.WholesaleCustomerRemainingMapper;
import cn.wonhigh.retail.fas.api.dal.WholesaleCustomerRemainingSumMapper;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleControlApiDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleCustomerRemainingDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleOutControlDto;
import cn.wonhigh.retail.fas.api.model.ApiMessage;
import cn.wonhigh.retail.fas.api.vo.CustomerOrderRemain;
import cn.wonhigh.retail.fas.api.vo.SystemParamSet;
import cn.wonhigh.retail.fas.api.vo.WholesaleCustomerRemainingDtl;
import cn.wonhigh.retail.fas.api.vo.WholesaleCustomerRemainingSum;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.GmsBillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.OtherDeductionTypeEnum;
import cn.wonhigh.retail.fas.common.enums.WholesaleRemainingTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("wholesaleControlApiService") 
public class WholesaleControlApiServiceImpl implements WholesaleControlApiService {
	
	@Resource
	private WholesaleControlApiMapper wholesaleControlApiMapper;
	
	@Resource
	private WholesaleCustomerRemainingMapper customerRemainingMapper;
	
	@Resource
	private WholesaleCustomerRemainingSumMapper remainingSumMapper;
	
	@Resource
	private WholesaleCustomerRemainingDtlMapper remainingDtlMapper;
	
	@Resource
	private CustomerOrderRemainMapper orderRemainMapper;
	
	@Resource
	private SystemParamSetMapper systemParamSetMapper;
	
	@Resource
	private OtherDeductionMapper otherDeductionMapper;
	
	@Resource
	private CustomerReceRelMapper customerReceRelMapper;	
	
	@Resource
	private BillSaleBalanceApiService billSaleBalanceApiService;
	
	@Resource
	private CustomerOrderRemainMapper customerOrderRemainMapper;
	
	private Logger logger = Logger.getLogger(WholesaleControlApiServiceImpl.class);
	
	//初始版本
	@Override
	public WholesaleControlApiDto getCheckMessage(String billNo)throws RpcException {
 		try {
 			WholesaleControlApiDto dto = wholesaleControlApiMapper.selectByBillNo(billNo);
 			if(null == dto){
 				throw new RpcException("FAS","没有找到批发订单!");
 			}else{
 				return dto;
 			}
		} catch(Exception e) {
			logger.error("获取批发预警信息出错:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	
	}
	
	private String checkOrderGoodParams(String billNo, Integer itemOrderType){
		String errorMessage = "";
		if(StringUtils.isEmpty(billNo)){
			errorMessage = "批发订单不能为空!";
		}
		if(itemOrderType != null){
			if(itemOrderType.intValue()!=1 && itemOrderType.intValue()!=2 ) {
				errorMessage = " GMS订补货类型传参有误！ itemOrderType ："+itemOrderType;
			}
		}else {
			errorMessage = " GMS订补货类型传参有误！ itemOrderType is null";
		}
		return errorMessage;
	}
	
	/***
	 * 批发订单  订货   使用订单控制
	 */
	@Override
	public String getOrderGoodsCheckMessage(String billNo) throws RpcException {
		logger.info("getOrderGoodsCheckMessage --- billNo: " + billNo );
		String errorMessage = "";
		if(StringUtils.isEmpty(billNo)){
			errorMessage = "批发订单不能为空!";
		}
		if(StringUtils.isNotEmpty(errorMessage)) {
			return errorMessage;
		}
		try {
			List<WholesaleControlApiDto> termDtlList = null;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", billNo);
			WholesaleControlApiDto apiDto = wholesaleControlApiMapper.selectWholesaleReceTermByBillNo(params);
			if(apiDto == null){
				errorMessage = "FAS 没有找到批发订单!";
			}else {
				if(apiDto.getCustomerReceRelStatus() == 1 && apiDto.getWholesaleReceTermStatus() == 1 ) {
					//启用保证金控制
					if(apiDto.getMarginControlFlag() != null && apiDto.getMarginControlFlag() == 1){
						if(apiDto.getMarginRemainderAmount().compareTo(apiDto.getMarginAmount()) == -1) {
							errorMessage = " 批发订单  "+ billNo +"的保证金不足,还需交"
									+ apiDto.getMarginAmount().subtract(apiDto.getMarginRemainderAmount()) +",请前往财务交款!";
						}
					}
					//启用保证金验证，没通过直接跳过订货条款的验证，返回保证金不足
					if(("").equals(errorMessage)) {
						if(apiDto.getTermNo() != null){ 
							params.clear();
							params.put("termNo", apiDto.getTermNo());
							params.put("controlPoint", "0");	//订货明细设置
							termDtlList = wholesaleControlApiMapper.selectWholesaleReceTermDtlByTermNo(params);
							if(termDtlList != null && termDtlList.size() > 0) {
								params.clear();
								params.put("billNo", billNo);
								WholesaleControlApiDto amountApiDto = wholesaleControlApiMapper.selectOrderPreAmountByParams(params);	//获取当前订单出库金额和预收款
								if(amountApiDto != null){
									BigDecimal orderGoodsAmount = termDtlList.get(0).getAdvanceScale().divide(new BigDecimal(100)).multiply(amountApiDto.getSendAmount());
									if(orderGoodsAmount.compareTo(amountApiDto.getPaidAmount())==1){
										errorMessage = " 批发订单为  "+ billNo +"的订货预收款不足,还需交"
															+ orderGoodsAmount.subtract(amountApiDto.getPaidAmount()) +",请前往财务交款!";
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e) {
			errorMessage = " 获取批发订货预警信息出错！";
			logger.error("获取批发订货预警信息出错:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
		return errorMessage;
	}
	
	/***
	 * 批发订单  订货   使用订单控制  
	 * itemOrderType 1:补货， 2:订货
	 */
	@Override
	public String getOrderGoodsCheckMessage(String billNo, Integer itemOrderType) throws RpcException {
		logger.info("getOrderGoodsCheckMessage --- billNo: " + billNo + " --itemOrderType: " + itemOrderType );
		String errorMessage = checkOrderGoodParams(billNo, itemOrderType);
		if(StringUtils.isNotEmpty(errorMessage)) {
			return errorMessage;
		}
		try {
			List<WholesaleControlApiDto> termDtlList = null;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", billNo);
			WholesaleControlApiDto apiDto = wholesaleControlApiMapper.selectWholesaleReceTermByBillNo(params);
			if(apiDto == null){
				errorMessage = "FAS 没有找到批发订单!";
			}else {
				if(apiDto.getCustomerReceRelStatus() == 1 && apiDto.getWholesaleReceTermStatus() == 1 ) {
					//启用保证金控制
					if(apiDto.getMarginControlFlag() != null && apiDto.getMarginControlFlag() == 1){
						if(apiDto.getMarginRemainderAmount().compareTo(apiDto.getMarginAmount()) == -1) {
							errorMessage = " 批发订单  "+ billNo +"的保证金不足,还需交"
									+ apiDto.getMarginAmount().subtract(apiDto.getMarginRemainderAmount()) +",请前往财务交款!";
						}
					}
					//启用保证金验证，没通过直接跳过订货条款的验证，返回保证金不足
					if(("").equals(errorMessage)) {
						if(apiDto.getTermNo() != null){ 
							params.clear();
							params.put("termNo", apiDto.getTermNo());
							params.put("controlPoint", itemOrderType.intValue()==1 ? "2" : "0");	
							termDtlList = wholesaleControlApiMapper.selectWholesaleReceTermDtlByTermNo(params);
							if(termDtlList != null && termDtlList.size() > 0) {
								params.clear();
								params.put("billNo", billNo);
								WholesaleControlApiDto amountApiDto = wholesaleControlApiMapper.selectOrderPreAmountByParams(params);	//获取当前订单出库金额和预收款
								if(amountApiDto != null){
									BigDecimal orderGoodsAmount = termDtlList.get(0).getAdvanceScale().divide(new BigDecimal(100)).multiply(amountApiDto.getSendAmount());
									if(orderGoodsAmount.compareTo(amountApiDto.getPaidAmount())==1){
										errorMessage = " 批发订单为  "+ billNo +"的订货预收款不足,还需交"
															+ orderGoodsAmount.subtract(amountApiDto.getPaidAmount()) +",请前往财务交款!";
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e) {
			errorMessage += " 获取批发订货预警信息出错！";
			logger.error("获取批发订货预警信息出错:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
		return errorMessage;
	}
	
	
	/***
	 * 批发订单  出库单  使用客户余额控制
	 */
	@Override
	public String getSendGoodsCheckMessage(String billNo, String orderUnitNo, String customerNo, BigDecimal amount) throws RpcException {

		String errorMessage = "";
		try {
			errorMessage = checkSendOrderMessage(billNo, orderUnitNo, customerNo, amount, "1");
		}catch(Exception e) {
			errorMessage += "获取批发出库预警信息出错!";
			logger.error("获取批发出库预警信息出错:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
		return errorMessage;
	}
	
	/***
	 * 发货通知单  使用客户余额控制
	 */
	@Override
	public String getDeliveryReleaseMessage(String billNo, String orderUnitNo, List<Map<String, Object>> list) throws RpcException {
		String errorMessage = "";
		try{
			if(list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					errorMessage += checkSendOrderMessage(billNo, orderUnitNo, map.get("customer_no")+"", new BigDecimal(map.get("amount")+""), "0");
				}
			}
		}catch(Exception e) {
			errorMessage += "获取批发出库预警信息出错!";
			logger.error("获取批发出库预警信息出错:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
		return errorMessage;
	}	

	private String checkSendOrderParams(String billNo, String orderUnitNo, String customerNo, BigDecimal amount){
		String errorMessage = "";
		if(StringUtils.isEmpty(billNo)){
			errorMessage = "批发订单不能为空!";
		}else if (StringUtils.isEmpty(orderUnitNo)){
			errorMessage = "订货单位不能为空!";
		}else if (StringUtils.isEmpty(customerNo)){
			errorMessage = "客户编码不能为空!";
		}else if (amount == null){
			errorMessage = "订单金额不能为空!";
		}
		return errorMessage;
	}
	
	public String checkSendOrderMessage(String billNo, String orderUnitNo, String customerNo, BigDecimal amount, String type)throws Exception {
		logger.info("checkSendOrderMessage --- billNo:" + billNo + ", orderUnitNo: " + orderUnitNo + ", customerNo: " + customerNo + ", amount: " + amount);
		String errorMessage = checkSendOrderParams(billNo, orderUnitNo, customerNo, amount);
		if(StringUtils.isNotEmpty(errorMessage)) {
			return errorMessage;
		}
		List<WholesaleControlApiDto> termDtlList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerNo", customerNo);
		params.put("orderUnitNo", orderUnitNo);
		WholesaleControlApiDto apiDto = wholesaleControlApiMapper.selectWholesaleReceTermByCustomer(params);
		if(amount.compareTo(new BigDecimal(0)) == 1){
			if(apiDto != null){
				//启用保证金控制
				if(apiDto.getMarginControlFlag() != null && apiDto.getMarginControlFlag() == 1){
					if(apiDto.getMarginRemainderAmount().compareTo(apiDto.getMarginAmount()) == -1) {
						errorMessage = " 客户："+ apiDto.getBuyerName() +" 保证金不足,还需交"
								+ apiDto.getMarginAmount().subtract(apiDto.getMarginRemainderAmount()) +",请前往财务交款!";
					}
				}
				//启用保证金验证，没通过直接跳过订货条款的验证，返回保证金不足
				if(("").equals(errorMessage)) {
					if(apiDto.getTermNo() != null) {
						params = new HashMap<String, Object>();
						params.put("termNo", apiDto.getTermNo());
						params.put("controlPoint", "1");		//出货明细设置
						termDtlList = wholesaleControlApiMapper.selectWholesaleReceTermDtlByTermNo(params);
						if(termDtlList != null && termDtlList.size() > 0){
							params.clear();
							params.put("customerNo", customerNo);
							params.put("orderUnitNo", orderUnitNo);
							WholesaleCustomerRemainingDto remainingDto = null;
							List<WholesaleCustomerRemainingDto> remainingDtoList = customerRemainingMapper.selectRemainingSumByParams(params);
							if(remainingDtoList != null && remainingDtoList.size() > 0) {
								remainingDto = remainingDtoList.get(0);
								if(amount.compareTo(remainingDto.getRemainingAmount()) == 1) {
									errorMessage += " 客户：  "+ remainingDto.getCustomerName() +" 的出货预收款不足,还需交"
											+ amount.subtract(remainingDto.getRemainingAmount()) +",请前往财务交款! </br>";
								}
							}else {
								params.clear();
								params.put("buyerNo", customerNo);
								params.put("orderUnitNo", orderUnitNo);
								WholesaleControlApiDto paidDto = wholesaleControlApiMapper.selectCalcPaidAmountByParams(params);
								WholesaleControlApiDto sendDto = wholesaleControlApiMapper.selectCalcSendAmountByParams(params);
								if(paidDto.getPaidAmount().subtract(sendDto.getSendAmount()).subtract(amount).compareTo(new BigDecimal(0)) < 0){
									errorMessage += " 客户：  "+ apiDto.getBuyerName() +" 的出货预收款不足,还需交"
											+ sendDto.getSendAmount().add(amount).subtract(paidDto.getPaidAmount()) +",请前往财务交款! </br>";
								}
							}
						}
					}
				}
			}
		}
		return errorMessage;
	}

	@Override
	public ApiMessage validateWholesaleOut(WholesaleOutControlDto controlDto) throws RpcException{
		ApiMessage msg = new ApiMessage();
		Map<String, Object> params = new HashMap<String, Object>();
		SystemParamSet systemParamSet = new SystemParamSet();
		//查询客户是否有收款条款
		
		
		
		
		//查询批发控制参数
		params.clear();
		params.put("companyNo", controlDto.getCompanyNo());
		params.put("paramCode", "AW_Wholesale_Credit");
		params.put("status", "1");
		List<SystemParamSet> systemParamSetList = systemParamSetMapper.selectByParams(null, params);
		if (null == systemParamSetList || systemParamSetList.size() == 0) {  
			systemParamSet.setDtlValue("1");
		}else{
			systemParamSet = systemParamSetList.get(0);
		}
		params.clear();
		//客户余额控制
		if ("1".equals(systemParamSet.getDtlValue())) {
			if (StringUtils.isEmpty(controlDto.getCustomerNo())) {
				msg.setErrorCode("0020");
				msg.setErrorMsg("客户订单为空");
				return msg;
			}
			params.put("customerNo", controlDto.getCustomerNo());			
			List<WholesaleCustomerRemainingSum> remainingSumList = remainingSumMapper.selectByParams(null, params) ;
			if (null == remainingSumList || remainingSumList.size() == 0) {
				msg.setErrorCode("0030");
				msg.setErrorMsg("没有找到客户余额");
				return msg;
			}
			WholesaleCustomerRemainingSum remainingSum = remainingSumList.get(0);
			msg = checkRemainAmount(remainingSum.getRemainingAmount(), controlDto, "客户余额");
		}else if("0".equals(systemParamSet.getDtlValue())){//订单余额控制
			if (StringUtils.isEmpty(controlDto.getOrderNo())) {
				msg.setErrorCode("0040");
				msg.setErrorMsg("客户订单为空");
				return msg;
			}
			params.put("orderNo", controlDto.getOrderNo());			
			List<CustomerOrderRemain> orderRemainList = orderRemainMapper.selectByParams(null, params) ;
			if (null == orderRemainList || orderRemainList.size() == 0) {
				msg.setErrorCode("0050");
				msg.setErrorMsg("没有找到客户订单");
				return msg;
			}
			CustomerOrderRemain orderRemain = orderRemainList.get(0);
			msg = checkRemainAmount(orderRemain.getRemainingAmount(), controlDto, "订单余额");

		}
		return msg;
	}
	
	
	private ApiMessage checkRemainAmount(BigDecimal remainAmount, WholesaleOutControlDto controlDto, String bizType){
		ApiMessage msg = new ApiMessage();
		
		if (BigDecimalUtil.add(remainAmount,controlDto.getRebateAmount()).compareTo(controlDto.getOutAmount()) >= 0) {
			msg.setErrorCode("0000");
			msg.setErrorMsg("");
			return msg;
		} else {
			//余额不足判断贷款
			//余额+贷款额度+返利金额-出库金额 >= 0
        	BigDecimal amount = BigDecimalUtil.add(remainAmount,controlDto.getCreditAmount(),controlDto.getRebateAmount());
        	if (amount.compareTo(controlDto.getOutAmount()) >= 0) {                		
        		msg.setErrorCode("0000");
    			msg.setErrorMsg("");
        		return msg;
			} else {
				msg.setErrorCode("0060");
				msg.setErrorMsg(bizType + "不足");
				return msg;
			}
		}
	}

	@Override
	public Integer updateOrderStatus(String orderNo, Integer status) throws RpcException{
		Map<String, Object> params = new HashMap<String, Object>();
		//查询批发订单
		params.put("orderNo", orderNo);
		List<CustomerOrderRemain> orderRemainList = orderRemainMapper.selectByParams(null, params) ;
		if (null != orderRemainList && orderRemainList.size() > 0) {
			CustomerOrderRemain orderRemain = orderRemainList.get(0);
			orderRemain.setStatus(status);
			return orderRemainMapper.updateByPrimaryKeySelective(orderRemain);
		} 
		return 0;
	}

	@Override
	@Transactional
	public ApiMessage addOtherDedution(OtherDeduction otherDedution) throws RpcException {
		ApiMessage msg = new ApiMessage();
		try {
			otherDedution.setId(null);
			if (null==otherDedution.getDeductionDate()) {
				otherDedution.setDeductionDate(new Date("yyyy-MM-dd"));
			}
			otherDedution.setBalanceType(BalanceTypeEnums.AREA_WHOLESALE.getTypeNo());//结算方式
			otherDedution.setStatus(YesNoEnum.YES.getValue());//结算方式
			if (null != otherDedution.getRebateAmount()) {
				 otherDedution.setFineAmount(otherDedution.getRebateAmount());
				 otherDedution.setDeductionAmount(otherDedution.getRebateAmount());
				 otherDedution.setType(OtherDeductionTypeEnum.RebateAmount.getValue());
				 otherDeductionMapper.insertSelective(otherDedution);
				
			 }
			 if (null != otherDedution.getOtherPrice()) {
				 otherDedution.setFineAmount(otherDedution.getOtherPrice());
				 otherDedution.setDeductionAmount(otherDedution.getOtherPrice());
				 otherDedution.setType(OtherDeductionTypeEnum.OtherPrice.getValue());
				 otherDeductionMapper.insertSelective(otherDedution);
			 }
			 msg.setErrorCode("0000");
			 msg.setErrorMsg("");
			 return msg;
		} catch (Exception e) {
			logger.error("增加扣项出错:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		} 
		
	}

	@Override
	public Boolean findCustomerReceRel(String companyNo, String customerNo) throws RpcException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", companyNo);
			params.put("customerNo", customerNo);
			params.put("queryCondition", "and status=1");
			
			int count = customerReceRelMapper.selectCount(params, null);
			if (count > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("查询客户收款条款出错:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		} 
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RpcException.class)    
	public String changeWholesaleCost(List<BillBalanceApiDto> lstBill) throws RpcException {
		try {
			//检查单据是否已经结算,解冻或者存在
			String message = "";
			BigDecimal changeAmount = BigDecimal.ZERO;
			String billNo = lstBill.get(0).getBillNo();
			String refBillNo = lstBill.get(0).getRefBillNo();
			Integer bizType = lstBill.get(0).getBizType();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", billNo);
			params.put("balanceNoNotNull", "true");
			params.put("customerNo", lstBill.get(0).getBuyerNo());
			int selectCount = billSaleBalanceApiService.selectCountByParams(params);
			if (selectCount > 0) {
				message = "单据已结算或者已解冻，不允许重传！";
				return message;
			}
			List<WholesaleCustomerRemainingDtl> remainingDtlList = remainingDtlMapper.selectByParams(null, params);
			WholesaleCustomerRemainingDtl remainingDtl = remainingDtlList.get(0);
			//增加返还金额明细
			remainingDtl.setMoney(BigDecimalUtil.subtract(BigDecimal.ZERO,remainingDtl.getMoney()));
			remainingDtl.setId(null);
			remainingDtl.setCreateTime(null);
			remainingDtl.setUpdateTime(null);
			remainingDtl.setRemark("批发作废单据:" + remainingDtl.getBillNo());
			remainingDtlMapper.insertSelective(remainingDtl);
			//修改以前的单据type为99作废
			params.put("type", WholesaleRemainingTypeEnums.CANCEL.getTypeNo());
			remainingDtlMapper.updateTypeByBillNo(params);
			BigDecimal sendAmount = BigDecimal.ZERO;//出库金额
			for (BillBalanceApiDto bill : lstBill) {
				sendAmount = sendAmount.add(BigDecimalUtil.multiInt(bill.getCost(), bill.getSendQty()));
			}			
			//如果是退货单
			if (bizType.intValue() ==  BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() 
					|| bizType.intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue()
					|| bizType.intValue() == BizTypeEnums.WHOLESALE_RETURN.getStatus().intValue()){
				changeAmount = BigDecimalUtil.add(sendAmount, remainingDtl.getMoney());
				remainingDtl.setMoney(sendAmount);
				remainingDtl.setRemark("批发变价退货:" + remainingDtl.getBillNo());
			}else {
				changeAmount = BigDecimalUtil.subtract(sendAmount,remainingDtl.getMoney());
				remainingDtl.setMoney(BigDecimalUtil.subtract(BigDecimal.ZERO,sendAmount));
				remainingDtl.setRemark("批发变价出库:" + remainingDtl.getBillNo());
			}
			//新增出库金额明细
			remainingDtlMapper.insertSelective(remainingDtl);
			
			//如果是客残退货和召回退货
			if (bizType.intValue() ==  BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() 
					|| bizType.intValue() == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue()){
				params.put("frozenCustomerAmount", changeAmount);
			}else {
				params.put("remainingAmount", BigDecimalUtil.subtract(BigDecimal.ZERO,changeAmount));
			}
			//更新客户余额
			remainingSumMapper.updateByCustomerNo(params);
			//修改批发单据价格
			wholesaleControlApiMapper.batchUpdateBillCost(lstBill);
			//如果有关联的批发订单,修改订单的余额
			
			CustomerOrderRemain customerOrderRemain = new CustomerOrderRemain();
			List<CustomerOrderRemain> remainingDtoList = null;
			// 出库单
			if(null != refBillNo){
				if ((bizType == BizTypeEnums.WHOLESALE.getStatus().intValue()
						|| bizType == BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().intValue())){
						params.put("orderNo", refBillNo);
						remainingDtoList = customerOrderRemainMapper.selectByParams(null, params);
						if (null != remainingDtoList && remainingDtoList.size() > 0) {
							customerOrderRemain = remainingDtoList.get(0);
							customerOrderRemain.setOutAmount(BigDecimalUtil.add(customerOrderRemain.getOutAmount(), changeAmount));
							customerOrderRemain.setRemainingAmount(BigDecimalUtil.subtract(customerOrderRemain.getRemainingAmount(), changeAmount));
							customerOrderRemainMapper.updateByPrimaryKeySelective(customerOrderRemain);
						}
				}		
			}
			// 客残退货单或者召回退货单		
			if ((bizType == BizTypeEnums.CUSTOMER_RETURN.getStatus().intValue() 
					|| bizType == BizTypeEnums.WHOLESALE_RECALL.getStatus().intValue()) ){
					params.put("companyNo", lstBill.get(0).getSalerNo());
					params.put("status", GmsBillStatusEnums.PARTRECEIPT.getStatus());
					// 查询最早一笔尚未完结的批发订单
					remainingDtoList = customerOrderRemainMapper.selectByParams(null, params);
					if (null != remainingDtoList && remainingDtoList.size() > 0) {
						customerOrderRemain = remainingDtoList.get(0);
						customerOrderRemain.setFrozenOrderAmount(BigDecimalUtil.add(customerOrderRemain.getFrozenOrderAmount(),changeAmount));
						customerOrderRemainMapper.updateByPrimaryKeySelective(customerOrderRemain);
					}						
			} 							
			return message;
		} catch (Exception e) {
			logger.error("批发变价出错:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		} 
	}



	

}
