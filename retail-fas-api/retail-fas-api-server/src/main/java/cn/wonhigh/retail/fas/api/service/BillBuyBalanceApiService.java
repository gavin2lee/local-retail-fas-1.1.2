package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

public interface BillBuyBalanceApiService {
	
	/**add by Ning.ly
	 * 查询供应商总部交易信息
	 * @param billHeaderApiDto 单据头对象
	 * @return 作废是否成功
	 * @throws RpcException 异常
	 */
	int selectBillBuyBalanceCount(Map<String, Object> params) throws ServiceException;
	
	
	/**add by Ning.ly
	 * 查询供应商与总部交易信息
	 * @param billHeaderApiDto 单据头对象
	 * @return 作废是否成功
	 * @throws RpcException 异常
	 */
	List<BillBuyBalance> selectBillBuyBalanceInfo(SimplePage page,String orderByField,String orderBy,Map<String, Object> params) throws ServiceException;

	/**add by Ning.ly
	 * 查询供应商与总部交易信息Footer
	 * @param billHeaderApiDto 单据头对象
	 * @return 作废是否成功
	 * @throws RpcException 异常
	 */
	List<BillBuyBalance> selectBillBuyBalanceFooter(Map<String, Object> params)throws ServiceException;
	
	/**
	 * 采购类单据插入单据池
	 * @param balance BillBalanceApiDto
	 * @return int
	 * @throws ServiceException 异常
	 */
	int insert(BillBalanceApiDto balance) throws ServiceException;
	
	/**
	 * 批量插入数据
	 * @param list 待插入的数据集合
	 * @return 插入的数量
	 * @throws ServiceException 异常
	 */
	int batchInsert(List<BillBalanceApiDto> list) throws ServiceException;
	
	/**
	 * 通过原单据编码查询单据池中相关单据的记录数
	 * @param refBillNo 原单据编码
	 * @return 记录数
	 * @throws ServiceException 异常
	 */
	int selectCountByRefBillNo(String refBillNo) throws ServiceException;
	
	/**
	 * 通过单据编码删除为结算的数据
	 * @param billNo 单据编码
	 * @return 删除的数量
	 * @throws ServiceException 异常
	 */
	int deleteByBillNo(String billNo) throws ServiceException;
	
	/**
	 * 通过单据编码及货号删除为结算的数据
	 * @param billNo 单据编码
	 * @return 删除的数量
	 * @throws ServiceException 异常
	 */
	int deleteByBillNoAndItemNo(String billNo, String itemNo) throws ServiceException;
	
	/**
	 * 通过单据编码查询已结算单据的数量
	 * @param billNo 单据编码
	 * @return 数量
	 * @throws ServiceException 异常
	 */
	int selectCountByBillNo(String billNo) throws ServiceException;
	
	/**
	 * 作废单据
	 * @param billHeaderApiDto 参数条件
	 * @return 作废的数量
	 * @throws ServiceException 异常
	 */
	int invalid(BillHeaderApiDto billHeaderApiDto) throws ServiceException;
	
	/**
	 * 通过map参数，查询数量
	 * @param params 参训参数
	 * @return 查询到的数量
	 * @throws ServiceException 异常
	 */
	int selectCountByParams(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据源单编号和货号查询源单的结算路径编码
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	String selectRefPathNo(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 根据源单编号查询单据
	 * @param refBillNo
	 * @return
	 * @throws ServiceException
	 */
	List<BillBuyBalance> selectByRefBillNo(String refBillNo) throws ServiceException;

	int queryReportCount(Map<String, Object> params)throws ServiceException;


	List<Map<String, Object>> queryReportByPage(Map<String, Object> params,SimplePage page)throws ServiceException;


	List<Map<String, Object>> queryReportFooter(Map<String, Object> params)throws ServiceException;


	Map<String, Object> queryReportColumn(Map<String, Object> params)throws ServiceException;

	BigDecimal findRegionCost(BillBalanceApiDto dto) throws ServiceException;


	int selectIsSplitCount(String refBillNo)throws ServiceException;


	Integer selectRefBizType(String refBillNo)throws ServiceException;


	Integer selectIsWholesaleCount(String orderUnitNo) throws ServiceException;
}
