package cn.wonhigh.retail.fas.dal.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.SystemUser;

public interface SystemUserMapper {
	SystemUser selectByPrimaryKey(Long userid);
	List<SystemUser> querySystemUserList(SystemUser systemUser);
    int removeSystemUser(Long userid);
    int insertSystemUser(SystemUser record);
    int updateSystemUser(SystemUser record);
    
    int removeSystemUserRole(Long userId);

    int insertSystemUserRole(@Param("userId") Long userId,@Param("roleId") Long roleId);

}