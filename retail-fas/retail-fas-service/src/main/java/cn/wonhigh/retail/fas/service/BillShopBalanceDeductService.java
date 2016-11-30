package cn.wonhigh.retail.fas.service;


import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDeductFooterDto;
import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDeductDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;

import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-27 19:22:11
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
public interface BillShopBalanceDeductService extends BaseCrudService {
//	int save(String[] deducts) throws ServiceException;
	
	int updateBalanceDeductBalanceNo(Map<String,Object> params);
	
	BillShopBalanceDeduct getSumAmount(Map<String,Object> params); 
	
	/**
	 * 获取费用金额汇总对象
	 * @param params 参数
	 * @return 费用金额汇总对象
	 */
	GatherBillShopBalanceDeductDto gatherBalanceDeduct(Map<String, Object> params);

	/**
	 * 获取页脚汇总对象
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	BillShopBalanceDeductFooterDto getFooterDto(Map<String, Object> params);
}