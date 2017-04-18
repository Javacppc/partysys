package com.partysys.sysmanage.branch.service;

import com.partysys.core.service.BaseService;
import com.partysys.sysmanage.branch.entity.Branch;

public interface BranchService extends BaseService<Branch>{

	int findCount();

}
