package com.sq.core.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import com.sq.core.cache.GeneralCache;
import com.sq.core.cache.sql.SqlCacheForClass;
import com.sq.core.dao.Dao;
import com.sq.core.exception.DaoConfigException;
import com.sq.core.utils.AnnotationInfoUtil;

public abstract class DaoImpl<T> implements Dao<T> {
	private static final String PAGE_START = "start";
	private static final String PAGE_LIMIT = "limit";
	private static final String PAGE_OBJ = "obj";

	private final String underline = "_";

	private boolean cacheFlag = false;
	private String clsName;
	private String ClassName;
	private Class<?> cls;
	private SqlCacheForClass sqlCache;

	@Resource
	private SqlSessionTemplate sqlSession;

	@Resource
	private GeneralCache gc;

	public DaoImpl() {
		this.cls = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if ((t instanceof ParameterizedType)) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.cls = ((Class) p[0]);

			this.ClassName = (this.cls.getSimpleName() + '.');
			this.clsName = this.cls.getSimpleName();
			try {
				String keyField = AnnotationInfoUtil
						.getKeyAnnotationField(this.cls.newInstance());
				if (keyField == null)
					return;
				this.cacheFlag = true;
				this.sqlCache = new SqlCacheForClass();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			throw new DaoConfigException("Dao config class is not valid");
		}
	}

	public SqlSessionTemplate getSqlSession() {
		return this.sqlSession;
	}

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int count(String sqlId, T parameter) {
		int count = ((Integer) getSqlSession().selectOne(
				this.ClassName + sqlId, parameter)).intValue();
		return count;
	}

	@Override
	public List<T> queryPageList(String sqlId, T parameter, int index, int size) {
		Map m = new HashMap(8, 1.0F);

		int start = size * (index - 1);
		int limit = size;
		m.put(PAGE_OBJ, parameter);
		m.put(PAGE_START, Integer.valueOf(start));
		m.put(PAGE_LIMIT, Integer.valueOf(limit));
		List list = getSqlSession().selectList(this.ClassName + sqlId, m);
		return list;
	}

	@Override
	public T queryById(String sqlId, int id) {
		Object obj = getSqlSession().selectOne(this.ClassName + sqlId,
				Integer.valueOf(id));
		return obj;
	}

	@Override
	public T queryByCode(String sqlId, String code) {
		Object obj = getSqlSession().selectOne(this.ClassName + sqlId, code);
		return obj;
	}

	@Override
	public int insert(String sqlId, T entity) {
		int id = getSqlSession().insert(this.ClassName + sqlId, entity);
		return id;
	}

	@Override
	public int delete(String sqlId, T parameter) {
		int rs = getSqlSession().delete(this.ClassName + sqlId, parameter);
		return rs;
	}

	@Override
	public int deleteById(String sqlId, int id) {
		int rs = getSqlSession().delete(this.ClassName + sqlId,
				Integer.valueOf(id));
		return rs;
	}

	@Override
	public int update(String sqlId, T entity) {
		int rs = getSqlSession().update(this.ClassName + sqlId, entity);
		return rs;
	}

	@Override
	public List<T> queryList(String sqlId) {
		List list = getSqlSession().selectList(this.ClassName + sqlId);
		return list;
	}

	@Override
	public List<T> query(String sqlId, T parameter) {
		List list = getSqlSession().selectList(this.ClassName + sqlId,
				parameter);
		return list;
	}

	@Override
	public List<T> queryList(String sqlId, T parameter) {
		List list = getSqlSession().selectList(this.ClassName + sqlId,
				parameter);
		return list;
	}

	@Override
	public void batchInsert(String sqlId, List<T> entityList) {
		if ((entityList != null) && (entityList.size() > 0)) {
			SqlSession session = getSqlSession().getSqlSessionFactory()
					.openSession(ExecutorType.BATCH, false);
			try {
				int size = entityList.size();
				for (int i = 0; i < size; i++) {
					Object entity = entityList.get(i);
					getSqlSession().insert(this.ClassName + sqlId, entity);
				}
				session.commit();

				session.clearCache();
			} finally {
				session.close();
			}
		}
	}

	@Override
	public int batchUpdate(String sqlId, List<T> entityList) {
		int rs = 0;
		SqlSession session = getSqlSession().getSqlSessionFactory()
				.openSession(ExecutorType.BATCH, false);
		try {
			int size = entityList.size();
			for (int i = 0; i < size; i++) {
				Object entity = entityList.get(i);
				update(sqlId, entity);
				rs++;
			}
			session.commit();

			session.clearCache();
		} finally {
			session.close();
		}
		return rs;
	}

	@Override
	public List<T> iterator(String sqlId, T t) {
		List list = null;
		if (this.cacheFlag) {
			list = getListInCache(sqlId, t);
		} else {
			List idList = getSqlSession().selectList(this.ClassName + sqlId, t);
			if ((idList != null) && (idList.size() > 0)) {
				int lsize = idList.size();
				list = new ArrayList(lsize);
				for (int i = 0; i < lsize; i++) {
					Integer id = (Integer) idList.get(i);
					Object obj = getSqlSession().selectOne(
							this.ClassName + "queryById", id);
					list.add(obj);
				}
			}
		}
		return list;
	}

	private List<T> getListInCache(String sqlId, T t) {
		List list = null;
		try {
			String paramKey = this.sqlCache.getKeyForParam(t);
			List idList = this.sqlCache.getIdList(paramKey);
			List ids = null;
			if (idList == null) {
				Lock lock = this.sqlCache.getlock(paramKey);
				lock.lock();
				try {
					idList = this.sqlCache.getIdList(paramKey);
					if (idList == null) {
						ids = getSqlSession().selectList(
								this.ClassName + sqlId, t);
						if ((ids != null) && (ids.size() > 0)) {
							idList = new ArrayList(ids.size());
							for (int i = 0; i < ids.size(); i++) {
								Integer id = (Integer) ids.get(i);
								idList.add(AnnotationInfoUtil
										.getCacheKeyForModel(this.clsName, id));
							}
							System.out.println("********putSql:" + paramKey);
							this.sqlCache.putSql(paramKey, idList);
						}
					}
				} finally {
					lock.unlock();
				}
			}

			if ((idList != null) && (idList.size() > 0))
				list = getAndSetObjectInCache(list, idList, this.gc);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list;
	}

	private List<T> getAndSetObjectInCache(List<T> list, List<String> idList,
			GeneralCache gc) throws Exception {
		int idsszie = idList.size();
		list = new ArrayList(idsszie);
		for (int i = 0; i < idList.size(); i++) {
			String key = idList.get(i);
			Object obj = gc.get(key, this.cls);
			if (obj == null) {
				int id = Integer.valueOf(
						org.apache.commons.lang3.StringUtils.split(key,
								this.underline)[1]).intValue();
				obj = queryById("queryById", id);
				if (obj != null) {
					gc.put(key, obj);
				}
			}
			if (obj != null) {
				list.add(obj);
			}
		}
		return list;
	}

	public String getClsName() {
		return this.clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public boolean getCacheFlag() {
		return this.cacheFlag;
	}

	public void setCacheFlag(boolean cacheFlag) {
		this.cacheFlag = cacheFlag;
	}
}
