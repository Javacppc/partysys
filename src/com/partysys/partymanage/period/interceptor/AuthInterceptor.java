package com.partysys.partymanage.period.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.partysys.core.permission.PermissionCheck;
import com.partysys.core.permission.impl.PermissionCheckImpl;
import com.partysys.sysmanage.party.entity.Partymember;
/**
 * 汇总党费的拦截器
 * @author 朱可凡
 *
 */
public class AuthInterceptor extends AbstractInterceptor{
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Partymember pm = (Partymember) invocation.getInvocationContext().getSession().get("SYS_USER");
		PermissionCheck pc = new PermissionCheckImpl();
		if (pc.isAccess(pm, "汇总")) {
			//只要有汇总党费的角色就可以访问系统
			return invocation.invoke();
		}
		//跳转到没有权限访问页面
		return Action.LOGIN;
	}
}
