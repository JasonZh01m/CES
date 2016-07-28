 package com.jsscom.ces.vo;
 
 import com.sq.core.cache.JsonSerializable;
 import java.util.List;
 
 public class ResponseModel<T>
 {
   private String terminalno;
   private String reqid;
   private String returncode = "9000";
   private String returnmsg;
   private List<T> data;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getTerminalno() {
     return this.terminalno;
   }
 
   public String getReqid() {
     return this.reqid;
   }
 
   public String getReturncode() {
     return this.returncode;
   }
 
   public String getReturnmsg() {
     return this.returnmsg;
   }
 
   public List<T> getData() {
     return this.data;
   }
 
   public void setTerminalno(String terminalno) {
     this.terminalno = terminalno;
   }
 
   public void setReqid(String reqid) {
     this.reqid = reqid;
   }
 
   public void setReturncode(String returncode) {
     this.returncode = returncode;
   }
 
   public void setReturnmsg(String returnmsg) {
     this.returnmsg = returnmsg;
   }
 
   public void setData(List<T> data) {
     this.data = data;
   }
 }

