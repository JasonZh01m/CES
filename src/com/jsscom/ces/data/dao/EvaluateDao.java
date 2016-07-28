package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.Evaluate;
import com.sq.core.dao.Dao;
import java.util.List;
import java.util.Map;

public abstract interface EvaluateDao extends Dao<Evaluate>
{
  public abstract int count(Map<String, Object> paramMap);

  public abstract List<Evaluate> queryPage(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

