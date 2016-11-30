package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;

/**
 * 结算路径导出时转换model对象的类
 * @author 杨勇
 * @date 2014-7-8 下午5:23:40
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SettlePathExportFormat extends AbstractExportFormat<SettlePathDto> {

	@Override
	public Map format(List<String> fields, SettlePathDto obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				if("billBasis".equals(field)) {
					Object fieldVal = getFieldVal(field, obj);
					if(fieldVal == null) {
						map.put(field, convertBillBasis(getFieldVal(field, obj).toString()));
					} else {
						map.put(field, "");
					}
				}
				if("styleName".equals(field)) {
					Object value = getFieldVal(field, obj);
					map.put(field, convertStyle(value == null ? "" : value.toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);

		}
	}
	
	private String convertStyle(String value) {
		if("".equals(value)) {
			return "全部";
		}
		return value;
	}
	
	private String convertBillBasis(String value) {
		if("1".equals(value)) {
			return "到货单";
		}
		if("2".equals(value)) {
			return "退厂单";
		}
		return "";
	}
}
