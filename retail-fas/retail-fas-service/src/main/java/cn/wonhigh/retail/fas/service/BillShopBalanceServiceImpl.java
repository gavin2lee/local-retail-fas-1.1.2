package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.GatherDaysaleSumDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途
 * 
 * @author chen.mj
 * @date 2014-09-04 17:20:02
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
@Service("billShopBalanceService")
class BillShopBalanceServiceImpl extends BaseCrudServiceImpl implements BillShopBalanceService {
	
	@Resource
	private BillShopBalanceMapper billShopBalanceMapper;	
	
	@Override
	public BaseCrudMapper init() {
		return billShopBalanceMapper;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int delete(String[] ids) {
		return 0;

//			int iCount = 0;
//			for (String str : ids) {
//				String id = str.split(",")[0];
//				String balanceNo = str.split(",")[1];
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("id", id);
//				params.put("balanceNo", balanceNo);
//				List<BillShopBalance> billShopBalanceList= billShopBalanceMapper.selectByParams(null, params);
//				
//				String shopNo=null;
//				String month=null;
//				Date startDate = null;
//				Date endDate = null;
//				if (!CollectionUtils.isEmpty(billShopBalanceList)) {
//					shopNo=billShopBalanceList.get(0).getShopNo();
//					month=billShopBalanceList.get(0).getMonth(); 
//					startDate=billShopBalanceList.get(0).getBalanceStartDate();
//					endDate=billShopBalanceList.get(0).getBalanceEndDate();
//				}
//				
//				//更新结算期     未结算标示
//				ShopBalanceDate  shopBalanceDatePar = new ShopBalanceDate();
//				shopBalanceDatePar.setShopNo(shopNo);
//				shopBalanceDatePar.setBalanceFlag((byte) 1);
//				shopBalanceDatePar.setMonth(month);
//				shopBalanceDatePar.setBalanceStartDate(startDate);
//				shopBalanceDatePar.setBalanceEndDate(endDate);
//				try {
//					shopBalanceDateService.updateBalanceDateSet(shopBalanceDatePar);
//				} catch (ServiceException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				params.put("shopNo", shopNo);
//				
//				List<BillShopBalanceCateSum> billShopBalanceCateSumList= billShopBalanceCateSumMapper.selectByParams(null, params);
//				iCount = billShopBalanceCateSumMapper.deleteBalanceNoForModel(billShopBalanceCateSumList.get(0).getBalanceNo());
//				
//				List<BillShopBalanceDaysaleSum> billShopBalanceDaysaleSumList= billShopBalanceDaysaleSumMapper.selectByParams(null, params);
//				iCount = billShopBalanceDaysaleSumMapper.deleteBalanceNoForModel(billShopBalanceDaysaleSumList.get(0).getBalanceNo());
//				
//				List<BillShopBalanceDiff> billShopBalanceDiffList= billShopBalanceDiffMapper.selectByParams(null, params);
//				iCount = billShopBalanceDiffMapper.deleteBalanceNoForModel(billShopBalanceDiffList.get(0).getBalanceNo());
//				
//				List<BillShopBalanceProSum> billShopBalanceProSumList= billShopBalanceProSumMapper.selectByParams(null, params);
//				iCount = billShopBalanceProSumMapper.deleteBalanceNoForModel(billShopBalanceProSumList.get(0).getBalanceNo());
//				
//				//更新MPS
//				List<BalanceCallBackDto> balanceCallBack=getBalanceCallBack(shopNo,month,startDate,endDate);
//				balanceRateApi.updateBalanceDate(balanceCallBack);
//				
//				//更新POS
//				List<String> shopNoList = new ArrayList();
//				shopNoList.add(shopNo);
//				OcOrderParameterParentNoDto ocOrderParameterParentNoDto =getOcOrderParameterParentNoDto(shopNo,startDate,endDate); 
//				orderMainApi.financeConfirm(shopNoList,balanceNo,ocOrderParameterParentNoDto);
//				
////				List<BillShopBalance> billShopBalanceList= billShopBalanceMapper.selectByParams(null, params);
//				iCount = billShopBalanceMapper.deleteByPrimarayKeyForModel(billShopBalanceList.get(0));
//				
////				根据结算单号删除  bill_shop_balance_cate_sum  bill_shop_balance_daysale_sum  bill_shop_balance_diff  bill_shop_balance_pro_sum
//				
//			}
//			return iCount;
	}

	@Override
	public BillShopBalanceDaysaleSum getSystemSalesAmount(BillShopBalance billShopBalance) {
		BillShopBalanceDaysaleSum billShopBalanceDaysaleSum = billShopBalanceMapper.getSystemSalesAmount(billShopBalance);
		return billShopBalanceDaysaleSum;
	}

	@Override
	public List<BillShopBalanceDaysaleSum> getPaymentMethodSum(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		List<BillShopBalanceDaysaleSum> billShopBalanceDaysaleSum = billShopBalanceMapper.getPaymentMethodSum(page, orderByField, orderBy, params, null);
		return billShopBalanceDaysaleSum;
	}

	@Override
	public BigDecimal getExpectCashAmount(Map<String, Object> params) {
		return billShopBalanceMapper.getExpectCashAmount(params);
	}

	@Override
	public BigDecimal getBalanceDeductAmount(Map<String, Object> params) {
		return billShopBalanceMapper.getBalanceDeductAmount(params);
	}

	@Override
	public BigDecimal getBalanceDiffAmount(Map<String, Object> params) {
		return billShopBalanceMapper.getBalanceDiffAmount(params);
	}

	@Override
	public BigDecimal getPaymentAmount(BillShopBalance billShopBalance) {
		return billShopBalanceMapper.getPaymentAmount(billShopBalance);
	}

	@Override
	public int updateInvoiceByBalanceNo(BillShopBalance shopBalance) {
		return billShopBalanceMapper.updateInvoiceByBalanceNo(shopBalance);
	}

	@Override
	public BillShopBalanceProSum getProSumSalesAmount(Map<String, Object> params) {
		return billShopBalanceMapper.getProSumSalesAmount(params);
	}

	@Override
	public BigDecimal getBalanceDeductDiffAmount(Map<String, Object> params) {
		return billShopBalanceMapper.getBalanceDeductDiffAmount(params);
	}

	@Override
	public BigDecimal getSumAmount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return billShopBalanceMapper.getSumAmount(params);
	}

