package com.partysys.sysmanage.info.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.sysmanage.info.service.InfoService;

public class InfoAction extends BaseAction{
	
	@Autowired
	private InfoService infoService;
	
}
