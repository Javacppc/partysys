package com.partysys.sysmanage.role.dao;

import java.io.Serializable;

import com.partysys.core.dao.BaseDao;
import com.partysys.sysmanage.role.entity.Role;
import com.partysys.sysmanage.role.entity.Roleprivilege;


public interface RoleDao extends BaseDao<Role>{
	/**
	 * 根据权限Id号删除角色所对应的所有权限
	 * @param roleId
	 */
	void deleteRolePrivilegeByRoleId(Serializable roleId);
	/**
	 * 保存角色权限表
	 * @param rp
	 */
	void saveRolePrivilege(Roleprivilege rp);

}
