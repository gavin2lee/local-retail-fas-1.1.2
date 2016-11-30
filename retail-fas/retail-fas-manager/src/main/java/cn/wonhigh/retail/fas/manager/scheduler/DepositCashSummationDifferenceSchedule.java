package cn.wonhigh.retail.fas.manager.scheduler;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.constans.CommonConstants;
import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupOrderPaywayDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.DepositCashCumulativeDifference;
import cn.wonhigh.retail.fas.common.model.IndependShopDayReport;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.manager.OrderMainManager;
import cn.wonhigh.retail.fas.service.DepositCashCumulativeDifferenceService;
import cn.wonhigh.retail.fas.service.DepositCashService;
import cn.wonhigh.retail.fas.service.ShopService;

import com.yougou.logistics.base.common.enums.JobBizStatusEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.interfaces.RemoteJobServiceExtWithParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.vo.scheduler.RemoteJobInvokeParamsDto;

@Service
@Deprecated
@ManagedResource(objectName = CommonConstants.SYS_NAME + "DepositCashSummationDifferenceSchedule", description = StringUtils.EMPTY)
public class DepositCashSummationDifferenceSchedule implements RemoteJobServiceExtWithParams{
	
	@Resource
	private OrderMainManager orderMainManager ;
	
	@Resource
	private ShopService shopService;
	
	@Resource
	private DepositCashService depositCashService;
	
	@Resource
	private DepositCashCumulativeDifferenceService depositCashCumulativeDifferenceService;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final Logger log = Logger.getLogger(DepositCashSummationDifferenceSchedule.class);
	
	public List<POSOcGroupOrderPaywayDto> getSaleRecordByDay(String shopNo,Date toDayDate) throws ManagerException{
	
		List<POSOcGroupOrderPaywayDto> posOcGroupOrderPaywayList = null;
		
		POSOcOrderParameterDto ocOrderParameterDto = new POSOcOrderParameterDto();
		ocOrderParameterDto.setEndOutDate(toDayDate);
		ocOrderParameterDto.setStartOutDate(toDayDate);
		ocOrderParameterDto.setShopNo(shopNo);
		
		List<Integer> businessTypeList = new ArrayList<Integer>();
    	
    	//0-正常销售 1-跨店销售
    	businessTypeList.add(new Integer(0));
		businessTypeList.add(new Integer(1));
		
		ocOrderParameterDto.setBusinessTypeList(businessTypeList);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(new Integer(30));
		statusList.add(new Integer(41));
		ocOrderParameterDto.setStatusList(statusList);
		
		POSOcSimplePageDto simplePage = new POSOcSimplePageDto();
		simplePage.setPageNo(1);
		simplePage.setPageSize(Integer.MAX_VALUE);
		
		
		try {
			POSOcPagingDto<POSOcGroupOrderPaywayDto> ocPaging = orderMainManager.findList4OcGroupOrderPayway(simplePage,null,ocOrderParameterDto);
			
			if(ocPaging != null && ocPaging.getResult() != null){
				posOcGroupOrderPaywayList = ocPaging.getResult();
			}
			
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			throw new ManagerException(e.getMessage(), e);
		}
		return posOcGroupOrderPaywayList;
	}
	
	public List<DepositCash> getAllDepositCash(String shopNo,Date toDayDate) throws ServiceException{
		
		List<DepositCash> depositCashList = null;
		
		SimplePage simplePage = new SimplePage();
		simplePage.setPageNo(1);
		simplePage.setPageSize(Integer.MAX_VALUE);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startDate", toDayDate);
		params.put("endDate", toDayDate);
		params.put("shopNo", shopNo);
		params.put("status", 0);
		
		try {
			depositCashList = depositCashService.findByPage(simplePage, null, null, params);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(e.getMessage(), e);
		}
		return depositCashList;
	}
	
	
	public List<Shop> getRentShops() throws ServiceException{
		List<Shop> shopList = null;
		
		String payType = "U030302";
		try {
			//查询 租金店
			shopList = shopService.selectShopInfoByPayTypeWithoutDataAuthority(payType);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(e.getMessage(), e);
		}
		return shopList;
	}
	
