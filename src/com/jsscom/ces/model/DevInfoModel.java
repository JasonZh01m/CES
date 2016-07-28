 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.JSON;
 import java.io.Serializable;
 
 public class DevInfoModel
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String serialno;
   private String ip;
   private String mac;
   private String orgcode;
   private String windowname;
   private String orgname;
 
   public String getSerialno()
   {
     return this.serialno;
   }
 
   public String getIp()
   {
     return this.ip;
   }
 
   public String getMac()
   {
     return this.mac;
   }
 
   public String getOrgcode()
   {
     return this.orgcode;
   }
 
   public String getWindowname()
   {
     return this.windowname;
   }
 
   public String getOrgname()
   {
     return this.orgname;
   }
 
   public void setSerialno(String serialno)
   {
     this.serialno = serialno;
   }
 
   public void setIp(String ip)
   {
     this.ip = ip;
   }
 
   public void setMac(String mac)
   {
     this.mac = mac;
   }
 
   public void setOrgcode(String orgcode)
   {
     this.orgcode = orgcode;
   }
 
   public void setWindowname(String windowname)
   {
     this.windowname = windowname;
   }
 
   public void setOrgname(String orgname)
   {
     this.orgname = orgname;
   }
 
   public String toString()
   {
     return JSON.toJSONString(this);
   }
 }

