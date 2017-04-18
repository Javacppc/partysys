package com.partysys.syshome.action;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 管理系统的首页
 * @author 朱可凡
 *
 */
public class HomeAction extends ActionSupport{
	@Override
	public String execute() throws Exception {
		//跳转到系统首页
		return "home";
	}
}
