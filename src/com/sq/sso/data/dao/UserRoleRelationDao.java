package com.sq.sso.data.dao;

import com.sq.core.dao.Dao;
import com.sq.sso.model.UserRoleRelation;
import java.util.List;

public abstract interface UserRoleRelationDao extends Dao<UserRoleRelation>
{
  public abstract int insert(UserRoleRelation paramUserRoleRelation);

  public abstract int delete(UserRoleRelation paramUserRoleRelation);

  public abstract List<UserRoleRelation> query(UserRoleRelation paramUserRoleRelation);

  public abstract List<UserRoleRelation> queryUserIdByRoleId(UserRoleRelation paramUserRoleRelation);

  public abstract List<UserRoleRelation> queryUserIdByPerimssionId(UserRoleRelation paramUserRoleRelation);
}

