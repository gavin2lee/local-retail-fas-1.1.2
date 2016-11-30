package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.wonhigh.retail.fas.common.model.POSOcGroupOrderPayway;
import cn.wonhigh.retail.fas.common.utils.CommonConvertModelWithDto;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupOrderPaywayDto;
 

/**
 * TODO: 增加描述
 * 
 * @author zhang.lh
 * @date 2014-12-3 下午6:02:06
 * @version 0.1.0 
 * @copyright wonhigh.cn
 */
@Component("groupPayWayDataSet")
public class GroupPayWayDataSet extends CommonConvertModelWithDto<POSOcGroupOrderPayway, POSOcGroupOrderPaywayDto>{
 
 	public List<POSOcGroupOrderPaywayDto> convertListDto(List<POSOcGroupOrderPayway> list, Class<?> classType) throws Exception {
		List<POSOcGroupOrderPaywayDto> ocGroupOrderPaywayDto = new ArrayList<POSOcGroupOrderPaywayDto>();
		if (null != list) {
			ocGroupOrderPaywayDto = super.convertListDto(list, classType);
 		}
 		return ocGroupOrderPaywayDto;
	}

}
