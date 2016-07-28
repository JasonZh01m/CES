package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.Media;
import com.sq.core.dao.Dao;

public abstract interface MediaDao extends Dao<Media>
{
  public abstract Media queryByMD5(String paramString);
}

