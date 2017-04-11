package com.partysys.sysmanage.party.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.partysys.core.action.BaseAction;
import com.partysys.core.exception.ActionException;
import com.partysys.core.util.QueryHelper;
import com.partysys.sysmanage.branch.entity.Branch;
import com.partysys.sysmanage.branch.service.BranchService;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.entity.Rolepartymember;
import com.partysys.sysmanage.party.entity.RolepartymemberId;
import com.partysys.sysmanage.party.service.PartymemberService;
import com.partysys.sysmanage.role.entity.Role;
import com.partysys.sysmanage.role.service.RoleService;

public class PartymemberAction extends BaseAction implements InitializingBean{
	
	@Autowired 
	private PartymemberService partymemberService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BranchService branchService;
	
	//上传文件所需要的三步骤
	/**
	 * 上传的文件实体
	 */
	private File headImg;
	/**
	 * 上传文件的类型（EXCEL?TEXT?PPT?.....）
	 */
	private String headImgContentType;
	/**
	 * 上传文件的文件名（用户方）
	 */
	private String headImgFileName;
	/**
	 * 将用户上传的文件保存在服务器的地址在哪(需要指定)
	 */
	private String savePath;
	/**
	 * 接受用户勾选的角色复选框的值
	 */
	private String[] roleIds;
	/**
	 * 搜索框中的值(党员姓名)
	 */
	private String strName;
	/**
	 * 搜索框中的值(学工号)
	 */
	private String strNumber;
	/**
	 * 搜索框中的值（年级）
	 */
	private String strGrade;
	/**
	 * 接受用户选择的支部信息
	 */
	private String nbranchId;
	
	/**
	 * 党员
	 */
	private Partymember partymember;
	/**
	 * 部门map
	 */
	private Map<String,String> MAP_DEPT = null;
	
