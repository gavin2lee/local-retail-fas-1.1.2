package cn.wonhigh.retail.fas.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.BillCustMarginNt;
import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.service.BillCustMarginNtService;
import cn.wonhigh.retail.fas.service.WholesalePrepayWarnService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-22 09:46:46
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
@Service("billCustMarginNtManager")
class BillCustMarginNtManagerImpl extends BaseCrudManagerImpl implements BillCustMarginNtManager {
	
    @Resource
    private BillCustMarginNtService billCustMarginNtService;
    
    @Resource
    private WholesalePrepayWarnService wholesalePrepayWarnService;

    @Override
    public BaseCrudService init() {
        return billCustMarginNtService;
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int doAudit(List<BillCustMarginNt> oList) throws ManagerException {
		try {
			for(BillCustMarginNt model : oList) {
				billCustMarginNtService.modifyById(model);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("companyNo", model.getCompanyNo());
				params.put("customerNo", model.getCustomerNo());
				//YANGYONGTODO 通过billCustMarginNtService查询数据库，判断单据的审核状态是否没有发生变化
				List<WholesalePrepayWarn> warnList = wholesalePrepayWarnService.findByBiz(null, params);
				if(warnList != null && warnList.size() > 0) {
					WholesalePrepayWarn warn = warnList.get(0);
					if(model.getAuditStatus().intValue() == FasAduitStatusEnum.ADUIT_STATUS.getValue().intValue()) {
						warn.setRecedMarginAmount(BigDecimalUtil.add(warn.getRecedMarginAmount(), model.getPaidMarginAmount()));
					} else {
						warn.setRecedMarginAmount(BigDecimalUtil.subtract(warn.getRecedMarginAmount(), model.getPaidMarginAmount()));
					}
					warn.setMarginFull(BigDecimalUtil.subtract(
							warn.getRecedMarginAmount(), warn.getMarginAmount()).doubleValue() >= 0 ? 
									YesNoEnum.YES.getValue() : YesNoEnum.NO.getValue());
					wholesalePrepayWarnService.modifyById(warn);
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return 1;
	}
}