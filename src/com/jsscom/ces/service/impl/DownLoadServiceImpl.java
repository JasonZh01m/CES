 package com.jsscom.ces.service.impl;
 
 import com.jsscom.ces.data.dao.MediaDao;
 import com.jsscom.ces.data.dao.UpdateFileDao;
 import com.jsscom.ces.model.Media;
 import com.jsscom.ces.model.UpdateFile;
 import com.jsscom.ces.service.DownLoadService;
 import javax.annotation.Resource;
 import org.springframework.stereotype.Service;
 
 @Service
 public class DownLoadServiceImpl
   implements DownLoadService
 {
 
   @Resource
   private MediaDao mediaDao;
 
   @Resource
   private UpdateFileDao updateFileDao;
 
   public UpdateFile getUpdateFile(String md5Code)
   {
     return this.updateFileDao.queryByMD5(md5Code);
   }
 
   public Media getMedia(String md5Code)
   {
     return this.mediaDao.queryByMD5(md5Code);
   }
 
   public UpdateFile getUpdateFileName(String name)
   {
     return this.updateFileDao.queryByName(name);
   }
 }

