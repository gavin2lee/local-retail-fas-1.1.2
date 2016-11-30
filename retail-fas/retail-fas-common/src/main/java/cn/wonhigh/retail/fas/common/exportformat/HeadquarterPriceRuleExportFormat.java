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

import cn.wonhigh.retail.fas.common.model.HeadquarterPriceRule;
@SuppressWarnings({"rawtypes", "unchecked"})
public class HeadquarterPriceRuleExportFormat extends AbstractExportFormat<HeadquarterPriceRule>{
	@Override
	public Map format(List<String> fields, HeadquarterPriceRule obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				if("addBasis".equals(field)){
					map.put(field, convertAddBasis(getFieldVal(field, obj).toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);

		}
	}
	
	private String convertAddBasis(String value) {
		if(StringUtils.isEmpty(value)) {
			return "";
		}
		if("1".equals(value)) {
			return "厂进价";
		}
		if("2".equals(value)) {
			return "牌价";
		}
		return "";
	}
}
