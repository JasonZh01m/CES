 package com.sq.sso;
 
 import com.sq.core.cache.JsonSerializable;
 import java.io.Serializable;
 
 public class SSOSession
   implements Serializable
 {
   private static final long serialVersionUID = 1434098209343541111L;
   private String loginName;
   private String rids;
   private String userName;
   private String roleName;
   private String ip;
   private long logoutTime;
   private long loginTime;
   private String mac;
   private String tokenCode;
   private String userId;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getUserId() {
     return this.userId;
   }
 
   public void setUserId(String userId) {
     this.userId = userId;
   }
 
   public String getLoginName() {
     return this.loginName;
   }
 
   public void setLoginName(String loginName) {
     this.loginName = loginName;
   }
 
   public String getRids()
   {
     return this.rids;
   }
 
   public void setRids(String rids)
   {
     this.rids = rids;
   }
 
   public String getUserName() {
     return this.userName;
   }
 
   public void setUserName(String userName) {
     this.userName = userName;
   }
 
   public String getRoleName() {
     return this.roleName;
   }
 
   public void setRoleName(String roleName) {
     this.roleName = roleName;
   }
 
   public String getIp() {
     return this.ip;
   }
 
   public void setIp(String ip) {
     this.ip = ip;
   }
 
   public long getLoginTime() {
     return this.loginTime;
   }
 
   public void setLoginTime(long loginTime) {
     this.loginTime = loginTime;
   }
 
   public long getLogoutTime()
   {
     return this.logoutTime;
   }
 
   public void setLogoutTime(long logoutTime)
   {
     this.logoutTime = logoutTime;
   }
 
   public String getMac() {
     return this.mac;
   }
 
   public void setMac(String mac) {
     this.mac = mac;
   }
 
   public String getTokenCode() {
     return this.tokenCode;
   }
 
   public void setTokenCode(String tokenCode) {
     this.tokenCode = tokenCode;
   }
 }

