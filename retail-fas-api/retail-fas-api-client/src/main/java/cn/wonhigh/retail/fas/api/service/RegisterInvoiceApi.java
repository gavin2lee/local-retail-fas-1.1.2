package cn.wonhigh.retail.fas.api.service;

import com.yougou.logistics.base.common.exception.RpcException;

import cn.wonhigh.retail.fas.api.dto.PagingDto;
import cn.wonhigh.retail.fas.api.dto.RegisterInvoiceDto;
import cn.wonhigh.retail.fas.api.dto.RegisterInvoiceParamDto;

public interface RegisterInvoiceApi {
	
	/**
	 * query the bill list 
	 * @param registerInvoicePara
	 * @return
	 * @throws RpcException
	 */
	PagingDto<RegisterInvoiceDto> queryRegisterInvoiceList(RegisterInvoiceParamDto registerInvoicePara) throws RpcException ;
	
	/**
	 * update bill's status by the billNo
	 * @param billNo bill'no
	 * @param useFlag status
	 * @return
	 * @throws RpcException
	 */
	boolean updateBillUseFlagByBillNo(String billNo,Integer useFlag) throws RpcException ;
	
}
