package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.POSDepositCashDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderParameterDto;
import cn.wonhigh.retail.fas.common.model.CashInComeCheck;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.service.CashTransactionDtlService;
import cn.wonhigh.retail.fas.service.CreditCardTransactionDtlService;
import cn.wonhigh.retail.fas.service.ShopService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("cashInComeCheckManagerImpl")
public class CashInComeCheckManagerImpl implements CashInComeCheckManager {
	
	@Resource
	private ShopService shopService ;
	
	@Resource
	private CreditCardTransactionDtlService creditCardTransactionService;
	
	@Resource
	private CashTransactionDtlService cashTransactionDtlService;
	
	@Resource
	private OrderMainManager orderMainManager ;
	
	@Override
	public Map<String,Object> queryList(Map<String,Object> param) throws ManagerException{
		
		POSOrderParameterDto orderParameter = new POSOrderParameterDto();
		Map<String,Object> obj = null;
		List<String> shopNos = new ArrayList<String>();
		
		try{
			POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
			
			String pageSizeTemp = param.get("pageSize").toString();
    		String pageNumberTemp = param.get("pageNumber").toString();
    		if(pageSizeTemp != null && !"".equals(pageSizeTemp)){
    			pageDto.setPageSize(Integer.valueOf(pageSizeTemp));
    		}
    		if(pageNumberTemp != null && !"".equals(pageNumberTemp)){
    			pageDto.setPageNo(Integer.valueOf(pageNumberTemp));
    		}
    		
    		
    		String companyNo = param.get("companyNo").toString();
    		String shopNo = param.get("shopNo").toString();
        	String startTime = param.get("createTimeStart").toString();
        	String endTime = param.get("createTimeEnd").toString();
        	
        	if((companyNo == null || "".equals(companyNo)) && (shopNo == null || "".equals(shopNo))){
        		return null;
        	}
        	if(startTime == null || "".equals(startTime) || endTime == null || "".equals(endTime)){
        		return null;
        	}
        	
        	if(shopNo == null || "".equals(shopNo)){
        		
	    		List<Shop> shopList = getAllShopByComanyNo(companyNo);
	    		if(shopList == null || shopList.size() < 1){
	    			return null;
	    		}
	    		
	    		//adding conditional
	    		for(Shop shop : shopList){
	    			if(!shopNos.contains(shop.getShopNo())){
	    				shopNos.add(shop.getShopNo());
	    			}
	    		}
    		}else{
    			
    			String[] shopNoArray = shopNo.split(",");
    			for(String shopNoStr : shopNoArray){
    				shopNos.add(shopNoStr);
    			}
    		}
    		
    		orderParameter.setStartDate(startTime);
    		orderParameter.setEndDate(endTime);
			
    		POSOcPagingDto<POSDepositCashDto> pagingDto = orderMainManager.findList4OrderDepositCash(pageDto, shopNos, orderParameter);
    		
//			PagingDto<DepositCashDto> pagingDto = orderApi.findList4OrderDepositCash(pageDto,shopNos,orderParameter);
			
			if(pagingDto != null && pagingDto.getResult() != null && pagingDto.getResult().size() > 0){
				
				List<POSDepositCashDto> depositCashDtoList = pagingDto.getResult();
				Map<String,CashInComeCheck> depositMap = new HashMap<String,CashInComeCheck>();
				
				for(POSDepositCashDto deposit : depositCashDtoList){
					
					String account = deposit.getAccount();
					long depositTime = deposit.getDepositDate().getTime();
					String key = account + "|" +depositTime;
					
					if(!depositMap.containsKey(key)){
						
						CashInComeCheck cashInComeCheck = new CashInComeCheck();
						cashInComeCheck.setDepositAccount(deposit.getAccount());
						cashInComeCheck.setDepositDate(deposit.getDepositDate());
						cashInComeCheck.setDepositAmount(deposit.getAmount());
						
						depositMap.put(key, cashInComeCheck);
					}else{
						CashInComeCheck cashInComeCheck = depositMap.get(key);
						
						if(cashInComeCheck.getDepositAmount() != null){
							cashInComeCheck.setDepositAmount(cashInComeCheck.getDepositAmount().add(deposit.getAmount()));
						}
						depositMap.put(key, cashInComeCheck);
					}
					
				}
				
				Iterator<String> iterator = depositMap.keySet().iterator();
				
				Map<String,Object> params = new HashMap<String,Object>();
				List<CashInComeCheck> cashInComeCheckList = new ArrayList<CashInComeCheck>();
				while(iterator.hasNext()){
					
					String key = iterator.next();
					
					CashInComeCheck cashInComeCheck =  depositMap.get(key);
					
					params.put("cardNumber", cashInComeCheck.getDepositAccount());
					params.put("dealTime", cashInComeCheck.getDepositDate());
					
//					BigDecimal totalInComeAmount = creditCardTransactionService.getSumActualInComeAmount(params);
					BigDecimal totalInComeAmount = cashTransactionDtlService.getSumActualInComeAmount(params);
					cashInComeCheck.setInComeAmount(totalInComeAmount);
					
					if(cashInComeCheck.getInComeAmount() != null){
						cashInComeCheck.setInComeDiff(cashInComeCheck.getInComeAmount().subtract(cashInComeCheck.getDepositAmount()));
					}
					cashInComeCheckList.add(cashInComeCheck);
				}
				
				if(cashInComeCheckList.size() > 0){
					obj = new HashMap<String,Object>();
					obj.put("total", cashInComeCheckList.size());
					obj.put("rows", cashInComeCheckList);
				}
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new ManagerException(e.getMessage(), e);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			throw new ManagerException(e.getMessage(), e);
		}
		return obj;
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
