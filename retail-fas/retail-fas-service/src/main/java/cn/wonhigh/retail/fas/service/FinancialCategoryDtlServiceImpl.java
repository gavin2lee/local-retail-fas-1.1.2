package cn.wonhigh.retail.fas.service;

import cn.wonhigh.retail.fas.dal.database.FinancialCategoryDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2014-12-23 10:38:39
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("financialCategoryDtlService")
class FinancialCategoryDtlServiceImpl extends BaseCrudServiceImpl implements FinancialCategoryDtlService {
	
    @Resource
    private FinancialCategoryDtlMapper financialCategoryDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return financialCategoryDtlMapper;
    }
    
    /**
	 * 通过财务大类编码删除明细数据
	 * @param financialCategoryNo 财务大类编码
	 * @return 删除的记录数
	 * @throws ServiceException 异常
	 */
   	@Override
   	public int deleteByFinancialCategoryNo(String financialCategoryNo) throws ServiceException {
   		try {
   			return financialCategoryDtlMapper.deleteByFinancialCategoryNo(financialCategoryNo);
   		} catch(Exception e) {
   			throw new ServiceException(e.getMessage(), e);
   		}
   	}
}