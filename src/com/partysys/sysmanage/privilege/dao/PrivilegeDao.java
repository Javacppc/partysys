package com.partysys.sysmanage.privilege.dao;

import com.partysys.core.dao.BaseDao;
import com.partysys.sysmanage.privilege.entity.Privilege;

public interface PrivilegeDao extends BaseDao<Privilege>{
	/**
	 * 通过角色名称找到对应的角色Id
	 * @param name 角色名称
	 * @return 角色Id
	 */
	Integer findIdByName(String name);

}
