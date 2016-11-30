package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.dal.database.SelfShopTerminalAccountMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-17 16:59:10
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
@Service("selfShopTerminalAccountService")
class SelfShopTerminalAccountServiceImpl extends BaseCrudServiceImpl implements SelfShopTerminalAccountService {
    @Resource
    private SelfShopTerminalAccountMapper selfShopTerminalAccountMapper;

    @Override
    public BaseCrudMapper init() {
        return selfShopTerminalAccountMapper;
    }

	@Override
	public List<SelfShopTerminalAccount> queryListByShopNos(
			Map<String, Object> maps) throws ServiceException {
		return selfShopTerminalAccountMapper.queryListByShopNos(maps);
	}

}