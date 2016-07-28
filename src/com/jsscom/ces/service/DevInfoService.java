package com.jsscom.ces.service;

import com.jsscom.ces.model.DevInfo;
import com.sq.core.service.Service;
import java.util.List;

public abstract interface DevInfoService extends Service<DevInfo>
{
  public abstract int queryDevCode(String paramString);

  public abstract List<DevInfo> queryByClient(DevInfo paramDevInfo);
}

