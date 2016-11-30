package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.GatherDaysaleSumDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-04 17:20:02
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
public interface BillShopBalanceService extends BaseCrudService {
	
	int delete(String[] ids);
	
	BillShopBalanceDaysaleSum getSystemSalesAmount(BillShopBalance billShopBalance);
	
	List<BillShopBalanceDaysaleSum> getPaymentMethodSum(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	BigDecimal getExpectCashAmount(Map<String,Object> params);
	
	BigDecimal getBalanceDeductAmount(Map<String, Object> params);
	
	BigDecimal getBalanceDiffAmount(Map<String, Object> params);
	
	BigDecimal getPaymentAmount(BillShopBalance billShopBalance);
	
	int updateInvoiceByBalanceNo(BillShopBalance shopBalance);
	
	BillShopBalanceProSum getProSumSalesAmount(Map<String, Object> params);
	
	BigDecimal getBalanceDeductDiffAmount(Map<String, Object> params);
	
	BigDecimal getSumAmount(Map<String,Object> params);	
	
	String getMaxMonth(BillShopBalance billShopBalance);
	
	int hasNextBalanceDate(Map<String, Object> params) throws ServiceException;

	BillShopBalance getPerBillShopBalance(Map<String, Object> params) throws ServiceException;
	
	BillShopBalance getBacksectionSplitDeduct(Map<String, Object> params);
	
	/**
	 * 根据条件查询店铺结算单并统计票后账扣费用
	 * @param params
	 * @return
	 */
	List<BillShopBalance> findShopBalanceDeductAfter(Map<String, Object> params);
	
	/***********************************  yangyong   *******************************************/
	/**
	 * 汇总门店日销售数据
	 * @param params 查询参数
	 * @return 汇总的销售数据对象
	 */
	GatherDaysaleSumDto gatherDaysaleSum(Map<String, Object> params);
	
	BillShopBalanceDaysaleSum getConBaseDeductAmount(Map<String, Object> params);
	
    List<BillShopBalance> selectBlanceList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	int	selectBlanceCount(Map<String,Object> params);
	
	BillShopBalanceCateSum getSalesAmount(BillShopBalance billShopBalance);
	
	/**
	 * 根据开票申请号更新开票申请日期
	 * @param shopBalance
	 * @return
	 * @author wang.yj
	 */
	public int updateInvoiceDateByApplyNo(BillShopBalance shopBalance);
	
	
	List<BillShopBalance> checkBackPayTimeoutList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	int checkBackPayTimeoutCount(Map<String,Object> params);
	
	List<BillShopBalance> checkMakeInvoiceTimeoutList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	int checkMakeInvoiceTimeoutCount(Map<String,Object> params);
	
	BigDecimal getNoProSalesSumAmount(Map<String,Object> params);	
	
	List<BillShopBalance> selectSalesResultList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
		
	int	selectSalesResultCount(Map<String,Object> params);
		
	List<BillShopBalance> selectSalesBackSectionSplitList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
			
	int	selectSalesBackSectionSplitCount(Map<String,Object> params);
	
	List<BillShopBalance> findShopBalanceSalesInfo(Map<String, Object> params);
	
	BigDecimal getSalesAmount(Map<String,Object> params);	
}