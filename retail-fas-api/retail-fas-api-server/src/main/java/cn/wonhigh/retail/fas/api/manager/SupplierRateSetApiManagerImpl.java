package cn.wonhigh.retail.fas.api.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.service.SupplierRateSetApi;
import cn.wonhigh.retail.fas.api.service.SupplierRateSetService;
import cn.wonhigh.retail.fas.common.dto.SupplierRateSetDto;

import com.yougou.logistics.base.common.exception.RpcException;

@Service("supplierRateSetApiManagerImpl")
public class SupplierRateSetApiManagerImpl implements SupplierRateSetApi {
	@Resource
	private SupplierRateSetService supplierRateSetService;
	
	private Logger log = Logger.getLogger(getClass());
 
	@Override
	public List<SupplierRateSetDto> getSupplierRateSet(List<String> supplierNos) throws RpcException {
		if(supplierNos == null)
			return null;
		
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("supplierNos",supplierNos);
			
			return supplierRateSetService.getSupplierRateSetBySupplierNo(params);
		}catch(Exception ex){ 
			log.error("获取供应商设置失败",ex);
			throw new RpcException("财务服务","获取供应商设置失败",ex);
		}
	}

	@Override
	public SupplierRateSetDto getSupplierRateSet(String supplierNo) throws RpcException {
		if(supplierNo == null || supplierNo.equals(""))
			return null;
		
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("supplierNos",new String[]{supplierNo});
			
			List<SupplierRateSetDto> list = supplierRateSetService.getSupplierRateSetBySupplierNo(params);
			if(list == null || list.isEmpty())
				return null;
			else
				return list.get(0);
		}catch(Exception ex){ 
			log.error("获取供应商设置失败",ex);
			throw new RpcException("财务服务","获取供应商设置失败",ex);
		}
	}
}
