package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.model.POSOrderAndReturnExMainDtl;
import cn.wonhigh.retail.fas.common.utils.CommonConvertModelWithDto;

/**
 * TODO: 增加描述
 * 
 * @author you.jp
 * @date 2014-10-8 下午5:10:40
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */
@Component("orderMainInfoDataSet")
public class OrderMainInfoDataSet extends CommonConvertModelWithDto<POSOrderAndReturnExMainDtl, POSOrderAndReturnExMainDtlDto>{
   	
	public List<POSOrderAndReturnExMainDtlDto> convertListDto(List<POSOrderAndReturnExMainDtl> list, Class<?> classType) throws Exception {
		List<POSOrderAndReturnExMainDtlDto> orderDtoList = new ArrayList<POSOrderAndReturnExMainDtlDto>() ;
		if (null != list) {
			orderDtoList = super.convertListDto(list, classType);
 		}
		return orderDtoList;
	}
}
