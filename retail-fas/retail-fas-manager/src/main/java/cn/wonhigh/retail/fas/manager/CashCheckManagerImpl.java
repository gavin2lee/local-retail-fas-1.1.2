package cn.wonhigh.retail.fas.manager;

import java.beans.DesignMode;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.POSDepositCashDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupOrderPaywayDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.model.CashCheck;
import cn.wonhigh.retail.fas.common.model.CashTransactionDtl;
import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.SelfShopBankInfo;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.service.CashCheckService;
import cn.wonhigh.retail.fas.service.CashTransactionDtlService;
import cn.wonhigh.retail.fas.service.DepositCashService;
import cn.wonhigh.retail.fas.service.SelfShopBankInfoService;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.oc.common.api.dto.OrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.oc.common.api.service.OrderMainApi;
import cn.wonhigh.retail.oc.common.api.util.OcPagingDto;
import cn.wonhigh.retail.pos.api.client.service.sales.OrderApi;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-13 17:36:27
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
@Service("cashCheckManager")
class CashCheckManagerImpl extends BaseCrudManagerImpl implements CashCheckManager {
	public static final XLogger logger = XLoggerFactory.getXLogger(CashCheckManagerImpl.class);
    @Resource
    private CashCheckService cashCheckService;
    
    @Resource
    private DepositCashService depositCashService;
    
    @Resource
    private ShopService shopService;

	@Override
	protected BaseCrudService init() {
		return cashCheckService;
	}

	@Override
	public CashCheck findShopSaleDetailCount(Map<String, Object> params) throws ManagerException {
		try {
			return cashCheckService.findShopSaleDetailCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<CashCheck> findShopSaleDetailList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params) throws ManagerException {
		try {
			return cashCheckService.findShopSaleDetailList(page, orderBy, orderByField, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<CashCheck> setCashCheckProperties(List<CashCheck> list,Map<String, Object> params) throws ManagerException{
		for (CashCheck cashCheck : list) {
			//根据存现账号和日期从现金交易明细汇总存现金额
			BigDecimal actualIncomeAmount = this.queryActualIncomeAmount(cashCheck);
			cashCheck.setActualIncomeAmount(actualIncomeAmount);
			cashCheck.setDepositDiff(actualIncomeAmount.subtract(cashCheck.getDepositAmount()));
		}
		return list;
	}

	private BigDecimal queryActualIncomeAmount(CashCheck cashCheck) throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if(cashCheck.getDepositAccount()!=null && cashCheck.getDepositDate()!=null){
				params.put("depositAccount", Arrays.asList(cashCheck.getDepositAccount().split(",")));
				params.put("outDate", DateUtil.formatDateByFormat(cashCheck.getDepositDate(), "yyyy-MM-dd"));
				return cashCheckService.queryActualIncomeAmount(params);
			}
		} catch (ServiceException e) {
			throw new ManagerException("查询现金交易明细汇总金额出错", e);
		}
		
		return BigDecimal.valueOf(0d);
	}

	@Override
	public List<CashCheck> queryCashCheckDetail(Map<String, Object> params) throws ManagerException {
		try {
			List<DepositCash> list = depositCashService.findByBiz(null, params);
			List<CashCheck> result = new ArrayList<CashCheck>();
			for(DepositCash depositCash:list){
				CashCheck cashCheck = new CashCheck();
				cashCheck.setShopNo(depositCash.getShopNo());
				cashCheck.setShopName(depositCash.getShopName());
				//计算系统现金销售
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("shopNo", depositCash.getShopNo());
				Shop shop = shopService.selectSubsiInfo(map);
				cashCheck.setMallNo(shop.getMallNo());
				cashCheck.setMallName(shop.getMallName());
				map.put("startOutDate", DateUtil.formatDateByFormat(depositCash.getStartOutDate(), "yyyy-MM-dd"));
				map.put("endOutDate", DateUtil.formatDateByFormat(depositCash.getEndOutDate(), "yyyy-MM-dd"));
				BigDecimal incomeAmount = cashCheckService.querySystemIncomeAmount(map);
				cashCheck.setIncomeAmount(incomeAmount);
				cashCheck.setDepositAccount(depositCash.getAccount());
				cashCheck.setDepositDate(depositCash.getDepositDate());
				
				map.put("depositAccount", Arrays.asList(depositCash.getAccount()));
				map.put("outDate", DateUtil.formatDateByFormat(cashCheck.getDepositDate(), "yyyy-MM-dd"));
				//计算存现单金额
				BigDecimal actualIncomeAmount = cashCheckService.queryActualIncomeAmount(map);
				cashCheck.setActualIncomeAmount(actualIncomeAmount);
				cashCheck.setDepositDiff(actualIncomeAmount.subtract(incomeAmount));
				result.add(cashCheck);
			}
			
			return result;
		} catch (ServiceException e) {
			logger.error("获取现金明细失败!");
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
}