package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.dto.MPSPaymentDto;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupOrderPaywayDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.model.DepositCashCumulativeDifference;
import cn.wonhigh.retail.fas.common.model.IndependShopDayReport;
import cn.wonhigh.retail.fas.common.model.POSOcGroupOrderPayway;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.DepositCashCumulativeDifferenceService;
import cn.wonhigh.retail.fas.service.DepositCashService;
import cn.wonhigh.retail.fas.service.OrderMainService;
import cn.wonhigh.retail.mps.api.client.dto.operation.SalePayway;
import cn.wonhigh.retail.mps.api.client.service.operation.SaleCommonApi;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("shopDayReportManagerImpl")
public class ShopDayReportManagerImpl extends BaseCrudManagerImpl implements ShopDayReportManager {

	@Resource
	private OrderMainManager orderMainManager ;
	
	@Resource
	private OrderMainService orderMainService;
	@Resource
	private DepositCashService depositCashService;
	
	@Resource
	private ShopManager shopManager ;
	
	@Resource
	private SaleCommonApi saleCommonApi;
	
	@Resource
	private DepositCashCumulativeDifferenceService depositCashCumulativeDifferenceService;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected BaseCrudService init() {
		return null;
	}
	
	public List<IndependShopDayReport> queryEachDayReport(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException{
		
		List<IndependShopDayReport> independShopDayReportList = new ArrayList<IndependShopDayReport>();
		POSOcOrderParameterDto ocOrderParameterDto = new POSOcOrderParameterDto();
		try {
			String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
			String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
			String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
        	String isSubTotal = params.get("isSubTotal") == null ? null : params.get("isSubTotal").toString();
        	List<Integer> businessTypeList = new ArrayList<Integer>();
        	
        	//0-正常销售 1-跨店销售
        	businessTypeList.add(new Integer(0));
    		businessTypeList.add(new Integer(1));
    		
    		Date createTimeEnd = dateFormat.parse(params.get("createTimeEnd").toString());
        	Date createTimeStart = dateFormat.parse(params.get("createTimeStart").toString());
    		ocOrderParameterDto.setEndOutDate(createTimeEnd);
    		ocOrderParameterDto.setStartOutDate(createTimeStart);
    		ocOrderParameterDto.setBusinessTypeList(businessTypeList);
    		List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(new Integer(30));
			statusList.add(new Integer(41));
			ocOrderParameterDto.setStatusList(statusList);
			
    		POSOcSimplePageDto simplePage = new POSOcSimplePageDto();
    		simplePage.setPageNo(1);
    		simplePage.setPageSize(Integer.MAX_VALUE);
    		List<String> shopNoList = new ArrayList<String>();
    		
    		Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("companyNo", companyNo);
			if(organNo != null && !"".equals(organNo)){
				paramMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNo));
			}
			if(shopNo != null && !"".equals(shopNo)){
			    paramMap.put("shopNoLists",  FasUtil.formatInQueryCondition(shopNo));
			}
    		List<Shop>  shopList =  shopManager.findByBiz(null, paramMap);
    		if(shopList != null && shopList.size() > 0) {
   			 for(Shop shop : shopList) {
   				shopNoList.add(shop.getShopNo());
   			 }
    		}
    		
    		POSOcPagingDto<POSOcGroupOrderPaywayDto> ocPaging = orderMainManager.findList4OcGroupOrderPayway(simplePage,shopNoList,ocOrderParameterDto);

    		//封装数据  （实现按店铺和结算日  汇总多种结算（明细表）形式的数据于同一条数据中显示，并排序）
    		if(ocPaging != null && ocPaging.getResult() != null && ocPaging.getResult().size() > 0) {
    			List<POSOcGroupOrderPaywayDto> ocGroupOrderPaywayDtoList =  ocPaging.getResult();
				if(ocGroupOrderPaywayDtoList != null && ocGroupOrderPaywayDtoList.size() > 0){
				
					TreeMap<String, TreeMap<String, List<POSOcGroupOrderPaywayDto>>> maps = new TreeMap<String, TreeMap<String, List<POSOcGroupOrderPaywayDto>>>();
					for(POSOcGroupOrderPaywayDto paywayDto : ocGroupOrderPaywayDtoList){
						String outTime = dateFormat.format(paywayDto.getOutDate());
						String shopNoTe = paywayDto.getShopNo();
						String key = shopNoTe;
						String childKey = outTime;
						List<POSOcGroupOrderPaywayDto> paywayDtoList = null;
						TreeMap<String, List<POSOcGroupOrderPaywayDto>> listMap = null;
						if(!maps.containsKey(key)) {
							paywayDtoList = new ArrayList<POSOcGroupOrderPaywayDto>();
							listMap = new TreeMap<String, List<POSOcGroupOrderPaywayDto>>();
						}else {
							listMap = maps.get(key);
							if(!listMap.containsKey(childKey)) {
								paywayDtoList = new ArrayList<POSOcGroupOrderPaywayDto>();
							}else {
								paywayDtoList = listMap.get(childKey);
							}
						}
						paywayDtoList.add(paywayDto);
						listMap.put(childKey, paywayDtoList);
						maps.put(key, listMap);
					}
					
					for (Map.Entry<String, TreeMap<String, List<POSOcGroupOrderPaywayDto>>> listMapTemp : maps.entrySet()) { 
						IndependShopDayReport subShopDayReport = new IndependShopDayReport();
						for (Map.Entry<String, List<POSOcGroupOrderPaywayDto>> paywayDtaoList : listMapTemp.getValue().entrySet()) { 
							IndependShopDayReport shopDayReport = new IndependShopDayReport();
							shopDayReport.setTotalAmount(new BigDecimal(0));
							for(POSOcGroupOrderPaywayDto ocGroupOrderPaywayDtoTemp : paywayDtaoList.getValue()) {
								shopDayReport.setShopNo(ocGroupOrderPaywayDtoTemp.getShopNo());
								shopDayReport.setShopName(ocGroupOrderPaywayDtoTemp.getShopName());
								shopDayReport.setOutDate(ocGroupOrderPaywayDtoTemp.getOutDate());
								fillObject(ocGroupOrderPaywayDtoTemp,shopDayReport,subShopDayReport);
							}
							independShopDayReportList.add(shopDayReport);
						}
						//增加小计对象
						if("true".equals(isSubTotal)) {
							subShopDayReport.setShopName("小计：");
							independShopDayReportList.add(subShopDayReport);	
						}
						
					}
				}
			}
		}catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);

		}
		return independShopDayReportList;
	}
	
	@Override
	public List<IndependShopDayReport> queryEachDayReportLocal(Map<String,Object> params) throws ManagerException{
		
		try{
			
			String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
			String startTime = params.get("createTimeStart") == null ? null : params.get("createTimeStart").toString();
        	String endTime = params.get("createTimeEnd") == null ? null : params.get("createTimeEnd").toString();
        	int pageNo = params.get("page") == null ? 0 : Integer.parseInt(params.get("page").toString());
        	int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize").toString());
			
        	if(shopNo == null || "".equals(shopNo) || startTime == null || "".equals(startTime) || endTime == null || "".equals(endTime)){
        		return null;
        	}
        	
        	SimplePage simplePage = new SimplePage();
        	simplePage.setPageNo(pageNo);
    		simplePage.setPageSize(pageSize);
			
    		List<IndependShopDayReport> list=new ArrayList<IndependShopDayReport>();
    		//现金类型从pos中财务已确认取数据
    		List<IndependShopDayReport> cashList=depositCashCumulativeDifferenceService.findByPayNameFromPOS(simplePage, null, null, params);
    		list.addAll(cashList);
    		String[] payName={"现金券","银行卡","商场卡","商场券","预收款",};//不同支付方式金额,
    		for(String pn:payName){
    			params.put("payName", pn);
    			List<IndependShopDayReport> l = depositCashCumulativeDifferenceService.findByPayName(simplePage, null, null, params);
    			list.addAll(l);
    		}
    		//其他支付方式
    		params.put("payName", "其它");
    		List<IndependShopDayReport> otherList= depositCashCumulativeDifferenceService.findByPayNameOther(simplePage, null, null, params);
    		list.addAll(otherList);
    		list=sum(list);
    		return list;
		}catch(Exception e){
			throw new ManagerException(e.getMessage(), e);

		}
	}
	
	private List<IndependShopDayReport> sum(List<IndependShopDayReport> IndependShopDayReportList){
		List<IndependShopDayReport> list = new ArrayList<IndependShopDayReport>();
		IndependShopDayReport temp = null;
		Map<String, IndependShopDayReport> map = new LinkedHashMap<String, IndependShopDayReport>();
		for (IndependShopDayReport independShopDayReport : IndependShopDayReportList) {
			if (null == map.get(independShopDayReport.getOutDate()+independShopDayReport.getPayName())) {
				temp = new IndependShopDayReport();
				this.initIndependShopDayReport(temp);
				temp.setShopNo(independShopDayReport.getShopNo());
				temp.setShopName(independShopDayReport.getShopName());
				temp.setCompanyNo(independShopDayReport.getCompanyNo());
				temp.setCompanyName(independShopDayReport.getCompanyName());
				temp.setOutDate(independShopDayReport.getOutDate());//设置日期
				temp.setPayName(independShopDayReport.getPayName());
				map.put(independShopDayReport.getOutDate()+independShopDayReport.getPayName(), temp);
			}
			this.sumToIndependShopDayReport(map.get(independShopDayReport.getOutDate()+independShopDayReport.getPayName()), independShopDayReport);
		}
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			IndependShopDayReport independShopDayReportObject = map.get(key);
			list.add(independShopDayReportObject);
		}
		return list;
	}
	
	private void initIndependShopDayReport(IndependShopDayReport temp) {
		//销售
		temp.setCashAmount(new BigDecimal(0d));
		temp.setCashCouponAmount(new BigDecimal(0d));
		temp.setCreditCardAmount(new BigDecimal(0d));
		temp.setMallCardAmount(new BigDecimal(0d));
		temp.setMallCouponAmount(new BigDecimal(0d));
		temp.setAdvancePay(new BigDecimal(0d));
		temp.setOtherAmount(new BigDecimal(0d));
		//实收
		temp.setCashAmount_(new BigDecimal(0d));
		
		temp.setSaleAmount(new BigDecimal(0d));
		temp.setAmount(new BigDecimal(0d));
		temp.setPoundage(new BigDecimal(0d));//手续费（除现金外各个支付方式累计之和 ）
		
	}

	private void sumToIndependShopDayReport(IndependShopDayReport mainIndependShopDayReport,
			IndependShopDayReport addIndependShopDayReport) {
		//销售金额累加
		if(mainIndependShopDayReport.getOutDate().equals(addIndependShopDayReport.getOutDate())&&mainIndependShopDayReport.getPayName().equals(addIndependShopDayReport.getPayName())){
			if (null != mainIndependShopDayReport.getSaleAmount() && null != addIndependShopDayReport.getSaleAmount()) {
				if("现金".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setCashAmount(mainIndependShopDayReport.getCashAmount().add(addIndependShopDayReport.getSaleAmount()));						
				}else if("现金券".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setCashCouponAmount(mainIndependShopDayReport.getCashCouponAmount().add(addIndependShopDayReport.getSaleAmount()));						
				}else if("银行卡".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setCreditCardAmount(mainIndependShopDayReport.getCreditCardAmount().add(addIndependShopDayReport.getSaleAmount()));			
				}else if("商场卡".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setMallCardAmount(mainIndependShopDayReport.getMallCardAmount().add(addIndependShopDayReport.getSaleAmount()));			
				}else if("商场券".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setMallCouponAmount(mainIndependShopDayReport.getMallCouponAmount().add(addIndependShopDayReport.getSaleAmount()));			
				}else if("预收款".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setAdvancePay(mainIndependShopDayReport.getAdvancePay().add(addIndependShopDayReport.getSaleAmount()));						
				}else if("其它".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setOtherAmount(mainIndependShopDayReport.getOtherAmount().add(addIndependShopDayReport.getSaleAmount()));
				}
				//手续费累计
				if(null!=addIndependShopDayReport.getPoundage()&&!"现金".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setPoundage(mainIndependShopDayReport.getPoundage().add(addIndependShopDayReport.getPoundage()));
				}
			}
		}
		//实收金额累加
		if(mainIndependShopDayReport.getOutDate().equals(addIndependShopDayReport.getOutDate())&&mainIndependShopDayReport.getPayName().equals(addIndependShopDayReport.getPayName())){
			if (null != mainIndependShopDayReport.getAmount() && null != addIndependShopDayReport.getAmount()) {
				if("现金".equals(mainIndependShopDayReport.getPayName())){
					mainIndependShopDayReport.setCashAmount_(mainIndependShopDayReport.getCashAmount_().add(addIndependShopDayReport.getAmount()));						
				}
			}
		}
	}

	public List<IndependShopDayReport> dataConvert(List<DepositCashCumulativeDifference> depositCashCumulativeDifferenceList){
		
		List<IndependShopDayReport> independShopDayReportList = new ArrayList<IndependShopDayReport>();
		for(DepositCashCumulativeDifference dto : depositCashCumulativeDifferenceList){
			
			IndependShopDayReport independShopDayReport = new IndependShopDayReport();
			independShopDayReport.setShopNo(dto.getShopNo());
			independShopDayReport.setShopName(dto.getShopName());
			independShopDayReport.setOutDate(dto.getSaleOut());
			independShopDayReport.setCashAmount(dto.getCashAmount());
			independShopDayReport.setCashCouponAmount(dto.getCashCouponAmount());
			independShopDayReport.setCreditCardAmount(dto.getBankCardAmount());
			independShopDayReport.setMallCardAmount(dto.getMallCardAmount());
			independShopDayReport.setMallCouponAmount(dto.getMallCouponAmount());
			independShopDayReport.setAdvancePay(dto.getExpectCashAmount());
			independShopDayReport.setOtherAmount(dto.getOtherAmount());
			independShopDayReport.setTotalAmount(dto.getSaleTotalAmount());
			independShopDayReport.setDepositAmount(dto.getDepositCashTotalAmount());
			independShopDayReport.setCumulativeDifferenceAmount(dto.getDepositCashDifference());
			independShopDayReport.setCurrentDepositCashDefferenceAmount(dto.getCurrentDepositCashDifference());
			
			independShopDayReportList.add(independShopDayReport);
		}
		return independShopDayReportList;
	}
	
	
	public void fillObject(POSOcGroupOrderPaywayDto ocGroupOrderPaywayDtoTemp,IndependShopDayReport independShopDayReport,IndependShopDayReport subShopDayReport){
		
		if(PublicConstans.PAYCODE_CASHAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCashAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getCashAmount() == null) {
				subShopDayReport.setCashAmount(new BigDecimal(0));
			}
			subShopDayReport.setCashAmount(subShopDayReport.getCashAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()) );
		}else if(PublicConstans.PAYCODE_ADVANCEPAY.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setAdvancePay(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getAdvancePay() == null ) {
				subShopDayReport.setAdvancePay(new BigDecimal(0));
			}
			subShopDayReport.setAdvancePay(subShopDayReport.getAdvancePay().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_CASHCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCashCouponAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getCashCouponAmount() == null) {
				subShopDayReport.setCashCouponAmount(new BigDecimal(0));
			}
			subShopDayReport.setCashCouponAmount(subShopDayReport.getCashCouponAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_CREDITCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCreditCardAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());	
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getCreditCardAmount() == null) {
				subShopDayReport.setCreditCardAmount(new BigDecimal(0));
			}
			subShopDayReport.setCreditCardAmount(subShopDayReport.getCreditCardAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_MALLCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setMallCardAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getMallCardAmount() == null) {
				subShopDayReport.setMallCardAmount(new BigDecimal(0));
			}
			subShopDayReport.setMallCardAmount(subShopDayReport.getMallCardAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_OTHERAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setOtherAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getOtherAmount() == null) {
				subShopDayReport.setOtherAmount(new BigDecimal(0));
			}
			subShopDayReport.setOtherAmount(subShopDayReport.getOtherAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_MALLCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setMallCouponAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
			if(subShopDayReport.getMallCouponAmount() == null ) {
				subShopDayReport.setMallCouponAmount(new BigDecimal(0));
			}
			subShopDayReport.setMallCouponAmount(subShopDayReport.getMallCouponAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}
	}

	@Override
	public List<MPSPaymentDto> queryPaymentsList() throws ManagerException {
		List<MPSPaymentDto> paymentDtoList = null;
		 try {
			List<SalePayway> salePaywayList = saleCommonApi.getSalePayways();
			if(salePaywayList != null && salePaywayList.size() > 0){
				
				paymentDtoList = new ArrayList<MPSPaymentDto>();
				for(SalePayway salePayway : salePaywayList){
					
					MPSPaymentDto payment = new MPSPaymentDto();
					payment.setPayCode(salePayway.getPayCode());
					payment.setPayName(salePayway.getPayName());
					
					paymentDtoList.add(payment);
				}
				
			}
		} catch (RpcException e) {
			throw new ManagerException(e.getMessage(), e);

		}
		return paymentDtoList;
	}

	@Override
	public int findCountReport(Map<String, Object> params) throws ManagerException {
		POSOcOrderParameterDto ocOrderParameterDto = new POSOcOrderParameterDto();
		int count=0;
		try {
			String shopNo = params.get("shopNoTemp") == null ? null : params.get("shopNoTemp").toString();
			String organNo = params.get("organNoTemp") == null ? null : params.get("organNoTemp").toString();
			String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
        	List<Integer> businessTypeList = new ArrayList<Integer>();
        	//0-正常销售 1-跨店销售
        	businessTypeList.add(new Integer(0));
    		businessTypeList.add(new Integer(1));
    		
    		Date createTimeEnd = dateFormat.parse(params.get("createTimeEnd").toString());
        	Date createTimeStart = dateFormat.parse(params.get("createTimeStart").toString());
    		ocOrderParameterDto.setEndOutDate(createTimeEnd);
    		ocOrderParameterDto.setStartOutDate(createTimeStart);
    		ocOrderParameterDto.setShopNo(shopNo);
    		ocOrderParameterDto.setBusinessTypeList(businessTypeList);
    		List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(new Integer(30));
			statusList.add(new Integer(41));
			ocOrderParameterDto.setStatusList(statusList);
			
    		POSOcSimplePageDto simplePage = new POSOcSimplePageDto();
    		simplePage.setPageNo(1);
    		simplePage.setPageSize(Integer.MAX_VALUE);
    		List<String> shopNoList = new ArrayList<String>();
    		if(StringUtils.isNotEmpty(shopNo)){
    			if(shopNo.contains(",")){
    				String[] shopNos = shopNo.split(",");
    				for (String shopNoTemp : shopNos) {
    					shopNoList.add(shopNoTemp);
    				}
    			}else{
    				shopNoList.add(shopNo);
    			}
    		}
    		
//    		Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("companyNo", companyNo);
//			if(organNo != null && !"".equals(organNo)){
//				paramMap.put("organNos",  FasUtil.formatInQueryCondition(organNo));
//			}
//    		List<Shop>  shopList =  shopManager.findByBiz(null, paramMap);
//    		if(shopList != null && shopList.size() > 0) {
//   			 for(Shop shop : shopList) {
//   				shopNoList.add(shop.getShopNo());
//   			 }
//    		}
    		
    		Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("companyNo", companyNo);
			if(organNo != null && !"".equals(organNo)){
				paramMap.put("parentOrganNos",  FasUtil.formatInQueryCondition(organNo));
			}
			if(shopNo != null && !"".equals(shopNo)){
			    paramMap.put("shopNoLists",  FasUtil.formatInQueryCondition(shopNo));
			}
    		List<Shop>  shopList =  shopManager.findByBiz(null, paramMap);
    		if(!CollectionUtils.isEmpty(shopList)) {
   			 for(Shop shop : shopList) {
   				shopNoList.add(shop.getShopNo());
   			 }
    		}
    		POSOcPagingDto<POSOcGroupOrderPaywayDto> ocPaging = orderMainManager.findList4OcGroupOrderPayway(simplePage,shopNoList,ocOrderParameterDto);
           
    		if(ocPaging != null && ocPaging.getResult() != null && ocPaging.getResult().size() > 0){
    			count  = ocPaging.getResult().size();
			}
    		return count;
		}catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);

		}
	}

	@Override
	public int findShopDailyReportCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return orderMainService.findOrderPayWayCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e); 
		}
	}

	@Override
	public List<POSOcGroupOrderPayway> findShopDailyReportByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return orderMainService.findOrderPayWayList(page,sortColumn,sortOrder,params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<SaleOrderPayway> findShopDayReportByPage(
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return orderMainService.findSaleDayReportByPage(sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e); 
		}
	}
	
	@Override
	public List<SaleOrderPayway> findSaleDayReportForBrandByPage(
			String sortColumn, String sortOrder, Map<String, Object> params) throws ManagerException {
		try {
			return orderMainService.findSaleDayReportForBrandByPage(sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e); 
		}
	}
	
	@Override
	public SaleOrderPayway findShopDailyReportShowCount(
			Map<String, Object> params) {
		SaleOrderPayway saleOrderPayway = orderMainService.findShopDailyReportShowCount(params);
		BigDecimal paidinAmount = depositCashService.getPaidinAmountCount(params);
		if(paidinAmount!=null){
			saleOrderPayway.setS01(paidinAmount);
			saleOrderPayway.setD01(paidinAmount.subtract(saleOrderPayway.getP01()));
			saleOrderPayway.setAmount(saleOrderPayway.getAmount().add(paidinAmount));
			saleOrderPayway.setDiffAmount(saleOrderPayway.getDiffAmount().add(paidinAmount));
			saleOrderPayway.setSum(saleOrderPayway.getSum().add(paidinAmount));
		}else{
			saleOrderPayway.setS01(new BigDecimal(0d));
			saleOrderPayway.setD01(saleOrderPayway.getP01()==null?BigDecimal.ZERO:saleOrderPayway.getP01().negate());
		}
		
		return saleOrderPayway;
	}

	@Override
	public SaleOrderPayway findSaleDayReportForBrandCount(
			Map<String, Object> params) {
		return orderMainService.findSaleDayReportForBrandCount(params);//仅仅计算了销售的总计
	}

	@Override
	public List<Map<String, Object>> findList(Map<String, Object> params) throws ManagerException {
		try {
			return orderMainService.findList(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> findDayReportForBrandList(Map<String, Object> param) throws ManagerException {
		try {
			return orderMainService.findDayReportForBrandList(param);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}