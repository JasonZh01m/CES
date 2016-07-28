package com.jsscom.ces.service;

import com.jsscom.ces.model.EmployeePhoto;
import com.jsscom.ces.vo.UploadFileModel;
import com.sq.core.service.Service;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public abstract interface EmployeePhotoService extends Service<EmployeePhoto>
{
  public abstract UploadFileModel addFile(String paramString, MultipartFile paramMultipartFile);

  public abstract List<EmployeePhoto> getEmployee(String paramString);

  public abstract void deleteFile(String paramString);

  public abstract UploadFileModel fileFile(String paramString);
}

