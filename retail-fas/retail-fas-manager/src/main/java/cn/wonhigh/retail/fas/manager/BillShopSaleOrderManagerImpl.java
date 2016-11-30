package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.model.BillSalesOutstandingAnalysis;
import cn.wonhigh.retail.fas.common.model.BillShopSaleOrder;
import cn.wonhigh.retail.fas.common.model.POSOrderAndReturnExMainDtl;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.service.BillShopSaleOrderService;
import cn.wonhigh.retail.fas.service.OrderMainService;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.mps.api.client.dto.pageable.PaginationDto;
import cn.wonhigh.retail.mps.api.client.dto.promotion.ProMainDto;
import cn.wonhigh.retail.mps.api.client.dto.promotion.ProMainParamDto;
import cn.wonhigh.retail.mps.api.client.service.promotion.PromotionApi;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderMainDto;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderParameterDto;
import cn.wonhigh.retail.oc.common.api.dto.OrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.oc.common.api.service.OrderMainApi;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-05 10:01:20
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
@Service("billShopSaleOrderManager")
class BillShopSaleOrderManagerImpl extends BaseCrudManagerImpl implements BillShopSaleOrderManager {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BillShopSaleOrderManagerImpl.class);
	
    @Resource
    private BillShopSaleOrderService billShopSaleOrderService;
    
//    @Resource
//    private HeadquarterCostMaintainService headquarterCostMaintainService;
//    
//    @Resource
//    private RegionCostMaintainService regionCostMaintainService;
    
    @Resource
    private OrderMainManager orderMainManager;
    
	@Resource
	private OrderMainApi orderMainApi;
	
//	@Resource
//	private OrderMainService orderMainService;
	
	@Resource
	private ShopService shopService;
	
	@Resource
    private PromotionApi promotionApi;

    @Override
    public BaseCrudService init() {
        return billShopSaleOrderService;
    }

	@Override
	public int updateBalanceNo(BillShopSaleOrder billShopSaleOrder) {
		return billShopSaleOrderService.updateBalanceNo(billShopSaleOrder);
	}

	@Override
	public List<BillShopSaleOrder> selSumByCategory(
			BillShopSaleOrder billShopSaleOrder) {
		return billShopSaleOrderService.selSumByCategory(billShopSaleOrder);
	}

	public POSOcPagingDto<POSOrderAndReturnExMainDtlDto> queryShopSaleDetailListRemote(Map<String, Object> params) throws ManagerException{
	POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
	String pageSizeTemp = params.get("pageSize").toString();
	String pageNumberTemp = params.get("pageNumber").toString();
	if(pageSizeTemp != null && !"".equals(pageSizeTemp)){
		pageDto.setPageSize(Integer.valueOf(pageSizeTemp));
	}
	if(pageNumberTemp != null && !"".equals(pageNumberTemp)){
		pageDto.setPageNo(Integer.valueOf(pageNumberTemp));
	}
	
//		OcSimplePageDto pageDto = new OcSimplePageDto(page.getPageNo(),page.getPageSize(),sortColumn,sortOrder);
	    POSOcOrderParameterDto ocOrderParameterDto = new POSOcOrderParameterDto();
		List<String> shopNoList = new ArrayList<String>();
		cn.wonhigh.retail.fas.common.dto.POSOcPagingDto<cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			if(params.get("shopNo") != null && !"".equals(params.get("shopNo"))){
				String shops = params.get("shopNo").toString();
			    if(shops != null && !"".equals(shops)){
				    String[] shopstr = null;
				    if(StringUtils.isNotEmpty(shops) &&  shops.substring(0, 1).equals("(")){
				    	shops =shops.substring(2, shops.length()-2);
				    	shopNoList.add(shops);
				    }else if(StringUtils.isNotEmpty(shops) &&  !shops.substring(0, 1).equals("(")){
				    	shopstr=shops.split(",");
				       for(int i=0;i<shopstr.length;i++){
				    	   shopNoList.add(shopstr[i]);
				       }
				    }
		        }
			}
			
			String brandNos = StringUtils.isBlank(params.get("brandNos")+"") ? "" : String.valueOf(params.get("brandNos"));
			if(brandNos != "null" && brandNos != null && !"".equals(brandNos)){
				List<String> brandNoList = Arrays.asList(brandNos.split(","));
				ocOrderParameterDto.setBrandNoList(brandNoList);
			} 
			
			Object startDate = params.get("startDate");
			if(startDate != null && !"".equals(startDate)){
				if(startDate instanceof Date) {
					ocOrderParameterDto.setStartOutDate((Date)startDate);
				} else {
					ocOrderParameterDto.setStartOutDate(dateFormat.parse(startDate.toString()));
				}
			 }
			
			Object endDate = params.get("endDate");
			if(endDate != null && !"".equals(endDate)){
				if(endDate instanceof Date) {
					ocOrderParameterDto.setEndOutDate((Date)endDate);
				} else {
					ocOrderParameterDto.setEndOutDate(dateFormat.parse(endDate.toString()));
				}
			 }
			List<Integer> businessTypeList = new ArrayList<Integer>();
			businessTypeList.add(0);
			businessTypeList.add(1);
			businessTypeList.add(2);
			businessTypeList.add(6);
			ocOrderParameterDto.setBusinessTypeList(businessTypeList);
			 
			 List<Integer> statusList = new ArrayList<Integer>();
			 statusList.add(30);
			 statusList.add(41);
			 ocOrderParameterDto.setStatusList(statusList);
			 
			 Object orderNo = params.get("orderNo");
			 if(orderNo != null){
			    ocOrderParameterDto.setOrderNo(orderNo.toString());
			 }
//			 if (!CollectionUtils.isEmpty(shopNoList)) {
			    orderAndReturnExMainDtlDtoList = orderMainManager.findList4OrderMain(pageDto, shopNoList, ocOrderParameterDto,2,null);
//			 }
			
//			OcPagingDto<OrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = getMockData();
			return orderAndReturnExMainDtlDtoList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Get shops sale detail Failed ..");
			throw new ManagerException(e.getMessage(), e);
		}
	}
	

