package com.partysys.sysmanage.branch.action;

import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.core.exception.ActionException;
import com.partysys.core.util.QueryHelper;
import com.partysys.sysmanage.branch.entity.Branch;
import com.partysys.sysmanage.branch.service.BranchService;

public class BranchAction extends BaseAction{
	@Autowired
	private BranchService branchService;
	
	private Branch branch;
	private String strTitle;
	
	/**
	 * 显示列表页面
	 */
	public String listUI() throws Exception{
		try {
			QueryHelper helper = new QueryHelper(Branch.class, "b");
			if (branch != null) {
				if (StringUtils.isNotBlank(branch.getBranchName())) {
					branch.setBranchName(URLDecoder.decode(branch.getBranchName(), "utf-8"));
					helper.addWhereClause("b.branchName like ?", "%" + branch.getBranchName() + "%");
				}
			}
			pageResult = branchService.findByPage(helper, getPageNo(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息是: " + e.getMessage());
		}
		return "listUI";
	}
	/**
	 * 在弹出的新增窗口中添加支部信息（其实就是填一个支部名称而已）
	 * @return
	 * @throws ActionException 
	 */
	public String add() throws ActionException {
		try {
			if (branch != null) {
				branchService.save(branch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息："+e.getMessage());
		}
		return "list";
	}
	/**
	 * 在弹出的编辑窗口占中编辑支部信息
	 * @return
	 */
	public String edit() throws Exception{
		try {
			if (branch != null) {
				Branch mybranch = branchService.findById(branch.getBranchId());
				mybranch.setBranchName(branch.getBranchName());
				branchService.update(branch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		return "list";
	}
	/**
	 * 删除信息
	 * @return
	 */
	public String delete() throws Exception{
		try {
			if (branch != null && branch.getBranchId() != null) {
				strTitle = branch.getBranchName();
				//TODO: 此处是需要级联删除的（当删除部门的时候党员表中的部门字段也要同时被删除）
				branchService.delete(branch.getBranchId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		//删除完成后跳转到显示信息页面
		return "list";
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String deleteSelected() throws Exception{
		try {
			//删除用户选中的支部记录
			if (selectedRow != null) {
				for (String id : selectedRow) {
					strTitle = branch.getBranchName();
					branchService.delete(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息："+e.getMessage());
		}
		//删除完成后重定向到显示支部页面
		return "list";
	}
	
	public BranchService getBranchService() {
		return branchService;
	}

	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	
}
