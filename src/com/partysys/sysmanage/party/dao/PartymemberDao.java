package com.partysys.sysmanage.party.dao;

import java.util.List;

import com.partysys.core.dao.BaseDao;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.entity.Rolepartymember;

public interface PartymemberDao extends BaseDao<Partymember>{
	/**
	 * 保存党员对应的角色信息
	 * @param rolepartymember
	 */
	void saveUserRole(Rolepartymember rolepartymember);
	/**
	 * 通过Id号和学号来查询
	 * @param id
	 * @param number
	 * @return
	 */
	List<Partymember> findPartymemberByIdAndNumber(String id, String number);
	List<Rolepartymember> findUserRoleByUserId(String id);
	
}
