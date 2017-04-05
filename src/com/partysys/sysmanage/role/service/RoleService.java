package com.partysys.sysmanage.role.service;

import com.partysys.core.service.BaseService;
import com.partysys.sysmanage.role.entity.Role;
import com.partysys.sysmanage.role.entity.Roleprivilege;

public interface RoleService extends BaseService<Role>{
	/**
	 * 保存角色权限表
	 * @param rp
	 */
	void saveRolePrivilege(Roleprivilege rp);
	
}
