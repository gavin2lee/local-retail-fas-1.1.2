package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillStatus;
import cn.wonhigh.retail.fas.common.model.CardReturnCheck;
import cn.wonhigh.retail.fas.common.model.PaySaleCheck;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BillStatusService;
import cn.wonhigh.retail.fas.service.CardReturnCheckService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-19 20:33:19
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
@Service("cardReturnCheckManager")
class CardReturnCheckManagerImpl extends BaseCrudManagerImpl implements CardReturnCheckManager {
    @Resource
    private CardReturnCheckService cardReturnCheckService;
    @Resource
    private BillStatusService billStatusService;

    @Override
    public BaseCrudService init() {
        return cardReturnCheckService;
    }

	@Override
	public List<CardReturnCheck> findCardReturnCheckList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		return cardReturnCheckService.findCardReturnCheckList(page, orderByField, orderBy, params);
	}

	@Override
	public CardReturnCheck findCardReturnCheckCount(Map<String, Object> params)
			throws ManagerException {
		return cardReturnCheckService.findCardReturnCheckCount(params);
	}

	@Override
	public void updatePoundage(List<CardReturnCheck> cardReturnCheckList)
			throws ManagerException, ServiceException {
		for (CardReturnCheck cardReturnCheck:cardReturnCheckList) {
			if(cardReturnCheck.getStatus()!=null&&cardReturnCheck.getStatus()==0){
				cardReturnCheck.setPoundage(null);
				cardReturnCheck.setRate(null);
			}
			cardReturnCheckService.updatePoundage(cardReturnCheck);
			if(cardReturnCheck.getStatus()!=null&&cardReturnCheck.getStatus()==1){
				cardReturnCheck.setAuditor(cardReturnCheck.getAuditor());
				cardReturnCheck.setAuditTime(cardReturnCheck.getActualReturnTime());
			}else{
				cardReturnCheck.setAuditor(null);
				cardReturnCheck.setAuditTime(null);
			}
		}
		List<BillStatus> billStatusList = this.initBillStatus(cardReturnCheckList);
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

	private List<BillStatus> initBillStatus(
			List<CardReturnCheck> cardReturnCheckList) {
		List<BillStatus> list = new ArrayList<BillStatus>();
		for(CardReturnCheck cardReturnCheck:cardReturnCheckList){
			BillStatus entity = new BillStatus();
			entity.setBillType(1);
			entity.setPayName(cardReturnCheck.getPayName());
			entity.setBillNo(cardReturnCheck.getBusinessNo());
			entity.setStatus(cardReturnCheck.getStatus()==null?2:cardReturnCheck.getStatus());
			entity.setCreateUser(cardReturnCheck.getCreateUser());
			entity.setCreateTime(cardReturnCheck.getCreateTime());
			entity.setUpdateUser(cardReturnCheck.getAuditor());
			entity.setUpdateTime(cardReturnCheck.getAuditTime());
			entity.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(cardReturnCheck.getCompanyNo()));
			list.add(entity);
		}
		return list;
	}
}