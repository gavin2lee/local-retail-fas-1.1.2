package cn.wonhigh.retail.fas.manager.utils;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.wonhigh.retail.fas.common.dto.ShopExtensionDto;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yougou.logistics.base.common.exception.ManagerException;

@Component
public class CacheUntils {
	private static CacheUntils _ctx;
	Cache<String, Object> shopExtensionCache;
	
	@Resource
	ShopManager shopManager;
	
	public CacheUntils(){
		_ctx = this;
		shopExtensionCache = CacheBuilder.newBuilder().maximumSize(50000).expireAfterWrite(12, TimeUnit.HOURS).build();
	}
	
	
	public static CacheUntils current(){
		return _ctx;
	}
			
	@SuppressWarnings("unchecked")
	public ShopExtensionDto getShopExtRegion(String shopNo,String brandNo){
		Map<String,ShopExtensionDto> vals = (Map<String,ShopExtensionDto>)shopExtensionCache.getIfPresent(shopNo);
		if( vals == null ){
			loadRegion();
		}
		
		vals = (Map<String,ShopExtensionDto>)shopExtensionCache.getIfPresent(shopNo);
		
		if( vals == null )
			return null;
		
		return vals.get(brandNo);
	}
	
	@SuppressWarnings("unchecked")
	public ShopExtensionDto getShopExtOrgan(String shopNo,String brandNo){
		String key = shopNo + organ_pre;
		Map<String,ShopExtensionDto> vals = (Map<String,ShopExtensionDto>)shopExtensionCache.getIfPresent(key);
		if( vals == null ){
			loadOrgan();
		}
		
		vals = (Map<String,ShopExtensionDto>)shopExtensionCache.getIfPresent(key);
		
		if( vals == null )
			return null;
		
		return vals.get(brandNo);
	}
	
	
	private long tickets = 0 ;
	private  void loadRegion(){		
		long t = System.currentTimeMillis() - tickets;
		if( t < 1000 * 10 ){ // 10s内不在重复执行
			return;
		}
		try {			
			List<ShopExtensionDto> items = shopManager.fetchShopExtention("region");
			String shopNo =  null;
			Map<String,ShopExtensionDto> vals = null;
			for (ShopExtensionDto item : items) { 
				if( (!item.getShopNo().equals(shopNo))){
					if( vals != null && shopNo != null){
						shopExtensionCache.put(shopNo, vals);
					}
					shopNo = item.getShopNo(); 
					vals = new HashMap<>();
				}			
				vals.put(item.getBrandNo(), item);
			}	
			
			if( vals != null && shopNo != null){
				shopExtensionCache.put(shopNo, vals);
			}
			
		} catch (ManagerException e) { 
			
		}
		
	   tickets = System.currentTimeMillis();
	}

	private long tickets2 = 0 ;
	static final String organ_pre = "_o";
	private synchronized void loadOrgan(){
		
		long t = System.currentTimeMillis() - tickets2;
		if( t < 1000 * 10 ){ // 10s内不在重复执行
			return;
		}
		try {			
			List<ShopExtensionDto> items = shopManager.fetchShopExtention("organ");
			String shopNo =  null;
			Map<String,ShopExtensionDto> vals = null;
			for (ShopExtensionDto item : items) { 
				if( (!item.getShopNo().equals(shopNo))){
					if( vals != null && shopNo != null){
						shopExtensionCache.put(shopNo + organ_pre, vals);
					}
					shopNo = item.getShopNo(); 
					vals = new HashMap<>();
				}			
				vals.put(item.getBrandNo(), item);
			}	
			
			if( vals != null && shopNo != null){
				shopExtensionCache.put(shopNo+organ_pre, vals);
			}
			
		} catch (ManagerException e) { 
			
		}
		
	   tickets2 = System.currentTimeMillis();
	}

}
