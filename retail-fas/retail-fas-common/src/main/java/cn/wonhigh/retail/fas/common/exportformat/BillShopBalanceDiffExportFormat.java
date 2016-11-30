package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import com.yougou.logistics.base.common.exception.RpcException;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;

/**
 * 结算单-结算差异导出数据转换类
 * @author 杨勇
 * @date 2014-7-8 下午5:23:40
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillShopBalanceDiffExportFormat extends AbstractExportFormat<BillShopBalanceDiff> {

	@Override
	public Map format(List<String> fields, BillShopBalanceDiff obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				if("status".equals(field)) {
					Object value = getFieldVal(field, obj);
					map.put(field, convertStatus(value == null ? "" : value.toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	private String convertStatus(String value) {
		if("0".equals(value)) {
			return "未完成";
		}
		if("1".equals(value)) {
			return "已完成";
		}
		return "";
	}
}
