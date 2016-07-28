 package com.sq.core.vo;
 
 public class VerifyPermissionsModel
 {
   private String loginName;
   private String reqIp;
   private String mac;
   private String uri;
   private String tokenKey;
 
   public String toString()
   {
     return "AppFilterModel: [loginName = " + this.loginName + ",reqIp = " + this.reqIp + ",mac = " + this.mac + ",uri = " + this.uri + ",tokenKey=" + this.tokenKey + "]";
   }
 
   public String getLoginName() {
     return this.loginName;
   }
   public String getReqIp() {
     return this.reqIp;
   }
   public String getMac() {
     return this.mac;
   }
   public String getUri() {
     return this.uri;
   }
   public void setLoginName(String loginName) {
     this.loginName = loginName;
   }
   public void setReqIp(String reqIp) {
     this.reqIp = reqIp;
   }
   public void setMac(String mac) {
     this.mac = mac;
   }
   public void setUri(String uri) {
     this.uri = uri;
   }
 
   public String getTokenKey() {
     return this.tokenKey;
   }
   public void setTokenKey(String tokenKey) {
     this.tokenKey = tokenKey;
   }
 }

