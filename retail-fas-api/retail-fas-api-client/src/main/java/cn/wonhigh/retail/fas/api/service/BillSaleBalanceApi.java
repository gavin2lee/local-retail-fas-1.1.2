package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;

import com.yougou.logistics.base.common.exception.RpcException;

public interface BillSaleBalanceApi {

	/**
	 * 销售类单据插入单据池
	 * @param balance List<BillBalanceApiDto>
	 * @return boolean
	 * @throws RpcException 异常
	 */
	boolean insert(List<BillBalanceApiDto> lstBill) throws RpcException;

	/**
	 * 网销类单据插入单据池
	 * @param balance List<BillBalanceApiDto>
	 * @return boolean
	 * @throws RpcException 异常
	 */
	boolean insertNet(List<BillBalanceApiDto> lstBill) throws RpcException;
	
	/**
	 * 作废单据
	 * @param billHeaderApiDto 单据头对象
	 * @return 作废是否成功
	 * @throws RpcException 异常
	 */
	boolean invalid(BillHeaderApiDto billHeaderApiDto) throws RpcException;
	
	/**
	 * 批发单据作废
	 * @param billHeaderApiDto 单据头对象
	 * @throws RpcException 异常
	 */
	String invalidWholesaleOrders(BillHeaderApiDto billHeaderApiDto) throws RpcException;
	
}
