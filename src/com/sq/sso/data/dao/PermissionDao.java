package com.sq.sso.data.dao;

import com.sq.core.dao.Dao;
import com.sq.sso.model.Permission;
import com.sq.sso.model.Role;
import java.util.List;

public abstract interface PermissionDao extends Dao<Permission>
{
  public abstract List<Permission> queryAllPermissionByRole(Role paramRole);

  public abstract List<Permission> queryAllPermissionByRoleIdAndType(int paramInt);

  public abstract List<String> queryMainPagesByRole(Role paramRole);

  public abstract List<String> queryMainPages();

  public abstract List<Integer> queryForSysId(String paramString);

  public abstract List<Permission> queryAllPermissionByPid(String paramString);

  public abstract Integer validateResourceString(String paramString);

  public abstract void deleteByResourceString(String paramString);

  public abstract List<Permission> queryChildPermission(String paramString, int paramInt, boolean paramBoolean);

  public abstract List<Permission> queryAllLinkPermissionByPid(String paramString);

  public abstract List<Permission> queryAllPermissionByUserId(String paramString);
}

