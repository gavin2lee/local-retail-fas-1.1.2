package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.SettlePathBillBasisEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.BillSplitData;
import cn.wonhigh.retail.fas.common.model.BillSplitDataDtl;
import cn.wonhigh.retail.fas.common.model.BillSplitLog;
import cn.wonhigh.retail.fas.common.model.BillSplitSettlePathDtl;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;
import cn.wonhigh.retail.fas.common.model.SettlePathDtlQueryVo;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;
import cn.wonhigh.retail.fas.service.BillSaleBalanceService;
import cn.wonhigh.retail.fas.service.BillSplitDataDtlService;
import cn.wonhigh.retail.fas.service.BillSplitDataService;
import cn.wonhigh.retail.fas.service.BillSplitLogService;
import cn.wonhigh.retail.fas.service.RegionCostMaintainService;
import cn.wonhigh.retail.fas.service.SettlePathService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 15:58:32
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
@SuppressWarnings({"unchecked", "rawtypes"})
@Service("billSplitDataManager")
class BillSplitDataManagerImpl extends BaseCrudManagerImpl implements BillSplitDataManager {
    
	@Resource
    private BillSplitDataService billSplitDataService;
	
	@Resource
	private BillSplitDataDtlService billSplitDataDtlService;
	
	@Resource
	private SettlePathService settlePathService;
	
	@Resource
	private BillSplitLogService billSplitLogService;
	
	@Resource
	private RegionCostMaintainService regionCostMaintainService;
	
	@Resource
	private BillBuyBalanceService billBuyBalanceService;
	
	@Resource
	private BillSaleBalanceService billSaleBalanceService;
	
//	@Resource
//	private PurchasePrice purchasePriceApi;
	
