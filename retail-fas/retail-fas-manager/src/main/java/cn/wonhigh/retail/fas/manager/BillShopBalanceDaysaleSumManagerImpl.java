package cn.wonhigh.retail.fas.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.common.model.IndependShopDayReport;
import cn.wonhigh.retail.fas.service.BillShopBalanceDaysaleSumService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-12-02 14:50:43
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
@Service("billShopBalanceDaysaleSumManager")
class BillShopBalanceDaysaleSumManagerImpl extends BaseCrudManagerImpl implements BillShopBalanceDaysaleSumManager {
    @Resource
    private BillShopBalanceDaysaleSumService billShopBalanceDaysaleSumService;
    
    @Override
    public BaseCrudService init() {
        return billShopBalanceDaysaleSumService;
    }

	@Override
	public Map<String, Object> queryMallStoreSaleDayReportList(
			Map<String, Object> params) throws ManagerException {
		// TODO Auto-generated method stub
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<IndependShopDayReport> independShopDayReportList = new ArrayList<IndependShopDayReport>();
		Map<String,Object> obj = new HashMap<String,Object>();
		try{
			Integer total = this.findCount(params);
			if(total < 1){
				return null;
			}
			
		 	String pageNo = params.get("page").toString();
			String pageSize = params.get("pageSize").toString();
			if("".equals(pageNo) || "".equals(pageSize)){
				return null;
			}
			
			SimplePage page = new SimplePage(Integer.parseInt(pageNo), Integer.parseInt(pageSize), (int) total);
			List<BillShopBalanceDaysaleSum> shopDaySaleSumlist = this.findByPage(page, null, null, params);
			
			Map<String,List<BillShopBalanceDaysaleSum>> maps = new HashMap<String,List<BillShopBalanceDaysaleSum>>();
			for(BillShopBalanceDaysaleSum ocGroupOrderPaywayDto : shopDaySaleSumlist){
				String outTime = dateFormat.format(ocGroupOrderPaywayDto.getOutDate());
				String shopNoTe = ocGroupOrderPaywayDto.getShopNo();
				String key = shopNoTe + "|" + outTime;
				
				if(!maps.containsKey(key)){
					List<BillShopBalanceDaysaleSum> OcGroupOrderPaywayDtoList = new ArrayList<BillShopBalanceDaysaleSum>();
					OcGroupOrderPaywayDtoList.add(ocGroupOrderPaywayDto);
					maps.put(key, OcGroupOrderPaywayDtoList);
				}else{
					List<BillShopBalanceDaysaleSum> OcGroupOrderPaywayDtoListTemp =  maps.get(key);
					OcGroupOrderPaywayDtoListTemp.add(ocGroupOrderPaywayDto);
					maps.put(key, OcGroupOrderPaywayDtoListTemp);
				}
			}
			
			Iterator<String> iters = maps.keySet().iterator();
			while(iters.hasNext()){
				String key = iters.next();
				
				List<BillShopBalanceDaysaleSum> OcGroupOrderPaywayDtoListTemp =  maps.get(key);
				IndependShopDayReport independShopDayReport = new IndependShopDayReport();
				for(BillShopBalanceDaysaleSum ocGroupOrderPaywayDtoTemp : OcGroupOrderPaywayDtoListTemp){
					independShopDayReport.setShopNo(ocGroupOrderPaywayDtoTemp.getShopNo());
					independShopDayReport.setShopName(ocGroupOrderPaywayDtoTemp.getShortName());
					independShopDayReport.setOutDate(ocGroupOrderPaywayDtoTemp.getOutDate());
					fillObject(ocGroupOrderPaywayDtoTemp,independShopDayReport);
				}
				independShopDayReportList.add(independShopDayReport);
			}
			
			if(independShopDayReportList != null && independShopDayReportList.size() > 0){
				obj.put("rows",independShopDayReportList);
				obj.put("total", total);
			}
			
		}catch(Exception e){
			throw new ManagerException(e.getMessage(), e);

		}
		return obj;
	}
	
	public void fillObject(BillShopBalanceDaysaleSum ocGroupOrderPaywayDtoTemp,IndependShopDayReport independShopDayReport){
		
		if(PublicConstans.PAYCODE_CASHAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCashAmount(ocGroupOrderPaywayDtoTemp.getAmount());
//		}else if(PublicConstans.PAYCODE_CARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
//			independShopDayReport.setiCardAmount(ocGroupOrderPaywayDtoTemp.getAmount());
		}else if(PublicConstans.PAYCODE_CASHCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCashCouponAmount(ocGroupOrderPaywayDtoTemp.getAmount());
		}else if(PublicConstans.PAYCODE_CREDITCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setCreditCardAmount(ocGroupOrderPaywayDtoTemp.getAmount());	
		}else if(PublicConstans.PAYCODE_MALLCARDAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setMallCardAmount(ocGroupOrderPaywayDtoTemp.getAmount());
		}else if(PublicConstans.PAYCODE_OTHERAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setOtherAmount(ocGroupOrderPaywayDtoTemp.getAmount());
//		}else if(PublicConstans.PAYCODE_COMPANYONSALEAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
//			independShopDayReport.setCompanyOnSaleAmount(ocGroupOrderPaywayDtoTemp.getAmount());
		}else if(PublicConstans.PAYCODE_MALLCOUPONAMOUNT.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setMallCouponAmount(ocGroupOrderPaywayDtoTemp.getAmount());
		}else if(PublicConstans.PAYCODE_ADVANCEPAY.equals(ocGroupOrderPaywayDtoTemp.getPayCode())){
			independShopDayReport.setAdvancePay(ocGroupOrderPaywayDtoTemp.getAmount());
		}
	}
	
}