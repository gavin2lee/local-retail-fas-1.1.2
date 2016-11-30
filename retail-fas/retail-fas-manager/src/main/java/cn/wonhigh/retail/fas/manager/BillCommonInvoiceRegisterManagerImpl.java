package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceDtl;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceSource;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceApplyService;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceSourceService;
import cn.wonhigh.retail.fas.service.BillCommonInvoiceRegisterService;
import cn.wonhigh.retail.fas.service.BillCommonRegisterDtlService;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderInvoiceParameterDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-10 14:40:44
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
@Service("billCommonInvoiceRegisterManager")
class BillCommonInvoiceRegisterManagerImpl extends BaseCrudManagerImpl implements BillCommonInvoiceRegisterManager {
    @Resource
    private BillCommonInvoiceRegisterService billCommonInvoiceRegisterService;
    
    @Resource
    private BillBalanceInvoiceApplyService billBalanceInvoiceApplyService;
    
    @Resource
    private BillBalanceInvoiceSourceService billBalanceInvoiceSourceService;
    
//    @Resource
//	private OrderMainApi orderMainApi;
    
    @Resource
    private BillCommonRegisterDtlService billCommonRegisterDtlservice;
    
//    @Resource
//    private ShopSaleDetailManager shopSaleDetailManager;
//    
//    @Resource
//    private BillBalanceInvoiceApplyManager billBalanceInvoiceApplyManager;
//    
//    @Resource
//    private GroupSaleApplyInvoiceRelManager groupSaleApplyInvoiceRelManager;
    
    @Resource
    private BillBalanceInvoiceDtlManager billBalanceInvoiceDtlManager;
    
    @Resource
    private CommonUtilService commonUtilService;
    
    @Resource
    private SystemParamSetManager systemParamSetManager ;
    
    private Log log = LogFactory.getLog(BillCommonInvoiceRegisterManagerImpl.class);

    @Override
    public BaseCrudService init() {
        return billCommonInvoiceRegisterService;
    }
    
