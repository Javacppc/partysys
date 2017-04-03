package com.partysys.sysmanage.party.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.sysmanage.party.service.PartymemberService;

public class PartymemberAction extends BaseAction{
	
	@Autowired 
	private PartymemberService partymemberService;
	
}
