package cn.wonhigh.retail.fas.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.Supplier;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.common.vo.ShopVo;
import cn.wonhigh.retail.fas.dal.database.BrandMapper;
import cn.wonhigh.retail.fas.dal.database.CategoryMapper;
import cn.wonhigh.retail.fas.dal.database.CommonUtilMapper;
import cn.wonhigh.retail.fas.dal.database.CompanyMapper;
import cn.wonhigh.retail.fas.dal.database.ItemMapper;
import cn.wonhigh.retail.fas.dal.database.SupplierMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.CodingRuleService;

@Service("commonUtilService")
public class CommonUtilServiceImpl implements CommonUtilService {

	@Resource 
	private CommonUtilMapper  commonUtilMapper;
	
	@Resource 
	private CompanyMapper  companyMapper;
	
	@Resource 
	private SupplierMapper  supplierMapper;
	
	@Resource 
	private BrandMapper  brandMapper;
	
	@Resource 
	private CategoryMapper  categoryMapper;
	
	@Resource 
	private ItemMapper  itemMapper;
	
	@Resource
    private CodingRuleService codingRuleService;
	
	@Resource
	private ShopService shopService;
	
	@Override
	public List<String> queryHeadquarterMaintainedItems() throws ServiceException {
		 try{
		    	return this.commonUtilMapper.queryHeadquarterMaintainedItems();
		    }catch(Exception e){
		    	throw new ServiceException(e.getMessage(), e);
		    }
	}

	@Override
	public List<String> queryRegionMaintainedItems(String zoneNo) throws ServiceException {
		 try{
		    	return this.commonUtilMapper.queryRegionMaintainedItems(zoneNo);
		    }catch(Exception e){
		    	throw new ServiceException(e.getMessage(), e);
		    }
	}

