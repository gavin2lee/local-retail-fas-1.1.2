package cn.wonhigh.retail.fas.api.strategy;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.service.RegionCostCaculatorService;
import cn.wonhigh.retail.fas.api.vo.BillBuyBalanceAdditional;

import com.yougou.logistics.base.common.exception.ServiceException;

@Component("baroqueBillBuyBalanceAdditionalStrategy")
public class BaroqueBillBuyBalanceAdditionalStrategy implements BillBuyBalanceAdditionalStrategy {

	@Resource
	private RegionCostCaculatorService regionCostCaculatorService;

	@Override
	public boolean process(List<BillBalanceApiDto> dtoList) throws ServiceException {
		List<BillBuyBalanceAdditional> additionalList = this.regionCostCaculatorService
				.getBillBuyBalanceAdditional(dtoList);
		this.regionCostCaculatorService.batchInsert(additionalList);
		return true;
	}
}
