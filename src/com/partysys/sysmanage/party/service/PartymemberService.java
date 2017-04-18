package com.partysys.sysmanage.party.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.partysys.core.service.BaseService;
import com.partysys.sysmanage.branch.service.BranchService;
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
	/**
	 * 根据党员id查找支部id号
	 * @param id
	 * @return
	 */
	String findBranchIdByUserId(String id);
	/**
	 * 根据用户ID删除该用户对用的角色
	 * @param id
	 */
	void deleteUserRoleByUserId(Serializable id);
	/**
	 * 导入学生Excel文件
	 * @param headImg 文件
	 * @param headImgFileName 文件名
	 */
	void importExcelForStudent(File headImg, String headImgFileName, BranchService branchService) throws Exception;
	/**
	 * 导入教师Excel文件
	 * @param headImg
	 * @param headImgFileName
	 */
	void importExcelForTeacher(File headImg, String headImgFileName) throws Exception;
	/**
	 * 为学生导出Excel表格
	 * @param pm
	 * @param out
	 * @param branchService 
	 */
	void exportExcelForStudent(List<Partymember> pm, ServletOutputStream out, BranchService branchService);
	/**
	 * 为教师输出Excel表格
	 * @param pm
	 * @param out
	 */
	void exportExcelForTeacher(List<Partymember> pm, ServletOutputStream out);
	/**
	 * 通过学工号和密码来查询
	 * @param number
	 * @param password
	 * @return
	 */
	List<Partymember> findUserByNumberAndPass(String number, String password);
}
