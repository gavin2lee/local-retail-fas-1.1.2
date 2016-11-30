package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.model.Shop;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface ShopSaleDetailManager extends BaseCrudManager{
	
	public POSOcPagingDto<POSOrderAndReturnExMainDtlDto> queryShopSaleDetailListRemote(Map<String, Object> params) throws ManagerException;
	
	public List<ItemSaleDtlDto> converDateToViewData(List<POSOrderAndReturnExMainDtlDto> OrderAndReturnExMainList);

	public void queryShopSaleDetailList(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ManagerException;

	public List<Shop> getAllShopByComanyNo(String companyNo) throws ServiceException;
}