    /**
     * 发票登记页面保存处理
     * orderDtlDtoList：包括订单号及销售类型（正常销售、退换货）
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
    public int addBillCommonInvoiceRegister(BillCommonInvoiceRegister invoiceRegister,List<OcOrderInvoiceParameterDto> orderDtlDtoList) throws ManagerException{
		String billNo = "";
		try {
//			BillCommonInvoiceRegister invoiceRegister = (BillCommonInvoiceRegister) modelType;
			if (invoiceRegister.getBalanceType() == null) {
				throw new ServiceException("源单类型为空");
			}
			
			String requestId = BillBalanceInvoiceRegister.class.getSimpleName();
			//调用单据编号拼接处理方法，返回最终的单据编号
			billNo = commonUtilService.getNewBillNoCompanyNo(invoiceRegister.getSalerNo(),null,requestId);
			invoiceRegister.setBillNo(billNo);
			invoiceRegister.setId(UUIDGenerator.getUUID());
			int iCount = billCommonInvoiceRegisterService.add(invoiceRegister);
			
//			int balanceType = 0;
//			if (invoiceRegister.getBalanceType() != null) {
//				balanceType = invoiceRegister.getBalanceType();
//			}

//			// 员购及独立店铺类型 需要把发票编号回写到原订单(pos 销售明细)信息中
//			if (balanceType == BalanceTypeEnums.AREA_MEMBERS_PURCHASE.getTypeNo() || balanceType == BalanceTypeEnums.ALONE_SHOP.getTypeNo()) {
//				// 回写销售订单的发票号
//				orderMainApi.modifyOrderForInvoice(orderDtlDtoList,invoiceRegister.getBillNo(),invoiceRegister.getBillDate());
//			} else {// 除员购及独立店铺类型外，其他类型都需要更新开票申请的信息（回写发票编号）
				if (StringUtils.isNotBlank(invoiceRegister.getSourceNo())) {
					String[] sourceNos = invoiceRegister.getSourceNo().split(",");
					invoiceRegister.setStatus(BalanceStatusEnums.MAKE_INVOICE.getTypeNo());
					BillBalanceInvoiceApply billBalanceInvoiceApply = null;
					for (String sourceNo : sourceNos) {// 更新开票申请的发票编号，可能是一次更新多笔
						//地区团购，预开票(preInvoice=2 表示从发票登记新增选择开票申请为：团购 的信息进行产生的发票，既需要更新销售订单也需要更新开票申请)
						/*if(balanceType == BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()) {
							// 发票登记选择开票申请的团购结算类型时，需要更新销售订单的发票编号
							List<POSOrderAndReturnExMainDtlDto>  orderAndReturnExMainDtlDtoList =  new ArrayList<POSOrderAndReturnExMainDtlDto>();
							List<POSOrderAndReturnExMainDtlDto>  orderExMainDtlDtoList = billBalanceInvoiceApplyManager.getOrderInfo(balanceType+"" ,"",sourceNo,null);
							if(null != orderExMainDtlDtoList){
								orderAndReturnExMainDtlDtoList.addAll(orderExMainDtlDtoList);
							}
							updateOrderNoByInvoiceRegisterNo(orderAndReturnExMainDtlDtoList,billNo,invoiceRegister.getBillDate());
						}else if(balanceType == BalanceTypeEnums.AREA_SALEORDER.getTypeNo()){//地区团购批发 回写团购批开票发票关联表中的发票编号
							GroupSaleApplyInvoiceRel groupSaleApplyInvoiceRel = new GroupSaleApplyInvoiceRel();
							groupSaleApplyInvoiceRel.setInvoiceNo(billNo);
							groupSaleApplyInvoiceRel.setInvoiceDate(invoiceRegister.getBillDate());
							groupSaleApplyInvoiceRel.setInvoiceApplyNo(sourceNo);
							groupSaleApplyInvoiceRelManager.updateByInvoiceApplyNo(groupSaleApplyInvoiceRel);
						} */
						billBalanceInvoiceApply = new BillBalanceInvoiceApply();
						billBalanceInvoiceApply.setBillNo(sourceNo);
						billBalanceInvoiceApply.setInvoiceRegisterNo(billNo);
						billBalanceInvoiceApply.setStatus(3);
						// 回写开票申请的发票号
						billBalanceInvoiceApplyService.updateByPrimaryKeySelective(billBalanceInvoiceApply);
					}
				}
