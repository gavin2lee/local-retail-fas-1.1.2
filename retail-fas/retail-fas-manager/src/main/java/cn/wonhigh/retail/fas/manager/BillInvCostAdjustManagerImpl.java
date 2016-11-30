package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.BillStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillInvCostAdjust;
import cn.wonhigh.retail.fas.common.model.BillInvCostAdjustDtl;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.InventoryBook;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BillInvCostAdjustDtlService;
import cn.wonhigh.retail.fas.service.BillInvCostAdjustService;
import cn.wonhigh.retail.fas.service.InventoryBookService;
import cn.wonhigh.retail.fas.service.ItemCostService;
import cn.wonhigh.retail.fas.service.ItemService;
import cn.wonhigh.retail.fas.service.OrderUnitService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-31 16:10:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("billInvCostAdjustManager")
class BillInvCostAdjustManagerImpl extends BaseCrudManagerImpl implements BillInvCostAdjustManager {
	@Resource
    private BillInvCostAdjustService billInvCostAdjustService;
    
    @Resource
    private BillInvCostAdjustDtlService billInvCostAdjustDtlService;
    
    @Resource
    private InventoryBookService inventoryBookService;
    
    @Resource
    private ItemService itemService;
    
    @Resource
    private ItemCostManager itemCostManager;
    
    @Resource
    private OrderUnitService orderUnitService;
    
    @Resource
    private ItemCostService itemCostService;
    
    @Resource
    private CodingRuleService codingRuleService;
    
    @Override
    public BaseCrudService init() {
        return billInvCostAdjustService;
    }
    
