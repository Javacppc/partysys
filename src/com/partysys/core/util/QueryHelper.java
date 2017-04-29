package com.partysys.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

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
	private String InCluase = "";
	private int InCount = 0;
	
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
	/**
	 * 生成Where子句
	 * @param condition
	 * @param params
	 */
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
	 * 生成In子句（简单的非子查询）
	 * @param condition: 指定循环开始拼接的字符串 例如:p.id in (
	 * @param order: In语句中要加的值 例如 number, pass
	 * @param isEnd: 是否结束In语句
	 */
	public void addInClause(String condition, String order, boolean isEnd) {
		InCount++;
		//若本来已经有where语句了
		if (whereCluase.length() > 1) {
			if (InCount == 1) {
				whereCluase += !isEnd ? " AND " + condition + "'" + order + "'" : " AND " + condition + "'" + order + "'" + ")";
			} else {
				whereCluase += !isEnd ? ", " + "'" + order + "'" : ", " + "'" + order + "'" + ")";
			}
		} else {
			if (InCount == 1) {
				whereCluase += !isEnd ? " WHERE " + condition + "'" +order + "'": " WHERE " + condition + "'" +order + "'" + ")";
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
