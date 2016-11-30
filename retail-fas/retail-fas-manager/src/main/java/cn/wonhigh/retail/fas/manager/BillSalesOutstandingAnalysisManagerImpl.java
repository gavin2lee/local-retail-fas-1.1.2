package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSalesOutstandingAnalysis;
import cn.wonhigh.retail.fas.service.BillSalesOutstandingAnalysisService;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-13 15:20:45
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
@Service("billSalesOutstandingAnalysisManager")
class BillSalesOutstandingAnalysisManagerImpl extends BaseCrudManagerImpl implements BillSalesOutstandingAnalysisManager {
    @Resource
    private BillSalesOutstandingAnalysisService billSalesOutstandingAnalysisService;
    
//    @Resource
//    private PromotionApi promotionApi;
    
//    @Resource
//	private SalePriceApi salePriceApi;

    @Override
    public BaseCrudService init() {
        return billSalesOutstandingAnalysisService;
    }

    @Override
    public BillSalesOutstandingAnalysis findSalesOutstandingAnalysisCount(Map<String,Object> params) throws ManagerException{
    	try {
			return billSalesOutstandingAnalysisService.findSalesOutstandingAnalysisCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);

		}
    }
    
	@Override
	public List<BillSalesOutstandingAnalysis> findSalesOutstandingAnalysis(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		try {
			List<BillSalesOutstandingAnalysis> list = billSalesOutstandingAnalysisService.findSalesOutstandingAnalysis(page, orderByField, orderBy, params);
			if(CollectionUtils.isNotEmpty(list)){
				String queryType = params.get("queryType").toString();
				//如果只查询GMS 则只需要调用查询牌价的接口
				if(!"1".equals(queryType)){ //如果只查询POS 则只需要调用查询活动信息的接口
					getPromotionInfo(list);
				}
			}
			return list ;
		} catch (ManagerException e) {
			throw new ManagerException(e.getMessage(), e);
		}catch(Exception ex){
			throw new ManagerException(ex.getMessage(), ex);
		}
		
	}
	@Override
	public void findSalesOutstandingAnalysis(int type,SimplePage page,Map<String,Object> params,Function<Object, Boolean> handler) throws ManagerException{
		try {
			billSalesOutstandingAnalysisService.findSalesOutstandingAnalysis(type,page,params,handler);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
	
	   
	/**
     * 根据大类、品牌、季度、年份、性别 分类汇总 gms 及 pos 销售单据信息
     * @param page
     * @param orderByField
     * @param orderBy
     * @param params
     * @return
     */
    public List<BillSalesOutstandingAnalysis> findBillSaleCollectList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException{
		try {
			List<BillSalesOutstandingAnalysis> list = billSalesOutstandingAnalysisService.findBillSaleCollectList(page, orderByField, orderBy, params);
			if(CollectionUtils.isNotEmpty(list)){
				getPromotionInfo(list);
			}
			return list ;
		} catch (ManagerException e) {			
			throw e;
		} catch (Exception e) {			
			throw new ManagerException(e);
		}
    }
    
    /**
     * 根据大类、品牌、季度、年份、性别 分类汇总 的记录数
     * @param params
     * @return
     */
    public BillSalesOutstandingAnalysis findBillSaleCollectCount(Map<String,Object> params) throws ManagerException{
    	try {
			return billSalesOutstandingAnalysisService.findBillSaleCollectCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
    }
   
    public void processPromotionInfo(BillSalesOutstandingAnalysis billSalesOutstandingAnalysis) throws ManagerException{
//    	
//    	
//    	ProMainParamDto proMainParamDto  = new ProMainParamDto ();
//    	billSalesOutstandingAnalysis.setVirtualFlagStr("实数");
//		// 活动编号为空及gms 单据的记录都不用需要调用接口查询活动信息
//		if(StringUtils.isBlank(billSalesOutstandingAnalysis.getProNo()) || billSalesOutstandingAnalysis.getSourceType().equals(0)){
//			return;
//		}
//		proMainParamDto.setProNo(billSalesOutstandingAnalysis.getProNo());
//		proMainParamDto.setShopNo(billSalesOutstandingAnalysis.getShopNo());
//		try {
//			PaginationDto<ProMainDto> proMainDto = promotionApi.findList4ProMain(proMainParamDto);
//			
//			List<ProMainDto> proMainList = proMainDto.getList();
//			if(CollectionUtils.isNotEmpty(proMainList)){
//				billSalesOutstandingAnalysis.setProStartDate(proMainList.get(0).getStartTime());
//				billSalesOutstandingAnalysis.setProEndDate(proMainList.get(0).getEndTime());
//				billSalesOutstandingAnalysis.setStrength(proMainList.get(0).getStrength());
//				if(StringUtils.isNotBlank(proMainList.get(0).getVirtualFlagStr())){
//					billSalesOutstandingAnalysis.setVirtualFlagStr(proMainList.get(0).getVirtualFlagStr());
//				}
//				billSalesOutstandingAnalysis.setPropertiesStr(proMainList.get(0).getPropertiesStr());
//				billSalesOutstandingAnalysis.setActivityTypeStr(proMainList.get(0).getActivityTypeStr());
//				billSalesOutstandingAnalysis.setLaunchTypeStr(proMainList.get(0).getLaunchTypeStr());
////				billSalesOutstandingAnalysis.setRateTypeStr(proMainList.get(0).getRateTypeStr());
//			}
//		} catch (Exception e) {
//			throw new ManagerException(e);
//		}
		
    }
    /**
     * 根据活动编号，调用接口获取活动的相关信息
     * @param list
     * @throws RpcException 
     */
    private void getPromotionInfo(List<BillSalesOutstandingAnalysis> list) throws ManagerException{
    	
		for (BillSalesOutstandingAnalysis billSalesOutstandingAnalysis : list) {
			processPromotionInfo(billSalesOutstandingAnalysis);
		}
    }
    
    
//    /**
//     * 根据活动编号，调用接口获取活动的相关信息
//     * @param list
//     * @throws RpcException 
//     */
//    private void getItemTagPrice(List<BillSalesOutstandingAnalysis> list) throws RpcException{
//		for (BillSalesOutstandingAnalysis billSalesOutstandingAnalysis : list) {
//			if(billSalesOutstandingAnalysis.getSourceType().equals(1)){//pos 的单据不需要查询牌价
//				continue;
//			}
//			//调用MPS接口，根据订货单位机构查询商品牌价 
//			PriceVo price = salePriceApi.getItemTagPriceByParam(billSalesOutstandingAnalysis.getOrganNo1(), new ItemDto(billSalesOutstandingAnalysis.getItemNo()));
//			if(price != null && null != price.getTagPrice()) {
//				double tagAmount = price.getTagPrice().doubleValue() * billSalesOutstandingAnalysis.getQty();
//				billSalesOutstandingAnalysis.setTagAmount(new BigDecimal(tagAmount));
//			}
//		}
//    }
    
    
    /**
     * 查询销售回款分析明细，用于导出
     * @param params
     * @return
     */
    public List<BillSalesOutstandingAnalysis> findSaleDetailForExport(Map<String,Object> params) throws ManagerException{
    	try{
    		return billSalesOutstandingAnalysisService.findSaleDetailForExport(params);
    	} catch (Exception e) {
			throw new ManagerException(e);
		}
		
    }
}