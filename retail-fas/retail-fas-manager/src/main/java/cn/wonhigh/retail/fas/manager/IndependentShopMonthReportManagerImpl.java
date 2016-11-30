package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupOrderPaywayDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.IndependentShopMonthReport;
import cn.wonhigh.retail.fas.common.model.SelfShopBankInfo;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.service.BillShopBalanceDeductService;
import cn.wonhigh.retail.fas.service.CreditCardTransactionDtlService;
import cn.wonhigh.retail.fas.service.SelfShopBankInfoService;
import cn.wonhigh.retail.fas.service.ShopService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("independentShopMonthReportManagerImpl")
public class IndependentShopMonthReportManagerImpl extends BaseCrudManagerImpl implements
		IndependentShopMonthReportManager {

	@Resource
	private BillShopBalanceDeductService billShopBalanceDeductService;
	
	@Resource
	private SelfShopBankInfoService selfShopBankInfoService;
	
	@Resource
	private OrderMainManager orderMainManager;
	
	@Resource
	private ShopService shopService;
	
	@Resource
	private CreditCardTransactionDtlService creditCardTransactionService;
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(IndependentShopMonthReportManagerImpl.class);
	
	private static final Integer BUSINESS_TYPE_NOMAL_SALE = 0;
	
	private static final Integer BUSINESS_TYPE_OTHER_SHOP_SALE = 1;
	
	private static final Integer BUSINESS_TYPE_ALL = 2;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected BaseCrudService init() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List<IndependentShopMonthReport> queryEachMonthShopReport(Map<String,Object> params) throws ManagerException {
		
		List<String> shopNoList = new ArrayList<String>();
		List<Integer> businessTypeList = new ArrayList<Integer>();
//		shopNoList.add("CA01R");
		
		POSOcOrderParameterDto orderParameterDto = new POSOcOrderParameterDto();
		List<IndependentShopMonthReport> independentShopMonthList = null;
		try {
			
			Object saleTypeTemp = params.get("saleType");
			Object shopNoTemp = params.get("shopNo");
			Object companyNoTemp = params.get("companyNo");
			Object startDateMonthTemp = params.get("createTimeStart");
			Object endDateMonthTemp = params.get("createTimeEnd");
			
			String saleType = null;
			String shopNo = null;
			String companyNo = null;
			String startDateMonthStr = null;
			String endDateMonthStr = null;
			
			if(saleTypeTemp != null){
				saleType = saleTypeTemp.toString();
			}
			if(shopNoTemp != null){
				shopNo = shopNoTemp.toString();
			}
			if(companyNoTemp != null){
				companyNo = companyNoTemp.toString();
			}
			if(startDateMonthTemp != null){
				startDateMonthStr = startDateMonthTemp.toString();
			}
			if(endDateMonthTemp != null){
				endDateMonthStr = endDateMonthTemp.toString();
			}
			
			if((shopNo == null || "".equals(shopNo)) && (companyNo == null || "".equals(companyNo)) ){
				return null;
			}
			if((startDateMonthStr == null || "".equals(startDateMonthStr)) || (endDateMonthStr == null || "".equals(endDateMonthStr))){
				return null;
			}
			
			
			if(shopNo != null && !"".equals(shopNo)){
				String[] shopNoArr = shopNo.split(",");
				for(String shopNoTem : shopNoArr){
					shopNoList.add(shopNoTem);
				}
			}else{
				List<Shop> shopList = getAllShopByComanyNo(companyNo);
				for(Shop shop : shopList){
	    			shopNoList.add(shop.getShopNo());
	    		}
			}
		
			if(saleType != null && !"".equals(saleType)){
				Integer businessType = Integer.parseInt(saleType);
				if(businessType.intValue() == BUSINESS_TYPE_NOMAL_SALE){
					businessTypeList.add(BUSINESS_TYPE_NOMAL_SALE);
				}else if (businessType.intValue() == BUSINESS_TYPE_OTHER_SHOP_SALE){
					businessTypeList.add(BUSINESS_TYPE_OTHER_SHOP_SALE);
				}else if(businessType.intValue() == BUSINESS_TYPE_ALL){
					businessTypeList.add(BUSINESS_TYPE_NOMAL_SALE);
					businessTypeList.add(BUSINESS_TYPE_OTHER_SHOP_SALE);
				}
			}else{
				businessTypeList.add(BUSINESS_TYPE_NOMAL_SALE);
				businessTypeList.add(BUSINESS_TYPE_OTHER_SHOP_SALE);
			}

			Date firstDayOfMonth = getTheFirstDayOfMonth(startDateMonthStr);
			Date lastDayOfMonth = getTheLastdayOfMonth(endDateMonthStr);
			
			orderParameterDto.setBusinessTypeList(businessTypeList);
			orderParameterDto.setStartOutDate(firstDayOfMonth);
			orderParameterDto.setEndOutDate(lastDayOfMonth);
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(new Integer(30));
			statusList.add(new Integer(41));
			orderParameterDto.setStatusList(statusList);
			
			List<POSOcGroupOrderPaywayDto> ocGroupOrderPaywayList = orderMainManager.findList4OcGroupOrderPaywayForShop(shopNoList,orderParameterDto);
			
			if(ocGroupOrderPaywayList != null && ocGroupOrderPaywayList.size() > 0){
			
				LOGGER.info(" POS Datasource : "+ocGroupOrderPaywayList.size());
				Map<String,List<POSOcGroupOrderPaywayDto>> mapsTemp = new HashMap<String,List<POSOcGroupOrderPaywayDto>>();
				for(POSOcGroupOrderPaywayDto orderPaywayDto : ocGroupOrderPaywayList){
					
					if(orderPaywayDto.getOutDate() == null){
						continue;
					}					
					String outDate = dateFormat.format(orderPaywayDto.getOutDate());
					String shopNoTep = orderPaywayDto.getShopNo();
					
					String key = shopNoTep + outDate.subSequence(0, 7);
					if(!mapsTemp.containsKey(key)){
						List<POSOcGroupOrderPaywayDto> OcGroupOrderPaywayDtoList = new ArrayList<POSOcGroupOrderPaywayDto>();
						OcGroupOrderPaywayDtoList.add(orderPaywayDto);
						mapsTemp.put(key, OcGroupOrderPaywayDtoList);
					}else{
						List<POSOcGroupOrderPaywayDto> OcGroupOrderPaywayDtoListTemp =  mapsTemp.get(key);
						OcGroupOrderPaywayDtoListTemp.add(orderPaywayDto);
						mapsTemp.put(key, OcGroupOrderPaywayDtoListTemp);
					}
				}
				
				Iterator<String> iters = mapsTemp.keySet().iterator();
				independentShopMonthList = new ArrayList<IndependentShopMonthReport>();
				while(iters.hasNext()){
					String key = iters.next();
					
					List<POSOcGroupOrderPaywayDto> OcGroupOrderPaywayDtoListTemp =  mapsTemp.get(key);
					IndependentShopMonthReport independShopMonthReport = new IndependentShopMonthReport();
					independShopMonthReport.setTotalAmount(new BigDecimal(0));
					for(POSOcGroupOrderPaywayDto ocGroupOrderPaywayDtoTemp : OcGroupOrderPaywayDtoListTemp){
						independShopMonthReport.setShopNo(ocGroupOrderPaywayDtoTemp.getShopNo());
						independShopMonthReport.setShopName(ocGroupOrderPaywayDtoTemp.getShopName());
						
						String saleMonth = dateFormat.format(ocGroupOrderPaywayDtoTemp.getOutDate());
						independShopMonthReport.setSaleMonth(saleMonth);
						fillObject(ocGroupOrderPaywayDtoTemp,independShopMonthReport);
					}
					BigDecimal rebateAmount = getCreditCardFeeForShop(independShopMonthReport.getSaleMonth(),independShopMonthReport.getShopNo());
					BigDecimal shopBasicSpeed = getBasicSpendForShop(independShopMonthReport.getShopNo(),independShopMonthReport.getSaleMonth().replace("-","").substring(0, 6));
					
					independShopMonthReport.setFeeAmount(rebateAmount);
					independShopMonthReport.setBasicSpendAmount(shopBasicSpeed);
					if(rebateAmount != null && shopBasicSpeed != null){
						independShopMonthReport.setActualIncomeAmount(independShopMonthReport.getTotalAmount().subtract(rebateAmount.add(shopBasicSpeed)));
					}else{
						if(rebateAmount != null){
							independShopMonthReport.setActualIncomeAmount(independShopMonthReport.getTotalAmount().subtract(rebateAmount));
						}
						if(shopBasicSpeed != null){
							independShopMonthReport.setActualIncomeAmount(independShopMonthReport.getTotalAmount().subtract(shopBasicSpeed));
						}
					}
					
					independShopMonthReport.setSaleMonth(independShopMonthReport.getSaleMonth().substring(0, 7));
					independentShopMonthList.add(independShopMonthReport);
				}
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ManagerException(e.getMessage(), e);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ManagerException(e.getMessage(), e);
		}
		return independentShopMonthList;
	}
	
	public BigDecimal getCreditCardFeeForShop(String saleMonth,String shopNo) throws ServiceException{
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("shopNo", shopNo);
		
		String terminalNumber = null;
		BigDecimal rebateAmount = null;
		List<SelfShopBankInfo> selfShopBankInfoList = selfShopBankInfoService.findByBiz(null, maps);
		if(selfShopBankInfoList != null && selfShopBankInfoList.size() > 0){
			terminalNumber = selfShopBankInfoList.get(0).getTerminalNumber();
			
			//计算回扣费平摊
			if(terminalNumber == null || "".equals(terminalNumber)){
				return null;
			}else{
			
				maps.clear();
				maps.put("saleMonth", saleMonth);
				maps.put("terminalNo", terminalNumber);
				
				rebateAmount = creditCardTransactionService.getSumRebateAmount(maps);
				
				if(rebateAmount != null){
					maps.put("queryCondition", " AND terminal_number = (select terminal_number from self_shop_bank_info where shop_no = '"+shopNo+"')  ");
					int count = selfShopBankInfoService.findCount(maps);
					
					rebateAmount = rebateAmount.divide(new BigDecimal(count), 2);
				}
			}
		}
		return rebateAmount ;
	}
	
	/**
	 * Get shop basic speed ...
	 * @param shopNo
	 * @param month
	 * @return
	 */
	public BigDecimal getBasicSpendForShop(String shopNo,String month){
		
		//已结
		Integer status = 2; 
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("shopNo", shopNo);
		params.put("month", month);
		params.put("status", status);
		params.put("balanceNo", "");
		
		BillShopBalanceDeduct billShopBalanceDeduct = billShopBalanceDeductService.getSumAmount(params);
		
		if(billShopBalanceDeduct != null){
			return billShopBalanceDeduct.getActualAmount();
		}
		return null;
	}
	
	public List<Shop> getAllShopByComanyNo(String companyNo) throws ServiceException{
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("companyNo", companyNo);
    	List<Shop> shopList = shopService.findByBiz(new Shop(), params);
    	if(shopList == null || shopList.size() < 1){
    		return null;
    	}
    	return shopList;
    }
	
	public Date getTheFirstDayOfMonth(String startDateMonth){
		
		String[] strTime =  startDateMonth.split("-");
		
		Calendar cal = Calendar.getInstance();
		  // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		cal.set(Calendar.YEAR, Integer.parseInt(strTime[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(strTime[1]));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = cal.getTime();
		return firstDate;
	}
	
	public Date getTheLastdayOfMonth(String endDateMonth){
		
		String[] strTime =  endDateMonth.split("-");
		
		Calendar cal = Calendar.getInstance();
		// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		cal.set(Calendar.YEAR, Integer.parseInt(strTime[0]) );
		cal.set(Calendar.MONTH, Integer.parseInt(strTime[1]) );
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();
		return lastDate;
	}
	
	public void fillObject(POSOcGroupOrderPaywayDto ocGroupOrderPaywayDtoTemp,IndependentShopMonthReport independShopMonthReport){
		
		if(PublicConstans.PAYCODE_CASHAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopMonthReport.setCashAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopMonthReport.setTotalAmount(independShopMonthReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_ADVANCEPAY.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopMonthReport.setAdvancePayAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopMonthReport.setTotalAmount(independShopMonthReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_CASHCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopMonthReport.setCashCouponAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopMonthReport.setTotalAmount(independShopMonthReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_CREDITCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopMonthReport.setCreditCardAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());	
			independShopMonthReport.setTotalAmount(independShopMonthReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_MALLCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopMonthReport.setMallCardAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopMonthReport.setTotalAmount(independShopMonthReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_OTHERAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopMonthReport.setOthersAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopMonthReport.setTotalAmount(independShopMonthReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_MALLCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopMonthReport.setMallCouponAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopMonthReport.setTotalAmount(independShopMonthReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}
	}
}
