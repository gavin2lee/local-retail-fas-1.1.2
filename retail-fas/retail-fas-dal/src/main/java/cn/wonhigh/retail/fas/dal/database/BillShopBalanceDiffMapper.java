package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffExport;
import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffFooterDto;
import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDiffDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-12-23 17:24:40
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
public interface BillShopBalanceDiffMapper extends BaseCrudMapper {
	
	public <ModelType> int deleteBalanceNoForModel(ModelType record);
	
	public BigDecimal getDiffBalanceSum(BillShopBalanceDiff  billShopBalanceDiff);
	
	public BigDecimal getDiffBackAmountSum(BillShopBalanceDiff  billShopBalanceDiff);
	
	public List<BillShopBalanceDiff>  getSumAmount(@Param("params")Map<String,Object> params);

	public int findDiffTrackCount(@Param("params")Map<String, Object> params) throws Exception;

	public List<BillShopBalanceDiffExport> findDiffTrackPage(@Param("page")SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);

	/**
	 * 汇总结算差异金额数据
	 * @param params 参数
	 * @return 结算差异数据汇总对象
	 */
	public GatherBillShopBalanceDiffDto gatherBalanceDiff(@Param("params")Map<String, Object> params);

	/**
	 * 获取页脚汇总数据
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	public List<BillShopBalanceDiffFooterDto> getFooterDto(@Param("params")Map<String, Object> params);

	/**
	 * 修改结算差异的状态
	 * @param params 限制参数
	 * @return 影响的行数
	 * @throws Exception 异常
	 */
	public int modifyStatus(@Param("params")Map<String, Object> params) throws Exception;
	
	/**
	 * 结算单跟踪差异和明细
	 * @param params
	 * @return
	 */
	public List<BillShopBalanceDiffExport> findDiffTrackExport(@Param("params")Map<String, Object> params);
	
	public int selectlistSearchCount(@Param("params")Map<String, Object> params) throws Exception;

	public List<BillShopBalanceDiff> selectlistSearchByPage(@Param("page")SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
	
	public int selectlistDiffTrackCount(@Param("params")Map<String, Object> params) throws Exception;

	public List<BillShopBalanceDiff> selectlistDiffTrackByPage(@Param("page")SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params")Map<String, Object> params);
	
}