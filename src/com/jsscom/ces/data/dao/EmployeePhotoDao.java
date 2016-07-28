package com.jsscom.ces.data.dao;

import com.jsscom.ces.model.EmployeePhoto;
import com.sq.core.dao.Dao;
import java.util.List;

public abstract interface EmployeePhotoDao extends Dao<EmployeePhoto>
{
  public abstract List<EmployeePhoto> findFile(String paramString);

  public abstract List<EmployeePhoto> getEmployee(String paramString);
}

