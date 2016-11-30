package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSalesOutstandingAnalysis;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-13 16:42:47
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
public interface BillSalesOutstandingAnalysisMapper extends BaseCrudMapper {
	
	public BillSalesOutstandingAnalysis findSalesOutstandingAnalysisCount(@Param("params")Map<String,Object> params);
	
	public  List<BillSalesOutstandingAnalysis> findSalesOutstandingAnalysis(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params,@Param("authorityParams") AuthorityParams authorityParams);
    
    /**
     * 根据大类、品牌、季度、年份、性别 分类汇总 gms 及 pos 销售单据信息
     * @param page
     * @param orderByField
     * @param orderBy
     * @param params
     * @return
     */
    public List<BillSalesOutstandingAnalysis> findBillSaleCollectList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);
    
    /**
     * 根据大类、品牌、季度、年份、性别 分类汇总 的记录数
     * @param params
     * @return
     */
    public BillSalesOutstandingAnalysis findBillSaleCollectCount(@Param("params")Map<String,Object> params);
    
    /**
     * 查询销售回款分析明细，用于导出
     * @param params
     * @return
     */
    public List<BillSalesOutstandingAnalysis> findSaleDetailForExport(@Param("params")Map<String,Object> params);
    
}