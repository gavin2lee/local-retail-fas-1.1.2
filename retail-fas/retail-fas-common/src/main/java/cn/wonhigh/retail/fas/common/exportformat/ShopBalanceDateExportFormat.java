package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;

/**
 * 结算期管理导出时转换model对象的类
 * @author 杨勇
 * @date 2015-1-19 下午5:23:40
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ShopBalanceDateExportFormat extends AbstractExportFormat<ShopBalanceDate> {

	@Override
	public Map format(List<String> fields, ShopBalanceDate obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				Object fieldVal = getFieldVal(field, obj);
				if(fieldVal == null) {
					continue;
				}
				if("balanceFlag".equals(field)) {
					map.put(field, convertBalanceFlag(fieldVal.toString()));
				}
				if("billalready".equals(field)) {
					map.put(field, convertBillalready(fieldVal.toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);

		}
	}
	
	private String convertBalanceFlag(String value) {
		if("0".equals(value)) {
			return "已生成预估";
		}
		if("1".equals(value)) {
			return "未生成";
		}
		if("2".equals(value)) {
			return "已生成";
		}
		return value;
	}
	
	private String convertBillalready(String value) {
		if("1".equals(value)) {
			return "未开票";
		}
		if("2".equals(value)) {
			return "已开票";
		}
		return value;
	}
}
