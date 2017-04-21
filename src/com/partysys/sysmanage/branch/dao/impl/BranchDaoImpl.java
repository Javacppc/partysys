package com.partysys.sysmanage.branch.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.dao.impl.BaseDaoImpl;
import com.partysys.sysmanage.branch.dao.BranchDao;
import com.partysys.sysmanage.branch.entity.Branch;

public class BranchDaoImpl extends BaseDaoImpl<Branch> implements BranchDao{

	@Override
	public Branch findBranchByIdAndName(String branchId, String branchName) {
		String hql = "FROM Branch b WHERE b.branchName='" + branchName + "'";
		if (StringUtils.isNotBlank(branchId)) {
			hql += " AND b.branchId='" + branchId + "'";
		}
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		return (Branch) query.uniqueResult();
	}
	
}
