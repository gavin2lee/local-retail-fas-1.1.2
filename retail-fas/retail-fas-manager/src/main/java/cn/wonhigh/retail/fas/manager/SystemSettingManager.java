package cn.wonhigh.retail.fas.manager;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.SystemUser;

/**
 * 系统设置功能Manager管理类
 * @author wei.hj
 *
 */
public interface SystemSettingManager {
	/**
	 * 查询用户列表
	 * @param systemUser 用户实体
	 * @return 用户实体列表
	 */
	public List<SystemUser> querySystemUserList(SystemUser systemUser);

	/**
	 * 查询用户信息
	 * @param userid 编号
	 * @return 用户实体
	 */
	public SystemUser querySystemUserById(Long userid);
}
