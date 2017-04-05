package com.partysys.sysmanage.privilege.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.sysmanage.privilege.dao.PrivilegeDao;
import com.partysys.sysmanage.privilege.entity.Privilege;
import com.partysys.sysmanage.privilege.service.PrivilegeService;
@Service("privilegeService")
public class PrivilegeServiceImpl extends BaseServiceImpl<Privilege> implements PrivilegeService{
	private PrivilegeDao privilegeDao;
	@Autowired
	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		super.setBaseDao(privilegeDao);
		this.privilegeDao = privilegeDao;
	}
	@Override
	public Integer findIdByName(String name) {
		return privilegeDao.findIdByName(name);
	}
	
}
