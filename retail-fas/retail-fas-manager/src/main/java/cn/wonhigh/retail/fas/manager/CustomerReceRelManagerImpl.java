package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.CustomerReceRel;
import cn.wonhigh.retail.fas.common.model.CustomerReceRelDtl;
import cn.wonhigh.retail.fas.common.model.WholesaleMarginInit;
import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.service.CustomerReceRelDtlService;
import cn.wonhigh.retail.fas.service.CustomerReceRelService;
import cn.wonhigh.retail.fas.service.WholesaleMarginInitService;
import cn.wonhigh.retail.fas.service.WholesalePrepayWarnService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.UUIDHexGenerator;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-11-04 13:40:23
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
@Service("customerReceRelManager")
class CustomerReceRelManagerImpl extends BaseCrudManagerImpl implements CustomerReceRelManager {
	
    @Resource
    private CustomerReceRelService customerReceRelService;
    
    @Resource
    private CustomerReceRelDtlService customerReceRelDtlService;
    
    @Resource
    private WholesaleMarginInitService wholesaleMarginInitService;
    
    @Resource
    private WholesalePrepayWarnService wholesalePrepayWarnService;

    @Override
    public BaseCrudService init() {
        return customerReceRelService;
    }
    
    /**
     * 覆盖父类的新增方法,在新增成功后,同时新增WholesaleMarginInit对象
     * @param modelType CustomerReceRel
     * @return 新增的数量
     * @throws ManagerException 异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public <ModelType> int add(ModelType modelType) throws ManagerException {
    	try {
			int count = this.customerReceRelService.add(modelType);
			if(modelType instanceof CustomerReceRel) {
				WholesaleMarginInit wholesaleMarginInit = this.buildWholesaleMarginInit((CustomerReceRel)modelType);
				wholesaleMarginInitService.add(wholesaleMarginInit);
			}
			return count;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
    /**
     * 覆盖父类的修改方法,在修改成功后,同时修改WholesaleMarginInit对象
     * @param modelType CustomerReceRel
     * @return 修改的数量
     * @throws ManagerException 异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public <ModelType> int modifyById(ModelType modelType) throws ManagerException {
		try {
			int count = this.customerReceRelService.modifyById(modelType);
			if(modelType instanceof CustomerReceRel) {
				CustomerReceRel model = (CustomerReceRel)modelType;
				model = customerReceRelService.findById(model);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("companyNo", model.getCompanyNo());
				params.put("customerNo", model.getCustomerNo());
				List<WholesaleMarginInit> list = wholesaleMarginInitService.findByBiz(null, params);
				//只修改没完成初始化的数据
				if(list != null && list.size() > 0) {
					if(list.get(0).getFinishFlag().intValue() == 0) {
						WholesaleMarginInit wholesaleMarginInit = list.get(0);
						wholesaleMarginInit.setMarginAmount(model.getMarginAmount());
						wholesaleMarginInitService.modifyById(wholesaleMarginInit);
					}
				} else {
					WholesaleMarginInit wholesaleMarginInit = this.buildWholesaleMarginInit(model);
					wholesaleMarginInitService.add(wholesaleMarginInit);
				}
				//同时修改客户保证金预收款预警里的数据
				List<WholesalePrepayWarn> lstWarn = wholesalePrepayWarnService.findByBiz(null, params);
				if(lstWarn != null && lstWarn.size() > 0) {
					for(WholesalePrepayWarn warn : lstWarn) {
						if(model.getMarginControlFlag() != null && model.getMarginControlFlag().intValue() == 1) {
							warn.setMarginAmount(model.getMarginAmount());
							warn.setMarginFull(BigDecimalUtil.subtract(
									warn.getRecedMarginAmount(), model.getMarginAmount()).doubleValue() >= 0 ? 
											YesNoEnum.YES.getValue() : YesNoEnum.NO.getValue());
						} else {
							warn.setMarginAmount(null);
							warn.setRecedMarginAmount(null);
							warn.setMarginFull(null);
						}
						wholesalePrepayWarnService.modifyById(warn);
					}
				}
			}
			return count;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}

	}
    
    /**
     * 通过CustomerReceRel对象组装WholesaleMarginInit对象
     * @param model CustomerReceRel
     * @return WholesaleMarginInit
     */
    private WholesaleMarginInit buildWholesaleMarginInit(CustomerReceRel model) {
    	WholesaleMarginInit wholesaleMarginInit = new WholesaleMarginInit();
    	wholesaleMarginInit.setCompanyNo(model.getCompanyNo());
    	wholesaleMarginInit.setCompanyName(model.getCompanyName());
    	wholesaleMarginInit.setCustomerNo(model.getCustomerNo());
    	wholesaleMarginInit.setCustomerName(model.getCustomerName());
    	wholesaleMarginInit.setMarginAmount(model.getMarginAmount());
    	wholesaleMarginInit.setCreateTime(new Date());
    	return wholesaleMarginInit;
    }

	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void add(CustomerReceRel model,
			Map<CommonOperatorEnum, List<CustomerReceRelDtl>> params)
			throws ManagerException {
    	try {
    		model.setId(UUIDHexGenerator.generate());
    		if(model.getMarginControlFlag() == null){
    			model.setMarginControlFlag(YesNoEnum.NO.getValue());
    		}
			super.add(model);
			if(!CollectionUtils.isEmpty(params.get(CommonOperatorEnum.INSERTED))){
				for (CustomerReceRelDtl dtl : params.get(CommonOperatorEnum.INSERTED)) {
					dtl.setRefId(model.getId());
					customerReceRelDtlService.add(dtl);
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void update(CustomerReceRel model,
			Map<CommonOperatorEnum, List<CustomerReceRelDtl>> params)
			throws ManagerException {
    	try {
    		if(model.getMarginControlFlag() == null){
    			model.setMarginControlFlag(YesNoEnum.NO.getValue());
    		}
			super.modifyById(model);
			if(!CollectionUtils.isEmpty(params.get(CommonOperatorEnum.INSERTED))){
				for (CustomerReceRelDtl dtl : params.get(CommonOperatorEnum.INSERTED)) {
					dtl.setRefId(model.getId());
					customerReceRelDtlService.add(dtl);
				}
			}
			if(!CollectionUtils.isEmpty(params.get(CommonOperatorEnum.UPDATED))){
				for (CustomerReceRelDtl dtl : params.get(CommonOperatorEnum.UPDATED)) {
					customerReceRelDtlService.modifyById(dtl);
				}
			}
			if(!CollectionUtils.isEmpty(params.get(CommonOperatorEnum.DELETED))){
				for (CustomerReceRelDtl dtl : params.get(CommonOperatorEnum.DELETED)) {
					customerReceRelDtlService.deleteById(dtl);
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		
	}
}