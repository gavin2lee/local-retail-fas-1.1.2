package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.backend.data.core.DbHelper;
import cn.wonhigh.retail.fas.common.model.BillSalesOutstandingAnalysis;
import cn.wonhigh.retail.fas.dal.database.BillSalesOutstandingAnalysisMapper;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-13 15:20:45
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
@Service("billSalesOutstandingAnalysisService")
class BillSalesOutstandingAnalysisServiceImpl extends BaseCrudServiceImpl implements BillSalesOutstandingAnalysisService {
    @Resource
    private BillSalesOutstandingAnalysisMapper billSalesOutstandingAnalysisMapper;

    @Override
    public BaseCrudMapper init() {
        return billSalesOutstandingAnalysisMapper;
    }
    
    @Override
    public BillSalesOutstandingAnalysis findSalesOutstandingAnalysisCount(Map<String,Object> params) throws ServiceException{
    	return billSalesOutstandingAnalysisMapper.findSalesOutstandingAnalysisCount(params);
    }
    
	public void findSalesOutstandingAnalysis(int type,SimplePage page,Map<String,Object> params,Function<Object, Boolean> handler) throws ServiceException{
		String statement;
		if( type == 1)
			statement = "cn.wonhigh.retail.fas.dal.database.BillSalesOutstandingAnalysisMapper.findSalesOutstandingAnalysis";
//			statement = "cn.wonhigh.retail.fas.dal.database.BillSalesOutstandingAnalysisMapper.findSaleDetailForExport";
		else
			statement = "cn.wonhigh.retail.fas.dal.database.BillSalesOutstandingAnalysisMapper.findBillSaleCollectList";
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("page", page);
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ServiceException(e);
		}	
	}
	
	@Override
	public  List<BillSalesOutstandingAnalysis> findSalesOutstandingAnalysis(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ServiceException {
		return billSalesOutstandingAnalysisMapper.findSalesOutstandingAnalysis(page, orderByField, orderBy, params, null);
	}
	
	/**
     * 根据大类、品牌、季度、年份、性别 分类汇总 gms 及 pos 销售单据信息
     * @param page
     * @param orderByField
     * @param orderBy
     * @param params
     * @return
     */
    @Override
    public List<BillSalesOutstandingAnalysis> findBillSaleCollectList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ServiceException{
    	return billSalesOutstandingAnalysisMapper.findBillSaleCollectList(page, orderByField, orderBy, params);
    }
    
    /**
     * 根据大类、品牌、季度、年份、性别 分类汇总 的记录数
     * @param params
     * @return
     */
    @Override
    public BillSalesOutstandingAnalysis findBillSaleCollectCount(Map<String,Object> params) throws ServiceException{
    	return billSalesOutstandingAnalysisMapper.findBillSaleCollectCount(params);
    }
    
    /**
     * 查询销售回款分析明细，用于导出
     * @param params
     * @return
     */
    @Override
    public List<BillSalesOutstandingAnalysis> findSaleDetailForExport(Map<String,Object> params) throws ServiceException{
    	return billSalesOutstandingAnalysisMapper.findSaleDetailForExport(params);
    }

}