package cn.wonhigh.retail.fas.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.dal.database.HeadquarterCostMaintainMapper;

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
@Service("headquarterCostMaintainService")
class HeadquarterCostMaintainServiceImpl extends BaseCrudServiceImpl implements HeadquarterCostMaintainService {
    @Resource
    private HeadquarterCostMaintainMapper headquarterCostMaintainMapper;

    @Override
    public BaseCrudMapper init() {
        return headquarterCostMaintainMapper;
    }

	@Override
	public HeadquarterCostMaintain getLastEffectiveModel(Map<String, Object> params)
			throws ServiceException {
		try {
			return headquarterCostMaintainMapper.getLastEffectiveModel(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findRegionPriceCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return headquarterCostMaintainMapper.selectItemRegionPriceCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public void batchAdd(List<HeadquarterCostMaintain> batchInsert)
			throws ServiceException {
		headquarterCostMaintainMapper.batchAdd(batchInsert);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			HeadquarterCostMaintain obj = (HeadquarterCostMaintain)modelType;
			Map<String, Object> queryParams = new HashMap<String, Object>();
    		queryParams.put("itemNo", obj.getItemNo());
    		queryParams.put("effectiveTime",  DateUtil.format1(obj.getEffectiveTime()));
    		List<HeadquarterCostMaintain> lstItem = headquarterCostMaintainMapper.selectByParams(null, queryParams);
    		if(lstItem.size() >0){
    			HeadquarterCostMaintain cost = lstItem.get(0);
    			obj.setId(cost.getId());
    			obj.setCreateTime(cost.getCreateTime());
    			obj.setCreateUser(cost.getCreateUser());
    			headquarterCostMaintainMapper.updateByPrimaryKeySelective(obj);
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
	public HeadquarterCostMaintain getHeadquarterCost(String itemNo, Date date) throws ServiceException {
		
		if(itemNo == null || date == null) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemNo", itemNo);
		params.put("effectiveTime", date);
		return headquarterCostMaintainMapper.getLastEffectiveModel(params);
	}

	@Override
	public int batchGenetareCostByRule(Map<String, Object> params)
			throws ServiceException {
		try {
			int pageNo = 1;
			int pageSize = 1000;
			SimplePage page = new SimplePage(pageNo, pageSize, 0);
			int count = 0;
			String createUser = CurrentUser.getCurrentUser().getUsername();
			Date createTime = new Date();
			while(true){
				List<HeadquarterCostMaintain> lstItem = headquarterCostMaintainMapper.selectItemByRuleNo(page,String.valueOf(params.get("addRuleNo")));
				for (HeadquarterCostMaintain headquarterCostMaintain : lstItem) {
					headquarterCostMaintain.setEffectiveTime(DateUtil.parseToDate(String.valueOf(params.get("effectiveTime")), "yyyy-MM-dd"));
					headquarterCostMaintain.setCreateUser(createUser);
					headquarterCostMaintain.setCreateTime(createTime);
					count += this.add(headquarterCostMaintain);
				}
				if (lstItem.size() < pageSize) {
					break;
				} else {
					page.setPageNo(++pageNo);
				}
			}
			
			return count;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public void batchAddNUpdate(List<HeadquarterCostMaintain> batchInsert) throws ServiceException {
		headquarterCostMaintainMapper.batchAddNUpdate(batchInsert);
	}
}