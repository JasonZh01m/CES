package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.UserOrganizationRelation;
import com.sq.core.dao.Dao;
import java.util.List;

public abstract interface UserOrganizationRelationDao extends Dao<UserOrganizationRelation>
{
  public abstract List<UserOrganizationRelation> query(UserOrganizationRelation paramUserOrganizationRelation);

  public abstract List<UserOrganizationRelation> queryOrgan(UserOrganizationRelation paramUserOrganizationRelation);

  public abstract List<UserOrganizationRelation> queryByOrg(int paramInt);
}

