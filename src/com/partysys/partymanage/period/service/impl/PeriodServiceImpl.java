package com.partysys.partymanage.period.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partysys.core.service.impl.BaseServiceImpl;
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
}
