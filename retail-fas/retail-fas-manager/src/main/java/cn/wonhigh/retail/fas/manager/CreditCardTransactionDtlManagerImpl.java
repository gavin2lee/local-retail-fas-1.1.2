package cn.wonhigh.retail.fas.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.CreditCardCensusDto;
import cn.wonhigh.retail.fas.common.model.CreditCardTransactionDtl;
import cn.wonhigh.retail.fas.common.model.SelfShopBankInfo;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.CreditCardTransactionDtlService;
import cn.wonhigh.retail.fas.service.SelfShopBankInfoService;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.oc.common.api.dto.OcGroupOrderPayWayTimeDto;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderParameterParentDto;
import cn.wonhigh.retail.oc.common.api.service.OrderMainApi;
import cn.wonhigh.retail.oc.common.api.util.OcPagingDto;
import cn.wonhigh.retail.oc.common.api.util.OcSimplePageDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 11:40:01
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
@Service("creditCardTransactionDtlManager")
class CreditCardTransactionDtlManagerImpl extends BaseCrudManagerImpl implements CreditCardTransactionDtlManager {
    @Resource
    private CreditCardTransactionDtlService creditCardTransactionDtlService;
    
    @Resource
    private OrderMainApi orderMainApi;
    
    @Resource
    private ShopService shopService;
    
    @Resource
    private SelfShopBankInfoService selfShopBankInfoService;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public BaseCrudService init() {
        return creditCardTransactionDtlService;
    }

	@Override
	public boolean uploadListTransactionDtl(List<CreditCardTransactionDtl> list)
			throws ManagerException {
		try {
			if(list != null && list.size() > 0) {
				for(CreditCardTransactionDtl dtl : list) {
					dtl.setId(UUIDGenerator.getUUID());
					this.add(dtl);
				}
			}
		} catch(ManagerException e) {
			throw e;
		}
		return true;
	}

	@Override
	public Map<String,Object> getCreditCardCensus(
			Map<String, Object> maps) throws ManagerException {
		// TODO Auto-generated method stub
		
		List<String> shopNos = null;
		Map<String,Object> obj = null;
		try {
			
			String shopNoList = maps.get("shopNo").toString();
			String startTime = maps.get("createTimeStart").toString();
	    	String endTime = maps.get("createTimeEnd").toString();
	    	int pageNo = StringUtils.isEmpty(maps.get("page").toString()) ? 1 : Integer.parseInt(maps.get("page").toString());
			int pageSize = StringUtils.isEmpty(maps.get("rows").toString()) ? 10 : Integer.parseInt(maps.get("rows").toString());
	    	
			if(shopNoList == null || "".equals(shopNoList) || startTime == null || "".equals(startTime) || endTime == null || "".equals(endTime)){
	    		return null;
	    	}
			
			if(!"".equals(shopNoList)){
				shopNos = new ArrayList<String>();
				if(shopNoList.indexOf(",") != -1){
					String[] shopArray = shopNoList.split(",");
					for(String shopNo : shopArray){
						shopNos.add(shopNo);
					}
				}else{
					shopNos.add(shopNoList);
				}
			}
			
	    	maps.clear();
	    	int startRows = 0;
	    	int endRows = 10;
	    	
	    	if(pageNo > 1){
	    		startRows = (pageNo - 1) * pageSize;
	    		endRows = (pageNo * pageSize);
	    	}
	    	
	    	maps.put("startDealTime", startTime);
	    	maps.put("endDealTime", endTime);
	    	maps.put("shopNoList", shopNos);
	    	maps.put("startRows", startRows);
	    	maps.put("endRows", endRows);
	    	
	    	Integer count = creditCardTransactionDtlService.getCreditCardCensusCount(maps);
	    	
	    	if(count > 0){
	    	
		    	List<CreditCardCensusDto> creditCardCensusDto = creditCardTransactionDtlService.getCreditCardCensusList(maps);
		    	if(creditCardCensusDto != null && creditCardCensusDto.size() > 0){
		    		
		    		List<CreditCardCensusDto> creditCardCensusList = convertPostDataToCreditCardDto(creditCardCensusDto);
		    		obj = new HashMap<String,Object>();
					obj.put("total", count);
					obj.put("rows", creditCardCensusList);
		    	}
	    	}
	    	
			return obj;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ManagerException(e.getMessage(), e);

		}
	}
	
	public List<CreditCardCensusDto> convertPostDataToCreditCardDto(List<CreditCardCensusDto> creditCardCensusDto) throws ServiceException{
		
		List<CreditCardCensusDto> creditCardCensusList = new ArrayList<CreditCardCensusDto>();
		Map<String,Object> params = new HashMap<String,Object>();
		for(CreditCardCensusDto creditCardDto : creditCardCensusDto){
			CreditCardCensusDto creditCard = new CreditCardCensusDto();
			creditCard.setCardNumber(creditCardDto.getCardNumber());
			creditCard.setTerminalNumber(creditCardDto.getTerminalNumber());
			creditCard.setTimes(creditCardDto.getTimes());
			creditCard.setTotalAmount(creditCardDto.getTotalAmount());
			
			if(creditCard.getTerminalNumber() != null && !"".equals(creditCard.getTerminalNumber()))
			{
				params.put("terminalNumber", creditCard.getTerminalNumber());
				List<SelfShopBankInfo> selfShopBankInfoList = selfShopBankInfoService.findByBiz(new SelfShopBankInfo(), params);
				
				if(selfShopBankInfoList != null && selfShopBankInfoList.size() > 0){
					
					StringBuffer sbf = new StringBuffer();
					for(int i = 0;i < selfShopBankInfoList.size();i++){
						
						SelfShopBankInfo bankInfo = selfShopBankInfoList.get(i);
						
						String shopName = bankInfo.getShopName();
						if(i == (selfShopBankInfoList.size() - 1)){
							sbf.append(shopName);
						}else{
							sbf.append(shopName).append("，");
						}
					}
					creditCard.setShopName(sbf.toString());
				}
			}
			
			creditCardCensusList.add(creditCard);
		}
		return creditCardCensusList;
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
}