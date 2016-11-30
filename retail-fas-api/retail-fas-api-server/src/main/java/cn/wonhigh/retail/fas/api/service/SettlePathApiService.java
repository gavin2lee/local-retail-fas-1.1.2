package cn.wonhigh.retail.fas.api.service;

import cn.wonhigh.retail.fas.api.model.BillSplitSettlePathDtlModel;
import cn.wonhigh.retail.fas.common.model.SettlePathDtlQueryVo;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface SettlePathApiService {

	BillSplitSettlePathDtlModel querySettlePathDtl(SettlePathDtlQueryVo vo);

	Integer selectRefBizType(String refBillNo);
}