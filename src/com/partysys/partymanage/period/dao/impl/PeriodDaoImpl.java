package com.partysys.partymanage.period.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import com.partysys.core.dao.impl.BaseDaoImpl;
import com.partysys.partymanage.period.dao.PeriodDao;
import com.partysys.partymanage.period.entity.Period;

public class PeriodDaoImpl extends BaseDaoImpl<Period> implements PeriodDao{

	@Override
	public Period findPeriodByIdAndDate(String periodId, String date) {
		String hql = "FROM Period p WHERE p.date='" + date + "'";
		//解决编辑时会出现的问题
		if (StringUtils.isNotBlank(periodId)) {
			hql += " AND p.periodId='"+periodId+"'";
		}
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		return (Period) query.uniqueResult();
	}
	
}
