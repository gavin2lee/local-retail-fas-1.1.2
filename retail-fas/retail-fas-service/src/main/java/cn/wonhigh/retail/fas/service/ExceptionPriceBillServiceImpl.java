package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ExceptionPriceCheckDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.dal.database.ExceptionPriceBillMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2015-03-07 11:07:26
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("exceptionPriceBillService")
class ExceptionPriceBillServiceImpl extends BaseCrudServiceImpl implements ExceptionPriceBillService {
    @Resource
    private ExceptionPriceBillMapper exceptionPriceBillMapper;

    @Override
    public BaseCrudMapper init() {
        return exceptionPriceBillMapper;
    }
	
	@Override
	public int findRegionPriceExceptionsCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return exceptionPriceBillMapper.getRegionPriceExceptionsCount(params);
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ExceptionPriceCheckDto> findRegionPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getRegionPriceExceptionsByPage(page, sortColumn, sortOrder, params);
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int findPurchasePriceExceptionsCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return exceptionPriceBillMapper.getPurchasePriceExceptionsCount(params);
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ExceptionPriceCheckDto> findPurchasePriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getPurchasePriceExceptionsByPage(page, sortColumn, sortOrder, params);
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int findFasAllPriceExceptionsCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return exceptionPriceBillMapper.getFasAllPriceExceptionsCount(params);
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ExceptionPriceCheckDto> findFasAllPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getFasAllPriceExceptionsByPage(page, sortColumn, sortOrder, params);
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public int batchUpdatePriceSchedule() throws ServiceException {
		try {
			//更新1301,地区向总部采购
			exceptionPriceBillMapper.updateBuyAsnForAreaAndHq();
			//更新1301,总部向供应商采购
			exceptionPriceBillMapper.updateBuyAsnForHqAndSupplier();
			//更新1301,总部向地区销售
			exceptionPriceBillMapper.updateSaleAsnForHqAndArea();
			//更新1304,总部验收单
			exceptionPriceBillMapper.updateBuyReceiptForHq();
			//更新1304,地区验收单
			exceptionPriceBillMapper.updateBuyReceiptForArea();
			//更新1371,地区间跨区调货出库
			exceptionPriceBillMapper.updateSaleTransferOut();
			//更新1372,地区间跨区调货入库
			exceptionPriceBillMapper.updateBuyTransferIn();
			//更新1371,总部跨区调货出库
			exceptionPriceBillMapper.updateSaleTransferOutHQ();
			//更新1372,总部跨区调货入库
			exceptionPriceBillMapper.updateBuyTransferInHQ();
			//更新1335,1361,地区其他出库
//			exceptionPriceBillMapper.updateSaleAreaOthers();
			
			//更新1301,地区自采，地区与供应商结算
			exceptionPriceBillMapper.updateBuyAsnForAreaAndSupplier();
			//更新1301,地区自采，地区与供应商结算
			exceptionPriceBillMapper.updateBuyAsnCostCheckForAreaAndSupplier();
			
			//更新1301,地区向总部采购
			exceptionPriceBillMapper.updateBuyAsnCostCheckForAreaAndHq();
			//更新1301,总部向供应商采购
			exceptionPriceBillMapper.updateBuyAsnCostCheckForHqAndSupplier();
			//更新1301,总部向地区销售
			exceptionPriceBillMapper.updateSaleAsnCostCheckForHqAndArea();
			//更新1304,总部验收单
			exceptionPriceBillMapper.updateBuyReceiptCostCheckForHq();
			//更新1304,地区验收单
			exceptionPriceBillMapper.updateBuyReceiptCostCheckForArea();
			//更新1371,跨区调货出库
			exceptionPriceBillMapper.updateSaleTransferOutCostCheck();
			//更新1372,跨区调货入库
			exceptionPriceBillMapper.updateBuyTransferInCostCheck();
			//更新1335,1361,地区其他出库
//			exceptionPriceBillMapper.updateSaleAreaOthersCostCheck();
			
			return 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int findTagPriceExceptionsCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return exceptionPriceBillMapper.findTagPriceExceptionsCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ExceptionPriceCheckDto> findTagPriceExceptionsByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.findTagPriceExceptionsByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBuyBalance> getBuyAsnRegionPrices(Map<String, Object> params)
			throws ServiceException {
		try {
			return exceptionPriceBillMapper.getBuyAsnRegionPrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBuyBalance> getBuyReceiptRegionPrices(
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getBuyReceiptRegionPrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBuyBalance> getBuyReturnRegionPrices(
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getBuyReturnRegionPrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBuyBalance> getBuyTransferInRegionPrices(
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getBuyTransferInRegionPrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillSaleBalance> getSaleAsnRegionPrices(
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getSaleAsnRegionPrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillSaleBalance> getSaleTransferOutRegionPrices(
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getSaleTransferOutRegionPrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBuyBalance> getBuyAsnPurchasePrices(
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getBuyAsnPurchasePrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBuyBalance> getBuyReceiptPurchasePrices(
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getBuyReceiptPurchasePrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BillBuyBalance> getBuyReturnPurchasePrices(
			Map<String, Object> params) throws ServiceException {
		try {
			return exceptionPriceBillMapper.getBuyReturnPurchasePrices(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateBillBuyBalanceCost(BillBuyBalance buyBalance)
			throws ServiceException {
		try {
			return exceptionPriceBillMapper.updateBillBuyBalanceCost(buyBalance);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateBillSaleBalanceCost(BillBuyBalance buyBalance)
			throws ServiceException {
		try {
			return exceptionPriceBillMapper.updateBillSaleBalanceCost(buyBalance);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateBillSaleBalanceCost(BillSaleBalance saleBalance)
			throws ServiceException {
		try {
			return exceptionPriceBillMapper.updateSaleBalanceCost(saleBalance);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}