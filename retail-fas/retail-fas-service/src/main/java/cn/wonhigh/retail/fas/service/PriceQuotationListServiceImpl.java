package cn.wonhigh.retail.fas.service;

import java.util.HashMap;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.PriceQuotationList;
import cn.wonhigh.retail.fas.dal.database.PriceQuotationListMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-10-27 16:11:04
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
@Service("priceQuotationListService")
class PriceQuotationListServiceImpl extends BaseCrudServiceImpl implements PriceQuotationListService {
    @Resource
    private PriceQuotationListMapper priceQuotationListMapper;

    @Override
    public BaseCrudMapper init() {
        return priceQuotationListMapper;
    }

	@Override
	public PriceQuotationList getPriceQuotationList(String organId,
			String itemNo) throws ServiceException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("organId", organId);
		params.put("itemNo", itemNo);
		return priceQuotationListMapper.getPriceQuotationList(params);
	}
}