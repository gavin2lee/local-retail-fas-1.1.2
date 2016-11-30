package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.ContractDiscount;
import cn.wonhigh.retail.fas.common.utils.AlgorithmUtil;
import cn.wonhigh.retail.fas.dal.database.PEUtilMapper;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-24 14:59:22
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("peUtilService")
class PEUtilServiceImpl implements PEUtilService {

	@Resource
	private PEUtilMapper peUtilMapper;
	
	
	@Override
	public BigDecimal getBuyBalancePrice(BillBuyBalance balance) throws ServiceException{
		try {
			BigDecimal balancePrice = new BigDecimal(0);
			BigDecimal tagPrice = peUtilMapper.selectTagPriceByItemNo(balance.getItemNo());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("salerNo", balance.getSalerNo());
			params.put("buyerNo", balance.getBuyerNo());
			params.put("brandNo", balance.getBrandNo());
			params.put("categoryNo", balance.getCategoryNo().substring(0, 2));
			params.put("orderType", balance.getOrderType());
			params.put("contractDiscountType", 1);
			params.put("sendDate", balance.getSendDate());
			ContractDiscount discount = peUtilMapper.selectLastContractDiscount(params);
			if(null != tagPrice && null != discount){
				if("1".equals(discount.getPriceBasis())){// 牌价算法
					balancePrice = AlgorithmUtil.algorithmAll(discount.getAlgorithm(), tagPrice, discount.getDiscount1(), discount.getDiscount2());
				}else if("2".equals(discount.getPriceBasis())){// 终端销售金额
					
				}
			}
			return balancePrice;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BigDecimal getSaleBalancePrice(BillSaleBalance balance)
			throws ServiceException {
		try {
			BigDecimal balancePrice = new BigDecimal(0);
			BigDecimal tagPrice = peUtilMapper.selectTagPriceByItemNo(balance.getItemNo());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("salerNo", balance.getSalerNo());
			params.put("buyerNo", balance.getBuyerNo());
			params.put("brandNo", balance.getBrandNo());
			params.put("categoryNo", balance.getCategoryNo().substring(0, 2));
			params.put("orderType", balance.getOrderType());
			params.put("contractDiscountType", 2);
			params.put("sendDate", balance.getSendDate());
			ContractDiscount discount = peUtilMapper.selectLastContractDiscount(params);
			if(null != tagPrice && null != discount){
				if("1".equals(discount.getPriceBasis())){// 牌价算法
					balancePrice = tagPrice.multiply(discount.getDiscount1()).setScale(2,BigDecimal.ROUND_HALF_UP);
					balancePrice = balancePrice.add(discount.getAddPrice());
				}else if("2".equals(discount.getPriceBasis())){// 终端销售金额
					
				}else if("3".equals(discount.getPriceBasis())){// 供应商结算价
					
				}
			}
			return balancePrice;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Date getDueDate(String billNo,int billType)
			throws ServiceException {
		try {
			if(BillTypeEnums.ASN.getRequestId().intValue() == billType){
				return peUtilMapper.selectAsnDueDateByBillNo(billNo);
			}
			return peUtilMapper.selectAdjustDueDateByBillNo(billNo);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BigDecimal selectTagPriceByItemNo(String itemNo)
			throws ServiceException {
		try {
			return peUtilMapper.selectTagPriceByItemNo(itemNo);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public ContractDiscount selectLastContractDiscount(
			Map<String, Object> params) throws ServiceException {
		try {
			return peUtilMapper.selectLastContractDiscount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
}