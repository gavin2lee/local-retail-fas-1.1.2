package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleControlApiDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleOutControlDto;
import cn.wonhigh.retail.fas.api.model.ApiMessage;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;

import com.yougou.logistics.base.common.exception.RpcException;

public interface WholesaleControlApiService {

	/**
	 * 批发检查   初始版本
	 * @param billNo 单据编号
	 * @return 检查信息
	 * @throws RpcException 异常
	 */
	WholesaleControlApiDto getCheckMessage(String billNo)throws RpcException;
	
		/**
	 * 批发订货检查
	 * @param billNo 单据编号
	 * @param orderAmount 出库订单金额
	 * @return 检查信息
	 * @throws RpcException 异常
	 */
	String getOrderGoodsCheckMessage(String billNo)throws RpcException;
	
	/**
	 * 批发订货检查
	 * @param billNo 单据编号
	 * @param itemOrderType 订货类型 1：补货、2：订货
	 * @return 检查信息
	 * @throws RpcException 异常
	 */
	String getOrderGoodsCheckMessage(String billNo, Integer itemOrderType)throws RpcException;
	
	/**
	 * 批发出库检查
	 * @param billNo 出库单
	 * @param orderUnitNo 订货单位
	 * @param customerNo 客户
	 * @param amount 金额
	 * @return 检查信息
	 * @throws RpcException
	 */
	String getSendGoodsCheckMessage(String billNo,String orderUnitNo,String customerNo,BigDecimal amount) throws RpcException;
	
	/**
	 * 发货通知单
	 * @param billNo 通知单
	 * @param orderUnitNo 订货单位
	 * @param list<Map>  map (orderNo 批发订单、customerNo 客户、amount 金额)
	 * @return 检查信息
	 * @throws RpcException
	 */
	String getDeliveryReleaseMessage(String billNo,String orderUnitNo,List<Map<String, Object>> list) throws RpcException;

    /**
     * 批发出库验证
     * @param orderNo 订单号
     * @param customerNo 客户编号
     * @param outAmount  出库金额
     * @param rebateAmount 返利
     * @param creditAmount 信贷额度
     * @param creditCount 可用信贷次数
     * @return
     */
	ApiMessage validateWholesaleOut(WholesaleOutControlDto wholesaleOutControlDto) throws RpcException;

	/**
	 * 修改批发订单的完结状态
	 * @param orderNo 订单号
	 * @param status 状态码
	 * @return
	 */
    Integer updateOrderStatus(String orderNo, Integer status) throws RpcException;
    
    
    /**
     * 增加扣项
     * @param companyNo,companyName,customerNo,customerName, billNo(出库单号),bizType(业务类型),rebateAmount(返利金额),
     * otherPrice(其它费用),orderNo(关联订单号),brand_no(品牌编号),brand_name(品牌名称),deductionDate
     * @return
     */
    ApiMessage addOtherDedution(OtherDeduction otherDedution) throws RpcException;
    
    /**
     * 查询客户-收款条款
     * @return 
     */
    Boolean findCustomerReceRel(String companyNo, String customerNo) throws RpcException;
    
	/**
	 * 批发变价处理
	 * @param balance List<BillBalanceApiDto>
	 * @return boolean
	 * @throws RpcException 异常
	 */
    String changeWholesaleCost(List<BillBalanceApiDto> lstBill) throws RpcException;
	
}
