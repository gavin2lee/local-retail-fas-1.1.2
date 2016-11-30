package cn.wonhigh.retail.fas.api.service;

import cn.wonhigh.retail.fas.api.dto.InsideBizTypeDto;
import cn.wonhigh.retail.fas.api.dto.InsideBizTypeParamDto;
import cn.wonhigh.retail.fas.api.dto.PagingDto;

import com.yougou.logistics.base.common.exception.RpcException;

public interface InsideBizTypeApi {
	
	/**
	 * query the inside biz type list 
	 * @param registerInvoicePara
	 * @return
	 * @throws RpcException
	 */
	public PagingDto<InsideBizTypeDto> queryInsideBizTypeList(InsideBizTypeParamDto insideBizTypeParam) throws RpcException ;

	public PagingDto<InsideBizTypeDto> queryClientList(InsideBizTypeParamDto insideBizTypeParam) throws RpcException;
	
	
}
