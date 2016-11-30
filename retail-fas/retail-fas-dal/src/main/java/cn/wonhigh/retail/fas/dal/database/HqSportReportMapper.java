package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.HqShipmentCollet;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-13 16:42:47
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
public interface HqSportReportMapper extends BaseCrudMapper {
	
	/**
	 * 根据条件查询总部出货统计表
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
    public List<HqShipmentCollet> findHqShipmentColletByCondition(@Param("params")Map<String,Object> params,@Param("page")SimplePage page);
    
    /**
     * 根据条件查询总部出货统计表的记录数
     * @param params
     * @return
     */
    public HqShipmentCollet findHqShipmentColletCount(@Param("params")Map<String,Object> params);
    
    /**
	 * 根据条件查询退供应商统计表明细
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
    public List<HqShipmentCollet> findRecallSupplierList(@Param("params")Map<String,Object> params,@Param("page")SimplePage page);
    
    /**
     * 根据条件查询退供应商统计表明细的记录数
     * @param params
     * @return
     */
    public HqShipmentCollet findRecallSupplierCount(@Param("params")Map<String,Object> params);
    
    
    /**
	 * 根据条件查询退供应商统计表-退残业务
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
    public List<HqShipmentCollet> findReturnSupplierList(@Param("params")Map<String,Object> params,@Param("page")SimplePage page);
    
    /**
     * 根据条件查询退供应商统计表－退残业务的记录数
     * @param params
     * @return
     */
    public HqShipmentCollet findReturnSupplierCount(@Param("params")Map<String,Object> params);
    
}