package cn.wonhigh.retail.fas.manager;

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
import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodSalesSum;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.Lookup;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.model.Store;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.dal.database.ShardingFlagInterceptor;
import cn.wonhigh.retail.fas.service.BackOrderSalesService;
import cn.wonhigh.retail.fas.service.BrandUnitService;
import cn.wonhigh.retail.fas.service.CategoryService;
import cn.wonhigh.retail.fas.service.CompanyPeriodSalesSumService;
import cn.wonhigh.retail.fas.service.CompanyService;
import cn.wonhigh.retail.fas.service.LookupEntryService;
import cn.wonhigh.retail.fas.service.LookupService;
import cn.wonhigh.retail.fas.service.OrganService;
import cn.wonhigh.retail.fas.service.PeriodBalanceService;
import cn.wonhigh.retail.fas.service.StoreService;
import cn.wonhigh.retail.mdm.api.util.CacheContext;
import cn.wonhigh.retail.mdm.common.model.Category;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author wang.xy1
 * @date 2014-08-28 09:02:52
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("periodBalanceManager")
class PeriodBalanceManagerImpl extends BaseCrudManagerImpl implements
		PeriodBalanceManager {

	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(PeriodBalanceManagerImpl.class);

	@Resource
	private PeriodBalanceService periodBalanceService;

	@Resource
	private CompanyService companyService;

	@Resource
	private BrandUnitService brandUnitService;

	@Resource
	private CategoryService categoryService;
	
	@Resource
	private StoreService storeService;
	
	@Resource
	private OrganService organService;

	@Resource
	private LookupEntryService lookupEntryService;

	@Resource
	private LookupService lookupService;

	@Resource
	private BackOrderSalesService backOrderSalesService;

	@Resource
	private CompanyPeriodSalesSumService companyPeriodSalesSumService;
	
	@Resource
	private OrderUnitManager orderUnitManager;

	@Override
	public BaseCrudService init() {
		return periodBalanceService;
	}

	@Override
	public int findCompanyPeriodBalanceCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService.findCompanyPeriodBalanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> findCompanyPeriodBalancePages(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService.findCompanyPeriodBalancePages(page,
					sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int findCompanyPeriodBalanceSubTotalCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService
					.findCompanyPeriodBalanceSubTotalCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> findCompanyPeriodBalanceSubTotalPages(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return periodBalanceService.findCompanyPeriodBalanceSubTotalPages(
					page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Resource
	private Search search;

	public List<PeriodBalance> setExtendsPeriodBalanceProperties(
			List<PeriodBalance> periodBalances) {
		if (CollectionUtils.isEmpty(periodBalances)) {
			return new ArrayList<PeriodBalance>();
		}
		List<PeriodBalance> retList = new ArrayList<PeriodBalance>();
		CacheContext context = CacheContext.current();// new Context(this,
														// periodBalances.get(0).getCompanyNo());
		if (!CollectionUtils.isEmpty(periodBalances)) {
			try {
				for (PeriodBalance pBalance : periodBalances) {
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
					sum = sum + pBalance.getOthersOutQty();//其他出库
					pBalance.setBalanceDiff(sum);
					// 小计查询
					if (null != pBalance.getCategoryNo()
							&& pBalance.getCategoryNo().indexOf("zzzzz") != -1) {
						retList.add(setSubTotalProperties(pBalance, context));
					} else {
						retList.add(setExtendsProperties(pBalance, context));
					}
				}
			} catch (Exception e) {
				LOGGER.error("结存数据转换出错。");
			}
		}
		return retList;
	}

	private PeriodBalance setExtendsProperties(PeriodBalance periodBalance,
			CacheContext context) {

		cn.wonhigh.retail.mdm.common.model.Item item = context.getItem(
				periodBalance.getItemCode().trim(), periodBalance.getBrandNo());
		LOGGER.debug("获取主数据的商品缓存");
		if (item != null) {

			periodBalance.setCompanyName(context.getCompany(
					periodBalance.getCompanyNo()).getName());
			if (null != periodBalance.getBrandNo()
					&& null != context.getBrand(periodBalance.getBrandNo())) {
				periodBalance.setBrandName(context.getBrand(
						periodBalance.getBrandNo()).getName());
			}
			if (null != periodBalance.getOrderUnitNo()
					&& null != context.getOrderUnit(periodBalance
							.getOrderUnitNo())) {
				periodBalance.setOrderUnitName(context.getOrderUnit(
						periodBalance.getOrderUnitNo()).getName());
			}

			cn.wonhigh.retail.mdm.common.model.Category category = context
					.getCategory(item.getCategoryNo());
			if (null != category && null != category.getCategoryNo()) {
				periodBalance.setCategoryName(context.getCategoryName(item
						.getCategoryNo()));
				if (category.getCategoryNo().length() >= 2) {
					String rootCategoryNo = item.getRootCategoryNo();
					Category rootCategory = context.getCategory(rootCategoryNo);
					if (null != rootCategory) {
						periodBalance.setFirstLevelCategoryNo(rootCategory
								.getCategoryNo());
						periodBalance.setFirstLevelCategoryName(rootCategory
								.getName());
					}
				}
				if (category.getCategoryNo().length() >= 4) {
					String secondCategoryNo = category.getCategoryNo()
							.substring(0, 4);
					Category secondCategory = context
							.getCategory(secondCategoryNo);
					if (null != secondCategory) {
						periodBalance.setSecondLevelCategoryNo(secondCategory
								.getCategoryNo());
						periodBalance.setSecondLevelCategoryName(secondCategory
								.getName());
					}
				}
			}

			// 设置年份季节名称
			periodBalance.setYearsName(context.getLookupName(item.getYears()));
			periodBalance.setSeasonName(context.getLookupName(item
					.getSellSeason()));
			periodBalance.setGender(context.getLookupName(item.getGender()));
			periodBalance.setOrderfrom(context.getLookupName(item
					.getOrderfrom()));

		}

		return periodBalance;
	}

	// 小计对象赋值，便于前端展示
	public PeriodBalance setSubTotalProperties(PeriodBalance periodBalance,
			CacheContext context) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", periodBalance.getItemCode());
		cn.wonhigh.retail.mdm.common.model.Item item = context.getItem(
				periodBalance.getItemCode(), periodBalance.getBrandNo());// periodBalance.getItemCode());
		if (item != null) {
			cn.wonhigh.retail.mdm.common.model.Category category = context
					.getCategory(item.getCategoryNo());
			if (null != category && null != category.getCategoryNo()) {
				periodBalance.setCategoryName(category.getName());

			}

			// 设置年份季节名称
			periodBalance
					.setYearsName(context.getLookupName(item.getYears()));
			periodBalance.setSeasonName(context.getCategoryName(item
					.getSellSeason()));
			periodBalance.setGender(context.getLookupName(item.getGender()));
			periodBalance.setOrderfrom(context.getLookupName(item
					.getOrderfrom()));

		}

		periodBalance.setCompanyName(context.getCompany(
				periodBalance.getCompanyNo()).getName());
		if (null != periodBalance.getBrandNo()
				&& null != context.getBrand(periodBalance.getBrandNo())) {
			periodBalance.setBrandName(context.getBrand(
					periodBalance.getBrandNo()).getName());
		}
		if (null != periodBalance.getOrderUnitNo()
				&& null != context.getOrderUnit(periodBalance.getOrderUnitNo())) {
			periodBalance.setOrderUnitName(context.getOrderUnit(
					periodBalance.getOrderUnitNo()).getName());
		}
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

	@Override
	public int findCompanyPeriodBalanceSubTotalCount1(Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService
					.findCompanyPeriodBalanceSubTotalCount1(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> findCompanyPeriodBalanceSubTotalPages1(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			List<PeriodBalance> list = periodBalanceService.findCompanyPeriodBalanceSubTotalPages1(
					page, sortColumn, sortOrder, params);
			for(PeriodBalance p:list){
				if("zzzzzzzzzzzzzz".equals(p.getItemNo())){
					p.setCompanyNo("小计：");
					p.setBrandUnitName(null);
					p.setFirstLevelCategoryName(null);
					p.setItemCode(null);
					p.setItemName(null);
				}
			}
			
			return list;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> selectTotalRow(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService.selectTotalRow(page, sortColumn,
					sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
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
			int pageSize = 2000;// 每次查询100条
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

			// 销售单据的汇总
			// companyPeriodSalesSumService.transferCompanySalesSum(params);
			// 重新批量插入数据
			int totalCount = companyPeriodSalesSumService
					.countTransferCompanySalesSum(params);
			if (totalCount <= 0) {
				return;
			}
			int pageSize = 2000;// 每次查询2000条
			int pageNo = 1;// 当前页数
			int totalPage = totalCount / pageSize;// 总页数
			if (totalCount % pageSize != 0 || totalPage == 0) {
				totalPage++;
			}
			while (pageNo <= totalPage) {
				SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
				List<CompanyPeriodSalesSum> salesSums = companyPeriodSalesSumService
						.getTransferCompanySalesSumByPage(page, "", "", params);
				for (CompanyPeriodSalesSum companyPeriodSalesSum : salesSums) {
					companyPeriodSalesSum.setShardingFlag(params.get(
							"shardingFlag").toString());
					companyPeriodSalesSum
							.setYear(params.get("year").toString());
					companyPeriodSalesSum.setMonth(params.get("month")
							.toString());
					companyPeriodSalesSum.setCreateUser(params
							.get("createUser").toString());
					companyPeriodSalesSum.setCreateTime(new Date());
					companyPeriodSalesSum.setUpdateUser(params
							.get("createUser").toString());
					companyPeriodSalesSum.setUpdateTime(new Date());
				}
				companyPeriodSalesSumService
						.batchInsertPeriodSalesSum(salesSums);
				pageNo++;
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}

	}

	@Override
	public void storeBalanceExport(SimplePage page, Map<String, Object> params,
			Function<Object, Boolean> handler) throws ManagerException {
		try {
			periodBalanceService.storeBalanceExport(page, params, handler);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findSdBalanceCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService.findSdBalanceCount(params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<PeriodBalance> findSdBalanceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService.findSdBalanceByPage(page, sortColumn,
					sortOrder, params);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void storeSdBalanceExport(SimplePage page,
			Map<String, Object> params, Function<Object, Boolean> handler)
			throws ManagerException {
		try {
			periodBalanceService.storeSdBalanceExport(page, params, handler);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findStoreBalanceCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService.findStoreBalanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> findStoreBalanceList(SimplePage page,
			String orderBy, String orderByField, Map<String, Object> params)
			throws ManagerException {
		try {
			return periodBalanceService.findStoreBalanceList(page, orderBy,
					orderByField, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int queryStoreBalanceCount(Map<String, Object> params) throws ManagerException {
		try {
			return periodBalanceService.queryStoreBalanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> queryStoreBalanceList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return periodBalanceService.queryStoreBalanceList(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<String> getAllItemNos(Map<String, Object> params) throws ManagerException{
		try {
			return periodBalanceService.getAllItemNos(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<PeriodBalance> setStoreBalanceReportProperties(List<PeriodBalance> periodBalances) {
		if (CollectionUtils.isEmpty(periodBalances)) {
			return new ArrayList<PeriodBalance>();
		}
		List<PeriodBalance> retList = new ArrayList<PeriodBalance>();
		List<String> storeNos = new ArrayList<String>();
		for(PeriodBalance p:periodBalances){
			if(!storeNos.contains(p.getStoreNo())){
				storeNos.add(p.getStoreNo());
			}
			if("zzzzzzzzzzzzzz".equals(p.getItemNo())){
				p.setCompanyNo("小计：");
				p.setBrandUnitNo(null);
				p.setOrderUnitNo(null);
				p.setOrganNo(null);
				p.setFirstLevelCategoryName(null);
				p.setItemCode(null);
				p.setItemName(null);
				p.setYears(null);
				p.setSellSeason(null);
				p.setOrderfrom(null);
				p.setGender(null);
			}
		}
		Context context = new Context(this,periodBalances.get(0).getCompanyNo(),storeNos);
		
		for(PeriodBalance pBalance:periodBalances){
			retList.add(this.setExtendsProperties(pBalance,context));
		}
		
		return retList;
	}
	
	private PeriodBalance setExtendsProperties(PeriodBalance pBalance, Context context) {
		BrandUnit brandUnit = context.getBrandUnit(pBalance.getBrandUnitNo());
		if(null != brandUnit && null != brandUnit.getBrandUnitNo()){
			pBalance.setBrandUnitName(brandUnit.getName());
		}
		
		cn.wonhigh.retail.fas.common.model.Category category = context.getCategory(pBalance.getCategoryNo());
		if (null != category && null != category.getCategoryNo()) {
			pBalance.setCategoryName(category.getName());
			if (category.getCategoryNo().length() >= 2) {
				String rootCategoryNo = category.getCategoryNo().substring(0, 2);
				cn.wonhigh.retail.fas.common.model.Category rootCategory = context.getCategory(rootCategoryNo);
				if (null != rootCategory) {
					pBalance.setFirstLevelCategoryNo(rootCategory.getCategoryNo());
					pBalance.setFirstLevelCategoryName(rootCategory.getName());
				}
			}
			if (category.getCategoryNo().length() >= 4) {
				String secondCategoryNo = category.getCategoryNo().substring(0, 4);
				cn.wonhigh.retail.fas.common.model.Category secondCategory = context.getCategory(secondCategoryNo);
				if (null != secondCategory) {
					pBalance.setSecondLevelCategoryNo(secondCategory.getCategoryNo());
					pBalance.setSecondLevelCategoryName(secondCategory.getName());
				}
			}
		}
		
		Company company = context.getCompany(pBalance.getCompanyNo());
		if(null != company && null != company.getCompanyNo()){
			pBalance.setCompanyName(company.getName());
		}
		
		if(!"All".equals(pBalance.getStoreNo())){
			Store store = context.getStore(pBalance.getStoreNo());
			if(null != store && null != store.getStoreNo()){
				pBalance.setStoreName(store.getFullName());
			}
		}
		
		Organ organ = context.getOrgan(pBalance.getOrganNo());
		if(null != organ && null != organ.getOrganNo()){
			pBalance.setOrganName(organ.getName());
		}
		
		// 设置年份季节名称
		pBalance.setYearsName(context.getLookupEntryName(LookupEnum.YEAR.getCode(), pBalance.getYears()));
		pBalance.setSeasonName(context.getLookupEntryName(LookupEnum.SELL_SEASON.getCode(), pBalance.getSellSeason()));
		pBalance.setGender(context.getLookupEntryName(LookupEnum.GENDER.getCode(), pBalance.getGender()));
		pBalance.setOrderfrom(context.getLookupEntryName(LookupEnum.ORDER_STYLE.getCode(), pBalance.getOrderfrom()));
		
		return pBalance;
	}

	private static class Context{
		public Map<String, BrandUnit> brandUnits = new HashMap<String, BrandUnit>();
		public Map<String, cn.wonhigh.retail.fas.common.model.Category> categories = new HashMap<String, cn.wonhigh.retail.fas.common.model.Category>();
		public Map<String, Company> companys = new HashMap<String,Company>();
		public Map<String, Store> stores = new HashMap<String,Store>();
		public Map<String, Organ> organs = new HashMap<String,Organ>();
		public static Map<String, List<LookupEntry>> lookDtlMap = new LinkedHashMap<String, List<LookupEntry>>();
		PeriodBalanceManagerImpl parent;
		
		public Context(PeriodBalanceManagerImpl parent,String companyNo,List<String> storeNos){
			this.parent = parent;
			loadBrandUnit();
			loadCategory();
			loadCompany(companyNo);
			loadStore(storeNos);
			loadOrgan();
			loadLookUpDtl();
		}
		
		public BrandUnit getBrandUnit(String brandUnitNo){
			return brandUnits.get(brandUnitNo);
		}
		
		public cn.wonhigh.retail.fas.common.model.Category getCategory(String category){			
			return categories.get(category);
		}
		
		public Company getCompany(String companyNo){
			 return companys.get(companyNo);
		}
		
		public Store getStore(String storeNo){
			return stores.get(storeNo);
		}
		
		public Organ getOrgan(String organNo){
			return organs.get(organNo);
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
		
		void loadBrandUnit(){
			try {
				List<BrandUnit> ls = parent.brandUnitService.findByBiz(null, null);
				for (BrandUnit brandUnit : ls) {
					brandUnits.put(brandUnit.getBrandUnitNo(), brandUnit);
				}
			} catch (ServiceException e) {
				LOGGER.error("品牌部数据查询出错。");
			}
		}
		
		void loadCategory(){
			try {
				List<cn.wonhigh.retail.fas.common.model.Category> ls = parent.categoryService.findByBiz(null, null);
				for (cn.wonhigh.retail.fas.common.model.Category category : ls) {
					categories.put(category.getCategoryNo(), category);
				}
			} catch (ServiceException e) {
				LOGGER.error("结存大类数据查询出错。");
			}
		}
		
		void loadCompany(String companyNo){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", companyNo);
			try {
				List<Company> ls = parent.companyService.findByBiz(null, params);
				for(Company company:ls){
					companys.put(companyNo, company);
				}
				
			} catch (ServiceException e) {
				LOGGER.error("结存公司数据查询出错。");
			}
		}
		
		void loadStore(List<String> storeNos){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("storeNos", storeNos);
			try {
				List<Store> ls = parent.storeService.findByBiz(null, params);
				for(Store store:ls){
					stores.put(store.getStoreNo(), store);
				}
			} catch (ServiceException e) {
				LOGGER.error("结存机构数据查询出错。");
			}
		}
		
		void loadOrgan(){
			try {
				List<Organ> ls = parent.organService.findByBiz(null, null);
				for (Organ organ : ls) {
					organs.put(organ.getOrganNo(), organ);
				}
			} catch (ServiceException e) {
				LOGGER.error("管理城市数据查询出错。");
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
						List<LookupEntry> lookupEntries = parent.lookupEntryService.findByBiz(null, param);
						lookDtlMap.put(code, lookupEntries);
					}
				}
			} catch (ServiceException e) {
				LOGGER.error("查询字典表数据失败。");
			}
		}
	}
	
	/**
	 * 店铺结算导出（鞋）
	 * @param page
	 * @param params
	 * @author wangyj
	 */
	public void queryStoreBalanceList(SimplePage page, Map<String, Object> params, Function<Object, Boolean> handler) throws ManagerException{
		try {
			periodBalanceService.queryStoreBalanceList(page,params,handler);//明细行
			periodBalanceService.queryStoreBalanceListAll(params,handler);//合计行
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public PeriodBalance queryStoreBalanceAll(Map<String, Object> params) throws ManagerException {
		try {
			return periodBalanceService.queryStoreBalanceAll(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int checkPeriodBalanceData(CompanySettlePeriod settlePeriod) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date accountSettleTime = settlePeriod.getAccountSettleTime();
		List<OrderUnit> orderUnits = new ArrayList<OrderUnit>();
		try {
			params.put("companyNo", settlePeriod.getCompanyNo());
			orderUnits = orderUnitManager.findByBiz(null, params);
			params.put("orderUnits", orderUnits);
			params.put("year", DateUtil.getYear(accountSettleTime));
			params.put("month", DateUtil.getMonth(accountSettleTime));
			params.put("startDate", DateUtil.getFirstDayOfMonth(accountSettleTime));
			params.put("endDate", DateUtil.getLastDayOfMonth(accountSettleTime));
			params.put("shardingFlag",ShardingUtil.getShardingFlagByCompanyNo(settlePeriod.getCompanyNo()));
			
			return periodBalanceService.checkPeriodBalanceData(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
}