package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;

/**
 * 店铺信息管理导入需要转换的字段处理类
 * @author 王仕秒
 * @date 2015-9-29 
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ShopTerminalAccountExportFormat extends AbstractExportFormat<SelfShopTerminalAccount> {
	@Override
	public Map format(List<String> fields, SelfShopTerminalAccount obj) throws Exception {
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

	private Object convertStatus(String value) {
		if("0".equals(value)) {
			return "启用";
		}
		if("1".equals(value)) {
			return "停用";
		}
		return value;
	}
	
}
