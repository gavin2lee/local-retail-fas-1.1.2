/**
 * title:InvoiceInfoExportFormat.java
 * package:cn.wonhigh.retail.fas.common.exportformat
 * description:开票信息导出格式化
 * auther:user
 * date:2015-4-30 下午12:57:25
 */
package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.wonhigh.retail.fas.common.model.InvoiceInfo;
@SuppressWarnings({"rawtypes", "unchecked"})
public class InvoiceInfoExportFormat extends AbstractExportFormat<InvoiceInfo>{
	@Override
	public Map format(List<String> fields, InvoiceInfo obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				if("clientType".equals(field)) {
					map.put(field, convertClientType(getFieldVal(field, obj).toString()));
				}
				if("invoiceType".equals(field)){
					map.put(field, convertInvoiceType(getFieldVal(field, obj).toString()));
				}
				if("status".equals(field)){
					map.put(field, convertStatus(getFieldVal(field, obj).toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	private String convertClientType(String value) {
		if(StringUtils.isEmpty(value)) {
			return "";
		}
		if("1".equals(value)) {
			return "公司";
		}
		if("2".equals(value)) {
			return "客户";
		}
		if("3".equals(value)) {
			return "商场";
		}
		if("4".equals(value)) {
			return "商业集团";
		}
		if("5".equals(value)) {
			return "供应商";
		}
		return "";
	}
	
	private String convertInvoiceType(String value) {
		if(StringUtils.isEmpty(value)) {
			return "";
		}
		if("0".equals(value)) {
			return "普通发票";
		}
		if("1".equals(value)) {
			return "增值票";
		}
		return "";
	}
	
	private String convertStatus(String value) {
		if(StringUtils.isEmpty(value)) {
			return "";
		}
		if("0".equals(value)) {
			return "备选";
		}
		if("1".equals(value)) {
			return "首选";
		}
		return "";
	}
}
