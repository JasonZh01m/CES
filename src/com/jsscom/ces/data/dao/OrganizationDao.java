package com.jsscom.ces.data.dao;

import java.util.List;

import com.jsscom.ces.model.Organization;
import com.sq.core.dao.Dao;

public abstract interface OrganizationDao extends Dao<Organization> {
	public abstract Organization findOrgByInfo(Organization paramOrganization);

	public abstract List<Organization> findAllOrganizationByPid(
			String paramString);

	public abstract List<Organization> findAllOrganizationByMap(
			Organization paramOrganization);

	public abstract Organization queryCheckedOrganizationByPid(
			String paramString);

	public abstract List<Organization> queryOrganizationInfo(
			Organization paramOrganization);

	public abstract List<Organization> queryOrganizationInfo();

	public abstract List<Organization> queryAllUserByOrganization(int paramInt);

	public abstract List<Organization> findAllOrganByMap(
			Organization paramOrganization);
}
