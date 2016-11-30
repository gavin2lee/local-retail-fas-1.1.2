package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.MallDeductionSet;
import cn.wonhigh.retail.fas.common.model.SequenceId;
import cn.wonhigh.retail.fas.common.model.SequenceStrId;
import cn.wonhigh.retail.fas.dal.database.MallDeductionSetMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-30 09:49:24
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
@Service("mallDeductionSetService")
class MallDeductionSetServiceImpl extends BaseServiceImpl implements MallDeductionSetService {
    @Resource
    private MallDeductionSetMapper mallDeductionSetMapper;
    

    @Override
    public BaseCrudMapper init() {
        return mallDeductionSetMapper;
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
   			MallDeductionSet mallDeductionSet=(MallDeductionSet)modelType;
   			
   			if(modelType != null){
				 if(modelType instanceof SequenceStrId){
					SequenceStrId id = (SequenceStrId)modelType;
					if(null == id.getId()){
						mallDeductionSet.setId(String.valueOf(getId()));
					}
				}
			}
   			mallDeductionSet.setCode(generationCode(mallDeductionSet));
   			return mallDeductionSetMapper.insertSelective(modelType);
   		} catch (Exception e) {
   			throw new ServiceException("",e);
   		}
   	}

    	/**
    	 * 生成单据编号
    	 */
    	private String generationCode(MallDeductionSet mallDeductionSet){
    		String  code = "1";
    		String maxCode = mallDeductionSetMapper.getMaxCode(mallDeductionSet);
    		if(maxCode != null && !"".equals(maxCode)){
    			code=String.valueOf(Integer.valueOf(maxCode)+1);
    		}
    		return code;
    	}

		@Override
		public List<MallDeductionSet> selectCostByParams(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) {
			return mallDeductionSetMapper.selectCostByParams(page, orderByField, orderBy, params);
		}

		@Override
		public int selectCostByCount(Map<String, Object> params) {
			return mallDeductionSetMapper.selectCostByCount(params);
		}
    	
}