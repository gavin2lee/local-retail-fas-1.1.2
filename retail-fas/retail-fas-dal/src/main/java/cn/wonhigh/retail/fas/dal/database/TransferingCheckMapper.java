package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.TransferingCheckDto;
import org.apache.ibatis.annotations.Param;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 收发货在途对账报表
 * @author ning.ly
 * @date  2015-04-24 10:10:28
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
public interface TransferingCheckMapper extends BaseCrudMapper {
	
	/**
	 * 查询收发货在途核对汇总合计
	 * @param params
	 * @return
	 */
	public List<TransferingCheckDto> selectTransferingCheckGatherFooter(@Param("page") SimplePage page,@Param("params")Map<String, Object> params);
	
	/**
	 * 查询收发货在途核对明细合计
	 * @param params
	 * @return
	 */
	public List<TransferingCheckDto> selectTransferingCheckDtlFooter(@Param("page") SimplePage page,@Param("params")Map<String, Object> params);

	/**
	 * 查询收发货在途核对明细
	 * @param shopNoList, ocOrderParameterDto
	 * @return
	 * @throws RpcException
	 */
	public List<TransferingCheckDto> selectTransferingCheckDtl(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	

	/**
	 * 查询收发货在途核对汇总
	 * @param shopNoList, ocOrderParameterDto
	 * @return
	 * @throws RpcException
	 */
	public List<TransferingCheckDto> selectTransferingCheckTotal(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	

	/**
	 * 查询收发货在途核对明细数量
	 * @param shopNoList, ocOrderParameterDto
	 * @return
	 * @throws RpcException
	 */
	public int selectTransferingCheckDtlCount(@Param("params")Map<String, Object> params);
}