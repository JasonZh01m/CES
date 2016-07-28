package com.jsscom.ces.data.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jsscom.ces.data.dao.BusinessDao;
import com.jsscom.ces.model.Business;

@Repository
// public class BusinessDaoImpl extends DaoImpl implements BusinessDao {
public class BusinessDaoImpl implements BusinessDao {
	@Override
	public List<Business> queryBusinessInfo() {
		return getSqlSession()
				.selectList(super.getClsName() + ".queryBusiness");
	}

	@Override
	public List<Business> queryBusiness(String name) {
		return getSqlSession().selectList(super.getClsName() + ".query", name);
	}

	@Override
	public int findBusinessByCode(String code) {
		return ((Integer) getSqlSession().selectOne(
				getClsName() + ".queryBusinessByCode", code)).intValue();
	}

	@Override
	public int count(String paramString, Business paramT) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Business> queryPageList(String paramString, Business paramT,
			int paramInt1, int paramInt2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Business queryById(String paramString, int paramInt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Business queryByCode(String paramString1, String paramString2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(String paramString, Business paramT) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String paramString, Business paramT) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void batchInsert(String paramString, List<Business> paramList) {
		// TODO Auto-generated method stub

	}

	@Override
	public int deleteById(String paramString, int paramInt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String paramString, Business paramT) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchUpdate(String paramString, List<Business> paramList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Business> queryList(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Business> query(String paramString, Business paramT) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Business> queryList(String paramString, Business paramT) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Business> iterator(String paramString, Business paramT) {
		// TODO Auto-generated method stub
		return null;
	}
}
