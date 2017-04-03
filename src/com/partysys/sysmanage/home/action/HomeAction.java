package com.partysys.sysmanage.home.action;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 在系统首页中嵌入系统管理页面
 * @author 朱可凡
 *
 */
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
