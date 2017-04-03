package com.partysys.sysmanage.privilege.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.sysmanage.privilege.service.PrivilegeService;

public class PrivilegeAction extends BaseAction{
	@Autowired
	private PrivilegeService privilegeService;
	
}
