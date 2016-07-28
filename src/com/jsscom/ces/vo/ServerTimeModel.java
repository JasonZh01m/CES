 package com.jsscom.ces.vo;
 
 public class ServerTimeModel
 {
   private String sysTime;
 
   public ServerTimeModel(String dateTime)
   {
     this.sysTime = dateTime;
   }
 
   public String getSysTime() {
     return this.sysTime;
   }
 
   public void setSysTime(String sysTime) {
     this.sysTime = sysTime;
   }
 }

