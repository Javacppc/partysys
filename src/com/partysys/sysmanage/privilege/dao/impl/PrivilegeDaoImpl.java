package com.partysys.sysmanage.privilege.dao.impl;

import org.hibernate.Query;

import com.partysys.core.dao.impl.BaseDaoImpl;
import com.partysys.sysmanage.privilege.dao.PrivilegeDao;
import com.partysys.sysmanage.privilege.entity.Privilege;

public class PrivilegeDaoImpl extends BaseDaoImpl<Privilege> implements PrivilegeDao{

	@Override
	public Integer findIdByName(String name) {
		Query query = getSessionFactory().getCurrentSession().createQuery("select p.privilegeId from Privilege p where p.name=?");
		query.setParameter(0, name);
		return (Integer) query.uniqueResult();
	}

}
