package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 批发客户余额明细
 * @author user
 * @date  2016-06-06 16:23:31
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
public interface WholesaleCustomerRemainingDtlMapper extends BaseCrudMapper {
    
    WholesaleCustomerRemainingDtl selectCustomerRemainingTotalByDate(@Param("params") Map<String, Object> params);
	
	void updatePositionOne();
	
	List<WholesaleCustomerRemainingDtl> selectPositionTwo();
	
	List<WholesaleCustomerRemainingDtl> selectPositionThree(@Param("params") Map<String, Object> params);
	
	
	/**
	 * 分页查询客户余额明细
	 * @param SimplePage 分页对象
	 * @param params 查询参数
	 * @return 明细数据集合
	 * @throws Exception 异常
	 */
	List<WholesaleCustomerRemainingDtl> selectDtlByPage(@Param("page") SimplePage page, @Param("params")Map<String,Object> params) ;

	/**
	 * 批量插入客户余额明细
	 * @param params
	 * @return
	 */
	int batchInsertRemainingDtl(@Param("list") List<WholesaleCustomerRemainingDtl> list);
}