public OcOrderMainDto findList4SumOcOrderByParameter(OcOrderParameterDto ocOrderParameterDto,List<String> shopNoList) throws ManagerException{
	try {
		orderMainApi.findList4SumOcOrderByParameter(ocOrderParameterDto, shopNoList);
	} catch (RpcException e) {
		throw new ManagerException(e.getMessage(), e);

	}
	return null;
}

	public List<BillShopSaleOrder> converDateToViewData(List<OrderAndReturnExMainDtlDto> OrderAndReturnExMainList){		
		List<BillShopSaleOrder> shopSaleOrders = new ArrayList<BillShopSaleOrder>();		
		if(OrderAndReturnExMainList == null){
			return null;
		}
		for(OrderAndReturnExMainDtlDto orderAndReturnExMainDtlDto : OrderAndReturnExMainList){			
			BillShopSaleOrder shopSaleOrder = new BillShopSaleOrder();
			shopSaleOrder.setOrderNo(orderAndReturnExMainDtlDto.getOrderNo());
			shopSaleOrder.setShopName(orderAndReturnExMainDtlDto.getShopName());
			shopSaleOrder.setShopNo(orderAndReturnExMainDtlDto.getShopNo());
			shopSaleOrder.setAssistantName(null);
			shopSaleOrder.setOrderNo(orderAndReturnExMainDtlDto.getOrderNo());
			shopSaleOrder.setOldOrderNo(orderAndReturnExMainDtlDto.getOldOrderNo());
			shopSaleOrder.setOutDate(orderAndReturnExMainDtlDto.getOutDate());			
			String str = null;
			switch(orderAndReturnExMainDtlDto.getOrderBillType()){
				case 0:
					str = "正常销售";
					break;
				case 1:
					str = "换货";
					break;
				case 2:
					str = "退货";
			}
			shopSaleOrder.setOrderBillTypeName(str);	
			shopSaleOrder.setItemNo(orderAndReturnExMainDtlDto.getItemCode());
			shopSaleOrder.setItemName(orderAndReturnExMainDtlDto.getItemName());
			shopSaleOrder.setTagPrice(orderAndReturnExMainDtlDto.getTagPrice());
//			itemDtl.setTagPriceAmount( itemDtl.getTagPrice().multiply(new BigDecimal(itemDtl.getQty())) );
			shopSaleOrder.setSalePrice(orderAndReturnExMainDtlDto.getSalePrice());
//			itemDtl.setSalePriceAmount(orderAndReturnExMainDtlDto.getSalePrice().multiply(new BigDecimal(itemDtl.getQty())));
			shopSaleOrder.setQty(orderAndReturnExMainDtlDto.getQty());
			shopSaleOrder.setSettlePrice(orderAndReturnExMainDtlDto.getSettlePrice());
			shopSaleOrder.setAmount(orderAndReturnExMainDtlDto.getAmount());			
			shopSaleOrders.add(shopSaleOrder);
		}
		return shopSaleOrders;
	}
	
	
	public List<ItemSaleDtlDto> converDateToViewDataItem(List<POSOrderAndReturnExMainDtlDto> OrderAndReturnExMainList){
		List<ItemSaleDtlDto> itemSaleDtl = new ArrayList<ItemSaleDtlDto>();
		if(OrderAndReturnExMainList == null){
			return null;
		}
		for(POSOrderAndReturnExMainDtlDto orderAndReturnExMainDtlDto : OrderAndReturnExMainList){
			
			ItemSaleDtlDto itemDtl = new ItemSaleDtlDto();
			itemDtl.setOrderNo(orderAndReturnExMainDtlDto.getOrderNo());
			itemDtl.setShopName(orderAndReturnExMainDtlDto.getShopName());
			itemDtl.setShopNo(orderAndReturnExMainDtlDto.getShopNo());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", itemDtl.getShopNo());
			Shop  shop = shopService.selectSubsiInfo(params);
			if(shop != null && !"".equals(shop)) {
				itemDtl.setCompanyNo(shop.getCompanyName());
				itemDtl.setZoneNo(shop.getZoneName());
				itemDtl.setBizCityNo(shop.getOrganName());
				itemDtl.setBsgroupsNo(shop.getBsgroupsName());
				itemDtl.setRegionNo(shop.getRegionName());
				itemDtl.setMallNo(shop.getMallName());
			}
			itemDtl.setAssistantName(null);
			itemDtl.setOrderNo(orderAndReturnExMainDtlDto.getOrderNo());
			itemDtl.setOldOrderNo(orderAndReturnExMainDtlDto.getOldOrderNo());
			itemDtl.setOutDate(orderAndReturnExMainDtlDto.getOutDate());
			
			String str = null;
			switch(orderAndReturnExMainDtlDto.getOrderBillType()){
				case 0:
					str = "正常销售";
					break;
				case 1:
					str = "换货";
					break;
				case 2:
					str = "退货";
			}
			itemDtl.setOrderBillType(str);
			itemDtl.setItemNo(orderAndReturnExMainDtlDto.getItemCode());
			itemDtl.setItemName(orderAndReturnExMainDtlDto.getItemName());
			itemDtl.setTagPrice(orderAndReturnExMainDtlDto.getTagPrice());
			itemDtl.setQty(orderAndReturnExMainDtlDto.getDtlQty());
			if(itemDtl.getQty() != null && itemDtl.getTagPrice() != null){
				itemDtl.setTagPriceAmount( itemDtl.getTagPrice().multiply(new BigDecimal(itemDtl.getQty())) );
			}
			itemDtl.setSalePrice(orderAndReturnExMainDtlDto.getSalePrice());
			if(itemDtl.getQty() != null && itemDtl.getSalePrice() != null){
				itemDtl.setSalePriceAmount(itemDtl.getSalePrice().multiply(new BigDecimal(itemDtl.getQty())));
			}
			itemDtl.setSettlePrice(orderAndReturnExMainDtlDto.getSettlePrice());
			itemDtl.setAmount(orderAndReturnExMainDtlDto.getDtlAmount());//.getAmount());
			itemDtl.setRemark(orderAndReturnExMainDtlDto.getRemark());
			itemDtl.setProName(orderAndReturnExMainDtlDto.getProName());
			itemDtl.setBrandNo(orderAndReturnExMainDtlDto.getBrandName());
			itemDtl.setSkuNo(orderAndReturnExMainDtlDto.getSkuNo());
			itemDtl.setItemCode(orderAndReturnExMainDtlDto.getItemCode());
			itemDtl.setSizeNo(orderAndReturnExMainDtlDto.getSizeNo());
			itemDtl.setDiscPrice(orderAndReturnExMainDtlDto.getDiscPrice());
			
			if(itemDtl.getSalePrice() != null && itemDtl.getDiscPrice() != null){
				itemDtl.setDiscAmount(itemDtl.getSalePrice().subtract(itemDtl.getDiscPrice()));
			}
			
			itemDtl.setSettleAmount(orderAndReturnExMainDtlDto.getSettleAmount());
			itemDtl.setReducePrice(orderAndReturnExMainDtlDto.getReducePrice());
			itemDtl.setPrefPrice(orderAndReturnExMainDtlDto.getPrefPrice());
			itemDtl.setProNo(orderAndReturnExMainDtlDto.getProNo());
			itemDtl.setDiscount(orderAndReturnExMainDtlDto.getDiscount());
			BigDecimal  discount = orderAndReturnExMainDtlDto.getDiscount();
//			 DecimalFormat df = new DecimalFormat("#");
			 String  discountN;
			 DecimalFormat df = new DecimalFormat("0.000#");
			 if(null!=discount){
			     
			     BigDecimal discount0 = discount.setScale(0,RoundingMode.FLOOR);
			     BigDecimal discount4 = discount.setScale(4, RoundingMode.HALF_UP);
			     
			     if(discount0.compareTo(discount4)==0){
			    	 discountN=discount0.toString()+"%";	
			     }else{
			    	 
			    	 discountN=df.format(discount)+"%";
			     }
			     itemDtl.setDiscountName(discountN);
		     }
			
			 
			itemDtl.setVipDiscount(orderAndReturnExMainDtlDto.getVipDiscount());
			itemDtl.setBaseScore(orderAndReturnExMainDtlDto.getBaseScore());
			itemDtl.setProScore(orderAndReturnExMainDtlDto.getProScore());
			itemDtl.setItemDiscount(orderAndReturnExMainDtlDto.getItemDiscount());
			itemDtl.setItemDiscountStr(orderAndReturnExMainDtlDto.getItemDiscount()+"%");
			itemDtl.setRemark(orderAndReturnExMainDtlDto.getRemark());
			
//	       处理总部价  地区价  成本价
//			Map<String, Object> headquarterCostParams = new HashMap<String, Object>();
//			headquarterCostParams.put("itemNo", itemDtl.getItemNo());
//			headquarterCostParams.put("effectiveTime", itemDtl.getOutDate());
//			HeadquarterCostMaintain headquarterCostMaintain = null;
//			try {
//				headquarterCostMaintain = headquarterCostMaintainService.getLastEffectiveModel(headquarterCostParams);
//			} catch (ServiceException e1) {
//				e1.printStackTrace();
//			}
//			
//			if(headquarterCostMaintain != null) {
//				if(headquarterCostMaintain.getHeadquarterCost() != null){
//					itemDtl.setHeadquarterCost(headquarterCostMaintain.getHeadquarterCost());
//				}
//			}
//			
//			BigDecimal regionCost = null;
//			try {
//				regionCost = regionCostMaintainService.findRegionCost(itemDtl.getItemNo(), itemDtl.getZoneNo(), itemDtl.getOutDate());
//			} catch (ServiceException e) {
//				e.printStackTrace();
//			}
//
//			if(null!=regionCost){
//				itemDtl.setRegionCost(regionCost);
//			}
//			itemDtl.setHeadquarterCost(itemDtl.getHeadquarterCost().multiply(new BigDecimal(itemDtl.getQty())));
			itemDtl.setRegionCost(orderAndReturnExMainDtlDto.getRegionCost().multiply(new BigDecimal(itemDtl.getQty())));
			itemDtl.setUnitCost(orderAndReturnExMainDtlDto.getUnitCost().multiply(new BigDecimal(itemDtl.getQty())));
			itemDtl.setLaunchType(orderAndReturnExMainDtlDto.getLaunchType());
			if("1".equals(itemDtl.getLaunchType())){
				itemDtl.setLaunchTypeName("公司活动");
			}else if("2".equals(itemDtl.getLaunchType())){
				itemDtl.setLaunchTypeName("商场活动");
			}
			itemDtl.setActivityDescribe(orderAndReturnExMainDtlDto.getActivityDescribe());
			itemDtl.setRateCode(orderAndReturnExMainDtlDto.getRateCode());
			itemDtl.setProStartDate(orderAndReturnExMainDtlDto.getProStartDate());
			itemDtl.setProEndDate(orderAndReturnExMainDtlDto.getProEndDate());
			itemDtl.setBillingCode(orderAndReturnExMainDtlDto.getBillingCode());
			itemDtl.setCategoryName(orderAndReturnExMainDtlDto.getCategoryName());
			itemDtl.setSaleAmount(orderAndReturnExMainDtlDto.getSaleAmount());
			itemDtl.setDiscountAmount(orderAndReturnExMainDtlDto.getDiscountAmount());
			itemDtl.setDiscountCode(orderAndReturnExMainDtlDto.getDiscountCode());
			itemDtl.setAssistantNo(orderAndReturnExMainDtlDto.getAssistantNo());
			itemDtl.setAssistantName(orderAndReturnExMainDtlDto.getAssistantName());
//			if(CollectionUtils.isNotEmpty(OrderAndReturnExMainList)){
//				try {
//					getPromotionInfo(orderAndReturnExMainDtlDto.getProNo(),orderAndReturnExMainDtlDto.getShopNo());
//				} catch (RpcException e) {
//					e.printStackTrace();
//				}
//			}
			itemSaleDtl.add(itemDtl);
		}
		return itemSaleDtl;
	}

	
	/**
     * 根据活动编号，调用接口获取活动的相关信息
     * @param list
     * @throws RpcException 
     */
    private void getPromotionInfo(String proNo,String shopNo) throws RpcException{
    	ProMainParamDto proMainParamDto  = new ProMainParamDto ();
			proMainParamDto.setProNo(proNo);
			proMainParamDto.setShopNo(shopNo);
			PaginationDto<ProMainDto> proMainDto = promotionApi.findList4ProMain(proMainParamDto);
			
			List<ProMainDto> proMainList = proMainDto.getList();
			if(CollectionUtils.isNotEmpty(proMainList)){
				ItemSaleDtlDto itemDtl = new ItemSaleDtlDto();
				itemDtl.setProStartDate(proMainList.get(0).getStartTime());
				itemDtl.setProEndDate(proMainList.get(0).getEndTime());
//				pOSOrderAndReturnExMainDtlDto.setStrength(proMainList.get(0).getStrength());
//				if(StringUtils.isNotBlank(proMainList.get(0).getVirtualFlagStr())){
//					pOSOrderAndReturnExMainDtlDto.setVirtualFlagStr(proMainList.get(0).getVirtualFlagStr());
//				}
//				pOSOrderAndReturnExMainDtlDto.setProperties(proMainList.get(0).getPropertiesStr());
//				pOSOrderAndReturnExMainDtlDto.setActivityType(proMainList.get(0).getActivityTypeStr());
				itemDtl.setLaunchType(proMainList.get(0).getLaunchTypeStr());
//				billSalesOutstandingAnalysis.setRateTypeStr(proMainList.get(0).getRateTypeStr());
			}
    }
    
	@Override
	public OcOrderMainDto findList4SumOcOrderByParameter(
			Map<String, Object> params) {
		    OcOrderMainDto ocOrderMainDto = new OcOrderMainDto();
		    OcOrderParameterDto ocOrderParameterDto= new OcOrderParameterDto();
			List<String> shopNoList = new ArrayList<String>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				if(params.get("shopNo") != null && !"".equals(params.get("shopNo"))){
			    String shops = params.get("shopNo").toString();
			    if(shops != null && !"".equals(shops)){
			    String[] shopstr = null;
			    if(StringUtils.isNotEmpty(shops) &&  shops.substring(0, 1).equals("(")){
			    	shops =shops.substring(2, shops.length()-2);
			    	shopNoList.add(shops);
			    }else if(StringUtils.isNotEmpty(shops) &&  !shops.substring(0, 1).equals("(")){
			    	shopstr=shops.split(",");
			       for(int i=0;i<shopstr.length;i++){
			    	   shopNoList.add(shopstr[i]);
			       }
			    }
		        }
				}
//					shopNoList.add(shops);
			    
//				shopNoList.add("BSCA01");
				Object startDate = params.get("startOutDate");
				if(startDate != null && !"".equals(startDate)){
					if(startDate instanceof Date) {
						ocOrderParameterDto.setStartOutDate((Date)startDate);
					} else {
						ocOrderParameterDto.setStartOutDate(dateFormat.parse(startDate.toString()));
					}
				 }
				
				Object endDate = params.get("endOutDate");
				if(endDate != null && !"".equals(endDate)){
					if(endDate instanceof Date) {
						ocOrderParameterDto.setEndOutDate((Date)endDate);
					} else {
						ocOrderParameterDto.setEndOutDate(dateFormat.parse(endDate.toString()));
					}
				 }
				List<Integer> businessTypeList = new ArrayList<Integer>();
				businessTypeList.add(0);
				businessTypeList.add(1);
				businessTypeList.add(2);
				businessTypeList.add(6);
				ocOrderParameterDto.setBusinessTypeList(businessTypeList);
				 
				 List<Integer> statusList = new ArrayList<Integer>();
				 statusList.add(30);
				 statusList.add(41);
				 ocOrderParameterDto.setStatusList(statusList);
				 if (!CollectionUtils.isEmpty(shopNoList)) {
					 ocOrderMainDto = orderMainApi.findList4SumOcOrderByParameter(ocOrderParameterDto, shopNoList);
				 }
				
//				OcPagingDto<OrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = getMockData();
				return ocOrderMainDto;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error("Get shops sale detail Failed ..");
			}
			return ocOrderMainDto;
		}

