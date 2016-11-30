package cn.wonhigh.retail.fas.common.exportformat;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CardReturnCheck;
import cn.wonhigh.retail.fas.common.model.PaySaleCheck;

/**
 * 银行卡退款核对需要转换的字段处理类
 * @author 王仕秒
 * @date 2015-10-20
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class CardReturnCheckExportFormat extends AbstractExportFormat<CardReturnCheck> {
	@Override
	public Map format(List<String> fields, CardReturnCheck obj) throws Exception {
		Map map = null;
		try {
			map = super.format(fields, obj);
			for(String field : fields) {
				Object fieldVal = getFieldVal(field, obj);
				if("status".equals(field)) {
					map.put(field, convertStatus(fieldVal));
				}
			}
			return map;
		} catch(Exception e) {
			throw new Exception(e.getMessage(), e);

		}
	}
	
	private Object convertStatus(Object value) {
		if(null!=value){
			if("-1".equals(value.toString())){
				return null;
			}else if("1".equals(value.toString())) {
				return "退款成功";
			}else if("0".equals(value.toString())) {
				return "退款处理中";
			}else{
				return "尚未处理";
			}
		}
		return "尚未处理";
	}
}
