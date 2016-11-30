package cn.wonhigh.retail.fas.web.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.manager.BillShopBalanceManager;

import com.yougou.logistics.base.common.utils.BeanUtilsCommon;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 结算单明细页面-按收款方式显示数据时的处理类
 * 
 * @author yang.y
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillDtlPaymentMethodHandler extends AbstactBillDtlHandler {

	@Override
	public List<Map> bulidExportData() throws Exception {
		List<Map> dataMapList = new ArrayList<Map>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
    	queryParams.put("balanceNo", this.getParams().get("balanceNo"));
    	BillShopBalanceManager billShopBalanceManager = (BillShopBalanceManager)this.getManager();
//    	int total = billShopBalanceManager.findCount(queryParams);
    	// 查询按活动显示的数据
    	SimplePage page = new SimplePage(1, Integer.MAX_VALUE, 1);
    	List<BillShopBalanceDaysaleSum> list = billShopBalanceManager.findPaymentMethodSum(page, null, null, queryParams);
        if(list != null && list.size() > 0) {
        	List<String> fields = new ArrayList<String>();
        	List<Map> columnsList = this.getColumnsList();
			for(Map map : columnsList) {
				fields.add(map.get("field").toString());
			}
			
			// 增加合计行
			BigDecimal sumAmount = billShopBalanceManager.getSumAmount(queryParams);
			BillShopBalanceDaysaleSum billShopBalanceDaysaleSum = new BillShopBalanceDaysaleSum();
			billShopBalanceDaysaleSum.setPayName("合计");
			billShopBalanceDaysaleSum.setAmount(sumAmount);
			list.add(billShopBalanceDaysaleSum);
			
        	boolean flag = true;
			ExportFormat formatAnnotation = null;
			AbstractExportFormat<BillShopBalanceDaysaleSum> format = null;
        	for(BillShopBalanceDaysaleSum vo : list){
        		Map map = null;
        		if(flag) {
					formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
					flag = false;
				}
				if(formatAnnotation != null) {
					format = (AbstractExportFormat<BillShopBalanceDaysaleSum>) formatAnnotation.className().newInstance();
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
