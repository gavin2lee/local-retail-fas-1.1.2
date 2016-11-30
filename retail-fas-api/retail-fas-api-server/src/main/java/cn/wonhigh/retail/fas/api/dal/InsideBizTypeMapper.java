package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.model.InsideBizTypeModel;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-04-02 14:16:40
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface InsideBizTypeMapper {
	/**
	 * 根据公司编号及状态查询内购业务类型信息
	 * @param params
	 * @return
	 * @author wangyj
	 */
	List<InsideBizTypeModel> findInsideBizTypeByCodition(Map<String,Object> params);
	/**
	 * 根据公司或业务类型编号查询客户信息
	 * @param params
	 * @return
	 * @author wangyj
	 */
	List<InsideBizTypeModel> findClientListByCodition(Map<String,Object> params);
}