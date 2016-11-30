package cn.wonhigh.retail.fas.api.dal;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.PriceQuotationList;

/**
 * 请写出类的用途 
 */

public interface PriceQuotationListMapper {
	/**
	 * 根据机构编号、货号查询牌价
	 * @param params
	 * @return
	 */
	PriceQuotationList getPriceQuotationList(@Param("params") Map<String, Object> params);
	
}