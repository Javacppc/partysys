package com.partysys.sysmanage.branch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.sysmanage.branch.dao.BranchDao;
import com.partysys.sysmanage.branch.entity.Branch;
import com.partysys.sysmanage.branch.service.BranchService;
@Service("branchService")
public class BranchServiceImpl extends BaseServiceImpl<Branch> implements BranchService{
	
	
	private BranchDao branchDao;
	
	@Autowired
	public void setBranchDao(BranchDao branchDao) {
		super.setBaseDao(branchDao);
		this.branchDao = branchDao;
	}

	@Override
	public int findCount() {
		return (int) branchDao.findCount();
	}

	@Override
	public Branch findBranchByIdAndName(String branchId, String branchName) {
		return branchDao.findBranchByIdAndName(branchId, branchName);
	}
	
}