//			}
			return iCount;
		} catch (Exception e) {
//			codingRuleService.recycleSerialNo(BillCommonInvoiceRegister.class.getSimpleName(), billNo);
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
    /**
     * 发票登记页面保存处理(从销售明细页面直接生成发票登记信息)
     * orderDtlDtoList：包括订单号及销售类型（正常销售、退换货）
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
    public int addByGroupOrMemberBuyer(BillCommonInvoiceRegister invoiceRegister,List<OcOrderInvoiceParameterDto> orderDtlDtoList) throws ManagerException{
		String billNo = "";
		try {
//			BillCommonInvoiceRegister invoiceRegister = (BillCommonInvoiceRegister) modelType;
			if (invoiceRegister.getBalanceType() == null) {
				throw new ServiceException("源单类型为空");
			}
//			billNo = codingRuleService.getSerialNo(BillBalanceInvoiceRegister.class.getSimpleName());
			String requestId = BillBalanceInvoiceRegister.class.getSimpleName();
			//调用单据编号拼接处理方法，返回最终的单据编号
			billNo = commonUtilService.getNewBillNoCompanyNo(invoiceRegister.getSalerNo(),null,requestId);
			invoiceRegister.setBillNo(billNo);
			invoiceRegister.setId(UUIDGenerator.getUUID());
			int iCount = billCommonInvoiceRegisterService.add(invoiceRegister);
			
			// 员购销售明细/团购销售明细, 需要把发票编号回写到原订单(pos 销售明细)信息中
//			orderMainApi.modifyOrderForInvoice(orderDtlDtoList,invoiceRegister.getBillNo(),invoiceRegister.getBillDate());
			return iCount;
		} catch (Exception e) {
//			codingRuleService.recycleSerialNo(BillCommonInvoiceRegister.class.getSimpleName(), billNo);
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int remove(String[] ids) throws ManagerException {
		int count = 0;
		try {
			String tempId = "";
			for (String str : ids) {
				String id = str.split(",")[0];
				String billNo = str.split(",")[1];
				String balanceType = str.split(",")[2];
				if(!tempId.equals(id)){
					tempId = id;
					if(StringUtils.isNotBlank(billNo)){
						billCommonRegisterDtlservice.deleteByBillNo(billNo);
						log.info("删除明细,单据号:"+billNo);
					}
					BillCommonInvoiceRegister invoiceRegister = new BillCommonInvoiceRegister();
					invoiceRegister.setId(id);
					count = billCommonInvoiceRegisterService.deleteById(invoiceRegister);
					log.info("删除主表信息,单据号:"+billNo);
					if (StringUtils.isNotBlank(balanceType)) {
					/*//独立店铺类型 需要根据发票清空原订单中对应的发票号
					if(balanceType.equals(String.valueOf(BalanceTypeEnums.ALONE_SHOP.getTypeNo()))){
						Map<String, Object> params = new HashMap<String,Object>();
						params.put("invoiceNoFlag", InvoiceNoFlagEnums.SEARCH_INVOICE_NO_NOT_NULL.getTypeNO());//根据发票编号查询标识
						params.put("invoiceNo", billNo);
						POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = shopSaleDetailManager.queryShopSaleDetailListRemote(params);
//						OcPagingDto<OrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = orderMainApi.findList4OrderMain(new OcSimplePageDto(),null,null,1,billNo);
						List<POSOrderAndReturnExMainDtlDto> orderDtlDtoList = orderAndReturnExMainDtlDtoList.getResult();
						updateOrderNoByInvoiceRegisterNo(orderDtlDtoList,null,null);
					}else if(balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_MEMBERS_PURCHASE.getTypeNo()))) {//员购
						// 删除发票登记时清空员购销售订单的发票编号
						List<POSOrderAndReturnExMainDtlDto>  orderDtlDtoList = billBalanceInvoiceApplyManager.getOrderInfo(balanceType,billNo,"",null);
						updateOrderNoByInvoiceRegisterNo(orderDtlDtoList,null,null);
					}else{// 员购及独立店铺类型(团购比较特殊)外 ，根据发票编号清空开票申请表中对应的发票编号
						//团购比较特别，既可以团购明细进行登记，也可以选择开票申请进行登记
						if(balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()))){
							// 删除发票登记时清空团购销售订单的发票编号
							List<POSOrderAndReturnExMainDtlDto>  orderDtlDtoList = billBalanceInvoiceApplyManager.getOrderInfo(balanceType,billNo,"",null);
							updateOrderNoByInvoiceRegisterNo(orderDtlDtoList,null,null);
						}else if(balanceType.equals(String.valueOf(BalanceTypeEnums.AREA_SALEORDER.getTypeNo()))){//地区团购批发 删除时清空团购批开票发票关联表中的发票编号
							//清空团购批发开票申请、发票登记关联表的发票信息
							GroupSaleApplyInvoiceRel groupSaleApplyInvoiceRel = new GroupSaleApplyInvoiceRel();
							groupSaleApplyInvoiceRel.setInvoiceNo(billNo);
							groupSaleApplyInvoiceRelManager.updateByInvoiceApplyNo(groupSaleApplyInvoiceRel);
						}*/
						//清空开票申请中的发票号
						BillBalanceInvoiceApply billBalanceInvoiceApply  = new BillBalanceInvoiceApply();
						billBalanceInvoiceApply.setInvoiceRegisterNo(billNo);
						billBalanceInvoiceApply.setStatus(2);
						billBalanceInvoiceApplyService.updateInvoiceRegisterNo(billBalanceInvoiceApply);
//					}
					}
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
//		} catch (RpcException e) {
//			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}
	
