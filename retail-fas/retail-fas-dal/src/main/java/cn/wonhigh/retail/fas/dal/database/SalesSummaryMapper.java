package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.SalesSummary;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-10-23 09:29:50
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
public interface SalesSummaryMapper extends BaseCrudMapper {
	
	public SalesSummary selectSalesSummaryCount(@Param("page") SimplePage page,@Param("params")Map<String,Object> params,@Param("authorityParams") AuthorityParams authorityParams);
	
	public List<SalesSummary> selectSalesSummary(@Param("page") SimplePage page, @Param("params") Map<String,Object> params);
	
	public int selectCount(@Param("params")Map<String,Object> params);
	
}