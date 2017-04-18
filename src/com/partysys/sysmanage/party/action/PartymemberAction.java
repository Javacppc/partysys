package com.partysys.sysmanage.party.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	 * 搜索框中的值（根据老师和学生查询）
	 */
	private String strType;
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
	 * 解决在编辑用户信息时的问题（之前的支部号要记录下来）
	 */
	private String prebranchId;
	/**
	 * 解决在编辑用户信息时的问题(之前用户的姓名要记录下来)
	 */
	private String preusername;
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
					helper.addWhereClause("p.grade like ?", partymember.getGrade() + "%");
				}
				if (StringUtils.isNotBlank(partymember.getClassification())) {
					partymember.setClassification(URLDecoder.decode(partymember.getClassification(), "utf-8"));
					helper.addWhereClause("p.classification = ?", partymember.getClassification());
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
			//获取部门信息并将部门信息显示在前台页面
			List<Branch> branchList = branchService.findAll();
			MAP_DEPT = new HashMap<>();
			String id,name;
			for (int i = 0; i < branchList.size(); ++i) {
				id = branchList.get(i).getBranchId();
				name = branchList.get(i).getBranchName();
				MAP_DEPT.put(id, name);
			}
			
			//显示部门信息
			ActionContext.getContext().getContextMap().put("mapDept", MAP_DEPT);
			//显示角色信息
			ActionContext.getContext().getContextMap().put("roleList", roleService.findAll());
			if (partymember != null) {
				//接受用户在搜索框中所输入的党员信息
				strName = partymember.getName();
				strNumber = partymember.getNumber();
				strGrade = partymember.getGrade();
				strType = partymember.getClassification();
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
				partymemberService.save(partymember);//尚未持久化的的Partymember怎么可能有主键所以要在这里先保存一次
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
				//保存党员信息(此时会级联更新对应的角色)
				partymemberService.update(partymember);			
				
				//获取该党员的支部信息（通过支部Id找到这个支部对象，然后实现支部对象和党员对象之间的关联）
				Branch branch = branchService.findById(nbranchId);
				//保存用户所属支部的关联关系(从表设置关联关系)
				partymember.setBranch(branch);
				//此时支部人数增加了
				branch.getPartymembers().add(partymember);
				//查找到党员所具备的所有角色
				List<Rolepartymember> roles = partymemberService.findUserRoleByUserId(partymember.getId());
				//新增的党员信息必须是支部管理员，它才属于这个支部的管理员，所以下面要判断一下这个新增党员是否是支部管理员
				for (Rolepartymember mem : roles) {
					//如果用户具有支部相关角色则把该用户存放到Branch的BranchAdmin字段（因为只有支部管理员才是这个支部的管理员）
					if (mem.getRole().getRoleName().contains("支部")) {
						branch.getBranchAdmin().add(partymember.getName());
					}
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
		try {
			//获取部门信息并将部门信息显示在前台页面
			List<Branch> branchList = branchService.findAll();
			MAP_DEPT = new HashMap<>();
			String id,name;
			for (int i = 0; i < branchList.size(); ++i) {
				id = branchList.get(i).getBranchId();
				name = branchList.get(i).getBranchName();
				MAP_DEPT.put(id, name);
			}
			
			//显示部门信息
			ActionContext.getContext().getContextMap().put("mapDept", MAP_DEPT);
			//显示角色信息
			ActionContext.getContext().getContextMap().put("roleList", roleService.findAll());
			if (partymember != null && partymember.getId() != null) {
				strName = partymember.getName();
				strNumber = partymember.getNumber();
				strGrade = partymember.getGrade();
				strType = partymember.getClassification();
				partymember = partymemberService.findById(partymember.getId());
				//处理角色回显在页面上
				List<Rolepartymember> rolepartymember = partymemberService.findUserRoleByUserId(partymember.getId());
				if (rolepartymember != null) {
					//处理角色权限回显
					roleIds = new String[rolepartymember.size()];
					int i = 0;
					for (Rolepartymember r : rolepartymember) {
						roleIds[i] = r.getId().getRoleId();
						++i;
					}
				}
				//处理部门信息回显
				//保留原来的支部ID号和党员姓名(在edit()方法中会用到)
				prebranchId = partymemberService.findBranchIdByUserId(partymember.getId());
				preusername = partymember.getName();
				if (prebranchId != null)
					nbranchId = prebranchId;
				else {
					nbranchId = "";
					prebranchId = "";
				}
			}
		} catch (Exception e) {
			Log logger = LogFactory.getLog(getClass());
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息是：" + e.getMessage());
		}
		return "editUI";
	}
	/**
	 * 编辑党员信息
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
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
				//对于角色，先要删除所有用户原来的权限
				partymemberService.deleteUserRoleByUserId(partymember.getId());
				//再添加已经更新后的权限
				if (roleIds != null) {
					for (String roleId : roleIds) {
						Rolepartymember rp = new Rolepartymember();
						rp.setId(new RolepartymemberId(partymember.getId(), roleId));	
						rp.setPartymember(partymember);//N的一端设置关联关系
						rp.setRole(roleService.findById(roleId));
						partymember.getRolepartymembers().add(rp);
					}
				}
				//保存党员信息(此时会级联更新对应的角色)
				partymemberService.update(partymember);
				//处理修改后的部门（如果用户修改了部门，那么1）他所在的原来的那个部门需要处理，2）修改后的部门需要处理）
				
				//如果用户改变了用户部门，那么获取该党员的支部信息（通过支部Id找到这个支部对象，然后实现支部对象和党员对象之间的关联）
				//否则不执行下面的if语句
				if (!prebranchId.equals(nbranchId)) {
					//如果之前根本就没有选择部门的话，直接添加即可(就不需要删除了)
					if (!prebranchId.equals("")) {
						Branch oldBranch = branchService.findById(prebranchId);
						//将原来旧部门的管理员字段去掉该党员的信息
						//这里有一点需要注意，那就是党员姓名会变，但是记录在br_admin表中的姓名还是原来的
						//所以判断一下，如果管理员改了姓名那么就删除之前的名字
						oldBranch.getBranchAdmin().remove(preusername.equals(partymember.getName()) ? partymember.getName() : preusername);
						//删除原来旧部门与党员之间的关联关系
						oldBranch.getPartymembers().remove(partymember);
						//BUG FIXED IN 2017_4_11_20_32:在修改知否肯定要保存到数据库中
						branchService.update(oldBranch);
					}
					Branch branch = null;
					//如果新添加的部门是空的话就不执行更新了
					if (!nbranchId.equals("")) {
						//BUG FIXED IN 2017_4_11_21_44
						branch = branchService.findById(nbranchId);
						//保存用户所属支部的关联关系(从表设置关联关系)
						partymember.setBranch(branch);
						//此时支部人数增加了
						branch.getPartymembers().add(partymember);
						//查找到党员所具备的所有角色
						List<Rolepartymember> roles = partymemberService.findUserRoleByUserId(partymember.getId());
						//新增的党员信息必须是支部管理员，它才属于这个支部的管理员，所以下面要判断一下这个新增党员是否是支部管理员
						for (Rolepartymember mem : roles) {
							//如果用户具有支部相关角色则把该用户存放到Branch的BranchAdmin字段（因为只有支部管理员才是这个支部的管理员）
							if (mem.getRole().getRoleName().contains("支部")) {
								/*//如果支部管理员列表里已经有这个人了，就不要再加了，否则就重复了!
								if (!branch.getBranchAdmin().contains(partymember.getName()))	*/
									branch.getBranchAdmin().add(partymember.getName());
							}
						}
						//更新支部表信息(添加完党员信息之后就需要修改支部信息---因为支部肯定人数会增加，以及可能会有新的管理员)
						branchService.update(branch);
					}
				} else {
					//部门不为空的话
					if (!nbranchId.equals("") && !prebranchId.equals("")) {
						Branch branch = branchService.findById(nbranchId);
						//角色
						List<Rolepartymember> roles = partymemberService.findUserRoleByUserId(partymember.getId());
						//先删除原先的角色信息
						branch.getBranchAdmin().remove(preusername.equals(partymember.getName())?partymember.getName():preusername);
						//再添加新的信息
						for (Rolepartymember mem : roles) {
							if (mem.getRole().getRoleName().contains("支部")) {
									branch.getBranchAdmin().add(partymember.getName());
							}
						}
						branchService.update(branch);
					}
					/*//部门名称不为空
					if (!nbranchId.equals("")) {
						Branch branch = branchService.findById(nbranchId);
						//如果改变了姓名并且未改支部名称
						if (!preusername.equals(partymember.getName())) {
							//根据部门Id找到					
							List<String> admin = branch.getBranchAdmin();
							for (int i = 0; i < admin.size(); ++i) {
								if (admin.get(i).equals(preusername)) {
									//如果找到了就设置这个用户名为新的值
									admin.set(i, partymember.getName());
								}
							}
							branchService.update(branch);
						}
					}*/
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息是：" + e.getMessage());
		}
		return "list";
	}
	/**
	 * 删除党员
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		try {
			 if (partymember != null && partymember.getId() != null) {
				 strName = partymember.getName();
				 strGrade = partymember.getGrade();
				 strNumber = partymember.getNumber();
				 strType = partymember.getClassification();
				 Partymember pm = partymemberService.findById(partymember.getId());
				 //删除支部信息
				 deleteBranch(pm);
				 //删除党员以及党员对应的角色信息
				 partymemberService.delete(partymember.getId());
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息是：" + e.getMessage());
		}
		return "list";
	}
	/**
	 * 删除党员支部信息
	 * @param pm 
	 */
	private void deleteBranch(Partymember pm) {
		 //删除党员对应的支部信息
		 //找到这个部门
		 Branch branch = branchService.findById(pm.getBranch().getBranchId());
		 System.out.println(branch.getBranchName());
		 //在支部表中删除对应的党员
		 branch.getPartymembers().remove(pm);
		 List<Rolepartymember> roles = partymemberService.findUserRoleByUserId(pm.getId());
		 //删除支部表对应的管理员信息（如果是支部管理员的话）
		 for (Rolepartymember mem : roles) {
			 //判断删除的这个用户是不是支部管理员
		 	if (mem.getRole().getRoleName().contains("支部")) {
				branch.getBranchAdmin().remove(pm.getName());//删除管理员信息
			 }
		 }
		 //BUG FIXED IN 2017_4_12 原因：又是忘记更新到数据库中
		 branchService.update(branch);
	}
	/**
	 * 批量删除党员
	 * @return
	 * @throws Exception
	 */
	public String deleteSelected() throws Exception{
		try {
			if (selectedRow != null) {
				strName = partymember.getName();
				strGrade = partymember.getGrade();
				strNumber = partymember.getNumber();
				strType = partymember.getClassification();
				Partymember  pm;
				for (String id : selectedRow) {
					pm = partymemberService.findById(id);
					//删除支部表中有关该党员的所有信息(原来党员的Id，党员的姓名)
					deleteBranch(pm);
					partymemberService.delete(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息是：" + e.getMessage());
		}
		return "list";
	}
	/**
	 * 导出学生党员信息到Execl表格
	 * @throws ActionException
	 */
	public void exportExcelForStudent() throws Exception{
		QueryHelper helper = new QueryHelper(Partymember.class, "p");
		helper.addWhereClause("p.classification=?", "STUDENT");
		List<Partymember> pm = partymemberService.find(helper);
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("application/x-execel");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String("学生党员表.xls".getBytes(), "ISO-8859-1"));
		ServletOutputStream out = res.getOutputStream();
		partymemberService.exportExcelForStudent(pm, out, branchService);
		if (out != null) {
			out.close();
		}
	}
	/**
	 * 导出教师党员信息到Excel表格
	 * @throws Exception
	 */
	public void exportExcelForTeacher() throws Exception{
		QueryHelper helper = new QueryHelper(Partymember.class, "p");
		helper.addWhereClause("p.classification=?", "TEACHER");
		List<Partymember> pm = partymemberService.find(helper);
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("application/x-execel");
		res.setHeader("Content-Disposition", "attachment;filename="+new String("教师党员表.xls".getBytes(), "ISO-8859-1"));
		ServletOutputStream out = res.getOutputStream();
		partymemberService.exportExcelForTeacher(pm, out);
		if (out != null) {
			out.close();
		}
	}
	/**
	 * 从Excel表格中导入学生党员
	 * @return
	 */
	public String importExcelForStudent() throws Exception{
		try {
				//如果获取的文件不为空 即用户上传了文件
				if (headImg != null) {
					//如果文件名匹配.xls和.xlsx格式的话
					if (headImgFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
						partymemberService.importExcelForStudent(headImg, headImgFileName, branchService);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ActionException("Action层出现异常，异常信息是: " + e.getMessage());
		}
		//导入党员信息完成之后直接跳转到显示页面
		return "list";
	}
	/**
	 * 从Excel表格中导入教师党员
	 * @return
	 * @throws Exception
	 */
	public String importExcelForTeacher() throws Exception{
		if (headImg != null) {
			if (headImgFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
				partymemberService.importExcelForTeacher(headImg, headImgFileName);
			}
		}
		return "list";
	}
	/**
	 * 在依赖注入完成之后执行
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
		/*//-------------------测试BUG--------------------
		for (Branch b : branchList) {
			System.out.println(b.getBranchName());
		}
		//-------------------测试BUG--------------------
		 */		
		
	}
	/**
	 * 验证输入的学号是否重复
	 */
	public void verifyAccount() throws Exception{
		try {
			//System.out.println("是否被执行？。。。。");
			if (partymember != null && partymember.getNumber() != null) {
				List<Partymember> partyList = partymemberService.findPartymemberByIdAndNumber(partymember.getId(), partymember.getNumber());
				String strResult = "true";
				if (partyList != null && partyList.size() > 0) {
					strResult = "false";//说明账号已经存在
				}
				//System.out.println(strResult);
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
	public String getPrebranchId() {
		return prebranchId;
	}
	public void setPrebranchId(String prebranchId) {
		this.prebranchId = prebranchId;
	}
	public String getPreusername() {
		return preusername;
	}
	public void setPreusername(String preusername) {
		this.preusername = preusername;
	}
	public String getStrType() {
		return strType;
	}
	public void setStrType(String strType) {
		this.strType = strType;
	}	
	
}