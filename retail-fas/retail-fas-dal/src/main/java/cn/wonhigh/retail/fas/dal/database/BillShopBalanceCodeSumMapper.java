package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceCodeSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-06-26 19:11:14
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
public interface BillShopBalanceCodeSumMapper extends BaseCrudMapper {
    public <BillShopBalanceCodeSum> int deleteBalanceNoForModel(BillShopBalanceCodeSum  billShopBalanceCodeSum);
	
	public BillShopBalanceCodeSum getSumAmount(@Param("params")Map<String,Object> params) throws ManagerException, ServiceException;
	
}