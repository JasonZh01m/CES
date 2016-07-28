package com.jsscom.ces.service;

import com.jsscom.ces.model.DevInfo;
import com.jsscom.ces.model.Employee;

public abstract interface DataUploadService
{
  public abstract int registerDevInfo(String paramString, DevInfo paramDevInfo);

  public abstract int registerEmployee(Employee paramEmployee);

  public abstract int updateEvaluate(Employee paramEmployee);
}

