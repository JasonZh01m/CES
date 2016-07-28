package com.jsscom.ces.service;

import com.jsscom.ces.model.UserOrganizationRelation;
import com.sq.core.service.Service;
import java.util.List;

public abstract interface UserOrganizationRelationService extends Service<UserOrganizationRelation>
{
  public abstract List<UserOrganizationRelation> query(UserOrganizationRelation paramUserOrganizationRelation);

  public abstract List<UserOrganizationRelation> queryByOrgan(UserOrganizationRelation paramUserOrganizationRelation);

  public abstract int deleteByUserOrganization(UserOrganizationRelation paramUserOrganizationRelation);
}

