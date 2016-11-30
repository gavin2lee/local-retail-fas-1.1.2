package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffExport;
import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffFooterDto;
import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDiffDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-15 10:52:13
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
public interface BillShopBalanceDiffService extends BaseCrudService {
	
	BigDecimal getDiffBalanceSum(BillShopBalanceDiff  billShopBalanceDiff);
	
	BigDecimal getDiffBackAmountSum(BillShopBalanceDiff  billShopBalanceDiff);
	
	List<BillShopBalanceDiff> getSumAmount(Map<String,Object> params) throws ServiceException;

	int findDiffTrackCount(Map<String, Object> params) throws ServiceException;

	List<BillShopBalanceDiffExport> findDiffTrackPage(SimplePage page,
			String orderByField,String orderBy, Map<String, Object> params) throws ServiceException;
	
	int selectlistSearchCount(Map<String, Object> params) throws ServiceException;

	List<BillShopBalanceDiff> selectlistSearchByPage(SimplePage page, String orderByField,String orderBy, 
			Map<String, Object> params) throws ServiceException;
	
	int selectlistDiffTrackCount(Map<String, Object> params) throws ServiceException;

	List<BillShopBalanceDiff> selectlistDiffTrackByPage(SimplePage page, String orderByField,String orderBy, 
			Map<String, Object> params) throws ServiceException;

	/**
	 * 汇总结算差异金额数据
	 * @param params 参数
	 * @return 结算差异数据汇总对象
	 */
	GatherBillShopBalanceDiffDto gatherBalanceDiff(Map<String, Object> params);

	/**
	 * 获取页脚汇总数据
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	BillShopBalanceDiffFooterDto getFooterDto(Map<String, Object> params);
	
	/**
	 * 修改结算差异的状态
	 * @param params 限制参数
	 * @return 影响的行数
	 * @throws ServiceException 异常
	 */
	int modifyStatus(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 结算单跟踪差异和明细
	 * @param params
	 * @return
	 */
	List<BillShopBalanceDiffExport> findDiffTrackExport(Map<String, Object> params)throws ServiceException;
	
}