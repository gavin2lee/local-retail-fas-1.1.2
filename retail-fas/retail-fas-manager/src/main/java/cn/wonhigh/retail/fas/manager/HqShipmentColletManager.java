package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.HqShipmentCollet;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-13 15:20:45
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
public interface HqShipmentColletManager extends BaseCrudManager {
    
	/**
	 * 根据条件查询总部出货统计表
	 * @param params
	 * @param page
	 * @return
	 * @throws ManagerException
	 */
    public List<HqShipmentCollet> findHqShipmentColletByCondition(Map<String,Object> params,SimplePage page) throws ManagerException;
    
    /**
     * 根据条件查询总部出货统计表的记录数
     * @param params
     * @return
     */
    public HqShipmentCollet findHqShipmentColletCount(Map<String,Object> params) throws ManagerException;
    
    /**
   	 * 根据条件查询退供应商统计表明细
   	 * @param params
   	 * @param page
   	 * @return
   	 * @throws ManagerException
   	 */
       public List<HqShipmentCollet> findRecallSupplierList(Map<String,Object> params,SimplePage page) throws ManagerException;
       
       /**
        * 根据条件查询退供应商统计表明细的记录数
        * @param params
        * @return
        */
       public HqShipmentCollet findRecallSupplierCount(Map<String,Object> params)throws ManagerException;
       
       
       /**
   	 * 根据条件查询退供应商统计表汇总
   	 * @param params
   	 * @param page
   	 * @return
   	 * @throws ManagerException
   	 */
       public List<HqShipmentCollet> findReturnSupplierList(Map<String,Object> params,SimplePage page)throws ManagerException;
       
       /**
        * 根据条件查询退供应商统计表汇总的记录数
        * @param params
        * @return
        */
       public HqShipmentCollet findReturnSupplierCount(Map<String,Object> params) throws ManagerException;
}