package com.partysys.sysmanage.branch.dao;

import com.partysys.core.dao.BaseDao;
import com.partysys.sysmanage.branch.entity.Branch;

public interface BranchDao extends BaseDao<Branch>{

	Branch findBranchByIdAndName(String branchId, String branchName);

}
