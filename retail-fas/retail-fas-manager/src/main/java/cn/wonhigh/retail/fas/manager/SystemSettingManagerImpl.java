package cn.wonhigh.retail.fas.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.SystemUser;
import cn.wonhigh.retail.fas.service.SystemUserService;


/**
 * 系统设置功能Manager管理类
 * @author wei.hj
 *
 */
@Service("systemSettingManager")
public class SystemSettingManagerImpl implements SystemSettingManager {

	@Autowired
	private SystemUserService systemUserService;

	@Override
	public List<SystemUser> querySystemUserList(SystemUser systemUser) {
		return this.systemUserService.querySystemUserList(systemUser);
	}

	@Override
	public SystemUser querySystemUserById(Long userid) {
		// TODO Auto-generated method stub
		return systemUserService.querySystemUserById(userid);
	}

}
