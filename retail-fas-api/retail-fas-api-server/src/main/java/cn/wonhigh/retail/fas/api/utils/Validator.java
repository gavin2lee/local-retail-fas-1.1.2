package cn.wonhigh.retail.fas.api.utils;

import java.util.List;

import org.apache.log4j.Logger;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yougou.logistics.base.common.exception.ManagerException;

public class Validator {
	private static Logger logger = Logger.getLogger(Validator.class);
	/**
	 * 校验数据是否正确
	 * @param lstBill List<BillBalanceApiDto>
	 * @return Boolean
	 */
	public static boolean validateAndConvertModel(List<BillBalanceApiDto> lstBill) {
		if(lstBill == null || lstBill.size() == 0) {
			return false;
		}
		// 判断是否是批发/团购订单
		boolean isOrderBillFlag = isOrderBill(lstBill.get(0));
		boolean valid = true;
		for(BillBalanceApiDto dto : lstBill) {
			try {
				valid = ReflectUtil.validateRequired(dto, new String[]{"billNo", "billType"});
			} catch (ManagerException e) {
				logger.error(e.getMessage(), e);
			}
			if(!valid) {
				return false;
			}
			dto.setOriginalBillNo(dto.getBillNo());
			if(StringUtils.isEmpty(dto.getSalerNo())) {
				dto.setSalerNo(dto.getSupplierNo());
			}
			if(StringUtils.isEmpty(dto.getSalerName())) {
				dto.setSalerName(dto.getSupplierName());
			}
			// 如果不是批发/团购订单，批发/团购订单中billCost存订单金额，cost存销售价
			if(!isOrderBillFlag && dto.getCost() != null && 
				dto.getBillCost() == null) {
				dto.setBillCost(dto.getCost());
			}
			// 1335 1361
		}
		return true;
	}
	
	/**
	 * 判断单据是否是判断是否是批发/团购订单
	 * 
	 * @param billBalanceApiDto
	 *            单据
	 * @return ture or false
	 */
	private static boolean isOrderBill(BillBalanceApiDto billBalanceApiDto) {
		boolean result = false;
		if (BillTypeEnums.SALEORDER.getRequestId() == billBalanceApiDto.getBillType().intValue()
				&& billBalanceApiDto.getBizType() != null){
			Integer bizType = billBalanceApiDto.getBizType().intValue();
			if(BizTypeEnums.WHOLESALE.getStatus().intValue() == bizType
					|| BizTypeEnums.WHOLESALE_SHOP_OUT.getStatus().intValue() == bizType
					|| BizTypeEnums.WHOLESALE_RETURN.getStatus().intValue() == bizType
					|| BizTypeEnums.GROUPSALE.getStatus().intValue() == bizType 
					|| BizTypeEnums.GROUPSALE_RETURN.getStatus().intValue() == bizType){
						result = true;
			}
		}
		return result;
	}
}
