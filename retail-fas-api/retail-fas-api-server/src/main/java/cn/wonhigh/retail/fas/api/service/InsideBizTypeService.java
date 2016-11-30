package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.api.model.InsideBizTypeModel;

import com.yougou.logistics.base.common.exception.ServiceException;

public interface InsideBizTypeService {
	
	/**
	 * 根据公司编号及状态查询内购业务类型信息
	 * @param params
	 * @return
	 * @author wangyj
	 */
	List<InsideBizTypeModel> findInsideBizTypeByCodition(Map<String,Object> params) throws ServiceException;
	
	/**
	 * 根据公司编号及内购业务类型查询客户信息
	 * @param params
	 * @return
	 * @author wangyj
	 */
	List<InsideBizTypeModel> findClientListByCodition(Map<String,Object> params) throws ServiceException;
	
}
