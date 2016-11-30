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

public interface WholesaleControlApi {

	//初始版本
	WholesaleControlApiDto getCheckMessage(String billNo) throws RpcException;
	
	/**
	 * 批发订货检查
	 * @param billNo 单据编号
	 * @throws RpcException 异常
	 */
	String getOrderGoodsCheckMessage(String billNo)throws RpcException;
	
	/**
	 * 批发订货检查
	 * @param billNo 单据编号
	 * @throws RpcException 异常
	 * isOrder true:订货控制 / false:补货控制
	 */
	String getOrderGoodsCheckMessage(String billNo, Integer itemOrderType)throws RpcException;
	
	/**
	 * 批发出库检查 
	 * @param billNo 出库单
	 * @param orderUnitNo 订货单位
	 * @param customerNo  客户编码
	 * @param amount 金额
	 * @return
	 * @throws RpcException
	 */
	String getSendGoodsCheckMessage(String billNo,String orderUnitNo, String customerNo,BigDecimal amount)throws RpcException;
	
	/**
	 * 发货通知单
	 * @param billNo 
	 * @param orderUnitNo
	 * @param list<Map> map (包含 customer_no  客户编码、amount 金额)
	 * @return
	 * @throws RpcException
	 */
	String getDeliveryReleaseMessage(String billNo,String orderUnitNo,List<Map<String, Object>> list)throws RpcException;
		
	/**
	 * 批发发货控制
	 * @return
	 * @throws RpcException
	 */
	ApiMessage validateWholesaleOut(WholesaleOutControlDto wholesaleOutControlDto)throws RpcException;
	
    /**
     * 查询客户-收款条款
     * @return 
     */
    Boolean findCustomerReceRel(String companyNo, String customerNo) throws RpcException;
    
	/**
	 * 修改批发订单的完结状态
	 * @param orderNo 订单号
	 * @param status 状态码
	 * @return
	 */
    Integer updateOrderStatus(String orderNo, Integer status) throws RpcException;
    
    
	/**
	 * 批发变价处理
	 * @param balance List<BillBalanceApiDto>
	 * @return boolean
	 * @throws RpcException 异常
	 */
    String changeWholesaleCost(List<BillBalanceApiDto> lstBill) throws RpcException;
}