//	/**
//	 * 根据发票号查询独立店铺的销售订单信息，获取订单号及销售类型，用于删除发票登记时，清空相关订单的发票号
//	 * @param billNo
//	 * @return
//	 * @throws ManagerException
//	 * @throws RpcException 
//	 */
//	private void updateOrderNoByInvoiceRegisterNo(List<POSOrderAndReturnExMainDtlDto>  orderDtlDtoList,String invoiceNo,Date invoiceDate) throws ManagerException, RpcException{
//		List<OcOrderInvoiceParameterDto> ocOrderInvoiceParameterDtoList = null;
//		if(!CollectionUtils.isEmpty(orderDtlDtoList)){
//			ocOrderInvoiceParameterDtoList = new ArrayList<OcOrderInvoiceParameterDto>();
//			for (POSOrderAndReturnExMainDtlDto orderDtlDto : orderDtlDtoList) {
//				OcOrderInvoiceParameterDto ocOrderInvoiceParameterDto = new OcOrderInvoiceParameterDto();
//				ocOrderInvoiceParameterDto.setOrderNo(orderDtlDto.getOrderNo());
//				ocOrderInvoiceParameterDto.setOrderBillType(orderDtlDto.getOrderBillType());
//				ocOrderInvoiceParameterDtoList.add(ocOrderInvoiceParameterDto);
//			}
//			//回写销售订单的发票号
//			orderMainApi.modifyOrderForInvoice(ocOrderInvoiceParameterDtoList, invoiceNo , invoiceDate);
//		}
//	}

	/**
	 * 导入发票明细，包括发票表头主体信息（可能存在多笔一样的主体信息，例如一张发票有多张明细情况时）
	 * @param dtlList 发票明细集合,包括开票申请号及公司、客户等信息
	 * @param createUser 登陆用户名称
	 * @throws ManagerException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void doImportInvoiceRegsiter(List<BillCommonRegisterDtl> dtlList,String createUser) throws ManagerException{
		String sourceNo = "";
		boolean flag = false;
		String billNo = "";
		try{
		if(!CollectionUtils.isEmpty(dtlList)){
			for (BillCommonRegisterDtl billCommonRegisterDtl : dtlList) {
				if(!sourceNo.equals(billCommonRegisterDtl.getSourceNo())){
					flag = true;
//					String requestId = BillBalanceInvoiceRegister.class.getSimpleName();
					//调用单据编号拼接处理方法，返回最终的单据编号
//					billNo = commonUtilService.getNewBillNoCompanyNo(billCommonRegisterDtl.getSalerNo(),null,requestId);
					sourceNo = billCommonRegisterDtl.getSourceNo();
				}
				if(flag){
					BillCommonInvoiceRegister billCommonInvoiceRegister = new BillCommonInvoiceRegister();
//					billCommonInvoiceRegister.setBillNo(billNo);
					billCommonInvoiceRegister.setSourceNo(billCommonRegisterDtl.getSourceNo());
					billCommonInvoiceRegister.setSalerNo(billCommonRegisterDtl.getSalerNo());
					billCommonInvoiceRegister.setBuyerNo(billCommonRegisterDtl.getBuyerNo());
					billCommonInvoiceRegister.setCreateUser(createUser);
					billNo = addInvoiceRegsiter(billCommonInvoiceRegister);
					flag = false;
				}
				billCommonRegisterDtl.setBillNo(billNo);
				if(null == billCommonRegisterDtl.getTaxRate()){
					billCommonRegisterDtl.setTaxRate(new BigDecimal(0.17));
				}
				// 计算不含税金额 ＝ 金额 / 1+0.17
				if(null == billCommonRegisterDtl.getNoTaxAmount()){
					double noTaxAmount = billCommonRegisterDtl.getInvoiceAmount().doubleValue() / (1 + billCommonRegisterDtl.getTaxRate().doubleValue()) ;
					billCommonRegisterDtl.setNoTaxAmount(new BigDecimal(noTaxAmount));
				}
				// 税额 ＝ 金额 － 不含税金额
				if(null == billCommonRegisterDtl.getTaxAmount()){
					double taxAmount = billCommonRegisterDtl.getInvoiceAmount().doubleValue() - 
							billCommonRegisterDtl.getInvoiceAmount().doubleValue() / (1 + billCommonRegisterDtl.getTaxRate().doubleValue()) ;
					billCommonRegisterDtl.setTaxAmount(new BigDecimal(taxAmount));
				}
//				if(null == billCommonRegisterDtl.getPrice()){
//					double price = billCommonRegisterDtl.getInvoiceAmount().doubleValue() / billCommonRegisterDtl.getQty();
//					billCommonRegisterDtl.setPrice(new BigDecimal(price));
//				}
				billCommonRegisterDtl.setId(UUIDGenerator.getUUID());
				billCommonRegisterDtlservice.add(billCommonRegisterDtl);
			}
		}
		}catch(ServiceException e){
//			codingRuleService.recycleSerialNo(BillCommonInvoiceRegister.class.getSimpleName(), billNo);
			throw new ManagerException(e.getMessage(),e);
		}
	}
	
	/**
	 * 根据开票申请号查询开票申请信息,并设置发票登记的表头基本信息;并且根据开票申请号回写发票号
	 * @param billCommonInvoiceRegister 发票登记对象
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public String addInvoiceRegsiter(BillCommonInvoiceRegister billCommonInvoiceRegister) throws ServiceException{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("billNo", billCommonInvoiceRegister.getSourceNo());
		BillBalanceInvoiceApply billBalanceInoviceApply = new BillBalanceInvoiceApply();
		billBalanceInoviceApply.setBillNo(billCommonInvoiceRegister.getSourceNo());
		BillBalanceInvoiceApply inoviceApply = billBalanceInvoiceApplyService.findById(billBalanceInoviceApply);
		String requestId = BillBalanceInvoiceRegister.class.getSimpleName();
		//调用单据编号拼接处理方法，返回最终的单据编号
		String billNo = commonUtilService.getNewBillNoCompanyNo(inoviceApply.getSalerNo(),null,requestId);
//		if(!CollectionUtils.isEmpty(applyList)){//根据开票申请基本信息获取发票登记基本信息
		if(null != inoviceApply){
//			BillBalanceInvoiceApply billBalanceInvoiceApply = (BillBalanceInvoiceApply)applyList.get(0);
			billCommonInvoiceRegister.setBillNo(billNo);
			billCommonInvoiceRegister.setAmount(inoviceApply.getAmount());
			billCommonInvoiceRegister.setPreInvoice(inoviceApply.getPreInvoice());
			billCommonInvoiceRegister.setSalerNo(inoviceApply.getSalerNo());
			billCommonInvoiceRegister.setBuyerNo(inoviceApply.getBuyerNo());
			billCommonInvoiceRegister.setSalerName(inoviceApply.getSalerName());
			billCommonInvoiceRegister.setBuyerName(inoviceApply.getBuyerName());
			billCommonInvoiceRegister.setBillDate(inoviceApply.getPostPayDate());
			billCommonInvoiceRegister.setInvoiceType(inoviceApply.getInvoiceType().intValue());
			billCommonInvoiceRegister.setOrganNo(inoviceApply.getOrganNo());
			billCommonInvoiceRegister.setOrganName(inoviceApply.getOrganName());
			billCommonInvoiceRegister.setMonth(inoviceApply.getMonth());
		}
		//根据开票申请号查询源单信息获取结算类型
		List<BillBalanceInvoiceSource> sourceList = billBalanceInvoiceSourceService.findByBiz(null, params);
		if(!CollectionUtils.isEmpty(sourceList)){
			BillBalanceInvoiceSource billBalanceInvoiceSource = (BillBalanceInvoiceSource)sourceList.get(0);
			billCommonInvoiceRegister.setBalanceType(billBalanceInvoiceSource.getBalanceType());
		}else{//源单为空，则表示为空白单
			billCommonInvoiceRegister.setBalanceType(BalanceTypeEnums.APPLY_BILL_BLANK.getTypeNo());
		}
		billCommonInvoiceRegister.setStatus(0);//制单状态
		billCommonInvoiceRegister.setId(UUIDGenerator.getUUID());
		billCommonInvoiceRegister.setCreateTime(new Date());
		billCommonInvoiceRegisterService.add(billCommonInvoiceRegister);
		
		//回写开票申请的发票号
		BillBalanceInvoiceApply billBalanceInvoiceApply = new BillBalanceInvoiceApply();
		billBalanceInvoiceApply.setBillNo(billCommonInvoiceRegister.getSourceNo());
		billBalanceInvoiceApply.setInvoiceRegisterNo(billCommonInvoiceRegister.getBillNo());
		billBalanceInvoiceApply.setStatus(3);
		billBalanceInvoiceApplyService.updateByPrimaryKeySelective(billBalanceInvoiceApply);
		return billNo ;
	}
	
	@Override
	public List<LookupVo> findByAllCustomer(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)throws ManagerException {
		try {
			return billCommonInvoiceRegisterService.findByAllCustomer(page,orderByField, orderBy, params);
		} catch (ServiceException e) {
		}
		return null;
	}

	@Override
	public int selectAllCustomerCount(Map<String, Object> params) throws ManagerException {
		int i=0;
		try {
			i= billCommonInvoiceRegisterService.findAllCustomerCount(params);
		} catch (ServiceException e) {
		}
		return i;
	}

	@Override
	public int selectCountByBalanceNo(Map<String, Object> params) throws ManagerException {
		try {
			return billCommonInvoiceRegisterService.selectCountByBalanceNo(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BillCommonInvoiceRegister> selectListByBalanceNo(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return billCommonInvoiceRegisterService.selectListByBalanceNo(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	/**
	 * 开票申请列表选中开票申请记录，直接点击“生成发票”按钮，根据选择的开票申请信息，按开票方+收票方+结算类型+发票类型+是否预开票 属性区分，
	 * 是否开成一笔发票信息还是多笔发票信息
	 * @param list 选中的开票申请记录
	 * @param userName 操作人名称，用于作创建人
	 * @return 产生的记录数
	 * @throws ManagerException
	 */
	public List<BillCommonInvoiceRegister> addInoviceRegisters(List<Object> list,String userName) throws ManagerException {
		List<BillCommonInvoiceRegister> returnResult = new ArrayList<BillCommonInvoiceRegister>();
		try{
			//<saler,  <buyer,List<module>>>
			Map<String, List<BillBalanceInvoiceApply>> applyListMap = new HashMap<String, List<BillBalanceInvoiceApply>>();
			//按照开票方+收票方将结算单合并
			for (Object obj : list) {
				BillBalanceInvoiceApply apply = (BillBalanceInvoiceApply) obj;
				// 以开票方及收票方作为Key，区分需要开票的信息
				String key = apply.getSalerNo() +"|"+ apply.getBuyerNo() +"|"+ apply.getInvoiceType()
						+"|"+ apply.getBalanceType() +"|"+ apply.getPreInvoice();
				if(!applyListMap.containsKey(key)){
					
					List<BillBalanceInvoiceApply> balanceInvoiceApplyGenerateList = new ArrayList<BillBalanceInvoiceApply>();
					balanceInvoiceApplyGenerateList.add(apply);
					
					applyListMap.put(key, balanceInvoiceApplyGenerateList);
				}else{
					List<BillBalanceInvoiceApply> balanceInvoiceApplyGenerateList = applyListMap.get(key);
					balanceInvoiceApplyGenerateList.add(apply);
					
					applyListMap.put(key, balanceInvoiceApplyGenerateList);
				}
			}
			Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
			systemParamSetParams.put("paramCode", "AI_Register_Dlt_Create");
			systemParamSetParams.put("status", 1);
			// 取出按开票方及收票方作为Key 存放的记录
			Iterator<String> iterator = applyListMap.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				
				List<BillBalanceInvoiceApply> balanceInvoiceApplyList = applyListMap.get(key);
				if(!CollectionUtils.isEmpty(balanceInvoiceApplyList)){
					BillCommonInvoiceRegister invoiceRegister = new BillCommonInvoiceRegister();
					String billNo = commonUtilService.getNewBillNoCompanyNo(balanceInvoiceApplyList.get(0).getSalerNo(),
							null,BillBalanceInvoiceRegister.class.getSimpleName());
					invoiceRegister.setRemark("");
					invoiceRegister.setBillNo(billNo);
					invoiceRegister.setSalerNo(balanceInvoiceApplyList.get(0).getSalerNo());
					invoiceRegister.setSalerName(balanceInvoiceApplyList.get(0).getSalerName());
					invoiceRegister.setBuyerNo(balanceInvoiceApplyList.get(0).getBuyerNo());
					invoiceRegister.setBuyerName(balanceInvoiceApplyList.get(0).getBuyerName());
					if(null != balanceInvoiceApplyList.get(0).getInvoiceType()){
						invoiceRegister.setInvoiceType(balanceInvoiceApplyList.get(0).getInvoiceType().intValue());
					}
					invoiceRegister.setPreInvoice(balanceInvoiceApplyList.get(0).getPreInvoice());
					invoiceRegister.setStatus(0);
					invoiceRegister.setOrganNo(balanceInvoiceApplyList.get(0).getOrganNo());
					invoiceRegister.setOrganName(balanceInvoiceApplyList.get(0).getOrganName());
					invoiceRegister.setMonth(balanceInvoiceApplyList.get(0).getMonth());
					Date date = new Date();
					invoiceRegister.setBillDate(date);
					invoiceRegister.setCreateTime(date);
					invoiceRegister.setCreateUser(userName);
					
					if(StringUtils.isNotBlank(balanceInvoiceApplyList.get(0).getBalanceType())){
						invoiceRegister.setBalanceType(Integer.parseInt(balanceInvoiceApplyList.get(0).getBalanceType()));
					}
					
					//查询系统参数
					systemParamSetParams.put("businessOrganNo", balanceInvoiceApplyList.get(0).getSalerNo());
					String paramValue = systemParamSetManager.findSystemParamByParma(null, systemParamSetParams);
					BigDecimal totalAmount = new BigDecimal(0);
					for (BillBalanceInvoiceApply billBalanceInvoiceApply : balanceInvoiceApplyList) {
						if(billBalanceInvoiceApply.getAmount() != null){
							totalAmount = totalAmount.add(billBalanceInvoiceApply.getAmount());
						}
						//根据系统参数判断是否生成发票明细,除设置为不生成的，其他默认生成
						if(!"0".equals(paramValue) ){
							//根据开票申请号，取得按大类分明细信息
							Map<String,Object> params = new HashMap<String,Object>();
							params.put("billNo", billBalanceInvoiceApply.getBillNo());
							List<BillBalanceInvoiceDtl> dtlList = billBalanceInvoiceDtlManager.findByBiz(null, params);
							if(!CollectionUtils.isEmpty(dtlList)){
								int count = 0;
								for (BillBalanceInvoiceDtl billBalanceInvoiceDtl : dtlList) {
									BillCommonRegisterDtl registerDtl = new BillCommonRegisterDtl();
									registerDtl.setBillNo(billNo);
									registerDtl.setBrandName(billBalanceInvoiceDtl.getBrandNo());
									registerDtl.setBrandName(billBalanceInvoiceDtl.getBrandName());
									registerDtl.setCategoryNo(billBalanceInvoiceDtl.getCategoryNo());
									registerDtl.setCategoryName(billBalanceInvoiceDtl.getCategoryName());
									registerDtl.setNoTaxAmount(billBalanceInvoiceDtl.getNoTaxAmount());
									registerDtl.setQty(billBalanceInvoiceDtl.getQty());
									registerDtl.setTaxAmount(billBalanceInvoiceDtl.getTaxAmount());
									registerDtl.setTaxRate(billBalanceInvoiceDtl.getTaxRate());
									registerDtl.setEstimatedAmount(billBalanceInvoiceDtl.getEstimatedAmount());
									registerDtl.setInvoiceAmount(billBalanceInvoiceDtl.getSendAmount());
									if(null != billBalanceInvoiceDtl.getQty() && billBalanceInvoiceDtl.getQty() != 0 ){
										registerDtl.setPrice(billBalanceInvoiceDtl.getSendAmount().divide(new BigDecimal(billBalanceInvoiceDtl.getQty()), RoundingMode.HALF_DOWN));
									}
									registerDtl.setInvoiceName(billBalanceInvoiceDtl.getSalerName());
									registerDtl.setId(UUIDGenerator.getUUID());
									registerDtl.setSalerNo(invoiceRegister.getSalerNo());
									count += billCommonRegisterDtlservice.add(registerDtl);
								}
								log.info("新增明细,单据号:"+billNo+",结果:"+count);
							}
						}
						BillBalanceInvoiceApply invoiceApply = new BillBalanceInvoiceApply();
						invoiceApply.setBillNo(billBalanceInvoiceApply.getBillNo());
						invoiceApply.setInvoiceRegisterNo(billNo);
						invoiceApply.setStatus(3);
						// 回写开票申请的发票号
						billBalanceInvoiceApplyService.updateByPrimaryKeySelective(invoiceApply);
					}
					invoiceRegister.setAmount(totalAmount);
					invoiceRegister.setId(UUIDGenerator.getUUID());
					billCommonInvoiceRegisterService.add(invoiceRegister);
					log.info("新增主表信息,单据号:"+billNo);
					invoiceRegister.setSourceNo(balanceInvoiceApplyList.get(0).getBillNo());
					invoiceRegister.setBalanceNo(billNo);
					returnResult.add(invoiceRegister);
				}
			}
		}catch(ServiceException e){
			BillCommonInvoiceRegister invoiceRegister = new BillCommonInvoiceRegister();
			invoiceRegister.setRemark("系统异常，发票登记生成失败,请联系管理员。");
			returnResult.add(invoiceRegister);
			throw new ManagerException(e);
		}
		return returnResult;	
	}
	
	@Override
	public List<BillCommonInvoiceRegister> getByInvoice(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		List<BillCommonInvoiceRegister> list = billCommonInvoiceRegisterService.getByInvoice(page, orderByField, orderBy, params);
		Map<String, Object> map = new HashMap<String, Object>();
		for(BillCommonInvoiceRegister b:list){
			map.put("billNo", b.getBillNo());
			List<BillCommonInvoiceRegister> temp = billCommonInvoiceRegisterService.getShopAndBrand(map);
			if(!CollectionUtils.isEmpty(temp)){
				for(BillCommonInvoiceRegister b1:temp){
					b.setShortName(b1.getShortName());
					b.setBrandName(b1.getBrandName());
					break;
				}
			}
		}
		return list;
	}

	@Override
	public int getByInvoiceCount(Map<String, Object> params)
			throws ManagerException {
		return billCommonInvoiceRegisterService.getCountByInvoice(params);
	}
	
	@Override
	public void updateConfirm(List<BillCommonInvoiceRegister> invoiceRegisterList,String user) throws ManagerException {
		for (BillCommonInvoiceRegister billCommonInvoiceRegister : invoiceRegisterList) {
			billCommonInvoiceRegisterService.updateInvoice(billCommonInvoiceRegister.getBillNo(),billCommonInvoiceRegister.getDtlId(), user);
		}
	}

