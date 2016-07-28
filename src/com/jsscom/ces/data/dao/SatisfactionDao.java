package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.Satisfaction;
import com.sq.core.dao.Dao;
import java.util.List;
import java.util.Map;

public abstract interface SatisfactionDao extends Dao<Satisfaction>
{
  public abstract int count(String paramString, Map<String, Object> paramMap);

  public abstract List<Satisfaction> queryPage(String paramString, Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

