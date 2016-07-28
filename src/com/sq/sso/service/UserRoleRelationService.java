package com.sq.sso.service;

import com.sq.core.service.Service;
import com.sq.sso.model.UserRoleRelation;
import java.util.List;

public abstract interface UserRoleRelationService extends Service<UserRoleRelation>
{
  public abstract int insert(UserRoleRelation paramUserRoleRelation);

  public abstract int delete(UserRoleRelation paramUserRoleRelation);

  public abstract List<UserRoleRelation> query(UserRoleRelation paramUserRoleRelation);
}

