 package com.sq.sso.model;
 
 import com.sq.sso.vo.SSOSession;
 
 public class LoginResult
 {
   private boolean result;
   private String message;
   private String userName;
   private SSOSession session;
 
   public SSOSession getSession()
   {
     return this.session;
   }
   public void setSession(SSOSession session) {
     this.session = session;
   }
 
   public boolean isResult() {
     return this.result;
   }
   public void setResult(boolean result) {
     this.result = result;
   }
   public String getMessage() {
     return this.message;
   }
   public void setMessage(String message) {
     this.message = message;
   }
   public String getUserName() {
     return this.userName;
   }
   public void setUserName(String userName) {
     this.userName = userName;
   }
 }

