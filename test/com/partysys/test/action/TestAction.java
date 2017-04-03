package com.partysys.test.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.test.service.TestService;

public class TestAction extends BaseAction{
	@Autowired
	private TestService testService;
	
	@Override
	public String execute() throws Exception {
		testService.say();
		return SUCCESS;
	}
}
