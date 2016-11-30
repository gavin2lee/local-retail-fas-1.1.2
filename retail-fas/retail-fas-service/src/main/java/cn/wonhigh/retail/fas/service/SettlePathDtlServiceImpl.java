package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.dal.database.SettlePathDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
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
@Service("settlePathDtlService")
class SettlePathDtlServiceImpl extends BaseServiceImpl implements SettlePathDtlService {
	
    @Resource
    private SettlePathDtlMapper settlePathDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return settlePathDtlMapper;
    }

	@Override
	public int findCompanyCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return settlePathDtlMapper.findCompanyCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<Object> findCompanyPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException {
		try {
			return settlePathDtlMapper.findCompanyPage(page, orderByField, orderBy,params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
    
	/**
	 * 通过结算路径编码删除明细数据
	 * @param pathNo 结算路径编码
	 * @return 删除的记录数
	 * @throws ServiceException 异常
	 */
	@Override
	public int deleteByPathNo(String pathNo) throws ServiceException {
		try {
			return settlePathDtlMapper.deleteByPathNo(pathNo);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}