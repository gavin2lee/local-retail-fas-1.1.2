package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


import cn.wonhigh.retail.fas.common.model.ExpectCash;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-08-26 16:05:20
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
public interface ExpectCashMapper extends BaseCrudMapper {
	/**
	 * 查询预收款单
	 * @Title: selectExpectCashByParams  
	 * @param @param params
	 * @param @return 
	 * @return List<ExpectCash> 
	 * @author: zhou.q1 
	 * @throws
	 */
	public List<ExpectCash> selectExpectCashByParams(@Param("params")Map<String, Object> params) throws ServiceException;
	
	public List<ExpectCash> findExpectCashByParams(@Param("page") SimplePage page, @Param("orderByField") String orderByField, @Param("orderBy") String orderBy, @Param("params")Map<String, Object> params) throws ServiceException;
	
	public int countExpectCashByParams(@Param("params")Map<String, Object> params) throws ServiceException;

   public List<ExpectCash> processExpectCashBalanceDiff(@Param("params")Map<String, Object> params) throws ServiceException;
		
   public List<ExpectCash> processUseExpectCashBalanceDiff(@Param("params")Map<String, Object> params) throws ServiceException;
   
   public ExpectCash getExpectCashAmount(@Param("params")Map<String, Object> params) throws ServiceException;
  
}