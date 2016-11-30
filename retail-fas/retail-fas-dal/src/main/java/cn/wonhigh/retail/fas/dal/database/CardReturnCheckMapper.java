package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.CardReturnCheck;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-19 20:33:19
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
public interface CardReturnCheckMapper extends BaseCrudMapper {
	/**
	 * 根据查询条件，查询银行卡退款核对总数
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public CardReturnCheck findCardReturnCheckCount(@Param("params")Map<String, Object> params) throws ManagerException;
	
	/**
	 * 根据查询条件，查询银行卡退款核对明细
	 * @param params
	 * @return
	 * @author wangsm
	 */
	public List<CardReturnCheck> findCardReturnCheckList(@Param("page")SimplePage page,@Param("orderByField")String orderByField, @Param("orderBy")String orderBy, @Param("params")Map<String, Object> params)throws ManagerException;

	/**
	 * 手动修改手续费以后，保存
	 * 
	 */
	public void updatePoundage(@Param("cardReturnCheck")CardReturnCheck cardReturnCheck);
}