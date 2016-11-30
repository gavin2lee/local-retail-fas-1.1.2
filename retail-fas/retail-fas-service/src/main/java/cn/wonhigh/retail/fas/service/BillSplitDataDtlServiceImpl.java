package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSplitDataDtl;
import cn.wonhigh.retail.fas.dal.database.BillSplitDataDtlMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 15:58:32
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
@Service("billSplitDataDtlService")
class BillSplitDataDtlServiceImpl extends BaseCrudServiceImpl implements BillSplitDataDtlService {
    
	@Resource
    private BillSplitDataDtlMapper billSplitDataDtlMapper;

    @Override 
    public BaseCrudMapper init() {
        return billSplitDataDtlMapper;
    }

	@Override
	public List<BillSplitDataDtl> selectBillAsn(BillSplitDataDtl modelType, Map<String, Object> params) {
		return billSplitDataDtlMapper.selectBillAsn(modelType, params);
	}

	@Override
	public List<BillSplitDataDtl> selectBillReturn(BillSplitDataDtl modelType, Map<String, Object> params) {
		return billSplitDataDtlMapper.selectBillReturn(modelType, params);
	}

	@Override
	public int updateSplitFlag(Map<String, Object> params) {
		return billSplitDataDtlMapper.updateSplitFlag(params);
	}
}