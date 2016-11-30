package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.backend.cache.suggest.Search;
import cn.wonhigh.retail.fas.common.enums.LookupEnum;
import cn.wonhigh.retail.fas.common.model.BackOrderSales;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodSalesSum;
import cn.wonhigh.retail.fas.common.model.Lookup;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.service.BackOrderSalesService;
import cn.wonhigh.retail.fas.service.BrandService;
import cn.wonhigh.retail.fas.service.CategoryService;
import cn.wonhigh.retail.fas.service.CompanyPeriodBalanceService;
import cn.wonhigh.retail.fas.service.CompanyPeriodSalesSumService;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.LookupEntryService;
import cn.wonhigh.retail.fas.service.LookupService;
import cn.wonhigh.retail.mdm.common.model.Item;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-15 16:12:30
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("companyPeriodBalanceManager")
class CompanyPeriodBalanceManagerImpl extends BaseCrudManagerImpl implements CompanyPeriodBalanceManager {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(CompanyPeriodBalanceManagerImpl.class);

	@Resource
    private CompanyPeriodBalanceService companyPeriodBalanceService;

	@Resource
	private BackOrderSalesService backOrderSalesService;

	@Resource
	private CompanyPeriodSalesSumService companyPeriodSalesSumService;
	
	@Resource
	private CompanyService companyService;

	@Resource
	private BrandService brandService;

	@Resource
	private CategoryService categoryService;

	@Resource
	private LookupEntryService lookupEntryService;

	@Resource
	private LookupService lookupService;
	
    @Override
    public BaseCrudService init() {
        return companyPeriodBalanceService;
    }
    
