package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.backend.data.core.DbHelper;
import cn.wonhigh.retail.fas.common.constans.POSPublicConstans;
import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.oc.common.api.service.OrderMainApi;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("shopSaleDetailManagerImpl")
public class ShopSaleDetailManagerImpl extends BaseCrudManagerImpl implements ShopSaleDetailManager {
	
	@Resource
    private ShopService shopService;
	
	@Resource
	private OrderMainApi orderMainApi;
	
	@Resource
	private OrderMainManager orderMainManager;
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShopSaleDetailManagerImpl.class);
	
	@Override
	protected BaseCrudService init() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public POSOcPagingDto<POSOrderAndReturnExMainDtlDto> queryShopSaleDetailListRemote(
			Map<String, Object> params) throws ManagerException {

		POSOcSimplePageDto pageDto = new POSOcSimplePageDto();

		POSOcOrderParameterDto ocOrderParameterDto = new POSOcOrderParameterDto();
		List<String> shopNoList = new ArrayList<String>();
		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			String invoiceNoFlagTemp = params.get("invoiceNoFlag") == null ? "2": params.get("invoiceNoFlag").toString();
			String invoiceNo = "";
			int invoiceNoFlag = Integer.parseInt(invoiceNoFlagTemp);
			if (!invoiceNoFlagTemp.equals("1")) {
				String companyNo = params.get("companyNo").toString();
				String shopNo = params.get("shopNo") == null ? "" : params.get("shopNo").toString();
				String startTime = params.get("createTimeStart").toString();
				String endTime = params.get("createTimeEnd").toString();
				String orderNo = params.get("orderNo").toString();
				
				if((companyNo == null || "".equals(companyNo)) && (shopNo == null || "".equals(shopNo))){
					return null;
				}
				
				if (startTime == null || "".equals(startTime) || endTime == null || "".equals(endTime)) {
					return null;
				}

				String pageSizeTemp = params.get("pageSize").toString();
				String pageNumberTemp = params.get("pageNumber").toString();
				if (pageSizeTemp != null && !"".equals(pageSizeTemp)) {
					pageDto.setPageSize(Integer.valueOf(pageSizeTemp));
				}
				if (pageNumberTemp != null && !"".equals(pageNumberTemp)) {
					pageDto.setPageNo(Integer.valueOf(pageNumberTemp));
				}

				if(shopNo != null && !"".equals(shopNo)){
					String[] shopNos = shopNo.split(",");
					shopNoList = Arrays.asList(shopNos);
				}else{
					List<Shop> shopList = getAllShopByComanyNo(companyNo);
					if (shopList == null || shopList.size() < 1) {
						return null;
					}
	
					// adding conditional
					for (Shop shop : shopList) {
						if (!shopNoList.contains(shop.getShopNo())) {
							shopNoList.add(shop.getShopNo());
						}
					}
				}
				
				ocOrderParameterDto.setOrderNo(orderNo);

				Date createTimeEnd = dateFormat.parse(endTime.toString());
				Date createTimeStart = dateFormat.parse(startTime.toString());
				ocOrderParameterDto.setEndOutDate(createTimeEnd);
				ocOrderParameterDto.setStartOutDate(createTimeStart);

				// 0-正常销售 1-跨店销售
				List<Integer> businessTypeList = new ArrayList<Integer>();
				Object obj = params.get("businessTypeList");
				if (obj != null) {
					ocOrderParameterDto.setBusinessTypeList(businessTypeList);
				} else {
					businessTypeList.add(new Integer(0));
					businessTypeList.add(new Integer(1));
					businessTypeList.add(new Integer(2));
					businessTypeList.add(new Integer(6));
					ocOrderParameterDto.setBusinessTypeList(businessTypeList);
				}
			} else {
				invoiceNo = params.get("invoiceNo") == null ? "" : params.get("invoiceNo").toString();
			}

			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(new Integer(30));
			statusList.add(new Integer(41));
			ocOrderParameterDto.setStatusList(statusList);

			// orderAndReturnExMainDtlDtoList =orderMainApi.findList4OrderMain(pageDto, shopNoList,ocOrderParameterDto);
			orderAndReturnExMainDtlDtoList = orderMainManager.findList4OrderMain(pageDto, shopNoList, ocOrderParameterDto, invoiceNoFlag,invoiceNo);

			return orderAndReturnExMainDtlDtoList;
		} catch (Exception e) {
			LOGGER.error("Get shops sale detail Failed ..");
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	public List<ItemSaleDtlDto> converDateToViewData(List<POSOrderAndReturnExMainDtlDto> OrderAndReturnExMainList){
		
		Integer busniessType_normal = 0;
		Integer busniessType_jumpShop = 1;
		Integer busniessType_special = 6;//特卖销售
		
		List<ItemSaleDtlDto> itemSaleDtl = new ArrayList<ItemSaleDtlDto>();
		if(OrderAndReturnExMainList == null){
			return null;
		}
		for(POSOrderAndReturnExMainDtlDto orderAndReturnExMainDtlDto : OrderAndReturnExMainList){
			
			ItemSaleDtlDto itemDtl = new ItemSaleDtlDto();
			itemDtl.setOrderNo(orderAndReturnExMainDtlDto.getOrderNo());
			itemDtl.setShopName(orderAndReturnExMainDtlDto.getShopName());
			itemDtl.setShopNo(orderAndReturnExMainDtlDto.getShopNo());
			itemDtl.setAssistantName(null);
			itemDtl.setOrderNo(orderAndReturnExMainDtlDto.getOrderNo());
			itemDtl.setOldOrderNo(orderAndReturnExMainDtlDto.getOldOrderNo());
			itemDtl.setOutDate(orderAndReturnExMainDtlDto.getOutDate());
			
			String str = null;
			switch(orderAndReturnExMainDtlDto.getOrderBillType()){
				//0-正常销售 1-跨店销售 2-退货
				case 0:
					if(orderAndReturnExMainDtlDto.getBusinessType().intValue() == busniessType_normal.intValue()){
						str = "正常销售";
					}else if(orderAndReturnExMainDtlDto.getBusinessType().intValue() == busniessType_jumpShop.intValue()){
						str = "跨店销售";
					}else if(orderAndReturnExMainDtlDto.getBusinessType().intValue() == 2){
						str = "商场团购";
					}else if(orderAndReturnExMainDtlDto.getBusinessType().intValue() == 3){
						str = "内购";
					}else if(orderAndReturnExMainDtlDto.getBusinessType().intValue() == busniessType_special.intValue()){
						str = "特卖销售";
					}else{
						str = "其他销售";
					}
					break;
				case 1:
					str = "换货";
					break;
				case 2:
					str = "退货";
					break;
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
			if(itemDtl.getSettlePrice() != null && itemDtl.getQty() != null){
				itemDtl.setDealAmount(itemDtl.getSettlePrice().multiply(new BigDecimal(itemDtl.getQty())));
			}
			
			itemDtl.setAmount(orderAndReturnExMainDtlDto.getAmount());
			itemDtl.setRemark(orderAndReturnExMainDtlDto.getRemark());
			
			itemSaleDtl.add(itemDtl);
		}
		return itemSaleDtl;
	}
	
	private int totalRows = 0;
	public int getTotalRows(){
		return totalRows;
	}
	
	public List<Shop> getAllShopByComanyNo(String companyNo) throws ServiceException{
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("companyNo", companyNo);
    	params.put("status", 1);
    	List<Shop> shopList = shopService.findByBiz(new Shop(), params);
    	if(shopList == null || shopList.size() < 1){
    		return null;
    	}
    	return shopList;
    }

	@Override
	public void queryShopSaleDetailList(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ManagerException {
		String statement = "cn.wonhigh.retail.fas.dal.database.OrderMainMapper.findOrderBillByPage";
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("page", page);
		ps.put("params", params);
		ps.put("orderByField", POSPublicConstans.ORDER_BY_FIELD_OUT_DATE);
		ps.put("orderBy", POSPublicConstans.ORDER_BY_DESC);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ManagerException(e);
		}
	}
}
