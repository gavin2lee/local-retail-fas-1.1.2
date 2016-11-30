package cn.wonhigh.retail.fas.web.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceCateSumFooterDto;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.utils.AnnotationReflectUtil;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceCateSumManager;

import com.yougou.logistics.base.common.utils.BeanUtilsCommon;

/**
 * 结算单明细页面-按大类显示数据时的处理类
 * 
 * @author yang.y
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillDtlCategoryHandler extends AbstactBillDtlHandler {

	@Override
	public List<Map> bulidExportData() throws Exception {
		List<Map> dataMapList = new ArrayList<Map>();
		BillShopBalanceCateSumManager billShopBalanceCateSumManager = (BillShopBalanceCateSumManager)this.getManager();
		Map<String, Object> queryParams = new HashMap<String, Object>();
    	queryParams.put("balanceNo", this.getParams().get("balanceNo"));
		List<BillShopBalanceCateSum> lstCateSum = billShopBalanceCateSumManager.findByBiz(null, queryParams);
        if(lstCateSum != null && lstCateSum.size() > 0) {
        	List<String> fields = new ArrayList<String>();
        	List<Map> columnsList = this.getColumnsList();
			for(Map map : columnsList) {
				fields.add(map.get("field").toString());
			}
			
			// 增加合计行
			BillShopBalanceCateSumFooterDto footerDto = billShopBalanceCateSumManager.getFootDto(queryParams);
			BillShopBalanceCateSum billShopBalanceCateSum = new BillShopBalanceCateSum();
			billShopBalanceCateSum.setCategoryName("合计");
			// 复制属性
			AnnotationReflectUtil.copyProperties(billShopBalanceCateSum, footerDto);
			List<BillShopBalance> lstBillShopBalance = this.getBillShopBalanceManager().findByBiz(null, queryParams);
			//商场开票金额
//			BigDecimal mallBillingAmount = BigDecimal.ZERO;
//			if(lstBillShopBalance != null && lstBillShopBalance.size() > 0 
//					&& lstBillShopBalance.get(0).getMallBillingAmount().compareTo(BigDecimal.ZERO) != 0) {
//				mallBillingAmount = lstBillShopBalance.get(0).getMallBillingAmount();
//				// 设置页脚开票金额的合计
//				billShopBalanceCateSum.setBillingAmount(mallBillingAmount);
//				// 设置页脚开票金额的合计
//				BigDecimal amount = BigDecimal.ZERO;
//				for(int i = 0; i < lstCateSum.size(); i++) {
//					BillShopBalanceCateSum bill = lstCateSum.get(i);
//					if(i < lstCateSum.size() - 1) {
//						BigDecimal billAmount = BigDecimalUtil.format(BigDecimalUtil.multi(mallBillingAmount, 
//								BigDecimalUtil.division(bill.getSaleAmount(), billShopBalanceCateSum.getSaleAmount())),
//								BigDecimalUtil.POINT_2_PATTERN);
//						amount = BigDecimalUtil.format(BigDecimalUtil.add(amount, billAmount), BigDecimalUtil.POINT_2_PATTERN);
//						bill.setBillingAmount(billAmount);
//					} else {
//						bill.setBillingAmount(BigDecimalUtil.subtract(mallBillingAmount, amount));
//					}
//				}
//			}
			// 将合计行对象添加到list中
			lstCateSum.add(billShopBalanceCateSum);
			
        	boolean flag = true;
			ExportFormat formatAnnotation = null;
			AbstractExportFormat<BillShopBalanceCateSum> format = null;
        	for(BillShopBalanceCateSum vo : lstCateSum){
        		Map map = null;
        		if(flag) {
					formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
					flag = false;
				}
				if(formatAnnotation != null) {
					format = (AbstractExportFormat<BillShopBalanceCateSum>) formatAnnotation.className().newInstance();
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
