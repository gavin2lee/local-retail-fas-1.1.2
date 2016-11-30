package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.backend.data.core.DbHelper;
import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.model.BalanceInvoiceApplyGenerator;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCodeSum;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.POSOcGroupOrderPayway;
import cn.wonhigh.retail.fas.common.model.POSOcGroupRootCategory;
import cn.wonhigh.retail.fas.common.model.POSOcOcGroupPromation;
import cn.wonhigh.retail.fas.common.model.POSOcOcGroupWildCard;
import cn.wonhigh.retail.fas.common.model.POSOrderAndReturnExMainDtl;
import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.dal.database.OrderMainMapper;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
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
@Service("orderMainService")
class OrderMainServiceImpl extends BaseCrudServiceImpl implements OrderMainService {
    @Resource
    private OrderMainMapper orderMainMapper;

    @Override
    public BaseCrudMapper init() {
        return orderMainMapper;
    }
    
    /**
	 * 销售订单按大类汇总接口查询
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	@Override
	public List<POSOcGroupRootCategory> findOcOrderGroupRootCategory(Map<String, Object> params) throws ServiceException {
 		try{
			return orderMainMapper.findOcOrderGroupRootCategory(params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 销售订单按活动汇总接口查询
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	@Override
	public List<POSOcOcGroupPromation> findOcOrderGroupPromationDto(Map<String, Object> params) throws ServiceException {
 		try{
			return orderMainMapper.findOcOrderGroupPromation(params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}
	

 	@Override
	public List<POSOcOcGroupWildCard> findListOcOrderGroupWildCard(Map<String, Object> params) throws ServiceException {
		try{
			return orderMainMapper.selectListOcOrderGroupWildCard(params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
 	}
 	
 	 
 		
 		
	/**
	 *销售订单每日按支付方式的销售汇总接口总数
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	@Override
	public int findOrderPayWayCount( Map<String, Object> params) throws ServiceException {
 		try{
			return orderMainMapper.findOrderPayWayCount(params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/**
	 *销售订单每日按支付方式的销售汇总接口
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 */
	@Override
	public List<POSOcGroupOrderPayway> findOrderPayWayList(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params) throws ServiceException {
		try{
			return orderMainMapper.findOcGroupOrderPayway(page,sortColumn,sortOrder,params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public int findOrderBillCount(Map<String, Object> params) throws ServiceException {
		try{
			return orderMainMapper.findOrderBillCount(params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<POSOrderAndReturnExMainDtl> findOrderBillByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try{
			return orderMainMapper.findOrderBillByPage(page, sortColumn, sortOrder, params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public List<POSOcGroupOrderPayway> findOrderPayWayListForShop(
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try{
			return orderMainMapper.findOrderPayWayListForShop(sortColumn, sortOrder, params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public int findOrderBillCountForCompanyNo(Map<String, Object> params)
			throws ServiceException {
		try{
			return orderMainMapper.findOrderBillCountForCompanyNo(params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public List<POSOrderAndReturnExMainDtl> findOrderBillByPageForCompanyNo(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try{
			return orderMainMapper.findOrderBillByPageForCompanyNo(page, sortColumn, sortOrder, params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void modifyOrderForInvoiceApplyNoAndDate(Map<String, Object> params)
			throws ServiceException {
		try{
			 orderMainMapper.updateByInvoiceApplyNoAndDate(params);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void modifyOrderForInvoiceNoAndDate(Map<String, Object> params)
			throws ServiceException {
		try{
			 orderMainMapper.updateByInvoiceNoAndDate(params);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	@Override
	public List<BalanceInvoiceApplyGenerator> findApplyGeneratorDetail(SimplePage page, Map<String, Object> params)  throws ServiceException{
		try{
			 return orderMainMapper.findApplyGeneratorDetail(page,params);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据查询条件查询生成开票申请：单据开票申请，查询gms 及pos 的内购销售单据记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	@Override
	public int findApplyGeneratorDetailCount(Map<String, Object> params) throws ServiceException{
		try{
			 return orderMainMapper.findApplyGeneratorDetailCount(params);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记信息
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<OrderDtlDto> findPosInnerBuyDetailList(SimplePage page, Map<String, Object> params) throws ServiceException{
		try{
			 return orderMainMapper.findPosInnerBuyDetailList(page, params);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据查询条件，查询pos 内购销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public OrderDtlDto findPosInnerBuyDetailCount(Map<String, Object> params) throws ServiceException{
		try{
			 return orderMainMapper.findPosInnerBuyDetailCount(params);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public POSOrderAndReturnExMainDtl getSumOcOrderByParameter(
			Map<String, Object> params) throws ServiceException {
		try {
			return orderMainMapper.selectSumOcOrderByParameter(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillShopBalanceCodeSum> findListOrderGroupCodeSum(
			Map<String, Object> params) throws ServiceException {
		return orderMainMapper.findListOrderGroupCodeSum(params);
	}

	@Override
	public int findSaleOrderPaywayCount(Map<String, Object> params)
			throws ServiceException {
		return orderMainMapper.findSaleOrderPaywayCount(params);
	}

	@Override
	public List<SaleOrderPayway> findSaleOrderPaywayByPage(SimplePage page,
			String sortColumn, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return orderMainMapper.findSaleOrderPaywayByPage(page, sortColumn, orderBy, params);
	}

	@Override
	public SaleOrderPayway getSumSaleOrderPayway(Map<String, Object> params)
			throws ServiceException {
		return orderMainMapper.getSumSaleOrderPayway(params);
	}

	@Override
	public int findSaleMonthReportCount(Map<String, Object> params)
			throws ServiceException {
		return orderMainMapper.findSaleMonthReportCount(params);
	}

	@Override
	public List<SaleOrderPayway> findSaleMonthReportByPage(SimplePage page,
			String sortColumn, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return orderMainMapper.findSaleMonthReportByPage(page, sortColumn, orderBy, params);
	}

	@Override
	public SaleOrderPayway getSumSaleMonthReport(Map<String, Object> params)
			throws ServiceException {
		return orderMainMapper.getSumSaleMonthReport(params);
	}

	@Override
	public List<SaleOrderPayway> findSaleDayReportByPage(
			String sortColumn, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return orderMainMapper.findSaleDayReportByPage(sortColumn, orderBy, params);
	}

	@Override
	public List<SaleOrderPayway> findSaleDayReportByDetail(
			SimplePage page, String sortColumn, String orderBy,Map<String, Object> params) throws ServiceException {
		return orderMainMapper.findSaleDayReportByDetail(page, sortColumn, orderBy, params);
	}

	@Override
	public SaleOrderPayway findShopDailyReportShowCount(
			Map<String, Object> params) {
		return orderMainMapper.findShopDailyReportShowCount(params);
	}

	@Override
	public List<SaleOrderPayway> findSaleDayReportForBrandByPage(
			String sortColumn, String orderBy, Map<String, Object> params) throws ServiceException {
		return orderMainMapper.findSaleDayReportForBrandByPage(sortColumn, orderBy, params);
	}

	@Override
	public SaleOrderPayway findSaleDayReportForBrandCount(
			Map<String, Object> params) {
		return orderMainMapper.findSaleDayReportForBrandCount(params);
	}
	
	
	/**
	 * 查询店铺销售明细
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	@Override
	public List<ItemSaleDtlDto> findShopSaleDetailList(SimplePage page,Map<String, Object> params)  throws ServiceException{
		return orderMainMapper.findShopSaleDetailList(page, params);
	}
	
	/**
	 * 查询店铺销售明细
	 * @param page
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	@Override
	public void findShopSaleDetailList(Map<String, Object> params,Function<Object, Boolean> handler)  throws ServiceException{
		
		String statement = "cn.wonhigh.retail.fas.dal.database.OrderMainMapper.findShopSaleDetailList";
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("page", null);
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ServiceException(e);
		}	
	}
	
	/**
	 * 店铺销售明细合计及记录数
	 * @param params
	 * @return
	 * @author wang.yj
	 */
	@Override
	public ItemSaleDtlDto findShopSaleDetailCount(Map<String, Object> params)  throws ServiceException{
		return orderMainMapper.findShopSaleDetailCount(params);
	}

	@Override
	public BigDecimal getSaleAmount(Map<String, Object> params) {
		return orderMainMapper.getSaleAmount(params);
	}

	@Override
	public List<POSOcOcGroupPromation> findOcOrderGroupPromationDtoH(
			Map<String, Object> params) throws ServiceException {
		try{
			return orderMainMapper.findOcOrderGroupPromationH(params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录
	 * @param page
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public List<ItemSaleDtlDto> findOrderDetailByOrderNo(SimplePage page,Map<String, Object> params) throws ServiceException {
		try{
			return orderMainMapper.findOrderDetailByOrderNo(page,params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据店铺编号,日期,及订单编号,查询开票申请处的销售明细记录数
	 * @param params
	 * @return
	 * @author wangyj
	 */
	public int findOrderDetailCountByOrderNo(Map<String, Object> params) throws ServiceException {
		try{
			return orderMainMapper.findOrderDetailCountByOrderNo(params);
		} catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public List<OrderMain> findOrderMainInfo(Map<String, Object> params) throws ServiceException {
		try {
			return orderMainMapper.findOrderMainInfo(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

	@Override
	public List<Map<String, Object>> findList(Map<String, Object> params) throws ServiceException {
		try {
			return orderMainMapper.findList(params);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> findDayReportForBrandList(Map<String, Object> param) throws ServiceException {
		try {
			return orderMainMapper.findDayReportForBrandList(param);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}