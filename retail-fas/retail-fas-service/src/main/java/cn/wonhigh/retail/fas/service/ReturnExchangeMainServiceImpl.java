package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.ItemReturnRecord;
import cn.wonhigh.retail.fas.dal.database.ReturnExchangeMainMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:13:46
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
@Service("returnExchangeMainService")
class ReturnExchangeMainServiceImpl extends BaseCrudServiceImpl implements ReturnExchangeMainService {
    @Resource
    private ReturnExchangeMainMapper returnExchangeMainMapper;

    @Override
    public BaseCrudMapper init() {
        return returnExchangeMainMapper;
    }
    

	@Override
	public void modifyOrderForInvoiceNoAndDate(Map<String, Object> params)
			throws ServiceException {
		try{
			  returnExchangeMainMapper.updateOrderForInvoiceNoAndDate(params);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public ItemReturnRecord findItemReturnCount(Map<String, Object> params) throws ServiceException {
		try {
			return returnExchangeMainMapper.findItemReturnCount(params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}


	@Override
	public List<ItemReturnRecord> findItemReturnList(SimplePage page, String orderBy,
			String orderByField, Map<String, Object> params) throws ServiceException {
		try {
			return returnExchangeMainMapper.findItemReturnList(page, orderBy, orderByField, params);
		} catch (Exception e) {
			throw new ServiceException();
		}
	}
}