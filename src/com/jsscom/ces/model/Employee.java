 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.JSON;
 import com.google.gson.annotations.SerializedName;
 
 public class Employee
 {
   private int id;
 
   @SerializedName("username")
   private String code;
 
   @SerializedName("usercode")
   private String name;
 
   @SerializedName("orgcode")
   private String orgCode;
   private String lastTime;
   private String ids;
   private String orgCodeName;
 
   public int getId()
   {
     return this.id;
   }
   public void setId(int id) {
     this.id = id;
   }
   public String getCode() {
     return this.code;
   }
   public void setCode(String code) {
     this.code = code;
   }
   public String getName() {
     return this.name;
   }
   public void setName(String name) {
     this.name = name;
   }
 
   public String getOrgCode() {
     return this.orgCode;
   }
   public void setOrgCode(String orgCode) {
     this.orgCode = orgCode;
   }
   public String getLastTime() {
     return this.lastTime;
   }
   public void setLastTime(String lastTime) {
     this.lastTime = lastTime;
   }
   public String getOrgCodeName() {
     return this.orgCodeName;
   }
   public void setOrgCodeName(String orgCodeName) {
     this.orgCodeName = orgCodeName;
   }
   public String getIds() {
     return this.ids;
   }
   public void setIds(String ids) {
     this.ids = ids;
   }
 
   public String toString() {
     return JSON.toJSONString(this);
   }
 }

