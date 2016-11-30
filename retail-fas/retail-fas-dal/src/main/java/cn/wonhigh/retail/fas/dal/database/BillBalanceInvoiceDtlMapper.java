package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceDtl;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-05 15:30:06
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
public interface BillBalanceInvoiceDtlMapper extends BaseCrudMapper {
	
	void deleteInvoiceDtl(String billNo);
	
	/**
	 * 根据参数对开票明细进行分组
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceDtl> selectByGroupByParams(@Param("params")Map<String,Object> params);
	
	/**
	 * 根据参数对开票明细进行汇总，但是不会汇总实际扣率和合同扣率
	 * @param params
	 * @return
	 */
	public List<BillBalanceInvoiceDtl> selectByGroupByParamExport(@Param("params")Map<String,Object> params); 
}