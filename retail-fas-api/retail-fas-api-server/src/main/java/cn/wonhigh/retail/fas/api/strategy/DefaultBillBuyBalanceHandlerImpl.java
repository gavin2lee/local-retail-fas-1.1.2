package cn.wonhigh.retail.fas.api.strategy;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.manager.BillBalanceApiManagerImpl;
import cn.wonhigh.retail.fas.api.manager.BillBuyBalanceApiManagerImpl;
import cn.wonhigh.retail.fas.api.model.ApiMessage;
import cn.wonhigh.retail.fas.api.service.BillBuyBalanceApiService;
import cn.wonhigh.retail.fas.api.service.BillSaleBalanceApiService;
import cn.wonhigh.retail.fas.api.utils.CommonUtils;
import cn.wonhigh.retail.fas.api.utils.Validator;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Component("defaultBillBuyBalanceHandlerImpl")
public class DefaultBillBuyBalanceHandlerImpl extends BillBalanceApiManagerImpl implements BillBuyBalanceHandler {

	private Logger logger = Logger.getLogger(BillBuyBalanceApiManagerImpl.class);

	@Resource
	private BillBuyBalanceApiService billBuyBalanceApiService;

	@Resource
	private BillSaleBalanceApiService billSaleBalanceApiService;
	
	@Override
	public boolean process(List<BillBalanceApiDto> lstBill) throws RpcException {
		try {
			if (!Validator.validateAndConvertModel(lstBill)) {
				throw new RpcException("fas", "数据校验不通过!");
			}
			// 收货单（入库单）获取原相关发货单（出库单）信息
			this.formatReceiveBill(lstBill);

			boolean isSplit = false;
			BillBalanceApiDto dto = lstBill.get(0);
			if(CommonUtils.isPEByShardingFlag(dto.getShardingFlag())){
				isSplit = this.isPESplit(dto);
			}else{
				isSplit = this.isSplitType(lstBill);
			}
			if (isSplit) {
				ApiMessage message = this.splitBill(lstBill);
				if (message != null) {
					this.addFailureLog(lstBill.get(0), message.getErrorMsg());
					throw new RpcException(message.getProjectName(), message.getErrorMsg());
				}
				return true;
			}
			int count = billBuyBalanceApiService.batchInsert(lstBill);
			if (count > 0) {
				this.addSuccessLog(lstBill.get(lstBill.size() - 1));
			}
			return count > 0;
		} catch (ServiceException e) {
			logger.error("调用insert方法失败:" + e.getMessage(), e);
			this.addFailureLog(lstBill.get(0), "批量插入数据失败");
			throw new RpcException(e.getMessage(), e);
		}
	}

	/**
	 * 判断体育是否拆单
	 * @param dto
	 * @return
	 * @throws ServiceException
	 */
	private boolean isPESplit(BillBalanceApiDto dto) throws ServiceException {
		Integer billType = dto.getBillType();
		Integer bizType = dto.getBizType();
		// 到货单 | 验收单（订补货的才拆单）
		if ((billType.intValue() == BillTypeEnums.ASN.getRequestId().intValue() || billType.intValue() == BillTypeEnums.RECEIPT
				.getRequestId().intValue()) && bizType != null && (bizType.intValue() == 0 || bizType.intValue() == 1 || bizType.intValue() == 60)) {
			return true;
		}
		// 退厂单(原残退厂拆单)
		if (billType.intValue() == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
			return true;
		}
		// 到货单差异单据处理
		if((billType.intValue() == BillTypeEnums.ASN.getRequestId().intValue() || billType.intValue() == BillTypeEnums.RECEIPT.getRequestId().intValue()) 
				&& bizType != null && (bizType.intValue() == 3 || bizType.intValue() == 4 || bizType.intValue() == 5
						|| bizType.intValue() == 6 || bizType.intValue() == 7 || bizType.intValue() == 8 || bizType.intValue() == 9) ){
			int count = billBuyBalanceApiService.selectIsSplitCount(dto.getRefBillNo());
			return count >0;
		}
		return false;
	}
}
