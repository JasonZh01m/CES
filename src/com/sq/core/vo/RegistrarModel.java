 package com.sq.core.vo;
 
 import com.sq.core.utils.DateUtil;
 
 public class RegistrarModel
 {
   private String createUserName;
   private int createUserId;
   private String createTime;
 
   public String getCreateUserName()
   {
     return this.createUserName;
   }
 
   public int getCreateUserId() {
     return this.createUserId;
   }
 
   public String getCreateTime() {
     if (this.createTime == null) {
       this.createTime = DateUtil.getDateTime();
     }
     return this.createTime;
   }
 
   public void setCreateUserName(String createUserName) {
     this.createUserName = createUserName;
   }
 
   public void setCreateUserId(int createUserId) {
     this.createUserId = createUserId;
   }
 
   public void setCreateTime(String createTime) {
     this.createTime = createTime;
   }
 }

