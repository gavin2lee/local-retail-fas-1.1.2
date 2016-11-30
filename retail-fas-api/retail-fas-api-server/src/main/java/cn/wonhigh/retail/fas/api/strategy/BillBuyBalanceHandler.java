/**
 * 
 */
package cn.wonhigh.retail.fas.api.strategy;

import java.util.List;

import com.yougou.logistics.base.common.exception.RpcException;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;

/**
 * @author user
 *
 */
public interface BillBuyBalanceHandler {
		boolean process(List<BillBalanceApiDto> lstBill) throws RpcException;
}
