package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.UpdateFile;
import com.sq.core.dao.Dao;

public abstract interface UpdateFileDao extends Dao<UpdateFile>
{
  public abstract UpdateFile queryByMD5(String paramString);

  public abstract UpdateFile queryByName(String paramString);
}

