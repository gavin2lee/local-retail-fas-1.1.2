package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.CardReturnCheck;
import cn.wonhigh.retail.fas.dal.database.CardReturnCheckMapper;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-19 20:33:19
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
@Service("cardReturnCheckService")
class CardReturnCheckServiceImpl extends BaseCrudServiceImpl implements CardReturnCheckService {
    @Resource
    private CardReturnCheckMapper cardReturnCheckMapper;

    @Override
    public BaseCrudMapper init() {
        return cardReturnCheckMapper;
    }

	@Override
	public CardReturnCheck findCardReturnCheckCount(Map<String, Object> params)
			throws ManagerException {
		return cardReturnCheckMapper.findCardReturnCheckCount(params);
	}

	@Override
	public List<CardReturnCheck> findCardReturnCheckList(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params)
			throws ManagerException {
		return cardReturnCheckMapper.findCardReturnCheckList(page, orderByField, orderBy, params);
	}

	@Transactional(rollbackFor=ServiceException.class)
	public void updatePoundage(CardReturnCheck cardReturnCheck) {
		cardReturnCheckMapper.updatePoundage(cardReturnCheck);
	}
}