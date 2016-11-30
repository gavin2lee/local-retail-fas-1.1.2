package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ShopExtensionDto;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.dal.database.ShopMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("shopService")
class ShopServiceImpl extends BaseCrudServiceImpl implements ShopService {
    @Resource
    private ShopMapper shopMapper;

    @Override
    public BaseCrudMapper init() {
        return shopMapper;
    }

	@Override
	public Shop selectSubsiInfo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return shopMapper.selectSubsiInfo(params);
	}

	@Override
	public ShopBrand selectShopBrandInfo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return shopMapper.selectShopBrandInfo(params);
	}

	@Override
	public List<Shop> selectSubsiInfoList(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		return shopMapper.selectSubsiInfoList(page, orderByField, orderBy, params);
	}

	@Override
	public List<Shop> selectShopInfoByPayTypeWithoutDataAuthority(String payType)
			throws ServiceException {
		// TODO Auto-generated method stub
		return shopMapper.selectShopInfoByPayTypeWithoutDataAuthority(payType);
	}
	
	@Override
	public List<Shop> selectShopInfoListByShopNos(Map<String,Object> params) 
			throws ServiceException {
		return shopMapper.selectShopInfoListByShopNos(params);
	}

	@Override
	public List<String> getShopBySelfCheckin(Map<String, Object> params) {
		return shopMapper.getShopBySelfCheckin(params);
	}

	@Override
	public int findShopAndOrganCount(Map<String, Object> params)  throws ServiceException{
		return shopMapper.selectShopAndOrganCount(params);
	}

	@Override
	public List<Shop> findShopAndOrganByPage(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)  throws ServiceException{
		return shopMapper.selectShopAndOrganByPage(page, orderByField, orderBy, params);
	}
	
	@Override
	public List<ShopExtensionDto> fetchShopExtention(String attributeNo){
		if( "region".equalsIgnoreCase(attributeNo))
			return shopMapper.fetchShopRegion();
		else if("organ".equalsIgnoreCase(attributeNo))
			return shopMapper.fetchShopOrgan();
		return null;
	}
	
	@Override
	public Map<String, Shop> getAllMall(String companyNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", companyNo);
		List<Shop> list = shopMapper.findMallInfo(params);
		Map<String, Shop> result = new HashMap<String, Shop>();
		for(Shop shop:list){
			result.put(shop.getShopNo(), shop);
		}
		return result;
	}

	/**
	 * 根据条件查询店铺属性的管理城市及片区信息 
	 */
	@Override
	public List<ShopExtensionDto> findShopExtentionByCondition(
			Map<String, Object> params) {
		return shopMapper.findShopExtentionByCondition(params);
	}
	
}