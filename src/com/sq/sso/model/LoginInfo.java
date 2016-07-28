 package com.sq.sso.model;
 
 public class LoginInfo
 {
   private String userId;
   private String userName;
   private String loginName;
   private String passWord;
   private String clientIp;
   private String targetUrl;
   private String captcha;
   private String timeOut;
   private long overTime;
   private String captchaKey;
 
   public String getCaptchaKey()
   {
     return this.captchaKey;
   }
 
   public void setCaptchaKey(String captchaKey) {
     this.captchaKey = captchaKey;
   }
   public String getUserId() {
     return this.userId;
   }
   public void setUserId(String userId) {
     this.userId = userId;
   }
   public String getUserName() {
     return this.userName;
   }
   public void setUserName(String userName) {
     this.userName = userName;
   }
   public String getLoginName() {
     return this.loginName;
   }
   public void setLoginName(String loginName) {
     this.loginName = loginName;
   }
   public String getPassWord() {
     return this.passWord;
   }
   public void setPassWord(String passWord) {
     this.passWord = passWord;
   }
   public String getClientIp() {
     return this.clientIp;
   }
   public void setClientIp(String clientIp) {
     this.clientIp = clientIp;
   }
   public String getTargetUrl() {
     return this.targetUrl;
   }
   public void setTargetUrl(String targetUrl) {
     this.targetUrl = targetUrl;
   }
   public String getCaptcha() {
     return this.captcha;
   }
   public void setCaptcha(String captcha) {
     this.captcha = captcha;
   }
 
   public String getTimeOut() {
     return this.timeOut;
   }
   public void setTimeOut(String timeOut) {
     this.timeOut = timeOut;
   }
   public long getOverTime() {
     return this.overTime;
   }
   public void setOverTime(long overTime) {
     this.overTime = overTime;
   }
   public String toString() {
     return "LoginInfo: [userId = " + this.userId + ",userName = " + this.userName + ",loginName = " + this.loginName + ",passWord = " + this.passWord + ",clientIp = " + this.clientIp + ",captcha = " + this.captcha + "]";
   }
 }

