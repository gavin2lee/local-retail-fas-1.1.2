/**
 * title:TransferBalanceDateApi.java
 * package:cn.wonhigh.retail.fas.api.service
 * description:地区间调货结算期
 * auther:user
 * date:2016-7-25 下午2:10:17
 */
package cn.wonhigh.retail.fas.api.service;

import cn.wonhigh.retail.fas.common.model.TransferBalanceDate;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;

/**
 * 
 */
public interface TransferBalanceDateApi {
	/**
	 * 获取调货结算期(体育跨区调货业务)
	 * @param salerNo 
	 * @param buyerNo
	 * @param sendDate
	 * @return
	 * @throws ManagerException 
	 * @throws RpcException 
	 */
    public TransferBalanceDate findTransferBalanceDate(String salerNo,String buyerNo) throws  RpcException;  
}