    @Override
    public BaseCrudService init() {
        return billSplitDataService;
    }

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=ManagerException.class)
	public int billSplit(BillSplitData model) throws ManagerException {
		BillSplitLog log = this.findLog(model);
		try {
			List<BillSplitDataDtl> lstSplitDataDtl = this.queryBillSplitDataDtl(model);
			if(lstSplitDataDtl == null || lstSplitDataDtl.size() == 0) {
				this.processLog(log, model, 2, "单据没有明细数据");
				return 0;
			}
			this.split(model, log, lstSplitDataDtl);
		} catch(Exception e) {
			//处理失败的日志在controller里处理
			throw new ManagerException(e.getMessage(), e);
		}
		return 0;
	}
	
	private int split(BillSplitData model, BillSplitLog log, List<BillSplitDataDtl> lstSplitDataDtl) 
			throws Exception {
		int buySplitIndex = 0, saleSplitIndex = 0;
		//查询该单据已拆分的条数
		Map<String, Object> splitDtlMap = new HashMap<String, Object>();
		splitDtlMap.put("refBillNo", model.getBillNo());
		splitDtlMap.put("isSpilt", 1);
		int buySplitCount = billBuyBalanceService.findSplitCount(splitDtlMap);
		int saleSplitCount = billSaleBalanceService.findSplitCount(splitDtlMap);
		List<BillBuyBalance> lstBuyBalance = new ArrayList<BillBuyBalance>();
		List<BillSaleBalance> lstSaleBalance = new ArrayList<BillSaleBalance>();
		for(int i = 0; i < lstSplitDataDtl.size(); i++) {
			BillSplitDataDtl dtl = lstSplitDataDtl.get(i);
			BillSplitSettlePathDtl billSplitSettlePathDtl = this.findSettlePathDtls(dtl, model);
			if(billSplitSettlePathDtl.getList() == null || billSplitSettlePathDtl.getList().size() == 0) {
				this.processLog(log, model, 1, billSplitSettlePathDtl.getErrorMsg());
				return 0;
			}
			for(int j = 1; j < billSplitSettlePathDtl.getList().size(); j++) {
				if(j == 1) { //应付
					BillBuyBalance buyBalance = this.buildBuyBalance(model, dtl, billSplitSettlePathDtl.getList().get(j), 
							lstBuyBalance);
					buyBalance.setSalerNo(model.getSupplierNo());
					buyBalance.setSalerName(model.getSupplierName());
					buyBalance.setBuyerNo(billSplitSettlePathDtl.getList().get(j).getCompanyNo()); //中间结算公司
					buyBalance.setBuyerName(billSplitSettlePathDtl.getList().get(j).getCompanyName());
					buyBalance.setBalanceType(BalanceTypeEnums.HQ_VENDOR.getTypeNo());
					// 设置单据编码bill_no
					List<BillBuyBalance> lstBillSplitDtl = this.findSplitDtl(model.getBillNo(), buyBalance.getSalerNo(), 
							buyBalance.getBuyerNo(), 1);
					if(lstBillSplitDtl == null || lstBillSplitDtl.size() == 0) {
						if(lstBuyBalance.contains(buyBalance)) {
							int dataIndex = lstBuyBalance.indexOf(buyBalance);
							buyBalance.setBillNo(lstBuyBalance.get(dataIndex).getBillNo());
						} else {
							buyBalance.setBillNo(dtl.getBillNo() + "_" + (buySplitIndex+1+buySplitCount));
							buySplitIndex++;
						}
					} else {
						buyBalance.setBillNo(lstBillSplitDtl.get(0).getBillNo());
					}
					lstBuyBalance.add(buyBalance);
				} else { // 应收
					//卖
					BillSaleBalance saleBalance = this.buildSaleBalance(model, dtl, billSplitSettlePathDtl.getList().get(j), 
							lstBuyBalance);
					saleBalance.setSalerNo(billSplitSettlePathDtl.getList().get(j - 1).getCompanyNo());
					saleBalance.setSalerName(billSplitSettlePathDtl.getList().get(j - 1).getCompanyName());
					saleBalance.setBuyerNo(billSplitSettlePathDtl.getList().get(j).getCompanyNo()); //中间结算公司
					saleBalance.setBuyerName(billSplitSettlePathDtl.getList().get(j).getCompanyName());
					// 设置单据编码bill_no
					List<BillSaleBalance> lstBillSplitDtl = this.findSplitDtl(model.getBillNo(), saleBalance.getSalerNo(), 
							saleBalance.getBuyerNo(), 2);
					if(lstBillSplitDtl == null || lstBillSplitDtl.size() == 0) {
						if(lstSaleBalance.contains(saleBalance)) {
							int dataIndex = lstSaleBalance.indexOf(saleBalance);
							saleBalance.setBillNo(lstSaleBalance.get(dataIndex).getBillNo());
						} else {
							saleBalance.setBillNo(dtl.getBillNo() + "_" + (saleSplitIndex+1+saleSplitCount));
							saleSplitIndex++;
						}
					} else {
						saleBalance.setBillNo(lstBillSplitDtl.get(0).getBillNo());
					}
					lstSaleBalance.add(saleBalance);
					
					//买
					BillBuyBalance buyBalance = this.buildBuyBalance(model, dtl, billSplitSettlePathDtl.getList().get(j), 
							lstBuyBalance);
					buyBalance.setSalerNo(billSplitSettlePathDtl.getList().get(j - 1).getCompanyNo());
					buyBalance.setSalerName(billSplitSettlePathDtl.getList().get(j - 1).getCompanyName());
					buyBalance.setBuyerNo(billSplitSettlePathDtl.getList().get(j).getCompanyNo()); //中间结算公司
					buyBalance.setBuyerName(billSplitSettlePathDtl.getList().get(j).getCompanyName());
					buyBalance.setBalanceType(BalanceTypeEnums.AREA_PURCHASE.getTypeNo());
					// 设置单据编码bill_no
					List<BillBuyBalance> lstBuyBillSplitDtl = this.findSplitDtl(model.getBillNo(), buyBalance.getSalerNo(), 
							buyBalance.getBuyerNo(), 1);
					if(lstBuyBillSplitDtl == null || lstBuyBillSplitDtl.size() == 0) {
						if(lstBuyBalance.contains(buyBalance)) {
							int dataIndex = lstBuyBalance.indexOf(buyBalance);
							buyBalance.setBillNo(lstBuyBalance.get(dataIndex).getBillNo());
						} else {
							buyBalance.setBillNo(dtl.getBillNo() + "_" + (buySplitIndex+1+buySplitCount));
							buySplitIndex++;
						}
					} else {
						buyBalance.setBillNo(lstBuyBillSplitDtl.get(0).getBillNo());
					}
					lstBuyBalance.add(buyBalance);
				}
			}
		}
		//批量插入拆弹明细汇总
		if(lstBuyBalance.size() > 0) {
			for(BillBuyBalance balance : lstBuyBalance) {
				billBuyBalanceService.add(balance);
			}
		}
		if(lstSaleBalance.size() > 0) {
			for(BillSaleBalance balance : lstSaleBalance) {
				billSaleBalanceService.add(balance);
			}
		}
		//修改明细表中的拆单标志
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", model.getBillNo());
		params.put("brandNo", model.getBrandNo());
		billSplitDataDtlService.updateSplitFlag(params);
		this.processLog(log, model, 0, "");
		return 0;
	}
	
	/**
	 * 查询拆单的数据
	 * @param billNo 原单据编码
	 * @param salerNo 卖方编码
	 * @param buyerNo 买方编码
	 * @param balanceType 结算类型（买/卖关系）
	 * @return List
	 * @throws ServiceException 异常
	 */
	private List findSplitDtl(String billNo, String salerNo, String buyerNo, int balanceType) 
			throws ServiceException {
		Map<String, Object> billSplitDtlMap = new HashMap<String, Object>();
		billSplitDtlMap.put("refBillNo", billNo);
		billSplitDtlMap.put("groupBy", "bill_no, ref_bill_no");
		billSplitDtlMap.put("salerNo", salerNo);
		billSplitDtlMap.put("buyerNo", buyerNo);
		if(balanceType == 1) {  //买关系
			List<BillBuyBalance> list = billBuyBalanceService.findByBiz(null, billSplitDtlMap);
			return list;
		}
		//卖关系
		List<BillSaleBalance> list = billSaleBalanceService.findByBiz(null, billSplitDtlMap);
		return list;
	}
	
	/**
	 * 组装BillBuyBalance对象
	 * @param model BillSplitData
	 * @param dtl BillSplitData
	 * @param pathDtl SettlePathDtl
	 * @param lstBuyBalance List<BillBuyBalance>
	 * @return BillBuyBalance
	 * @throws ServiceException 异常
	 */
	private BillBuyBalance buildBuyBalance(BillSplitData model, BillSplitDataDtl dtl, 
			SettlePathDtl pathDtl, List<BillBuyBalance> lstBuyBalance) 
					throws ServiceException {
		BillBuyBalance balance = new BillBuyBalance();
		balance.setId(UUIDGenerator.getUUID());
		balance.setRefBillNo(dtl.getBillNo());
		balance.setBrandNo(dtl.getBrandNo());
		balance.setBrandName(dtl.getBrandName());
		balance.setItemNo(dtl.getItemNo());
		balance.setItemName(dtl.getItemName());
		balance.setItemCode(dtl.getItemCode());
		balance.setCategoryNo(dtl.getCategoryNo());
		balance.setCategoryName(dtl.getCategoryName());
		balance.setSendDate(model.getSendOutDate());
		balance.setSendQty(dtl.getSendOutQty());
		balance.setTaxRate(model.getTaxRate());
		balance.setSupplierNo(model.getSupplierNo());
		balance.setBillCost(dtl.getSupplierCost());
		balance.setReceiveStoreNo(model.getStoreNo());
		balance.setReceiveStoreName(model.getStoreName());
		balance.setSendStoreNo(model.getStoreNo());
		balance.setSendStoreName(model.getStoreName());
		balance.setOrderNo(model.getOrderNo());
		balance.setBillType(model.getBillBasis());
		balance.setRefBillType(model.getBillType());
		balance.setStatus(model.getBillStatus());
		balance.setBizType(model.getBillType());
		
		// 取地区成本
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", dtl.getItemNo());
		params.put("zoneNo", pathDtl.getFinancialBasis());
		List<RegionCostMaintain> listCost = regionCostMaintainService.findByBiz(null, params);
		if(listCost != null && listCost.size() > 0) {
			balance.setCost(listCost.get(0).getRegionCost());
		} else {
			balance.setCost(BigDecimal.ZERO);
		}
		balance.setExclusiveCost(BigDecimalUtil.multi(balance.getCost(),
				BigDecimalUtil.subtract(new BigDecimal(1), balance.getTaxRate())));
		balance.setIsSplit(1);
		return balance;
	}
	
	/**
	 * 组装BillSaleBalance对象
	 * @param model BillSplitData
	 * @param dtl BillSplitData
	 * @param pathDtl SettlePathDtl
	 * @param lstBuyBalance List<BillBuyBalance>
	 * @return BillSaleBalance
	 * @throws ServiceException 异常
	 */
	private BillSaleBalance buildSaleBalance(BillSplitData model, BillSplitDataDtl dtl, 
			SettlePathDtl pathDtl, List<BillBuyBalance> lstBuyBalance) 
					throws ServiceException {
		BillSaleBalance balance = new BillSaleBalance();
		balance.setId(UUIDGenerator.getUUID());
		balance.setRefBillNo(dtl.getBillNo());
		balance.setBrandNo(dtl.getBrandNo());
		balance.setBrandName(dtl.getBrandName());
		balance.setItemNo(dtl.getItemNo());
		balance.setItemName(dtl.getItemName());
		balance.setItemCode(dtl.getItemCode());
		balance.setCategoryNo(dtl.getCategoryNo());
		balance.setCategoryName(dtl.getCategoryName());
		balance.setSendDate(model.getSendOutDate());
		balance.setSendQty(dtl.getSendOutQty());
		balance.setTaxRate(model.getTaxRate());
		balance.setSupplierNo(model.getSupplierNo());
		balance.setBillCost(dtl.getSupplierCost());
		balance.setSendStoreNo(model.getStoreNo());
		balance.setSendStoreName(model.getStoreName());
		balance.setOrderNo(model.getOrderNo());
		balance.setBillType(model.getBillBasis());
		balance.setRefBillType(model.getBillType());
		balance.setStatus(model.getBillStatus());
		balance.setBizType(model.getBillType());
		// 取地区成本
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", dtl.getItemNo());
		params.put("zoneNo", pathDtl.getFinancialBasis());
		List<RegionCostMaintain> listCost = regionCostMaintainService.findByBiz(null, params);
		if(listCost != null && listCost.size() > 0) {
			balance.setCost(listCost.get(0).getRegionCost());
		} else {
			balance.setCost(BigDecimal.ZERO);
		}
		balance.setExclusiveCost(BigDecimalUtil.multi(balance.getCost(),
				BigDecimalUtil.subtract(new BigDecimal(1), balance.getTaxRate())));
		balance.setIsSplit(1);
		return balance;
	}
	
	/**
	 * 查询单据明细
	 * @param model BillSplitData
	 * @return 单据明细链表
	 */
	private List<BillSplitDataDtl> queryBillSplitDataDtl(BillSplitData model) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", model.getBillNo());
		params.put("brandNo", model.getBrandNo());
		params.put("billBasis", model.getBillBasis());
		List<BillSplitDataDtl> list = billSplitDataDtlService.findByBiz(null, params);
		return list;
	}
	
	/**
	 * 根据单据明细，查询结算路径集合
	 * @param dtl 单据明细数据
	 * @param model 单据表头数据
	 * @return BillSplitSettlePathDtl
	 */
	private BillSplitSettlePathDtl findSettlePathDtls(BillSplitDataDtl dtl, BillSplitData model) 
			throws ManagerException {
		try {
//			SettlePathDtlQueryVo vo = new SettlePathDtlQueryVo();
//			vo.setBillType(model.getBillType());
//			vo.setSupplierNo(model.getSupplierNo());
//			vo.setCompanyNo(model.getCompanyNo());
//			vo.setBillBasis(model.getBillBasis());
//			vo.setBrandNo(dtl.getBrandNo());
//			vo.setCategoryNo(dtl.getCategoryNo());
//			vo.setYears(dtl.getYears());
//			vo.setSeason(dtl.getSeason());
			SettlePathDtlQueryVo vo = new SettlePathDtlQueryVo();
			int billType = model.getBillType();
			// 到货单的订补货是通过bizType区别的
			if(billType == BillTypeEnums.ASN.getRequestId().intValue()) {
				// YANGYONGTODO
				vo.setBillType(model.getBillType());
				vo.setBillBasis(Integer.parseInt(SettlePathBillBasisEnums.ORDERING.getValue()));
			} else if(billType == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
				vo.setReturnOwnFlag(1);
			}
			vo.setSupplierNo(model.getSupplierNo());
			vo.setCompanyNo(model.getCompanyNo());
			vo.setBrandNo(dtl.getBrandNo());
			vo.setCategoryNo(dtl.getCategoryNo());
			vo.setYears(dtl.getYears());
			vo.setSeason(dtl.getSeason());
			BillSplitSettlePathDtl billSplitSettlePathDtl = settlePathService.querySettlePathDtl(vo);
			return billSplitSettlePathDtl;
		} catch(Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 该方法在接口中声明，是为了操作失败时，在controller插入日志（成功的时候需要拆分后的数据与日志插入在同一事务中）
	 * 处理日志，若日志已存在，则更新数据，否则，新增数据
	 * @param log 原日志对象
	 * @param model BillSplitData
	 * @param status 日志状态
	 * @throws ManagerException 异常
	 */
	private void processLog(BillSplitLog log, BillSplitData model, int status, String failureReason) 
			throws ManagerException {
		try {
			if(log == null) {
				//插入日志表 YANGYONGTODO 需考虑在何处插入
				BillSplitLog billSplitLog = new BillSplitLog();
				billSplitLog.setRefBillNo(model.getBillNo());
				billSplitLog.setBillType(model.getBillBasis());
				billSplitLog.setSendOutDate(model.getSendOutDate());
				billSplitLog.setProcessStatus(status);
				billSplitLog.setSplitTime(new Date());
				billSplitLog.setFailureReason(failureReason);
				billSplitLog.setBrandNo(model.getBrandNo());
				billSplitLogService.add(billSplitLog);
			} else {
				log.setSendOutDate(model.getSendOutDate());
				log.setProcessStatus(status);
				log.setFailureReason(failureReason);
				log.setSplitTime(new Date());
				log.setBrandNo(model.getBrandNo());
				billSplitLogService.modifyById(log);
			}
		} catch(ServiceException e1) {
			throw new ManagerException(e1.getMessage(), e1);
		}
	}
	
	/**
	 * 该方法在接口中声明，是为了操作失败时，在controller插入日志（成功的时候需要拆分后的数据与日志插入在同一事务中）
	 * 通过组装的BillSplitData对象查询日志表中是否已存在billNo的数据
	 * @param model 单据表头数据
	 * @return BillSplitLog
	 * @throws ManagerException 异常
	 */
	private BillSplitLog findLog(BillSplitData model) throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("refBillNo", model.getBillNo());
			params.put("brandNo", model.getBrandNo());
			List<BillSplitLog> list = billSplitLogService.findByBiz(null, params);
			if(list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return null;
	}
	
	/**
    * 查询指定时间范围内，未拆分或拆单失败的退厂单数量
    * @param params 查询条件
    * @return 退厂单数量
    * @throws ManagerException 异常
    */
	@Override
	public int selectBillReturnCount(Map<String, Object> params) throws ManagerException {
		try {
			return billSplitDataService.selectBillReturnCount(params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
    * 查询指定时间范围内，未拆分或拆单失败的退厂单
    * @param params 查询条件
    * @return 退厂单
    * @throws ManagerException 异常
    */
	@Override
	public List<BillSplitData> findBillReturn(SimplePage page,String orderByField,String orderBy,Map<String,Object> params)
			throws ManagerException {
		try {
			return billSplitDataService.findBillReturn(page, null, null, params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}