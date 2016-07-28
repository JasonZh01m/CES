 package com.jsscom.ces.vo;
 
 public class UploadFileModel
 {
   private String type;
   private String name;
   private String md5Code;
   private String savePath;
   private String downloadPath;
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getSavePath() {
     return this.savePath;
   }
 
   public void setSavePath(String savePath) {
     this.savePath = savePath;
   }
 
   public String getType() {
     return this.type;
   }
 
   public String getMd5Code() {
     return this.md5Code;
   }
 
   public String getDownloadPath() {
     return this.downloadPath;
   }
 
   public void setType(String type) {
     this.type = type;
   }
 
   public void setMd5Code(String md5Code) {
     this.md5Code = md5Code;
   }
 
   public void setDownloadPath(String downloadPath) {
     this.downloadPath = downloadPath;
   }
 }

