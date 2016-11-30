package cn.wonhigh.retail.fas.manager;

import java.util.Date;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDeductFooterDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface BillShopBalanceDeductManager extends BaseCrudManager {
	
	/**
	 * 生成店铺的费用数据
	 * @param deductParams
	 * @return
	 * @throws ManagerException
	 */
	int generateBalanceDeduct(BillShopBalanceDeduct deductParams) throws ManagerException;
	
	int updateBalanceDeductBalanceNo(Map<String,Object> params);
	
	BillShopBalanceDeduct getSumAmount(Map<String,Object> params); 
	
	/**
	 * 获取页脚汇总对象
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	BillShopBalanceDeductFooterDto getFooterDto(Map<String, Object> params);
	
	int remove(String[] ids,String createUser,Date createTime) throws ManagerException;
}