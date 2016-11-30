package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingOccurDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSendaDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleCustomerRemainingSumDto;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 批发客户余额
 * @author user
 * @date  2016-06-06 17:11:35
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
public interface WholesaleCustomerRemainingSumMapper extends BaseCrudMapper {
		
	WholesaleCustomerRemainingSumDto selectCalcPaidAmountByParams(@Param("params") Map<String, Object> params);
	
	WholesaleCustomerRemainingSumDto selectCalcSendAmountByParams(@Param("params") Map<String, Object> params);
	
	/**
	 * 查询客户余额
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<WholesaleCustomerRemainingSumDto> findCustomerRemainningByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	/**
	 * 查询森达客户余额明细
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<WholesaleCustomerRemainingSendaDto> findSendaListByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	/**
	 * 查询客户品牌部
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<WholesaleCustomerRemainingSumDto> findBrandsByCustomerNos(@Param("list") List<String> customerNos);
	
	/**
	 * 客户余额发生查询
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	List<WholesaleCustomerRemainingOccurDto> findCustomerRemainningOccurByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params") Map<String, Object> params);
	
	/**
	 * 根据客户编号修改余额
	 * @return
	 */
	int updateByCustomerNo(@Param("params") Map<String, Object> params);
	
	/**
	 * 查询森达客户余额明细总条数
	 * @return
	 */
	int findSendaCount(@Param("params") Map<String, Object> params);
	
	

}