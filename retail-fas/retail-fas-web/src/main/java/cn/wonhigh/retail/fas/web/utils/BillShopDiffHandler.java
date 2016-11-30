package cn.wonhigh.retail.fas.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDiffFooterDto;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;
import cn.wonhigh.retail.fas.common.utils.AnnotationReflectUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceDiffManager;

import com.yougou.logistics.base.common.utils.BeanUtilsCommon;

/**
 * 结算单明细页面-结算差异页签的处理类
 * 
 * @author yang.y
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillShopDiffHandler extends AbstactBillDtlHandler {

	@Override
	public List<Map> bulidExportData() throws Exception {
		List<Map> dataMapList = new ArrayList<Map>();
		Map<String, Object> queryParams = new HashMap<String, Object>();
    	queryParams.put("balanceNo", this.getParams().get("balanceNo"));
    	// 查询结算差异数据
    	List<BillShopBalanceDiff> lstDiff = this.getManager().findByBiz(null, queryParams);
        if(lstDiff != null && lstDiff.size() > 0) {
        	List<String> fields = new ArrayList<String>();
        	List<Map> columnsList = this.getColumnsList();
			for(Map map : columnsList) {
				fields.add(map.get("field").toString());
			}
			// 增加合计行
			BillShopBalanceDiff billShopBalanceDiff = new BillShopBalanceDiff();
			billShopBalanceDiff.setDiffTypeName("合计");
			BillShopBalanceDiffFooterDto footerDto = ((BillShopBalanceDiffManager)this.getManager()).getFooterDto(queryParams);
			if(footerDto != null) {
				// 复制属性值
				AnnotationReflectUtil.copyProperties(billShopBalanceDiff, footerDto);
			}
			lstDiff.add(billShopBalanceDiff);
			
        	boolean flag = true;
			ExportFormat formatAnnotation = null;
			AbstractExportFormat<BillShopBalanceDiff> format = null;
        	for(BillShopBalanceDiff vo : lstDiff){
        		Map map = null;
        		if(flag) {
					formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
					flag = false;
				}
				if(formatAnnotation != null) {
					format = (AbstractExportFormat<BillShopBalanceDiff>) formatAnnotation.className().newInstance();
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
