package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceDtl;

import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-07 16:30:58
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
public interface BillBalanceInvoiceDtlService extends BaseCrudService {
	
	void deleteInvoiceDtl(String billNo);
	
	/**
	 * 根据参数对开票明细进行分组
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceDtl> selectByGroupByParams(Map<String,Object> params);
	
	/**
	 * 根据参数对开票明细进行汇总，但是不会汇总实际扣率和合同扣率
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceDtl> selectByGroupByParamExport(Map<String,Object> params);
}