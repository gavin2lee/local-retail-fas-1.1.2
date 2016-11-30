package cn.wonhigh.retail.fas.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDeductFooterDto;
import cn.wonhigh.retail.fas.common.enums.ShopMallEnums;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.utils.AnnotationReflectUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceDeductManager;

import com.yougou.logistics.base.common.utils.BeanUtilsCommon;

/**
 * 结算单明细页面-票前费用页签的处理类
 * 
 * @author yang.y
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillShopDeductHandler extends AbstactBillDtlHandler {

	@Override
	public List<Map> bulidExportData() throws Exception {
		List<Map> dataMapList = new ArrayList<Map>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
    	queryParams.put("balanceNo", this.getParams().get("balanceNo"));
    	queryParams.put("costDeductType", ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId());
    	// 查询票前费用数据
    	List<BillShopBalanceDeduct> lstDeduct = this.getManager().findByBiz(null, queryParams);
        if(lstDeduct != null && lstDeduct.size() > 0) {
        	List<String> fields = new ArrayList<String>();
        	List<Map> columnsList = this.getColumnsList();
			for(Map map : columnsList) {
				fields.add(map.get("field").toString());
			}
			// 增加合计项
			BillShopBalanceDeduct billShopBalanceDeduct = new BillShopBalanceDeduct();
			billShopBalanceDeduct.setMonth("合计");
			BillShopBalanceDeductFooterDto footerDto = ((BillShopBalanceDeductManager)this.getManager()).getFooterDto(queryParams);
			if(footerDto != null) {
				// 复制属性值
				AnnotationReflectUtil.copyProperties(billShopBalanceDeduct, footerDto);
			}
			lstDeduct.add(billShopBalanceDeduct);
			
        	boolean flag = true;
			ExportFormat formatAnnotation = null;
			AbstractExportFormat<BillShopBalanceDeduct> format = null;
        	for(BillShopBalanceDeduct vo : lstDeduct){
				Map map = null;
        		if(flag) {
					formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
					flag = false;
				}
				if(formatAnnotation != null) {
					format = (AbstractExportFormat<BillShopBalanceDeduct>) formatAnnotation.className().newInstance();
					map = format.format(fields, vo);
				} else {
					map = new HashMap();
					BeanUtilsCommon.object2MapWithoutNull(vo,map);
				}
                dataMapList.add(map);
            }
         }
        return dataMapList;
	}
}
