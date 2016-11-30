package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yougou.logistics.base.common.exception.RpcException;

import cn.wonhigh.retail.fas.common.model.BillShopBalance;

/**
 * 商场结算单导出时转换model对象的类
 * @author 杨勇
 * @date 2015-1-19 下午5:23:40
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillShopBalanceExportFormat extends AbstractExportFormat<BillShopBalance> {

	@Override
	public Map format(List<String> fields, BillShopBalance obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				Object fieldVal = getFieldVal(field, obj);
				if(fieldVal == null) {
					continue;
				}
				if("status".equals(field)) {
					map.put(field, convertStatus(fieldVal.toString(), obj));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	private String convertStatus(String value, BillShopBalance obj) {
		if("1".equals(value)) {
	    	return "制单";
	    }
	    if("2".equals(value)) {
	    	return "确认";
	    }
	    if("3".equals(value)) {
	    	return "作废";
	    }
	    // 区分是已生成开票申请还是已生成发票登记
	    if(StringUtils.isNotEmpty(obj.getInvoiceApplyNo()) && StringUtils.isEmpty(obj.getInvoiceRegisterNo())) {
	    	return "已开票申请";
	    }
	    if(StringUtils.isNotEmpty(obj.getInvoiceRegisterNo())) {
	    	return "已开票";
	    }
	    return "制单";
	}
}
