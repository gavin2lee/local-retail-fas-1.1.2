package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.WholesaleMarginInit;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author admin
 * @date  2014-09-19 15:00:52
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
public interface WholesaleMarginInitManager extends BaseCrudManager {

	/**
	 * 完结初始化
	 * @param list 待完结的数据集合
	 * @return Boolean
	 */
	Boolean finishBill(List<WholesaleMarginInit> list) throws ManagerException;
	
}