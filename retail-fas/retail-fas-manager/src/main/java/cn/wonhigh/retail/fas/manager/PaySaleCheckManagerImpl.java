package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillStatus;
import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.PaySaleCheck;
import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BillStatusService;
import cn.wonhigh.retail.fas.service.PaySaleCheckService;
import cn.wonhigh.retail.fas.service.SelfShopTerminalAccountService;

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
 * @date  2015-08-17 18:00:37
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
@Service("paySaleCheckManager")
class PaySaleCheckManagerImpl extends BaseCrudManagerImpl implements PaySaleCheckManager {
    @Resource
    private PaySaleCheckService paySaleCheckService;
    @Resource
    private BillStatusService billStatusService;
    
    @Override
    public BaseCrudService init() {
        return paySaleCheckService;
    }

	@Override
	public List<PaySaleCheck> findPaySaleCheckList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		return paySaleCheckService.findPaySaleCheckList(page, orderByField, orderBy, params);
	}

	@Override
	public PaySaleCheck findPaySaleCheckCount(Map<String, Object> params)
			throws ManagerException {
		// TODO Auto-generated method stub
		return paySaleCheckService.findPaySaleCheckCount(params);
	}

	@Override
	public void updateData(List<PaySaleCheck> paySaleCheckList,String type) throws ServiceException {
		SystemUser user = CurrentUser.getCurrentUser();
		for(PaySaleCheck paySaleCheck:paySaleCheckList){
			if(paySaleCheck.getStatus()==0 && "1".equals(type)){
				paySaleCheck.setPoundage(null);
				paySaleCheck.setRate(null);
			}
			paySaleCheckService.updateData(paySaleCheck.getRowId(), paySaleCheck.getPoundage(), paySaleCheck.getCreditCardRate());//手续费修改
			if(paySaleCheck.getStatus()==1){
				paySaleCheck.setAuditor(user.getLoginName());
				paySaleCheck.setAuditTime(new Date(new Date().getTime()));
			}else{
				paySaleCheck.setAuditor(null);
				paySaleCheck.setAuditTime(null);
			}
		}
		List<BillStatus> billStatusList = this.initBillStatus(paySaleCheckList);
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

	private List<BillStatus> initBillStatus(List<PaySaleCheck> paySaleCheckList) {
		List<BillStatus> list = new ArrayList<BillStatus>();
		for(PaySaleCheck paySaleChek:paySaleCheckList){
			BillStatus entity = new BillStatus();
			entity.setBillType(1);
			entity.setPayName(paySaleChek.getPayName());
			entity.setBillNo(paySaleChek.getOrderNo());
			entity.setStatus(paySaleChek.getStatus());
			entity.setCreateUser(paySaleChek.getCreateUser());
			entity.setCreateTime(paySaleChek.getCreateTime());
			entity.setUpdateUser(paySaleChek.getAuditor());
			entity.setUpdateTime(paySaleChek.getAuditTime());
			entity.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(paySaleChek.getCompanyNo()));
			list.add(entity);
		}
		return list;
	}
}