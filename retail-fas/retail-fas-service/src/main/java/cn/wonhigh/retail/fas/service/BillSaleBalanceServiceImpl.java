package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.dto.SaleOrderDto;
import cn.wonhigh.retail.fas.common.dto.WholesaleBalanceDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillSaleBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-10-16 17:38:17
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
@Service("billSaleBalanceService")
class BillSaleBalanceServiceImpl extends BaseCrudServiceImpl implements BillSaleBalanceService {
	
	@Resource
    private CodingRuleService codingRuleService; 
	
    @Resource
    private BillSaleBalanceMapper billSaleBalanceMapper;

    @Resource
    private BillBalanceMapper billBalanceMapper;
    
    @Override
    public BaseCrudMapper init() {
        return billSaleBalanceMapper;
    }

	@Override
	public int findSplitCount(Map<String, Object> params) {
		return billSaleBalanceMapper.findSplitCount(params);
	}

	@Override
	public int batchUpdateBalanceNoById(Map<String, Object> params)
			throws ServiceException {
		return billSaleBalanceMapper.batchUpdateBalanceNoById(params);
	}

	@Override
	public List<BillBalance> generateOtherStockOutBalance(
			Map<String, Object> params) throws ServiceException {
		return billSaleBalanceMapper.generateOtherStockOutBalance(params);
	}

