package com.partysys.sysmanage.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.partysys.core.permission.PermissionCheck;
import com.partysys.core.permission.impl.PermissionCheckImpl;
import com.partysys.sysmanage.party.entity.Partymember;

/**
 * 权限控制拦截器（使用系统管理模块需要有系统管理员权限）
 * @author 朱可凡
 *
 */
public class AuthInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map<String, Object> session = ctx.getSession();
		Partymember pm = (Partymember) session.get("SYS_USER");
		PermissionCheck pc = new PermissionCheckImpl();
		if (pc.isAccess(pm, "系统管理员")) {
			//具有系统管理员权限的直接放行
			return invocation.invoke();
		}
		//没有系统管理员权限？直接跳转到noPermissionUI（全局定义）
		return Action.LOGIN;
		
		//return invocation.invoke();
	}
}
