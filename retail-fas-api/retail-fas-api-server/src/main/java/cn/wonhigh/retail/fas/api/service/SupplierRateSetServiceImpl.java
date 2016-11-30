package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.yougou.logistics.base.common.exception.ServiceException;
import cn.wonhigh.retail.fas.api.dal.SupplierRateSetMapper;
import cn.wonhigh.retail.fas.common.dto.SupplierRateSetDto;

@Service("SupplierRateSetServiceImpl")
public class SupplierRateSetServiceImpl implements SupplierRateSetService{

	@Resource
	private SupplierRateSetMapper supplierRateSetMapper ;
	
	private Logger log = Logger.getLogger(getClass());
	
	public List<SupplierRateSetDto> getSupplierRateSetBySupplierNo(Map<String, Object> params) throws ServiceException{ 
		try{
			return supplierRateSetMapper.getSupplierRateSetBySupplierNo(params);
		}catch(Exception e){
			// TODO Auto-generated catch block
			log.debug("系统错误-->获取供应商设置失败");
			throw new ServiceException("获取供应商设置失败",e);
		}
	}
}
