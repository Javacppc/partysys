package com.partysys.sysmanage.role.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.partysys.core.action.BaseAction;
import com.partysys.core.exception.ActionException;
import com.partysys.core.util.QueryHelper;
import com.partysys.sysmanage.privilege.entity.Privilege;
import com.partysys.sysmanage.privilege.service.PrivilegeService;
import com.partysys.sysmanage.role.entity.Role;
import com.partysys.sysmanage.role.entity.Roleprivilege;
import com.partysys.sysmanage.role.service.RoleService;
/**
 * Role角色管理层次的业务逻辑层
 * @author 朱可凡
 *
 */
public class RoleAction extends BaseAction implements InitializingBean{

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	public RoleAction() {
		
	}
	/**
	 * 用于接收来自用户提交的角色信息
	 */
	private Role role;
	/**
	 * 接收用户选择的权限列表
	 */
	private String[] privilegeIds;
	/**
	 * 用于接收用户搜索框中的选择
	 */
	private String strTitle;
	/**
	 * 权限键值对Map--用于在页面中显示权限并提供给系统管理员勾选
	 */
	public static final Map<String, String> PRIVILEGE_MAP = new HashMap<>();
	
	
	/**
	 * 用于显示列表信息的
	 * @return
	 * @throws Exception
	 */
	public String listUI() throws Exception{
		try {
		
		//加载权限信息供显示列表显示
		ActionContext.getContext().getContextMap().put("privilegeMap", PRIVILEGE_MAP);
		QueryHelper helper = new QueryHelper(Role.class, "r");
		
			if (role != null) {
				if (StringUtils.isNotBlank(role.getRoleName())) {
					//将地址栏中传来的查询参数解码转换为中文
					role.setRoleName(URLDecoder.decode(role.getRoleName(),"utf-8"));
					helper.addWhereClause("r.roleName like ?", "%" + role.getRoleName() + "%");
				}
			}
			//System.out.println(getPageSize());
			pageResult = roleService.findByPage(helper,getPageNo(),getPageSize());
		}  catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息：" + e.getMessage());
		}
		return "listUI";
	}
	/**
	 * 跳转到新增页面
	 * @return
	 */
	public String addUI() throws Exception{
		try {
			ActionContext.getContext().getContextMap().put("privilegeMap", PRIVILEGE_MAP);
			if (role != null) {
				strTitle = role.getRoleName();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息：" + e.getMessage());
		}
		return "addUI";
	}
	/**
	 * 用于保存新增
	 * @return
	 */
	public String add() throws Exception{
		/*if (role != null) {
			//先持久化主表记录
			roleService.save(role);
			//用户勾选了权限选项，此时需要级联保存该角色和该角色对应的权限
			if (privilegeIds != null) {
				//Set<Roleprivilege> set = new HashSet<>();
				for (int i = 0; i < privilegeIds.length; ++i) {
					Integer privilegeId = privilegeService.findIdByName(privilegeIds[i]);
					//由N的一端控制关联关系
					Roleprivilege rp = new Roleprivilege(role,new Privilege(privilegeId,privilegeIds[i]));
					rp.setRole(role);
					//持久化从表记录
					roleService.saveRolePrivilege(rp);
				}
				//role.setRoleprivileges(set);
			}
		}*/
		try {
			if (role != null) {
				if (privilegeIds != null) {
					Set<Roleprivilege> set = new HashSet<>();
					for (int i = 0; i < privilegeIds.length; ++i) {
						set.add(new Roleprivilege(role, privilegeIds[i]));
					}
					role.setRoleprivileges(set);
				}
				roleService.save(role);
			}
			role = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现异常，异常信息：" + e.getMessage());
		}
		//新增保存完成后直接重定向到显示页面
		return "list";
	}
	
	/**
	 * 跳转到编辑页面
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception{
		try {
			ActionContext.getContext().getContextMap().put("privilegeMap", PRIVILEGE_MAP);
			if (role != null && role.getRoleId() != null) {
				//保存用户填在搜索框中的值
				strTitle = role.getRoleName();
				role = roleService.findById(role.getRoleId());
			}
			if (role.getRoleprivileges() != null) {
				privilegeIds = new String[role.getRoleprivileges().size()];
				int i = 0;
				for (Roleprivilege pri : role.getRoleprivileges()) {
					privilegeIds[i++] = pri.getCode();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现问题，异常信息" + e.getMessage());
		}
		return "editUI";
	}
	/**
	 * 保存编辑
	 * @return
	 */
	public String edit() throws Exception{
		try {
			if (role != null) {
				if (privilegeIds != null) {
					Set<Roleprivilege> set = new HashSet<>();
					for (int i = 0; i < privilegeIds.length; ++i) {
						set.add(new Roleprivilege(role, privilegeIds[i]));
					}
					role.setRoleprivileges(set);
				}
				roleService.update(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现问题，异常信息" + e.getMessage());
		}
		//编辑完成后回到列表页面
		return "list";
	}
	/**
	 * 删除某一条角色信息
	 * @return
	 */
	public String delete() throws Exception{
		try {
			if (role != null && role.getRoleId() != null) {
				strTitle = role.getRoleName();
				roleService.delete(role.getRoleId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Action层出现问题，异常信息" + e.getMessage());
		}
		//删除完成后回到列表页面
		return "list";
	}
	/**
	 * 用户点击复选框进行批量删除操作
	 * @return
	 */
	public String deleteSelected() throws Exception{
		try {
			//如果用户选择的记录不为空
			if (selectedRow != null) {
				strTitle = role.getRoleName();
				//将用户选择的记录全部删除
				for (String id : selectedRow) {
					roleService.delete(id);
				}
			}
		} catch (Exception e) {
			throw new ActionException("Action层出现问题，异常信息" + e.getMessage());
		}
		//批量删除完成后跳转到列表页面
		return "list";
	}
	
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	
	
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}
	/**
	 * 当用户第一次访问角色模块时就从数据库中加载所有的权限信息（这就不需要每次访问角色页面都从数据库中查询）
	 * 从而导致效率低下的问题
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		//查询数据库中的所有权限
		List<Privilege> privileges = privilegeService.findAll();
		if (privileges != null) {
			for (Privilege pri : privileges) {
				//向权限map中添加键值对：键（权限名称）值：权限描述---从数据库中查询
				PRIVILEGE_MAP.put(pri.getName(), pri.getPrivilegeName());
			}
		}
	}
	
}
