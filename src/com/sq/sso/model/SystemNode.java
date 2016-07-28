 package com.sq.sso.model;
 
 import java.io.Serializable;
 
 public class SystemNode
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int sysId;
   private String sysResourceString;
 
   public String toString()
   {
     return "SystemNode [sysId=" + this.sysId + ",sysResourceString=" + this.sysResourceString + "]";
   }
 
   public int getSysId() {
     return this.sysId;
   }
   public String getSysResourceString() {
     return this.sysResourceString;
   }
   public void setSysId(int sysId) {
     this.sysId = sysId;
   }
   public void setSysResourceString(String sysResourceString) {
     this.sysResourceString = sysResourceString;
   }
 }

