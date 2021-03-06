package cn.wonhigh.retail.fas.dal.database;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.GatherDaysaleSumDto;
import cn.wonhigh.retail.fas.common.model.BillSalesSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-10 10:10:03
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
public interface BillShopBalanceMapper extends BaseCrudMapper {
	
	 <ModelType> int updateInvoiceByBalanceNo(BillShopBalance shopBalance);
	
	/**
	 * 查询当前时间最新结算单号(用于生成结算单号)
	 * @param shopBalance  查询条件
	 * @return String 最新结算单号
	 */
	 String selectShopBalanceMaxBillNo(BillShopBalance shopBalance);
	
	 BillShopBalanceDaysaleSum getSystemSalesAmount(BillShopBalance billShopBalance);
	
//	 BillShopBalanceDaysaleSum getPaymentMethodSum(BillShopBalance billShopBalance);
	 <ModelType> List<ModelType> getPaymentMethodSum(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params,@Param("authorityParams") AuthorityParams authorityParams);
	
     BigDecimal getExpectCashAmount(@Param("params")Map<String, Object> params);
	
	 BigDecimal getBalanceDeductAmount(@Param("params")Map<String, Object> params);
	
	 BigDecimal getBalanceDiffAmount(@Param("params")Map<String, Object> params);
	
	 BigDecimal getPaymentAmount(BillShopBalance billShopBalance);
	
	 BillShopBalanceProSum getProSumSalesAmount(@Param("params")Map<String, Object> params);
	
	 BigDecimal getBalanceDeductDiffAmount(@Param("params")Map<String, Object> params);
	
	 BigDecimal getSumAmount(@Param("params")Map<String,Object> params);	
	
	 String getMaxMonth(BillShopBalance billShopBalance);

	 int hasNextBalanceDate(@Param("params")Map<String, Object> params) throws Exception;

	 BillShopBalance getPerBillShopBalance(@Param("params")Map<String, Object> params) throws Exception;
	 
	 BillShopBalance getBacksectionSplitDeduct(@Param("params")Map<String, Object> params);
	 
	/**
	 * 根据条件查询店铺结算单并统计票后账扣费用
	 * @param params
	 * @return
	 */
	 <ModelType> List<ModelType> selectShopBalanceDeductAfter(@Param("params")Map<String, Object> params);
	 
	 /**
	 * 汇总门店日销售数据
	 * @param params 查询参数
	 * @return 汇总的销售数据对象
	 */
	 GatherDaysaleSumDto gatherDaysaleSum(@Param("params")Map<String, Object> params);
	 
	 BillShopBalanceDaysaleSum getConBaseDeductAmount(@Param("params")Map<String, Object> params);
	 
	 List<BillShopBalance> selectBlanceList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
		
	int	selectBlanceCount(@Param("params")Map<String, Object> params);
	
	BillShopBalanceCateSum getSalesAmount(BillShopBalance billShopBalance);
	
	/**
	 * 根据开票申请号更新开票申请日期
	 * @param shopBalance
	 * @return
	 * @author wang.yj
	 */
	public int updateInvoiceDateByApplyNo(BillShopBalance shopBalance);
	
	List<BillShopBalance> checkBackPayTimeoutList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	int	checkBackPayTimeoutCount(@Param("params")Map<String, Object> params);
	
	List<BillShopBalance> checkMakeInvoiceTimeoutList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	int	checkMakeInvoiceTimeoutCount(@Param("params")Map<String, Object> params);
	
	BigDecimal getNoProSalesSumAmount(@Param("params")Map<String, Object> params);
	
    public int selectSalesResultCount(@Param("params")Map<String, Object> params);
	    
	public List<BillShopBalance> selectSalesResultList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params,@Param("authorityParams") AuthorityParams authorityParams);
		 
	    
	public int selectSalesBackSectionSplitCount(@Param("params")Map<String, Object> params);
	    
    public List<BillShopBalance> selectSalesBackSectionSplitList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params,@Param("authorityParams") AuthorityParams authorityParams);
		 
    List<BillShopBalance> findShopBalanceSalesInfo(@Param("params")Map<String,Object> params);
    
    BigDecimal getSalesAmountBase(@Param("params")Map<String, Object> params);
}