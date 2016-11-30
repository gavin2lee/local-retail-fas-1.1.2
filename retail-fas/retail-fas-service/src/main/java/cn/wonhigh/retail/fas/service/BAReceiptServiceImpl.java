package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.BAReceiptDto;
import cn.wonhigh.retail.fas.dal.database.BAReceiptMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author wangm
 * @date 2016-03-18 16:58:06
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("baReceiptService")
class BAReceiptServiceImpl implements BAReceiptService {

	@Resource
	private BAReceiptMapper baReceiptMapper;

	@Override
	public int findReceiptCount(Map<String, Object> params) throws ServiceException {
		try {
			String searchType = String.valueOf(params.get("searchType"));
			if ("item".equals(searchType)) {
				return baReceiptMapper.findReceiptItemCount(params);
			}
			return baReceiptMapper.findReceiptBillCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BAReceiptDto> findReceiptList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			String searchType = String.valueOf(params.get("searchType"));
			if ("item".equals(searchType)) {
				return baReceiptMapper.findReceiptItemList(page, sortColumn, sortOrder, params);
			}
			return baReceiptMapper.findReceiptBillList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BAReceiptDto> findReceiptFooter(Map<String, Object> params) throws ServiceException {
		try {
			return baReceiptMapper.findReceiptFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateCost(BAReceiptDto baReceiptDto) throws ServiceException {
		try {
			List<BAReceiptDto> lstItem = baReceiptMapper.selectNeedUpdatePurchaseList(baReceiptDto.getOriginalBillNo());
			if (!CollectionUtils.isEmpty(lstItem)) {
				for (BAReceiptDto dto : lstItem) {
					baReceiptMapper.updateCostById(dto);
				}
				return 1;
			}
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateRate(BAReceiptDto baReceiptDto) throws ServiceException {
		try {
			List<BAReceiptDto> lstItem = baReceiptMapper.selectNeedUpdateRateList(baReceiptDto.getOriginalBillNo());
			if (!CollectionUtils.isEmpty(lstItem)) {
				for (BAReceiptDto dto : lstItem) {
					baReceiptMapper.updateRateById(dto);
				}
				return 1;
			}
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateBuyBalanceNo(Map<String, Object> params) throws ServiceException {
		try {
			return baReceiptMapper.updateBuyBalanceNo(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String[] getItemNos(String originalBillNo) throws ServiceException {
		try {
			return baReceiptMapper.getItemNos(originalBillNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateBuyBalanceNoByItem(Map<String, Object> params) throws ServiceException {
		try {
			baReceiptMapper.updateBuyBalanceNoByItem(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BAReceiptDto> getReceiptForUpdate(Map<String, Object> params) throws ServiceException {
		try {
			return baReceiptMapper.getReceiptForUpdate(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateBuyBalanceTagPrice(Map<String, Object> params) throws ServiceException {
		try {
			baReceiptMapper.updateBuyBalanceTagPrice(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}