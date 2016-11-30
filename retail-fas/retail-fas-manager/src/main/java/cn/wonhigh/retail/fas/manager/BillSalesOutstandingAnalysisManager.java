package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSalesOutstandingAnalysis;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillSalesOutstandingAnalysisManager extends BaseCrudManager {
	
	public BillSalesOutstandingAnalysis findSalesOutstandingAnalysisCount(Map<String,Object> params) throws ManagerException;
	
	public List<BillSalesOutstandingAnalysis> findSalesOutstandingAnalysis(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;
	
	public void findSalesOutstandingAnalysis(int type,SimplePage page,Map<String,Object> params,Function<Object, Boolean> handler) throws ManagerException;
	
	
	public void processPromotionInfo(BillSalesOutstandingAnalysis billSalesOutstandingAnalysis) throws ManagerException;
    /**
     * 根据大类、品牌、季度、年份、性别 分类汇总 gms 及 pos 销售单据信息
     * @param page
     * @param orderByField
     * @param orderBy
     * @param params
     * @return
     */
    public List<BillSalesOutstandingAnalysis> findBillSaleCollectList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;
    
    /**
     * 根据大类、品牌、季度、年份、性别 分类汇总 的记录数
     * @param params
     * @return
     */
    public BillSalesOutstandingAnalysis findBillSaleCollectCount(Map<String,Object> params) throws ManagerException;
    
    /**
     * 查询销售回款分析明细，用于导出
     * @param params
     * @return
     */
    public List<BillSalesOutstandingAnalysis> findSaleDetailForExport(Map<String,Object> params) throws ManagerException;
}