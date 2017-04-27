package com.partysys.partymanage.period.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.partymanage.deus.entity.Deus;
import com.partysys.partymanage.period.dao.PeriodDao;
import com.partysys.partymanage.period.entity.Period;
import com.partysys.partymanage.period.service.PeriodService;
@Service("periodService")
public class PeriodServiceImpl extends BaseServiceImpl<Period> implements PeriodService{
	
	private PeriodDao periodDao;
	@Autowired
	public void setPeriodDao(PeriodDao periodDao) {
		super.setBaseDao(periodDao);
		this.periodDao = periodDao;
	}
	@Override
	public Period findPeriodByIdAndDate(String periodId, String date) {
		return periodDao.findPeriodByIdAndDate(periodId, date);
	}
	
	@Override
	public void delete(Serializable id) {
		Period period = findById(id);
		//删除所有党费记录
		for (Deus d : period.getDeus()) {
			periodDao.delete(d.getDeusId());
		}
		periodDao.delete(id);
	}
}
