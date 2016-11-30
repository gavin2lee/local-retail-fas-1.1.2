package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillBalanceDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.CustomImperfect;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface BillBalanceHQService extends BaseCrudService {

	/**
	 * 根据生成条件查询结算信息 (并按结算主体、供应商、品牌进行分组得出汇总金额信息)
	 * 
	 * @param params
	 *            查询条件
	 * @return List<BillBalanceDto> 结算单集合
	 * @throws ServiceException
	 */
	public List<BillBalance> queryBalanceList(Map<String, Object> params) throws ServiceException;

	/**
	 * 保存结算单信息
	 * 
	 * @param lstBillBalanceDto
	 *            需要保存的结算单集合
	 * @param BillBalance
	 *            结算单生成条件(用于初始化结算单基础信息、如结算单号、等等)
	 * @throws ServiceException
	 */
	public void saveBalanceList(List<BillBalance> lstBillBalance, BillBalance billBalance) throws ServiceException;

	/**
	 * 查询结算汇总表数量
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int selectBalanceGatherCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询结算汇总表集合
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public List<BillBalanceDto> selectBalanceGatherList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;

	/**
	 * 查询结算汇总列表页脚合计行
	 * 
	 * @param params
	 *            查询条件
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalanceDto> selectBalanceGatherFooter(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询结算单列表数量
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectBalanceCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询结算单列表集合
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalance> selectBalanceByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;

	/**
	 * 查询结算单列表页脚合计行
	 * 
	 * @param params
	 *            查询条件
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalance> selectBalanceFooter(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询入库明细/出库明细数量
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public int selectEnterCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询入库明细/出库明细集合
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<Object> selectEnterList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException;

	/**
	 * 查询明细列表页脚合计行
	 * 
	 * @param params
	 *            查询条件
	 * @return
	 * @throws ManagerException
	 */
	public List<Object> selectEnterFooter(Map<String, Object> params) throws ServiceException;

	/**
	 * 查询明细汇总数量
	 * 
	 * @param page
	 * @param sortColumn
	 * @param sortOrder
	 * @param params
	 * @return
	 */
	public int selectItemGatherCount(Map<String, Object> params) throws ServiceException;

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
			Map<String, Object> params) throws ServiceException;

	/**
	 * 查询明细汇总汇总列表页脚合计行
	 * 
	 * @param params
	 *            查询条件
	 * @return
	 * @throws ManagerException
	 */
	public List<Object> selectItemGatherFooter(Map<String, Object> params) throws ServiceException;

	/**
	 * 扣项调整
	 * 
	 * @param obj
	 *            结算单
	 * @param lstItem
	 *            扣项集合
	 * @return 调整后的结算单
	 * @throws ServiceException
	 */
	public BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem) throws ServiceException;

	/**
	 * 选单新增结算单
	 * 
	 * @param obj
	 *            结算单
	 * @param lstItem
	 *            选择的业务单据
	 * @return 新增的结算单
	 * @throws ServiceException
	 */
	public BillBalance createBalance(BillBalance obj, List<Object> lstItem) throws ServiceException;

	/**
	 * 汇总总部结算单状态
	 * 
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	public List<BillBalance> selectHqCount() throws ServiceException;

	/**
	 * 扣项调整
	 * 
	 * @param obj
	 *            结算单
	 * @param lstDeduction
	 *            扣项集合
	 * @param lstImperfect
	 *            残鞋集合
	 * @return 调整后的结算单
	 * @throws ServiceException
	 */
	public BillBalance balanceAdjust(BillBalance obj, List<OtherDeduction> lstDeduction,
			List<CustomImperfect> lstImperfect) throws ServiceException;

	public int updateBalancePrice(Map<String, Object> params) throws ServiceException;

	public int queryZeroPriceBill(Map<String, Object> params) throws ServiceException;

	public void findExportList(Map<String, Object> params, Function<Object, Boolean> handler) throws ServiceException;

	public int queryExcessPriceBillCount(Map<String, Object> params) throws ServiceException;

	public List<BillBalance> selectBalanceForBaroque(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException;

}