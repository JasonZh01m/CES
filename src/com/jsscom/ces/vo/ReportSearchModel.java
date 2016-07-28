 package com.jsscom.ces.vo;
 
 import com.sq.core.cache.JsonSerializable;
 
 public class ReportSearchModel
 {
   private String startTime;
   private String endTime;
   private String orgCode;
   private String serviceCode;
   private String date;
   private String userId;
   private String orgId;
 
   public String toString()
   {
     return JsonSerializable.serializableString(this);
   }
 
   public String getStartTime()
   {
     return this.startTime;
   }
 
   public String getEndTime()
   {
     return this.endTime;
   }
 
   public void setStartTime(String startTime)
   {
     this.startTime = startTime;
   }
 
   public void setEndTime(String endTime)
   {
     this.endTime = endTime;
   }
 
   public String getOrgCode()
   {
     return this.orgCode;
   }
 
   public void setOrgCode(String orgCode)
   {
     this.orgCode = orgCode;
   }
 
   public String getServiceCode()
   {
     return this.serviceCode;
   }
 
   public void setServiceCode(String serviceCode)
   {
     this.serviceCode = serviceCode;
   }
 
   public String getDate()
   {
     return this.date;
   }
 
   public void setDate(String date)
   {
     this.date = date;
   }
 
   public String getUserId()
   {
     return this.userId;
   }
 
   public void setUserId(String userId)
   {
     this.userId = userId;
   }
 
   public String getOrgId()
   {
     return this.orgId;
   }
 
   public void setOrgId(String orgId)
   {
     this.orgId = orgId;
   }
 }

