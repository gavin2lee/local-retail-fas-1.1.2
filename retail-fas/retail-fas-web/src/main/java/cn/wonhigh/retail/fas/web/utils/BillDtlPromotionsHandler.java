package cn.wonhigh.retail.fas.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;
import cn.wonhigh.retail.fas.manager.BillShopBalanceProSumManager;

import com.yougou.logistics.base.common.utils.BeanUtilsCommon;

/**
 * 结算单明细页面-按活动显示数据时的处理类
 * 
 * @author yang.y
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillDtlPromotionsHandler extends AbstactBillDtlHandler {

	@Override
	public List<Map> bulidExportData() throws Exception {
		List<Map> dataMapList = new ArrayList<Map>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
    	queryParams.put("balanceNo", this.getParams().get("balanceNo"));
    	// 查询按活动显示的数据
    	List<BillShopBalanceProSum> list = this.getManager().findByBiz(null, queryParams);
        if(list != null && list.size() > 0) {
        	List<String> fields = new ArrayList<String>();
        	List<Map> columnsList = this.getColumnsList();
			for(Map map : columnsList) {
				fields.add(map.get("field").toString());
			}
			
			// 增加合计行
			List<BillShopBalanceProSum> footerDto = ((BillShopBalanceProSumManager)this.getManager()).getSumAmount(queryParams);
			BillShopBalanceProSum billShopBalanceProSum = new BillShopBalanceProSum();
			billShopBalanceProSum.setProNo("合计");
				if(footerDto != null && footerDto.size() > 0) {
					for(BillShopBalanceProSum shopBalanceProSum : footerDto) {
						billShopBalanceProSum.setDeductAmount(shopBalanceProSum.getDeductAmount());
						billShopBalanceProSum.setSaleAmount(shopBalanceProSum.getSaleAmount());
					}
			}
			list.add(billShopBalanceProSum);
			
        	boolean flag = true;
			ExportFormat formatAnnotation = null;
			AbstractExportFormat<BillShopBalanceProSum> format = null;
        	for(BillShopBalanceProSum vo : list){
        		Map map = null;
        		if(flag) {
					formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
					flag = false;
				}
				if(formatAnnotation != null) {
					format = (AbstractExportFormat<BillShopBalanceProSum>) formatAnnotation.className().newInstance();
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
