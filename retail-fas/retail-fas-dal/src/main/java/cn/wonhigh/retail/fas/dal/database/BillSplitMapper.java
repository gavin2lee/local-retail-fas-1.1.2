package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSplit;

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
public interface BillSplitMapper extends BaseCrudMapper {

	public int batchAdd(@Param("list")List<BillSplit> list);
	
	/**
	 * 查询需要拆单的单据总数
	 * @param params
	 * @return
	 */
	int selectSplitBillCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 查询需要拆单的单据明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<BillBuyBalance> selectSplitBill(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
	
	/**
	 * 查询结算路径
	 * @param params
	 * @return
	 */
	List<SettlePathDto> selectSettlePath(@Param("params")Map<String,Object> params);
	
	/**
	 * 查询buy表已结算的单据数量
	 * @param params
	 * @return
	 */
	int selectBuySettleCount(@Param("params")Map<String,Object> params);
	
	/**
	 * 查询sale表已结算的单据数量
	 * @param params
	 * @return
	 */
	int selectSaleSettleCount(@Param("params")Map<String,Object> params);
}