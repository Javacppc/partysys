package com.partysys.core.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;
/**
 * 我們通常想在捕获到异常后做一些事情（即对异常的处理---比如发生异常之后跳转到新的页面）
 * ,做这些事情时可能需要之前的一些参数
 * @author zhuxiaodong
 *
 */
public class SysResultAction extends StrutsResultSupport{
	@Override
	protected void doExecute(String info, ActionInvocation invocation) throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		BaseAction action = (BaseAction) invocation.getAction();
		
		//做一些處理 do something。。。
		System.out.println("进入了SysResultAction...");
		//response.sendRedirect(req.getContextPath()+"/user_listUI.action");
		
	}
}
