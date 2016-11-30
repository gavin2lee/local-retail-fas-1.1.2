package cn.wonhigh.retail.fas.api.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.dto.WholesaleCustomerRemainingDto;

public interface WholesaleCustomerRemainingMapper {
	
	List<WholesaleCustomerRemainingDto> selectRemainingSumByParams(@Param("params") Map<String,Object> params);
	
	List<WholesaleCustomerRemainingDto> selectRemainingDtlByParams(@Param("params") Map<String,Object> params);
	
	int insertCustomerRemainingSum(WholesaleCustomerRemainingDto remainingSumDto);
	
	int updateCustomerRemainingSum(WholesaleCustomerRemainingDto remainingSumDto);
	
	int insertCustomerRemainingDtl(WholesaleCustomerRemainingDto remainingDto);
	
}
