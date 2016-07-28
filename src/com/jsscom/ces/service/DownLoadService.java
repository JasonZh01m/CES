package com.jsscom.ces.service;

import com.jsscom.ces.model.Media;
import com.jsscom.ces.model.UpdateFile;

public abstract interface DownLoadService
{
  public abstract UpdateFile getUpdateFile(String paramString);

  public abstract Media getMedia(String paramString);

  public abstract UpdateFile getUpdateFileName(String paramString);
}

