 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.JSON;
 import com.google.gson.annotations.SerializedName;
 import com.sq.core.annotation.Cache;
 import com.sq.core.annotation.CacheKey;
 import com.sq.core.annotation.CodeField;
 import java.io.Serializable;
 
 @Cache(usage="r")
 public class DevInfo
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
 
   @CacheKey
   private int id;
 
   @CodeField
   private String code;
 
   @SerializedName("serialno")
   private String serialNo;
   private String ip;
   private String mac;
 
   @SerializedName("orgcode")
   private String orgCode;
 
   @SerializedName("windowname")
   private String windowName;
 
   @SerializedName("devstatus")
   private int auditStatus = -1;
   private String auditUserId;
   private String auditUserName;
   private String createTime;
   private String installTime;
 
   @SerializedName("orgname")
   private String orgName;
   private String userId;
   private String orgId;
   private String rids;
 
   public String getOrgId()
   {
     return this.orgId;
   }
 
   public void setOrgId(String orgId)
   {
     this.orgId = orgId;
   }
 
   public int getId()
   {
     return this.id;
   }
 
   public String getCode()
   {
     return this.code;
   }
 
   public String getSerialNo() {
     return this.serialNo;
   }
 
   public String getIp()
   {
     return this.ip;
   }
 
   public String getMac() {
     return this.mac;
   }
 
   public String getOrgCode() {
     return this.orgCode;
   }
 
   public String getWindowName() {
     return this.windowName;
   }
 
   public int getAuditStatus() {
     return this.auditStatus;
   }
 
   public String getOrgName() {
     return this.orgName;
   }
 
   public String getAuditUserId() {
     return this.auditUserId;
   }
 
   public String getAuditUserName()
   {
     return this.auditUserName;
   }
 
   public String getCreateTime()
   {
     return this.createTime;
   }
 
   public String getInstallTime()
   {
     return this.installTime;
   }
 
   public void setId(int id)
   {
     this.id = id;
   }
 
   public void setCode(String code)
   {
     this.code = code;
   }
 
   public void setSerialNo(String serialNo) {
     this.serialNo = serialNo;
   }
 
   public void setIp(String ip)
   {
     this.ip = ip;
   }
 
   public void setMac(String mac)
   {
     this.mac = mac;
   }
 
   public void setOrgCode(String orgCode) {
     this.orgCode = orgCode;
   }
 
   public void setWindowName(String windowName) {
     this.windowName = windowName;
   }
 
   public void setAuditStatus(int auditStatus)
   {
     this.auditStatus = auditStatus;
   }
 
   public void setAuditUserId(String auditUserId)
   {
     this.auditUserId = auditUserId;
   }
 
   public void setAuditUserName(String auditUserName)
   {
     this.auditUserName = auditUserName;
   }
 
   public void setCreateTime(String createTime)
   {
     this.createTime = createTime;
   }
 
   public void setInstallTime(String installTime)
   {
     this.installTime = installTime;
   }
 
   public void setOrgName(String orgName) {
     this.orgName = orgName;
   }
 
   public String getUserId() {
     return this.userId;
   }
 
   public String getRids()
   {
     return this.rids;
   }
 
   public void setRids(String rids)
   {
     this.rids = rids;
   }
 
   public void setUserId(String userId)
   {
     this.userId = userId;
   }
 
   public String toString()
   {
     return JSON.toJSONString(this);
   }
 }

