package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yougou.logistics.base.common.exception.RpcException;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;

/**
 * 开票申请单导出时转换model对象的类
 * 
 * @author 杨勇
 * @date 2014-7-8 下午5:23:40
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillBalanceInvoiceApplyExportFormat extends AbstractExportFormat<BillBalanceInvoiceApply> {

	@Override
	public Map format(List<String> fields, BillBalanceInvoiceApply obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				if("status".equals(field)) {
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
		if("1".equals(value)) {
			return "制单";
		}
		if("2".equals(value)) {
			return "确认";
		}
		if("3".equals(value)) {
			return "已开票";
		}
		return "";
	}
}
