package com.partysys.partymanage.period.service;

import com.partysys.core.service.BaseService;
import com.partysys.partymanage.period.entity.Period;

public interface PeriodService extends BaseService<Period>{

	Period findPeriodByIdAndDate(String periodId, String date);
	
}
