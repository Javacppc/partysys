package com.partysys.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.partysys.core.constant.Constant;
/**
 * 登陆过滤器（对于尚未登录的用户需要跳转到登陆页面）
 * @author 朱可凡
 *
 */
public class LoginFilter implements Filter{
	private FilterConfig config;
	@Override
	public void destroy() {
		config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestUri = httpRequest.getRequestURI();
		if (!requestUri.contains("sys/login_")) {
			//用户的请求不是登录请求，需要进行相应处理
			//如果用户已经登陆过了
			if (httpRequest.getSession().getAttribute(Constant.SYS_USER) != null) {
				//对于已登录用户此处需要判定他所拥有的权限并判定他是否有权限访问系统中的某些模块
				chain.doFilter(httpRequest, httpResponse);
			} else {
				//用户尚未登录，需要跳转到登陆页面
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/sys/login_toLoginUI.action");
			}
		} else {
			//是登录请求则直接放行
			chain.doFilter(httpRequest, httpResponse);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	
}
