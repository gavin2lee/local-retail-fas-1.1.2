package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplitDtl;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 14:37:36
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
public interface BillBacksectionSplitDtlService extends BaseCrudService {
	
	/**
	 * 查询合计
	 * @param params
	 * @return
	 */
	BillBacksectionSplitDtl selectTotalSum(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 通过结算单号，汇总本次回款金额
	 * @param balanceNo 结算单号
	 * @return 本次回款金额
	 */
	BigDecimal sumPaymentAmount(String balanceNo);
	

	int selectShopBlanaceAccountCount(Map<String, Object> params) throws ServiceException;

	List<BillBacksectionSplitDtl> selectShopBlanaceAccountByPage(SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ServiceException;
	
	/**
	 * 批量查询出新增明细数据
	 * @param params
	 * @return
	 */
	List<BillBacksectionSplitDtl> selectAddInsertDtl(Map<String, Object> params);
	
	/**
	 * 根据回款单编号删除明细
	 * @param backsectionNo
	 * @return
	 */
	int deleteByBacksectionNo(String backsectionNo);
}