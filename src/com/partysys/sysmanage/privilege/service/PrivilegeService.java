package com.partysys.sysmanage.privilege.service;

import com.partysys.core.service.BaseService;
import com.partysys.sysmanage.privilege.entity.Privilege;

public interface PrivilegeService extends BaseService<Privilege>{
	/**
	 * 通过角色名称找到对应的角色Id
	 * @param name 角色名称
	 * @return 角色Id
	 */
	Integer findIdByName(String name);

}
