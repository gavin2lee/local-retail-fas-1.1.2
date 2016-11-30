package cn.wonhigh.retail.fas.api.dal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.common.dto.ReportDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

public interface BillBuyBalanceApiMapper {

	/**
	 * add by Ning.ly 查询供应商总部交易信息
	 * 
	 * @param billHeaderApiDto
	 *            单据头对象
	 * @return 作废是否成功
	 * @throws RpcException
	 *             异常
	 */
	int selectBillBuyBalanceCount(@Param("params") Map<String, Object> params) throws Exception;

	/**
	 * add by Ning.ly 查询供应商与总部交易信息
	 * 
	 * @param billHeaderApiDto
	 *            单据头对象
	 * @return 作废是否成功
	 * @throws RpcException
	 *             异常
	 */
	List<BillBuyBalance> selectBillBuyBalanceInfo(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params) throws Exception;

	/**
	 * add by Ning.ly 查询供应商与总部交易信息Footer
	 * 
	 * @param billHeaderApiDto
	 *            单据头对象
	 * @return 作废是否成功
	 * @throws RpcException
	 *             异常
	 */
	List<BillBuyBalance> selectBillBuyBalanceFooter(@Param("params") Map<String, Object> params) throws Exception;

	/**
	 * 采购类单据插入单据池
	 * 
	 * @param balance
	 *            BillBuyBalance
	 * @return Boolean
	 * @throws ServiceException
	 *             异常
	 */
	int insert(BillBuyBalance balance) throws Exception;

	/**
	 * 批量插入数据
	 * 
	 * @param list
	 *            待插入的数据集合
	 * @return 插入的数量
	 * @throws Exception
	 *             异常
	 */
	int batchInsert(@Param("organTypeNo")String organTypeNo, @Param("list")List<BillBuyBalance> list) throws Exception;

	/**
	 * 通过原单据编码查询单据池中相关单据的记录数
	 * 
	 * @param refBillNo
	 *            原单据编码
	 * @return 记录数
	 */
	int selectCountByRefBillNo(@Param("refBillNo") String refBillNo);

	/**
	 * 通过单据编码删除为结算的数据
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 删除的数量
	 * @throws ServiceException
	 *             异常
	 */
	int deleteByBillNo(@Param("billNo") String billNo) throws Exception;

	/**
	 * 通过单据编码及货号删除未结算的数据
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 删除的数量
	 * @throws ServiceException
	 *             异常
	 */
	int deleteByBillNoAndItemNo(@Param("billNo") String billNo, @Param("itemNo") String itemNo) throws Exception;

	/**
	 * 通过单据编码查询已结算单据的数量
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 数量
	 * @throws ServiceException
	 *             异常
	 */
	Integer selectCountByBillNo(@Param("billNo") String billNo) throws Exception;

	/**
	 * 作废单据
	 * 
	 * @param billHeaderApiDto
	 *            参数条件
	 * @return 作废的数量
	 * @throws ServiceException
	 *             异常
	 */
	int invalid(@Param("params") Map<String, Object> params) throws Exception;

	/**
	 * 通过map参数，查询数量
	 * 
	 * @param params
	 *            参训参数
	 * @return 查询到的数量 Exception ServiceException 异常
	 */
	int selectCountByParams(@Param("params") Map<String, Object> params) throws Exception;

	/**
	 * 验收单新增时，修改关联的到货单的接受数量
	 * 
	 * @param params
	 *            限制参数
	 * @return 修改的记录数
	 */
	int modifyBillAsnReceiveQty(@Param("params") Map<String, Object> params) throws Exception;

	/**
	 * 跨区入库单新增时，修改关联的跨区出库单的接受数量
	 * 
	 * @param params
	 *            限制参数
	 * @return 修改的记录数
	 */
	int modifyBillTransferReceiveQty(@Param("params") Map<String, Object> params) throws Exception;

	/**
	 * 根据源单编号和货号查询源单的结算路径编码
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	String selectRefPathNo(@Param("params") Map<String, Object> params) throws Exception;

	/**
	 * 根据源单编号查询单据
	 * 
	 * @param refBillNo
	 * @return
	 */
	List<BillBuyBalance> selectByRefBillNo(@Param("refBillNo") String refBillNo) throws Exception;

	List<ReportDto> findRowByPage(@Param("page") SimplePage page, @Param("params") Map<String, Object> params);

	List<ReportDto> findQtyByPage(@Param("page") SimplePage page, @Param("params") Map<String, Object> params);

	List<ReportDto> findColumnByParams(@Param("params") Map<String, Object> params);

	int findReportCount(@Param("params") Map<String, Object> params);

	List<ReportDto> findReportFooter(@Param("params") Map<String, Object> params);

	BigDecimal findRegionCost(BillBalanceApiDto dto);

	int selectIsSplitCount(@Param("billNo")String billNo);

	int updateBuyTransferSendDate(@Param("billNo")String billNo,@Param("sendDate")Date sendDate);

	Integer selectRefBizType(@Param("billNo")String billNo);

	Integer selectIsWholesaleCount(@Param("orderUnitNo")String orderUnitNo);
}
