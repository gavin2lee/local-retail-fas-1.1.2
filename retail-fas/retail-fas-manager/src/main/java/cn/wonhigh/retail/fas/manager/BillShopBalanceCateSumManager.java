package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceCateSumFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillShopBalanceCateSumManager extends BaseCrudManager {
	
	BigDecimal getSumAmount(Map<String,Object> params) throws ManagerException;
	
	/**
	 * 获取页脚对象
	 * @param params 查询参数
	 * @return 页脚对象
	 */
	BillShopBalanceCateSumFooterDto getFootDto(Map<String, Object> params);
	
	public List<BillShopBalanceCateSum> findBrandShopCate(Map<String, Object> params) throws ManagerException;
	
	public List<BillShopBalanceCateSum> findBalanceShopBrand(Map<String, Object> params) throws ManagerException;
	
	public  int modifyInvoiceAmountShareByNo(Map<String,Object> params) throws ManagerException;
	
    public  int modifySubInvoiceAmountByBalance(Map<String,Object> params) throws ManagerException;
    
	public  int modifySubInvoiceAmountByBrand(Map<String,Object> params) throws ManagerException;
	
    public  int modifyCateSumAmountDiff(Map<String,Object> params) throws ManagerException;
     
    public List<BillShopBalanceCateSum> findBrandSalesCategory3(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;
	
}