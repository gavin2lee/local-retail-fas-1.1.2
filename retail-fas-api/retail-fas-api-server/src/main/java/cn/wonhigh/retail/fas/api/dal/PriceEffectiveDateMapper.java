package cn.wonhigh.retail.fas.api.dal;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.api.vo.PriceEffectiveDate;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 价格日期
 * @author user
 * @date  2016-03-10 15:23:28
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface PriceEffectiveDateMapper extends BaseCrudMapper {
	/**
	 * 获取采购价生效日期
	 * @param paramCode
	 * @return
	 */
	PriceEffectiveDate selectPurchaseEffectiveDate(@Param("params")Map<String, Object> params);
}