    @Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ManagerException.class)
	public void generateCompanyOwerGuest(Map<String, Object> params)
			throws ManagerException {
		try {
			// 先删除本公司当月的欠客数据
			backOrderSalesService.deleteCompanyOwerGuestCount(params);
			// 重新批量插入数据
			int totalCount = backOrderSalesService
					.selectCompanyOwerGuestCount(params);
			if (totalCount <= 0) {
				return;
			}
			int pageSize = 1000;// 每次查询100条
			int pageNo = 1;// 当前页数
			int totalPage = totalCount / pageSize;// 总页数
			if (totalCount % pageSize != 0 || totalPage == 0) {
				totalPage++;
			}
			List<BackOrderSales> backOrderSales = null;
			while (pageNo <= totalPage) {
				SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
				backOrderSales = backOrderSalesService
						.selectCompanyOwerGuestByPage(page, "", "", params);

				backOrderSalesService
						.batchInsertCompanyOwerGuest(backOrderSales);

				pageNo++;
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
    @Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void generateCompanySalesSum(Map<String, Object> params)
			throws ManagerException {
		try {
			// 先删除本公司当月的销售汇总数据
			companyPeriodSalesSumService.deleteCompanySalesSum(params);
			// 重新批量插入数据
			int totalCount = companyPeriodSalesSumService.countTransferCompanySalesSum(params);
			if (totalCount <= 0) {
				return;
			}
			int pageSize = 5000;// 每次查询100条
			int pageNo = 1;// 当前页数
			int totalPage = totalCount / pageSize;// 总页数
			if (totalCount % pageSize != 0 || totalPage == 0) {
				totalPage++;
			}
			while (pageNo <= totalPage) {
				SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
				List<CompanyPeriodSalesSum> salesSums = companyPeriodSalesSumService.getTransferCompanySalesSumByPage(page, "", "", params);
				for (CompanyPeriodSalesSum companyPeriodSalesSum : salesSums) {
					companyPeriodSalesSum.setShardingFlag(params.get("shardingFlag").toString());
					companyPeriodSalesSum.setYear(params.get("year").toString());
					companyPeriodSalesSum.setMonth(params.get("month").toString());
					companyPeriodSalesSum.setCreateUser(params.get("createUser").toString());
					companyPeriodSalesSum.setCreateTime(new Date());
					companyPeriodSalesSum.setUpdateUser(params.get("createUser").toString());
					companyPeriodSalesSum.setUpdateTime(new Date());
				}
				companyPeriodSalesSumService.batchInsertPeriodSalesSum(salesSums);
				pageNo++;
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
	@Override
	public int findCompanyPeriodBalanceSubTotalCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return companyPeriodBalanceService
					.findCompanyPeriodBalanceSubTotalCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<CompanyPeriodBalance> findCompanyPeriodBalanceSubTotalPages(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return companyPeriodBalanceService.findCompanyPeriodBalanceSubTotalPages(
					page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<CompanyPeriodBalance> findCompanyPeriodBalancePages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return companyPeriodBalanceService.findCompanyPeriodBalancePages(page,
					sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<CompanyPeriodBalance> selectTotalRow(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return companyPeriodBalanceService.selectTotalRow(page, sortColumn,
					sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Resource
	private Search search;

	public List<CompanyPeriodBalance> setExtendsPeriodBalanceProperties(List<CompanyPeriodBalance> companyPeriodBalances){
		if (CollectionUtils.isEmpty(companyPeriodBalances)) {
			return new ArrayList<CompanyPeriodBalance>();
		}
    	List<CompanyPeriodBalance> retList = new ArrayList<CompanyPeriodBalance>();
    	Context context = new Context(this,companyPeriodBalances.get(0).getCompanyNo());
    	
    	
    	if (!CollectionUtils.isEmpty(companyPeriodBalances)) {
    		try{
    			context.search = search.open();
	    		for (CompanyPeriodBalance pBalance : companyPeriodBalances) {
	    			Integer sum = 0;
	    			sum = sum + pBalance.getOpeningQty();// 期初数量
					sum = sum - pBalance.getPreSumOweQty();// 期初累计欠客
					sum = sum + pBalance.getPurchaseInQty();// 期间入采购入库
					sum = sum + pBalance.getPurchaseReturnQty();// 期间入采购退回
					sum = sum + pBalance.getOuterTransferInQty();// 期间外区调入
					sum = sum - pBalance.getSalesSumQty();// 期间销售数量
					sum = sum + pBalance.getDuringNetInventoryQty();// 期间盘差数量
					sum = sum - pBalance.getClosingQty();// 期末数量
					sum = sum + pBalance.getCurrSumOweQty();// 累计欠客数量
					sum = sum + pBalance.getOthersInQty();//其他入库
	    			pBalance.setBalanceDiff(sum);
	    			//小计查询
	    			if (null != pBalance.getCategoryNo() && pBalance.getCategoryNo().indexOf("zzzzz") != -1) {
	    				retList.add(setSubTotalProperties(pBalance,context));
	    			}else {
	    				retList.add(setExtendsProperties(pBalance,context));
	    			}
	    		}
    		}
    		catch (Exception e) {
    			LOGGER.error("结存数据转换出错。");
			}
		}
    	return retList;
    }
	
	private CompanyPeriodBalance setExtendsProperties(CompanyPeriodBalance periodBalance,Context context) {
		List<cn.wonhigh.retail.mdm.common.model.Item> items = context.search.get("item", periodBalance.getItemCode());
		LOGGER.debug("获取主数据的商品缓存");
		if (!CollectionUtils.isEmpty(items)) {
			cn.wonhigh.retail.mdm.common.model.Item item = items.get(0);
			for (Item i : items) {
				if(periodBalance.getBrandNo()!=null && periodBalance.getBrandNo().equals(i.getBrandNo())){
					item = i;
					break;
				}
			}
			// 取牌价 wangyj（期末数量*商品牌价）
			if(null != item.getTagPrice()){
				periodBalance.setTagPrice(new BigDecimal(periodBalance.getClosingQty()).multiply(item.getTagPrice()));
			}else{
				periodBalance.setTagPrice(new BigDecimal(0.00));
			}
			periodBalance.setCompanyName(context.getCompany(periodBalance.getCompanyNo()).getName());
			if (null != context.getBrand(periodBalance.getBrandNo())) {
				periodBalance.setBrandName(context.getBrand(periodBalance.getBrandNo()).getName());
			}
//			if (null != context.getOrderUnit(periodBalance.getOrderUnitNo())) {
//				periodBalance.setOrderUnitName(context.getOrderUnit(periodBalance.getOrderUnitNo()).getName());
//			}
			
			Category category = context.getCategory(item.getCategoryNo());
			if (null != category && null != category.getCategoryNo()) {
				periodBalance.setCategoryName(category.getName());
				if (category.getCategoryNo().length() >= 2) {
					String rootCategoryNo = category.getCategoryNo().substring(0, 2);
					Category rootCategory = context.getCategory(rootCategoryNo);
					if (null != rootCategory) {
						periodBalance.setFirstLevelCategoryNo(rootCategory.getCategoryNo());
						periodBalance.setFirstLevelCategoryName(rootCategory.getName());
					}
				}
				if (category.getCategoryNo().length() >= 4) {
					String secondCategoryNo = category.getCategoryNo().substring(0, 4);
					Category secondCategory = context.getCategory(secondCategoryNo);
					if (null != secondCategory) {
						periodBalance.setSecondLevelCategoryNo(secondCategory.getCategoryNo());
						periodBalance.setSecondLevelCategoryName(secondCategory.getName());
					}
				}
			}
			
			// 设置年份季节名称
			periodBalance.setYearsName(context.getLookupEntryName(LookupEnum.YEAR.getCode(), item.getYears()));
			periodBalance.setSeasonName(context.getLookupEntryName(LookupEnum.SELL_SEASON.getCode(), item.getSellSeason()));
			periodBalance.setGender(context.getLookupEntryName(LookupEnum.GENDER.getCode(), item.getGender()));
			periodBalance.setOrderfrom(context.getLookupEntryName(LookupEnum.ORDER_STYLE.getCode(), item.getOrderfrom()));
			
		}
		
		return periodBalance;
	}

	// 小计对象赋值，便于前端展示
	public CompanyPeriodBalance setSubTotalProperties(CompanyPeriodBalance periodBalance,Context context) {
		List<cn.wonhigh.retail.mdm.common.model.Item> items = context.search.get("item", periodBalance.getItemCode());
		if (!CollectionUtils.isEmpty(items)) {
			cn.wonhigh.retail.mdm.common.model.Item item = items.get(0);
			for (Item i : items) {
				if(periodBalance.getBrandNo()!=null && periodBalance.getBrandNo().equals(i.getBrandNo())){
					item = i;
					break;
				}
			}
			Category category = context.getCategory(item.getCategoryNo());
			if (null != category && null != category.getCategoryNo()) {
				periodBalance.setCategoryName(category.getName());
				if (category.getCategoryNo().length() >= 2) {
					String rootCategoryNo = category.getCategoryNo().substring(0, 2);
					Category rootCategory = context.getCategory(rootCategoryNo);
					if (null != rootCategory) {
						periodBalance.setFirstLevelCategoryNo(rootCategory.getCategoryNo());
						periodBalance.setFirstLevelCategoryName(rootCategory.getName());
					}
				}
			}
			
			// 设置年份季节名称
			periodBalance.setYearsName(context.getLookupEntryName(LookupEnum.YEAR.getCode(), item.getYears()));
			periodBalance.setSeasonName(context.getLookupEntryName(LookupEnum.SELL_SEASON.getCode(), item.getSellSeason()));
			periodBalance.setGender(context.getLookupEntryName(LookupEnum.GENDER.getCode(), item.getGender()));
			periodBalance.setOrderfrom(context.getLookupEntryName(LookupEnum.ORDER_STYLE.getCode(), item.getOrderfrom()));
			
		}
		
		periodBalance.setCompanyName(context.getCompany(periodBalance.getCompanyNo()).getName());
		periodBalance.setBrandName(context.getBrand(periodBalance.getBrandNo()).getName());
		
		periodBalance.setCompanyNo("小计：");
		periodBalance.setItemCode("");
		periodBalance.setItemName("");
		periodBalance.setCategoryName("");
		periodBalance.setYearsName("");
		periodBalance.setSeasonName("");
		periodBalance.setOrderfrom("");
		periodBalance.setGender("");

		return periodBalance;
	}
	
	private static class Context{
		public Search search;
		public Map<String, Category> categories = new HashMap<String, Category>();
		public Map<String, Company> companies = new HashMap<String, Company>();
//		public Map<String, OrderUnit> orderunits = new HashMap<String, OrderUnit>();
		public Map<String, Brand> brands = new HashMap<String, Brand>();
		public static Map<String, List<LookupEntry>> lookDtlMap = new LinkedHashMap<String, List<LookupEntry>>();
		CompanyPeriodBalanceManagerImpl parent;
		
		public Context(CompanyPeriodBalanceManagerImpl parent,String companyNo){
			this.parent = parent;
			loadCategory();
			loadCompany(companyNo);
//			loadOrderUnit(companyNo);
			loadBrand();
			loadLookUpDtl();
		}
	
		
		public Category getCategory( String category){			
			return categories.get(category);
		}
		
		public Company getCompany(String companyNo){
			 return  companies.get(companyNo);
		}
		
//		public OrderUnit getOrderUnit(String orderUnitNo){
//			return orderunits.get(orderUnitNo);
//		}
		
		public Brand getBrand(String brandNo){
			return brands.get(brandNo);
		}
		
		public String getLookupEntryName(String lookupCode, String entryNo){
			List<LookupEntry> entries = lookDtlMap.get(lookupCode);
			for (LookupEntry lookupEntry : entries) {
				if (lookupEntry.getCode().equals(entryNo)) {
					return lookupEntry.getName();
				}
			}
			return "";
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
						List<LookupEntry> lookupEntries = parent.lookupEntryService.findByBiz(null, param);
						lookDtlMap.put(code, lookupEntries);
					}
				}
			} catch (ServiceException e) {
				LOGGER.error("查询字典表数据失败。");
			}
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
		
		void loadCompany(String companyNo){
			Company company = new Company();
			company.setCompanyNo(companyNo);
			try {
				companies.put(companyNo, parent.companyService.findById(company));
			} catch (ServiceException e) {
				LOGGER.error("结存公司数据查询出错。");
			}
		}
		
//		void loadOrderUnit(String companyNo){
//			try {
//				Map<String, Object> ouMap = new HashMap<String, Object>();
//				ouMap.put("companyNo", companyNo);
//				List<OrderUnit> ous = parent.orderUnitService.findByBiz(null, ouMap);
//				for (OrderUnit orderUnit : ous) {
//					orderunits.put(orderUnit.getOrderUnitNo(), orderUnit);
//				}
//			} catch (ServiceException e) {
//				LOGGER.error("结存订货单位数据查询出错。");
//			}
//		}
//		
		void loadBrand(){
			try {
				List<Brand> bs = parent.brandService.findAllBrandWithoutPermission();
				for (Brand brand : bs) {
					brands.put(brand.getBrandNo(), brand);
				}
			} catch (ServiceException e) {
				LOGGER.error("结存品牌数据查询出错。");
			}
		}
	}

}