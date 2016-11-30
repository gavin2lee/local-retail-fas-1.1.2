package cn.wonhigh.retail.fas.manager;


import java.util.ArrayList;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.model.ExpectCash;
import cn.wonhigh.retail.fas.common.utils.CommonUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentShopVo;
import cn.wonhigh.retail.fas.service.ExpectCashService;
//import cn.wonhigh.retail.fas.web.controller.ShopBalanceDateController;
//import cn.wonhigh.retail.pos.api.client.service.system.CommonCodeGeneratorApi;
import cn.wonhigh.retail.pos.api.client.dto.finance.ExpectCashDto;
import cn.wonhigh.retail.pos.api.client.service.finance.ExpectCashApi;
import cn.wonhigh.retail.pos.api.client.service.system.CommonCodeGeneratorDto;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * ExpectCashManagerImpl
 * @author tang.yc
 * @date  2014-08-26 16:05:20
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
@Service("expectCashManager")
class ExpectCashManagerImpl extends BaseCrudManagerImpl implements ExpectCashManager {
    @Resource
    private ExpectCashService expectCashService;
    
    @Resource
	private ExpectCashApi expectCashApi;
    
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(ExpectCash.class);
    
//    @Resource
//    private CommonCodeGeneratorApi commonCodeGeneratorApi;

    @Override
    public BaseCrudService init() {
        return expectCashService;
    }

