package com.partysys.sysmanage.branch.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.sysmanage.branch.service.BranchService;

public class BranchAction extends BaseAction{
	@Autowired
	private BranchService branchService;
	
}
