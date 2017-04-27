package com.partysys.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件查询助手工具类
 * 针对
 * FROM 类 别名
 * WHERE 条件1,...
 * ORDER By 
 * 
 * @author 朱可凡
 *
 */
public class QueryHelper {
	
	private List<Object> parameters;
	
	private String fromClause = "";
	private String whereCluase = "";
	private String orderByCluase = "";
	
	
	public static final String ORDER_DESC = "DESC";
	public static final String ORDER_ASC = "ASC";
	/**
	 * 用构造方法生成from语句
	 * @param clazz
	 * @param alias
	 */
	public QueryHelper(Class clazz, String alias) {
		if (alias != null)
			fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
		else 
			fromClause = "FROM " + clazz.getSimpleName();
	}
	
	public void addWhereClause(String condition, Object...params) {
		if (whereCluase.length() > 1) {
			whereCluase += " AND " + condition;
		} else {
			whereCluase += " WHERE " + condition;
		}
		
		if (parameters == null)
			parameters = new ArrayList<>();
		if (params != null) {
			for (Object param : params) {
				parameters.add(param);
			}
		}
	}
	/**
	 * 生成OrderBy子句的方法
	 * @param property
	 * @param order
	 */
	public void addOrderByClause(String property, String order) {
		if (orderByCluase.length() > 1) {
			orderByCluase += " , " + property + " " + order;
		} else {
			orderByCluase = " ORDER BY " + property + " " + order;
		}
	}
	
	/** 
	 * 生成HQL语句
	 * @return
	 */
	public String getQueryListHql() {
		return fromClause + whereCluase + orderByCluase;
	}
	/**
	 * 查询HQL语句中?对应的查询条件值的集合
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	public String getQueryCountHql() {
		return "SELECT COUNT(*) " + fromClause + whereCluase;
	}
	
	
}
