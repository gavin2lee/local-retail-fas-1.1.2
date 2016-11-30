package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.SettleCategoryDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-22 14:57:26
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
@Service("settleCategoryDtlService")
class SettleCategoryDtlServiceImpl extends BaseServiceImpl implements SettleCategoryDtlService {
	
    @Resource
    private SettleCategoryDtlMapper settleCategoryDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return settleCategoryDtlMapper;
    }

    /**
	 * 通过结算大类编码删除明细数据
	 * @param settleCategoryNo 结算大类编码
	 * @return 删除的记录数
	 * @throws ServiceException 异常
	 */
	@Override
	public int deleteBySettleCategoryNo(String settleCategoryNo) throws ServiceException {
		try {
			return settleCategoryDtlMapper.deleteBySettleCategoryNo(settleCategoryNo);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}