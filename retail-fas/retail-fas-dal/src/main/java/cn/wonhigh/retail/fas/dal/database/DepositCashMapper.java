package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.DepositCheck;

import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 13:51:56
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
public interface DepositCashMapper extends BaseCrudMapper {
	/**
	 * 根据查询条件，查询pos 存现核对明细存入金额总数和期间销售金额总数
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public DepositCash findDetailCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据查询条件，查询pos 存现核对明细
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public List<DepositCash> findDetail(@Param("page")SimplePage page,@Param("orderByField")String orderByField,@Param("orderBy")String orderBy,@Param("params")Map<String, Object> params);
		
	public BigDecimal getExistAmount(@Param("params")Map<String, Object> params);
	
	public List<DepositCash> getPaidinAmount(@Param("params")Map<String, Object> params);
	
	public BigDecimal getPaidinAmountCount(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据参数查询各店铺最近一次存现时间
	 * @param params
	 * @return
	 */
	public List<DepositCash> findLastDepositDate(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询店铺超出最近一次存现的天数（现金销售为0不统计）
	 * @return
	 */
	public DepositCheck findBeyondDates(@Param("params")Map<String, Object> params);
}