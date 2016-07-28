 package com.jsscom.ces.model;
 
 import com.alibaba.fastjson.JSON;
 
 public class Business
 {
   private int id;
   private String code;
   private String name;
   private String createTime;
 
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
   public String getCreateTime() {
     return this.createTime;
   }
   public void setCreateTime(String createTime) {
     this.createTime = createTime;
   }
 
   public String toString() {
     return JSON.toJSONString(this);
   }
 }

