package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.OrderDtlDto;
import cn.wonhigh.retail.fas.common.model.MemberOrderDetail;
import cn.wonhigh.retail.fas.common.model.MemberOrderDtl;
import cn.wonhigh.retail.fas.common.model.MemberOrderSummary;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface OrderDtlManager extends BaseCrudManager {

	public int selectOrderSummaryCount(Map<String, Object> params) throws ManagerException;

	public List<MemberOrderSummary> selectOrderSummaryByPage(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException;

	public List<MemberOrderSummary> selectOrderSummaryOperateByPage(SimplePage page, String orderByField,
			String orderBy, Map<String, Object> params) throws ManagerException;

	public int financeConfirm(List<OrderDtlDto> list,MemberOrderDtl dtl) throws ManagerException;

	public int financeAntiConfirm(List<OrderDtlDto> list) throws ManagerException;

	public List<MemberOrderDetail> selectOrderMemberList(Map<String, Object> params) throws ManagerException;
	
	/**
	 * 此方法针对选中汇总记录进行确认的处理，根据公司编号、店铺编号、及日期查询出所以记录，并进行操作
	 * @param 店铺编号
	 * @param dtl 公司编号、店铺编号、及日期
	 * @return
	 * @author wangyj
	 * @throws ManagerException
	 */
	public int financeConfirms(String shopNoStr,MemberOrderDtl dtl,String startDate,String endDate) throws ManagerException;
}