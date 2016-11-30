package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.model.CreditCardTransactionDtl;
import cn.wonhigh.retail.fas.common.model.ItemReturnRecord;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.service.CreditCardTransactionDtlService;
import cn.wonhigh.retail.fas.service.OrderMainService;
import cn.wonhigh.retail.fas.service.ReturnExchangeMainService;
import cn.wonhigh.retail.fas.service.ShopService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("itemReturnManagerImpl")
public class ItemReturnManagerImpl extends BaseCrudManagerImpl implements ItemReturnManager {
	
	@Resource
	private ReturnExchangeMainService returnExchangeMainService;
	
	@Resource
	private CreditCardTransactionDtlService creditCardTransactionDtlService;
	
	@Resource
	private OrderMainService orderMainService;
	
	@Resource
	private ShopService shopService;
	
	@Override
	protected BaseCrudService init() {
		return null;
	}

	@Override
	public ItemReturnRecord findItemReturnCount(Map<String, Object> params) throws ManagerException {
		try {
			return returnExchangeMainService.findItemReturnCount(params);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}

	@Override
	public List<ItemReturnRecord> findItemReturnList(SimplePage page, String orderBy,
			String orderByField, Map<String, Object> params) throws ManagerException {
		try {
			return returnExchangeMainService.findItemReturnList(page, orderBy, orderByField, params);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}

	@Override
	public List<ItemReturnRecord> setItemReturnProperties(List<ItemReturnRecord> list,String companyNo) throws ManagerException {
		try {
			Map<String, Shop> shopInfo = shopService.getAllMall(companyNo);
			Map<String, OrderMain> orderMainInfo = this.getOrderMainInfo(list);
			for (ItemReturnRecord itemReturnRecord : list) {
				Shop shop = shopInfo.get(itemReturnRecord.getShopNo());
				if(null != shop){
					itemReturnRecord.setMallNo(shopInfo.get(itemReturnRecord.getShopNo()).getMallNo());
					itemReturnRecord.setMallName(shopInfo.get(itemReturnRecord.getShopNo()).getMallName());
				}
				OrderMain orderMain = orderMainInfo.get(itemReturnRecord.getOldOrderNo());
				BigDecimal oldAllAmount = orderMain == null?BigDecimal.valueOf(0d):orderMain.getAllAmount();
				itemReturnRecord.setOldAllAmount(oldAllAmount);//原单金额
				BigDecimal temp = itemReturnRecord.getOldAllAmount().add(itemReturnRecord.getAllAmount());
				if(temp.compareTo(BigDecimal.ZERO) == 0){
					itemReturnRecord.setFullOpen("全部");
				}else{
					itemReturnRecord.setFullOpen("部分");
				}
				
				BigDecimal actualIncomeAmount = itemReturnRecord.getAmount();
				Date actualReturnTime = itemReturnRecord.getOutDate();
				//针对银联刷卡根据消费日期、交易卡号、退款金额，从银联刷卡交易明细中选取“调账”的显示实际退款金额
				if("04".equals(itemReturnRecord.getPayCode())){
					actualIncomeAmount = BigDecimal.valueOf(0d);
					actualReturnTime = null;
					if(null!=itemReturnRecord.getCreditCardAccount()){
						Map<String, Object> params = this.getReturnInfo(itemReturnRecord);
						CreditCardTransactionDtl creditCardTransactionDtl = creditCardTransactionDtlService.findReturnInfo(params);
						if(null != creditCardTransactionDtl){
							actualIncomeAmount = creditCardTransactionDtl.getActualIncomeAmount();
							actualReturnTime = creditCardTransactionDtl.getRealityDealTime();
						}
					}
				}
				itemReturnRecord.setActualReturnAmount(actualIncomeAmount);
				itemReturnRecord.setActualReturnTime(actualReturnTime);
				
			}
			return list;
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}
	
	private Map<String, Object> getReturnInfo(ItemReturnRecord itemReturnRecord) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDealTime", itemReturnRecord.getOutDate());
		params.put("endDealTime", itemReturnRecord.getOutDate());
		if(null != itemReturnRecord.getCreditCardAccount()){
			params.put("cardNumberList", FasUtil.formatInQueryCondition(itemReturnRecord.getCreditCardAccount()));
		}
		params.put("amount", itemReturnRecord.getAmount());
		return params;
	}

	private Map<String, OrderMain> getOrderMainInfo(List<ItemReturnRecord> list) throws ServiceException{
		Map<String, OrderMain> obj = new HashMap<String, OrderMain>();
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> orderNoList = new ArrayList<>();
		for (ItemReturnRecord itemReturnRecord : list) {
			if(StringUtils.isNotBlank(itemReturnRecord.getOldOrderNo())){
				orderNoList.add(itemReturnRecord.getOldOrderNo());
			}
		}
		if(orderNoList.size()>0){
			params.put("orderNoList", orderNoList);
			List<OrderMain> orderList = orderMainService.findOrderMainInfo(params);
			for (OrderMain orderMain : orderList) {
				obj.put(orderMain.getOrderNo(), orderMain);
			}
		}
		
		return obj;
	}

}
