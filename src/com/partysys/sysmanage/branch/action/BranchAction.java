package com.partysys.sysmanage.branch.action;

import java.net.URLDecoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.core.exception.ActionException;
import com.partysys.core.util.QueryHelper;
import com.partysys.sysmanage.branch.entity.Branch;
import com.partysys.sysmanage.branch.service.BranchService;
/**
 * 支部业务逻辑类
 * @author 朱可凡
 *
 */
public class BranchAction extends BaseAction{
	@Autowired
	private BranchService branchService;
	
	private Branch branch;
	/**
	 * 封装搜索结果
	 */
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
			branch = null;
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
				//只有当党员全删了之后才可删除该支部
				branchService.delete(branch.getBranchId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("删除支部失败!请先删除所有该支部所有党员!");
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
			throw new ActionException("删除支部失败!请先删除所有该支部所有党员!");
		}
		//删除完成后重定向到显示支部页面
		return "list";
	}
	/**
	 * 验证是否是相同的账户名称
	 */
	public void verify() throws Exception{
		try {
			if (branch != null && branch.getBranchName() != null) {
				QueryHelper helper = new QueryHelper(Branch.class, "b");
				helper.addWhereClause("b.branchName = ?", branch.getBranchName());
				String result = "true";
				if (!branchService.find(helper).isEmpty()) {
					//表示有该支部了
					result = "false";
				}
				HttpServletResponse res = ServletActionContext.getResponse();
				res.setContentType("text/html");
				ServletOutputStream out = res.getOutputStream();
				
				out.write(result.getBytes());
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息: " + e.getMessage());
		}
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
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	
}
