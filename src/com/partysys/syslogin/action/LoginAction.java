package com.partysys.syslogin.action;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.partysys.core.constant.Constant;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.service.PartymemberService;
/**
 * 系统管理登陆界面Action
 * @author 朱可凡
 *
 */
public class LoginAction extends ActionSupport{
	private Partymember partymember;
	private String loginResult;
	@Autowired
	private PartymemberService partymemberService;
	/**
	 * 跳转到登陆页面
	 * @return
	 */
	public String toLoginUI() {
		return "loginUI";
	}
	/**
	 * 登出
	 * @return
	 */
	public String logout() {
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.SYS_USER);
		//退出登录后跳转到登陆页面
		return "loginUI";
	}
	/**
	 * 跳转到没有权限访问页面
	 * @return
	 */
	//用拦截器来实现权限控制 2017_4_18 by 朱可凡
	/*public String toNoPermissionUI() {
		return "noPermissionUI";
	}*/
	/**
	 * 处理登陆的Action
	 * @return
	 */
	public String login() {
		if (partymember != null) {
			if (StringUtils.isNotBlank(partymember.getNumber()) && StringUtils.isNotBlank(partymember.getPassword())) {
				List<Partymember> list = partymemberService.findUserByNumberAndPass(partymember.getNumber(), partymember.getPassword());
				if (list != null && list.size() > 0) {
					//登陆成功
					Partymember p = list.get(0);
					//将list集合包装成Set集合
					p.setRolepartymembers(new HashSet<>(partymemberService.findUserRoleByUserId(p.getId())));
					//将用户信息设置到Session中
					ServletActionContext.getRequest().getSession().setAttribute(Constant.SYS_USER, p);
					Log log = LogFactory.getLog(getClass());
					log.info(new Date() + "用户: " + p.getName() + "登陆了系统");
					//登陆过后跳转到主页
					return "homepage";
				} else {
					loginResult = "用户名或密码错误!";
				}
			} else {
				loginResult = "用户名或密码不可为空!";
			}
		} else {
			loginResult = "用户名和密码不可为空!";
		}
		return toLoginUI();
	}
	public Partymember getPartymember() {
		return partymember;
	}
	public void setPartymember(Partymember partymember) {
		this.partymember = partymember;
	}
	public String getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	public PartymemberService getPartymemberService() {
		return partymemberService;
	}
	public void setPartymemberService(PartymemberService partymemberService) {
		this.partymemberService = partymemberService;
	}
}
