package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ItemReturnRecord;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface ItemReturnManager extends BaseCrudManager {
	
	public ItemReturnRecord findItemReturnCount(Map<String, Object> params) throws ManagerException;
	
	public List<ItemReturnRecord> findItemReturnList(SimplePage page,String orderBy,String orderByField,Map<String, Object> params) throws ManagerException;

	public List<ItemReturnRecord> setItemReturnProperties(List<ItemReturnRecord> list,String companyNo) throws ManagerException;
}
