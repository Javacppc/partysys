package com.partysys.partymanage.home.action;

import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport{
	/**
	 * 加载整个系统管理页面框架
	 * @return
	 */
	public String frame() {
		return "frame";
	}
	/**
	 * 加载左部菜单栏
	 * @return
	 */
	public String left() {
		return "left";
	}
	/**
	 * 加载顶部菜单栏
	 * @return
	 */
	public String top() {
		return "top";
	}
}
