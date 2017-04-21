package com.partysys.sysmanage.party.dao;

import java.io.Serializable;
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
	String findBranchIdByUserId(String id);
	/**
	 * 根据用户ID删除角色
	 * @param id
	 */
	void deleteUserRoleByUserId(Serializable id);
	/**
	 * 通过学工号和密码查询
	 * @param number
	 * @param password
	 * @return
	 */
	List<Partymember> findUserByNumberAndPass(String number, String password);
	/**
	 * 删除党员对应的培养人
	 * @param id
	 */
	void deleteCultivateById(Serializable id);
	
}
