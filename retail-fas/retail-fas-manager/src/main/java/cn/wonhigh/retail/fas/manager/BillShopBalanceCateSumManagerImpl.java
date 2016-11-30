package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceCateSumFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.service.BillShopBalanceCateSumService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-12-02 14:50:43
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
@Service("billShopBalanceCateSumManager")
class BillShopBalanceCateSumManagerImpl extends BaseCrudManagerImpl implements BillShopBalanceCateSumManager {
	
    @Resource
    private BillShopBalanceCateSumService billShopBalanceCateSumService;

    @Override
    public BaseCrudService init() {
        return billShopBalanceCateSumService;
    }

	@Override
	public BigDecimal getSumAmount(Map<String, Object> params)
			throws ManagerException {
		return billShopBalanceCateSumService.getSumAmount(params);
	}

	/**
	 * 获取页脚对象
	 * @param params 查询参数
	 * @return 页脚对象
	 */
	@Override
	public BillShopBalanceCateSumFooterDto getFootDto(Map<String, Object> params) {
		return billShopBalanceCateSumService.getFootDto(params);
	}

	@Override
	public List<BillShopBalanceCateSum> findBrandShopCate(
			Map<String, Object> params) throws ManagerException {
			try {
				return billShopBalanceCateSumService.findBrandShopCate(params);
			}  catch (ServiceException e) {
				throw new ManagerException(e.getMessage(), e);
			}
	}

	@Override
	public List<BillShopBalanceCateSum> findBalanceShopBrand(
			Map<String, Object> params) throws ManagerException {
		try {
		return billShopBalanceCateSumService.findBalanceShopBrand(params);
		}  catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int modifyInvoiceAmountShareByNo(Map<String, Object> params)
			throws ManagerException {
			return billShopBalanceCateSumService.modifyInvoiceAmountShareByNo(params);
		}

	@Override
	public int modifySubInvoiceAmountByBalance(Map<String, Object> params)
			throws ManagerException {
		return billShopBalanceCateSumService.modifySubInvoiceAmountByBalance(params);
	}

	@Override
	public int modifySubInvoiceAmountByBrand(Map<String, Object> params)
			throws ManagerException {
		return billShopBalanceCateSumService.modifySubInvoiceAmountByBrand(params);
	}

	@Override
	public int modifyCateSumAmountDiff(Map<String, Object> params)
			throws ManagerException {		
		return billShopBalanceCateSumService.modifyCateSumAmountDiff(params);
	}

	@Override
	public List<BillShopBalanceCateSum> findBrandSalesCategory3
			(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException {
		try {
			return billShopBalanceCateSumService.findBrandSalesCategory3(page, orderByField, orderBy, params);
			}  catch ( Exception e) {
				throw new ManagerException(e.getMessage(), e);
			}
			
	}
}