package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;
import cn.wonhigh.retail.fas.dal.database.WholesalePrepayWarnMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 11:02:19
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
@Service("wholesalePrepayWarnService")
class WholesalePrepayWarnServiceImpl extends BaseServiceImpl implements WholesalePrepayWarnService {
	
    @Resource
    private WholesalePrepayWarnMapper wholesalePrepayWarnMapper;

    @Override
    public BaseCrudMapper init() {
        return wholesalePrepayWarnMapper;
    }

	@Override
	public Map<String, BigDecimal> selectTotalAmount(Map<String, Object> params)
			throws ServiceException {
		return wholesalePrepayWarnMapper.selectTotalAmount(params);
	}

	/**
	 * 查询保证金额
	 * @param params 查询参数
	 * @return Map<String, BigDecimal>
	 * @throws ServiceException 异常
	 */
	@Override
	public Map<String, BigDecimal> selectMarginAmount(Map<String, Object> params)
			throws ServiceException {
		return wholesalePrepayWarnMapper.selectMarginAmount(params);
	}

	/**
	 * 查询可冲销的数据集合
	 * @param params 查询参数
	 * @return List<WholesalePrepayWarn>
	 */
	@Override
	public List<WholesalePrepayWarn> selectReversal(Map<String, Object> params)
			throws ServiceException {
		return wholesalePrepayWarnMapper.selectReversal(params);
	}

	/**
	 * 修改指定公司和客户的保证金余额和保证金是否足额标志
	 * @param params 参数
	 * @return 影响的记录数
	 * @throws ServiceException 异常
	 */
	@Override
	public int updateMargin(Map<String, Object> params) throws ServiceException {
		try {
			return wholesalePrepayWarnMapper.updateMargin(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}