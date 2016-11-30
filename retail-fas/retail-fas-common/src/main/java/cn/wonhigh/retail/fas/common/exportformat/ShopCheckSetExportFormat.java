/**
 * title:MallBalanceDiffTypeExportFormat.java
 * package:cn.wonhigh.retail.fas.common.exportformat
 * description:差异类型导出格式化
 * auther:user
 * date:2015-4-30 下午12:57:25
 */
package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.wonhigh.retail.fas.common.model.ShopCheckSet;
@SuppressWarnings({"rawtypes", "unchecked"})
public class ShopCheckSetExportFormat extends AbstractExportFormat<ShopCheckSet>{
	@Override
	public Map format(List<String> fields, ShopCheckSet obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				if("status".equals(field)){
					map.put(field, convertStatus(getFieldVal(field, obj).toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);

		}
	}
	
	private String convertStatus(String value) {
		if(StringUtils.isEmpty(value)) {
			return "";
		}
		if("0".equals(value)) {
			return "启用";
		}
		if("1".equals(value)) {
			return "禁用";
		}
		return "";
	}
}
