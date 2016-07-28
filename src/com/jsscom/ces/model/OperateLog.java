 package com.jsscom.ces.model;
 
 import java.io.Serializable;
 
 public class OperateLog
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int logId;
   private int userId;
   private String logContent;
   private int logType;
   private String logDate;
 
   public int getLogId()
   {
     return this.logId;
   }
   public void setLogId(int logId) {
     this.logId = logId;
   }
   public int getUserId() {
     return this.userId;
   }
   public void setUserId(int userId) {
     this.userId = userId;
   }
   public String getLogContent() {
     return this.logContent;
   }
   public void setLogContent(String logContent) {
     this.logContent = logContent;
   }
   public int getLogType() {
     return this.logType;
   }
   public void setLogType(int logType) {
     this.logType = logType;
   }
   public String getLogDate() {
     return this.logDate;
   }
   public void setLogDate(String logDate) {
     this.logDate = logDate;
   }
 }

