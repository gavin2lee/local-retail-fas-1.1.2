package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.InsideBizTypeMapper;
import cn.wonhigh.retail.fas.api.model.InsideBizTypeModel;

import com.yougou.logistics.base.common.exception.ServiceException;

@Service("InsideBizTypeService")
public class InsideBiztypeServiceImpl implements InsideBizTypeService {

	@Resource
	private InsideBizTypeMapper insideBizTypeMapper;
	
	@Override
	public List<InsideBizTypeModel> findInsideBizTypeByCodition(
			Map<String, Object> params) throws ServiceException {
		return insideBizTypeMapper.findInsideBizTypeByCodition(params);
	}

	
	@Override
	public List<InsideBizTypeModel> findClientListByCodition(
			Map<String, Object> params) throws ServiceException {
		return insideBizTypeMapper.findClientListByCodition(params);
	}

}
