package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillStatus;
import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.DepositCheck;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BillStatusService;
import cn.wonhigh.retail.fas.service.DepositCashService;
import cn.wonhigh.retail.fas.service.OrderMainService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 13:51:56
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
@Service("depositCashManager")
class DepositCashManagerImpl extends BaseCrudManagerImpl implements DepositCashManager {
    @Resource
    private DepositCashService depositCashService;
    @Resource
    private BillStatusService billStatusService;
    @Resource
    private OrderMainService orderMainService;

    @Override
    public BaseCrudService init() {
        return depositCashService;
    }

	@Override
	public DepositCash findDetailCount(Map<String, Object> params) {
		return depositCashService.findDetailCount(params);
	}

	@Override
	public List<DepositCash> findDetail(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) {
		return depositCashService.findDetail(page, orderByField, orderBy, params);
	}
	
	/**
	 * 现金存入单现金状态保存
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = ManagerException.class)
	public void updateData(List<DepositCash> depositCashList) throws ServiceException {
		SystemUser user = CurrentUser.getCurrentUser();
		for(DepositCash depositCash:depositCashList){
			if(depositCash.getStatus()==1){
				depositCash.setAuditor(user.getLoginName());
				depositCash.setAuditTime(new Date(new Date().getTime()));
			}else{
				depositCash.setAuditor(null);
				depositCash.setAuditTime(null);
			}
		}
		List<BillStatus> billStatusList = this.initBillStatus(depositCashList);
		Map<CommonOperatorEnum, List<BillStatus>> param = this.getBillStatusParam(billStatusList);
		billStatusService.save(param);//修改保存到单据状态表
	}

	private Map<CommonOperatorEnum, List<BillStatus>> getBillStatusParam(
			List<BillStatus> billStatusList) {
		Map<CommonOperatorEnum, List<BillStatus>> params = new HashMap<CommonOperatorEnum, List<BillStatus>>();
		List<BillStatus> addList = new ArrayList<BillStatus>();
		List<BillStatus> updateList = new ArrayList<BillStatus>();
		for(BillStatus billStatus:billStatusList){
			BillStatus temp = billStatusService.findByBillNo(billStatus.getBillNo(),billStatus.getPayName());
			if(null!=temp){
				billStatus.setId(temp.getId());
				if(billStatus.getStatus()!=1){
					billStatus.setUpdateUser(null);
					billStatus.setUpdateTime(null);
				}
				billStatus.setShardingFlag(null);
				updateList.add(billStatus);
			}else{
				billStatus.setId(UUIDGenerator.getUUID());
				addList.add(billStatus);
			}
		}
		params.put(CommonOperatorEnum.INSERTED, addList);
		params.put(CommonOperatorEnum.UPDATED, updateList);
		return params;
	}

	private List<BillStatus> initBillStatus(List<DepositCash> depositCashList) {
		List<BillStatus> list = new ArrayList<BillStatus>();
		for(DepositCash depositCash:depositCashList){
			BillStatus entity = new BillStatus();
			entity.setBillType(0);
			entity.setPayName("现金");
			entity.setBillNo(depositCash.getBillNo());
			entity.setStatus(depositCash.getStatus());
			entity.setCreateUser(depositCash.getCreateUser());
			entity.setCreateTime(depositCash.getCreateTime());
			entity.setUpdateUser(depositCash.getUpdateUser());
			entity.setUpdateTime(depositCash.getUpdateTime());
			entity.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(depositCash.getCompanyNo()));
			list.add(entity);
		}
		return list;

	}

	@Override
	public List<DepositCash> getSaleAmountByOutDate(
			List<DepositCash> depositCashList) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(DepositCash depositCash:depositCashList){
			params.put("shopNo", depositCash.getShopNo());
			params.put("startOutDate", depositCash.getStartOutDate());
			params.put("endOutDate", depositCash.getEndOutDate());
			depositCash.setSaleAmount(orderMainService.getSaleAmount(params));
			params.put("account", depositCash.getAccount());
			params.put("createTime", depositCash.getCreateTime());
			BigDecimal existAmount = depositCashService.getExistAmount(params);
			depositCash.setExistAmount(existAmount);
			depositCash.setDepositDiff(depositCash.getSaleAmount().subtract(depositCash.getAmount()).subtract(existAmount));
		}
		return depositCashList;
	}

	@Override
	public Map<String, BigDecimal> getPaidinAmount(Map<String, Object> params) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		List<DepositCash> list = depositCashService.getPaidinAmount(params);
		for(DepositCash depositCash:list){
			map.put(depositCash.getShopNo()+DateUtil.format(depositCash.getStartOutDate(), "yyyy-MM-dd"), depositCash.getAmount());
		}
		return map;
	}

	@Override
	public List<DepositCash> findLastDepositDate(Map<String, Object> params) throws ManagerException {
		try {
			return depositCashService.findLastDepositDate(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public DepositCheck findBeyondDates(Map<String, Object> params) throws ManagerException {
		try {
			return depositCashService.findBeyondDates(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}