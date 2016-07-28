package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.DevInfo;
import com.sq.core.dao.Dao;
import java.util.Map;

public abstract interface DevInfoDao extends Dao<DevInfo>
{
  public abstract int findDevCode(String paramString);

  public abstract int validateUnique(Map<String, Object> paramMap);
}

