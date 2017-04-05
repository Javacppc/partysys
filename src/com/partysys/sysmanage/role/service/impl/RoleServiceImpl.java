package com.partysys.sysmanage.role.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.sysmanage.role.dao.RoleDao;
import com.partysys.sysmanage.role.entity.Role;
import com.partysys.sysmanage.role.entity.Roleprivilege;
import com.partysys.sysmanage.role.service.RoleService;
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	private RoleDao roleDao;
	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
		this.roleDao = roleDao;
	}
	@Override
	public void update(Role entity) {
		//先删除原有的角色所对应的权限
		roleDao.deleteRolePrivilegeByRoleId(entity.getRoleId());
		//再添加修改过后的角色和所对应的权限信息
		roleDao.update(entity);
	}
	@Override
	public void delete(Role entity) {
		//先删除角色所对应的所有权限
		roleDao.deleteRolePrivilegeByRoleId(entity.getRoleId());
		//再删除角色
		roleDao.delete(entity);
	}
	
	@Override
	public void delete(Serializable id) {
		//先删除角色所对应的所有权限
		roleDao.deleteRolePrivilegeByRoleId(id);
		//再删除角色
		roleDao.delete(id);
	}
	@Override
	public void saveRolePrivilege(Roleprivilege rp) {
		roleDao.saveRolePrivilege(rp);
	}
}
