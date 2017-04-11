package com.partysys.sysmanage.party.service;

import java.util.List;

import com.partysys.core.service.BaseService;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.entity.Rolepartymember;

public interface PartymemberService extends BaseService<Partymember>{
	/**
	 * 保存党员信息的同时保存
	 * @param partymember
	 * @param roleIds
	 * @param branchId
	 * @param branchName
	 */
	void saveUserAndRoles(Partymember partymember, String[] roleIds);
	/**
	 * 通过Id号和学工号查询用户
	 * @param id
	 * @param number
	 * @return
	 */
	List<Partymember> findPartymemberByIdAndNumber(String id, String number);
	/**
	 * 通过用户Id号查询用户所具有的角色
	 * @param id
	 * @return
	 */
	public List<Rolepartymember> findUserRoleByUserId(String id);
	void saveUserRole(Rolepartymember rp);
}
