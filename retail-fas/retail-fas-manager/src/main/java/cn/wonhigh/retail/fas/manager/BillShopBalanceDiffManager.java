package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffExport;
import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillShopBalanceDiffManager extends BaseCrudManager {
	
    BigDecimal getDiffBalanceSum(BillShopBalanceDiff  billShopBalanceDiff)  throws ManagerException;;
	
	BigDecimal getDiffBackAmountSum(BillShopBalanceDiff  billShopBalanceDiff)  throws ManagerException;;
	
	BillShopBalanceDiff getSumAmount(Map<String,Object> params) throws ManagerException;

	int findDiffTrackCount(Map<String, Object> params) throws ManagerException;

	List<BillShopBalanceDiffExport> findDiffTrackPage(SimplePage page, String orderByField,String orderBy, 
			Map<String, Object> params) throws ManagerException;
	
	/**
	 * 获取页脚汇总数据
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	BillShopBalanceDiffFooterDto getFooterDto(Map<String, Object> params);
	
	List<BillShopBalanceDiffExport> findDiffTrackExport(Map<String, Object> params) throws ManagerException;
	
	int selectlistSearchCount(Map<String, Object> params) throws ManagerException;

	List<BillShopBalanceDiff> selectlistSearchByPage(SimplePage page, String orderByField,String orderBy, 
			Map<String, Object> params) throws ManagerException;
	
	
	
	int selectlistDiffTrackCount(Map<String, Object> params) throws ManagerException;

	List<BillShopBalanceDiff> selectlistDiffTrackByPage(SimplePage page, String orderByField,String orderBy, 
			Map<String, Object> params) throws ManagerException;
	
}