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
import cn.wonhigh.retail.fas.common.model.BillCustFineNt;
import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.service.BillCustFineNtService;
import cn.wonhigh.retail.fas.service.WholesalePrepayWarnService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-22 11:59:28
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
@Service("billCustFineNtManager")
class BillCustFineNtManagerImpl extends BaseCrudManagerImpl implements BillCustFineNtManager {
   
	@Resource
    private BillCustFineNtService billCustFineNtService;
	
	@Resource
    private WholesalePrepayWarnService wholesalePrepayWarnService;

    @Override
    public BaseCrudService init() {
        return billCustFineNtService;
    }

    @Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int doAudit(List<BillCustFineNt> oList) throws ManagerException {
		try {
			for(BillCustFineNt model : oList) {
				billCustFineNtService.modifyById(model);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("companyNo", model.getCompanyNo());
				params.put("customerNo", model.getCustomerNo());
				List<WholesalePrepayWarn> warnList = wholesalePrepayWarnService.findByBiz(null, params);
				if(warnList != null && warnList.size() > 0) {
					WholesalePrepayWarn warn = warnList.get(0);
					if(model.getAuditStatus().intValue() == FasAduitStatusEnum.ADUIT_STATUS.getValue().intValue()) {
						warn.setRecedMarginAmount(BigDecimalUtil.subtract(warn.getRecedMarginAmount(), model.getFineAmount()));
					} else {
						warn.setRecedMarginAmount(BigDecimalUtil.add(warn.getRecedMarginAmount(), model.getFineAmount()));
					}
					warn.setMarginFull(BigDecimalUtil.subtract(
							warn.getRecedMarginAmount(), warn.getMarginAmount()).doubleValue() >= 0 ? 
									YesNoEnum.YES.getValue() : YesNoEnum.NO.getValue());
					params.put("recedMarginAmount", warn.getRecedMarginAmount());
					params.put("marginFull", warn.getMarginFull());
					wholesalePrepayWarnService.updateMargin(params);
//					wholesalePrepayWarnService.modifyById(warn);
				}
			}
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return 1;
	}
}