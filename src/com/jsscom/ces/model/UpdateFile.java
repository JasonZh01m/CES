 package com.jsscom.ces.model;
 
 import com.sq.core.cache.JsonSerializable;
 
 public class UpdateFile
 {
   private int id;
   private String fileName;
   private String fileNote = "";
   private String fileVersion;
   private String fileType;
   private String filePath;
   private String fileMd5;
   private String savePath;
   private String action;
   private String downloadPath = "";
   private String type;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getDownloadPath()
   {
     return this.downloadPath;
   }
 
   public void setDownloadPath(String downloadPath)
   {
     this.downloadPath = downloadPath;
   }
 
   public int getId()
   {
     return this.id;
   }
 
   public String getFileMd5()
   {
     return this.fileMd5;
   }
 
   public void setFileMd5(String fileMd5)
   {
     this.fileMd5 = fileMd5;
   }
 
   public String getFileName()
   {
     return this.fileName;
   }
 
   public String getFileNote() {
     return this.fileNote;
   }
 
   public String getFileVersion() {
     return this.fileVersion;
   }
 
   public String getFileType() {
     return this.fileType;
   }
 
   public String getFilePath() {
     return this.filePath;
   }
 
   public String getSavePath()
   {
     return this.savePath;
   }
 
   public String getAction() {
     return this.action;
   }
 
   public void setId(int id) {
     this.id = id;
   }
 
   public void setFileName(String fileName) {
     this.fileName = fileName;
   }
 
   public void setFileNote(String fileNote) {
     this.fileNote = fileNote;
   }
 
   public void setFileVersion(String fileVersion) {
     this.fileVersion = fileVersion;
   }
 
   public void setFileType(String fileType) {
     this.fileType = fileType;
   }
 
   public void setFilePath(String filePath) {
     this.filePath = filePath;
   }
 
   public void setSavePath(String savePath)
   {
     this.savePath = savePath;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getType()
   {
     return this.type;
   }
 
   public void setType(String type)
   {
     this.type = type;
   }
 }

