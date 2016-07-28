 package com.jsscom.ces.vo;
 
 public class UploadResultModel
 {
   private int success;
   private String msg;
   private String info;
   private String type;
   private String md5Code;
   private String savePath;
   private String downloadPath;
 
   public String getSavePath()
   {
     return this.savePath;
   }
 
   public void setSavePath(String savePath) {
     this.savePath = savePath;
   }
 
   public int getSuccess() {
     return this.success;
   }
 
   public String getMsg() {
     return this.msg;
   }
 
   public String getInfo() {
     return this.info;
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
 
   public void setSuccess(int success) {
     this.success = success;
   }
 
   public void setMsg(String msg) {
     this.msg = msg;
   }
 
   public void setInfo(String info) {
     this.info = info;
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

