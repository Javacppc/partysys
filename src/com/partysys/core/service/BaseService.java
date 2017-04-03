package com.partysys.core.service;

import java.io.Serializable;
import java.util.List;

import com.partysys.core.util.QueryHelper;
/**
 * 基础的业务逻辑类
 * @author zhuxiaodong
 *
 * @param <T>
 */
public interface BaseService<T> {
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
	
	T findById(Serializable id);
	/**
	 * 带参数的查询，不推荐使用，推荐使用带QueryHelper参数的find方法
	 * @param hql
	 * @param params
	 * @return
	 */
	@Deprecated
	List<T> find(String hql , List<Object> params);
	/**
	 * 用于条件查询的查询，推荐使用
	 * @param helper
	 * @return
	 */
	List<T> find(QueryHelper helper);
	/**
	 * 分页查询
	 * @param hql 指定的hql语句
	 * @param pageNo 指定要显示第几页？
	 * @param pageSize 指定每一页有多少个记录
	 * @param params 指定参数
	 * @return
	 */
	public List<T> findByPage(String hql , int pageNo, int pageSize
			, Object... params);
	/**
	 * 分页查询
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<T> findByPage(String hql , int pageNo, int pageSize);
}
