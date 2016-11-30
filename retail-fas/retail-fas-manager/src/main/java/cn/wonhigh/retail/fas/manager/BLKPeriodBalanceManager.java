package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface BLKPeriodBalanceManager extends BaseCrudManager {

	/**
	 * 按照小计方式查询巴洛克-公司期间结存总计对象
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	BLKPeriodBalance findBLKPeriodBalanceSubTotalCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 按照小计方式查询巴洛克-公司期间结存明细
	 * @param page
	 * @param orderBy
	 * @param orderByField
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	List<BLKPeriodBalance> findBLKPeriodBalanceSubTotalPages(SimplePage page, String orderBy, String orderByfield, Map<String, Object> params) throws ManagerException;

	/**
	 * 查询巴洛克-公司期间结存总计对象
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	BLKPeriodBalance findBLKPeriodBalanceCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询巴洛克-公司期间结存明细
	 * @param page
	 * @param orderBy
	 * @param orderByfield
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	List<BLKPeriodBalance> findBLKPeriodBalancePages(SimplePage page, String orderBy, String orderByfield, Map<String, Object> params) throws ManagerException;

	/**
	 * 从主数据缓存读取相信息
	 * @param list
	 * @return
	 */
	List<BLKPeriodBalance> setExtendsPeriodBalanceProperties(List<BLKPeriodBalance> list);

	/**
	 * 巴洛克- 查询机构店铺结存总计
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	int selectStoreBanalceCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 巴洛克-查询机构店铺结存明细
	 * @param page
	 * @param object
	 * @param object2
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	List<BLKPeriodBalance> selectStoreBalanceList(SimplePage page, String orderBy, String orderByField, Map<String, Object> params) throws ManagerException;

	/**
	 * 店铺结存报表设置额外属性
	 * @param list
	 * @return
	 */
	List<BLKPeriodBalance> setExtendsProperties(List<BLKPeriodBalance> list);

}
