package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.Business;
import com.sq.core.dao.Dao;
import java.util.List;

public abstract interface BusinessDao extends Dao<Business>
{
  public abstract List<Business> queryBusinessInfo();

  public abstract List<Business> queryBusiness(String paramString);

  public abstract int findBusinessByCode(String paramString);
}

