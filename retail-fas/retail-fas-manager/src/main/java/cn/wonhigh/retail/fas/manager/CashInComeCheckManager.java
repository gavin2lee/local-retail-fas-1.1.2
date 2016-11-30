package cn.wonhigh.retail.fas.manager;

import java.util.Map;

import com.yougou.logistics.base.common.exception.ManagerException;

public interface CashInComeCheckManager {
	
	public Map<String,Object> queryList(Map<String,Object> params) throws ManagerException;
}
