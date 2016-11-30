package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalanceNet;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface BillSaleBalanceApiMapper {

	/**
	 * 销售类单据插入单据池
	 * @param balance BillSaleBalance
	 * @return int
	 * @throws ServiceException 异常
	 */
	int insert(BillSaleBalance bill) throws Exception;
	
	/**
	 * 批量插入数据
	 * @param list 待插入的数据集合
	 * @return 插入的数量
	 * @throws Exception 异常
	 */
	int batchInsert(@Param("organTypeNo")String organTypeNo, @Param("list")List<BillSaleBalance> list) throws Exception;

	/**
	 * 通过原单据编码查询单据池中相关单据的记录数
	 * @param refBillNo 原单据编码
	 * @return 记录数
	 */
	int selectCountByRefBillNo(@Param("refBillNo")String refBillNo);
	
	/**
	 * 通过单据编码删除为结算的数据
	 * @param billNo 单据编码
	 * @return 删除的数量
	 * @throws ServiceException 异常
	 */
	int deleteByBillNo(@Param("billNo")String billNo) throws Exception;
	
	/**
	 * 通过单据编码及货号删除未结算的数据
	 * @param billNo 单据编码
	 * @return 删除的数量
	 * @throws ServiceException 异常
	 */
	int deleteByBillNoAndItemNo(@Param("billNo")String billNo, @Param("itemNo")String itemNo) throws Exception;

	/**
	 * 通过单据编码查询已结算单据的数量
	 * @param billNo 单据编码
	 * @return 数量
	 * @throws ServiceException 异常
	 */
	int selectCountByBillNo(@Param("billNo")String billNo) throws Exception;

	/**
	 * 作废单据
	 * @param billHeaderApiDto 参数条件
	 * @return 作废的数量
	 * @throws ServiceException 异常
	 */
	int invalid(@Param("params")Map<String, Object> params) throws Exception;

	/**
	 * 通过map参数，查询数量
	 * @param params 参训参数
	 * @return 查询到的数量
	 * Exception ServiceException 异常
	 */
	int selectCountByParams(@Param("params")Map<String, Object> params) throws Exception;
	
	/**
	 * 验收单新增时，修改关联的到货单的接受数量
	 * @param params 限制参数
	 * @return 修改的记录数
	 */
	int modifyBillAsnReceiveQty(@Param("params")Map<String, Object> params) throws Exception;

	String selectYearRebateRemainderAmount(@Param("params")Map<String, Object> params) throws Exception;

	void updateRebateAmount(@Param("params")Map<String, Object> params)throws Exception;
	
	BillSaleBalance selectSumBillSaleBalanceByBillNo(@Param("billNo")String billNo)throws Exception;
	
	/**
	 * 根据源单编号查询单据
	 * @param refBillNo
	 * @return
	 */
	List<BillSaleBalance> selectByRefBillNo(@Param("refBillNo")String refBillNo) throws Exception;
	
	/**
	 * 根据单号或源单号、货号查询相关单据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	BillSaleBalance selectRefBill(@Param("params")Map<String, Object> params) throws Exception;

	/**
	 * 查询系统参数数量
	 * @param salerNo
	 * @param paramCode
	 * @param dtlValue
	 * @return
	 */
	int selectParamsCount(@Param("companyNo")String salerNo, @Param("paramCode")String paramCode, @Param("dtlValue")String dtlValue);
	
	/**
	 * 插入网销数据
	 * @param netBalance
	 * @return 插入的数量
	 * @throws Exception 异常
	 */
	int insertNet(BillSaleBalanceNet netBalance);
	
}
