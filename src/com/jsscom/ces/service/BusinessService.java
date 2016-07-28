package com.jsscom.ces.service;

import com.jsscom.ces.model.Business;
import com.sq.core.service.Service;
import java.util.List;

public abstract interface BusinessService extends Service<Business>
{
  public abstract List<Business> queryBusinessInfo();

  public abstract List<Business> queryBusiness(String paramString);

  public abstract int queryDevCode(String paramString);
}

