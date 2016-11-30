package cn.wonhigh.retail.fas.api.strategy;

import java.util.List;

import org.springframework.util.StringUtils;

import cn.wonhigh.retail.backend.core.SpringContext;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.service.RegionCostCaculatorService;

import com.yougou.logistics.base.common.exception.ServiceException;

public class BillBuyBalanceHandlerFactory {

	public static BillBuyBalanceHandler getInstanse(List<BillBalanceApiDto> lstBill) {
		BillBuyBalanceHandler handler = null;
		if (lstBill != null) {
			BillBalanceApiDto bill = lstBill.get(0);
			boolean isBaroque = determineBaroque(bill.getItemCode());
			if (isBaroque) {
				handler = SpringContext.getBean(BaroqueBillBuyBalanceHandlerImpl.class);
			} else {
				handler = SpringContext.getBean(DefaultBillBuyBalanceHandlerImpl.class);
			}
		}
		return handler;
	}

	private static boolean determineBaroque(String itemCode) {
		RegionCostCaculatorService regionCostCaculatorService = SpringContext.getBean(RegionCostCaculatorService.class);
		boolean result = false;
		try {
			if (StringUtils.hasText(itemCode)) {
				result = regionCostCaculatorService.isBaroqueBill(itemCode);
			}
		} catch (ServiceException ex) {
			result = false;
		}
		return result;
	}
}
