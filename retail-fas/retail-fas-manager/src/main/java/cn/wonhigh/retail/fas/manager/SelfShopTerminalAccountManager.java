package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

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
public interface SelfShopTerminalAccountManager extends BaseCrudManager {
	boolean addListShopTerminalAccount(List<SelfShopTerminalAccount> selfShopTerminalAccountList) throws ManagerException;
	public Map<String,String> validationTerminalAccount(SelfShopTerminalAccount selfShopTerminalAccount) throws ManagerException;
}