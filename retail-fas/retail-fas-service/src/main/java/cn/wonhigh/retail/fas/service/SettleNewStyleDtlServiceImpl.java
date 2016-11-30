package cn.wonhigh.retail.fas.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.SettleNewStyleDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-26 15:42:01
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
@Service("settleNewStyleDtlService")
class SettleNewStyleDtlServiceImpl extends BaseServiceImpl implements SettleNewStyleDtlService {
	
    @Resource
    private SettleNewStyleDtlMapper settleNewStyleDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return settleNewStyleDtlMapper;
    }

    /**
	 * 通过结算大类编码删除明细数据
	 * @param settleCategoryNo 结算大类编码
	 * @return 删除的记录数
	 * @throws ServiceException 异常
	 */
	@Override
	public int deleteByStyleNo(String styleNo) throws ServiceException {
		try {
			return settleNewStyleDtlMapper.deleteByStyleNo(styleNo);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}