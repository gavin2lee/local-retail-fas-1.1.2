package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.backend.cache.suggest.Search;
import cn.wonhigh.retail.fas.common.enums.LookupEnum;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.Lookup;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.service.CategoryService;
import cn.wonhigh.retail.fas.service.LookupEntryService;
import cn.wonhigh.retail.fas.service.LookupService;
import cn.wonhigh.retail.mdm.common.model.Item;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("specifyItemCostManager")
public class SpecifyItemCostManagerImpl extends BaseCrudManagerImpl implements SpecifyItemCostManager {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SpecifyItemCostManagerImpl.class);
	
	@Resource
	private CategoryService categoryService;
	
	@Resource
	private LookupService lookupService;
	
	@Resource
	private LookupEntryService lookupEntryService;

	@Resource
	private Search search;

	@Override
	protected BaseCrudService init() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BillBuyBalance> setExtendsProperties(List<BillBuyBalance> list) {
		List<BillBuyBalance> retList = new ArrayList<BillBuyBalance>();
    	Context context = new Context(this,list.get(0).getCompanyNo());
    	if (!CollectionUtils.isEmpty(list)) {
    		try{
    			context.search = search.open();
	    		for (BillBuyBalance billBuyBalance : list) {
	    			//小计查询
	    			retList.add(setExtendsProperties(billBuyBalance,context));
	    		}
    		}
    		catch (Exception e) {
    			LOGGER.error("结存数据转换出错。");
			}
		}
		return retList;
	}
	
	private BillBuyBalance setExtendsProperties(BillBuyBalance billBuyBalance,Context context) {
		List<Item> items = context.search.get("item", billBuyBalance.getItemCode().trim());
		LOGGER.debug("获取主数据的商品缓存");
		if (!CollectionUtils.isEmpty(items)) {
			Item item = items.get(0);
			Category category = context.getCategory(item.getCategoryNo());
			if (null != category && null != category.getCategoryNo()) {
				billBuyBalance.setCategoryName(category.getName());
				if (category.getCategoryNo().length() >= 2) {
					String rootCategoryNo = category.getCategoryNo().substring(0, 2);
					Category rootCategory = context.getCategory(rootCategoryNo);
					if (null != rootCategory) {
						billBuyBalance.setFirstLevelCategoryNo(rootCategory.getCategoryNo());
						billBuyBalance.setFirstLevelCategoryName(rootCategory.getName());
					}
				}
				if (category.getCategoryNo().length() >= 4) {
					String secondCategoryNo = category.getCategoryNo().substring(0, 4);
					Category secondCategory = context.getCategory(secondCategoryNo);
					if (null != secondCategory) {
						billBuyBalance.setSecondLevelCategoryNo(secondCategory.getCategoryNo());
						billBuyBalance.setSecondLevelCategoryName(secondCategory.getName());
					}
				}
			}
			
			// 设置年份季节名称
			billBuyBalance.setYearsName(context.getLookupEntryName(LookupEnum.YEAR.getCode(), item.getYears()));
			billBuyBalance.setSeasonName(context.getLookupEntryName(LookupEnum.SELL_SEASON.getCode(), item.getSellSeason()));
			billBuyBalance.setGender(context.getLookupEntryName(LookupEnum.GENDER.getCode(), item.getGender()));
			billBuyBalance.setOrderfrom(context.getLookupEntryName(LookupEnum.ORDER_STYLE.getCode(), item.getOrderfrom()));
		}
		
		return billBuyBalance;
	}

	private static class Context{
		public Search search;
		public Map<String, Category> categories = new HashMap<String, Category>();
		public static Map<String, List<LookupEntry>> lookDtlMap = new LinkedHashMap<String, List<LookupEntry>>();
		SpecifyItemCostManagerImpl parent;
		
		public Context(SpecifyItemCostManagerImpl parent,String companyNo){
			this.parent = parent;
			loadCategory();
			loadLookUpDtl();
		}
	
		public Category getCategory( String category){			
			return categories.get(category);
		}
		
		public String getLookupEntryName(String lookupCode, String entryNo){
			List<LookupEntry> entries = lookDtlMap.get(lookupCode);
			for (LookupEntry lookupEntry : entries) {
				if (lookupEntry.getLookupEntryNo().equals(entryNo)) {
					return lookupEntry.getName();
				}
			}
			return "";
		}
		
		void loadCategory(){
			try {
				List<Category> ls = parent.categoryService.findByBiz(null, null);
				for (Category category : ls) {
					categories.put(category.getCategoryNo(), category);
				}
			} catch (ServiceException e) {
				LOGGER.error("结存大类数据查询出错。");
			}
		}
		
		void loadLookUpDtl(){
			try {
				String lookupcodes[] = new String[]{"YEAR","SELL_SEASON","ORDER_STYLE","GENDER"};
				Map<String, Object> param = new HashMap<String, Object>();
				List<Lookup> lookups = null;
				for (String code : lookupcodes) {
					param.put("lookupCode", code);
					lookups = parent.lookupService.findByBiz(null, param);
					if (!CollectionUtils.isEmpty(lookups)) {
						param.clear();
						param.put("lookupId", lookups.get(0).getId());
						if(lookups.get(0).getId()==7){
							System.out.println();
						}
						List<LookupEntry> lookupEntries = parent.lookupEntryService.findByBiz(null, param);
						lookDtlMap.put(code, lookupEntries);
					}
				}
			} catch (ServiceException e) {
				LOGGER.error("查询字典表数据失败。");
			}
		}
	}

}
