package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;
import cn.wonhigh.retail.fas.common.model.DepositCash;

/**
 * POS存现管理需要转换的字段处理类
 * @author 王仕秒
 * @date 2015-9-29 
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DepositCashExportFormat extends AbstractExportFormat<DepositCash> {
	@Override
	public Map format(List<String> fields, DepositCash obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				Object fieldVal = getFieldVal(field, obj);
				if(fieldVal == null) {
					continue;
				}
				if("account".equals(field)) {
					map.put(field, fieldVal.toString());
				}
				if("mallNo".equals(field)) {
					map.put(field, fieldVal.toString());
				}
				if("cashDiff".equals(field)) {
					map.put(field, fieldVal.toString());
				}
				if("status".equals(field)) {
					map.put(field, convertStatus(fieldVal.toString()));
				}
				if("currencyType".equals(field)) {
					map.put(field, convertCurrencyType(fieldVal.toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	private Object convertCurrencyType(String value) {
		if("0".equals(value)) {
			return "人民币";
		}
		if("1".equals(value)) {
			return "美元";
		}
		return value;
	}

	private Object convertStatus(String value) {
		if("1".equals(value)) {
			return "是";
		}else {
			return "否";
		}
	}
}
