package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillBalanceDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author wang.m1
 * @date 2014-09-05 14:50:55
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
public interface BillBalanceHqMapper {

	/**
	 * 总部厂商结算 -查询汇总数量(功能模块-结算单汇总表)
	 * 
	 * @param params
	 *            查询条件
	 * @return int 汇总后的数量
	 */
	public int selectHqBuyBalanceGatherCount(@Param("params") Map<String, Object> params);

	/**
	 * 总部厂商结算-查询拆分单据汇总集合(功能模块-结算单汇总表)
	 * 
	 * @param params
	 *            查询条件
	 * @return int 汇总后的数量
	 */
	public List<BillBalanceDto> selectHqBuyBalanceGatherList(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * 总部批发结算-查询汇总数量(功能模块-结算单汇总表)
	 * 
	 * @param params
	 *            查询条件
	 * @return int 汇总后的数量
	 */
	public int selectHqSaleBalanceGatherCount(@Param("params") Map<String, Object> params);

	/**
	 * 总部批发结算-查询汇总集合(功能模块-结算单汇总表)
	 * 
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<BillBalanceDto> selectHqSaleBalanceGatherList(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * 总部厂商结算-查询到货单拆分汇总结算单集合(用于生成结算单)
	 * 
	 * @param Map
	 * @return list 结算单集合
	 */
	public List<BillBalance> selectHqBuyGather(@Param("params") Map<String, Object> params);

	/**
	 * 总部批发结算-查询总部批发结算单集合(用于生成结算单)
	 * 
	 * @param Map
	 * @return list 结算单集合
	 */
	public List<BillBalance> selectHqSaleGather(@Param("params") Map<String, Object> params);

	/**
	 * 地区采购结算-查询入库明细列表
	 * 
	 * @param Map
	 * @return list 结算单集合
	 */
	public int selectAreaGatherCount(@Param("params") Map<String, Object> params);

	/**
	 * 地区采购结算-查询入库明细汇总列表
	 * 
	 * @param Map
	 * @return list 结算单集合
	 */
	public List<BillBalanceDto> selectAreaGatherList(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * 查询结算单列表数量
	 * 
	 * @param params
	 * @return
	 */
	public int selectBalanceCount(@Param("params") Map<String, Object> params);

	/**
	 * 查询结算单列表集合
	 * 
	 * @param params
	 * @return
	 */
	public List<BillBalance> selectBalanceByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * 查询入库明细汇总数量
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public int selectBuyItemGatherCount(@Param("params") Map<String, Object> params);

	/**
	 * 查询出库明细汇总数量
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public int selectSaleItemGatherCount(@Param("params") Map<String, Object> params);

	/**
	 * 查询入库明细汇总集合
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public List<Object> selectBuyItemGatherList(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * 查询出库明细汇总集合
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public List<Object> selectSaleItemGatherList(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	public List<BillBalance> selectBalanceFooter(@Param("params") Map<String, Object> params);

	public List<BillBalanceDto> selectHqBuyBalanceGatherFooter(@Param("params") Map<String, Object> params);

	public List<BillBalanceDto> selectHqSaleBalanceGatherFooter(@Param("params") Map<String, Object> params);

	public List<BillBalanceDto> selectAreaGatherFooter(@Param("params") Map<String, Object> params);

	public List<Object> selectItemGatherGaher(@Param("params") Map<String, Object> params);

	public List<Object> selectBuyItemGatherFooter(@Param("params") Map<String, Object> params);

	public List<Object> selectSaleItemGatherFooter(@Param("params") Map<String, Object> params);

	/**
	 * 汇总总部结算单状态
	 * 
	 * @param Map
	 * @return list 结算单集合
	 */
	public List<BillBalance> selectHqCount();

	public int updateBuyBalancePrice(@Param("params") Map<String, Object> params);

	public int updateSaleBalancePrice(@Param("params") Map<String, Object> params);

	public int updateSaleBalancePriceForTransfer(@Param("params") Map<String, Object> params);

	public int queryBuyExcessPriceBillCount(@Param("params") Map<String, Object> params);

	public int queryBuyZeroPriceBillCount(@Param("params") Map<String, Object> params);

	public int querySaleZeroPriceBillCount(@Param("params") Map<String, Object> params);

	public List<BillBalance> selectBalanceForBaroque(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	public List<BillBuyBalance> selectBuyBalanceExpricePrice(@Param("params") Map<String, Object> params);

	public List<BillSaleBalance> selectSaleBalanceExpricePrice(@Param("params") Map<String, Object> params);

	public List<BillSaleBalance> selectSaleBalancePriceForTransfer(@Param("params") Map<String, Object> params);
}