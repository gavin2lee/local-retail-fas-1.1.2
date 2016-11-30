package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SettlementBodyChangeRecord;
import cn.wonhigh.retail.fas.dal.database.SettlementBodyChangeRecordMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-12-28 11:09:32
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
@Service("settlementBodyChangeRecordService")
class SettlementBodyChangeRecordServiceImpl extends BaseCrudServiceImpl implements SettlementBodyChangeRecordService {
    @Resource
    private SettlementBodyChangeRecordMapper settlementBodyChangeRecordMapper;

    @Override
    public BaseCrudMapper init() {
        return settlementBodyChangeRecordMapper;
    }

	@Override
	public SettlementBodyChangeRecord findByPageSum(Map<String, Object> params)
			throws ServiceException {
		try{
			return settlementBodyChangeRecordMapper.selectByPageSum(params);
		}catch(Exception e){
			throw new ServiceException("系统错误-->获取存货所属(主体变更)汇总数据失败",e);
		}
	}
}