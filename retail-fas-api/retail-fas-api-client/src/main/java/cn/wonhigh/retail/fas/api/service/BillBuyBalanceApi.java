package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.utils.SimplePage;

public interface BillBuyBalanceApi {

	/**
	 * 采购类单据插入单据池
	 * @param lstBill List<BillBalanceApiDto>
	 * @return int
	 * @throws RpcException 异常
	 */
	boolean insert(List<BillBalanceApiDto> lstBill) throws RpcException;
	
	/**
	 * 作废单据
	 * @param billHeaderApiDto 单据头对象
	 * @return 作废是否成功
	 * @throws RpcException 异常
	 */
	boolean invalid(BillHeaderApiDto billHeaderApiDto) throws RpcException;
	
	/**
	 * 判断是否结算
	 * @param billNo 编号
	 * @return 作废是否结算
	 * @throws RpcException 异常
	 */
	boolean isBalance(String billNo) throws RpcException;
	
	/**add by Ning.ly
	 * 查询供应商总部交易信息
	 * @param billHeaderApiDto 单据头对象
	 * @return 作废是否成功
	 * @throws RpcException 异常
	 */
	int selectBillBuyBalanceCount(Map<String, Object> params) throws RpcException;
	
	
	/**add by Ning.ly
	 * 查询供应商与总部交易信息
	 * @param billHeaderApiDto 单据头对象
	 * @return 作废是否成功
	 * @throws RpcException 异常
	 */
	List<BillBuyBalance> selectBillBuyBalanceInfo(SimplePage page,String orderByField,String orderBy,Map<String, Object> params) throws RpcException;
	
	/**
	 * 查询供应商与总部交易信息Footer
	 * @param billHeaderApiDto 单据头对象
	 * @return 作废是否成功
	 * @throws RpcException 异常
	 */
	List<BillBuyBalance> selectBillBuyBalanceFooter(Map<String, Object> params) throws RpcException;
	
	/**
	 * 查询总部厂商明细对账表数量
	 * @param params 查询条件
	 * @return 数量
	 * @throws RpcException 异常
	 */
	int queryReportCount(Map<String, Object> params) throws RpcException;
	
	/**
	 * 查询总部厂商明细对账表集合
	 * @param params 查询条件
	 * @return 列表集合
	 * @throws RpcException 异常
	 */
	List<Map<String, Object>> queryReportByPage(Map<String, Object> params,SimplePage page) throws RpcException;
	
	/**
	 * 查询总部厂商明细对账表footer
	 * @param params 查询条件
	 * @return footer
	 * @throws RpcException 异常
	 */
	List<Map<String, Object>> queryReportFooter(Map<String, Object> params) throws RpcException;
	
	/**
	 * 查询总部厂商明细对账表表头列
	 * @param params 查询条件
	 * @return footer
	 * @throws RpcException 异常
	 */
	Map<String, Object> queryReportColumn(Map<String, Object> params) throws RpcException;
}