    @Override
	public int add(ExpectCash expectCash, SystemUser user, CurrentShopVo currentShopParams)
			throws ManagerException {
    	CommonCodeGeneratorDto codeGeneratorDto = new CommonCodeGeneratorDto();
    	try {
    		expectCash.setId(UUIDHexGenerator.generate());
    		codeGeneratorDto.setShopNo(currentShopParams.getShopNo());
//			codeGeneratorDto = commonCodeGeneratorApi.findShopBillNoByStoreAccounts(codeGeneratorDto, PublicConstans.EXPECT_CASH);
    		expectCash.setRefBillNo(expectCash.getBillNo());
    		expectCash.setBillNo(codeGeneratorDto.getSerialNo());
    		expectCash.setShopNo(currentShopParams.getShopNo());
    		expectCash.setShopName(currentShopParams.getShopName());
    		
    		expectCash.setCreateUserNo(Integer.toString(user.getUserid()));
    		expectCash.setCreateUser(user.getUsername());
    		expectCash.setCreateTime(new Date());
			
			return expectCashService.add(expectCash);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
    
    @Override
	public int modify(ExpectCash expectCash, SystemUser user, CurrentShopVo currentShopParams) throws ManagerException{
    	try {
    		expectCash.setShopNo(currentShopParams.getShopNo());
    		expectCash.setShopName(currentShopParams.getShopName());
    		expectCash.setUpdateUser(user.getUsername());
    		expectCash.setUpdateTime(new Date());
			return expectCashService.modifyById(expectCash);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
    
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByIds(String[] ids)throws ManagerException {
		try {
			for (String id : ids) {
				ExpectCash expectCash = new ExpectCash();
				expectCash.setId(id);
				expectCashService.deleteById(expectCash);
			}
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,String> verifyByIds(String[] ids, String flag,SystemUser user,String userName)
			throws ManagerException {
		Map<String,String> message = new HashMap<String, String>();
		List<String> fail = new ArrayList<String>(50);
		List<String> success = new ArrayList<String>(50);
		try {
			List<ExpectCashDto> expectCashDtos = new ArrayList();
			for (String id : ids) {
				ExpectCash expectCash = new ExpectCash();
				expectCash.setId(id);
				expectCash = expectCashService.findById(expectCash);
				expectCash.setUpdateUser(userName);
	    		expectCash.setUpdateTime(new Date());
				
				expectCash.setAuditorNo(Integer.toString(user.getUserid()));
				expectCash.setAuditor(userName);
				expectCash.setAuditTime(new Date());
				if("1".equals(flag)){
					expectCash.setConfirmFlag(PublicConstans.FIN_CONFIRM);
				}else if ("2".equals(flag)){
					expectCash.setConfirmFlag(PublicConstans.DATA_CONFIRM);
				}else
					expectCash.setConfirmFlag(PublicConstans.DATA_UNCONFIRM);
				
				
				expectCashService.modifyById(expectCash);
				
//					for(ExpectCash  expectCash : expectCashList){
					ExpectCashDto  expectCashDto = new ExpectCashDto();
						expectCashDto.setShopNo(expectCash.getShopNo());
						expectCashDto.setId(id);
						expectCash = expectCashService.findById(expectCash);
						expectCashDto.setConfirmFlag(expectCash.getConfirmFlag());
						expectCashDto.setMonth(expectCash.getMonth());
						expectCashDto.setUpdateUser(userName);
						expectCashDto.setUpdateTime(new Date());
						expectCash.setAuditorNo(Integer.toString(user.getUserid()));
						expectCashDto.setAuditor(userName);
						expectCashDto.setAuditTime(new Date());
						expectCashDto.setCategoryNo(expectCash.getCategoryNo());
						expectCashDto.setName(expectCash.getName());
						expectCashDto.setBrandUnitNo(expectCash.getBrandUnitNo());
						expectCashDto.setBrandUnitName(expectCash.getBrandUnitName());
						expectCashDto.setRate(expectCash.getRate());
					expectCashDtos.add(expectCashDto);
//			    }
				success.add(expectCash.getBillNo());
				
				//确认退款时修改收款单的已使用金额
//				if(expectCash.getBusinessFlag().equals(PublicConstans.EXPECT_CASH_REFUND)){
//					Map<String, Object> params = new HashMap<String, Object>();
//					params.put("shopNo", currentShopParams.getShopNo());
//					params.put("billNo", expectCash.getRefBillNo());
//					params.put("settleCode", expectCash.getSettleCode());
//					params.put("businessFlag", PublicConstans.EXPECT_CASH_RECEIPT);
//					List<ExpectCash> items = expectCashService.findByBiz(expectCash, params);
//					if(CommonUtil.hasValue(items)){
//						ExpectCash entity = items.get(0);
//						if(entity.getAmount().subtract(entity.getUsedAmount()).compareTo(expectCash.getAmount()) >= 0){
//							expectCashService.modifyById(expectCash);
//							success.add(expectCash.getBillNo());
//							entity.setUsedAmount(entity.getUsedAmount().add(expectCash.getAmount()));
//							entity.setUpdateTime(new Date());
//							entity.setUpdateUser(expectCash.getUpdateUser());
//							expectCashService.modifyById(entity);
//						}else{
//							fail.add("【" + expectCash.getBillNo() + "】退款金额不能大于原单【" + expectCash.getRefBillNo() + 
//									"】可用金额:" + entity.getAmount().subtract(entity.getUsedAmount()).toString());
//						}
//					}
//				}else{
//					
//				}
			}
			try {
				expectCashApi.confirmExpectCash(expectCashDtos);
			} catch (RpcException e) {
				LOGGER.error(e.getMessage(),e);
				throw new ManagerException(e.getMessage(), e);
			}
			
			message.put("success", success.toString());
			message.put("fail", fail.toString());
			return message;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public boolean verifyTheOnly(ExpectCash expectCash) throws ManagerException{
		try {
//			PublicConstans.EXPECT_CASH_BOOK == expectCash.getBusinessType() && 
			if (StringUtils.isBlank(expectCash.getSettleCode())) {	
				return true;
			} else {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("shopNo", expectCash.getShopNo());
				params.put("settleCode", expectCash.getSettleCode());
				params.put("businessFlag", expectCash.getBusinessFlag().toString());
				List<ExpectCash> item = expectCashService.findByBiz(expectCash, params);
				if(CollectionUtils.isEmpty(item)){
					return true;
				}else{
					ExpectCash entity = item.get(0);
					if(1 == item.size() && entity.getId().equals(expectCash.getId())){
						return true;
					}else{
						return false;
					}
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public boolean verifyPrice(ExpectCash expectCash) throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", expectCash.getShopNo());
			params.put("billNo", expectCash.getBillNo());
			params.put("settleCode", expectCash.getSettleCode());
			params.put("businessFlag", PublicConstans.EXPECT_CASH_RECEIPT);
			List<ExpectCash> item = expectCashService.findByBiz(expectCash, params);
			if(CollectionUtils.isEmpty(item)){
				throw new ManagerException();
			}else{
				ExpectCash entity = item.get(0);
				if(1 == item.size() && entity.getAmount().subtract(entity.getUsedAmount()).compareTo(expectCash.getAmount()) < 0){
					return true;
				}else{
					return false;
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public boolean verifyRefundPrice(ExpectCash expectCash) throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", expectCash.getShopNo());
			params.put("billNo", expectCash.getRefBillNo());
			//params.put("settleCode", expectCash.getSettleCode());
			params.put("businessFlag", PublicConstans.EXPECT_CASH_RECEIPT);
			List<ExpectCash> item = expectCashService.findByBiz(expectCash, params);
			if(CollectionUtils.isEmpty(item)){
				throw new ManagerException();
			}else{
				ExpectCash entity = item.get(0);
				if(1 == item.size() && entity.getAmount().subtract(entity.getUsedAmount()).compareTo(expectCash.getAmount()) < 0){
					return true;
				}else{
					return false;
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
//
	@Override
	public List<ExpectCash> findExpectCashByParams(Map<String, Object> params)
			throws ManagerException {
		try {
			return expectCashService.findExpectCashByParams(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public List<ExpectCash> findExpectCashByParams(SimplePage page,
			String sortColumn, String orderBy, Map<String, Object> params)
			throws ManagerException {
		try {
			return expectCashService.findExpectCashByParams(page, orderBy, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
		
	}

	@Override
	public int countExpectCashByParams(Map<String, Object> params) 
			throws ManagerException {
		try {
			return expectCashService.countExpectCashByParams(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ExpectCash> processExpectCashBalanceDiff(
			Map<String, Object> params) throws ManagerException {
		try {
			return expectCashService.processExpectCashBalanceDiff(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ExpectCash> processUseExpectCashBalanceDiff(
			Map<String, Object> params) throws ManagerException {
		try {
			return expectCashService.processUseExpectCashBalanceDiff(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public ExpectCash getExpectCashAmount(Map<String, Object> params)
			throws ManagerException {
		try {
			return expectCashService.getExpectCashAmount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
}