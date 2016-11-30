package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface BillSalesSumService extends BaseCrudService {
	
	public <ModelType> List<ModelType> findSalesSum(SimplePage page,String orderByField,String orderBy,Map<String,Object> params)throws ServiceException;
	
	public <ModelType> List<ModelType> findSaleGoodsGms(SimplePage page,String orderByField,String orderBy,Map<String,Object> params)throws ServiceException;
	
    List<BillSalesSum> selectSubsiInfoList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params)throws ServiceException;
	
    BillSalesSum selectSubsiInfo(Map<String,Object> params);
    
    
    public BillSalesSum selectSalesSumPosCount(Map<String,Object> params,AuthorityParams authorityParams)throws ServiceException;
    
    public List<BillSalesSum> selectSalesSumPos(SimplePage page,String orderByField,String orderBy,Map<String,Object> params,AuthorityParams authorityParams)throws ServiceException;
	    
	public BillSalesSum selectSalesSumGmsCount(Map<String,Object> params,AuthorityParams authorityParams)throws ServiceException;
	    
    public List<BillSalesSum> selectSalesSumGms(SimplePage page,String orderByField,String orderBy,Map<String,Object> params,AuthorityParams authorityParams)throws ServiceException;
    
    public BillSalesSum selectSalesSumOtherCount(Map<String,Object> params,AuthorityParams authorityParams)throws ServiceException;
    
    public List<BillSalesSum> selectSalesSumOther(SimplePage page,String orderByField,String orderBy,Map<String,Object> params,AuthorityParams authorityParams)throws ServiceException;
	
    /**
     * 检查有销售但未设置结算期的店铺
     * @param params
     * @return
     * @throws ServiceException
     */
    public <ModelType> List<ModelType> checkShopBalanceDate(Map<String,Object> params)throws ServiceException;
    
    /**
	 * 检查有内购销售但未设置内购结算期的公司
	 * 
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public <ModelType> List<ModelType> checkInnerBuyBalanceDate( Map<String, Object> params) throws ServiceException;
    
    /**
     * 获取前期少估扣费
     * @param shopNo
     * @param invoiceApplyDate
     * @param brandUnitNo
     * @param categoryNo
     * @return
     */
    public BigDecimal getPredictionDeductions(String shopNo, Date invoiceApplyDate, String brandUnitNo, String categoryNo) throws ServiceException;
 
}