package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import cn.wonhigh.retail.fas.common.dto.MPSPaymentDto;
import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.IndependShopDayReport;
import cn.wonhigh.retail.fas.common.model.POSOcGroupOrderPayway;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface ShopDayReportManager extends BaseCrudManager {
	
	public List<IndependShopDayReport> queryEachDayReport(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;

	public List<MPSPaymentDto> queryPaymentsList() throws ManagerException;
	
	public int  findCountReport(Map<String,Object> params) throws ManagerException;
	
	public List<IndependShopDayReport> queryEachDayReportLocal(Map<String,Object> params) throws ManagerException;

	/**
	 * 商场门店日报表查询总数
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int findShopDailyReportCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 商场门店日报表查询列表数据
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<POSOcGroupOrderPayway> findShopDailyReportByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;
	
	/**
	 * 商场门店日报表查询列表数据
	 * @param params
	 * @return
	 * @throws ManagerException
	 */	
	@Cacheable(value = "shopDayReport", key = "#params")
	public List<SaleOrderPayway> findShopDayReportByPage(
				String sortColumn, String sortOrder,
				Map<String, Object> params) throws ManagerException;
	
	/**
	 * 商场门店日报表分品牌查询列表数据
	 * @param params
	 * @return
	 * @throws ManagerException
	 */	
	@Cacheable(value = "shopDayReportForBrand")
	public List<SaleOrderPayway> findSaleDayReportForBrandByPage(
				String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException;
	
	/**
	 * 商场门店日报表查询列表数据合计对象
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public SaleOrderPayway findShopDailyReportShowCount(Map<String, Object> params);
	
	/**
	 * 分品牌查询店铺销售日报表 总计-list
	 * @param params
	 * @return
	 */
	public SaleOrderPayway findSaleDayReportForBrandCount(Map<String, Object> params);

	/**
	 *  查询日报表所有记录
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	@Cacheable(value = "dayReport")
	public List<Map<String, Object>> findList(Map<String, Object> params) throws ManagerException;

	/**
	 * 分品牌查询日报表所有记录
	 * @param param
	 * @return
	 * @throws ManagerException 
	 */
	@Cacheable(value = "dayReportForBrand")
	public List<Map<String, Object>> findDayReportForBrandList(Map<String, Object> params) throws ManagerException;
}
