package com.jsscom.ces.service;

import com.jsscom.ces.model.Organization;
import com.jsscom.ces.model.UserOrganizationRelation;
import com.sq.core.service.Service;
import com.sq.core.vo.ZtreeBean;
import java.util.List;

public abstract interface OrganizationService extends Service<Organization>
{
  public abstract boolean queryOrgByInfo(Organization paramOrganization);

  public abstract List<Organization> queryByClient();

  public abstract List<Organization> queryAllOrganizationByPid(String paramString);

  public abstract List<Organization> queryAllOrganizationByMap(Organization paramOrganization);

  public abstract boolean hasChilders(Organization paramOrganization);

  public abstract void deleteOrganizationByPid(int paramInt);

  public abstract Organization queryCheckedOrganizationByPid(String paramString);

  public abstract List<Organization> queryOrganizationInfo(Organization paramOrganization);

  public abstract List<Organization> queryOrganizationInfo();

  public abstract List<Organization> queryAllUserByOrganization(int paramInt);

  public abstract List<ZtreeBean> queryAllOrganiztionByUser(Organization paramOrganization);

  public abstract List<UserOrganizationRelation> queryAllUserByOrg(int paramInt);

  public abstract List<Organization> queryLikeByCode(String paramString);
}

