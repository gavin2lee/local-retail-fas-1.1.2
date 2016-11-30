package cn.wonhigh.retail.fas.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.model.ExceptionPriceBill;
import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.service.ExceptionPriceBillService;
import cn.wonhigh.retail.fas.service.OrderUnitService;
import cn.wonhigh.retail.gms.api.service.CalculateWeightedCostApi;
import cn.wonhigh.retail.gms.api.vo.PriceCheckConditionDto;
import cn.wonhigh.retail.gms.common.dto.PagingDto;
import cn.wonhigh.retail.gms.common.model.PriceExceptionBill;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("exceptionPriceCheckManager")
public class ExceptionPriceCheckManagerImpl extends BaseCrudManagerImpl implements ExceptionPriceCheckManager {

	@Resource
	private ExceptionPriceBillService exceptionPriceBillService;
	
	@Resource
	private OrderUnitService orderUnitService ;
	
	@Resource
	private CalculateWeightedCostApi calculateWrightedCostApi;
	
	@Override
    public BaseCrudService init() {
        return exceptionPriceBillService;
    }
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ExceptionPriceCheckManagerImpl.class);
	
	private SimpleDateFormat dateFormatToDay = new SimpleDateFormat("yyyy-MM-dd");
    
	//GMS需要异常价格检查的单据类型
    private static final Integer[] billTypeNoList = {1301,1304,1333,1371,1372};

    @Override
	public boolean checkExceptionPrice(Map<String, Object> params) {
    	boolean result = false;
		try {
			String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
			String billType = params.get("billType") == null ? null : params.get("billType").toString();
			String createTimeStart = params.get("createTimeStart") == null ? null : params.get("createTimeStart").toString();
			String createTimeEnd = params.get("createTimeEnd") == null ? null : params.get("createTimeEnd").toString();
			String billNo = params.get("billNo") == null ? null : params.get("billNo").toString();
			String itemNo = params.get("itemNo") == null ? null : params.get("itemNo").toString();
			
			if(StringUtils.isEmpty(companyNo)){
				return false;
			}
			List<String> orderUnitNoList = getOrderUnitByCompanyNo(companyNo);
			
			List<Integer> billTypeList = null;
			if(StringUtils.isNotEmpty(billType)){
				billTypeList = new ArrayList<Integer>();
				if(billType.indexOf(",") != -1){
					String[] billTypeArray = billType.split(",");
					for(String str : billTypeArray){
						billTypeList.add(Integer.parseInt(str));
					}
				}else{
					billTypeList.add(Integer.parseInt(billType));
				}
			}else{
				billTypeList = Arrays.asList(billTypeNoList);
			}
			
			PriceCheckConditionDto priceCheckConditionDto = new PriceCheckConditionDto();
			priceCheckConditionDto.setOrderUnitNos(orderUnitNoList);
			priceCheckConditionDto.setBillNo(billNo);
			priceCheckConditionDto.setItemNo(itemNo);
			priceCheckConditionDto.setBillDateStart(createTimeStart);
			priceCheckConditionDto.setBillDateEnd(createTimeEnd);
			priceCheckConditionDto.setMultiBillTypes(billTypeList);
			priceCheckConditionDto.setShardingFlag(params.get("shardingFlag").toString());
			
			//调用GMS接口，校验异常单据
			result = calculateWrightedCostApi.checkPriceExceptionBill(priceCheckConditionDto);
			
		} catch (Exception e) {
			LOGGER.error("调用GMS接口异常",e.getMessage(),e);
		}
		
		return result;
	}
	
	private List<String> getOrderUnitByCompanyNo(String companyNo) throws ServiceException{
		List<String> orderUnitNos = new ArrayList<String>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("companyNo", companyNo);
		List<OrderUnit> orderUnitList = orderUnitService.findByBiz(null, params);
		
		if(!CollectionUtils.isEmpty(orderUnitList)){
			for(OrderUnit orderUnit : orderUnitList){
				orderUnitNos.add(orderUnit.getOrderUnitNo());
			}
		}
		return orderUnitNos;
	}
	
	@Override
	public Map<String,Object> queryPriceExceptionBillList(Map<String,Object> params) throws ManagerException{
		try {
			Map<String,Object> dataMap = new HashMap<String, Object>();
			
			String billNo = params.get("billNo") == null ? null : params.get("billNo").toString();
			String billType = params.get("billType") == null ? null : params.get("billType").toString();
			String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
			String itemNo = params.get("itemNo") == null ? null : params.get("itemNo").toString();
			String billDateStart = params.get("createTimeStart") == null ? null : params.get("createTimeStart").toString();
			String billDateEnd = params.get("createTimeEnd") == null ? null : params.get("createTimeEnd").toString();
			
			int pageNo  = 1;
			int pageSize = 10;
			if(params.get("page")!=null){
				pageNo = Integer.parseInt(params.get("page").toString());
			}
			if(params.get("rows")!=null){
				pageSize = Integer.parseInt(params.get("rows").toString());
			}
			List<String> orderUnitNoList = getOrderUnitByCompanyNo(companyNo);
			if (CollectionUtils.isEmpty(orderUnitNoList)) {
				dataMap.put("total", 0);
				dataMap.put("rows", new ArrayList<ExceptionPriceBill>());
				return dataMap;
			}
			
			PriceCheckConditionDto priceCheckConditionDto = new PriceCheckConditionDto();
			List<Integer> billTypeList = new ArrayList<Integer>();
			if(billType != null && !"".equals(billType)){
				if(billType.indexOf(",") != -1){
					String[] billTypeArray = billType.split(",");
					for(String str : billTypeArray){
						billTypeList.add(Integer.parseInt(str));
					}
				}else{
					billTypeList.add(Integer.parseInt(billType));
				}
			}else{
				billTypeList = Arrays.asList(billTypeNoList);
			}
			if(params.get("shardingFlag")!=null){
				priceCheckConditionDto.setShardingFlag(params.get("shardingFlag").toString());
			}
			
			priceCheckConditionDto.setOrderUnitNos(orderUnitNoList);
			priceCheckConditionDto.setItemNo(itemNo);
			priceCheckConditionDto.setBillNo(billNo);
			priceCheckConditionDto.setMultiBillTypes(billTypeList);
			priceCheckConditionDto.setBillDateStart(billDateStart);
			priceCheckConditionDto.setBillDateEnd(billDateEnd);
			priceCheckConditionDto.setPage(pageNo);
			priceCheckConditionDto.setPageSize(pageSize);
			PagingDto<PriceExceptionBill> pagingDto=null;
			List<PriceExceptionBill> list=null;
			if(params.get("exports")!=null){
				list=calculateWrightedCostApi.getPriceExceptionExportData(priceCheckConditionDto);
			}else{
				pagingDto = calculateWrightedCostApi.queryPriceExceptionBillList(priceCheckConditionDto);
			}
			
			if (null != pagingDto) {
				dataMap.put("total", pagingDto.getTotalSize());
				dataMap.put("rows", convertGMSExceptionBillsToFAS(pagingDto.getList()));
			}else if(null!=list){
				dataMap.put("rows",convertGMSExceptionBillsToFAS(list));
			}else {
				dataMap.put("total", 0);
				dataMap.put("rows", new ArrayList<ExceptionPriceBill>());
			}
			return dataMap;
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage(),e);
			throw new ManagerException(e);
		} catch (RpcException e) {
			LOGGER.error(e.getMessage(),e);
			throw new ManagerException(e);
		}
		
	}
	
	private List<ExceptionPriceBill> convertGMSExceptionBillsToFAS(List<PriceExceptionBill> gmsExceptionBills) {
		List<ExceptionPriceBill> fasExceptionPriceBills = new ArrayList<ExceptionPriceBill>();
		if (!CollectionUtils.isEmpty(gmsExceptionBills)) {
			for (PriceExceptionBill priceExceptionBill : gmsExceptionBills) {
				ExceptionPriceBill fasPriceBill = new ExceptionPriceBill();
				fasPriceBill.setShardingFlag(priceExceptionBill.getShardingFlag());
				fasPriceBill.setId(priceExceptionBill.getId());
				fasPriceBill.setBaseCost(priceExceptionBill.getBaseCost());
				fasPriceBill.setBillDate(priceExceptionBill.getBillDate());
				fasPriceBill.setBillNo(priceExceptionBill.getBillNo());
				fasPriceBill.setCost(priceExceptionBill.getCost());
				fasPriceBill.setCostFrom(priceExceptionBill.getCostFrom());
				fasPriceBill.setCostFromName(priceExceptionBill.getCostFrom().intValue() ==  1 ? "采购价" : "地区价");
				fasPriceBill.setItemCode(priceExceptionBill.getItemCode());
				fasPriceBill.setItemName(priceExceptionBill.getItemName());
				fasPriceBill.setItemNo(priceExceptionBill.getItemNo());
				fasPriceBill.setOrderUnitNo(priceExceptionBill.getOrderUnitNo());
				fasPriceBill.setQty(priceExceptionBill.getQty());
				fasPriceBill.setSizeNo(priceExceptionBill.getSizeNo());
				fasPriceBill.setSkuNo(priceExceptionBill.getSkuNo());
				fasPriceBill.setBillType(priceExceptionBill.getBillType());
				fasPriceBill.setBillTypeName(BillTypeEnums.getNameByNo(priceExceptionBill.getBillType()));
				fasPriceBill.setSupplierNo(priceExceptionBill.getSupplierNo());
				fasPriceBill.setExceptionReason(priceExceptionBill.getExceptionReason());
				fasPriceBill.setCreateTime(priceExceptionBill.getCreateTime());
				
				fasExceptionPriceBills.add(fasPriceBill);
			}
		}
		return fasExceptionPriceBills;
	}

	@Override
	public boolean updatePriceExceptionBillList(Map<String,Object> params) throws ManagerException{
		
		List<PriceExceptionBill> priceExceptionBillList = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.setDateFormat(dateFormatToDay);
			String checkedList = StringUtils.isEmpty(params.get("inserted").toString()) ? "" : params.get("inserted").toString();
			if(StringUtils.isNotEmpty(checkedList)) {
				priceExceptionBillList = new ArrayList<PriceExceptionBill>();
				String[] str = checkedList.split("&");
				for(String s : str){
					ExceptionPriceBill PriceCheckConditionDto =  objectMapper.readValue(s, ExceptionPriceBill.class);
					
					PriceExceptionBill priceExceptionBillDto = new PriceExceptionBill();
					priceExceptionBillDto.setBaseCost(PriceCheckConditionDto.getBaseCost());
					priceExceptionBillDto.setBillDate(PriceCheckConditionDto.getBillDate());
					priceExceptionBillDto.setBillNo(PriceCheckConditionDto.getBillNo());
					priceExceptionBillDto.setBillType(PriceCheckConditionDto.getBillType());
					priceExceptionBillDto.setCost(PriceCheckConditionDto.getCost());
					priceExceptionBillDto.setCostFrom(PriceCheckConditionDto.getCostFrom());
					priceExceptionBillDto.setId(PriceCheckConditionDto.getId());
					priceExceptionBillDto.setItemCode(PriceCheckConditionDto.getItemCode());
					priceExceptionBillDto.setItemNo(PriceCheckConditionDto.getItemNo());
					priceExceptionBillDto.setOrderUnitNo(PriceCheckConditionDto.getOrderUnitNo());
					priceExceptionBillDto.setSupplierNo(PriceCheckConditionDto.getSupplierNo());
					priceExceptionBillDto.setSkuNo(PriceCheckConditionDto.getSkuNo());
					priceExceptionBillDto.setSizeNo(PriceCheckConditionDto.getSizeNo());
					priceExceptionBillDto.setShardingFlag(PriceCheckConditionDto.getShardingFlag());
					
					priceExceptionBillList.add(priceExceptionBillDto);
				}
			}
			
			if(priceExceptionBillList != null && priceExceptionBillList.size() > 0){
				return calculateWrightedCostApi.updatePriceExceptionBillList(priceExceptionBillList);
			}
		} catch (Exception e) {
			LOGGER.error("更新GMS异常价格失败。" + e.getMessage());
			throw new ManagerException(e);
		}
		return false;
	}
	
}
