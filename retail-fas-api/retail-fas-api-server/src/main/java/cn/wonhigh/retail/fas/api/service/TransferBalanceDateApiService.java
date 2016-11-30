/**
 * title:TransferBalanceDateApiService.java
 * package:cn.wonhigh.retail.fas.api.service
 * description:TODO
 * auther:user
 * date:2016-7-25 下午3:07:56
 */
package cn.wonhigh.retail.fas.api.service;

import java.util.Map;

import com.yougou.logistics.base.common.exception.ServiceException;

import cn.wonhigh.retail.fas.common.model.TransferBalanceDate;

/**
 * 
 */
public interface TransferBalanceDateApiService {
	public TransferBalanceDate findTransferBalanceDate(Map<String,Object> params) throws ServiceException;;
}
