package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface BillSaleBalanceApiService {

	/**
	 * 销售类单据插入单据池
	 * @param lstBill List<BillBalanceApiDto>
	 * @return int
	 * @throws ServiceException 异常
	 */
	int insert(BillBalanceApiDto dto) throws ServiceException;
	
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
	
	int updateWholesaleCustomerRemiaining(BillSaleBalance billSaleBalance, int isInvalid, int tempNum,BigDecimal sendAmount, BigDecimal rebateAmount,BigDecimal otherPrice)throws ServiceException;

	BillSaleBalance selectSumBillSaleBalanceByBillNo(String billNo) throws ServiceException;
	
	/**
	 * 根据源单编号查询单据
	 * @param refBillNo
	 * @return
	 * @throws ServiceException
	 */
	List<BillSaleBalance> selectByRefBillNo(String refBillNo) throws ServiceException;

	/**
	 * 查询系统参数数量
	 * @param salerNo
	 * @param paramCode
	 * @param dtlValue
	 * @return
	 */
	int selectParamsCount(String salerNo, String paramCode, String dtlValue);
	
    /**
     * 维护批发客户订单余额表
     * @param listBill
     * @param isInvalid 是否作废,1作废
     * @throws ServiceException
     */
	void updateCustomerOrderRemain(BillSaleBalance billSaleBalance, BigDecimal sendAmount, BigDecimal rebateAmount,BigDecimal otherPrice) throws ServiceException;

	
	/**
	 * 批量插入网销数据
	 * @param list 待插入的数据集合
	 * @return 插入的数量
	 * @throws ServiceException 异常
	 */
	int batchInsertNet(List<BillBalanceApiDto> lstBill)throws ServiceException;
	
}