	@Override
	public void saveBatchGenerateOtherStockOut(List<BillBalance> balances,
			BillBalance billBalance) throws ServiceException {
		try {
			for (BillBalance bill : balances) {
				// 初始化结算单信息
				initBillInfo(bill, billBalance);
				// 验证
				validate(bill);
				
				// 保存结算单信息
				billBalanceMapper.insertSelective(bill);
				
				// 反写出库明细的结算单号
				billSaleBalanceMapper.updateOtherStockOutBalanceNo(bill);
				
				// 保存明细
//				this.saveSaleBalanceDtl(bill);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	/**
	 * 初始化结算单信息
	 */
	private void initBillInfo(BillBalance bill, BillBalance billBalance) throws Exception{
		bill.setId(UUIDGenerator.getUUID());
		String billNo = "";
		try {
			billNo = codingRuleService.getSerialNo(BillBalance.class.getSimpleName());
		} catch (Exception e) {
			codingRuleService. recycleSerialNo(BillBalance.class.getSimpleName(),billNo);
			throw new ServiceException(e);
		}
		bill.setBillNo(billNo);
		bill.setBalanceType(billBalance.getBalanceType());
		bill.setStatus(billBalance.getStatus());
		bill.setBalanceStartDate(billBalance.getBalanceStartDate());
		bill.setBalanceEndDate(billBalance.getBalanceEndDate());
		bill.setCreateUser(billBalance.getCreateUser());
		bill.setCreateTime(billBalance.getCreateTime());
		if(StringUtils.isNotBlank(billBalance.getBillName())){
			bill.setBillName(billBalance.getBillName());
		}
		if(StringUtils.isNotBlank(billBalance.getRemark())){
			bill.setRemark(billBalance.getRemark());
		}
	}

	/**
	 * 根据查询条件汇总单据金额
	 * @param params 查询条件
	 * @return 汇总的单据金额
	 * @throws ServiceException 异常
	 */
	@Override
	public BillBalance sumBillCostAndQty(Map<String, Object> params) throws ServiceException {
		try {
			return billSaleBalanceMapper.sumBillCostAndQty(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 查询地区批发结算数据(批量生成结算单)
	 * @param params 查询条件
	 * @return 数据集合
	 * @throws ServiceException 异常
	 */
	@Override
	public List<WholesaleBalanceDto> findMulitZoneBalance(Map<String, Object> params) throws ServiceException {
		try {
			return billSaleBalanceMapper.findMulitZoneBalance(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 查询批发订单的数量
	 * @param params 查询参数
	 * @return 订单的数据
	 * @throws ServiceException 异常
	 */
	@Override
	public int findSaleOrderCount(Map<String, Object> params) throws ServiceException {
		try {
			return billSaleBalanceMapper.findSaleOrderCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 分页查询批发订单
	 * @param SimplePage 分页对象
	 * @param sortColumn 排序字段
	 * @param sortOrder 排序条件
	 * @param params 查询参数
	 * @return 订单数据集合
	 * @throws ServiceException 异常
	 */
	@Override
	public List<SaleOrderDto> findSaleOrderByPage(SimplePage page, String sortColumn, String sortOrder, 
			Map<String, Object> params) throws ServiceException {
		try {
			return billSaleBalanceMapper.findSaleOrderByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillSaleBalance> selectBillSaleBalanceByNo(Map<String, Object> params) {
		return billSaleBalanceMapper.selectBillSaleBalanceByNo(params);
	}

	@Override
	public List<BillSaleBalance> selectSaleOrder(SimplePage page, Map<String, Object> params) {
		return billSaleBalanceMapper.selectSaleOrder(page, params);
	}

	@Override
	public int selectSaleOrderCounts(Map<String, Object> params) {
		return billSaleBalanceMapper.selectSaleOrderCounts(params);
	}
	
	@Override
	public int selectSaleOrderCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return billSaleBalanceMapper.selectSaleOrderCount(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<SaleOrderDto> selectSaleOrderByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return billSaleBalanceMapper.selectSaleOrderByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int updateSaleBalanceCost(Map<String, Object> params)
			throws ServiceException {
		// TODO Auto-generated method stub
		return billSaleBalanceMapper.updateSaleBalanceCost(params);
	}

	@Override
	public void updateSaleBalanceIsBillInvoice(Map<String, Object> params)
			throws ServiceException {
		// TODO Auto-generated method stub
		billSaleBalanceMapper.updateSaleBalanceIsBillInvoice(params);
	}

	@Override
	public Integer queryBillSaleBalanceListForGeneratorInvoiceCount(
			Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return billSaleBalanceMapper.selectBillSaleBalanceListForGeneratorInvoiceCount(params);
	}

	@Override
	public List<BillSaleBalance> queryBillSaleBalanceListForGeneratorInvoice(
			Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return billSaleBalanceMapper.selectBillSaleBalanceListForGeneratorInvoice(params);
	}
	
	@Override
	public List<Object> selectEnterFooter(Map<String, Object> params)throws ServiceException {
		return billSaleBalanceMapper.selectEnterFooter(params);
	}
	
	/**
	 * 根据查询条件查询GMS 内购销售信息: 团购出库、报废、差异索赔、客残销售、盘差索赔
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findGmsInnerBuyDetailList(SimplePage page, String orderByField,String orderBy,Map<String,Object> params)throws ServiceException{
		return billSaleBalanceMapper.findGmsInnerBuyDetailList(page, orderByField, orderBy, params);
	}
	
	/**
	 * 根据查询条件查询GMS 内购销售记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findGmsInnerBuyDetailCount(Map<String,Object> params)throws ServiceException{
		return billSaleBalanceMapper.findGmsInnerBuyDetailCount(params);
	}

	@Override
	public Integer selectBrandCategoryDeductionCount(Map<String, Object> params)
			throws ServiceException {
		return billSaleBalanceMapper.selectBrandCategoryDeductionCount(params);
	}

	@Override
	public List<Object> selectBrandCategoryDeductionFooter(
			Map<String, Object> params) throws ServiceException {
		return billSaleBalanceMapper.selectBrandCategoryDeductionFooter(params);
	}

	@Override
	public List<BillSaleBalance> selectBrandCategoryDeductionByPage(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		return billSaleBalanceMapper.selectBrandCategoryDeductionByPage(page, orderByField, orderBy, params);
	}

	@Override
	public List<BillSaleBalance> selectSendAmountDeductByBrand(String balanceNo)
			throws ServiceException {
		return billSaleBalanceMapper.selectSendAmountDeductByBrand(balanceNo);
	}

	@Override
	public BillSaleBalance selectSendAmountByBalanceNo(String balanceNo)
			throws ServiceException {
		return billSaleBalanceMapper.selectSendAmountByBalanceNo(balanceNo);
	}

	@Override
	public int resetOtherDeductionByBalanceNo(String balanceNo)
			throws ServiceException {
		return billSaleBalanceMapper.resetOtherDeductionByBalanceNo(balanceNo);
	}

}