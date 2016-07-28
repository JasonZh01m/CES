package com.sq.sso.data.dao;

import com.sq.core.dao.Dao;
import com.sq.sso.model.Role;
import java.util.List;
import java.util.Map;

public abstract interface RoleDao extends Dao<Role>
{
  public abstract List<Role> queryAllRoleByUser(int paramInt);

  public abstract int update(Role paramRole);

  public abstract void deleteRolePermission(Role paramRole);

  public abstract void insertRolePermission(Map paramMap);

  public abstract List<Role> queryAllRole();
}

