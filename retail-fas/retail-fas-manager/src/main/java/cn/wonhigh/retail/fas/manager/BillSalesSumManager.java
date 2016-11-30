package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-11 15:51:08
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
public interface BillSalesSumManager extends BaseCrudManager {
	
	/**
	 * 处理门店销售订单业务   商场  独立店   员购   
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<BillSalesSum> findSalesSum(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;
	
	/**
	 * 处理GMS货品流通的单据   退残  报废  团购     
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<BillSalesSum> findSaleGoodsGms(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;
	
	BillSalesSum initSubsiInfo(Map<String,Object> params);
	
	List<BillSalesSum> initSubsiInfoList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ServiceException;
	
	
	
	public BillSalesSum selectSalesSumPosCount(Map<String,Object> params,AuthorityParams authorityParams)throws ManagerException;
    
    public List<BillSalesSum> selectSalesSumPos(SimplePage page,String orderByField,String orderBy,Map<String,Object> params,AuthorityParams authorityParams)throws ManagerException;
	    
	public BillSalesSum selectSalesSumGmsCount(Map<String,Object> params,AuthorityParams authorityParams)throws ManagerException;
	    
    public List<BillSalesSum> selectSalesSumGms(SimplePage page,String orderByField,String orderBy,Map<String,Object> params,AuthorityParams authorityParams)throws ManagerException;
    
    public BillSalesSum selectSalesSumOtherCount(Map<String,Object> params,AuthorityParams authorityParams)throws ManagerException;
    
    public List<BillSalesSum> selectSalesSumOther(SimplePage page,String orderByField,String orderBy,Map<String,Object> params,AuthorityParams authorityParams)throws ManagerException;
	
    /**
     * 检查有销售但未设置结算期的店铺
     * @param params
     * @return
     * @throws ManagerException
     */
    public List<BillSalesSum> checkShopBalanceDate(Map<String,Object> params) throws ManagerException;
    
    /**
     * 获取前期少估扣费
     * @param shopNo
     * @param invoiceApplyDate
     * @param brandUnitNo
     * @param categoryNo
     * @return
     * @throws ManagerException
     */
    public BigDecimal getPredictionDeductions(String shopNo, Date invoiceApplyDate, String brandUnitNo, String categoryNo) throws ManagerException;

    /**
  	 * 检查有内购销售但未设置内购结算期的公司
  	 * 
  	 * @param params
  	 * @return
  	 * @author wangyj
  	 */
  	public List<BillSalesSum> checkInnerBuyBalanceDate( Map<String, Object> params) throws ManagerException;
      
 

























}