package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.MallDeductionSet;

/**
 * 扣费类别导出时转换model对象的类
 * @author 杨勇
 * @date 2015-1-19 下午5:23:40
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MallDeductionSetExportFormat extends AbstractExportFormat<MallDeductionSet> {

	@Override
	public Map format(List<String> fields, MallDeductionSet obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				Object fieldVal = getFieldVal(field, obj);
				if(fieldVal == null) {
					continue;
				}
				if("status".equals(field)) {
					map.put(field, convertStatus(fieldVal.toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	private String convertStatus(String value) {
		if("0".equals(value)) {
			return "停用";
		}
		if("1".equals(value)) {
			return "启用";
		}
		return value;
	}
}
