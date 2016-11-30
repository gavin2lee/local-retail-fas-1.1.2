package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceCateSumFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
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
public interface BillShopBalanceCateSumService extends BaseCrudService {
	
	public BigDecimal getSumAmount(@Param("params")Map<String,Object> params);

	public List<BillShopBalanceCateSum> findShopCateSummary(Map<String, Object> params) throws ServiceException;

	/**
	 * 获取页脚对象
	 * @param params 查询参数
	 * @return 页脚对象
	 */
	public BillShopBalanceCateSumFooterDto getFootDto(Map<String, Object> params);
	
	public List<BillShopBalanceCateSum> findBrandShopCate(Map<String, Object> params) throws ServiceException;
	
	public List<BillShopBalanceCateSum> findBalanceShopBrand(Map<String, Object> params) throws ServiceException;
	
	public  int modifyInvoiceAmountShareByNo(@Param("params")Map<String,Object> params);
	
     public  int modifySubInvoiceAmountByBalance(@Param("params")Map<String, Object> params);
	
	public  int modifySubInvoiceAmountByBrand(@Param("params")Map<String, Object> params);
	
	public  int modifySubInvoiceAmountByRound(@Param("params")Map<String, Object> params);
	
    public  int modifyBrandBillingAmountByRound(@Param("params")Map<String, Object> params);
	
	public  int modifyBrandBillingAmountByBrand(@Param("params")Map<String, Object> params);
	
    public  int modifyCateSumAmountDiff(@Param("params")Map<String, Object> params);
    
    public List<BillShopBalanceCateSum> findBrandSalesCategory3(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
}