	public IndependShopDayReport censusSaleAmountByCurrency(List<POSOcGroupOrderPaywayDto> ocGroupOrderPaywayDtoList){

		IndependShopDayReport independShopDayReport = new IndependShopDayReport();
		
		Map<String,List<POSOcGroupOrderPaywayDto>> maps = new HashMap<String,List<POSOcGroupOrderPaywayDto>>();
		for(POSOcGroupOrderPaywayDto ocGroupOrderPaywayDto : ocGroupOrderPaywayDtoList){
			String outTime = dateFormat.format(ocGroupOrderPaywayDto.getOutDate());
			String shopNo = ocGroupOrderPaywayDto.getShopNo();
			String key = shopNo + "|" + outTime;
			
			if(!maps.containsKey(key)){
				List<POSOcGroupOrderPaywayDto> OcGroupOrderPaywayDtoList = new ArrayList<POSOcGroupOrderPaywayDto>();
				OcGroupOrderPaywayDtoList.add(ocGroupOrderPaywayDto);
				maps.put(key, OcGroupOrderPaywayDtoList);
			}else{
				List<POSOcGroupOrderPaywayDto> OcGroupOrderPaywayDtoListTemp =  maps.get(key);
				OcGroupOrderPaywayDtoListTemp.add(ocGroupOrderPaywayDto);
				maps.put(key, OcGroupOrderPaywayDtoListTemp);
			}
		}
		
		Iterator<String> iters = maps.keySet().iterator();
		while(iters.hasNext()){
			String key = iters.next();
			
			List<POSOcGroupOrderPaywayDto> OcGroupOrderPaywayDtoListTemp =  maps.get(key);
			
			independShopDayReport.setTotalAmount(new BigDecimal(0));
			for(POSOcGroupOrderPaywayDto ocGroupOrderPaywayDtoTemp : OcGroupOrderPaywayDtoListTemp){
				independShopDayReport.setShopNo(ocGroupOrderPaywayDtoTemp.getShopNo());
				independShopDayReport.setShopName(ocGroupOrderPaywayDtoTemp.getShopName());
				independShopDayReport.setOutDate(ocGroupOrderPaywayDtoTemp.getOutDate());
				fillObject(ocGroupOrderPaywayDtoTemp,independShopDayReport);
			}
		}
		return independShopDayReport;
	}
	
	public void getSummationDifference(IndependShopDayReport independShopDayReport,List<DepositCash> depositCashList) throws Exception{
		try {
			
			BigDecimal depositTotalAmount = new BigDecimal(0);
			for(DepositCash depositCashDto : depositCashList){
				if(depositCashDto.getAmount() != null){
					depositTotalAmount = depositTotalAmount.add(depositCashDto.getAmount());
				}
			}
			log.info(" The total deposit amount is "+depositTotalAmount+" .");
			
			DepositCashCumulativeDifference depositCashCumulativeDifferenceDto = new DepositCashCumulativeDifference();
			depositCashCumulativeDifferenceDto.setDepositCashDifference(BigDecimal.ZERO);
			
			//当前存现差异
			BigDecimal cumulativeDifference = depositTotalAmount.subtract(independShopDayReport.getCashAmount());
			
			//历史总差异
	 		BigDecimal cumulativeDifferenceHistory = depositCashCumulativeDifferenceService.selectCumulativeDifferenceByShopNo(independShopDayReport.getShopNo());
	 		if(cumulativeDifferenceHistory != null){
	 			BigDecimal newCumulativeDifference =  cumulativeDifferenceHistory.add(cumulativeDifference);
	 			depositCashCumulativeDifferenceDto.setDepositCashDifference(newCumulativeDifference);
	 		}else{
	 			depositCashCumulativeDifferenceDto.setDepositCashDifference(cumulativeDifference);
	 		}
	 		depositCashCumulativeDifferenceDto.setId(UUIDGenerator.getUUID());
	 		depositCashCumulativeDifferenceDto.setShopNo(independShopDayReport.getShopNo());
	 		depositCashCumulativeDifferenceDto.setShopName(independShopDayReport.getShopName());
	 		depositCashCumulativeDifferenceDto.setDepositCashTotalAmount(depositTotalAmount);
	 		depositCashCumulativeDifferenceDto.setSaleOut(independShopDayReport.getOutDate());
	 		depositCashCumulativeDifferenceDto.setCashAmount(independShopDayReport.getCashAmount());
	 		depositCashCumulativeDifferenceDto.setCashCouponAmount(independShopDayReport.getCashCouponAmount());
	 		depositCashCumulativeDifferenceDto.setBankCardAmount(independShopDayReport.getCreditCardAmount());
	 		depositCashCumulativeDifferenceDto.setMallCardAmount(independShopDayReport.getMallCardAmount());
	 		depositCashCumulativeDifferenceDto.setMallCouponAmount(independShopDayReport.getMallCouponAmount());
	 		depositCashCumulativeDifferenceDto.setExpectCashAmount(independShopDayReport.getAdvancePay());
	 		depositCashCumulativeDifferenceDto.setOtherAmount(independShopDayReport.getOtherAmount());
	 		depositCashCumulativeDifferenceDto.setSaleTotalAmount(independShopDayReport.getTotalAmount());
	 		depositCashCumulativeDifferenceDto.setCreateTime(new Date());
	 		depositCashCumulativeDifferenceDto.setCurrentDepositCashDifference(cumulativeDifference);
	 		
	 		depositCashCumulativeDifferenceService.add(depositCashCumulativeDifferenceDto);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(e.getMessage(), e);

		} catch (Exception e){
			throw new Exception(e.getMessage(), e);
		}
		
	}
	
