 package com.jsscom.ces.vo;
 
 import com.sq.core.cache.JsonSerializable;
 
 public class ArgsModel<T>
 {
   private String terminalid;
   private String reqid;
   private T param;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getTerminalid()
   {
     return this.terminalid;
   }
 
   public String getReqid()
   {
     return this.reqid;
   }
 
   public T getParam()
   {
     return this.param;
   }
 
   public void setTerminalid(String terminalid)
   {
     this.terminalid = terminalid;
   }
 
   public void setReqid(String reqid)
   {
     this.reqid = reqid;
   }
 
   public void setParam(T param)
   {
     this.param = param;
   }
 }

