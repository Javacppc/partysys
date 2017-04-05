package com.partysys.sysmanage.info.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.partysys.core.action.BaseAction;
import com.partysys.sysmanage.info.entity.Info;
import com.partysys.sysmanage.info.service.InfoService;

public class InfoAction extends BaseAction{
	
	@Autowired
	private InfoService infoService;
	/**
	 * 接受发布的信息
	 */
	private Info info;
	/**
	 * 接受用户在搜索框中输入的值
	 */
	private String strTitle;
	/**
	 * 显示信息页面
	 * @return
	 * @throws Exception
	 */
	public String listUI() throws Exception{
		
		return "listUI";
	}
	/**
	 * 跳转到新增页面
	 * @return
	 */
	public String addUI() throws Exception{
		return "addUI";
	}
	
	/**
	 * 保存新增
	 * @return
	 */
	public String add() throws Exception{
		//信息发布成功后跳转（其实是重定向）到显示信息页面
		return "list";
	}
	/**
	 * 跳转到编辑页面
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception{
		
		return "editUI";
	}
	/**
	 * 对信息进行编辑
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		//信息编辑成功后重定向到显示信息页面
		return "list";
	}
	/**
	 * 删除信息
	 * @return
	 */
	public String delete() {
		
		//删除完成后跳转到显示信息页面
		return "list";
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String deleteSelected() {
		
		//删除完成后重定向到显示信息页面
		return "list";
	}
	public InfoService getInfoService() {
		return infoService;
	}
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	
}
