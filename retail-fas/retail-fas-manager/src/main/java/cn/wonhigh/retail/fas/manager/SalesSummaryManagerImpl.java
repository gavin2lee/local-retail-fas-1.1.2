package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.bcel.AtAjAttributes;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;
import cn.wonhigh.retail.fas.common.model.SalesSummary;
import cn.wonhigh.retail.fas.service.SalesSummaryService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 15:58:32
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
@Service("salesSummaryManager")
class  SalesSummaryManagerImpl   extends BaseCrudManagerImpl implements SalesSummaryManager{
    @Resource
	public SalesSummaryService salesSummaryService;
	@Override
	protected BaseCrudService init() {
		// TODO Auto-generated method stub
		return salesSummaryService;
	}
	
	@Override
	public List<SalesSummary> selectSalesSummary(SimplePage page, Map<String,Object> params)throws ManagerException   {
		// TODO Auto-generated method stub
		return salesSummaryService.selectSalesSummary(page,params);
	}
	
	@Override
	public int selectCount(Map<String, Object> params) throws ManagerException  {
		
		return salesSummaryService.selectCount(params);
	}

	@Override
	public SalesSummary selectSalesSummaryCount(Map<String, Object> params,
			AuthorityParams authorityParams) throws ManagerException {
		// TODO Auto-generated method stub
		return salesSummaryService.selectSalesSummaryCount(params,authorityParams);
	}

}