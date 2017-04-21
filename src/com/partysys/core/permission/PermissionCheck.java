package com.partysys.core.permission;

import com.partysys.sysmanage.party.entity.Partymember;

public interface PermissionCheck {
	/**
	 * 验证用户是否有权限访问系统的某一部分
	 * @param p
	 * @param code
	 * @return
	 */
	public boolean isAccess(Partymember p, String code) throws Exception;
}
