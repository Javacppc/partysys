package com.partysys.sysmanage.party.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.sysmanage.branch.dao.BranchDao;
import com.partysys.sysmanage.branch.entity.Branch;
import com.partysys.sysmanage.party.dao.PartymemberDao;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.entity.Rolepartymember;
import com.partysys.sysmanage.party.service.PartymemberService;
import com.partysys.sysmanage.role.entity.Role;
@Service("partymemberService")
public class PartymemberServiceImpl extends BaseServiceImpl<Partymember> implements PartymemberService{
	private PartymemberDao partymemberDao;
	@Autowired
	public void setPartymemberDao(PartymemberDao partymemberDao) {
		super.setBaseDao(partymemberDao);
		this.partymemberDao = partymemberDao;
	}
	

	@Override
	public void saveUserAndRoles(Partymember partymember, String[] roleIds) {
		/*//先保存党员信息（先保存主表记录）
		save(partymember);
		//再保存用户所具有的角色(从表记录),如果没有选择角色那么就什么也不做
		if (roleIds != null) {
			for (String roleId : roleIds) {
				partymemberDao.saveUserRole(new Rolepartymember(new Partymember(partymember.getId()), new Role(roleId)));
			}
		}*/
		//如果填写了角色信息
		if (roleIds != null) {
			for (String roleId : roleIds) {
				Rolepartymember rp = new Rolepartymember();
				rp.setPartymember(partymember);
				rp.setRole(new Role(roleId));
				partymember.getRolepartymembers().add(rp);
				//partymemberDao.saveUserRole(rp);
			}
		}
		//保存党员信息
		save(partymember);
	}
	
	@Override
	public List<Rolepartymember> findUserRoleByUserId(String id) {
		return partymemberDao.findUserRoleByUserId(id);
	}


	@Override
	public List<Partymember> findPartymemberByIdAndNumber(String id, String number) {
		return partymemberDao.findPartymemberByIdAndNumber(id, number);
	}


	@Override
	public void saveUserRole(Rolepartymember rp) {
		partymemberDao.saveUserRole(rp);		
	}
}