	@Override
	public int countHQNeedRuleMatchItems(Map<String, Object> params) throws ServiceException {
		try{
			return this.commonUtilMapper.countHQNeedRuleMatchItems(params);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<HeadquarterCostMaintain> queryHQNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try{
	    	return this.commonUtilMapper.queryHQNeedRuleMatchItems(page,sortColumn,sortOrder,params,null);
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public int countRegionNeedRuleMatchItems(Map<String, Object> params)
			throws ServiceException {
		try{
			if(null != params.get("HQQuotationFlag")&& params.get("HQQuotationFlag").toString().equals("1")){
				return this.commonUtilMapper.countRegionNeedRuleFactoryMatchItems(params);
			}
			return this.commonUtilMapper.countRegionNeedRuleMatchItems(params);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<RegionCostMaintain> queryRegionNeedRuleMatchItems(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params)
			throws ServiceException {
		try{
			if(null != params.get("HQQuotationFlag")&& params.get("HQQuotationFlag").toString().equals("1")){
				return this.commonUtilMapper.queryRegionNeedRuleFactoryMatchItems(page,sortColumn,sortOrder,params,null);
			}
	    	return this.commonUtilMapper.queryRegionNeedRuleMatchItems(page,sortColumn,sortOrder,params,null);
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public String getCompanyNameByNo(String companyNo) throws ServiceException{
		if(StringUtils.isBlank(companyNo)){
			return "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", companyNo);
		try{
			List<Company> lstCompany = companyMapper.selectByParams(null, params);
			if(lstCompany.size() > 0){
				return lstCompany.get(0).getName() == null ? "" : lstCompany.get(0).getName();
			}
			return "";
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
		
	}

	@Override
	public String getSupplierNameByNo(String supplierNo) throws ServiceException{
		if(StringUtils.isBlank(supplierNo)){
			return "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierNo", supplierNo);
		try{
			List<Supplier> lstSupplier = supplierMapper.selectByParams(null, params);
			if(lstSupplier.size() > 0){
				return lstSupplier.get(0).getFullName() == null ? "" : lstSupplier.get(0).getFullName();
			}
			return "";
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public String getbrandNameByNo(String brandNo) throws ServiceException{
		if(StringUtils.isBlank(brandNo)){
			return "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brandNo", brandNo);
		try{
			List<Brand> lstBrand = brandMapper.selectByParams(null, params);
			if(lstBrand.size() > 0){
				return lstBrand.get(0).getName() == null ? "" : lstBrand.get(0).getName();
			}
			return "";
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public String getCategoryNameByNo(String categoryNo) throws ServiceException{
		if(StringUtils.isBlank(categoryNo)){
			return "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryNo", categoryNo);
		try{
			List<Category> lstCategory = categoryMapper.selectByParams(null, params);
			if(lstCategory.size() > 0){
				return lstCategory.get(0).getName() == null ? "" : lstCategory.get(0).getName();
			}
			return "";
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
		
	}
	
	@Override
	public String getItemNameByNo(String itemNo) throws ServiceException{
		if(StringUtils.isBlank(itemNo)){
			return "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", itemNo);
		try{
			List<Item> lstItem = itemMapper.selectByParams(null, params);
			if(lstItem.size() > 0){
				return lstItem.get(0).getName() == null ? "" : lstItem.get(0).getName();
			}
			return "";
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public String generateBillNo(int billType) throws ServiceException {
			String str = "";
			if("".equals(str)){
				return "";
			}
			String[] arr = str.split(",");
			String prefix = arr[0];
			String tableName = arr[1];
			String pkName = arr[2];
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			String pkValue = prefix + dateFormat.format(new Date()).substring(2);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pkValue", pkValue);
			params.put("pkName", pkName);
			params.put("tableName", tableName);
			try{
				String maxSeq = commonUtilMapper.selectHqMaxBillNo(params);
				int nextBillNo = Integer.valueOf(maxSeq.substring(maxSeq.length()-5))+1;
				String suffix = nextBillNo >= 10000 ?
						String.valueOf(nextBillNo) : (nextBillNo >= 1000 ? 
						"0" + nextBillNo : (nextBillNo >= 100? 
						"00" + nextBillNo : nextBillNo >= 10?
						"000" + nextBillNo : "0000"+nextBillNo));
				return pkValue +suffix;
		    }catch(Exception e){
		    	throw new ServiceException(e.getMessage(), e);
		    }
	}
	
	@Override
	public String generateBillNo(String no,String tableName,String prefix) throws ServiceException {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateSeq = dateFormat.format(new Date());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("no", no);
			params.put("dataNo", dateSeq);
			params.put("tableName", tableName);
			try{
				String maxBillNo = commonUtilMapper.selectMaxBillNo(params);
				int nextBillNo = Integer.valueOf(maxBillNo.substring(maxBillNo.length()-4))+1;
				String suffix = nextBillNo >= 1000 ? String.valueOf(nextBillNo) : (nextBillNo >= 100 ? "0" + nextBillNo : (nextBillNo >= 10? "00" + nextBillNo: "000"+nextBillNo));
				return prefix + dateSeq +suffix;
		    }catch(Exception e){
		    	throw new ServiceException(e.getMessage(), e);
		    }
	}

	@Override
	public int findShopCount(Map<String, Object> params)
			throws ServiceException {
		try{
			return commonUtilMapper.findShopCount(params);
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public List<ShopVo> findShopByPage(SimplePage page, String sortColumn,
			String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try{
			return commonUtilMapper.findShopByPage(page, sortColumn, sortOrder, params);
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public int selectPageOrganizationCount(Map<String, Object> params)
			throws ServiceException {
		try{
			return commonUtilMapper.selectPageOrganizationCount(params);
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public List<LookupVo> selectPageOrganizationList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try{
			return commonUtilMapper.selectPageOrganizationList(page, sortColumn, sortOrder, params);
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}
	
	/**
	 * 根据公司编号，查询大区编码，再把大区编码、公司编号及codingRule 产生的编号拼接成单据编号
	 * @param companyNo 公司编号
	 * @param requestId coding_rule 表的requestId字段值
	 * @return 大区编码 + 公司编号 + codingRule 生成的序列（年份由参数的4位截取后面2位：2015 取15）
	 * @throws ServiceException
	 */
	@Override
	public String getNewBillNoCompanyNo(String companyNo,String shopNo,String requestId) throws ServiceException{
		String billNoStr = "";
		try {
			String billNo = "";
			if(companyNo != null && !"".equals(companyNo)){
				Company company = new Company();
				company.setCompanyNo(companyNo);
				Company newCompany = companyMapper.selectByPrimaryKey(company);
				// 取得大区编码
				if(null != newCompany && StringUtils.isNotBlank(newCompany.getZoneNo())){
					billNo = newCompany.getZoneNo();
				}
				billNo = billNo + companyNo ;
			}
			
			if(shopNo != null && !"".equals(shopNo)){
				Map<String, Object> shopParams = new HashMap<String, Object>();
				shopParams.put("shopNo", shopNo);
				Shop shopData = shopService.selectSubsiInfo(shopParams);
				if(null != shopData && StringUtils.isNotBlank(shopData.getZoneNo())){
					billNo = shopData.getZoneNo(); 
				}			
				billNo = billNo + shopNo ;
			}
			
			//获取序列号
			billNoStr = codingRuleService.getSerialNo(requestId);
			StringBuffer sb = new StringBuffer();
			sb.append(billNoStr);
			sb.delete(2, 4);// 截去年份前两位，只保留后面两位
			billNo = billNo + sb.toString();
			return billNo;
		}catch(Exception e){
			codingRuleService.recycleSerialNo(requestId, billNoStr);
			throw new ServiceException (e.getMessage(), e);
		}
	}

	@Override
	public String getNewBillNoCompanyNo(String companyNo, String requestId)
			throws ServiceException {
		String billNoStr = "";
		try {
			String billNo = "";
			if(companyNo != null && !"".equals(companyNo)){
				Company company = new Company();
				company.setCompanyNo(companyNo);
				Company newCompany = companyMapper.selectByPrimaryKey(company);
				// 取得大区编码
				if(null != newCompany && StringUtils.isNotBlank(newCompany.getZoneNo())){
					billNo = newCompany.getZoneNo();
				}
				billNo = billNo + companyNo ;
			}
			//获取序列号
			billNoStr = codingRuleService.getSerialNo(requestId);
			StringBuffer sb = new StringBuffer();
			sb.append(billNoStr);
			sb.delete(2, 4);// 截去年份前两位，只保留后面两位
			billNo = billNo + sb.toString();
			return billNo;
		}catch(Exception e){
			codingRuleService.recycleSerialNo(requestId, billNoStr);
			throw new ServiceException (e.getMessage(), e);
		}
	}

	@Override
	public int selectItemExtendsCount(Map<String, Object> params)
			throws ServiceException {
		try{
			return commonUtilMapper.selectItemExtendsCount(params);
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public List<ItemDto> selectItemExtendsList(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try{
			return commonUtilMapper.selectItemExtendsList(page, sortColumn, sortOrder, params);
	    }catch(Exception e){
	    	throw new ServiceException(e.getMessage(), e);
	    }
	}

	@Override
	public int countHQFirstNewNeedRuleMatchItems(Map<String, Object> params)
			throws ServiceException {
		try{
			return this.commonUtilMapper.countHQFirstNewNeedRuleMatchItems(params);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<HeadquarterCostMaintain> queryHQFirstNewNeedRuleMatchItems(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try{
			return this.commonUtilMapper.queryHQFirstNewNeedRuleMatchItems(page,sortColumn,sortOrder,params,null);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int countQuotationRuleMatchItems(Map<String, Object> params)
			throws ServiceException {
		try{
			return this.commonUtilMapper.countQuotationRuleMatchItems(params);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<HeadquarterCostMaintain> queryQuotationRuleMatchItems(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException {
		try{
			return this.commonUtilMapper.queryQuotationRuleMatchItems(page,sortColumn,sortOrder,params,null);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<ZoneInfo> findPriceZones(Map<String, Object> params) throws ServiceException{
		try{
			return this.commonUtilMapper.findPriceZones(params);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<RegionCostMaintain> getRegionNeedRuleMatchItems(
			SimplePage page, Map<String, Object> queryParams)
			throws ServiceException {
		try{
			return this.commonUtilMapper.getRegionNeedRuleMatchItems(page,queryParams);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<HeadquarterCostMaintain> getHQNeedRuleMatchItems(
			SimplePage page, Map<String, Object> queryParams)
			throws ServiceException {
		try{
			return this.commonUtilMapper.getHQNeedRuleMatchItems(page,queryParams);
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
}
