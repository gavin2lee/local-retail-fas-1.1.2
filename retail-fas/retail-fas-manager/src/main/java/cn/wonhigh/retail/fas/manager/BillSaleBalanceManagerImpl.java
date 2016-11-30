package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.service.BillBalanceService;
import cn.wonhigh.retail.fas.service.BillSaleBalanceService;
import cn.wonhigh.retail.fas.service.CategoryService;
import cn.wonhigh.retail.fas.service.OtherDeductionService;
import cn.wonhigh.retail.mps.api.client.service.price.SalePriceApi;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-16 17:38:17
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
@Service("billSaleBalanceManager")
class BillSaleBalanceManagerImpl extends BaseCrudManagerImpl implements BillSaleBalanceManager {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BillSaleBalanceManagerImpl.class);
    
	@Resource
    private BillSaleBalanceService billSaleBalanceService;
	
	@Resource
    private CategoryService categoryService;
	
	@Resource
    private OtherDeductionService otherDeductionService;
	
	@Resource
	private BillBalanceService billBalanceService;
	
	@Resource
	private SalePriceApi salePriceApi;
	
    @Override
    public BaseCrudService init() {
        return billSaleBalanceService;
    }

    /**
	 * 查询批发订单的数量
	 * @param params 查询参数
	 * @return 订单的数据
	 * @throws ManagerException 异常
	 */
	@Override
	public int findSaleOrderCount(Map<String, Object> params) throws ManagerException {
		try {
			return billSaleBalanceService.findSaleOrderCount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 分页查询批发订单
	 * @param SimplePage 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return 订单数据集合
	 * @throws ManagerException 异常
	 */
	@Override
	public List<SaleOrderDto> findSaleOrderByPage(SimplePage page, String sortColumn, String sortOrder, 
			Map<String, Object> params) throws ManagerException {
		try {
			return billSaleBalanceService.findSaleOrderByPage(page, sortColumn, sortOrder, params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSaleBalance> selectBillSaleBalanceByNo(Map<String, Object> params) {
		return billSaleBalanceService.selectBillSaleBalanceByNo(params);
	}

	@Override
	public List<BillSaleBalance> selectSaleOrder(SimplePage page, Map<String, Object> params) {
		return billSaleBalanceService.selectSaleOrder(page, params);
	}

	@Override
	public int selectSaleOrderCounts(Map<String, Object> params) {
		return billSaleBalanceService.selectSaleOrderCounts(params);
	}
	
	@Override
	public int selectSaleOrderCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billSaleBalanceService.selectSaleOrderCount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<SaleOrderDto> selectSaleOrderByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return billSaleBalanceService.selectSaleOrderByPage(page, sortColumn, sortOrder, params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 根据查询条件查询GMS 内购销售信息: 团购出库、报废、差异索赔、客残销售、盘差索赔
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findGmsInnerBuyDetailList(SimplePage page, String orderByField,String orderBy,Map<String,Object> params)throws ManagerException{
		try {
			return billSaleBalanceService.findGmsInnerBuyDetailList(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}
	
	/**
	 * 根据查询条件查询GMS 内购销售记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findGmsInnerBuyDetailCount(Map<String,Object> params)throws ManagerException{
		try {
			return billSaleBalanceService.findGmsInnerBuyDetailCount(params);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}
	
	public List<BillSaleBalance> setExtendsBillSaleBalanceProperties(List<BillSaleBalance> list) {
    	List<BillSaleBalance> retList = new ArrayList<BillSaleBalance>();
    	if (!CollectionUtils.isEmpty(list)) {
    		for (BillSaleBalance billBalance : list) {
				retList.add(setExtendsProperties(billBalance));
    		}
		}
    	return retList;
    }
	
	private BillSaleBalance setExtendsProperties(BillSaleBalance billSaleBalance) {
		try {
			if (null != billSaleBalance.getCategoryNo()) {
				List<Category> categories = null;
				//设置一级大类
				Map<String, Object> categoryMaps = new HashMap<String, Object>();
				if (billSaleBalance.getCategoryNo().length() >= 2) {
					categoryMaps.put("categoryNo", billSaleBalance.getCategoryNo().substring(0, 2));
					categories = categoryService.findByBiz(null, categoryMaps);
					if (!CollectionUtils.isEmpty(categories)) {
						billSaleBalance.setOneLevelCategoryNo(categories.get(0).getCategoryNo());
						billSaleBalance.setOneLevelCategoryName(categories.get(0).getName());
					}
				}
				if (billSaleBalance.getCategoryNo().length() >= 4) {
					//设置二级大类
					categoryMaps.put("categoryNo", billSaleBalance.getCategoryNo().substring(0, 4));
					categories = categoryService.findByBiz(null, categoryMaps);
					if (!CollectionUtils.isEmpty(categories)) {
						billSaleBalance.setTwoLevelCategoryNo(categories.get(0).getCategoryNo());
						billSaleBalance.setTwoLevelCategoryName(categories.get(0).getName());
					}
				}
			}
			if(null != billSaleBalance.getTagPrice() && billSaleBalance.getTagPrice().compareTo(new BigDecimal(0)) != 0) {
				billSaleBalance.setDiscount(billSaleBalance.getCost().divide(billSaleBalance.getTagPrice(), 5 ,BigDecimal.ROUND_HALF_UP)); // 扣率 = cost/牌价
				billSaleBalance.setDiscountStr((billSaleBalance.getCost().multiply(new BigDecimal(100))).divide(billSaleBalance.getTagPrice(), 3 ,BigDecimal.ROUND_HALF_UP) + "%");
				if (null != billSaleBalance.getOtherDeductCost() && billSaleBalance.getOtherDeductCost().compareTo(BigDecimal.ZERO) != 0) {
					BigDecimal rebatePrice = BigDecimalUtil.subtract(billSaleBalance.getCost(),billSaleBalance.getOtherDeductCost().divide(new BigDecimal(billSaleBalance.getSendQty()),3 ,BigDecimal.ROUND_HALF_UP));
					billSaleBalance.setBillRebateDiscountStr((rebatePrice.multiply(new BigDecimal(100))).divide(
							billSaleBalance.getTagPrice(), 3, BigDecimal.ROUND_HALF_UP) + "%");
				}
			}else {
				billSaleBalance.setDiscount(new BigDecimal(0));
			}
			
		} catch (ServiceException e) {
			LOGGER.debug("销售出库对象其他属性设置出错。");
		}
		return billSaleBalance;
	}

	/**
	 * 合计
	 */
	@Override
	public List<Object> selectEnterFooter(Map<String, Object> params)throws ManagerException {
		try {
			return billSaleBalanceService.selectEnterFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Integer selectBrandCategoryDeductionCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billSaleBalanceService.selectBrandCategoryDeductionCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object> selectBrandCategoryDeductionFooter(
			Map<String, Object> params) throws ManagerException {
		try {
			return billSaleBalanceService.selectBrandCategoryDeductionFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSaleBalance> selectBrandCategoryDeductionByPage(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		try {
			return billSaleBalanceService.selectBrandCategoryDeductionByPage(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}