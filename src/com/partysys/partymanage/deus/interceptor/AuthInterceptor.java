package com.partysys.partymanage.deus.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.partysys.core.permission.PermissionCheck;
import com.partysys.core.permission.impl.PermissionCheckImpl;
import com.partysys.sysmanage.party.entity.Partymember;
/**
 * 访问党费管理需要具有教师或学生党建管理权限
 * @author 朱可凡
 *
 */
public class AuthInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Partymember pm = (Partymember) invocation.getInvocationContext().getSession().get("SYS_USER");
		PermissionCheck pc = new PermissionCheckImpl();
		//若该角色拥有教师汇总(支部书记)党费，学生汇总(支部书记)党费权限，那么就可以访问系统
		if (pc.isAccess(pm, "Tmanage") 
				|| pc.isAccess(pm, "studentmanage") 
				|| pc.isAccess(pm, "teachersumcash") 
				|| pc.isAccess(pm, "studentsumcash")) {
			return invocation.invoke();
		}
		//跳转到没有权限访问页面
		return Action.LOGIN;
	}
}
