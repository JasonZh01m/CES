package com.sq.sso.service;

import com.sq.core.service.Service;
import com.sq.core.vo.ZtreeBean;
import com.sq.sso.model.Permission;
import java.util.List;

public abstract interface PermissionService extends Service<Permission>
{
  public abstract List<Permission> queryPermissionForMenu(String paramString);

  public abstract List<Permission> getOperateByRid(int paramInt, String paramString);

  public abstract List<Permission> queryAllPermissionByRoleId(int paramInt);

  public abstract boolean validateResourceString(String paramString1, String paramString2);

  public abstract List<ZtreeBean> queryAllPermissionByRole(int paramInt);

  public abstract List<ZtreeBean> queryAllPermissionZtreeBean();

  public abstract Integer validateResourceString(String paramString);

  public abstract List<Permission> initUriBySysId(int paramInt);

  public abstract List<Permission> queryAllPermission();

  public abstract int deletePermissionId(int paramInt);

  public abstract List<Permission> queryAllPermissionByUserId(String paramString);

  public abstract int updatePermission(Permission paramPermission);

  public abstract int addPermission(Permission paramPermission);
}

