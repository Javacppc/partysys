package com.partysys.sysmanage.role.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.sysmanage.privilege.service.PrivilegeService;
import com.partysys.sysmanage.role.service.RoleService;

public class RoleAction extends BaseAction{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	
}
