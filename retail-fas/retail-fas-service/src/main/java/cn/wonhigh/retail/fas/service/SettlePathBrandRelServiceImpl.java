package cn.wonhigh.retail.fas.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.SettlePathBrandRel;
import cn.wonhigh.retail.fas.dal.database.SettlePathBrandRelMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
@Service("settlePathBrandRelService")
class SettlePathBrandRelServiceImpl extends BaseServiceImpl implements SettlePathBrandRelService {
    
	@Resource
    private SettlePathBrandRelMapper settlePathBrandRelMapper;

    @Override
    public BaseCrudMapper init() {
        return settlePathBrandRelMapper;
    }
    
    @Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public int save(String pathNo, List<SettlePathBrandRel> list) 
			throws ServiceException {
    	try {
    		int count=0;
    		//先删
    		this.settlePathBrandRelMapper.deleteBySettlePathNo(pathNo);
    		if(null != list && list.size() > 0){
    			//后增
    			for(SettlePathBrandRel modelType : list) {
    				modelType.setPathNo(pathNo);
    				super.add(modelType);
    			}
    			count+=list.size();
    		}
    		return count;
    	} catch(Exception e) {
    		throw new ServiceException(e.getMessage(), e);
    	}
	}

    /**
	 * 通过结算路径编码删除关联的品牌数据
	 * @param pathNo 结算路径编码
	 * @return 影响条数
	 * @throws ServiceException 
	 */
	@Override
	public int deleteByPathNo(String pathNo) throws ServiceException {
		try {
			return settlePathBrandRelMapper.deleteByPathNo(pathNo);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}