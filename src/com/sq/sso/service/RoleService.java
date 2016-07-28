package com.sq.sso.service;

import com.sq.core.service.Service;
import com.sq.sso.model.Role;
import java.util.List;

public abstract interface RoleService extends Service<Role>
{
  public abstract List<Role> queryAllRoleByUser(int paramInt);

  public abstract void updateRole(Role paramRole, String paramString);

  public abstract void insertRole(Role paramRole, String paramString);

  public abstract void deleteRole(int paramInt);

  public abstract List<Role> queryAllRole();

  public abstract boolean checkRoleCode(Role paramRole);
}

