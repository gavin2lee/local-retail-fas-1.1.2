package cn.wonhigh.retail.fas.service;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.SettlePathBrandRel;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.service.BaseCrudService;

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
public interface SettlePathBrandRelService extends BaseCrudService {
	
	/**
	 * 保存
	 * @param pathNo 结算路径编码
	 * @param list 结算路径和品牌关联的数据集合
	 * @return 影响条数
	 * @throws ServiceException 
	 */
	public int save(String pathNo, List<SettlePathBrandRel> list) 
			throws ServiceException;

	/**
	 * 通过结算路径编码删除关联的品牌数据
	 * @param pathNo 结算路径编码
	 * @return 影响条数
	 * @throws ServiceException 
	 */
	public int deleteByPathNo(String pathNo)
			throws ServiceException;
}