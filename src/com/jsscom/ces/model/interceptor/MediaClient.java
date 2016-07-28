 package com.jsscom.ces.model.interceptor;
 
 public class MediaClient
 {
   private String filename;
   private String filetype;
   private String filemd5;
   private String filepath;
 
   public String getFilename()
   {
     return this.filename;
   }
   public void setFilename(String filename) {
     this.filename = filename;
   }
   public String getFiletype() {
     return this.filetype;
   }
   public void setFiletype(String filetype) {
     this.filetype = filetype;
   }
   public String getFilemd5() {
     return this.filemd5;
   }
   public void setFilemd5(String filemd5) {
     this.filemd5 = filemd5;
   }
   public String getFilepath() {
     return this.filepath;
   }
   public void setFilepath(String filepath) {
     this.filepath = filepath;
   }
 }

