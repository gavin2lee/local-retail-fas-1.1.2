package cn.wonhigh.retail.fas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.SystemUser;
import cn.wonhigh.retail.fas.dal.database.SystemUserMapper;

import com.yougou.logistics.base.common.annotation.ChangeDataSource;
import com.yougou.logistics.base.common.exception.ServiceException;
/**
 * 
 * 用户管理实现类
 * @author wei.b
 * @date Jun 19, 2014 1:38:27 PM
 * @version 0.1.0 
 * @copyright yougou.com
 */
@Service("systemUserService")
@ChangeDataSource("oracleDataSource")
public class SystemUserServiceImpl implements SystemUserService {

	@Autowired
	private SystemUserMapper systemUserMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	@ChangeDataSource("mysqlDataSource")
	public SystemUser querySystemUserById(Long userid) {
		return systemUserMapper.selectByPrimaryKey(userid);
	}

	@Override
	public List<SystemUser> querySystemUserList(SystemUser systemUser) {
		return systemUserMapper.querySystemUserList(systemUser);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int removeSystemUser(Long userid) {
		return systemUserMapper.removeSystemUser(userid);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int insertSystemUser(SystemUser record) {
		return systemUserMapper.insertSystemUser(record);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int updateSystemUser(SystemUser record) {
		return systemUserMapper.updateSystemUser(record);
	}

	@Override
	public int removeSystemUserRole(Long userId) {
		return systemUserMapper.removeSystemUserRole(userId);
	}

	@Override
	public int insertSystemUserRole(Long userId, Long roleId) {
		return systemUserMapper.insertSystemUserRole(userId, roleId);
	}

}
