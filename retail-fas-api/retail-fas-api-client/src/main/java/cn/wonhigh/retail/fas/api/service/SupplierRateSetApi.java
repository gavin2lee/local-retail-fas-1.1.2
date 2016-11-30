package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import cn.wonhigh.retail.fas.common.dto.SupplierRateSetDto;
import com.yougou.logistics.base.common.exception.RpcException;

/**
 * 获取供应商货币、税率等设置
 * @author kain
 *
 */
public interface SupplierRateSetApi {



	/**
	 * 
	 * @param supplierNos 供应商集合
	 * @return
	 * @throws RpcException
	 */
	List<SupplierRateSetDto> getSupplierRateSet(List<String> supplierNos) throws RpcException;
	
	
	/**
	 * 
	 * @param supplierNo
	 * @return
	 * @throws RpcException
	 */
	SupplierRateSetDto getSupplierRateSet(String supplierNo) throws RpcException;
	
}
