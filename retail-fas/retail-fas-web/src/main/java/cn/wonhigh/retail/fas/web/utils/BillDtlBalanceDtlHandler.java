package cn.wonhigh.retail.fas.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.ItemSaleDtlDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderAndReturnExMainDtlDto;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.manager.BillShopSaleOrderManager;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderMainDto;

import com.yougou.logistics.base.common.utils.BeanUtilsCommon;

/**
 * 结算单明细页面-按明细显示数据时的处理类
 * 
 * @author yang.y
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BillDtlBalanceDtlHandler extends AbstactBillDtlHandler {

	/**
	 * 构建导出数据
	 * @return 导出数据集合
	 * @throws Exception 异常
	 */
	@Override
	public List<Map> bulidExportData() throws Exception {
		List<Map> dataMapList = new ArrayList<Map>();
		BillShopSaleOrderManager billShopSaleOrderManager = (BillShopSaleOrderManager)this.getManager();
		
		Map<String, Object> params = this.getParams();
		if(params != null) {
			params.put("pageSize", Integer.MAX_VALUE);
			params.put("pageNumber", 1);
		}
		List<ItemSaleDtlDto> itemSaleDtlDtos = null;
		List<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainList = null;
		POSOcPagingDto<POSOrderAndReturnExMainDtlDto> orderAndReturnExMainDtlDtoList = 
				billShopSaleOrderManager.queryShopSaleDetailListRemote(params);
		// 查询销售单明细
		if(orderAndReturnExMainDtlDtoList != null){
			orderAndReturnExMainList = orderAndReturnExMainDtlDtoList.getResult() == null ? 
					new ArrayList<POSOrderAndReturnExMainDtlDto>() : orderAndReturnExMainDtlDtoList.getResult() ;
			itemSaleDtlDtos = billShopSaleOrderManager.converDateToViewDataItem(orderAndReturnExMainList);
		 }
		
        if(itemSaleDtlDtos != null && itemSaleDtlDtos.size() > 0) {
        	List<String> fields = new ArrayList<String>();
        	List<Map> columnsList = this.getColumnsList();
			for(Map map : columnsList) {
				fields.add(map.get("field").toString());
			}
			// 增加合计行
			OcOrderMainDto ocOrderMainDto = billShopSaleOrderManager.findList4SumOcOrderByParameter(params);
			ItemSaleDtlDto footerDto = new ItemSaleDtlDto();
			footerDto.setOrderNo("合计");
			footerDto.setQty(ocOrderMainDto.getQty());
			footerDto.setSettleAmount(ocOrderMainDto.getSettleAmount());
			footerDto.setAllAmount(ocOrderMainDto.getAllAmount());
			footerDto.setAmount(ocOrderMainDto.getAllAmount());
			footerDto.setSettlePrice(ocOrderMainDto.getSettleAmount());
			itemSaleDtlDtos.add(footerDto);
			
        	boolean flag = true;
			ExportFormat formatAnnotation = null;
			AbstractExportFormat<ItemSaleDtlDto> format = null;
        	for(ItemSaleDtlDto vo : itemSaleDtlDtos){
        		Map map = null;
        		if(flag) {
					formatAnnotation = vo.getClass().getAnnotation(ExportFormat.class);
					flag = false;
				}
				if(formatAnnotation != null) {
					format = (AbstractExportFormat<ItemSaleDtlDto>) formatAnnotation.className().newInstance();
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
