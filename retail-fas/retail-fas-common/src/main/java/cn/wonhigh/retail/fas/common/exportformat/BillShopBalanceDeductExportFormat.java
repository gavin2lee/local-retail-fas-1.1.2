package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import com.yougou.logistics.base.common.exception.RpcException;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;

/**
 * 费用管理导出时转换model对象的类
 * @author 杨勇
 * @date 2015-1-19 下午5:23:40
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillShopBalanceDeductExportFormat extends AbstractExportFormat<BillShopBalanceDeduct> {

	@Override
	public Map format(List<String> fields, BillShopBalanceDeduct obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				Object fieldVal = getFieldVal(field, obj);
				if(fieldVal == null) {
					continue;
				}
				if("status".equals(field)) {
					map.put(field, convertBillStatus(fieldVal.toString()));
				}
				if("costType".equals(field)) {
					map.put(field, convertCostType(fieldVal.toString()));
				}
				if("costDeductType".equals(field)) {
					map.put(field, convertCostDeductType(fieldVal.toString()));
				}
				if("costPayType".equals(field)) {
					map.put(field, convertCostPayType(fieldVal.toString()));
				}
				if("processStatus".equals(field)) {
					map.put(field, convertProcessStatus(fieldVal.toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	private String convertBillStatus(String value) {
		if("1".equals(value)) {
			return "未结";
		}
		if("2".equals(value)) {
			return "已结";
		}
		if("3".equals(value)) {
			return "已开票";
		}
		return value;
	}
	
	private String convertCostType(String value) {
		if("1".equals(value)) {
			return "合同内";
		}
		if("2".equals(value)) {
			return "合同外";
		}
		return "";
	}
	
	private String convertCostPayType(String value) {
		if("1".equals(value)) {
			return "帐扣";
		}
		if("2".equals(value)) {
			return "现金";
		}
		return "";
	}
	
	private String convertCostDeductType(String value) {
		if("1".equals(value)) {
			return "票前";
		}
		if("2".equals(value)) {
			return "票后";
		}
		return "";
	}
	
	private String convertProcessStatus(String value) {
		if("2".equals(value)) {
			return "已完成";
		}
		return "未完成";
	}
}
