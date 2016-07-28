 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.JSON;
 import java.io.Serializable;
 
 public class Organization
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int id;
   private String code;
   private int parentId;
   private String name;
   private int level;
   private String address;
   private String pId;
   private int userId;
   private String orgId;
 
   public int getId()
   {
     return this.id;
   }
 
   public String getCode() {
     return this.code;
   }
 
   public int getParentId()
   {
     return this.parentId;
   }
 
   public String getName() {
     return this.name;
   }
 
   public int getLevel() {
     return this.level;
   }
 
   public String getAddress() {
     return this.address;
   }
 
   public String getpId() {
     return this.pId;
   }
 
   public void setId(int id) {
     this.id = id;
   }
 
   public void setCode(String code)
   {
     this.code = code;
   }
 
   public void setParentId(int parentId)
   {
     this.parentId = parentId;
   }
 
   public void setName(String name)
   {
     this.name = name;
   }
 
   public void setLevel(int level)
   {
     this.level = level;
   }
 
   public void setAddress(String address)
   {
     this.address = address;
   }
 
   public void setpId(String pId)
   {
     this.pId = pId;
   }
 
   public String getOrgId()
   {
     return this.orgId;
   }
 
   public void setOrgId(String orgId) {
     this.orgId = orgId;
   }
 
   public int getUserId() {
     return this.userId;
   }
 
   public void setUserId(int userId) {
     this.userId = userId;
   }
 
   public String toString()
   {
     return JSON.toJSONString(this);
   }
 }

