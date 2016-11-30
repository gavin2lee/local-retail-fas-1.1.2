/**
 * title:TransferBalanceDateApiManagerImpl.java
 * package:cn.wonhigh.retail.fas.api.manager
 * description:地区调货结算期
 * auther:user
 * date:2016-7-25 下午2:58:43
 */
package cn.wonhigh.retail.fas.api.manager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.service.TransferBalanceDateApi;
import cn.wonhigh.retail.fas.api.service.TransferBalanceDateApiService;
import cn.wonhigh.retail.fas.common.model.TransferBalanceDate;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("transferBalanceDateApiManagerImpl")
public class TransferBalanceDateApiManagerImpl implements TransferBalanceDateApi {
	@Resource
    private TransferBalanceDateApiService transferBalanceDateApiService;
	
	private Logger log = Logger.getLogger(getClass());
	
	@Override
	public TransferBalanceDate findTransferBalanceDate(String salerNo, String buyerNo) throws RpcException {
		Map<String, Object> params = new HashMap<String, Object>();
		TransferBalanceDate tbd = null;
		try {
			if (StringUtils.isNotEmpty(salerNo) && StringUtils.isNotEmpty(buyerNo)) {
				params.put("salerNo", salerNo);
				params.put("buyerNo", buyerNo);
				tbd = transferBalanceDateApiService.findTransferBalanceDate(params);
			}
			return tbd;
		} catch (ServiceException e) {
			log.error("获取结算终止日失败", e);
			throw new RpcException(10001, "获取结算终止日失败", "财务辅助dubbo服务", e);
		}
	}

}
