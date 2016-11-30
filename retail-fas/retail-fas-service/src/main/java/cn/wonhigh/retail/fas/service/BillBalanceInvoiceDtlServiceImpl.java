package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceDtl;
import cn.wonhigh.retail.fas.dal.database.BillBalanceInvoiceDtlMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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
@Service("billBalanceInvoiceDtlService")
class BillBalanceInvoiceDtlServiceImpl extends BaseCrudServiceImpl implements BillBalanceInvoiceDtlService {
    @Resource
    private BillBalanceInvoiceDtlMapper billBalanceInvoiceDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return billBalanceInvoiceDtlMapper;
    }

	@Override
	public void deleteInvoiceDtl(String billNo) {
		billBalanceInvoiceDtlMapper.deleteInvoiceDtl(billNo);
	}

	@Override
	public List<BillBalanceInvoiceDtl> selectByGroupByParams(Map<String, Object> params) {
		return billBalanceInvoiceDtlMapper.selectByGroupByParams(params);
	}

	@Override
	public List<BillBalanceInvoiceDtl> selectByGroupByParamExport(
			Map<String, Object> params) {
		return billBalanceInvoiceDtlMapper.selectByGroupByParamExport(params);
	}
}