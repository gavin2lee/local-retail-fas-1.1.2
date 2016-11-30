package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
public interface DataHqAreaExchangeMapper extends BaseCrudMapper {

	/**
	 * 查询需要对调的跨区调货单总数
	 * @param params
	 * @return
	 */
	int selectSaleTransferBillCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 查询需要对调的跨区调货单明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectSaleTransferBill(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	/**
	 * 查询需要对调的跨区调货单总数
	 * @param params
	 * @return
	 */
	int selectBuyTransferBillCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 查询需要对调的跨区调货单明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectBuyTransferBill(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	/**
	 * 对调Sale表调货出库单
	 * @param billSaleBalance
	 * @return
	 */
	int updateSaleTransferBill(BillSaleBalance billSaleBalance);
	
	/**
	 * 对调buy表调货入库单
	 * @param billBuyBalance
	 * @return
	 */
	int updateBuyTransferBill(BillBuyBalance billBuyBalance);

	/**
	 * 查询合计行
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectTotalRow(@Param("params")Map<String, Object> params);

	/**
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectBuyTotalRow(@Param("params")Map<String, Object> params);
}