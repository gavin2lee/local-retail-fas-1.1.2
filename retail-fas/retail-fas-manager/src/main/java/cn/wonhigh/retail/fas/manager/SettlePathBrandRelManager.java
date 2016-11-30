package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.SettlePathBrandRel;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public interface SettlePathBrandRelManager extends BaseCrudManager {
	
	/**
	 * 保存
	 * @param pathNo 结算路径编码
	 * @param list 结算路径和品牌关联的数据集合
	 * @return 影响条数
	 * @throws ManagerException 异常
	 */
	public int save(String pathNo, List<SettlePathBrandRel> list) 
			throws ManagerException;
}