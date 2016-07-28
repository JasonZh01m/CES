package com.jsscom.ces.service;

import com.jsscom.ces.model.UpdateFile;
import com.jsscom.ces.vo.UploadResultModel;
import com.sq.core.service.Service;
import com.sq.core.vo.ResultModel;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public abstract interface UpdateFileService extends Service<UpdateFile>
{
  public abstract ResultModel deleteFile(int paramInt);

  public abstract UploadResultModel addFile(String paramString, MultipartFile paramMultipartFile, UpdateFile paramUpdateFile);

  public abstract List<UpdateFile> queryByClient();
}

