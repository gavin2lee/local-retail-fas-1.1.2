package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.enums.AlgorithmEnums;
import cn.wonhigh.retail.fas.common.enums.PriceBasisEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.service.PayRelationshipService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-18 16:58:06
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
@Service("payRelationshipManager")
class PayRelationshipManagerImpl extends BaseCrudManagerImpl implements PayRelationshipManager {
	
	private Logger logger = Logger.getLogger(PayRelationshipManagerImpl.class);
	
    @Resource
    private PayRelationshipService payRelationshipService;
    
    @Override
    public BaseCrudService init() {
        return payRelationshipService;
    }

	@Override
	public int findItemCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return payRelationshipService.findItemCount(params);
		}catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}


	@Override
	public List<PayRelationship> findItemList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return payRelationshipService.findItemList(page, sortColumn, sortOrder, params);
		}catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<PayRelationship> findFooter(Map<String, Object> params)
			throws ManagerException {
		try {
			return payRelationshipService.findFooter(params);
		}catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public int updateDueDate(Map<String, Object> params)throws ManagerException {
		try {
			return payRelationshipService.updateDueDate(params);
		}catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public PayRelationship findReturnProfitCount(Map<String, Object> params) throws ManagerException {
		try {
			return payRelationshipService.findReturnProfitCount(params);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}

	@Override
	public List<PayRelationship> findReturnProfitList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return payRelationshipService.findReturnProfitList(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}

	@Override
	public int updateDiscountNew(Map<String, Object> params)
			throws ManagerException, InterruptedException {
		try {
			int pageNo = 1;
			int pageSize = 10000;
			SimplePage page = new SimplePage(pageNo, pageSize, 0);
			SystemUser currUser = CurrentUser.getCurrentUser();
			Date currTime = new Date();
			while(true){
				ExecutorService exe = Executors.newFixedThreadPool(20);
				List<PayRelationship> updateDiscountList = payRelationshipService.selectUpdateDiscountList(page,params);
				for (PayRelationship payRelationship : updateDiscountList) {
					payRelationship.setUpdateUser(currUser.getUsername());
					payRelationship.setUpdateTime(currTime);
					exe.execute(new UpdateDiscountThread(payRelationship));
				}
				exe.shutdown();
		        exe.awaitTermination(2, TimeUnit.HOURS);
				if (updateDiscountList.size() < pageSize) {
					break;
				}
				params.put("lastId", updateDiscountList.get(updateDiscountList.size()-1).getId());
			}
			return 1;
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}
	@Override
	public int updateCostNew(Map<String, Object> params)
			throws ManagerException, InterruptedException {
		try {
			int pageNo = 1;
			int pageSize = 10000;
			SimplePage page = new SimplePage(pageNo, pageSize, 0);
			SystemUser currUser = CurrentUser.getCurrentUser();
			Date currTime = new Date();
			while(true){
				ExecutorService exe = Executors.newFixedThreadPool(20);
				List<PayRelationship> updateCostList = payRelationshipService.selectUpdateCostList(page,params);
				for (PayRelationship payRelationship : updateCostList) {
					payRelationship.setUpdateUser(currUser.getUsername());
					payRelationship.setUpdateTime(currTime);
					exe.execute(new UpdateCostThread(payRelationship));
				}
				exe.shutdown();
		        exe.awaitTermination(2, TimeUnit.HOURS);
				if (updateCostList.size() < pageSize) {
					break;
				} 
				params.put("lastId", updateCostList.get(updateCostList.size()-1).getId());
			}
			return 1;
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}
	
	@Override
	public List<PayRelationship> doImportCostNew(List<ValidateResultVo> listValidate) throws Exception {
		List<PayRelationship> errorList = new ArrayList<>();
		SystemUser currUser = CurrentUser.getCurrentUser();
		ExecutorService exe = Executors.newFixedThreadPool(20);
		for (ValidateResultVo validateVo : listValidate) {
			PayRelationship ship = (PayRelationship)validateVo.getValidateObj();
			if(validateVo.getPass() == YesNoEnum.YES.getValue()){
				ship.setUpdateUser(currUser.getUsername());
				ship.setUpdateTime(new Date());
				exe.execute(new ImportCostThread(ship));
			}else{
				errorList.add(ship);
			}
		}
		exe.shutdown();
        exe.awaitTermination(2, TimeUnit.HOURS);
		return errorList;
	}

	@Override
	public List<PayRelationship> doImportDiscountNew(List<ValidateResultVo> listValidate) throws Exception{
		List<PayRelationship> errorList = new ArrayList<>();
		SystemUser currUser = CurrentUser.getCurrentUser();
		ExecutorService exe = Executors.newFixedThreadPool(20);
		for (ValidateResultVo validateVo : listValidate) {
			PayRelationship ship = (PayRelationship)validateVo.getValidateObj();
			if(validateVo.getPass() == YesNoEnum.YES.getValue()){
				ship.setUpdateUser(currUser.getUsername());
				ship.setUpdateTime(new Date());
				exe.execute(new ImportDiscountThread(ship));
			}else{
				errorList.add(ship);
			}
			
		}
		exe.shutdown(); 
        exe.awaitTermination(2, TimeUnit.HOURS);
		return errorList;
	}

	@Override
	public List<PayRelationship> doImportTagPriceNew(List<ValidateResultVo> listValidate) throws ManagerException{
		try {
			List<PayRelationship> errorList = new ArrayList<>();
			for (ValidateResultVo validateVo : listValidate) {
				PayRelationship ship = (PayRelationship)validateVo.getValidateObj();
				if(validateVo.getPass() == YesNoEnum.YES.getValue()){
					Map<String, Object> params = new HashMap<String, Object>();
					if(StringUtils.isBlank(ship.getSettlementNumber()) 
							&& StringUtils.isBlank(ship.getBusinessBillNo())){
						continue ;
					}
					params.put("settlementNumber", ship.getSettlementNumber());
					params.put("businessBillNo", ship.getBusinessBillNo());
					List<PayRelationship> lstShip = payRelationshipService.findByBiz(null, params);
					if(lstShip.size() == 1 && StringUtils.isBlank(lstShip.get(0).getBalanceNo())){
						PayRelationship newShip = lstShip.get(0);
						newShip.setItemCode(ship.getItemCode());
						newShip.setTagPrice(ship.getTagPrice());
						payRelationshipService.importTagPrice(newShip);
						continue;
					}
				}
				errorList.add(ship);
			}
			return errorList;
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}
	
	class UpdateDiscountThread implements Runnable {
		
		private PayRelationship payRelationship;
		
		public UpdateDiscountThread(PayRelationship payRelationship) {
			this.payRelationship = payRelationship;
		}
		@Override
		public void run() {
			try {
				payRelationshipService.updateDiscount(payRelationship);
				payRelationship = payRelationshipService.findById(payRelationship);
				payRelationshipService.updateCost(payRelationship);
			} catch (Exception e) {
				logger.error("更新折扣异常,单据编号：".concat(payRelationship.getBusinessBillNo()), e);
			}
		}
	}
	
	class UpdateCostThread implements Runnable {
		
		private PayRelationship payRelationship;
		
		public UpdateCostThread(PayRelationship payRelationship) {
			this.payRelationship = payRelationship;
		}
		@Override
		public void run() {
			try {
				payRelationshipService.updateCost(payRelationship);
			} catch (Exception e) {
				logger.error("更新价格异常,单据编号：".concat(payRelationship.getBusinessBillNo()), e);
			}
		}
	}
	
	class ImportCostThread implements Runnable {
			
		private PayRelationship payRelationship;
		
		public ImportCostThread(PayRelationship payRelationship) {
			this.payRelationship = payRelationship;
		}
		@Override
		public void run() {
			try {
				if(StringUtils.isBlank(payRelationship.getSettlementNumber()) 
						&& StringUtils.isBlank(payRelationship.getBusinessBillNo())){
					return ;
				}
				payRelationshipService.updateBalanceFlag(payRelationship);
			} catch (Exception e) {
				logger.error("导入金额异常,结算号：".concat(payRelationship.getSettlementNumber()), e);
			}
		}
	}

	class ImportDiscountThread implements Runnable {
		
		private PayRelationship payRelationship;
		
		public ImportDiscountThread(PayRelationship payRelationship) {
			this.payRelationship = payRelationship;
		}
		@Override
		public void run() {
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				if(StringUtils.isBlank(payRelationship.getSettlementNumber()) 
						&& StringUtils.isBlank(payRelationship.getBusinessBillNo())){
					return ;
				}
				params.put("settlementNumber", payRelationship.getSettlementNumber());
				params.put("businessBillNo", payRelationship.getBusinessBillNo());
				List<PayRelationship> lstShip = payRelationshipService.findByBiz(null, params);
				if(lstShip.size() >0){
					PayRelationship newShip = lstShip.get(0);
					if(StringUtils.isBlank(newShip.getBalanceNo())){
						newShip.setUpdateUser(payRelationship.getUpdateUser());
						newShip.setUpdateTime(payRelationship.getUpdateTime());
						BigDecimal balanceDiscount1 = payRelationship.getBalanceDiscount1();
						BigDecimal balanceDiscount2 = payRelationship.getBalanceDiscount2();
						BigDecimal zoneOriginalDiscount1 = payRelationship.getZoneOriginalDiscount1();
						BigDecimal zoneOriginalDiscount2 = payRelationship.getZoneOriginalDiscount2();
						if(null != balanceDiscount1 || null != zoneOriginalDiscount1){
							if(null != balanceDiscount1){
								newShip.setBalanceDiscount1(balanceDiscount1);
								newShip.setBalanceDiscount2(balanceDiscount2);
							}
							if(null != zoneOriginalDiscount1){
								newShip.setZoneOriginalDiscount1(zoneOriginalDiscount1);
								newShip.setZoneOriginalDiscount2(zoneOriginalDiscount2);
							}
							newShip.setSupplierSendDate(payRelationship.getSupplierSendDate());
							payRelationshipService.updateCost(newShip);
						}else{
							if(null !=payRelationship.getSupplierSendDate()){
								newShip.setSupplierSendDate(payRelationship.getSupplierSendDate());
								payRelationshipService.modifyById(newShip);
							}
						}
					}
					
				}
			} catch (Exception e) {
				logger.error("导入折扣异常,结算号：".concat(payRelationship.getSettlementNumber()), e);
			}
		}
	}

	class ImportDiscountBuyThread implements Runnable {
		
		private PayRelationship payRelationship;
		
		public ImportDiscountBuyThread(PayRelationship payRelationship) {
			this.payRelationship = payRelationship;
		}
		@Override
		public void run() {
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				if(StringUtils.isBlank(payRelationship.getBusinessBillNo())){
					return ;
				}
				params.put("businessBillNo", payRelationship.getBusinessBillNo());
				List<PayRelationship> lstShip = payRelationshipService.findByBiz(null, params);
				if(lstShip.size() >0){
					PayRelationship newShip = lstShip.get(0);
					if(StringUtils.isBlank(newShip.getBalanceNo())){
						newShip.setItemCode(payRelationship.getItemCode());
						newShip.setTagPrice(payRelationship.getTagPrice());
						newShip.setUpdateUser(payRelationship.getUpdateUser());
						newShip.setUpdateTime(payRelationship.getUpdateTime());
						BigDecimal balanceDiscount1 = payRelationship.getBalanceDiscount1();
						BigDecimal zoneOriginalDiscount1 = payRelationship.getZoneOriginalDiscount1();
						if(null != balanceDiscount1 || null != zoneOriginalDiscount1){
							if(null != balanceDiscount1){
								balanceDiscount1 = balanceDiscount1.setScale(4,BigDecimal.ROUND_HALF_UP);
								newShip.setSupplierDiscount1(balanceDiscount1);
								newShip.setSupplierDiscount2(BigDecimal.ONE);
								newShip.setBalanceDiscount1(balanceDiscount1);
								newShip.setBalanceDiscount2(BigDecimal.ONE);
							}
							if(null != zoneOriginalDiscount1){
								zoneOriginalDiscount1 = zoneOriginalDiscount1.setScale(4,BigDecimal.ROUND_HALF_UP);
								newShip.setZoneOriginalDiscount1(zoneOriginalDiscount1);
								newShip.setZoneOriginalDiscount2(BigDecimal.ONE);
							}
							newShip.setAlgorithm(AlgorithmEnums.C.getTypeNo());
							newShip.setZoneAlgorithm(AlgorithmEnums.C.getTypeNo());
							newShip.setSupplierContractDiscountNo("C");
							newShip.setCompanyContractDiscountNo("C");
							newShip.setZonePriceBasis(String.valueOf(PriceBasisEnums.TAG_PRICE.getTypeNo()));
							payRelationshipService.updateCostBuy(newShip);
						}
					}
				}
			} catch (Exception e) {
				logger.error("导入折扣异常,单据编号：".concat(payRelationship.getBusinessBillNo()), e);
			}
		}
	}
	
	@Override
	public int generateBalanceNew(Map<String, Object> params)throws ManagerException{
		try {
			return payRelationshipService.generateBalanceNew(params);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}

	@Override
	public List<PayRelationship> doImportDiscountBuyNew(
			List<ValidateResultVo> listValidate) throws Exception {
		List<PayRelationship> errorList = new ArrayList<>();
		SystemUser currUser = CurrentUser.getCurrentUser();
		ExecutorService exe = Executors.newFixedThreadPool(20);
		for (ValidateResultVo validateVo : listValidate) {
			PayRelationship ship = (PayRelationship)validateVo.getValidateObj();
			if(validateVo.getPass() == YesNoEnum.YES.getValue()){
				ship.setUpdateUser(currUser.getUsername());
				ship.setUpdateTime(new Date());
				exe.execute(new ImportDiscountBuyThread(ship));
			}else{
				errorList.add(ship);
			}
		}
		exe.shutdown();
        exe.awaitTermination(2, TimeUnit.HOURS);
		return errorList;
	}

}