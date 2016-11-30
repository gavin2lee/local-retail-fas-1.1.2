package cn.wonhigh.retail.fas.api.strategy;

import java.util.List;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface BillBuyBalanceAdditionalStrategy {

	boolean process(List<BillBalanceApiDto> dtoList) throws ServiceException;
}
