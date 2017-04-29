package com.partysys.core.permission.impl;

import java.util.Set;

import com.partysys.core.permission.PermissionCheck;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.entity.Rolepartymember;
import com.partysys.sysmanage.role.entity.Roleprivilege;

public class PermissionCheckImpl implements PermissionCheck{

	@Override
	public boolean isAccess(Partymember pm, String code) throws Exception{
		try {
			//获取用户的所有角色
			Set<Rolepartymember> rolepartymembers = pm.getRolepartymembers();
			if (rolepartymembers == null) {
				return false;
			}
			for (Rolepartymember rp : rolepartymembers) {
				//具有“xxx”这一权限的角色的人都可以使用xxx模块
				for (Roleprivilege pri : rp.getRole().getRoleprivileges()) {
					if (code.equals(pri.getCode())) {
						return true;
					}
				}
			}
			return false;
			//return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("出现异常： " + e.getMessage());
		}
	}
	
}