    /**
     * 新增或修改操作
     * @param model BillInvCostAdjust对象
     * @return 更新后的BillInvCostAdjust对象
     * @throws Exception 
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillInvCostAdjust addFetchId(BillInvCostAdjust model) throws Exception {
		try{
	    	if (StringUtils.isBlank(model.getBillNo())) {
				model.setBillNo(codingRuleService.getSerialNo("CA"));
				model.setId(UUIDGenerator.getUUID());
				model.setBillType(BillTypeEnums.INVENTORY_COST_ADJUST.getRequestId());
				model.setStatus(BillStatusEnums.CREATE.getTypeNo());
				model.setCreateTime(DateUtil.getCurrentDateTime());
				super.add(model);
			} else {
				super.modifyById(model);
			}
			return model;
		} catch (ServiceException e) {
			if(null != model.getBillNo() && !"".equals(model.getBillNo())) {
				
			}
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
    /**
	 * 删除操作
	 * @param list 待删除的数据集合
	 * @return 删除的记录数
	 * @throws ManagerException 异常
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int delete(List<BillInvCostAdjust> list) throws ManagerException {
		int count = 0;
		try {
			if(list != null && list.size() > 0) {
				for(BillInvCostAdjust dto : list) {
					//删除表头数据
					count += super.deleteById(dto);
					//删除表体数据
					billInvCostAdjustDtlService.deleteByBillNo(dto.getBillNo());
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return count;
	}
	
	/**
	 * 查询结算公司数量
	 * @param params 查询参数
	 * @return 数量
	 * @throws ManagerException 异常
	 */
	@Override
	public int findCompanyCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.billInvCostAdjustService.findCompanyCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 查询结算公司集合
	 * @param params 查询参数
	 * @return 查询到的集合
	 * @throws ManagerException 异常
	 */
	@Override
	public List<Company> findCompanyByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		try {
			return this.billInvCostAdjustService.findCompanyByPage(page, orderByField, orderBy, params);
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 审核单据
	 * @param model BillInvCostAdjust对象
	 * @return 设置后的BillInvCostAdjust对象
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillInvCostAdjust confirm(BillInvCostAdjust model) throws Exception {
		try {
			//修改销售出库单状态
			SystemUser currentUser = CurrentUser.getCurrentUser();
			model.setAuditor(currentUser.getUsername());
			model.setAuditTime(DateUtil.getCurrentDateTime());
			model.setStatus(BillStatusEnums.CONFIRM.getTypeNo());
			int count = modifyById(model);
			model = billInvCostAdjustService.findById(model);
			if(count > 0) {
				// 修改成本主文件中的成本
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("billNo", model.getBillNo());
				params.put("shardingFlag", model.getShardingFlag());
				List<BillInvCostAdjustDtl> lstDtl = billInvCostAdjustDtlService.findByBiz(null, params);
				if(lstDtl != null && lstDtl.size() > 0) {
					this.operateInventoryBookOrItemCost(model, lstDtl);
				}
//				InvCostGenerateDto invCostGenerateDto = this.createInvCostGenerateDto(model, lstDtl);
//				periodBalanceService.accountMonthAllData(invCostGenerateDto);
//				periodBalanceService.generateMonthEndWeightedCost(invCostGenerateDto);
			} 
			return model;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 组装指定年、月对应的最小日期或最大日期
	 * @param year 年
	 * @param month 月
	 * @param maxOrMinDayFlag 最小日期或最大日期标志
	 * @return Date
	 */
	private Date parseToDate(String year, String month, int maxOrMinDayFlag) {
		if(StringUtils.isEmpty(year) || StringUtils.isEmpty(month)) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		int day = 1;
		if(maxOrMinDayFlag == 1) {
			day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		calendar.set(Calendar.DATE, day);
		Date date = calendar.getTime();
		return date;
	}
	
	/**
	 * 新增库存流水账或单位成本
	 * @param model 库存成本调整表头对象
	 * @param lstDtl 库存成本调整明细对象集合
	 * @throws ManagerException 异常
	 */
	private void operateInventoryBookOrItemCost(BillInvCostAdjust model, List<BillInvCostAdjustDtl> lstDtl) 
			throws ManagerException {
		try {
			if(lstDtl != null && lstDtl.size() > 0) {
				for(BillInvCostAdjustDtl dtl : lstDtl) {
//					String orderUnitNo = "";
//					BigDecimal diverAmount = this.getDiverAmount(model, dtl);
//					List<Item> items = null;
//					Map<String, Object> itemMap = new HashMap<String, Object>();
//					// 修改结存
//					if(diverAmount != null && diverAmount.doubleValue() != 0) {
//						orderUnitNo = getOrderUnitNo(model.getCompanyNo(), orderUnitNo);
//						InventoryBook inventoryBook = new InventoryBook();
//						inventoryBook.setId(UUIDGenerator.getUUID());
//						inventoryBook.setBillNo(model.getBillNo());
//						inventoryBook.setBillType(model.getBillType());
//						inventoryBook.setCompanyNo(model.getCompanyNo());
//						inventoryBook.setOrderUnitNo(orderUnitNo);
//						inventoryBook.setStoreNo("ALL");
//						inventoryBook.setItemNo(dtl.getItemNo());
//						inventoryBook.setItemCode(dtl.getItemCode());
//						inventoryBook.setItemName(dtl.getItemName());
//						inventoryBook.setSizeKind(dtl.getSizeKind());
//						
//						inventoryBook.setShardingFlag(model.getShardingFlag());
//						
//						itemMap.put("itemNo", dtl.getItemNo());
//						items = itemService.findByBiz(null, itemMap);
//						if (!CollectionUtils.isEmpty(items)) {
//							inventoryBook.setBrandNo(items.get(0).getBrandNo());
//							inventoryBook.setCategoryNo(items.get(0).getCategoryNo());
//						}
//						
//						//默认为itemNo
//						inventoryBook.setSkuNo(dtl.getItemNo());
//						inventoryBook.setCost(diverAmount);
//						// 组装单据日期
//						inventoryBook.setBillDate(parseToDate(model.getYear(), model.getMonth(), 0));
//						inventoryBook.setCreateTime(model.getAuditTime());
////						inventoryBook.setShardingFlag(dtl.getShardingFlag());
//						inventoryBookService.add(inventoryBook);
//					}
					//修改成本主文件
					this.modifyItemCost(model, dtl);
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 将本次调整的成本与汇总指定公司、货号、年月调整的差额进行相减
	 * @param model 库存成本调整主表
	 * @param dtl 库存成本调整明细表
	 * @return 相减操作得到的值
	 * @throws ServiceException 异常
	 */
	private BigDecimal getDiverAmount(BillInvCostAdjust model, BillInvCostAdjustDtl dtl) 
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", model.getCompanyNo());
		params.put("year", model.getYear());
		params.put("month", model.getMonth());
		params.put("itemNo", dtl.getItemNo());
		// 只取确认状态的单据
		params.put("status", BillStatusEnums.CONFIRM.getTypeNo());
		// 过滤掉当前单据
		params.put("billNo", model.getBillNo());
		params.put("shardingFlag", model.getShardingFlag());
//		BigDecimal result = billInvCostAdjustService.sumDiverAmount(params);
		BillInvCostAdjustDtl lastDtl = billInvCostAdjustDtlService.findLastJoinHeaderByParams(params);
		BigDecimal dtlAmount = dtl.getDiverAmount();
		BigDecimal lastDiverAmount = null;
		if(lastDtl != null) {
			lastDiverAmount = lastDtl.getDiverAmount();
		}
		if(lastDiverAmount == null) {
			lastDiverAmount = BigDecimal.ZERO;
		}
		if(dtlAmount == null) {
			dtlAmount = BigDecimal.ZERO;
		}
		Double amount = dtlAmount.doubleValue() - lastDiverAmount.doubleValue();
		return new BigDecimal(amount);
	}
	
	/**
	 * 获取指定结算公司的订货单位编码
	 * @param companyNo 结算公司编码
	 * @param orderUnitNo 原订货单位（如果为空，则执行查询，否则原值返回）
	 * @return 订货单位编码
	 * @throws ServiceException 异常
	 */
	private String getOrderUnitNo(String companyNo, String orderUnitNo) throws ServiceException {
		if(StringUtils.isEmpty(orderUnitNo)) {
			Map<String, Object> params = new HashMap<String, Object>();
			// OrderUnitMapper.xml需要此参数
			params.put("flag", "1");
			params.put("companyNo", companyNo);
			List<OrderUnit> lstOrderUnit = orderUnitService.findByBiz(null, params);
			if(lstOrderUnit != null && lstOrderUnit.size() > 0) {
				orderUnitNo = lstOrderUnit.get(0).getOrderUnitNo();
			}
		}
		return orderUnitNo;
	}
	
	/**
	 * 修改或新增成本核算数据
	 * @param model 库存成本调整表头对象
	 * @param dtl 库存成本调整明细对象
	 * @throws ManagerException 异常
	 */
	private void modifyItemCost(BillInvCostAdjust model, BillInvCostAdjustDtl dtl) throws ServiceException, ManagerException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", model.getCompanyNo());
		params.put("year", model.getYear());
		params.put("month", model.getMonth());
		params.put("itemNo", dtl.getItemNo());
		params.put("shardingFlag", model.getShardingFlag());
		List<ItemCost> lstItemCost = itemCostService.findByBiz(null, params);
		if(lstItemCost != null && lstItemCost.size() > 0) {
			ItemCost itemCost = lstItemCost.get(0);
			itemCost.setUnitCost(dtl.getAdjustCost());
			itemCost.setLot("M");//手动调整的成本标识
			itemCost.setUpdateUser(model.getAuditor());
			itemCost.setUpdateTime(model.getAuditTime());
			itemCostManager.modifyById(itemCost);
		} else {
			ItemCost itemCost = new ItemCost();
			itemCost.setId(UUIDGenerator.getUUID());
			itemCost.setItemNo(dtl.getItemNo());
			itemCost.setItemCode(dtl.getItemCode());
			itemCost.setItemName(dtl.getItemName());
			itemCost.setBrandNo(dtl.getBrandNo());
			itemCost.setCompanyNo(model.getCompanyNo());
			itemCost.setCompanyName(model.getCompanyName());
			itemCost.setYear(model.getYear());
			itemCost.setMonth(model.getMonth());
			itemCost.setShardingFlag(model.getShardingFlag());
			itemCost.setUnitCost(dtl.getAdjustCost());
			itemCost.setLot("M");//手动调整的成本标识
			itemCost.setCreateUser(model.getAuditor());
			itemCost.setCreateTime(model.getAuditTime());
			itemCostService.add(itemCost);
		}
	}

	/**
	 * 通过查询条件，获取结存对象
	 * @param params 查询条件
	 * @return 结存对象
	 * @throws ManagerException 异常
	 */
	@Override
	public PeriodBalance findPeriodBalance(Map<String, Object> params) throws ManagerException {
		try {
			List<PeriodBalance> lstPeriodBalance = billInvCostAdjustService.findPeriodBalance(params);
			if(lstPeriodBalance != null && lstPeriodBalance.size() > 0) {
				return lstPeriodBalance.get(0);
			}
			return null;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public PeriodBalance findPeriodBalanceBLK(Map<String, Object> params) throws ManagerException {
		try {
			return billInvCostAdjustService.findPeriodBalanceBLK(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 获取开关控制
	 * @return Map
	 * @throws ManagerException
	 */
	@Override
	public Map<String, String> getControllerFlag() throws ManagerException {
		try {
			return billInvCostAdjustService.getControllerFlag();
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}