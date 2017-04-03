package com.partysys.core.dao;

import java.io.Serializable;
import java.util.List;

import com.partysys.core.util.QueryHelper;

/**
 * 基础的Dao类（实现了基本的增删改查分页功能），供其它所有DAO组件继承
 * @author zhuxiaodong
 *
 * @param <T>
 */
public interface BaseDao<T>
{
	/**
	 *  根据ID加载实体
	 * @param id
	 * @return
	 */
	T get(Serializable id);
	/**
	 *  保存实体
	 * @param entity
	 * @return
	 */
	Serializable save(T entity);
	/**
	 *  更新实体
	 * @param entity
	 */
	void update(T entity);
	/**
	 *  删除实体
	 * @param entity
	 */
	void delete(T entity);
	/**
	 *  根据ID删除实体
	 * @param id
	 */
	void delete(Serializable id);
	/**
	 *  获取所有实体
	 * @return
	 */
	List<T> findAll();
	/**
	 *  获取实体总数
	 * @return
	 */
	long findCount();
	T findById(Serializable id);
	/**
	 * 带查询参数的查询，不推荐使用。推荐使用带QuertHelper参数的类
	 * @param hql 输入的hql语句
	 * @param params 传入的查询参数
	 * @return
	 */
	List<T> find(String hql , List<Object> params);
	/**
	 * 条件查询
	 * @param helper 条件查询工具类
	 * @return
	 */
	List<T> find(QueryHelper helper);
	/**
	 * 分页查询
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<T> findByPage(String hql,
			 int pageNo, int pageSize);
	/**
	 * 分页查询
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public List<T> findByPage(String hql , int pageNo, int pageSize
			, Object... params);
}


