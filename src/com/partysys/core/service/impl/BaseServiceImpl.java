package com.partysys.core.service.impl;

import java.io.Serializable;
import java.util.List;

import com.partysys.core.dao.BaseDao;
import com.partysys.core.page.PageResult;
import com.partysys.core.service.BaseService;
import com.partysys.core.util.QueryHelper;
/**
 * 基础Service实现类，所有Service可以继承该BaseService类
 * @author 朱可凡
 *
 * @param <T>
 */
public class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T get(Serializable id) {
		return baseDao.get(id);
	}

	@Override
	public Serializable save(T entity) {
		return baseDao.save(entity);
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	@Override
	public List<T> findAll() {
		//System.out.println(baseDao == null);
		return baseDao.findAll();
	}

	@Override
	public T findById(Serializable id) {
		return baseDao.findById(id);
	}

	@Override
	public List<T> find(String hql, List<Object> params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List<T> find(QueryHelper helper) {
		return baseDao.find(helper);
	}

	@Override
	public PageResult findByPage(String hql, int pageNo, int pageSize, Object... params) {
		return baseDao.findByPage(hql, pageNo, pageSize,params);
	}

	@Override
	public List<T> findByPage(String hql, int pageNo, int pageSize) {
		return baseDao.findByPage(hql, pageNo, pageSize);
	}

	@Override
	public PageResult findByPage(QueryHelper helper, int pageNo, int pageSize) {
		return baseDao.findByPage(helper, pageNo, pageSize);
	}

}
