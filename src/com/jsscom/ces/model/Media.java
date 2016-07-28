 package com.jsscom.ces.model;
 
 import com.sq.core.cache.JsonSerializable;
 import com.sq.core.vo.RegistrarModel;
 
 public class Media extends RegistrarModel
 {
   private int id;
   private String name;
   private String type;
   private String md5Code;
   private String savePath;
   private String downloadPath;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getSavePath() {
     return this.savePath;
   }
 
   public void setSavePath(String savePath) {
     this.savePath = savePath;
   }
 
   public int getId()
   {
     return this.id;
   }
 
   public String getName() {
     return this.name;
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
 
   public void setId(int id) {
     this.id = id;
   }
 
   public void setName(String name) {
     this.name = name;
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

