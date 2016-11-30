package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.CostCategorySetting;
import cn.wonhigh.retail.fas.common.model.SequenceStrId;
import cn.wonhigh.retail.fas.dal.database.CostCategorySettingMapper;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-08-28 10:48:25
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * r
 */
@Service("costCategorySettingService")
class CostCategorySettingServiceImpl extends BaseServiceImpl implements CostCategorySettingService {
    @Resource
    private CostCategorySettingMapper costCategorySettingMapper;

    
	 @Resource
	    private CodingRuleService codingRuleService;  
	 
    @Override
    public BaseCrudMapper init() {
        return costCategorySettingMapper;
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
			CostCategorySetting costCategorySet=(CostCategorySetting)modelType;
			if(modelType != null){
				 if(modelType instanceof SequenceStrId){
					SequenceStrId id = (SequenceStrId)modelType;
					if(null == id.getId()){
						costCategorySet.setId(String.valueOf(getId()));
					}
				}
			}
			costCategorySet.setCostCateNo(billNo);
			costCategorySet.setCode(generationCode(costCategorySet));
			return costCategorySettingMapper.insertSelective(modelType);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

 	/**
 	 * 生成单据编号
 	 */
 	private String generationCode(CostCategorySetting costCategorySet){
// 		String prefix = "IS";//BalanceTypeEnums.getTypeCodeByNo(billBalance.getBalanceType());
// 		Calendar cal = Calendar.getInstance();
// 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhh");
// 		String dateSeq = dateFormat.format(cal.getTime());
// 		invoiceRuleSet.setInvoiceRuleNo(dateSeq);
// 		int nextCode=0001;
 		String  code = "1";
 		String maxCode = costCategorySettingMapper.getMaxCode(costCategorySet);
 		if(maxCode != null && !"".equals(maxCode)){
// 			nextCode = Integer.valueOf(maxCode)+1;	
 			code=String.valueOf(Integer.valueOf(maxCode)+1);
 		}
 		String naCode =  code; //String.valueOf(
 		return  naCode;
 	}
 	
    @Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public <ModelType> int save(Map<CommonOperatorEnum, List<ModelType>> params) throws ServiceException {
		try {
			int count=0;
			for (Entry<CommonOperatorEnum, List<ModelType>> param : params.entrySet()) {
				if(param.getKey().equals(CommonOperatorEnum.DELETED)){
					List<ModelType> list=params.get(CommonOperatorEnum.DELETED);
					if(null!=list&&list.size()>0){
						for (ModelType modelType : list) {
							count+=this.costCategorySettingMapper.deleteByPrimarayKeyForModel(modelType);
						}
					}
				}
				if(param.getKey().equals(CommonOperatorEnum.UPDATED)){
					List<ModelType> list=params.get(CommonOperatorEnum.UPDATED);
					if(null!=list&&list.size()>0){
						for (ModelType modelType : list) {
							count+=this.costCategorySettingMapper.updateByPrimaryKeySelective(modelType);
						}
					}
				}
				if(param.getKey().equals(CommonOperatorEnum.INSERTED)){
					List<ModelType> list=params.get(CommonOperatorEnum.INSERTED);
					if(null!=list&&list.size()>0){
						for (ModelType modelType : list) {
							this.costCategorySettingMapper.insertSelective(modelType);
						}
						count+=list.size();
					}
				}
			}
			return count;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
    
}