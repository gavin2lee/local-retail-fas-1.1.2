package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;


import cn.wonhigh.retail.fas.common.dto.TransferingCheckDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface TransferingCheckManager extends BaseCrudManager {
	/**
	 * 查询收发货在途核对汇总合计
	 * @param params
	 * @return
	 */
	public List<TransferingCheckDto> selectTransferingCheckGatherFooter(SimplePage page,Map<String, Object> params)throws ManagerException;
	
	/**
	 * 查询收发货在途核对明细合计
	 * @param params
	 * @return
	 */
	public List<TransferingCheckDto> selectTransferingCheckDtlFooter(SimplePage page,Map<String, Object> params)throws ManagerException;
	
	/**
	 * 查询收发货在途核对明细数量
	 * @param shopNoList, ocOrderParameterDto
	 * @return
	 * @throws RpcException
	 */
	public int selectTransferingCheckDtlCount(Map<String, Object> params)throws ManagerException;

	/**
	 * 查询收发货在途核对明细
	 * @param shopNoList, ocOrderParameterDto
	 * @return
	 * @throws RpcException
	 */
	public List<TransferingCheckDto> selectTransferingCheckDtl(SimplePage page, String sortColumn, String sortOrder,Map<String, Object> params)throws ManagerException;
	

	/**
	 * 查询收发货在途核对汇总
	 * @param shopNoList, ocOrderParameterDto
	 * @return
	 * @throws RpcException
	 */
	public List<TransferingCheckDto> selectTransferingCheckTotal(SimplePage page, String sortColumn, String sortOrder,Map<String, Object> params) throws ManagerException;

	/**
	 * 查询体育收发货明细数量
	 * @param params
	 * @return
	 */
	public int findReceiveAndSendDtlCount(Map<String, Object> params);

	/**
	 * 查询体育收发货明细
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public List<TransferingCheckDto> findReceiveAndSendDtl(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * 查询体育收发货明细合计
	 * @param object
	 * @param params
	 * @return
	 */
	public List<TransferingCheckDto> findReceiveAndSendDtlFooter(Object object,
			Map<String, Object> params);

	/**
	 * 查询体育收发货汇总数量
	 * @param params
	 * @return
	 */
	public int findReceiveAndSendSumCount(Map<String, Object> params);

	/**
	 * 查询体育收发货汇总
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public List<TransferingCheckDto> findReceiveAndSendSum(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params);

	/**
	 * 查询体育收发货汇总合计
	 * @param object
	 * @param params
	 * @return
	 */
	public List<TransferingCheckDto> findReceiveAndSendSumFooter(Object object,
			Map<String, Object> params);
}