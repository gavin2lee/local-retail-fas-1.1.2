package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.WholesaleReceTermDtlDto;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.CustomerReceRel;
import cn.wonhigh.retail.fas.common.model.WholesaleMarginInit;
import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.service.CustomerReceRelService;
import cn.wonhigh.retail.fas.service.WholesaleMarginInitService;
import cn.wonhigh.retail.fas.service.WholesalePrepayWarnService;
import cn.wonhigh.retail.fas.service.WholesaleReceTermDtlService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-19 15:00:52
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
@Service("wholesaleMarginInitManager")
class WholesaleMarginInitManagerImpl extends BaseCrudManagerImpl implements WholesaleMarginInitManager {
	
    @Resource
    private WholesaleMarginInitService wholesaleMarginInitService;
    
    @Resource
    private WholesalePrepayWarnService wholesalePrepayWarnService;
    
    @Resource
    private CustomerReceRelService customerReceRelService;
    
    @Resource
    private WholesaleReceTermDtlService wholesaleReceTermDtlService;

    @Override
    public BaseCrudService init() {
        return wholesaleMarginInitService;
    }
    
    /**
	 * 完结初始化
	 * @param list 待完结的数据集合
	 * @return Boolean
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public Boolean finishBill(List<WholesaleMarginInit> list) throws ManagerException {
		try {
			int count = 0;
			for(WholesaleMarginInit model : list) {
				//先修改初始化数据的标志
				model.setFinishFlag(YesNoEnum.YES.getValue());
				wholesaleMarginInitService.modifyById(model);
				
				//往客户保证金预警表里插入初始化数据
				WholesalePrepayWarn warn = this.bulidWarnModel(model);
				warn = this.convertPreOrderFull(warn);
				count += wholesalePrepayWarnService.add(warn);
			}
			return count > 0;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
	/**
	 * 组装WholesalePrepayWarn对象
	 * @param model WholesaleMarginInit
	 * @return WholesalePrepayWarn
	 * @throws ServiceException 异常
	 */
	private WholesalePrepayWarn bulidWarnModel(WholesaleMarginInit model) throws ServiceException {
		WholesalePrepayWarn warn = new WholesalePrepayWarn();
		warn.setCompanyName(model.getCompanyName());
		warn.setCompanyNo(model.getCompanyNo());
		warn.setCustomerName(model.getCustomerName());
		warn.setCustomerNo(model.getCustomerNo());
		warn.setMarginAmount(model.getMarginAmount());
		warn.setRecedMarginAmount(model.getInitMarginAmount());
		//保证金是否足额，保证金余额-合同保证金>=0即为是，否则为否
		warn.setMarginFull(BigDecimalUtil.subtract(
				model.getInitMarginAmount(), model.getMarginAmount()).doubleValue() >= 0 ? 
						YesNoEnum.YES.getValue() : YesNoEnum.NO.getValue());
		warn.setPrePayment(model.getInitPrePayment());
		warn.setPreOrderNo(model.getPreOrderNo());
		//YANGYONGTODO
		warn.setOrderAmount(new BigDecimal(30000));
		
		
		//订单金额，取对应销售订单的未结金额；
		//订货预收是否足额，以及发货预收是否足额，根据收款条款的预收比例进行判断。如果订单预收比例大于等于收款条款的比例则为是，否则为否
		//预收款盈余=预收金额-订单金额
		warn.setPrePaymentProfit(BigDecimalUtil.subtract(warn.getPrePayment(), warn.getOrderAmount()));
		//预收款差额=订单金额-预收款+对应的冲销金额
		warn.setPrePaymentOver(BigDecimalUtil.subtract(BigDecimalUtil.add( warn.getOrderAmount(), warn.getReversalAmount()),
				warn.getPrePayment()));
		//可冲销金额(不能为负数,只有预收款金额大于订单金额时), 已冲销金额初始化的时候默认为0
		BigDecimal reversalOverAmount = BigDecimalUtil.subtract(warn.getPrePayment(), warn.getOrderAmount());
		if(reversalOverAmount.doubleValue() > 0) {
			warn.setReversalOverAmount(reversalOverAmount);
		}
		return warn;
	}
	
	 /**
     * 设置订货预收/发货预收是否足额
     * @param warn WholesalePrepayWarn
     * @return WholesalePrepayWarn
     * @throws ServiceException 异常
     */
    private WholesalePrepayWarn convertPreOrderFull(WholesalePrepayWarn warn) throws ServiceException {
    	//通过客户编码查询客户对应的收款条款编码
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", warn.getCompanyNo());
		params.put("customerNo", warn.getCustomerNo());
		List<CustomerReceRel> lstReceRel = customerReceRelService.findByBiz(null, params);
		String termNo = "";
		List<WholesaleReceTermDtlDto> lstTermDtl = null;
		if(lstReceRel != null && lstReceRel.size() > 0) {
			termNo = lstReceRel.get(0).getTermNo();
		}
		//通过收款条款编码，查询条款明细集合
		if(StringUtils.isNotEmpty(termNo)) {
			params = new HashMap<String, Object>();
			params.put("termNo", termNo);
			lstTermDtl = wholesaleReceTermDtlService.findByBiz(null, params);
		}
		if(lstTermDtl != null && lstTermDtl.size() > 0) {
			BigDecimal preOrderScale = null;
			BigDecimal preSendOutScale = null;
			// 遍历收款条款明细集合，获取订货\补货的预收比例
			for(WholesaleReceTermDtlDto dtlDto : lstTermDtl) {
				//订货预收比例
				if(dtlDto.getAdvanceType().intValue() == 0 && 
						dtlDto.getControlPoint().intValue() == 0) {
					preOrderScale = dtlDto.getAdvanceScale();
				} 
				//发货预收比例
				else if(dtlDto.getAdvanceType().intValue() == 1 && 
						dtlDto.getControlPoint().intValue() == 0) {
					preSendOutScale = dtlDto.getAdvanceScale();
				}
			}
			BigDecimal orderScale = BigDecimalUtil.division(BigDecimalUtil.multi(warn.getPrePayment(),
					new BigDecimal(100)), warn.getOrderAmount());
			//订货预收是否足额
			if(preOrderScale != null) {
				if(BigDecimalUtil.subtract(orderScale, preOrderScale).doubleValue() > 0) {
					warn.setPreOrderFull(YesNoEnum.YES.getValue());
				} else {
					warn.setPreOrderFull(YesNoEnum.NO.getValue());
				}
			}
			//发货预收是否足额
			if(preSendOutScale != null) {
				if(BigDecimalUtil.subtract(orderScale, preSendOutScale).doubleValue() > 0) {
					warn.setPreSendOutFull(YesNoEnum.YES.getValue());
				} else {
					warn.setPreSendOutFull(YesNoEnum.NO.getValue());
				}
			}
		}
    	return warn;
    }
}