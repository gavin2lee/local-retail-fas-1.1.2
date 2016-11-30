package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDeductFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceOperatelog;
import cn.wonhigh.retail.fas.common.model.CostCategorySetting;
import cn.wonhigh.retail.fas.common.model.MallDeductionSet;
import cn.wonhigh.retail.fas.common.model.RateExpenseOperate;
import cn.wonhigh.retail.fas.common.model.RateExpenseSporadic;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.RateExpenseSporadicMapper;
import cn.wonhigh.retail.fas.service.BillShopBalanceDeductService;
import cn.wonhigh.retail.fas.service.BillShopBalanceOperatelogService;
import cn.wonhigh.retail.fas.service.CostCategorySettingService;
import cn.wonhigh.retail.fas.service.MallDeductionSetService;
import cn.wonhigh.retail.fas.service.RateExpenseOperateService;
import cn.wonhigh.retail.fas.service.RateExpenseSporadicService;
import cn.wonhigh.retail.fas.service.ShopBalanceDateService;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.mps.api.client.dto.finance.BalanceCallBackDto;
import cn.wonhigh.retail.mps.api.client.dto.finance.BalanceParamDto;
import cn.wonhigh.retail.mps.api.client.service.finance.BalanceRateApi;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 生成费用，只生成合同设置为租金、固定扣额；与销售额、扣率有关的费用计算在生成结算单是动态生成；
 * @author chen.mj
 * @date 2014-11-27 19:22:11
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("billShopBalanceDeductManager")
class BillShopBalanceDeductManagerImpl extends BaseCrudManagerImpl implements
		BillShopBalanceDeductManager {
	
	Logger logger = Logger.getLogger(BillShopBalanceDeductManagerImpl.class);
	
	@Resource
	private BillShopBalanceDeductService billShopBalanceDeductService;
	
	@Resource
	private  ShopBalanceDateService  shopBalanceDateService;
	
	@Resource
	private  RateExpenseOperateService  rateExpenseOperatService;
	
	@Resource
	private  RateExpenseSporadicService  rateExpenseSporadicService;

	@Resource
	private CostCategorySettingService costCategorySettingService;
	
	@Resource
	private BalanceRateApi balanceRateApi;
	
	@Resource
	private MallDeductionSetService  mallDeductionSetService;

	@Resource
	private ShopService  shopService;
	
	@Resource
	private BillShopBalanceOperatelogService billShopBalanceOperatelogService;
	
	@Resource
	private RateExpenseSporadicMapper rateExpenseSporadicMapper;
	
	
	@Override
	public BaseCrudService init() {
		return billShopBalanceDeductService;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)	
	public int generateBalanceDeduct(BillShopBalanceDeduct deductParam) throws ManagerException {
		try {
			int iCount = 0;
			
			//设置查询条件，取结算期状态不是已生成的最早一条结算期
			Map<String, Object> balanceDateParams = new HashMap<String, Object>();
			balanceDateParams.put("shopNo", deductParam.getShopNo());
			balanceDateParams.put("month", deductParam.getMonth());
//			票前控制  票后不控制，已结算也可以生成
			if(deductParam.getCostDeductTypeStr().equals("1")){
				balanceDateParams.put("notEqualBalanceFlag", 2);//不是已生成状态
			}else if (deductParam.getCostDeductTypeStr().equals("2")){
			} else { //没有选择票前票后，默认控制未结算
				balanceDateParams.put("notEqualBalanceFlag", 2);//不是已生成状态
			}
//			balanceDateParams.put("deductStatus", 1); //  0-不生成费用  1 - 生成费用
			List<ShopBalanceDate> shopBalanceDateList = shopBalanceDateService.findByBiz(null, balanceDateParams);
			if(CollectionUtils.isEmpty(shopBalanceDateList)){
				return -1;
			}
			//设置起止时间，可以生成费用的最早的一条结算期的起止时间
			Date startDate = shopBalanceDateList.get(0).getBalanceStartDate();
			Date endDate = shopBalanceDateList.get(0).getBalanceEndDate();
			deductParam.setBalanceStartDate(startDate);
			deductParam.setBalanceEndDate(endDate);
			
			//场地经营费，  只取处理纯租金，rate_expense_operate    `settlement_type`  '结算类型,1-阶段保底+扣率 2-纯租金 3-最大值(租金、扣率) 4-和值(租金+扣率)'
			//其他零星费用，只取处理月定额，rate_expense_sporadic   `debited_rule` '扣费规则 1-月定额 2-费率',
			
			//设置场地经营费用查询条件,按结算月
			Map<String, Object> rateOperateMap = new HashMap<String, Object>();
			rateOperateMap.put("shopNo", deductParam.getShopNo());
			rateOperateMap.put("settlementPeriod", deductParam.getMonth());
			rateOperateMap.put("settlementType", 2);//纯租金
			rateOperateMap.put("queryCondition", " and status=0");//未结算
			
//			票前控制  票后控制     费用扣取方式 1-票前 2-票后
			if(deductParam.getCostDeductTypeStr().equals("1")){
				rateOperateMap.put("debitedMode", 1);//票前
			}else if (deductParam.getCostDeductTypeStr().equals("2")){
				rateOperateMap.put("debitedMode", 2);//票后
			} 
			
			//生成合同场地租赁费用，更新合同扣费状态
			int saveOperateCount=saveOperateToBalanceDeduct(rateOperateMap,deductParam);
			
			//设置零星费用查询条件,按起止日期
			Map<String, Object> rateSporadicMap = new HashMap<String, Object>();
			rateSporadicMap.put("shopNo", deductParam.getShopNo());
			rateSporadicMap.put("startDate", startDate);
			rateSporadicMap.put("endDate", endDate);
			rateSporadicMap.put("debitedRule", 1);//月定额
//			rateSporadicMap.put("status", 0);//未结算
			rateSporadicMap.put("settlementDate", endDate);
			
//			票前控制  票后控制     费用扣取方式 1-票前 2-票后
			if(deductParam.getCostDeductTypeStr().equals("1")){
				rateSporadicMap.put("debitedMode", 1);//票前
			}else if (deductParam.getCostDeductTypeStr().equals("2")){
				rateSporadicMap.put("debitedMode", 2);//票后
			} 
			
			//生成零星费用，更新合同扣费状态
			int saveSporadicCount=saveSporadicToBalanceDeduct(rateSporadicMap,deductParam);
			
			iCount = saveOperateCount+saveSporadicCount;
			return iCount;
			
		    } catch (Exception e) {
			 logger.error(e.getMessage(),e);
			 throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 检查是否存在  已扣
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	public int getShopBalanceDeductBuckle(Map<String, Object> params) throws ManagerException{
		int count=0;
		try {
			params.put("status", 2);
			count = billShopBalanceDeductService.findCount(params);
		} catch (ServiceException e) {
			logger.error(e.getMessage(),e);
			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}
	
	/**
	 * 检查是否存在  未扣   
	 * @param params
	 * @return
	 * @throws ManagerException 
	 */
	public int getShopBalanceDeductNotBuckle(Map<String, Object> params) throws ManagerException{
		int count=0;
		try {
			params.put("status", 1);
			count = billShopBalanceDeductService.findCount(params);
		} catch (ServiceException e) {
			logger.error(e.getMessage(),e);
			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}
	
	/*
	 * 保存  按结算月   到费用表
	 */
	public int saveOperateToBalanceDeduct(Map<String, Object> rateOperateMap,BillShopBalanceDeduct deductParam) throws RpcException, ServiceException{
		
		List<RateExpenseOperate> rateExpenseOperateList = rateExpenseOperatService.findByBiz(null, rateOperateMap);	
		int  count=0;
		if (!CollectionUtils.isEmpty(rateExpenseOperateList)) {
			Map<String, Object> shopParams = new HashMap<String, Object>();
			for(RateExpenseOperate rateExpenseOperate : rateExpenseOperateList){
				if(rateExpenseOperate.getRental() == null 
						|| rateExpenseOperate.getRental().compareTo(BigDecimal.ZERO) == 0){
					continue;
				}
				BillShopBalanceDeduct billShopBalanceDeduct = new BillShopBalanceDeduct();
				billShopBalanceDeduct.setCompanyNo(deductParam.getCompanyNo()); 
				billShopBalanceDeduct.setShopNo(deductParam.getShopNo()); 
				billShopBalanceDeduct.setMonth(deductParam.getMonth());
				billShopBalanceDeduct.setBillDate(new Date());
				billShopBalanceDeduct.setCostType((byte) 1);
				billShopBalanceDeduct.setGenerateType(0);
				billShopBalanceDeduct.setStatus(1);
				billShopBalanceDeduct.setCreateUser(deductParam.getCreateUser());
				billShopBalanceDeduct.setCreateTime(deductParam.getCreateTime());
				
				//查询店铺其他基本信息
				shopParams.put("shopNo", deductParam.getShopNo());
				Shop shopModule= shopService.selectSubsiInfo(shopParams);
				if(shopModule != null){
					billShopBalanceDeduct.setCompanyName(shopModule.getCompanyName());
					billShopBalanceDeduct.setShortName(shopModule.getShortName());
					billShopBalanceDeduct.setOrganNo(shopModule.getOrganNo()); 
					billShopBalanceDeduct.setOrganName(shopModule.getOrganName());  
					billShopBalanceDeduct.setBsgroupsNo(shopModule.getBsgroupsNo()); 
					billShopBalanceDeduct.setBsgroupsName(shopModule.getBsgroupsName());  
					billShopBalanceDeduct.setMallNo(shopModule.getMallNo());  
					billShopBalanceDeduct.setMallName(shopModule.getMallName());
			   }
				ShopBrand shopBrand = shopService.selectShopBrandInfo(shopParams); 
				if(shopBrand != null){
					billShopBalanceDeduct.setBrandNo(shopBrand.getBrandNo());
					billShopBalanceDeduct.setBrandName(shopBrand.getName());
				}
				
				//设置扣费类别
				Map<String, Object> mallDeductionSetParams = new HashMap<String, Object>();
				mallDeductionSetParams.put("companyNo",deductParam.getCompanyNo());
				mallDeductionSetParams.put("debitedRental","1" ); 
				mallDeductionSetParams.put("status","1" );
				List<MallDeductionSet> mallDeductionSetList =mallDeductionSetService.findByBiz(null, mallDeductionSetParams);
				if (!CollectionUtils.isEmpty(mallDeductionSetList)) {
					billShopBalanceDeduct.setDeductionCode(mallDeductionSetList.get(0).getCode());
					billShopBalanceDeduct.setDeductionName(mallDeductionSetList.get(0).getName());
					billShopBalanceDeduct.setCostCateName(mallDeductionSetList.get(0).getCostName());
					billShopBalanceDeduct.setCostCateCode(mallDeductionSetList.get(0).getCostCode());
				}
				//获取会计科目
				Map<String, Object> costSetParams = new HashMap<String, Object>();
				costSetParams.put("companyNo",deductParam.getCompanyNo());
				costSetParams.put("code",billShopBalanceDeduct.getCostCateCode()); 
				List<CostCategorySetting> costCategorySettingList = costCategorySettingService.findByBiz(null, costSetParams);
				if(!CollectionUtils.isEmpty(costCategorySettingList)) {
					billShopBalanceDeduct.setAccountsNo(costCategorySettingList.get(0).getAccountsNo());
				}
//				debited_mode` tinyint(4) DEFAULT NULL COMMENT '费用扣取方式 1-票前 2-票后'	
				
				billShopBalanceDeduct.setCostDeductType(rateExpenseOperate.getDebitedMode());
				billShopBalanceDeduct.setCostPayType(rateExpenseOperate.getPaymentMode());
				billShopBalanceDeduct.setDeductAmount(rateExpenseOperate.getRental()== null ? BigDecimal.ZERO : rateExpenseOperate.getRental());
				billShopBalanceDeduct.setActualAmount(rateExpenseOperate.getRental()== null ? BigDecimal.ZERO : rateExpenseOperate.getRental());
				billShopBalanceDeduct.setDiffAmount(billShopBalanceDeduct.getDeductAmount().subtract(billShopBalanceDeduct.getActualAmount()));
				 if(billShopBalanceDeduct.getDiffAmount().compareTo(BigDecimal.ZERO) == 0){
				     billShopBalanceDeduct.setProcessStatus((byte) 2);
				 }  else {
					 billShopBalanceDeduct.setProcessStatus((byte) 1); 
				 }
				billShopBalanceDeduct.setDeductDate(billShopBalanceDeduct.getDeductDate());
				billShopBalanceDeduct.setRateType(1);  
				billShopBalanceDeduct.setBalanceStartDate(deductParam.getBalanceStartDate());
				billShopBalanceDeduct.setBalanceEndDate(deductParam.getBalanceEndDate());
				billShopBalanceDeduct.setDeductType((byte) 1);
				billShopBalanceDeduct.setGenerateType(0);
				billShopBalanceDeduct.setId(UUIDGenerator.getUUID());
				count += billShopBalanceDeductService.add(billShopBalanceDeduct);
				
				List<BalanceCallBackDto> balanceCallBack=getBalanceCallBack(billShopBalanceDeduct.getShopNo(),billShopBalanceDeduct.getMonth(),
						billShopBalanceDeduct.getBalanceStartDate(),billShopBalanceDeduct.getBalanceEndDate(),null);
				balanceRateApi.updateBalanceDate(balanceCallBack);
			}
		}
		return count;
	}
	
	/**
	 * 
	 * @param params
	 * @param billShopBalanceDeduct
	 * @return
	 * @throws RpcException
	 * @throws ServiceException
	 */
	/*
	 * 保存    按起止时期    到费用表
	 */
	public int saveSporadicToBalanceDeduct(Map<String, Object> params,BillShopBalanceDeduct deductParam) throws RpcException, ServiceException{
		
		List<RateExpenseSporadic> rateExpenseSporadicList = rateExpenseSporadicService.findByBiz(null, params);
		int  count=0;
		if (!CollectionUtils.isEmpty(rateExpenseSporadicList)) {
			Map<String, Object> shopParams = new HashMap<String, Object>();
			for(RateExpenseSporadic rateExpenseSporadic : rateExpenseSporadicList){
				if(rateExpenseSporadic.getAmount() == null 
						|| rateExpenseSporadic.getAmount().compareTo(BigDecimal.ZERO) == 0){
					continue;
				}
				BillShopBalanceDeduct billShopBalanceDeduct = new BillShopBalanceDeduct();
				billShopBalanceDeduct.setCompanyNo(deductParam.getCompanyNo()); 
				billShopBalanceDeduct.setShopNo(deductParam.getShopNo()); 
				billShopBalanceDeduct.setMonth(deductParam.getMonth());
				billShopBalanceDeduct.setBillDate(new Date());
				billShopBalanceDeduct.setCostType((byte) 1);
				billShopBalanceDeduct.setGenerateType(0);
				billShopBalanceDeduct.setStatus(1);
				billShopBalanceDeduct.setCreateUser(deductParam.getCreateUser());
				billShopBalanceDeduct.setCreateTime(deductParam.getCreateTime());
				
				//查询店铺其他基本信息
				shopParams.put("shopNo", deductParam.getShopNo());
				Shop shopModule= shopService.selectSubsiInfo(shopParams);
				if(shopModule != null){
					billShopBalanceDeduct.setCompanyName(shopModule.getCompanyName());
					billShopBalanceDeduct.setShortName(shopModule.getShortName());
					billShopBalanceDeduct.setOrganNo(shopModule.getOrganNo()); 
					billShopBalanceDeduct.setOrganName(shopModule.getOrganName());  
					billShopBalanceDeduct.setBsgroupsNo(shopModule.getBsgroupsNo()); 
					billShopBalanceDeduct.setBsgroupsName(shopModule.getBsgroupsName());  
					billShopBalanceDeduct.setMallNo(shopModule.getMallNo());  
					billShopBalanceDeduct.setMallName(shopModule.getMallName());
			   }
				
				ShopBrand shopBrand = shopService.selectShopBrandInfo(shopParams); 
				if(shopBrand != null){
					billShopBalanceDeduct.setBrandNo(shopBrand.getBrandNo());
					billShopBalanceDeduct.setBrandName(shopBrand.getName());
				}
				String brandNo  = rateExpenseSporadic.getBrandNo();
				String brandName  = rateExpenseSporadic.getBrandName();
				if(null != brandNo){
					billShopBalanceDeduct.setBrandNo(brandNo);
				}
				
				if(null != brandName){
					billShopBalanceDeduct.setBrandName(brandName);
				}
				
//				`debited_mode` tinyint(4) DEFAULT NULL COMMENT '费用扣取方式 1-票前 2-票后'
				
				billShopBalanceDeduct.setCostDeductType(rateExpenseSporadic.getDebitedMode());
				billShopBalanceDeduct.setCostPayType(rateExpenseSporadic.getPaymentMode());
				
				if(StringUtils.isNotEmpty(rateExpenseSporadic.getDebitedType())){
					Map<String, Object> mallDeductParams = new HashMap<String, Object>();
					mallDeductParams.put("companyNo",deductParam.getCompanyNo());
					mallDeductParams.put("code", rateExpenseSporadic.getDebitedType());
					mallDeductParams.put("status",1); 
					List<MallDeductionSet> mallDeductionSetList =mallDeductionSetService.findByBiz(null, mallDeductParams);
					if(!CollectionUtils.isEmpty(mallDeductionSetList)){
						billShopBalanceDeduct.setDeductionCode(mallDeductionSetList.get(0).getCode());
						billShopBalanceDeduct.setDeductionName(mallDeductionSetList.get(0).getName());
						billShopBalanceDeduct.setCostCateName(mallDeductionSetList.get(0).getCostName());
						billShopBalanceDeduct.setCostCateCode(mallDeductionSetList.get(0).getCostCode());
				   }
				}
				
	//			获取会计科目
			    Map<String, Object> costSetParams = new HashMap<String, Object>();
			    costSetParams.put("companyNo",deductParam.getCompanyNo());
			    costSetParams.put("code",billShopBalanceDeduct.getCostCateCode()); 
				List<CostCategorySetting> costCategorySettingList = costCategorySettingService.findByBiz(null, costSetParams);
				if(!CollectionUtils.isEmpty(costCategorySettingList)) {
					billShopBalanceDeduct.setAccountsNo(costCategorySettingList.get(0).getAccountsNo());
			    }
				billShopBalanceDeduct.setDeductAmount(rateExpenseSporadic.getAmount() == null ? BigDecimal.ZERO : rateExpenseSporadic.getAmount());
			    billShopBalanceDeduct.setActualAmount(rateExpenseSporadic.getAmount() == null ? BigDecimal.ZERO : rateExpenseSporadic.getAmount());
			    billShopBalanceDeduct.setDiffAmount(billShopBalanceDeduct.getDeductAmount().subtract(billShopBalanceDeduct.getActualAmount()));
			    if(billShopBalanceDeduct.getDiffAmount().compareTo(BigDecimal.ZERO) == 0){
			    	billShopBalanceDeduct.setProcessStatus((byte) 2);
			    } else {
					billShopBalanceDeduct.setProcessStatus((byte) 1); 
				 }
			    billShopBalanceDeduct.setDeductDate(billShopBalanceDeduct.getDeductDate());
			    billShopBalanceDeduct.setRateId(rateExpenseSporadic.getId());
			    billShopBalanceDeduct.setMallDeductionName(rateExpenseSporadic.getMallDeductionName());
			    billShopBalanceDeduct.setRateType(2);
				billShopBalanceDeduct.setBalanceStartDate(deductParam.getBalanceStartDate());
				billShopBalanceDeduct.setBalanceEndDate(deductParam.getBalanceEndDate());
				billShopBalanceDeduct.setDeductType((byte) 1);
				billShopBalanceDeduct.setGenerateType(0);
				billShopBalanceDeduct.setId(UUIDGenerator.getUUID());
				count += billShopBalanceDeductService.add(billShopBalanceDeduct);
				List<String> sporadicIds  = new ArrayList<String>();
				sporadicIds.add(rateExpenseSporadic.getId());
				
				List<BalanceCallBackDto> balanceCallBack=getBalanceCallBack(billShopBalanceDeduct.getShopNo(),billShopBalanceDeduct.getMonth(),
						billShopBalanceDeduct.getBalanceStartDate(),billShopBalanceDeduct.getBalanceEndDate(),sporadicIds);
				balanceRateApi.updateBalanceDate(balanceCallBack);
			}
		}
		return count;
	}
	
	/**
	 * 获取结算期间的开始结束时间
	 * @param modelType
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public <ModelType> List<ModelType> getBalanceDate(ModelType modelType,
			Map<String, Object> params) throws ServiceException {
		try {
			return shopBalanceDateService.findByBiz(modelType, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
	/*
	 * mps参数组装 
	 */
	public	List<BalanceParamDto> getBalanceParams(String shopNo,String month,Date startDate,Date endDate){
		List<BalanceParamDto> balanceParams = new ArrayList<BalanceParamDto>();
		BalanceParamDto balanceParamDto= new BalanceParamDto();
		balanceParamDto.setShopNo(shopNo);
		balanceParamDto.setBalanceMonth(month);
		balanceParamDto.setStartDate(startDate);//dateFormat.parse(strDate));
		balanceParamDto.setEndDate(endDate);//dateFormat.parse(endDate1));
		balanceParamDto.setQueryType(2);
		balanceParams.add(balanceParamDto);
		return balanceParams;
	}
	
	/*
	 *  反写mps参数组装 
	 */
	public	List<BalanceCallBackDto> getBalanceCallBack(String shopNo,String month,Date startDate,Date endDate,List<String> sporadicIds){
		List<BalanceCallBackDto> balanceCallBacks = new ArrayList<BalanceCallBackDto>();
		BalanceCallBackDto balanceCallBackDto= new BalanceCallBackDto();
		balanceCallBackDto.setShopNo(shopNo);
		balanceCallBackDto.setBalanceMonth(month);
		balanceCallBackDto.setBalanceDate(endDate);
		balanceCallBackDto.setBalanceType(1);
		balanceCallBackDto.setQueryType(2);
		balanceCallBackDto.setSiteFeeStatus(1);
		balanceCallBackDto.setSporadicIds(sporadicIds);
		balanceCallBacks.add(balanceCallBackDto);
		return balanceCallBacks;
	}
	
	/*
	 * 保存合同场地租赁费用   结算期扣费设置
	 */
	/*private int saveRateExpenseOperat(List<BalanceParamDto> params) throws RpcException, ServiceException{
		List<RateExpenseOperateDto>  RateExpenseOperateDtoList = balanceRateApi.getRateExpenseOperatesByParam(params);
		int  count=0;
		if (!CollectionUtils.isEmpty(RateExpenseOperateDtoList)) {
			for(RateExpenseOperateDto rateExpenseOperateDto : RateExpenseOperateDtoList){
				RateExpenseOperate  rateExpenseOperate = new RateExpenseOperate();
				rateExpenseOperate.setId(rateExpenseOperateDto.getId());
				rateExpenseOperate= rateExpenseOperatService.findById(rateExpenseOperate);
				//已经存在就先删掉，然后再添加
			    if(rateExpenseOperate != null){
			    	rateExpenseOperatService.deleteById(rateExpenseOperate);	
				}
				count=rateExpenseOperatService.add(rateExpenseOperateDto);	
			}
		}
		return count;
	}*/
	
	/*
	 * 保存零星费用 
	 */
	/*private int saveRateExpenseSporadic(List<BalanceParamDto> params) throws RpcException, ServiceException{
		List<RateExpenseSporadicDto>  RateExpenseSporadicDtoList =  balanceRateApi.getRateExpenseSporadicsByParam(params);
		int  count=0;
		if (!CollectionUtils.isEmpty(RateExpenseSporadicDtoList)) {
			for(RateExpenseSporadicDto rateExpenseSporadicDto : RateExpenseSporadicDtoList){
				RateExpenseSporadic  rateExpenseSporadic = new RateExpenseSporadic();
				rateExpenseSporadic.setId(rateExpenseSporadicDto.getId());
				rateExpenseSporadic= rateExpenseSporadicService.findById(rateExpenseSporadic);
				//已经存在就先删掉，然后再添加
			    if(rateExpenseSporadic != null){
			    	rateExpenseSporadicService.deleteById(rateExpenseSporadic);	
				}
			    count=rateExpenseSporadicService.add(rateExpenseSporadicDto);	
			}
		}
		return count;
	}*/

	@Override
	public int updateBalanceDeductBalanceNo(Map<String, Object> params) {
		return billShopBalanceDeductService.updateBalanceDeductBalanceNo(params);
	}

	@Override
	public BillShopBalanceDeduct getSumAmount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return billShopBalanceDeductService.getSumAmount(params);
	}

	/**
	 * 获取页脚汇总对象
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	@Override
	public BillShopBalanceDeductFooterDto getFooterDto(Map<String, Object> params) {
		return billShopBalanceDeductService.getFooterDto(params);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int remove(String[] ids,String createUser,Date createTime) throws ManagerException {
		int count = 1;
		try {
			for (String str : ids) {
				String rateId ="";
				String id = str.split(",")[0];
				if(str.split(",")[1] != null && !"".equals(str.split(",")[1]) && !"undefined".equals(str.split(",")[1])){
				  rateId = str.split(",")[1];
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", id);
				
				 if(StringUtils.isNotBlank(rateId) && !"null".equals(rateId)  && !"undefined".equals(rateId)){
				     params.put("rateId", rateId);
				 }
//				 params.put("balanceNo", "");
				List<BillShopBalanceDeduct> billShopBalanceDeductList= billShopBalanceDeductService.findByBiz(null, params);
				if (CollectionUtils.isEmpty(billShopBalanceDeductList)) {
					continue;
				}
				String shopNo = billShopBalanceDeductList.get(0).getShopNo();
				String month = billShopBalanceDeductList.get(0).getMonth();
				Date startDate = billShopBalanceDeductList.get(0).getBalanceStartDate();
//				Date endDate = billShopBalanceDeductList.get(0).getBalanceEndDate();
//				params.put("shopNo", shopNo);
				String remarkDesc ="";
				//0：系统自动生成，1：在界面上新增
				if(0==billShopBalanceDeductList.get(0).getGenerateType()){
					List<String> sporadicIds  = new ArrayList<String>();
					if(billShopBalanceDeductList.get(0).getRateId() != null && !"".equals(billShopBalanceDeductList.get(0).getRateId())){
						sporadicIds.add(billShopBalanceDeductList.get(0).getRateId());
						remarkDesc += billShopBalanceDeductList.get(0).getRateId().substring(27, 32)+"|";
					}
					//获取已结算的最大结束日期赋值给endDate
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
					Date endDatebef = formatter.parse("2000-01-01");
					
					Map<String, Object> shopBalanceDateMps = new HashMap<String, Object>();
					shopBalanceDateMps.put("shopNo", shopNo);
					shopBalanceDateMps.put("getBefMonth", 1);
					List<ShopBalanceDate> shopBalanceDateList = shopBalanceDateService.findByBiz(null, shopBalanceDateMps);
					if(!CollectionUtils.isEmpty(shopBalanceDateList)) {
						endDatebef=shopBalanceDateList.get(0).getBalanceEndDate();
					}
					if(2==billShopBalanceDeductList.get(0).getCostDeductType()){//因为票后不受结算单控制，有结算单的情况取结算期就不正确
						endDatebef=formatter.parse("2000-01-01");
					}
					List<BalanceCallBackDto> balanceCallBack=getBalanceCallBackDel(shopNo,month,startDate,endDatebef,sporadicIds);
					balanceRateApi.updateBalanceDate(balanceCallBack);
					
					//更新本地库的
					if(billShopBalanceDeductList.get(0).getRateId() != null && !"".equals(billShopBalanceDeductList.get(0).getRateId())){
						RateExpenseSporadic rateExpenseSporadic = new RateExpenseSporadic();
						rateExpenseSporadic.setId(billShopBalanceDeductList.get(0).getRateId());
						rateExpenseSporadic = rateExpenseSporadicService.findById(rateExpenseSporadic);
						if (rateExpenseSporadic != null) {
							rateExpenseSporadic.setShopNo(shopNo);
							rateExpenseSporadic.setSettlementDate(endDatebef);
							if (!CollectionUtils.isEmpty(sporadicIds)) {
								rateExpenseSporadic.setId(billShopBalanceDeductList.get(0).getRateId());
							}
							rateExpenseSporadicMapper.updateByPrimaryKey(rateExpenseSporadic);	
						}
					}
					
					BillShopBalanceOperatelog  operateLog = new    BillShopBalanceOperatelog();
					operateLog.setId(UUIDGenerator.getUUID());
					operateLog.setBalanceNo("");
					operateLog.setCompanyNo(billShopBalanceDeductList.get(0).getCompanyNo());
					operateLog.setCompanyName(billShopBalanceDeductList.get(0).getCompanyName());
					operateLog.setShopNo(shopNo);
					operateLog.setShortName(billShopBalanceDeductList.get(0).getShortName());
					operateLog.setMonth(month);
					operateLog.setOperateNo((byte) 3);
					operateLog.setCreateUser(createUser);
					operateLog.setCreateTime(createTime);
					operateLog.setBalanceStartDate(startDate);
					operateLog.setBalanceEndDate(billShopBalanceDeductList.get(0).getBalanceEndDate());
					operateLog.setRemarkDesc(remarkDesc);
//					operateLog.setMallNumberAmount(billShopBalanceList.get(0).getMallNumberAmount());
//					operateLog.setMallBillingAmount(billShopBalanceList.get(0).getMallBillingAmount());
					operateLog.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(billShopBalanceDeductList.get(0).getCompanyNo()));
				    billShopBalanceOperatelogService.add(operateLog);
				}
				billShopBalanceDeductService.deleteById(billShopBalanceDeductList.get(0).getId());
			}
		} catch (Exception e) {
			logger.error("删除费用失败。", e);
			count = 0;
			throw new ManagerException(e);
		}
		
		return count;
	}
	
	/*
	 *  反写mps参数组装 
	 */
	public	List<BalanceCallBackDto> getBalanceCallBackDel(String shopNo,String month,Date startDate,Date endDate,List<String> sporadicIds){
		List<BalanceCallBackDto> balanceCallBacks = new ArrayList<BalanceCallBackDto>();
		BalanceCallBackDto balanceCallBackDto= new BalanceCallBackDto();
		balanceCallBackDto.setShopNo(shopNo);
		balanceCallBackDto.setBalanceMonth(month);
		balanceCallBackDto.setBalanceDate(endDate);
		balanceCallBackDto.setBalanceType(0);
		balanceCallBackDto.setQueryType(0);
		balanceCallBackDto.setSiteFeeStatus(0);
		if (!CollectionUtils.isEmpty(sporadicIds)) {
		    balanceCallBackDto.setSporadicIds(sporadicIds);
		}
		balanceCallBacks.add(balanceCallBackDto);
		return balanceCallBacks;
	}
}