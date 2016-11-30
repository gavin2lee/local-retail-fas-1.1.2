package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.MemberOrderDetail;
import cn.wonhigh.retail.fas.common.model.MemberOrderSummary;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-23 15:21:34
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
public interface OrderDtlMapper extends BaseCrudMapper {

	public int selectOrderSummaryCount(@Param("params") Map<String, Object> params);

	public List<MemberOrderSummary> selectOrderSummaryByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public List<MemberOrderSummary> selectOrderSummaryOperateByPage(@Param("page") SimplePage page,
			@Param("orderByField") String orderByField, @Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params, @Param("authorityParams") AuthorityParams authorityParams);

	public int financeConfirm(@Param("params") Map<String, Object> params);

	public int financeAntiConfirm(@Param("params") Map<String, Object> params);

	public List<MemberOrderDetail> selectOrderMemberList(@Param("params") Map<String, Object> params);
}