package cn.wonhigh.retail.fas.service;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.SystemUser;

/**
 * 权限业务逻辑处理
 * @author wei.hj
 *
 */
public interface SystemUserService {
	/**
	 * 查询用户
	 * @param userId 用户编号
	 * @return 用户实体
	 */
	SystemUser querySystemUserById(Long userId);

	/**
	 * 根据查询用户查询
	 * @param systemUser 实体,使用编号
	 * @return 用户实体列表
	 */
	List<SystemUser> querySystemUserList(SystemUser systemUser);

	/**
	 * 移除用户
	 * @param userId 用户编号
	 * @return 条数
	 */
	int removeSystemUser(Long userId);

	/**
	 * 插入用户信息
	 * @param record 用户实体  
	 * @return 编号
	 */
	int insertSystemUser(SystemUser record);

	/**
	 * 更新用户信息
	 * @param record 用户实体  
	 * @return 条数
	 */
	int updateSystemUser(SystemUser record);

	/**
	 * 移除用户信息
	 * @param userId 用户编号
	 * @return 条数
	 */
	int removeSystemUserRole(Long userId);

	/**
	 * 增加用户信息
	 * @param userId 用户编号
	 * @param roleId 角色编号
	 * @return 编号
	 */
	int insertSystemUserRole(Long userId, Long roleId);

}
