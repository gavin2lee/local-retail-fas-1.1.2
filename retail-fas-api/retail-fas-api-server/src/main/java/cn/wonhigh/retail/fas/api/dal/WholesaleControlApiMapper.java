package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleControlApiDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public interface WholesaleControlApiMapper {

	//初始版本
	WholesaleControlApiDto selectByBillNo(@Param("billNo")String billNo);
	
	/***
	 * 获取收款条款 ByBillNo
	 * @param params
	 * @return
	 */
	WholesaleControlApiDto selectWholesaleReceTermByBillNo(@Param("params") Map<String, Object> params);
	
	/***
	 * 获取收款条款 ByCustomer
	 * @param params
	 * @return
	 */
	WholesaleControlApiDto selectWholesaleReceTermByCustomer(@Param("params") Map<String, Object> params);
	
	/***
	 * 获取条款明细
	 * @param params
	 * @return
	 */
	List<WholesaleControlApiDto> selectWholesaleReceTermDtlByTermNo(@Param("params") Map<String, Object> params);
	
	/***
	 * 获取批发订单预收款
	 * @param params
	 * @return
	 */
	WholesaleControlApiDto selectOrderPreAmountByParams(@Param("params") Map<String, Object> params);
	
	/***
	 * 汇总客户预收款
	 * @param params
	 * @return
	 */
	WholesaleControlApiDto selectCalcPaidAmountByParams(@Param("params") Map<String, Object> params);
	
	/***
	 * 汇总客户已出库金额
	 * @param params
	 * @return
	 */
	WholesaleControlApiDto selectCalcSendAmountByParams(@Param("params") Map<String, Object> params);
	
	/**
	 * 批量修改批发单价格
	 * @param params
	 * @return
	 */
	int batchUpdateBillCost(@Param("list") List<BillBalanceApiDto> list) ;
	
}