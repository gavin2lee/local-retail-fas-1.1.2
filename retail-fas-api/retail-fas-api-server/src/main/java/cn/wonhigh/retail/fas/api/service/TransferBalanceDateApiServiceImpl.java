/**
 * title:TransferBalanceDateApiServiceImpl.java
 * package:cn.wonhigh.retail.fas.api.service
 * description:TODO
 * auther:user
 * date:2016-7-25 下午3:10:21
 */
package cn.wonhigh.retail.fas.api.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.TransferBalanceDateApiMapper;
import cn.wonhigh.retail.fas.common.model.TransferBalanceDate;

@Service("transferBalanceDateApiServiceImpl")
public class TransferBalanceDateApiServiceImpl implements
		TransferBalanceDateApiService {
	@Resource
    private TransferBalanceDateApiMapper transferBalanceDateApiMapper;
	
	@Override
	public TransferBalanceDate findTransferBalanceDate(Map<String,Object> params) {
		TransferBalanceDate tbd = new TransferBalanceDate();
		tbd = transferBalanceDateApiMapper.selectTransferBalanceDate(params);
		return tbd;
	}

}
