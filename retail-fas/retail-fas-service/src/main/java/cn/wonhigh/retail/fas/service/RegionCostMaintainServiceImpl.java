package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.backend.data.core.DbHelper;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.dal.database.RegionCostMaintainMapper;
import cn.wonhigh.retail.fas.dal.database.ZoneInfoMapper;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-01 09:25:14
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
@Service("regionCostMaintainService")
class RegionCostMaintainServiceImpl extends BaseCrudServiceImpl implements RegionCostMaintainService {
    @Resource
    private RegionCostMaintainMapper regionCostMaintainMapper;

    @Resource
    private ZoneInfoMapper zoneInfoMapper;
    
    @Override
    public BaseCrudMapper init() {
        return regionCostMaintainMapper;
    }

	@Override
	public BigDecimal findRegionCost(String itemNo, String zoneNo,
			Date effectiveDate) throws ServiceException {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("itemNo", itemNo);
			params.put("zoneNo", zoneNo);
			params.put("effectiveDate", effectiveDate);
			return regionCostMaintainMapper.findRegionCost(params);
		}catch(Exception e) {
    		throw new ServiceException("系统错误-->获取商品地区成本价失败",e);
		}
	}

	@Override
	public List<RegionCostMaintain> findZoneRegionCost(
			Map<String, Object> params) throws ServiceException {
		return regionCostMaintainMapper.findZoneRegionCost(params);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			RegionCostMaintain obj = (RegionCostMaintain)modelType;
			obj.setId(UUIDHexGenerator.generate());
			if (StringUtils.isEmpty(obj.getShardingFlag())) {
				if(obj.getZoneNo().equals("P") || obj.getZoneNo().equals("O")){// 价格大区特殊处理
					obj.setShardingFlag(ShardingUtil.getShardingFlagByZoneNo("F"));
				}else{
					obj.setShardingFlag(ShardingUtil.getShardingFlagByZoneNo(obj.getZoneNo()));
				}
			}
    		Map<String, Object> queryParams = new HashMap<String, Object>();
    		queryParams.put("itemNo", obj.getItemNo()); 
    		queryParams.put("zoneNo", obj.getZoneNo());
    		queryParams.put("effectiveTime", DateUtil.format1(obj.getEffectiveTime()));
    		List<RegionCostMaintain> lstItem = regionCostMaintainMapper.selectByParams(null, queryParams);
    		if(lstItem.size() >0){
    			RegionCostMaintain cost = lstItem.get(0);
    			obj.setId(cost.getId());
    			obj.setCreateTime(cost.getCreateTime());
    			obj.setCreateUser(cost.getCreateUser());
    			regionCostMaintainMapper.updateByPrimaryKeySelective(obj);
    		}else{
    			obj.setId(UUIDHexGenerator.generate());
    			super.add(obj);
    		}
    		return 1;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public void findAreaPriceExport(SimplePage page,
			Map<String, Object> params, Function<Object, Boolean> handler)
			throws ServiceException {
		String areaPriceReport=(String) params.get("areaPriceReport");
		String statement="";
		if(areaPriceReport!=null && areaPriceReport.equals("yes")){
			statement = "cn.wonhigh.retail.fas.dal.database.RegionCostMaintainMapper.selectRegionCostReport";
		}else{
			statement = "cn.wonhigh.retail.fas.dal.database.RegionCostMaintainMapper.selectByPage";
		}
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("page", page);
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ServiceException(e);
		}
	}

	@Override
	public RegionCostMaintain getRegionCost(String zoneNo, String itemNo,
			Date date) throws ServiceException {
		
		if(zoneNo == null || itemNo == null || date == null) return null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("zoneNo", zoneNo);
		params.put("itemNo", itemNo);
		params.put("date", date);
		return regionCostMaintainMapper.getRegionCost(params);
	}

	@Override
	public List<RegionCostMaintain> findRegionCostReport(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return regionCostMaintainMapper.selectRegionCostReport(page, orderByField, orderBy, params);
	}

	@Override
	public int batchGenetareCostByRule(Map<String, Object> params)
			throws ServiceException {
		try {
			List<ZoneInfo> zoneInfos = zoneInfoMapper.selectPriceZones();
			String addRuleNo = String.valueOf(params.get("addRuleNo"));
			int count = 0;
			String createUser = CurrentUser.getCurrentUser().getUsername();
			Date createTime = new Date();
				for (ZoneInfo zoneInfo : zoneInfos) {
					String zoneNo = zoneInfo.getZoneNo();
					String zoneName = zoneInfo.getName();
				 	int pageNo = 1;
					int pageSize = 1000;
					SimplePage page = new SimplePage(pageNo, pageSize, 0);
					while(true){
						 List<RegionCostMaintain> lstItem = regionCostMaintainMapper.selectItemByRuleNo(page,addRuleNo,zoneNo);
						 for (RegionCostMaintain regionCostMaintain : lstItem) {
							regionCostMaintain.setEffectiveTime(DateUtil.parseToDate(String.valueOf(params.get("effectiveTime")), "yyyy-MM-dd"));
							regionCostMaintain.setCreateUser(createUser);
							regionCostMaintain.setCreateTime(createTime);
							regionCostMaintain.setZoneNo(zoneNo);
							regionCostMaintain.setZoneName(zoneName);
							count += this.add(regionCostMaintain);
						 }	
						 if (lstItem.size() < pageSize) {
							break;
						 } else {
							page.setPageNo(++pageNo);
						 }
					 }
			}
			return count;
		} catch (Exception e) {
			 throw new ServiceException(e);
		}
	}
	
	
}