package com.partysys.partymanage.period.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.partymanage.period.entity.Period;
import com.partysys.partymanage.period.service.PeriodService;
/**
 * 汇总党费Action
 * @author 朱可凡
 *
 */
public class PeriodAction extends BaseAction{
	@Autowired
	private PeriodService periodService;

	private Period period;
	/**
	 * 显示列表页面
	 * @return
	 * @throws Exception
	 */
	public String listUI() throws Exception{
		
		return "listUI";
	}
	
	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public PeriodService getPeriodService() {
		return periodService;
	}

	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
	}
	
	
}
