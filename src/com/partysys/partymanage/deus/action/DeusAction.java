package com.partysys.partymanage.deus.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.partymanage.deus.service.DeusService;
import com.partysys.partymanage.period.service.PeriodService;
/**
 * 党费管理Action(针对普通管理员)
 * @author 朱可凡
 *
 */
public class DeusAction extends BaseAction{
	@Autowired
	private DeusService deusService;
	@Autowired
	private PeriodService periodService;
	
	
	
	public DeusService getDeusService() {
		return deusService;
	}

	public void setDeusService(DeusService deusService) {
		this.deusService = deusService;
	}

	public PeriodService getPeriodService() {
		return periodService;
	}

	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
	}
	
}