	@Override
	public String getMaxMonth(BillShopBalance billShopBalance) {
		// TODO Auto-generated method stub
		return billShopBalanceMapper.getMaxMonth(billShopBalance);
	}
	
	/**
	 * 通过店铺和结算单中的终止结算日期,查询是否已生成下期结算单
	 * @param params 查询参数
	 * @return 是否已生成下期结算单的标志
	 * @throws ServiceException 异常
	 */
	@Override
	public int hasNextBalanceDate(Map<String, Object> params)
			throws ServiceException {
		try {
			return billShopBalanceMapper.hasNextBalanceDate(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 获取上期的结算单对象
	 * @param params 查询参数
	 * @return 上期结算单对象
	 */
	@Override
	public BillShopBalance getPerBillShopBalance(Map<String, Object> params) throws ServiceException {
		try {
			return billShopBalanceMapper.getPerBillShopBalance(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BillShopBalance getBacksectionSplitDeduct(Map<String, Object> params) {
		return billShopBalanceMapper.getBacksectionSplitDeduct(params);
	}

	@Override
	public List<BillShopBalance> findShopBalanceDeductAfter(Map<String, Object> params) {
		return billShopBalanceMapper.selectShopBalanceDeductAfter(params);
	}

	
	/***********************************  yangyong   *******************************************/
	/**
	 * 汇总门店日销售数据
	 * @param params 查询参数
	 * @return 汇总的销售数据对象
	 */
	@Override
	public GatherDaysaleSumDto gatherDaysaleSum(Map<String, Object> params) {
		return billShopBalanceMapper.gatherDaysaleSum(params);
	}

	@Override
	public BillShopBalanceDaysaleSum getConBaseDeductAmount(
			Map<String, Object> params) {
		return billShopBalanceMapper.getConBaseDeductAmount(params);
	}

	@Override
	public List<BillShopBalance> selectBlanceList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billShopBalanceMapper.selectBlanceList(page, orderByField, orderBy, params);
	}

	@Override
	public int selectBlanceCount(Map<String, Object> params) {
		return billShopBalanceMapper.selectBlanceCount(params);
	}

	@Override
	public BillShopBalanceCateSum getSalesAmount(BillShopBalance billShopBalance) {
		return billShopBalanceMapper.getSalesAmount(billShopBalance);
	}
	
	/**
	 * 根据开票申请号更新开票申请日期
	 * @param shopBalance
	 * @return
	 * @author wang.yj
	 */
	public int updateInvoiceDateByApplyNo(BillShopBalance shopBalance){
		return billShopBalanceMapper.updateInvoiceDateByApplyNo(shopBalance);
	}

	@Override
	public List<BillShopBalance> checkBackPayTimeoutList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billShopBalanceMapper.checkBackPayTimeoutList(page, orderByField, orderBy, params);
	}

	@Override
	public int checkBackPayTimeoutCount(Map<String, Object> params) {
		return billShopBalanceMapper.checkBackPayTimeoutCount(params);
	}

	@Override
	public List<BillShopBalance> checkMakeInvoiceTimeoutList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billShopBalanceMapper.checkMakeInvoiceTimeoutList(page, orderByField, orderBy, params);
	}

	@Override
	public int checkMakeInvoiceTimeoutCount(Map<String, Object> params) {
		return billShopBalanceMapper.checkMakeInvoiceTimeoutCount(params);
	}

	@Override
	public BigDecimal getNoProSalesSumAmount(Map<String, Object> params) {
		return billShopBalanceMapper.getNoProSalesSumAmount(params);
	}

	@Override
	public List<BillShopBalance> selectSalesResultList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) {
		return billShopBalanceMapper.selectSalesResultList(page, orderByField, orderBy, params, null);
	}

	@Override
	public int selectSalesResultCount(Map<String, Object> params) {
		return billShopBalanceMapper.selectSalesResultCount(params);
	}

	@Override
	public List<BillShopBalance> selectSalesBackSectionSplitList(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billShopBalanceMapper.selectSalesBackSectionSplitList(page, orderByField, orderBy, params, null);
	}

	@Override
	public int selectSalesBackSectionSplitCount(Map<String, Object> params) {
		return billShopBalanceMapper.selectSalesBackSectionSplitCount(params);
	}

	@Override
	public List<BillShopBalance> findShopBalanceSalesInfo(
			Map<String, Object> params) {
		return billShopBalanceMapper.findShopBalanceSalesInfo(params);
	}

	@Override
	public BigDecimal getSalesAmount(Map<String, Object> params) {
		return billShopBalanceMapper.getSalesAmountBase(params);
	}
}