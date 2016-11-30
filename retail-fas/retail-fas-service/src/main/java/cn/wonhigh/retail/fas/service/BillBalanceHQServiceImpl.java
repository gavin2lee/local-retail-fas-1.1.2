package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.backend.data.core.DbHelper;
import cn.wonhigh.retail.fas.common.dto.BillBalanceDto;
import cn.wonhigh.retail.fas.common.enums.BalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.CustomImperfect;
import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillBalanceHqMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillBuyBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillSaleBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.CustomImperfectMapper;
import cn.wonhigh.retail.fas.dal.database.OtherDeductionMapper;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途
 * 
 * @author wang.m1
 * @date 2014-09-05 10:33:45
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
@Service("billBalanceHQService")
class BillBalanceHQServiceImpl extends BaseCrudServiceImpl implements BillBalanceHQService {

	@Resource
	private CommonUtilService commonUtilService;

	@Resource
	private BillBalanceHqMapper billBalanceHqMapper;

	@Resource
	private BillBalanceMapper billBalanceMapper;

	@Resource
	private OtherDeductionMapper otherDeductionMapper;

	@Resource
	private CustomImperfectMapper customImperfectMapper;

	@Resource
	private BillBuyBalanceMapper billBuyBalanceMapper;

	@Resource
	private BillSaleBalanceMapper billSaleBalanceMapper;

	@Resource
	private OperateLogService operateLogService;

