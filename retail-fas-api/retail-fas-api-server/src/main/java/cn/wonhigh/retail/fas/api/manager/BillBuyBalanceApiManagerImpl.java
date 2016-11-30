package cn.wonhigh.retail.fas.api.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.api.service.BillBuyBalanceApi;
import cn.wonhigh.retail.fas.api.service.BillBuyBalanceApiService;
import cn.wonhigh.retail.fas.api.service.BillSaleBalanceApiService;
import cn.wonhigh.retail.fas.api.service.FinancialAccountApiService;
import cn.wonhigh.retail.fas.api.service.PayRelationshipApiService;
import cn.wonhigh.retail.fas.api.strategy.BillBuyBalanceHandler;
import cn.wonhigh.retail.fas.api.strategy.BillBuyBalanceHandlerFactory;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Service("billBuyBalanceApiManager")
public class BillBuyBalanceApiManagerImpl extends BillBalanceApiManagerImpl implements BillBuyBalanceApi {

	private Logger logger = Logger.getLogger(BillBuyBalanceApiManagerImpl.class);

	@Resource
	private BillBuyBalanceApiService billBuyBalanceApiService;

	@Resource
	private BillSaleBalanceApiService billSaleBalanceApiService;

	@Resource
	private FinancialAccountApiService financialAccountApiService;

	@Resource
	private PayRelationshipApiService payRelationshipApiService;

