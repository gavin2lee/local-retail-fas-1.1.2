package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ShopExtensionDto;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBrand;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 11:02:24
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
public interface ShopService extends BaseCrudService {
	
	List<Shop> selectSubsiInfoList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params)throws ServiceException;
	
	Shop selectSubsiInfo(Map<String, Object> params);
	
	ShopBrand selectShopBrandInfo(Map<String, Object> params);
	
	List<Shop> selectShopInfoByPayTypeWithoutDataAuthority(String payType) throws ServiceException;
	
	List<Shop> selectShopInfoListByShopNos(Map<String,Object> params) throws ServiceException;
	
	List<String> getShopBySelfCheckin(Map<String,Object> params);
	
	int findShopAndOrganCount(Map<String,Object> params) throws ServiceException;
	
	List<Shop> findShopAndOrganByPage(SimplePage page,String orderByField,String orderBy,Map<String, Object> params) throws ServiceException;
	
	/**
	 * 
	 * @param attributeNo eg:region,organ
	 * @return
	 */
	List<ShopExtensionDto> fetchShopExtention(String attributeNo);
	

	public Map<String, Shop> getAllMall(String companyNo);
	
	public List<ShopExtensionDto> findShopExtentionByCondition(Map<String,Object> params);
	
}