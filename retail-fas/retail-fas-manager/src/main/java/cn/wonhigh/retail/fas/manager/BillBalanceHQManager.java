package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillBalanceDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.CustomImperfect;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途
 * 
 * @author wang.m1
 * @date 2014-09-05 10:33:45
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface BillBalanceHQManager extends BaseCrudManager {
	/**
	 * 根据生成结算单条件生成结算单
	 * 
	 * @param BillBalanceDto
	 *            结算单对象
	 * @return BillBalanceDto 返回生成的结算单
	 * @throws ManagerException
	 */
	public BillBalance addBill(BillBalance billBalance, Map<String, Object> params) throws ManagerException;

	/**
	 * 查询结算汇总表数量
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectBalanceGatherCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询结算汇总表集合
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalanceDto> selectBalanceGatherList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;

	/**
	 * 查询结算汇总列表页脚合计行
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalanceDto> selectBalanceGatherFooter(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询结算单列表数量
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectBalanceCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询结算单列表集合
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalance> selectBalanceByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;

	/**
	 * 查询结算单列表页脚合计行
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalance> selectBalanceFooter(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询入库明细/出库明细数量
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectEnterCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询入库明细/出库明细集合
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Object> selectEnterList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException;

	/**
	 * 查询明细列表页脚合计行
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Object> selectEnterFooter(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询明细汇总数量
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public int selectItemGatherCount(Map<String, Object> params) throws ManagerException;

	/**
	 * 查询明细汇总集合
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public List<Object> selectItemGatherList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException;

	/**
	 * 查询明细汇总列表页脚合计行
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Object> selectItemGatherFooter(Map<String, Object> params) throws ManagerException;

	/**
	 * 获取结算金额
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public BillBalance getBalanceAmount(Map<String, Object> params) throws ManagerException;

	/**
	 * 获取结算金额调整扣项
	 * 
	 * @param obj
	 *            结算单对象
	 * @param lstItem
	 *            扣项集合
	 * @return
	 * @throws ManagerException
	 */
	public BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem) throws ManagerException;

	/**
	 * 选单新增结算单
	 * 
	 * @param obj
	 *            结算单对象
	 * @param lstItem
	 *            选择的业务单据集合
	 * @return
	 * @throws ManagerException
	 */
	public BillBalance createBalance(BillBalance obj, List<Object> lstItem) throws ManagerException;

	/**
	 * 汇总总部结算单状态
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalance> selectHqCount() throws ManagerException;

	/**
	 * 结算调整
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public BillBalance balanceAdjust(BillBalance obj, List<OtherDeduction> lstDeduction,
			List<CustomImperfect> lstImperfect) throws ManagerException;

	/**
	 * 结算前处理操作
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, Object> handleBeforeBalance(Map<String, Object> params) throws ManagerException;

	/**
	 * 更新异常价格
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int updateExceptionPrice(Map<String, Object> params) throws ManagerException;

	/**
	 * 导出
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public void findExportList(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException;

	/**
	 * 获取巴洛克验收单
	 * 
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	public List<BillBalance> selectBalanceForBaroque(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params) throws ManagerException;
}