package cn.wonhigh.retail.fas.web.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.CommonUtilManager;
import cn.wonhigh.retail.fas.manager.RegionCostMaintainManager;
import cn.wonhigh.retail.fas.manager.ZoneInfoManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 批量匹配地区加价规则，生成地区价
 * 
 * @author wang.xy
 * @date 2014-10-9 上午10:31:42
 * @version 0.1.0
 * @copyright yougou.com
 */
public class RegionRuleMatchHandleThread implements Runnable {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(RegionRuleMatchHandleThread.class);
	
	private RegionCostMaintainManager regionCostMaintainManager;

	private CommonUtilManager commonUtilManager;

	@Resource
	private ZoneInfoManager zoneInfoManager;
	
    @Resource
    private BrandManager brandManager;
    
	private List<String> zoneNos;
	
	private Map<String, Object> params; 
	
	public RegionRuleMatchHandleThread(RegionCostMaintainManager maintainManager, CommonUtilManager commonUtilManager,
			ZoneInfoManager zoneInfoManager, BrandManager brandManager, List<String> zoneNos, Map<String, Object> params) {
		this.regionCostMaintainManager = maintainManager;
		this.commonUtilManager = commonUtilManager;
		this.zoneInfoManager = zoneInfoManager;
		this.brandManager = brandManager;
		this.zoneNos = zoneNos;
		this.params = params;
	}

	@Override
	public void run() {
		try {
			LOGGER.debug("###################### 开始批量生成地区价：  ##############################");
				
			String organTypeNo = this.params.get("organTypeNo").toString();
			
			List<ZoneInfo> zoneInfos = zoneInfoManager.findPriceZones();
			Map<String, ZoneInfo> zoneMap = new HashMap<String, ZoneInfo>();
			for (String zoneNo : zoneNos) {
				for (ZoneInfo zoneInfo : zoneInfos) {
					if(zoneNo.equals(zoneInfo.getZoneNo())){
						zoneMap.put(zoneInfo.getZoneNo(), zoneInfo);
					}
				}
			}
			
			List<Brand> brands = brandManager.findByBiz(null, null);
			Map<String, Brand> brandMap = new HashMap<String, Brand>();
			for (Brand brand : brands) {
				brandMap.put(brand.getBrandNo(), brand);
			}
			
			if (CollectionUtils.isEmpty(this.zoneNos)) {
				List<ZoneInfo> zoneLists = this.zoneInfoManager.findPriceZones();
				for (ZoneInfo zoneNoInfo : zoneLists) {
					this.params.put("zoneNo", zoneNoInfo.getZoneNo());
					if (StringUtils.isEmpty(organTypeNo) || "0".equals(organTypeNo)) {
						this.params.put("shardingFlag", "0_Z");
					}else {
						this.params.put("shardingFlag", organTypeNo + "_" + zoneNoInfo.getZoneNo());
						if ("O".equals(zoneNoInfo.getZoneNo()) || "P".equals(zoneNoInfo.getZoneNo())) {
							this.params.put("shardingFlag", organTypeNo + "_F");
						}
					}
					
					batchMatchRegionCost(this.params, zoneMap, brandMap);
				}
			}else {
				for (String zoneNo : zoneNos) {
					params.put("zoneNo", zoneNo);
					if (StringUtils.isEmpty(organTypeNo) || "0".equals(organTypeNo)) {
						params.put("shardingFlag", "0_Z");
					}else {
						params.put("shardingFlag", organTypeNo + "_" + zoneNo);
						if ("O".equals(zoneNo) || "P".equals(zoneNo)) {
							params.put("shardingFlag", organTypeNo + "_F");
						}
					}
					batchMatchRegionCost(this.params, zoneMap, brandMap);
				}
			}
			LOGGER.debug("###################### 批量生成地区价成功  ##############################");
			
		} catch (ManagerException e) {
			LOGGER.info("批量生成地区价失败");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void batchMatchRegionCost(Map<String, Object> params, 
			Map<String, ZoneInfo> zoneMap, Map<String, Brand> brandMap) throws Exception{
		int totalCount = this.commonUtilManager.countRegionNeedRuleMatchItems(params);
		if (totalCount <= 0) {
			return;
		}
		int pageSize = 2000;// 每次查询2000条
		int pageNo = 1;// 当前页数
		int i=1;
		int totalPage = totalCount / pageSize;// 总页数
		if (totalCount % pageSize != 0 || totalPage == 0) {
			totalPage++;
		}
			
		List<RegionCostMaintain> needMaintainItems = null;
		while (i <= totalPage) {
			SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
			needMaintainItems = this.commonUtilManager.queryRegionNeedRuleMatchItems(page, "", "",params);
			// 某一个地区需要维护的商品进行价格维护
			for (RegionCostMaintain regionCostMaintain : needMaintainItems) {
				regionCostMaintain.setZoneNo((String)params.get("zoneNo"));
				regionCostMaintain.setZoneName(zoneMap.get(params.get("zoneNo")).getName());
				regionCostMaintain.setShardingFlag((String)params.get("shardingFlag"));
				regionCostMaintain.setCreateUser((String)params.get("userName"));
				regionCostMaintain.setCreateTime(DateUtil.getCurrentDateTime());
				regionCostMaintain.setAddBasis(params.get("HQQuotationFlag").toString());
					
				try {
					this.regionCostMaintainManager.addRegionCost(regionCostMaintain);
				} catch (ManagerException e) {
					LOGGER.info("匹配加价规则失败, 商品编码： " + regionCostMaintain.getItemCode() + " 地区编码： " + regionCostMaintain.getZoneNo());
				}
				
			}
			i++;
		}
	}
}
