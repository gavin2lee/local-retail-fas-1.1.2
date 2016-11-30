package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.dal.database.OperateLogMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-11-20 11:53:39
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
@Service("operateLogService")
class OperateLogServiceImpl extends BaseCrudServiceImpl implements OperateLogService {
	
    @Resource
    private OperateLogMapper operateLogMapper;

    @Override
    public BaseCrudMapper init() {
        return operateLogMapper;
    }

    /**
     * 通过单据编码和模块编码删除数据
     * @param params 限制参数
     * @return 删除的数量
     * @throws Exception 异常
     */
    @Override
	public int deleteByDataAndModuleNo(Map<String, Object> params)
			throws ServiceException {
		try {
			return operateLogMapper.deleteByDataAndModuleNo(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}


	@Override
	public int deleteByDataAndModuleNo(String dataNo, String moduleNo)
			throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("moduleNo", moduleNo);
			params.put("dataNo", dataNo);
			return operateLogMapper.deleteByDataAndModuleNo(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
    /**
	 * 通过单据编码和模块编码查询group by后的数据
	 * @param params 查询参数
	 * @return 数据集合
	 * @throws ServiceException 异常
	 */
	@Override
	public List<OperateLog> selectProcessData(Map<String, Object> params)
			throws ServiceException {
		try {
			return operateLogMapper.selectProcessData(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}