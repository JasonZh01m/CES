 package com.sq.sso.model;
 
 import java.io.Serializable;
 
 public class UserRoleRelation
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int userId;
   private String roleId;
 
   public String toString()
   {
     return "UserRoleRelation [userId=" + this.userId + ",roleId=" + this.roleId + "]";
   }
 
   public int getUserId() {
     return this.userId;
   }
   public String getRoleId() {
     return this.roleId;
   }
   public void setUserId(int userId) {
     this.userId = userId;
   }
   public void setRoleId(String roleId) {
     this.roleId = roleId;
   }
 }

