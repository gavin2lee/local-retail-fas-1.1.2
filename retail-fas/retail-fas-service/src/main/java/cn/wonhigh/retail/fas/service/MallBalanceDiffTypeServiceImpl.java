package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.CostCategorySetting;
import cn.wonhigh.retail.fas.common.model.MallBalanceDiffType;
import cn.wonhigh.retail.fas.common.model.SequenceStrId;
import cn.wonhigh.retail.fas.dal.database.MallBalanceDiffTypeMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-11 10:15:51
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
@Service("mallBalanceDiffTypeService")
class MallBalanceDiffTypeServiceImpl extends BaseServiceImpl implements MallBalanceDiffTypeService {
    @Resource
    private MallBalanceDiffTypeMapper mallBalanceDiffTypeMapper;

    @Resource
    private CodingRuleService codingRuleService; 
    
    @Override
    public BaseCrudMapper init() {
        return mallBalanceDiffTypeMapper;
    }
    
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
   			validate(modelType);
   			String billNo = "";
   			billNo = codingRuleService.getSerialNo(CostCategorySetting.class.getSimpleName());
   			MallBalanceDiffType mallBalanceDiffType=(MallBalanceDiffType)modelType;
   			
   			mallBalanceDiffType.setBillNo(billNo);
   			mallBalanceDiffType.setCode(generationCode(mallBalanceDiffType));
   			if(modelType != null){
				 if(modelType instanceof SequenceStrId){
					SequenceStrId id = (SequenceStrId)modelType;
					if(null == id.getId()){
						mallBalanceDiffType.setId(String.valueOf(getId()));
					}
				}
			}
   			return mallBalanceDiffTypeMapper.insertSelective(modelType);
   		} catch (Exception e) {
   			throw new ServiceException("",e);
   		}
   	}

    	/**
    	 * 生成单据编号
    	 */
    	private String generationCode(MallBalanceDiffType mallBalanceDiffType){
    		String  code = "1";
    		String maxCode = mallBalanceDiffTypeMapper.getMaxCode(mallBalanceDiffType);
    		if(maxCode != null && !"".equals(maxCode)){
    			code=String.valueOf(Integer.valueOf(maxCode)+1);
    		}
    		String naCode =  code; //String.valueOf(
    		return  naCode;
    	}
    	
}