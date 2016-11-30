package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途
 * 
 * @author Administrator
 * @date 2015-03-16 12:12:04
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
public interface BillSalesSumMapper extends BaseCrudMapper {
	public <ModelType> List<ModelType> findSalesSum(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public BillSalesSum selectSalesSumPosCount(@Param("page") SimplePage page,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public List<BillSalesSum> selectSalesSumPos(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public BillSalesSum selectSalesSumGmsCount(@Param("page") SimplePage page,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public List<BillSalesSum> selectSalesSumGms(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public BillSalesSum selectSalesSumOtherCount(@Param("page") SimplePage page,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public List<BillSalesSum> selectSalesSumOther(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public <ModelType> List<ModelType> findSaleGoodsGms(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public <ModelType> List<ModelType> selectSubsiInfoList(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	BillSalesSum selectSubsiInfo(Map<String, Object> params);

	/**
	 * 检查有销售但未设置结算期的店铺
	 * 
	 * @param params
	 * @return
	 */
	public <ModelType> List<ModelType> checkShopBalanceDate(@Param("params") Map<String, Object> params);
	
	/**
	 * 检查有内购销售但未设置内购结算期的公司
	 * 
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public <ModelType> List<ModelType> checkInnerBuyBalanceDate(@Param("params") Map<String, Object> params) ;

	/**
	 * 获取前期少估扣费
	 * 
	 * @param shopNo
	 * @param invoiceApplyDate
	 * @param brandUnitNo
	 * @param categoryNo
	 * @return
	 */
	public BigDecimal getPredictionDeductions(@Param("shopNo") String shopNo,
			@Param("invoiceApplyDate") Date invoiceApplyDate, @Param("brandUnitNo") String brandUnitNo,
			@Param("categoryNo") String categoryNo);

	/**
	 * 按照店铺，类别分组获取前期少估扣费
	 * @param shopNo
	 * @param invoiceApplyDate
	 * @param brandUnitNo
	 * @param categoryNo
	 * @return
	 */
	public List<BillSalesSum> getCompanyPredictionDeductions(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("shardingFlag")String shardingFlag);

}