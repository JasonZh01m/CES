 package com.jsscom.ces.vo;
 
 import com.alibaba.fastjson.JSON;
 
 public class EmployeeModel
 {
   private String usercode;
   private String username;
   private String orgcode;
 
   public String getUsercode()
   {
     return this.usercode;
   }
   public void setUsercode(String usercode) {
     this.usercode = usercode;
   }
   public String getUsername() {
     return this.username;
   }
   public void setUsername(String username) {
     this.username = username;
   }
   public String getOrgcode() {
     return this.orgcode;
   }
   public void setOrgcode(String orgcode) {
     this.orgcode = orgcode;
   }
 
   public String toString()
   {
     return JSON.toJSONString(this);
   }
 }

