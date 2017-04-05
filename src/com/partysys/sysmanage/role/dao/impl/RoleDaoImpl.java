package com.partysys.sysmanage.role.dao.impl;

import java.io.Serializable;

import org.hibernate.Query;

import com.partysys.core.dao.impl.BaseDaoImpl;
import com.partysys.sysmanage.role.dao.RoleDao;
import com.partysys.sysmanage.role.entity.Role;
import com.partysys.sysmanage.role.entity.Roleprivilege;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{

	@Override
	public void deleteRolePrivilegeByRoleId(Serializable roleId) {
		Query query = getSessionFactory().getCurrentSession().createQuery("DELETE FROM Roleprivilege WHERE role.roleId=?");
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

	@Override
	public void saveRolePrivilege(Roleprivilege rp) {
		getSessionFactory().getCurrentSession().save(rp);
	}

}
