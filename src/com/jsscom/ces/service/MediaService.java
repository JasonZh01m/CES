package com.jsscom.ces.service;

import com.jsscom.ces.model.Media;
import com.jsscom.ces.vo.UploadResultModel;
import com.sq.core.service.Service;
import com.sq.core.vo.ResultModel;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public abstract interface MediaService extends Service<Media>
{
  public abstract UploadResultModel addFile(String paramString, MultipartFile paramMultipartFile, Media paramMedia);

  public abstract ResultModel deleteFile(String paramString);

  public abstract List<Media> queryByClient();
}

