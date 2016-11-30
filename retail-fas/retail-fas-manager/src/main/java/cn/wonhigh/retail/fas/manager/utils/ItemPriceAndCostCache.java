package cn.wonhigh.retail.fas.manager.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.vo.ItemPriceCost;
import cn.wonhigh.retail.fas.manager.ItemCostManager;
import cn.wonhigh.retail.fas.manager.scheduler.ProcessPosBillTask;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemPriceAndCostCache {
	
	private Logger log = Logger.getLogger(ItemPriceAndCostCache.class);
	
	@Resource
	ItemCostManager itemCostManager;
	
	Cache<String, BigDecimal> costCache;
	Cache<String, ItemPriceCost> priceCache;
	
	
	public ItemPriceAndCostCache(){
		costCache = callableCached();
		priceCache = callableCached();
		System.err.println("ItemPriceAndCostCache:" + this.hashCode());
	}
	
	<K, V> Cache<K, V> callableCached() {
		return CacheBuilder.newBuilder().maximumSize(50000).expireAfterWrite(45, TimeUnit.MINUTES).build();		
	}
	
	/**
	 * 获取单位成本
	 * @param companyNo
	 * @param itemNo
	 * @param outDate
	 * @return
	 */
	public BigDecimal getItemCost(final String companyNo,final String itemNo,final Date outDate){
		String key = getKey(companyNo, itemNo, outDate);	
		
		try {
			BigDecimal price = costCache.get(key, new Callable<BigDecimal>() {
				@Override
				public BigDecimal call() throws Exception{
					return itemCostManager.getItemCost(companyNo, itemNo, outDate);
				}
			});
			return price;
		} catch (ExecutionException e) { 
			log.error(String.format("获取%s,%s商品成本信息错误,日期:%tc",companyNo,itemNo,outDate));
			return null;
		}
		
	}
	
	
	public ItemPriceCost getItemPrice(final String zoneNo,final String itemNo,final Date outDate){		
		String key = getKey(zoneNo, itemNo, outDate);
		try {
			ItemPriceCost cost = priceCache.get(key, new Callable<ItemPriceCost>() {
				@Override
				public ItemPriceCost call() throws Exception{
					return itemCostManager.getItemAllPrice(zoneNo, itemNo, outDate);
				}
			});
			return cost;
		} catch (ExecutionException e) { 
			log.error(String.format("获取%s,%s商品价格信息错误,日期:%tc",zoneNo,itemNo,outDate));
			return null;
		}
		
	}
	
	
	public ItemPriceCost getItemPriceAndCost(final String companyNo,final String itemNo,final Date outDate){		
		String key = getKey(companyNo, itemNo, outDate);
		try {
			ItemPriceCost cost = priceCache.get(key, new Callable<ItemPriceCost>() {
				@Override
				public ItemPriceCost call() throws Exception{
					return itemCostManager.getItemPriceAndCost(companyNo, itemNo, outDate);
				}
			});
			return cost;
		} catch (ExecutionException e) { 
			log.error(String.format("获取%s,%s商品成本/价格信息错误,日期:%tc",companyNo,itemNo,outDate));
			return null;
		}
		
	}
	
	private String getKey(String tag,String itemNo,Date date){
		String d = DateUtil.format(date);
		return String.format("%s-%s-%s", tag,itemNo,d);
	}
	
	
}
