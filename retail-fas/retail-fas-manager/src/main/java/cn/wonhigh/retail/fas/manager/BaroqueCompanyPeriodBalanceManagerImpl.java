package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.Lookup;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.common.model.Store;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.service.BaroqueCompanyPeriodBalanceService;
import cn.wonhigh.retail.fas.service.BrandService;
import cn.wonhigh.retail.fas.service.CategoryService;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.HeadquarterCostMaintainService;
import cn.wonhigh.retail.fas.service.LookupEntryService;
import cn.wonhigh.retail.fas.service.LookupService;
import cn.wonhigh.retail.fas.service.OrderUnitService;
import cn.wonhigh.retail.fas.service.OrganService;
import cn.wonhigh.retail.fas.service.StoreService;
import cn.wonhigh.retail.mdm.common.model.Item;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

@Service("baroqueCompanyPeriodBalance")
public class BaroqueCompanyPeriodBalanceManagerImpl extends BaseCrudManagerImpl implements
		BaroqueCompanyPeriodBalanceManager {

	@Resource
	private BaroqueCompanyPeriodBalanceService baroqueCompanyPeriodBalanceService;

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BLKPeriodBalanceManagerImpl.class);

	@Resource
	private CompanyService companyService;

	@Resource
	private BrandService brandService;

	@Resource
	private OrderUnitService orderUnitService;

	@Resource
	private StoreService storeService;

	@Resource
	private OrganService organService;

	@Resource
	private CategoryService categoryService;

	@Resource
	private HeadquarterCostMaintainService headquarterCostMaintainService;

	@Resource
	private LookupEntryService lookupEntryService;

	@Resource
	private LookupService lookupService;

	@Override
	public List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return this.baroqueCompanyPeriodBalanceService.getBaroqueCompanyPeriodBalanceByPage(page, orderByField,
					orderBy, params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooter(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params)  throws ManagerException {
		try {
			return this.baroqueCompanyPeriodBalanceService.getBaroqueCompanyPeriodBalanceFooter(page, orderByField,
					orderBy, params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Integer getBaroqueCompanyPeriodBalanceCount(Map<String, Object> params) throws ManagerException {
		try {
			return this.baroqueCompanyPeriodBalanceService.getBaroqueCompanyPeriodBalanceCount(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	protected BaseCrudService init() {
		return baroqueCompanyPeriodBalanceService;
	}

	@Resource
	private Search search;

	@Override
	public List<BLKPeriodBalance> setExtendsPeriodBalanceProperties(List<BLKPeriodBalance> blkPeriodBalances) {
		if (CollectionUtils.isEmpty(blkPeriodBalances)) {
			return new ArrayList<BLKPeriodBalance>();
		}

		List<String> itemNos = new ArrayList<String>();
		List<String> storeNos = new ArrayList<String>();
		for (BLKPeriodBalance blkPeriodBalance : blkPeriodBalances) {
			if (!itemNos.contains(blkPeriodBalance.getItemNo())) {
				itemNos.add(blkPeriodBalance.getItemNo());
			}
			if (!storeNos.contains(blkPeriodBalance.getStoreNo())) {
				storeNos.add(blkPeriodBalance.getStoreNo());
			}
		}

		List<BLKPeriodBalance> retList = new ArrayList<BLKPeriodBalance>();
		Context context = new Context(this, blkPeriodBalances.get(0), itemNos, storeNos);
		if (!CollectionUtils.isEmpty(blkPeriodBalances)) {
			try {
				context.search = search.open();
				for (BLKPeriodBalance pBalance : blkPeriodBalances) {
					// Integer sum = 0;
					// sum = sum + pBalance.getOpeningQty();// 期初数量
					// sum = sum - pBalance.getPreSumOweQty();// 期初累计欠客
					// sum = sum + pBalance.getPurchaseInQty();// 期间入采购入库
					// sum = sum + pBalance.getPurchaseReturnQty();// 期间入采购退回
					// sum = sum + pBalance.getOuterTransferInQty();// 期间外区调入
					// sum = sum - pBalance.getSalesSumQty();// 期间销售数量
					// sum = sum + pBalance.getDuringNetInventoryQty();// 期间盘差数量
					// sum = sum - pBalance.getClosingQty();// 期末数量
					// sum = sum + pBalance.getCurrSumOweQty();// 累计欠客数量
					// pBalance.setBalanceDiff(sum);
					// //小计查询
					// if (null != pBalance.getCategoryNo() &&
					// pBalance.getCategoryNo().indexOf("zzzzz") != -1) {
					// retList.add(setSubTotalProperties(pBalance,context));
					// }else {
					retList.add(setExtendsProperties(pBalance, context));
					// }
				}
			} catch (Exception e) {
				LOGGER.error("结存数据转换出错。");
			}
		}
		return retList;
	}

	private BLKPeriodBalance setSubTotalProperties(BLKPeriodBalance periodBalance, Context context) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", periodBalance.getItemCode());
		List<cn.wonhigh.retail.mdm.common.model.Item> items = context.search.get("item", periodBalance.getItemCode());
		if (!CollectionUtils.isEmpty(items)) {
			cn.wonhigh.retail.mdm.common.model.Item item = items.get(0);
			for (Item i : items) {
				if (periodBalance.getBrandNo() != null && periodBalance.getBrandNo().equals(i.getBrandNo())) {
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

		}

		periodBalance.setCompanyName(context.getCompany(periodBalance.getCompanyNo()).getName());
		periodBalance.setBrandName(context.getBrand(periodBalance.getBrandNo()).getName());

		periodBalance.setCompanyNo("小计：");
		periodBalance.setCompanyName("");
		periodBalance.setItemCode("");
		periodBalance.setCategoryName("");
		periodBalance.setYearsName("");
		periodBalance.setSeasonName("");
		periodBalance.setOrderfrom("");
		periodBalance.setGender("");
		periodBalance.setPurchaseSeason("");

		return periodBalance;
	}

	private BLKPeriodBalance setExtendsProperties(BLKPeriodBalance periodBalance, Context context) {
		List<cn.wonhigh.retail.mdm.common.model.Item> items = context.search.get("item", periodBalance.getItemCode());
		LOGGER.debug("获取主数据的商品缓存");
		if (!CollectionUtils.isEmpty(items)) {
			cn.wonhigh.retail.mdm.common.model.Item item = items.get(0);
			for (Item i : items) {
				if (periodBalance.getBrandNo() != null && periodBalance.getBrandNo().equals(i.getBrandNo())) {
					item = i;
					break;
				}
			}

			// 取牌价 wangyj（期末数量*商品牌价）
			if (null != item.getTagPrice()) {
				periodBalance.setTagPrice(new BigDecimal(periodBalance.getClosingQty()).multiply(item.getTagPrice()));
			} else {
				periodBalance.setTagPrice(new BigDecimal(0.00));
			}
			periodBalance.setCompanyName(context.getCompany(periodBalance.getCompanyNo()).getName());
			if (null != context.getBrand(periodBalance.getBrandNo())) {
				periodBalance.setBrandName(context.getBrand(periodBalance.getBrandNo()).getName());
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
				if (category.getCategoryNo().length() >= 4) {
					String secondCategoryNo = category.getCategoryNo().substring(0, 4);
					Category secondCategory = context.getCategory(secondCategoryNo);
					if (null != secondCategory) {
						periodBalance.setSecondLevelCategoryNo(secondCategory.getCategoryNo());
						periodBalance.setSecondLevelCategoryName(secondCategory.getName());
					}
				}
			}

			HeadquarterCostMaintain preHeadquarterCostMaintain = context
					.getPreHeadquarterCostMaintain(item.getItemNo());
			if (null != preHeadquarterCostMaintain && null != preHeadquarterCostMaintain.getHeadquarterCost()) {
				periodBalance.setPreHeadquarterCost(preHeadquarterCostMaintain.getHeadquarterCost());
			} else {
				periodBalance.setPreHeadquarterCost(BigDecimal.ZERO);
			}

			HeadquarterCostMaintain headquarterCostMaintain = context.getHeadquarterCostMaintain(item.getItemNo());
			if (null != headquarterCostMaintain && null != headquarterCostMaintain.getHeadquarterCost()) {
				periodBalance.setHeadquarterCost(headquarterCostMaintain.getHeadquarterCost());
			}

			// 设置年份季节名称
			periodBalance.setYearsName(context.getLookupEntryName(LookupEnum.YEAR.getCode(), item.getYears()));
			periodBalance.setSeasonName(context.getLookupEntryName(LookupEnum.SELL_SEASON.getCode(),
					item.getSellSeason()));
			periodBalance.setGender(context.getLookupEntryName(LookupEnum.GENDER.getCode(), item.getGender()));
			periodBalance
					.setOrderfrom(context.getLookupEntryName(LookupEnum.ORDER_STYLE.getCode(), item.getOrderfrom()));
			periodBalance.setPurchaseSeason(context.getLookupEntryName(LookupEnum.SEASON.getCode(),
					item.getPurchaseSeason()));

		}

		return periodBalance;
	}

	private static class Context {
		public Search search;
		public Map<String, Category> categories = new HashMap<String, Category>();
		public Map<String, Company> companies = new HashMap<String, Company>();
		public Map<String, OrderUnit> orderunits = new HashMap<String, OrderUnit>();
		public Map<String, Store> stores = new HashMap<String, Store>();
		public Map<String, Organ> organs = new HashMap<String, Organ>();
		public Map<String, Brand> brands = new HashMap<String, Brand>();
		public Map<String, HeadquarterCostMaintain> preHeadquarterCostMaintains = new HashMap<String, HeadquarterCostMaintain>();
		public Map<String, HeadquarterCostMaintain> headquarterCostMaintains = new HashMap<String, HeadquarterCostMaintain>();
		public static Map<String, List<LookupEntry>> lookDtlMap = new LinkedHashMap<String, List<LookupEntry>>();
		BaroqueCompanyPeriodBalanceManagerImpl parent;

		public Context(BaroqueCompanyPeriodBalanceManagerImpl parent, BLKPeriodBalance blkPeriodBalance,
				List<String> itemNos, List<String> storeNos) {
			this.parent = parent;
			loadCategory();
			loadCompany(blkPeriodBalance.getCompanyNo());
			loadOrderUnit(blkPeriodBalance.getCompanyNo());
			loadStore(storeNos);
			loadOrgan();
			loadBrand();
			loadPreHeadquarterCostMaintain(itemNos, blkPeriodBalance);
			loadHeadquarterCostMaintain(itemNos, blkPeriodBalance);
			loadLookUpDtl();
		}

		public Category getCategory(String category) {
			return categories.get(category);
		}

		public Company getCompany(String companyNo) {
			return companies.get(companyNo);
		}

		public OrderUnit getOrderUnit(String orderUnitNo) {
			return orderunits.get(orderUnitNo);
		}

		public Store getStore(String storeNo) {
			return stores.get(storeNo);
		}

		public Organ getOrgan(String organNo) {
			return organs.get(organNo);
		}

		public Brand getBrand(String brandNo) {
			return brands.get(brandNo);
		}

		public HeadquarterCostMaintain getPreHeadquarterCostMaintain(String itemNo) {
			return preHeadquarterCostMaintains.get(itemNo);
		}

		public HeadquarterCostMaintain getHeadquarterCostMaintain(String itemNo) {
			return headquarterCostMaintains.get(itemNo);
		}

		public String getLookupEntryName(String lookupCode, String entryNo) {
			List<LookupEntry> entries = lookDtlMap.get(lookupCode);
			for (LookupEntry lookupEntry : entries) {
				if (lookupEntry.getCode().equals(entryNo)) {
					return lookupEntry.getName();
				}
			}
			return "";
		}

		void loadLookUpDtl() {
			try {
				String lookupcodes[] = new String[] { "YEAR", "SELL_SEASON", "ORDER_STYLE", "GENDER", "SEASON" };
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

		void loadCategory() {
			try {
				List<Category> ls = parent.categoryService.findByBiz(null, null);
				for (Category category : ls) {
					categories.put(category.getCategoryNo(), category);
				}
			} catch (ServiceException e) {
				LOGGER.error("结存大类数据查询出错。");
			}
		}

		void loadCompany(String companyNo) {
			Company company = new Company();
			company.setCompanyNo(companyNo);
			try {
				companies.put(companyNo, parent.companyService.findById(company));
			} catch (ServiceException e) {
				LOGGER.error("结存公司数据查询出错。");
			}
		}

		void loadOrderUnit(String companyNo) {
			try {
				Map<String, Object> ouMap = new HashMap<String, Object>();
				ouMap.put("companyNo", companyNo);
				List<OrderUnit> ous = parent.orderUnitService.findByBiz(null, ouMap);
				for (OrderUnit orderUnit : ous) {
					orderunits.put(orderUnit.getOrderUnitNo(), orderUnit);
				}
			} catch (ServiceException e) {
				LOGGER.error("结存订货单位数据查询出错。");
			}
		}

		void loadStore(List<String> storeNos) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("storeNos", storeNos);
			try {
				List<Store> ls = parent.storeService.findByBiz(null, params);
				for (Store store : ls) {
					stores.put(store.getStoreNo(), store);
				}
			} catch (ServiceException e) {
				LOGGER.error("结存机构数据查询出错。");
			}
		}

		void loadOrgan() {
			try {
				List<Organ> ls = parent.organService.findByBiz(null, null);
				for (Organ organ : ls) {
					organs.put(organ.getOrganNo(), organ);
				}
			} catch (ServiceException e) {
				LOGGER.error("管理城市数据查询出错。");
			}
		}

		void loadBrand() {
			try {
				List<Brand> bs = parent.brandService.findAllBrandWithoutPermission();
				for (Brand brand : bs) {
					brands.put(brand.getBrandNo(), brand);
				}
			} catch (ServiceException e) {
				LOGGER.error("结存品牌数据查询出错。");
			}
		}

		void loadPreHeadquarterCostMaintain(List<String> itemNos, BLKPeriodBalance blkPeriodBalance) {
			try {
				Integer year = Integer.parseInt(blkPeriodBalance.getYear());
				Integer month = Integer.parseInt(blkPeriodBalance.getMonth());

				Calendar cal = Calendar.getInstance();
				cal.setTime(DateUtil.getFirstDayOfMonth(year, month));
				// 先获取上月所属年月
				cal.add(Calendar.MONTH, -1);

				List<HeadquarterCostMaintain> ls = queryHeadquarterCostMaintain(itemNos, cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH) + 1);
				for (HeadquarterCostMaintain headquarterCostMaintain : ls) {
					preHeadquarterCostMaintains.put(headquarterCostMaintain.getItemNo(), headquarterCostMaintain);
				}
			} catch (ServiceException e) {
				LOGGER.error("期初总部加权成本查询出错。");
			}
		}

		void loadHeadquarterCostMaintain(List<String> itemNos, BLKPeriodBalance blkPeriodBalance) {
			try {
				Integer year = Integer.parseInt(blkPeriodBalance.getYear());
				Integer month = Integer.parseInt(blkPeriodBalance.getMonth());

				List<HeadquarterCostMaintain> ls = queryHeadquarterCostMaintain(itemNos, year, month);
				for (HeadquarterCostMaintain headquarterCostMaintain : ls) {
					headquarterCostMaintains.put(headquarterCostMaintain.getItemNo(), headquarterCostMaintain);
				}
			} catch (ServiceException e) {
				LOGGER.error("总部加权成本查询出错。");
			}
		}

		private List<HeadquarterCostMaintain> queryHeadquarterCostMaintain(List<String> itemNos, Integer year,
				Integer month) throws ServiceException {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemNos", itemNos);
			params.put("effectiveTimeStart", DateUtil.getFirstDayOfMonthStr(year, month));
			params.put("effectiveTimeEnd", DateUtil.getLastDayOfMonthStr(year, month));
			List<HeadquarterCostMaintain> ls = parent.headquarterCostMaintainService.findByBiz(null, params);
			return ls;
		}
	}

	@Override
	public List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByItemNo(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return this.baroqueCompanyPeriodBalanceService.getBaroqueCompanyPeriodBalanceByItemNO(page, orderByField,
					orderBy, params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooterItem(
			SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return this.baroqueCompanyPeriodBalanceService.getBaroqueCompanyPeriodBalanceFooterItem(page, orderByField,
					orderBy, params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Integer getBaroqueCompanyPeriodBalanceCountItem(
			Map<String, Object> params) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return this.baroqueCompanyPeriodBalanceService.getBaroqueCompanyPeriodBalanceCountItem(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

}
