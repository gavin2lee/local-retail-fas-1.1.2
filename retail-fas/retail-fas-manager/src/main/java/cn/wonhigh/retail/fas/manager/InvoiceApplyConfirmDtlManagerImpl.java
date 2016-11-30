package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.InvoiceApplyConfirmDtl;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.InvoiceApplyConfirmDtlService;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-06-03 16:57:11
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
@Service("invoiceApplyConfirmDtlManager")
class InvoiceApplyConfirmDtlManagerImpl extends BaseCrudManagerImpl implements InvoiceApplyConfirmDtlManager {
    @Resource
    private InvoiceApplyConfirmDtlService invoiceApplyConfirmDtlService;

    @Override
    public BaseCrudService init() {
        return invoiceApplyConfirmDtlService;
    }
    
    /**
     * 新增信息
     * @param list
     * @param createUser
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
    public int addInvoiceApplyConfirmDtl(List<OrderDtlDto> list,String createUser) throws ManagerException{
    	int count = 0 ;
    	try{
    	if(CollectionUtils.isNotEmpty(list)){
    		Date date = new Date();
    		for (OrderDtlDto orderDtlDto : list) {
    			if(StringUtils.isEmpty(orderDtlDto.getOrderDtlId())){
    				continue;
    			}
    			InvoiceApplyConfirmDtl invoiceApplyConfirmDtl = new InvoiceApplyConfirmDtl();
    			invoiceApplyConfirmDtl.setOrderDtlId(orderDtlDto.getOrderDtlId());
    			invoiceApplyConfirmDtl.setItemNo(orderDtlDto.getItemNo());
    			//根据单据编号及商品编号查询是否存，如果存则修改状态，不存在则新增记录
    			InvoiceApplyConfirmDtl invoiceApplyConfirmDtlList = invoiceApplyConfirmDtlService.findById(invoiceApplyConfirmDtl);
    			if(null == invoiceApplyConfirmDtlList){
    				invoiceApplyConfirmDtl.setOrderNo(orderDtlDto.getOrderNo());
	    			invoiceApplyConfirmDtl.setShopNo(orderDtlDto.getShopNo());
	    			invoiceApplyConfirmDtl.setCompanyNo(orderDtlDto.getCompanyNo());
	    			invoiceApplyConfirmDtl.setBillType(orderDtlDto.getBizTypeName());
	    			invoiceApplyConfirmDtl.setBalanceType((byte)BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo());
	    			invoiceApplyConfirmDtl.setStatus((byte)YesNoEnum.YES.getValue());
	    			invoiceApplyConfirmDtl.setCreateTime(date);
	    			invoiceApplyConfirmDtl.setCreateUser(createUser);
	    			invoiceApplyConfirmDtl.setUpdateTime(date);
        			invoiceApplyConfirmDtl.setUpdateUser(createUser);
        			invoiceApplyConfirmDtl.setId(UUIDGenerator.getUUID());
	    			count += invoiceApplyConfirmDtlService.add(invoiceApplyConfirmDtl);
    			}else{
    				invoiceApplyConfirmDtl.setUpdateTime(date);
        			invoiceApplyConfirmDtl.setUpdateUser(createUser);
        			invoiceApplyConfirmDtl.setStatus((byte)YesNoEnum.YES.getValue());
        			invoiceApplyConfirmDtl.setOrderNo(orderDtlDto.getOrderNo());
        			count += invoiceApplyConfirmDtlService.updateByOrderNo(invoiceApplyConfirmDtl);
    			}
			}
    	}
    	}catch(ServiceException e){
    		throw new ManagerException();
    	}
    	return count;
    }
    
    /**
     * 根据单据号及产品编号，修改状态或开票申请号
     * @param list
     * @param createUser
     * @return
     * @throws ManagerException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
    public int updateByOrderNo(List<OrderDtlDto> list,String createUser) throws ManagerException{
    	int count = 0 ;
    	try{
    		Date date = new Date();
	    	if(CollectionUtils.isNotEmpty(list)){
	    		for (OrderDtlDto orderDtlDto : list) {
	    			InvoiceApplyConfirmDtl invoiceApplyConfirmDtl = new InvoiceApplyConfirmDtl();
	    			invoiceApplyConfirmDtl.setOrderNo(orderDtlDto.getOrderNo());
	    			invoiceApplyConfirmDtl.setUpdateTime(date);
	    			invoiceApplyConfirmDtl.setUpdateUser(createUser);
	    			invoiceApplyConfirmDtl.setStatus((byte)YesNoEnum.NO.getValue());
	    			invoiceApplyConfirmDtl.setOrderDtlId(orderDtlDto.getOrderDtlId());
	    			count += invoiceApplyConfirmDtlService.updateByOrderNo(invoiceApplyConfirmDtl);
				}
	    	}
    	}catch(ServiceException e){
    		throw new ManagerException();
    	}
    	return count;
    }
    
}