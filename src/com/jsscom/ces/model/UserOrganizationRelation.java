 package com.jsscom.ces.model;
 
 public class UserOrganizationRelation
 {
   private int userId;
   private String orgId;
   private String userIds;
   private String code;
 
   public int getUserId()
   {
     return this.userId;
   }
   public void setUserId(int userId) {
     this.userId = userId;
   }
   public String getOrgId() {
     return this.orgId;
   }
   public void setOrgId(String orgId) {
     this.orgId = orgId;
   }
   public String getCode() {
     return this.code;
   }
   public void setCode(String code) {
     this.code = code;
   }
   public String getUserIds() {
     return this.userIds;
   }
   public void setUserIds(String userIds) {
     this.userIds = userIds;
   }
 }