	@Override
	public BaseCrudMapper init() {
		return billBalanceMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int deleteById(Object obj) throws ServiceException {
		try {
			BillBalance billBalance = (BillBalance) obj;
			int type = (int) billBalance.getBalanceType();
			if (type == BalanceTypeEnums.HQ_VENDOR.getTypeNo()) {
				return deleteBuyBalance(billBalance.getId());
			} else if (type == BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()) {
				return deleteSaleBalance(billBalance.getId());
			} else if (type == BalanceTypeEnums.PE_SUPPLIER.getTypeNo()) {
				return deletePeBalance(billBalance.getId());
			} else if (type == BalanceTypeEnums.BAROQUE_RECEIPT.getTypeNo()) {
				return deleteBABalance(billBalance.getId());
			}
			return 0;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	private int deleteBABalance(String idList) {
		int iCount = 0;
		if (StringUtils.isNotBlank(idList)) {
			String idArr[] = idList.split(";");
			for (String str : idArr) {
				BillBalance billBalance = new BillBalance();
				billBalance.setId(str.split(",")[0]);
				String billNo = str.split(",")[1];
				billBalanceMapper.deleteByPrimarayKeyForModel(billBalance);// 删除结算单
				billBalanceMapper.clearBuyBalanceNo(billNo);// 清除明细结算单号
				iCount++;
			}
		}
		return iCount;
	}

	private int deletePeBalance(String idList) {
		int iCount = 0;
		if (StringUtils.isNotBlank(idList)) {
			String idArr[] = idList.split(";");
			for (String str : idArr) {
				BillBalance billBalance = new BillBalance();
				billBalance.setId(str.split(",")[0]);
				String billNo = str.split(",")[1];
				billBalanceMapper.deleteByPrimarayKeyForModel(billBalance);// 删除结算单
				billBalanceMapper.clearDeductionBalanceNo(billNo);// 清除其他扣项明细结算单号
				billBalanceMapper.clearPayRelationShip(billNo);// 清除关系表记录
				billBalanceMapper.clearBuyAdjustBalanceNo(billNo);// 清除采购入库调整单
				iCount++;
			}
		}
		return iCount;
	}

	@Override
	public List<BillBalance> queryBalanceList(Map<String, Object> params) throws ServiceException {
		try {
			String balanceType = String.valueOf(params.get("balanceType"));
			int iBalanceType = Integer.parseInt(balanceType);
			if (iBalanceType == BalanceTypeEnums.HQ_VENDOR.getTypeNo()) {// 总部厂商
				return billBalanceHqMapper.selectHqBuyGather(params);
			}
			return billBalanceHqMapper.selectHqSaleGather(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveBalanceList(List<BillBalance> lstBillBalance, BillBalance billBalance) throws ServiceException {
		try {
			int type = (int) billBalance.getBalanceType();
			if (type == BalanceTypeEnums.HQ_VENDOR.getTypeNo()) { // 总部厂商
				this.saveBuyBalance(lstBillBalance, billBalance);
			} else {
				this.saveSaleBalance(lstBillBalance, billBalance);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	/**
	 * 删除总部批发结算单
	 * 
	 * @param idList
	 * @return
	 * @throws ServiceException
	 */
	private int deleteSaleBalance(String idList) throws ServiceException {
		int iCount = 0;
		if (StringUtils.isNotBlank(idList)) {
			String idArr[] = idList.split(";");
			for (String str : idArr) {
				BillBalance billBalance = new BillBalance();
				billBalance.setId(str.split(",")[0]);
				String billNo = str.split(",")[1];
				billBalanceMapper.deleteByPrimarayKeyForModel(billBalance);// 删除结算单
				billBalanceMapper.clearSaleBalanceNo(billNo);// 清除出库明细结算单号
				billBalanceMapper.clearDeductionBalanceNo(billNo);// 清除其他扣项明细结算单号
				if(ShardingUtil.isPE()){
					billBalanceMapper.clearSaleAdjustBalanceNo(billNo);// 清除采购入库调整明细结算单号
				}
				operateLogService.deleteByDataAndModuleNo(billNo,
						String.valueOf(OperateLogEnums.OperateModule.JSD.getModuleNo()));// 审批日志
				iCount++;
			}
		}
		return iCount;
	}

	/**
	 * 删除总部厂商结算单
	 * 
	 * @param idList
	 * @return
	 * @throws ServiceException
	 */
	private int deleteBuyBalance(String idList) throws ServiceException {
		int iCount = 0;
		if (StringUtils.isNotBlank(idList)) {
			String idArr[] = idList.split(";");
			for (String str : idArr) {
				BillBalance billBalance = new BillBalance();
				billBalance.setId(str.split(",")[0]);
				String billNo = str.split(",")[1];
				billBalanceMapper.deleteByPrimarayKeyForModel(billBalance);// 删除结算单
				billBalanceMapper.clearBuyBalanceNo(billNo);// 清除入库明细结算单号
				billBalanceMapper.clearImperfectBalanceNo(billNo);// 清除客残明细结算单号
				billBalanceMapper.clearDeductionBalanceNo(billNo);// 清除其他扣项明细结算单号
				operateLogService.deleteByDataAndModuleNo(billNo,
						String.valueOf(OperateLogEnums.OperateModule.JSD.getModuleNo()));// 审批日志
				iCount++;
			}
		}
		return iCount;
	}

	/**
	 * 保存总部厂商结算单
	 * 
	 * @param idList
	 * @return
	 */
	private void saveBuyBalance(List<BillBalance> lstBillBalance, BillBalance billBalance) throws ServiceException {
		try {
			for (BillBalance bill : lstBillBalance) {
				// 初始化结算单信息
				this.initBillInfo(bill, billBalance);
				// 单据编号
				bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(bill.getBuyerNo(),
						FasBillTypeEnums.getRequestIdByBalanceType(bill.getBalanceType())));
				// 验证
				validate(bill);
				// 保存
				billBalanceMapper.insertSelective(bill);
				// 反写其他扣项明细结算单号
				billBalanceMapper.updateDeductionBalanceNo(bill);
				// 反写客残明细结算单号
				billBalanceMapper.updateImperfectBalanceNo(bill);
				// 设置查询条件
				bill.setQueryCondition(BalanceTypeEnums.getQueryConditionByNo(bill.getBalanceType()));
				// 反写采购入库明细结算单号
				billBalanceMapper.updateBuyBalanceNo(bill);
				// 反写单据采购价
				billBalanceMapper.updateBuyPurchasePrice(bill);
				// 记录审批日志
				this.insertOperateLog(bill);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 保存总部批发结算单
	 * 
	 * @param idList
	 * @return
	 */
	private void saveSaleBalance(List<BillBalance> lstBillBalance, BillBalance billBalance) throws ServiceException {
		try {
			for (BillBalance bill : lstBillBalance) {
				// 初始化结算单信息
				this.initBillInfo(bill, billBalance);
				// 单据编号
				bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(bill.getSalerNo(),
						FasBillTypeEnums.getRequestIdByBalanceType(bill.getBalanceType())));
				// 验证
				validate(bill);
				// 保存
				billBalanceMapper.insertSelective(bill);
				// 设置查询条件
				bill.setQueryCondition(BalanceTypeEnums.getQueryConditionByNo(bill.getBalanceType()));
				// 反写销售出库明细结算单号
				billBalanceMapper.updateSaleBalanceNo(bill);
				// 反写采购入库调整明细结算单号
				if(ShardingUtil.isPE()){
					billBalanceMapper.updateSaleAdjustBalanceNo(bill);
				}
				// 记录审批日志
				this.insertOperateLog(bill);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 初始化结算单信息
	 */
	private void initBillInfo(BillBalance bill, BillBalance billBalance) throws Exception {
		bill.setId(UUIDHexGenerator.generate());
		bill.setBalanceType(billBalance.getBalanceType());
		bill.setBalanceDate(billBalance.getBalanceDate());
		bill.setBalanceStartDate(billBalance.getBalanceStartDate());
		bill.setBalanceEndDate(billBalance.getBalanceEndDate());
		bill.setCreateUser(billBalance.getCreateUser());
		bill.setCreateTime(billBalance.getCreateTime());
		bill.setCurrency(billBalance.getCurrency());
		bill.setBillName(billBalance.getBillName());
		bill.setExtendCategoryNo(billBalance.getExtendCategoryNo());
		bill.setExtendCategoryName(billBalance.getExtendCategoryName());
		bill.setExtendCategoryCondition(billBalance.getExtendCategoryCondition());
		bill.setRemark(billBalance.getRemark());
		bill.setMultiBrandUnitNo(billBalance.getMultiBrandUnitNo());
		bill.setMultiBrandNo(billBalance.getMultiBrandNo());
		bill.setSupplierNo(billBalance.getSupplierNo());
		bill.setSupplierGroupNo(billBalance.getSupplierGroupNo());
		bill.setTwoLevelCategoryNo(billBalance.getTwoLevelCategoryNo());
		bill.setGender(billBalance.getGender());
		bill.setYears(billBalance.getYears());
		bill.setMultiCategoryNo(billBalance.getMultiCategoryNo());
		bill.setPriceRangeCondition(billBalance.getPriceRangeCondition());
		bill.setSystemCompanyNo(billBalance.getSystemCompanyNo());
	}

	/**
	 * 插入审批日志对象
	 * 
	 * @param billBalance
	 * @throws ServiceException
	 */
	private void insertOperateLog(BillBalance obj) throws ServiceException {
		OperateLog operateLog = new OperateLog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setDataNo(obj.getBillNo());
		operateLog.setModuleNo(OperateLogEnums.OperateModule.JSD.getModuleNo());
		operateLog.setCreateUser(obj.getCreateUser());
		operateLog.setCreateTime(obj.getCreateTime());
		operateLog.setStatus(BalanceStatusEnums.NO_CONFIRM.getTypeNo());
		operateLog.setStatusName(BalanceStatusEnums.NO_CONFIRM.getTypeName());
		operateLog.setOperateStatusName(BalanceStatusEnums.NO_CONFIRM.getTypeName());
		operateLogService.add(operateLog);
	}

	@Override
	public int selectBalanceCount(Map<String, Object> params) throws ServiceException {
		try {
			return billBalanceHqMapper.selectBalanceCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			return billBalanceHqMapper.selectBalanceByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceFooter(Map<String, Object> params) throws ServiceException {
		try {
			return billBalanceHqMapper.selectBalanceFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int selectEnterCount(Map<String, Object> params) throws ServiceException {
		if (null != params.get("balanceType")) {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			try {
				if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType
						|| BalanceTypeEnums.OTHER_DEDUCTION.getTypeNo() == iBalanceType) {
					return billBuyBalanceMapper.selectEnterCount(params);
				} else if (BalanceTypeEnums.AREA_PURCHASE.getTypeNo() == iBalanceType) {
					return billBuyBalanceMapper.selectAreaEnterCount(params);
				}
				return billSaleBalanceMapper.selectEnterCount(params);
			} catch (Exception e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}
		return 0;
	}

	@Override
	public List<Object> selectEnterList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		if (null != params.get("balanceType")) {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			try {
				if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType
						|| BalanceTypeEnums.OTHER_DEDUCTION.getTypeNo() == iBalanceType) {
					return billBuyBalanceMapper.selectEnterByPage(page, sortColumn, sortOrder, params);
				} else if (BalanceTypeEnums.AREA_PURCHASE.getTypeNo() == iBalanceType) {
					return billBuyBalanceMapper.selectAreaEnterByPage(page, sortColumn, sortOrder, params);
				}
				return billSaleBalanceMapper.selectEnterByPage(page, sortColumn, sortOrder, params);
			} catch (Exception e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> selectEnterFooter(Map<String, Object> params) throws ServiceException {
		int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
		try {
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType
					|| BalanceTypeEnums.OTHER_DEDUCTION.getTypeNo() == iBalanceType) {
				return billBuyBalanceMapper.selectEnterFooter(params);
			} else if (BalanceTypeEnums.AREA_PURCHASE.getTypeNo() == iBalanceType) {
				return billBuyBalanceMapper.selectAreaEnterFooter(params);
			}
			return billSaleBalanceMapper.selectEnterFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int selectItemGatherCount(Map<String, Object> params) throws ServiceException {
		try {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType) {
				return billBalanceHqMapper.selectBuyItemGatherCount(params);
			}
			return billBalanceHqMapper.selectSaleItemGatherCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object> selectItemGatherList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType) {
				return billBalanceHqMapper.selectBuyItemGatherList(page, sortColumn, sortOrder, params);
			}
			return billBalanceHqMapper.selectSaleItemGatherList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object> selectItemGatherFooter(Map<String, Object> params) throws ServiceException {
		try {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType) {
				return billBalanceHqMapper.selectBuyItemGatherFooter(params);
			}
			return billBalanceHqMapper.selectSaleItemGatherFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int selectBalanceGatherCount(Map<String, Object> params) throws ServiceException {
		try {
			String balanceType = String.valueOf(params.get("balanceType"));
			int iBalanceType = Integer.parseInt(balanceType);
			if (iBalanceType == BalanceTypeEnums.HQ_VENDOR.getTypeNo()) {
				return billBalanceHqMapper.selectHqBuyBalanceGatherCount(params);
			} else if (iBalanceType == BalanceTypeEnums.AREA_PURCHASE.getTypeNo()) {
				return billBalanceHqMapper.selectAreaGatherCount(params);
			}
			return billBalanceHqMapper.selectHqSaleBalanceGatherCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalanceDto> selectBalanceGatherList(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try {
			String balanceType = String.valueOf(params.get("balanceType"));
			int iBalanceType = Integer.parseInt(balanceType);
			if (iBalanceType == BalanceTypeEnums.HQ_VENDOR.getTypeNo()) {
				return billBalanceHqMapper.selectHqBuyBalanceGatherList(page, sortColumn, sortOrder, params);
			} else if (iBalanceType == BalanceTypeEnums.AREA_PURCHASE.getTypeNo()) {
				return billBalanceHqMapper.selectAreaGatherList(page, sortColumn, sortOrder, params);
			}
			return billBalanceHqMapper.selectHqSaleBalanceGatherList(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public List<BillBalanceDto> selectBalanceGatherFooter(Map<String, Object> params) throws ServiceException {
		try {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType) {
				return billBalanceHqMapper.selectHqBuyBalanceGatherFooter(params);
			} else if (iBalanceType == BalanceTypeEnums.AREA_PURCHASE.getTypeNo()) {
				return billBalanceHqMapper.selectAreaGatherFooter(params);
			}
			return billBalanceHqMapper.selectHqSaleBalanceGatherFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BillBalance balanceAdjust(BillBalance obj, List<Object> lstItem) throws ServiceException {
		try {
			BigDecimal deductionAmount = new BigDecimal(0);
			int deductionQty = 0;
			String billNo = obj.getBillNo();
			billBalanceMapper.clearDeductionBalanceNo(billNo);
			if (!CollectionUtils.isEmpty(lstItem)) {
				for (Object object : lstItem) {
					OtherDeduction deduction = (OtherDeduction) object;
					deduction.setBalanceNo(billNo);
					deduction.setBalanceType(BalanceTypeEnums.HQ_VENDOR.getTypeNo());
					otherDeductionMapper.updateByPrimaryKeySelective(deduction);
					deductionAmount = deductionAmount.add(deduction.getDeductionAmount());
					deductionQty += deduction.getDeductionQty();
				}
			}
			obj.setDeductionQty(deductionQty);
			obj.setDeductionAmount(deductionAmount);
			obj.setBalanceQty(obj.getOutQty() + obj.getReturnQty() - obj.getDeductionQty());
			obj.setBalanceAmount(obj.getOutAmount().add(obj.getReturnAmount()).subtract(deductionAmount));
			billBalanceMapper.updateByPrimaryKeySelective(obj);
			return obj;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BillBalance balanceAdjust(BillBalance obj, List<OtherDeduction> lstDeduction,
			List<CustomImperfect> lstImperfect) throws ServiceException {
		try {
			BigDecimal deductionAmount = new BigDecimal(0);
			BigDecimal customImperfectAmount = new BigDecimal(0);
			int customImperfectQty = 0;
			String billNo = obj.getBillNo();
			// 其他扣项
			billBalanceMapper.clearDeductionBalanceNo(billNo);
			if (!CollectionUtils.isEmpty(lstDeduction)) {
				for (Object object : lstDeduction) {
					OtherDeduction deduction = (OtherDeduction) object;
					deduction.setBalanceNo(billNo);
					deduction.setBalanceType(BalanceTypeEnums.HQ_VENDOR.getTypeNo());
					otherDeductionMapper.updateByPrimaryKeySelective(deduction);
					deductionAmount = deductionAmount.add(deduction.getDeductionAmount());
				}
			}
			// 客残明细
			billBalanceMapper.clearImperfectBalanceNo(billNo);
			if (!CollectionUtils.isEmpty(lstImperfect)) {
				for (CustomImperfect imperfect : lstImperfect) {
					imperfect.setBalanceNo(billNo);
					imperfect.setBalanceType(BalanceTypeEnums.HQ_VENDOR.getTypeNo());
					customImperfectMapper.updateByPrimaryKeySelective(imperfect);
					customImperfectAmount = customImperfectAmount.add(imperfect.getAmount());
					customImperfectQty += imperfect.getQty();
				}
			}
			obj.setDeductionAmount(deductionAmount);
			obj.setCustomReturnAmount(customImperfectAmount);
			obj.setCustomReturnQty(customImperfectQty);
			obj.setBalanceQty(obj.getOutQty() + obj.getReturnQty() - obj.getCustomReturnQty());
			obj.setBalanceAmount(obj.getOutAmount().add(obj.getReturnAmount()).subtract(deductionAmount)
					.subtract(customImperfectAmount));
			billBalanceMapper.updateByPrimaryKeySelective(obj);
			return obj;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BillBalance createBalance(BillBalance obj, List<Object> lstItem) throws ServiceException {
		try {
			obj.setId(UUIDHexGenerator.generate());
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == obj.getBalanceType().intValue()) {
				obj.setBillNo(commonUtilService.getNewBillNoCompanyNo(obj.getBuyerNo(),
						FasBillTypeEnums.getRequestIdByBalanceType(obj.getBalanceType())));
				return this.createBuyBalance(obj, lstItem);
			}
			obj.setBillNo(commonUtilService.getNewBillNoCompanyNo(obj.getSalerNo(),
					FasBillTypeEnums.getRequestIdByBalanceType(obj.getBalanceType())));
			return this.createSaleBalance(obj, lstItem);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 新增总部厂商结算单(选单)
	 * 
	 * @param obj
	 * @param lstItem
	 * @return
	 * @throws ServiceException
	 */
	private BillBalance createBuyBalance(BillBalance obj, List<Object> lstItem) throws ServiceException {
		BigDecimal outAmount = new BigDecimal(0);
		BigDecimal entryAmount = new BigDecimal(0);
		BigDecimal returnAmount = new BigDecimal(0);
		BigDecimal customReturnAmount = new BigDecimal(0);
		BigDecimal deductionAmount = new BigDecimal(0);
		int outQty = 0;
		int entryQty = 0;
		int returnQty = 0;
		int customReturnQty = 0;
		for (Object item : lstItem) {
			BillBuyBalance bill = (BillBuyBalance) item;
			bill.setBalanceNo(obj.getBillNo());
			bill.setBalanceType(BalanceTypeEnums.HQ_VENDOR.getTypeNo());
			bill.setBalanceStatus(BalanceStatusEnums.NO_CONFIRM.getTypeNo());
			billBuyBalanceMapper.updateBalanceInfoById(bill);
			if (bill.getBillType().intValue() == BillTypeEnums.ASN.getRequestId().intValue()) {
				outAmount = outAmount.add(bill.getCost().multiply(new BigDecimal(bill.getSendQty())));
				entryAmount = entryAmount.add(bill.getCost().multiply(new BigDecimal(bill.getReceiveQty())));
				outQty += bill.getSendQty();
				entryQty += bill.getReceiveQty();
			} else if (bill.getBillType().intValue() == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
				returnAmount = returnAmount.add(bill.getCost().multiply(new BigDecimal(bill.getSendQty())));
				returnQty += bill.getSendQty();
			}
		}
		// 其他扣项
		OtherDeduction deduction = otherDeductionMapper.selectByBillBalance(obj);
		if (deduction != null) {
			deductionAmount = deduction.getDeductionAmount();
			billBalanceMapper.updateDeductionBalanceNo(obj);
		}
		// 残鞋明细
		CustomImperfect imperfect = customImperfectMapper.selectByBillBalance(obj);
		if (imperfect != null) {
			customReturnAmount = imperfect.getAmount();
			customReturnQty = imperfect.getQty();
			billBalanceMapper.updateImperfectBalanceNo(obj);
		}

		obj.setOutAmount(outAmount);
		obj.setEntryAmount(entryAmount);
		obj.setReturnAmount(returnAmount);
		obj.setDeductionAmount(deductionAmount);
		obj.setCustomReturnAmount(customReturnAmount);
		obj.setOutQty(outQty);
		obj.setEntryQty(entryQty);
		obj.setReturnQty(returnQty);
		obj.setCustomReturnQty(customReturnQty);
		obj.setBalanceQty(outQty + returnQty - customReturnQty);
		obj.setBalanceAmount(outAmount.add(returnAmount).subtract(deductionAmount).subtract(customReturnAmount));
		billBalanceMapper.insertSelective(obj);
		this.insertOperateLog(obj); // 记录审批日志
		return obj;
	}

	/**
	 * 新增总部地区结算单(选单)
	 * 
	 * @param obj
	 * @param lstItem
	 * @return
	 * @throws ServiceException
	 */
	private BillBalance createSaleBalance(BillBalance obj, List<Object> lstItem) throws ServiceException {
		BigDecimal outAmount = new BigDecimal(0);
		BigDecimal entryAmount = new BigDecimal(0);
		BigDecimal returnAmount = new BigDecimal(0);
		int outQty = 0;
		int entryQty = 0;
		int returnQty = 0;
		for (Object item : lstItem) {
			BillSaleBalance bill = (BillSaleBalance) item;
			bill.setBalanceNo(obj.getBillNo());
			bill.setBalanceType(BalanceTypeEnums.HQ_WHOLESALE.getTypeNo());
			bill.setBalanceStatus(BalanceStatusEnums.NO_CONFIRM.getTypeNo());
			billSaleBalanceMapper.updateBalanceInfoById(bill);
			if (bill.getBillType().intValue() == BillTypeEnums.RETURNOWN.getRequestId().intValue()) {
				returnAmount = returnAmount.add(bill.getCost().multiply(new BigDecimal(bill.getSendQty())));
				returnQty += bill.getSendQty();
			} else {
				outAmount = outAmount.add(bill.getCost().multiply(new BigDecimal(bill.getSendQty())));
				entryAmount = entryAmount.add(bill.getCost().multiply(new BigDecimal(bill.getReceiveQty())));
				outQty += bill.getSendQty();
				entryQty += bill.getReceiveQty();
			}
		}
		obj.setOutAmount(outAmount);
		obj.setEntryAmount(entryAmount);
		obj.setReturnAmount(returnAmount);
		obj.setOutQty(outQty);
		obj.setEntryQty(entryQty);
		obj.setReturnQty(returnQty);
		obj.setBalanceQty(outQty + returnQty);
		obj.setBalanceAmount(outAmount.add(returnAmount));
		billBalanceMapper.insertSelective(obj);
		this.insertOperateLog(obj); // 记录审批日志
		return obj;
	}

	@Override
	public List<BillBalance> selectHqCount() throws ServiceException {
		try {
			return billBalanceHqMapper.selectHqCount();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int updateBalancePrice(Map<String, Object> params) throws ServiceException {
		try {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType
					|| BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo() == iBalanceType) {
				List<BillBuyBalance> lstBuy = billBalanceHqMapper.selectBuyBalanceExpricePrice(params);
				for (BillBuyBalance buyBalance : lstBuy) {
					billBuyBalanceMapper.updateByPrimaryKeySelective(buyBalance);
				}
				return 1;
			} else if (iBalanceType == BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()) {
				List<BillSaleBalance> lstSale = billBalanceHqMapper.selectSaleBalanceExpricePrice(params);
				for (BillSaleBalance saleBalance : lstSale) {
					billSaleBalanceMapper.updateByPrimaryKeySelective(saleBalance);
				}
				List<BillSaleBalance> lstSaleTransfer = billBalanceHqMapper.selectSaleBalancePriceForTransfer(params);
				for (BillSaleBalance saleBalance : lstSaleTransfer) {
					billSaleBalanceMapper.updateByPrimaryKeySelective(saleBalance);
				}
				return 1;
			}
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int queryZeroPriceBill(Map<String, Object> params) throws ServiceException {
		try {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType
					|| BalanceTypeEnums.HQ_INSTEADOF_BUY.getTypeNo() == iBalanceType) {
				return billBalanceHqMapper.queryBuyZeroPriceBillCount(params);
			} else if (iBalanceType == BalanceTypeEnums.HQ_WHOLESALE.getTypeNo()) {
				return billBalanceHqMapper.querySaleZeroPriceBillCount(params);
			}
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void findExportList(Map<String, Object> params, Function<Object, Boolean> handler) throws ServiceException {
		int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
		String statement = "cn.wonhigh.retail.fas.dal.database.billSaleBalanceMapper.selectEnterByPage";
		if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType
				|| BalanceTypeEnums.OTHER_DEDUCTION.getTypeNo() == iBalanceType) {
			statement = "cn.wonhigh.retail.fas.dal.database.billBuyBalanceMapper.selectEnterByPage";
		} else if (BalanceTypeEnums.AREA_PURCHASE.getTypeNo() == iBalanceType) {
			statement = "cn.wonhigh.retail.fas.dal.database.billBuyBalanceMapper.selectEnterByPage";
		}
		Map<String, Object> ps = new HashMap<String, Object>();
		SimplePage page = new SimplePage();
		page.setPageSize(Integer.MAX_VALUE);
		ps.put("page", page);
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int queryExcessPriceBillCount(Map<String, Object> params) throws ServiceException {
		try {
			int iBalanceType = Integer.parseInt(String.valueOf(params.get("balanceType")));
			if (BalanceTypeEnums.HQ_VENDOR.getTypeNo() == iBalanceType) {
				return billBalanceHqMapper.queryBuyExcessPriceBillCount(params);
			}
			return 0;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalance> selectBalanceForBaroque(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		try {
			return this.billBalanceHqMapper.selectBalanceForBaroque(page, orderByField, orderBy, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}

}