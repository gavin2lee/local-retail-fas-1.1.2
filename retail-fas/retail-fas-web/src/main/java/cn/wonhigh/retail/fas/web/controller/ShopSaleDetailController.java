package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.manager.ShopSaleDetailManager;
import cn.wonhigh.retail.pos.api.client.utils.CommonUtils;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/shop_sale_detail")
@ModuleVerify("30170013")
public class ShopSaleDetailController extends ExcelImportController<ItemSaleDtlDto>{
	
	@Resource
	private ShopSaleDetailManager shopSaleDetailManager;
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShopSaleDetailController.class);
	
	private static Integer ALL_RECORD = 2;
	
	@Override
	protected CrudInfo init() {
		// TODO Auto-generated method stub
		return new CrudInfo("IndepShop_management/sale_detail/",
				shopSaleDetailManager);
	}
	
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String,Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		
		Map<String, Object> params = builderParams(req, model);
		params.put("invoiceNoFlag", ALL_RECORD);
		params.put("invoiceNo", null);
		List<ItemSaleDtlDto> itemSaleDtlDtos = null;
		
		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = shopSaleDetailManager.queryShopSaleDetailListRemote(params);
		
		if(orderAndReturnExMainDtlDtoList == null || orderAndReturnExMainDtlDtoList.getResult() == null ||  orderAndReturnExMainDtlDtoList.getResult().size() < 1){
			return null;
		}
		List<POSOrderAndReturnExMainDtlDto> OrderAndReturnExMainList = orderAndReturnExMainDtlDtoList.getResult();
		itemSaleDtlDtos = shopSaleDetailManager.converDateToViewData(OrderAndReturnExMainList);
		
		int total = orderAndReturnExMainDtlDtoList.getTotal();
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", itemSaleDtlDtos);
		return obj;
	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List list) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected List<ItemSaleDtlDto> queryExportData(Map<String,Object> params) throws ManagerException {
		// TODO Auto-generated method stub
		params.put("pageSize", Integer.MAX_VALUE);
		params.put("pageNumber", 1);
		params.put("invoiceNoFlag", ALL_RECORD);
		params.put("invoiceNo", null);
		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = shopSaleDetailManager.queryShopSaleDetailListRemote(params);
		if(orderAndReturnExMainDtlDtoList == null || orderAndReturnExMainDtlDtoList.getResult() == null ||  orderAndReturnExMainDtlDtoList.getResult().size() < 1){
			return null;
		}
		List<POSOrderAndReturnExMainDtlDto> OrderAndReturnExMainList = orderAndReturnExMainDtlDtoList.getResult();
		
		for(POSOrderAndReturnExMainDtlDto dto : OrderAndReturnExMainList){
			LOGGER.info(" 店铺编码  --> "+dto.getShopNo()+" , 店铺名称 --> "+dto.getShopName());
		}
		
		List<ItemSaleDtlDto> itemSaleDtlDtos = shopSaleDetailManager.converDateToViewData(OrderAndReturnExMainList);
		return itemSaleDtlDtos;
	}
	
	@Override
	protected void selectManagerMenthod(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ManagerException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		POSOcOrderParameterDto ocOrderParameterDto = new POSOcOrderParameterDto();
		List<String> shopNoList = new ArrayList<String>();

		try {
			String companyNo = params.get("companyNo").toString();
			String shopNo = params.get("shopNo") == null ? "" : params.get("shopNo").toString();
			String startTime = params.get("createTimeStart").toString();
			String endTime = params.get("createTimeEnd").toString();
			String orderNo = params.get("orderNo").toString();
			
			if(shopNo != null && !"".equals(shopNo)){
				String[] shopNos = shopNo.split(",");
				shopNoList = Arrays.asList(shopNos);
			}else{
				List<Shop> shopList =shopSaleDetailManager.getAllShopByComanyNo(companyNo);
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

			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(new Integer(30));
			statusList.add(new Integer(41));
			ocOrderParameterDto.setStatusList(statusList);

		} catch (Exception e) {
			LOGGER.error("Get shops sale detail Failed ..");
			throw new ManagerException(e.getMessage(), e);
		}
		
		params = ocOrderParameterDto.getParams();
		if (CommonUtils.hasValue(shopNoList)) {
			params.put("shopNoList", shopNoList);
		}
		params.put("invoiceNoFlag_4", ALL_RECORD);
		
		this.shopSaleDetailManager.queryShopSaleDetailList(page, params, handler);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void queryResultMap(Map vals) {
		vals.put("orderNo", vals.get("odOrderNo"));
		Integer i = Integer.parseInt((String) vals.get("orderBillType"));
		Integer businessType = (Integer) vals.get("businessType");
		String orderBillType = null;
		switch(i){
			case 0:
				if(businessType.intValue() == 0){
					orderBillType = "正常销售";
				}else if(businessType.intValue() == 1){
					orderBillType = "跨店销售";
				}else if(businessType.intValue() == 2){
					orderBillType = "商场团购";
				}else if(businessType.intValue() == 3){
					orderBillType = "内购";
				}else if(businessType.intValue() == 6){
					orderBillType = "特卖销售";
				}else{
					orderBillType = "其他销售";
				}
				break;
			case 1:
				orderBillType = "换货";
				break;
			case 2:
				orderBillType = "退货";
				break;
		}
		vals.put("orderBillType",orderBillType);
		vals.put("itemNo", vals.get("odItemCode"));
		vals.put("itemName", vals.get("odItemName"));
		
		
		BigDecimal qty = BigDecimal.valueOf((Integer)vals.get("dtlQty")) ;
		vals.put("qty", qty);
		BigDecimal tagPrice = new BigDecimal(String.valueOf(vals.get("odTagPrice")));
		vals.put("tagPrice", tagPrice);
		if(null != tagPrice && null != qty){
			vals.put("tagPriceAmount", tagPrice.multiply(qty));
		}
		BigDecimal salePrice = new BigDecimal(String.valueOf(vals.get("odSalePrice")));
		vals.put("salePrice", salePrice);
		if(null != salePrice && null != qty){
			vals.put("salePriceAmount", salePrice.multiply(qty));
		}
		BigDecimal settlePrice = new BigDecimal(String.valueOf(vals.get("odSettlePrice")));
		vals.put("settlePrice", settlePrice);
		if(null != settlePrice && null != qty){
			vals.put("dealAmount", settlePrice.multiply(qty));
		}
	}
}
