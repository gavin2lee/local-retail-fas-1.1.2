package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.dal.database.WholesaleCustomerRemainingDtlMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-07-06 12:15:59
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
@Service("wholesaleCustomerRemainingDtlService")
class WholesaleCustomerRemainingDtlServiceImpl extends BaseServiceImpl implements WholesaleCustomerRemainingDtlService {
    @Resource
    private WholesaleCustomerRemainingDtlMapper wholesaleCustomerRemainingDtlMapper;

    @Override
    public BaseCrudMapper init() {
        return wholesaleCustomerRemainingDtlMapper;
    }

	@Override
	public WholesaleCustomerRemainingDtl selectCustomerRemainingTotalByDate(
			Map<String, Object> params) {
		return wholesaleCustomerRemainingDtlMapper.selectCustomerRemainingTotalByDate(params);
	}
	
	@Override
	public WholesaleCustomerRemainingDtl setDtlPosition(WholesaleCustomerRemainingDtl remainingDtl) throws NumberFormatException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mainId", remainingDtl.getMainId());
		params.put("createTime", remainingDtl.getCreateTime());
		Random random=new Random();
		int num = random.nextInt(1000);
		params.put("num", num);
		List<WholesaleCustomerRemainingDtl> dtlList = wholesaleCustomerRemainingDtlMapper.selectByParams(null, params);
		if (dtlList != null && dtlList.size() > 0 && StringUtils.isNotBlank(Long.toString(dtlList.get(0).getPosition()))) {
			Long position = dtlList.get(0).getPosition();
			remainingDtl.setPosition(position + 1);
		} else {
			remainingDtl.setPosition(Long.parseLong(DateUtil.getCurrentDateTimeToStr() + "0001"));
		}
		return remainingDtl;
	}

	@Override
	public List<WholesaleCustomerRemainingDtl> selectDtlByPage(SimplePage page, Map<String, Object> params)
			throws ServiceException {
		return wholesaleCustomerRemainingDtlMapper.selectDtlByPage(page, params);
	}

	@Override
	public int batchInsertRemainingDtl(List<WholesaleCustomerRemainingDtl> list) throws ServiceException {
		// TODO Auto-generated method stub
		return wholesaleCustomerRemainingDtlMapper.batchInsertRemainingDtl(list);
	}
}