package com.partysys.sysmanage.party.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.partysys.core.dao.impl.BaseDaoImpl;
import com.partysys.sysmanage.party.dao.PartymemberDao;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.entity.Rolepartymember;

public class PartymemberDaoImpl extends BaseDaoImpl<Partymember> implements PartymemberDao{

	@Override
	public void saveUserRole(Rolepartymember rolepartymember) {
		Session s = getSessionFactory().getCurrentSession();
		s.save(rolepartymember);
	}

	@Override
	public List<Partymember> findPartymemberByIdAndNumber(String id, String number) {
		String hql = "FROM Partymember p WHERE p.number='"+number+"'";
		Query query = null;
		//在编辑页面中需要排除这样一种情况：排除自己的id号防止系统以为自己取了相同的账户
		if (StringUtils.isNoneBlank(id)) {
			hql += " AND p.id!='" + id + "'";
		}
		query = getSessionFactory().getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<Rolepartymember> findUserRoleByUserId(String id) {
		//根据用户Id查找对应的用户角色表
		//BUG HAPPENED In 2017_4_11
		Query query = getSessionFactory().getCurrentSession().createQuery("FROM Rolepartymember p WHERE p.id.userId=?");
		query.setParameter(0, id);
		return query.list();
	}

}
