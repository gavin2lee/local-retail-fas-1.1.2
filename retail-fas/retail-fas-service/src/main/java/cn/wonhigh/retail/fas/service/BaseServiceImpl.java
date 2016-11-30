package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import cn.wonhigh.retail.fas.common.model.SequenceId;
import cn.wonhigh.retail.fas.common.model.SequenceStrId;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
abstract class  BaseServiceImpl extends BaseCrudServiceImpl {
	
	@Resource
    protected CodingRuleService codingRuleService;
	
	protected int getId()throws ServiceException{
		try {
			String serialNo = codingRuleService.getSerialNo("public").substring(2);
			return Integer.parseInt(serialNo);
		} catch (ServiceException e) {
			throw new ServiceException("",e);
		}
		
	}

	
	@Override
	public <ModelType> int add(ModelType modelType) throws ServiceException {
		try {
			if(modelType != null){
				if( modelType instanceof SequenceId){
					SequenceId id = (SequenceId)modelType;
					if(null == id.getId()){
						id.setId(getId());
					}
				}else if(modelType instanceof SequenceStrId){
					SequenceStrId id = (SequenceStrId)modelType;
					if(null == id.getId()){
						id.setId(String.valueOf(getId()));
					}
				}
			}
			return super.add(modelType);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
}