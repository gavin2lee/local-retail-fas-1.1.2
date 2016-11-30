package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;
import cn.wonhigh.retail.fas.common.model.SalesSummary;
import cn.wonhigh.retail.fas.dal.database.SalesSummaryMapper;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author zhouxm
 * @date  2014-10-10 11:20:13
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
@Service("salesSummaryService")
class SalesSummaryServiceImpl extends BaseCrudServiceImpl implements SalesSummaryService {
    @Resource
	public SalesSummaryMapper salesSummaryMapper;
    
	@Override
	public BaseCrudMapper init() {
		// TODO Auto-generated method stub
		return salesSummaryMapper;
	}
	@Override
	public List<SalesSummary> selectSalesSummary(SimplePage page, Map<String,Object> params) throws ManagerException  {
		return salesSummaryMapper.selectSalesSummary(page,params);
	}
	@Override
	public int selectCount(Map<String, Object> params)throws ManagerException  {
		// TODO Auto-generated method stub
		return salesSummaryMapper.selectCount(params);
	}
	@Override
	public SalesSummary selectSalesSummaryCount(Map<String, Object> params,
			AuthorityParams authorityParams) throws ManagerException {
		// TODO Auto-generated method stub
		return salesSummaryMapper.selectSalesSummaryCount(null, params, authorityParams);
	}
}
	