	/**
	 * 采购类单据插入单据池
	 * 
	 * @param lstBill
	 *            List<BillBalanceApiDto>
	 * @return Boolean
	 * @throws RpcException
	 *             异常
	 */
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RpcException.class)
	public boolean insert(List<BillBalanceApiDto> lstBill) throws RpcException {
		if(!CollectionUtils.isEmpty(lstBill) && !this.isTransferBillType(lstBill.get(0)) // 调货入库单 不判断是否结算
				&& this.isBalance(lstBill.get(0).getBillNo())){
			throw new RpcException("FAS", "单据已结算，不允许重传");
		}
		if(isWholesale(lstBill.get(0).getOrderUnitNo())){// 批发判断
			logger.info("批发公司数据不同步：" + lstBill.get(0).getBillNo());
			return true;
		}
		BillBuyBalanceHandler handler = BillBuyBalanceHandlerFactory.getInstanse(lstBill);
		if (handler != null) {
			return handler.process(lstBill);
		} else {
			throw new RpcException("", "");
		}
	}

	// 批发判断
	private boolean isWholesale(String orderUnitNo) throws RpcException {
		if(StringUtils.isNotBlank(orderUnitNo)){
			try {
				return billBuyBalanceApiService.selectIsWholesaleCount(orderUnitNo) > 0;
			} catch (Exception e) {
				logger.error("查询批发单据出错:" + e.getMessage(), e);
				throw new RpcException(e.getMessage(), e);
			}
		}
		return false;
	}

	/**
	 * 判断单据是否是跨区调货类型
	 * 
	 * @param billBalanceApiDto
	 *            BillBalanceApiDto对象
	 * @return true or false
	 */
	private boolean isTransferBillType(BillBalanceApiDto billBalanceApiDto) {
		if (billBalanceApiDto.getBillType().intValue() == BillTypeEnums.REGION_SHOP_SHOP.getRequestId().intValue()
				|| billBalanceApiDto.getBillType().intValue() == BillTypeEnums.REGION_STORE_STORE.getRequestId()
						.intValue()
				|| billBalanceApiDto.getBillType().intValue() == BillTypeEnums.REGION_SHOP_STORE.getRequestId()
						.intValue()
				|| billBalanceApiDto.getBillType().intValue() == BillTypeEnums.REGION_STORE_SHOP.getRequestId()
						.intValue()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 作废单据
	 * 
	 * @param billHeaderApiDto
	 *            单据头对象
	 * @return 作废是否成功
	 * @throws RpcException
	 *             异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RpcException.class)
	@Override
	public boolean invalid(BillHeaderApiDto billHeaderApiDto) throws RpcException {
		try {
			Map<String, String> msgMap = this.validateBillHeader(billHeaderApiDto);
			if (msgMap != null && msgMap.size() > 0) {
				throw new RpcException("FAS", msgMap.get("errorMsg"));
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billType", billHeaderApiDto.getBillType());
			params.put("bizType", billHeaderApiDto.getBizType());
			params.put("billNo", billHeaderApiDto.getBillNo());
			params.put("balanceNoNotNull", "true");
			int count = 0;
			// 如果是拆单类型的单据，需要删除bill_buy_balance和bill_sale_balance两张表里的数据
			if (this.isSplitBillType(billHeaderApiDto.getBillType(), billHeaderApiDto.getBizType())) {
				// 查询单据是否已做结算(拆单的单据需查询两种表)
				int buyCount = billBuyBalanceApiService.selectCountByParams(params);
				if (buyCount > 0) {
					throw new RpcException("FAS", "单据已结算，不允许作废！");
				}
				int saleCount = billSaleBalanceApiService.selectCountByParams(params);
				if (saleCount > 0) {
					throw new RpcException("FAS", "单据已结算，不允许作废！");
				}
				count = billBuyBalanceApiService.invalid(billHeaderApiDto);
				count += billSaleBalanceApiService.invalid(billHeaderApiDto);
				payRelationshipApiService.invalid(billHeaderApiDto);
			} else {
				// 查询单据是否已做结算
				int selectCount = billBuyBalanceApiService.selectCountByParams(params);
				if (selectCount > 0) {
					throw new RpcException("FAS", "单据已结算，不允许作废！");
				}
				count = billBuyBalanceApiService.invalid(billHeaderApiDto);
				payRelationshipApiService.invalid(billHeaderApiDto);
			}
			return count > 0;
		} catch (ServiceException e) {
			logger.error("调用invalid作废方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	@Override
	public int selectBillBuyBalanceCount(Map<String, Object> params) throws RpcException {
		try {
			// 1.查询总部职能公司
			params.put("zoneCompanyNo", financialAccountApiService.selectLeadRoleCompanyNos());
			int count = billBuyBalanceApiService.selectBillBuyBalanceCount(params);
			return count;
		} catch (ServiceException e) {
			logger.error("调用selectBillBuyBalanceCount方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBuyBalance> selectBillBuyBalanceInfo(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws RpcException {
		try {
			// 1.查询总部职能公司
			params.put("zoneCompanyNo", financialAccountApiService.selectLeadRoleCompanyNos());
			List<BillBuyBalance> list = billBuyBalanceApiService.selectBillBuyBalanceInfo(page, orderByField, orderBy,
					params);
			return list;
		} catch (ServiceException e) {
			logger.error("调用selectBillBuyBalanceInfo方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBuyBalance> selectBillBuyBalanceFooter(Map<String, Object> params) throws RpcException {
		try {
			// 1.查询总部职能公司
			params.put("zoneCompanyNo", financialAccountApiService.selectLeadRoleCompanyNos());
			List<BillBuyBalance> list = billBuyBalanceApiService.selectBillBuyBalanceFooter(params);
			return list;
		} catch (ServiceException e) {
			logger.error("调用selectBillBuyBalanceFooter方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	@Override
	public int queryReportCount(Map<String, Object> params) throws RpcException {
		try {
			// 1.查询总部职能公司
			if (null == params.get("zoneCompanyNo")) {
				params.put("zoneCompanyNo", financialAccountApiService.selectLeadRoleCompanyNos());
			}
			return billBuyBalanceApiService.queryReportCount(params);
		} catch (ServiceException e) {
			logger.error("调用queryReportCount方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryReportByPage(Map<String, Object> params, SimplePage page) throws RpcException {
		try {
			// 1.查询总部职能公司
			if (null == params.get("zoneCompanyNo")) {
				params.put("zoneCompanyNo", financialAccountApiService.selectLeadRoleCompanyNos());
			}
			return billBuyBalanceApiService.queryReportByPage(params, page);
		} catch (ServiceException e) {
			logger.error("调用queryReportByPage方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryReportFooter(Map<String, Object> params) throws RpcException {
		try {
			// 1.查询总部职能公司
			if (null == params.get("zoneCompanyNo")) {
				params.put("zoneCompanyNo", financialAccountApiService.selectLeadRoleCompanyNos());
			}
			return billBuyBalanceApiService.queryReportFooter(params);
		} catch (ServiceException e) {
			logger.error("调用queryReportFooter方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> queryReportColumn(Map<String, Object> params) throws RpcException {
		try {
			// 1.查询总部职能公司
			if (null == params.get("zoneCompanyNo")) {
				params.put("zoneCompanyNo", financialAccountApiService.selectLeadRoleCompanyNos());
			}
			return billBuyBalanceApiService.queryReportColumn(params);
		} catch (ServiceException e) {
			logger.error("调用queryReportFooter方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}

	}

	@Override
	public boolean isBalance(String billNo) throws RpcException {
		try {
			int buyCount = billBuyBalanceApiService.selectCountByBillNo(billNo);
			int saleCount = billSaleBalanceApiService.selectCountByBillNo(billNo);
			int shipCount = payRelationshipApiService.selectBalanceCountByBillNo(billNo);
			return buyCount >0 || saleCount > 0 || shipCount >0;
		} catch (ServiceException e) {
			logger.error("调用查询是否结算方法失败:" + e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}

}
