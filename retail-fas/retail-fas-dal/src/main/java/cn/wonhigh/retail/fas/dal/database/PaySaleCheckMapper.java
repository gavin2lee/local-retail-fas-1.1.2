package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.PaySaleCheck;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-18 10:55:57
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface PaySaleCheckMapper extends BaseCrudMapper {
	/**
	 * 根据查询条件，查询支付方式销售核对总数
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public PaySaleCheck findPaySaleCheckCount(@Param("params")Map<String, Object> params) throws ManagerException;
	
	/**
	 * 根据查询条件，查询支付方式销售核对明细
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public List<PaySaleCheck> findPaySaleCheckList(@Param("page")SimplePage page,@Param("orderByField")String orderByField, @Param("orderBy")String orderBy, @Param("params")Map<String, Object> params)throws ManagerException;

	/**
	 * 修改手续费
	 * @param id
	 * @param poundage
	 */
	public void updateData(@Param("id")String id,@Param("poundage")BigDecimal poundage, @Param("rate")BigDecimal rate);
}