	public void fillObject(POSOcGroupOrderPaywayDto ocGroupOrderPaywayDtoTemp,IndependShopDayReport independShopDayReport){
		
		if(PublicConstans.PAYCODE_CASHAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCashAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_ADVANCEPAY.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setAdvancePay(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_CASHCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCashCouponAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_CREDITCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCreditCardAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());	
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_MALLCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setMallCardAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_OTHERAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setOtherAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}else if(PublicConstans.PAYCODE_MALLCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setMallCouponAmount(ocGroupOrderPaywayDtoTemp.getGroupAmount());
			independShopDayReport.setTotalAmount(independShopDayReport.getTotalAmount().add(ocGroupOrderPaywayDtoTemp.getGroupAmount()));
		}
	}

	@Override
	public void executeJobWithParams(String arg0, String arg1, String arg2,
			RemoteJobInvokeParamsDto arg3) {
		// TODO Auto-generated method stub
		
		try {
			
			log.info("######################################################Get Started################################");
			
			List<Shop> shopList = getRentShops();
			List<POSOcGroupOrderPaywayDto> posOcGroupOrderPaywayList = null;
			List<DepositCash> depositCashList = null;
			
			Date toDayDate = DateUtil.getdate(DateUtil.format(new Date(), "yyyy-MM-dd"));
			log.info(" The current date is "+DateUtil.format(toDayDate, "yyyy-MM-dd"));
			
			for(Shop shop : shopList){
				
				//获取当前店铺,当天各种支付方式金额
				posOcGroupOrderPaywayList = getSaleRecordByDay(shop.getShopNo(),toDayDate);
				
				if(posOcGroupOrderPaywayList == null || posOcGroupOrderPaywayList.size() < 1){
					continue;
				}
				log.info(" shopNo : "+shop.getShopNo()+" , There are "+posOcGroupOrderPaywayList.size()+" sale records which belongs to the shop !");
				
				//对各种支付方法汇总
				IndependShopDayReport independShopDayReport = censusSaleAmountByCurrency(posOcGroupOrderPaywayList);
				
				//获取当前店铺 当天存现总额
				depositCashList = getAllDepositCash(shop.getShopNo(),toDayDate);
				if(depositCashList != null && depositCashList.size() > 0){
					log.info(" AND find "+depositCashList.size()+" deposit records by these conditions {shopNo : "+
							shop.getShopNo()+" , the date of depositing : "+DateUtil.format(toDayDate)+" }");
				}
				
				//生成记录
				getSummationDifference(independShopDayReport,depositCashList);
				
				log.info("######################################################Get Ended################################");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(),e);
		}
		
	}

	@Override
	public JobBizStatusEnum getJobStatus(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLogs(String arg0, String arg1, String arg2, long arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeJob(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseJob(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restartJob(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resumeJob(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopJob(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}
}
