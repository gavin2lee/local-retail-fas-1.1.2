package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterParentDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.GroupSaleApplyInvoiceRel;
import cn.wonhigh.retail.fas.common.model.MemberOrderDetail;
import cn.wonhigh.retail.fas.common.model.MemberOrderDtl;
import cn.wonhigh.retail.fas.common.model.MemberOrderSummary;
import cn.wonhigh.retail.fas.service.MemberOrderDtlService;
import cn.wonhigh.retail.fas.service.OrderDtlService;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 15:21:34
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
@Service("orderDtlManager")
class OrderDtlManagerImpl extends BaseCrudManagerImpl implements OrderDtlManager {

	@Resource
	private OrderDtlService orderDtlService;
	
	@Resource
	private MemberOrderDtlService memberOrderDtlService;
	
	@Resource
	private OrderMainManager orderMainManager;
	
	@Resource
	private BillSaleBalanceManager billSaleBalanceManager;
	
	@Resource
	private GroupSaleApplyInvoiceRelManager groupSaleApplyInvoiceRelManager;
	
	private int CONFIRM_OPTTYPE = 1;
	private int ANTI_CONFIRM_OPTTYPE = 0;

	@Override
	public BaseCrudService init() {
		return orderDtlService;
	}

	@Override
	public int selectOrderSummaryCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.orderDtlService.selectOrderSummaryCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<MemberOrderSummary> selectOrderSummaryByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		try {
			return this.orderDtlService.selectOrderSummaryByPage(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<MemberOrderSummary> selectOrderSummaryOperateByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return this.orderDtlService.selectOrderSummaryOperateByPage(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	@Override
	public int financeConfirm(List<OrderDtlDto> list,MemberOrderDtl dtl) throws ManagerException {
		try {
			int count = 0;
			Date date = new Date();
			for (OrderDtlDto model : list) {
				MemberOrderDtl memberOrderDtl = new MemberOrderDtl();
//				if(model.getOrderId() != null){
//					dtl.setId(model.getOrderId());
//					dtl.setOrderNo(model.getOrderNo());
//					dtl.setInvoiceAmount(model.getInvoiceAmount());
//					dtl.setShopNo(model.getShopNo());
//					dtl.setStatus(status);
//					dtl.setCompanyNo(model.getCompanyNo());
//					dtl.setCreateTime(new Date());
////					dtl.setOutEndDate(model.getEndDate());
////					dtl.setOutStartDate(model.getStartDate());
//					count += memberOrderDtlService.modifyById(dtl);
//				}else{
				memberOrderDtl.setOrderNo(model.getOrderNo());
				memberOrderDtl.setInvoiceAmount(model.getInvoiceAmount());
				memberOrderDtl.setShopNo(model.getShopNo());
				memberOrderDtl.setCreateTime(date);
				memberOrderDtl.setStatus(dtl.getStatus());
				memberOrderDtl.setOutStartDate(dtl.getOutStartDate());
				memberOrderDtl.setOutEndDate(dtl.getOutEndDate());
				memberOrderDtl.setCompanyNo(dtl.getCompanyNo());
					count += memberOrderDtlService.add(memberOrderDtl);
//				}
			}
			return count;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	@Override
	public int financeAntiConfirm(List<OrderDtlDto> list) throws ManagerException {
		try {
			int count = 0;
			for (OrderDtlDto model : list) {
				MemberOrderDtl dtl = new MemberOrderDtl();
				dtl.setOrderNo(model.getOrderNo());
				dtl.setShopNo(model.getShopNo());
				dtl.setCompanyNo(model.getCompanyNo());
				count += memberOrderDtlService.deleteById(dtl);
			}
			return count;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<MemberOrderDetail> selectOrderMemberList(Map<String, Object> params) throws ManagerException {
		try {
			return orderDtlService.selectOrderMemberList(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 此方法针对选中汇总记录进行确认的处理，根据公司编号、店铺编号、及日期查询出所以记录，并进行操作
	 * @param orderDtlDto 公司编号、店铺编号、及日期
	 * @return
	 * @author wangyj
	 * @throws ManagerException
	 * @throws ServiceException 
	 */
	@Override
	public int financeConfirms(String shopNoStr,MemberOrderDtl dtl,String startDate,String endDate) throws ManagerException {
		int count = 0 ;
		try {
			if(dtl.getStatus().intValue() == 1){//确认
				Map<String,Object> params = new HashMap<String,Object>();
				//根据条件查询相关订单销售明细，并返回订单号、店铺及其他相关信息、
				List<MemberOrderDtl> memberOrderDtlList = findMeberOrderDtlList(shopNoStr,dtl,startDate,endDate,CONFIRM_OPTTYPE);
				
				params.put("companyNo", dtl.getCompanyNo());
				params.put("outStartDate", startDate);
				params.put("outEndDate", endDate);
				Date date = new Date();
				for (MemberOrderDtl memberOrderDtl : memberOrderDtlList) {//循环保存确认状态信息
					params.put("shopNo", memberOrderDtl.getShopNo());
					params.put("orderNo", memberOrderDtl.getOrderNo());
					//根据店铺及订单号查询状态信息是否存，如果存在则不再保存
					List<MemberOrderDtl> omemberOrderDtlList = memberOrderDtlService.findByBiz(null, params);
					if(CollectionUtils.isEmpty(omemberOrderDtlList)){
						memberOrderDtl.setCreateTime(date);
						memberOrderDtl.setStatus((byte)1);
						memberOrderDtl.setCompanyNo(dtl.getCompanyNo());
						memberOrderDtl.setOutEndDate(dtl.getOutEndDate());
						memberOrderDtl.setOutStartDate(dtl.getOutStartDate());
						count += memberOrderDtlService.add(memberOrderDtl);
					}
				}
			}else{// 反确认 
				//根据条件查询相关订单销售明细，并返回订单号、店铺及其他相关信息、
				List<MemberOrderDtl> memberOrderDtlList = findMeberOrderDtlList(shopNoStr,dtl,startDate,endDate,ANTI_CONFIRM_OPTTYPE);
				if(CollectionUtils.isNotEmpty(memberOrderDtlList)){
					for (MemberOrderDtl memberOrderDtl : memberOrderDtlList) {
						memberOrderDtl.setCompanyNo(dtl.getCompanyNo());
						memberOrderDtl.setOutEndDate(dtl.getOutEndDate());
						memberOrderDtl.setOutStartDate(dtl.getOutStartDate());
						//反确认时，把状态记录删除
						count += memberOrderDtlService.deleteById(memberOrderDtl);
					}
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		} 
		return count;
	}
	
	private List<MemberOrderDtl> findMeberOrderDtlList(String shopNoStr,MemberOrderDtl dtl,String startDate,String endDate,int optType) throws ManagerException{
		Map<String,Object> params = new HashMap<String,Object>();
		String[] shopNos = shopNoStr.split(",");
//		params.put("billType", BillTypeEnums.SALEOUT.getRequestId());
		params.put("balanceStartDate", startDate);
		params.put("balanceEndDate", endDate);
		params.put("salerNo", dtl.getCompanyNo());
//		params.put("bizType", BizTypeEnums.GROUPSALE.getStatus());
		
		
		List<BillSaleBalance> saleBalanceLists = new ArrayList<BillSaleBalance>();
		List<String> shopNoList = new ArrayList<String>();
		for (String shopNo : shopNos) {
			params.put("buyerNo", shopNo);
			//取得gms 出库订单明细
			List<BillSaleBalance> saleBalanceList = billSaleBalanceManager.findByBiz(null, params);
			if(CollectionUtils.isEmpty(saleBalanceList)){//如果gms 出库明细为空，则表示此店铺编号在pos系统上
				shopNoList.add(shopNo);
			}else{
				saleBalanceLists.addAll(saleBalanceList);
			}
		}
		
		List<MemberOrderDtl> memberOrderDtlList = new ArrayList<MemberOrderDtl>(); 
		if(CollectionUtils.isNotEmpty(saleBalanceLists)){
			for (BillSaleBalance billSaleBalance : saleBalanceLists) {
				if(optType == 1){
					MemberOrderDtl memberOrderDtl = new MemberOrderDtl();
					memberOrderDtl.setShopNo(billSaleBalance.getBuyerNo());
					memberOrderDtl.setOrderNo(billSaleBalance.getBillNo());
					memberOrderDtlList.add(memberOrderDtl);
				}else{
					params.put("orderNo", billSaleBalance.getOrderNo());
					//根据订单号查询gms 销售订单明细是否已登记了发票，如果已登记发票，则不能再反确认
					List<GroupSaleApplyInvoiceRel> groupSaleApplyInvoiceRelList = groupSaleApplyInvoiceRelManager.findByBiz(null, params);
					if(!CollectionUtils.isEmpty(groupSaleApplyInvoiceRelList)){
						for (GroupSaleApplyInvoiceRel groupSaleApplyInvoiceRel : groupSaleApplyInvoiceRelList) {
							if(StringUtils.isEmpty(groupSaleApplyInvoiceRel.getInvoiceNo())){//取发票编号为空的订单明细记录
								MemberOrderDtl memberOrderDtl = new MemberOrderDtl();
								memberOrderDtl.setShopNo(billSaleBalance.getBuyerNo());
								memberOrderDtl.setOrderNo(billSaleBalance.getBillNo());
								memberOrderDtlList.add(memberOrderDtl);
							}
						}
					}
				}
			}
		}
		//调用pos 接口，查询多个店铺在参数里的销售期间的销售订单明细
		if(CollectionUtils.isNotEmpty(shopNoList)){
			memberOrderDtlList.addAll(findShopSaleOrderForPos(dtl,shopNoList,optType));
		}
		return memberOrderDtlList;
	}
		
	/**
	 * 
	 * @param dtl
	 * @param shopNoList
	 * @return
	 * @throws ManagerException 
	 */
	private List<MemberOrderDtl> findShopSaleOrderForPos(MemberOrderDtl dtl,List<String> shopNoList,int optType) throws ManagerException{
		List<MemberOrderDtl> memberOrderDtlList = new ArrayList<MemberOrderDtl>();
		POSOcOrderParameterParentDto ocOrderParameterParentNoDto = new POSOcOrderParameterParentDto();
		List<Integer> businessTypeList = new ArrayList<Integer>();
		List<Integer> statusList = new ArrayList<Integer>();
		businessTypeList.add(3);
		statusList.add(30);
		statusList.add(41);
		ocOrderParameterParentNoDto.setBusinessTypeList(businessTypeList);
		ocOrderParameterParentNoDto.setStatusList(statusList);
		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = null;
		try {
			ocOrderParameterParentNoDto.setStartOutDate(dtl.getOutStartDate());
			ocOrderParameterParentNoDto.setEndOutDate(dtl.getOutEndDate());
			POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
			pageDto.setPageNo(1);
			pageDto.setPageSize(100000);
			//调用pos的接口,要按照店铺进行汇总，数据不能分页查询，拿到所有的订单
			orderAndReturnExMainDtlDtoList = this.orderMainManager.findList4OrderMainForCompany(pageDto, dtl.getCompanyNo(), ocOrderParameterParentNoDto, 2, "", "",shopNoList);
			if(null != orderAndReturnExMainDtlDtoList){
				 List<POSOrderAndReturnExMainDtlDto> orderDtlList = orderAndReturnExMainDtlDtoList.getResult();
				 if(CollectionUtils.isEmpty(orderDtlList)){
					 return memberOrderDtlList;
				 }
				 for (POSOrderAndReturnExMainDtlDto orderAndReturnExMainDtlDto : orderDtlList) {
					 if(optType == 1){
						 MemberOrderDtl memberOrderDtl = new MemberOrderDtl();
						 memberOrderDtl.setOrderNo(orderAndReturnExMainDtlDto.getOrderNo());
						 memberOrderDtl.setShopNo(orderAndReturnExMainDtlDto.getShopNo());
						 memberOrderDtlList.add(memberOrderDtl);
					 }else{
						 if(StringUtils.isEmpty(orderAndReturnExMainDtlDto.getInvoiceNo())){
							 MemberOrderDtl memberOrderDtl = new MemberOrderDtl();
							 memberOrderDtl.setOrderNo(orderAndReturnExMainDtlDto.getOrderNo());
							 memberOrderDtl.setShopNo(orderAndReturnExMainDtlDto.getShopNo());
							 memberOrderDtlList.add(memberOrderDtl);
						 }
					 }
				}
			}
		} catch (ManagerException e) {
			throw new ManagerException(e.getMessage(), e);
		} 
		return memberOrderDtlList;
	}

}