//	                   已经改为取开票申请
//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录
//	 * @return
//	 */
//	@Override
//	public List<BillCommonInvoiceRegister> findInvoiceRegisterForPayment(SimplePage page, Map<String, Object> params)throws ManagerException {
//		try {
//			return billCommonInvoiceRegisterService.findInvoiceRegisterForPayment(page, params);
//		} catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//	}
//
//	/**
//	 * 在团购预付款登记里，根据结算公司和客户查询，取得发票号的信息记录数
//	 * @param page
//	 * @param params 暂时只查询预开票为2（是）及结算类型为团购的记录数
//	 * @return
//	 */
//	@Override
//	public int findInvoiceregisterCountForPayment(Map<String, Object> params)throws ManagerException {
//		try {
//			return billCommonInvoiceRegisterService.findInvoiceregisterCountForPayment(params);
//		} catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//	}
//	
//	/**
//	 * 根据发票单据号修改发票是否使用标识，0＝未使用；1＝已使用
//	 * @param params
//	 */
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
//	public void updateUseFlagByBillNo(Map<String,Object> params) throws ManagerException {
//		try {
//			billCommonInvoiceRegisterService.updateUseFlagByBillNo(params);
//		} catch (Exception e) {
//			throw new ManagerException(e.getMessage(),e);
//		}
//	}
}