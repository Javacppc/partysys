package com.partysys.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.partysys.core.dao.BaseDao;
import com.partysys.core.page.PageResult;
import com.partysys.core.util.QueryHelper;
/**
 * 基础DAO类的实现类
 * @author zhuxiaodong
 *
 * @param <T>
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	// DAO组件进行持久化操作底层依赖的SessionFactory组件
	private SessionFactory sessionFactory;
	// 依赖注入SessionFactory所需的setter方法
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	private Class<T> clazz;
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}
	public SessionFactory getSessionFactory()
	{
		return this.sessionFactory;
	}
	// 根据ID加载实体
	@SuppressWarnings("unchecked")
	public T get(Serializable id)
	{
		return (T)getSessionFactory().getCurrentSession()
			.get(clazz , id);
	}
	// 保存实体
	public Serializable save(T entity)
	{
		return getSessionFactory().getCurrentSession()
			.save(entity);
	}
	// 更新实体
	public void update(T entity)
	{
		getSessionFactory().getCurrentSession().saveOrUpdate(entity);
	}
	// 删除实体
	public void delete(T entity)
	{
		getSessionFactory().getCurrentSession().delete(entity);
	}
	// 根据ID删除实体
	public void delete(Serializable id)
	{
		getSessionFactory().getCurrentSession()
			.createQuery("delete " + clazz.getSimpleName()
				+ " en where en.id = ?0")
			.setParameter("0" , id)
			.executeUpdate();
	}
	// 获取所有实体
	public List<T> findAll()
	{
		return find("select en from "
			+ clazz.getSimpleName() + " en");
	}
	// 获取实体总数
	
	public long findCount()
	{
		List<?> l = find("select count(*) from "
			+ clazz.getSimpleName());
		// 返回查询得到的实体总数
		if (l != null && l.size() == 1 )
		{
			return (Long)l.get(0);
		}
		return 0;
	}

	// 根据HQL语句查询实体
	@SuppressWarnings("unchecked")
	protected List<T> find(String hql)
	{
		return (List<T>)getSessionFactory().getCurrentSession()
			.createQuery(hql)
			.list();
	}
	// 根据带占位符参数HQL语句查询实体
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql , List<Object> params)
	{
		// 创建查询
		Query query = getSessionFactory().getCurrentSession()
			.createQuery(hql);
		// 为包含占位符的HQL语句设置参数
		if (params != null) {
			for(int i = 0 , len = params.size() ; i < len ; i++)
			{
				query.setParameter(i + "" , params.get(i));
			}
		}
		return (List<T>)query.list();
	}
	/**
	 * 使用hql 语句进行分页查询操作
	 * @param hql 需要查询的hql语句
	 * @param pageNo 查询第pageNo页的记录
	 * @param pageSize 每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPage(String hql,
		 int pageNo, int pageSize)
	{
		// 创建查询
		return getSessionFactory().getCurrentSession()
			.createQuery(hql)
			// 执行分页
			.setFirstResult((pageNo - 1) * pageSize)
			.setMaxResults(pageSize)
			.list();
	}
	/**
	 * 使用hql 语句进行分页查询操作
	 * @param hql 需要查询的hql语句
	 * @param params 如果hql带占位符参数，params用于传入占位符参数
	 * @param pageNo 查询第pageNo页的记录
	 * @param pageSize 每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPage(String hql , int pageNo, int pageSize
		, Object... params)
	{
		// 创建查询
		Query query = getSessionFactory().getCurrentSession()
			.createQuery(hql);
		// 为包含占位符的HQL语句设置参数
		for(int i = 0 , len = params.length ; i < len ; i++)
		{
			query.setParameter(i + "" , params[i]);
		}
		// 执行分页，并返回查询结果
		return query.setFirstResult((pageNo - 1) * pageSize)
			.setMaxResults(pageSize)
			.list();
	}
	@Override
	public T findById(Serializable id) {
		List<Object> obj = new ArrayList<>();
		obj.add(id);
		return find("select en from " + clazz.getSimpleName() + " en" + " where en.id = ?0", obj).get(0);
	}
	@Override
	public List<T> find(QueryHelper helper){
		Query query = getSessionFactory().getCurrentSession()
				.createQuery(helper.getQueryListHql());
		List<Object> objs = helper.getParameters();
		if (objs != null) {
			for (int i = 0; i < objs.size(); ++i) {
				query.setParameter(i, objs.get(i));
			}
		}
		return query.list();
	}
	
	@Override
	public PageResult findByPage(QueryHelper helper, int pageNo, int pageSize) {
		Query query = getSessionFactory().getCurrentSession()
				.createQuery(helper.getQueryListHql());
		List<Object> objs = helper.getParameters();
		if (objs != null) {
			for (int i = 0; i < objs.size(); ++i) {
				query.setParameter(i, objs.get(i));
			}
		}
		//大部分情况下都不会传入pageNo的值所以对于这种情况初始为1
		if (pageNo < 1) pageNo = 1;
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		List items = query.list();//分页后的查询结果
		Query queryCount = getSessionFactory().getCurrentSession()
				.createQuery(helper.getQueryCountHql());//获取总记录数
		if (objs != null) {
			for (int i = 0; i < objs.size(); ++i) {
				queryCount.setParameter(i, objs.get(i));
			}
		}
		long totalCount = (long) queryCount.uniqueResult();
		return new PageResult(totalCount,pageNo, pageSize, items);
	}
	
}