//	@Override
//	public POSOrderAndReturnExMainDtl getSumOcOrderByParameter(Map<String, Object> params) throws ManagerException {
//		try {
//			return orderMainService.getSumOcOrderByParameter(params);
//		} catch (ServiceException e) {
//			throw new ManagerException(e.getMessage(), e);
//		}
//	}

	@Override
	public int selectDiscountSumCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectDiscountSumCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<String> selectDiscountSumColumn(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectDiscountSumColumn(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> selectDiscountSumTotal(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectDiscountSumTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> selectDiscountSumData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectDiscountSumData(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectPayWaySumCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWaySumCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> selectPayWaySumColumn(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWaySumColumn(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> selectPayWaySumTotal(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWaySumTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> selectPayWaySumData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWaySumData(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public int selectPayWayOrderCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayOrderCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> selectPayWayOrderTotal(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayOrderTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> selectPayWayOrderData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayOrderData(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectPayWayOrderBrandCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayOrderBrandCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> selectPayWayOrderBrandTotal(
			Map<String, Object> params) throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayOrderBrandTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> selectPayWayOrderBrandData(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayOrderBrandData(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectPayWayDayCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayDayCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> selectPayWayDayTotal(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayDayTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> selectPayWayDayData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayDayData(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectPayWayMonthCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayMonthCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> selectPayWayMonthTotal(Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayMonthTotal(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> selectPayWayMonthData(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return billShopSaleOrderService.selectPayWayMonthData(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
}