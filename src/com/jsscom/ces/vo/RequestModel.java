 package com.jsscom.ces.vo;
 
 import com.alibaba.fastjson.JSON;
 
 public class RequestModel
 {
   private String cmd;
   private String args;
   private String verifycode;
 
   public String getCmd()
   {
     return this.cmd;
   }
 
   public void setCmd(String cmd) {
     this.cmd = cmd;
   }
 
   public String getArgs()
   {
     return this.args;
   }
 
   public void setArgs(String args) {
     this.args = args;
   }
 
   public String getVerifycode() {
     return this.verifycode;
   }
 
   public void setVerifycode(String verifycode) {
     this.verifycode = verifycode;
   }
 
   public String toString()
   {
     return JSON.toJSONString(this);
   }
 }

