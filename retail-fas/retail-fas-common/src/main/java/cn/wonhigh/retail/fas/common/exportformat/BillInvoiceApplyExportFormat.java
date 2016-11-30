package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import com.yougou.logistics.base.common.exception.RpcException;

import cn.wonhigh.retail.fas.common.dto.BillInvoiceApplyDto;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;


/**
 * 地区批发申请单导出时转换model对象的类
 * 
 * @author 杨勇
 * @date 2014-7-8 下午5:23:40
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillInvoiceApplyExportFormat extends AbstractExportFormat<BillInvoiceApplyDto> {

	@Override
	public Map format(List<String> fields, BillInvoiceApplyDto obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				if("auditStatus".equals(field)) {
					map.put(field, convertAuditStatus(getFieldVal(field, obj).toString()));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	private String convertAuditStatus(String value) {
		FasAduitStatusEnum []enums = FasAduitStatusEnum.values();
		for(FasAduitStatusEnum e : enums) {
			if(e.getValue().toString().equals(value)) {
				return e.getText();
			} 
		}
		return "";
	}
}
