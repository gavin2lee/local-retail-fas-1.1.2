package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.PreWarnMessage;
import cn.wonhigh.retail.fas.common.model.PreWarnTemplate;
import cn.wonhigh.retail.fas.service.PreWarnMessageService;
import cn.wonhigh.retail.fas.service.PreWarnTemplateService;
import cn.wonhigh.retail.gms.api.service.CalculateWeightedCostApi;
import cn.wonhigh.retail.gms.api.vo.PriceCheckConditionDto;
import cn.wonhigh.retail.gms.common.dto.PagingDto;
import cn.wonhigh.retail.gms.common.model.PriceExceptionBill;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-09-01 15:49:31
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
@Service("preWarnTemplateManager")
public class PreWarnTemplateManagerImpl extends BaseCrudManagerImpl implements PreWarnTemplateManager {
    
	@Resource
    private PreWarnTemplateService preWarnTemplateService;
    
	@Resource
	private PreWarnMessageService preWarnMessageService;
	
	@Resource
	private CalculateWeightedCostApi calculateWrightedCostApi;

    @Override
    public BaseCrudService init() {
        return preWarnTemplateService;
    }
    
    public void queryGMSPriceException(Map<String,Object> params) throws ManagerException{
			
//    		Map<String,Object> dataMap = new HashMap<String, Object>();
			
//			String billNo = params.get("billNo") == null ? null : params.get("billNo").toString();
//			String billType = params.get("billType") == null ? null : params.get("billType").toString();
//			String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
//			String itemNo = params.get("itemNo") == null ? null : params.get("itemNo").toString();
//			String billDateStart = params.get("createTimeStart") == null ? null : params.get("createTimeStart").toString();
//			String billDateEnd = params.get("createTimeEnd") == null ? null : params.get("createTimeEnd").toString();
			
//			int pageNo  = 1;
//			int pageSize = 10;
//			if(params.get("page")!=null){
//				pageNo = Integer.parseInt(params.get("page").toString());
//			}
//			if(params.get("rows")!=null){
//				pageSize = Integer.parseInt(params.get("rows").toString());
//			}
//			List<String> orderUnitNoList = getOrderUnitByCompanyNo(companyNo);
//			
//			PriceCheckConditionDto priceCheckConditionDto = new PriceCheckConditionDto();
//			
//			List<Integer> billTypeList = new ArrayList<Integer>();
//			if(billType != null && !"".equals(billType)){
//				if(billType.indexOf(",") != -1){
//					String[] billTypeArray = billType.split(",");
//					for(String str : billTypeArray){
//						billTypeList.add(Integer.parseInt(str));
//					}
//				}else{
//					billTypeList.add(Integer.parseInt(billType));
//				}
//			}else{
//				billTypeList = Arrays.asList(billTypeNoList);
//			}
			
//			priceCheckConditionDto.setShardingFlag(params.get("shardingFlag").toString());
//			priceCheckConditionDto.setOrderUnitNos(orderUnitNoList);
//			priceCheckConditionDto.setItemNo(itemNo);
//			priceCheckConditionDto.setBillNo(billNo);
//			priceCheckConditionDto.setMultiBillTypes(billTypeList);
//			priceCheckConditionDto.setBillDateStart(billDateStart);
//			priceCheckConditionDto.setBillDateEnd(billDateEnd);
//			priceCheckConditionDto.setPage(pageNo);
//			priceCheckConditionDto.setPageSize(pageSize);
			List<PriceExceptionBill> list = null;
			PriceCheckConditionDto priceCheckConditionDto = new PriceCheckConditionDto();
			try{
				list=calculateWrightedCostApi.getPriceExceptionExportData(priceCheckConditionDto);
				if(null != list){
					PreWarnMessage message = null;
					for (PriceExceptionBill priceBill : list) {
						message = new PreWarnMessage(47,1,null,null,null,priceBill.getId());
						preWarnMessageService.add(message);
					}
				}
			} catch (Exception e) {
				
			}
    }

	@Override
	@Transactional
	public void check(String checkType) throws ManagerException {
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(checkType)){
				params.put("type", checkType);
			}
			List<PreWarnTemplate> templateList = preWarnTemplateService.findByBiz(null, params);
			for (PreWarnTemplate template : templateList) {
				if(template.getSqlDeleteStatement()!=null){
					preWarnTemplateService.querySqlStatement(template.getSqlDeleteStatement());
				}
				if(template.getSqlStatement()!=null){
					System.out.println("---" + template.getSqlStatement() + "----");
					preWarnTemplateService.querySqlStatement(template.getSqlStatement());
				}
			}
			if(checkType==null || checkType.equals("1")) {
				queryGMSPriceException(null);
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}