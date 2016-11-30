package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.cps.api.model.Goods;
import cn.wonhigh.retail.cps.api.service.QuoteApi;
import cn.wonhigh.retail.fas.common.enums.ExcessStatusEnums;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.common.model.OfficialItem;
import cn.wonhigh.retail.fas.service.LookupEntryService;
import cn.wonhigh.retail.fas.service.OfficialItemService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-04 15:25:06
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
@Service("officialItemManager")
class OfficialItemManagerImpl extends BaseCrudManagerImpl implements OfficialItemManager {
    @Resource
    private OfficialItemService officialItemService;
    
	@Resource
	private LookupEntryService lookupEntryService;
	
    @Override
    public BaseCrudService init() {
        return officialItemService;
    }
    @Resource
    private QuoteApi quoteApi;


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public Map<String, Object> updateOfficialItem(Map<String, Object> params) throws ManagerException {
			int pageNo = 1;
			int pageSize = 1000;
			int addCount = 0;
			int updateCount = 0;
			SimplePage page = new SimplePage(pageNo, pageSize, 0);
			OfficialItem item = null;
			try {
				while(true){
					List<Goods>  list = quoteApi.getFormalGoodsByParams(page, params, null);
					//do start 业务
					for (Goods goods : list) {
						item = new OfficialItem();
						String itemCode = goods.getItemCode();
						String quoteNo = goods.getQuoteNo();
						item.setId(UUIDHexGenerator.generate());
						item.setItemCode(itemCode);
						item.setQuoteNo(quoteNo);
						item.setSupplierNo(goods.getSupplierNo());
						item.setSupplierName(goods.getSupplierName());
						item.setBrandNo(goods.getBrandNo2());
						item.setBrandName(goods.getBrandName());
						item.setBrandUnitNo(goods.getBrandUnitNo());
						item.setStyleNo(goods.getStyleNo());
						item.setYear(goods.getYear());
						item.setSeason(goods.getSeason());
						item.setExcessStatus(goods.getExcessStatus());
						item.setExcessStatusName(ExcessStatusEnums.getNameByNo(goods.getExcessStatus()));
						item.setQprice(goods.getQprice());
						item.setAprice(goods.getAprice());
						item.setSysPrice(goods.getSysPrice());
						item.setUpdateTime(goods.getUpdateTime());
						setItemExtendsInfo(item);
						addCount -= officialItemService.deleteByItemCodeAndQuotoNo(itemCode,quoteNo);
						addCount += officialItemService.add(item);
					}
					if (list.size() < pageSize) {
						break;
					} else {
						page.setPageNo(++pageNo);
					}
				}
				Map<String, Object> result =  new HashMap<String, Object>();
				result.put("addCount", addCount);
				result.put("updateCount", updateCount);
				return result;
		}catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	private void setItemExtendsInfo(OfficialItem item) throws ServiceException{
		Map<String, Object> params = new HashMap<>();
		params.put("organTypeNo", "U010101");
		params.put("name", item.getYear());
		params.put("lookupId", "5");
		List<LookupEntry> lstLookup = lookupEntryService.findByBiz(null, params);
		if(lstLookup.size() >0){
			item.setYearNo(lstLookup.get(0).getCode());
		}
		params.put("name", item.getSeason());
		params.put("lookupId", "6");
		lstLookup = lookupEntryService.findByBiz(null, params);
		if(lstLookup.size() >0){
			item.setSeasonNo(lstLookup.get(0).getCode());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public Map<String, Object> updateAllOfficeItem() throws ManagerException {
		int pageNo = 1;
		int pageSize = 1000;
		int addCount = 0;
		SimplePage page = new SimplePage(pageNo, pageSize, 0);
		OfficialItem item = null;
		Map<String, Object> params = new HashMap<>();
		params.put("updateTimeStart", "1990-1-1");
		try {
			officialItemService.deleteAll();
			while(true){
				List<Goods>  list = quoteApi.getFormalGoodsByParams(page, params, null);
				//do start 业务
				for (Goods goods : list) {
					item = new OfficialItem();
					String itemCode = goods.getItemCode();
					String quoteNo = goods.getQuoteNo();
					item.setId(UUIDHexGenerator.generate());
					item.setItemCode(itemCode);
					item.setQuoteNo(quoteNo);
					item.setSupplierNo(goods.getSupplierNo());
					item.setSupplierName(goods.getSupplierName());
					item.setBrandNo(goods.getBrandNo2());
					item.setBrandName(goods.getBrandName());
					item.setBrandUnitNo(goods.getBrandUnitNo());
					item.setStyleNo(goods.getStyleNo());
					item.setYear(goods.getYear());
					item.setSeason(goods.getSeason());
					item.setExcessStatus(goods.getExcessStatus());
					item.setExcessStatusName(ExcessStatusEnums.getNameByNo(goods.getExcessStatus()));
					item.setQprice(goods.getQprice());
					item.setAprice(goods.getAprice());
					item.setSysPrice(goods.getSysPrice());
					item.setUpdateTime(goods.getUpdateTime());
					setItemExtendsInfo(item);
					addCount -=officialItemService.deleteByItemCodeAndQuotoNo(itemCode,quoteNo);
					addCount += officialItemService.add(item);
				}
				if (list.size() < pageSize) {
					break;
				} else {
					page.setPageNo(++pageNo);
				}
			}
			Map<String, Object> result =  new HashMap<String, Object>();
			result.put("addCount", addCount);
			return result;
	}catch (ServiceException e) {
		throw new ManagerException(e.getMessage(), e);
	}
	}
}