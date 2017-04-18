package com.partysys.core.permission.impl;

import java.util.Set;

import com.partysys.core.permission.PermissionCheck;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.entity.Rolepartymember;

public class PermissionCheckImpl implements PermissionCheck{

	@Override
	public boolean isAccess(Partymember pm, String code) {
		//获取用户的所有角色
		Set<Rolepartymember> rolepartymembers = pm.getRolepartymembers();
		for (Rolepartymember rp : rolepartymembers) {
			if (rp.getRole().getRoleName().equals(code)) {
				//具有系统管理员权限就可以使用系统管理功能
				return true;
			}
		}
		return false;
	}
	
}