	/**
	 * 显示列表
	 * @return
	 * @throws Exception
	 */
	public String listUI() throws Exception{
		try {
			QueryHelper helper = new QueryHelper(Partymember.class, "p");
			if (partymember != null) {
				//根据党员姓名查询
				if (StringUtils.isNotBlank(partymember.getName())) {
					partymember.setName(URLDecoder.decode(partymember.getName(), "utf-8"));
					helper.addWhereClause("p.name like ?", "%" + partymember.getName() + "%");
				}
				//根据学工号来查询
				if (StringUtils.isNotBlank(partymember.getNumber())) {
					partymember.setNumber(URLDecoder.decode(partymember.getNumber(), "utf-8"));
					helper.addWhereClause("p.number like ?", "%" + partymember.getNumber() + "%");
				}
				//根据所属的年级查询
				if (StringUtils.isNoneBlank(partymember.getGrade())) {
					partymember.setGrade(URLDecoder.decode(partymember.getGrade(), "utf-8"));
					helper.addWhereClause("p.grade like ?", "%" + partymember.getGrade());
				}
			}
			pageResult = partymemberService.findByPage(helper,getPageNo(),getPageSize());
		} catch (Exception e) {
			throw new ActionException("Action层出现异常，异常信息是：" + e.getMessage());
		}
		return "listUI";
	}
	/**
	 * 跳转到新增党员页面（录入党员）
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception{
		try {
			//显示部门信息
			ActionContext.getContext().getContextMap().put("mapDept", MAP_DEPT);
			//显示角色信息
			ActionContext.getContext().getContextMap().put("roleList", roleService.findAll());
			if (partymember != null) {
				//接受用户在搜索框中所输入的党员信息
				strName = partymember.getName();
				strNumber = partymember.getNumber();
				strGrade = partymember.getGrade();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息是： " + e.getMessage());
		}
		return "addUI";
	}
	/**
	 * 添加党员
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		try {
			if (partymember != null) {
				//如果用户上传了图片
				if (headImg != null) {
					//使用UUID算法生成文件名
					String fileName = UUID.randomUUID().toString()  + headImgFileName.substring(headImgFileName.lastIndexOf("."));
					//将用户上传的文件保存到服务器的某个位置
					FileUtils.copyFile(headImg, new File(getSavePath(), fileName));
					//在党员信息表中存放其图片在服务器中的相对路径
					partymember.setHeadImg("partymember/" + fileName);
				}
				//保存党员信息时同时保存党员所具有的角色
				//partymemberService.saveUserAndRoles(partymember, roleIds);
				
				
				if (roleIds != null) {
					for (String roleId : roleIds) {
						Rolepartymember rp = new Rolepartymember();
						rp.setId(new RolepartymemberId(partymember.getId(), roleId));
						rp.setPartymember(partymember);//N的一端设置关联关系
						rp.setRole(roleService.findById(roleId));
						partymember.getRolepartymembers().add(rp);
						//partymemberService.saveUserRole(rp);
					}
				}
				//保存党员信息(此时会级联保存对应的角色)
				partymemberService.save(partymember);
				
				
				
				//获取该党员的支部信息（通过支部Id找到这个支部对象，然后实现支部对象和党员对象之间的关联）
				Branch branch = branchService.findById(nbranchId);
				//保存用户所属支部的关联关系(从表设置关联关系)
				partymember.setBranch(branch);
				//此时支部人数增加了
				branch.getPartymembers().add(partymember);
				//查找到党员所具备的所有角色
				List<Rolepartymember> roles = partymemberService.findUserRoleByUserId(partymember.getId());
				//新增的党员信息必须是支部管理员，它才属于这个支部的管理员，所以下面要判断一下这个新增党员是否是支部管理员
				System.out.println("HQL语句有问题");
				for (Rolepartymember mem : roles) {
					//如果用户具有支部相关角色则把该用户存放到Branch的BranchAdmin字段（因为只有支部管理员才是这个支部的管理员）
					if (mem.getRole().getRoleName().contains("支部")) {
						branch.getBranchAdmin().add(partymember.getName());
					}
					System.out.println(mem.getRole().getRoleName());
				}
				//更新支部表信息(添加完党员信息之后就需要修改支部信息---因为支部肯定人数会增加，以及可能会有新的管理员)
				branchService.update(branch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息是： " + e.getMessage());
		}
		partymember = null;
		return "list";
	}
	/**
	 * 跳转到编辑党员信息页面
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception{
		return "editUI";
	}
	/**
	 * 编辑党员信息
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		return "list";
	}
	/**
	 * 删除党员
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		return "list";
	}
	/**
	 * 批量删除党员
	 * @return
	 * @throws Exception
	 */
	public String deleteSelected() throws Exception{
		return "list";
	}
	/**
	 * 导出党员信息到Execl表格
	 * @throws ActionException
	 */
	public void exportExcel() throws Exception{
		
	}
	/**
	 * 从Excel表格中导入党员
	 * @return
	 */
	public String importExcel() {
		//导入党员信息完成之后直接跳转到显示页面
		return "list";
	}
	/**
	 * 在依赖注入完成之后从支部表中查出一共有多少支部
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<Branch> branchList = branchService.findAll();
		//-------------------测试BUG--------------------
		for (Branch b : branchList) {
			System.out.println(b.getBranchName());
		}
		//-------------------测试BUG--------------------
		MAP_DEPT = new HashMap<>();
		String id,name;
		for (int i = 0; i < branchList.size(); ++i) {
			id = branchList.get(i).getBranchId();
			name = branchList.get(i).getBranchName();
			MAP_DEPT.put(id, name);
		}
	}
	/**
	 * 验证输入的学号是否重复
	 */
	public void verifyAccount() throws Exception{
		try {
			System.out.println("是否被执行？。。。。");
			if (partymember != null && partymember.getNumber() != null) {
				List<Partymember> partyList = partymemberService.findPartymemberByIdAndNumber(partymember.getId(), partymember.getNumber());
				String strResult = "true";
				if (partyList != null && partyList.size() > 0) {
					strResult = "false";//说明账号已经存在
				}
				System.out.println(strResult);
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream output = response.getOutputStream();
				//将返回的结果输出到浏览器
				output.write(strResult.getBytes());
				output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常,异常信息是：" + e.getMessage());
		}
	}
	public PartymemberService getPartymemberService() {
		return partymemberService;
	}
	public void setPartymemberService(PartymemberService partymemberService) {
		this.partymemberService = partymemberService;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public Partymember getPartymember() {
		return partymember;
	}
	public void setPartymember(Partymember partymember) {
		this.partymember = partymember;
	}
	public String getStrNumber() {
		return strNumber;
	}
	public void setStrNumber(String strNumber) {
		this.strNumber = strNumber;
	}
	public String getStrGrade() {
		return strGrade;
	}
	public void setStrGrade(String strGrade) {
		this.strGrade = strGrade;
	}
	public BranchService getBranchService() {
		return branchService;
	}
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	public String getNbranchId() {
		return nbranchId;
	}
	public void setNbranchId(String nbranchId) {
		this.nbranchId = nbranchId;
	}	
	
}
