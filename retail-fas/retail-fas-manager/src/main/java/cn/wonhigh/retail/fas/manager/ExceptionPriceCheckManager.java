package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface ExceptionPriceCheckManager extends BaseCrudManager{
	
	//操作GMS表
	boolean checkExceptionPrice(Map<String,Object> params) throws ManagerException;
	
	public Map<String,Object> queryPriceExceptionBillList(Map<String,Object> params) throws ManagerException;
	
	public boolean updatePriceExceptionBillList(Map<String,Object> params) throws ManagerException;
	
}	
