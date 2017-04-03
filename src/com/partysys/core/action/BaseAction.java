package com.partysys.core.action;

import com.opensymphony.xwork2.ActionSupport;
import com.partysys.core.page.PageResult;
/**
 * 这里定义了一个全局的Action基类
 * 用于抽取本系统中所有Action所公有的操作
 * 比如：批量刪除在用戶管理，角色管理中都会用到，所以把它们抽象到这个
 * 公共Action之中，方便重用，无需写多次了
 * @author zhuxiaodong
 *
 */
public abstract class BaseAction extends ActionSupport {
	/**
	 * 用於批量刪除時用
	 */
	protected String[] selectedRow;
	
	/**
	 * 返回分页对象
	 */
	protected PageResult pageResult;
	/**
	 * 返回页号
	 */
	protected int pageNo;
	/**
	 * 返回页大小
	 */
	protected int pageSize;
	/**
	 * 设置默认每页显示的记录数
	 */
	public static final int DEFAULT_PAGE_SIZE = 5;
	
	
	public PageResult getPageResult() {
		return pageResult;
	}
	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		//如果未指定每一页显示多少条记录，直接给定一个默认值
		if (pageSize < 1) pageSize = DEFAULT_PAGE_SIZE;
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
}
